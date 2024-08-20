package Controller;

import Dao.AllSalesDao;
import Dao.SalesDAO;
import Model.Enums.MonthsYear;
import Model.Enums.Situation;
import Model.Sales;
import Services.ToPDF;
import View.AllSales;
import View.VendasAtual;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AllSalesController {

    Formatting format = new Formatting();

    ToPDF pdf = new ToPDF();

    public void qtdSituationMonth() {
        AllSales.lblCancelada.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.CANCELED, LocalDate.now().minusMonths(1), 'm') + " Linhas Canceladas");
        AllSales.lblCancelada1.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.CANCELED, LocalDate.now(), 'm') + " Linhas Canceladas");
        AllSales.lblCancelada2.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.CANCELED, LocalDate.now(), 'a') + " Linhas Canceladas");

        AllSales.lblInstaladas.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now().minusMonths(1), 'm') + " Linhas Instaladas");
        AllSales.lblInstaladas1.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now(), 'm') + " Linhas Instaladas");
        AllSales.lblInstaladas2.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now(), 'a') + " Linhas Instaladas");
        AllSales.lblProvsingQtd.setText(AllSalesDao.returnQtdPackgeInstalled(Situation.PROVISIONING, LocalDate.now(), 'm') + " Linhas Em Aprovisionamento");

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

    public void toExportPDFController(char type, LocalDate dateInitial, LocalDate dateFinal) {
        Set<String> processedMonths = new HashSet<>();
        List<MonthsYear> monthsYear = new ArrayList<>();
        float value = 0;
        int qtd = 0;
        List<Sales> sales = new ArrayList<>();
        switch (type) {
            case 'a'://a de all - todos
                sales = (SalesDAO.returnData('a', LocalDate.MIN, LocalDate.MIN));
        qtd = AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now(), 'a');
        value = AllSalesDao.returnValuesPackage(Situation.INSTALLED, LocalDate.now(),'a');
                
                break;

            case 'm'://m de this month - esse mes
                sales = (SalesDAO.returnData('m', LocalDate.MIN, LocalDate.MIN));
        qtd = AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now(), 'm');
        value = AllSalesDao.returnValuesPackage(Situation.INSTALLED, LocalDate.now(),'m');
                break;
            case 'l'://l de last month - mes passado
                sales = (SalesDAO.returnData('l', LocalDate.MIN, LocalDate.MIN));
        qtd = AllSalesDao.returnQtdPackgeInstalled(Situation.INSTALLED, LocalDate.now().minusMonths(1), 'm');
        value = AllSalesDao.returnValuesPackage(Situation.INSTALLED, LocalDate.now().minusMonths(1),'m');
                break;
            case 'c'://c de choose - escolja de periodos
                sales = (SalesDAO.returnData('c', dateInitial, dateFinal));
        qtd = AllSalesDao.returnQtdPackgeInstalledByPeriod(Situation.INSTALLED, dateInitial, dateFinal);
        value = AllSalesDao.returnValuesPackageByPeriod(Situation.INSTALLED, dateInitial, dateFinal);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opção Invalida! \n"
                        + "Para pesquisar todas vendas insira 'a' de all"
                        + "Para pesquisar vendas desse mês insira 'm' de month"
                        + "Para pesquisar mês passado digite 'l' de last month"
                        + "E para escolha de periodos 'c' de choose", "Erro", JOptionPane.ERROR_MESSAGE);

        }

        for (Sales sale : sales) {
            String month = sale.getInstallationMarked().getMonth() + "";
            if (!processedMonths.contains(month)) {
                monthsYear.add(MonthsYear.valueOf(month));
                processedMonths.add(month);
            }
        }
        pdf.toExportPDFService(sales, monthsYear, monthsYear.toString(), qtd, value);
    }
    
        public void returnData(DefaultTableModel dtm, LocalDate dateTimeInicial) {
        List<Sales> data = AllSalesDao.returnSalesMadeToday(dateTimeInicial);
        
            if (data.size() > 0) {
                
            
        dtm.setRowCount(0);

        if (dtm == VendasAtual.tblVendasRes.getModel()) {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            VendasAtual.lblQtSellsTable.setText((VendasAtual.tblVendasRes.getRowCount() > 9 ? VendasAtual.tblVendasRes.getRowCount() : "0" + VendasAtual.tblVendasRes.getRowCount()) + " Registros de Vendas");

        } else {
            for (Sales sales : data) {

                Object[] dados = {
                    sales.getId(),
                    sales.getSeller().getTr(),
                    format.dateTimeFormaterField(sales.getSellDateHour()),
                    sales.getCpf(),
                    sales.getCustomers(),
                    sales.getContact(),
                    sales.getPackages(),
                    format.formatMoneyNumber(sales.getValuePackage() + "", 'M'),
                    format.dateFormaterField((sales.getInstallationMarked()).toLocalDate()),
                    sales.getPeriod().toString(),
                    sales.getOrigin().toString(),
                    sales.getSituation(),
                    sales.getObservation(),
                    sales.getPrioritize()
                };
                dtm.addRow(dados);
            }
            
        }
            }else{
                 JOptionPane.showMessageDialog(null, "Você não fez nenhuma Venda hoje MANO!!!!\nTRATE DE FAZER CARA!! VIAJA NAS CONTAS" , "ALERTA", JOptionPane.INFORMATION_MESSAGE);

            }
    }

    

}
