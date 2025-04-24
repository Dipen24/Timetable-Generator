package TeacherPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class TeacherCourses extends JFrame {
    private static final Color TEACHER_THEME = new Color(70, 130, 180);
    private static final String ASSIGNMENTS_FILE = "data/course_assignments.csv";
    private static final String COURSES_FILE = "data/courses.csv";

    private String username;
    private String fullName;
    private JPanel mainPanel;
    private JTable courseTable;
    private JButton backButton;
    private DefaultTableModel tableModel;

    public TeacherCourses(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
        initComponents();
        loadAssignedCourses();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Courses - " + fullName);
        setPreferredSize(new Dimension(800, 600));

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table
        String[] columnNames = { "Course Code", "Course Name", "Credits", "Department", "Prerequisites" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(30);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(courseTable);
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

    private void loadAssignedCourses() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Load course assignments
        Map<String, String> assignments = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ASSIGNMENTS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(username)) {
                    assignments.put(parts[0], parts[1]); // course code -> teacher username
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load course details for assigned courses
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && assignments.containsKey(parts[0])) {
                    tableModel.addRow(parts);
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