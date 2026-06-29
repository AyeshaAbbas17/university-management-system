import java.io.Serializable;

public abstract class Facility extends CampusEntity implements Serializable{
    protected double maintenanceCost;
    protected boolean isOpen;
    
    // Constructors
    public Facility() {
        this(0, "", "",0.0,false);
    }

    public Facility(int entityId, String name, String location,double cost,boolean isOpen) {
        super(entityId, name, location);
        setCost(cost);
        setIsOpen(isOpen);
    }

    //Essential methods
    public abstract double calculateOperationalCost();

    public void openFacility() {
        isOpen = true;
    }

    public void closeFacility() {
        isOpen = false;
    }

    // Setters, Getters and toString()
    public void setCost(double cost) {
        if (cost >= 0) {
            this.maintenanceCost = cost;
        } else {
            System.out.println("Invalid Cost");
            System.out.println("Setting to 0.");
            this.maintenanceCost = 0;
        }
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public double getCost() {
        return maintenanceCost;
    }


    @Override
    public String toString() {
        return super.toString() + "\nMaintenance Cost : " + maintenanceCost;
        
    }

    
        
    

}// end of Facility