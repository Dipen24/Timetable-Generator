package AdminPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;

public class CourseDialog extends JDialog {
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private static final String SCHEDULE_FILE = "data/course_schedules.csv";
    private boolean approved = false;
    private JTextField courseCodeField;
    private JTextField courseNameField;
    private JSpinner creditsSpinner;
    private JTextField departmentField;
    private JTextField prerequisitesField;

    // Course components
    private JCheckBox lectureCheckBox;
    private JCheckBox tutorialCheckBox;
    private JCheckBox labCheckBox;

    // Number of sections
    private JSpinner lectureSectionsSpinner;
    private JSpinner tutorialSectionsSpinner;
    private JSpinner labSectionsSpinner;

    // Sessions per week
    private JSpinner lectureSessionsSpinner;
    private JSpinner tutorialSessionsSpinner;
    private JSpinner labSessionsSpinner;

    // Schedule panels
    private SchedulePanel lectureSchedulePanel;
    private SchedulePanel tutorialSchedulePanel;
    private SchedulePanel labSchedulePanel;

    private JButton okButton;
    private JButton cancelButton;

    private boolean isEditMode;
    private String[] existingData;
    private String[] scheduleData;

    public CourseDialog(JFrame parent, boolean isEditMode, String[] existingData) {
        super(parent, isEditMode ? "Edit Course" : "Add Course", true);
        this.isEditMode = isEditMode;
        this.existingData = existingData;

        initComponents();
        if (isEditMode) {
            loadExistingData();
        }
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 248, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Basic course information
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Course Code:"), gbc);

        gbc.gridx = 1;
        courseCodeField = new JTextField(20);
        add(courseCodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Course Name:"), gbc);

        gbc.gridx = 1;
        courseNameField = new JTextField(20);
        add(courseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Credits:"), gbc);

        gbc.gridx = 1;
        creditsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 6, 1));
        add(creditsSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Department:"), gbc);

        gbc.gridx = 1;
        departmentField = new JTextField(20);
        add(departmentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Prerequisites:"), gbc);

        gbc.gridx = 1;
        prerequisitesField = new JTextField(20);
        add(prerequisitesField, gbc);

        // Course components
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Course Components:"), gbc);

        // Lecture configuration
        gbc.gridx = 0;
        gbc.gridy = 6;
        lectureCheckBox = new JCheckBox("Lecture");
        add(lectureCheckBox, gbc);

        gbc.gridx = 1;
        JPanel lectureConfigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lectureConfigPanel.add(new JLabel("Sections:"));
        lectureSectionsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        lectureConfigPanel.add(lectureSectionsSpinner);
        lectureConfigPanel.add(new JLabel("Sessions/Week:"));
        lectureSessionsSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 5, 1));
        lectureConfigPanel.add(lectureSessionsSpinner);
        add(lectureConfigPanel, gbc);

        // Tutorial configuration
        gbc.gridx = 0;
        gbc.gridy = 7;
        tutorialCheckBox = new JCheckBox("Tutorial");
        add(tutorialCheckBox, gbc);

        gbc.gridx = 1;
        JPanel tutorialConfigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tutorialConfigPanel.add(new JLabel("Sections:"));
        tutorialSectionsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        tutorialConfigPanel.add(tutorialSectionsSpinner);
        tutorialConfigPanel.add(new JLabel("Sessions/Week:"));
        tutorialSessionsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
        tutorialConfigPanel.add(tutorialSessionsSpinner);
        add(tutorialConfigPanel, gbc);

        // Lab configuration
        gbc.gridx = 0;
        gbc.gridy = 8;
        labCheckBox = new JCheckBox("Lab");
        add(labCheckBox, gbc);

        gbc.gridx = 1;
        JPanel labConfigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labConfigPanel.add(new JLabel("Sections:"));
        labSectionsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        labConfigPanel.add(labSectionsSpinner);
        labConfigPanel.add(new JLabel("Sessions/Week:"));
        labSessionsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
        labConfigPanel.add(labSessionsSpinner);
        add(labConfigPanel, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        styleButton(okButton);
        styleButton(cancelButton);

        okButton.addActionListener(e -> {
            if (validateInput()) {
                String courseCode = courseCodeField.getText().trim();
                handleComponentScheduling(courseCode);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

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

    private void updateSchedulePanels() {
        // Remove existing schedule panels
        if (lectureSchedulePanel != null) {
            remove(lectureSchedulePanel);
        }
        if (tutorialSchedulePanel != null) {
            remove(tutorialSchedulePanel);
        }
        if (labSchedulePanel != null) {
            remove(labSchedulePanel);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;

        // Add schedule panels for selected components
        if (lectureCheckBox.isSelected()) {
            lectureSchedulePanel = new SchedulePanel("Lecture",
                    (Integer) lectureSectionsSpinner.getValue(),
                    (Integer) lectureSessionsSpinner.getValue(),
                    true);
            add(lectureSchedulePanel, gbc);
            gbc.gridy++;
        }

        if (tutorialCheckBox.isSelected()) {
            tutorialSchedulePanel = new SchedulePanel("Tutorial",
                    (Integer) tutorialSectionsSpinner.getValue(),
                    (Integer) tutorialSessionsSpinner.getValue(),
                    true);
            add(tutorialSchedulePanel, gbc);
            gbc.gridy++;
        }

        if (labCheckBox.isSelected()) {
            labSchedulePanel = new SchedulePanel("Lab",
                    (Integer) labSectionsSpinner.getValue(),
                    (Integer) labSessionsSpinner.getValue(),
                    false);
            add(labSchedulePanel, gbc);
        }

        pack();
        revalidate();
        repaint();
    }

    private boolean validateInput() {
        if (courseCodeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course code", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (courseNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (departmentField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a department", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!lectureCheckBox.isSelected() && !tutorialCheckBox.isSelected() && !labCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select at least one course component", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void loadExistingData() {
        if (existingData != null && existingData.length >= 5) {
            courseCodeField.setText(existingData[0]);
            courseNameField.setText(existingData[1]);
            creditsSpinner.setValue(Integer.parseInt(existingData[2]));
            departmentField.setText(existingData[3]);
            prerequisitesField.setText(existingData[4]);

            // Load component data if available
            if (existingData.length > 5) {
                // Parse and load component data
                // Format: componentType:sections:sessions:schedule
                for (int i = 5; i < existingData.length; i++) {
                    String[] componentData = existingData[i].split(":");
                    if (componentData.length >= 4) {
                        String type = componentData[0];
                        int sections = Integer.parseInt(componentData[1]);
                        int sessions = Integer.parseInt(componentData[2]);
                        String schedule = componentData[3];

                        switch (type) {
                            case "Lecture":
                                lectureCheckBox.setSelected(true);
                                lectureSectionsSpinner.setValue(sections);
                                lectureSessionsSpinner.setValue(sessions);
                                break;
                            case "Tutorial":
                                tutorialCheckBox.setSelected(true);
                                tutorialSectionsSpinner.setValue(sections);
                                tutorialSessionsSpinner.setValue(sessions);
                                break;
                            case "Lab":
                                labCheckBox.setSelected(true);
                                labSectionsSpinner.setValue(sections);
                                labSessionsSpinner.setValue(sessions);
                                break;
                        }
                    }
                }
                updateSchedulePanels();
            }
        }
    }

    public boolean isApproved() {
        return approved;
    }

    public String[] getCourseData() {
        List<String> data = new ArrayList<>();
        data.add(courseCodeField.getText().trim());
        data.add(courseNameField.getText().trim());
        data.add(String.valueOf(creditsSpinner.getValue()));
        data.add(departmentField.getText().trim());
        data.add(prerequisitesField.getText().trim());

        // Add component data
        if (lectureCheckBox.isSelected()) {
            String lectureData = "Lecture:" + lectureSectionsSpinner.getValue() + ":" +
                    lectureSessionsSpinner.getValue();
            data.add(lectureData);
        }

        if (tutorialCheckBox.isSelected()) {
            String tutorialData = "Tutorial:" + tutorialSectionsSpinner.getValue() + ":" +
                    tutorialSessionsSpinner.getValue();
            data.add(tutorialData);
        }

        if (labCheckBox.isSelected()) {
            String labData = "Lab:" + labSectionsSpinner.getValue() + ":" +
                    labSessionsSpinner.getValue();
            data.add(labData);
        }

        return data.toArray(new String[0]);
    }

    private void saveScheduleData(String courseCode, String componentType,
            List<List<SessionScheduler.SessionInfo>> scheduleData) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(SCHEDULE_FILE, true), "UTF-8"))) {
            // If file is empty, write header
            if (new File(SCHEDULE_FILE).length() == 0) {
                writer.write("Course Code,Component,Section,Day,Start Time,End Time,Classroom");
                writer.newLine();
            }

            for (int section = 0; section < scheduleData.size(); section++) {
                List<SessionScheduler.SessionInfo> sectionSessions = scheduleData.get(section);
                for (SessionScheduler.SessionInfo session : sectionSessions) {
                    writer.write(String.format("%s,%s,%d,%s,%s,%s,%s",
                            courseCode,
                            componentType,
                            section + 1,
                            session.dayCombo.getSelectedItem(),
                            session.startTimeCombo.getSelectedItem(),
                            session.endTimeCombo.getSelectedItem(),
                            session.classroomCombo.getSelectedItem()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving schedule: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleComponentScheduling(String courseCode) {
        if (lectureCheckBox.isSelected()) {
            SessionScheduler scheduler = new SessionScheduler(
                    this,
                    "Lecture",
                    (Integer) lectureSectionsSpinner.getValue(),
                    (Integer) lectureSessionsSpinner.getValue());
            scheduler.setVisible(true);
            if (scheduler.isApproved()) {
                saveScheduleData(courseCode, "Lecture", scheduler.getScheduleData());
            } else {
                return;
            }
        }

        if (tutorialCheckBox.isSelected()) {
            SessionScheduler scheduler = new SessionScheduler(
                    this,
                    "Tutorial",
                    (Integer) tutorialSectionsSpinner.getValue(),
                    (Integer) tutorialSessionsSpinner.getValue());
            scheduler.setVisible(true);
            if (scheduler.isApproved()) {
                saveScheduleData(courseCode, "Tutorial", scheduler.getScheduleData());
            } else {
                return;
            }
        }

        if (labCheckBox.isSelected()) {
            SessionScheduler scheduler = new SessionScheduler(
                    this,
                    "Lab",
                    (Integer) labSectionsSpinner.getValue(),
                    (Integer) labSessionsSpinner.getValue());
            scheduler.setVisible(true);
            if (scheduler.isApproved()) {
                saveScheduleData(courseCode, "Lab", scheduler.getScheduleData());
            } else {
                return;
            }
        }

        approved = true;
        dispose();
    }
}