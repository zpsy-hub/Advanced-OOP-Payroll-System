package service;

import java.io.IOException;
import java.util.List;
import model.Leave;
import util.LeaveData;

public class LeaveRequestService {

    // Submit a leave request
    public static void submitLeaveRequest(String id, String employeeName, String leaveType, String startDate, String endDate, String totalDays) {
        try {
            LeaveData.writeLeaveApplicationData(id, employeeName, leaveType, startDate, endDate, totalDays);
            // Deduct leave balance
            deductLeaveBalance(id, leaveType, Integer.parseInt(totalDays));
            System.out.println("Leave request submitted successfully.");
        } catch (IOException e) {
            System.err.println("Error submitting leave request: " + e.getMessage());
        }
    }

    // Deduct leave balance
    private static void deductLeaveBalance(String id, String leaveType, int days) {
        try {
            List<Leave> leaves = LeaveData.readLeaveCsv();
            for (Leave leave : leaves) {
                if (leave.getId().equals(id)) {
                    switch (leaveType) {
                        case "Sick Leave":
                            leave.setSickLeaveDays(leave.getSickLeaveDays() - days);
                            break;
                        case "Vacation Leave":
                            leave.setVacationLeaveDays(leave.getVacationLeaveDays() - days);
                            break;
                        case "Emergency Leave":
                            leave.setEmergencyLeaveDays(leave.getEmergencyLeaveDays() - days);
                            break;
                        default:
                            System.err.println("Invalid leave type.");
                            return;
                    }
                    // Update leave balance in CSV
                    LeaveData.updateLeaveCsv(leaves);
                    return;
                }
            }
            System.err.println("Employee not found with ID: " + id);
        } catch (IOException e) {
            System.err.println("Error deducting leave balance: " + e.getMessage());
        }
    }

    // Other methods for leave-related operations can be added here
}

