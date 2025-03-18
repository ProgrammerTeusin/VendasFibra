package View;

import Controller.Formatting;
import Controller.GoalsDefineds;
import Model.Enums.TypeCalculation;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class PartnerShips extends javax.swing.JFrame {

    BigDecimal valueP;
    Map<String, List<GoalsDefineds>> packsServicesDefined = new HashMap<>();
    Map<String, List<GoalsDefineds>> packsServicesDefinedSketch = new HashMap<>();//sketch == rascunho
    JTabbedPane jTabbedPanes;
    String[] packServiceChange;
    int QtdToGoals;
    int rowClick = -1;
    TypeFunctionBtn typeButton = TypeFunctionBtn.SAVE;
    TypeCalculation typeCalculation;

    enum TypeFunctionBtn {
        SAVE,
        UPDATE
    };
    enum TypeFunctionSwitch {
        DELETE,
        UPDATE
    };

    public PartnerShips() {
        initComponents();
        txtDateSave.setText(format.dateTimeFormaterField(LocalDateTime.now()));
        txtAmountOfMounthsToReceive.setText(1 + "");
        txtAmountOfMounthsToReceive.setHorizontalAlignment(JTextField.RIGHT);
        txtPackageOrService.requestFocus();

    }
    Formatting format = new Formatting();

    public void createViewTab(String[] packs) {
        killTabbedPanes();
        jTabbedPanes = new JTabbedPane();

        jTabbedPanes.setBackground(new Color(255, 255, 255));

        for (String pack : packs) {
//criacao da tablea goals para cada pacote ou servico que for adicionado
            JTable tblGoals = createTableGoals();
            // Envolva a tabela em um JScrollPane
            JScrollPane scrollPane = new JScrollPane(tblGoals);

//fim tabela
            packsServicesDefined.put(pack, new ArrayList<>());

            System.out.println(pack);
            jTabbedPanes.addTab(pack, scrollPane);
        }
        jPanel2.add(jTabbedPanes, new AbsoluteConstraints(20, 100, 690, 290));
        System.out.println("QTD de comp: " + jPanel2.getComponentCount());
    }

    private JTable createTableGoals() {
        JTable tblGoals = new JTable();
        tblGoals.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Pacote/Serviço", "Valor R$", "Tipo de Calculo", "Qtd Meta"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        actionMouseRight(tblGoals);
        return tblGoals;
    }

    public static void addGoalToPack(Map<String, List<GoalsDefineds>> packsServicesDefined, String packS, GoalsDefineds goal) {
        // Verificar se o pacote existe
        packsServicesDefined.computeIfAbsent(packS, k -> new ArrayList<>()).add(goal);
    }

    private void updateTableForPack(String packS) {
        JTable tblGoals = getTableForPack(packS);
        if (tblGoals == null) {
            System.out.println("Tabela não encontrada para o pacote: " + packS);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblGoals.getModel();
        model.setRowCount(0); // Limpar tabela

        List<GoalsDefineds> goals = packsServicesDefined.get(packS);
        if (goals != null) {
            for (GoalsDefineds goal : goals) {
                model.addRow(
                        new Object[]{
                            goal.getPackService(),
                            goal.getValue(),
                            goal.getTypeCalcule(),
                            goal.getQdtToGoal()});
            }
        }

        tblGoals.revalidate();
        tblGoals.repaint();
    }

    private JTable getTableForPack(String packS) {
        for (int i = 0; i < jTabbedPanes.getTabCount(); i++) {
            if (jTabbedPanes.getTitleAt(i).equals(packS)) {
                JScrollPane scrollPane = (JScrollPane) jTabbedPanes.getComponentAt(i);
                return (JTable) scrollPane.getViewport().getView();
            }
        }
        return null;
    }

    public void killTabbedPanes() {

        for (Component component : jPanel2.getComponents()) {
            if (component instanceof JTabbedPane) {
                jPanel2.remove(component);
            }
        }
    }

    private void showSuccessMessage() {
        // Criar JDialog para a mensagem de sucesso
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true); // Remove barra de título
        dialog.setSize(300, 100); // Definir tamanho da caixa de mensagem

        // Centralizar a caixa de mensagem na tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 760;
        int y = 160;
//    int x = (screenSize.width - dialog.getWidth()) / 2;
//    int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        // Criar JLabel para a mensagem de sucesso
        JLabel lblSuccess = new JLabel("Meta incluída com sucesso!", JLabel.CENTER);
        lblSuccess.setFont(new Font("Arial", Font.BOLD, 16));
        lblSuccess.setForeground(Color.GREEN);
        dialog.add(lblSuccess);

        // Mostrar a caixa de mensagem
        dialog.setVisible(true);

        // Criar Timer para fechar a caixa de mensagem após 2 segundos
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Fechar a caixa de mensagem
            }
        });
        timer.setRepeats(false); // Garantir que o timer seja executado apenas uma vez
        timer.start();
    }

    private void temporyClean() {
        cbPackService.setSelectedIndex(0);
        valueP = null;
        cbCalculeType.setSelectedIndex(0);
        txtQtdGoals.setText("");
        txtValueService.setText("");
        typeCalculation = null;
    }

    private void updateGoals() {
        String cbPackSer = cbPackService.getSelectedItem() + "";
        if (!cbPackSer.equals("Todos")) {
            packsServicesDefined.get(cbPackSer).set(rowClick,
                    new GoalsDefineds(cbPackSer,valueP, typeCalculation, QtdToGoals) {
            });

            JTable table = getTableForPack(cbPackSer);
            table.setValueAt(cbPackSer, rowClick, 1);
            table.setValueAt(format.formatMoneyNumber(valueP + "", 'M'), rowClick, 1);
            table.setValueAt(typeCalculation, rowClick, 2);
            table.setValueAt(QtdToGoals, rowClick, 3);
        } else {
            for (String packServ : packServiceChange) {

                for (int i = 0; i < packsServicesDefined.get(packServ).size(); i++) {
                    System.out.println("Antes: " + packsServicesDefined.get(packServ).get(i).toString());
                    if (packsServicesDefined.get(packServ).get(i).getQdtToGoal() == packsServicesDefinedSketch.get(packServ).get(rowClick).getQdtToGoal()) {
                        if (rootPaneCheckingEnabled) {
                            
                        }
                        JTable table = getTableForPack(packServ);
                        table.setValueAt(packServ, i, 1);
                        table.setValueAt(format.formatMoneyNumber(valueP + "", 'M'), i, 1);
                        table.setValueAt(typeCalculation, i, 2);
                        table.setValueAt(QtdToGoals, i, 3);

                        packsServicesDefined.get(packServ).set(rowClick,
                                new GoalsDefineds(packServ,valueP, typeCalculation, QtdToGoals) {
                        });
                        System.out.println("Depois: " + packsServicesDefined.get(packServ).get(i).toString());
                    }
                }
            }
            temporyClean();
            cbPackService.requestFocus();
        }
    }

    public void actionMouseRight(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Criação de popup
                    JPopupMenu pop = new JPopupMenu();
                    JMenuItem toUpdate = new JMenuItem("Editar");

                    JMenu menuDelete = new JMenu("Excluir");
                    JMenuItem popDeleteRowAllTable = new JMenuItem("Excluir de todas Metas");
                    JMenuItem popDelete = new JMenuItem("Excluir Meta");

                    popDelete.addActionListener(actionEvent -> {
                        int answer = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar essa meta?", "Aviso", JOptionPane.ERROR_MESSAGE);
                        if (answer == 0) {
                            actionListnerToDelete();

                        }
                    });
                    toUpdate.addActionListener(actionEvent -> {
                        // int answer = JOptionPane.showConfirmDialog(null, "Deseja editar essa meta?", "Aviso", JOptionPane.ERROR_MESSAGE);
                        // if (answer == 0) {

                        // Obter índice do guia atual
                        int selectedTabIndex = jTabbedPanes.getSelectedIndex();
                        String selectedTabTitle = jTabbedPanes.getTitleAt(selectedTabIndex);

                        // Obter a linha clicada
                        rowClick = table.getSelectedRow();

                        cbPackService.setSelectedItem(table.getValueAt(rowClick, 0));
                        txtValueService.setText(format.formatMoneyNumber(table.getValueAt(rowClick, 1) + "", 'M'));
                        cbCalculeType.setSelectedItem(table.getValueAt(rowClick, 2));
                        txtQtdGoals.setText(table.getValueAt(rowClick, 3) + "");
                        typeButton = TypeFunctionBtn.UPDATE;
                        packsServicesDefinedSketch = packsServicesDefined;
                        System.out.println("linha selecionada: " + rowClick + "  Tab selecionada: " + selectedTabTitle);
                        //}
                    });

                    menuDelete.add(popDelete);
                    menuDelete.add(popDeleteRowAllTable);

                    pop.add(toUpdate);
                    pop.add(menuDelete); // Adicione menuDelete diretamente ao popup

                    table.setComponentPopupMenu(pop);
                }
            }

            private void actionListnerToDelete() {
                packsServicesDefined.forEach((p, k) -> {
                    System.out.println("Chave: " + p + " Valore: " + k.toString() + " Tamanho da lista: " + k.size());
                });
                for (String pack : packServiceChange) {
                    // Obter a tabela correta para o pacote atual
                    JTable packTable = getTableForPack(pack);
                    // Obter o modelo da tabela
                    DefaultTableModel model = (DefaultTableModel) packTable.getModel();
                    // Remover a linha selecionada da tabela correspondente
                    int selectedRow = packTable.getSelectedRow();
                    if (selectedRow != -1) {
                        model.removeRow(selectedRow);
                        packsServicesDefined.get(pack).remove(selectedRow);
                    }

                }
                packsServicesDefined.forEach((p, k) -> {
                    System.out.println("Chave: " + p + " Valore: " + k.toString() + " Tamanho da lista: " + k.size());
                });
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtAmountOfMounthsToReceive = new javax.swing.JTextField();
        txtPartnership = new javax.swing.JTextField();
        txtDateSave = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        cbCalculeType = new javax.swing.JComboBox<>();
        txtQtdGoals = new javax.swing.JTextField();
        btnInsertGoal = new javax.swing.JButton();
        txtValueService = new javax.swing.JTextField();
        cbPackService = new javax.swing.JComboBox<>();
        cbCaluleSituation = new javax.swing.JComboBox<>();
        txtPackageOrService = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtAmountOfMounthsToReceive.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Qtd Mes Pos Venda a Receber "));
        txtAmountOfMounthsToReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountOfMounthsToReceiveActionPerformed(evt);
            }
        });
        txtAmountOfMounthsToReceive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAmountOfMounthsToReceiveKeyPressed(evt);
            }
        });
        jPanel1.add(txtAmountOfMounthsToReceive, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 190, 40));

        txtPartnership.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Parceria"));
        txtPartnership.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPartnershipActionPerformed(evt);
            }
        });
        txtPartnership.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPartnershipKeyPressed(evt);
            }
        });
        jPanel1.add(txtPartnership, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 190, 40));

        txtDateSave.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data Criação"));
        txtDateSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDateSaveKeyPressed(evt);
            }
        });
        jPanel1.add(txtDateSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 40, 130, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Definir Metas"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbCalculeType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "Maior", "Maior igual", "Menor", "Menor igual", "Igual", "Diferente" }));
        cbCalculeType.setToolTipText("");
        cbCalculeType.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de calculo"));
        cbCalculeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCalculeTypeActionPerformed(evt);
            }
        });
        cbCalculeType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbCalculeTypeKeyPressed(evt);
            }
        });
        jPanel2.add(cbCalculeType, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 110, 40));

        txtQtdGoals.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Qtd para Meta"));
        txtQtdGoals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdGoalsActionPerformed(evt);
            }
        });
        txtQtdGoals.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtdGoalsKeyPressed(evt);
            }
        });
        jPanel2.add(txtQtdGoals, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 172, 40));

        btnInsertGoal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/icones/mais.png"))); // NOI18N
        btnInsertGoal.setToolTipText("Procurar Clientes");
        btnInsertGoal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertGoalActionPerformed(evt);
            }
        });
        btnInsertGoal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnInsertGoalKeyPressed(evt);
            }
        });
        jPanel2.add(btnInsertGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 40, 30));

        txtValueService.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Valor R$"));
        txtValueService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValueServiceActionPerformed(evt);
            }
        });
        txtValueService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValueServiceKeyPressed(evt);
            }
        });
        jPanel2.add(txtValueService, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 140, 40));

        cbPackService.setToolTipText("");
        cbPackService.setBorder(javax.swing.BorderFactory.createTitledBorder("Pacote/Serviço"));
        cbPackService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPackServiceActionPerformed(evt);
            }
        });
        cbPackService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPackServiceKeyPressed(evt);
            }
        });
        jPanel2.add(cbPackService, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 1200, 440));

        cbCaluleSituation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Feitas", "Instaladas" }));
        cbCaluleSituation.setToolTipText("");
        cbCaluleSituation.setBorder(javax.swing.BorderFactory.createTitledBorder("Situação a Calcular"));
        cbCaluleSituation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCaluleSituationActionPerformed(evt);
            }
        });
        cbCaluleSituation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbCaluleSituationKeyPressed(evt);
            }
        });
        jPanel1.add(cbCaluleSituation, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 120, 40));

        txtPackageOrService.setToolTipText("Escrever assim: 'XMB;YMG'");
        txtPackageOrService.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Pacotes/Serviços"));
        txtPackageOrService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPackageOrServiceKeyPressed(evt);
            }
        });
        jPanel1.add(txtPackageOrService, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 150, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAmountOfMounthsToReceiveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountOfMounthsToReceiveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

        }
    }//GEN-LAST:event_txtAmountOfMounthsToReceiveKeyPressed

    private void txtAmountOfMounthsToReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountOfMounthsToReceiveActionPerformed

    }//GEN-LAST:event_txtAmountOfMounthsToReceiveActionPerformed

    private void txtPartnershipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPartnershipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPartnershipActionPerformed

    private void txtPartnershipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPartnershipKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPartnershipKeyPressed

    private void txtDateSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDateSaveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

        } else if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("passou");
        }
    }//GEN-LAST:event_txtDateSaveKeyPressed

    private void txtQtdGoalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdGoalsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtdGoalsActionPerformed

    private void txtQtdGoalsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtdGoalsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            QtdToGoals = Integer.parseInt(txtQtdGoals.getText());
            btnInsertGoal.requestFocus();
        }
    }//GEN-LAST:event_txtQtdGoalsKeyPressed

    private void cbCalculeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCalculeTypeActionPerformed
        //JOptionPane.showMessageDialog(null,"Pacote escolhido: "+packgeSelected,"Aviso",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cbCalculeTypeActionPerformed

    private void cbCalculeTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCalculeTypeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtQtdGoals.requestFocus();
        }
    }//GEN-LAST:event_cbCalculeTypeKeyPressed

    private void cbCaluleSituationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCaluleSituationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCaluleSituationActionPerformed

    private void cbCaluleSituationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCaluleSituationKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCaluleSituationKeyPressed

    private void txtPackageOrServiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPackageOrServiceKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (cbPackService.getItemCount() > 0) {
                cbPackService.removeAllItems();
            }
            packServiceChange = txtPackageOrService.getText().split(";");
            createViewTab(packServiceChange);
            cbPackService.addItem("Selecione...");
            for (String pack : packServiceChange) {
                cbPackService.addItem(pack);

            }
            cbPackService.addItem("Todos");
            cbPackService.requestFocus();
        }
    }//GEN-LAST:event_txtPackageOrServiceKeyPressed

    private void btnInsertGoalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertGoalActionPerformed
        showSuccessMessage();
    }//GEN-LAST:event_btnInsertGoalActionPerformed

    private void txtValueServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValueServiceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValueServiceActionPerformed

    private void txtValueServiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValueServiceKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            valueP = new BigDecimal(format.formatMoneyNumber(txtValueService.getText(), 'N'));
            txtValueService.setText(format.formatMoneyNumber(valueP + "", 'M'));
            cbCalculeType.requestFocus();
        }
    }//GEN-LAST:event_txtValueServiceKeyPressed

    private void cbPackServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPackServiceActionPerformed

    }//GEN-LAST:event_cbPackServiceActionPerformed

    private void cbPackServiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPackServiceKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            txtValueService.requestFocus();
        }
    }//GEN-LAST:event_cbPackServiceKeyPressed

    private void btnInsertGoalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnInsertGoalKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            if (typeButton == TypeFunctionBtn.SAVE) {

                addGoals();
            } else {
                updateGoals();
                typeButton = TypeFunctionBtn.SAVE;
            }
        }


    }//GEN-LAST:event_btnInsertGoalKeyPressed

    private void addGoals() {
        String packS = cbPackService.getSelectedItem().toString();
        typeCalculation = TypeCalculation.fromString(cbCalculeType.getSelectedItem().toString());

        // Validar valores
//            if (packS.isEmpty() || typeCalculate == null) {
//                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
        if (cbPackService.getSelectedItem().equals("Todos")) {
            for (String packSer : packServiceChange) {
                addGoalToPack(packsServicesDefined, packSer, new GoalsDefineds(packSer, valueP, typeCalculation, QtdToGoals));
                updateTableForPack(packSer);
            }
        } else {

            // Adicionar meta ao pacote
            addGoalToPack(packsServicesDefined, packS, new GoalsDefineds(packS, valueP, typeCalculation, QtdToGoals));
            // Atualizar a tabela para o pacote correspondente
            updateTableForPack(packS);
        }

        cbPackService.requestFocus();
        showSuccessMessage();
        temporyClean();
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PartnerShips().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertGoal;
    private javax.swing.JComboBox<String> cbCalculeType;
    private javax.swing.JComboBox<String> cbCaluleSituation;
    private javax.swing.JComboBox<String> cbPackService;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtAmountOfMounthsToReceive;
    private javax.swing.JTextField txtDateSave;
    private javax.swing.JTextField txtPackageOrService;
    private javax.swing.JTextField txtPartnership;
    private javax.swing.JTextField txtQtdGoals;
    private javax.swing.JTextField txtValueService;
    // End of variables declaration//GEN-END:variables

}
