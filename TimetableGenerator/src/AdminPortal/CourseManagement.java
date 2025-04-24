package AdminPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManagement extends javax.swing.JFrame {
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private JPanel mainPanel;
    private JTable courseTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private List<String[]> courseData;
    private static final String COURSE_FILE = "data/courses.csv";
    private static final String SCHEDULE_FILE = "data/course_schedules.csv";
    private static final String[] COURSE_HEADERS = {
            "Course Code", "Course Name", "Credits", "Department", "Prerequisites"
    };

    public CourseManagement() {
        ensureDataDirectoryExists();
        initComponents();
        loadCourseData();
        setLocationRelativeTo(null);
    }

    private void ensureDataDirectoryExists() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        // Create course file with headers if it doesn't exist
        File courseFile = new File(COURSE_FILE);
        if (!courseFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(courseFile))) {
                writer.write(String.join(",", COURSE_HEADERS));
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create schedule file with headers if it doesn't exist
        File scheduleFile = new File(SCHEDULE_FILE);
        if (!scheduleFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(scheduleFile))) {
                writer.write("Course Code,Component,Section,Day,Start Time,End Time");
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Course Management");
        setBackground(new Color(255, 248, 220));

        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 248, 220));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table model
        String[] columnNames = { "Course Code", "Course Name", "Credits", "Department", "Prerequisites" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(30);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(courseTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 248, 220));

        addButton = new JButton("Add Course");
        editButton = new JButton("Edit Course");
        deleteButton = new JButton("Delete Course");
        backButton = new JButton("Back");

        // Style buttons
        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(backButton);

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> {
            CourseDialog dialog = new CourseDialog(this, false, null);
            dialog.setVisible(true);
            if (dialog.isApproved()) {
                String[] newCourse = dialog.getCourseData();
                courseData.add(newCourse);
                updateTable();
                saveCourseData();
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow >= 0) {
                CourseDialog dialog = new CourseDialog(this, true, courseData.get(selectedRow));
                dialog.setVisible(true);
                if (dialog.isApproved()) {
                    courseData.set(selectedRow, dialog.getCourseData());
                    updateTable();
                    saveCourseData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to edit", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this course?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    courseData.remove(selectedRow);
                    updateTable();
                    saveCourseData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to delete", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        // Add main panel to frame
        add(mainPanel);

        // Set frame properties
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void styleButton(JButton button) {
        button.setBackground(ADMIN_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 30));
    }

    private void loadCourseData() {
        courseData = new ArrayList<>();
        File file = new File(COURSE_FILE);
        if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
                boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip header line
                    }
                String[] data = line.split(",");
                    if (data.length >= 5) {
                        courseData.add(data);
                    }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading course data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
        updateTable();
    }

    private void saveCourseData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            // Write headers
            writer.write(String.join(",", COURSE_HEADERS));
            writer.newLine();

            // Write data
            for (String[] course : courseData) {
                writer.write(String.join(",", course));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving course data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (String[] course : courseData) {
            tableModel.addRow(course);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new CourseManagement().setVisible(true);
        });
    }
}