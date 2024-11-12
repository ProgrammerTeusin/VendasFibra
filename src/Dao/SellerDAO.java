package Dao;

import DAO.ConnectionFactory;
import Model.Enums.Origin;
import Model.Vendedor;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class SellerDAO {

    
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
    
    public static int returnAccessQtdDAO(Vendedor seller) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT count(l.idTR) as qtdTR FROM tbLogin l "
                + "join tbSeller s on l.idTR = s.id WHERE s.tr = ? and l.dayTimeAccess >= ? and l.dayTimeAccess <= ?";

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 59);
        try {
            ps = conn.prepareCall(sql);
            ps.setString(1, seller.getTr());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDate.now().atTime(start)));
            ps.setTimestamp(3, Timestamp.valueOf(LocalDate.now().atTime(end)));
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("qtdTR");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SellerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public  void insertAccessQtd(Vendedor seller){
     
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        Resultset rs = null;

        String sqlInjection = "INSERT INTO tbLogin(dayTimeAccess,idTR) values (?,?)";

        try {
            ps = conn.prepareStatement(sqlInjection);
     
            ps.setTimestamp(1,Timestamp.from(Instant.now()));
            ps.setInt(2,returnIdSeller(seller.getTr()));
        
            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar logins \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("erro ao consultar logins: "+ex);
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
