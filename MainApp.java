import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SmartCampusRunner.loadSystemData();

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}