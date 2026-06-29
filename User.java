//for role based access
import java.io.Serializable;
import java.util.*;
public class User implements Serializable {
    protected String username;
    protected String password;
    protected String role; // admin , teacher , student

    // constructor
    public User() {
        this("", "", "");
    }
    public User(String username, String password, String role) {
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    // Essential Methods
    //gettiing called from AuthenticationManager for each object in the user list
    public boolean validateLogin(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean isRole(String role) {
        return this.role.equalsIgnoreCase(role);
    }

    // Setters, getters and toString()
    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        } else {
            this.username = "unknown";
        }
    }

    public void setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            this.password = password;
        } else {
            this.password = "1234";
        }
    }

    public void setRole(String role) {
        if (role != null && !role.trim().isEmpty()) {
            this.role = role;
        } else {
            this.role = "STUDENT";
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "Username: " + username + "\nRole: " + role;
    }
}