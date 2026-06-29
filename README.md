# 🏫 Smart University Campus Management System

## About

The Smart University Campus Management System is a Java Swing desktop application developed for CSC-241 (Object-Oriented Programming). The system integrates academic operations, campus facilities, and student services into a single platform while demonstrating advanced object-oriented programming principles and software design practices.

The application provides dedicated dashboards for administrators, teachers, and students, allowing each user to access functionality relevant to their role. The system includes academic management, campus facility monitoring, classroom conflict resolution, emergency handling, report generation, an interactive campus map, and automatic data persistence through Java object serialization.

---

## Features

### Academic Management

- Department management and performance reporting
- Course creation and deletion
- Student enrollment and removal
- Assignment management
- Classroom availability checking
- Schedule conflict detection and resolution
- Timetable generation and visualization

### Campus Facilities

- Library management with book tracking and usage reports
- Cafeteria management with menu display and operational statistics
- Hostel resident management

### Campus Services

- Health Center emergency notifications
- Security incident handling and alerts
- Transport scheduling with peak-hour route adjustments
- Medical emergency handling through coordinated service notifications

### Reports and Analytics

- Department performance reports
- Library usage reports
- Cafeteria information and menu reports
- Student assignment reports
- Teacher timetable views

### Campus Map

- Interactive campus map implemented using a custom Swing `JPanel`
- Color-coded building statuses:
  - Green: Active/Open
  - Orange: Busy
  - Red: Closed
- Available to both Admin and Student dashboards

### GUI Features

- Java Swing-based desktop interface
- Role-based dashboards for Admin, Teacher, and Student
- CRUD operations using forms, tables, and input components
- Tabbed navigation system
- Automatic data saving when the application closes

---

## OOP Concepts Demonstrated

| Concept | Implementation |
|----------|----------------|
| **Inheritance** | `CampusEntity → AcademicUnit → Department, Classroom, Lab`<br>`CampusEntity → Facility → Library, Cafeteria, Hostel`<br>`CampusEntity → ServiceUnit → HealthCenter, SecurityService, TransportService`<br>`User → Admin, Teacher, Student` |
| **Abstract Classes** | `CampusEntity`, `AcademicUnit`, `Facility`, `ServiceUnit` |
| **Interfaces** | `Notifiable` (`Admin`, `HealthCenter`, `SecurityService`), `Schedulable` (`Course`, `TransportService`), `Reportable` (`Department`, `Library`) |
| **Composition** | `Course → Assignment` |
| **Aggregation** | `Department → Course`, `Library → Book`, `CampusZone → Facilities & Service Units`, `Course → Student` |
| **Runtime Polymorphism** | `calculateOperationalCost()` and `toString()` overrides across multiple classes |
| **Generics** | `CampusRepository<T>` |
| **File Handling & Serialization** | `FileHandler`, `CampusData`, and all serializable system classes |
| **Role-Based Access Control** | `User`, `Admin`, `Teacher`, `Student`, `AuthenticationManager` |

---

## Logical Flow of the System

### First Run

When the application is launched for the first time, the system checks for existing saved data. If no data file is found, `SmartCampusRunner.initializeSystem()` creates the default departments, courses, classrooms, facilities, services, users, assignments, and enrollments required by the application.

### Runtime

During execution, all operations performed through the graphical interface directly update the in-memory repositories. Student, course, facility, user, and academic data remain synchronized throughout the system.

### Saving Data

Whenever the application closes, the complete system state is stored inside a `CampusData` object and serialized into `campus.dat`. A backup copy is simultaneously written to `backup.dat` to reduce the risk of data loss.

### Loading Data

On startup, the application attempts to load `campus.dat`. If the file is missing, empty, or corrupted, the system automatically falls back to `backup.dat`. If both files fail, a fresh system initialization is performed.

---

## Project Structure

```text
Smart University Campus Management System
│
├── MainApp.java
├── SmartCampusRunner.java
├── RoleDashboard.java
├── AuthenticationManager.java
├── FileHandler.java
├── CampusData.java
├── CampusRepository.java
├── CampusZone.java
├── CampusEntity.java
├── AcademicUnit.java
├── Facility.java
├── ServiceUnit.java
├── Department.java
├── Course.java
├── Classroom.java
├── Lab.java
├── Assignment.java
├── Library.java
├── Cafeteria.java
├── Hostel.java
├── Book.java
├── HealthCenter.java
├── SecurityService.java
├── TransportService.java
├── User.java
├── Admin.java
├── Teacher.java
├── Student.java
├── StudentAssignmentMapping.java
├── LoginFrame.java
├── AdminDashboard.java
├── TeacherDashboard.java
├── StudentDashboard.java
└── CampusMapPanel.java
```

---

## Getting Started

### Requirements

- Java JDK
- VS Code, IntelliJ IDEA, or any Java-supported IDE

### Running the Application

1. Clone or download the repository.
2. Open the project folder in your preferred IDE.
3. Compile all Java files.
4. Run `MainApp.java`.
5. Log in using one of the default accounts below.

---

## Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | 123 |
| Teacher | teacher1 | 123 |
| Student | ayesha | 123 |
| Student | hooria | 123 |

> **Note:** Users must select the correct role during login. Valid credentials with an incorrect role selection will result in an authentication failure.

---

## Role-Based Access

### Admin

Administrators have complete control over the system and can:

- Add and remove students
- Add and remove courses
- View all students and courses
- Generate department performance reports
- Generate library usage reports
- View cafeteria information and menu details
- Trigger medical emergencies
- Notify both the Health Center and Security Service
- Check classroom availability
- Detect and resolve scheduling conflicts
- Access the interactive campus map

### Teacher

Teachers can manage their academic responsibilities and can:

- View their assigned courses
- Add courses to their teaching list
- Remove courses from their teaching list
- Enroll students in courses
- Remove students from courses
- View students enrolled in a particular course
- Access their timetable and course schedules

### Student

Students have read-only access and can:

- View their enrolled courses
- Access their class schedules
- View assignment reports and obtained marks
- Access the interactive campus map

---

## Data Storage & Recovery

The system uses Java object serialization to maintain persistent storage and ensure data integrity.

Key features include:

- Automatic saving to `campus.dat`
- Automatic backup creation in `backup.dat`
- Loading existing data on application startup
- Recovery from missing, empty, or corrupted files
- Exception handling for `IOException`, `EOFException`, `FileNotFoundException`, and `ClassNotFoundException`
- Complete system state preservation through the `CampusData` container class

---

## Team

- **Ayesha Abbas (FA25-BCS-020)**
- **Hooria Zainab (FA25-BCS-042)**
