//for aggregation in Course
import java.io.Serializable;
import java.util.*;

public class Student extends User implements Serializable{
    protected String studentName;
    protected String regNo;
    protected String departmentName;
    protected double cgpa;
    protected ArrayList<Course> enrolledCourses = new ArrayList<>();

    // Constructors
    public Student() {
        this("", "", "", "", "", "", 0.0, new ArrayList<Course>());

    }
    public Student(String username, String password, String role, String studentName, String regNo,
            String departmentName, double cgpa, ArrayList<Course> enrolledCourses) {

        super(username, password, role);
        setStudentName(studentName);
        setRegNo(regNo);
        setDepartmentName(departmentName);
        setCgpa(cgpa);
        setEnrolledCourses(enrolledCourses);
    }
    // Essential methods
    public void addCourse(Course c) {
        if (c != null && !enrolledCourses.contains(c)) {
            enrolledCourses.add(c);
        }
    }

    // mentioned in role based access
    public void viewSchedule() {
        System.out.println("===== Student Schedule =====");
        System.out.println("Student: " + studentName);
        System.out.println("Reg No: " + regNo);
        System.out.println();

        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        for (Course c : enrolledCourses) {
            System.out.println("Course Name: " + c.getCourseName());
            System.out.println("Course ID: " + c.getCourseId());
            System.out.println("Time Slot: " + c.getTimeSlot());

            if (c.getClassroom() != null) {
                System.out.println("Classroom: " + c.getClassroom().getName());
            } else {
                System.out.println("Classroom: Not Assigned");
            }

            System.out.println("---------------------------");
        }
    }

    public void viewReport(ArrayList<StudentAssignmentMapping> mappings) {
        System.out.println("===== Student Assignment Report =====");
        System.out.println("Student: " + studentName);
        System.out.println("Reg No: " + regNo);
        System.out.println();

        boolean found = false;

        for (StudentAssignmentMapping m : mappings) {
            if (m.getStudent() != null && m.getStudent().getRegNo().equals(this.regNo)) {
                found = true;
                Assignment a = m.getAssignment();

                System.out.println("Assignment Title: " + a.getTitle());
                System.out.println("Total Marks: " + a.getTotalMarks());
                System.out.println("Obtained Marks: " + m.getObtainedMarks());
                System.out.println("---------------------------");
            }
        }

        if (!found) {
            System.out.println("No assignment records found for this student.");
        }
    }

    // Setters, Getters, toString()

    public String getStudentName(){
        return this.studentName;
    }
   
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setCgpa(double cgpa) {
        if (cgpa >= 0 && cgpa <= 4.0) {
            this.cgpa = cgpa;
        } else {
            System.out.println("Invalid CGPA! Setting it to 0.");
            this.cgpa = 0;
        }
    }
    public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
        if (enrolledCourses != null) {
            this.enrolledCourses = new ArrayList<Course>(enrolledCourses);
        } else {
            System.out.println("Invalid Course List! Creating empty list.");
            this.enrolledCourses = new ArrayList<>();
        }
    }
    public String getRegNo() {
        return regNo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public double getCgpa() {
        return cgpa;
    }

    public ArrayList<Course> getEnrolledCourses() {
       
       // return new ArrayList<Course>(enrolledCourses);
        return enrolledCourses; // return the Actualll list
    }

    @Override

    public String toString() {
        return super.toString() +
                "\nStudent Name: " + studentName + "\nReg No: " + regNo + "\nCGPA: " + cgpa + "\nCourses: "
                + enrolledCourses.size();
    }
}