package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.UserRole;
import model.User;
import service.SQL_client;

public class UserRepository {
    public UserRepository() {
        // Constructor
    }

    public User authenticateUser(String username, String password) {
        try {
            Connection conn = SQL_client.getInstance().getConnection(); // Obtain database connection
            if (conn == null) {
                // Handle null connection (optional)
                System.err.println("Database connection is null.");
                return null;
            }
            
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM payroll_system.login_credentials WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int employeeNumber = rs.getInt("emp_id");
                String lastName = rs.getString("employee_lastname");
                String firstName = rs.getString("employee_firstname");
                String position = rs.getString("position");
                UserRole role = determineUserRole(position);
                return new User(username, password, employeeNumber, lastName, firstName, position, role);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return null; // Authentication failed or database error occurred
    }

    private UserRole determineUserRole(String position) {
        if (position.equalsIgnoreCase("Payroll Manager") || 
            position.equalsIgnoreCase("Payroll Team Leader") || 
            position.equalsIgnoreCase("Payroll Rank and File") ||
            position.equalsIgnoreCase("Accounting Head")) {
            return UserRole.PAYROLL;
        } else if (position.equalsIgnoreCase("HR Manager") || 
                   position.equalsIgnoreCase("HR Team Leader") || 
                   position.equalsIgnoreCase("HR Rank and File")) {
            return UserRole.HR;
        } else if (position.equalsIgnoreCase("IT Operations and Systems")) {
            return UserRole.IT;
        } else {
            return UserRole.EMPLOYEE;
        }
    }


    // Get employee ID based on the username
    public int getEmployeeIdByUsername(String username, String password) { // Changed return type to int
        User user = authenticateUser(username, password); // Get the user object
        if (user != null) {
            return user.getId(); // Return the employee ID of the user
        } else {
            return -1; // Return -1 if user not found
        }
    }
}