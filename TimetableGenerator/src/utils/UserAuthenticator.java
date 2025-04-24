package utils;

import java.io.*;
import java.util.*;

public class UserAuthenticator {
    private static final String USERS_DIR = "users";
    private static final String STUDENTS_FILE = USERS_DIR + "/students.csv";
    private static final String TEACHERS_FILE = USERS_DIR + "/teachers.csv";
    private static final String ADMINS_FILE = USERS_DIR + "/admins.csv";

    static {
        // Create users directory if it doesn't exist
        new File(USERS_DIR).mkdirs();

        // Create files if they don't exist
        createFileIfNotExists(STUDENTS_FILE);
        createFileIfNotExists(TEACHERS_FILE);
        createFileIfNotExists(ADMINS_FILE);

        // Add default admin if admins file is empty
        if (new File(ADMINS_FILE).length() == 0) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(ADMINS_FILE))) {
                writer.println("username,password,fullname,email");
                writer.println("admin,admin123,System Administrator,admin@example.com");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Add header row
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    if (filePath.contains("teachers")) {
                        writer.println("username,password,fullname,email,department");
                    } else {
                        writer.println("username,password,fullname,email");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean registerUser(String userType, String username, String password,
            String fullName, String email, String department) {
        String filePath = getUserFilePath(userType);

        // Check if username already exists
        if (isUsernameTaken(filePath, username)) {
            return false;
        }

        try (FileWriter fw = new FileWriter(filePath, true);
                PrintWriter writer = new PrintWriter(new BufferedWriter(fw))) {

            if (userType.equals("teacher")) {
                writer.println(String.format("%s,%s,%s,%s,%s",
                        username, password, fullName, email, department));
            } else {
                writer.println(String.format("%s,%s,%s,%s",
                        username, password, fullName, email));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isUsernameTaken(String filePath, String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validateLogin(String userType, String username, String password) {
        String filePath = getUserFilePath(userType);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean userExists(String userType, String username) {
        String filePath = getUserFilePath(userType);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getUserFilePath(String userType) {
        return switch (userType.toLowerCase()) {
            case "student" -> STUDENTS_FILE;
            case "teacher" -> TEACHERS_FILE;
            case "admin" -> ADMINS_FILE;
            default -> throw new IllegalArgumentException("Invalid user type: " + userType);
        };
    }

    public boolean authenticateTeacher(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return validateLogin("teacher", username, password);
    }

    public String getTeacherFullName(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    return parts[2]; // Return full name
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}