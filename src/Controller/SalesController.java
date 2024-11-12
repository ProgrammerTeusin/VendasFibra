package Controller;

import sounds.PlaySound;
import DAO.ConnectionFactory;
import Dao.SalesDAO;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Situation;
import static Model.Enums.Situation.CANCELED;
import static Model.Enums.Situation.INSTALLED;
import static Model.Enums.Situation.PROVISIONING;
import Model.Sales;
import Model.Vendedor;
import Services.FormsTables;
import Services.SaleService;
import View.AllSales;
import View.Loading;
import View.CurrentSales;
import com.mysql.cj.protocol.Resultset;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mathe
 */
public class SalesController {

    Formatting format = new Formatting();
    SalesDAO salesdao = new SalesDAO();
    SaleService salesSer = new SaleService();
    FormsTables ftService = new FormsTables();
    Loading load = new Loading();

    public void saveSales(Sales sale) {

        Thread postSales = new Thread(() -> {
            load.lblMessage.setText("Aguarde! Salvando no banco e atualizando valores");
            load.show();
            salesdao.InsertSales(sale);
            configPriceSellingMonthController(Packages.fromString(sale.getPackages()), sale);

        });
        Thread postSalesExcel = new Thread(() -> {
            try {
                postSales.join();
                load.lblMessage.setText("Aguarde! Salvando no Excel e retornando dados atualizados");
                salesSer.insertSellExcel(sale);
                returnData('m', (DefaultTableModel) CurrentSales.tblVendasRes.getModel(), sale.getInstallationMarked().toLocalDate(), sale.getInstallationMarked().toLocalDate());
                load.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        postSales.start();
        postSalesExcel.start();
    }

    public void updateSales(Sales sale) {

        Thread updateValues = new Thread(() -> {
            load.lblMessage.setText("Aguarde! Salvando no banco e atualizando valores");
            salesdao.updateSalesDAO(sale);
            load.show();
            load.lblMessage.setText("Aguarde! Atualizando valores");
            configPriceSellingMonthController(Packages.fromString(sale.getPackages()), sale);

            System.out.println("passou aqui sem thread");
        });

        Thread saveExcel = new Thread(() -> {
            try {
                updateValues.join();
                load.lblMessage.setText("Aguarde! Salvando no Excel e atualizando valores");
                System.out.println("passou aqui ThreadsaveExcel");
                salesSer.updateValuesExcel(sale);
                load.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        updateValues.start();
        saveExcel.start();
    }

    public void saveSalesExcel(Sales sale) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "INSERT INTO tbSales(idSeller,DateMade,customers,contacts,valueSale,"
                + "package,idDateInstalation,origin,observation,cpf,idSituation) values (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!salesdao.searchDate(sale.getInstallationMarked())) {
                salesdao.insertDateMarked(sale.getInstallationMarked());
            }
            Situation situ;
            if (sale.getSituation() == null) {
                situ = Situation.PROVISIONING;  //situation;

            } else {
                situ = sale.getSituation();
            }
            Origin ori = sale.getOrigin() != null ? Origin.fromString(sale.getOrigin() + "") : Origin.CHAT;
            ps.setInt(1, sale.getSeller().getIdentificador());
            ps.setTimestamp(2, Timestamp.valueOf(sale.getSellDateHour()));
            ps.setString(3, sale.getCustomers());
            ps.setString(4, sale.getContact());
            ps.setFloat(5, sale.getValuePackage());
            ps.setString(6, sale.getPackages());
            ps.setInt(7, salesdao.searchDate2(sale.getInstallationMarked()));
            ps.setString(8, ori.name());
            ps.setString(9, sale.getObservation());
            ps.setString(10, sale.getCpf());
            ps.setInt(11, SalesDAO.searchSituation(situ.name()));
            ps.executeUpdate();
            returnData('m', (DefaultTableModel) CurrentSales.tblVendasRes.getModel(), sale.getInstallationMarked().toLocalDate(), sale.getInstallationMarked().toLocalDate());

            //configPriceSellingMonthController(Packages.fromString(sale.getPackages()));
            // JOptionPane.showMessageDialog(null, "Vendas armazenada com sucesso as " + format.dateTimeFormaterField(sale.getSellDateHour()), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar venda \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void returnData(char type, DefaultTableModel dtm, LocalDate dateTimeInicial, LocalDate dateTimeFinal) {
        List<Sales> data = SalesDAO.returnData(type, dateTimeInicial, dateTimeFinal);
        //DefaultTableModel dtm = (DefaultTableModel) CurrentSales.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == CurrentSales.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = ftService.tableSales(sales);
                dtm.addRow(dados);
            }
            CurrentSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = ftService.tableAllSales(sales);
                dtm.addRow(dados);
            }

            //  AllSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");
        }
    }

    public void returnDataBySituation(char type, Situation situ, DefaultTableModel dtm, LocalDate dateTimeInicial, LocalDate dateTimeFinal) {
        List<Sales> data = SalesDAO.returnDataBySituation(type, situ, dateTimeInicial, dateTimeFinal);
        //DefaultTableModel dtm = (DefaultTableModel) CurrentSales.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == CurrentSales.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = ftService.tableSales(sales);
                dtm.addRow(dados);
            }
            CurrentSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = ftService.tableAllSales(sales);
                dtm.addRow(dados);
            }
            //  AllSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        }
    }
   
    public List<Integer> returnDataByDay(LocalDateTime dateTimeInicial, Situation situ) {
   
        List<Integer> values = new ArrayList<>();
        List<Sales> data = SalesDAO.returnDataByDay(dateTimeInicial ,situ);
        for (Sales sales : data) {
            values.add(sales.getInstallationMarked().getDayOfMonth());
        }
        return values;
    }
    
    public void returnDataByDelayedInstalations(DefaultTableModel dtm) {
        LocalTime time = getTime();

        List<Sales> data = SalesDAO.returnDataByDelayedInstalations(time);
        //DefaultTableModel dtm = (DefaultTableModel) CurrentSales.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == CurrentSales.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = ftService.tableSales(sales);
                dtm.addRow(dados);
            }
            CurrentSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = ftService.tableAllSales(sales);
                dtm.addRow(dados);
            }
            //  AllSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        }
    }

    public LocalTime getTime() {
        LocalTime time = LocalTime.now();
        //if (time.getHour() >= 18) {
        if (time.getHour() >= 18 && time.getMinute() >= 1) {
            time = LocalTime.of(19, 0);
        } else if (time.getHour() >= 12) {
            time = LocalTime.of(13, 0);

        } else {
            time = LocalTime.of(0, 0);
        }
        return time;
    }

    public int qtdSalesDelayedInstalations() throws InterruptedException {
        LocalTime time = getTime();
        return SalesDAO.returnQtdDelayedInstalations(time);
    }
    int numberSalesDelayed = 0;
    int vez = 0;
    PlaySound play = new PlaySound();
    
    private Thread alertThread;
    private Thread tocThread;
    private Thread timeThread;

    public void infinitSearchSalesNoInstalled() {
        iniciarAlertThread();
        TocThreads();
        iniciarTimeThread();
    }

    private Thread iniciarAlertThread() {
       Thread threadAlert = new Thread(() -> {
            try {
                play.run("delayedInstalation.mp3");
            } catch (InterruptedException ex) {
                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       return threadAlert;
    }

    private Thread TocThreads() {
       Thread threadToc = new Thread(() -> {
            try {
                new PlaySound().run("sound.mp3");
            } catch (InterruptedException ex) {
                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       return threadToc;
    }

    private void iniciarTimeThread() {
        if (timeThread == null || !timeThread.isAlive()) {
            timeThread = new Thread(() -> {
                while (true) {
                    try {
                        String hour = LocalTime.now().getHour() > 9 ? LocalTime.now().getHour() + "" : "0" + LocalTime.now().getHour();
                        String minute = LocalTime.now().getMinute() > 9 ? LocalTime.now().getMinute() + "" : "0" + LocalTime.now().getMinute();
                        CurrentSales.lblSalesWithoutInstalationTimeUpdate.setText("Atualizado às " + hour + ":" + minute);

                        int qtd = qtdSalesDelayedInstalations();
                        if (qtd > 0) {
                            if (tocThread != null && !tocThread.isAlive()) {
                                
                                tocThread = TocThreads();
                                tocThread.start();
                            }
                            if (alertThread != null && !alertThread.isAlive()) {
                                alertThread = iniciarAlertThread();
                                alertThread.start();
                            }
                            CurrentSales.lblSalesWithoutInstalation.setText((qtd > 9) ? qtd + " Vendas com instalação atrasada" : "0" + qtd + " Vendas com instalação atrasada");
                        } else {
                            CurrentSales.lblSalesWithoutInstalation.setText("         Tudo em Ordem Por Aqui");
                        }

                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException ex) {
                        System.out.println("Erro: " + ex);
                    }
                }
            });
            timeThread.start();
        } else {
            System.out.println("Thread de tempo já foi iniciada.");
        }
    }

    
    
    public void returnDataByPrioriti(DefaultTableModel dtm, LocalDate dateTimeInicial, LocalDate dateTimeFinal) {
        List<Sales> data = SalesDAO.returnDataByPrioriti(dateTimeInicial, dateTimeFinal);
        //DefaultTableModel dtm = (DefaultTableModel) CurrentSales.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == CurrentSales.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = ftService.tableSales(sales);
                dtm.addRow(dados);
            }
            CurrentSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = ftService.tableAllSales(sales);
                dtm.addRow(dados);
            }
            //  AllSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        }
    }

    public void returnDataByCpfOrName(String search, char cpfOrName, DefaultTableModel dtm) {//c para  cpf n para nome
        List<Sales> data = SalesDAO.returnDataByCPForName(search, cpfOrName);
        //DefaultTableModel dtm = (DefaultTableModel) CurrentSales.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == CurrentSales.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = ftService.tableSales(sales);
                dtm.addRow(dados);
            }
            CurrentSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = ftService.tableAllSales(sales);
                dtm.addRow(dados);
            }
        }
    }

    public void returnDataByWhats(String search, DefaultTableModel dtm) {//c para  cpf n para nome
        List<Sales> data = SalesDAO.returnDataByWhats(search);
        //DefaultTableModel dtm = (DefaultTableModel) CurrentSales.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == CurrentSales.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = ftService.tableSales(sales);
                dtm.addRow(dados);
            }
            CurrentSales.lblQtSellsTable.setText((CurrentSales.tblVendasRes.getRowCount() > 9 ? CurrentSales.tblVendasRes.getRowCount() : "0" + CurrentSales.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = ftService.tableAllSales(sales);
                dtm.addRow(dados);
            }
        }
    }

    public void fillingsPacksges(char time, LocalDateTime dateTime) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas 
//Alerta de gasto de processamento, melhore no futuro colocando uma unica busca

        Packages[] pack = {Packages.I_400MB};
        int count400Provisioning = SalesDAO.returnQtdPackgeInstalled(pack, Situation.PROVISIONING, time, dateTime);
        int count400Installed = SalesDAO.returnQtdPackgeInstalled(pack, Situation.INSTALLED, time, dateTime);
        int count400Canceled = SalesDAO.returnQtdPackgeInstalled(pack, Situation.CANCELED, time, dateTime);

        int count500600Provisioning = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.PROVISIONING, time, dateTime);
        int count500600Installed = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.INSTALLED, time, dateTime);
        int count500600Canceled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.CANCELED, time, dateTime);

        int count7001GBProvisioning = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.PROVISIONING, time, dateTime);
        int count7001GBInstalled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.INSTALLED, time, dateTime);
        int count7001GBCanceled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.CANCELED, time, dateTime);

        setLabelText(CurrentSales.lblAprovisionamento400, count400Provisioning, " Em Aprovisionamento");
        setLabelText(CurrentSales.lblAprovisionamento600, count500600Provisioning, " Em Aprovisionamento");
        setLabelText(CurrentSales.lblAprovisionamento700, count7001GBProvisioning, " Em Aprovisionamento");

        setLabelText(CurrentSales.lblInstalada400, count400Installed, "  Instaladas");
        setLabelText(CurrentSales.lblInstalada600, count500600Installed, "  Instaladas");
        setLabelText(CurrentSales.lblInstalada700, count7001GBInstalled, "  Instaladas");

        setLabelText(CurrentSales.lblCancelada400, count400Canceled, "  Canceladas");
        setLabelText(CurrentSales.lblCancelada600, count500600Canceled, "  Canceladas");
        setLabelText(CurrentSales.lblCancelada700, count7001GBCanceled, "  Canceladas");

        int totCannncell = (count7001GBCanceled + count500600Canceled + count400Canceled);
        int totProvisig = (count7001GBProvisioning + count500600Provisioning + count400Provisioning);
        int totInstalled = (count7001GBInstalled + count500600Installed + count400Installed);
        setLabelText(CurrentSales.lblAprovisionamentoTot, totProvisig, " Em Apovisionamento");
        setLabelText(CurrentSales.lblInstaladaTot, totInstalled, " Instaladas");
        setLabelText(CurrentSales.lblCanceladaTot, totCannncell, " Canceladas");

    }

    public void fillingsValuesPacksges(Map<String, Integer> returnPositionTable) {
        Map<String, Integer> position = returnPositionTable;

        double packProvisig400 = 0;
        double packInstalled400 = 0;
        double packcancell400 = 0;
        double packProvisig600 = 0;
        double packInstalled600 = 0;
        double packcancell600 = 0;
        double packProvisig700 = 0;
        double packInstalled700 = 0;
        double packcancell700 = 0;

        for (int i = 0; i < CurrentSales.tblVendasRes.getRowCount(); i++) {
            Packages pack = Packages.fromString(CurrentSales.tblVendasRes.getValueAt(i, position.get("package")) + "");
            Situation situation = Situation.fromString(CurrentSales.tblVendasRes.getValueAt(i, position.get("situation")) + "");

            double valuePerPack = Double.parseDouble(format.formatMoneyNumber(CurrentSales.tblVendasRes.getValueAt(i, position.get("value")) + "", 'N'));

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
        CurrentSales.lblAprovisionamentoTot1.setText(format.formatMoneyNumber((packProvisig400 + packProvisig600 + packProvisig700) + "", 'M'));
        CurrentSales.lblInstaladaTot1.setText(format.formatMoneyNumber((packInstalled400 + packInstalled600 + packInstalled700) + "", 'M'));
        CurrentSales.lblCanceladaTot1.setText(format.formatMoneyNumber((packcancell400 + packcancell600 + packcancell700) + "", 'M'));
    }

    public void setLabelText(JLabel label, int count, String message) {
        label.setText((count > 9)
                ? (String.valueOf(count) + message)
                : (count > 0 ? ("0" + count + message)
                        : message.equals(" Em Apovisionamento") ? "Nenhuma" + message : "Nenhuma" + message.substring(0, message.length() - 1)));
    }

    public void searchSellsPlanilha() {

        salesSer.searchSellsPlanilhaService();
    }

    public void configPriceSellingMonthController(Packages pack, Sales sales) {
        float valueLast = SalesDAO.returnLastValuePackage(pack);
        int sizeTable = CurrentSales.tblVendasRes.getRowCount();

        int qtdInstalled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.ALL}, Situation.INSTALLED, 'm', sales.getInstallationMarked());
        if (pack != Packages.I_400MB) {
            pack = Packages.I_500MB;
        }
        Map<String, Float> values = SaleService.returnValuesPlanService(qtdInstalled, pack);
        if (valueLast != values.get(pack.toString()) && sizeTable > 1) {

            SalesDAO.updateValuesPackageMonthDAO(values.get(pack.toString()), pack, sales.getInstallationMarked());

            List<Integer> ids = SalesDAO.returnIdsPackages(pack, sales.getInstallationMarked());
            salesSer.updateAllValuesExcel(values.get(pack.toString()), ids);
            ids.clear();

            pack = pack == Packages.I_400MB ? Packages.I_500MB : Packages.I_400MB;
            Map<String, Float> values2 = SaleService.returnValuesPlanService(qtdInstalled, pack);
            SalesDAO.updateValuesPackageMonthDAO(values2.get(pack.toString()),
                    pack,
                    sales.getInstallationMarked());

            ids = SalesDAO.returnIdsPackages(pack, sales.getInstallationMarked());
            salesSer.updateAllValuesExcel(values2.get(pack.toString()), ids);
            System.out.println("passou Aqui: ");

        }
    }


    public void searchSellsPlanilhaController() {
        List<Sales> sal = salesSer.searchSellsPlanilhaService();
        for (Sales sales : sal) {
            saveSalesExcel(sales);
        }
        JOptionPane.showMessageDialog(null, "Vendas armazenada com sucesso as ", "Erro", JOptionPane.ERROR_MESSAGE);

    }
    
    public void insertAllorManySellExcel(){
        List<Sales> data = SalesDAO.returnData('a', LocalDate.now(), LocalDate.now());
        salesSer.insertAllorManySellExcel(data);
    }
}
