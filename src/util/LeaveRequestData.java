package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.LeaveBalance;

public class LeaveRequestData {

    // Read LeaveBalance data from a CSV file and return a list of leaves
    public static List<LeaveBalance> readLeaveCsv() throws IOException {
        List<LeaveBalance> leaves = new ArrayList<>();
        String filename = "src/data/LeaveBalance Balance.csv";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line; // Variable to store each line read from the CSV

        // Skip the header line
        reader.readLine();

        // Read each line from the CSV and create a LeaveBalance object
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String id = fields[0];
            String lastName = fields[1];
            String firstName = fields[2];
            int sickLeaveDays = Integer.parseInt(fields[3]);
            int vacationLeaveDays = Integer.parseInt(fields[4]);
            int emergencyLeaveDays = Integer.parseInt(fields[5]);

            // Create a LeaveBalance object and add it to the list
            LeaveBalance leave = new LeaveBalance(id, lastName, firstName, sickLeaveDays, emergencyLeaveDays, vacationLeaveDays);
            leaves.add(leave);
        }

        reader.close(); // Close the reader
        return leaves;
    }

    // Update the leave balance in the CSV file
    public static void updateLeaveCsv(List<LeaveBalance> leaves) throws IOException {
        String filename = "src/Data/LeaveBalance Balance Tally.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Write the header line
        writer.write("Employee ID,Last Name,First Name,Sick LeaveBalance Days,Vacation LeaveBalance Days,Emergency LeaveBalance Days\n");

        // Write leave data for each leave object
        for (LeaveBalance leave : leaves) {
            writer.write(leave.getId() + "," +
                    leave.getLastName() + "," +
                    leave.getFirstName() + "," +
                    leave.getSickLeaveDays() + "," +
                    leave.getVacationLeaveDays() + "," +
                    leave.getEmergencyLeaveDays() + "\n");
        }

        writer.close(); // Close the writer
    }
    
    // Write leave application data to a CSV file
    public static void writeLeaveApplicationData(String employeeId, String employeeName, String leaveType, String startDate, String endDate, String totalDays) throws IOException {
        String filePath = "src/Data/LeaveBalance Application Approved Dates.csv";

        // Create a BufferedWriter to write to the file, with append mode enabled
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

        // Write the leave application data to the file
        writer.write(employeeId + "," +
                employeeName + "," +
                leaveType + "," +
                startDate + "," +
                endDate + "," +
                totalDays + "\n");

        writer.close(); // Close the writer
    }
    
    // Retrieve leave data for a specific employee ID
    public static LeaveBalance getLeaveDataByEmployeeId(String employeeId) throws IOException {
        List<LeaveBalance> leaves = readLeaveCsv();
        for (LeaveBalance leave : leaves) {
            if (leave.getId().equals(employeeId)) {
                return leave;
            }
        }
        return null; // Employee ID not found
    }

    // Update leave balance based on leave type
    public static void updateLeaveBalance(LeaveBalance leave, String leaveType, long totalDays) {
        switch (leaveType) {
            case "Sick LeaveBalance":
                leave.setSickLeaveDays(leave.getSickLeaveDays() - (int) totalDays);
                break;
            case "Vacation LeaveBalance":
                leave.setVacationLeaveDays(leave.getVacationLeaveDays() - (int) totalDays);
                break;
            case "Emergency LeaveBalance":
                leave.setEmergencyLeaveDays(leave.getEmergencyLeaveDays() - (int) totalDays);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid leave type.", "LeaveBalance Request Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
    
    // Retrieve the leave tally balance for a specific employee and leave type
    public static int getLeaveTallyBalance(String employeeId, String leaveType) {
        try {
            // Retrieve leave data for the employee
            LeaveBalance leave = LeaveRequestData.getLeaveDataByEmployeeId(employeeId);

            // Check if leave data is found
            if (leave != null) {
                // Determine the leave balance based on the leave type
                switch (leaveType) {
                    case "Sick LeaveBalance":
                        return leave.getSickLeaveDays();
                    case "Vacation LeaveBalance":
                        return leave.getVacationLeaveDays();
                    case "Emergency LeaveBalance":
                        return leave.getEmergencyLeaveDays();
                    default:
                        // Handle invalid leave type
                        JOptionPane.showMessageDialog(null, "Invalid leave type.", "LeaveBalance Request Error", JOptionPane.ERROR_MESSAGE);
                        return 0;
                }
            } else {
                // Handle case where employee ID is not found
                JOptionPane.showMessageDialog(null, "Employee ID not found.", "LeaveBalance Request Error", JOptionPane.ERROR_MESSAGE);
                return 0;
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return 0;
        }
    }
    
}
