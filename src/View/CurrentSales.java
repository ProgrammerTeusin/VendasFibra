package View;

import Controller.AllSalesController;
import Controller.Formatting;
import Controller.SalesController;
import Controller.SellerController;
import Dao.SalesDAO;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.PartnerShip;
import Model.Enums.Period;
import Model.Enums.Situation;
import static Model.Enums.Situation.CANCELED;
import static Model.Enums.Situation.INSTALLED;
import static Model.Enums.Situation.PROVISIONING;
import Model.Enums.ToPrioritize;
import Model.Sales;
import Model.Seller;
import Services.JTablesFormatting;
import Services.OptionsWindow;
import Services.OptionsWindow;
import Services.SaleService;
import Services.ToPDF;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import sounds.PlaySound;

public class CurrentSales extends javax.swing.JFrame {

    public static CurrentSales mainScreen = new CurrentSales();

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
    private LocalDateTime ldTSaleMadeExtra;
    private float valuePackage;
    private TypeOperation type;
    int idSelection = 0;
    int id = 0;
    int row = -1;
    SaleService ss = new SaleService();
    AllSalesController asc = new AllSalesController();
    boolean fieldsVisible = true;
    boolean searchInstaltionToday = false;
    ToPrioritize priotize;
    Formatting format = new Formatting();
    SalesController sc = new SalesController();
    SellerController sellerControl = new SellerController();
    Map<String, Object> dataBeforeUpdate = new HashMap<>();
    Map<String, Object> dataafterUpdate = new HashMap<>();
    private final String tomorrowDay = LocalDate.now().getDayOfWeek() != DayOfWeek.SATURDAY
            ? format.dateFormaterField(LocalDate.now().plusDays(1))
            : format.dateFormaterField(LocalDate.now().plusDays(2));
    List<Sales> severelSales = new ArrayList<>();
    Seller seller;
    PartnerShip partnerShip;

    List<JRadioButton> rd = new ArrayList<>();

    public CurrentSales() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        ColorInPainel();
        jPanel1.setSize(getMaximumSize());

        fillingsAllComponentsUpdated();

        filingComoBoxValues();
        txtCPF.requestFocus();
        txtDataInstalacao.setText(tomorrowDay);
        // txaObs.setText("Sem Ligação");
        JTablesFormatting.tableFormatColors(tblVendasRes);

        actionMouseRight();
        triggerToChage(false);
        type = TypeOperation.INSERT;
        lblDateMade.setText("");
        lblSalesWithoutInstalationTimeUpdate.setText("");
        lblSalesWithoutInstalation.setText("");
        sc.infinitSearchSalesNoInstalled();
        if (tblVendasRes.getRowCount() > 0) {
            jButton1.setVisible(false);
        } else {

            jButton1.setVisible(true);
        }
        seller = new Seller(cbTR.getSelectedItem() + "");
        sellerControl.welcome(seller);
        sellerControl.insertAccess(seller);
        lblAccessQtd.setText("Você esta acessando pela " + sellerControl.qtdAccess(seller) + "° Vez");
        cboxUpdateDateMade.setVisible(false);
        txtDateMadeUpdate.setVisible(false);

        jButton2.setVisible(false);

        mouseAndCloseOption();
        applyWidthsFromFile();
        applyInsertWidths();
        
        cbTR.setSelectedIndex(2);

    }

    public void mouseAndCloseOption() {
        OptionsWindow.CloseWindow(this);

        OptionsWindow.mouseOpt(jButton1);
        OptionsWindow.mouseOpt(btnCancell);
        OptionsWindow.mouseOpt(btnSale);
        OptionsWindow.mouseOpt(btnSetVisible);
        OptionsWindow.mouseOpt(btnSearchIntaltion);
        OptionsWindow.mouseOpt(lblFeitasHoje);
        OptionsWindow.mouseOpt(jPanel9);
        //OptionsWindow.mouseOpt(brnRestartSystem);
    }

    public void fillingsAllComponentsUpdated() {
        sc.returnData('m', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());
        sc.fillingsPacksges('m', LocalDateTime.now());
        fillingsValuesPacksges();
        returnPacksInstalledsAndValuesLBL();
        typePossibilitySellins();
        int qtdIn = returnSellingsDoToday();
        lblFeitasHoje.setText(qtdIn > 0 ? qtdIn + "" : "Nenhuma");
        lblQtSellsTable.setText((tblVendasRes.getRowCount() > 9 ? tblVendasRes.getRowCount() : "0" + tblVendasRes.getRowCount()) + " Registros de Vendas");
    }

    public void filingComoBoxValues() {
        cbPacote.setModel(new DefaultComboBoxModel(Packages.values()));
        cbPeriodo.setModel(new DefaultComboBoxModel(Period.values()));
        cbOrigem.setModel(new DefaultComboBoxModel(Origin.values()));
        cbSeachSelingsBy.setModel(new DefaultComboBoxModel(Situation.values()));
        cbPartnerShip.setModel(new DefaultComboBoxModel(PartnerShip.values()));

        List<Seller> sellers = new SellerController().returnAllSeller();
        for (Seller seller : sellers) {

            cbTR.addItem(seller.getTr());
        }
        cbSeachSelingsBy.addItem("Priorizadas");

        cbSituatiom.setModel(new DefaultComboBoxModel(Situation.values()));
        cbSituatiom.removeItem(Situation.ALL);
        cbSituatiom.removeItem(Situation.DAY);
    }

    public void ColorInPainel() throws NumberFormatException {
        panelCanceled3.setBackground(java.awt.Color.decode("#FFC7CE"));
        panelCanceled1.setBackground(java.awt.Color.decode("#FFC7CE"));
        panelCanceled4.setBackground(java.awt.Color.decode("#FFC7CE"));
        panelCanceled5.setBackground(java.awt.Color.decode("#C6EFCE"));
        panelCanceled6.setBackground(java.awt.Color.decode("#C6EFCE"));
        panelCanceled7.setBackground(java.awt.Color.decode("#C6EFCE"));
        panelCanceled8.setBackground(java.awt.Color.decode("#C6EFCE"));
        panelCanceled9.setBackground(java.awt.Color.decode("#FFE699"));
        panelCanceled10.setBackground(java.awt.Color.decode("#FFE699"));
        panelCanceled11.setBackground(java.awt.Color.decode("#FFE699"));
        panelCanceled12.setBackground(java.awt.Color.decode("#FFE699"));
        panelCanceled13.setBackground(java.awt.Color.decode("#FFC7CE"));
        paneValue.setBackground(java.awt.Color.decode("#C6EFCE"));
        paneValue.setVisible(false);
        lblValuePlan.setBackground(java.awt.Color.decode("#C6EFCE"));
        lblValuePlan.setForeground(java.awt.Color.decode("#006100"));
    }

    private int searchQtdSituationTable(Situation situation) {
        int col = findColumnByName("Situação");
        int qtd = 0;
        if (situation == Situation.ALL) {
            qtd = tblVendasRes.getRowCount();
        } else {
            for (int i = 0; i < tblVendasRes.getRowCount(); i++) {
                String value = tblVendasRes.getValueAt(i, col) + "";
                if (value.equals(situation.toString())) {
                    qtd++;
                }

            }
        }

        return qtd;
    }

    public String returnSimbolsSomeQtd(String simbols, int qtd) {
        String value = "";
        for (int i = 0; i < qtd; i++) {
            value += simbols;
        }
        return value;
    }

    private String uptadesObservation() {
        StringBuilder msgObs = new StringBuilder();
        dataBeforeUpdate.forEach((key, value) -> {
            if (dataafterUpdate.containsKey(key)) {
                if (wasThereUpadteInFilds(value, dataafterUpdate.get(key))) {
                    if (key.equals("dateInstalation")) {

                        msgObs.append("Remarcado de ")
                                .append(value)
                                .append(" de ")
                                .append(dataBeforeUpdate.get("period"))
                                .append(" Para ")
                                .append(dataafterUpdate.get(key))
                                .append(" de ")
                                .append(dataafterUpdate.get("period"))
                                .append("\n");

                    } else if (!key.equals("situation")) {
                        msgObs.append(key + " mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");

                    } else {
                        msgObs.append("Sua Venda mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");

                    }
                }
                //         if (key.equals("dateInstalation")) {
//
//                    msgObs.append("Remarcado de ").append(value)
//                            .append(" Para ")
//                            .append(dataafterUpdate.get(key))
//                            .append(" Na parte da ")
//                            .append(dataafterUpdate.get("period"))
//                            .append("\n");
//                } else if (key.equals("cpf")) {
//                    msgObs.append("CPF mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");
//                } else if (key.equals("custormes")) {
//                    msgObs.append("Nome mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");
//                } else if (key.equals("contact")) {
//                    msgObs.append("Whats/Telefone mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");
//                } else if (key.equals("package")) {
//                    msgObs.append("Pacote mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");
//                } else if (key.equals("origin")) {
//                    msgObs.append("Origem mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");
//                } else if (key.equals("situation")) {
//                    msgObs.append("Situação mudou de ").append(value).append(" Para ").append(dataafterUpdate.get(key)).append("\n");
//                } else {
//                    System.out.println("nada aconteceu");
//                }
//                
            }
        });
        return msgObs.toString().length() > 0 ? returnSimbolsSomeQtd("--%$&--", 10) + "\n" + returnSimbolsSomeQtd(" ", 5) + "Atualizado em "
                + format.dateTimeFormaterField(LocalDateTime.now()) + "\n" + msgObs.toString() : "";
    }

    private void play() {

        //  if ((dataBeforeUpdate.get("situation") != null && dataafterUpdate.get("situation") != null)) {
        if (dataBeforeUpdate.get("situation") != dataafterUpdate.get("situation") && dataafterUpdate.get("situation").equals(Situation.INSTALLED)) {

            Thread gloria = playSound("gloria.mp3");
            Thread ris = playSound("risada.mp3");
            ris.start();
            gloria.start();

        }
//        if (dataafterUpdate.get("situation").equals(Situation.CANCELED)) {
//            Thread no = playSound("ohno.mp3");
//            no.start();
//        }
        // }
    }

    public Thread playSound(String toc) {
        Thread t = new Thread(() -> {
            try {
                new PlaySound().run(toc);
            } catch (InterruptedException ex) {
                Logger.getLogger(CurrentSales.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        return t;
    }

    private boolean wasThereUpadteInFilds(Object value1, Object value2) {
        return !value1.equals(value2);

    }

    private enum TypeOperation {
        UPDATE,
        INSERT;
    }

    private int returnSellingsDoToday() {
        int count = 0;
        Map<String, Integer> position = returnPositionTable();
        for (int i = 0; i < tblVendasRes.getRowCount(); i++) {
            int day = format.dateTimeFormaterBank(tblVendasRes.getValueAt(i, position.get("dateMade")) + "").getDayOfMonth();
            int month = format.dateTimeFormaterBank(tblVendasRes.getValueAt(i, position.get("dateMade")) + "").getMonthValue();
            if (day == LocalDate.now().getDayOfMonth() && month == LocalDate.now().getMonthValue()) {
                count++;
            }
        }
        return count;
    }

    private void returnPacksInstalledsAndValuesLBL() {
        Map<String, Integer> positionT = returnPositionTable();
        if (fillingPacksValuesWillIntalationToday(positionT) > 0 || countPackgeInstalled(tblVendasRes, positionT.get("dateInstalation"), positionT.get("situation")) > 0) {
            fillingPacksValuesIntaledToday(positionT);
            btnSearchIntaltion.setVisible(true);
        } else {
            btnSearchIntaltion.setVisible(false);

        }
    }

    private void ocultFields(boolean toActive) {
        lblAprovisionamento400.setVisible(toActive);
        lblAprovisionamento600.setVisible(toActive);
        lblAprovisionamento700.setVisible(toActive);
        lblAprovisionamentoTot.setVisible(toActive);
        lblAprovisionamentoTot1.setVisible(toActive);

        lblCancelada400.setVisible(toActive);
        lblCancelada600.setVisible(toActive);
        lblCancelada700.setVisible(toActive);
        lblCanceladaTot.setVisible(toActive);
        lblCanceladaTot1.setVisible(toActive);

        lblInstalada400.setVisible(toActive);
        lblInstalada600.setVisible(toActive);
        lblInstalada700.setVisible(toActive);
        lblInstaladaTot.setVisible(toActive);
        lblInstaladaTot1.setVisible(toActive);
        lblQtSellsTable.setVisible(toActive);
        lblEstimativa.setVisible(toActive);
        jPanel5.setVisible(toActive);
        jPanel9.setVisible(toActive);
        if (toActive) {

            btnSetVisible.setIcon(new ImageIcon(getClass().getResource("/IconsImages/eyeOpen.png")));
        } else {

            btnSetVisible.setIcon(new ImageIcon(getClass().getResource("/IconsImages/eyeHifen.png")));
        }
    }

    private void fillData() {

        String fielsWithout = fielWithoutFielling();
        if (fielsWithout == "" || fielsWithout == null || fielsWithout.length() == 0) {
            if (cliente == null) {
                cliente = txtCliente.getText();
            }
            //   if (trSell == null) {
            trSell = cbTR.getSelectedItem() + "";
            //}

            if (cpf == null) {
                cpf = txtCPF.getText();
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
            if (cboxPriorizar.isSelected()) {
                //JOptionPane.showMessageDialog(null, "Você selecionou" , "Aviso", JOptionPane.WARNING_MESSAGE);
                priotize = ToPrioritize.YES;
            }
            if (!cboxPriorizar.isSelected()) {
                //JOptionPane.showMessageDialog(null, "Você selecionou" , "Aviso", JOptionPane.WARNING_MESSAGE);
                priotize = ToPrioritize.NO;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Favor preencher os seguintes campos\n " + fielsWithout, "Aviso", JOptionPane.WARNING_MESSAGE);

        }

    }

    private void fillDataUpadate() {

        String fielsWithout = fielWithoutFielling();
        if (fielsWithout == "" || fielsWithout == null || fielsWithout.length() == 0) {

            cliente = txtCliente.getText();

            trSell = cbTR.getSelectedItem() + "";

            cpf = txtCPF.getText();

            contacts = txtContato.getText();

            //observation = (observation != null || !observation.trim().isEmpty()) ? observation+  + uptadesObservation() : uptadesObservation();
            setDatasUpdateBeforeOrAfter(dataafterUpdate);

            ldTSaleMade = format.dateTimeFormaterBank(txtDateMadeUpdate.getText());
            String ob = uptadesObservation();
            observation = txaObs.getText() + ob;
//            observation = dataafterUpdate.get("obs").toString().length() > dataBeforeUpdate.get("obs").toString().length()
//                    ? returnSimbolsSomeQtd("--%$&--", 10) + "\n" + returnSimbolsSomeQtd(" ", 5) + "Atualizado em "
//                    + format.dateTimeFormaterField(LocalDateTime.now()) + "\n"
//                    + ob + returnSimbolsSomeQtd("--%$&--", 10)
//                    : observation + "\n" ;

            InsertPeriod();

            fillingPackage();

            originSell = Origin.fromString(cbOrigem.getSelectedItem() + "");

            situation = (Situation) cbSituatiom.getSelectedItem();
            if (cboxPriorizar.isSelected()) {
                //JOptionPane.showMessageDialog(null, "Você selecionou" , "Aviso", JOptionPane.WARNING_MESSAGE);
                priotize = ToPrioritize.YES;
            } else {
                priotize = ToPrioritize.NO;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Favor preencher os seguintes campos\n " + fielsWithout, "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

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

    private void isToUpdateOrInsert(TypeOperation tb) {
        if (tb == TypeOperation.UPDATE) {
            triggerToChage(true);
            txtCPF.requestFocus();
            btnSale.setText("Atualizar Venda");

        } else if (tb == TypeOperation.INSERT) {
            triggerToChage(false);
            txtCPF.requestFocus();
            btnSale.setText("Cadastrar Venda");

        } else {
            JOptionPane.showMessageDialog(null, "Escolha uma opção valida", "Erro", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void setDatasUpdateBeforeOrAfter(Map<String, Object> values) {
        values.put("cpf", txtCPF.getText());
        values.put("custormes", txtCliente.getText());
        values.put("contact", txtContato.getText());
        values.put("package", Packages.fromString(cbPacote.getSelectedItem() + ""));
        values.put("dateInstalation", txtDataInstalacao.getText());
        values.put("period", Period.fromString(cbPeriodo.getSelectedItem() + ""));
        values.put("origin", (Origin.fromString(cbOrigem.getSelectedItem() + "")));
        values.put("situation", situation.fromString(cbSituatiom.getSelectedItem() + ""));
        values.put("obs", txtCliente.getText());
        if (cboxUpdateDateMade.isSelected()) {
            values.put("dateMade", txtDateMadeUpdate.getText());
        } else {
            values.put("dateMade", lblDateMade.getText());

        }
    }

    private void Saling() {
        ldTSaleMade = LocalDateTime.now();
        SalesController sc = new SalesController();
        if (fielWithoutFielling().length() > 0) {
            JOptionPane.showMessageDialog(null, "Favor preencher os seguintes campos\n " + fielWithoutFielling(), "Aviso", JOptionPane.ERROR_MESSAGE);

        } else {
            sc.saveSales(new Sales(
                    // new Seller(1),
                    new Seller(trSell),
                    ldTSaleMade, cpf, cliente,
                    contacts, packgeSelected.toString(),
                    valuePackage, ldTSaleMarked, periodInstalation,
                    originSell, Situation.PROVISIONING, partnerShip, observation, priotize));

            lblQtSellsTable.setText((tblVendasRes.getRowCount() > 9 ? tblVendasRes.getRowCount() : "0" + tblVendasRes.getRowCount()) + " Registros de Vendas");
            int qtdIn = returnSellingsDoToday();
            lblFeitasHoje.setText(qtdIn > 0 ? qtdIn + "" : "Nenhuma");
            cleanFields();
            txtCPF.requestFocus();

        }
    }

    private void update() {
        SalesController sc = new SalesController();

        sc.updateSales(new Sales(id, new Seller(trSell),
                ldTSaleMade, cpf, cliente,
                contacts, packgeSelected.toString(),
                valuePackage, ldTSaleMarked, periodInstalation,
                originSell, situation, partnerShip, observation, priotize));

        sc.fillingsPacksges('m', ldTSaleMarked);

        lblQtSellsTable.setText((tblVendasRes.getRowCount() > 9 ? tblVendasRes.getRowCount() : "0" + tblVendasRes.getRowCount()) + " Registros de Vendas");

        cleanFields();
        txtCPF.requestFocus();
        typePossibilitySellins();
        fillingPacksValuesIntaledToday(returnPositionTable());

    }

    public void restarApplication() {
        dispose();
        new CurrentSales().show();

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
        double packSKY = 0;
        int j = 0;
        float val = 0;
        for (int i = 0; i < tblVendasRes.getRowCount(); i++) {
            Packages pack = Packages.fromString(tblVendasRes.getValueAt(i, position.get("package")) + "");
            Situation situation = Situation.fromString(tblVendasRes.getValueAt(i, position.get("situation")) + "");
            PartnerShip partnerShip = PartnerShip.fromString(tblVendasRes.getValueAt(i, position.get("partnerShip")) + "");
            double valuePerPack = Double.parseDouble(format.formatMoneyNumber(tblVendasRes.getValueAt(i, position.get("value")) + "", 'N'));

            if (situation == Situation.INSTALLED) {
                j++;

                if (partnerShip == PartnerShip.SKY) {
                    packSKY += valuePerPack;
                } else {
                    val += valuePerPack;
                }

            }

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
                        System.out.println("deu errp");
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
        lblCanceladaTot1.setText(format.formatMoneyNumber((packcancell400 + packcancell600 + packcancell700) + "", 'M'));

        panelCanceled8.remove(CurrentSales.lblInstaladaTot1);
        if (packSKY > 0) {
            panelCanceled8.add(lblInstaladaTot1, new AbsoluteConstraints(70, 40, -1, -1));
            lblInstaladaTot1.setFont((new Font("Serif", 1, 24)));
            lblInstaladaTot1.setText(format.formatMoneyNumber((val) + "", 'M')
                    + format.returnQtdSpaces(25) + format.formatMoneyNumber((packSKY) + "", 'M'));

        } else {
            panelCanceled8.add(lblInstaladaTot1, new AbsoluteConstraints(210, 40, -1, -1));
            lblInstaladaTot1.setText(val + " Fibra Instaladas");
            lblInstaladaTot1.setText(format.formatMoneyNumber((val) + "", 'M'));
        }
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
        posicoes.put("tr", findColumnByName("TR Vend."));
        posicoes.put("partnerShip", findColumnByName("Parceira"));
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
        posicoes.put("prioriti", findColumnByName("Priorizar"));

        return posicoes;
    }

    private void cleanFields() {
        txtCPF.setText("");
        txtCliente.setText("");
        txtContato.setText("");
        txaObs.setText("");
        lblValuePlan.setText("");
        paneValue.setVisible(false);
        cbOrigem.setSelectedIndex(0);
        cbPacote.setSelectedIndex(0);
        cbPeriodo.setSelectedIndex(0);
        cbSituatiom.setSelectedIndex(0);
        cpf = null;
        contacts = null;
        cliente = null;
        ldTSaleMarked = null;
        ldTSaleMade = null;
        packgeSelected = null;
        originSell = null;
        trSell = null;
        periodInstalation = null;
        priotize = null;
        observation = null;
        type = TypeOperation.INSERT;
        isToUpdateOrInsert(type);
        txtCPF.requestFocus();
        lblDateMade.setText("");
        cboxPriorizar.setSelected(false);
        txtDataInstalacao.setText(tomorrowDay);
        dataBeforeUpdate.clear();
        dataafterUpdate.clear();
        cboxUpdateDateMade.setVisible(false);

        txtDateMadeUpdate.setVisible(false);
    }

    private String fielWithoutFielling() {
        String fwf = "";
        if (txtCPF.getText().equals("")) {
            fwf += "\"CPF\" ";
        }
        if (txtCliente.getText().equals("")) {
            fwf += "\"Cliente\" ";
        }
//        if (txtTrVendida.getText().equals("")) {
//            fwf += "\" TR \" ";
//        }
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
        if (cbPartnerShip.getSelectedIndex() == 0) {
            fwf += "\"Parceira\" ";
        }
        if (type == TypeOperation.UPDATE) {
            if (cbSituatiom.getSelectedIndex() == 0) {
                fwf += "\"Situação\" ";
            }
        }

        return fwf;

    }

    public void typePossibilitySellins() {
        int cancelled = searchQtdSituationTable(Situation.CANCELED);
        int installed = searchQtdSituationTable(Situation.INSTALLED);
        int made = searchQtdSituationTable(Situation.ALL);

        lblEstimativa.setText("Previsão de:\n " + returnValueSitationMonth(cancelled)
                + " Linhas canceladas\n " + returnValueSitationMonth(installed)
                + " Linhas Instaladas\n " + returnValueSitationMonth(made) + " Feitas");
        lblEstimativa.setFont(new Font(Font.SERIF, 1, 14));
        lblEstimativa.setForeground(Color.blue);

    }

    private static double returnValueSitationMonth(int qtd) {
        LocalDate today = LocalDate.now();
        int salesDaysToToday = getSalesDays(LocalDate.of(today.getYear(), today.getMonth(), 1), today);
        int salesDaysInMonth = getSalesDays(LocalDate.of(today.getYear(), today.getMonth(), 1), today.withDayOfMonth(today.lengthOfMonth()));

        double tot = Math.ceil(((double) qtd / salesDaysToToday) * salesDaysInMonth);
        return tot;
    }

//         private double returnValueSitationMonth(int qtd) {
//
//        Calendar calendar = Calendar.getInstance();
//        int daysToToday = LocalDate.now().getDayOfMonth();
//        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        double tot = Math.ceil(((double) qtd / daysToToday) * daysInMonth);
//        return tot;
//    }
    private static int getSalesDays(LocalDate start, LocalDate end) {
        int workingDays = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                // if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            date = date.plusDays(1);
        }
        return workingDays;
    }

    private void InsertPeriod() throws HeadlessException {

        periodInstalation = (Period) cbPeriodo.getSelectedItem();
        if (periodInstalation == Period.MORNING) {
            ldTSaleMarked = format.dateTimeFormaterBank(txtDataInstalacao.getText() + " 08:00");

        }
        if (periodInstalation == Period.AFTERNOON) {
            ldTSaleMarked = format.dateTimeFormaterBank(txtDataInstalacao.getText() + " 13:00");

        }

    }

    private void fillingPackage() {
        if (cbPacote.getSelectedIndex() == 0) {
            //JOptionPane.showMessageDialog(null, "Selecione um pacote", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            cbPacote.requestFocus();
            lblValuePlan.setText("");
            paneValue.setVisible(false);
        } else {
            packgeSelected = (Packages) cbPacote.getSelectedItem();
            Packages[] pack = {Packages.ALL};
            if (partnerShip.equals(PartnerShip.OI)) {
                valuePackage = SaleService.ValuePerSale(SalesDAO.returnQtdPackgeInstalled(pack, situation.INSTALLED, 'm', partnerShip, LocalDateTime.now()), packgeSelected);

            } else {
                valuePackage = SaleService.ValuePerSaleSKY(packgeSelected);
            }
            lblValuePlan.setText(format.formatMoneyNumber(valuePackage + "", 'M'));
            paneValue.setVisible(true);
            txtDataInstalacao.requestFocus();
        }

    }
//conta as que vao instalar no dia

    public int countPackgeInstalled(JTable tbl, Map<String, Integer> values, char period) {
        int countPack = 0;

        for (int i = 0; i < tbl.getRowCount(); i++) {
            LocalDate dateFormated = format.dateFormaterBank(tbl.getValueAt(i, values.get("dateInstalation")) + "");
            int dayPego = dateFormated.getDayOfMonth();
            int dayToday = LocalDate.now().getDayOfMonth();

            if (dayPego == dayToday) {
                if ((tbl.getValueAt(i, values.get("period"))).toString().charAt(0) == period
                        && (tbl.getValueAt(i, values.get("situation"))).toString().charAt(0) == 'A') {
                    countPack += 1;
                }
            }
        }
        return countPack;
    }

    //conta as instaladas do dia
    public int countPackgeInstalled(JTable tbl, int columnData, int columnSituation) {
        int countPack = 0;

        for (int i = 0; i < tbl.getRowCount(); i++) {
            LocalDate dateFormated = format.dateFormaterBank(tbl.getValueAt(i, returnPositionTable().get("dateInstalation")) + "");
            int dayPego = dateFormated.getDayOfMonth();
            int dayToday = LocalDate.now().getDayOfMonth();
            if (dayPego == dayToday) {
                if ((tbl.getValueAt(i, columnSituation)).toString() == "Instalada") {
                    countPack += 1;
                }
            }
        }

        return countPack;
    }

    public void insertWidthsRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Meus Arquivos\\Minhas Vendas\\Oi Fibra\\Programas Venndas\\VendasFibra\\src\\arquivosTxT\\widthsColumns.txt"))) {
            Map<String, Integer> position = returnPositionTable();
            Map<String, Integer> widths = new HashMap<>();
            position.forEach((k, v) -> {
                try {
                    //widths.put(k, tblVendasRes.getColumnModel().getColumn(v).getWidth());
                    bw.write(k + " : " + tblVendasRes.getColumnModel().getColumn(v).getWidth() + "\n");
                } catch (IOException ex) {
                    Logger.getLogger(CurrentSales.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(CurrentSales.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Map<String, Integer> configureWidthsTableRecorded() {
        Map<String, Integer> widths = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Meus Arquivos\\Minhas Vendas\\Oi Fibra\\Programas Venndas\\VendasFibra\\src\\arquivosTxT\\widthsColumns.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length == 2) {
                    String columnName = parts[0];
                    Integer columnWidth = Integer.parseInt(parts[1]);
                    widths.put(columnName, columnWidth);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CurrentSales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CurrentSales.class.getName()).log(Level.SEVERE, null, ex);
        }

        return widths;
    }

    public void applyWidthsFromFile() {
        Map<String, Integer> widths = configureWidthsTableRecorded();
        Map<String, Integer> position = returnPositionTable();

        position.forEach((columnName, index) -> {
            Integer width = widths.get(columnName);
            if (width != null) {
                tblVendasRes.getColumnModel().getColumn(index).setPreferredWidth(width);
                System.out.println("Aplicada largura: " + width + " para a coluna: " + columnName);
            }
        });
    }
    int qtsss = -14;

    public void applyInsertWidths() {
        tblVendasRes.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
            @Override
            public void columnMarginChanged(ChangeEvent e) {
                insertWidthsRecords();
                qtsss++;
                System.out.println("Você alterou " + qtsss + " vezes");
            }

            @Override
            public void columnAdded(TableColumnModelEvent e) {
            }

            @Override
            public void columnRemoved(TableColumnModelEvent e) {
            }

            @Override
            public void columnMoved(TableColumnModelEvent e) {
            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent e) {
            }
        });
    }

    private int fillingPacksValuesWillIntalationToday(Map<String, Integer> values) {
        int instMorning = countPackgeInstalled(tblVendasRes, values, 'M');
        int instAfternoon = countPackgeInstalled(tblVendasRes, values, 'T');
        int instTot = countPackgeInstalled(tblVendasRes, values.get("dateInstalation"), values.get("situation"));

        int tot = instAfternoon + instMorning;
        if (tot > 0 || countPackgeInstalled(tblVendasRes, values.get("dateInstalation"), values.get("situation")) > 0) {
            jPanel3.setVisible(true);
            jPanel4.setVisible(true);
            jPanel7.setVisible(true);
            jPanel8.setVisible(true);
            lblInstaMorning1.setText(instMorning + "");
            lblInstaAfternoon1.setText(instAfternoon + "");
            lblInstaAfternoon1.setText(instAfternoon + "");
        } else {
            jPanel3.setVisible(false);
            jPanel4.setVisible(false);
            jPanel7.setVisible(false);
            jPanel8.setVisible(false);
        }
        return tot;
    }

    private void fillingPacksValuesIntaledToday(Map<String, Integer> values) {
        int instTot = countPackgeInstalled(tblVendasRes, values.get("dateInstalation"), values.get("situation"));

        lblInstadasToday.setText(instTot + "");

    }

    public void actionMouseRight() {
        tblVendasRes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Map<String, Integer> values = returnPositionTable();
                    tblVendasRes.setComponentPopupMenu(null);
                    severelSales.clear();
                    periodInstalation = null;
// Obter a linha e a coluna na qual o usuário clicou
                    row = tblVendasRes.rowAtPoint(e.getPoint());
                    int column = tblVendasRes.columnAtPoint(e.getPoint());

//                    // Selecionar a célula como se o botão esquerdo tivesse sido pressionado
//                    tblVendasRes.changeSelection(row, column, false, false);
                    if (JTablesFormatting.allLinesSelected > 1) {
                        String nums = "";
                        for (int i = 0; i < JTablesFormatting.positionss.length; i++) {
                            nums += "CPF: " + tblVendasRes.getValueAt(JTablesFormatting.positionss[i], values.get("cpf")) + " ";
                        }
                        optionSituationMouseRight(nums);

                    } else {

                        functionMouseRightJustOneLine();
                    }

                }
            }

        });

    }

    public void fillingToSeveralUpdates(String situ) {
        Map<String, Integer> values = returnPositionTable();
        for (int i = 0; i < JTablesFormatting.positionss.length; i++) {
            int id = Integer.parseInt(tblVendasRes.getValueAt(JTablesFormatting.positionss[i], 0) + "");

            periodInstalation = Period.fromString(tblVendasRes.getValueAt(row, values.get("period")) + "");
            if (periodInstalation == Period.MORNING) {
                ldTSaleMarked = format.dateTimeFormaterBank(tblVendasRes.getValueAt(row, values.get("dateInstalation")) + " 08:00");
            }
            if (periodInstalation == Period.AFTERNOON) {
                ldTSaleMarked = format.dateTimeFormaterBank(tblVendasRes.getValueAt(row, values.get("dateInstalation")) + " 13:00");

            }
            System.out.println("Datas timmmes: " + ldTSaleMarked);
            severelSales.add(new Sales(id,
                    Situation.fromString(situ),
                    tblVendasRes.getValueAt(row, values.get("obs")) + "",
                    ldTSaleMarked
            ));
            System.out.println("Tamanho do negoci: " + severelSales.size());
        }
    }

    public void optionSituationMouseRight(String nums) {
        JMenu menu = new JMenu("Mudar para");
        JPopupMenu pop = new JPopupMenu();
        JMenuItem aprovisionamentoItem = new JMenuItem("Aprovisionamento");
        JMenuItem instaladaItem = new JMenuItem("Instalada");
        JMenuItem canceladaItem = new JMenuItem("Cancelada");

        aprovisionamentoItem.addActionListener(functionMenusMouseRight(aprovisionamentoItem.getText(), nums));
        instaladaItem.addActionListener(functionMenusMouseRight(instaladaItem.getText(), nums));
        canceladaItem.addActionListener(functionMenusMouseRight(canceladaItem.getText(), nums));
        menu.add(aprovisionamentoItem);
        menu.add(instaladaItem);
        menu.add(canceladaItem);
        pop.add(menu);

        tblVendasRes.setComponentPopupMenu(pop);

//                        int dialogResult = JOptionPane.showConfirmDialog(null, "LINHASselecionadas : " + JTablesFormatting.allLinesSelected
//                                + "\nLinhas: " + nums, "Venda " + (row + 1) + " Linhas: ", JOptionPane.YES_NO_OPTION);
    }

    public ActionListener functionMenusMouseRight(String value, String nums) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja alterar as vendas "
                        + nums + " para  " + value, "Alterar Vendas ", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    fillingToSeveralUpdates(e.getActionCommand());
                    sc.updateSeveralSales(severelSales);
                    sc.fillingsPacksges('m', ldTSaleMarked);

                    lblQtSellsTable.setText((tblVendasRes.getRowCount() > 9 ? tblVendasRes.getRowCount() : "0" + tblVendasRes.getRowCount()) + " Registros de Vendas");

                    txtCPF.requestFocus();
                    typePossibilitySellins();
                    fillingPacksValuesIntaledToday(returnPositionTable());
                    sc.returnData('m', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());

                }
            }
        };

    }

    public void functionMouseRightJustOneLine() throws NumberFormatException {
        Map<String, Integer> values = returnPositionTable();
        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja alterar a venda: "
                + "\n Feita em: " + tblVendasRes.getValueAt(row, values.get("dateMade")) + "\n "
                + "Instalação marcada para " + tblVendasRes.getValueAt(row, values.get("dateInstalation"))
                + " De " + tblVendasRes.getValueAt(row, values.get("period")), "Venda " + (row + 1) + " CPF: " + tblVendasRes.getValueAt(row, values.get("cpf")), JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {

            type = TypeOperation.UPDATE;
            isToUpdateOrInsert(type);
            partnerShip = PartnerShip.fromString(tblVendasRes.getValueAt(row, values.get("partnerShip")) + "");
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
            lblDateMade.setText(tblVendasRes.getValueAt(row, values.get("dateMade")) + "");
            priotize = priotize.fromString(tblVendasRes.getValueAt(row, values.get("prioriti")) + "");
            txtDateMadeUpdate.setText(lblDateMade.getText());
            cbTR.setSelectedItem(tblVendasRes.getValueAt(row, values.get("tr")) + "");
            cbPartnerShip.setSelectedItem(partnerShip);
            if (priotize == priotize.YES) {
                cboxPriorizar.setSelected(true);
            }
            setDatasUpdateBeforeOrAfter(dataBeforeUpdate);
            cboxUpdateDateMade.setVisible(true);
            txtDateMadeUpdate.setVisible(false);
        }
    }

    private void searchInstation() {
        searchInstaltionToday = !searchInstaltionToday;
        if (searchInstaltionToday) {
            btnSearchIntaltion.setText("V M");
            btnSearchIntaltion.setToolTipText("Buscar todas vendas do mes");
            sc.returnData('d', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());
        } else {

            btnSearchIntaltion.setText("B I H");
            btnSearchIntaltion.setToolTipText("Buscar Instalações de Hoje");
            sc.returnData('m', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());
        }
    }

    private void insertOrigin() throws HeadlessException {

        originSell = (Origin) cbOrigem.getSelectedItem();
        txaObs.requestFocus();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        panelCanceled12 = new javax.swing.JPanel();
        lblAprovisionamentoTot = new javax.swing.JLabel();
        lblAprovisionamentoTot1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        panelCanceled13 = new javax.swing.JPanel();
        lblCanceladaTot1 = new javax.swing.JLabel();
        lblCanceladaTot = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        panelCanceled8 = new javax.swing.JPanel();
        lblInstaladaTot = new javax.swing.JLabel();
        lblInstaladaTot1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtCliente = new javax.swing.JTextField();
        txtCPF = new javax.swing.JTextField();
        cbPeriodo = new javax.swing.JComboBox<>();
        txtContato = new javax.swing.JTextField();
        cbOrigem = new javax.swing.JComboBox<>();
        txtDataInstalacao = new javax.swing.JTextField();
        cbPacote = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        btnSale = new javax.swing.JButton();
        cbSituatiom = new javax.swing.JComboBox<>();
        lblDateMade = new javax.swing.JLabel();
        cboxPriorizar = new javax.swing.JCheckBox();
        cbPartnerShip = new javax.swing.JComboBox<>();
        btnCancell = new javax.swing.JButton();
        cboxUpdateDateMade = new javax.swing.JCheckBox();
        txtDateMadeUpdate = new javax.swing.JTextField();
        cbTR = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        panelCanceled4 = new javax.swing.JPanel();
        lblCancelada700 = new javax.swing.JLabel();
        panelCanceled5 = new javax.swing.JPanel();
        lblInstalada700 = new javax.swing.JLabel();
        panelCanceled10 = new javax.swing.JPanel();
        lblAprovisionamento700 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        panelCanceled1 = new javax.swing.JPanel();
        lblCancelada400 = new javax.swing.JLabel();
        panelCanceled7 = new javax.swing.JPanel();
        lblInstalada400 = new javax.swing.JLabel();
        panelCanceled9 = new javax.swing.JPanel();
        lblAprovisionamento400 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        panelCanceled3 = new javax.swing.JPanel();
        lblCancelada600 = new javax.swing.JLabel();
        panelCanceled6 = new javax.swing.JPanel();
        lblInstalada600 = new javax.swing.JLabel();
        panelCanceled11 = new javax.swing.JPanel();
        lblAprovisionamento600 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblInstadasToday = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblInstaMor = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblInstaMorning1 = new javax.swing.JLabel();
        lblInstaAfternoon1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblEstimativa = new javax.swing.JTextArea();
        btnSearchIntaltion = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblFeitasHoje = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVendasRes = new javax.swing.JTable();
        lblQtSellsTable = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnSetVisible = new javax.swing.JButton();
        paneValue = new javax.swing.JPanel();
        lblValuePlan = new javax.swing.JLabel();
        cbSeachSelingsBy = new javax.swing.JComboBox<>();
        lblSalesWithoutInstalation = new javax.swing.JLabel();
        lblSalesWithoutInstalationTimeUpdate = new javax.swing.JLabel();
        lblAccessQtd = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vendas Fibra");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Comissão", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255)), "Aprovisionamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N

        panelCanceled12.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled12.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAprovisionamentoTot.setFont(new java.awt.Font("Serif", 1, 22)); // NOI18N
        lblAprovisionamentoTot.setForeground(java.awt.Color.decode("#9C5700"));
        lblAprovisionamentoTot.setText("Aprovisionamento");
        panelCanceled12.add(lblAprovisionamentoTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lblAprovisionamentoTot1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        lblAprovisionamentoTot1.setForeground(java.awt.Color.decode("#9C5700"));
        lblAprovisionamentoTot1.setText("Aprovisionamento");
        panelCanceled12.add(lblAprovisionamentoTot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCanceled12, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCanceled12, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 260, 100));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255)), "Cancelada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N

        panelCanceled13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCanceladaTot1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        lblCanceladaTot1.setForeground(java.awt.Color.decode("#9C0006"));
        lblCanceladaTot1.setText("Cancelada");
        panelCanceled13.add(lblCanceladaTot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        lblCanceladaTot.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        lblCanceladaTot.setForeground(java.awt.Color.decode("#9C0006"));
        lblCanceladaTot.setText("Cancelada");
        panelCanceled13.add(lblCanceladaTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(panelCanceled13, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCanceled13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 240, 100));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255)), "Instalada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N

        panelCanceled8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstaladaTot.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        lblInstaladaTot.setForeground(java.awt.Color.decode("#006100"));
        lblInstaladaTot.setText("Instaladas");
        panelCanceled8.add(lblInstaladaTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, -1, -1));

        lblInstaladaTot1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        lblInstaladaTot1.setForeground(java.awt.Color.decode("#006100"));
        lblInstaladaTot1.setText("Instaladas");
        panelCanceled8.add(lblInstaladaTot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(panelCanceled8, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(panelCanceled8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 520, 100));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 560, 250));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Cadastrar Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(204, 0, 204))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteKeyPressed(evt);
            }
        });
        jPanel6.add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 190, 40));

        txtCPF.setBackground(new java.awt.Color(255, 255, 255));
        txtCPF.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "CPF/CNPJ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
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
        txtContato.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Contato", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtContato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContatoKeyPressed(evt);
            }
        });
        jPanel6.add(txtContato, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 130, 40));

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
        jPanel6.add(cbOrigem, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 40, 110, 40));

        txtDataInstalacao.setBackground(new java.awt.Color(255, 255, 255));
        txtDataInstalacao.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data de Instalação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
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
        txaObs.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Observação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txaObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaObsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaObs);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 730, 60));

        btnSale.setBackground(new java.awt.Color(0, 204, 0));
        btnSale.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnSale.setForeground(new java.awt.Color(255, 255, 255));
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
        jPanel6.add(cbSituatiom, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 90, 110, 40));

        lblDateMade.setBackground(new java.awt.Color(255, 51, 51));
        lblDateMade.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblDateMade.setText("jLabel2");
        jPanel6.add(lblDateMade, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 15, 220, 20));

        cboxPriorizar.setText("Priorizar Venda");
        cboxPriorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxPriorizarActionPerformed(evt);
            }
        });
        jPanel6.add(cboxPriorizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 94, -1, 30));

        cbPartnerShip.setBackground(new java.awt.Color(255, 255, 255));
        cbPartnerShip.setBorder(javax.swing.BorderFactory.createTitledBorder("Parceira"));
        cbPartnerShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPartnerShipActionPerformed(evt);
            }
        });
        cbPartnerShip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPartnerShipKeyPressed(evt);
            }
        });
        jPanel6.add(cbPartnerShip, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 110, 40));

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

        cboxUpdateDateMade.setText("Alterar Data Feita?");
        cboxUpdateDateMade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxUpdateDateMadeActionPerformed(evt);
            }
        });
        jPanel6.add(cboxUpdateDateMade, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 15, -1, 20));

        txtDateMadeUpdate.setBackground(new java.awt.Color(255, 255, 255));
        txtDateMadeUpdate.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data de Instalação", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtDateMadeUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDateMadeUpdateKeyPressed(evt);
            }
        });
        jPanel6.add(txtDateMadeUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 140, 40));

        cbTR.setBackground(new java.awt.Color(255, 255, 255));
        cbTR.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "TR Vendida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        cbTR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTRActionPerformed(evt);
            }
        });
        cbTR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTRKeyPressed(evt);
            }
        });
        jPanel6.add(cbTR, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 40, 110, 40));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 1270, 150));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Internet Vendidas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft YaHei", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255)), "700MB / 1Gb", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCanceled4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelada700.setFont(new java.awt.Font("Footlight MT Light", 1, 18)); // NOI18N
        lblCancelada700.setForeground(java.awt.Color.decode("#9C0006"));
        lblCancelada700.setText("Cancelada");
        panelCanceled4.add(lblCancelada700, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel15.add(panelCanceled4, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 105, 210, 50));

        panelCanceled5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstalada700.setFont(new java.awt.Font("Footlight MT Light", 1, 18)); // NOI18N
        lblInstalada700.setForeground(java.awt.Color.decode("#006100"));
        lblInstalada700.setText("Instaladas");
        panelCanceled5.add(lblInstalada700, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel15.add(panelCanceled5, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 60, 210, 50));

        panelCanceled10.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled10.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAprovisionamento700.setFont(new java.awt.Font("Footlight MT Light", 1, 16)); // NOI18N
        lblAprovisionamento700.setForeground(java.awt.Color.decode("#9C5700"));
        lblAprovisionamento700.setText("Aprovisionamento");
        panelCanceled10.add(lblAprovisionamento700, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel15.add(panelCanceled10, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 18, 210, 50));

        jPanel10.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 220, 160));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 0), new java.awt.Color(255, 0, 0), java.awt.Color.red, java.awt.Color.lightGray), "400MB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCanceled1.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelada400.setFont(new java.awt.Font("Footlight MT Light", 1, 18)); // NOI18N
        lblCancelada400.setForeground(java.awt.Color.decode("#9C0006"));
        lblCancelada400.setText("Cancelada");
        panelCanceled1.add(lblCancelada400, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel16.add(panelCanceled1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 105, 206, 50));

        panelCanceled7.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled7.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstalada400.setFont(new java.awt.Font("Footlight MT Light", 1, 18)); // NOI18N
        lblInstalada400.setForeground(java.awt.Color.decode("#006100"));
        lblInstalada400.setText("Instaladas");
        panelCanceled7.add(lblInstalada400, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel16.add(panelCanceled7, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 60, 206, 50));

        panelCanceled9.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled9.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAprovisionamento400.setFont(new java.awt.Font("Footlight MT Light", 1, 16)); // NOI18N
        lblAprovisionamento400.setForeground(java.awt.Color.decode("#9C5700"));
        lblAprovisionamento400.setText("Aprovisionamento");
        panelCanceled9.add(lblAprovisionamento400, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel16.add(panelCanceled9, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 18, 206, 45));

        jPanel10.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 220, 160));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255), new java.awt.Color(204, 0, 255)), "500MB / 600MB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCanceled3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelada600.setFont(new java.awt.Font("Footlight MT Light", 1, 18)); // NOI18N
        lblCancelada600.setForeground(java.awt.Color.decode("#9C0006"));
        lblCancelada600.setText("Cancelada");
        panelCanceled3.add(lblCancelada600, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jPanel17.add(panelCanceled3, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 113, 216, 40));

        panelCanceled6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstalada600.setFont(new java.awt.Font("Footlight MT Light", 1, 18)); // NOI18N
        lblInstalada600.setForeground(java.awt.Color.decode("#006100"));
        lblInstalada600.setText("Instaladas");
        panelCanceled6.add(lblInstalada600, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jPanel17.add(panelCanceled6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 64, 216, 50));

        panelCanceled11.setMaximumSize(new java.awt.Dimension(34, 34));
        panelCanceled11.setMinimumSize(new java.awt.Dimension(0, 0));
        panelCanceled11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAprovisionamento600.setFont(new java.awt.Font("Footlight MT Light", 1, 16)); // NOI18N
        lblAprovisionamento600.setForeground(java.awt.Color.decode("#9C5700"));
        lblAprovisionamento600.setText("Aprovisionamento");
        panelCanceled11.add(lblAprovisionamento600, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel17.add(panelCanceled11, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 18, 216, 50));

        jPanel10.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 230, 160));

        jPanel3.setBackground(new java.awt.Color(0, 0, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("jLabel1");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(" Instaladas Hoje");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 30));

        jPanel10.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 110, 30));

        jPanel7.setBackground(new java.awt.Color(102, 204, 0));
        jPanel7.setForeground(new java.awt.Color(255, 0, 0));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstadasToday.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        lblInstadasToday.setForeground(new java.awt.Color(0, 0, 0));
        lblInstadasToday.setText("a");
        jPanel7.add(lblInstadasToday, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jPanel10.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 110, 30));

        jPanel4.setBackground(new java.awt.Color(255, 153, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("jLabel1");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, -1, -1));

        lblInstaMor.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        lblInstaMor.setForeground(new java.awt.Color(0, 0, 0));
        lblInstaMor.setText("Manhã");
        jPanel4.add(lblInstaMor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Instalar Hoje");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Tarde");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jPanel10.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 170, 30));

        jPanel8.setBackground(new java.awt.Color(102, 204, 0));
        jPanel8.setForeground(new java.awt.Color(255, 0, 0));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstaMorning1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        lblInstaMorning1.setForeground(new java.awt.Color(0, 0, 0));
        lblInstaMorning1.setText("Manhã");
        jPanel8.add(lblInstaMorning1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblInstaAfternoon1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        lblInstaAfternoon1.setForeground(new java.awt.Color(0, 0, 0));
        lblInstaAfternoon1.setText("Tarde");
        jPanel8.add(lblInstaAfternoon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jPanel10.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 170, 30));

        lblEstimativa.setEditable(false);
        lblEstimativa.setColumns(20);
        lblEstimativa.setRows(5);
        lblEstimativa.setToolTipText("clique aqui");
        jScrollPane3.setViewportView(lblEstimativa);

        jPanel10.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 210, 100));

        btnSearchIntaltion.setBackground(new java.awt.Color(153, 0, 153));
        btnSearchIntaltion.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchIntaltion.setText("B I H");
        btnSearchIntaltion.setToolTipText("Buscar Instalações de Hoje");
        btnSearchIntaltion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchIntaltionActionPerformed(evt);
            }
        });
        jPanel10.add(btnSearchIntaltion, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 60, 60));

        jPanel5.setBackground(new java.awt.Color(153, 0, 153));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("jLabel1");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Feitas Hoje");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel10.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 110, 30));

        jPanel9.setBackground(new java.awt.Color(102, 204, 0));
        jPanel9.setForeground(new java.awt.Color(255, 0, 0));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel9MouseEntered(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFeitasHoje.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        lblFeitasHoje.setForeground(new java.awt.Color(0, 0, 0));
        lblFeitasHoje.setText("a");
        jPanel9.add(lblFeitasHoje, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 20, -1));

        jPanel10.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 110, 30));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 720, 250));

        tblVendasRes.setBackground(new java.awt.Color(255, 255, 255));
        tblVendasRes.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblVendasRes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TR Vend.", "Parceira", "Data Criação", "CPF/CNPJ", "Cliente", "Contato", "Pacote", "Valor", "Data Instalação", "Periodo", "Origem", "Situação", "Observação", "Priorizar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true, true, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVendasRes.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tblVendasResMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblVendasResMouseMoved(evt);
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
            tblVendasRes.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblVendasRes.getColumnModel().getColumn(8).setMinWidth(0);
            tblVendasRes.getColumnModel().getColumn(8).setPreferredWidth(80);
            tblVendasRes.getColumnModel().getColumn(8).setMaxWidth(80);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 1280, 160));

        lblQtSellsTable.setText("jLabel1");
        jPanel1.add(lblQtSellsTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 230, -1));

        jButton1.setText("Salvar dadosda planilha no banco");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 470, -1, -1));

        btnSetVisible.setBackground(new java.awt.Color(255, 255, 255));
        btnSetVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsImages/eyeOpen.png"))); // NOI18N
        btnSetVisible.setBorder(null);
        btnSetVisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetVisibleActionPerformed(evt);
            }
        });
        jPanel1.add(btnSetVisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 140, 40, 30));

        paneValue.setMaximumSize(new java.awt.Dimension(34, 34));
        paneValue.setMinimumSize(new java.awt.Dimension(0, 0));
        paneValue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblValuePlan.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 24)); // NOI18N
        lblValuePlan.setForeground(java.awt.Color.decode("#006100"));
        lblValuePlan.setText("Instaladas");
        paneValue.add(lblValuePlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        jPanel1.add(paneValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 270, 190, 40));

        cbSeachSelingsBy.setBackground(new java.awt.Color(255, 255, 255));
        cbSeachSelingsBy.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar vendas"));
        cbSeachSelingsBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSeachSelingsByActionPerformed(evt);
            }
        });
        cbSeachSelingsBy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbSeachSelingsByKeyPressed(evt);
            }
        });
        jPanel1.add(cbSeachSelingsBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 460, 110, 40));

        lblSalesWithoutInstalation.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        lblSalesWithoutInstalation.setForeground(java.awt.Color.decode("#9C0006"));
        lblSalesWithoutInstalation.setText("Vendas com instalaçoes atrasadas");
        lblSalesWithoutInstalation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalesWithoutInstalationMouseClicked(evt);
            }
        });
        jPanel1.add(lblSalesWithoutInstalation, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 290, -1));

        lblSalesWithoutInstalationTimeUpdate.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        lblSalesWithoutInstalationTimeUpdate.setForeground(java.awt.Color.decode("#9C0006"));
        lblSalesWithoutInstalationTimeUpdate.setText("Atualizado em:");
        lblSalesWithoutInstalationTimeUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalesWithoutInstalationTimeUpdateMouseClicked(evt);
            }
        });
        jPanel1.add(lblSalesWithoutInstalationTimeUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, -1, -1));

        lblAccessQtd.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        lblAccessQtd.setForeground(java.awt.Color.decode("#9C0006"));
        lblAccessQtd.setText("Atualizado em:");
        lblAccessQtd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAccessQtdMouseClicked(evt);
            }
        });
        jPanel1.add(lblAccessQtd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 510, 20));

        jButton2.setText("Salvar dadosdo banco na planilha");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1530, 700));

        jMenu3.setText("Relatorio de Vendas");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu2.setText("Exportar PDF");

        jMenuItem1.setText("Entre Datas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Este Mês");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Mês Passado");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Retirar numeros");
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

        jMenu4.setText("Usuarios");

        jMenuItem4.setText("Cadastrar TR");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


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

//              String[] contacts = txtContato.getText().replace(" ", "").split("/");
//                   JOptionPane.showMessageDialog(null, contacts  , "Aviso", JOptionPane.ERROR_MESSAGE);
//                for (String contact : contacts) {
//                  contatos.add(contact);
//              }
            contacts = txtContato.getText();

            cbPacote.requestFocus();

        }
    }//GEN-LAST:event_txtContatoKeyPressed

    private void cbPacoteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPacoteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {

            fillingPackage();

        }
    }//GEN-LAST:event_cbPacoteKeyPressed


    private void txtDataInstalacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataInstalacaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            // ld = format.dateFormaterBank(txtDataInstalacao.getText());
            cbPeriodo.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("passou");
        }
    }//GEN-LAST:event_txtDataInstalacaoKeyPressed

    private void cbPeriodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPeriodoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            InsertPeriod();
            cbOrigem.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("passou aqui");
        }
    }//GEN-LAST:event_cbPeriodoKeyPressed


    private void cbOrigemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbOrigemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            insertOrigin();

        } else if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {

        }
    }//GEN-LAST:event_cbOrigemKeyPressed


    private void cbPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPacoteActionPerformed
        fillingPackage();   //JOptionPane.showMessageDialog(null,"Pacote escolhido: "+packgeSelected,"Aviso",JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_cbPacoteActionPerformed

    private void btnSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleActionPerformed

        if (type == TypeOperation.INSERT) {
            fillData();
            Saling();
        } else {
            fillDataUpadate();
            play();
            update();
            JTablesFormatting.tableFormatColors(tblVendasRes);
        }
        Thread returnAllUpdateds = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Passou aqui");
                fillingsAllComponentsUpdated();
            } catch (InterruptedException ex) {
                System.out.println("Erro: " + ex);
            }

        });
        returnAllUpdateds.start();

//        returnPacksInstalledsAndValuesLBL();
//        if (searchInstaltionToday) {
//            sc.returnData('m', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());
//            fillingsValuesPacksges();
//            sc.returnData('d', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());
//        } else {
//            sc.returnData('m', (DefaultTableModel) tblVendasRes.getModel(), LocalDate.now(), LocalDate.now());
//            fillingsValuesPacksges();
//        }
    }//GEN-LAST:event_btnSaleActionPerformed

    private void txaObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaObsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {

            observation = txaObs.getText();
            if (type == TypeOperation.INSERT) {
                cbPartnerShip.requestFocus();
            } else {
                cbSituatiom.requestFocus();
            }
        }
    }//GEN-LAST:event_txaObsKeyPressed

    private void btnSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaleKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (type == TypeOperation.INSERT) {
                Saling();
            } else {
                update();
            }
        }
        typePossibilitySellins();
    }//GEN-LAST:event_btnSaleKeyPressed

    private void cbPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPeriodoActionPerformed
        InsertPeriod();
    }//GEN-LAST:event_cbPeriodoActionPerformed

    private void cbOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrigemActionPerformed
        insertOrigin();
    }//GEN-LAST:event_cbOrigemActionPerformed


    private void tblVendasResMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasResMouseClicked

    }//GEN-LAST:event_tblVendasResMouseClicked

    private void cbSituatiomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSituatiomActionPerformed
        situation = (Situation) cbSituatiom.getSelectedItem();
//        if (situation == Situation.CANCELED) {
//            Thread t = playSound("nossa.mp3");
//            t.start();
//        }

    }//GEN-LAST:event_cbSituatiomActionPerformed

    private void cbSituatiomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSituatiomKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            situation = (Situation) cbSituatiom.getSelectedItem();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (type == TypeOperation.INSERT) {
                Saling();
            } else {
                update();
            }
        }
        typePossibilitySellins();

    }//GEN-LAST:event_cbSituatiomKeyPressed

    private void btnCancellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancellActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar a alteração da venda?", "ALERTA!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            isToUpdateOrInsert(TypeOperation.INSERT);
            cleanFields();
        }

    }//GEN-LAST:event_btnCancellActionPerformed

    private void tblVendasResMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasResMousePressed

    }//GEN-LAST:event_tblVendasResMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sc.searchSellsPlanilhaController();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed

    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        OptionsWindow.permissionToOpenWindow("Relatorio de vendas", AllSales.allSalesWindow);

    }//GEN-LAST:event_jMenu3MouseClicked

    private void btnSetVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetVisibleActionPerformed
        fieldsVisible = !fieldsVisible;
        ocultFields(fieldsVisible);
    }//GEN-LAST:event_btnSetVisibleActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        new DateChoose().show();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        asc.toExportPDFController('l', LocalDate.MIN, LocalDate.MIN);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // This month
        asc.toExportPDFController('m', LocalDate.MIN, LocalDate.MIN);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnSearchIntaltionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchIntaltionActionPerformed
        searchInstation();
    }//GEN-LAST:event_btnSearchIntaltionActionPerformed

    private void cbSeachSelingsByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSeachSelingsByActionPerformed

        Situation sit = Situation.fromString(cbSeachSelingsBy.getSelectedItem() + "");
        if (sit != Situation.SELECT) {
            DefaultTableModel dtm = (DefaultTableModel) tblVendasRes.getModel();
            if (cbSeachSelingsBy.getSelectedItem().toString() == "Priorizadas") {
                sc.returnDataByPrioriti(dtm, LocalDate.MIN, LocalDate.MIN);
                clearRdDayProvisioning();
            } else if (sit.equals(Situation.DAY)) {

                int z = 0;

                for (Integer day : sc.returnDataByDay(LocalDateTime.now(), Situation.PROVISIONING)) {
                    JRadioButton radioButton = new JRadioButton("Dia " + day);
                    radioButton.addActionListener(p -> {
                        String dia = radioButton.getText().replaceAll("[^0-9]", "");
                        String month = LocalDate.now().getMonthValue() + "";
                        String year = LocalDate.now().getYear() + "";
                        LocalDate dat = format.dateFormaterBank(
                                (dia.length() > 1 ? dia : "0" + dia) + "/"
                                + (month.length() > 1 ? month : "0" + month) + "/"
                                + year);

                        sc.returnData('d', dtm, dat, dat);
                    });
                    rd.add(radioButton);
                    buttonGroup1.add(radioButton);
                    z++;
                }

                jPanel1.setLayout(null); // Usar layout nulo para posicionamento manual

                int posY = cbSeachSelingsBy.getLocation().y + 10;
                int posX = cbSeachSelingsBy.getLocation().x - 80;

                for (JRadioButton jRadioButton : rd) {
                    jRadioButton.setBounds(posX, posY, 60, 20);
                    jPanel1.add(jRadioButton);
                    System.out.println("posivao: " + jRadioButton.getBounds());
                    posX -= 80; // Atualizar posição y para o próximo botão (+40 pixels de gap)
                }

// Revalidar e repintar o painel para garantir que os componentes sejam exibidos
                jPanel1.revalidate();
                jPanel1.repaint();

            } else if (sit != Situation.ALL) {
                clearRdDayProvisioning();

                sc.returnDataBySituation('m', sit, dtm, LocalDate.MIN, LocalDate.MIN);

            } else {
                clearRdDayProvisioning();
                sc.returnData('m', dtm, LocalDate.MIN, LocalDate.MIN);
                btnSearchIntaltion.setText("B I H");
                btnSearchIntaltion.setToolTipText("Buscar Instalações de Hoje");
                searchInstaltionToday = false;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Favor escolher uma opção adequada", "Erro", JOptionPane.WARNING_MESSAGE);

        }

    }//GEN-LAST:event_cbSeachSelingsByActionPerformed

    public void clearRdDayProvisioning() {
        if (!rd.isEmpty()) {
            for (JRadioButton radioButton : rd) {
                jPanel1.remove(radioButton);
            }
            rd.clear();
            jPanel1.revalidate();
            jPanel1.repaint();
        }
    }

    private void cbSeachSelingsByKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSeachSelingsByKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSeachSelingsByKeyPressed

    private void cboxPriorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxPriorizarActionPerformed

        if (cboxPriorizar.isSelected()) {

            cboxPriorizar.setSelected(false);
            int decicion = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja colocar prioriedade na venda?", "Aviso", JOptionPane.WARNING_MESSAGE);
            if (decicion == 0) {
                priotize = ToPrioritize.YES;
                cboxPriorizar.setSelected(true);

            }
        } else {
            cboxPriorizar.setSelected(true);
            int decicion = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja tirar a prioriedade da venda?", "Aviso", JOptionPane.WARNING_MESSAGE);
            if (decicion == 0) {
                priotize = ToPrioritize.NO;
                cboxPriorizar.setSelected(false);

            }
        }

    }//GEN-LAST:event_cboxPriorizarActionPerformed

    private void cbPartnerShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPartnerShipActionPerformed
        partnerShip = PartnerShip.fromString(cbPartnerShip.getSelectedItem() + "");

        fillingPackage();
    }//GEN-LAST:event_cbPartnerShipActionPerformed

    private void cbPartnerShipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPartnerShipKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            cboxPriorizar.requestFocus();

        }
    }//GEN-LAST:event_cbPartnerShipKeyPressed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

        OptionsWindow.permissionToOpenWindow("Principal de Vendas", RemoveNumbers.removeNumbers);

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        new RemoveNumbers().show();
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        asc.returnData((DefaultTableModel) tblVendasRes.getModel(), LocalDate.now());
    }//GEN-LAST:event_jPanel9MouseClicked

    private void lblSalesWithoutInstalationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalesWithoutInstalationMouseClicked
        sc.returnDataByDelayedInstalations((DefaultTableModel) tblVendasRes.getModel());
    }//GEN-LAST:event_lblSalesWithoutInstalationMouseClicked

    private void lblSalesWithoutInstalationTimeUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalesWithoutInstalationTimeUpdateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalesWithoutInstalationTimeUpdateMouseClicked

    private void lblAccessQtdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAccessQtdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAccessQtdMouseClicked

    private void jPanel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseEntered

    }//GEN-LAST:event_jPanel9MouseEntered

    private void cboxUpdateDateMadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxUpdateDateMadeActionPerformed
        if (cboxUpdateDateMade.isSelected()) {
            txtDateMadeUpdate.setVisible(true);
            txtDateMadeUpdate.requestFocus();
        } else {
            txtDateMadeUpdate.setVisible(false);

        }
    }//GEN-LAST:event_cboxUpdateDateMadeActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new Sellers().setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void tblVendasResMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasResMouseDragged
        JPopupMenu popMneu = new JPopupMenu();
        JMenuItem showObs = new JMenuItem();
    }//GEN-LAST:event_tblVendasResMouseDragged
    int numLine = 0;
    private void tblVendasResMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasResMouseMoved
        int numColum = tblVendasRes.getColumnCount() - 2;
        int row = tblVendasRes.rowAtPoint(evt.getPoint());
        int col = tblVendasRes.columnAtPoint(evt.getPoint());
        if (row != numLine && numColum == col) {

            tblVendasRes.setToolTipText("<html> " + tblVendasRes.getValueAt(row, tblVendasRes.getColumnCount() - 2).toString() + "</html>");
            numLine = tblVendasRes.rowAtPoint(evt.getPoint());

        }
    }//GEN-LAST:event_tblVendasResMouseMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        sc.insertAllorManySellExcel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtDateMadeUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDateMadeUpdateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateMadeUpdateKeyPressed

    private void cbTRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTRActionPerformed
        trSell = cbTR.getSelectedItem() + "";
    }//GEN-LAST:event_cbTRActionPerformed

    private void cbTRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTRKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            trSell = cbTR.getSelectedItem() + "";
            cbOrigem.requestFocus();
        }
    }//GEN-LAST:event_cbTRKeyPressed

    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                OptionsWindow.permissionToOpenWindow("Tela Principal", mainScreen);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancell;
    private javax.swing.JButton btnSale;
    private javax.swing.JToggleButton btnSearchIntaltion;
    private javax.swing.JButton btnSetVisible;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbOrigem;
    private javax.swing.JComboBox<String> cbPacote;
    private javax.swing.JComboBox<String> cbPartnerShip;
    private javax.swing.JComboBox<String> cbPeriodo;
    private javax.swing.JComboBox<String> cbSeachSelingsBy;
    private javax.swing.JComboBox<String> cbSituatiom;
    private javax.swing.JComboBox<String> cbTR;
    private javax.swing.JCheckBox cboxPriorizar;
    private javax.swing.JCheckBox cboxUpdateDateMade;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JLabel lblAccessQtd;
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
    private javax.swing.JLabel lblDateMade;
    private javax.swing.JTextArea lblEstimativa;
    private javax.swing.JLabel lblFeitasHoje;
    private javax.swing.JLabel lblInstaAfternoon1;
    private javax.swing.JLabel lblInstaMor;
    private javax.swing.JLabel lblInstaMorning1;
    private javax.swing.JLabel lblInstadasToday;
    public static javax.swing.JLabel lblInstalada400;
    public static javax.swing.JLabel lblInstalada600;
    public static javax.swing.JLabel lblInstalada700;
    public static javax.swing.JLabel lblInstaladaTot;
    public static javax.swing.JLabel lblInstaladaTot1;
    public static javax.swing.JLabel lblQtSellsTable;
    public static javax.swing.JLabel lblSalesWithoutInstalation;
    public static javax.swing.JLabel lblSalesWithoutInstalationTimeUpdate;
    public static javax.swing.JLabel lblValuePlan;
    private javax.swing.JPanel paneValue;
    private javax.swing.JPanel panelCanceled1;
    private javax.swing.JPanel panelCanceled10;
    private javax.swing.JPanel panelCanceled11;
    private javax.swing.JPanel panelCanceled12;
    private javax.swing.JPanel panelCanceled13;
    private javax.swing.JPanel panelCanceled3;
    private javax.swing.JPanel panelCanceled4;
    private javax.swing.JPanel panelCanceled5;
    private javax.swing.JPanel panelCanceled6;
    private javax.swing.JPanel panelCanceled7;
    public static javax.swing.JPanel panelCanceled8;
    private javax.swing.JPanel panelCanceled9;
    public static javax.swing.JTable tblVendasRes;
    private javax.swing.JTextArea txaObs;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtContato;
    private javax.swing.JTextField txtDataInstalacao;
    private javax.swing.JTextField txtDateMadeUpdate;
    // End of variables declaration//GEN-END:variables

}
