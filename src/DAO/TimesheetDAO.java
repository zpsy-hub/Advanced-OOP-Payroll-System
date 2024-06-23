package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import service.SQL_client;

public class TimesheetDAO {
    private static TimesheetDAO instance = null;
    private Connection conn = null;

    public TimesheetDAO() {
        conn = SQL_client.getInstance().getConnection();
    }

    public static TimesheetDAO getInstance() {
        if (instance == null) {
            instance = new TimesheetDAO();
        }
        return instance;
    }

    // Method to insert a new timesheet record into the database
    public void insertTimesheetRecord(int empId, LocalDate date, Time timeIn, Time timeOut) {
        try {
            String sql = "INSERT INTO payrollsystem_db.timesheet (emp_id, date, time_in, time_out) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3, timeIn);
            statement.setTime(4, timeOut);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all timesheet records for a given employee ID
    public List<String[]> getTimesheetRecords(int empId) {
        List<String[]> records = new ArrayList<>();
        try {
            String sql = "SELECT timesheet_id, emp_id, date, time_in, time_out, total_hours FROM payrollsystem_db.timesheet WHERE emp_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int timesheetId = resultSet.getInt("timesheet_id");
                Date date = resultSet.getDate("date");
                Time timeIn = resultSet.getTime("time_in");
                Time timeOut = resultSet.getTime("time_out");
                double totalHours = resultSet.getDouble("total_hours"); // Use double instead of BigDecimal
                String[] record = {String.valueOf(timesheetId), String.valueOf(empId), date.toString(), timeIn.toString(), timeOut.toString(), Double.toString(totalHours)};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Method to retrieve all timesheet records from the database
    public List<String[]> getAllTimesheetRecords() {
        List<String[]> records = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payrollsystem_db.timesheet";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int timesheetId = resultSet.getInt("timesheet_id");
                int empId = resultSet.getInt("emp_id");
                Date date = resultSet.getDate("date");
                Time timeIn = resultSet.getTime("time_in");
                Time timeOut = resultSet.getTime("time_out");
                double totalHours = resultSet.getDouble("total_hours"); // Use double instead of BigDecimal
                String[] record = {String.valueOf(timesheetId), String.valueOf(empId), date.toString(), timeIn.toString(), timeOut.toString(), Double.toString(totalHours)};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    
    // Method to get timesheet records for the logged-in employee
    public List<String[]> getLoggedInEmployeeTimesheetRecords(int empId) {
        List<String[]> records = new ArrayList<>();
        String sql = "SELECT date, time_in, time_out, total_hours FROM payrollsystem_db.timesheet WHERE emp_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getDate("date").toString();
                String timeIn = resultSet.getTime("time_in") != null ? resultSet.getTime("time_in").toString() : "";
                String timeOut = resultSet.getTime("time_out") != null ? resultSet.getTime("time_out").toString() : "";
                String totalHours = resultSet.getString("total_hours");
                records.add(new String[]{date, timeIn, timeOut, totalHours});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Method to check if there is a time in record for the logged-in employee on the current date
    public boolean hasTimeInRecordForToday(int empId) {
        String sql = "SELECT COUNT(*) FROM payrollsystem_db.timesheet WHERE emp_id = ? AND DATE(date) = CURDATE() AND time_in IS NOT NULL";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if there is a time out record for the logged-in employee on the current date
    public boolean hasTimeOutRecordForToday(int empId) {
        String sql = "SELECT COUNT(*) FROM payrollsystem_db.timesheet WHERE emp_id = ? AND DATE(date) = CURDATE() AND time_out IS NOT NULL";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // Method to record time in for an employee
    public void recordTimeIn(int empId, String date, String timeIn, String timeOut) {
        String sql = "INSERT INTO payrollsystem_db.timesheet (emp_id, date, time_in, time_out) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, empId);
            statement.setDate(2, Date.valueOf(date));  // Convert String date to SQL Date
            statement.setTime(3, Time.valueOf(timeIn)); // Convert String time to SQL Time
            statement.setTime(4, null); // Time out is initially null
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to record time out for an employee
    public void recordTimeOut(int empId) {
        String sql = "UPDATE payrollsystem_db.timesheet SET time_out = ? WHERE emp_id = ? AND date = CURDATE() AND time_out IS NULL";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setTime(1, new Time(System.currentTimeMillis()));  // Current system time as time out
            statement.setInt(2, empId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
