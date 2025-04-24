package StudentPortal;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.util.Set;
import java.util.HashSet;

public class CourseSelectionDialog extends JDialog {
    private static final Color STUDENT_THEME = new Color(161, 29, 95);
    private String courseCode;
    private CourseRegistrationWindow parent;
    private Map<String, List<String>> availableSections; // Component type -> List of sections
    private Map<String, String> currentRegistrations; // component -> section

    private JComboBox<String> lectureCombo;
    private JComboBox<String> tutorialCombo;
    private JComboBox<String> labCombo;
    private JButton registerButton;
    private JButton cancelButton;

    public CourseSelectionDialog(CourseRegistrationWindow parent, String courseCode) {
        super(parent, "Register for " + courseCode, true);
        this.parent = parent;
        this.courseCode = courseCode;
        this.availableSections = new HashMap<>();
        this.currentRegistrations = new HashMap<>();
        loadAvailableSections();
        loadCurrentRegistrations();
        initComponents();
    }

    private void loadAvailableSections() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/course_schedules.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(courseCode)) {
                    String component = parts[1];
                    String section = parts[2];
                    List<String> sections = availableSections.computeIfAbsent(component, k -> new ArrayList<>());
                    if (!sections.contains(section)) { // Only add if section not already present
                        sections.add(section);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCurrentRegistrations() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 &&
                        parts[0].trim().equals(parent.getUsername()) &&
                        parts[1].trim().equals(courseCode)) {
                    currentRegistrations.put(parts[2].trim(), parts[3].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Select Sections for " + courseCode);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Selection panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Lecture selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectionPanel.add(new JLabel("Lecture:"), gbc);
        lectureCombo = new JComboBox<>();
        if (availableSections.containsKey("Lecture")) {
            DefaultComboBoxModel<String> lectureModel = new DefaultComboBoxModel<>();
            for (String section : availableSections.get("Lecture")) {
                lectureModel.addElement(section);
            }
            lectureCombo.setModel(lectureModel);
            // Set current registration if exists
            if (currentRegistrations.containsKey("Lecture")) {
                lectureCombo.setSelectedItem(currentRegistrations.get("Lecture"));
            }
        }
        gbc.gridx = 1;
        selectionPanel.add(lectureCombo, gbc);

        // Tutorial selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionPanel.add(new JLabel("Tutorial:"), gbc);
        tutorialCombo = new JComboBox<>();
        if (availableSections.containsKey("Tutorial")) {
            DefaultComboBoxModel<String> tutorialModel = new DefaultComboBoxModel<>();
            for (String section : availableSections.get("Tutorial")) {
                tutorialModel.addElement(section);
            }
            tutorialCombo.setModel(tutorialModel);
            // Set current registration if exists
            if (currentRegistrations.containsKey("Tutorial")) {
                tutorialCombo.setSelectedItem(currentRegistrations.get("Tutorial"));
            }
        }
        gbc.gridx = 1;
        selectionPanel.add(tutorialCombo, gbc);

        // Lab selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        selectionPanel.add(new JLabel("Lab:"), gbc);
        labCombo = new JComboBox<>();
        if (availableSections.containsKey("Lab") || availableSections.containsKey("Practical")) {
            DefaultComboBoxModel<String> labModel = new DefaultComboBoxModel<>();
            List<String> labSections = availableSections.getOrDefault("Lab",
                    availableSections.getOrDefault("Practical", new ArrayList<>()));
            for (String section : labSections) {
                labModel.addElement(section);
            }
            labCombo.setModel(labModel);
            // Set current registration if exists
            if (currentRegistrations.containsKey("Lab")) {
                labCombo.setSelectedItem(currentRegistrations.get("Lab"));
            }
        }
        gbc.gridx = 1;
        selectionPanel.add(labCombo, gbc);

        mainPanel.add(selectionPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        styleButton(registerButton);
        styleButton(cancelButton);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        registerButton.addActionListener(e -> registerForSection());
        cancelButton.addActionListener(e -> dispose());

        add(mainPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    private void styleButton(JButton button) {
        button.setBackground(STUDENT_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
    }

    private String getCourseNameForCode(String courseCode) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/courses.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(courseCode.trim())) {
                    return parts[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown Course";
    }

    private boolean isComponentRegistered(String component) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String username = parts[0].trim();
                    String courseCode = parts[1].trim();
                    String registeredComponent = parts[2].trim();

                    if (username.equals(parent.getUsername()) &&
                            courseCode.equals(this.courseCode) &&
                            registeredComponent.equals(component)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean hasScheduleClash(String component, String section) {
        // Get the schedule for the selected component and section
        List<ScheduleSlot> newSchedule = getScheduleForComponent(courseCode, component, section);

        // Get all existing registrations for the student
        List<ScheduleSlot> existingSchedule = getExistingSchedule();

        // Check for clashes
        for (ScheduleSlot newSlot : newSchedule) {
            for (ScheduleSlot existingSlot : existingSchedule) {
                if (newSlot.overlaps(existingSlot)) {
                    JOptionPane.showMessageDialog(this,
                            String.format(
                                    "Schedule Clash Detected!\n\n%s %s-%s (%s %s-%s)\nclashes with\n%s %s-%s (%s %s-%s)",
                                    courseCode, component, section,
                                    newSlot.day, newSlot.startTime, newSlot.endTime,
                                    existingSlot.courseCode, existingSlot.component, existingSlot.section,
                                    existingSlot.day, existingSlot.startTime, existingSlot.endTime),
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }
        }
        return false;
    }

    private List<ScheduleSlot> getScheduleForComponent(String courseCode, String component, String section) {
        List<ScheduleSlot> schedule = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/course_schedules.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 &&
                        parts[0].trim().equals(courseCode) &&
                        parts[1].trim().equals(component) &&
                        parts[2].trim().equals(section)) {
                    schedule.add(new ScheduleSlot(
                            courseCode,
                            component,
                            section,
                            parts[3].trim(), // day
                            parts[4].trim(), // start time
                            parts[5].trim() // end time
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    private List<ScheduleSlot> getExistingSchedule() {
        List<ScheduleSlot> schedule = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equals(parent.getUsername())) {
                    String regCourseCode = parts[1].trim();
                    String regComponent = parts[2].trim();
                    String regSection = parts[3].trim();

                    // Get schedule for this registration
                    schedule.addAll(getScheduleForComponent(regCourseCode, regComponent, regSection));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    private static class ScheduleSlot {
        String courseCode;
        String component;
        String section;
        String day;
        String startTime;
        String endTime;

        ScheduleSlot(String courseCode, String component, String section,
                String day, String startTime, String endTime) {
            this.courseCode = courseCode;
            this.component = component;
            this.section = section;
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        boolean overlaps(ScheduleSlot other) {
            if (!this.day.equals(other.day))
                return false;

            // Convert times to comparable format (assume format is HH:mm)
            int thisStart = timeToMinutes(this.startTime);
            int thisEnd = timeToMinutes(this.endTime);
            int otherStart = timeToMinutes(other.startTime);
            int otherEnd = timeToMinutes(other.endTime);

            // Check for overlap
            return (thisStart < otherEnd && thisEnd > otherStart);
        }

        private int timeToMinutes(String time) {
            String[] parts = time.split(":");
            return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        }
    }

    private void registerForSection() {
        // Get all selected components
        List<ComponentSelection> selectedComponents = new ArrayList<>();
        Map<String, String> updatedComponents = new HashMap<>();

        if (lectureCombo != null && lectureCombo.isEnabled() && lectureCombo.getSelectedIndex() != -1) {
            String section = (String) lectureCombo.getSelectedItem();
            selectedComponents.add(new ComponentSelection("Lecture", section));
            updatedComponents.put("Lecture", section);
        }
        if (tutorialCombo != null && tutorialCombo.isEnabled() && tutorialCombo.getSelectedIndex() != -1) {
            String section = (String) tutorialCombo.getSelectedItem();
            selectedComponents.add(new ComponentSelection("Tutorial", section));
            updatedComponents.put("Tutorial", section);
        }
        if (labCombo != null && labCombo.isEnabled() && labCombo.getSelectedIndex() != -1) {
            String section = (String) labCombo.getSelectedItem();
            selectedComponents.add(new ComponentSelection("Lab", section));
            updatedComponents.put("Lab", section);
        }

        if (selectedComponents.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select at least one section to register for.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for schedule clashes with other courses (excluding current course)
        for (ComponentSelection comp : selectedComponents) {
            // Skip clash check if component and section haven't changed
            if (currentRegistrations.containsKey(comp.component) &&
                    currentRegistrations.get(comp.component).equals(comp.section)) {
                continue;
            }

            if (hasScheduleClashExcludingCurrent(comp.component, comp.section)) {
                return; // Message already shown by hasScheduleClash
            }
        }

        // If no clashes, proceed with registration update
        try {
            // Read all registrations
            List<String> updatedRegistrations = new ArrayList<>();
            boolean foundHeader = false;

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!foundHeader) {
                        updatedRegistrations.add(line);
                        foundHeader = true;
                        continue;
                    }

                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String username = parts[0].trim();
                        String regCourseCode = parts[1].trim();
                        String component = parts[2].trim();

                        // Skip lines for current user and course if component is being updated
                        if (username.equals(parent.getUsername()) &&
                                regCourseCode.equals(courseCode) &&
                                updatedComponents.containsKey(component)) {
                            continue;
                        }
                        updatedRegistrations.add(line);
                    }
                }
            }

            // Add new/updated registrations
            for (ComponentSelection comp : selectedComponents) {
                String entry = String.format("%s,%s,%s,%s",
                        parent.getUsername(),
                        courseCode,
                        comp.component,
                        comp.section);
                updatedRegistrations.add(entry);
            }

            // Write back all registrations
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("data/student_registrations.csv"), "UTF-8"))) {
                for (String line : updatedRegistrations) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            parent.refreshRegistrations();
            JOptionPane.showMessageDialog(this,
                    "Successfully updated registration for " + courseCode,
                    "Registration Updated",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error updating registration: " + e.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean hasScheduleClashExcludingCurrent(String component, String section) {
        // Get the schedule for the selected component and section
        List<ScheduleSlot> newSchedule = getScheduleForComponent(courseCode, component, section);

        // Get all existing registrations for the student (excluding current course)
        List<ScheduleSlot> existingSchedule = getExistingScheduleExcludingCourse(courseCode);

        // Check for clashes
        for (ScheduleSlot newSlot : newSchedule) {
            for (ScheduleSlot existingSlot : existingSchedule) {
                if (newSlot.overlaps(existingSlot)) {
                    JOptionPane.showMessageDialog(this,
                            String.format(
                                    "Schedule Clash Detected!\n\n%s %s-%s (%s %s-%s)\nclashes with\n%s %s-%s (%s %s-%s)",
                                    courseCode, component, section,
                                    newSlot.day, newSlot.startTime, newSlot.endTime,
                                    existingSlot.courseCode, existingSlot.component, existingSlot.section,
                                    existingSlot.day, existingSlot.startTime, existingSlot.endTime),
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }
        }
        return false;
    }

    private List<ScheduleSlot> getExistingScheduleExcludingCourse(String excludeCourseCode) {
        List<ScheduleSlot> schedule = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 &&
                        parts[0].trim().equals(parent.getUsername()) &&
                        !parts[1].trim().equals(excludeCourseCode)) {
                    String regCourseCode = parts[1].trim();
                    String regComponent = parts[2].trim();
                    String regSection = parts[3].trim();

                    // Get schedule for this registration
                    schedule.addAll(getScheduleForComponent(regCourseCode, regComponent, regSection));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    private static class ComponentSelection {
        String component;
        String section;

        ComponentSelection(String component, String section) {
            this.component = component;
            this.section = section;
        }
    }
}