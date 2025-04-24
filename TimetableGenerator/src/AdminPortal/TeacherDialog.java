package AdminPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import utils.UserAuthenticator;

public class TeacherDialog extends JDialog {
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private boolean approved = false;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField departmentField;
    private JButton okButton;
    private JButton cancelButton;
    private JCheckBox showPasswordCheckBox;

    public TeacherDialog(JFrame parent, String title, String[] existingData) {
        super(parent, title, true);
        initComponents(existingData);
    }

    private void initComponents(String[] existingData) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Create a panel to hold all components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(255, 248, 220));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Show Password Checkbox
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(new Color(255, 248, 220));
        showPasswordCheckBox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? '\0' : '•');
        });
        mainPanel.add(showPasswordCheckBox, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        mainPanel.add(fullNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        departmentField = new JTextField(20);
        mainPanel.add(departmentField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 248, 220));

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        styleButton(okButton);
        styleButton(cancelButton);

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Add action listeners
        okButton.addActionListener(e -> {
            if (validateInput()) {
                approved = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Load existing data if provided
        if (existingData != null) {
            loadExistingData(existingData);
        }

        // Add main panel to dialog
        add(mainPanel);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void styleButton(JButton button) {
        button.setBackground(ADMIN_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
    }

    private boolean validateInput() {
        if (usernameField.getText().isEmpty() ||
                new String(passwordField.getPassword()).isEmpty() ||
                fullNameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                departmentField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if username already exists (only for new teachers)
        if (getTitle().equals("Add Teacher") &&
                UserAuthenticator.userExists("teacher", usernameField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Username already exists",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void loadExistingData(String[] data) {
        usernameField.setText(data[0]);
        fullNameField.setText(data[1]);
        emailField.setText(data[2]);
        departmentField.setText(data[3]);
    }

    public boolean isApproved() {
        return approved;
    }

    public String[] getTeacherData() {
        return new String[] {
                usernameField.getText(),
                new String(passwordField.getPassword()),
                fullNameField.getText(),
                emailField.getText(),
                departmentField.getText()
        };
    }
}