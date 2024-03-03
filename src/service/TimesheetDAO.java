package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.UserRepository;

public class TimesheetDAO {
    private static TimesheetDAO instance = null;
    private Connection conn = null;
    TimesheetDAO() {
        conn = SQL_client.getInstance().getConnection();
    }

    public static TimesheetDAO getInstance() {
        if (instance == null) {
            instance = new TimesheetDAO();
        }
        return instance;
    }

    // Method to insert a new timesheet record into the database
    public void insertTimesheetRecord(String recordId, int empId, String empLastName, String empFirstName,
            String date, String timeIn, String timeOut) {
        try {
            String sql = "INSERT INTO payroll_system.attendance (record_id, emp_id, employee_lastname, employee_firstname, date, time_in, time_out) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, recordId);
            statement.setInt(2, empId);
            statement.setString(3, empLastName);
            statement.setString(4, empFirstName);
            statement.setString(5, date);
            statement.setString(6, timeIn);
            statement.setString(7, timeOut);
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
            String sql = "SELECT * FROM payroll_system.attendance WHERE emp_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String recordId = resultSet.getString("record_id");
                String lastName = resultSet.getString("employee_lastname");
                String firstName = resultSet.getString("employee_firstname");
                String date = resultSet.getString("date");
                String timeIn = resultSet.getString("time_in");
                String timeOut = resultSet.getString("time_out");
                String[] record = {recordId, lastName, firstName, date, timeIn, timeOut};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    
    public List<String[]> getLoggedInEmployeeTimesheetRecords(int empId) {
        List<String[]> records = new ArrayList<>();
        try {
            String sql = "SELECT date, time_in, time_out FROM payroll_system.attendance WHERE emp_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getString("date");
                String timeIn = resultSet.getString("time_in");
                String timeOut = resultSet.getString("time_out");
                String[] record = {date, timeIn, timeOut};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    
    public static void main(String[] args) {
        // Assuming you have instantiated your TimesheetDAO
        TimesheetDAO dao = TimesheetDAO.getInstance();
        
        // Get timesheet records for empId = 1
        List<String[]> records = dao.getLoggedInEmployeeTimesheetRecords(1);
        
        // Print the retrieved records
        System.out.println("Timesheet Records for empId = 1:");
        for (String[] record : records) {
            String date = record[0];
            String timeIn = record[1];
            String timeOut = record[2];
            System.out.println("Date: " + date + ", Time In: " + timeIn + ", Time Out: " + timeOut);
        }
    }
}
