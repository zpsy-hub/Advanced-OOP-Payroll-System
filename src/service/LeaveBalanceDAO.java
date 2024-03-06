package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
