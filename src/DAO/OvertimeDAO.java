package DAO;

import model.Overtime;
import model.OvertimeType;
import service.SQL_client;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OvertimeDAO {
    private Connection connection;
    private static OvertimeDAO instance;

    private OvertimeDAO() {
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static OvertimeDAO getInstance() {
        if (instance == null) {
            instance = new OvertimeDAO();
        }
        return instance;
    }

    public List<Overtime> getOvertimeByEmployeeId(int empid) {
        List<Overtime> overtimes = new ArrayList<>();
        try {
            String sql = "SELECT o.*, ot.overtime_type_name " +
                         "FROM payrollsystem_db.overtime o " +
                         "JOIN payrollsystem_db.overtime_type ot ON o.overtime_type_id = ot.overtime_type_id " +
                         "WHERE o.emp_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, empid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime(
                    resultSet.getInt("overtime_id"),
                    resultSet.getInt("emp_id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("status"),
                    resultSet.getTime("start_time").toLocalTime(),
                    resultSet.getTime("end_time").toLocalTime(),
                    resultSet.getDouble("total_hours"),
                    resultSet.getString("reason"),
                    resultSet.getTimestamp("approval_date") != null ? resultSet.getTimestamp("approval_date").toLocalDateTime().toLocalDate() : null,
                    resultSet.getInt("overtime_type_id"),
                    resultSet.getString("overtime_type_name"),
                    resultSet.getString("employeeName")
                );
                overtimes.add(overtime);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overtimes;
    }

    public void addOvertime(Overtime overtime) {
        try {
            String sql = "INSERT INTO payrollsystem_db.overtime (emp_id, overtime_type_id, date, start_time, end_time, reason) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtime.getEmpId());
            statement.setInt(2, overtime.getOvertimeTypeId());
            statement.setDate(3, Date.valueOf(overtime.getDate()));
            statement.setTime(4, Time.valueOf(overtime.getStartTime()));
            statement.setTime(5, Time.valueOf(overtime.getEndTime()));
            statement.setString(6, overtime.getReason());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOvertime(int overtimeid) {
        try {
            String sql = "DELETE FROM payrollsystem_db.overtime WHERE overtime_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtimeid);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Overtime> getAllOvertimes() {
        List<Overtime> overtimes = new ArrayList<>();
        try {
            String sql = "SELECT o.*, ot.overtime_type_name " +
                         "FROM payrollsystem_db.overtime o " +
                         "JOIN payrollsystem_db.overtime_type ot ON o.overtime_type_id = ot.overtime_type_id";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime(
                        resultSet.getInt("overtime_id"),
                        resultSet.getInt("emp_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("status"),
                        resultSet.getTime("start_time").toLocalTime(),
                        resultSet.getTime("end_time").toLocalTime(),
                        resultSet.getDouble("total_hours"),
                        resultSet.getString("reason"),
                        resultSet.getTimestamp("approval_date") != null ? resultSet.getTimestamp("approval_date").toLocalDateTime().toLocalDate() : null,
                        resultSet.getInt("overtime_type_id"),
                        resultSet.getString("overtime_type_name")
                );
                overtimes.add(overtime);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overtimes;
    }

    public List<Overtime> getPendingOvertimeRequests() {
        List<Overtime> pendingOvertimes = new ArrayList<>();
        String query = "SELECT o.*, CONCAT(e.last_name, ', ', e.first_name) AS employeeName " +
                       "FROM payrollsystem_db.overtime o " +
                       "JOIN payrollsystem_db.employee e ON o.emp_id = e.emp_id " +
                       "WHERE o.status = 'Pending'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime();
                overtime.setOvertimeId(resultSet.getInt("overtime_id"));
                overtime.setEmpId(resultSet.getInt("emp_id"));
                overtime.setDate(resultSet.getDate("date").toLocalDate());
                overtime.setStartTime(resultSet.getTime("start_time").toLocalTime());
                overtime.setEndTime(resultSet.getTime("end_time").toLocalTime());
                overtime.setTotalHours(resultSet.getDouble("total_hours"));
                overtime.setReason(resultSet.getString("reason"));
                overtime.setStatus(resultSet.getString("status"));
                overtime.setDateApproved(resultSet.getTimestamp("approval_date") != null ? resultSet.getTimestamp("approval_date").toLocalDateTime().toLocalDate() : null);
                overtime.setEmployeeName(resultSet.getString("employeeName"));
                pendingOvertimes.add(overtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingOvertimes;
    }


    public List<Overtime> getProcessedOvertimeRequests() {
        List<Overtime> processedOvertimes = new ArrayList<>();
        String query = "SELECT o.*, CONCAT(e.last_name, ', ', e.first_name) AS employeeName " +
                       "FROM payrollsystem_db.overtime o " +
                       "JOIN payrollsystem_db.employee e ON o.emp_id = e.emp_id " +
                       "WHERE o.status IN ('Approved', 'Rejected')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime();
                overtime.setOvertimeId(resultSet.getInt("overtime_id"));
                overtime.setEmpId(resultSet.getInt("emp_id"));
                overtime.setDate(resultSet.getDate("date").toLocalDate());
                overtime.setStartTime(resultSet.getTime("start_time").toLocalTime());
                overtime.setEndTime(resultSet.getTime("end_time").toLocalTime());
                overtime.setTotalHours(resultSet.getDouble("total_hours"));
                overtime.setReason(resultSet.getString("reason"));
                overtime.setStatus(resultSet.getString("status"));
                overtime.setDateApproved(resultSet.getTimestamp("approval_date") != null ? resultSet.getTimestamp("approval_date").toLocalDateTime().toLocalDate() : null);
                overtime.setEmployeeName(resultSet.getString("employeeName"));
                processedOvertimes.add(overtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return processedOvertimes;
    }


    public void approveOvertime(int empId, LocalDate date, LocalDate dateApproved, int approverId) {
        try {
            String sql = "UPDATE payrollsystem_db.overtime SET status = 'Approved', approval_date = ?, approver_id = ? WHERE emp_id = ? AND date = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(2, approverId);
            statement.setInt(3, empId);
            statement.setDate(4, java.sql.Date.valueOf(date));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rejectOvertime(int empId, LocalDate date, LocalDate dateApproved, int approverId) {
        try {
            String sql = "UPDATE payrollsystem_db.overtime SET status = 'Rejected', approval_date = ?, approver_id = ? WHERE emp_id = ? AND date = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(2, approverId);
            statement.setInt(3, empId);
            statement.setDate(4, java.sql.Date.valueOf(date));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<OvertimeType> getOvertimeTypes() {
        List<OvertimeType> overtimeTypes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payrollsystem_db.overtime_type";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OvertimeType overtimeType = new OvertimeType(
                        resultSet.getInt("overtime_type_id"),
                        resultSet.getString("overtime_type_name"),
                        resultSet.getDouble("multiplier")
                );
                overtimeTypes.add(overtimeType);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overtimeTypes;
    }
}
