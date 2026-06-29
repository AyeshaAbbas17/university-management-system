//for aggregation in Department
//can use ArrayList as department has multiple courses

import java.util.*;
import java.io.*;
public class Course implements Schedulable, Serializable{
    private ArrayList<Student> students = new ArrayList<>(); //aggregation
    private ArrayList<Assignment> assignments = new ArrayList<>(); //composition
    //no of Students can be calculated from array list's size
    private String courseId;
    private String courseName;
    private int creditHours;
    private Department department ; //to get department name for navigation
    private String timeSlot;
    private Classroom classroom;
    private boolean isScheduled;
    //Constructors
     public Course(){
        this(new ArrayList<Student>(), "", "",0,null,"",null, false);
     }

    public Course(ArrayList<Student> students, String courseId, String courseName,int creditHours, Department department, String time, Classroom croom, boolean scheduled) {
        setStudents(students);
        //no assignmennts its it is has composition so use add method
        setCourseId(courseId);
        setCourseName(courseName);
        setCreditHours(creditHours);
        setDepartment(department);
        setTimeSlot(time);
        setClassroom(croom);
        setScheduled(scheduled);
    }
    
    //method to add assignment one by one cuz it has composition
    //ALWAYS USE THIS METHOD
    public void addAssignment(String id, String title, int marks, String dueDate){
        Assignment a = new Assignment(id, title, marks, dueDate);
        this.assignments.add(a);
    }

    //ALWAYS USE THISSSSSS TO ENROLL STUDENT IN COURSE...........
    public void enrollStudent(Student s) {//so that a student can be added in a course and student's enrolled courses get updated simultaneously
        if (s != null) {
            students.add(s);
            s.addCourse(this);
            }
    }
    //helper method
    //If a classroom becomes unavailable then system reschedules affected classes 
    public void handleClassroomUnavailable() {
        System.out.println("Classroom is unavailable for course: " + courseName);
        System.out.println("Rescheduling...");
        isScheduled = false;
        System.out.println("New Time Slot: 11:00 AM - 1:00 PM");
        System.out.println("Room: Alternative Classroom Assigned");
        isScheduled = true;

    }
    public void checkClassroomAndSchedule(Classroom classroom) {

        if (classroom == null || !classroom.isAvailable()) {
            System.out.println("Classroom unavailable!");

            handleClassroomUnavailable(); // Course handles itself
        } else {
            System.out.println("Classroom available. Scheduling course...");

            isScheduled = true;
            generateSchedule();
        }
    }
    @Override
    public void generateSchedule() {

        System.out.println("===== Course Schedule =====");
        System.out.println("Course: " + courseName);
        System.out.println("Time Slot: " + timeSlot);
        if(classroom != null){
            System.out.println("Room: " + classroom.getName());
        }
        System.out.println("Course schedule generated.");
}
//======================Remove student=================================
    public void removeStudent(Student s) {
        if (s != null && students.contains(s)) {
            students.remove(s);
            s.getEnrolledCourses().remove(this); // mirror update on student side
            System.out.println("Student removed from course: " + courseName);
            System.out.println("Student removed from course: " + courseName);
        }
    }

    
    //Setters, Getters, toString()
    
    public void setStudents(ArrayList<Student> students) {
        if (students != null) {
            this.students = new ArrayList<>(students); 
        } else {
            this.students = new ArrayList<>();
        }
    }
    public void setAssignments(ArrayList<Assignment> assignments) {
        if (assignments != null) {
            this.assignments = new ArrayList<>(assignments);
        } else {
            this.assignments = new ArrayList<>();
        }
    }
    public void setCourseId(String courseId) {
        if (courseId != null && !courseId.trim().equals("")) {
            this.courseId = courseId;
        } else {
            System.out.println("Invalid Course ID!");
            this.courseId = "C0";
        }
    }
    public void setCourseName(String courseName) {
        if (courseName != null && !courseName.trim().equals("")) {
            this.courseName = courseName;
        } else {
            System.out.println("Invalid Course Name!");
            this.courseName = "Untitled Course";
        }
    }
   public void setCreditHours(int creditHours) {
        if (creditHours > 0) {
            this.creditHours = creditHours;
        } else {
            System.out.println("Invalid Credit Hours!");
            this.creditHours = 1;
        }
    }
    public void setDepartment(Department department) {
        if (department != null) {
            this.department = department;
        } else {
            System.out.println("Invalid Department!");
            this.department = null;
        }
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    public void setScheduled(boolean scheduled) {
        this.isScheduled= scheduled;
    }



    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }
    public ArrayList<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public Department getDepartment() {
        return department;
    }
    public String getTimeSlot() {
        return timeSlot;
    }
    public Classroom getClassroom() {
        return classroom;
    }

    @Override
    public String toString() {
        String temp =  "Course Id : " + courseId+ "\nCourse Name : " + courseName + "\nCredit Hours : " + creditHours;
        if(this.department != null){
            temp += "\nDepartment Name : "+department.getName();
        }
        int i=1;
        for(Student s : students){
            
            temp+="\nStudent "+(i++)+" : "+s.toString() ;
        }
        int j=1;
        for(Assignment a : assignments){
            
            temp+="\nAssignment "+(j++)+" : "+a.toString() ;
        }
        temp += "\nClassroom : "+classroom;
        temp +="\nTime Slot : "+ timeSlot;
        return temp;

    }
    
}
