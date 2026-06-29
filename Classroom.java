import java.io.Serializable;

public class Classroom extends AcademicUnit implements Serializable{
    protected int capacity;
    protected boolean isAvailable;
    protected int equipmentCount;
    
    //Constructors
    public Classroom() {
        this(0, "", "",0.0 ,0,false,0);
    }
    public Classroom(int entityId, String name, String location, double baseMaintenanceCost, int capacity,boolean available, int equipmentCount) {
        super(entityId, name, location, baseMaintenanceCost);
        setCapacity(capacity);
        setAvailable(available);
        setEquipmentCount(equipmentCount);
    }
    //Essential Methods
    @Override
    public double calculateOperationalCost() {
        return baseMaintenanceCost + (capacity * 20) + (equipmentCount * 100);
    }


    //Setters, Getters, toString()
    public void setCapacity(int capacity) {
        if (capacity >= 0){
            this.capacity = capacity;
        }else{
            System.out.println("Invalid Capacity! Setting it to 0.");
            this.capacity = 0;
        }  
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    public void setEquipmentCount(int equipmentCount) {
        if (equipmentCount >= 0){
        this.equipmentCount = equipmentCount;
        }else{
            System.out.println("Invalid Equipment Count! Setting it to 0.");
            this.equipmentCount = 0;
        }
    }
    public int getCapacity(){
        return capacity;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public int getEquipmentCount(){
        return equipmentCount;
    }
    @Override
    public String toString(){
        return super.toString()+"\nCapacity : " + capacity + "\nIs it Available ? " + isAvailable + "\nEquipment Count ; " + equipmentCount;
    }
}