package View;

import Controller.AllSalesController;
import Controller.Formatting;
import Controller.SalesController;
import Dao.SalesDAO;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Sales;
import Model.Vendedor;
import Services.SaleService;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AlterData extends javax.swing.JFrame {

    private String cpf;
    private String cliente;
    private String observation;
    private String trSell;
    private String contacts;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter dtfComplete = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Origin originSell;
    private Period periodInstalation;
    private Packages packgeSelected;
    private Situation situation;
    public static LocalDateTime ldTSaleMarked;
    public static LocalDateTime ldTSaleMade;
    private float valuePackage;
    public static int idUpdate;
    boolean fieldsVisible = true;
    Formatting format = new Formatting();
    SaleService ss = new SaleService();
    AllSalesController asc = new AllSalesController();

    public AlterData() {
        initComponents();
        cbPacote.setModel(new DefaultComboBoxModel(Packages.values()));
        cbPacote.removeItem(Packages.ALL);
        // cbPacote.removeItem(Packages.SELECT);
        cbPeriodo.setModel(new DefaultComboBoxModel(Period.values()));
        // cbPeriodo.removeItem(Period.SELECT);
        cbOrigem.setModel(new DefaultComboBoxModel(Origin.values()));
        //cbOrigem.removeItem(Origin.SELECT);
        cbSituatiom.setModel(new DefaultComboBoxModel(Situation.values()));
        cbSituatiom.removeItem(Situation.ALL);
        //cbSituatiom.removeItem(Situation.SELECT);

    }

    private void fillingPackage() {
        if (cbPacote.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um pacote", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            cbPacote.requestFocus();
        } else {
            packgeSelected = (Packages) cbPacote.getSelectedItem();

        }
        Packages[] pack = {Packages.ALL};
        valuePackage = SaleService.ValuePerSale(SalesDAO.returnQtdPackgeInstalled(pack, situation.INSTALLED, 'm'), packgeSelected);
        txtDataInstalacao.requestFocus();
    }

    private String fielWithoutFielling() {
        String fwf = "";
        if (txtCPF.getText().equals("")) {
            fwf += "\"CPF\" ";
        }
        if (txtCliente.getText().equals("")) {
            fwf += "\"Cliente\" ";
        }
         if (txtTrVendida.getText().equals("")) {
            fwf += "\" TR \" ";
        }
        if (txtContato.getText().equals("")) {
            fwf += "\"Contato\" ";
        }
        if (cbOrigem.getSelectedIndex() == 0) {
            fwf += "\"Origem\" ";
        }
        if (cbPacote.getSelectedIndex() == 0) {
            fwf += "\"Pacote\" ";
        }
        if (cbPeriodo.getSelectedIndex() == 0) {
            fwf += "\"Periodo\" ";
        }

        return fwf;

    }

    private void fillData() {

        String fielsWithout = fielWithoutFielling();
        if (fielsWithout == "" || fielsWithout == null || fielsWithout.length() == 0) {
            if (cliente == null) {
                cliente = txtCliente.getText();
            }

            if (cpf == null) {
                cpf = txtCPF.getText();
            }
              if (trSell == null) {
                trSell = txtTrVendida.getText();
            }

            if (contacts == null) {
                contacts = txtContato.getText();
            }
            if (observation == null) {
                observation = txaObs.getText();
            }

            if (ldTSaleMarked == null) {
                InsertPeriod();
            }

            if (packgeSelected == null) {
                fillingPackage();
            }

            if (originSell == null) {
                originSell = Origin.valueOf(cbOrigem.getSelectedItem() + "");
            }
            if (situation == null) {
                situation = (Situation) cbSituatiom.getSelectedItem();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Favor preencher os seguintes campos\n " + fielsWithout, "Aviso", JOptionPane.WARNING_MESSAGE);

        }

    }

    private void InsertPeriod() {

        periodInstalation = (Period) cbPeriodo.getSelectedItem();
        if (periodInstalation == Period.MORNING) {
            ldTSaleMarked = format.dateTimeFormaterBank(txtDataInstalacao.getText() + " 08:00");

        }
        if (periodInstalation == Period.AFTERNOON) {
            ldTSaleMarked = format.dateTimeFormaterBank(txtDataInstalacao.getText() + " 13:00");

        }

        txtTrVendida.requestFocus();

    }

    private void insertOrigin() {

        originSell = (Origin) cbOrigem.getSelectedItem();
        txaObs.requestFocus();

    }

    private void update() {
        SalesController sc = new SalesController();

        sc.updateSales(new Sales(idUpdate, new Vendedor(trSell),
                ldTSaleMade, cpf, cliente,
                contacts, packgeSelected.toString(),
                valuePackage, ldTSaleMarked, periodInstalation,
                originSell, situation, observation));

        ss.tableFormatColors(AllSales.tblRelatorioVendas);

        sc.fillingsPacksges('m');
        sc.returnData('a', (DefaultTableModel) AllSales.tblRelatorioVendas.getModel(), LocalDate.MIN, LocalDate.MIN);
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCPF = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        txtContato = new javax.swing.JTextField();
        cbPacote = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        txtDataInstalacao = new javax.swing.JTextField();
        cbPeriodo = new javax.swing.JComboBox<>();
        txtTrVendida = new javax.swing.JTextField();
        cbOrigem = new javax.swing.JComboBox<>();
        cbSituatiom = new javax.swing.JComboBox<>();
        btnSale = new javax.swing.JButton();
        btnCancell = new javax.swing.JButton();
        txtValue = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCPF.setBackground(new java.awt.Color(255, 255, 255));
        txtCPF.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "CPF/CNPJ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFKeyPressed(evt);
            }
        });
        jPanel1.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 54, 110, 40));

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 54, 190, 40));

        txtContato.setBackground(new java.awt.Color(255, 255, 255));
        txtContato.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Contato", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtContato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContatoKeyPressed(evt);
            }
        });
        jPanel1.add(txtContato, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 54, 130, 40));

        cbPacote.setBackground(new java.awt.Color(255, 255, 255));
        cbPacote.setBorder(javax.swing.BorderFactory.createTitledBorder("Pacote"));
        cbPacote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPacoteActionPerformed(evt);
            }
        });
        cbPacote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPacoteKeyPressed(evt);
            }
        });
        jPanel1.add(cbPacote, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 137, 110, 40));

        txaObs.setBackground(new java.awt.Color(255, 255, 255));
        txaObs.setColumns(20);
        txaObs.setForeground(new java.awt.Color(255, 0, 0));
        txaObs.setRows(5);
        txaObs.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Observação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txaObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaObsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaObs);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 368, 450, 66));

        txtDataInstalacao.setBackground(new java.awt.Color(255, 255, 255));
        txtDataInstalacao.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data de Instalação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtDataInstalacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataInstalacaoKeyPressed(evt);
            }
        });
        jPanel1.add(txtDataInstalacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 137, 181, 40));

        cbPeriodo.setBackground(new java.awt.Color(255, 255, 255));
        cbPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder("Perido"));
        cbPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPeriodoActionPerformed(evt);
            }
        });
        cbPeriodo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPeriodoKeyPressed(evt);
            }
        });
        jPanel1.add(cbPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 137, 110, 40));

        txtTrVendida.setBackground(new java.awt.Color(255, 255, 255));
        txtTrVendida.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "TR vendida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtTrVendida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTrVendidaKeyPressed(evt);
            }
        });
        jPanel1.add(txtTrVendida, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 216, 120, 40));

        cbOrigem.setBackground(new java.awt.Color(255, 255, 255));
        cbOrigem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Origem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        cbOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOrigemActionPerformed(evt);
            }
        });
        cbOrigem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbOrigemKeyPressed(evt);
            }
        });
        jPanel1.add(cbOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 216, 173, 40));

        cbSituatiom.setBackground(new java.awt.Color(255, 255, 255));
        cbSituatiom.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Situação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        cbSituatiom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSituatiomActionPerformed(evt);
            }
        });
        cbSituatiom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbSituatiomKeyPressed(evt);
            }
        });
        jPanel1.add(cbSituatiom, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 216, 110, 40));

        btnSale.setText("Atualizar Venda");
        btnSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleActionPerformed(evt);
            }
        });
        btnSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSaleKeyPressed(evt);
            }
        });
        jPanel1.add(btnSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 463, 120, 40));

        btnCancell.setBackground(new java.awt.Color(255, 51, 51));
        btnCancell.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnCancell.setForeground(new java.awt.Color(255, 255, 255));
        btnCancell.setText("Cancelar");
        btnCancell.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        btnCancell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancellActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancell, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 462, 120, 40));

        txtValue.setBackground(new java.awt.Color(255, 255, 255));
        txtValue.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Valor do pacote", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValueKeyPressed(evt);
            }
        });
        jPanel1.add(txtValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 284, 130, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            cliente = txtCliente.getText();
            txtContato.requestFocus();
        }
    }//GEN-LAST:event_txtClienteKeyPressed

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (txtCPF.getText().length() < 11) {
                JOptionPane.showMessageDialog(null, "O cpf/cnpj que digitou esta incorreto\n TAmanhaho: " + txtCPF.getText().length() + " caractes", "Aviso", JOptionPane.ERROR_MESSAGE);
            } else {
                cpf = txtCPF.getText();
                txtCliente.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCPFKeyPressed

    private void cbPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPeriodoActionPerformed
        InsertPeriod();
    }//GEN-LAST:event_cbPeriodoActionPerformed

    private void cbPeriodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPeriodoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            InsertPeriod();

        }
    }//GEN-LAST:event_cbPeriodoKeyPressed

    private void txtContatoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContatoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (txtCPF.getText().length() < 11) {
                JOptionPane.showMessageDialog(null, "O cpf/cnpj que digitou esta incorreto\n TAmanhaho: " + txtCPF.getText().length() + " caractes", "Aviso", JOptionPane.ERROR_MESSAGE);
            } else {

                contacts = txtCPF.getText();

                cbPacote.requestFocus();
            }
        }
    }//GEN-LAST:event_txtContatoKeyPressed

    private void txtTrVendidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrVendidaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            cbOrigem.requestFocus();
        }
    }//GEN-LAST:event_txtTrVendidaKeyPressed

    private void cbOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrigemActionPerformed
        insertOrigin();
    }//GEN-LAST:event_cbOrigemActionPerformed

    private void cbOrigemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbOrigemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            insertOrigin();

        }
    }//GEN-LAST:event_cbOrigemKeyPressed

    private void txtDataInstalacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataInstalacaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            // ld = format.dateFormaterBank(txtDataInstalacao.getText());
            cbPeriodo.requestFocus();
        }
    }//GEN-LAST:event_txtDataInstalacaoKeyPressed

    private void cbPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPacoteActionPerformed
        fillingPackage();   //JOptionPane.showMessageDialog(null,"Pacote escolhido: "+packgeSelected,"Aviso",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cbPacoteActionPerformed

    private void cbPacoteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPacoteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            fillingPackage();

        }
    }//GEN-LAST:event_cbPacoteKeyPressed

    private void txaObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaObsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            observation = txaObs.getText();
            btnSale.requestFocus();

        }
    }//GEN-LAST:event_txaObsKeyPressed

    private void btnSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleActionPerformed
        fillData();

        update();

    }//GEN-LAST:event_btnSaleActionPerformed

    private void btnSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaleKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            update();

        }
    }//GEN-LAST:event_btnSaleKeyPressed

    private void btnCancellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancellActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar a alteração da venda?", "ALERTA!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnCancellActionPerformed

    private void cbSituatiomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSituatiomActionPerformed
        situation = (Situation) cbSituatiom.getSelectedItem();
    }//GEN-LAST:event_cbSituatiomActionPerformed

    private void cbSituatiomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSituatiomKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            situation = (Situation) cbSituatiom.getSelectedItem();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            update();

        }
    }//GEN-LAST:event_cbSituatiomKeyPressed

    private void txtValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValueKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValueKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlterData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancell;
    private javax.swing.JButton btnSale;
    public static javax.swing.JComboBox<String> cbOrigem;
    public static javax.swing.JComboBox<String> cbPacote;
    public static javax.swing.JComboBox<String> cbPeriodo;
    public static javax.swing.JComboBox<String> cbSituatiom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea txaObs;
    public static javax.swing.JTextField txtCPF;
    public static javax.swing.JTextField txtCliente;
    public static javax.swing.JTextField txtContato;
    public static javax.swing.JTextField txtDataInstalacao;
    public static javax.swing.JTextField txtTrVendida;
    public static javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
