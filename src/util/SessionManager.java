package util;

import model.User;
import service.LoginDAO;
import service.SQL_client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class SessionManager {
	private static User loggedInUser;
    private LoginDAO userRepository;

    public SessionManager(LoginDAO userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password, Connection conn) {
        // Authenticate user
        loggedInUser = userRepository.authenticateUser(username, password);
        if (loggedInUser != null) {
            // Get the employee ID of the logged-in user
            int employeeIdString = userRepository.getEmployeeIdByUsername(username);
            Integer loggedInUserEmployeeId = null;
            try {
                loggedInUserEmployeeId = Integer.valueOf(employeeIdString);
            } catch (NumberFormatException e) {
                // Handle the case where the employee ID is not a valid integer
                e.printStackTrace();
            }
            // Log the login attempt in the database
            logLoginAttempt(conn, loggedInUserEmployeeId, username, true); // Pass the connection
            return true; // Return true if login succeeds
        } else {
            // Log the failed login attempt in the database
            logLoginAttempt(conn, null, username, false); // Pass the connection
            return false; // Return false if login fails
        }
    }



    // Method to log login attempt in the database
    public void logLoginAttempt(Connection conn, Integer empId, String username, boolean success) {
        try {
            String query = "INSERT INTO payroll_system.login_attempts (emp_id, username, timestamp, success) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, empId != null ? empId : 0); 
                ps.setString(2, username);
                ps.setBoolean(3, success); 
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
  
    public static User getLoggedInUser(SessionManager sessionManager) {
        return SessionManager.getLoggedInUser(sessionManager);
    }


}
