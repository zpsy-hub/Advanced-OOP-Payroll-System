package util;

import model.User;
import service.LoginService;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private User loggedInUser;
    private UserRepository userRepository;
    private LoginService loginService;
    private Map<String, Boolean> loginAttempts;

    public SessionManager(UserRepository userRepository) {
        this.userRepository = userRepository;
        loginService = new LoginService(userRepository);
        loginAttempts = new HashMap<>();
    }

 // Method to log in a user and set the logged-in user session
    public boolean login(String username, String password) {
        boolean success = loginService.login(username, password);
        if (success) {
            loggedInUser = userRepository.getUserByUsername(username);
        }
        return success;
    }


    // Method to log out the current user and clear the session
    public void logout() {
        loggedInUser = null;
    }

    // Method to check if a user is logged in
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    // Method to get the logged-in user
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Method to log login attempt
    public void logLoginAttempt(String username, boolean success) {
        loginAttempts.put(username, success);
    }
}
