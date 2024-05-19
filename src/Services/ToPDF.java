package Services;

import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Sales;
import Model.Vendedor;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dynalink.linker.support.Guards;

public class ToPDF {

    public ToPDF() {
       

    }
public void head(Sales sale, String Tile){
       Document document = new Document();

        try {
            FileOutputStream file = new FileOutputStream("FilesPDF.pdf");
            PdfWriter.getInstance(document, file);
            document.open();
            Paragraph p = new Paragraph("Relatorio De Vendas Mês "+sale.getInstallationMarked().getMonth().name());
            p.setAlignment(Element.ALIGN_TOP);
            
            
            document.add(p);
            System.out.println("Deu ccerto ");
            document.close();                                           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ToPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    public static void main(String[] args) {
        ToPDF t = new ToPDF();
        t.head(new Sales(new Vendedor("799469"), LocalDateTime.now(), "75465356",
                "Eu", "65465465", packages, 0, LocalDateTime.MIN, Period.SELECT, Origin.SELECT, Situation.SELECT, observation), 5,LocalDateTime.now() , Period.SELECT, Origin.SELECT, Situation.SELECT, ""), "");
    }
}
