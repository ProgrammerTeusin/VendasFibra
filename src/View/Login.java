package View;

import java.awt.Point;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Login extends javax.swing.JFrame {

    public static String nome;
    public static String usuario;
    public static String usuario2;
    public static String sobrenome;
    public static int id;

    public Login() {
        initComponents();   
        setResizable(false);
        txtUsuario.setText("teusin");
        pwdSenha.setText("123");
        btnAcesso.requestFocus();
        usuario = pwdSenha.getText();
    }
    private Point point = new Point();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtUsuario = new JTextField();
        btnAcesso = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        pwdSenha = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(8, 238, 5));
        setMinimumSize(new java.awt.Dimension(353, 420));
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(131, 131, 116));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 273, 32));

        btnAcesso.setBackground(new java.awt.Color(77, 255, 0));
        btnAcesso.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAcesso.setForeground(new java.awt.Color(255, 50, 50));
        btnAcesso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsImages/chave.png"))); // NOI18N
        btnAcesso.setText("Acessar");
        btnAcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcessoActionPerformed(evt);
            }
        });
        btnAcesso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAcessoKeyPressed(evt);
            }
        });
        jPanel1.add(btnAcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 110, 50));

        jButton1.setBackground(new java.awt.Color(244, 53, 53));
        jButton1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 130, 40));

        pwdSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pwdSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(pwdSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 273, 32));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsImages/icons8-usuário-96.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 104, 106));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsImages/senha.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 30, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconsImages/usuario.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 30, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 420, 350));

        jPanel2.setBackground(new java.awt.Color(65, 233, 11));

        jLabel4.setFont(new java.awt.Font("Georgia", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Login");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 70));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
//quando clicado a tecla enter caso os dados estejam corretos, abre a guia
    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            usuario = pwdSenha.getText();
            pwdSenha.requestFocus();
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void pwdSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwdSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAcesso.requestFocus();
        }
    }//GEN-LAST:event_pwdSenhaKeyPressed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        point.x = evt.getX();
        point.y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point g = this.getLocation();
        setLocation(g.x + evt.getX() - point.x, g.y + evt.getY() - point.y);
    }//GEN-LAST:event_formMouseDragged

    private void btnAcessoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAcessoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           // new VendasAtual().show();
           new VendasAtual().show();
           this.dispose();
        }
    }//GEN-LAST:event_btnAcessoKeyPressed

    private void btnAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcessoActionPerformed
           new VendasAtual().show();
        //new VendasAtual().show();
        this.dispose();
    }//GEN-LAST:event_btnAcessoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        float decisao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Sair?", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        if (decisao == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

  
    

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAcesso;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JPasswordField pwdSenha;
    public static javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
//070421  - password invert