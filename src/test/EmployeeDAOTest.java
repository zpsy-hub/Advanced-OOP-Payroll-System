package test;

import java.util.List;

import DAO.EmployeeDAO;
import model.Employee;

public class EmployeeDAOTest {
	public static void main(String[] args) {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

        // Test fetching all employees
        System.out.println("Fetching all employees:");
        List<Employee> employees = EmployeeDAO.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        // Test fetching an employee by ID
        System.out.println("\nFetching employee with ID 1:");
        Employee employee = EmployeeDAO.getEmployeeById(1);
        if (employee != null) {
            System.out.println(employee);
        } else {
            System.out.println("Employee not found.");
        }

        // Test creating a new employee
        System.out.println("\nCreating a new employee:");
        Employee newEmployee = new Employee(
            0, // Auto-incremented ID
            "Doe",
            "John",
            "1990-01-01",
            "123 Main St",
            "1234567890",
            "55-4476527-2",
            "545652640232",
            "888-572-294-000",
            "211385556888",
            "Regular",
            "IT",
            "Rank and File",
            "Jane Smith",
            30000.00,
            15000.00,
            187.50
        );

        boolean created = employeeDAO.createEmployee(newEmployee);
        if (created) {
            System.out.println("Employee created successfully.");
        } else {
            System.out.println("Error creating employee.");
        }

        /*// Test updating an employee
        System.out.println("\nUpdating employee with ID 1:");
        if (employee != null) {
            employee.setLastName("Smith");
            boolean updated = EmployeeDAO.updateEmployee(employee);
            if (updated) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Error updating employee.");
            }
        }

        // Test deleting an employee
        System.out.println("\nDeleting employee with ID 1:");
        boolean deleted = EmployeeDAO.deleteEmployee(1);
        if (deleted) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Error deleting employee.");
        }
    }*/
	}

}
