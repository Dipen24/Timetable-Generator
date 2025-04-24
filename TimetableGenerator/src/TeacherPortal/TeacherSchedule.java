package TeacherPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class TeacherSchedule extends JFrame {
    private static final Color TEACHER_THEME = new Color(70, 130, 180);
    private static final String ASSIGNMENTS_FILE = "data/course_assignments.csv";
    private static final String COURSES_FILE = "data/courses.csv";
    private static final String SCHEDULES_FILE = "data/course_schedules.csv";
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

    public TeacherSchedule(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
        this.courseNames = new HashMap<>();
        loadCourseNames();
        initComponents();
        loadSchedule();
    }

    private void loadCourseNames() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    courseNames.put(parts[0], parts[1]); // code -> name
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Weekly Schedule - " + fullName);
        setPreferredSize(new Dimension(1000, 600));

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table
        String[] columnNames = new String[DAYS.length + 1];
        columnNames[0] = "Time";
        System.arraycopy(DAYS, 0, columnNames, 1, DAYS.length);

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Initialize empty schedule
        for (int i = 0; i < TIME_SLOTS.length - 1; i++) {
            Object[] rowData = new Object[DAYS.length + 1];
            rowData[0] = TIME_SLOTS[i] + " - " + TIME_SLOTS[i + 1];
            for (int j = 1; j <= DAYS.length; j++) {
                rowData[j] = "";
            }
            tableModel.addRow(rowData);
        }

        scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(40);
        scheduleTable.getTableHeader().setReorderingAllowed(false);
        scheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < scheduleTable.getColumnCount(); i++) {
            scheduleTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));

        backButton = new JButton("Back to Dashboard");
        styleButton(backButton);
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> {
            new TeacherDashboard(username, fullName).setVisible(true);
            dispose();
        });

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void loadSchedule() {
        // First get assigned courses
        Set<String> assignedCourses = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ASSIGNMENTS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(username)) {
                    assignedCourses.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Then load schedules for assigned courses
        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && assignedCourses.contains(parts[0])) {
                    String courseCode = parts[0];
                    String component = parts[1];
                    String section = parts[2];
                    String day = parts[3];
                    String startTime = parts[4];
                    String endTime = parts[5];

                    // Find the day column
                    int dayColumn = -1;
                    for (int i = 0; i < DAYS.length; i++) {
                        if (DAYS[i].equals(day)) {
                            dayColumn = i + 1; // +1 because first column is time
                            break;
                        }
                    }

                    // Find the time rows
                    int startRow = -1;
                    int endRow = -1;
                    for (int i = 0; i < TIME_SLOTS.length - 1; i++) {
                        if (TIME_SLOTS[i].equals(startTime)) {
                            startRow = i;
                        }
                        if (TIME_SLOTS[i].equals(endTime)) {
                            endRow = i;
                        }
                    }

                    if (dayColumn != -1 && startRow != -1 && endRow != -1) {
                        // Convert component to short form
                        String componentShort;
                        switch (component.toLowerCase()) {
                            case "lecture":
                                componentShort = "L";
                                break;
                            case "tutorial":
                                componentShort = "T";
                                break;
                            case "lab":
                            case "practical":
                                componentShort = "P";
                                break;
                            default:
                                componentShort = component;
                        }

                        String displayText = String.format("%s %s%s", courseCode, componentShort, section);

                        // Fill all slots between start and end time
                        for (int row = startRow; row < endRow; row++) {
                            tableModel.setValueAt(displayText, row, dayColumn);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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