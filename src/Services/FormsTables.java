package Services;

import Controller.Formatting;
import Model.Sales;


public class FormsTables {
    
    Formatting format = new Formatting();
    public Object[] tableSales(Sales sales) {
        Object[] dados = {
            sales.getId(),
            sales.getPartnetship(),
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
        return dados;
    }

    public Object[] tableAllSales(Sales sales) {
        Object[] dados = {
            sales.getId(),
            sales.getSeller().getTr(),
            sales.getPartnetship(),
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
        return dados;
    }

}
