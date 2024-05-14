package View;

import Controller.Formatting;
import Controller.SalesController;
import Services.SaleService;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

public class AllSales extends javax.swing.JFrame {

    SaleService ss = new SaleService();
    SalesController sc = new SalesController();
    Formatting format = new Formatting();

    public AllSales() {
        initComponents();

        setExtendedState(MAXIMIZED_BOTH);
        jPanel1.setSize(getMaximumSize());
        sc.returnData('a', (DefaultTableModel) tblRelatorioVendas.getModel(), LocalDate.now(), LocalDate.now());
        ss.tableFormatColors(tblRelatorioVendas);
        lblQtSellsTable.setText((tblRelatorioVendas.getRowCount() > 9 ? tblRelatorioVendas.getRowCount() : "0" + tblRelatorioVendas.getRowCount()) + " Registros de Vendas");
        // fielEditable(txtCPF,"Data"); 
        txtField1.setVisible(false);
        txtField2.setVisible(false);
 cbChoose.setModel(new DefaultComboBoxModel(typeForSearch.values()));
        
    }

    enum typeForSearch {
        select("Selecione"),
        thisMonth("Este Mês"),
        lastMonth("Mês Passado"),
        period("Perido"),
        cpf("CPF/CNPJ"),
        name("Cliente");

        String type;

        private typeForSearch(String type) {
            this.type = type;
        }
         @Override
    public String toString() {
        return type;
    }

        public typeForSearch fromString(String value) {
            for (typeForSearch types : typeForSearch.values()) {
                if (types.toString().equals(value)) {
                    return types;
                }
            }
            return null;
        }
    }

    private void fielEditable(JTextField txt, String titule) {
        txt.setBorder(BorderFactory.createTitledBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), titule, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRelatorioVendas = new javax.swing.JTable();
        lblQtSellsTable = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cbChoose = new javax.swing.JComboBox<>();
        txtField2 = new javax.swing.JTextField();
        txtField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblRelatorioVendas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblRelatorioVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tr Vendida", "Data Criação", "CPF/CNPJ", "Cliente", "Contato", "Pacote", "Valor", "Data Instalação", "Periodo", "Origem", "Situação", "Observação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRelatorioVendas.setPreferredSize(new java.awt.Dimension(590, 1170));
        tblRelatorioVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRelatorioVendasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblRelatorioVendasMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblRelatorioVendas);
        if (tblRelatorioVendas.getColumnModel().getColumnCount() > 0) {
            tblRelatorioVendas.getColumnModel().getColumn(0).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblRelatorioVendas.getColumnModel().getColumn(0).setMaxWidth(30);
            tblRelatorioVendas.getColumnModel().getColumn(1).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(1).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(2).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblRelatorioVendas.getColumnModel().getColumn(2).setMaxWidth(150);
            tblRelatorioVendas.getColumnModel().getColumn(3).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(3).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(4).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(4).setPreferredWidth(200);
            tblRelatorioVendas.getColumnModel().getColumn(4).setMaxWidth(200);
            tblRelatorioVendas.getColumnModel().getColumn(5).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblRelatorioVendas.getColumnModel().getColumn(5).setMaxWidth(100);
            tblRelatorioVendas.getColumnModel().getColumn(6).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(6).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(6).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(7).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(7).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(7).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(8).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(8).setPreferredWidth(150);
            tblRelatorioVendas.getColumnModel().getColumn(8).setMaxWidth(150);
            tblRelatorioVendas.getColumnModel().getColumn(9).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(9).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(9).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(10).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(10).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(10).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(11).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(11).setPreferredWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(11).setMaxWidth(80);
            tblRelatorioVendas.getColumnModel().getColumn(12).setMinWidth(0);
            tblRelatorioVendas.getColumnModel().getColumn(12).setPreferredWidth(200);
            tblRelatorioVendas.getColumnModel().getColumn(12).setMaxWidth(200);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 1250, 340));

        lblQtSellsTable.setText("jLabel1");
        jPanel1.add(lblQtSellsTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 230, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51)), "Mes passado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("jLabel1");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel2.setText("jLabel1");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 260, 150));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51)), "Mes passado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 260, 150));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51)), "Mes passado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 240, 150));

        cbChoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Este Mês", "Mês Passado", "Escolher Periodo", "CPF/CNPJ", "Nome" }));
        cbChoose.setBorder(javax.swing.BorderFactory.createTitledBorder("Procurar por"));
        cbChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChooseActionPerformed(evt);
            }
        });
        cbChoose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbChooseKeyPressed(evt);
            }
        });
        jPanel1.add(cbChoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 230, 50));

        txtField2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "CPF/CNPJ"));
        txtField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtField2KeyPressed(evt);
            }
        });
        jPanel1.add(txtField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 200, 40));

        txtField1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data Inicio"));
        txtField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtField1KeyPressed(evt);
            }
        });
        jPanel1.add(txtField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 190, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1390, 650));

        jMenu1.setText("Vendas Mês");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblRelatorioVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelatorioVendasMouseClicked

    }//GEN-LAST:event_tblRelatorioVendasMouseClicked

    private void tblRelatorioVendasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRelatorioVendasMousePressed

    }//GEN-LAST:event_tblRelatorioVendasMousePressed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        new VendasAtual().show();
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        new VendasAtual().show();
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void txtField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtField2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            
            sc.returnData('c', (DefaultTableModel) tblRelatorioVendas.getModel(), format.dateFormaterBank(txtField1.getText()), format.dateFormaterBank(txtField2.getText()));
                 lblQtSellsTable.setText((tblRelatorioVendas.getRowCount() > 9 ? tblRelatorioVendas.getRowCount() : "0" + tblRelatorioVendas.getRowCount()) + " Registros de Vendas");

        }
    }//GEN-LAST:event_txtField2KeyPressed

    private void cleanSearch() {
        txtField1.setText("");
        txtField2.setText("");
        txtField1.requestFocus();
    }

    private void txtField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtField1KeyPressed
      //  if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
        if (cbChoose.getSelectedItem() == typeForSearch.period) {//periodo
            if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
              txtField2.requestFocus();
                
            }
                //sc.returnData('m', (DefaultTableModel) tblRelatorioVendas.getModel(), format.dateFormaterBank(), LocalDate.MIN);
            } else if (cbChoose.getSelectedItem() == typeForSearch.cpf) {//CPF
                sc.returnDataByCpfOrName(txtField1.getText(), 'c', (DefaultTableModel) tblRelatorioVendas.getModel());
            
            } else if (cbChoose.getSelectedItem() == typeForSearch.name) {//nome
                   sc.returnDataByCpfOrName(txtField1.getText(), 'n', (DefaultTableModel) tblRelatorioVendas.getModel());
            
            } else {
                JOptionPane.showMessageDialog(null, "Favor escolha uma opção\n ", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
                 lblQtSellsTable.setText((tblRelatorioVendas.getRowCount() > 9 ? tblRelatorioVendas.getRowCount() : "0" + tblRelatorioVendas.getRowCount()) + " Registros de Vendas");

       // }
       

    }//GEN-LAST:event_txtField1KeyPressed

    private void cbChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChooseActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tblRelatorioVendas.getModel();
                cleanSearch();
         if (cbChoose.getSelectedItem() == typeForSearch.thisMonth) {
 txtField1.setVisible(false);
                txtField2.setVisible(false);
                sc.returnData('m', dtm, LocalDate.MIN, LocalDate.MIN);

            } else if (cbChoose.getSelectedItem() == typeForSearch.lastMonth) { //Mes passado
                txtField1.setVisible(false);
                txtField2.setVisible(false);
                sc.returnData('l',  dtm, LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(1));
            } else if (cbChoose.getSelectedItem() == typeForSearch.period) {//periodo
              fielEditable(txtField1, "Data Inicio");
                fielEditable(txtField2, "Data Fim");
                txtField1.setVisible(true);
                txtField2.setVisible(true);
                txtField1.setText(format.dateFormaterField(LocalDate.now()));
                txtField2.setText(format.dateFormaterField(LocalDate.now()));
                txtField1.requestFocus();
                //sc.returnData('m', (DefaultTableModel) tblRelatorioVendas.getModel(), format.dateFormaterBank(),LocalDate.MIN);
            } else if (cbChoose.getSelectedItem() == typeForSearch.cpf) {//CPF
                fielEditable(txtField1, "CPF/CNPJ");
                txtField1.setVisible(true);
                txtField2.setVisible(false);
                txtField1.requestFocus();
            } else if (cbChoose.getSelectedItem() == typeForSearch.name) {//nome
                fielEditable(txtField1, "Nome");
                txtField1.setVisible(true);
                txtField2.setVisible(false);
                txtField1.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Favor escolha uma opção\n ", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
         lblQtSellsTable.setText((tblRelatorioVendas.getRowCount() > 9 ? tblRelatorioVendas.getRowCount() : "0" + tblRelatorioVendas.getRowCount()) + " Registros de Vendas");
    }//GEN-LAST:event_cbChooseActionPerformed

    private void cbChooseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbChooseKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

        }
    }//GEN-LAST:event_cbChooseKeyPressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllSales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbChoose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lblQtSellsTable;
    public static javax.swing.JTable tblRelatorioVendas;
    private javax.swing.JTextField txtField1;
    private javax.swing.JTextField txtField2;
    // End of variables declaration//GEN-END:variables
}
