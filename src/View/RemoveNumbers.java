    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author mathe
 */
public class RemoveNumbers extends javax.swing.JFrame {

    /**
     * Creates new form RemoveNumbers
     */
    public RemoveNumbers() {
        initComponents();
        txaNumbers.requestFocus();
        returnDDDTxt(path);
    }
    String[] numbersToRemove;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaNumbers = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaNumberUpadat = new javax.swing.JTextArea();
        lbl2 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        txtNumbers = new javax.swing.JTextField();
        lblNumberInserts = new javax.swing.JLabel();
        lblNUmsUpdat = new javax.swing.JLabel();
        lblNumberRemoved = new javax.swing.JLabel();
        lblAllnumbersDeleted = new javax.swing.JLabel();
        lblQtdNumsTxt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Retirar numeros");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txaNumbers.setColumns(20);
        txaNumbers.setRows(5);
        txaNumbers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaNumbersKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txaNumbers);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 154, 154, 268));

        txaNumberUpadat.setColumns(20);
        txaNumberUpadat.setRows(5);
        jScrollPane1.setViewportView(txaNumberUpadat);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 154, 268));

        lbl2.setText("Numeros ");
        jPanel1.add(lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        lbl3.setText("Numeros atualizados");
        jPanel1.add(lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, -1, -1));

        txtNumbers.setBackground(new java.awt.Color(255, 255, 255));
        txtNumbers.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Numeros a retirar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtNumbers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumbersKeyPressed(evt);
            }
        });
        jPanel1.add(txtNumbers, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 190, 40));

        lblNumberInserts.setText("jLabel1");
        jPanel1.add(lblNumberInserts, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 428, -1, -1));

        lblNUmsUpdat.setText("jLabel1");
        jPanel1.add(lblNUmsUpdat, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, -1, -1));

        lblNumberRemoved.setForeground(new java.awt.Color(255, 0, 51));
        lblNumberRemoved.setText("jLabel1");
        jPanel1.add(lblNumberRemoved, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        lblAllnumbersDeleted.setBackground(new java.awt.Color(255, 255, 255));
        lblAllnumbersDeleted.setForeground(new java.awt.Color(255, 0, 51));
        jPanel1.add(lblAllnumbersDeleted, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 850, 20));

        lblQtdNumsTxt.setText("jLabel1");
        jPanel1.add(lblQtdNumsTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 270, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 864, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
      String path = "D:\\Meus Arquivos\\Minhas Vendas\\Oi Fibra\\Programas Venndas\\VendasFibra\\src\\arquivosTxT\\arquivo.txt";
String pathNumsProibed = "D:\\Meus Arquivos\\Minhas Vendas\\Oi Fibra\\Programas Venndas\\VendasFibra\\src\\arquivosTxT\\numerosProibidos.txt";
  
    public void insertDDDTxt() {

        try (BufferedWriter bfwritter = new BufferedWriter(new FileWriter(path))) {
            bfwritter.write(txtNumbers.getText());
            bfwritter.close();
            System.out.println("Deu obm");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao transferir para o arquivo\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao transferir para o arquivo: " + e);
        }
        txaNumbers.requestFocus();
    }

    public void insertProhibedNumberTxt(List<String> values) {
       try (BufferedWriter bfwritter = new BufferedWriter(new FileWriter(pathNumsProibed,true))) {
            for (String p : values) {
                bfwritter.write(p + "\n");
            }
            System.out.println("Números proibidos foram gravados com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao transferir para o arquivo\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao transferir para o arquivo: " + e);
        }
    }

    public void returnDDDTxt(String path) {

        try (BufferedReader bfr = new BufferedReader(new FileReader(path))) {

            txtNumbers.setText(bfr.readLine());
            bfr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao transferir para o arquivo\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao transferir para o arquivo: " + e);
        }
        txaNumbers.requestFocus();
    }
    public void returnQtdNumsProibiedTxt(String path) {

        try (BufferedReader bfr = new BufferedReader(new FileReader(path))) {

            lblQtdNumsTxt.setText(bfr.lines().count()+" Totais proibidos na lista");
            bfr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao transferir para o arquivo\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao transferir para o arquivo: " + e);
        }
        txaNumbers.requestFocus();
    }

    private void txaNumbersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaNumbersKeyPressed
                                           
    if (evt.getKeyCode() == KeyEvent.VK_TAB) {
        numbersToRemove = txtNumbers.getText().trim().split(" ");
        Map<String, Integer> valuesMap = new HashMap<>();
        List<String> numberProhibed = new ArrayList<>();
        String[] values = txaNumbers.getText().trim().split("\n");
        int alter = 0;
        List<String> numbersUpadates = new ArrayList<>();
        int cont = 0;
        for (int i = values.length - 1; i > (-1); i--) {
            if (values[i].trim().length() > 11) {
                values[i] = values[i].substring(2, values[i].length());
            }
            numbersUpadates.add(values[i]);
            if (values[i].trim().length() >= 2) {
                String ddd = values[i].trim().substring(0, 2);
                System.out.println("value ddd: " + ddd);
                for (int j = 0; j < numbersToRemove.length; j++) {
                    if (ddd.contains(numbersToRemove[j]) || ddd.equals(numbersToRemove[j])) {
                        if (valuesMap.containsKey(numbersToRemove[j])) {
                            valuesMap.replace(ddd, (valuesMap.get(numbersToRemove[j]) + 1));
                        } else {
                            valuesMap.put(ddd, 1);
                        }
                        numbersUpadates.remove(numbersUpadates.size() - 1);
                        numberProhibed.add(values[i]);
                        cont++;
                    }
                }
            }
        }
        valuesMap.forEach((key, value) -> {
            lblAllnumbersDeleted.setText(lblAllnumbersDeleted.getText() + " DDD " + key + ": " + value + "x     ");
        });
        for (int i = numbersUpadates.size() - 1; i > -1; i--) {
            txaNumberUpadat.setText(txaNumberUpadat.getText() + "\n" + numbersUpadates.get(i));
        }
        lblNumberInserts.setText(values.length + " Numeros inseridos");
        lblNumberRemoved.setText(cont + " Numeros Removidos");
        lblNUmsUpdat.setText(txaNumberUpadat.getLineCount() + " Numeros");
        insertProhibedNumberTxt(numberProhibed);
        returnQtdNumsProibiedTxt(pathNumsProibed);
        
    }

    }//GEN-LAST:event_txaNumbersKeyPressed

    private void txtNumbersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumbersKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            insertDDDTxt();
            numbersToRemove = txtNumbers.getText().split(" ");
            txaNumbers.requestFocus();
        }
    }//GEN-LAST:event_txtNumbersKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RemoveNumbers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoveNumbers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoveNumbers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoveNumbers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RemoveNumbers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lblAllnumbersDeleted;
    private javax.swing.JLabel lblNUmsUpdat;
    private javax.swing.JLabel lblNumberInserts;
    private javax.swing.JLabel lblNumberRemoved;
    private javax.swing.JLabel lblQtdNumsTxt;
    private javax.swing.JTextArea txaNumberUpadat;
    private javax.swing.JTextArea txaNumbers;
    private javax.swing.JTextField txtNumbers;
    // End of variables declaration//GEN-END:variables
}
