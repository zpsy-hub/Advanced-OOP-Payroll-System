package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import model.Employee;

public class EmployeeData {

    // Using LinkedHashMap to preserve insertion order
    private Map<String, Employee> employeeMap;
    private static final String DATABASE_FILE_PATH = "src/data/Employee Database.csv";
    private static final String LOG_FILE_PATH = "src/data/Employee Changes Log.csv";
    private boolean shouldLog = false; // Flag to control logging

    // Constructor
    public EmployeeData() {
        employeeMap = new LinkedHashMap<>(); 
    }

    // Method to add an employee
    public void addEmployee(Employee employee, String user) {
        employeeMap.put(employee.getId(), employee);
        saveToCSV(DATABASE_FILE_PATH);
        log(user, "Add", employee.getId(), "", "", "");
    }

    // Method to get an employee by ID
    public Employee getEmployee(String employeeId) {
        return employeeMap.get(employeeId);
    }

    // Method to update an employee
    public void updateEmployee(String employeeId, Employee updatedEmployee, String user) {
        if (employeeMap.containsKey(employeeId)) {
            Employee originalEmployee = employeeMap.get(employeeId);
            employeeMap.put(employeeId, updatedEmployee);
            saveToCSV(DATABASE_FILE_PATH);
            
            // Log the fields that have been updated
            logUpdatedFields(user, employeeId, originalEmployee, updatedEmployee);
        } else {
            JOptionPane.showMessageDialog(null, "Employee with ID " + employeeId + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to remove an employee
    public void removeEmployee(String employeeId, String user) {
        if (employeeMap.containsKey(employeeId)) {
            employeeMap.remove(employeeId);
            saveToCSV(DATABASE_FILE_PATH);
            log(user, "Remove", employeeId, "", "", "");
        } else {
            JOptionPane.showMessageDialog(null, "Employee with ID " + employeeId + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load employee data from a CSV file
    public void loadFromCSV(String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                // Check if the data length is at least 19 (assuming you have 19 fields)
                if (data.length >= 19) {
                    Employee employee = new Employee(data[0], data[1], data[2], data[3], data[4],
                            data[5], data[6], data[7], data[8], data[9], data[10], data[11],
                            data[12], Double.parseDouble(data[13]), Double.parseDouble(data[14]),
                            Double.parseDouble(data[15]), Double.parseDouble(data[16]),
                            Double.parseDouble(data[17]), Double.parseDouble(data[18]));
                    addEmployee(employee, "System");
                }
            }
        }
    }

    // Method to get a list of all employees
    public List<Employee> getEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    // Method to save employee data to a CSV file
    public void saveToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header
            writer.write("Id,LastName,FirstName,Birthday,Address,PhoneNumber,SssNumber,PhilhealthNumber,"
                    + "TinNumber,PagibigNumber,Status,Position,ImmediateSupervisor,BasicSalary,RiceSubsidy,"
                    + "PhoneAllowance,ClothingAllowance,GrossSemiMonthlyRate,HourlyRate\n");

            for (Employee employee : employeeMap.values()) {
                String employeeData = String.join(",", employee.getId(), employee.getLastName(),
                        employee.getFirstName(), employee.getBirthday(), employee.getAddress(),
                        employee.getPhoneNumber(), employee.getSssNumber(), employee.getPhilhealthNumber(),
                        employee.getTinNumber(), employee.getPagibigNumber(), employee.getStatus(),
                        employee.getPosition(), employee.getImmediateSupervisor(),
                        String.valueOf(employee.getBasicSalary()), String.valueOf(employee.getRiceSubsidy()),
                        String.valueOf(employee.getPhoneAllowance()),
                        String.valueOf(employee.getClothingAllowance()),
                        String.valueOf(employee.getGrossSemiMonthlyRate()),
                        String.valueOf(employee.getHourlyRate()));
                writer.write(employeeData);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save employee data to CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to log employee changes
    private void log(String user, String action, String employeeId, String fieldChanged, String oldValue, String newValue) {
        if (!shouldLog) {
            return; // Exit the method if logging is disabled
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String date = now.format(dateFormatter);
            String time = now.format(timeFormatter);
            String logEntry = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", date, time, user, action, employeeId, fieldChanged, oldValue, newValue);
            writer.write(logEntry);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to log employee changes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to enable/disable logging
    public void setLogging(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }
    
    private void logUpdatedFields(String user, String employeeId, Employee originalEmployee, Employee updatedEmployee) {
        if (!shouldLog) {
            return; // Exit the method if logging is disabled
        }

        // Check if the employee exists in the map
        if (!employeeMap.containsKey(employeeId)) {
            return; // Exit the method if the employee does not exist
        }

        Employee employee = employeeMap.get(employeeId);

        // Compare each field of the original and updated employees
        if (!originalEmployee.getLastName().equals(updatedEmployee.getLastName())) {
            log(user, "Update", employeeId, "LastName", originalEmployee.getLastName(), updatedEmployee.getLastName());
            employee.setLastName(updatedEmployee.getLastName());
        }
        // Add similar checks for other fields...

        // Save the changes to the CSV file
        saveToCSV(DATABASE_FILE_PATH);
    }

}
