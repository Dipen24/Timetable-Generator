# Timetable Generator System

A comprehensive Java-based application for managing university timetables, course registrations, and classroom allocations.

## Features

- **User Authentication**

  - Student login
  - Teacher login
  - Admin login

- **Student Portal**

  - Course registration
  - View personal schedule
  - Automatic timetable generation
  - Download timetable

- **Teacher Portal**

  - View course students
  - Manage course components
  - View teaching schedule

- **Admin Portal**
  - Classroom management
  - Course management
  - User management

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, etc.)

## Project Structure

```
TimetableGenerator/
├── src/
│   ├── AdminPortal/
│   │   ├── AdminDashboard.java
│   │   ├── ClassroomManagement.java
│   │   ├── CourseManagement.java
│   │   └── UserManagement.java
│   ├── StudentPortal/
│   │   ├── StudentDashboard.java
│   │   ├── StudentSchedule.java
│   │   ├── CourseRegistrationWindow.java
│   │   ├── CourseSelectionDialog.java
│   │   └── AutoTimetableGenerator.java
│   ├── TeacherPortal/
│   │   ├── TeacherDashboard.java
│   │   ├── CourseStudents.java
│   │   └── TeacherSchedule.java
│   └── Login/
│       └── LoginWindow.java
├── data/
│   ├── users.csv
│   ├── courses.csv
│   ├── classrooms.csv
│   └── student_registrations.csv
└── README.md
```

## Setup Instructions

1. **Install JDK**

   - Download and install JDK 11 or higher from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html)
   - Set up JAVA_HOME environment variable

2. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd TimetableGenerator
   ```

3. **Import Project**

   - Open your Java IDE
   - Import the project as an existing Java project
   - Ensure the JDK is properly configured

4. **Data Files Setup**
   - The `data` directory contains necessary CSV files
   - Ensure the following files exist:
     - `users.csv`: User credentials and roles
     - `courses.csv`: Course information
     - `classrooms.csv`: Classroom details
     - `student_registrations.csv`: Student course registrations

## Running the Application

1. **Compile the Project**

   - In your IDE, build the project
   - Ensure there are no compilation errors

2. **Run the Application**
   - Run `LoginWindow.java` as the main class
   - The application will start with the login screen

## Default Login Credentials

- **Admin**

  - Username: admin
  - Password: admin123

- **Teacher**

  - Username: teacher
  - Password: teacher123

- **Student**
  - Username: student
  - Password: student123

## Usage Guide

### For Students

1. Log in with student credentials
2. Navigate to "Register for Courses" to select courses
3. Use "View My Schedule" to see your timetable
4. Download your schedule using the download button

### For Teachers

1. Log in with teacher credentials
2. View your assigned courses and students
3. Check your teaching schedule
4. Manage course components

### For Administrators

1. Log in with admin credentials
2. Manage classrooms, courses, and users
3. Monitor system-wide operations

## Data File Formats

### users.csv

```
username,password,role
admin,admin123,admin
teacher,teacher123,teacher
student,student123,student
```

### courses.csv

```
course_code,course_name,credits,teacher_username
CS101,Introduction to Programming,3,teacher1
```

### classrooms.csv

```
Room Number,Capacity,Building,Floor,Facilities
F101,50,Main,1,Projector
```

### student_registrations.csv

```
username,course_code,component,section,timestamp
student1,CS101,LEC,1,1234567890
```

## Troubleshooting

1. **File Not Found Errors**

   - Ensure all CSV files exist in the `data` directory
   - Check file permissions

2. **Login Issues**

   - Verify credentials in `users.csv`
   - Check file encoding (should be UTF-8)

3. **Display Issues**
   - Ensure JDK is properly installed
   - Check system display settings

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For any queries or support, please contact the development team.
