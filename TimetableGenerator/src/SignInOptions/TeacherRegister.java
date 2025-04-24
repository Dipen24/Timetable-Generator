package SignInOptions;

import utils.UserAuthenticator;
import javax.swing.*;
import java.awt.*;

public class TeacherRegister extends javax.swing.JFrame {
    // Teacher theme color
    private static final Color TEACHER_THEME = new Color(20, 88, 165);

    private JTextField teacherUsername_field;
    private JPasswordField teacherPassword_field;
    private JTextField teacherFullName_field;
    private JTextField teacherEmail_field;
    private JTextField teacherDepartment_field; // Additional field for department
    private JButton teacherReg_button;
    private JButton teacherBack_button;
    private JLabel teacherReg_label;
    private JCheckBox teacherShowPass_CB;

    public TeacherRegister() {
        initComponents();
        setTitle("Teacher Registration");
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create header
        teacherReg_label = new JLabel("Teacher Registration");
        teacherReg_label.setFont(new Font("Bahnschrift", Font.BOLD, 24));
        teacherReg_label.setForeground(TEACHER_THEME);

        // Create fields
        teacherUsername_field = new JTextField(20);
        teacherPassword_field = new JPasswordField(20);
        teacherFullName_field = new JTextField(20);
        teacherEmail_field = new JTextField(20);
        teacherDepartment_field = new JTextField(20);

        // Create show password checkbox
        teacherShowPass_CB = new JCheckBox("Show Password");
        teacherShowPass_CB.setForeground(new Color(51, 51, 51)); // Same as student
        teacherShowPass_CB.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        teacherShowPass_CB.addActionListener(e -> {
            if (teacherShowPass_CB.isSelected()) {
                teacherPassword_field.setEchoChar((char) 0);
            } else {
                teacherPassword_field.setEchoChar('•');
            }
        });

        // Create buttons
        teacherReg_button = new JButton("Register");
        teacherReg_button.setBackground(TEACHER_THEME);
        teacherReg_button.setForeground(Color.WHITE);
        teacherReg_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        teacherReg_button.setBorderPainted(false);
        teacherReg_button.setFocusPainted(false);

        teacherBack_button = new JButton("Back to Login");
        teacherBack_button.setBackground(Color.GRAY);
        teacherBack_button.setForeground(Color.WHITE);
        teacherBack_button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        teacherBack_button.setBorderPainted(false);
        teacherBack_button.setFocusPainted(false);

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(teacherReg_label, gbc);

        // Add form fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow("Username:", teacherUsername_field, 1, gbc);
        addFormRow("Password:", teacherPassword_field, 2, gbc);

        // Add show password checkbox
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(teacherShowPass_CB, gbc);

        addFormRow("Full Name:", teacherFullName_field, 4, gbc);
        addFormRow("Email:", teacherEmail_field, 5, gbc);
        addFormRow("Department:", teacherDepartment_field, 6, gbc);

        // Add buttons
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        add(teacherReg_button, gbc);

        gbc.gridy = 8;
        gbc.insets = new Insets(5, 5, 20, 5);
        add(teacherBack_button, gbc);

        // Add action listeners
        teacherReg_button.addActionListener(e -> registerTeacher());
        teacherBack_button.addActionListener(e -> {
            new TeacherSI_app().setVisible(true);
            dispose();
        });

        // Window settings
        setPreferredSize(new Dimension(400, 550));
        pack();
    }

    private void addFormRow(String labelText, JComponent field, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setForeground(TEACHER_THEME);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }

    private void registerTeacher() {
        String username = teacherUsername_field.getText();
        String password = new String(teacherPassword_field.getPassword());
        String fullName = teacherFullName_field.getText();
        String email = teacherEmail_field.getText();
        String department = teacherDepartment_field.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() ||
                email.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required!",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UserAuthenticator.registerUser("teacher", username, password, fullName, email, department)) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful! You can now login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            new TeacherSI_app().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed. Username might already exist.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}