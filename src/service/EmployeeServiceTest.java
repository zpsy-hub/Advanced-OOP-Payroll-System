package service;

import model.Employee;
import model.User;
import util.SessionManager;

public class EmployeeServiceTest {

    public static void main(String[] args) {
        // Mocking a logged-in user
        User loggedInUser = new User("1", null, null, null, null, null, null); // Assuming there's a constructor that takes an ID
        // Mocking a session manager
        SessionManager sessionManager = new SessionManager(null); // Assuming there's a constructor that takes a User parameter
        sessionManager.setCurrentUser(loggedInUser); // Assuming there's a setCurrentUser method

        // Mocking an EmployeeDAO instance
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Creating an EmployeeService instance
        EmployeeService employeeService = new EmployeeService(employeeDAO, sessionManager);

        // Test getLoggedInEmployee method
        Employee loggedInEmployee = employeeService.getLoggedInEmployee();
        if (loggedInEmployee != null) {
            System.out.println("Logged-in employee details:");
            System.out.println("Employee ID: " + loggedInEmployee.getEmpId());
            System.out.println("First Name: " + loggedInEmployee.getFirstName());
            System.out.println("Last Name: " + loggedInEmployee.getLastName());
            // Print other details as needed
        } else {
            System.out.println("No user is logged in.");
        }

        // Test getEmployeeById method
        String empId = "1"; // Assuming the ID of the employee to retrieve is "1"
        Employee employeeById = employeeService.getEmployeeById(empId);
        if (employeeById != null) {
            System.out.println("\nEmployee details by ID:");
            System.out.println("Employee ID: " + employeeById.getEmpId());
            System.out.println("First Name: " + employeeById.getFirstName());
            System.out.println("Last Name: " + employeeById.getLastName());
            // Print other details as needed
        } else {
            System.out.println("Employee with ID " + empId + " not found.");
        }
    }
}
