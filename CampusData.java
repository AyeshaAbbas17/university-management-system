import java.io.Serializable;
import java.util.ArrayList;

public class CampusData implements Serializable {


    public ArrayList<AcademicUnit> academicUnits;

    // =========================
    // USERS (AUTHENTICATION SYSTEM)
    // =========================
    public ArrayList<User> users;
    // =========================
    // CORE ACADEMIC DATA
    // =========================
    
    public ArrayList<Student> students;
    public ArrayList<Teacher> teachers;
    public ArrayList<Admin> admins;

    public ArrayList<Course> courses;
    public ArrayList<Department> departments;
    // =========================
    // FACILITIES & SERVICES
    // =========================
    public ArrayList<Facility> facilities;

    public HealthCenter healthCenter;
    public SecurityService securityService;
    public TransportService transportService;
    // =========================
    // OPERATIONAL DATA
    // =========================
   // public ArrayList<StudentAssignmentMapping> mappings;
    ArrayList<StudentAssignmentMapping> mappings = new ArrayList<>();
    // =========================
    // CONSTRUCTOR
    // =========================
    public CampusData() {

        // Users
        users = new ArrayList<>();
        academicUnits = new ArrayList<>();
        // Academic
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        admins = new ArrayList<>();
        courses = new ArrayList<>();
        departments = new ArrayList<>();

        // Facilities
        facilities = new ArrayList<>();
        // Operations
        mappings = new ArrayList<>();
        // Services (single objects)
        healthCenter = null;
        securityService = null;
        transportService = null;
    }
}