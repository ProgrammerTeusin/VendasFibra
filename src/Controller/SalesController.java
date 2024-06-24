package Controller;

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
import Services.SaleService;
import View.AllSales;
import View.VendasAtual;
import com.mysql.cj.protocol.Resultset;
import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void saveSales(Sales sale) {

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "INSERT INTO tbSales(idSeller,DateMade,customers,contacts,valueSale,"
                + "package,idDateInstalation,origin,observation,cpf,idSituation,priotize) values (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!salesdao.searchDate(sale.getInstallationMarked())) {
                salesdao.insertDateMarked(sale.getInstallationMarked());
            }
            Origin ori = sale.getOrigin() != null ? Origin.fromString(sale.getOrigin() + "") : Origin.CHAT;
//se der erro futuramente, descomente a linha debaixo            
//ps.setInt(1, salesdao.returnIdSeller(sale.getSeller().getTr()));
            sale.setSeller(new Vendedor(salesdao.returnIdSeller(sale.getSeller().getTr())));
            ps.setInt(1, sale.getSeller().getIdentificador());
            ps.setTimestamp(2, Timestamp.valueOf(sale.getSellDateHour()));
            ps.setString(3, sale.getCustomers());
            ps.setString(4, sale.getContact());
            ps.setFloat(5, sale.getValuePackage());
            ps.setString(6, sale.getPackages());
            ps.setInt(7, salesdao.searchDate2(sale.getInstallationMarked()));
            ps.setString(8, sale.getOrigin().name());
            ps.setString(9, sale.getObservation());
            ps.setString(10, sale.getCpf());
            ps.setInt(11, SalesDAO.searchSituation(sale.getSituation().name()));
            ps.setString(12, sale.getPrioritize().name());
            ps.executeUpdate();

            configPriceSellingMonthController(Packages.fromString(sale.getPackages()), sale);
            returnData('m', (DefaultTableModel) VendasAtual.tblVendasRes.getModel(), sale.getInstallationMarked().toLocalDate(), sale.getInstallationMarked().toLocalDate());
            salesSer.insertSellExcel(sale);
            JOptionPane.showMessageDialog(null, "Vendas armazenada com sucesso as " + format.dateTimeFormaterField(sale.getSellDateHour()), "Erro", JOptionPane.ERROR_MESSAGE);

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
            returnData('m', (DefaultTableModel) VendasAtual.tblVendasRes.getModel(), sale.getInstallationMarked().toLocalDate(), sale.getInstallationMarked().toLocalDate());

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
        //DefaultTableModel dtm = (DefaultTableModel) VendasAtual.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == VendasAtual.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            VendasAtual.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    sales.getSeller().getTr(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            //  AllSales.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        }
    }

    public void returnDataBySituation(char type, Situation situ, DefaultTableModel dtm, LocalDate dateTimeInicial, LocalDate dateTimeFinal) {
        List<Sales> data = SalesDAO.returnDataBySituation(type, situ, dateTimeInicial, dateTimeFinal);
        //DefaultTableModel dtm = (DefaultTableModel) VendasAtual.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == VendasAtual.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            VendasAtual.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    sales.getSeller().getTr(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            //  AllSales.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        }
    }

    public void returnDataByPrioriti(DefaultTableModel dtm, LocalDate dateTimeInicial, LocalDate dateTimeFinal) {
        List<Sales> data = SalesDAO.returnDataByPrioriti(dateTimeInicial, dateTimeFinal);
        //DefaultTableModel dtm = (DefaultTableModel) VendasAtual.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == VendasAtual.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            VendasAtual.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    sales.getSeller().getTr(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            //  AllSales.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        }
    }

    public void returnDataByCpfOrName(String search, char cpfOrName, DefaultTableModel dtm) {//c para  cpf n para nome
        List<Sales> data = SalesDAO.returnDataByCPForName(search, cpfOrName);
        //DefaultTableModel dtm = (DefaultTableModel) VendasAtual.tblVendasRes.getModel();
        dtm.setRowCount(0);

        if (dtm == VendasAtual.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            VendasAtual.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    sales.getSeller().getTr(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
        }
    }

    public void fillingsPacksges(char time) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas 
//Alerta de gasto de processamento, melhore no futuro colocando uma unica busca

        Packages[] pack = {Packages.I_400MB};
        int count400Provisioning = SalesDAO.returnQtdPackgeInstalled(pack, Situation.PROVISIONING, time);
        int count400Installed = SalesDAO.returnQtdPackgeInstalled(pack, Situation.INSTALLED, time);
        int count400Canceled = SalesDAO.returnQtdPackgeInstalled(pack, Situation.CANCELED, time);

        int count500600Provisioning = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.PROVISIONING, time);
        int count500600Installed = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.INSTALLED, time);
        int count500600Canceled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.CANCELED, time);

        int count7001GBProvisioning = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.PROVISIONING, time);
        int count7001GBInstalled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.INSTALLED, time);
        int count7001GBCanceled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.CANCELED, time);

        setLabelText(VendasAtual.lblAprovisionamento400, count400Provisioning, " Em Aprovisionamento");
        setLabelText(VendasAtual.lblAprovisionamento600, count500600Provisioning, " Em Aprovisionamento");
        setLabelText(VendasAtual.lblAprovisionamento700, count7001GBProvisioning, " Em Aprovisionamento");

        setLabelText(VendasAtual.lblInstalada400, count400Installed, "  Instaladas");
        setLabelText(VendasAtual.lblInstalada600, count500600Installed, "  Instaladas");
        setLabelText(VendasAtual.lblInstalada700, count7001GBInstalled, "  Instaladas");

        setLabelText(VendasAtual.lblCancelada400, count400Canceled, "  Canceladas");
        setLabelText(VendasAtual.lblCancelada600, count500600Canceled, "  Canceladas");
        setLabelText(VendasAtual.lblCancelada700, count7001GBCanceled, "  Canceladas");

        int totCannncell = (count7001GBCanceled + count500600Canceled + count400Canceled);
        int totProvisig = (count7001GBProvisioning + count500600Provisioning + count400Provisioning);
        int totInstalled = (count7001GBInstalled + count500600Installed + count400Installed);
        setLabelText(VendasAtual.lblAprovisionamentoTot, totProvisig, " Em Apovisionamento");
        setLabelText(VendasAtual.lblInstaladaTot, totInstalled, " Instaladas");
        setLabelText(VendasAtual.lblCanceladaTot, totCannncell, " Canceladas");

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

        for (int i = 0; i < VendasAtual.tblVendasRes.getRowCount(); i++) {
            Packages pack = Packages.fromString(VendasAtual.tblVendasRes.getValueAt(i, position.get("package")) + "");
            Situation situation = Situation.fromString(VendasAtual.tblVendasRes.getValueAt(i, position.get("situation")) + "");

            double valuePerPack = Double.parseDouble(format.formatMoneyNumber(VendasAtual.tblVendasRes.getValueAt(i, position.get("value")) + "", 'N'));

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
        VendasAtual.lblAprovisionamentoTot1.setText(format.formatMoneyNumber((packProvisig400 + packProvisig600 + packProvisig700) + "", 'M'));
        VendasAtual.lblInstaladaTot1.setText(format.formatMoneyNumber((packInstalled400 + packInstalled600 + packInstalled700) + "", 'M'));
        VendasAtual.lblCanceladaTot1.setText(format.formatMoneyNumber((packcancell400 + packcancell600 + packcancell700) + "", 'M'));
        System.out.println("passou Aqui: ");
    }

    public void setLabelText(JLabel label, int count, String message) {
        label.setText((count > 9)
                ? (String.valueOf(count) + message)
                : (count > 0 ? ("0" + count + message)
                        : message.equals(" Em Apovisionamento") ? "Nenhuma" + message : "Nenhuma" + message.substring(0, message.length() - 1)));
    }

    public void updateSales(Sales sale) {
        salesdao.updateSalesDAO(sale);
        salesSer.updateValuesExcel(sale);
        // fillingsPacksges('m'); comentei pois esta  usando  esse metodo  tamem no allSales
        //returnData('m', (DefaultTableModel) VendasAtual.tblVendasRes.getModel(),LocalDate.now(),LocalDate.now());
    }

    public void searchSellsPlanilha() {

        salesSer.searchSellsPlanilhaService();
    }

    public void configPriceSellingMonthController(Packages pack, Sales sales) {
        float valueLast = SalesDAO.returnLastValuePackage(pack);
        int sizeTable = VendasAtual.tblVendasRes.getRowCount();

        int qtdInstalled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.ALL}, Situation.INSTALLED, 'm');
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
            SalesDAO.updateValuesPackageMonthDAO(values2.get(pack.toString()), pack, sales.getInstallationMarked());
           
            ids = SalesDAO.returnIdsPackages(pack, sales.getInstallationMarked());
              salesSer.updateAllValuesExcel(values2.get(pack.toString()), ids);

        }
    }
//    public void saveAllSalesByPackageExcel(Packages pack, Map<String, Float> values, LocalDateTime installationMarked) {
//        if (pack != Packages.I_400MB) {
//            Packages packExtra = pack;
//            pack = Packages.I_500MB;
//            SalesDAO.updateValuesPackageMonthDAO(values.get(pack.toString()), pack.toString(), installationMarked);
//            List<Integer> ids = SalesDAO.returnIdsPackages(packExtra, installationMarked);
//            salesSer.updateAllValuesExcel(values.get(pack.toString()), ids, packExtra);
//
//        } else {
//            SalesDAO.updateValuesPackageMonthDAO(values.get(pack.toString()), pack.toString(), installationMarked);
//
//            List<Integer> ids2 = SalesDAO.returnIdsPackages(pack, installationMarked);
//            salesSer.updateAllValuesExcel(values.get(pack.toString()), ids2, pack);
//
//        }
//    }

    public void searchSellsPlanilhaController() {
        List<Sales> sal = salesSer.searchSellsPlanilhaService();
        for (Sales sales : sal) {
            saveSalesExcel(sales);
        }
        JOptionPane.showMessageDialog(null, "Vendas armazenada com sucesso as ", "Erro", JOptionPane.ERROR_MESSAGE);

    }
}
