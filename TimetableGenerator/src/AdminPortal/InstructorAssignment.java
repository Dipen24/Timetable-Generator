package AdminPortal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

public class InstructorAssignment extends javax.swing.JFrame {
    private static final String ASSIGNMENTS_FILE = "data/course_assignments.csv";
    private static final String TEACHERS_FILE = "users/teachers.csv";
    private static final String COURSES_FILE = "data/courses.csv";
    private static final Color ADMIN_THEME = new Color(255, 204, 102);

    private JPanel mainPanel;
    private JTable assignmentTable;
    private JScrollPane tableScrollPane;
    private JButton assignButton;
    private JButton removeButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private Map<String, String> teacherMap; // Maps username to full name
    private Set<String> courseCodes; // Store available course codes

    public InstructorAssignment() {
        loadTeachersAndCourses();
        initComponents();
        loadAssignments();
        setLocationRelativeTo(null);
    }

    private void loadTeachersAndCourses() {
        teacherMap = new HashMap<>();
        courseCodes = new HashSet<>();

        // Load teachers
        try (BufferedReader reader = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    teacherMap.put(parts[0], parts[2]); // username -> full name
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load courses
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    courseCodes.add(parts[0]); // course code
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instructor Assignment");

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 248, 220));
        mainPanel.setPreferredSize(new Dimension(1196, 695));

        // Initialize table
        String[] columnNames = { "Course Code", "Course Name", "Assigned Instructor", "Department" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        assignmentTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(assignmentTable);
        assignmentTable.setFillsViewportHeight(true);

        // Initialize buttons
        assignButton = new JButton("Assign Instructor");
        removeButton = new JButton("Remove Assignment");
        backButton = new JButton("Back to Dashboard");

        // Style buttons
        styleButton(assignButton);
        styleButton(removeButton);
        styleButton(backButton);

        // Add action listeners
        assignButton.addActionListener(e -> assignInstructor());
        removeButton.addActionListener(e -> removeAssignment());
        backButton.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        // Layout setup
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(50, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 1096,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(assignButton, GroupLayout.PREFERRED_SIZE, 200,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 200,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addGap(476, 476, 476)
                                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 200,
                                                        GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(50, Short.MAX_VALUE)));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(30, Short.MAX_VALUE)
                                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 550,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(assignButton, GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(45, Short.MAX_VALUE)));

        // Main window layout
        GroupLayout windowLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(windowLayout);
        windowLayout.setHorizontalGroup(
                windowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        windowLayout.setVerticalGroup(
                windowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }

    private void styleButton(JButton button) {
        button.setBackground(ADMIN_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private void loadAssignments() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Load course details and assignments
        Map<String, String[]> courseDetails = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    courseDetails.put(parts[0], new String[] { parts[1], parts[3] }); // code -> {name, department}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load assignments
        Map<String, String> assignments = new HashMap<>();
        File assignmentsFile = new File(ASSIGNMENTS_FILE);
        if (assignmentsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(assignmentsFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        assignments.put(parts[0], parts[1]); // course code -> teacher username
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Populate table
        for (String courseCode : courseDetails.keySet()) {
            String[] details = courseDetails.get(courseCode);
            String teacherUsername = assignments.get(courseCode);
            String teacherName = teacherUsername != null ? teacherMap.get(teacherUsername) : "Not Assigned";
            tableModel.addRow(new Object[] {
                    courseCode,
                    details[0],
                    teacherName != null ? teacherName : "Not Assigned",
                    details[1]
            });
        }
    }

    private void assignInstructor() {
        int selectedRow = assignmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a course to assign an instructor",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseCode = (String) tableModel.getValueAt(selectedRow, 0);

        // Create array of teacher names for selection
        String[] teacherNames = teacherMap.values().toArray(new String[0]);
        Arrays.sort(teacherNames);

        String selectedTeacher = (String) JOptionPane.showInputDialog(
                this,
                "Select instructor for " + courseCode,
                "Assign Instructor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                teacherNames,
                teacherNames[0]);

        if (selectedTeacher != null) {
            // Find username for selected teacher name
            String teacherUsername = "";
            for (Map.Entry<String, String> entry : teacherMap.entrySet()) {
                if (entry.getValue().equals(selectedTeacher)) {
                    teacherUsername = entry.getKey();
                    break;
                }
            }

            // Update assignment
            saveAssignment(courseCode, teacherUsername);
            tableModel.setValueAt(selectedTeacher, selectedRow, 2);
        }
    }

    private void removeAssignment() {
        int selectedRow = assignmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a course to remove assignment",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseCode = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove the instructor assignment for this course?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            saveAssignment(courseCode, null);
            tableModel.setValueAt("Not Assigned", selectedRow, 2);
        }
    }

    private void saveAssignment(String courseCode, String teacherUsername) {
        // Load existing assignments
        Map<String, String> assignments = new HashMap<>();
        File file = new File(ASSIGNMENTS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        assignments.put(parts[0], parts[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Update assignment
        if (teacherUsername != null) {
            assignments.put(courseCode, teacherUsername);
        } else {
            assignments.remove(courseCode);
        }

        // Save all assignments
        try (PrintWriter writer = new PrintWriter(new FileWriter(ASSIGNMENTS_FILE))) {
            writer.println("Course Code,Teacher Username");
            for (Map.Entry<String, String> entry : assignments.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving assignment: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}