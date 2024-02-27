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
import util.LeaveRequestData;

public class LeaveRequestService {

	// Submit a leave request
	public static boolean submitLeaveRequest(User loggedInEmployee, String leaveType, String startDate, String endDate) {
	    try {
	        // Get employee ID and name
	        String id = loggedInEmployee.getid();
	        String employeeName = loggedInEmployee.getLastName();

	        // Split start and end dates into year, month, and day components
	        String[] startComponents = startDate.split("-");
	        String[] endComponents = endDate.split("-");

	        // Calculate total days
	        int totalDays = calculateTotalDays(startComponents[0], startComponents[1], startComponents[2],
	                                            endComponents[0], endComponents[1], endComponents[2]);

	        // Check if requested days exceed leave balance
	        if (checkLeaveBalance(id, leaveType, totalDays)) {
	            // Deduct leave balance and submit leave request
	            LeaveRequestData.writeLeaveApplicationData(id, employeeName, leaveType, startDate, endDate, String.valueOf(totalDays));
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
    public static int calculateTotalDays(String startYear, String startMonth, String startDay, String endYear, String endMonth, String endDay) {
        try {
            // Convert date components to integers
            int startYearValue = Integer.parseInt(startYear);
            int startMonthValue = getMonthNumber(startMonth); // Convert month name to integer
            int startDayValue = Integer.parseInt(startDay);
            int endYearValue = Integer.parseInt(endYear);
            int endMonthValue = getMonthNumber(endMonth); // Convert month name to integer
            int endDayValue = Integer.parseInt(endDay);

            // Check if the second date is earlier than the first date
            LocalDate startDate = LocalDate.of(startYearValue, startMonthValue, startDayValue);
            LocalDate endDate = LocalDate.of(endYearValue, endMonthValue, endDayValue);
            if (endDate.isBefore(startDate)) {
                return 0; // Return 0 if end date is earlier than start date
            }

            // Calculate the difference in days
            int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // Adding 1 to include both start and end dates
            return totalDays;
        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            return -1; // Indicate an error condition
        }
    }

    // Method to get the integer representation of a month name
    private static int getMonthNumber(String monthName) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(monthName)) {
                // Month index starts from 1, so add 1 to the index
                return i + 1;
            }
        }
        return -1; // Return -1 if month name is not found
    }

    // Check if requested days exceed leave balance
    private static boolean checkLeaveBalance(String id, String leaveType, long totalDays) {
        try {
            List<Leave> leaves = LeaveRequestData.readLeaveCsv();
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
            List<Leave> leaves = LeaveRequestData.readLeaveCsv();
            for (Leave leave : leaves) {
                if (leave.getId().equals(id)) {
                    checkLeaveBalance(leave, leaveType, totalDays);
                    // Update leave balance in CSV
                    LeaveRequestData.updateLeaveCsv(leaves);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Employee not found with ID: " + id, "Leave Request Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deducting leave balance: " + e.getMessage(), "Leave Request Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
