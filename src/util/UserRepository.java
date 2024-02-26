package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.UserRole;

public class UserRepository {
    private List<User> userList;
    private String csvFilePath = "src/data/Login Credentials.csv";

    public UserRepository() {
        userList = new ArrayList<>();
        loadUsersFromCSV();
    }

    // Load user data from CSV file
    private void loadUsersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean headerSkipped = false; // Flag to skip the header row
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the header row
                }
                String[] data = line.split(",");
                String employeeNumber = data[0]; // Assuming employee ID is the first column
                String lastName = data[1];
                String firstName = data[2];
                String position = data[3];
                String username = data[4];
                String password = data[5];
                UserRole role = determineUserRole(Integer.parseInt(employeeNumber));
                User user = new User(username, password, employeeNumber, lastName, firstName, position, role);
                userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserRole determineUserRole(int employeeNumber) {
        if (employeeNumber >= 6 && employeeNumber <= 9) {
            return UserRole.HR;
        } else if (employeeNumber >= 10 && employeeNumber <= 14) {
            return UserRole.PAYROLL;
        } else {
            return UserRole.EMPLOYEE;
        }
    }

    // Authenticate user based on username and password
    public UserRole authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getRole();
            }
        }
        return null; // Authentication failed
    }

    // Get user by username
    public User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    // Get employee ID based on the logged-in user's username
    public String getEmployeeIdByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user.getid(); // Corrected to getId() instead of getEmployeeId()
            }
        }
        return null; // Employee ID not found
    }
}
