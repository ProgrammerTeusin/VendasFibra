package View;

import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class VendasAtual2 extends javax.swing.JFrame {


    public VendasAtual2() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        jPanel1.setSize(getMaximumSize());
        txtCPF.requestFocus();
        cbPacote.setModel(new DefaultComboBoxModel(Packages.values()));
        cbPeriodo.setModel(new DefaultComboBoxModel(Period.values()));
        cbOrigem.setModel(new  DefaultComboBoxModel(Origin.values()));
        
        
    }
    
    public void fillInGoal(){//preenchaMeta
      Map<Packages, Map<Integer, BigDecimal>> goals = new HashMap<>();
      Map<Integer, BigDecimal> innerMap = new HashMap<>();
       
      innerMap.put(Integer.valueOf(txtInstaladasMaior1.getText()), BigDecimal.valueOf(Double.parseDouble(txtComissaoBatido1.getText())));
goals.put(Packages.I_1GB, innerMap);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtInstaladasMaior1 = new javax.swing.JTextField();
        txtInstaladasMaior2 = new javax.swing.JTextField();
        txtInstaladasMaior3 = new javax.swing.JTextField();
        txtInstaladasMaior4 = new javax.swing.JTextField();
        txtComissaoBatido1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtComissaoBatido2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtComissaoBatido3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtComissaoBatido4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lblAprovisionamentoTot = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblCanceladaTot = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lblInstaladaTot = new javax.swing.JLabel();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comissão", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Regras", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtInstaladasMaior1.setBackground(new java.awt.Color(255, 255, 255));
        txtInstaladasMaior1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Instaladas > que:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtInstaladasMaior1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, 40));

        txtInstaladasMaior2.setBackground(new java.awt.Color(255, 255, 255));
        txtInstaladasMaior2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Instaladas > que:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtInstaladasMaior2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 110, 40));

        txtInstaladasMaior3.setBackground(new java.awt.Color(255, 255, 255));
        txtInstaladasMaior3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Instaladas > que:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtInstaladasMaior3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 110, 40));

        txtInstaladasMaior4.setBackground(new java.awt.Color(255, 255, 255));
        txtInstaladasMaior4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Instaladas > que:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtInstaladasMaior4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 110, 40));

        txtComissaoBatido1.setBackground(new java.awt.Color(255, 255, 255));
        txtComissaoBatido1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Valor por Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtComissaoBatido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 110, 40));

        jLabel5.setText("Comissão");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        txtComissaoBatido2.setBackground(new java.awt.Color(255, 255, 255));
        txtComissaoBatido2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Valor por Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtComissaoBatido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 110, 40));

        jLabel6.setText("Comissão");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        txtComissaoBatido3.setBackground(new java.awt.Color(255, 255, 255));
        txtComissaoBatido3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Valor por Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtComissaoBatido3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 110, 40));

        jLabel7.setText("Comissão");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        txtComissaoBatido4.setBackground(new java.awt.Color(255, 255, 255));
        txtComissaoBatido4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Valor por Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.add(txtComissaoBatido4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 110, 40));

        jLabel8.setText("Comissão");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 520, 120));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Aprovisionamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 153, 0))); // NOI18N

        lblAprovisionamentoTot.setText("Aprovisionamento");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(lblAprovisionamentoTot)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(lblAprovisionamentoTot)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 170, 80));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Total Instaladas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 0))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("8 instaladas");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("R$ 40");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(50, 50, 50))
        );

        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 140, 200));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Canceladas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        lblCanceladaTot.setText("Cancelada");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(55, 55, 55)
                    .addComponent(lblCanceladaTot)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(lblCanceladaTot)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 180, 80));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "Instaladas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 0))); // NOI18N

        lblInstaladaTot.setText("Instaladas");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 146, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(47, 47, 47)
                    .addComponent(lblInstaladaTot)
                    .addContainerGap(47, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(lblInstaladaTot)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 160, 80));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 740, 250));

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
        jScrollPane1.setViewportView(txaObs);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1100, 60));

        jButton1.setText("Cadastrar venda");
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 90, 120, 40));

        jButton2.setText("Limpar Campos");
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 40, 120, 40));

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
                .addContainerGap(22, Short.MAX_VALUE))
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

        jPanel10.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 140, 140));

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
                .addContainerGap(22, Short.MAX_VALUE))
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

        jPanel10.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, 140));

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
                .addContainerGap(22, Short.MAX_VALUE))
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

        jPanel10.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 140, 140));

        lblEstimativa.setText("Estimativa de ");
        jPanel10.add(lblEstimativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 510, 250));

        tblVendasRes.setBackground(new java.awt.Color(255, 255, 255));
        tblVendasRes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Data Criação", "CPF/CNPJ", "Pacote", "Data Instalação", "Situação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblVendasRes);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 910, 110));

        lblValuePlan.setText("jLabel1");
        jPanel1.add(lblValuePlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCliente.requestFocus();
        }
    }//GEN-LAST:event_txtCPFKeyPressed

    private void txtClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyPressed
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtContato.requestFocus();
        }
    }//GEN-LAST:event_txtClienteKeyPressed

    private void txtContatoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContatoKeyPressed
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbPacote.requestFocus();
        }
    }//GEN-LAST:event_txtContatoKeyPressed

    private void cbPacoteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPacoteKeyPressed
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDataInstalacao.requestFocus();
        }
    }//GEN-LAST:event_cbPacoteKeyPressed

    private void txtDataInstalacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataInstalacaoKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbPeriodo.requestFocus();
        }
    }//GEN-LAST:event_txtDataInstalacaoKeyPressed

    private void cbPeriodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPeriodoKeyPressed
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtTrVendida.requestFocus();
        }
    }//GEN-LAST:event_cbPeriodoKeyPressed

    private void txtTrVendidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrVendidaKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbOrigem.requestFocus();
        }
    }//GEN-LAST:event_txtTrVendidaKeyPressed

    private void cbOrigemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbOrigemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txaObs.requestFocus();
        }
    }//GEN-LAST:event_cbOrigemKeyPressed

    private void cbPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPacoteActionPerformed
       boolean y = cbPacote.getSelectedItem() == Packages.I_400MB;
        String vaç = cbPacote.getSelectedItem() + " = "+ Packages.I_400MB.toString() +" ? "+ y;
        JOptionPane.showMessageDialog(null, vaç  , "Aviso", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_cbPacoteActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendasAtual2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbOrigem;
    private javax.swing.JComboBox<String> cbPacote;
    private javax.swing.JComboBox<String> cbPeriodo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAprovisionamento400;
    private javax.swing.JLabel lblAprovisionamento600;
    private javax.swing.JLabel lblAprovisionamento700;
    private javax.swing.JLabel lblAprovisionamentoTot;
    private javax.swing.JLabel lblCancelada400;
    private javax.swing.JLabel lblCancelada600;
    private javax.swing.JLabel lblCancelada700;
    private javax.swing.JLabel lblCanceladaTot;
    private javax.swing.JLabel lblEstimativa;
    private javax.swing.JLabel lblInstalada400;
    private javax.swing.JLabel lblInstalada600;
    private javax.swing.JLabel lblInstalada700;
    private javax.swing.JLabel lblInstaladaTot;
    private javax.swing.JLabel lblValuePlan;
    private javax.swing.JTable tblVendasRes;
    private javax.swing.JTextArea txaObs;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtComissaoBatido1;
    private javax.swing.JTextField txtComissaoBatido2;
    private javax.swing.JTextField txtComissaoBatido3;
    private javax.swing.JTextField txtComissaoBatido4;
    private javax.swing.JTextField txtContato;
    private javax.swing.JTextField txtDataInstalacao;
    private javax.swing.JTextField txtInstaladasMaior1;
    private javax.swing.JTextField txtInstaladasMaior2;
    private javax.swing.JTextField txtInstaladasMaior3;
    private javax.swing.JTextField txtInstaladasMaior4;
    private javax.swing.JTextField txtTrVendida;
    // End of variables declaration//GEN-END:variables
}
