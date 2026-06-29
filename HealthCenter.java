import java.io.Serializable;

public class HealthCenter extends ServiceUnit implements Notifiable, Serializable {
    protected int numberOfBeds;
    protected int patientPerDay;
    protected double medicineCostPerDay;

    // Constructors
    public HealthCenter() {
        this(0, "", "", 0, 0, 0, 0, 0.0);
    }

    public HealthCenter(int entityId, String name, String location, int serviceHours, int staffCount, int numberOfBeds,
            int patientPerDay, double medicineCostPerDay) {
        super(entityId, name, location, serviceHours, staffCount);
        setNumberOfBeds(numberOfBeds);
        setPatientPerDay(patientPerDay);
        setMedicineCostPerDay(medicineCostPerDay);
    }

    // Essential mrthod
    @Override
    public double calculateOperationalCost() {
        return (serviceHours * 60) + (staffCount * 200) + medicineCostPerDay + (patientPerDay * 30);
    }

    //@Override interface's method
    public void sendNotification() {
        System.out.println("Medical emergency notification sent.");
    }

    // Setters,getters,toString
    public void setNumberOfBeds(int numberOfBeds) {
        if (numberOfBeds >= 0) {
            this.numberOfBeds = numberOfBeds;
        } else {
            System.out.println("Invalid number of beds! Setting to 0.");
            this.numberOfBeds = 0;
        }
    }

    public void setPatientPerDay(int patientPerDay) {
        if (patientPerDay >= 0) {
            this.patientPerDay = patientPerDay;
        } else {
            System.out.println("Invalid number of patients! Setting to 0.");
            this.patientPerDay = 0;
        }
    }

    public void setMedicineCostPerDay(double medicineCostPerDay) {
        if (medicineCostPerDay >= 0) {
            this.medicineCostPerDay = medicineCostPerDay;
        } else {
            System.out.println("Invalid number! Setting to 0.");
            this.medicineCostPerDay = 0;
        }
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getPatientPerDay() {
        return patientPerDay;
    }

    public double getMedicineCostPerDay() {
        return medicineCostPerDay;
    }

    @Override
    public String toString() {
        return super.toString() + "\nBeds: " + numberOfBeds + "\nPatients/Day: " + patientPerDay + "\nMedicine Cost: "
                + medicineCostPerDay;
    }
}