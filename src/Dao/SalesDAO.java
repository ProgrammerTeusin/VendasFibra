package Dao;

import Controller.Formatting;
import Controller.SalesController;
import DAO.ConnectFactory;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.PartnerShip;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Enums.ToPrioritize;
import Model.Sales;
import Model.Seller;
import com.mysql.cj.protocol.Resultset;
import java.awt.HeadlessException;
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
import javax.swing.table.DefaultTableModel;

public class SalesDAO {

    Formatting format = new Formatting();

    public static List<Sales> returnData(char type, LocalDate dateInitial, LocalDate dateFinal) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        ResultSet rs = null;

        String sql;
        Formatting format = new Formatting();
        Sales sale;
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;
            switch (type) {
                case 'c':

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.id,v.tr,situa.situation,s.customers,"
                            + "s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                            + "s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? order by s.id desc";
                    ps = con.prepareStatement(sql);
                    ps.setDate(1, Date.valueOf(dateInitial + ""));
                    ps.setDate(2, Date.valueOf(dateFinal + ""));
                    rs = ps.executeQuery();
                    break;
                case 'd': //day uniq - dia unico
                    dateTimeInitial = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(0, 0)));
                    dateTimeFinal = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(23, 59)));

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.id,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                            + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,"
                            + "d.dateIntalation from tbSales s join tbDateInstalation d on "
                            + "s.idDateInstalation = d.id join tbSeller v on "
                            + "s.idSeller = v.id join tbSituation situa on "
                            + "s.idSituation = situa.id "
                            + "join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? order by s.id desc";
                    ps = con.prepareStatement(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    rs = ps.executeQuery();
                    break;
                case 'm':
                    LocalDateTime now = LocalDateTime.now();
                    dateTimeInitial = Timestamp.valueOf(now.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(now.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    // sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.id,v.tr,situa.situation,s.customers,"
                            + "s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                            + "s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "order by s.DateMade desc";
                    ps = con.prepareStatement(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    rs = ps.executeQuery();
                    break;
                case 'l'://l para lastMonth

//                    dateTimeInitial = Timestamp.valueOf(LocalDateTime.of(dateInitial, LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    //                  dateTimeFinal = Timestamp.valueOf(LocalDateTime.of(dateFinal, LocalTime.MIN).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    dateTimeInitial = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                    dateTimeFinal = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
                    sql = "SELECT s.id,v.id,s.priotize,v.tr,situa.situation,s.customers,"
                            + "s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,"
                            + "par.partnerShip,"
                            + "d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "order by d.dateIntalation desc";
                    ps = con.prepareStatement(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    rs = ps.executeQuery();
                    break;
                case 'a':
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,v.id,situa.situation,s.customers,"
                            + "s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                            + "s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id order by s.id desc";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erro ao identificar qual pesquisar \n Para buscar todas vendas insira 'a'\n Para buscar vendas do mes insira 'm'", "Erro", JOptionPane.ERROR_MESSAGE);

            }

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            return returnSearch(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao buscar vendas" + ex.getMessage());
        }

        return null;
    }

    public static List<Sales> returnSearch(ResultSet rs) throws SQLException {
        LocalDateTime dateInstalation;
        LocalDateTime dateMade;
        Period period;
        List<Sales> sales = new ArrayList<>();
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
            ToPrioritize prioriti = rs.getString("s.priotize") == null ? ToPrioritize.NO : ToPrioritize.valueOf(rs.getString("s.priotize"));
            sales.add(new Sales(
                    rs.getInt("s.id"),
                    // new Seller(rs.getInt("v.id") , rs.getString("v.tr")),
                    new Seller(rs.getString("v.tr")),
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
                    PartnerShip.valueOf(rs.getString("par.partnerShip")),
                    rs.getString("s.observation"),
                    prioriti
            ));

        }
        return sales;
    }

    public static List<Sales> returnDataBySituation(char type, Situation situation, LocalDate dateInitial, LocalDate dateFinal) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
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
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,"
                            + "s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + " join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND situa.situation = ?"
                            + "order by s.dateMade desc";
                    ps = con.prepareStatement(sql);
                    ps.setDate(1, Date.valueOf(dateInitial + ""));
                    ps.setDate(2, Date.valueOf(dateFinal + ""));
                    ps.setString(3, situation.toString());
                    rs = ps.executeQuery();
                    break;
                case 'd': //day uniq - dia unico
                    dateTimeInitial = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(0, 0)));
                    dateTimeFinal = Timestamp.valueOf(dateInitial.atTime(LocalTime.of(23, 59)));

                    //   sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                            + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,"
                            + "d.dateIntalation from tbSales s join tbDateInstalation d on "
                            + "s.idDateInstalation = d.id join tbSeller v on "
                            + " join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "s.idSeller = v.id join tbSituation situa on "
                            + "s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND situa.situation = ? order by s.dateMade desc";
                    ps = con.prepareStatement(sql);
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
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.id,v.tr,situa.situation,s.customers,s.origin,"
                            + "s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                            + "s.valueSale,d.dateIntalation from tbSales s "
                            + "join tbDateInstalation d on s.idDateInstalation = d.id "
                            + "join tbSeller v on s.idSeller = v.id "
                            + " join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "AND situa.situation = ? "
                            + "order by s.DateMade desc";
                    ps = con.prepareStatement(sql);
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
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                            + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                            + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                            + " join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                            + "order by d.dateMade desc";
                    ps = con.prepareStatement(sql);
                    ps.setTimestamp(1, dateTimeInitial);
                    ps.setTimestamp(2, dateTimeFinal);
                    ps.setString(3, situation.name());
                    rs = ps.executeQuery();
                    break;
                case 'a':
                    sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,"
                            + "s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                            + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                            + " join tbPartnerShip par on par.id = s.idPartnerShip "
                            + "join tbSeller v on s.idSeller = v.id "
                            + "join tbSituation situa on s.idSituation = situa.id "
                            + "where situa.situation = ?"
                            + "order by s.dateMade desc";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, situation.name());
                    rs = ps.executeQuery();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erro ao identificar qual pesquisar \n Para buscar todas vendas insira 'a'\n Para buscar vendas do mes insira 'm'", "Erro", JOptionPane.ERROR_MESSAGE);

            }

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;
//
//            while (rs.next()) {
//                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
//                Timestamp timestampMa = rs.getTimestamp("s.DateMade");
//
//                dateInstalation = timestampIns.toLocalDateTime();
//                dateMade = timestampMa.toLocalDateTime();
//
//                if (dateInstalation.getHour() > 8) {
//                    period = Period.AFTERNOON;
//                } else {
//                    period = Period.MORNING;
//                }
//
//                sales.add(new Sales(
//                        rs.getInt("s.id"),
//                        new Seller(rs.getString("v.tr")),
//                        dateMade,
//                        rs.getString("s.cpf"),
//                        rs.getString("s.customers"),
//                        rs.getString("s.contacts"),
//                        rs.getString("s.package"),
//                        rs.getFloat("s.valueSale"),
//                        dateInstalation,
//                        period,
//                        Origin.valueOf(rs.getString("s.origin")),
//                        Situation.valueOf(rs.getString("situa.situation")),
//                        rs.getString("s.observation"),
//                        rs.getString("s.priotize") == null ? ToPrioritize.NO : ToPrioritize.valueOf(rs.getString("s.priotize"))));
//
//            }
            return returnSearch(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByPrioriti(LocalDate dateInitial, LocalDate dateFinal) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
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
            sql = "SELECT s.id,par.partnerShip,s.priotize,v.id,v.tr,situa.situation,s.customers,s.origin,"
                    + "s.observation,s.contacts,s.DateMade, s.cpf, s.package, "
                    + "s.valueSale,d.dateIntalation from tbSales s "
                    + "join tbDateInstalation d on s.idDateInstalation = d.id "
                    + " join tbPartnerShip par on par.id = s.idPartnerShip "
                    + "join tbSeller v on s.idSeller = v.id "
                    + "join tbSituation situa on s.idSituation = situa.id "
                    + "where d.dateIntalation >= ? AND d.dateIntalation <= ? "
                    + "AND s.priotize = ?"
                    + "order by s.DateMade desc";
            ps = con.prepareStatement(sql);
            ps.setTimestamp(1, dateTimeInitial);
            ps.setTimestamp(2, dateTimeFinal);
            ps.setString(3, ToPrioritize.YES.name());
            rs = ps.executeQuery();

//            LocalDateTime dateInstalation;
//            LocalDateTime dateMade;
//            Period period;
//
//            while (rs.next()) {
//                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
//                Timestamp timestampMa = rs.getTimestamp("s.DateMade");
//
//                dateInstalation = timestampIns.toLocalDateTime();
//                dateMade = timestampMa.toLocalDateTime();
//
//                if (dateInstalation.getHour() > 8) {
//                    period = Period.AFTERNOON;
//                } else {
//                    period = Period.MORNING;
//                }
//
//                sales.add(new Sales(
//                        rs.getInt("s.id"),
//                        new Seller(rs.getString("v.tr")),
//                        dateMade,
//                        rs.getString("s.cpf"),
//                        rs.getString("s.customers"),
//                        rs.getString("s.contacts"),
//                        rs.getString("s.package"),
//                        rs.getFloat("s.valueSale"),
//                        dateInstalation,
//                        period,
//                        Origin.valueOf(rs.getString("s.origin")),
//                        Situation.valueOf(rs.getString("situa.situation")),
//                        rs.getString("s.observation"),
//                        ToPrioritize.valueOf(rs.getString("s.priotize"))));
//
//            }
            return returnSearch(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByDay(LocalDateTime dateInitial, Situation situ) {//c de chosse - para escolha de datas m- para vendas do mes 'a' para all de todas as vendas . l para lastMonth
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        Formatting format = new Formatting();
        Sales sale;
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;

            LocalDateTime now = LocalDateTime.now();
            dateTimeInitial = Timestamp.valueOf(dateInitial.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
            dateTimeFinal = Timestamp.valueOf(dateInitial.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
            // sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id where d.dateIntalation >= ? AND d.dateIntalation <= ? order by d.dateIntalation desc";
            sql = "select s.id,par.partnerShip, d.dateIntalation, st.situation from tbsales s "
                    + "join tbDateInstalation d on s.idDateInstalation = d.id "
                    + " join tbPartnerShip par on par.id = s.idPartnerShip "
                    + "join tbSituation st on st.id = s.idSituation "
                    + "where d.dateIntalation >= ? and d.dateIntalation <= ?"
                    + "and st.situation = ? group by day(d.dateIntalation) order by d.dateIntalation desc";
            ps = con.prepareStatement(sql);
            ps.setTimestamp(1, dateTimeInitial);
            ps.setTimestamp(2, dateTimeFinal);
            ps.setString(3, situ.name());
            rs = ps.executeQuery();

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            while (rs.next()) {
                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");

                dateInstalation = timestampIns.toLocalDateTime();

                if (dateInstalation.getHour() > 8) {
                    period = Period.AFTERNOON;
                } else {
                    period = Period.MORNING;
                }
                Sales salem = new Sales();
                salem.setInstallationMarked(dateInstalation);
                sales.add(salem);

            }
            return sales;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByCPForName(String search, char cpfOrName) {//c para  cpf n para nome
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
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
                sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                        + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                        + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                        + " join tbPartnerShip par on par.id = s.idPartnerShip "
                        + "join tbSeller v on s.idSeller = v.id "
                        + "join tbSituation situa on s.idSituation = situa.id  "
                        + "where cpf LIKE ? order by d.dateIntalation desc";
            } else {
                sql = "SELECT s.id,par.partnerShip,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                        + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                        + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                        + " join tbPartnerShip par on par.id = s.idPartnerShip "
                        + "join tbSeller v on s.idSeller = v.id "
                        + "join tbSituation situa on s.idSituation = situa.id  "
                        + "where customers LIKE ? order by d.dateIntalation desc";
            }
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();

//            while (rs.next()) {
//                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
//                Timestamp timestampMa = rs.getTimestamp("s.DateMade");
//
//                dateInstalation = timestampIns.toLocalDateTime();
//                dateMade = timestampMa.toLocalDateTime();
//
//                if (dateInstalation.getHour() > 8) {
//                    period = Period.AFTERNOON;
//                } else {
//                    period = Period.MORNING;
//                }
//
//                sales.add(new Sales(
//                        rs.getInt("s.id"),
//                        new Seller(rs.getString("v.tr")),
//                        dateMade,
//                        rs.getString("s.cpf"),
//                        rs.getString("s.customers"),
//                        rs.getString("s.contacts"),
//                        rs.getString("s.package"),
//                        rs.getFloat("s.valueSale"),
//                        dateInstalation,
//                        period,
//                        Origin.valueOf(rs.getString("s.origin")),
//                        Situation.valueOf(rs.getString("situa.situation")),
//                        rs.getString("s.observation"),
//                        rs.getString("s.priotize") == null ? ToPrioritize.NO : ToPrioritize.valueOf(rs.getString("s.priotize"))));
//
//            }
            return returnSearch(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByWhats(String search) {
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            sql = "SELECT s.id,s.priotize,par.partnerShip,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                    + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                    + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                    + " join tbPartnerShip par on par.id = s.idPartnerShip "
                    + "join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id  "
                    + "where contacts LIKE ? order by d.dateIntalation desc";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();

//            while (rs.next()) {
//                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
//                Timestamp timestampMa = rs.getTimestamp("s.DateMade");
//
//                dateInstalation = timestampIns.toLocalDateTime();
//                dateMade = timestampMa.toLocalDateTime();
//
//                if (dateInstalation.getHour() > 8) {
//                    period = Period.AFTERNOON;
//                } else {
//                    period = Period.MORNING;
//                }
//
//                sales.add(new Sales(
//                        rs.getInt("s.id"),
//                        new Seller(rs.getString("v.tr")),
//                        dateMade,
//                        rs.getString("s.cpf"),
//                        rs.getString("s.customers"),
//                        rs.getString("s.contacts"),
//                        rs.getString("s.package"),
//                        rs.getFloat("s.valueSale"),
//                        dateInstalation,
//                        period,
//                        Origin.valueOf(rs.getString("s.origin")),
//                        Situation.valueOf(rs.getString("situa.situation")),
//                        rs.getString("s.observation"),
//                        rs.getString("s.priotize") == null ? ToPrioritize.NO : ToPrioritize.valueOf(rs.getString("s.priotize"))));
//
//            }
            return returnSearch(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByDelayedInstalations(LocalTime time) {//instalações atrasadas
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;

            sql = "SELECT s.id,s.priotize,par.partnerShip,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                    + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                    + "from tbSales s "
                    + "join tbDateInstalation d on s.idDateInstalation = d.id "
                    + " join tbPartnerShip par on par.id = s.idPartnerShip "
                    + "join tbSeller v on s.idSeller = v.id "
                    + "join tbSituation situa on s.idSituation = situa.id  "
                    + "where d.dateIntalation < ? and situa.situation = ? order by d.dateIntalation desc";

            ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atTime(time)));
            ps.setString(2, Situation.PROVISIONING.name());
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
                        new Seller(rs.getString("v.tr")),
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
                        PartnerShip.valueOf(rs.getString("par.partnerShip")),
                        rs.getString("s.observation"),
                        rs.getString("s.priotize") == null ? ToPrioritize.NO : ToPrioritize.valueOf(rs.getString("s.priotize"))));

            }
            return sales;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static List<Sales> returnDataByID(int id, char cpfOrName) {//c para  cpf n para nome
        PreparedStatement ps = null;
        Connection con = ConnectFactory.getConnection();
        ResultSet rs = null;

        String sql;
        List<Sales> sales = new ArrayList<>();
        try {
            Timestamp dateTimeInitial;
            Timestamp dateTimeFinal;

            LocalDateTime dateInstalation;
            LocalDateTime dateMade;
            Period period;
            sql = "SELECT s.id,s.priotize,v.tr,situa.situation,s.customers,s.origin,s.observation,"
                    + "s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation "
                    + "from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id "
                    + "join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id  "
                    + "where s.id = ? order by d.dateIntalation desc";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

//            while (rs.next()) {
//                Timestamp timestampIns = rs.getTimestamp("d.dateIntalation");
//                Timestamp timestampMa = rs.getTimestamp("s.DateMade");
//
//                dateInstalation = timestampIns.toLocalDateTime();
//                dateMade = timestampMa.toLocalDateTime();
//
//                if (dateInstalation.getHour() > 8) {
//                    period = Period.AFTERNOON;
//                } else {
//                    period = Period.MORNING;
//                }
//
//                sales.add(new Sales(
//                        rs.getInt("s.id"),
//                        new Seller(rs.getString("v.tr")),
//                        dateMade,
//                        rs.getString("s.cpf"),
//                        rs.getString("s.customers"),
//                        rs.getString("s.contacts"),
//                        rs.getString("s.package"),
//                        rs.getFloat("s.valueSale"),
//                        dateInstalation,
//                        period,
//                        Origin.valueOf(rs.getString("s.origin")),
//                        Situation.valueOf(rs.getString("situa.situation")),
//                        rs.getString("s.observation"),
//                        rs.getString("s.priotize") == null ? ToPrioritize.NO : ToPrioritize.valueOf(rs.getString("s.priotize"))));
//
//            }
            return returnSearch(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static int searchSituation(String situation) {
        Connection conn = ConnectFactory.getConnection();
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

    public static int returnQtdPackgeInstalled(Packages[] packag, Situation situation, char time, PartnerShip partnerS, LocalDateTime dateTime) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas
        PreparedStatement ps = null;
        Connection conection = ConnectFactory.getConnection();
        ResultSet rs = null;
        String searchPartnerShi = "";
        if (!partnerS.equals(PartnerShip.BOTH)) {
            searchPartnerShi = "AND s.idPartnerShip = ? ";
        }
        try {
            int id = searchSituation(situation.name());
            String sql = "";
            if (time == 'm') {
                Timestamp dateInitial = Timestamp.valueOf(dateTime.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
                Timestamp dateFinal = Timestamp.valueOf(dateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));

                if (packag.length > 1) {
                    //sql = "SELECT count(package) as packages from tbSales where (package = ? or package = ?) and idSituation = ? and ";
                    sql = "SELECT count(package) as packages FROM tbSales s"
                            + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                            + " WHERE (package = ? or package = ?) AND s.idSituation = ?  AND d.dateIntalation >= ? AND d.dateIntalation <= ? " + searchPartnerShi;
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setString(2, packag[1].toString());
                    ps.setInt(3, id);
                    ps.setTimestamp(4, dateInitial);
                    ps.setTimestamp(5, dateFinal);
                    if (!searchPartnerShi.equals("")) {
                        ps.setInt(6, searchIdFrom(partnerS.name(), "tbPartnerShip", "partnerShip"));

                    }
                } else if (packag[0] == Packages.ALL) {
                    // sql = "SELECT count(package) as packages from tbSales where idSituation = ?";
                    sql = "SELECT count(package) as packages FROM tbSales s"
                            + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                            + " WHERE s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ? " + searchPartnerShi;
                    ps = conection.prepareStatement(sql);
                    ps.setInt(1, id);
                    ps.setTimestamp(2, dateInitial);
                    ps.setTimestamp(3, dateFinal);
                    if (!searchPartnerShi.equals("")) {
                        ps.setInt(4, searchIdFrom(partnerS.name(), "tbPartnerShip", "partnerShip"));
                        System.out.println("passaata aqui");

                    }
                } else {
                    //sql = "SELECT count(package) as packages from tbSales where package = ? and idSituation = ?";
                    sql = "SELECT count(package) as packages FROM tbSales s"
                            + " JOIN tbDateInstalation d ON s.idDateInstalation = d.id"
                            + " WHERE package = ?  AND s.idSituation = ? AND d.dateIntalation >= ? AND d.dateIntalation <= ? " + searchPartnerShi;
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setInt(2, id);
                    ps.setTimestamp(3, dateInitial);
                    ps.setTimestamp(4, dateFinal);
                    if (!searchPartnerShi.equals("")) {
                        ps.setInt(5, searchIdFrom(partnerS.name(), "tbPartnerShip", "partnerShip"));

                    }
                }
            } else {

                if (packag.length > 1) {
                    sql = "SELECT count(package) as packages from tbSales where (package = ? or package = ?) and idSituation = ? " + searchPartnerShi;
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setString(2, packag[1].toString());
                    ps.setInt(3, id);
                    if (!searchPartnerShi.equals("")) {
                        ps.setInt(4, searchIdFrom(partnerS.name(), "tbPartnerShip", "partnerShip"));

                    }
                } else if (packag[0] == Packages.ALL) {
                    sql = "SELECT count(package) as packages from tbSales where idSituation = ? " + searchPartnerShi;
                    ps = conection.prepareStatement(sql);
                    ps.setInt(1, id);
                    if (!searchPartnerShi.equals("")) {
                        ps.setInt(2, searchIdFrom(partnerS.name(), "tbPartnerShip", "partnerShip"));

                    }
                } else {
                    sql = "SELECT count(package) as packages from tbSales where package = ?  and idSituation = ? " + searchPartnerShi;
                    ps = conection.prepareStatement(sql);
                    ps.setString(1, packag[0].toString());
                    ps.setInt(2, id);
                    if (!searchPartnerShi.equals("")) {
                        ps.setInt(3, searchIdFrom(partnerS.name(), "tbPartnerShip", "partnerShip"));

                    }
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

    public static int returnQtdDelayedInstalations(LocalTime time) {//time se divide em mes 'm' e todos 'a de all'  onde buscara dados do mes ou de todas as vendas
        PreparedStatement ps = null;
        Connection conection = ConnectFactory.getConnection();
        ResultSet rs = null;

        try {

            String sql = "SELECT count(cpf) as instalations from tbSales s "
                    + "join tbDateInstalation d on s.idDateInstalation = d.id "
                    + "join tbSituation situ on s.idSituation = situ.id "
                    + "where d.dateIntalation < ? and situ.situation = ?";

            ps = conection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atTime(time)));
            ps.setString(2, Situation.PROVISIONING.name());

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("instalations");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar vendas atrasadas" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao contabilizar vendas atrasadas " + ex);
        }
        return 0;
    }

    public void insertDateMarked(LocalDateTime date) {
        Connection conn = ConnectFactory.getConnection();
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
        Connection conn = ConnectFactory.getConnection();
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
        Connection conn = ConnectFactory.getConnection();
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

    public static int searchIdFrom(String valueSearch, String table, String column) {
        PreparedStatement ps = null;
        Connection conection = ConnectFactory.getConnection();
        ResultSet rs = null;

        try {
            String sql = String.format("SELECT id, %s FROM %s WHERE %s = ?", column, table, column);

            ps = conection.prepareStatement(sql);
            ps.setString(1, valueSearch);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cliente" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao buscar cliente" + ex);
        }
        return 0;
    }

    public void updateSalesDAO(Sales sale) {
        Connection conn = ConnectFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "update tbSales set idSeller =  ?, cpf = ? ,DateMade = ?, customers = ?, "
                + "contacts = ?, package = ?, valueSale = ?, idDateInstalation = ?, idSituation  = ?, "
                + "origin = ?, observation = ?, priotize = ?, idPartnerShip  = ?  where id = ?";

        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!searchDate(sale.getInstallationMarked())) {
                insertDateMarked(sale.getInstallationMarked());
            }
            sale.setSeller(new Seller(new SellerDAO().returnIdSeller(sale.getSeller().getTr())));
            ps.setInt(1, sale.getSeller().getIdentificador());
// ps.setInt(1, returnIdSeller(sale.getSeller().getTr()));

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
            ps.setInt(13, searchIdFrom(sale.getPartnetship().name(), "tbPartnerShip", "partnerShip"));
            ps.setInt(14, sale.getId());
            int rowsAffected = ps.executeUpdate();

//            JOptionPane.showMessageDialog(null, "Venda alterada com sucesso as " + format.dateTimeFormaterField(sale.getSellDateHour())
//                    + "Dados: \n" + sale.getCpf() + "\n" + Timestamp.valueOf(sale.getSellDateHour()) + "\n"
//                    + sale.getCustomers() + "\n" + sale.getContact() + "\n" + sale.getPackages() + "\n"
//                    + searchDate2(sale.getInstallationMarked()) + "\n"
//                    + SalesDAO.searchSituation(sale.getSituation().name()) + "\n"
//                    + sale.getOrigin().name() + "\n"
//                    + sale.getObservation() + "\n"
//                    + sale.getId(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
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

    public void updateSeveralSales(List<Sales> sales) {
        Connection conn = ConnectFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlInjection = "UPDATE tbSales SET idSituation = ?, observation = ? WHERE id = ?";

        try {
            ps = conn.prepareStatement(sqlInjection);
            for (Sales sale : sales) {
                int situationId = SalesDAO.searchSituation(sale.getSituation().name());
                ps.setInt(1, situationId);
                ps.setString(2, sale.getObservation());
                ps.setInt(3, sale.getId());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Venda ID " + sale.getId() + " atualizada com sucesso.");
                } else {
                    System.out.println("Nenhuma linha afetada para Venda ID " + sale.getId());
                }
            }

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

    public static void updateValuesPackageMonthDAO(Float value, Packages pack, LocalDateTime dateInstallationMarked) {
        Connection conn = ConnectFactory.getConnection();
        PreparedStatement ps = null;

        if (dateInstallationMarked.getMonth() != LocalDateTime.now().getMonth()) {

        }
        Timestamp dateInitial = Timestamp.valueOf(dateInstallationMarked.with(TemporalAdjusters.firstDayOfMonth()).withHour(00).withMinute(00));
        Timestamp dateFinal = Timestamp.valueOf(dateInstallationMarked.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));

        //System.out.println("Data inicial " + dateInitial + " DInal: " + dateFinal);
        String sqlInjection;

        try {
            conn.prepareStatement("SET SQL_SAFE_UPDATES = 0;");
            //tava comentada
            if (pack == Packages.I_400MB) {
                //sqlInjection = "UPDATE tbSales SET valueSale = ? WHERE dateMade >= ? AND dateMade <= ? and package = ?";
                sqlInjection = "UPDATE tbSales s "
                        + " join tbPartnerShip par on par.id = s.idPartnerShip "
                        + "JOIN tbDateInstalation d ON s.idDateInstalation = d.id "
                        + "SET s.valueSale = ? WHERE d.dateIntalation >= ? "
                        + "AND d.dateIntalation <= ? and s.package = ? AND par.partnerShip = ?";

            } else {
                pack = Packages.I_400MB;
                //sqlInjection = "UPDATE tbSales SET valueSale = ? WHERE dateMade >= ? AND dateMade <= ? and package != ?";
                sqlInjection = "UPDATE tbSales s "
                        + " join tbPartnerShip par on par.id = s.idPartnerShip "
                        + "JOIN tbDateInstalation d ON s.idDateInstalation = d.id SET s.valueSale = ? "
                        + "WHERE d.dateIntalation >= ? AND d.dateIntalation <= ? "
                        + "and s.package !=  ? AND par.partnerShip = ?";

            }

            ps = conn.prepareStatement(sqlInjection);
            ps.setFloat(1, value);
            ps.setTimestamp(2, dateInitial);
            ps.setTimestamp(3, dateFinal);
            ps.setString(4, pack.toString());
            ps.setString(5, PartnerShip.OI.name());

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
        Connection conection = ConnectFactory.getConnection();
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

    public static List<Integer> returnIdsPackages(Packages packag, LocalDateTime dateInstalled) {
        try (Connection connection = ConnectFactory.getConnection()) {
            List<Integer> values = new ArrayList<>();
            Timestamp dateInitial = Timestamp.valueOf(dateInstalled.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0));
            Timestamp dateFinal = Timestamp.valueOf(dateInstalled.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59));
//            String sql;

//            if (packag == Packages.I_400MB) {
//                sql = "SELECT s.id FROM tbSales s "
//                        + "JOIN tbDateInstalation d ON s.idDateInstalation = d.id "
//                        + " join tbPartnerShip par on par.id = s.idPartnerShip "
//                        + "WHERE d.dateIntalation >= ? AND d.dateIntalation <= ? "
//                        + "AND s.package = ? AND par.partnerShip = ?";
//
//            } else {
//                packag = Packages.I_400MB;
//                sql = "SELECT s.id FROM tbSales s "
//                        + "JOIN tbDateInstalation d ON s.idDateInstalation = d.id "
//                        + " join tbPartnerShip par on par.id = s.idPartnerShip "
//                        + "WHERE d.dateIntalation >= ? AND d.dateIntalation <= ? "
//                        + "AND s.package != ? AND par.partnerShip = ?";
//
//            }
            String sql = "SELECT s.id FROM tbSales s "
                    + "JOIN tbDateInstalation d ON s.idDateInstalation = d.id "
                    + "JOIN tbPartnerShip par ON par.id = s.idPartnerShip "
                    + "WHERE d.dateIntalation >= ? AND d.dateIntalation <= ? "
                    + "AND par.partnerShip = ? "
                    + "AND s.package " + (packag == Packages.I_400MB ? "= ?" : "!= ?");

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setTimestamp(1, dateInitial);
                ps.setTimestamp(2, dateFinal);
                ps.setString(3, PartnerShip.OI.name());
                ps.setString(4, packag.toString());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        values.add(rs.getInt("id"));
                    }
                }
            }
            return values;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void InsertSales(Sales sale) throws HeadlessException {

        Connection conn = ConnectFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "INSERT INTO tbSales(idSeller,DateMade,customers,contacts,valueSale,"
                + "package,idDateInstalation,origin,observation,cpf,idSituation,priotize,idPartnerShip) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!searchDate(sale.getInstallationMarked())) {
                insertDateMarked(sale.getInstallationMarked());
            }
            Origin ori = sale.getOrigin() != null ? Origin.fromString(sale.getOrigin() + "") : Origin.CHAT;
//se der erro futuramente, descomente a linha debaixo            
//ps.setInt(1, salesdao.returnIdSeller(sale.getSeller().getTr()));
            sale.setSeller(new Seller(new SellerDAO().returnIdSeller(sale.getSeller().getTr())));
            ps.setInt(1, sale.getSeller().getIdentificador());
            ps.setTimestamp(2, Timestamp.valueOf(sale.getSellDateHour()));
            ps.setString(3, sale.getCustomers());
            ps.setString(4, sale.getContact());
            ps.setFloat(5, sale.getValuePackage());
            ps.setString(6, sale.getPackages());
            ps.setInt(7, searchDate2(sale.getInstallationMarked()));
            ps.setString(8, sale.getOrigin().name());
            ps.setString(9, sale.getObservation());
            ps.setString(10, sale.getCpf());
            ps.setInt(11, SalesDAO.searchSituation(sale.getSituation().name()));
            ps.setString(12, sale.getPrioritize().name());
            ps.setInt(13, searchIdFrom(sale.getPartnetship().name(), "tbPartnerShip", "partnerShip"));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Venda armazenada com sucesso as " + format.dateTimeFormaterField(sale.getSellDateHour()), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

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

}
