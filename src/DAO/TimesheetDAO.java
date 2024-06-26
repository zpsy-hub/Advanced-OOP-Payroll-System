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
    public void insertTimesheetRecord(int empId, LocalDate date, Time timeIn, Time timeOut, int payPeriodId) {
        try {
            String sql = "INSERT INTO payrollsystem_db.timesheet (emp_id, date, time_in, time_out, pay_period_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3, timeIn);
            statement.setTime(4, timeOut);
            statement.setInt(5, payPeriodId);
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
            String sql = "SELECT timesheet_id, emp_id, date, time_in, time_out, total_hours, pay_period_id FROM payrollsystem_db.timesheet WHERE emp_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int timesheetId = resultSet.getInt("timesheet_id");
                Date date = resultSet.getDate("date");
                Time timeIn = resultSet.getTime("time_in");
                Time timeOut = resultSet.getTime("time_out");
                double totalHours = resultSet.getDouble("total_hours");
                int payPeriodId = resultSet.getInt("pay_period_id");
                String[] record = {String.valueOf(timesheetId), String.valueOf(empId), date.toString(), timeIn.toString(), timeOut.toString(), Double.toString(totalHours), String.valueOf(payPeriodId)};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Method to get all timesheet records
    public List<String[]> getAllTimesheetRecords() {
        List<String[]> records = new ArrayList<>();
        try {
            String sql = "SELECT t.timesheet_id, t.emp_id, CONCAT(e.last_name, ', ', e.first_name) as employee_name, t.date, t.time_in, t.time_out, t.total_hours " +
                         "FROM payrollsystem_db.timesheet t " +
                         "JOIN payrollsystem_db.employee e ON t.emp_id = e.emp_id";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int timesheetId = resultSet.getInt("timesheet_id");
                int empId = resultSet.getInt("emp_id");
                String employeeName = resultSet.getString("employee_name");
                Date date = resultSet.getDate("date");
                Time timeIn = resultSet.getTime("time_in");
                Time timeOut = resultSet.getTime("time_out");
                double totalHours = resultSet.getDouble("total_hours");
                String[] record = {String.valueOf(timesheetId), String.valueOf(empId), employeeName, date.toString(), timeIn.toString(), timeOut.toString(), Double.toString(totalHours)};
                records.add(record);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Method to get timesheet records for a specific payroll period
    public List<String[]> getTimesheetRecordsByPayrollPeriod(String payrollPeriod) {
        List<String[]> records = new ArrayList<>();
        try {
            if ("All Payroll Periods".equals(payrollPeriod)) {
                // Return all records if "All Payroll Periods" is selected
                return getAllTimesheetRecords();
            }

            String[] dates = payrollPeriod.split(" to ");
            if (dates.length != 2) {
                throw new IllegalArgumentException("Invalid payroll period format. Expected format: 'YYYY-MM-DD to YYYY-MM-DD'");
            }

            String payPeriodStart = dates[0];
            String payPeriodEnd = dates[1];

            // Retrieve payroll_period_id from payroll_periods table
            String queryPeriodId = "SELECT payroll_period_id FROM payrollsystem_db.payroll_periods WHERE pay_period_start = ? AND pay_period_end = ?";
            try (PreparedStatement statementPeriodId = conn.prepareStatement(queryPeriodId)) {
                statementPeriodId.setDate(1, Date.valueOf(payPeriodStart));
                statementPeriodId.setDate(2, Date.valueOf(payPeriodEnd));
                ResultSet rsPeriodId = statementPeriodId.executeQuery();

                int payrollPeriodId = 0;
                if (rsPeriodId.next()) {
                    payrollPeriodId = rsPeriodId.getInt("payroll_period_id");
                }
                rsPeriodId.close();

                if (payrollPeriodId > 0) {
                    // Query timesheet records based on payroll_period_id
                    String sql = "SELECT t.timesheet_id, t.emp_id, CONCAT(e.last_name, ', ', e.first_name) as employee_name, t.date, t.time_in, t.time_out, t.total_hours " +
                                 "FROM payrollsystem_db.timesheet t " +
                                 "JOIN payrollsystem_db.employee e ON t.emp_id = e.emp_id " +
                                 "WHERE t.pay_period_id = ?";
                    
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setInt(1, payrollPeriodId);
                        ResultSet resultSet = statement.executeQuery();
                        
                        while (resultSet.next()) {
                            int timesheetId = resultSet.getInt("timesheet_id");
                            int empId = resultSet.getInt("emp_id");
                            String employeeName = resultSet.getString("employee_name");
                            Date date = resultSet.getDate("date");
                            Time timeIn = resultSet.getTime("time_in");
                            Time timeOut = resultSet.getTime("time_out");
                            double totalHours = resultSet.getDouble("total_hours");
                            String[] record = {String.valueOf(timesheetId), String.valueOf(empId), employeeName, date.toString(), timeIn.toString(), timeOut.toString(), Double.toString(totalHours)};
                            records.add(record);
                        }

                        resultSet.close();
                    }
                } else {
                    System.out.println("No payroll period found for the specified dates.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return records;
    }

    // Method to retrieve distinct pay periods (pay_period_start - pay_period_end) for dropdown
    public List<String> getDistinctPayPeriods() {
        List<String> payPeriods = new ArrayList<>();
        try {
            String sql = "SELECT pay_period_start, pay_period_end FROM payrollsystem_db.payroll_periods";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDate payPeriodStart = resultSet.getDate("pay_period_start").toLocalDate();
                LocalDate payPeriodEnd = resultSet.getDate("pay_period_end").toLocalDate();
                String formattedPeriod = payPeriodStart.toString() + " to " + payPeriodEnd.toString();
                payPeriods.add(formattedPeriod);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payPeriods;
    }

    // Method to record time in for an employee
    public void recordTimeIn(int empId, LocalDate date, Time timeIn) {
        String sql = "INSERT INTO payrollsystem_db.timesheet (emp_id, date, time_in, time_out) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, empId);
            statement.setDate(2, Date.valueOf(date)); // Convert LocalDate to SQL Date
            statement.setTime(3, timeIn); // Use provided Time for timeIn
            statement.setNull(4, Types.TIME); // Set time_out to NULL initially
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to record time out for an employee
    public void recordTimeOut(int empId) {
        String sql = "UPDATE payrollsystem_db.timesheet SET time_out = ? WHERE emp_id = ? AND DATE(date) = CURDATE() AND time_out IS NULL";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setTime(1, new Time(System.currentTimeMillis()));  // Current system time as time out
            statement.setInt(2, empId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
 // Method to retrieve timesheet records for the logged-in employee
    public List<String[]> getLoggedInEmployeeTimesheetRecords(int empId) {
        List<String[]> records = new ArrayList<>();
        String sql = "SELECT date, time_in, time_out, total_hours, pay_period_id FROM payrollsystem_db.timesheet WHERE emp_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getDate("date").toString();
                String timeIn = resultSet.getTime("time_in") != null ? resultSet.getTime("time_in").toString() : "";
                String timeOut = resultSet.getTime("time_out") != null ? resultSet.getTime("time_out").toString() : "";
                String totalHours = resultSet.getString("total_hours");
                int payPeriodId = resultSet.getInt("pay_period_id");
                records.add(new String[]{date, timeIn, timeOut, totalHours, String.valueOf(payPeriodId)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    
}
