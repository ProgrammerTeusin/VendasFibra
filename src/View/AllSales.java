package View;

import Controller.AllSalesController;
import Controller.Formatting;
import Controller.SalesController;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Services.SaleService;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

public class AllSales extends javax.swing.JFrame {

    SaleService ss = new SaleService();
    SalesController sc = new SalesController();
    Formatting format = new Formatting();
    AllSalesController asc = new AllSalesController();
    boolean fieldsVisible = true;

    public AllSales() {
        initComponents();
 panelCanceled9.setBackground(java.awt.Color.decode("#FFE699"));
 panelCanceled10.setBackground(java.awt.Color.decode("#FFC7CE"));
 panelCanceled12.setBackground(java.awt.Color.decode("#FFC7CE"));
 panelCanceled15.setBackground(java.awt.Color.decode("#FFC7CE"));
 panelCanceled11.setBackground(java.awt.Color.decode("#C6EFCE"));
 panelCanceled13.setBackground(java.awt.Color.decode("#C6EFCE"));
 panelCanceled14.setBackground(java.awt.Color.decode("#C6EFCE"));
 
 lblProvsingQtd.setForeground(java.awt.Color.decode("#9C5700"));
 lblProvsingvalue.setForeground(java.awt.Color.decode("#9C5700"));
 
 lblCancelada.setForeground(java.awt.Color.decode("#9C0006"));
 lblCancelada1.setForeground(java.awt.Color.decode("#9C0006"));
 lblCanceladaValue1.setForeground(java.awt.Color.decode("#9C0006"));
 lblCanceladaValue2.setForeground(java.awt.Color.decode("#9C0006"));
 lblCancelada2.setForeground(java.awt.Color.decode("#9C0006"));
 lblCanceladaValue.setForeground(java.awt.Color.decode("#9C0006"));
 
 lblInstaladas.setForeground(java.awt.Color.decode("#006100"));
 lblInstaladas1.setForeground(java.awt.Color.decode("#006100"));
 lblInstaladas2.setForeground(java.awt.Color.decode("#006100"));
 lblInstaladasValue.setForeground(java.awt.Color.decode("#006100"));
 lblInstaladasValue1.setForeground(java.awt.Color.decode("#006100"));
 lblInstaladasValue2.setForeground(java.awt.Color.decode("#006100"));
 
        setExtendedState(MAXIMIZED_BOTH);
        jPanel1.setSize(getMaximumSize());
        sc.returnData('a', (DefaultTableModel) tblRelatorioVendas.getModel(), LocalDate.now(), LocalDate.now());
        ss.tableFormatColors(tblRelatorioVendas);
        lblQtSellsTable.setText((tblRelatorioVendas.getRowCount() > 9 ? tblRelatorioVendas.getRowCount() : "0" + tblRelatorioVendas.getRowCount()) + " Registros de Vendas");
        // fielEditable(txtCPF,"Data"); 
        txtField1.setVisible(false);
        txtField2.setVisible(false);
        cbChoose.setModel(new DefaultComboBoxModel(typeForSearch.values()));

        asc.valueSituationMonth();
        asc.qtdSituationMonth();
        ocultFields(fieldsVisible);
        actionMouseRight();
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

    private void ocultFields(boolean toActive) {
        lblCancelada.setVisible(toActive);
        lblCancelada1.setVisible(toActive);
        lblCancelada2.setVisible(toActive);
        lblInstaladas.setVisible(toActive);
        lblInstaladas2.setVisible(toActive);
        lblInstaladas1.setVisible(toActive);

        lblCanceladaValue.setVisible(toActive);
        lblCanceladaValue1.setVisible(toActive);
        lblCanceladaValue2.setVisible(toActive);
        lblInstaladasValue.setVisible(toActive);
        lblInstaladasValue2.setVisible(toActive);
        lblInstaladasValue1.setVisible(toActive);
        lblProvsingQtd.setVisible(toActive);
        lblProvsingvalue.setVisible(toActive);

        if (toActive) {

            btnSetVisible.setIcon(new ImageIcon(getClass().getResource("/IconsImages/eyeOpen.png")));
        } else {

            btnSetVisible.setIcon(new ImageIcon(getClass().getResource("/IconsImages/eyeHifen.png")));
        }
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
        posicoes.put("tr", findColumnByName("Tr Vendida"));
        return posicoes;
    }

    public int findColumnByName(String name) {
        for (int i = 0; i < tblRelatorioVendas.getColumnCount(); i++) {
            if (tblRelatorioVendas.getColumnName(i).equals(name)) {
                return i;
            }
        }
        return -1;  // Retorna -1 se a coluna não foi encontrada
    }

    public void actionMouseRight() {
        tblRelatorioVendas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {

// Obter a linha e a coluna na qual o usuário clicou
                    int row = tblRelatorioVendas.rowAtPoint(e.getPoint());
                    int column = tblRelatorioVendas.columnAtPoint(e.getPoint());

                    // Selecionar a célula como se o botão esquerdo tivesse sido pressionado
                    tblRelatorioVendas.changeSelection(row, column, false, false);

                    Map<String, Integer> values = returnPositionTable();
// Exibir uma caixa de diálogo
                        AlterData.idUpdate = Integer.parseInt(tblRelatorioVendas.getValueAt(row, 0).toString());
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja alterar a venda: "
                            + "\n Feita em: " + tblRelatorioVendas.getValueAt(row, values.get("dateMade")) + "\n "
                            + "Instalação marcada para " + tblRelatorioVendas.getValueAt(row, values.get("dateInstalation"))
                            + " De " + tblRelatorioVendas.getValueAt(row, values.get("period")), "Venda " + AlterData.idUpdate + " CPF: " + tblRelatorioVendas.getValueAt(row, values.get("cpf")), JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        new AlterData().show();
                        AlterData.txtCPF.setText(tblRelatorioVendas.getValueAt(row, values.get("cpf")) + "");
                        AlterData.txtTrVendida.setText(tblRelatorioVendas.getValueAt(row, values.get("tr")) + "");
                        AlterData.txtCliente.setText(tblRelatorioVendas.getValueAt(row, values.get("custormes")) + "");
                        AlterData.txtContato.setText(tblRelatorioVendas.getValueAt(row, values.get("contact")) + "");
                        AlterData.cbPacote.setSelectedItem(Packages.fromString(tblRelatorioVendas.getValueAt(row, values.get("package")) + ""));
                        AlterData.txtDataInstalacao.setText(tblRelatorioVendas.getValueAt(row, values.get("dateInstalation")) + "");
                        AlterData.cbPeriodo.setSelectedItem(Period.fromString(tblRelatorioVendas.getValueAt(row, values.get("period")) + ""));
                        // txtTrVendida.setText(tblVendasRes.getValueAt(row, 7)+"");
                        AlterData.cbOrigem.setSelectedItem((Origin.fromString(tblRelatorioVendas.getValueAt(row, values.get("origin")) + "")));
                        AlterData.cbSituatiom.setSelectedItem((tblRelatorioVendas.getValueAt(row, values.get("situation"))));
                        AlterData.txaObs.setText(tblRelatorioVendas.getValueAt(row, values.get("obs")) + "");
                        AlterData.ldTSaleMade = format.dateTimeFormaterBank(tblRelatorioVendas.getValueAt(row, values.get("dateMade")) + "");
                        AlterData.txtValue.setText(tblRelatorioVendas.getValueAt(row, values.get("value"))+"");

                    }
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRelatorioVendas = new javax.swing.JTable();
        lblQtSellsTable = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelCanceled12 = new javax.swing.JPanel();
        lblCancelada = new javax.swing.JLabel();
        lblCanceladaValue = new javax.swing.JLabel();
        panelCanceled13 = new javax.swing.JPanel();
        lblInstaladas = new javax.swing.JLabel();
        lblInstaladasValue = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelCanceled9 = new javax.swing.JPanel();
        lblProvsingQtd = new javax.swing.JLabel();
        lblProvsingvalue = new javax.swing.JLabel();
        panelCanceled10 = new javax.swing.JPanel();
        lblCancelada1 = new javax.swing.JLabel();
        lblCanceladaValue1 = new javax.swing.JLabel();
        panelCanceled11 = new javax.swing.JPanel();
        lblInstaladasValue1 = new javax.swing.JLabel();
        lblInstaladas1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        panelCanceled14 = new javax.swing.JPanel();
        lblInstaladas2 = new javax.swing.JLabel();
        lblInstaladasValue2 = new javax.swing.JLabel();
        panelCanceled15 = new javax.swing.JPanel();
        lblCancelada2 = new javax.swing.JLabel();
        lblCanceladaValue2 = new javax.swing.JLabel();
        cbChoose = new javax.swing.JComboBox<>();
        txtField2 = new javax.swing.JTextField();
        txtField1 = new javax.swing.JTextField();
        btnSetVisible = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatorio de Vendas");
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

        panelCanceled12.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled12.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelada.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblCancelada.setForeground(new java.awt.Color(255, 0, 51));
        lblCancelada.setText("Canceladas");
        panelCanceled12.add(lblCancelada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblCanceladaValue.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblCanceladaValue.setForeground(new java.awt.Color(255, 0, 51));
        lblCanceladaValue.setText("Canceladas");
        panelCanceled12.add(lblCanceladaValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, 20));

        jPanel3.add(panelCanceled12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 300, 60));

        panelCanceled13.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled13.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstaladas.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblInstaladas.setForeground(new java.awt.Color(0, 153, 0));
        lblInstaladas.setText("Instaladas");
        panelCanceled13.add(lblInstaladas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblInstaladasValue.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblInstaladasValue.setForeground(new java.awt.Color(0, 153, 0));
        lblInstaladasValue.setText("Instaladas");
        panelCanceled13.add(lblInstaladasValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, -1));

        jPanel3.add(panelCanceled13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 300, 60));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 320, 150));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51)), "Este Mês", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCanceled9.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled9.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProvsingQtd.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblProvsingQtd.setForeground(new java.awt.Color(255, 153, 0));
        lblProvsingQtd.setText("Provising");
        panelCanceled9.add(lblProvsingQtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblProvsingvalue.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblProvsingvalue.setForeground(new java.awt.Color(255, 153, 0));
        lblProvsingvalue.setText("Provising");
        panelCanceled9.add(lblProvsingvalue, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        jPanel4.add(panelCanceled9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 370, 40));

        panelCanceled10.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled10.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelada1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblCancelada1.setForeground(new java.awt.Color(255, 0, 51));
        lblCancelada1.setText("Canceladas");
        panelCanceled10.add(lblCancelada1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblCanceladaValue1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblCanceladaValue1.setForeground(new java.awt.Color(255, 0, 51));
        lblCanceladaValue1.setText("Canceladas");
        panelCanceled10.add(lblCanceladaValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 80, 20));

        jPanel4.add(panelCanceled10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 370, 40));

        panelCanceled11.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled11.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstaladasValue1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblInstaladasValue1.setForeground(new java.awt.Color(0, 153, 0));
        lblInstaladasValue1.setText("Instaladas");
        panelCanceled11.add(lblInstaladasValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        lblInstaladas1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblInstaladas1.setForeground(new java.awt.Color(0, 153, 0));
        lblInstaladas1.setText("Instaladas");
        panelCanceled11.add(lblInstaladas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel4.add(panelCanceled11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 370, 40));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 390, 150));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51), new java.awt.Color(51, 0, 51)), "Este Ano", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        panelCanceled14.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled14.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstaladas2.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblInstaladas2.setForeground(new java.awt.Color(0, 153, 0));
        lblInstaladas2.setText("Instaladas");
        panelCanceled14.add(lblInstaladas2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        lblInstaladasValue2.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblInstaladasValue2.setForeground(new java.awt.Color(0, 153, 0));
        lblInstaladasValue2.setText("Instaladas");
        panelCanceled14.add(lblInstaladasValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

        panelCanceled15.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled15.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelada2.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblCancelada2.setForeground(new java.awt.Color(255, 0, 51));
        lblCancelada2.setText("Canceladas");
        panelCanceled15.add(lblCancelada2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblCanceladaValue2.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        lblCanceladaValue2.setForeground(new java.awt.Color(255, 0, 51));
        lblCanceladaValue2.setText("Canceladas");
        panelCanceled15.add(lblCanceladaValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCanceled15, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
            .addComponent(panelCanceled14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(panelCanceled15, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(panelCanceled14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 340, 150));

        cbChoose.setBackground(new java.awt.Color(255, 255, 255));
        cbChoose.setForeground(new java.awt.Color(0, 0, 0));
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

        txtField2.setBackground(new java.awt.Color(255, 255, 255));
        txtField2.setForeground(new java.awt.Color(0, 0, 0));
        txtField2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "CPF/CNPJ"));
        txtField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtField2KeyPressed(evt);
            }
        });
        jPanel1.add(txtField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 200, 40));

        txtField1.setBackground(new java.awt.Color(255, 255, 255));
        txtField1.setForeground(new java.awt.Color(0, 0, 0));
        txtField1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data Inicio"));
        txtField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtField1KeyPressed(evt);
            }
        });
        jPanel1.add(txtField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 190, 40));

        btnSetVisible.setBackground(new java.awt.Color(255, 255, 255));
        btnSetVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsImages/eyeOpen.png"))); // NOI18N
        btnSetVisible.setBorder(null);
        btnSetVisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetVisibleActionPerformed(evt);
            }
        });
        jPanel1.add(btnSetVisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 170, 30, 30));

        jToggleButton1.setText("Exportar PDF");
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 230, -1, -1));

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

        jMenu2.setText("Exportar PDF");

        jMenuItem1.setText("Entre Datas");
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Este Mês");
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Mês Passado");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

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
            if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
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
            sc.returnData('l', dtm, LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(1));
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

    private void btnSetVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetVisibleActionPerformed
        fieldsVisible = !fieldsVisible;
        ocultFields(fieldsVisible);
    }//GEN-LAST:event_btnSetVisibleActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllSales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSetVisible;
    private javax.swing.JComboBox<String> cbChoose;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    public static javax.swing.JLabel lblCancelada;
    public static javax.swing.JLabel lblCancelada1;
    public static javax.swing.JLabel lblCancelada2;
    public static javax.swing.JLabel lblCanceladaValue;
    public static javax.swing.JLabel lblCanceladaValue1;
    public static javax.swing.JLabel lblCanceladaValue2;
    public static javax.swing.JLabel lblInstaladas;
    public static javax.swing.JLabel lblInstaladas1;
    public static javax.swing.JLabel lblInstaladas2;
    public static javax.swing.JLabel lblInstaladasValue;
    public static javax.swing.JLabel lblInstaladasValue1;
    public static javax.swing.JLabel lblInstaladasValue2;
    public static javax.swing.JLabel lblProvsingQtd;
    public static javax.swing.JLabel lblProvsingvalue;
    public static javax.swing.JLabel lblQtSellsTable;
    private javax.swing.JPanel panelCanceled10;
    private javax.swing.JPanel panelCanceled11;
    private javax.swing.JPanel panelCanceled12;
    private javax.swing.JPanel panelCanceled13;
    private javax.swing.JPanel panelCanceled14;
    private javax.swing.JPanel panelCanceled15;
    private javax.swing.JPanel panelCanceled9;
    public static javax.swing.JTable tblRelatorioVendas;
    private javax.swing.JTextField txtField1;
    private javax.swing.JTextField txtField2;
    // End of variables declaration//GEN-END:variables
}
