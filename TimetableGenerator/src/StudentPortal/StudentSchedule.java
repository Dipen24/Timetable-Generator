package StudentPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class StudentSchedule extends JFrame {
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
    private JButton backButton;
    private DefaultTableModel tableModel;
    private Map<String, String> courseNames; // Course code -> Course name

    public StudentSchedule(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
        this.courseNames = new HashMap<>();
        loadCourseNames();
        initComponents();
        loadSchedule();
    }

    private void loadCourseNames() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/courses.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    courseNames.put(parts[0].trim(), parts[1].trim()); // code -> name
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading course names: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("My Schedule");
        setSize(1000, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Add title label
        JLabel titleLabel = new JLabel("Class Schedule - " + fullName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(STUDENT_THEME);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Initialize the schedule table
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

        // Set column widths
        scheduleTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        for (int i = 1; i < scheduleTable.getColumnCount(); i++) {
            scheduleTable.getColumnModel().getColumn(i).setPreferredWidth(180);
        }

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

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(STUDENT_THEME));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for back and download buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 248, 255));

        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            new StudentDashboard(username, fullName).setVisible(true);
            dispose();
        });

        // Download button
        JButton downloadButton = new JButton("Download Schedule");
        styleButton(downloadButton);
        downloadButton.addActionListener(e -> downloadSchedule());

        buttonPanel.add(backButton);
        buttonPanel.add(downloadButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadSchedule() {
        try {
            // Clear existing schedule
            for (int row = 0; row < scheduleTable.getRowCount(); row++) {
                for (int col = 1; col < scheduleTable.getColumnCount(); col++) {
                    scheduleTable.setValueAt("", row, col);
                }
            }

            // Read registered courses
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
                String line;
                // Skip header
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String registeredUsername = parts[0].trim();
                        if (registeredUsername.equals(username)) {
                            String courseCode = parts[1].trim();
                            String component = parts[2].trim();
                            String section = parts[3].trim();

                            // Load schedule for this course component
                            loadCourseSchedule(courseCode, component, section);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading schedule: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCourseSchedule(String courseCode, String component, String section) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/course_schedules.csv"), "UTF-8"))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String scheduleCourseCode = parts[0].trim();
                    String scheduleComponent = parts[1].trim();
                    String scheduleSection = parts[2].trim();

                    if (scheduleCourseCode.equals(courseCode) &&
                            scheduleComponent.equals(component) &&
                            scheduleSection.equals(section)) {

                        String day = parts[3].trim();
                        String startTime = parts[4].trim();
                        String endTime = parts[5].trim();

                        // Find column index for day
                        int dayIndex = -1;
                        for (int i = 0; i < DAYS.length; i++) {
                            if (DAYS[i].equals(day)) {
                                dayIndex = i + 1;
                                break;
                            }
                        }

                        if (dayIndex != -1) {
                            // Find row indices for start and end times
                            int startRow = -1;
                            int endRow = -1;
                            for (int i = 0; i < TIME_SLOTS.length; i++) {
                                if (TIME_SLOTS[i].equals(startTime))
                                    startRow = i;
                                if (TIME_SLOTS[i].equals(endTime))
                                    endRow = i;
                            }

                            if (startRow != -1 && endRow != -1) {
                                // Get course name
                                String courseName = courseNames.getOrDefault(courseCode, "");

                                // Create display text with consistent formatting
                                String displayText = String.format(
                                        "<html><center>" +
                                                "<div style='display: inline-block; background-color: %s; color: white; padding: 2px 6px; margin-bottom: 4px; border-radius: 3px;'>"
                                                +
                                                "%s" +
                                                "</div><br>" +
                                                "<div style='color: #666666; margin: 2px 0;'>%s</div>" +
                                                "<div style='color: #000000;'>%s-%s</div>" +
                                                "</center></html>",
                                        getComponentColor(component),
                                        courseCode,
                                        courseName,
                                        component,
                                        section);

                                for (int row = startRow; row < endRow; row++) {
                                    // Check if there's already content in this cell
                                    Object existingContent = scheduleTable.getValueAt(row, dayIndex);
                                    if (existingContent != null && !existingContent.toString().isEmpty()) {
                                        // Check if this course is already in the cell
                                        String existingHtml = existingContent.toString();
                                        if (!existingHtml.contains(courseCode + "</div><br>")) {
                                            // Only append if this course isn't already in the cell
                                            displayText = existingHtml.replace("</html>", "") +
                                                    "<hr style='margin: 4px 0;'>" +
                                                    displayText.replace("<html><center>", "");
                                        } else {
                                            continue; // Skip if course is already in the cell
                                        }
                                    }
                                    scheduleTable.setValueAt(displayText, row, dayIndex);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading course schedule: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getComponentColor(String component) {
        switch (component) {
            case "Lecture":
                return "#4CAF50"; // Green
            case "Lab":
                return "#4CAF50"; // Using same green for consistency
            case "Tutorial":
                return "#4CAF50"; // Using same green for consistency
            default:
                return "#4CAF50"; // Default to green
        }
    }

    private void downloadSchedule() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Schedule");
        fileChooser.setSelectedFile(new File("schedule_" + username + ".csv"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        // Add .csv extension if not present
        if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
        }

        try {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileToSave), "UTF-8"))) {

                // Write header row
                StringBuilder header = new StringBuilder();
                for (int col = 0; col < scheduleTable.getColumnCount(); col++) {
                    if (col > 0)
                        header.append(",");
                    header.append(scheduleTable.getColumnModel().getColumn(col).getHeaderValue());
                }
                writer.write(header.toString());
                writer.newLine();

                // Write data rows
                for (int row = 0; row < scheduleTable.getRowCount(); row++) {
                    StringBuilder line = new StringBuilder();
                    for (int col = 0; col < scheduleTable.getColumnCount(); col++) {
                        if (col > 0)
                            line.append(",");

                        Object value = scheduleTable.getValueAt(row, col);
                        if (value != null) {
                            String cellContent = value.toString();
                            // For cells with HTML content, extract the course information
                            if (cellContent.contains("<html>")) {
                                String courseInfo = extractCourseInfo(cellContent);
                                // Escape any commas in the course info
                                line.append("\"").append(courseInfo.replace("\"", "\"\"")).append("\"");
                            } else {
                                line.append(cellContent);
                            }
                        }
                    }
                    writer.write(line.toString());
                    writer.newLine();
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Schedule downloaded successfully to:\n" + fileToSave.getAbsolutePath(),
                    "Download Complete",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error downloading schedule: " + e.getMessage(),
                    "Download Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String extractCourseInfo(String htmlContent) {
        // Extract course code, name, and component-section from HTML content
        String courseInfo = htmlContent.replaceAll("<[^>]+>", " ") // Remove HTML tags
                .replaceAll("\\s+", " ") // Replace multiple spaces with single space
                .trim(); // Remove leading/trailing spaces

        // Further clean up the extracted text
        return courseInfo.replace(" existing code ", "")
                .replaceAll("\\s*\\|\\s*", " | "); // Add spaces around separators
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