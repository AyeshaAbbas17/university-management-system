import java.io.Serializable;

public class TransportService extends ServiceUnit implements Schedulable, Serializable {
    
        protected int numberOfVehicles;
        protected double fuelCostPerDay;
        protected int distanceCoveredPerDay;
        protected boolean peakHour;

        // Constructors
        public TransportService() {
            this(0, "", "", 0, 0, 0, 0.0, 0, false);
        }

        public TransportService(int entityId, String name, String location,int serviceHours, int staffCount,int numberOfVehicles,double fuelCostPerDay,
        int distanceCoveredPerDay, boolean peakhr) {
            super(entityId, name, location, serviceHours, staffCount);
            setNumberOfVehicles(numberOfVehicles);
            setFuelCostPerDay(fuelCostPerDay);
            setDistanceCoveredPerDay(distanceCoveredPerDay);
            setPeakHour(peakhr);
        }

        // Operational Cost
        @Override
        public double calculateOperationalCost() {
            return (serviceHours * 50)+ (numberOfVehicles * 200)+ fuelCostPerDay+ (staffCount * 100);
        }

        //@Override Interface's method
        
        public void generateSchedule() {
        System.out.println("===== Transport Schedule =====");
        System.out.println("Base Route Schedule:");
        System.out.println("Morning: 8:00 AM - Pickup");
        System.out.println("Afternoon: 1:00 PM - Mid Route");
        System.out.println("Evening: 5:00 PM - Drop Off");
        System.out.println();
        System.out.println("Transport route schedule generated.");
        }

        // Setters,Getters, toString
        public void setNumberOfVehicles(int numberOfVehicles) {
            if (numberOfVehicles >= 0) {
                this.numberOfVehicles = numberOfVehicles;
            } else {
                System.out.println("Invalid number of vehicles, Setting to 0");
                this.numberOfVehicles = 0;
            }
        }

        public void setFuelCostPerDay(double fuelCostPerDay) {
            if (fuelCostPerDay >= 0) {
                this.fuelCostPerDay = fuelCostPerDay;
            } else {
                System.out.println("Invalid fuel cost, Setting to 0");
                this.fuelCostPerDay = 0;
            }
        }

        public void setDistanceCoveredPerDay(int distanceCoveredPerDay) {
            if (distanceCoveredPerDay >= 0) {
                this.distanceCoveredPerDay = distanceCoveredPerDay;
            } else {
                System.out.println("Invalid value for Distance, Setting to 0");
                this.distanceCoveredPerDay = 0;
            }
        }

        // Additional Complexity "TransportService adjusts routes based on peak hours "
        // Helper method called in setter, if pkhr detected
        public void adjustRoutesForPeakHours() {
                System.out.println("Peak hour adjustments applied:");
                System.out.println("- Extra bus added");
                System.out.println("- Route optimized");
            
        }


        public void setPeakHour(boolean peakhr) {
            this.peakHour = peakhr;

            if (this.peakHour) {
                adjustRoutesForPeakHours();
            } else {
                System.out.println("Normal traffic hours - no adjustment needed.");
            }
        }

        
        public int getNumberOfVehicles() {
            return numberOfVehicles;
        }

        public double getFuelCostPerDay() {
            return fuelCostPerDay;
        }

        public int getDistanceCoveredPerDay() {
            return distanceCoveredPerDay;
        }

        public boolean getPeakhr(){
            return peakHour;
        }
        @Override
        public String toString() {
            return super.toString() + "\nVehicles: " + numberOfVehicles + "\nFuel Cost: " + fuelCostPerDay+ "\nDistance Covered: " + distanceCoveredPerDay +"\n Peak Hours Status: "+peakHour;
        }
    }