package Controller;

import Model.Enums.TypesDate;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;

public class Formatting {
  
        private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       private DateTimeFormatter dtfComplete = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

   
    public LocalDate dateFormaterBank(String date){
        return LocalDate.parse(date, dtf);
    }
    public String dateFormaterField(LocalDate date){
        return dtf.format(date);
    }
    
    public LocalDateTime dateTimeFormaterBank(String date){
        return LocalDateTime.parse(date, dtfComplete);
    }
    public String dateTimeFormaterField(LocalDateTime date){
        return dtfComplete.format(date);
    }
    
  public String formatMoneyNumber(String valor, char type) {
        switch (type) {
            case 'M':
                // Formata como moeda
                NumberFormat formatadorDeMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                try {
                    Number numero = formatadorDeMoeda.parse(valor);
                    return formatadorDeMoeda.format(numero);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            case 'N':
                // Formata como número, removendo "R$" e substituindo "," por "."
                return valor.replaceAll("[^\\d,]", "").replace(",", ".");
            default:
                JOptionPane.showMessageDialog(null, "Digite 'M' para formatar o valor em moeda e 'N' para formatar o valor em número normal!", "Erro", JOptionPane.INFORMATION_MESSAGE);
                return null;
        }
    }
}

    
   
