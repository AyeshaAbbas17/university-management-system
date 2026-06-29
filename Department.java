import java.util.*;
import java.io.*;

public class Department extends AcademicUnit implements Reportable,Serializable{
    //dont add no of courses as array list will do the work here if added risk of mismatch between list and counter
    protected ArrayList<Course> courses; //aggregation
    protected double departmentBudget;
    //Constructors
    public Department() {
        this(0, "", "",0,new ArrayList<Course>(),0);
    }
    public Department(int entityId, String name, String location, double baseMaintenanceCost, ArrayList<Course> courses,double departmentBudget) {
        super(entityId, name, location, baseMaintenanceCost);
        setCourses(courses);
        setDepartmentBudget(departmentBudget);
    }
    //Essential Methods
    public int getTotalStudents() { //Helper Method
        int total = 0;
        for (Course c : courses) {
            total += c.getStudents().size();   //check later if getStudents return null
        }

        return total;
        }
    @Override
    public double calculateOperationalCost() {
        return baseMaintenanceCost +(courses.size() * 500) +(getTotalStudents() * 100);
    }
    public void addCourse(Course c){
        courses.add(c);

    }
   
    @Override
    public void generateReport() {
        int totalCourses = courses.size();
        int totalStudents = 0;
        for (Course c : courses) {
            totalStudents += c.getStudents().size();
        }
        double operationalCost = calculateOperationalCost();

        System.out.println("===== Department Report =====");
        System.out.println("Department Name: " + getName());
        System.out.println("Total Courses: " + totalCourses);
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Operational Cost: " + operationalCost);
        System.out.println();
         System.out.println("Department performance report generated.");
    }
    
    //Setters, Getters , toString()
    public void setCourses(ArrayList<Course> courses) {
    if (courses != null) {
        ArrayList<Course> temp = new ArrayList<>(courses);
        this.courses = temp;
    } else {
        System.out.println("Invalid Course List! Creating empty list.");
        this.courses = new ArrayList<>();
    }
}

    public void setDepartmentBudget(double departmentBudget) {
        
        if (departmentBudget >= 0){
            this.departmentBudget = departmentBudget;
        }else{
            System.out.println("Invalid Department Budget! Setting it to 0.");
            this.departmentBudget = 0;
        }  
    }
    public ArrayList<Course> getCourses() {
        return new ArrayList<Course>(courses);
    }
    public double getDepartmentBudget() {
        return departmentBudget;
    }


    @Override
    public String toString() {
        String temp = super.toString()+ "\nDepartment Budget : " + departmentBudget +"\nCourses : ";
        int i = 1;
        for(Course c :courses){
            temp +="\nCourse "+(i++)+" : "+ c.toString();
        }
        return temp;
    } 
}
