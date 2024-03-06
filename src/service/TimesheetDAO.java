package service;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.UserRepository;

public class TimesheetDAO {
	private LocalDate payPeriodStartDate;
    private LocalDate payPeriodEndDate;
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
    
    //Gets an employee's records from loggeedinuser
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
    
    //Returns unique Monthyear from records
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
    
    //Records filtered by MonthYear
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
    
 // Method to retrieve all timesheet records from the database
    public List<String[]> getAllTimesheetRecords() {
        List<String[]> records = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.attendance";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String recordId = resultSet.getString("record_id");
                int empId = resultSet.getInt("emp_id");
                String lastName = resultSet.getString("employee_lastname");
                String firstName = resultSet.getString("employee_firstname");
                String date = resultSet.getString("date");
                String timeIn = resultSet.getString("time_in");
                String timeOut = resultSet.getString("time_out");
                String[] record = {recordId, String.valueOf(empId), lastName, firstName, date, timeIn, timeOut};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Method to retrieve timesheet records filtered by month-year
    public List<String[]> getFilteredTimesheetRecords(String selectedMonthYear) {
        List<String[]> records = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.attendance WHERE DATE_FORMAT(date, '%Y-%m') = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, selectedMonthYear);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String recordId = resultSet.getString("record_id");
                int empId = resultSet.getInt("emp_id");
                String lastName = resultSet.getString("employee_lastname");
                String firstName = resultSet.getString("employee_firstname");
                String date = resultSet.getString("date");
                String timeIn = resultSet.getString("time_in");
                String timeOut = resultSet.getString("time_out");
                String[] record = {recordId, String.valueOf(empId), lastName, firstName, date, timeIn, timeOut};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    
    // Method to retrieve timesheet records for a given month-year
    public List<String[]> getTimesheetRecordsForMonthYear(String monthYear) {
        List<String[]> records = new ArrayList<>();
        try {
            // Extract year and month from the given parameter
            String[] yearMonth = monthYear.split("-");
            String year = yearMonth[0];
            String month = yearMonth[1];
            
            // Construct the SQL query to filter records based on year and month
            String sql = "SELECT * FROM payroll_system.attendance WHERE YEAR(date) = ? AND MONTH(date) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, year);
            statement.setString(2, month);
            ResultSet resultSet = statement.executeQuery();
            
            // Retrieve records and add them to the list
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
    
    // Method to calculate the pay period start date based on the given month and year
    public LocalDate calculatePayPeriodStartDate(String monthYear) {
        // Retrieve the first recorded date from the timesheet records for the given month and year
        List<String[]> timesheetRecords = getTimesheetRecordsForMonthYear(monthYear);
        if (!timesheetRecords.isEmpty()) {
            // Sort the records based on date to get the earliest record
            Collections.sort(timesheetRecords, Comparator.comparing(record -> LocalDate.parse(record[3])));
            String firstRecordDate = timesheetRecords.get(0)[3]; // Assuming date is at index 3
            return LocalDate.parse(firstRecordDate);
        } else {
            // Default to the 1st day of the month if no records are found
            return LocalDate.parse(monthYear + "-01");
        }
    }

    // Method to calculate the pay period end date based on the given month and year
    public LocalDate calculatePayPeriodEndDate(String monthYear) {
        // Retrieve the last recorded date from the timesheet records for the given month and year
        List<String[]> timesheetRecords = getTimesheetRecordsForMonthYear(monthYear);
        if (!timesheetRecords.isEmpty()) {
            // Sort the records based on date to get the latest record
            Collections.sort(timesheetRecords, Comparator.comparing(record -> LocalDate.parse(record[3])));
            String lastRecordDate = timesheetRecords.get(timesheetRecords.size() - 1)[3]; // Assuming date is at index 3
            return LocalDate.parse(lastRecordDate);
        } else {
            // Default to the last day of the month if no records are found
            return LocalDate.parse(monthYear + "-01").withDayOfMonth(
                    YearMonth.parse(monthYear, DateTimeFormatter.ofPattern("yyyy-MM")).lengthOfMonth());
        }
    }



    // Getter method for pay period start date
    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }

    // Setter method for pay period start date
    public void setPayPeriodStartDate(LocalDate startDate) {
        this.payPeriodStartDate = startDate;
    }

    // Getter method for pay period end date
    public LocalDate getPayPeriodEndDate() {
        return payPeriodEndDate;
    }

    // Setter method for pay period end date
    public void setPayPeriodEndDate(LocalDate endDate) {
        this.payPeriodEndDate = endDate;
    }


}
