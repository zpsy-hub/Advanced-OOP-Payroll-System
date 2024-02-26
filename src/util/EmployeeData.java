package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Employee;

public class EmployeeData {

    private Map<String, Employee> employeeMap;

    public EmployeeData() {
        employeeMap = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    public Employee getEmployee(String employeeId) {
        return employeeMap.get(employeeId);
    }

    public void updateEmployee(String employeeId, Employee updatedEmployee) {
        if (employeeMap.containsKey(employeeId)) {
            employeeMap.put(employeeId, updatedEmployee);
        } else {
            System.out.println("Employee with ID " + employeeId + " does not exist.");
        }
    }

    public void removeEmployee(String employeeId) {
        employeeMap.remove(employeeId);
    }

    public void loadFromCSV(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                try {
                    // Check if the data length is at least 19 (assuming you have 19 fields)
                    if (data.length >= 19) {
                        Employee employee = new Employee(data[0], data[1], data[2], data[3], data[4],
                                data[5], data[6], data[7], data[8], data[9], data[10], data[11],
                                data[12], Double.parseDouble(data[13]), Double.parseDouble(data[14]),
                                Double.parseDouble(data[15]), Double.parseDouble(data[16]),
                                Double.parseDouble(data[17]), Double.parseDouble(data[18]));
                        addEmployee(employee);
                    } else {
                        System.out.println("Incomplete data for employee: " + line);
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where parsing fails
                    System.out.println("Failed to parse employee data: " + line);
                    e.printStackTrace();
                }
            }
        }
    }



    public void saveToCSV(String filePath) throws IOException {
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
        }
    }
}
