package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Employee;
import model.EmployeeHoursByPayPeriod;
import model.Payslip;
import service.SQL_client;

public class PayslipDAO {
    private static PayslipDAO instance = null;
    private static Connection connection;
    private EmployeeDAO employeeDAO;

    private PayslipDAO() {
        connection = SQL_client.getInstance().getConnection();
        employeeDAO = EmployeeDAO.getInstance();
    }

    public static PayslipDAO getInstance() {
        if (instance == null) {
            instance = new PayslipDAO();
        }
        return instance;
    }

    // Method to insert a payslip record into the database
    public boolean insertPayslip(Payslip payslip) {
        String sql = "INSERT INTO payrollsystem_db.payslips (emp_id, pay_period_start, pay_period_end, gross_pay, total_deductions, total_allowances, net_pay) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, payslip.getEmployeeId());
            statement.setDate(2, java.sql.Date.valueOf(payslip.getPeriodStartDate()));
            statement.setDate(3, java.sql.Date.valueOf(payslip.getPeriodEndDate()));
            statement.setDouble(4, payslip.getGrossIncome());
            statement.setDouble(5, payslip.getTotalDeductions());
            statement.setDouble(6, payslip.getTotalAllowances());
            statement.setDouble(7, payslip.getNetPay());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Payslip record inserted successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting payslip record: " + e.getMessage());
        }
        return false;
    }

    // Method to insert multiple payslip records in a batch transaction
    public boolean batchInsertPayslips(List<Payslip> payslips) {
        String sql = "INSERT INTO payrollsystem_db.payslips (emp_id, pay_period_start, pay_period_end, gross_pay, total_deductions, total_allowances, net_pay) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false); // Start transaction

            for (Payslip payslip : payslips) {
                statement.setInt(1, payslip.getEmployeeId());
                statement.setDate(2, java.sql.Date.valueOf(payslip.getPeriodStartDate()));
                statement.setDate(3, java.sql.Date.valueOf(payslip.getPeriodEndDate()));
                statement.setDouble(4, payslip.getGrossIncome());
                statement.setDouble(5, payslip.getTotalDeductions());
                statement.setDouble(6, payslip.getTotalAllowances());
                statement.setDouble(7, payslip.getNetPay());
                statement.addBatch(); // Add batch
            }

            int[] rowsInserted = statement.executeBatch(); // Execute batch insert
            connection.commit(); // Commit transaction

            // Check if all rows were inserted successfully
            for (int rows : rowsInserted) {
                if (rows != 1) {
                    return false; // If any row failed to insert, return false
                }
            }

            return true; // All rows inserted successfully
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction in case of an exception
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle rollback error
            }
            System.out.println("Error inserting payslips: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle reset auto-commit error
            }
        }
    }

   
    // Method to retrieve distinct pay periods (pay_period_start - pay_period_end) for dropdown
    public static List<String> getDistinctPayPeriods() {
        List<String> payPeriods = new ArrayList<>();
        try {
            String sql = "SELECT pay_period_start, pay_period_end FROM payrollsystem_db.payroll_periods";
            PreparedStatement statement = connection.prepareStatement(sql);
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

    // Method to retrieve payslips using payslip_view
    public List<Payslip> getPayslips() {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payrollsystem_db.payslip_view";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payslip payslip = new Payslip(
                        resultSet.getDate("pay_period_start").toLocalDate(),
                        resultSet.getDate("pay_period_end").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getDouble("hourly_rate"),
                        resultSet.getDouble("basic_salary"),
                        resultSet.getDouble("total_hours_worked"),
                        resultSet.getDouble("overtime_hours"),
                        resultSet.getDouble("gross_pay"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_allowances"),
                        resultSet.getDouble("sss_contrib_amount"),
                        resultSet.getDouble("philhealth_contrib_amount"),
                        resultSet.getDouble("pagibig_contrib_amount"),
                        resultSet.getDouble("withholding_tax"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("net_pay")
                );
                payslips.add(payslip);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslips: " + e.getMessage());
        }
        return payslips;
    }

    // Method to retrieve payslip by employee ID and month-year
    public Payslip getPayslipByEmployeeIdAndMonthYear(String employeeId, String monthYear) {
        Payslip payslip = null;
        String sql = "SELECT * FROM payrollsystem_db.payslip_view WHERE emp_id = ? AND YEAR(pay_period_start) = ? AND MONTH(pay_period_start) = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employeeId);

            // Extract year and month from monthYear string
            String[] parts = monthYear.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            statement.setInt(2, year);
            statement.setInt(3, month);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                payslip = new Payslip(
                        resultSet.getDate("pay_period_start").toLocalDate(),
                        resultSet.getDate("pay_period_end").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getDouble("hourly_rate"),
                        resultSet.getDouble("basic_salary"),
                        resultSet.getDouble("total_hours_worked"),
                        resultSet.getDouble("overtime_hours"),
                        resultSet.getDouble("gross_pay"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_allowances"),
                        resultSet.getDouble("sss_contrib_amount"),
                        resultSet.getDouble("philhealth_contrib_amount"),
                        resultSet.getDouble("pagibig_contrib_amount"),
                        resultSet.getDouble("withholding_tax"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("net_pay")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslip by employee ID and month-year: " + e.getMessage());
        }
        return payslip;
    }

    // Method to retrieve payslip by employee ID
    public Payslip getPayslipByEmployeeId(String employeeId) {
        Payslip payslip = null;
        String sql = "SELECT * FROM payrollsystem_db.payslip_view WHERE emp_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                payslip = new Payslip(
                        resultSet.getDate("pay_period_start").toLocalDate(),
                        resultSet.getDate("pay_period_end").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getDouble("hourly_rate"),
                        resultSet.getDouble("basic_salary"),
                        resultSet.getDouble("total_hours_worked"),
                        resultSet.getDouble("overtime_hours"),
                        resultSet.getDouble("gross_pay"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_allowances"),
                        resultSet.getDouble("sss_contrib_amount"),
                        resultSet.getDouble("philhealth_contrib_amount"),
                        resultSet.getDouble("pagibig_contrib_amount"),
                        resultSet.getDouble("withholding_tax"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("net_pay")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslip by employee ID: " + e.getMessage());
        }
        return payslip;
    }
    
    // Method to execute CalculateAndInsertNetPay stored procedure
    public static boolean executeCalculateAndInsertNetPay(int periodId) {
        String sql = "CALL payrollsystem_db.CalculateAndInsertNetPay(?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, periodId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error executing CalculateAndInsertNetPay stored procedure: " + e.getMessage());
            return false;
        }
    }


    // Method to retrieve newly inserted payslips for a specific pay period ID
    public List<Payslip> getNewlyInsertedPayslips(int periodId) {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payrollsystem_db.payslips WHERE payroll_period_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, periodId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payslip payslip = new Payslip(
                        resultSet.getInt("payslip_id"),
                        resultSet.getInt("emp_id"),
                        resultSet.getDate("pay_period_start").toLocalDate(),
                        resultSet.getDate("pay_period_end").toLocalDate(),
                        resultSet.getDouble("gross_pay"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("total_allowances"),
                        resultSet.getDouble("net_pay")
                );
                payslips.add(payslip);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving newly inserted payslips: " + e.getMessage());
        }
        return payslips;
    }
    
    // Method to filter records by a specific pay period and populate a JTable
    public void filterRecordsByPayPeriod(String selectedPayPeriod, JTable table) {
        try {
            String[] dates = selectedPayPeriod.split(" to ");
            LocalDate payPeriodStart = LocalDate.parse(dates[0]);
            LocalDate payPeriodEnd = LocalDate.parse(dates[1]);

            String sql = "SELECT * FROM payrollsystem_db.employee_attendance WHERE attendance_date >= ? AND attendance_date <= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(payPeriodStart));
            statement.setDate(2, Date.valueOf(payPeriodEnd));

            ResultSet resultSet = statement.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            // Populate the table model with data from the result set
            // Example:
            while (resultSet.next()) {
                // Example assuming columns 'employee_id' and 'attendance_date'
                String employeeId = resultSet.getString("employee_id");
                Date attendanceDate = resultSet.getDate("attendance_date");
                // Add row to model
                model.addRow(new Object[]{employeeId, attendanceDate});
            }
            // Set the model to the JTable
            table.setModel(model);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to retrieve pay period ID based on the pay period string
    public static int getPayPeriodId(String payPeriod) {
        int payPeriodId = -1;
        String[] dates = payPeriod.split(" to ");
        LocalDate startDate = LocalDate.parse(dates[0]);
        LocalDate endDate = LocalDate.parse(dates[1]);

        String sql = "SELECT payroll_period_id FROM payrollsystem_db.payroll_periods WHERE pay_period_start = ? AND pay_period_end = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                payPeriodId = resultSet.getInt("payroll_period_id");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving pay period ID: " + e.getMessage());
        }

        return payPeriodId;
    }
    
 
 // Method to retrieve payslips for a specific pay period ID
    public List<Payslip> getPayslipsByPayPeriod(String payPeriod) {
        List<Payslip> payslips = new ArrayList<>();
        int payPeriodId = getPayPeriodId(payPeriod); // Assuming getPayPeriodId method exists

        if (payPeriodId == -1) {
            System.out.println("Invalid pay period: " + payPeriod);
            return payslips; // Return empty list if pay period ID is not valid
        }

        String sql = "SELECT * FROM payrollsystem_db.payslip_view WHERE payroll_period_id = ?";

        try (Connection connection = SQL_client.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payPeriodId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payslip payslip = new Payslip(
                        resultSet.getInt("emp_id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getDouble("hourly_rate"),
                        resultSet.getInt("total_hours_worked"),
                        resultSet.getInt("overtime_hours"),
                        resultSet.getDouble("gross_pay"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_allowances"),
                        resultSet.getDouble("sss_contrib_amount"),
                        resultSet.getDouble("philhealth_contrib_amount"),
                        resultSet.getDouble("pagibig_contrib_amount"),
                        resultSet.getDouble("withholding_tax"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("net_pay"),
                        resultSet.getDate("pay_period_start").toLocalDate(),
                        resultSet.getDate("pay_period_end").toLocalDate(),
                        resultSet.getInt("payroll_period_id"),
                        resultSet.getDate("date_generated").toLocalDate()
                );
                payslips.add(payslip);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving payslips by pay period: " + e.getMessage());
        }

        return payslips;
    }
    
 // Method to retrieve employee hours by pay period ID
    public List<EmployeeHoursByPayPeriod> getEmployeeHoursByPayPeriod(int payPeriodId) {
        List<EmployeeHoursByPayPeriod> employeeHoursList = new ArrayList<>();
        String sql = "SELECT emp_id, employee_name, total_hours, overtime_total_hours " +
                     "FROM payrollsystem_db.employee_hours_by_pay_period " +
                     "WHERE pay_period_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payPeriodId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EmployeeHoursByPayPeriod employeeHours = new EmployeeHoursByPayPeriod(
                        resultSet.getInt("emp_id"),
                        resultSet.getString("employee_name"),
                        resultSet.getDouble("total_hours"),
                        resultSet.getDouble("overtime_total_hours")
                );
                employeeHoursList.add(employeeHours);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving employee hours by pay period: " + e.getMessage());
        }
        return employeeHoursList;
    }

    
    public Payslip getPayslipByEmployeeIdAndPayPeriod(String employeeId, int payPeriodId) {
        Payslip payslip = null;
        String sql = "SELECT * FROM payrollsystem_db.payslip_view WHERE emp_id = ? AND payroll_period_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employeeId);
            statement.setInt(2, payPeriodId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                payslip = new Payslip(
                    resultSet.getInt("payslip_id"),  // Ensure payslip_id is included in your query
                    resultSet.getDate("pay_period_start").toLocalDate(),
                    resultSet.getDate("pay_period_end").toLocalDate(),
                    resultSet.getInt("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("position"),
                    resultSet.getDouble("hourly_rate"),
                    resultSet.getDouble("basic_salary"),
                    resultSet.getInt("total_hours_worked"),
                    resultSet.getInt("overtime_hours"),  // Ensure overtime_hours is included in your query
                    resultSet.getDouble("gross_pay"),
                    resultSet.getDouble("rice_subsidy"),
                    resultSet.getDouble("phone_allowance"),
                    resultSet.getDouble("clothing_allowance"),
                    resultSet.getDouble("total_allowances"),
                    resultSet.getDouble("sss_contrib_amount"),
                    resultSet.getDouble("philhealth_contrib_amount"),
                    resultSet.getDouble("pagibig_contrib_amount"),
                    resultSet.getDouble("withholding_tax"),
                    resultSet.getDouble("total_deductions"),
                    resultSet.getDouble("net_pay")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslip by employee ID and pay period: " + e.getMessage());
        }
        return payslip;
    }



}
