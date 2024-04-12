package Controller;

import DAO.ConnectionFactory;
import Model.Sales;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author mathe
 */
public class SalesController {
    
    public void saveSales(Sales sale){
        
         Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = null;
        Resultset rs = null;
        
        String sqlInjection = "INSERT INTO tbSales(idSeller,DateMade,customers,contacts,"
                + "package,idDateInstalation,origin,observation) values (?,?,?,?,?,?,?,?)";
        
        
        try {
            ps = conn.prepareStatement(sqlInjection);
            if (!searchDate(sale.getInstallationMarked())) {
                 insertDateMarked(sale.getInstallationMarked());
            }
            ps.setInt(1, 1);
            ps.setTimestamp(2, Timestamp.valueOf(sale.getSellDateHour()));
            ps.setString(3, sale.getCustomers());
            ps.setString(4, sale.getContact());
            ps.setString(5, sale.getPackages());
            ps.setInt(6, searchDate2(sale.getInstallationMarked()));
            ps.setString(7, sale.getOrigin().toString());
            ps.setString(8, sale.getOrigin().toString());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
       
        }finally {
        try {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
       
        
    }
    
    public void insertDateMarked(LocalDateTime date){
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
    
    
public boolean searchDate(LocalDateTime date){
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
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return false;  // A data não foi encontrada
}

   
public int searchDate2(LocalDateTime date){
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
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return 0;  // A data não foi encontrada
}

}
