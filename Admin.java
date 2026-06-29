import java.io.Serializable;

public class Admin extends User implements Notifiable,Serializable {
    private String adminId;
    private String adminName;
    private String email;
    
    // Constructors
    public Admin() {
        this("", "", "", "", "","");
    }

    public Admin(String username, String password, String role,String adminId, String adminName, String email) {
        super(username, password, role);
        setAdminId(adminId);
        setAdminName(adminName);
        setEmail(email);
    }

    // Essential Methods
    @Override
    public void sendNotification() {
        System.out.println("Notification sent to admin.");
    }

    // If a medical emergency occurs → notify SecurityService and HealthCenter
    public void handleMedicalEmergency(HealthCenter hc, SecurityService ss) {
        System.out.println("Medical emergency detected!");
        hc.sendNotification();
        ss.sendNotification();
    }
    // Course schedules update dynamically when conflicts arise

    public void resolveScheduleConflict(Course c1, Course c2) {
        if (c1.getClassroom() == c2.getClassroom() && c1.getTimeSlot().equals(c2.getTimeSlot())) {
            System.out.println("Schedule Conflict Detected!");
            // reschedule second course
            c2.setTimeSlot("1:00 PM - 3:00 PM");
            System.out.println(c2.getCourseName() + " rescheduled successfully.");
        }
    }

    // Setters and Getters and toString()
    public void setAdminId(String adminId) {
        if (adminId != null && !adminId.trim().equals("")) {
            this.adminId = adminId;
        } else {
            System.out.println("Invalid Admin ID!");
        }
    }

    public void setAdminName(String adminName) {
        if (adminName != null && !adminName.trim().equals("")) {
            this.adminName = adminName;
        } else {
            System.out.println("Invalid Admin Name!");
        }
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().equals("")) {
            this.email = email;
        } else {
            System.out.println("Invalid Email!");
        }
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return super.toString() + "\nAdmin ID: " + adminId + "\nAdmin Name: " + adminName + "\nEmail: " + email;
    }
}