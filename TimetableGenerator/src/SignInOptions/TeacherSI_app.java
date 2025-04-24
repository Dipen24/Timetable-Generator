package SignInOptions;

import javax.swing.JOptionPane;
import utils.UserAuthenticator;
import TeacherPortal.TeacherDashboard;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class TeacherSI_app extends javax.swing.JFrame {

        public TeacherSI_app() {
                initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                teacherBuilder_panel = new javax.swing.JPanel();
                T_back_button = new javax.swing.JButton();
                T_Builder_label = new javax.swing.JLabel();
                T_login_panel = new javax.swing.JPanel();
                T_user_label = new javax.swing.JLabel();
                T_Pass_label = new javax.swing.JLabel();
                T_user_TF = new javax.swing.JTextField();
                T_Pass_PF = new javax.swing.JPasswordField();
                T_showPass_CB = new javax.swing.JCheckBox();
                T_Login_button = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                teacherBuilder_panel.setBackground(new java.awt.Color(191, 221, 255));
                teacherBuilder_panel.setPreferredSize(new java.awt.Dimension(1196, 695));

                T_back_button.setBackground(new java.awt.Color(191, 221, 255));
                T_back_button.setFont(new java.awt.Font("Qualy", 1, 24)); // NOI18N
                T_back_button.setForeground(new java.awt.Color(20, 88, 165));
                T_back_button.setText("<-");
                T_back_button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                T_back_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                T_back_buttonActionPerformed(evt);
                        }
                });

                T_Builder_label.setFont(new java.awt.Font("Bahnschrift", 1, 24));
                T_Builder_label.setForeground(new java.awt.Color(20, 88, 165));
                T_Builder_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                T_Builder_label.setText("Teacher Login");

                T_login_panel.setBackground(new java.awt.Color(191, 221, 255));

                T_user_label.setFont(new java.awt.Font("Segoe UI", 1, 14));
                T_user_label.setForeground(new java.awt.Color(20, 88, 165));
                T_user_label.setText("Username:");

                T_Pass_label.setFont(new java.awt.Font("Segoe UI", 1, 14));
                T_Pass_label.setForeground(new java.awt.Color(20, 88, 165));
                T_Pass_label.setText("Password:");

                T_user_TF.setBackground(new java.awt.Color(255, 255, 255));
                T_user_TF.setFont(new java.awt.Font("Segoe UI", 0, 14));

                T_Pass_PF.setBackground(new java.awt.Color(255, 255, 255));
                T_Pass_PF.setFont(new java.awt.Font("Segoe UI", 0, 14));

                T_showPass_CB.setBackground(new java.awt.Color(191, 221, 255));
                T_showPass_CB.setFont(new java.awt.Font("Segoe UI", 0, 12));
                T_showPass_CB.setForeground(new java.awt.Color(20, 88, 165));
                T_showPass_CB.setText("Show Password");
                T_showPass_CB.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                T_showPass_CBActionPerformed(evt);
                        }
                });

                T_Login_button.setBackground(new java.awt.Color(20, 88, 165));
                T_Login_button.setFont(new java.awt.Font("Segoe UI", 1, 14));
                T_Login_button.setForeground(new java.awt.Color(255, 255, 255));
                T_Login_button.setText("Login");
                T_Login_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                T_Login_buttonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout T_login_panelLayout = new javax.swing.GroupLayout(T_login_panel);
                T_login_panel.setLayout(T_login_panelLayout);
                T_login_panelLayout.setHorizontalGroup(
                                T_login_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(T_login_panelLayout.createSequentialGroup()
                                                                .addGap(59, 59, 59)
                                                                .addGroup(T_login_panelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(T_user_TF)
                                                                                .addComponent(T_Pass_PF)
                                                                                .addGroup(T_login_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(T_login_panelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(T_user_label)
                                                                                                                .addComponent(T_Pass_label)
                                                                                                                .addComponent(T_showPass_CB)
                                                                                                                .addComponent(T_Login_button,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                100,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                .addGap(59, 59, 59))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                T_login_panelLayout.createSequentialGroup()
                                                                                .addGap(184, 184, 184)
                                                                                .addGroup(T_login_panelLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(T_Login_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGap(181, 181, 181)));
                T_login_panelLayout.setVerticalGroup(
                                T_login_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(T_login_panelLayout.createSequentialGroup()
                                                                .addGap(72, 72, 72)
                                                                .addComponent(T_user_label)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(T_user_TF,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                30,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(T_Pass_label)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(T_Pass_PF,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                30,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(T_showPass_CB)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(T_Login_button,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                30,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(72, Short.MAX_VALUE)));

                javax.swing.GroupLayout teacherBuilder_panelLayout = new javax.swing.GroupLayout(teacherBuilder_panel);
                teacherBuilder_panel.setLayout(teacherBuilder_panelLayout);
                teacherBuilder_panelLayout.setHorizontalGroup(
                                teacherBuilder_panelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(teacherBuilder_panelLayout.createSequentialGroup()
                                                                .addGroup(teacherBuilder_panelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(teacherBuilder_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(355, 355, 355)
                                                                                                .addComponent(T_login_panel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(teacherBuilder_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(401, 401, 401)
                                                                                                .addComponent(T_Builder_label,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(72, 72, 72)))
                                                                .addGap(345, 345, 345))
                                                .addGroup(teacherBuilder_panelLayout.createSequentialGroup()
                                                                .addGap(27, 27, 27)
                                                                .addComponent(T_back_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(1089, 1089, 1089)));
                teacherBuilder_panelLayout.setVerticalGroup(
                                teacherBuilder_panelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(teacherBuilder_panelLayout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addComponent(T_back_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                46, Short.MAX_VALUE)
                                                                .addGap(8, 8, 8)
                                                                .addComponent(T_Builder_label,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(T_login_panel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(108, 108, 108)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(teacherBuilder_panel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(teacherBuilder_panel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        private void T_back_buttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_S_back_buttonActionPerformed
                SIO_app sio = new SIO_app();
                sio.show();

                dispose();
        }// GEN-LAST:event_S_back_buttonActionPerformed

        private void T_showPass_CBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_S_showPass_CBActionPerformed
                if (T_showPass_CB.isSelected()) {
                        T_Pass_PF.setEchoChar((char) 0);
                } else {
                        T_Pass_PF.setEchoChar('•');
                }
        }// GEN-LAST:event_S_showPass_CBActionPerformed

        private void T_Login_buttonActionPerformed(java.awt.event.ActionEvent evt) {
                String username = T_user_TF.getText();
                String password = new String(T_Pass_PF.getPassword());

                UserAuthenticator authenticator = new UserAuthenticator();
                if (authenticator.authenticateTeacher(username, password)) {
                        // Get teacher's full name from teachers.csv
                        String fullName = authenticator.getTeacherFullName(username);
                        if (fullName != null) {
                                new TeacherDashboard(username, fullName).setVisible(true);
                                dispose();
                        } else {
                                JOptionPane.showMessageDialog(this,
                                                "Error retrieving teacher information",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                } else {
                        JOptionPane.showMessageDialog(this,
                                        "Invalid username or password",
                                        "Login Failed",
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
                        java.util.logging.Logger.getLogger(TeacherSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(TeacherSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(TeacherSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(TeacherSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new TeacherSI_app().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel T_Builder_label;
        private javax.swing.JButton T_Login_button;
        private javax.swing.JPasswordField T_Pass_PF;
        private javax.swing.JLabel T_Pass_label;
        private javax.swing.JButton T_back_button;
        private javax.swing.JPanel T_login_panel;
        private javax.swing.JCheckBox T_showPass_CB;
        private javax.swing.JTextField T_user_TF;
        private javax.swing.JLabel T_user_label;
        private javax.swing.JPanel teacherBuilder_panel;
        // End of variables declaration//GEN-END:variables
}
