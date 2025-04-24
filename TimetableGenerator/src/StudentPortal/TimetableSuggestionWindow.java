package StudentPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableSuggestionWindow extends JFrame {
    private static final Color STUDENT_THEME = new Color(161, 29, 95);
    private static final String[] DAYS = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
    private static final String[] TIME_SLOTS = {
            "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00"
    };

    private String username;
    private String fullName;
    private JPanel mainPanel;
    private JTable scheduleTable;
    private JList<String> courseList;
    private DefaultListModel<String> courseListModel;
    private JButton generateButton;
    private JButton prevButton;
    private JButton nextButton;
    private JButton saveButton;
    private List<Map<String, List<CourseComponent>>> allPossibleSchedules;
    private int currentScheduleIndex = -1;
    private Map<String, String> courseNames; // code -> name

    public TimetableSuggestionWindow(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
        this.courseNames = new HashMap<>();
        this.allPossibleSchedules = new ArrayList<>();
        loadCourseNames();
        initComponents();
    }

    private void loadCourseNames() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/courses.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    courseNames.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Auto Timetable Generator");
        setSize(1200, 700);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Left panel for course selection
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBackground(new Color(240, 248, 255));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Available Courses"));

        courseListModel = new DefaultListModel<>();
        loadAvailableCourses();
        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane courseScrollPane = new JScrollPane(courseList);
        leftPanel.add(courseScrollPane, BorderLayout.CENTER);

        generateButton = new JButton("Generate Timetables");
        styleButton(generateButton);
        generateButton.addActionListener(e -> generateTimetables());
        leftPanel.add(generateButton, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Center panel for timetable display
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBackground(new Color(240, 248, 255));

        // Initialize schedule table
        initScheduleTable();
        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        navigationPanel.setBackground(new Color(240, 248, 255));

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        saveButton = new JButton("Save This Schedule");

        styleButton(prevButton);
        styleButton(nextButton);
        styleButton(saveButton);

        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        saveButton.setEnabled(false);

        prevButton.addActionListener(e -> showPreviousSchedule());
        nextButton.addActionListener(e -> showNextSchedule());
        saveButton.addActionListener(e -> saveCurrentSchedule());

        navigationPanel.add(prevButton);
        navigationPanel.add(saveButton);
        navigationPanel.add(nextButton);

        centerPanel.add(navigationPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Back to dashboard button
        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            new StudentDashboard(username, fullName).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void initScheduleTable() {
        String[] columnNames = { "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        scheduleTable = new JTable(TIME_SLOTS.length, DAYS.length + 1);
        scheduleTable.setModel(new DefaultTableModel(TIME_SLOTS.length, DAYS.length + 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // Set column headers
        for (int i = 0; i < columnNames.length; i++) {
            scheduleTable.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
        }

        // Configure table appearance
        scheduleTable.setRowHeight(80);
        scheduleTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        scheduleTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        scheduleTable.getTableHeader().setBackground(STUDENT_THEME);
        scheduleTable.getTableHeader().setForeground(Color.WHITE);
        scheduleTable.setShowGrid(true);
        scheduleTable.setGridColor(Color.LIGHT_GRAY);
        scheduleTable.getTableHeader().setReorderingAllowed(false);

        // Set time slots in the first column
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            scheduleTable.setValueAt(TIME_SLOTS[i], i, 0);
        }

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                if (column == 0) {
                    setBackground(new Color(240, 240, 240));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(Color.WHITE);
                    if (value != null && !value.toString().isEmpty()) {
                        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, STUDENT_THEME));
                    }
                }
                return c;
            }
        };
        scheduleTable.setDefaultRenderer(Object.class, centerRenderer);
    }

    private void loadAvailableCourses() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/courses.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String courseCode = parts[0].trim();
                    String courseName = parts[1].trim();
                    courseListModel.addElement(courseCode + " - " + courseName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateTimetables() {
        List<String> selectedCourses = courseList.getSelectedValuesList().stream()
                .map(s -> s.split(" - ")[0])
                .collect(Collectors.toList());

        if (selectedCourses.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select at least one course.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Reset current state
        allPossibleSchedules.clear();
        currentScheduleIndex = -1;

        // Get all possible combinations
        Map<String, List<CourseComponent>> courseComponents = new HashMap<>();
        for (String courseCode : selectedCourses) {
            List<CourseComponent> components = getAllCourseComponents(courseCode);
            courseComponents.put(courseCode, components);
        }

        // Generate all possible schedules
        generateScheduleCombinations(new ArrayList<>(selectedCourses), courseComponents, new HashMap<>(), 0);

        if (allPossibleSchedules.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No possible clash-free schedule found for the selected courses.",
                    "No Solution",
                    JOptionPane.WARNING_MESSAGE);
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            saveButton.setEnabled(false);
            return;
        }

        // Show first schedule
        currentScheduleIndex = 0;
        showCurrentSchedule();
        updateNavigationButtons();
    }

    private List<CourseComponent> getAllCourseComponents(String courseCode) {
        Map<String, CourseComponent> componentMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/course_schedules.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].trim().equals(courseCode)) {
                    String component = parts[1].trim();
                    String section = parts[2].trim();
                    String key = component + "-" + section;

                    CourseComponent courseComponent = componentMap.computeIfAbsent(
                            key,
                            k -> new CourseComponent(courseCode, component, section));

                    courseComponent.addSession(
                            parts[3].trim(), // day
                            parts[4].trim(), // start time
                            parts[5].trim() // end time
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(componentMap.values());
    }

    private void generateScheduleCombinations(List<String> courses,
            Map<String, List<CourseComponent>> courseComponents,
            Map<String, List<CourseComponent>> currentSchedule,
            int courseIndex) {
        if (courseIndex == courses.size()) {
            // We have a complete schedule, check if it's valid
            if (isValidSchedule(currentSchedule)) {
                allPossibleSchedules.add(new HashMap<>(currentSchedule));
            }
            return;
        }

        String currentCourse = courses.get(courseIndex);
        List<CourseComponent> components = courseComponents.get(currentCourse);

        // Group components by type
        Map<String, List<CourseComponent>> componentsByType = components.stream()
                .collect(Collectors.groupingBy(c -> c.component));

        // Try each possible combination of components for this course
        List<List<CourseComponent>> combinations = generateComponentCombinations(componentsByType);

        for (List<CourseComponent> combination : combinations) {
            currentSchedule.put(currentCourse, combination);
            generateScheduleCombinations(courses, courseComponents, currentSchedule, courseIndex + 1);
            currentSchedule.remove(currentCourse);
        }
    }

    private List<List<CourseComponent>> generateComponentCombinations(
            Map<String, List<CourseComponent>> componentsByType) {
        List<List<CourseComponent>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (Map.Entry<String, List<CourseComponent>> entry : componentsByType.entrySet()) {
            List<List<CourseComponent>> newResult = new ArrayList<>();
            for (CourseComponent component : entry.getValue()) {
                for (List<CourseComponent> existing : result) {
                    List<CourseComponent> newCombination = new ArrayList<>(existing);
                    newCombination.add(component);
                    newResult.add(newCombination);
                }
            }
            result = newResult;
        }

        return result;
    }

    private boolean isValidSchedule(Map<String, List<CourseComponent>> schedule) {
        List<CourseComponent> allComponents = schedule.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        for (int i = 0; i < allComponents.size(); i++) {
            for (int j = i + 1; j < allComponents.size(); j++) {
                if (allComponents.get(i).conflictsWith(allComponents.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showCurrentSchedule() {
        // Clear existing schedule
        for (int row = 0; row < scheduleTable.getRowCount(); row++) {
            for (int col = 1; col < scheduleTable.getColumnCount(); col++) {
                scheduleTable.setValueAt("", row, col);
            }
        }

        if (currentScheduleIndex < 0 || currentScheduleIndex >= allPossibleSchedules.size()) {
            return;
        }

        Map<String, List<CourseComponent>> schedule = allPossibleSchedules.get(currentScheduleIndex);

        // Display each course component
        for (Map.Entry<String, List<CourseComponent>> entry : schedule.entrySet()) {
            String courseCode = entry.getKey();
            String courseName = courseNames.getOrDefault(courseCode, "Unknown Course");

            for (CourseComponent comp : entry.getValue()) {
                for (Session session : comp.sessions) {
                    // Find column index for day
                    int dayIndex = -1;
                    for (int i = 0; i < DAYS.length; i++) {
                        if (DAYS[i].equals(session.day)) {
                            dayIndex = i + 1;
                            break;
                        }
                    }

                    if (dayIndex != -1) {
                        // Find row indices for start and end times
                        int startRow = -1;
                        int endRow = -1;
                        for (int i = 0; i < TIME_SLOTS.length; i++) {
                            if (TIME_SLOTS[i].equals(session.startTime))
                                startRow = i;
                            if (TIME_SLOTS[i].equals(session.endTime))
                                endRow = i;
                        }

                        if (startRow != -1 && endRow != -1) {
                            String displayText = String.format(
                                    "<html><center>" +
                                            "<div style='display: inline-block; background-color: #4CAF50; color: white; padding: 2px 6px; margin-bottom: 4px; border-radius: 3px;'>"
                                            +
                                            "%s" +
                                            "</div><br>" +
                                            "<div style='color: #666666; margin: 2px 0;'>%s</div>" +
                                            "<div style='color: #000000;'>%s-%s</div>" +
                                            "</center></html>",
                                    courseCode,
                                    courseName,
                                    comp.component,
                                    comp.section);

                            for (int row = startRow; row < endRow; row++) {
                                scheduleTable.setValueAt(displayText, row, dayIndex);
                            }
                        }
                    }
                }
            }
        }

        setTitle(String.format("Auto Timetable Generator - Schedule %d of %d",
                currentScheduleIndex + 1, allPossibleSchedules.size()));
    }

    private void showPreviousSchedule() {
        if (currentScheduleIndex > 0) {
            currentScheduleIndex--;
            showCurrentSchedule();
            updateNavigationButtons();
        }
    }

    private void showNextSchedule() {
        if (currentScheduleIndex < allPossibleSchedules.size() - 1) {
            currentScheduleIndex++;
            showCurrentSchedule();
            updateNavigationButtons();
        }
    }

    private void updateNavigationButtons() {
        prevButton.setEnabled(currentScheduleIndex > 0);
        nextButton.setEnabled(currentScheduleIndex < allPossibleSchedules.size() - 1);
        saveButton.setEnabled(currentScheduleIndex >= 0 && currentScheduleIndex < allPossibleSchedules.size());
    }

    private void saveCurrentSchedule() {
        if (currentScheduleIndex < 0 || currentScheduleIndex >= allPossibleSchedules.size()) {
            return;
        }

        try {
            // First, remove any existing registrations
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
                    if (!parts[0].trim().equals(username)) {
                        updatedRegistrations.add(line);
                    }
                }
            }

            // Add new registrations from current schedule
            Map<String, List<CourseComponent>> schedule = allPossibleSchedules.get(currentScheduleIndex);
            for (Map.Entry<String, List<CourseComponent>> entry : schedule.entrySet()) {
                for (CourseComponent comp : entry.getValue()) {
                    String registration = String.format("%s,%s,%s,%s",
                            username,
                            comp.courseCode,
                            comp.component,
                            comp.section);
                    updatedRegistrations.add(registration);
                }
            }

            // Write back all registrations
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("data/student_registrations.csv"), "UTF-8"))) {
                for (String line : updatedRegistrations) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Schedule saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving schedule: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(STUDENT_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        if (button.getText().equals("Back to Dashboard")) {
            button.setPreferredSize(new Dimension(200, 40));
        } else {
            button.setPreferredSize(new Dimension(150, 40));
        }
    }

    private static class CourseComponent {
        String courseCode;
        String component;
        String section;
        List<Session> sessions;

        CourseComponent(String courseCode, String component, String section) {
            this.courseCode = courseCode;
            this.component = component;
            this.section = section;
            this.sessions = new ArrayList<>();
        }

        void addSession(String day, String startTime, String endTime) {
            sessions.add(new Session(day, startTime, endTime));
        }

        boolean conflictsWith(CourseComponent other) {
            for (Session s1 : sessions) {
                for (Session s2 : other.sessions) {
                    if (s1.conflictsWith(s2)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class Session {
        String day;
        String startTime;
        String endTime;

        Session(String day, String startTime, String endTime) {
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        boolean conflictsWith(Session other) {
            if (!day.equals(other.day))
                return false;
            int thisStart = timeToMinutes(startTime);
            int thisEnd = timeToMinutes(endTime);
            int otherStart = timeToMinutes(other.startTime);
            int otherEnd = timeToMinutes(other.endTime);
            return (thisStart < otherEnd && thisEnd > otherStart);
        }
    }

    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}