package util;

import model.Employee;
import model.User;
import service.LoginService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private User loggedInUser;
    private UserRepository userRepository;
    private LoginService loginService;
    private Map<String, Boolean> loginAttempts;
    private EmployeeData employeeData;

    public SessionManager(UserRepository userRepository, EmployeeData employeeData) {
        this.userRepository = userRepository;
        this.employeeData = employeeData; // Injecting EmployeeData dependency
        loginService = new LoginService(userRepository);
        loginAttempts = new HashMap<>();
    }

    // Method to log in a user and set the logged-in user session
    public boolean login(String username, String password) throws IOException {
        boolean success = loginService.login(username, password);
        if (success) {
            loggedInUser = userRepository.getUserByUsername(username);
            if (loggedInUser != null) {
                // Get the employee ID of the logged-in user
                String loggedInUserEmployeeId = userRepository.getEmployeeIdByUsername(username);
                if (loggedInUserEmployeeId != null) {
                    // Retrieve employee personal details using the employee ID
                    employeeData.loadFromCSV("src/data/Employee Database.csv");
                    Employee loggedInEmployee = employeeData.getEmployee(loggedInUserEmployeeId);
                    // Set the logged-in employee's personal details
                    loggedInUser.setEmployee(loggedInEmployee);
                }
            }
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

    // Method to get the employee ID of the logged-in user
    public String getLoggedInUserEmployeeId() {
        if (isLoggedIn()) {
            return userRepository.getEmployeeIdByUsername(loggedInUser.getUsername());
        } else {
            return null; // Return null if no user is logged in
        }
    }
}
