package service;

import model.Employee;
import model.User;
import service.EmployeeDAO;
import util.SessionManager;
import util.UserRepository;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final User sessionManager;

    public EmployeeService(UserRepository userRepository, User loggedInEmployee) {
        this.employeeDAO = new EmployeeDAO(); 
        this.sessionManager = loggedInEmployee;
    }
    
    /**
     * Retrieves the details of the logged-in employee.
     *
     * @return The Employee object of the logged-in employee if found, null otherwise.
     */
    public Employee getLoggedInEmployee() {
        User loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            int empId = loggedInUser.getId(); // Assuming getId() returns an int directly
            return getEmployeeById(empId);
        } else {
            return null; // No user logged in
        }
    }


    /**
     * Retrieves an employee by their ID.
     *
     * @param empId The ID of the employee to retrieve.
     * @return The Employee object if found, null otherwise.
     */
    public Employee getEmployeeById(int empId) {
        return EmployeeDAO.getEmployeeById(empId);
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }
}
