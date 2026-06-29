abstract public class AcademicUnit extends CampusEntity {

   protected double baseMaintenanceCost;
   //Constructors
   public AcademicUnit() {
    this(0, "", "",0);
   }

   public AcademicUnit(int entityId, String name, String location, double baseMaintenanceCost) {
    super(entityId, name, location);
    setBaseMaintenanceCost(baseMaintenanceCost);
   }
   //Essential Methods
    public abstract double calculateOperationalCost();
    
   //Settrs, Getters and toString()
   public void setBaseMaintenanceCost(double baseMaintenanceCost) {
    if (baseMaintenanceCost >= 0) {
        this.baseMaintenanceCost = baseMaintenanceCost;
    } else {
        System.out.println("Invalid base maintenance cost!");
        System.out.println("Setting it to 0.");
        this.baseMaintenanceCost = 0;
    }
   }
   public double getBaseMaintenanceCost() {
    return baseMaintenanceCost;
   }

   @Override
   public String toString() {
    return super.toString()+ "\nBase Maintenance Cost : "+baseMaintenanceCost;
   }
      
}
