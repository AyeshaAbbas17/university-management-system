import java.io.Serializable;

public class StudentAssignmentMapping implements Serializable {
    //Association class or junction class
    //a mapping /relationship with extra data(obtainedMarks)
    private Student student;
    private Assignment assignment;
    private double obtainedMarks;

    // Constructors
    public StudentAssignmentMapping() {
        this(null, null, 0);
    }

    public StudentAssignmentMapping(Student student, Assignment assignment, double obtainedMarks) {
        setStudent(student);
        setAssignment(assignment);
        setObtainedMarks(obtainedMarks);

    }

    //Setters, Getters and toString()
    public void setStudent(Student student) {
        if (student != null) {
            this.student = student;
        } else {
            System.out.println("Invalid Student!");
            this.student = null;
        }
    }

    public void setAssignment(Assignment assignment) {
        if (assignment != null) {
            this.assignment = assignment;
        } else {
            System.out.println("Invalid Assignment!");
            this.assignment = null;
        }
    }

    public void setObtainedMarks(double obtainedMarks) {
        if (obtainedMarks >= 0) {
            this.obtainedMarks = obtainedMarks;
        } else {
            System.out.println("Invalid Marks! Setting to 0.");
            this.obtainedMarks = 0;
        }
    }
    public Student getStudent() {
        return student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public double getObtainedMarks() {
        return obtainedMarks;
    }

     @Override
    public String toString() {
        String studentName = (student != null) ? student.getStudentName() : "None";
        String assignmentTitle = (assignment != null)? assignment.getTitle() : "None";
        return "Student: " + studentName +"\nAssignment: " + assignmentTitle +"\nObtained Marks: " + obtainedMarks;
    }

    
}

