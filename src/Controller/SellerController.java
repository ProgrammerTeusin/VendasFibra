package Controller;

import Dao.SalesDAO;
import Dao.SellerDAO;
import Model.Sales;
import Model.Seller;
import Services.FormsTables;
import View.CurrentSales;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sounds.PlaySound;

public class SellerController {

    FormsTables ftService = new FormsTables();
    SellerDAO dao = new SellerDAO();

    public boolean isFirstAccess(Seller seller) {
        return SellerDAO.returnAccessQtdDAO(seller) < 1;
    }
    public int qtdAccess(Seller seller) {
        return SellerDAO.returnAccessQtdDAO(seller);
    }

    public void insertAccess(Seller seller) {

        dao.insertAccessQtd(seller);

    }
    public void insertSellerController(Seller seller) {

        dao.insertSeller(seller);

    }
    public List<Seller> returnAllSeller(){
     return dao.returnSellers();
    }

    public void welcome(Seller seller) {
        if (isFirstAccess(seller)) {
            PlaySound play = new PlaySound();
            try {
                play.run("welcome.mp3");
            } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tocar logins \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
            
            }

        }

    }
}
