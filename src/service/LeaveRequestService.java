package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.JOptionPane;
import model.Leave;
import model.User;
import util.LeaveData;

public class LeaveRequestService {

	// Submit a leave request
    public static boolean submitLeaveRequest(User loggedInEmployee, String leaveType, String startDate, String endDate) {
        try {
            // Get employee ID and name
            String id = loggedInEmployee.getid();
            String employeeName = loggedInEmployee.getLastName();

            // Calculate total days
            long totalDays = calculateTotalDays(startDate, endDate);
            // Check if requested days exceed leave balance
            if (checkLeaveBalance(id, leaveType, totalDays)) {
                // Deduct leave balance and submit leave request
                LeaveData.writeLeaveApplicationData(id, employeeName, leaveType, startDate, endDate, String.valueOf(totalDays));
                deductLeaveBalance(id, leaveType, totalDays);
                JOptionPane.showMessageDialog(null, "Leave request submitted successfully.");
                return true; // Return true if request is successful
            } else {
                JOptionPane.showMessageDialog(null, "Error submitting leave request: Insufficient leave balance for " + leaveType,
                        "Leave Request Error", JOptionPane.ERROR_MESSAGE);
                return false; // Return false if request fails due to insufficient balance
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error submitting leave request: " + e.getMessage(),
                    "Leave Request Error", JOptionPane.ERROR_MESSAGE);
            return false; // Return false if request fails due to IOException
        }
    }
    
 // Calculate total days between start and end dates
    public static long calculateTotalDays(String startDateStr, String endDateStr) {
        try {
            // Parse start date string
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Parse end date string
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Calculate the difference in days between the end date and start date
            return endDate.toEpochDay() - startDate.toEpochDay() + 1; // Adding 1 to include both start and end dates
        } catch (DateTimeParseException e) {
            // Handle parsing errors
            e.printStackTrace();
            return -1; // Indicate an error condition
        }
    }


    // Check if requested days exceed leave balance
    public static boolean checkLeaveBalance(String id, String leaveType, long totalDays) {
        try {
            List<Leave> leaves = LeaveData.readLeaveCsv();
            for (Leave leave : leaves) {
                if (leave.getId().equals(id)) {
                    switch (leaveType) {
                        case "Sick Leave":
                            return leave.getSickLeaveDays() >= totalDays;
                        case "Vacation Leave":
                            return leave.getVacationLeaveDays() >= totalDays;
                        case "Emergency Leave":
                            return leave.getEmergencyLeaveDays() >= totalDays;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid leave type.", "Leave Request Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Employee not found with ID: " + id, "Leave Request Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error checking leave balance: " + e.getMessage(), "Leave Request Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Deduct leave balance
    private static void deductLeaveBalance(String id, String leaveType, long totalDays) {
        try {
            List<Leave> leaves = LeaveData.readLeaveCsv();
            for (Leave leave : leaves) {
                if (leave.getId().equals(id)) {
                    updateLeaveBalance(leave, leaveType, totalDays);
                    // Update leave balance in CSV
                    LeaveData.updateLeaveCsv(leaves);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Employee not found with ID: " + id, "Leave Request Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deducting leave balance: " + e.getMessage(), "Leave Request Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Update leave balance based on leave type
    private static void updateLeaveBalance(Leave leave, String leaveType, long totalDays) {
        switch (leaveType) {
            case "Sick Leave":
                leave.setSickLeaveDays(leave.getSickLeaveDays() - (int) totalDays);
                break;
            case "Vacation Leave":
                leave.setVacationLeaveDays(leave.getVacationLeaveDays() - (int) totalDays);
                break;
            case "Emergency Leave":
                leave.setEmergencyLeaveDays(leave.getEmergencyLeaveDays() - (int) totalDays);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid leave type.", "Leave Request Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
