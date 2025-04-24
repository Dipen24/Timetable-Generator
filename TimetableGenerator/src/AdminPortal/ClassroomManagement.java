package AdminPortal;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ClassroomManagement extends JFrame {
    private static final String CLASSROOMS_FILE = "data/classrooms.csv";
    private static final Color ADMIN_THEME = new Color(255, 204, 102);
    private JTable classroomTable;
    private DefaultTableModel tableModel;

    public ClassroomManagement() {
        initComponents();
        loadClassrooms();
    }

    private void initComponents() {
        setTitle("Classroom Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(255, 248, 220));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(255, 248, 220));

        // Create table
        String[] columnNames = { "Room Number", "Capacity", "Building", "Floor", "Facilities" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        classroomTable = new JTable(tableModel);
        classroomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Style table
        classroomTable.getTableHeader().setBackground(ADMIN_THEME);
        classroomTable.getTableHeader().setForeground(Color.WHITE);
        classroomTable.setRowHeight(25);
        classroomTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(classroomTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel with GridLayout for better button arrangement
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setBackground(new Color(255, 248, 220));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Classroom");
        JButton editButton = new JButton("Edit Classroom");
        JButton deleteButton = new JButton("Delete Classroom");
        JButton backButton = new JButton("Back to Dashboard");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(backButton);

        addButton.addActionListener(e -> addClassroom());
        editButton.addActionListener(e -> editClassroom());
        deleteButton.addActionListener(e -> deleteClassroom());
        backButton.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loadClassrooms() {
        tableModel.setRowCount(0);
        try {
            File file = new File(CLASSROOMS_FILE);
            System.out.println("Loading classrooms from: " + file.getAbsolutePath());
            System.out.println("File exists: " + file.exists());
            System.out.println("File length: " + file.length());

            if (!file.exists()) {
                System.out.println("Creating new classrooms file with header");
                // Create file with header if it doesn't exist
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8"))) {
                    writer.write("Room Number,Capacity,Building,Floor,Facilities");
                    writer.newLine();
                }
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"))) {
                String line;
                // Skip header
                reader.readLine();
                System.out.println("Reading classroom data...");
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        System.out.println("Skipping empty line");
                        continue;
                    }
                    System.out.println("Processing line: " + line);
                    // Split the line by comma, but preserve empty fields
                    String[] data = line.split(",", -1);
                    System.out.println("Split data: " + Arrays.toString(data));
                    if (data.length >= 5) {
                        System.out.println("Adding row: " + Arrays.toString(data));
                        tableModel.addRow(new Object[] {
                                data[0].trim(),
                                data[1].trim(),
                                data[2].trim(),
                                data[3].trim(),
                                data[4].trim()
                        });
                    } else {
                        System.out.println("Invalid data format: " + line);
                    }
                }
                System.out.println("Total rows in table: " + tableModel.getRowCount());
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading classrooms: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addClassroom() {
        JTextField roomNumberField = new JTextField();
        JTextField capacityField = new JTextField();
        JTextField buildingField = new JTextField();
        JTextField floorField = new JTextField();
        JTextField facilitiesField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Room Number:"));
        panel.add(roomNumberField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Building:"));
        panel.add(buildingField);
        panel.add(new JLabel("Floor:"));
        panel.add(floorField);
        panel.add(new JLabel("Facilities:"));
        panel.add(facilitiesField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add New Classroom", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String roomNumber = roomNumberField.getText().trim();
            String capacity = capacityField.getText().trim();
            String building = buildingField.getText().trim();
            String floor = floorField.getText().trim();
            String facilities = facilitiesField.getText().trim();

            if (roomNumber.isEmpty() || capacity.isEmpty() || building.isEmpty() || floor.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "All fields except Facilities are required",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Integer.parseInt(capacity);
                Integer.parseInt(floor);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Capacity and Floor must be numbers",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(CLASSROOMS_FILE, true), "UTF-8"))) {
                // If file is empty, write header
                if (new File(CLASSROOMS_FILE).length() == 0) {
                    System.out.println("Writing header to empty file");
                    writer.write("Room Number,Capacity,Building,Floor,Facilities");
                    writer.newLine();
                }
                String classroomData = String.format("%s,%s,%s,%s,%s",
                        roomNumber, capacity, building, floor, facilities);
                System.out.println("Writing classroom data: " + classroomData);
                writer.write(classroomData);
                writer.newLine();
                writer.flush();
                System.out.println("Data written successfully");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error adding classroom: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Always reload the table after adding a classroom
            System.out.println("Reloading classroom table");
            loadClassrooms();
        }
    }

    private void editClassroom() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a classroom to edit",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentRoomNumber = tableModel.getValueAt(selectedRow, 0).toString();
        String currentCapacity = tableModel.getValueAt(selectedRow, 1).toString();
        String currentBuilding = tableModel.getValueAt(selectedRow, 2).toString();
        String currentFloor = tableModel.getValueAt(selectedRow, 3).toString();
        String currentFacilities = tableModel.getValueAt(selectedRow, 4).toString();

        JTextField roomNumberField = new JTextField(currentRoomNumber);
        JTextField capacityField = new JTextField(currentCapacity);
        JTextField buildingField = new JTextField(currentBuilding);
        JTextField floorField = new JTextField(currentFloor);
        JTextField facilitiesField = new JTextField(currentFacilities);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Room Number:"));
        panel.add(roomNumberField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Building:"));
        panel.add(buildingField);
        panel.add(new JLabel("Floor:"));
        panel.add(floorField);
        panel.add(new JLabel("Facilities:"));
        panel.add(facilitiesField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Edit Classroom", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newRoomNumber = roomNumberField.getText().trim();
            String newCapacity = capacityField.getText().trim();
            String newBuilding = buildingField.getText().trim();
            String newFloor = floorField.getText().trim();
            String newFacilities = facilitiesField.getText().trim();

            if (newRoomNumber.isEmpty() || newCapacity.isEmpty() || newBuilding.isEmpty() || newFloor.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "All fields except Facilities are required",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Integer.parseInt(newCapacity);
                Integer.parseInt(newFloor);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Capacity and Floor must be numbers",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateClassroomInFile(currentRoomNumber, newRoomNumber, newCapacity, newBuilding, newFloor, newFacilities);
        }
    }

    private void updateClassroomInFile(String oldRoomNumber, String newRoomNumber,
            String newCapacity, String newBuilding, String newFloor, String newFacilities) {
        try {
            java.util.List<String> lines = new java.util.ArrayList<>();
            boolean found = false;

            // Read all lines
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(CLASSROOMS_FILE), "UTF-8"))) {
                String line;
                lines.add(reader.readLine()); // Add header

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].trim().equals(oldRoomNumber)) {
                        lines.add(String.format("%s,%s,%s,%s,%s",
                                newRoomNumber, newCapacity, newBuilding, newFloor, newFacilities));
                        found = true;
                    } else {
                        lines.add(line);
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this,
                        "Classroom not found in file",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Write back all lines
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(CLASSROOMS_FILE), "UTF-8"))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            loadClassrooms();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error updating classroom: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClassroom() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a classroom to delete",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String roomNumber = tableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete classroom " + roomNumber + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                java.util.List<String> lines = new java.util.ArrayList<>();

                // Read all lines except the one to delete
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(CLASSROOMS_FILE), "UTF-8"))) {
                    String line;
                    lines.add(reader.readLine()); // Add header

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (!parts[0].trim().equals(roomNumber)) {
                            lines.add(line);
                        }
                    }
                }

                // Write back all remaining lines
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(CLASSROOMS_FILE), "UTF-8"))) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                }

                loadClassrooms();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error deleting classroom: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(ADMIN_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
    }
}