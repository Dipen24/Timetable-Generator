package StudentPortal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class StudentCourses extends javax.swing.JFrame {
    private static final Color STUDENT_THEME = new Color(161, 29, 93);
    private String studentUsername;
    private JPanel mainPanel;
    private JTable coursesTable;
    private JScrollPane tableScrollPane;
    private JButton backButton;
    private DefaultTableModel tableModel;

    public StudentCourses(String username) {
        this.studentUsername = username;
        initComponents();
        loadEnrolledCourses();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Enrolled Courses");

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 224, 239));
        mainPanel.setPreferredSize(new Dimension(1196, 695));

        // Initialize table
        String[] columnNames = { "Course Code", "Course Name", "Credits", "Department", "Teacher" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coursesTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(coursesTable);
        coursesTable.setFillsViewportHeight(true);

        // Initialize back button
        backButton = new JButton("Back to Dashboard");
        styleButton(backButton);

        backButton.addActionListener(e -> {
            new StudentDashboard(studentUsername).setVisible(true);
            dispose();
        });

        // Layout
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 1156,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backButton, GroupLayout.Alignment.TRAILING,
                                                GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE)));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(30, Short.MAX_VALUE)
                                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 575,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE)));

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
        button.setBackground(STUDENT_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private void loadEnrolledCourses() {
        try {
            // Clear existing data
            while (tableModel.getRowCount() > 0) {
                tableModel.removeRow(0);
            }

            // Load enrolled courses
            try (BufferedReader reader = new BufferedReader(new FileReader("data/student_enrollments.csv"))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].equals(studentUsername)) {
                        String courseCode = parts[1];
                        addCourseDetails(courseCode);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading courses: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourseDetails(String courseCode) {
        try {
            // Get course details
            String[] courseDetails = getCourseDetails(courseCode);
            if (courseDetails != null) {
                // Get teacher details
                String teacherUsername = getTeacherForCourse(courseCode);
                String teacherName = getTeacherName(teacherUsername);

                tableModel.addRow(new Object[] {
                        courseCode,
                        courseDetails[0], // Course Name
                        courseDetails[1], // Credits
                        courseDetails[2], // Department
                        teacherName
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getCourseDetails(String courseCode) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/courses.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(courseCode)) {
                    return new String[] { parts[1], parts[2], parts[3] };
                }
            }
        }
        return null;
    }

    private String getTeacherForCourse(String courseCode) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/course_assignments.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(courseCode)) {
                    return parts[1];
                }
            }
        }
        return "Not Assigned";
    }

    private String getTeacherName(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users/teachers.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return username;
    }
}