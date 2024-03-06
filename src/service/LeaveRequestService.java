package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import model.LeaveBalance;
import model.LeaveRequestLog;
import model.User;

public class LeaveRequestService {

    private LeaveBalanceDAO leaveBalanceDAO;
    private static final Map<String, Integer> MONTH_MAP = new HashMap<>();

    static {
        MONTH_MAP.put("January", 1);
        MONTH_MAP.put("February", 2);
        MONTH_MAP.put("March", 3);
        MONTH_MAP.put("April", 4);
        MONTH_MAP.put("May", 5);
        MONTH_MAP.put("June", 6);
        MONTH_MAP.put("July", 7);
        MONTH_MAP.put("August", 8);
        MONTH_MAP.put("September", 9);
        MONTH_MAP.put("October", 10);
        MONTH_MAP.put("November", 11);
        MONTH_MAP.put("December", 12);
    }

    public LeaveRequestService() {
        this.leaveBalanceDAO = new LeaveBalanceDAO(); 
    }

    public boolean submitLeaveRequest(User loggedInEmployee, String leaveType, int totalDays, String startDate, String endDate) {
        try {
            // Get employee ID and name
            int id = loggedInEmployee.getId();
            String employeeName = loggedInEmployee.getLastName() + ", " + loggedInEmployee.getFirstName();

            // Check if requested days exceed leave balance
            if (checkLeaveBalance(id, leaveType, totalDays)) {
                // Deduct leave balance and submit leave request
                LeaveRequestLog leaveRequestLog = new LeaveRequestLog(null, totalDays, employeeName, employeeName, employeeName, null, null, totalDays, totalDays, employeeName);
                leaveRequestLog.setTimestamp(new java.sql.Date(System.currentTimeMillis())); // Set current timestamp
                leaveRequestLog.setId(loggedInEmployee.getId());
                leaveRequestLog.setEmployeeLastName(loggedInEmployee.getLastName());
                leaveRequestLog.setEmployeeFirstName(loggedInEmployee.getFirstName());
                leaveRequestLog.setLeaveType(leaveType);
                leaveRequestLog.setDateStart(java.sql.Date.valueOf(parseDate(startDate))); 
                leaveRequestLog.setDateEnd(java.sql.Date.valueOf(parseDate(endDate))); 
                leaveRequestLog.setDaysTotal(totalDays);
                leaveRequestLog.setLeaveBalance(getUpdatedLeaveBalance(id, leaveType, totalDays)); // Calculate leave balance
                leaveRequestLog.setStatus("pending"); // Set status to pending

                LeaveRequestLogDAO.getInstance().addLeaveLog(leaveRequestLog);

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
    
 // Method to parse date in the format "Month-dd-yyyy" to "yyyy-MM-dd"
    private String parseDate(String date) {
        String[] parts = date.split("-");
        String month = parts[0];
        String day = parts[1];
        String year = parts[2];
        // Assuming month is in full text format, convert it to numeric value
        int monthValue = MONTH_MAP.get(month);
        return year + "-" + String.format("%02d", monthValue) + "-" + day;
    }

    // Calculate updated leave balance after deduction
    private int getUpdatedLeaveBalance(int id, String leaveType, int totalDays) throws IOException {
        LeaveBalance leaveBalance = leaveBalanceDAO.getLeaveBalanceByEmployeeId(id);
        if (leaveBalance != null) {
            switch (leaveType) {
                case "Sick Leave":
                    return leaveBalance.getSickLeave() - totalDays;
                case "Vacation Leave":
                    return leaveBalance.getVacationLeave() - totalDays;
                case "Emergency Leave":
                    return leaveBalance.getEmergencyLeave() - totalDays;
                default:
                    throw new IOException("Invalid leave type.");
            }
        } else {
            throw new IOException("Employee not found with ID: " + id);
        }
    }

    // Calculate total days method
    public static int calculateTotalDays(String startYear, String startMonth, String startDay, String endYear, String endMonth, String endDay) {
        try {
            int startYearInt = Integer.parseInt(startYear);
            int startMonthInt = MONTH_MAP.get(startMonth);
            int startDayInt = Integer.parseInt(startDay);
            
            int endYearInt = Integer.parseInt(endYear);
            int endMonthInt = MONTH_MAP.get(endMonth);
            int endDayInt = Integer.parseInt(endDay);

            LocalDate startDate = LocalDate.of(startYearInt, startMonthInt, startDayInt);
            LocalDate endDate = LocalDate.of(endYearInt, endMonthInt, endDayInt);

            // Calculate the difference between start and end dates inclusively
            long daysDifference = ChronoUnit.DAYS.between(startDate, endDate) + 1;

            // Ensure the result is non-negative
            return (int) Math.max(daysDifference, 0);
        } catch (NumberFormatException e) {
            // Handle parsing errors
            e.printStackTrace();
            return 0;
        }
    }

    // Check if requested days exceed leave balance
    private boolean checkLeaveBalance(int id, String leaveType, long totalDays) {
        LeaveBalance leaveBalance = leaveBalanceDAO.getLeaveBalanceByEmployeeId(id);
        if (leaveBalance != null) {
            switch (leaveType) {
                case "Sick Leave":
                    return leaveBalance.getSickLeave() >= totalDays;
                case "Vacation Leave":
                    return leaveBalance.getVacationLeave() >= totalDays;
                case "Emergency Leave":
                    return leaveBalance.getEmergencyLeave() >= totalDays;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid leave type.", "Leave Request Error", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Employee not found with ID: " + id, "Leave Request Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
