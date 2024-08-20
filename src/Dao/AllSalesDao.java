package Dao;

import DAO.ConnectionFactory;
import Model.Enums.MonthsYear;
import Model.Enums.Packages;
import Model.Enums.Situation;
import Model.Sales;
import Services.ToPDF;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AllSalesDao {

    public static int returnQtdPackgeInstalled(Situation situation, LocalDate data, char time) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas - c  de choose de  escolha   de  mes
        PreparedStatement ps = null;
        Connection conection = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            int id = SalesDAO.searchSituation(situation.name());
            String sql = "";
            Timestamp dateInitial;
            Timestamp dateFinal;
            if (time == 'm') {
                dateInitial = Timestamp.valueOf(LocalDateTime.of(data, LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                dateFinal = Timestamp.valueOf(LocalDateTime.of(data, LocalTime.MIN).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));

                sql = "SELECT count(package) as packages FROM tbSales s"
                        + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                        + " WHERE s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ? ";
                ps = conection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setTimestamp(2, dateInitial);
                ps.setTimestamp(3, dateFinal);

            } else {

                sql = "SELECT count(package) as packages FROM tbSales s"
                        + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                        + " WHERE s.idSituation = ?";
                ps = conection.prepareStatement(sql);
                ps.setInt(1, id);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("packages");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao contabilizar pacotes" + ex);
        }
        return 0;
    }

    public static int returnQtdPackgeInstalledByPeriod(Situation situation, LocalDate ldIntial, LocalDate ldFinal) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas - c  de choose de  escolha   de  mes
        PreparedStatement ps = null;
        Connection conection = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            int id = SalesDAO.searchSituation(situation.name());
            String sql = "";
            Timestamp dateInitial;
            Timestamp dateFinal;

            dateInitial = Timestamp.valueOf(ldIntial.atTime(LocalTime.of(0, 0)));
            dateFinal = Timestamp.valueOf(ldFinal.atTime(LocalTime.of(23, 59)));

            sql = "SELECT count(package) as packages FROM tbSales s"
                    + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                    + " WHERE s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ? ";
            ps = conection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setTimestamp(2, dateInitial);
            ps.setTimestamp(3, dateFinal);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("packages");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao contabilizar pacotes" + ex);
        }
        return 0;
    }

    public static float returnValuesPackage(Situation situation, LocalDate data, char time) {
        PreparedStatement ps = null;
        Connection conection = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            int id = SalesDAO.searchSituation(situation.name());
            String sql = "";
            Timestamp dateInitial;
            Timestamp dateFinal;
            if (time == 'm') {
                dateInitial = Timestamp.valueOf(LocalDateTime.of(data, LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                dateFinal = Timestamp.valueOf(LocalDateTime.of(data, LocalTime.MIN).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));

                sql = "SELECT sum(valueSale) as packages FROM tbSales s"
                        + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                        + " WHERE s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ?";
                ps = conection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setTimestamp(2, dateInitial);
                ps.setTimestamp(3, dateFinal);
            } else {
                sql = "SELECT sum(valueSale) as packages FROM tbSales s"
                        + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                        + " WHERE s.idSituation = ?";
                ps = conection.prepareStatement(sql);
                ps.setInt(1, id);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("packages");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao contabilizar pacotes" + ex);
        }
        return 0;
    }

    public static float returnValuesPackageByPeriod(Situation situation, LocalDate ldIntial, LocalDate ldFinal) {
        PreparedStatement ps = null;
        Connection conection = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            int id = SalesDAO.searchSituation(situation.name());
            String sql = "";
            Timestamp dateInitial;
            Timestamp dateFinal;
            dateInitial = Timestamp.valueOf(ldIntial.atTime(LocalTime.of(0, 0)));
            dateFinal = Timestamp.valueOf(ldFinal.atTime(LocalTime.of(23, 59)));

            sql = "SELECT sum(valueSale) as packages FROM tbSales s"
                    + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                    + " WHERE s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ? ";
            ps = conection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setTimestamp(2, dateInitial);
            ps.setTimestamp(3, dateFinal);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("packages");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao contabilizar pacotes" + ex);
        }
        return 0;
    }

  

    public static List<Sales> returnSalesMadeToday(LocalDate dateInitial) {
        PreparedStatement ps = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            Timestamp dateTimeInitial = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(0, 0)));
            Timestamp dateTimeFinal = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(23, 59)));

            String sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                    + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,"
                    + "d.dateIntalation from tbSales s join tbDateInstalation d on "
                    + "s.idDateInstalation = d.id join tbSeller v on "
                    + "s.idSeller = v.id join tbSituation situa on "
                    + "s.idSituation = situa.id "
                    + "where dateMade >= ? AND dateMade <= ? order by s.id desc";

            ps = con.prepareCall(sql);
            ps.setTimestamp(1, dateTimeInitial);
            ps.setTimestamp(2, dateTimeFinal);
            rs = ps.executeQuery();
            return SalesDAO.returnSearch(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }catch (ExceptionInInitializerError e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
        return null;
    }

}
