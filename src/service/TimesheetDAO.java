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
    
 // Method to record time in for a specific employee
    public void recordTimeIn(int empId, String empLastName, String empFirstName) {
        String recordId = getFormattedRecordId(empId); // Use the helper method to generate formatted record ID
        String timeIn = getCurrentTime();

        insertTimesheetRecord(recordId, empId, empLastName, empFirstName, getCurrentDate(), timeIn, null);
    }

    // Helper method to generate a formatted record ID based on employee ID and current date
    private String getFormattedRecordId(int empId) {
        // Combine employee ID and current date without dashes
        String formattedDate = getCurrentDate().replaceAll("-", "");
        return empId + "-" + formattedDate;
    }


    // Method to record time out for a specific employee
    public void recordTimeOut(int empId) {
        try {
            String timeOut = getCurrentTime();

            // Retrieve the last time in record for the employee and update its time_out field
            String sql = "UPDATE payroll_system.attendance SET time_out = ? WHERE emp_id = ? AND date = ? AND time_out IS NULL";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, timeOut);
            statement.setInt(2, empId);
            statement.setString(3, getCurrentDate());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the current date in the format yyyy-MM-dd
    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }

    // Helper method to get the current time in the format HH:mm:ss
    private String getCurrentTime() {
        return java.time.LocalTime.now().toString();
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
    
    public List<String> getUniqueMonthYearCombinations() {
        List<String> monthYearList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT DATE_FORMAT(date, '%Y-%m') AS month_year FROM payroll_system.attendance";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String monthYear = resultSet.getString("month_year");
                monthYearList.add(monthYear);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthYearList;
    }
    
    public List<String[]> getFilteredTimesheetRecords(int empId, String selectedMonthYear) {
        List<String[]> records = new ArrayList<>();
        try {
            // Adjust the SQL query to filter records based on empId and selectedMonthYear
            String sql = "SELECT date, time_in, time_out FROM payroll_system.attendance WHERE emp_id = ? AND DATE_FORMAT(date, '%Y-%m') = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.setString(2, selectedMonthYear);
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
    
    // Method to check if there is a time in record for the logged-in employee on the current date
    public boolean hasTimeInRecordForToday(int empId) {
        boolean hasRecord = false;
        try {
            String sql = "SELECT COUNT(*) FROM payroll_system.attendance WHERE emp_id = ? AND DATE(date) = CURDATE() AND time_in IS NOT NULL";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    hasRecord = true;
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasRecord;
    }

    // Method to check if there is a time out record for the logged-in employee on the current date
    public boolean hasTimeOutRecordForToday(int empId) {
        boolean hasRecord = false;
        try {
            String sql = "SELECT COUNT(*) FROM payroll_system.attendance WHERE emp_id = ? AND DATE(date) = CURDATE() AND time_out IS NOT NULL";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    hasRecord = true;
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasRecord;
    }


    
    public static void main(String[] args) {
        // Assuming you have instantiated your TimesheetDAO
        TimesheetDAO dao = TimesheetDAO.getInstance();

        // Get unique month-year combinations
        List<String> monthYearCombinations = dao.getUniqueMonthYearCombinations();

        // Print the retrieved month-year combinations
        System.out.println("Unique Month-Year Combinations:");
        for (String monthYear : monthYearCombinations) {
            System.out.println(monthYear);
        }
    }

}
