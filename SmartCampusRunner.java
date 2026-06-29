import java.util.*;
import java.io.*;

public class SmartCampusRunner {

    // ==========================
    // SYSTEM REPOSITORIES
    // ==========================
    static CampusRepository<Student> studentRepo = new CampusRepository<>();
    static CampusRepository<Course> courseRepo = new CampusRepository<>();
    static CampusRepository<Facility> facilityRepo = new CampusRepository<>();
    static CampusRepository<User> userRepo = new CampusRepository<>();
    static CampusRepository<AcademicUnit> academicRepo = new CampusRepository<>();

    // static fields for initialization as well as for admin control
    static Classroom c1;
    static Classroom c2;
    static Department csDept;
    static Course oop;
    static Course dsa;
    static Library library;
    static Cafeteria cafeteria;

    // ==========================
    // OTHER SYSTEM COLLECTIONS
    // ==========================
    static ArrayList<StudentAssignmentMapping> mappings = new ArrayList<>();

    // ==========================
    // AUTHENTICATION
    // ==========================
    static AuthenticationManager authManager = new AuthenticationManager();

    // ==========================
    // SERVICES
    // ==========================
    static HealthCenter healthCenter;
    static SecurityService securityService;

    // ==========================
    // SCANNER
    // ==========================
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" SMART UNIVERSITY CAMPUS MANAGEMENT ");
        System.out.println("========================================");

        // =========================================
        // LOAD SAVED DATA
        // =========================================
        loadSystemData();

        // =========================================
        // INITIALIZE SAMPLE DATA IF FILE IS EMPTY IS INSIDE loadystemData()
        // =========================================

        // =========================================
        // ROLE DASHBOARD
        // =========================================

        RoleDashboard dashboard = new RoleDashboard(courseRepo, studentRepo, authManager);

        // =========================================
        // LOGIN LOOP
        // =========================================
        while (true) {

            System.out.println("\n========== LOGIN ==========");
            System.out.println("1. Admin");
            System.out.println("2. Teacher");
            System.out.println("3. Student");
            System.out.println("4. Exit");
            int option;

            try {
                System.out.print("Choose Role: ");
                option = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input! Please enter a number (1-4).");
                sc.nextLine(); // clear invalid input
                continue;
            }
            String role = "";

            switch (option) {

                case 1:
                    role = "ADMIN";
                    break;

                case 2:
                    role = "TEACHER";
                    break;

                case 3:
                    role = "STUDENT";
                    break;

                case 4:

                    System.out.println("Saving System Data...");
                    saveSystemData();

                    System.out.println("System Closed Successfully.");
                    return;

                default:
                    System.out.println("Invalid Option!");
                    continue;
            }

            // =========================================
            // USERNAME PASSWORD INPUT
            // =========================================

            System.out.print("Enter Username: ");
            String username = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            // =========================================
            // AUTHENTICATION
            // =========================================

            User loggedInUser = authManager.login(username, password, role);

            // =========================================
            // VALID LOGIN
            // =========================================

            if (loggedInUser != null) {

                System.out.println("\nLogin Successful!");
                System.out.println("Welcome " + loggedInUser.getUsername());

                // =====================================
                // ADMIN DASHBOARD
                // =====================================

                if (loggedInUser instanceof Admin) {
                    dashboard.adminDashboard((Admin) loggedInUser,
                            healthCenter,
                            securityService,
                            mappings, csDept, library, cafeteria, oop, dsa, c1);
                }

                // =====================================
                // TEACHER DASHBOARD
                // =====================================

                else if (loggedInUser instanceof Teacher) {
                    dashboard.teacherDashboard(
                            (Teacher) loggedInUser,
                            mappings);
                }

                // =====================================
                // STUDENT DASHBOARD
                // =====================================

                else if (loggedInUser instanceof Student) {

                    dashboard.studentDashboard(
                            (Student) loggedInUser,
                            mappings);
                }

            } else {

                System.out.println("Invalid Credentials!");
            }
        }
    }

    // =====================================================
    // INITIALIZE SYSTEM
    // =====================================================

    public static void initializeSystem() {
        System.out.println("initializeSystem() is running");

        // =========================================
        // CLASSROOMS
        // =========================================

        c1 = new Classroom(
                1,
                "CS Lab 1",
                "Block A",
                5000,
                40,
                true,
                20);

        c2 = new Classroom(
                2,
                "Room B12",
                "Block B",
                4000,
                50,
                true,
                10);

        // =========================================
        // DEPARTMENT
        // =========================================

        csDept = new Department(
                101,
                "Computer Science",
                "Academic Block",
                10000,
                new ArrayList<Course>(),
                500000);

        // =========================================
        // COURSES
        // =========================================

        oop = new Course(
                new ArrayList<Student>(),
                "CS200",
                "Object Oriented Programming",
                3,
                csDept,
                "9:00 AM - 11:00 AM",
                c1,
                true);

        dsa = new Course(
                new ArrayList<Student>(),
                "CS210",
                "Data Structures",
                3,
                csDept,
                "11:00 AM - 1:00 PM",
                c2,
                true);

        // ADD COURSES TO DEPT
        ArrayList<Course> deptCourses = new ArrayList<>();
        deptCourses.add(oop);
        deptCourses.add(dsa);

        csDept.setCourses(deptCourses);

        // =========================================
        // ASSIGNMENTS
        // =========================================

        oop.addAssignment(
                "A1",
                "OOP Assignment",
                100,
                "12-06-2026");

        // =========================================
        // STUDENTS
        // =========================================

        Student s1 = new Student(
                "ayesha",
                "123",
                "STUDENT",
                "Ayesha",
                "SP23-BCS-001",
                "CS",
                3.8,
                new ArrayList<Course>());

        Student s2 = new Student(
                "hooria",
                "123",
                "STUDENT",
                "Hooria",
                "SP23-BCS-002",
                "CS",
                3.4,
                new ArrayList<Course>());

        // =========================================
        // ENROLLMENT
        // =========================================

        oop.enrollStudent(s1);
        oop.enrollStudent(s2);

        dsa.enrollStudent(s1);

        // =========================================
        // TEACHER
        // =========================================

        Teacher teacher = new Teacher(
                "teacher1",
                "123",
                "TEACHER",
                "T01",
                "CS",
                new ArrayList<Course>());

        teacher.addCourse(oop);
        teacher.addCourse(dsa);

        // =========================================
        // ADMIN
        // =========================================

        Admin admin = new Admin(
                "admin",
                "123",
                "ADMIN",
                "AD01",
                "Main Admin",
                "admin@uni.com");

        // =========================================
        // HEALTH CENTER
        // =========================================

        healthCenter = new HealthCenter();

        // =========================================
        // SECURITY SERVICE
        // =========================================

        securityService = new SecurityService(
                1,
                "Campus Security",
                "Main Gate",
                24,
                15,
                50,
                5,
                30000,
                new ArrayList<String>());

        // =========================================
        // REPOSITORIES
        // =========================================

        studentRepo.addItem(s1);
        studentRepo.addItem(s2);
        userRepo.addItem(s1);
        userRepo.addItem(s2);
        userRepo.addItem(teacher);
        userRepo.addItem(admin);

        courseRepo.addItem(oop);
        courseRepo.addItem(dsa);

        academicRepo.addItem(c1);
        academicRepo.addItem(c2);
        academicRepo.addItem(csDept);

        // =========================================
        // AUTHENTICATION USERS
        // =========================================

        authManager.addUser(admin);
        authManager.addUser(teacher);
        authManager.addUser(s1);
        authManager.addUser(s2);

        // =========================================
        // FACILITIES
        // =========================================

        library = new Library(
                1,
                "Central Library",
                "Block C",
                10000,
                true,
                200,
                new ArrayList<Book>());

        cafeteria = new Cafeteria(
                2,
                "Campus Cafe",
                "Main Area",
                7000,
                5000,
                300,
                true,
                new ArrayList<String>());

        facilityRepo.addItem(library);
        facilityRepo.addItem(cafeteria);

        // =========================================
        // STUDENT ASSIGNMENT MAPPINGS
        // =========================================

        Assignment assignment = oop.getAssignments().get(0);

        mappings.add(
                new StudentAssignmentMapping(
                        s1,
                        assignment,
                        88));

        mappings.add(
                new StudentAssignmentMapping(
                        s2,
                        assignment,
                        92));

        // =========================================
        // COMPLEX FUNCTIONALITY TESTING
        // =========================================

        admin.resolveScheduleConflict(oop, dsa);

        oop.checkClassroomAndSchedule(c1);

        // =========================================
        // TRANSPORT SERVICE
        // =========================================

        TransportService ts = new TransportService(
                1,
                "Campus Transport",
                "Transport Office",
                12,
                10,
                5,
                10000,
                120,
                true);

        ts.generateSchedule();
    }

    // =====================================================
    // SAVE SYSTEM DATA
    // =====================================================

    public static void saveSystemData() {

        CampusData data = new CampusData();

        data.students = studentRepo.getAllItems(); //
        data.courses = courseRepo.getAllItems();
        data.facilities = facilityRepo.getAllItems();
        data.users = userRepo.getAllItems();
        data.mappings= mappings;
        data.academicUnits = academicRepo.getAllItems();

        FileHandler.writeToFile(data);

        System.out.println("Data Saved Successfully.");
    }

    // =====================================================
    // LOAD SYSTEM DATA
    // =====================================================

    public static void loadSystemData() {

        CampusData data = FileHandler.readFromFile();
        // if (data == null || data.students == null || data.students.isEmpty() ||
        // data.teachers == null || data.teachers.isEmpty() || data.admins == null ||
        // data.admins.isEmpty()){
        if ((data == null) || data.users == null || data.users.isEmpty()) {
            System.out.println("No saved data found.");
            System.out.println("Initializing System");
            initializeSystem();
        } else {
            System.out.println("Loading saved data...");
            for (Student s : data.students) {
                studentRepo.addItem(s);
                // authManager.addUser(s);
            }
            if (data.courses != null) {
                for (Course c : data.courses) {
                    courseRepo.addItem(c);              // ← ADD: load into repo
                if (c.getCourseId().equals("CS200")) 
                    oop = c;
                if (c.getCourseId().equals("CS210")) 
                    dsa = c;
                }
            }
        
            for (Facility f : data.facilities) {
                facilityRepo.addItem(f);
                if (f instanceof Library)
                    library = (Library) f;
                if (f instanceof Cafeteria)
                    cafeteria = (Cafeteria) f;
            }
            for (User u : data.users) {
                authManager.addUser(u);
                userRepo.addItem(u);
            }
            for (AcademicUnit a : data.academicUnits) {
                academicRepo.addItem(a);
                if (a instanceof Department)
                    csDept = (Department) a;
                if (a instanceof Classroom) {
                    Classroom room = (Classroom) a;
                    if (c1 == null)
                        c1 = room;
                    else
                        c2 = room;
                }
            }

            if (data.mappings != null) {
                mappings.addAll(data.mappings);
            }

            healthCenter = new HealthCenter();
            securityService = new SecurityService(
                    1, "Campus Security", "Main Gate",
                    24, 15, 50, 5, 30000, new ArrayList<String>());
        }
    }
}