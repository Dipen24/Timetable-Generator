package StudentPortal;

import javax.swing.*;
import java.awt.*;
import SignInOptions.SIO_app;

public class StudentDashboard extends JFrame {
        private static final Color STUDENT_THEME = new Color(161, 29, 95);
        private String username;
        private String fullName;

        public StudentDashboard(String username, String fullName) {
                this.username = username;
                this.fullName = fullName;
                initComponents();
        }

        private void initComponents() {
                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Student Dashboard - " + username);
                setPreferredSize(new Dimension(600, 400));

                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBackground(new Color(240, 248, 255));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                // Welcome label
                JLabel welcomeLabel = new JLabel("Welcome, " + fullName);
                welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(welcomeLabel, BorderLayout.NORTH);

                // Button panel
                JPanel buttonPanel = new JPanel(new GridBagLayout());
                buttonPanel.setBackground(new Color(240, 248, 255));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 0, 10, 0);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridwidth = GridBagConstraints.REMAINDER;

                JButton registerButton = new JButton("Register for Courses");
                JButton autoTimetableButton = new JButton("Generate Auto Timetable");
                JButton scheduleButton = new JButton("View My Schedule");
                JButton logoutButton = new JButton("Logout");

                styleButton(registerButton);
                styleButton(autoTimetableButton);
                styleButton(scheduleButton);
                styleButton(logoutButton);

                gbc.gridy = 0;
                buttonPanel.add(registerButton, gbc);
                gbc.gridy = 1;
                buttonPanel.add(autoTimetableButton, gbc);
                gbc.gridy = 2;
                buttonPanel.add(scheduleButton, gbc);
                gbc.gridy = 3;
                buttonPanel.add(logoutButton, gbc);

                mainPanel.add(buttonPanel, BorderLayout.CENTER);

                // Add action listeners
                registerButton.addActionListener(e -> {
                        CourseRegistrationWindow regWindow = new CourseRegistrationWindow(username, fullName);
                        regWindow.setVisible(true);
                });

                autoTimetableButton.addActionListener(e -> {
                        TimetableSuggestionWindow generator = new TimetableSuggestionWindow(username, fullName);
                        generator.setVisible(true);
                });

                scheduleButton.addActionListener(e -> {
                        StudentSchedule scheduleWindow = new StudentSchedule(username, fullName);
                        scheduleWindow.setVisible(true);
                        setVisible(false); // Hide dashboard while schedule is open
                });

                logoutButton.addActionListener(e -> {
                        int choice = JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to logout?",
                                        "Confirm Logout",
                                        JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                                new SIO_app().setVisible(true);
                                dispose(); // Properly dispose of the window
                        }
                });

                add(mainPanel);
                pack();
                setLocationRelativeTo(null);
        }

        private void styleButton(JButton button) {
                button.setBackground(STUDENT_THEME);
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setPreferredSize(new Dimension(200, 40));
        }
}