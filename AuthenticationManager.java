import java.io.Serializable;
import java.util.ArrayList;

// we have to add users one by one
public class AuthenticationManager implements Serializable {
    private ArrayList<User> users;

    public AuthenticationManager() {
        users = new ArrayList<>();
    }

    // Add user
    public void addUser(User u) {
        if (u != null) {
            users.add(u);
        }
    }

    // Login check
    public User login(String username, String password, String selectedRole) {
        //System.out.println(users.size());
        for (User u : users) {
            // calling methods of User class
            if (u.validateLogin(username, password) && u.isRole(selectedRole)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList <User> getAllUsers(){
        return users;
    }
}