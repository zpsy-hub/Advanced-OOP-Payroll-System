package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Leave;
import model.LeaveType;
import service.SQL_client;

public class LeaveDAO {
    private Connection connection;
    private static LeaveDAO instance;

    public LeaveDAO() {
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static LeaveDAO getInstance() {
        if (instance == null) {
            instance = new LeaveDAO();
        }
        return instance;
    }

    // Retrieve leave record by employee ID, leave type, and year
    public Leave getLeaveByEmployeeIdAndYear(int empId, int leaveTypeId, int year) {
        Leave leave = null;
        String sql = "SELECT * FROM payrollsystem_db.emp_leaves WHERE emp_id = ? AND leave_type_id = ? AND year = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            statement.setInt(2, leaveTypeId);
            statement.setInt(3, year);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    leave = mapResultSetToLeave(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leave;
    }

    // Retrieve all leave records for an employee
    public List<Leave> getAllLeavesByEmployeeId(int empId) {
        List<Leave> leaves = new ArrayList<>();
        String sql = "SELECT * FROM payrollsystem_db.emp_leaves WHERE emp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = mapResultSetToLeave(resultSet);
                    leaves.add(leave);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leaves;
    }

    // Retrieve all leave types
    public List<LeaveType> getAllLeaveTypes() {
        List<LeaveType> leaveTypes = new ArrayList<>();
        String sql = "SELECT * FROM payrollsystem_db.leave_types";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LeaveType leaveType = new LeaveType(
                        resultSet.getInt("leave_type_id"),
                        resultSet.getString("leave_type_name"),
                        resultSet.getInt("days_allotted")
                    );
                    leaveTypes.add(leaveType);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leaveTypes;
    }

    // Retrieve leave balance for an employee
    public int getLeaveBalance(int empId, int leaveTypeId) {
        int balance = 0;
        Leave leave = getLeaveByEmployeeIdAndYear(empId, leaveTypeId, Date.valueOf("2024-01-01").toLocalDate().getYear());
        if (leave != null) {
            balance = leave.getLeaveDaysRemaining();
        } else {
            LeaveType leaveType = getLeaveTypeById(leaveTypeId);
            if (leaveType != null) {
                balance = leaveType.getDaysAllotted();
            }
        }
        return balance;
    }

    // Retrieve leave type by ID
    public LeaveType getLeaveTypeById(int leaveTypeId) {
        LeaveType leaveType = null;
        String sql = "SELECT * FROM payrollsystem_db.leave_types WHERE leave_type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, leaveTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    leaveType = new LeaveType(
                        resultSet.getInt("leave_type_id"),
                        resultSet.getString("leave_type_name"),
                        resultSet.getInt("days_allotted")
                    );
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leaveType;
    }

    // Add a new leave record
    public void addLeave(Leave leave) {
        String sql = "INSERT INTO payrollsystem_db.emp_leaves (emp_id, leave_type_id, year, date_submitted, start_date, end_date, days_taken, date_approved, leave_days_remaining) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, leave.getEmpId());
            statement.setInt(2, leave.getLeaveTypeId());
            statement.setInt(3, leave.getYear());
            statement.setDate(4, leave.getDateSubmitted());
            statement.setDate(5, leave.getStartDate());
            statement.setDate(6, leave.getEndDate());
            statement.setInt(7, leave.getDaysTaken());
            statement.setDate(8, leave.getDateApproved());
            statement.setInt(9, leave.getLeaveDaysRemaining());
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Update an existing leave record
    public void updateLeave(Leave leave) {
        String sql = "UPDATE payrollsystem_db.emp_leaves SET date_submitted = ?, start_date = ?, end_date = ?, days_taken = ?, date_approved = ?, leave_days_remaining = ? WHERE emp_id = ? AND leave_type_id = ? AND year = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, leave.getDateSubmitted());
            statement.setDate(2, leave.getStartDate());
            statement.setDate(3, leave.getEndDate());
            statement.setInt(4, leave.getDaysTaken());
            statement.setDate(5, leave.getDateApproved());
            statement.setInt(6, leave.getLeaveDaysRemaining());
            statement.setInt(7, leave.getEmpId());
            statement.setInt(8, leave.getLeaveTypeId());
            statement.setInt(9, leave.getYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Delete a leave record
    public void deleteLeave(int empId, int leaveTypeId, int year) {
        String sql = "DELETE FROM payrollsystem_db.emp_leaves WHERE emp_id = ? AND leave_type_id = ? AND year = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            statement.setInt(2, leaveTypeId);
            statement.setInt(3, year);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Retrieve leave logs for an employee
    public List<Leave> getLeaveLogsByEmployeeId(int empId) {
        List<Leave> leaveLogs = new ArrayList<>();
        String sql = "SELECT * FROM payrollsystem_db.emp_leaves WHERE emp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = mapResultSetToLeave(resultSet);
                    leaveLogs.add(leave);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return leaveLogs;
    }

    // Helper method to map ResultSet to Leave object
    private Leave mapResultSetToLeave(ResultSet resultSet) throws SQLException {
        return new Leave(
            resultSet.getInt("emp_id"),
            resultSet.getInt("leave_type_id"),
            resultSet.getInt("year"),
            resultSet.getDate("date_submitted"),
            resultSet.getDate("start_date"),
            resultSet.getDate("end_date"),
            resultSet.getInt("days_taken"),
            resultSet.getDate("date_approved"),
            resultSet.getInt("leave_days_remaining")
        );
    }

    // Handle SQL exceptions
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }

    // Update leave balance after leave is taken
    public void updateLeaveBalance(int empId, int leaveTypeId, int year, int daysTaken) {
        Leave leave = getLeaveByEmployeeIdAndYear(empId, leaveTypeId, year);
        if (leave != null) {
            int updatedBalance = leave.getLeaveDaysRemaining() - daysTaken;
            leave.setLeaveDaysRemaining(updatedBalance);
            updateLeave(leave);
        }
    }
}
