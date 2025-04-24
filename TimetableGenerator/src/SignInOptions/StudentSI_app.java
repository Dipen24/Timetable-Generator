package SignInOptions;

import javax.swing.JOptionPane;
import utils.UserAuthenticator;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentSI_app extends javax.swing.JFrame {

        public StudentSI_app() {
                initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                studentBuilder_panel = new javax.swing.JPanel();
                S_back_button = new javax.swing.JButton();
                S_Builder_label = new javax.swing.JLabel();
                S_login_panel = new javax.swing.JPanel();
                S_user_label = new javax.swing.JLabel();
                S_Pass_label = new javax.swing.JLabel();
                S_user_TF = new javax.swing.JTextField();
                S_Pass_PF = new javax.swing.JPasswordField();
                S_showPass_CB = new javax.swing.JCheckBox();
                S_Login_button = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                studentBuilder_panel.setBackground(new java.awt.Color(255, 192, 203));
                studentBuilder_panel.setPreferredSize(new java.awt.Dimension(1196, 695));

                S_back_button.setBackground(new java.awt.Color(255, 192, 203));
                S_back_button.setFont(new java.awt.Font("Qualy", 1, 24));
                S_back_button.setForeground(new java.awt.Color(161, 29, 95));
                S_back_button.setText("<-");
                S_back_button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                S_back_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                S_back_buttonActionPerformed(evt);
                        }
                });

                S_Builder_label.setFont(new java.awt.Font("Bahnschrift", 1, 24));
                S_Builder_label.setForeground(new java.awt.Color(161, 29, 95));
                S_Builder_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                S_Builder_label.setText("Student Login");

                S_login_panel.setBackground(new java.awt.Color(255, 192, 203));

                S_user_label.setFont(new java.awt.Font("Segoe UI", 1, 14));
                S_user_label.setForeground(new java.awt.Color(161, 29, 95));
                S_user_label.setText("Username:");

                S_Pass_label.setFont(new java.awt.Font("Segoe UI", 1, 14));
                S_Pass_label.setForeground(new java.awt.Color(161, 29, 95));
                S_Pass_label.setText("Password:");

                S_user_TF.setBackground(new java.awt.Color(255, 255, 255));
                S_user_TF.setFont(new java.awt.Font("Segoe UI", 0, 14));

                S_Pass_PF.setBackground(new java.awt.Color(255, 255, 255));
                S_Pass_PF.setFont(new java.awt.Font("Segoe UI", 0, 14));

                S_showPass_CB.setBackground(new java.awt.Color(255, 192, 203));
                S_showPass_CB.setFont(new java.awt.Font("Segoe UI", 0, 12));
                S_showPass_CB.setForeground(new java.awt.Color(161, 29, 95));
                S_showPass_CB.setText("Show Password");
                S_showPass_CB.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                S_showPass_CBActionPerformed(evt);
                        }
                });

                S_Login_button.setBackground(new java.awt.Color(161, 29, 95));
                S_Login_button.setFont(new java.awt.Font("Segoe UI", 1, 14));
                S_Login_button.setForeground(new java.awt.Color(255, 255, 255));
                S_Login_button.setText("Login");
                S_Login_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                S_Login_buttonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout S_login_panelLayout = new javax.swing.GroupLayout(S_login_panel);
                S_login_panel.setLayout(S_login_panelLayout);
                S_login_panelLayout.setHorizontalGroup(
                                S_login_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(S_login_panelLayout.createSequentialGroup()
                                                                .addGap(59, 59, 59)
                                                                .addGroup(S_login_panelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(S_user_TF)
                                                                                .addComponent(S_Pass_PF)
                                                                                .addGroup(S_login_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(S_login_panelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(S_user_label)
                                                                                                                .addComponent(S_Pass_label)
                                                                                                                .addComponent(S_showPass_CB)
                                                                                                                .addComponent(S_Login_button,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                100,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                .addGap(59, 59, 59))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                S_login_panelLayout.createSequentialGroup()
                                                                                .addGap(184, 184, 184)
                                                                                .addGroup(S_login_panelLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(S_Login_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGap(181, 181, 181)));
                S_login_panelLayout.setVerticalGroup(
                                S_login_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(S_login_panelLayout.createSequentialGroup()
                                                                .addGap(72, 72, 72)
                                                                .addComponent(S_user_label)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(S_user_TF,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                30,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(S_Pass_label)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(S_Pass_PF,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                30,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(S_showPass_CB)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(S_Login_button,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                30,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(72, Short.MAX_VALUE)));

                javax.swing.GroupLayout studentBuilder_panelLayout = new javax.swing.GroupLayout(studentBuilder_panel);
                studentBuilder_panel.setLayout(studentBuilder_panelLayout);
                studentBuilder_panelLayout.setHorizontalGroup(
                                studentBuilder_panelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(studentBuilder_panelLayout.createSequentialGroup()
                                                                .addGroup(studentBuilder_panelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(studentBuilder_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(355, 355, 355)
                                                                                                .addComponent(S_login_panel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(studentBuilder_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(401, 401, 401)
                                                                                                .addComponent(S_Builder_label,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(72, 72, 72)))
                                                                .addGap(345, 345, 345))
                                                .addGroup(studentBuilder_panelLayout.createSequentialGroup()
                                                                .addGap(27, 27, 27)
                                                                .addComponent(S_back_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(1089, 1089, 1089)));
                studentBuilder_panelLayout.setVerticalGroup(
                                studentBuilder_panelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(studentBuilder_panelLayout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addComponent(S_back_button,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                46, Short.MAX_VALUE)
                                                                .addGap(8, 8, 8)
                                                                .addComponent(S_Builder_label,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(S_login_panel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(108, 108, 108)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(studentBuilder_panel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(studentBuilder_panel,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        private void S_back_buttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_S_back_buttonActionPerformed
                SIO_app sio = new SIO_app();
                sio.show();

                dispose();
        }// GEN-LAST:event_S_back_buttonActionPerformed

        private void S_showPass_CBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_S_showPass_CBActionPerformed
                if (S_showPass_CB.isSelected()) {
                        S_Pass_PF.setEchoChar((char) 0);
                } else {
                        S_Pass_PF.setEchoChar('•');
                }
        }// GEN-LAST:event_S_showPass_CBActionPerformed

        private void S_Login_buttonActionPerformed(java.awt.event.ActionEvent evt) {
                String username = S_user_TF.getText();
                String password = new String(S_Pass_PF.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                        "Please enter both username and password",
                                        "Login Error",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (UserAuthenticator.validateLogin("student", username, password)) {
                        // Get student's full name
                        String fullName = "";
                        try (BufferedReader reader = new BufferedReader(new FileReader("users/students.csv"))) {
                                String line;
                                reader.readLine(); // Skip header
                                while ((line = reader.readLine()) != null) {
                                        String[] parts = line.split(",");
                                        if (parts.length >= 3 && parts[0].equals(username)) {
                                                fullName = parts[2];
                                                break;
                                        }
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(this,
                                                "Error loading student data",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        JOptionPane.showMessageDialog(this,
                                        "Login successful!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                        new StudentPortal.StudentDashboard(username, fullName).setVisible(true);
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
                        java.util.logging.Logger.getLogger(StudentSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(StudentSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(StudentSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(StudentSI_app.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new StudentSI_app().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel S_Builder_label;
        private javax.swing.JButton S_Login_button;
        private javax.swing.JPasswordField S_Pass_PF;
        private javax.swing.JLabel S_Pass_label;
        private javax.swing.JButton S_back_button;
        private javax.swing.JPanel S_login_panel;
        private javax.swing.JCheckBox S_showPass_CB;
        private javax.swing.JTextField S_user_TF;
        private javax.swing.JLabel S_user_label;
        private javax.swing.JPanel studentBuilder_panel;
        // End of variables declaration//GEN-END:variables
}
