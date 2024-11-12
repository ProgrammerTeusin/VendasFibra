
package View;

import Dao.SalesDAO;
import Services.SaleService;
import java.time.LocalDate;


public class InsertAllSalesExecel {
    public static void main(String[] args) {
        SaleService ser = new SaleService();
        ser.insertAllorManySellExcel(SalesDAO.returnData('a', LocalDate.MIN, LocalDate.MIN));
            
        
        
    }
    
}
