package Services;

import Model.Enums.Packages;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaleService {
public static float ValuePerSale(int qtdInstalled, Packages packages) {
    float value = 0;
    TreeMap<Integer, Float> p400MB = new TreeMap<Integer, Float>() {
        {
            put(40, 50.0f);
            put(29, 45.0f);
            put(19, 40.0f);
            put(0, 35.0f);
        }
    };

    TreeMap<Integer, Float> p500MB = new TreeMap<Integer, Float>() {
        {
            put(40, 60.0f);
            put(29, 50.0f);
            put(19, 45.0f);
            put(0, 35.0f);
        }
    };
    if (packages != Packages.SELECT) {

        if (packages == Packages.I_400MB) {
            for (Map.Entry<Integer, Float> entry : p400MB.descendingMap().entrySet()) {
                if (qtdInstalled > entry.getKey()) {
                    value = entry.getValue();
                    break;      
                }
            }
        } else {
            for (Map.Entry<Integer, Float> entry : p500MB.descendingMap().entrySet()) {
                if (qtdInstalled > entry.getKey()) {
                    value = entry.getValue();
                    break;
                }
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Escolha um pacote para retornar o valor", "Dados incorretos", JOptionPane.ERROR_MESSAGE);
    }

    return value;
}

public static void searchSellsPlanilhaService() {
           Workbook work =   new XSSFWorkbook(excelFile);
                   
    }

   
}
