package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LeaveRequestLog;

public class LeaveRequestLogDAO {
    private Connection connection;
    private static LeaveRequestLogDAO instance;

    public LeaveRequestLogDAO() {
        // Initialize the connection (you'll need to implement this)
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static LeaveRequestLogDAO getInstance() {
        if (instance == null) {
            instance = new LeaveRequestLogDAO();
        }
        return instance;
    }

    // Method to retrieve leave request logs by employee ID
    public List<LeaveRequestLog> getLeaveLogsByEmployeeId(int id) {
        List<LeaveRequestLog> leaveLogs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.leave_request_log WHERE emp_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LeaveRequestLog leaveLog = new LeaveRequestLog(
                    resultSet.getDate("timestamp"),
                    resultSet.getInt("emp_id"),
                    resultSet.getString("employee_lastname"),
                    resultSet.getString("employee_firstname"),
                    resultSet.getString("leave_type"),
                    resultSet.getDate("date_start"),
                    resultSet.getDate("date_end"),
                    resultSet.getInt("days_total"),
                    resultSet.getInt("leave_balance"),
                    resultSet.getString("status")
                );
                leaveLogs.add(leaveLog);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveLogs;
    }

    // Method to add a new leave request log
    public void addLeaveLog(LeaveRequestLog leaveLog) {
        try {
            String sql = "INSERT INTO payroll_system.leave_request_log (timestamp, emp_id, employee_lastname, employee_firstname, leave_type, date_start, date_end, days_total, leave_balance, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, leaveLog.getTimestamp());
            statement.setInt(2, leaveLog.getId());
            statement.setString(3, leaveLog.getEmployeeLastName());
            statement.setString(4, leaveLog.getEmployeeFirstName());
            statement.setString(5, leaveLog.getLeaveType());
            statement.setDate(6, leaveLog.getDateStart());
            statement.setDate(7, leaveLog.getDateEnd());
            statement.setInt(8, leaveLog.getDaysTotal());
            statement.setInt(9, leaveLog.getLeaveBalance());
            statement.setString(10, leaveLog.getStatus());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing leave request log
    public void updateLeaveLog(LeaveRequestLog leaveLog) {
        try {
            String sql = "UPDATE payroll_system.leave_request_log SET timestamp = ?, employee_lastname = ?, employee_firstname = ?, leave_type = ?, date_start = ?, date_end = ?, days_total = ?, leave_balance = ?, status = ? WHERE emp_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, leaveLog.getTimestamp());
            statement.setString(2, leaveLog.getEmployeeLastName());
            statement.setString(3, leaveLog.getEmployeeFirstName());
            statement.setString(4, leaveLog.getLeaveType());
            statement.setDate(5, leaveLog.getDateStart());
            statement.setDate(6, leaveLog.getDateEnd());
            statement.setInt(7, leaveLog.getDaysTotal());
            statement.setInt(8, leaveLog.getLeaveBalance());
            statement.setString(9, leaveLog.getStatus());
            statement.setInt(10, leaveLog.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Test retrieving leave request logs for employee ID 1
        int id = 1;
        List<LeaveRequestLog> leaveLogs = getInstance().getLeaveLogsByEmployeeId(id);
        if (!leaveLogs.isEmpty()) {
            System.out.println("Leave request logs for employee ID " + id + ":");
            for (LeaveRequestLog leaveLog : leaveLogs) {
                System.out.println(leaveLog);
            }
        } else {
            System.out.println("No leave request logs found for employee ID " + id);
        }
    }
}
