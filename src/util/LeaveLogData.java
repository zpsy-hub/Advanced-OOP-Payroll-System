package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.LeaveLog;
import model.User;
import model.Employee;
import model.Leave;

public class LeaveLogData {

    public static List<LeaveLog> readLeaveLogsFromCSV(String filename) throws IOException {
        List<LeaveLog> leaveLogs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean headerSkipped = false; // Skip the header line

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the header line
                }

                String[] parts = line.split(",");
                if (parts.length != 10) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                try {
                    LeaveLog leaveLog = new LeaveLog(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim(),
                        parts[6].trim(),
                        Integer.parseInt(parts[7].trim()),
                        Integer.parseInt(parts[8].trim()),
                        parts[9].trim()
                    );

                    leaveLogs.add(leaveLog);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                }
            }
        }

        return leaveLogs;
    }
    
    // Method to get leave logs for a specific employee
    public static List<LeaveLog> getLeaveLogsForEmployee(String employeeId) throws IOException {
        List<LeaveLog> allLeaveLogs = readLeaveLogsFromCSV("src/data/Leave Log.csv");
        List<LeaveLog> employeeLeaveLogs = new ArrayList<>();
        
        // Filter leave logs for the specified employee
        for (LeaveLog leaveLog : allLeaveLogs) {
            if (leaveLog.getId().equals(employeeId)) {
                employeeLeaveLogs.add(leaveLog);
            }
        }
        
        return employeeLeaveLogs;
    }
    
    // Method to get leave logs for all employees
    public static List<LeaveLog> getAllLeaveLogs() throws IOException {
        List<LeaveLog> allLeaveLogs = readLeaveLogsFromCSV("src/data/Leave Log.csv");
        return allLeaveLogs;
    }
    
    // Write leave log data to a CSV file
    public static void writeLeaveLogData(LeaveLog leaveLog, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

        File file = new File(filePath);
        if (file.length() == 0) {
            writer.write("Date,Employee ID,Last Name,First Name,Leave Type,Start Date,End Date,Total Days,Remaining Balance,Status\n");
        }

        Leave leave = LeaveRequestData.getLeaveDataByEmployeeId(leaveLog.getId());
        if (leave != null) {
            int remainingBalance = calculateRemainingBalance(leave, leaveLog.getLeaveType(), leaveLog.getTotalDays());
            writer.write(leaveLog.getDate() + "," +
                    leaveLog.getId() + "," +
                    leaveLog.getLastName() + "," +
                    leaveLog.getFirstName() + "," +
                    leaveLog.getLeaveType() + "," +
                    leaveLog.getStartDate() + "," +
                    leaveLog.getEndDate() + "," +
                    leaveLog.getTotalDays() + "," +
                    remainingBalance + "," +
                    leaveLog.getStatus() + "\n");
        } else {
            JOptionPane.showMessageDialog(null, "Leave data not found for Employee ID: " + leaveLog.getId(), "Leave Request Error", JOptionPane.ERROR_MESSAGE);
        }

        writer.close(); 
    }

    public static void addLeaveRequest(User user, String leaveType, String startDate, String endDate, int totalDays, int remainingBalance) {
        String status = "Pending";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        
        LeaveLog leaveLog = new LeaveLog(
                currentDate,
                user.getid(),
                user.getLastName(),
                user.getFirstName(),
                leaveType,
                startDate,
                endDate,
                totalDays,
                remainingBalance,
                status
        );        
        try {
        	writeLeaveLogData(leaveLog, "src/data/Leave Log.csv");
            System.out.println("Leave request added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error adding leave request.");
        }
    }

    // Calculate remaining balance based on leave type and total days taken
    public static int calculateRemainingBalance(Leave leave, String leaveType, long totalDays) {
        int remainingBalance = 0;
        switch (leaveType) {
            case "Sick Leave":
                remainingBalance = leave.getSickLeaveDays() - (int) totalDays;
                break;
            case "Vacation Leave":
                remainingBalance = leave.getVacationLeaveDays() - (int) totalDays;
                break;
            case "Emergency Leave":
                remainingBalance = leave.getEmergencyLeaveDays() - (int) totalDays;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid leave type.", "Leave Request Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        return remainingBalance;
    }
}
