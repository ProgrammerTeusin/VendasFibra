package Controller;

import Dao.AllSalesDao;
import Model.Enums.Situation;
import View.AllSales;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AllSalesController {

    Formatting format = new Formatting();

    public void qtdSituationMonth() {
                AllSales.lblCancelada.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.CANCELED, LocalDate.now().minusMonths(1), 'm')+" Linhas Canceladas");
                AllSales.lblCancelada1.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.CANCELED, LocalDate.now(), 'm')+" Linhas Canceladas");
                AllSales.lblCancelada2.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.CANCELED, LocalDate.now() , 'a')+" Linhas Canceladas");
                
                AllSales.lblInstaladas.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now().minusMonths(1) , 'm')+" Linhas Instaladas");
                AllSales.lblInstaladas1.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now() , 'm')+" Linhas Instaladas");
                AllSales.lblInstaladas2.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now() , 'a')+" Linhas Instaladas");
                AllSales.lblProvsingQtd.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.PROVISIONING, LocalDate.now() , 'm')+" Linhas Em Aprovisionamento");

     
    }

    public void valueSituationMonth() {
        // Map<Situation, Float> values = new HashMap<>();

        AllSales.lblCanceladaValue.setText(format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.CANCELED, LocalDate.now().minusMonths(1), 'm') + "", 'M'));

        AllSales.lblInstaladasValue.setText(format.formatMoneyNumber((AllSalesDao.returnValuesPackage(Situation.INSTALLED, LocalDate.now().minusMonths(1), 'm')) + "", 'M'));

        AllSales.lblCanceladaValue1.setText(format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.CANCELED, LocalDate.now(), 'm') + "", 'M'));
        AllSales.lblProvsingvalue.setText(format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.PROVISIONING, LocalDate.now(), 'm') + "", 'M'));
        AllSales.lblInstaladasValue1.setText(format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.INSTALLED, LocalDate.now(), 'm') + "", 'M'));

        AllSales.lblCanceladaValue2.setText(format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.CANCELED, LocalDate.now(), 'a') + "", 'M'));
        AllSales.lblInstaladasValue2.setText(format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.INSTALLED, LocalDate.now(), 'a') + "", 'M'));

//        int month = LocalDate.now().getMonthValue();
//        int monthParameter = date.getMonthValue();
//        int day = LocalDate.now().getDayOfMonth();
//        int dayParameter = date.getDayOfMonth();
//        if ((month == monthParameter) && (day == dayParameter)) {
//          //  values.put(Situation.INSTALLED, AllSalesDao.returnValuesPackage(Situation.PROVISIONING, date, time));
//
//        }
    }
}
