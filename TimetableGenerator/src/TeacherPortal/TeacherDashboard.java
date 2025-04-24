package TeacherPortal;

import javax.swing.*;
import java.awt.*;
import SignInOptions.SIO_app;

public class TeacherDashboard extends JFrame {
    private static final Color TEACHER_THEME = new Color(70, 130, 180);
    private String username;
    private String fullName;

    private JPanel mainPanel;
    private JButton viewCoursesButton;
    private JButton viewScheduleButton;
    private JButton logoutButton;

    public TeacherDashboard(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Teacher Dashboard - " + fullName);
        setPreferredSize(new Dimension(400, 300));

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + fullName);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(welcomeLabel, gbc);

        // Buttons
        viewCoursesButton = new JButton("View My Courses");
        viewScheduleButton = new JButton("View My Schedule");
        logoutButton = new JButton("Logout");

        styleButton(viewCoursesButton);
        styleButton(viewScheduleButton);
        styleButton(logoutButton);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        mainPanel.add(viewCoursesButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(viewScheduleButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(logoutButton, gbc);

        // Add action listeners
        viewCoursesButton.addActionListener(e -> {
            new TeacherCourses(username, fullName).setVisible(true);
            dispose();
        });

        viewScheduleButton.addActionListener(e -> {
            new TeacherSchedule(username, fullName).setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            // Return to login screen
            new SIO_app().setVisible(true);
            dispose();
        });

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void styleButton(JButton button) {
        button.setBackground(TEACHER_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
    }
}