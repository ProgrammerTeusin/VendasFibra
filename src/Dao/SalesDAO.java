package Dao;

import Controller.Formatting;
import Controller.SalesController;
import DAO.ConnectionFactory;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Enums.ToPrioritize;
import Model.Sales;
import Model.Vendedor;
import com.mysql.cj.MysqlType;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class SalesDAO {

    Formatting format = new Formatting();

    public static List<Sales> returnData(char type, LocalDate dateInitial, LocalDate dateFinal) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        Formatting format = new Formatting();
        Sales sale;
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;
            switch (type) {
                case 'c':

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by s.id desc";
                    ps = con.prepareCall(sql);
                    ps.setDate(1, Date.valueOf(dateInitial + ""));
                    ps.setDate(2, Date.valueOf(dateFinal + ""));
                    rs = ps.executeQuery();
                    break;
                case 'd': //day uniq - dia unico
                    dateTimeInitial = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(0, 0)));
                    dateTimeFinal = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(23, 59)));

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                            + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,"
                            + "d.dateIntalation from tbSales s join tbDateInstalation d on "
                            + "s.idDateInstalation = d.id join tbSeller v on "
                            + "s.idSeller = v.id join tbSituation situa on "
                            + "s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? order by s.id desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    rs = ps.executeQuery();
                    break;
                case 'm':
                    LocalDateTime now = LocalDateTime.now();
                    dateTimeInitial = Timestamp.valueOf(now.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(now.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    // sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by s.DateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    rs = ps.executeQuery();
                    break;
                case 'l':

//                    dateTimeInitial = Timestamp.valueOf(LocalDateTime.of(dateInitial, LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    //                  dateTimeFinal = Timestamp.valueOf(LocalDateTime.of(dateFinal, LocalTime.MIN).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    dateTimeInitial = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    rs = ps.executeQuery();
                    break;
                case 'a':
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id order by s.id desc";
                    ps = con.prepareCall(sql);
                    rs = ps.executeQuery();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erro ao identificar qual pesquisar \n Para buscar todas vendas insira 'a'\n Para buscar vendas do mes insira 'm'", "Erro", JOptionPane.ERROR_MESSAGE);

            }

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            while (rs.next()) {
                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
                Timestamp timestampMa = rs.getTimestamp("s.DateMade");

                dateInstalation = timestampIns.toLocalDateTime();
                dateMade = timestampMa.toLocalDateTime();

                if (dateInstalation.getHour() > 8) {
                    period = Period.AFTERNOON;
                } else {
                    period = Period.MORNING;
                }
                ToPrioritize prioriti = ToPrioritize.valueOf(rs.getString("s.priotize"));
                sales.add(new Sales(
                        rs.getInt("s.id"),
                        new Vendedor(rs.getString("v.tr")),
                        dateMade,
                        rs.getString("s.cpf"),
                        rs.getString("s.customers"),
                        rs.getString("s.contacts"),
                        rs.getString("s.package"),
                        rs.getFloat("s.valueSale"),
                        dateInstalation,
                        period,
                        Origin.valueOf(rs.getString("s.origin")),
                        Situation.valueOf(rs.getString("situa.situation")),
                        rs.getString("s.observation"),
                        prioriti
                ));
           
            }
            return sales;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataBySituation(char type, Situation situation, LocalDate dateInitial, LocalDate dateFinal) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        Formatting format = new Formatting();
        Sales sale;
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;
            switch (type) {
                case 'c':

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,"
                            + "s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND situa.situation = ?"
                            + "order by s.dateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setDate(1, Date.valueOf(dateInitial + ""));
                    ps.setDate(2, Date.valueOf(dateFinal + ""));
                    ps.setString(3, situation.toString());
                    rs = ps.executeQuery();
                    break;
                case 'd': //day uniq - dia unico
                    dateTimeInitial = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(0, 0)));
                    dateTimeFinal = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(23, 59)));

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                            + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,"
                            + "d.dateIntalation from tbSales s join tbDateInstalation d on "
                            + "s.idDateInstalation = d.id join tbSeller v on "
                            + "s.idSeller = v.id join tbSituation situa on "
                            + "s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND situa.situation = ? order by s.dateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    ps.setString(3, situation.name());
                    rs = ps.executeQuery();
                    break;
                case 'm':
                    LocalDateTime now = LocalDateTime.now();
                    dateTimeInitial = Timestamp.valueOf(now.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(now.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    // sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,"
                            + "s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                            + "s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND situa.situation = ?"
                            + "order by s.DateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    ps.setString(3, situation.name());
                    rs = ps.executeQuery();
                    break;
                case 'l':

//                    dateTimeInitial = Timestamp.valueOf(LocalDateTime.of(dateInitial, LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    //                  dateTimeFinal = Timestamp.valueOf(LocalDateTime.of(dateFinal, LocalTime.MIN).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    dateTimeInitial = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                            + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                            + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "order by d.dateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    ps.setString(3, situation.name());
                    rs = ps.executeQuery();
                    break;
                case 'a':
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,"
                            + "s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                            + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where situa.situation = ?"
                            + "order by s.dateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setString(1, situation.name());
                    rs = ps.executeQuery();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erro ao identificar qual pesquisar \n Para buscar todas vendas insira 'a'\n Para buscar vendas do mes insira 'm'", "Erro", JOptionPane.ERROR_MESSAGE);

            }

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            while (rs.next()) {
                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
                Timestamp timestampMa = rs.getTimestamp("s.DateMade");

                dateInstalation = timestampIns.toLocalDateTime();
                dateMade = timestampMa.toLocalDateTime();

                if (dateInstalation.getHour() > 8) {
                    period = Period.AFTERNOON;
                } else {
                    period = Period.MORNING;
                }

                sales.add(new Sales(
                        rs.getInt("s.id"),
                        new Vendedor(rs.getString("v.tr")),
                        dateMade,
                        rs.getString("s.cpf"),
                        rs.getString("s.customers"),
                        rs.getString("s.contacts"),
                        rs.getString("s.package"),
                        rs.getFloat("s.valueSale"),
                        dateInstalation,
                        period,
                        Origin.valueOf(rs.getString("s.origin")),
                        Situation.valueOf(rs.getString("situa.situation")),
                        rs.getString("s.observation"),
                        ToPrioritize.valueOf(rs.getString("s.priotize"))));

            }
            return sales;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
    public static List<Sales> returnDataByPrioriti(LocalDate dateInitial, LocalDate dateFinal) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        Formatting format = new Formatting();
        Sales sale;
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;
           
                    LocalDateTime now = LocalDateTime.now();
                    dateTimeInitial = Timestamp.valueOf(now.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(now.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    // sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,"
                            + "s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                            + "s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND s.priotize = ?"
                            + "order by s.DateMade desc";
                    ps = con.prepareCall(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    ps.setString(3, ToPrioritize.YES.name());
                    rs = ps.executeQuery();
                    
                
            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            while (rs.next()) {
                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
                Timestamp timestampMa = rs.getTimestamp("s.DateMade");

                dateInstalation = timestampIns.toLocalDateTime();
                dateMade = timestampMa.toLocalDateTime();

                if (dateInstalation.getHour() > 8) {
                    period = Period.AFTERNOON;
                } else {
                    period = Period.MORNING;
                }

                sales.add(new Sales(
                        rs.getInt("s.id"),
                        new Vendedor(rs.getString("v.tr")),
                        dateMade,
                        rs.getString("s.cpf"),
                        rs.getString("s.customers"),
                        rs.getString("s.contacts"),
                        rs.getString("s.package"),
                        rs.getFloat("s.valueSale"),
                        dateInstalation,
                        period,
                        Origin.valueOf(rs.getString("s.origin")),
                        Situation.valueOf(rs.getString("situa.situation")),
                        rs.getString("s.observation"),
                        ToPrioritize.valueOf(rs.getString("s.priotize"))));

            }
            return sales;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByCPForName(String search, char cpfOrName) {//c para  cpf n para nome
        PreparedStatement ps = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;
            if (cpfOrName == 'c') {
                sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                        + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                        + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                        + "join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id  "
                        + "where cpf LIKE ? order by d.dateIntalation desc";
            } else {
                sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                        + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                        + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                        + "join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id  "
                        + "where customers LIKE ? order by d.dateIntalation desc";
            }
            ps = con.prepareCall(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
                Timestamp timestampMa = rs.getTimestamp("s.DateMade");

                dateInstalation = timestampIns.toLocalDateTime();
                dateMade = timestampMa.toLocalDateTime();

                if (dateInstalation.getHour() > 8) {
                    period = Period.AFTERNOON;
                } else {
                    period = Period.MORNING;
                }

                sales.add(new Sales(
                        rs.getInt("s.id"),
                        new Vendedor(rs.getString("v.tr")),
                        dateMade,
                        rs.getString("s.cpf"),
                        rs.getString("s.customers"),
                        rs.getString("s.contacts"),
                        rs.getString("s.package"),
                        rs.getFloat("s.valueSale"),
                        dateInstalation,
                        period,
                        Origin.valueOf(rs.getString("s.origin")),
                        Situation.valueOf(rs.getString("situa.situation")),
                        rs.getString("s.observation"),
                        ToPrioritize.valueOf(rs.getString("s.priotize"))));

            }
            return sales;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static int searchSituation(String situation) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id FROM tbSituation WHERE situation = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, situation);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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

        return 0;  // A data não foi encontrada
    }

    public static int returnQtdPackgeInstalled(Packages[] packag, Situation situation, char time) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas
        PreparedStatement ps = null;
        Connection conection = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            int id = searchSituation(situation.name());
            String sql = "";
            if (time == 'm') {
                Timestamp dateInitial = Timestamp.valueOf(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                Timestamp dateFinal = Timestamp.valueOf(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));

                if (packag.length > 1) {
                    //sql = "SELECT count(package) as packages from tbSales where (package = ? or package = ?) and idSituation = ? and ";
                    sql = "SELECT count(package) as packages FROM tbSales s"
                            + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                            + " WHERE (package = ? or package = ?) AND s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ?";
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setString(2, packag[1].toString());
                    ps.setInt(3, id);
                    ps.setTimestamp(4, dateInitial);
                    ps.setTimestamp(5, dateFinal);
                } else if (packag[0] == Packages.ALL) {
                    // sql = "SELECT count(package) as packages from tbSales where idSituation = ?";
                    sql = "SELECT count(package) as packages FROM tbSales s"
                            + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                            + " WHERE s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ?";
                    ps = conection.prepareStatement(sql);
                    ps.setInt(1, id);
                    ps.setTimestamp(2, dateInitial);
                    ps.setTimestamp(3, dateFinal);
                } else {
                    //sql = "SELECT count(package) as packages from tbSales where package = ? and idSituation = ?";
                    sql = "SELECT count(package) as packages FROM tbSales s"
                            + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                            + " WHERE package = ?  AND s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ?";
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setInt(2, id);
                    ps.setTimestamp(3, dateInitial);
                    ps.setTimestamp(4, dateFinal);
                }
            } else {

                if (packag.length > 1) {
                    sql = "SELECT count(package) as packages from tbSales where (package = ? or package = ?) and idSituation = ?";
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setString(2, packag[1].toString());
                    ps.setInt(3, id);
                } else if (packag[0] == Packages.ALL) {
                    sql = "SELECT count(package) as packages from tbSales where idSituation = ?";
                    ps = conection.prepareStatement(sql);
                    ps.setInt(1, id);
                } else {
                    sql = "SELECT count(package) as packages from tbSales where package = ? and idSituation = ?";
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setInt(2, id);
                }
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

    public void insertDateMarked(LocalDateTime date) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlInjection = "INSERT INTO tbDateInstalation(dateIntalation) value (?)";
        try {
            ps = conn.prepareStatement(sqlInjection);
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar data", "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    public boolean searchDate(LocalDateTime date) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id FROM tbDateInstalation WHERE dateIntalation = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(date));
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;  // A data foi encontrada
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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

        return false;  // A data não foi encontrada
    }

    public int searchDate2(LocalDateTime date) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id FROM tbDateInstalation WHERE dateIntalation = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(date));
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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

        return 0;  // A data não foi encontrada
    }

    public int returnIdSeller(String tr) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id FROM tbSeller WHERE tr = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tr);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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

        return 0;  // A data não foi encontrada
    }

    public void updateSalesDAO(Sales sale) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "update tbSales set idSeller =  ?, cpf = ? ,DateMade = ?, customers = ?, "
                + "contacts = ?, package = ?, valueSale = ?, idDateInstalation = ?, idSituation  = ?, "
                + "origin = ?, observation = ?, priotize = ?  where id = ?";

        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!searchDate(sale.getInstallationMarked())) {
                insertDateMarked(sale.getInstallationMarked());
            }
            ps.setInt(1, returnIdSeller(sale.getSeller().getTr()));
            ps.setString(2, sale.getCpf());
            ps.setTimestamp(3, Timestamp.valueOf(sale.getSellDateHour()));
            ps.setString(4, sale.getCustomers());
            ps.setString(5, sale.getContact());
            ps.setString(6, sale.getPackages());
            ps.setFloat(7, sale.getValuePackage());
            ps.setInt(8, searchDate2(sale.getInstallationMarked()));
            ps.setInt(9, SalesDAO.searchSituation(sale.getSituation().name()));
            ps.setString(10, sale.getOrigin().name());
            ps.setString(11, sale.getObservation());
            ps.setString(12, sale.getPrioritize().name());
            ps.setInt(13, sale.getId());
            int rowsAffected = ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Venda alterada com sucesso as " + format.dateTimeFormaterField(sale.getSellDateHour())
                    + "Dados: \n" + sale.getCpf() + "\n" + Timestamp.valueOf(sale.getSellDateHour()) + "\n"
                    + sale.getCustomers() + "\n" + sale.getContact() + "\n" + sale.getPackages() + "\n"
                    + searchDate2(sale.getInstallationMarked()) + "\n"
                    + SalesDAO.searchSituation(sale.getSituation().name()) + "\n"
                    + sale.getOrigin().name() + "\n"
                    + sale.getObservation() + "\n"
                    + sale.getId(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            SalesController sc = new SalesController();
            System.out.println("Pacote escolhido: " + Packages.fromString(sale.getPackages()) + "Valor: R$" + sale.getValuePackage());

            sc.configPriceSellingMonthController(Packages.fromString(sale.getPackages()), sale);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar venda \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
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

    public static void updateValuesPackageMonthDAO(Float value, String pack, LocalDateTime dataMade) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;

        Timestamp dateInitial = Timestamp.valueOf(dataMade.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
        Timestamp dateFinal = Timestamp.valueOf(dataMade.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));

        //System.out.println("Data inicial " + dateInitial + " DInal: " + dateFinal);
        String sqlInjection;

        try {
            conn.prepareStatement("SET SQL_SAFE_UPDATES = 0;");
            if (pack == Packages.I_400MB.toString()) {
                //sqlInjection = "UPDATE tbSales SET valueSale = ? WHERE dateMade >= ? AND dateMade <= ? and package = ?";
                sqlInjection = "UPDATE tbSales s JOIN tbDateInstalation d ON s.idDateInstalation = d.id SET s.valueSale = ? WHERE d.dateIntalation >= ? AND d.dateIntalation <= ? and s.package = ?";

            } else {
                pack = Packages.I_400MB.toString();
                //sqlInjection = "UPDATE tbSales SET valueSale = ? WHERE dateMade >= ? AND dateMade <= ? and package != ?";
                sqlInjection = "UPDATE tbSales s JOIN tbDateInstalation d ON s.idDateInstalation = d.id SET s.valueSale = ? WHERE d.dateIntalation >= ? AND d.dateIntalation <= ? and s.package !=  ?";

            }
            ps = conn.prepareStatement(sqlInjection);
            ps.setFloat(1, value);
            ps.setTimestamp(2, dateInitial);
            ps.setTimestamp(3, dateFinal);
            ps.setString(4, pack);

            int rowsAffected = ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar valores da venda no mes \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);

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

    public static float returnLastValuePackage(Packages packag) {
        PreparedStatement ps = null;
        Connection conection = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            String sql = "";
            if (packag == Packages.I_400MB) {
                sql = "select valueSale from tbSales where package = ? order by DateMade desc  limit 1 ";
                ps = conection.prepareStatement(sql);
                ps.setString(1, packag.toString());
            } else {
                sql = "select valueSale from tbSales where package != ? order by DateMade desc  limit 1 ";
                ps = conection.prepareStatement(sql);
                ps.setString(1, packag.toString());

            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("valueSale");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
}
