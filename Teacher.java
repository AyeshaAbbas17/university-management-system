import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends User implements Serializable {
    protected String teacherId;
    protected String department;
    protected ArrayList<Course> courses;

    // Constructors
    public Teacher() {
        this("", "", "", "", "", new ArrayList<Course>());
    }

    public Teacher(String username, String password, String role,String teacherId, String department,ArrayList<Course> courses) {
        super(username, password, role);
        setTeacherId(teacherId);
        setDepartment(department);
        setCourses(courses);
    }

    // Essential Methods

    //methods for role based access
    public void addCourse(Course c) {
    if (c != null && !courses.contains(c)) {
        courses.add(c);
        System.out.println("Course added: " + c.getCourseName());
        }
    }
    public void removeCourse(Course c) {
        if (courses.remove(c)) {
            System.out.println("Course removed: " + c.getCourseName());
        } else {
            System.out.println("Course not found.");
        }
    }

    public void viewCourses() {
        System.out.println("===== Teacher Courses =====");
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        for (Course c : courses) {
            System.out.println("Course ID: " + c.getCourseId());
            System.out.println("Course Name: " + c.getCourseName());
            System.out.println("Time Slot: " + c.getTimeSlot());
            System.out.println("----------------------");
        }
    }
    public void addStudentToCourse(Student s, Course c) {
        if (s != null && c != null) {
            c.enrollStudent(s);
            System.out.println("Student added to course: " + c.getCourseName());
        }
    }
    public void viewStudentsInCourse(Course c) {
        if (c == null) return;
        System.out.println("Students in " + c.getCourseName() + ":");
        for (Student s : c.getStudents()) {
            System.out.println("- " + s.getStudentName() + " (" + s.getRegNo() + ")");
        }
    }
    public void updateMarks(ArrayList<StudentAssignmentMapping> mappings,Student s,Assignment a,double newMarks) {
        for (StudentAssignmentMapping m : mappings) {
            if (m.getStudent() == s && m.getAssignment() == a) {
                m.setObtainedMarks(newMarks);
                System.out.println("Marks updated for " + s.getStudentName());
                return;
            }
        }
        System.out.println("Mapping not found.");
    }
    //student can be removed from course
    //so calling method in course
    //if removed directly here, we do c.getStudents,so it returns a copy
    //no change will be made in original array
    //IMP LINE So we you remove from it, we are removing from a temporary list — not the actual course data.
    public void removeStudentFromCourse(Student s, Course c) {
        if (s != null && c != null) {
            c.removeStudent(s);  
        }
    }

    // Setters, Getters, toString
    public void setTeacherId(String teacherId) {
        if (teacherId != null)
            this.teacherId = teacherId;
        else
            this.teacherId = "";
    }

    public void setDepartment(String department) {
        if (department != null)
            this.department = department;
        else
            this.department = "";
    }
    public void setCourses(ArrayList<Course> courses) {
        if (courses != null) {
            this.courses = new ArrayList<>(courses);
           // this.courses = courses;
        } else {
            this.courses = new ArrayList<>();
        }
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getDepartment() {
        return department;
    }
    public ArrayList<Course> getCourses() {
        //return new ArrayList<>(courses);
         return courses; // return the ACTUAL list
    }

    @Override
    public String toString() {
        return super.toString() + "\nTeacher ID: " + teacherId + "\nDepartment: " + department;
    }
}