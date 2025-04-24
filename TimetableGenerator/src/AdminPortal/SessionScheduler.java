package AdminPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SessionScheduler extends JDialog {
    private static final String[] DAYS = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
    private static final String[] TIME_SLOTS = {
            "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
    };
    private static final String CLASSROOMS_FILE = "data/classrooms.csv";

    private String componentType;
    private int numberOfSections;
    private int sessionsPerWeek;
    private List<List<SessionInfo>> scheduleData;
    private boolean approved = false;
    private List<String> availableClassrooms;

    public SessionScheduler(Window parent, String componentType, int numberOfSections, int sessionsPerWeek) {
        super(parent, "Schedule " + componentType + " Sessions", ModalityType.APPLICATION_MODAL);
        this.componentType = componentType;
        this.numberOfSections = numberOfSections;
        this.sessionsPerWeek = sessionsPerWeek;
        this.scheduleData = new ArrayList<>();
        this.availableClassrooms = new ArrayList<>();
        loadClassrooms();
        initComponents();
    }

    private void loadClassrooms() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(CLASSROOMS_FILE), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    availableClassrooms.add(parts[0].trim()); // Add room number
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 248, 220));
        setSize(900, 600); // Increased width to accommodate classroom column
        setLocationRelativeTo(getParent());

        // Create main panel with tabs for each section
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 248, 220));

        for (int section = 1; section <= numberOfSections; section++) {
            JPanel sectionPanel = createSectionPanel(section);
            tabbedPane.addTab(componentType + " " + section, sectionPanel);
        }

        add(tabbedPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 248, 220));

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        styleButton(okButton);
        styleButton(cancelButton);

        okButton.addActionListener(e -> {
            if (validateSchedule()) {
                approved = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createSectionPanel(int section) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 248, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add headers
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Session"), gbc);

        gbc.gridx = 1;
        panel.add(new JLabel("Day"), gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Start Time"), gbc);

        gbc.gridx = 3;
        panel.add(new JLabel("End Time"), gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Classroom"), gbc);

        // Create session rows
        List<SessionInfo> sectionSessions = new ArrayList<>();
        for (int session = 1; session <= sessionsPerWeek; session++) {
            gbc.gridy = session;

            // Session number
            gbc.gridx = 0;
            panel.add(new JLabel("Session " + session), gbc);

            // Day combo box
            gbc.gridx = 1;
            JComboBox<String> dayCombo = new JComboBox<>(DAYS);
            panel.add(dayCombo, gbc);

            // Start time combo box
            gbc.gridx = 2;
            JComboBox<String> startTimeCombo = new JComboBox<>(TIME_SLOTS);
            panel.add(startTimeCombo, gbc);

            // End time combo box
            gbc.gridx = 3;
            JComboBox<String> endTimeCombo = new JComboBox<>(TIME_SLOTS);
            panel.add(endTimeCombo, gbc);

            // Classroom combo box
            gbc.gridx = 4;
            JComboBox<String> classroomCombo = new JComboBox<>(availableClassrooms.toArray(new String[0]));
            panel.add(classroomCombo, gbc);

            // Add action listener to validate time selection
            startTimeCombo.addActionListener(e -> validateTimeSelection(startTimeCombo, endTimeCombo));
            endTimeCombo.addActionListener(e -> validateTimeSelection(startTimeCombo, endTimeCombo));

            sectionSessions.add(new SessionInfo(dayCombo, startTimeCombo, endTimeCombo, classroomCombo));
        }
        scheduleData.add(sectionSessions);

        return panel;
    }

    private void validateTimeSelection(JComboBox<String> startCombo, JComboBox<String> endCombo) {
        // Removed time validation
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 204, 102));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
    }

    private boolean validateSchedule() {
        // Only check if times are selected, no validation of time range
        for (List<SessionInfo> sectionSessions : scheduleData) {
            for (SessionInfo session : sectionSessions) {
                if (session.dayCombo.getSelectedItem() == null ||
                        session.startTimeCombo.getSelectedItem() == null ||
                        session.endTimeCombo.getSelectedItem() == null ||
                        session.classroomCombo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please select day, time slots, and classroom for all sessions",
                            "Incomplete Schedule",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    private int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        return hours * 60;
    }

    public boolean isApproved() {
        return approved;
    }

    public List<List<SessionInfo>> getScheduleData() {
        return scheduleData;
    }

    public static class SessionInfo {
        public JComboBox<String> dayCombo;
        public JComboBox<String> startTimeCombo;
        public JComboBox<String> endTimeCombo;
        public JComboBox<String> classroomCombo;

        public SessionInfo(JComboBox<String> dayCombo, JComboBox<String> startTimeCombo,
                JComboBox<String> endTimeCombo, JComboBox<String> classroomCombo) {
            this.dayCombo = dayCombo;
            this.startTimeCombo = startTimeCombo;
            this.endTimeCombo = endTimeCombo;
            this.classroomCombo = classroomCombo;
        }
    }
}