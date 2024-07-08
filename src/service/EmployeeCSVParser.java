package service;

import org.apache.commons.csv.*;
import java.io.*;
import java.util.*;
import model.Employee;
import DAO.EmployeeDAO;

public class EmployeeCSVParser {
    public static List<Employee> parseEmployeesFromCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build())) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    String lastName = csvRecord.get("Last Name");
                    String firstName = csvRecord.get("First Name");
                    String birthday = convertDateFormat(csvRecord.get("Birthday")); // Convert date format
                    String address = csvRecord.get("Address");
                    String phoneNumber = csvRecord.get("Phone Number");
                    String sssNumber = csvRecord.get("SSS #");
                    String philhealthNumber = csvRecord.get("Philhealth #");
                    String tinNumber = csvRecord.get("TIN #");
                    String pagibigNumber = csvRecord.get("Pag-ibig #");
                    String status = csvRecord.get("Status");
                    String department = csvRecord.get("Department");
                    String position = csvRecord.get("Position");
                    String immediateSupervisor = csvRecord.get("Immediate Supervisor");
                    if (immediateSupervisor.equalsIgnoreCase("N/A")) {
                        immediateSupervisor = null;
                    }

                    double basicSalary = parseDouble(csvRecord.get("Basic Salary"));

                    int statusId = EmployeeDAO.getStatusIdByName(status);
                    if (statusId == -1) {
                        System.err.println("Invalid status: " + status);
                        continue;
                    }

                    int departmentId = EmployeeDAO.getDepartmentIdByName(department);
                    if (departmentId == -1) {
                        System.err.println("Invalid department: " + department);
                        continue;
                    }

                    int positionId = EmployeeDAO.getPositionIdByName(position);
                    if (positionId == -1) {
                        System.err.println("Invalid position: " + position);
                        continue;
                    }

                    int immediateSupervisorId = immediateSupervisor == null ? -1 : EmployeeDAO.getEmployeeIdByName(immediateSupervisor);
                    if (immediateSupervisor != null && immediateSupervisorId == -1) {
                        System.err.println("Invalid immediate supervisor: " + immediateSupervisor);
                        continue;
                    }

                    Employee employee = new Employee(0, lastName, firstName, birthday, address, phoneNumber, sssNumber, philhealthNumber, tinNumber, pagibigNumber, statusId, departmentId, positionId, immediateSupervisorId, basicSalary);
                    employees.add(employee);

                } catch (NumberFormatException e) {
                    System.err.println("Error parsing salary for record: " + csvRecord.toString() + " - " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error processing record: " + csvRecord.toString());
                }
            }

            // Batch insert employees into the database
            employeeDAO.batchAddEmployees(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Helper method to parse double values with commas
    private static double parseDouble(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(value.replace(",", ""));
    }

    // Helper method to convert date format from MM/DD/YYYY to YYYY-MM-DD
    private static String convertDateFormat(String date) {
        String[] parts = date.split("/");
        if (parts.length == 3) {
            return parts[2] + "-" + parts[0] + "-" + parts[1];
        } else {
            throw new IllegalArgumentException("Invalid date format: " + date);
        }
    }
}
