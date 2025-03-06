package Services;

import Controller.Formatting;
import Controller.SalesController;
import DAO.ConnectFactory;
import Dao.SalesDAO;
import Model.Enums.Origin;
import Model.Enums.Packages;
import Model.Enums.PartnerShip;
import Model.Enums.Period;
import Model.Enums.ToPrioritize;
import Model.Enums.Situation;
import Model.Sales;
import Model.Seller;
import View.Loading;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaleService {

    String sheet = ConnectFactory.URL == "jdbc:mysql://localhost:3306/vendasFibraTest" ? "VendasFibra2024Test" : "VendasFibra2024";
    //String pathInsertUpdateAndSearch = "C:\\Users\\mathe\\Downloads\\" + sheet + ".xlsx";
    String pathInsertUpdateAndSearch = "D:\\Meus Arquivos\\Minhas Vendas\\Oi Fibra\\Programas Venndas\\VendasFibra\\" + sheet + ".xlsx";

    Formatting format = new Formatting();
    //SalesController sc = new SalesController();

    public static float ValuePerSale(int qtdInstalled, Packages packages) {
        float value = 0;
        TreeMap<Integer, Float> p400MB = new TreeMap<Integer, Float>() {
            {
                put(41, 50.0f);
                put(30, 45.0f);
                put(20, 40.0f);
                put(0, 35.0f);
            }
        };

        TreeMap<Integer, Float> p500MB = new TreeMap<Integer, Float>() {
            {
                put(41, 60.0f);
                put(30, 50.0f);
                put(20, 45.0f);
                put(0, 35.0f);
            }
        };
        if (packages != Packages.SELECT) {

            if (packages == Packages.I_400MB) {
                for (Map.Entry<Integer, Float> entry : p400MB.descendingMap().entrySet()) {
                    if (qtdInstalled >= entry.getKey()) {
                        value = entry.getValue();
                        break;
                    }
                }
            } else {
                for (Map.Entry<Integer, Float> entry : p500MB.descendingMap().entrySet()) {
                    if (qtdInstalled >= entry.getKey()) {
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
    public static float ValuePerSaleSKY(Packages packages) {
        float value = 0;
        if (packages.equals(Packages.I_400MB)) {
            value = 30;
        }else{
            value = 35;
        }
        return value;
    }

    public static Map<String, Float> returnValuesPlanService(int qtdInstalled, Packages packages) {
        Map<String, Float> values = new HashMap<>();

        TreeMap<Integer, Float> p400MB = new TreeMap<Integer, Float>() {
            {
                put(41, 50.0f);
                put(30, 45.0f);
                put(20, 40.0f);
                put(0, 35.0f);
            }
        };

        TreeMap<Integer, Float> p500MB = new TreeMap<Integer, Float>() {
            {
                put(41, 60.0f);
                put(30, 50.0f);
                put(20, 45.0f);
                put(0, 35.0f);
            }
        };
        if (packages == Packages.I_400MB) {
            for (Map.Entry<Integer, Float> entry : p400MB.descendingMap().entrySet()) {
                if (qtdInstalled >= entry.getKey()) {
                    values.put("400MB", entry.getValue());
                    break;
                }
            }
        } else {
            for (Map.Entry<Integer, Float> entry : p500MB.descendingMap().entrySet()) {
                if (qtdInstalled >= entry.getKey()) {
                    values.put("500MB", entry.getValue());
                    break;
                }
            }
        }
        return values;

    }

    public List<Sales> searchSellsPlanilhaService() {

        List<Sales> sales = new ArrayList<>();

        try {
            File file = new File(pathInsertUpdateAndSearch);
            FileInputStream filePlani = new FileInputStream(file);

            XSSFWorkbook workbook;

            ///cria um workboo que e o mesmo que plailha com todas as abas
            workbook = new XSSFWorkbook(filePlani);
            ///recupernado uma aba da planilha
            XSSFSheet sheet = workbook.getSheetAt(0);
            //retora todas as linhas da planilha a aba 0
            Iterator<Row> rowIterator = sheet.iterator();
            ///varre todas as linnhas da plannnilha
            int i = 0;
            while (rowIterator.hasNext()) {
                Row next = rowIterator.next();
                if (i > 0) {

                    System.out.println("Valor da linha " + i + ": " + next.getCell(0));
                    int seller = (int) (Float.parseFloat(next.getCell(12) + ""));

//                    String obs = next.getCell(9) + "";
//                    String obs2 = obs.toLowerCase();
//                    if (obs2.contains("trhigo") || obs2.contains("higor") || obs2.contains("tr797118")) {
//                        seller = 2;
//                        
//                    } else {
//                        seller = 1;
//                    }
//                    next.createCell(12).setCellValue(seller);
//                     FileOutputStream outputStream = new FileOutputStream(file);
//            workbook.write(outputStream);
//            outputStream.close();
                    Period period = (next.getCell(7) + "").contains("Manh") ? Period.MORNING : Period.AFTERNOON;
                    LocalTime time;
                    if (period == Period.AFTERNOON) {
                        time = LocalTime.of(13, 00);
                    } else {
                        time = LocalTime.of(8, 00);

                    }
                    float value = next.getCell(4) == null ? 0 : Float.parseFloat(next.getCell(4) + "");
                    if (value == 0) {
                        if ((next.getCell(3) + "") == Packages.I_400MB.toString()) {
                            System.out.println("é igual? " + (next.getCell(3) + "  +=== " + Packages.I_400MB.toString()) + "\n\n");
                            value = 50;
                        }
                        if ((next.getCell(3) + "") != Packages.I_400MB.toString()) {
                            System.out.println("é igual? " + (next.getCell(3) + "  +=== " + Packages.I_400MB.toString()) + "\n\n");

                            value = 60;
                        }

                    }
                    System.out.println("Formato da data: " + next.getCell(6));
                    Origin ori = next.getCell(10) != null ? Origin.fromString(next.getCell(10) + "") : Origin.CHAT;
                    LocalDate dtInstalation;
                    try {
                        dtInstalation = format.dateFormaterBankExcel(next.getCell(6) + "") == null ? LocalDate.parse("2001-05-07") : format.dateFormaterBankExcel(next.getCell(6) + "");

                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Erro: " + e);
                        //JOptionPane.showMessageDialog(null, "erro " + e, "Dados incorretos", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Erro: " + e);
                        //JOptionPane.showMessageDialog(null, "erro " + e, "Dados incorretos", JOptionPane.ERROR_MESSAGE);
//
                        dtInstalation = format.dateFormaterBank(next.getCell(6) + "") == null ? LocalDate.parse("2001-05-07") : format.dateFormaterBank(next.getCell(6) + "");
                        //JOptionPane.showMessageDialog(null, "Sucesso " + e, "Sucesso depois do erro", JOptionPane.INFORMATION_MESSAGE);
                    }
                    LocalDateTime ldt;
                    try {
                        ldt = (next.getCell(5) + "").length() > 12 ? format.dateFormaterTimeBankExcel(next.getCell(5) + "") : format.dateFormaterTimeBankExcel(next.getCell(5) + " 13:00");
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Erro: " + e);
                        //JOptionPane.showMessageDialog(null, "erro " + e, "Dados incorretos data feita", JOptionPane.ERROR_MESSAGE);
                        ldt = (next.getCell(5) + "").length() > 12 ? format.dateTimeFormaterBank(next.getCell(5) + "") : format.dateTimeFormaterBank(next.getCell(5) + " 13:00");
                        //JOptionPane.showMessageDialog(null, "Sucesso " + e, "Sucesso depois do erro", JOptionPane.INFORMATION_MESSAGE);

                    }
//                    Situation situ;
//                    PartnerShip partn;
//                    if (next.getCell(8) == null) {
//                        situ = Situation.PROVISIONING;  //situation;
//
//                    } else {
//                        situ = Situation.fromString(next.getCell(8) + "");
//                    }
//                    if (next.getCell(13) == null) {
//                        partn = PartnerShip.OI;  //situation;
//
//                    } else {
//                        partn = PartnerShip.fromString(next.getCell(13) + "");  
//                    }
                    
                    Iterator<Cell> cellIterato = next.cellIterator();

                    sales.add(new Sales(new Seller(seller),
                            ldt,
                            next.getCell(0) + "", //cpf
                            next.getCell(1) + "", //customers
                            next.getCell(2) + "", //contact
                            next.getCell(3) + "", //packages
                            value, //valuePackage
                            dtInstalation.atTime(time), //installationMarked
                            period, //period
                            Origin.fromString(next.getCell(10) == null ? "Chat" : next.getCell(10) + ""),//origin
                            Situation.fromString(next.getCell(8) + ""), //situation;
                            PartnerShip.fromString(next.getCell(13) + ""),
                            next.getCell(9) + "", //observation
                            ToPrioritize.fromString(next.getCell(11) + "") //ToPrioritize
                    ));
                    System.out.println("Data instalação: " + dtInstalation.atTime(time));
                    System.out.println("Vendedor: " + seller + "\n"
                            + "Data feita: " + ldt + "\n"
                            + "customers: " + next.getCell(1) + "\n"
                            + "contact: " + next.getCell(2) + "\n"
                            + "packages: " + next.getCell(3) + "\n"
                            + "Data instalação: " + dtInstalation.atTime(time) + "\n"
                            + "period: " + Period.fromString(next.getCell(7) + "") + "\n"
                            + "Origin: " + ori + "\n"
                            + "observation: " + next.getCell(9) + "\n"
                            + "cpf: " + next.getCell(0) + "\n"
                            + "valuePackage: " + next.getCell(4) + "\n"
                            + "Parceira: " + next.getCell(13) + "\n"
                            + "observation: " + next.getCell(9) + "\n"
                            + "Priotize: " + next.getCell(11) + "\n"
                    );

                }
                i++;

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sales;
    }

   

    public void insertSellExcel(Sales sale) {
        File file = new File(pathInsertUpdateAndSearch);
        FileInputStream filePlani;

        try {
            filePlani = new FileInputStream(file);
            XSSFWorkbook workbook;

            ///cria um workboo que e o mesmo que plailha com todas as abas
            workbook = new XSSFWorkbook(filePlani);
            ///recupernado uma aba da planilha
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Cria uma nova linha após a última linha existente
            int lastRowNum = sheet.getLastRowNum();
            XSSFRow newRow = sheet.createRow(lastRowNum + 1);

            // Adicione células à nova linha conforme necessário
            newRow.createCell(0).setCellValue(sale.getCpf());
            newRow.createCell(1).setCellValue(sale.getCustomers());
            newRow.createCell(2).setCellValue(sale.getContact());
            newRow.createCell(3).setCellValue(sale.getPackages());
            newRow.createCell(4).setCellValue(sale.getValuePackage());
            newRow.createCell(5).setCellValue(format.dateTimeFormaterField(sale.getSellDateHour()) + "");
            newRow.createCell(6).setCellValue((format.dateFormaterField(sale.getInstallationMarked().toLocalDate())) + "");
            newRow.createCell(7).setCellValue(sale.getPeriod().toString());
            newRow.createCell(8).setCellValue(sale.getSituation().toString());
            newRow.createCell(9).setCellValue(sale.getObservation());
            newRow.createCell(10).setCellValue(sale.getOrigin().toString());
            newRow.createCell(11).setCellValue(sale.getPrioritize().toString());
            newRow.createCell(12).setCellValue(sale.getSeller().getIdentificador());
            newRow.createCell(13).setCellValue(sale.getPartnetship().name());
//CPF	Cliente	Contato	Pacote	Comissão	Data 	Data Instalação	Periodo	Situação	Obersavação	Origem
System.out.println("aeqEXCEKL salvo");
            // Escreva as alterações de volta para o arquivo
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showInputDialog(null, "Arquivo excel não encontrado\nFavor cole o PATH do arquivo!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showInputDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro: " + ex);
        }

    }
    int o = 1;

    public  void insertAllorManySellExcel(List<Sales> sales) {
        File file = new File(pathInsertUpdateAndSearch);
        FileInputStream filePlani = null;
        XSSFWorkbook workbook = null;
        FileOutputStream outputStream = null;

        try {
            filePlani = new FileInputStream(file);
            workbook = new XSSFWorkbook(filePlani);
            XSSFSheet sheet = workbook.getSheetAt(0);

           // int lastRowNum = sheet.getLastRowNum();
             int lastRowNum = 0;

            for (Sales sale : sales) {
                XSSFRow newRow = sheet.createRow(++lastRowNum);

                // Adicione células à nova linha conforme necessário
                newRow.createCell(0).setCellValue(sale.getCpf() + "");
                newRow.createCell(1).setCellValue(sale.getCustomers() + "");
// Limpeza do campo contact, removendo caracteres que não são números, espaços ou barras
String contact = sale.getContact().replaceAll("[^\\d/\\s]", "");
                newRow.createCell(2).setCellValue(contact);

                newRow.createCell(3).setCellValue(sale.getPackages() + "");
                newRow.createCell(4).setCellValue(sale.getValuePackage() + "");
                newRow.createCell(5).setCellValue(format.dateTimeFormaterField(sale.getSellDateHour()) + "");
                newRow.createCell(6).setCellValue(format.dateFormaterField(sale.getInstallationMarked().toLocalDate()) + "");
                newRow.createCell(7).setCellValue(sale.getPeriod().toString());
                newRow.createCell(8).setCellValue(sale.getSituation().toString());
                newRow.createCell(9).setCellValue(sale.getObservation());
                newRow.createCell(10).setCellValue(sale.getOrigin().toString());
                newRow.createCell(11).setCellValue(sale.getPrioritize().toString());
                newRow.createCell(12).setCellValue(sale.getSeller().getIdentificador());
                newRow.createCell(13).setCellValue(sale.getPartnetship().name());
                System.out.println("Inserindo dados " + o + ":  na linha: " + newRow.getRowNum() + "\n    # "
                        + sale.getCpf() + "  "
                        + sale.getCustomers() + "  "
                        + sale.getContact() + "  "
                        + sale.getPackages() + "  "
                        + sale.getValuePackage() + "  "
                        + format.dateTimeFormaterField(sale.getSellDateHour()) + "  "
                        + format.dateFormaterField(sale.getInstallationMarked().toLocalDate()) + "  "
                        + sale.getPeriod().toString() + "  "
                        + sale.getSituation().toString() + "  "
                        + sale.getObservation() + "  "
                        + sale.getOrigin().toString() + "  "
                        + sale.getPrioritize().toString() + "  "
                        + sale.getSeller().getIdentificador()
                );
                o++;
            }

            // Escreva as alterações de volta para o arquivo
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            
            JOptionPane.showMessageDialog(null, "Todos arquivivos salvos com sycesso", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo excel não encontrado\nFavor cole o PATH do arquivo!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro: " + ex);

        } finally {
            try {
                if (filePlani != null) {
                    filePlani.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateValuesExcel(Sales sale) {
        File file = new File(pathInsertUpdateAndSearch);
        FileInputStream filePlani;

        try {
            filePlani = new FileInputStream(file);
            XSSFWorkbook workbook;

            ///cria um workboo que e o mesmo que plailha com todas as abas
            workbook = new XSSFWorkbook(filePlani);
            ///recupernado uma aba da planilha
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            while (rowIterator.hasNext()) {
                Row next = rowIterator.next();
                if (i > 0) {
                    String cpf = next.getCell(0).toString();
                    if (i == sale.getId() || cpf == sale.getCpf()) {
                        next.getCell(0).setCellValue(cpf); //cpf
                        next.getCell(1).setCellValue(sale.getCustomers()); //customers
                        next.getCell(2).setCellValue(sale.getContact()); //contact
                        next.getCell(3).setCellValue(sale.getPackages().toString()); //packages
                        next.getCell(4).setCellValue(sale.getValuePackage()); //Comissão
                        next.getCell(6).setCellValue(format.dateFormaterField(sale.getInstallationMarked().toLocalDate())); //Data Instalação	
                        next.getCell(7).setCellValue(sale.getPeriod().toString()); //Periodo
                        next.getCell(8).setCellValue(sale.getSituation().toString()); //Situação
                        try {
                            next.getCell(9).setCellValue(sale.getObservation()); //Obersavação

                        } catch (java.lang.NullPointerException e) {

                            System.out.println("valor do eee alterado: " + sale.getObservation().trim());
                            next.getCell(9).setCellValue(sale.getObservation().trim()); //Obersavação
                        }
                        next.getCell(10).setCellValue(sale.getOrigin().toString()); //Origem
                        next.getCell(11).setCellValue(sale.getPrioritize().toString()); //Priorizar
                        next.getCell(12).setCellValue(sale.getSeller().getIdentificador()); //id tr
                        next.getCell(13).setCellValue(sale.getPartnetship().name()); 
                        //   System.out.println("Dados Inseridos " + sale.getCustomers() + " " + sale.getContact() + " " + sale.getValuePackage() + " " + next.getCell(7) + " " + sale.getSituation().toString());
System.out.println("aeqEXCEKL salvo");
                    }
                }
                i++;

// Salvar as alterações
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);

                    fileOut.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateAllValuesExcel(float value, List<Integer> ids) {
        File file = new File(pathInsertUpdateAndSearch);
        FileInputStream filePlani;

        try {
            filePlani = new FileInputStream(file);
            XSSFWorkbook workbook;

            ///cria um workboo que e o mesmo que plailha com todas as abas
            workbook = new XSSFWorkbook(filePlani);
            ///recupernado uma aba da planilha
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            int idPosition = 0;

//            ids.forEach(p -> {
//                Row row = sheet.getRow(p);
//                row.getCell(3).setCellValue(value); // Comissão
//                try (FileOutputStream fileOut = new FileOutputStream(file)) {
//                    workbook.write(fileOut);
//
//                    fileOut.close();
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//            );
            while (rowIterator.hasNext()) {
                Row next = rowIterator.next();
                if (i > 0) {
                    if (idPosition < ids.size() && i == ids.get(idPosition)) {
                        next.getCell(4).setCellValue(value); // Comissão
                        idPosition++;

                    }
                    try (FileOutputStream fileOut = new FileOutputStream(file)) {
                        workbook.write(fileOut);

                        fileOut.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                i++;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateSeveralSalesExcel(List<Sales> sales) {
        File file = new File(pathInsertUpdateAndSearch);
        FileInputStream filePlani;

        try {
            filePlani = new FileInputStream(file);
            XSSFWorkbook workbook;

            ///cria um workboo que e o mesmo que plailha com todas as abas
            workbook = new XSSFWorkbook(filePlani);
            ///recupernado uma aba da planilha
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            int idPosition = 0;


            while (rowIterator.hasNext()) {
                Row next = rowIterator.next();
                if (i > 0) {
                    if (idPosition < sales.size() && i == sales.get(idPosition).getId()) {
                        next.getCell(8).setCellValue(sales.get(idPosition).getSituation().toString()); // situacao
                        next.getCell(9).setCellValue(sales.get(idPosition).getObservation()); // obs
                        idPosition++;

                    }
                    try (FileOutputStream fileOut = new FileOutputStream(file)) {
                        workbook.write(fileOut);

                        fileOut.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                i++;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaleService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
