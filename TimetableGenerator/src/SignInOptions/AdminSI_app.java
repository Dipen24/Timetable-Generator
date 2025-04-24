package SignInOptions;

import javax.swing.JOptionPane;
import utils.UserAuthenticator;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class AdminSI_app extends javax.swing.JFrame {

        public AdminSI_app() {
                initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                adminBuilder_panel = new javax.swing.JPanel();
                A_back_button = new javax.swing.JButton();
                A_Builder_label = new javax.swing.JLabel();
                A_login_panel = new javax.swing.JPanel();
                A_user_label = new javax.swing.JLabel();
                A_Pass_label = new javax.swing.JLabel();
                A_user_TF = new javax.swing.JTextField();
                A_Pass_PF = new javax.swing.JPasswordField();
                A_showPass_CB = new javax.swing.JCheckBox();
                A_Login_button = new javax.swing.JButton();
                adminNewUser_button = new JButton("New User? Register");

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                adminBuilder_panel.setBackground(new java.awt.Color(255, 248, 220));
                adminBuilder_panel.setPreferredSize(new java.awt.Dimension(1196, 695));

                A_back_button.setBackground(new java.awt.Color(255, 248, 220));
                A_back_button.setFont(new java.awt.Font("Qualy", 1, 24)); // NOI18N
                A_back_button.setForeground(new java.awt.Color(255, 204, 102));
                A_back_button.setText("<-");
                A_back_button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                A_back_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                A_back_buttonActionPerformed(evt);
                        }
                });

                A_Builder_label.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 36)); // NOI18N
                A_Builder_label.setText("TIMETABLE BUILDER");

                A_login_panel.setBackground(new java.awt.Color(255, 255, 255));

                A_user_label.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
                A_user_label.setText("Username");

                A_Pass_label.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
                A_Pass_label.setText("Password");

                A_user_TF.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                A_user_TF.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                A_user_TFActionPerformed(evt);
                        }
                });

                A_Pass_PF.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

                A_showPass_CB.setForeground(new java.awt.Color(51, 51, 51));
                A_showPass_CB.setText("Show Password");
                A_showPass_CB.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                A_showPass_CBActionPerformed(evt);
                        }
                });

                A_Login_button.setBackground(new java.awt.Color(255, 204, 102));
                A_Login_button.setFont(new java.awt.Font("Dubai", 1, 20)); // NOI18N
                A_Login_button.setForeground(new java.awt.Color(255, 255, 255));
                A_Login_button.setText("Log In");
                A_Login_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                A_Login_buttonActionPerformed(evt);
                        }
                });

                adminNewUser_button.setBackground(new Color(255, 204, 102));
                adminNewUser_button.setForeground(Color.WHITE);
                adminNewUser_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
                adminNewUser_button.setBorderPainted(false);
                adminNewUser_button.setFocusPainted(false);
                adminNewUser_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                adminNewUser_buttonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout A_login_panelLayout = new javax.swing.GroupLayout(A_login_panel);
                A_login_panel.setLayout(A_login_panelLayout);
                A_login_panelLayout.setHorizontalGroup(
                                A_login_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(A_login_panelLayout.createSequentialGroup()
                                                                .addGap(59, 59, 59)
                                                                .addGroup(A_login_panelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(A_user_TF)
                                                                                .addComponent(A_Pass_PF)
                                                                                .addGroup(A_login_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(A_login_panelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(A_user_label,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(A_Pass_label,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addGap(303, 303, 303))
                                                                                .addGroup(A_login_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(A_showPass_CB,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(258, 258, 258)))
                                                                .addGap(61, 61, 61))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                A_login_panelLayout.createSequentialGroup()
                                                                                .addGap(184, 184, 184)
                                                                                .addGroup(A_login_panelLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(A_Login_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(adminNewUser_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGap(181, 181, 181)));
                A_login_panelLayout.setVerticalGroup(
                                A_login_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(A_login_panelLayout.createSequentialGroup()
                                                                .addGap(72, 72, 72)
                                                                .addComponent(A_user_label,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                21, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(A_user_TF,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                47, Short.MAX_VALUE)
                                                                .addGap(44, 44, 44)
                                                                .addComponent(A_Pass_label,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                23, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(A_Pass_PF,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                47, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(A_showPass_CB,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                26, Short.MAX_VALUE)
                                                                .addGap(42, 42, 42)
                                                                .addComponent(A_Login_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                46, Short.MAX_VALUE)
                                                                .addGap(20, 20, 20)
                                                                .addComponent(adminNewUser_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                46, Short.MAX_VALUE)
                                                                .addGap(30, 30, 30)));

                javax.swing.GroupLayout adminBuilder_panelLayout = new javax.swing.GroupLayout(adminBuilder_panel);
                adminBuilder_panel.setLayout(adminBuilder_panelLayout);
                adminBuilder_panelLayout.setHorizontalGroup(
                                adminBuilder_panelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(adminBuilder_panelLayout.createSequentialGroup()
                                                                .addGroup(adminBuilder_panelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(adminBuilder_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(355, 355, 355)
                                                                                                .addComponent(A_login_panel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(adminBuilder_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(401, 401, 401)
                                                                                                .addComponent(A_Builder_label,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(72, 72, 72)))
                                                                .addGap(345, 345, 345))
                                                .addGroup(adminBuilder_panelLayout.createSequentialGroup()
                                                                .addGap(27, 27, 27)
                                                                .addComponent(A_back_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(1089, 1089, 1089)));
                adminBuilder_panelLayout.setVerticalGroup(
                                adminBuilder_panelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(adminBuilder_panelLayout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addComponent(A_back_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                46, Short.MAX_VALUE)
                                                                .addGap(8, 8, 8)
                                                                .addComponent(A_Builder_label,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(A_login_panel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(108, 108, 108)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(adminBuilder_panel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(adminBuilder_panel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        private void A_user_TFActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_A_user_TFActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_A_user_TFActionPerformed

        private void A_showPass_CBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_A_showPass_CBActionPerformed
                if (A_showPass_CB.isSelected()) {
                        A_Pass_PF.setEchoChar((char) 0);
                } else {
                        A_Pass_PF.setEchoChar('*');
                }
        }// GEN-LAST:event_A_showPass_CBActionPerformed

        private void A_back_buttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_A_back_buttonActionPerformed
                SIO_app sio = new SIO_app();
                sio.show();
                dispose();
        }// GEN-LAST:event_A_back_buttonActionPerformed

        private void adminNewUser_buttonActionPerformed(java.awt.event.ActionEvent evt) {
                new AdminRegister().setVisible(true);
                dispose();
        }

        private void A_Login_buttonActionPerformed(java.awt.event.ActionEvent evt) {
                String username = A_user_TF.getText();
                String password = new String(A_Pass_PF.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                        "Please enter both username and password",
                                        "Login Error",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (UserAuthenticator.validateLogin("admin", username, password)) {
                        JOptionPane.showMessageDialog(this,
                                        "Login successful!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                        new AdminPortal.AdminDashboard().setVisible(true);
                        dispose();
                } else {
                        JOptionPane.showMessageDialog(this,
                                        "Invalid username or password",
                                        "Login Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(AdminSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(AdminSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(AdminSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(AdminSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new AdminSI_app().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel A_Builder_label;
        private javax.swing.JButton A_Login_button;
        private javax.swing.JPasswordField A_Pass_PF;
        private javax.swing.JLabel A_Pass_label;
        private javax.swing.JButton A_back_button;
        private javax.swing.JPanel A_login_panel;
        private javax.swing.JCheckBox A_showPass_CB;
        private javax.swing.JTextField A_user_TF;
        private javax.swing.JLabel A_user_label;
        private javax.swing.JPanel adminBuilder_panel;
        private javax.swing.JButton adminNewUser_button;
        // End of variables declaration//GEN-END:variables
}
