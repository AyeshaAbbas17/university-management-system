import java.io.*;

public class FileHandler {
    private static final String FILE_NAME = "campus.dat";

    // -------Writing to save
    public static void writeToFile(CampusData data) {

        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objout = new ObjectOutputStream(fos);
            objout.writeObject(data);
            objout.close();
            saveBackup(data); // save backup automatically
        }

        catch (FileNotFoundException ex) {
            System.out.println("File not found exception occurred.");
        }

        catch (IOException ex) {
            System.out.println("IO Exception occurred while saving data.");
        }

        catch (Exception ex) {
            System.out.println("Unexpected error occurred during save.");
        }
    }

    // ---- READ to load
    public static CampusData readFromFile() {
        CampusData data = null;
        File file = new File(FILE_NAME);

        try {

            if (!file.exists()) {
                return null;
            }

            // EMPTY FILE HANDLING
            if (file.exists() && file.length() == 0) {
                System.out.println("Empty file detected. Creating new system.");
                // "return new CampusData(); " , cannot do this, because this would be treated
                // as an object and system wouldnt initialize
                // but this object has no real data stored, so it should be treated as empty
                // file.
                return null;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream objin = new ObjectInputStream(fis);
            data = (CampusData) objin.readObject();
            objin.close();
        }

        catch (EOFException ex) {
            System.out.println("End of file reached unexpectedly.");
            System.out.println("Trying backup file...");
            return readBackupFile();
        }

        catch (ClassNotFoundException ex) {
            System.out.println("Class not found while reading file.");
            System.out.println("Trying backup file...");
            return readBackupFile();
        }

        catch (FileNotFoundException ex) {
            System.out.println("Main file not found.");
            System.out.println("Trying backup file...");
            return readBackupFile();
        }

        catch (IOException ex) {
            System.out.println("IO Exception or corrupted file detected.");
            System.out.println("Trying backup file...");
            return readBackupFile();
        }

        catch (Exception ex) {
            System.out.println("Unexpected error occurred while reading file.");
            System.out.println("Trying backup file...");
            return readBackupFile();
        }
        return data;
    }

    public static void saveBackup(CampusData data) {
        try {
            FileOutputStream fos = new FileOutputStream("backup.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        }

        catch (Exception ex) {
            System.out.println("Backup save failed.");
        }
    }

    public static CampusData readBackupFile() {
        try {

            FileInputStream fis = new FileInputStream("backup.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            CampusData data = (CampusData) ois.readObject();
            ois.close();
            return data;
        }

        catch (Exception ex) {
            System.out.println("Backup file also failed.");
            return new CampusData();
        }
    }
}