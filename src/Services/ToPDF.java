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
import com.lowagie.text.html.simpleparser.StyleSheet;
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

        path = "C:/Users/mathe/Downloads/" + nameFile + ".pdf";
        int num = 0;
        try {
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
                if (i < monthYear.size() - 1 && i != monthYear.size() - 2) {
                    month += ", ";
                }
                if (i == monthYear.size() - 2) {
                    month += " e ";
                }

            }

            Paragraph pTitle = insertColumFormatedParagraph("Relatorio De Vendas Mês(es) " + month + " de " + sale.getInstallationMarked().getYear(), 18, 1, Color.red, Color.red);
            pTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pTitle);
            document.add(new Paragraph("\n\n\n"));

            //apresentação do nome, qtd, valor a receber
            PdfPTable table = new PdfPTable(3); // 3 colunas.

            // Definir a tabela para ocupar toda a largura da página
            table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            table.setWidthPercentage(100); // largura como porcentagem do tamanho da página

            PdfPCell cellDate = new PdfPCell(new Phrase(
                    new Chunk("Data Gerada: " + "\n" + format.dateTimeFormaterField(LocalDateTime.now()),
                            new Font(Font.STRIKETHRU, 12, 1, java.awt.Color.decode("#9C5700")))));
            cellDate.setBorder(Rectangle.NO_BORDER); // Remove a borda
            cellDate.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDate.setBackgroundColor(java.awt.Color.decode("#FFE699"));
            table.addCell(cellDate);

            PdfPCell cellSeller = new PdfPCell(new Phrase(
                    new Chunk("Nome Vendedor: " + "\n" + " Carlos Matheus",
                            new Font(Font.STRIKETHRU, 12, 1, java.awt.Color.decode("#9C5700")))));
            cellSeller.setBorder(Rectangle.NO_BORDER); // Remove a borda
            cellSeller.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellSeller.setBackgroundColor(java.awt.Color.decode("#FFE699"));
            table.addCell(cellSeller);

            PdfPCell cellValues = insertColumFormated("Qtd Instalada: "
                    + qtdTot//SalesDAO.returnQtdPackgeInstalled(new Packages[]{Packages.ALL}, Situation.INSTALLED, 'm') + "\n"
                    + "\nValor Calculado : "
                    + format.formatMoneyNumber(valueTot + "", 'M'), 12, 1,
                    java.awt.Color.decode("#C6EFCE"),
                    java.awt.Color.decode("#006100"));

            cellValues.setBorder(Rectangle.NO_BORDER); // Remove a borda
            cellValues.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellValues.setBackgroundColor(java.awt.Color.decode("#C6EFCE"));
            table.addCell(cellValues);

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
                new Font(Font.BOLD, 14, 1, java.awt.Color.decode("#FF4500"))));
        String[] headers = {"Venda", "CPF", "Nome", "TR Vendida", "Situação"};

        PdfPTable table = new PdfPTable(headers.length); // Cria uma tabela com o número de colunas igual ao número de títulos

        for (String header : headers) {
            PdfPCell headerCell = insertColumFormated(header, 14, 1, java.awt.Color.decode("#800080"),
                    Color.white);
            table.addCell(headerCell).setHorizontalAlignment(Element.ALIGN_CENTER);
        }

        int cont = 0;
        List<String[]> cpfCancelled = new ArrayList();

        for (int i = 0; i < sale.size(); i++) {
            if (sale.get(i).getSituation() == Situation.INSTALLED) {
                cont++;
                PdfPCell cellCont = insertColumFormated(cont > 9 ? cont + "" : "0" + cont, 12, 1,
                        java.awt.Color.decode("#C6EFCE"),
                        java.awt.Color.decode("#006100"));
                PdfPCell cellCPF = insertColumFormated(sale.get(i).getCpf(), 12, 1,
                        java.awt.Color.decode("#C6EFCE"),
                        java.awt.Color.decode("#006100"));
                PdfPCell cellSituation = insertColumFormated(sale.get(i).getSituation().toString(), 12, 1,
                        java.awt.Color.decode("#C6EFCE"),
                        java.awt.Color.decode("#006100"));
                PdfPCell cellCustommers = insertColumFormated(sale.get(i).getCustomers(), 12, 1,
                        java.awt.Color.decode("#C6EFCE"),
                        java.awt.Color.decode("#006100"));
                PdfPCell cellTR = insertColumFormated(sale.get(i).getSeller().getTr(), 12, 1,
                        java.awt.Color.decode("#C6EFCE"),
                        java.awt.Color.decode("#006100"));

                table.addCell(cellCont);
                table.addCell(cellCPF);
                table.addCell(cellCustommers);
                table.addCell(cellTR);
                table.addCell(cellSituation);

                //use as debaixo e exlcua as de cima caso se arrependa das cores 
//                table.addCell((cont > 9 ? cont + "" : "0" + cont)).setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(sale.get(i).getCpf()).setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(sale.get(i).getCustomers()).setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(sale.get(i).getSeller().getTr()).setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(sale.get(i).getSituation().toString()).setHorizontalAlignment(Element.ALIGN_CENTER);
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

            PdfPCell cellCont = insertColumFormated(cont > 9 ? cont + "" : "0" + cont, 12, 1, java.awt.Color.decode("#FFC7CE"), java.awt.Color.decode("#9C0006"));
            PdfPCell cellCPF = insertColumFormated(values[0], 12, 1, java.awt.Color.decode("#FFC7CE"), java.awt.Color.decode("#9C0006"));
            PdfPCell cellCustommers = insertColumFormated(values[1], 12, 1, java.awt.Color.decode("#FFC7CE"), java.awt.Color.decode("#9C0006"));
            PdfPCell cellTR = insertColumFormated(values[2], 12, 1, java.awt.Color.decode("#FFC7CE"), java.awt.Color.decode("#9C0006"));
            PdfPCell cellSituation = insertColumFormated(values[3], 12, 1, java.awt.Color.decode("#FFC7CE"), java.awt.Color.decode("#9C0006"));

//            PdfPCell cellTR = new PdfPCell(new Phrase(
//                    new Chunk(,
//                            new Font(Font.STRIKETHRU, 12))));
//            // cellCont.setBorder(Rectangle.NO_BORDER); // Remove a borda
//            cellTR.setBorder(Rectangle.BOX); // Remove a borda
//            cellTR.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cellTR.setBackgroundColor(java.awt.Color.decode("#FFC7CE"));
            table.addCell(cellCont);
            table.addCell(cellCPF);
            table.addCell(cellCustommers);
            table.addCell(cellTR);
            table.addCell(cellSituation);

            //use as debaixo e exlcua as de cima caso se arrependa das cores 
//            table.addCell((cont > 9 ? cont + "" : "0" + cont)).setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(values[0]).setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(values[1]).setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(values[2]).setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(values[3]).setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        Anchor anchorTarget = downloadCPFs(sale, cont + " Registros de vendas ");

        document.add(anchorTarget);
        document.add(new Paragraph("\n"));
        cont = 0;
        document.add(table);
        document.add(new Paragraph("\n\n"));
        footer(path);

    }

    private PdfPCell insertColumFormated(String value, Color color) {
        PdfPCell cell = new PdfPCell(new Phrase(
                new Chunk(value,
                        new Font(Font.STRIKETHRU, 12))));
        cell.setBorder(Rectangle.ALIGN_UNDEFINED); // Remove a borda
        //cell.setBorder(Rectangle.BOX); // Remove a borda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(color);
        return cell;
    }

    private PdfPCell insertColumFormated(String value, int sizeLetter, int style, Color color, Color colorForeground) {
        PdfPCell cell = new PdfPCell(new Phrase(
                new Chunk(value,
                        new Font(Font.STRIKETHRU, sizeLetter, style, colorForeground))));
        cell.setBorder(Rectangle.ALIGN_UNDEFINED); // Remove a borda
        //cell.setBorder(Rectangle.BOX); // Remove a borda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(color);
        return cell;
    }

        private Anchor downloadCPFs(List<Sales> sale, String msg) {
            FileWriter writer = null;
            String reference = "C:\\Users\\mathe\\Downloads\\CPFs.txt";

            File file = new File(reference);
            if (!file.exists()) {
                file.mkdir();
            }
          //  reference += "/CPFs.txt";
            try {
                writer = new FileWriter(reference);
                int i = 1;
                for (Sales values : sale) {
                 //   writer.write("CPF " + i + ": " + values.getCpf() + "\n");
                    writer.write(values.getCpf() + "\n");
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

    private Paragraph insertColumFormatedParagraph(String value, int sizeLetter, int style, Color color, Color colorForeground) {
        Paragraph cell = new Paragraph(new Phrase(
                new Chunk(value,
                        new Font(Font.STRIKETHRU, sizeLetter, style, colorForeground))));
        return cell;
    }

    public void footer(String path) {
        Paragraph p = insertColumFormatedParagraph("Desenvolved by Seller Carlos Matheus", 12, 0, Color.red, Color.red);
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

    public void toExportPDFService(List<Sales> sales, List<MonthsYear> monthsYear, String path, int qtdTot, float valueTot) {

        head(sales.get(0), monthsYear, path, valueTot, qtdTot);
        body(sales);

    }

    public static void main(String[] args) {
        ToPDF t = new ToPDF();
//        Sales sale = new Sales(new Vendedor("799469"), LocalDateTime.now(),
//                "75465356", "Eu", "65465465", Packages.I_400MB.toString(),
//                0, LocalDateTime.now(), Period.SELECT,
//                Origin.SELECT, Situation.SELECT, "Ai pai para");
//        List<MonthsYear> mon = new ArrayList<>();
//        List<Sales> sales = new ArrayList<>();
//        sales.add(sale);
//        mon.add(MonthsYear.MAY);
//        mon.add(MonthsYear.AUGUST);
//        mon.add(MonthsYear.DECEMBER);
//        MonthsYear monthYear = MonthsYear.valueOf(sale.getInstallationMarked().getMonth().name());
//        //t.head(sale, "Aqui deu cert", monthYear,"FilesPDF.pdf");
//        // t.head(sale, mon, mon.toString() + ".pdf");
//        t.toExportPDFService(sales, mon, "C:/Users/mathe/Downloads/abr", 0, 0);
//        t.body(SalesDAO.returnData('c', LocalDate.of(2024, Month.MARCH, 13), LocalDate.now()));
//        t.head(new Sales(new Vendedor("799469"), LocalDateTime.now(), "75465356",
//                "Eu", "65465465", packages, 0, LocalDateTime.MIN, Period.SELECT, Origin.SELECT, Situation.SELECT, observation), 5,LocalDateTime.now() , Period.SELECT, Origin.SELECT, Situation.SELECT, "Era yna vez"));
    }
}
