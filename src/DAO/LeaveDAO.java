package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Leave;
import model.LeaveBalance;
import model.LeaveDetails;
import model.LeaveType;
import service.SQL_client;

public class LeaveDAO {
    private Connection connection;
    private static LeaveDAO instance;

    private LeaveDAO() {
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static synchronized LeaveDAO getInstance() {
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
        Leave leave = getLeaveByEmployeeIdAndYear(empId, leaveTypeId, LocalDate.now().getYear());
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

    // Add a new leave record including status
    public void addLeave(Leave leave) {
        String sql = "INSERT INTO payrollsystem_db.emp_leaves (emp_id, leave_type_id, year, date_submitted, start_date, end_date, days_taken, status, date_approved, leave_days_remaining) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, leave.getEmpId());
            statement.setInt(2, leave.getLeaveTypeId());
            statement.setInt(3, leave.getYear());
            statement.setDate(4, leave.getDateSubmitted());
            statement.setDate(5, leave.getStartDate());
            statement.setDate(6, leave.getEndDate());
            statement.setInt(7, leave.getDaysTaken());
            statement.setString(8, leave.getStatus());
            statement.setDate(9, leave.getDateApproved());
            statement.setInt(10, leave.getLeaveDaysRemaining());
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Update leave status and approval date
    public void updateLeaveStatusAndDate(int empId, int leaveTypeId, int year, String status, Date dateApproved) {
        String sql = "UPDATE payrollsystem_db.emp_leaves SET status = ?, date_approved = ? WHERE emp_id = ? AND leave_type_id = ? AND year = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setDate(2, dateApproved);
            statement.setInt(3, empId);
            statement.setInt(4, leaveTypeId);
            statement.setInt(5, year);
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

    // Map a ResultSet to a Leave object, including status
    private Leave mapResultSetToLeave(ResultSet resultSet) throws SQLException {
        return new Leave(
            resultSet.getInt("emp_id"),
            resultSet.getInt("leave_type_id"),
            resultSet.getInt("year"),
            resultSet.getDate("date_submitted"),
            resultSet.getDate("start_date"),
            resultSet.getDate("end_date"),
            resultSet.getInt("days_taken"),
            resultSet.getString("status"),
            resultSet.getDate("date_approved"),
            resultSet.getInt("leave_days_remaining")
        );
    }

    // Method to get Leave Type ID from Name
    public int getLeaveTypeIdFromName(String leaveTypeName) {
        String sql = "SELECT leave_type_id FROM payrollsystem_db.leave_types WHERE leave_type_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, leaveTypeName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("leave_type_id");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return -1;  // Return -1 or throw an exception if the type is not found
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Handle exceptions appropriately
    }
    
    // Retrieve leave history details
    public List<LeaveDetails> getLeaveHistory() {
        List<LeaveDetails> leaveHistory = new ArrayList<>();
        String sql = "SELECT l.*, e.first_name, e.last_name, lt.leave_type_name FROM payrollsystem_db.emp_leaves l JOIN payrollsystem_db.employee e ON l.emp_id = e.emp_id JOIN payrollsystem_db.leave_types lt ON l.leave_type_id = lt.leave_type_id";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Leave leave = new Leave(
                    resultSet.getInt("emp_id"),
                    resultSet.getInt("leave_type_id"),
                    resultSet.getInt("year"),
                    resultSet.getDate("date_submitted"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getInt("days_taken"),
                    resultSet.getString("status"),
                    resultSet.getDate("date_approved"),
                    resultSet.getInt("leave_days_remaining")
                );
                LeaveDetails details = new LeaveDetails(
                    leave,
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getString("leave_type_name")
                );
                leaveHistory.add(details);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions appropriately
        }
        return leaveHistory;
    }

    // Retrieve leave balances for all employees from the view
    public List<LeaveBalance> getAllEmployeeLeaveBalances() {
        List<LeaveBalance> balances = new ArrayList<>();
        String sql = "SELECT * FROM payrollsystem_db.emp_leave_balance_view";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                balances.add(mapResultSetToLeaveBalance(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return balances;
    }

    // Retrieve leave balance by employee ID from the view
    public LeaveBalance getLeaveBalanceByEmployeeId(int empId) {
        LeaveBalance leaveBalance = null;
        String sql = "SELECT * FROM payrollsystem_db.emp_leave_balance_view WHERE emp_id = ?";
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

    private LeaveBalance mapResultSetToLeaveBalance(ResultSet resultSet) throws SQLException {
        return new LeaveBalance(
            resultSet.getInt("emp_id"),
            resultSet.getString("last_name"),
            resultSet.getString("first_name"),
            resultSet.getInt("sick_leave"),
            resultSet.getInt("emergency_leave"),
            resultSet.getInt("vacation_leave")
        );
    }

    // Update leave days remaining when leave is approved
    public void updateLeaveDaysRemaining(int empId, int leaveTypeId, int year, int leaveDaysRemaining) {
        String sql = "UPDATE payrollsystem_db.emp_leaves SET leave_days_remaining = ? WHERE emp_id = ? AND leave_type_id = ? AND year = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, leaveDaysRemaining);
            statement.setInt(2, empId);
            statement.setInt(3, leaveTypeId);
            statement.setInt(4, year);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
 // Retrieve leave balance for a specific leave type of a specific employee
    public int getLeaveBalanceByEmployeeIdAndType(int empId, int leaveTypeId) {
        String leaveTypeColumn = "";
        switch (leaveTypeId) {
            case 1:
                leaveTypeColumn = "vacation_leave";
                break;
            case 2:
                leaveTypeColumn = "emergency_leave";
                break;
            case 3:
                leaveTypeColumn = "sick_leave";
                break;
            default:
                throw new IllegalArgumentException("Invalid leave type ID");
        }

        String sql = "SELECT " + leaveTypeColumn + " FROM payrollsystem_db.emp_leave_balance_view WHERE emp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(leaveTypeColumn);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return 0;  // Default to 0 if not found
    }
}
