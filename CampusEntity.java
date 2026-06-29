public abstract class CampusEntity{
    
    protected int entityId;
    protected String name;
    protected String location;

    //Constructors    
    public CampusEntity() {
        this(0, "", "");
    }
    public CampusEntity(int entityId, String name, String location) {
        setEntityId(entityId);
        setName(name);
        setLocation(location);
    }

    //Essential methods
    public abstract double calculateOperationalCost();

    //Setters, Getters and toString()
    public void setEntityId(int entityId) {
        if(entityId >= 0){
            this.entityId = entityId;
        }else{
            System.out.println("Invalid ID !");
        }
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +"\nEntity ID : " + entityId + "\nName : " + name + "\nLocation : " + location ;
    }
    
}