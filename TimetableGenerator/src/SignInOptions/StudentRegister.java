package SignInOptions;

import utils.UserAuthenticator;
import javax.swing.*;
import java.awt.*;

public class StudentRegister extends javax.swing.JFrame {
    // Student theme color
    private static final Color STUDENT_THEME = new Color(161, 29, 95);

    private JTextField studentUsername_field;
    private JPasswordField studentPassword_field;
    private JTextField studentFullName_field;
    private JTextField studentEmail_field;
    private JButton studentReg_button;
    private JButton studentBack_button;
    private JLabel studentReg_label;
    private JCheckBox studentShowPass_CB;

    public StudentRegister() {
        initComponents();
        setTitle("Student Registration");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create header
        studentReg_label = new JLabel("Student Registration");
        studentReg_label.setFont(new Font("Bahnschrift", Font.BOLD, 24));
        studentReg_label.setForeground(STUDENT_THEME);

        // Create fields
        studentUsername_field = new JTextField(20);
        studentPassword_field = new JPasswordField(20);
        studentFullName_field = new JTextField(20);
        studentEmail_field = new JTextField(20);

        // Create show password checkbox
        studentShowPass_CB = new JCheckBox("Show Password");
        studentShowPass_CB.setForeground(STUDENT_THEME);
        studentShowPass_CB.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        studentShowPass_CB.addActionListener(e -> {
            if (studentShowPass_CB.isSelected()) {
                studentPassword_field.setEchoChar((char) 0);
            } else {
                studentPassword_field.setEchoChar('•');
            }
        });

        // Create buttons
        studentReg_button = new JButton("Register");
        studentReg_button.setBackground(STUDENT_THEME);
        studentReg_button.setForeground(Color.WHITE);
        studentReg_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentReg_button.setBorderPainted(false);
        studentReg_button.setFocusPainted(false);

        studentBack_button = new JButton("Back to Login");
        studentBack_button.setBackground(Color.GRAY);
        studentBack_button.setForeground(Color.WHITE);
        studentBack_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentBack_button.setBorderPainted(false);
        studentBack_button.setFocusPainted(false);

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(studentReg_label, gbc);

        // Add form fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow("Username:", studentUsername_field, 1, gbc);
        addFormRow("Password:", studentPassword_field, 2, gbc);

        // Add show password checkbox
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(studentShowPass_CB, gbc);

        addFormRow("Full Name:", studentFullName_field, 4, gbc);
        addFormRow("Email:", studentEmail_field, 5, gbc);

        // Add buttons
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        add(studentReg_button, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(5, 5, 20, 5);
        add(studentBack_button, gbc);

        // Add action listeners
        studentReg_button.addActionListener(e -> registerStudent());
        studentBack_button.addActionListener(e -> {
            new StudentSI_app().setVisible(true);
            dispose();
        });

        // Window settings
        setPreferredSize(new Dimension(400, 500));
        pack();
    }

    private void addFormRow(String labelText, JComponent field, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setForeground(STUDENT_THEME);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }

    private void registerStudent() {
        String username = studentUsername_field.getText();
        String password = new String(studentPassword_field.getPassword());
        String fullName = studentFullName_field.getText();
        String email = studentEmail_field.getText();
        String department = "Student"; // Default department for students

        // Validate fields
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required!",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Attempt registration
        if (UserAuthenticator.registerUser("student", username, password, fullName, email, department)) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful! You can now login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            // Return to login screen
            new StudentSI_app().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed. Username might already exist.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}