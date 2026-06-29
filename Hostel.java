import java.io.Serializable;
import java.util.*;

public class Hostel extends Facility implements Serializable {
    protected int totalRooms;
    protected ArrayList<Student> residents;

    // Constructors
    public Hostel() {
        this(0, "", "", 0.0, false, 0, new ArrayList<>());
    }

    public Hostel(int entityId, String name, String location, double cost, boolean isOpen, int totalRooms,
            ArrayList<Student> residents) {
        super(entityId, name, location, cost, isOpen);
        setTotalRooms(totalRooms);
        setResidents(residents);

    }

    // Essential Method
    @Override
    public double calculateOperationalCost() {

        if (!isOpen) {
            return maintenanceCost * 0.5;
        }

        return maintenanceCost + (residents.size() * 200);
    }

    // Hostel managemnt methods

    // Hostel Management Methods
    public void addResident(Student s) {
        if (s != null) {
            if (residents.size() < totalRooms) {
                residents.add(s);
            } else {
                System.out.println("Hostel is full!");
            }
        }
    }

    public void removeResident(Student s) {
        residents.remove(s);
    }

    public void displayResidents() {
        if (residents.isEmpty()) {
            System.out.println("No residents in hostel.");
            return;
        }
        for (Student s : residents) {
            System.out.println(s);
        }
    }

    // Setters, Getters, toString
    public void setTotalRooms(int totalRooms) {
        if (totalRooms >= 0) {
            this.totalRooms = totalRooms;
        } else {
            System.out.println("Invalid number for Rooms! Setting to 0");
            this.totalRooms = 0;
        }
    }

    public void setResidents(ArrayList<Student> residents) {
        if (residents != null) {
            ArrayList<Student> temp = new ArrayList<>(residents);
            this.residents = temp;
        }
        else {
            System.out.println("Invalid Resident List! Creating empty list.");
            this.residents = new ArrayList<>();
        }
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public int getOccupiedRooms() {
        return residents.size();
    }

    public ArrayList<Student> getResidents() {
        return new ArrayList<>(residents);
    }

    @Override
    public String toString() {
        return super.toString() + "\nTotal Rooms: " + totalRooms + "\nOccupied Rooms: " + residents.size()
                + "\nHostel Open: " + isOpen;
    }
}