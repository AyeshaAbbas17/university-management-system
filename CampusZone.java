//It will aggregate Facility and Service Unit
//can make ArrayLists of type Facility and Service Unit

import java.io.Serializable;
import java.util.*;

public class CampusZone implements Serializable {
    private String zoneName;
    private ArrayList<Facility> facilities;
    private ArrayList<ServiceUnit> services;

    // Constructors
    public CampusZone() {
        this("", new ArrayList<>(), new ArrayList<>());
    }
    public CampusZone(String zoneName,ArrayList<Facility> facilities,ArrayList<ServiceUnit> services) {
        setZoneName(zoneName);
        setFacilities(facilities);
        setServices(services);
    }

    // Essential Methods
    public void addFacility(Facility f) {
        if (f != null) {
            facilities.add(f);
        }
    }
    public void addService(ServiceUnit s) {
        if (s != null) {
            services.add(s);
        }
    }

    public void removeFacility(Facility f) {
        facilities.remove(f);
    }

    public void removeService(ServiceUnit s) {
        services.remove(s);
    }

    public void displayFacilities() {
        if (facilities.isEmpty()) {
            System.out.println("No Facilities Available.");
            return;
        }

        for (Facility f : facilities) {
            System.out.println(f);
            System.out.println();
        }
    }

    public void displayServices() {
        if (services.isEmpty()) {
            System.out.println("No Services Available.");
            return;
        }

        for (ServiceUnit s : services) {
            System.out.println(s);
            System.out.println();
        }
    }
    
    //polyorphism 
    public double calculateTotalOperationalCost() {
        double total = 0;
        for (Facility f : facilities) {
            total += f.calculateOperationalCost();
        }

        for (ServiceUnit s : services) {
            total += s.calculateOperationalCost();
        }

        return total;
    }

    // Setters, Getters, toString
    public void setZoneName(String zoneName) {
        if (zoneName != null) {
            this.zoneName = zoneName;
        } else {
            System.out.println("Invalid Zone Name! Setting empty name.");
            this.zoneName = "";
        }
    }

    public void setFacilities(ArrayList<Facility> facilities) {
        if (facilities != null) {
            ArrayList<Facility> temp = new ArrayList<>(facilities);
            this.facilities = temp;
        } else {
            System.out.println("Invalid Facility List! Creating empty list.");
            this.facilities = new ArrayList<>();
        }
    }

    public void setServices(ArrayList<ServiceUnit> services) {
        if (services != null) {
            ArrayList<ServiceUnit> temp = new ArrayList<>(services);
            this.services = temp;
        } else {
            System.out.println("Invalid Service List! Creating empty list.");
            this.services = new ArrayList<>();
        }
    }

    public String getZoneName() {
        return zoneName;
    }

    public ArrayList<Facility> getFacilities() {
        return new ArrayList<>(facilities);
    }

    public ArrayList<ServiceUnit> getServices() {
        return new ArrayList<>(services);
    }

    @Override
    public String toString() {

        return "Zone Name: " + zoneName+ "\nFacilities: " + facilities.size()+ "\nServices: " + services.size()+ "\nTotal Operational Cost: " + calculateTotalOperationalCost();
    }
}
