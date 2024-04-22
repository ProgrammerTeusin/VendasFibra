package Dao;

import Controller.Formatting;
import DAO.ConnectionFactory;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Sales;
import Model.Vendedor;
import com.mysql.cj.MysqlType;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesDAO {
    Formatting format = new Formatting();

    public static List<Sales> returnData() {//retornarDados
        PreparedStatement ps = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql = "SELECT s.id,v.tr,situa.situation,s.customers,s.origin,s.observation,s.contacts,s.DateMade, s.cpf, s.package, s.valueSale,d.dateIntalation from tbSales s join tbDateInstalation d on s.idDateInstalation = d.id join tbSeller v on s.idSeller = v.id join tbSituation situa on s.idSituation = situa.id;";
        List<Sales> sales = new ArrayList<>();
        Formatting format = new Formatting();
        Sales sale;
        try {
            ps = con.prepareCall(sql);
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

                System.out.println(rs.getString("s.id"));
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
                        rs.getString("s.observation")));

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

    public static int returnQtdPackgeInstalled(Packages[] packag, Situation situation) {
    PreparedStatement ps = null;
    Connection conection = ConnectionFactory.getConnection();
    ResultSet rs = null;

    try {
        int id = searchSituation(situation.name());
        String sql = "";
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

        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("packages");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao contabilizar pacotes" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
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
    public void updateSalesDAO (Sales sale){
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "update tbSales set idSeller =  ?, cpf = ? ,DateMade = ?, customers = ?, "
                + "contacts = ?, package = ?, valueSale = ?, idDateInstalation = ?, idSituation  = ?, "
                + "origin = ?, observation = ?  where id = ?";

        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!searchDate(sale.getInstallationMarked())) {
                insertDateMarked(sale.getInstallationMarked());
            }
            ps.setInt(1, 1);
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
            ps.setInt(12, sale.getId());
           int rowsAffected = ps.executeUpdate();
System.out.println("Linhas afetadas: " + rowsAffected);

            JOptionPane.showMessageDialog(null, "Venda alterada com sucesso as " + format.dateTimeFormaterField(sale.getSellDateHour())
                   + "Dados: \n"+sale.getCpf()+"\n"+Timestamp.valueOf(sale.getSellDateHour())+"\n"
                    +sale.getCustomers()+"\n"+sale.getContact()+"\n"+sale.getPackages()+"\n"+
                    searchDate2(sale.getInstallationMarked())+"\n"+
                    SalesDAO.searchSituation(sale.getSituation().name())+"\n"+
                    sale.getOrigin().name()+"\n"+
                    sale.getObservation()+"\n"+
                    sale.getId(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar venda \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("erro visto: "+ex.getMessage());
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
