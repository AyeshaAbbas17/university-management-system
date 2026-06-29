import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    JLabel titleLabel;
    JLabel roleLabel, userLabel, passLabel;
    JComboBox<String> roleBox;
    JTextField userField;
    JPasswordField passField;
    JButton loginButton;

    public LoginFrame() {
        setTitle("Smart Campus Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen

        // Use GridLayout for the form
        setLayout(new BorderLayout());

        // Title Panel 
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        titleLabel = new JLabel("Smart Campus Login", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel 
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        roleLabel = new JLabel("Select Role:");
        roleBox = new JComboBox<>(new String[] { "ADMIN", "TEACHER", "STUDENT" });

        userLabel = new JLabel("Username:");
        userField = new JTextField();

        passLabel = new JLabel("Password:");
        passField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);

        formPanel.add(roleLabel);
        formPanel.add(roleBox);
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        add(formPanel, BorderLayout.CENTER);

        //Action Listener for Login buton
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleBox.getSelectedItem();
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                // Using  existing AuthenticationManager
                User loggedIn = SmartCampusRunner.authManager.login(username, password, role);

                if (loggedIn != null) {
                    dispose(); // close login window
                    if (loggedIn instanceof Admin) {
                        new AdminDashboard((Admin) loggedIn);
                    } else if (loggedIn instanceof Teacher) {
                        new TeacherDashboard((Teacher) loggedIn);
                    } else if (loggedIn instanceof Student) {
                        new StudentDashboard((Student) loggedIn);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Invalid username or password!",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}