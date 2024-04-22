package View;

import Controller.Formatting;
import Controller.SalesController;
import Dao.SalesDAO;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Model.Enums.Situation;
import static Model.Enums.Situation.CANCELED;
import static Model.Enums.Situation.INSTALLED;
import static Model.Enums.Situation.PROVISIONING;
import Model.Sales;
import Model.Vendedor;
import Services.SaleService;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class VendasAtual extends javax.swing.JFrame {

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
    private LocalDateTime ldTSaleMarked;
    private LocalDateTime ldTSaleMade;
    private float valuePackage;
    private TypeBank type;
    int idSelection = 0;
    int id = 0;

    private void fillData() {

        String fielsWithout = fielWithoutFielling();
        if (fielsWithout == "" || fielsWithout == null || fielsWithout.length() == 0) {
            if (cliente == null) {
                cliente = txtCliente.getText();
            }

            if (cpf == null) {
                cpf = txtCPF.getText();
            }

            if (contacts == null) {
                contacts = txtContato.getText();
            }
            if (observation == null) {
                observation = txaObs.getText();
            }

            if (ldTSaleMarked.equals("") || ldTSaleMarked == null) {
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
            JOptionPane.showMessageDialog(null, "Favor preencher os seguintes campos\n " + fielsWithout, "Aviso", JOptionPane.ERROR_MESSAGE);

        }

    }

    private enum TypeBank {
        UPDATE,
        INSERT;
    }

    public VendasAtual() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        jPanel1.setSize(getMaximumSize());
        sc.returnData();
        txtCPF.requestFocus();
        cbPacote.setModel(new DefaultComboBoxModel(Packages.values()));
        cbPeriodo.setModel(new DefaultComboBoxModel(Period.values()));
        cbOrigem.setModel(new DefaultComboBoxModel(Origin.values()));
        cbSituatiom.setModel(new DefaultComboBoxModel(Situation.values()));
        txtDataInstalacao.setText(format.dateFormaterField(LocalDate.now().plusDays(1)));
        txtTrVendida.setText("TR799118 Higo");
        txaObs.setText("Sem Ligação");
        tableFormatColors();
        tblVendasRes.setShowGrid(false);
        sc.fillingsPacksges();
        actionMouseRight();
        triggerToChage(false);
        type = TypeBank.INSERT;
        lblQtSellsTable.setText((tblVendasRes.getRowCount() > 9 ? tblVendasRes.getRowCount() : "0" + tblVendasRes.getRowCount()) + " Registros de Vendas");
        //returnPositionTable();
        fillingsValuesPacksges();
    }

    Formatting format = new Formatting();
    SalesController sc = new SalesController();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lblAprovisionamentoTot = new javax.swing.JLabel();
        lblAprovisionamentoTot1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblCanceladaTot = new javax.swing.JLabel();
        lblCanceladaTot1 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lblInstaladaTot = new javax.swing.JLabel();
        lblInstaladaTot1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtCliente = new javax.swing.JTextField();
        txtCPF = new javax.swing.JTextField();
        cbPeriodo = new javax.swing.JComboBox<>();
        txtContato = new javax.swing.JTextField();
        txtTrVendida = new javax.swing.JTextField();
        cbOrigem = new javax.swing.JComboBox<>();
        txtDataInstalacao = new javax.swing.JTextField();
        cbPacote = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        btnSale = new javax.swing.JButton();
        btnCancell = new javax.swing.JButton();
        cbSituatiom = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lblCancelada700 = new javax.swing.JLabel();
        lblInstalada700 = new javax.swing.JLabel();
        lblAprovisionamento700 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        lblAprovisionamento400 = new javax.swing.JLabel();
        lblInstalada400 = new javax.swing.JLabel();
        lblCancelada400 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        lblCancelada600 = new javax.swing.JLabel();
        lblInstalada600 = new javax.swing.JLabel();
        lblAprovisionamento600 = new javax.swing.JLabel();
        lblEstimativa = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVendasRes = new javax.swing.JTable();
        lblValuePlan = new javax.swing.JLabel();
        lblQtSellsTable = new javax.swing.JLabel();
        btnReadSells = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comissão", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Aprovisionamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 153, 0))); // NOI18N

        lblAprovisionamentoTot.setText("Aprovisionamento");

        lblAprovisionamentoTot1.setText("Aprovisionamento");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAprovisionamentoTot)
                    .addComponent(lblAprovisionamentoTot1))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblAprovisionamentoTot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAprovisionamentoTot1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 240, 100));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Canceladas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        lblCanceladaTot.setText("Cancelada");

        lblCanceladaTot1.setText("Cancelada");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCanceladaTot1)
                    .addComponent(lblCanceladaTot))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCanceladaTot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(lblCanceladaTot1)
                .addContainerGap())
        );

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 250, 90));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Instaladas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 0))); // NOI18N

        lblInstaladaTot.setText("Instaladas");

        lblInstaladaTot1.setText("Instaladas");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInstaladaTot1)
                    .addComponent(lblInstaladaTot))
                .addContainerGap(256, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(lblInstaladaTot)
                .addGap(18, 18, 18)
                .addComponent(lblInstaladaTot1)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 340, 90));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 560, 250));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(204, 0, 204))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteKeyPressed(evt);
            }
        });
        jPanel6.add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 190, 40));

        txtCPF.setBackground(new java.awt.Color(255, 255, 255));
        txtCPF.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "CPF/CNPJ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFKeyPressed(evt);
            }
        });
        jPanel6.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, 40));

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
        jPanel6.add(cbPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 110, 40));

        txtContato.setBackground(new java.awt.Color(255, 255, 255));
        txtContato.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Contato", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtContato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContatoKeyPressed(evt);
            }
        });
        jPanel6.add(txtContato, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 130, 40));

        txtTrVendida.setBackground(new java.awt.Color(255, 255, 255));
        txtTrVendida.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "TR vendida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtTrVendida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTrVendidaKeyPressed(evt);
            }
        });
        jPanel6.add(txtTrVendida, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 40, 120, 40));

        cbOrigem.setBackground(new java.awt.Color(255, 255, 255));
        cbOrigem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Origem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
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
        jPanel6.add(cbOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 40, 110, 40));

        txtDataInstalacao.setBackground(new java.awt.Color(255, 255, 255));
        txtDataInstalacao.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data de Instalação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtDataInstalacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataInstalacaoKeyPressed(evt);
            }
        });
        jPanel6.add(txtDataInstalacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 130, 40));

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
        jPanel6.add(cbPacote, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 110, 40));

        txaObs.setBackground(new java.awt.Color(255, 255, 255));
        txaObs.setColumns(20);
        txaObs.setForeground(new java.awt.Color(255, 0, 0));
        txaObs.setRows(5);
        txaObs.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txaObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaObsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaObs);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 980, 60));

        btnSale.setText("Cadastrar venda");
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
        jPanel6.add(btnSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 90, 120, 40));

        btnCancell.setBackground(new java.awt.Color(255, 51, 51));
        btnCancell.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnCancell.setForeground(new java.awt.Color(255, 255, 255));
        btnCancell.setText("Cancelar");
        btnCancell.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 0, 0), null, null));
        btnCancell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancellActionPerformed(evt);
            }
        });
        jPanel6.add(btnCancell, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 40, 120, 40));

        cbSituatiom.setBackground(new java.awt.Color(255, 255, 255));
        cbSituatiom.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Situação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
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
        jPanel6.add(cbSituatiom, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 90, 110, 40));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 1270, 160));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Internet Vendidas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "1GB/700MB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N

        lblCancelada700.setText("Cancelada");

        lblInstalada700.setText("Instaladas");

        lblAprovisionamento700.setText("Aprovisionamento");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAprovisionamento700)
                    .addComponent(lblInstalada700)
                    .addComponent(lblCancelada700))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblAprovisionamento700)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInstalada700)
                .addGap(18, 18, 18)
                .addComponent(lblCancelada700)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 210, 140));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "400MB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N

        lblAprovisionamento400.setText("Aprovisionamento");

        lblInstalada400.setText("Instaladas");

        lblCancelada400.setText("Cancelada");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAprovisionamento400)
                    .addComponent(lblInstalada400)
                    .addComponent(lblCancelada400))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblAprovisionamento400)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInstalada400)
                .addGap(18, 18, 18)
                .addComponent(lblCancelada400)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 140));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "600MB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N

        lblCancelada600.setText("Cancelada");

        lblInstalada600.setText("Instaladas");

        lblAprovisionamento600.setText("Aprovisionamento");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAprovisionamento600)
                    .addComponent(lblInstalada600)
                    .addComponent(lblCancelada600))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblAprovisionamento600)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInstalada600)
                .addGap(18, 18, 18)
                .addComponent(lblCancelada600)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 230, 140));

        lblEstimativa.setText("Estimativa de ");
        jPanel10.add(lblEstimativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 700, 250));

        tblVendasRes.setBackground(new java.awt.Color(255, 255, 255));
        tblVendasRes.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblVendasRes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data Criação", "CPF/CNPJ", "Cliente", "Contato", "Pacote", "Valor", "Data Instalação", "Periodo", "Origem", "Situação", "Observação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVendasRes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVendasResMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVendasResMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblVendasRes);
        if (tblVendasRes.getColumnModel().getColumnCount() > 0) {
            tblVendasRes.getColumnModel().getColumn(0).setMinWidth(0);
            tblVendasRes.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblVendasRes.getColumnModel().getColumn(0).setMaxWidth(20);
            tblVendasRes.getColumnModel().getColumn(2).setResizable(false);
            tblVendasRes.getColumnModel().getColumn(5).setResizable(false);
            tblVendasRes.getColumnModel().getColumn(6).setMinWidth(30);
            tblVendasRes.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblVendasRes.getColumnModel().getColumn(7).setResizable(false);
            tblVendasRes.getColumnModel().getColumn(8).setResizable(false);
            tblVendasRes.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, 1280, 170));

        lblValuePlan.setText("jLabel1");
        jPanel1.add(lblValuePlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 130, -1));

        lblQtSellsTable.setText("jLabel1");
        jPanel1.add(lblQtSellsTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 230, -1));

        btnReadSells.setText("Buscar vendas Planilha");
        btnReadSells.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadSellsActionPerformed(evt);
            }
        });
        jPanel1.add(btnReadSells, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 470, 160, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void triggerToChage(boolean active) {
        if (active) {
            txaObs.setSize((txaObs.getSize().height - cbSituatiom.getSize().width - 10), txaObs.getSize().height);
            cbSituatiom.setVisible(active);
            jScrollPane1.setSize((txaObs.getSize().height - cbSituatiom.getSize().width - 10), txaObs.getSize().height);
            btnCancell.setVisible(active);
        } else {
            txaObs.setSize((txaObs.getSize().height + cbSituatiom.getSize().width + 10), txaObs.getSize().height);
            jScrollPane1.setSize((txaObs.getSize().height + cbSituatiom.getSize().width + 10), txaObs.getSize().height);
            cbSituatiom.setVisible(active);
            btnCancell.setVisible(active);
        }
    }

    private void isToUpdateOrInsert(TypeBank tb) {
        if (tb == TypeBank.UPDATE) {
            triggerToChage(true);
            txtCPF.requestFocus();
            btnSale.setText("Atualizar Venda");

        } else if (tb == TypeBank.INSERT) {
            triggerToChage(false);
            txtCPF.requestFocus();
            btnSale.setText("Cadastrar Venda");

        } else {
            JOptionPane.showMessageDialog(null, "Escolha uma opção valida", "Erro", JOptionPane.ERROR_MESSAGE);

        }
        {

        }
    }

    private void Saling() {
        ldTSaleMade = LocalDateTime.now();
        SalesController sc = new SalesController();
        if (fielWithoutFielling().length() > 0) {
            JOptionPane.showMessageDialog(null, "Favor preencher os seguintes campos\n " + fielWithoutFielling(), "Aviso", JOptionPane.ERROR_MESSAGE);

        } else {
            sc.saveSales(new Sales(
                    new Vendedor(1),
                    ldTSaleMade, cpf, cliente,
                    contacts, packgeSelected.toString(),
                    valuePackage, ldTSaleMarked, periodInstalation,
                    originSell, Situation.PROVISIONING, observation));

            DefaultTableModel dtm = (DefaultTableModel) tblVendasRes.getModel();
            Object[] dados = {
                format.dateTimeFormaterField(ldTSaleMade),
                cpf,
                cliente,
                contacts,
                packgeSelected.toString(),
                ldTSaleMarked.toLocalDate(),
                periodInstalation.toString(),
                originSell.toString(),
                Situation.PROVISIONING.toString(),
                observation
            };
            dtm.addRow(dados);
            cleanFields();
            txtCPF.requestFocus();

        }
    }

    private void update() {
        SalesController sc = new SalesController();

        sc.updateSales(new Sales(id, new Vendedor(1),
                ldTSaleMade, cpf, cliente,
                contacts, packgeSelected.toString(),
                valuePackage, ldTSaleMarked, periodInstalation,
                originSell, situation, observation));

//            
        lblQtSellsTable.setText((tblVendasRes.getRowCount() > 9 ? tblVendasRes.getRowCount() : "0" + tblVendasRes.getRowCount()) + " Registros de Vendas");

        cleanFields();
        txtCPF.requestFocus();

    }

    public void fillingsValuesPacksges() {
        Map<String, Integer> position = returnPositionTable();

        double packProvisig400 = 0;
        double packInstalled400 = 0;
        double packcancell400 = 0;
        double packProvisig600 = 0;
        double packInstalled600 = 0;
        double packcancell600 = 0;
        double packProvisig700 = 0;
        double packInstalled700 = 0;
        double packcancell700 = 0;

        for (int i = 0; i < tblVendasRes.getRowCount(); i++) {
            Packages pack = Packages.fromString(tblVendasRes.getValueAt(i, position.get("package")) + "");
            Situation situation = Situation.fromString(tblVendasRes.getValueAt(i, position.get("situation")) + "");

            double valuePerPack = Double.parseDouble(format.formatMoneyNumber(tblVendasRes.getValueAt(i, position.get("value")) + "", 'N'));

            if (pack == Packages.I_400MB) {

                switch (situation) {
                    case CANCELED:
                        packcancell400 += valuePerPack;
                    case INSTALLED:
                        packInstalled400 += valuePerPack;

                        break;
                    case PROVISIONING:
                        packProvisig400 += valuePerPack;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            if (pack == Packages.I_500MB || pack == Packages.I_600MB) {

                switch (situation) {
                    case CANCELED:
                        packcancell600 += valuePerPack;
                        break;
                    case INSTALLED:
                        packInstalled600 += valuePerPack;

                        break;
                    case PROVISIONING:
                        packProvisig600 += valuePerPack;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            if (pack == Packages.I_700MB || pack == Packages.I_1GB) {
                switch (situation) {
                    case CANCELED:
                        packcancell700 += valuePerPack;
                        break;
                    case INSTALLED:
                        packInstalled700 += valuePerPack;

                        break;
                    case PROVISIONING:
                        packProvisig700 += valuePerPack;

                        break;
                    default:
                        throw new AssertionError();
                }
            }

        }
        lblAprovisionamentoTot1.setText(format.formatMoneyNumber((packProvisig400 + packProvisig600 + packProvisig700) + "", 'M'));
        lblInstaladaTot1.setText(format.formatMoneyNumber((packInstalled400 + packInstalled600 + packInstalled700) + "", 'M'));
        lblCanceladaTot1.setText(format.formatMoneyNumber((packcancell400 + packcancell600 + packcancell700) + "", 'M'));

    }

    private void tableFormatColors() {
        tblVendasRes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.setHorizontalAlignment(JLabel.CENTER); // Adicione esta linha para centralizar o texto
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Suponha que a coluna que contém o valor "instalada" seja a penúltima coluna
                int columnIndex = table.getColumnCount() - 2; // Ajuste este valor conforme necessário
                String cellValue = (table.getValueAt(row, columnIndex).toString());

                if ("Cancelada".equals(cellValue)) {
                    c.setBackground(Color.decode("#FFC7CE"));
                    c.setForeground(Color.decode("#9C0006"));
                } else if ("Instalada".equals(cellValue)) {
                    c.setBackground(Color.decode("#C6EFCE"));
                    c.setForeground(Color.decode("#006100"));
                } else {
                    c.setBackground(Color.decode("#FFE699"));
                    c.setForeground(Color.decode("#9C5700"));
                }

                return c;
            }
        });

    }

    //caso coloque as colunas desordenadas, esse metodo ira buscar os valores
    public int findColumnByName(String name) {
        for (int i = 0; i < tblVendasRes.getColumnCount(); i++) {
            if (tblVendasRes.getColumnName(i).equals(name)) {
                return i;
            }
        }
        return -1;  // Retorna -1 se a coluna não foi encontrada
    }

    private Map<String, Integer> returnPositionTable() {

        Map<String, Integer> posicoes = new HashMap<>();
        posicoes.put("cpf", findColumnByName("CPF/CNPJ"));
        posicoes.put("custormes", findColumnByName("Cliente"));
        posicoes.put("contact", findColumnByName("Contato"));
        posicoes.put("package", findColumnByName("Pacote"));
        posicoes.put("value", findColumnByName("Valor"));
        posicoes.put("dateInstalation", findColumnByName("Data Instalação"));
        posicoes.put("dateMade", findColumnByName("Data Criação"));
        posicoes.put("period", findColumnByName("Periodo"));
        posicoes.put("origin", findColumnByName("Origem"));
        posicoes.put("situation", findColumnByName("Situação"));
        posicoes.put("obs", findColumnByName("Observação"));
        return posicoes;
    }

    private void cleanFields() {
        txtCPF.setText("");
        txtCliente.setText("");
        txtContato.setText("");
        txaObs.setText("");
        lblValuePlan.setText("");
        cbOrigem.setSelectedIndex(0);
        cbPacote.setSelectedIndex(0);
        cbPeriodo.setSelectedIndex(0);
        cbSituatiom.setSelectedIndex(0);
        cpf = null;
        contacts = null;
        ldTSaleMarked = null;
        ldTSaleMade = null;
        packgeSelected = null;
        originSell = null;
        periodInstalation = null;
        observation = null;
        type = TypeBank.INSERT;
        txtCPF.requestFocus();
    }

    private String fielWithoutFielling() {
        String fwf = "";
        if (txtCPF.getText().equals("")) {
            fwf += "\"CPF\" ";
        }
        if (txtCliente.getText().equals("")) {
            fwf += "\"Cliente\" ";
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
        if (type == TypeBank.UPDATE) {
            if (cbSituatiom.getSelectedIndex() == 0) {
                fwf += "\"Situação\" ";
            }
        }

        return fwf;

    }
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

    private void txtClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            cliente = txtCliente.getText();
            txtContato.requestFocus();
        }
    }//GEN-LAST:event_txtClienteKeyPressed

    private void txtContatoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContatoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (txtCPF.getText().length() < 11) {
                JOptionPane.showMessageDialog(null, "O cpf/cnpj que digitou esta incorreto\n TAmanhaho: " + txtCPF.getText().length() + " caractes", "Aviso", JOptionPane.ERROR_MESSAGE);
            } else {
//              String[] contacts = txtContato.getText().replace(" ", "").split("/");
//                   JOptionPane.showMessageDialog(null, contacts  , "Aviso", JOptionPane.ERROR_MESSAGE);
//                for (String contact : contacts) {
//                  contatos.add(contact);
//              }
                contacts = txtCPF.getText();

                cbPacote.requestFocus();
            }
        }
    }//GEN-LAST:event_txtContatoKeyPressed

    private void cbPacoteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPacoteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            fillingPackage();

        }
    }//GEN-LAST:event_cbPacoteKeyPressed

    private void fillingPackage() {
        if (cbPacote.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um pacote", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            cbPacote.requestFocus();
        } else {
            packgeSelected = (Packages) cbPacote.getSelectedItem();

        }
        Packages[] pack = {Packages.ALL};
        valuePackage = SaleService.ValuePerSale(SalesDAO.returnQtdPackgeInstalled(pack, situation.INSTALLED), packgeSelected);
        lblValuePlan.setText(format.formatMoneyNumber(valuePackage + "", 'M'));
        txtDataInstalacao.requestFocus();
    }

    private void txtDataInstalacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataInstalacaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            // ld = format.dateFormaterBank(txtDataInstalacao.getText());
            cbPeriodo.requestFocus();
        }
    }//GEN-LAST:event_txtDataInstalacaoKeyPressed

    private void cbPeriodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPeriodoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            InsertPeriod();

        }
    }//GEN-LAST:event_cbPeriodoKeyPressed

    private void InsertPeriod() throws HeadlessException {

        periodInstalation = (Period) cbPeriodo.getSelectedItem();
        if (periodInstalation == Period.MORNING) {
            ldTSaleMarked = format.dateTimeFormaterBank(txtDataInstalacao.getText() + " 08:00");
            JOptionPane.showMessageDialog(null, "Data:  " + ldTSaleMarked, "Aviso", JOptionPane.INFORMATION_MESSAGE);

        }
        if (periodInstalation == Period.AFTERNOON) {
            ldTSaleMarked = format.dateTimeFormaterBank(txtDataInstalacao.getText() + " 13:00");
            JOptionPane.showMessageDialog(null, "Data:  " + ldTSaleMarked, "Aviso", JOptionPane.INFORMATION_MESSAGE);

        }

        txtTrVendida.requestFocus();

    }

    private void txtTrVendidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrVendidaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            cbOrigem.requestFocus();
        }
    }//GEN-LAST:event_txtTrVendidaKeyPressed

    private void cbOrigemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbOrigemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            insertOrigin();

        }
    }//GEN-LAST:event_cbOrigemKeyPressed

    private void insertOrigin() throws HeadlessException {

        originSell = (Origin) cbOrigem.getSelectedItem();
        txaObs.requestFocus();

    }

    private void cbPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPacoteActionPerformed
        fillingPackage();   //JOptionPane.showMessageDialog(null,"Pacote escolhido: "+packgeSelected,"Aviso",JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_cbPacoteActionPerformed

    private void btnSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleActionPerformed
        fillData();

        if (type == TypeBank.INSERT) {
            Saling();
        } else {
            update();
            tableFormatColors();
        }
    }//GEN-LAST:event_btnSaleActionPerformed

    private void txaObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaObsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            observation = txaObs.getText();
            if (type == TypeBank.INSERT) {
                btnSale.requestFocus();
            } else {
                cbSituatiom.requestFocus();
            }
        }
    }//GEN-LAST:event_txaObsKeyPressed

    private void btnSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaleKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB)
            if (type == TypeBank.INSERT) {
                Saling();
            } else {
                update();
            }
    }//GEN-LAST:event_btnSaleKeyPressed

    private void cbPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPeriodoActionPerformed
        InsertPeriod();
    }//GEN-LAST:event_cbPeriodoActionPerformed

    private void cbOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrigemActionPerformed
        insertOrigin();
    }//GEN-LAST:event_cbOrigemActionPerformed

    public void actionMouseRight() {
        tblVendasRes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Obter a linha e a coluna na qual o usuário clicou
                    int row = tblVendasRes.rowAtPoint(e.getPoint());
                    int column = tblVendasRes.columnAtPoint(e.getPoint());

                    // Selecionar a célula como se o botão esquerdo tivesse sido pressionado
                    tblVendasRes.changeSelection(row, column, false, false);
                    Map<String, Integer> ma = returnPositionTable();
                    // Exibir uma caixa de diálogo
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja alterar a venda: "
                            + "\n Feita em: " + tblVendasRes.getValueAt(row, ma.get("dateMade")) + "\n "
                            + "Instalação marcada para " + tblVendasRes.getValueAt(row, 5) + " De " + tblVendasRes.getValueAt(row, 6), "Venda " + (row + 1) + " CPF: " + tblVendasRes.getValueAt(row, 1), JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {

                        Map<String, Integer> values = returnPositionTable();

                        type = TypeBank.UPDATE;
                        isToUpdateOrInsert(type);
                        txtCPF.setText(tblVendasRes.getValueAt(row, values.get("cpf")) + "");
                        txtCliente.setText(tblVendasRes.getValueAt(row, values.get("custormes")) + "");
                        txtContato.setText(tblVendasRes.getValueAt(row, values.get("contact")) + "");
                        cbPacote.setSelectedItem(Packages.fromString(tblVendasRes.getValueAt(row, values.get("package")) + ""));
                        txtDataInstalacao.setText(tblVendasRes.getValueAt(row, values.get("dateInstalation")) + "");
                        cbPeriodo.setSelectedItem(Period.fromString(tblVendasRes.getValueAt(row, values.get("period")) + ""));
                        // txtTrVendida.setText(tblVendasRes.getValueAt(row, 7)+"");
                        cbOrigem.setSelectedItem((Origin.fromString(tblVendasRes.getValueAt(row, values.get("origin")) + "")));
                        cbSituatiom.setSelectedItem((tblVendasRes.getValueAt(row, values.get("situation"))));
                        txaObs.setText(tblVendasRes.getValueAt(row, values.get("obs")) + "");
                        id = Integer.parseInt(tblVendasRes.getValueAt(row, 0).toString());
                        ldTSaleMade = format.dateTimeFormaterBank(tblVendasRes.getValueAt(row, values.get("dateMade")) + "");

                    }
                }
            }
        });

    }


    private void tblVendasResMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasResMouseClicked

    }//GEN-LAST:event_tblVendasResMouseClicked

    private void cbSituatiomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSituatiomActionPerformed
        situation = (Situation) cbSituatiom.getSelectedItem();

    }//GEN-LAST:event_cbSituatiomActionPerformed

    private void cbSituatiomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSituatiomKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            situation = (Situation) cbSituatiom.getSelectedItem();
        }

    }//GEN-LAST:event_cbSituatiomKeyPressed

    private void btnCancellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancellActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar a alteração da venda?", "ALERTA!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            isToUpdateOrInsert(TypeBank.INSERT);
            cleanFields();
        }

    }//GEN-LAST:event_btnCancellActionPerformed

    private void tblVendasResMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasResMousePressed

    }//GEN-LAST:event_tblVendasResMousePressed

    private void btnReadSellsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadSellsActionPerformed
        SalesController.searchSellsPlanilha();
    }//GEN-LAST:event_btnReadSellsActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendasAtual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancell;
    private javax.swing.JButton btnReadSells;
    private javax.swing.JButton btnSale;
    private javax.swing.JComboBox<String> cbOrigem;
    private javax.swing.JComboBox<String> cbPacote;
    private javax.swing.JComboBox<String> cbPeriodo;
    private javax.swing.JComboBox<String> cbSituatiom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lblAprovisionamento400;
    public static javax.swing.JLabel lblAprovisionamento600;
    public static javax.swing.JLabel lblAprovisionamento700;
    public static javax.swing.JLabel lblAprovisionamentoTot;
    public static javax.swing.JLabel lblAprovisionamentoTot1;
    public static javax.swing.JLabel lblCancelada400;
    public static javax.swing.JLabel lblCancelada600;
    public static javax.swing.JLabel lblCancelada700;
    public static javax.swing.JLabel lblCanceladaTot;
    public static javax.swing.JLabel lblCanceladaTot1;
    private javax.swing.JLabel lblEstimativa;
    public static javax.swing.JLabel lblInstalada400;
    public static javax.swing.JLabel lblInstalada600;
    public static javax.swing.JLabel lblInstalada700;
    public static javax.swing.JLabel lblInstaladaTot;
    public static javax.swing.JLabel lblInstaladaTot1;
    private javax.swing.JLabel lblQtSellsTable;
    private javax.swing.JLabel lblValuePlan;
    public static javax.swing.JTable tblVendasRes;
    private javax.swing.JTextArea txaObs;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtContato;
    private javax.swing.JTextField txtDataInstalacao;
    private javax.swing.JTextField txtTrVendida;
    // End of variables declaration//GEN-END:variables

}
