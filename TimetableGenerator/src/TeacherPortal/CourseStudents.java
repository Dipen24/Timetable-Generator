package TeacherPortal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class CourseStudents extends javax.swing.JFrame {
    private static final Color TEACHER_THEME = new Color(20, 88, 165);
    private String teacherUsername;
    private String courseCode;
    private JPanel mainPanel;
    private JTable studentsTable;
    private JScrollPane tableScrollPane;
    private JButton backButton;
    private JLabel titleLabel;
    private DefaultTableModel tableModel;

    public CourseStudents(String teacherUsername, String courseCode) {
        this.teacherUsername = teacherUsername;
        this.courseCode = courseCode;
        initComponents();
        loadStudents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Course Students - " + courseCode);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(191, 221, 255));
        mainPanel.setPreferredSize(new Dimension(800, 600));

        // Initialize title
        titleLabel = new JLabel("Students Enrolled in " + courseCode);
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        titleLabel.setForeground(TEACHER_THEME);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize table
        String[] columnNames = { "Username", "Full Name", "Email" };
        tableModel = new DefaultTableModel(columnNames, 0);
        studentsTable = new JTable(tableModel);
        studentsTable.setEnabled(false);
        tableScrollPane = new JScrollPane(studentsTable);
        studentsTable.setFillsViewportHeight(true);

        // Initialize back button
        backButton = new JButton("Close");
        styleButton(backButton);

        backButton.addActionListener(e -> dispose());

        // Layout
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 760,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 760,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backButton, GroupLayout.Alignment.TRAILING,
                                                GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE)));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addComponent(titleLabel)
                                .addGap(20, 20, 20)
                                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 450,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
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
        button.setBackground(TEACHER_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private void loadStudents() {
        try {
            // Clear existing data
            while (tableModel.getRowCount() > 0) {
                tableModel.removeRow(0);
            }

            // Get registered students from student_registrations.csv
            try (BufferedReader reader = new BufferedReader(new FileReader("data/student_registrations.csv"))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[1].equals(courseCode)) {
                        String studentUsername = parts[0];
                        getStudentDetails(studentUsername);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading students: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getStudentDetails(String studentUsername) {
        // Implementation of getStudentDetails method
    }

    private void loadRegisteredStudents() {
        registeredStudents.clear();

        // Get registered students from student_registrations.csv
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
                    String component = parts[2].trim();
                    String section = parts[3].trim();

                    if (courseCode.equals(this.courseCode)) {
                        StudentInfo info = new StudentInfo(username);
                        info.addComponent(component, section);
                        registeredStudents.put(username, info);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading registered students: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        updateTable();
    }
}