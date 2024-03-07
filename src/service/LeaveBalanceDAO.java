package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LeaveBalance;

public class LeaveBalanceDAO {
    private Connection connection;
    private static LeaveBalanceDAO instance;

    public LeaveBalanceDAO() {
        // Initialize the connection (you'll need to implement this)
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static LeaveBalanceDAO getInstance() {
        if (instance == null) {
            instance = new LeaveBalanceDAO();
        }
        return instance;
    }

    // Retrieve leave balance by employee ID
    public LeaveBalance getLeaveBalanceByEmployeeId(int empId) {
        LeaveBalance leaveBalance = null;
        String sql = "SELECT * FROM payroll_system.leave_balance WHERE emp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    leaveBalance = mapResultSetToLeaveBalance(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leaveBalance;
    }
    
    // Retrieve all employees' leave records
    public List<LeaveBalance> getAllEmployeesLeaveRecords() {
        List<LeaveBalance> leaveRecords = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.leave_balance";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LeaveBalance leaveBalance = mapResultSetToLeaveBalance(resultSet);
                leaveRecords.add(leaveBalance);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leaveRecords;
    }

    // Update leave balance for an employee
    public void updateLeaveBalance(LeaveBalance leaveBalance) {
        String sql = "UPDATE payroll_system.leave_balance SET employee_lastname = ?, employee_firstname = ?, "
                + "sick_leave = ?, emergency_leave = ?, vacation_leave = ? WHERE emp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, leaveBalance.getEmployeeLastName());
            statement.setString(2, leaveBalance.getEmployeeFirstName());
            statement.setInt(3, leaveBalance.getSickLeave());
            statement.setInt(4, leaveBalance.getEmergencyLeave());
            statement.setInt(5, leaveBalance.getVacationLeave());
            statement.setInt(6, leaveBalance.getEmpId());
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
    //update Lave balance after approval of leave request
    public void updateNewLeaveBalance(int empId) {
        try {
            // Retrieve the leave type and new leave balance from the leave request log
            String sql = "SELECT leave_type, leave_balance FROM payroll_system.leave_request_log WHERE emp_id = ? AND status = 'Approved'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String leaveType = resultSet.getString("leave_type");
                int newLeaveBalance = resultSet.getInt("leave_balance");

                // Update the corresponding leave balance column in the leave_balance table
                String updateSql;
                switch (leaveType) {
                    case "Sick Leave":
                        updateSql = "UPDATE payroll_system.leave_balance SET sick_leave = ? WHERE emp_id = ?";
                        break;
                    case "Emergency Leave":
                        updateSql = "UPDATE payroll_system.leave_balance SET emergency_leave = ? WHERE emp_id = ?";
                        break;
                    case "Vacation Leave":
                        updateSql = "UPDATE payroll_system.leave_balance SET vacation_leave = ? WHERE emp_id = ?";
                        break;
                    default:
                        // Handle unknown leave types or error cases
                        System.err.println("Unknown leave type: " + leaveType);
                        return;
                }

                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, newLeaveBalance);
                updateStatement.setInt(2, empId);
                updateStatement.executeUpdate();
                updateStatement.close();
            } else {
                System.err.println("No approved leave request found for empId: " + empId);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Helper method to map ResultSet to LeaveBalance object
    private LeaveBalance mapResultSetToLeaveBalance(ResultSet resultSet) throws SQLException {
        return new LeaveBalance(
            resultSet.getInt("emp_id"),
            resultSet.getString("employee_lastname"),
            resultSet.getString("employee_firstname"),
            resultSet.getInt("sick_leave"),
            resultSet.getInt("emergency_leave"),
            resultSet.getInt("vacation_leave")
        );
    }

    // Handle SQL exceptions
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
           }
}
