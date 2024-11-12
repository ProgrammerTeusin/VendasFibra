package DAO;
//sempre importe essa classe para conectar ao bd, pois se quiser trocar de banco de dados, é so trocar o drive e a URL

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConnectionFactory {

    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    // public static final String URL = "jdbc:mysql://localhost:3306/vendasFibraTest";
  public static final String URL = "jdbc:mysql://localhost:3306/vendasFibra";
    private static final String user = "root";
    private static final String senha = "";

    public static Connection getConnection() {
        try {
            Class.forName(Driver);

            //JOptionPane.showMessageDialog(null, "Conexão bem-Sucedida:\n", "Erro", JOptionPane.QUESTION_MESSAGE);
            return DriverManager.getConnection(URL, user, senha);
        
        } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro na conexão com o Banco de Dados:\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro na conexão com o Banco de Dados:\n"+ex);           
        }catch(RuntimeException ex){
            JOptionPane.showMessageDialog(null, "Erro na conexão com o Banco de Dados:\n"+ ex);
            System.out.println("Erro na conexão com o Banco de Dados:\n"+ex);           
        }
        return null;
        
    }

    public static void closeConnection(Connection conn) {
        //verifica se o conection for diferente de null(ou seja se o conetion estiver aberto) fechamos ele
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Banco fechado com sucesso");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexao com o banco de dados:\n", "Erro"+ex, JOptionPane.ERROR_MESSAGE);
               System.out.println("Erro ao fechar conexao com o banco de dados:\n"+ex); 
            }
        }

    }

    public static void closeConnection(Connection conn, PreparedStatement stmt) {
        closeConnection(conn);
        try {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexao com o banco de dados:\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {

        closeConnection(conn, stmt);

        try {
            if (rs != null) {
                rs.close();
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexao com o banco de dados:\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
