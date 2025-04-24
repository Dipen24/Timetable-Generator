package AdminPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends javax.swing.JFrame {
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private JPanel mainPanel;
    private JButton courseManagementButton;
    private JButton instructorAssignmentButton;
    private JButton teacherManagementButton;
    private JButton studentManagementButton;
    private JButton classroomManagementButton;
    private JButton logoutButton;

    public AdminDashboard() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard");
        setBackground(new Color(255, 248, 220));

        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 248, 220));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create buttons
        courseManagementButton = new JButton("Course Management");
        instructorAssignmentButton = new JButton("Instructor Assignment");
        teacherManagementButton = new JButton("Teacher Management");
        studentManagementButton = new JButton("Student Management");
        classroomManagementButton = new JButton("Classroom Management");
        logoutButton = new JButton("Logout");

        // Style buttons
        styleButton(courseManagementButton);
        styleButton(instructorAssignmentButton);
        styleButton(teacherManagementButton);
        styleButton(studentManagementButton);
        styleButton(classroomManagementButton);
        styleButton(logoutButton);

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(courseManagementButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(instructorAssignmentButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(teacherManagementButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(studentManagementButton, gbc);

        gbc.gridy = 4;
        mainPanel.add(classroomManagementButton, gbc);

        gbc.gridy = 5;
        mainPanel.add(logoutButton, gbc);

        // Add action listeners
        courseManagementButton.addActionListener(e -> {
            new CourseManagement().setVisible(true);
            dispose();
        });

        instructorAssignmentButton.addActionListener(e -> {
            new InstructorAssignment().setVisible(true);
            dispose();
        });

        teacherManagementButton.addActionListener(e -> {
            new TeacherManagement().setVisible(true);
            dispose();
        });

        studentManagementButton.addActionListener(e -> {
            new StudentManagement().setVisible(true);
            dispose();
        });

        classroomManagementButton.addActionListener(e -> {
            new ClassroomManagement().setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                new SignInOptions.SIO_app().setVisible(true);
                dispose();
            }
        });

        // Add main panel to frame
        add(mainPanel);

        // Set frame properties
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void styleButton(JButton button) {
        button.setBackground(ADMIN_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }
}