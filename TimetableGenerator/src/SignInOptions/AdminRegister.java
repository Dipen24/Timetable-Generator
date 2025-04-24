package SignInOptions;

import utils.UserAuthenticator;
import javax.swing.*;
import java.awt.*;

public class AdminRegister extends javax.swing.JFrame {
    // Admin theme color
    private static final Color ADMIN_THEME = new Color(255, 204, 102);

    private JTextField adminUsername_field;
    private JPasswordField adminPassword_field;
    private JTextField adminFullName_field;
    private JTextField adminEmail_field;
    private JButton adminReg_button;
    private JButton adminBack_button;
    private JLabel adminReg_label;
    private JCheckBox adminShowPass_CB;

    public AdminRegister() {
        initComponents();
        setTitle("Admin Registration");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create header
        adminReg_label = new JLabel("Admin Registration");
        adminReg_label.setFont(new Font("Bahnschrift", Font.BOLD, 24));
        adminReg_label.setForeground(ADMIN_THEME);

        // Create fields
        adminUsername_field = new JTextField(20);
        adminPassword_field = new JPasswordField(20);
        adminFullName_field = new JTextField(20);
        adminEmail_field = new JTextField(20);

        // Create show password checkbox
        adminShowPass_CB = new JCheckBox("Show Password");
        adminShowPass_CB.setForeground(new Color(51, 51, 51)); // Same as student
        adminShowPass_CB.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        adminShowPass_CB.addActionListener(e -> {
            if (adminShowPass_CB.isSelected()) {
                adminPassword_field.setEchoChar((char) 0);
            } else {
                adminPassword_field.setEchoChar('•');
            }
        });

        // Create buttons
        adminReg_button = new JButton("Register");
        adminReg_button.setBackground(ADMIN_THEME);
        adminReg_button.setForeground(Color.WHITE);
        adminReg_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminReg_button.setBorderPainted(false);
        adminReg_button.setFocusPainted(false);

        adminBack_button = new JButton("Back to Login");
        adminBack_button.setBackground(Color.GRAY);
        adminBack_button.setForeground(Color.WHITE);
        adminBack_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminBack_button.setBorderPainted(false);
        adminBack_button.setFocusPainted(false);

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(adminReg_label, gbc);

        // Add form fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow("Username:", adminUsername_field, 1, gbc);
        addFormRow("Password:", adminPassword_field, 2, gbc);

        // Add show password checkbox
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(adminShowPass_CB, gbc);

        addFormRow("Full Name:", adminFullName_field, 4, gbc);
        addFormRow("Email:", adminEmail_field, 5, gbc);

        // Add buttons
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        add(adminReg_button, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(5, 5, 20, 5);
        add(adminBack_button, gbc);

        // Add action listeners
        adminReg_button.addActionListener(e -> registerAdmin());
        adminBack_button.addActionListener(e -> {
            new AdminSI_app().setVisible(true);
            dispose();
        });

        // Window settings
        setPreferredSize(new Dimension(400, 500));
        pack();
    }

    private void addFormRow(String labelText, JComponent field, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setForeground(ADMIN_THEME);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }

    private void registerAdmin() {
        String username = adminUsername_field.getText();
        String password = new String(adminPassword_field.getPassword());
        String fullName = adminFullName_field.getText();
        String email = adminEmail_field.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required!",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UserAuthenticator.registerUser("admin", username, password, fullName, email, null)) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful! You can now login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            new AdminSI_app().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed. Username might already exist.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}