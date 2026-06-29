import java.io.Serializable;
import java.util.ArrayList;

public class SecurityService extends ServiceUnit implements Notifiable, Serializable {
    
        protected int cctvCount;
        protected int overtimeHours;
        protected double equipmentCost;
        protected ArrayList<String> incidentReports;
        // Constructors
        public SecurityService() {
            this(0, "", "", 0, 0, 0, 0, 0.0,new ArrayList<>());
        }

        public SecurityService(int entityId, String name, String location, int serviceHours, int staffCount, int cctvCount,int overtimeHours,double equipmentCost,
                ArrayList<String> incidentReports) {
            super(entityId, name, location, serviceHours, staffCount);
            setCctvCount(cctvCount);
            setOvertimeHours(overtimeHours);
            setEquipmentCost(equipmentCost);
            setIncidentReports(incidentReports);
        }

        // Essential method
        @Override
        public double calculateOperationalCost() {

            return (serviceHours * 40)+(staffCount * 150)+ equipmentCost+ (overtimeHours * 75);
        }

        public void addIncident(String incident) {
            if (incident != null) {
                incidentReports.add(incident);
            }
        }

        public void displayIncidents() {
            if (incidentReports.isEmpty()) {
                System.out.println("No incidents reported.");
                return;
            }

            for (String i : incidentReports) {
                System.out.println(i);
            }
        }

        // @Overrirde interface's method
        @Override
        public void sendNotification() {
            System.out.println("Security alert notification sent.");
        }

        // Setters, getters, toString
        public void setCctvCount(int cctvCount) {
            if (cctvCount >= 0) {
                this.cctvCount = cctvCount;
            } else {
                this.cctvCount = 0;
            }
        }

        public void setOvertimeHours(int overtimeHours) {
            if (overtimeHours >= 0) {
                this.overtimeHours = overtimeHours;
            } else {
                this.overtimeHours = 0;
            }
        }

        public void setEquipmentCost(double equipmentCost) {
            if (equipmentCost >= 0) {
                this.equipmentCost = equipmentCost;
            } else {
                this.equipmentCost = 0;
            }
        }
        
        public void setIncidentReports(ArrayList<String> incidentReports) {
            if (incidentReports != null) {
                ArrayList<String> temp = new ArrayList<>(incidentReports);
                this.incidentReports= temp;
            } else {
                System.out.println("Inalid Report!Creating Empty List");
                this.incidentReports = new ArrayList<>();
            }
        }

        
        public int getCctvCount() {
            return cctvCount;
        }

        public int getOvertimeHours() {
            return overtimeHours;
        }

        public double getEquipmentCost() {
            return equipmentCost;
        }

        public ArrayList<String> getIncidentReports() {
            return new ArrayList<>(incidentReports);
        }

        @Override
        public String toString() {
            return super.toString()+ "\n Number of CCTV's Operating: " + cctvCount+ "\nOvertime Hours: " + overtimeHours+ "\nEquipment Cost: " + equipmentCost +"\n Number of Reports: "+incidentReports.size();
        }
    }