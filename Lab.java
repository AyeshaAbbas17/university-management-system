import java.io.Serializable;

public class Lab extends AcademicUnit implements Serializable {
    protected int equipmentCount;
    protected int numberOfMachines;
    protected boolean isFunctional;
    protected int capacity;

    //Constructors
    public Lab() {
        this(0, "", "",0,0,0,false,0);
        
    }
    public Lab(int entityId, String name, String location, double baseMaintenanceCost, int equipmentCount,int numberOfMachines, boolean functional, int capacity) {
        super(entityId, name, location, baseMaintenanceCost);
        setEquipmentCount(equipmentCount);
        setNumberOfMachines(numberOfMachines);
        setFunctional(functional);
        setCapacity(capacity);
    }
    //Essential Methods
    @Override
    public double calculateOperationalCost() {
       if (!isFunctional) {
        return baseMaintenanceCost * 0.5;
    }
        return baseMaintenanceCost + (equipmentCount * 150) + (numberOfMachines * 200);
    }
    //Setters, Getters and toString()
    public void setEquipmentCount(int equipmentCount) {
        if (equipmentCount >= 0){
        this.equipmentCount = equipmentCount;
        }else{
            System.out.println("Invalid Equipment Count! Setting it to 0.");
            this.equipmentCount = 0;
        }
        
    }
    public void setNumberOfMachines(int numberOfMachines) {
        if (numberOfMachines >= 0){
            this.numberOfMachines = numberOfMachines;
        }else{
            System.out.println("Invalid Number Of Machines! Setting it to 0.");
            this.numberOfMachines = 0;
        }
    }
    public void setFunctional(boolean functional) {
        this.isFunctional = functional;
    }
    public void setCapacity(int capacity) {
        if (capacity >= 0){
            this.capacity = capacity;
        }else{
            System.out.println("Invalid Capacity! Setting it to 0.");
            this.capacity = 0;
        }  
    }
    public int getEquipmentCount() {
        return equipmentCount;
    }
    public int getNumberOfMachines() {
        return numberOfMachines;
    }
    public boolean isFunctional() {
        return isFunctional;
    }
    public int getCapacity() {
        return capacity;
    }
    @Override
    public String toString() {
        return super.toString()+"\nEquipment Count : " + equipmentCount + "\nNumber Of Machines : " + numberOfMachines + "\n Is it Functional ? "+ isFunctional + "\nCapacity : " + capacity;
    }   
}
