package Services;

import Controller.Formatting;
import Dao.AllSalesDao;
import Dao.SalesDAO;
import Model.Enums.MonthsYear;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Sales;
import Model.Vendedor;
import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToPDF {

    Formatting format = new Formatting();
    Document document = new Document();
String path = "";
    public void head(Sales sale, List<MonthsYear> monthYear, String nameFile, float valueTot, int qtdTot) {

            path = "C:/Users/mathe/Downloads/" + nameFile+".pdf";
        try {
            File file = new File(nameFile);
            if (file.exists()) {
                //fazer depois para alterar os numeros
            }
            FileOutputStream filepdf = new FileOutputStream(path);
            PdfWriter writer = PdfWriter.getInstance(document, filepdf);
            document.open();

            //title 
            String month = "";
            for (int i = 0; i < monthYear.size(); i++) {
                month += monthYear.get(i).toString();

                if (month.contains("[")) {
                    month = monthYear.get(i).toString().replace('[', ' ');
                }
                if (month.contains("]")) {

                    month = monthYear.get(i).toString().replace(']', ' ');
                }
                if (i < monthYear.size() - 1 && i != monthYear.size()-2) {
                    month += ", ";
                }
                if (i == monthYear.size() - 2) {
                    month += " e ";
                }
                
            }

            Paragraph pTitle = new Paragraph();
            pTitle.setAlignment(Element.ALIGN_CENTER);
            pTitle.add(new Chunk("Relatorio De Vendas Mês(es) " + month + " de " + sale.getInstallationMarked().getYear(),
                    new Font(Font.DEFAULTSIZE, 18)));
            document.add(pTitle);
            document.add(new Paragraph("\n\n\n"));

            //apresentação do nome, qtd, valor a receber
            PdfPTable table = new PdfPTable(3); // 3 colunas.

            // Definir a tabela para ocupar toda a largura da página
            table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            table.setWidthPercentage(100); // largura como porcentagem do tamanho da página

            PdfPCell cell;
            cell = new PdfPCell(new Phrase(
                    new Chunk("Data Gerada: " + "\n" + format.dateTimeFormaterField(LocalDateTime.now()),
                            new Font(Font.STRIKETHRU, 12))));
            cell.setBorder(Rectangle.NO_BORDER); // Remove a borda
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(
                    new Chunk("Nome Vendedor: " + "\n" + " Carlos Matheus",
                            new Font(Font.STRIKETHRU, 12))));
            cell.setBorder(Rectangle.NO_BORDER); // Remove a borda
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(
                    new Chunk("Qtd Instalada: "
                            + qtdTot//SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.ALL}, Situation.INSTALLED, 'm') + "\n"
                            + "\nValor Calculado : "
                            + format.formatMoneyNumber(valueTot+"",'M'),
//format.formatMoneyNumber(AllSalesDao.returnValuesPackage(Situation.INSTALLED, sale.getInstallationMarked().toLocalDate(), 'm') + "", 'M'),
                            new Font(Font.STRIKETHRU, 12)))
            );
            cell.setBorder(Rectangle.NO_BORDER); // Remove a borda
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            document.add(table);

            System.out.println("Deu ccerto ");

            //document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ToPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //List<Sales> sale
    public void body(List<Sales> sale) {
        document.add(new Chunk("\n\nAlerta! O valor calculado foi feito automaticamente pelo sistema, "
                + "favor confirmar se esta correto o valor.\n\n",
                new Font(Font.NORMAL)));
        String[] headers = {"Venda", "CPF", "Nome", "TR Vendida", "Situação"};

        PdfPTable table = new PdfPTable(headers.length); // Cria uma tabela com o número de colunas igual ao número de títulos

        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header));
            table.addCell(headerCell).setHorizontalAlignment(Element.ALIGN_CENTER);
        }

        int cont = 0;
        List<String[]> cpfCancelled = new ArrayList();

        for (int i = 0; i < sale.size(); i++) {
            if (sale.get(i).getSituation() == Situation.INSTALLED) {
                cont++;

                table.addCell((cont > 9 ? cont + "" : "0" + cont)).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(sale.get(i).getCpf()).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(sale.get(i).getCustomers()).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(sale.get(i).getSeller().getTr()).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(sale.get(i).getSituation().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
            }
            if (sale.get(i).getSituation() == Situation.CANCELED) {

                String[] cpfCancelledArray = {
                    sale.get(i).getCpf(),
                    sale.get(i).getCustomers(),
                    sale.get(i).getSeller().getTr(),
                    sale.get(i).getSituation().toString()
                };
                cpfCancelled.add(cpfCancelledArray);
            }

        }
        for (String[] values : cpfCancelled) {
            cont++;
            table.addCell((cont > 9 ? cont + "" : "0" + cont)).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(values[0]).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(values[1]).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(values[2]).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(values[3]).setHorizontalAlignment(Element.ALIGN_CENTER);

        }
        Anchor anchorTarget = downloadCPFs(sale, cont + " Registros de vendas ");

        document.add(anchorTarget);
        document.add(new Paragraph("\n"));
cont = 0;
        document.add(table);
        document.add(new Paragraph("\n\n"));
        footer(path);

    }

    private Anchor downloadCPFs(List<Sales> sale, String msg) {
    FileWriter writer = null;
    String reference = "C:/Users/mathe/Desktop/CPFs.txt";
    try {
        writer = new FileWriter(reference);
        int i =1;
        for (Sales values : sale) {
            writer.write("CPF "+i+": "+ values.getCpf() + "\n");
        i++;
        }
    } catch (IOException ex) {
        Logger.getLogger(ToPDF.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ToPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    Anchor anchorTarget = new Anchor("                " + msg + "      Clique aqui para Baixar o arquivo TXT com todos os cpf");
    anchorTarget.setReference(reference);
    return anchorTarget;
}

    public void footer(String path) {
        Paragraph p = new Paragraph("Desenvolved by Seller Carlos Matheus");
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.close();
        try {
            File pdfFile = new File(path);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Awt Desktop não é suportado!");
                }
            } else {
                System.out.println("Arquivo não existe!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void toExportPDFService(List<Sales> sales, List<MonthsYear> monthsYear, String path,int qtdTot,float valueTot) {
        
        head(sales.get(0), monthsYear.reversed(), path,valueTot,qtdTot );
        body(sales);
        
    }

    public static void main(String[] args) {
        ToPDF t = new ToPDF();
        Sales sale = new Sales(new Vendedor("799469"), LocalDateTime.now(),
                "75465356", "Eu", "65465465", Packages.I_400MB.toString(),
                0, LocalDateTime.now(), Period.SELECT,
                Origin.SELECT, Situation.SELECT, "Ai pai para");
        List<MonthsYear> mon = new ArrayList<>();
        mon.add(MonthsYear.MAY);
        mon.add(MonthsYear.AUGUST);
        mon.add(MonthsYear.DECEMBER);
        MonthsYear monthYear = MonthsYear.valueOf(sale.getInstallationMarked().getMonth().name());
        //t.head(sale, "Aqui deu cert", monthYear,"FilesPDF.pdf");
       // t.head(sale, mon, mon.toString() + ".pdf");

        t.body(SalesDAO.returnData('c', LocalDate.of(2024, Month.MARCH, 13), LocalDate.now()));
//        t.head(new Sales(new Vendedor("799469"), LocalDateTime.now(), "75465356",
//                "Eu", "65465465", packages, 0, LocalDateTime.MIN, Period.SELECT, Origin.SELECT, Situation.SELECT, observation), 5,LocalDateTime.now() , Period.SELECT, Origin.SELECT, Situation.SELECT, "Era yna vez"));
    }
}
