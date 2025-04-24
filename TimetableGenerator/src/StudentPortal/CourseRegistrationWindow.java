package StudentPortal;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class CourseRegistrationWindow extends JFrame {
    private static final Color STUDENT_THEME = new Color(161, 29, 95);
    private String username;
    private String fullName;
    private Map<String, String> courseNames;
    private Map<String, Set<String>> registeredCourses;
    private JList<String> courseList;
    private DefaultListModel<String> courseListModel;

    public CourseRegistrationWindow(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
        this.courseNames = new HashMap<>();
        this.registeredCourses = new HashMap<>();
        loadCourseNames();
        loadRegisteredCourses();
        initComponents();
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

    private void loadRegisteredCourses() {
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
                        registeredCourses.computeIfAbsent(courseCode, k -> new HashSet<>())
                                .add(component + "-" + section);
                    }
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, it will be created when needed
            System.err.println("Warning: Could not read registrations file: " + e.getMessage());
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Course Registration");
        setPreferredSize(new Dimension(600, 500));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label
        JLabel titleLabel = new JLabel("Course Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Course list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(new Color(240, 248, 255));

        JLabel listLabel = new JLabel("Available Courses");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listPanel.add(listLabel, BorderLayout.NORTH);

        courseListModel = new DefaultListModel<>();
        updateCourseList();

        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        courseList.setBackground(Color.WHITE);
        courseList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JScrollPane scrollPane = new JScrollPane(courseList);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton registerButton = new JButton("Register Selected Course");
        JButton backButton = new JButton("Back to Dashboard");

        styleButton(registerButton);
        styleButton(backButton);

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        registerButton.addActionListener(e -> {
            String selectedCourse = courseList.getSelectedValue();
            if (selectedCourse != null) {
                String courseCode = selectedCourse.split(" - ")[0];
                new CourseSelectionDialog(this, courseCode).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a course first.",
                        "Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> dispose());

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void styleButton(JButton button) {
        button.setBackground(STUDENT_THEME);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
    }

    public void updateRegisteredCourses(String courseCode, String component, String section) {
        // First fix the existing file if it's empty or has wrong format
        ensureCorrectFileFormat();

        // Update the in-memory registration data
        registeredCourses.computeIfAbsent(courseCode, k -> new HashSet<>()).add(component + "-" + section);

        try {
            List<String> allLines = new ArrayList<>();

            // Read existing registrations (excluding the current user's registrations for
            // this course)
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream("data/student_registrations.csv"), "UTF-8"))) {
                String line;
                // Read and keep header
                String header = reader.readLine();
                if (header != null && header.trim().equals("username,course_code,component,section,timestamp")) {
                    allLines.add(header);
                } else {
                    // If header is wrong or missing, add the correct header
                    allLines.add("username,course_code,component,section,timestamp");
                }

                // Read existing registrations
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    // Only keep lines that are not for this user-course combination and are
                    // properly formatted
                    if (parts.length >= 4) {
                        String existingUsername = parts[0].trim();
                        String existingCourseCode = parts[1].trim();
                        if (!existingUsername.equals(username) || !existingCourseCode.equals(courseCode)) {
                            allLines.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                // If file doesn't exist or can't be read, we'll create it with header
                if (allLines.isEmpty()) {
                    allLines.add("username,course_code,component,section,timestamp");
                }
            }

            // Add new registration with correct format
            String timestamp = String.valueOf(System.currentTimeMillis());
            String newRegistration = String.format("%s,%s,%s,%s,%s",
                    username, // Always put username first
                    courseCode, // Course code second
                    component, // Component third
                    section, // Section fourth
                    timestamp // Timestamp last
            );
            allLines.add(newRegistration);

            // Write all lines back to file
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("data/student_registrations.csv"), "UTF-8"))) {
                for (int i = 0; i < allLines.size(); i++) {
                    writer.write(allLines.get(i));
                    writer.write("\n");
                }
            }

            // Refresh the registrations
            refreshRegistrations();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving registration: " + e.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ensureCorrectFileFormat() {
        File file = new File("data/student_registrations.csv");
        if (!file.exists() || file.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"))) {
                writer.write("username,course_code,component,section,timestamp\n");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error creating registration file: " + e.getMessage(),
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshRegistrations() {
        registeredCourses.clear();
        loadRegisteredCourses();
    }

    private void updateCourseList() {
        courseListModel.clear();
        for (String courseCode : courseNames.keySet()) {
            String displayText = String.format("%s - %s", courseCode, courseNames.get(courseCode));
            courseListModel.addElement(displayText);
        }
    }

    public String getUsername() {
        return username;
    }
}