package SignInOptions;

public class SIO_app extends javax.swing.JFrame {

        public SIO_app() {
                initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                SIO_panel = new javax.swing.JPanel();
                SIO_Label = new javax.swing.JLabel();
                studentSI_button = new javax.swing.JButton();
                teacherSI_button = new javax.swing.JButton();
                adminSI_button1 = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setBackground(new java.awt.Color(255, 255, 255));
                setBounds(new java.awt.Rectangle(700, 300, 0, 0));

                SIO_Label.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
                SIO_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                SIO_Label.setText("Sign In Options");
                SIO_Label.setAlignmentY(0.0F);

                studentSI_button.setBackground(new java.awt.Color(161, 29, 95));
                studentSI_button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                studentSI_button.setForeground(new java.awt.Color(255, 255, 255));
                studentSI_button.setText("Student");
                studentSI_button.setBorder(
                                new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                studentSI_button.setBorderPainted(false);
                studentSI_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                studentSI_buttonActionPerformed(evt);
                        }
                });

                teacherSI_button.setBackground(new java.awt.Color(20, 88, 165));
                teacherSI_button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                teacherSI_button.setForeground(new java.awt.Color(255, 255, 255));
                teacherSI_button.setText("Teacher");
                teacherSI_button.setBorder(
                                new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                teacherSI_button.setBorderPainted(false);
                teacherSI_button.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                teacherSI_buttonActionPerformed(evt);
                        }
                });

                adminSI_button1.setBackground(new java.awt.Color(255, 204, 102));
                adminSI_button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                adminSI_button1.setForeground(new java.awt.Color(255, 255, 255));
                adminSI_button1.setText("Admin");
                adminSI_button1.setBorder(
                                new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                adminSI_button1.setBorderPainted(false);
                adminSI_button1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                adminSI_button1ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout SIO_panelLayout = new javax.swing.GroupLayout(SIO_panel);
                SIO_panel.setLayout(SIO_panelLayout);
                SIO_panelLayout.setHorizontalGroup(
                                SIO_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(SIO_panelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(SIO_panelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(SIO_panelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(studentSI_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(teacherSI_button,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                178,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(SIO_Label,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap())
                                                .addGroup(SIO_panelLayout.createSequentialGroup()
                                                                .addGap(96, 96, 96)
                                                                .addComponent(adminSI_button1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                165,
                                                                                Short.MAX_VALUE)
                                                                .addGap(113, 113, 113)));
                SIO_panelLayout.setVerticalGroup(
                                SIO_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(SIO_panelLayout.createSequentialGroup()
                                                                .addComponent(SIO_Label,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                41, Short.MAX_VALUE)
                                                                .addGap(42, 42, 42)
                                                                .addGroup(
                                                                                SIO_panelLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(studentSI_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                36,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(teacherSI_button,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                36,
                                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(adminSI_button1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                36,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(15, 15, 15)
                                                                .addComponent(SIO_panel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(17, 17, 17)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGap(46, 46, 46)
                                                                .addComponent(SIO_panel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(38, 38, 38)));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void studentSI_buttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_studentSI_buttonActionPerformed
                StudentSI_app studentSI = new StudentSI_app();
                studentSI.show();

                dispose(); // Reminder for self: add an option to go back
        }// GEN-LAST:event_studentSI_buttonActionPerformed

        private void teacherSI_buttonActionPerformed(java.awt.event.ActionEvent evt) {
                TeacherSI_app teacherSI = new TeacherSI_app();
                teacherSI.setLocationRelativeTo(null);
                teacherSI.setVisible(true);
                dispose();
        }

        private void adminSI_button1ActionPerformed(java.awt.event.ActionEvent evt) {
                try {
                        AdminSI_app adminSI = new AdminSI_app();
                        adminSI.setLocationRelativeTo(null);
                        adminSI.setVisible(true);
                        dispose();
                } catch (Exception e) {
                        System.out.println("Error opening admin window: " + e.getMessage());
                        e.printStackTrace();
                }
        }

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
                        java.util.logging.Logger.getLogger(SIO_app.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(SIO_app.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(SIO_app.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(SIO_app.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new SIO_app().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel SIO_Label;
        private javax.swing.JPanel SIO_panel;
        private javax.swing.JButton adminSI_button1;
        private javax.swing.JButton studentSI_button;
        private javax.swing.JButton teacherSI_button;
        // End of variables declaration//GEN-END:variables
}