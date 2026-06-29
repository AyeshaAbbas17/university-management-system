import java.io.Serializable;
import java.util.ArrayList;

public class Cafeteria extends Facility implements Serializable {

    protected double foodCost;
    protected int dailyCustomers;
    protected ArrayList<String> menuItems;

    // Constructors
    public Cafeteria() {
        this(0, "", "", 0.0, 0.0, 0, false, new ArrayList<>());
    }

    public Cafeteria(int entityId, String name, String location, double maintenanceCost, double foodCost,
            int dailyCustomers, boolean isOpen, ArrayList<String> menuItems) {
        super(entityId, name, location, maintenanceCost, isOpen);
        setFoodCost(foodCost);
        setDailyCustomers(dailyCustomers);
        setMenuItems(menuItems);
    }

    // Essential Method
    @Override
    public double calculateOperationalCost() {
        if (!isOpen) {
            return maintenanceCost * 0.5;
        }
        return maintenanceCost + foodCost + (dailyCustomers * 8);
    }

    public void displayMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }
        System.out.println("---- Cafeteria Menu ----");
        for (String item : menuItems) {
            System.out.println("- " + item);
            System.out.println();
        }
    }

    // Setters,Getters.toString

    public void setFoodCost(double foodCost) {
        if (foodCost >= 0) {
            this.foodCost = foodCost;
        } else {
            System.out.println("Invalid Food Cost! Setting to 0");
            this.foodCost = 0;
        }
    }

    public void setDailyCustomers(int dailyCustomers) {
        if (dailyCustomers >= 0) {
            this.dailyCustomers = dailyCustomers;
        } else {
            System.out.println("Invalid Customer Count! Setting to 0");
            this.dailyCustomers = 0;
        }
    }

    public void setMenuItems(ArrayList<String> menuItems) {
        if (menuItems != null) {
            ArrayList<String> temp = new ArrayList<String>(menuItems);
            this.menuItems = temp;
        } else {
            System.out.println("Invalid items! Creating empty list");
            this.menuItems = new ArrayList<>();
        }
    }

    public double getFoodCost() {
        return foodCost;
    }

    public int getDailyCustomers() {
        return dailyCustomers;
    }

    public ArrayList<String> getMenuItems() {
        return new ArrayList<>(menuItems);
    }

    // toString

    @Override
    public String toString() {
        return super.toString() + "\nFood Cost: " + foodCost + "\nDaily Customers: " + dailyCustomers + "\nMenu Items: "
                + menuItems.size() + "\nCafe is Open: " + isOpen;
    }
}