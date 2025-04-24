package AdminPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherManagement extends javax.swing.JFrame {
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private JPanel mainPanel;
    private JTable teacherTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private List<String[]> teacherData;

    public TeacherManagement() {
        initComponents();
        loadTeacherData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Teacher Management");
        setBackground(new Color(255, 248, 220));

        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 248, 220));

        // Create table model
        String[] columnNames = { "Username", "Full Name", "Email", "Department" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        teacherTable = new JTable(tableModel);
        teacherTable.setRowHeight(30);
        teacherTable.getTableHeader().setReorderingAllowed(false);
        teacherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 248, 220));

        addButton = new JButton("Add Teacher");
        editButton = new JButton("Edit Teacher");
        deleteButton = new JButton("Delete Teacher");
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
            TeacherDialog dialog = new TeacherDialog(this, "Add Teacher", null);
            dialog.setVisible(true);
            if (dialog.isApproved()) {
                String[] teacherData = dialog.getTeacherData();
                addTeacherToTable(teacherData);
                saveTeacherData();
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow != -1) {
                String[] existingData = new String[4];
                for (int i = 0; i < 4; i++) {
                    existingData[i] = (String) tableModel.getValueAt(selectedRow, i);
                }
                TeacherDialog dialog = new TeacherDialog(this, "Edit Teacher", existingData);
                dialog.setVisible(true);
                if (dialog.isApproved()) {
                    String[] teacherData = dialog.getTeacherData();
                    updateTeacherInTable(selectedRow, teacherData);
                    saveTeacherData();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a teacher to edit",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow != -1) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this teacher?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    saveTeacherData();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a teacher to delete",
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

    private void loadTeacherData() {
        teacherData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users/teachers.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    teacherData.add(parts);
                    // Add to table: username, fullname, email, department
                    tableModel.addRow(new String[] { parts[0], parts[2], parts[3], parts[4] });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading teacher data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTeacherData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users/teachers.csv"))) {
            writer.println("username,password,fullname,email,department");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String[] row = new String[5];
                // Get username from table
                row[0] = (String) tableModel.getValueAt(i, 0);
                // Find the original password from teacherData
                for (String[] data : teacherData) {
                    if (data[0].equals(row[0])) {
                        row[1] = data[1];
                        break;
                    }
                }
                // Get fullname, email, department from table
                row[2] = (String) tableModel.getValueAt(i, 1);
                row[3] = (String) tableModel.getValueAt(i, 2);
                row[4] = (String) tableModel.getValueAt(i, 3);
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving teacher data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTeacherToTable(String[] teacherData) {
        // Add to table: username, fullname, email, department
        tableModel.addRow(new String[] {
                teacherData[0], // username
                teacherData[2], // fullname
                teacherData[3], // email
                teacherData[4] // department
        });
        // Add to teacherData list for password storage
        this.teacherData.add(teacherData);
    }

    private void updateTeacherInTable(int row, String[] teacherData) {
        // Update table: username, fullname, email, department
        tableModel.setValueAt(teacherData[0], row, 0); // username
        tableModel.setValueAt(teacherData[2], row, 1); // fullname
        tableModel.setValueAt(teacherData[3], row, 2); // email
        tableModel.setValueAt(teacherData[4], row, 3); // department

        // Update teacherData list for password storage
        for (int i = 0; i < this.teacherData.size(); i++) {
            if (this.teacherData.get(i)[0].equals(teacherData[0])) {
                this.teacherData.set(i, teacherData);
                break;
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TeacherManagement().setVisible(true);
        });
    }
}