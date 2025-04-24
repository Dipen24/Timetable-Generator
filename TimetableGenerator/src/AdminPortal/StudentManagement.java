package AdminPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagement extends javax.swing.JFrame {
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private JPanel mainPanel;
    private JTable studentTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private List<String[]> studentData;

    public StudentManagement() {
        initComponents();
        loadStudentData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Management");
        setBackground(new Color(255, 248, 220));

        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 248, 220));

        // Create table model
        String[] columnNames = { "Username", "Full Name", "Email" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.getTableHeader().setReorderingAllowed(false);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(studentTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 248, 220));

        addButton = new JButton("Add Student");
        editButton = new JButton("Edit Student");
        deleteButton = new JButton("Delete Student");
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
            StudentDialog dialog = new StudentDialog(this, "Add Student", null);
            dialog.setVisible(true);
            if (dialog.isApproved()) {
                String[] studentData = dialog.getStudentData();
                addStudentToTable(studentData);
                saveStudentData();
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                String[] existingData = new String[3];
                for (int i = 0; i < 3; i++) {
                    existingData[i] = (String) tableModel.getValueAt(selectedRow, i);
                }
                StudentDialog dialog = new StudentDialog(this, "Edit Student", existingData);
                dialog.setVisible(true);
                if (dialog.isApproved()) {
                    String[] studentData = dialog.getStudentData();
                    updateStudentInTable(selectedRow, studentData);
                    saveStudentData();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a student to edit",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    saveStudentData();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a student to delete",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
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

    private void loadStudentData() {
        studentData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users/students.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    studentData.add(parts);
                    // Add to table: username, fullname, email
                    tableModel.addRow(new String[] { parts[0], parts[2], parts[3] });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading student data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStudentData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users/students.csv"))) {
            writer.println("username,password,fullname,email");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String[] row = new String[4];
                // Get username from table
                row[0] = (String) tableModel.getValueAt(i, 0);
                // Find the original password from studentData
                for (String[] data : studentData) {
                    if (data[0].equals(row[0])) {
                        row[1] = data[1];
                        break;
                    }
                }
                // Get fullname and email from table
                row[2] = (String) tableModel.getValueAt(i, 1);
                row[3] = (String) tableModel.getValueAt(i, 2);
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving student data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudentToTable(String[] studentData) {
        // Add to table: username, fullname, email
        tableModel.addRow(new String[] {
                studentData[0], // username
                studentData[2], // fullname
                studentData[3] // email
        });
        // Add to studentData list for password storage
        this.studentData.add(studentData);
    }

    private void updateStudentInTable(int row, String[] studentData) {
        // Update table: username, fullname, email
        tableModel.setValueAt(studentData[0], row, 0); // username
        tableModel.setValueAt(studentData[2], row, 1); // fullname
        tableModel.setValueAt(studentData[3], row, 2); // email

        // Update studentData list for password storage
        for (int i = 0; i < this.studentData.size(); i++) {
            if (this.studentData.get(i)[0].equals(studentData[0])) {
                this.studentData.set(i, studentData);
                break;
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new StudentManagement().setVisible(true);
        });
    }
}