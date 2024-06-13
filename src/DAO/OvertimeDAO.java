package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.Overtime;
import service.SQL_client;

public class OvertimeDAO {
    private Connection connection;
    private static OvertimeDAO instance;

    public OvertimeDAO() {
        // Initialize the connection
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static OvertimeDAO getInstance() {
        if (instance == null) {
            instance = new OvertimeDAO();
        }
        return instance;
    }

    // Method to retrieve overtime records by employee ID
    public List<Overtime> getOvertimeByEmployeeId(int empId) {
        List<Overtime> overtimes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.overtime WHERE empid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime(
                    resultSet.getInt("overtimeid"),
                    resultSet.getInt("empid"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("status"),
                    resultSet.getTime("start").toLocalTime(),
                    resultSet.getTime("end").toLocalTime(),
                    resultSet.getDouble("total_hours"),
                    resultSet.getString("reason"),
                    resultSet.getDate("date_approved") != null ? resultSet.getDate("date_approved").toLocalDate() : null
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

    // Method to add a new overtime record
    public void addOvertime(Overtime overtime) {
        try {
            String sql = "INSERT INTO payroll_system.overtime (empid, date, status, start, end, total_hours, reason, date_approved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtime.getEmpId());
            statement.setDate(2, Date.valueOf(overtime.getDate()));
            statement.setString(3, overtime.getStatus());
            statement.setTime(4, Time.valueOf(overtime.getStart()));
            statement.setTime(5, Time.valueOf(overtime.getEnd()));
            statement.setDouble(6, overtime.getTotalHours());
            statement.setString(7, overtime.getReason());
            statement.setDate(8, overtime.getDateApproved() != null ? Date.valueOf(overtime.getDateApproved()) : null);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing overtime record
    public void updateOvertime(Overtime overtime) {
        try {
            String sql = "UPDATE payroll_system.overtime SET empid = ?, date = ?, status = ?, start = ?, end = ?, total_hours = ?, reason = ?, date_approved = ? WHERE overtimeid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtime.getEmpId());
            statement.setDate(2, Date.valueOf(overtime.getDate()));
            statement.setString(3, overtime.getStatus());
            statement.setTime(4, Time.valueOf(overtime.getStart()));
            statement.setTime(5, Time.valueOf(overtime.getEnd()));
            statement.setDouble(6, overtime.getTotalHours());
            statement.setString(7, overtime.getReason());
            statement.setDate(8, overtime.getDateApproved() != null ? Date.valueOf(overtime.getDateApproved()) : null);
            statement.setInt(9, overtime.getOvertimeId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an overtime record by its ID
    public void deleteOvertime(int overtimeId) {
        try {
            String sql = "DELETE FROM payroll_system.overtime WHERE overtimeid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtimeId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all overtime records
    public List<Overtime> getAllOvertimes() {
        List<Overtime> overtimes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.overtime";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime(
                    resultSet.getInt("overtimeid"),
                    resultSet.getInt("empid"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("status"),
                    resultSet.getTime("start").toLocalTime(),
                    resultSet.getTime("end").toLocalTime(),
                    resultSet.getDouble("total_hours"),
                    resultSet.getString("reason"),
                    resultSet.getDate("date_approved") != null ? resultSet.getDate("date_approved").toLocalDate() : null
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

    // Method to approve an overtime record
    public void approveOvertime(int overtimeId, LocalDate dateApproved) {
        try {
            String sql = "UPDATE payroll_system.overtime SET status = 'Approved', date_approved = ? WHERE overtimeid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(dateApproved));
            statement.setInt(2, overtimeId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
