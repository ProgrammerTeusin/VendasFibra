package Controller;

import DAO.ConnectionFactory;
import Dao.SalesDAO;
import Model.Enums.Packages;
import Model.Enums.Situation;
import Model.Sales;
import Services.SaleService;
import View.VendasAtual;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public void saveSales(Sales sale) {

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
            ps.setInt(1, 1);
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
            ps.executeUpdate();

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

    public void returnData() {
        List<Sales> data = SalesDAO.returnData();
        DefaultTableModel dtm = (DefaultTableModel) VendasAtual.tblVendasRes.getModel();
        dtm.setRowCount(0);

        for (Sales sales : data) {

            Object[] dados = {
                sales.getId(),
                format.dateTimeFormaterField(sales.getSellDateHour()),
                sales.getCpf(),
                sales.getCustomers(),
                sales.getContact(),
                sales.getPackages(),
                format.formatMoneyNumber(sales.getValuePackage()+"", 'M'),
                format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                sales.getPeriod().toString(),
                sales.getOrigin().toString(),
                sales.getSituation(),
                sales.getObservation()
            };
            dtm.addRow(dados);

        }
    }

    public void fillingsPacksges() {//Alerta de gasto de processamento, melhore no futuro colocando uma unica busca
        Packages[] pack = {Packages.I_400MB};
        int count400Provisioning = SalesDAO.returnQtdPackgeInstalled(pack, Situation.PROVISIONING);
        int count400Installed = SalesDAO.returnQtdPackgeInstalled(pack, Situation.INSTALLED);
        int count400Canceled = SalesDAO.returnQtdPackgeInstalled(pack, Situation.CANCELED);

        int count500600Provisioning = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.PROVISIONING);
        int count500600Installed = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.INSTALLED);
        int count500600Canceled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_500MB, Packages.I_600MB}, Situation.CANCELED);

        int count7001GBProvisioning = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.PROVISIONING);
        int count7001GBInstalled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.INSTALLED);
        int count7001GBCanceled = SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.I_700MB, Packages.I_1GB}, Situation.CANCELED);

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
                    setLabelText(VendasAtual.lblAprovisionamentoTot, totProvisig,  " Em Apovisionamento");
                setLabelText(VendasAtual.lblInstaladaTot, totInstalled, " Instaladas");
                setLabelText(VendasAtual.lblCanceladaTot, totCannncell, " Canceladas");

        
        
    }
    


    public void setLabelText(JLabel label, int count, String message) {
        label.setText((count > 9)
                ? (String.valueOf(count) + message)
                : (count > 0 ? ("0" + count + message)
                        : message.equals(" Em Apovisionamento") ? "Nenhuma" + message : "Nenhuma" + message.substring(0, message.length() - 1)));
    }

    public void updateSales(Sales sale) {
        salesdao.updateSalesDAO(sale);
        fillingsPacksges();
        returnData();
    }
    
    
      public static void searchSellsPlanilha() {
          SaleService.searchSellsPlanilhaService();
    }
}
