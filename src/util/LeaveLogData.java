package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.LeaveLog;

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
}
