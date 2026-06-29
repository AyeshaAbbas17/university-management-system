//FOR GUI IMPLEMENTATION
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RoleDashboard implements Serializable {

    private CampusRepository<Course> courseRepo;
    private CampusRepository<Student> studentRepo;
    private AuthenticationManager authManager;
    private Scanner sc = new Scanner(System.in);

    public RoleDashboard(
            CampusRepository<Course> courseRepo, CampusRepository<Student> studentRepo, AuthenticationManager authManager) {
            this.courseRepo = courseRepo;
            this.studentRepo = studentRepo;
            this.authManager = authManager;
    }

    // ======================================================
    // ADMIN DASHBOARD (FULL SYSTEM CONTROL VIA REPOSITORY)
    // ======================================================
    public void adminDashboard(Admin a,
            HealthCenter hc,
            SecurityService ss,
            ArrayList<StudentAssignmentMapping> mappings, Department depart,
            Library library,
            Cafeteria cafeteria,
            Course oop,
            Course ds,
            Classroom croom) {

        while (true) {

            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. View All Students");
            System.out.println("2. View All Courses");
            System.out.println("3. Add Student (Repository)");
            System.out.println("4. Remove Student (Repository)");
            System.out.println("5. Add Course (Repository)");
            System.out.println("6. Remove Course (Repository)");
            System.out.println("7. Handle Medical Emergency");
            System.out.println("8. Check Classroom & Handle Schedule Clash");
            System.out.println("9. Generate Department Report");
            System.out.println("10. Generate Library Report");
            System.out.println("11. Display Cafeteria Menu");
            System.out.println("12. Exit");


            int choice;

            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input! Please enter a number (1-12).");
                sc.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {

                case 1:
                    studentRepo.displayItems();
                    break;

                case 2:
                    courseRepo.displayItems();
                    break;

                case 3:
                    System.out.print("Enter Username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Roll Number: ");
                    String roll = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();

                    System.out.print("Enter CGPA: ");
                    double cgpa = sc.nextDouble();
                    sc.nextLine();

                    Student newStudent = new Student(
                            username,
                            password,
                            "STUDENT",
                            name,
                            roll,
                            dept,
                            cgpa,
                            new ArrayList<Course>());

                    studentRepo.addItem(newStudent);
                    authManager.addUser(newStudent);

                    System.out.println("Student Added Successfully.");

                    break;

                case 4:
                    System.out.println("Enter student index to remove:");
                    int sr = sc.nextInt();
                    studentRepo.removeItem(studentRepo.getItem(sr));
                    break;

                case 5:

                    System.out.print("Enter Course ID: ");
                    String courseId = sc.nextLine();

                    System.out.print("Enter Course Name: ");
                    String courseName = sc.nextLine();

                    System.out.print("Enter Credit Hours: ");
                    int creditHours = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Department Name: ");
                    String deptName = sc.nextLine();

                    System.out.print("Enter Department Location: ");
                    String deptLocation = sc.nextLine();

                    System.out.print("Enter Time Slot: ");
                    String timeSlot = sc.nextLine();

                    System.out.print("Enter Classroom Name: ");
                    String roomName = sc.nextLine();

                    System.out.print("Enter Classroom Location: ");
                    String roomLocation = sc.nextLine();

                    System.out.print("Enter Classroom Capacity: ");
                    int capacity = sc.nextInt();

                    System.out.print("Enter Equipment Count: ");
                    int equipmentCount = sc.nextInt();

                    sc.nextLine();

                    // Creatingg DEPARTMENT
                    Department deptartment = new Department(
                            100,
                            deptName,
                            deptLocation,
                            10000,
                            new ArrayList<Course>(),
                            500000);

                    // Creatingg CLASSROOM
                    Classroom classroom = new Classroom(
                            1,
                            roomName,
                            roomLocation,
                            5000,
                            capacity,
                            true,
                            equipmentCount);

                    // Creatingg COURSE
                    Course newCourse = new Course(
                            new ArrayList<Student>(),
                            courseId,
                            courseName,
                            creditHours,
                            deptartment,
                            timeSlot,
                            classroom,
                            true);

                    // Adding COURSE TO DEPARTMENT
                    deptartment.addCourse(newCourse);

                    // adding TO REPOSITORY
                    courseRepo.addItem(newCourse);
                    SmartCampusRunner.saveSystemData();

                    System.out.println("Course Added Successfully.");
                    break;

                case 6:
                // System.out.println("CASE 6 ENTERED");
                    System.out.println("Enter course index to remove:");
                    int cr = sc.nextInt();
                     Course courseToRemove = courseRepo.getItem(cr);

                    // Removing enrolled students from course
                     courseToRemove.getStudents().clear();
                    // Remove from all teachers
                    System.out.println("Users count: " + authManager.getAllUsers().size());
                    for (User u : authManager.getAllUsers()) {
                        if (u instanceof Teacher) {
                        Teacher t = (Teacher) u;
                        t.removeCourse(courseToRemove);
                    //System.out.println("Courseeeeee removed!!!!!");
                    }
                    }
                    // Remove from all students
                    for (Student s : studentRepo.getAllItems()) {
                        s.getEnrolledCourses().remove(courseToRemove);
                    }
                    // Removinggggg from repository
                    courseRepo.removeItem(courseToRemove);
                    SmartCampusRunner.saveSystemData();
                    System.out.println("Course removed completely.");
                    break;

                case 7:
                    a.handleMedicalEmergency(hc, ss);
                    break;

                case 8:
                    System.out.println("Checking classroom availability...");
                    oop.checkClassroomAndSchedule(croom);

                    System.out.println("Checking schedule conflict...");
                    a.resolveScheduleConflict(courseRepo.getItem(0), courseRepo.getItem(1));
                    break;

                case 9:
                    depart.generateReport();
                    break;

                case 10:
                    library.generateReport();
                    break;

                case 11:
                    cafeteria.displayMenu();
                    break;

                case 12:
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ======================================================
    // TEACHER DASHBOARD (ONLY TEACHER CLASS METHODS)
    // ======================================================
    public void teacherDashboard(Teacher t,
            ArrayList<StudentAssignmentMapping> mappings) {

        while (true) {

            System.out.println("\n===== TEACHER DASHBOARD =====");
            System.out.println("1. Add Course");
            System.out.println("2. Remove Course");
            System.out.println("3. View Courses");
            System.out.println("4. Add Student to Course");
            System.out.println("5. Remove Student from Course");
            System.out.println("6. View Students in Course");
            System.out.println("7. Update Marks");
            System.out.println("8. Exit");

            int choice;
            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input! Please enter a number (1-8).");
                sc.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {

                case 1:  //teacher add coureses in his own list
                    System.out.println("Enter course index:");
                    int ci = sc.nextInt();
                    t.addCourse(courseRepo.getItem(ci));
                    break;

                case 2:
                    System.out.println("Enter course index:");
                    int rc = sc.nextInt();
                    t.removeCourse(courseRepo.getItem(rc));
                    break;

                case 3:
                    t.viewCourses();
                    break;

                case 4:  //picking from repo and adding in a new course
                    System.out.println("Enter student index in repository:");
                    int si = sc.nextInt();
                    System.out.println("Enter course index:");
                    int c = sc.nextInt();
                    t.addStudentToCourse(
                            studentRepo.getItem(si),
                            courseRepo.getItem(c));
                    break;

                case 5:
                    System.out.println("Enter student index:");
                    int sr = sc.nextInt();
                    System.out.println("Enter course index:");
                    int cr = sc.nextInt();
                    t.removeStudentFromCourse(
                            studentRepo.getItem(sr),
                            courseRepo.getItem(cr));
                    break;

                case 6:
                    System.out.println("Enter course index:");
                    int vc = sc.nextInt();
                    t.viewStudentsInCourse(courseRepo.getItem(vc));
                    break;

                case 7:
                System.out.println("Updating Marks...");
                System.out.print("Enter Student Index: ");
                int studentIndex = sc.nextInt();
                System.out.print("Enter Course Index: ");
                int courseIndex = sc.nextInt();
                 Course selectedCourse = courseRepo.getItem(courseIndex);
                System.out.println("Assignments in this course:");
                 ArrayList<Assignment> assgnList = selectedCourse.getAssignments();
                for (int i = 0; i < assgnList.size(); i++) {
                    System.out.println(i + ". " + assgnList.get(i).getTitle());
                }   
                System.out.print("Enter Assignment Index: ");
                int assignIndex = sc.nextInt();
                System.out.print("Enter New Marks: ");
                double marks = sc.nextDouble();
                Student selectedStudent = studentRepo.getItem(studentIndex);
                Assignment selectedAssignment = assgnList.get(assignIndex);

                t.updateMarks(mappings,selectedStudent,selectedAssignment,marks);
                break;

                case 8:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ======================================================
    // STUDENT DASHBOARD (CORRECT - NO CHANGE NEEDED)
    // ======================================================
    public void studentDashboard(Student s,
            ArrayList<StudentAssignmentMapping> mappings) {

        while (true) {

            System.out.println("\n===== STUDENT DASHBOARD =====");
            System.out.println("1. View Schedule");
            System.out.println("2. View Report");
            System.out.println("3. Exit");
            int choice;
            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input! Please enter a number (1-3).");
                sc.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    s.viewSchedule();
                    break;

                case 2:
                    s.viewReport(mappings);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}