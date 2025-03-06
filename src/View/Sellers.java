package View;

import Controller.Formatting;
import Controller.SellerController;
import Model.Seller;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Sellers extends javax.swing.JFrame {

    String name = "";
    String TR = "";
    LocalDateTime ld;
    SellerController seller = new SellerController();
    Formatting format = new Formatting();

    public Sellers() {
        initComponents();
        txtName.requestFocus();
        hour().start();
        showTRs();
    }

    public Thread hour() {
        Thread hours = new Thread(() -> {
             while (true) {         
                 try {
                     txtDateRegistration.setText(format.dateTimeFormaterField(LocalDateTime.now()));
                     Thread.sleep(1000);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(Sellers.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
        });
      return hours;
    }

    private void showTRs() {
        DefaultTableModel tbl = (DefaultTableModel) tblSellers.getModel();
        tbl.setRowCount(0);
        List<Seller> sellers = seller.returnAllSeller();
        txtID.setText(sellers.size() + 1 + "");

        sellers.forEach(p -> {

            Object[] dados = {
                p.getIdentificador(),
                p.getNome(),
                p.getTr(),
                format.dateTimeFormaterField(p.getCadastroDataHora())
            };
            tbl.addRow(dados);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtTR = new javax.swing.JTextField();
        txtDateRegistration = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSellers = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Nome", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });
        jPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 180, 40));

        txtTR.setBackground(new java.awt.Color(255, 255, 255));
        txtTR.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "TR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtTR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTRKeyPressed(evt);
            }
        });
        jPanel1.add(txtTR, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 120, 40));

        txtDateRegistration.setBackground(new java.awt.Color(255, 255, 255));
        txtDateRegistration.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "Data de Cadastro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtDateRegistration.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDateRegistrationKeyPressed(evt);
            }
        });
        jPanel1.add(txtDateRegistration, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 130, 40));

        txtID.setBackground(new java.awt.Color(255, 255, 255));
        txtID.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)), "ID", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDKeyPressed(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 60, 40));

        tblSellers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nome", "TR", "Data cadastrado"
            }
        ));
        jScrollPane1.setViewportView(tblSellers);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 650, 90));

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 90, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            name = txtName.getText();
            txtTR.requestFocus();
        }
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtTRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTRKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            TR = txtName.getText();
            txtDateRegistration.requestFocus();
        }
    }//GEN-LAST:event_txtTRKeyPressed

    private void txtDateRegistrationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDateRegistrationKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            ld = format.dateTimeFormaterBank(txtDateRegistration.getText());

        }
    }//GEN-LAST:event_txtDateRegistrationKeyPressed

    private void txtIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        insertUser();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void insertUser() {
        TR = txtTR.getText();
        name = txtName.getText();
        ld = format.dateTimeFormaterBank(txtDateRegistration.getText());

        seller.insertSellerController(new Seller(ld, name, TR));

        showTRs();
        
        txtName.setText("");
        txtTR.setText("");
        txtName.requestFocus();
    }

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            insertUser();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sellers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSellers;
    private javax.swing.JTextField txtDateRegistration;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtTR;
    // End of variables declaration//GEN-END:variables
}
