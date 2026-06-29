import java.io.Serializable;

public abstract class ServiceUnit extends CampusEntity implements Serializable {

    protected int serviceHours;
    protected int staffCount;
    protected String status;

    // Constructors
    public ServiceUnit() {
        this(0, "", "", 0, 0);
    }

    public ServiceUnit(int entityId, String name, String location,
            int serviceHours, int staffCount) {

        super(entityId, name, location);
        setServiceHours(serviceHours);
        setStaffCount(staffCount);
    }

    //Essesntial methods
    public abstract double calculateOperationalCost();

    // Setters,getters,toString
    public void setServiceHours(int serviceHours) {
        if (serviceHours >= 0) {
            this.serviceHours = serviceHours;
        } else {
            System.out.println("Invalid Service Hours!");
            this.serviceHours = 0;
        }
    }

    public void setStaffCount(int staffCount) {
        if (staffCount >= 0) {
            this.staffCount = staffCount;
        } else {
            System.out.println("Invalid Staff Count!");
            this.staffCount = 0;
        }
    }

    
    public int getServiceHours() {
        return serviceHours;
    }

    public int getStaffCount() {
        return staffCount;
    }

    @Override
    public String toString() {
        return super.toString()+ "\nService Hours: " + serviceHours+ "\nStaff Count: " + staffCount;
    }
}