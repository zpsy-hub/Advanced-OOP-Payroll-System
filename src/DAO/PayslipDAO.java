package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Payslip;
import service.SQL_client;

public class PayslipDAO {
    private static PayslipDAO instance = null;
    private Connection connection;
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
        String sql = "INSERT INTO payroll_system.payslip (period_startdate, period_enddate, emp_id, emp_name, emp_position, hourlyRate, monthlyRate, totalHours, overtimeHours, gross_income, rice_subsidy, phone_allowance, clothing_allowance, total_benefits, sss_contrib, philhealth_contrib, pagibig_contrib, withholding_tax, total_deductions, net_pay) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(payslip.getPeriodStartDate()));
            statement.setDate(2, java.sql.Date.valueOf(payslip.getPeriodEndDate()));
            statement.setInt(3, payslip.getEmployeeId());
            statement.setString(4, payslip.getEmployeeName());
            statement.setString(5, payslip.getEmployeePosition());
            double hourlyRate = employeeDAO.getHourlyRateById(payslip.getEmployeeId());
            statement.setDouble(6, hourlyRate);
            statement.setDouble(7, payslip.getMonthlyRate());
            statement.setInt(8, payslip.getTotalHours());
            statement.setInt(9, payslip.getOvertimeHours());
            statement.setDouble(10, payslip.getGrossIncome());
            statement.setDouble(11, payslip.getRiceSubsidy());
            statement.setDouble(12, payslip.getPhoneAllowance());
            statement.setDouble(13, payslip.getClothingAllowance());
            statement.setDouble(14, payslip.getTotalBenefits());
            statement.setDouble(15, payslip.getSssContribution());
            statement.setDouble(16, payslip.getPhilhealthContribution());
            statement.setDouble(17, payslip.getPagibigContribution());
            statement.setDouble(18, payslip.getWithholdingTax());
            statement.setDouble(19, payslip.getTotalDeductions());
            statement.setDouble(20, payslip.getNetPay());

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
    
    public boolean batchInsertPayslips(List<Payslip> payslips) {
        String sql = "INSERT INTO payroll_system.payslip (period_startdate, period_enddate, emp_id, emp_name, emp_position, hourlyRate, monthlyRate, totalHours, overtimeHours, gross_income, rice_subsidy, phone_allowance, clothing_allowance, total_benefits, sss_contrib, philhealth_contrib, pagibig_contrib, withholding_tax, total_deductions, net_pay) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false); // Start transaction
            
            for (Payslip payslip : payslips) {
                // Set parameters for each payslip
                statement.setDate(1, java.sql.Date.valueOf(payslip.getPeriodStartDate()));
                statement.setDate(2, java.sql.Date.valueOf(payslip.getPeriodEndDate()));
                statement.setInt(3, payslip.getEmployeeId());
                statement.setString(4, payslip.getEmployeeName());
                statement.setString(5, payslip.getEmployeePosition());
                double hourlyRate = EmployeeDAO.getHourlyRateById(payslip.getEmployeeId());
                statement.setDouble(6, hourlyRate);
                statement.setDouble(7, payslip.getMonthlyRate());
                statement.setInt(8, payslip.getTotalHours());
                statement.setInt(9, payslip.getOvertimeHours());
                statement.setDouble(10, payslip.getGrossIncome());
                statement.setDouble(11, payslip.getRiceSubsidy());
                statement.setDouble(12, payslip.getPhoneAllowance());
                statement.setDouble(13, payslip.getClothingAllowance());
                statement.setDouble(14, payslip.getTotalBenefits());
                statement.setDouble(15, payslip.getSssContribution());
                statement.setDouble(16, payslip.getPhilhealthContribution());
                statement.setDouble(17, payslip.getPagibigContribution());
                statement.setDouble(18, payslip.getWithholdingTax());
                statement.setDouble(19, payslip.getTotalDeductions());
                statement.setDouble(20, payslip.getNetPay());
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


    // Method to retrieve an employee's payslip records from the database
    public List<Payslip> getPayslips() {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payroll_system.payslip";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve payslip information from the result set and create Payslip objects
                Payslip payslip = new Payslip(
                		resultSet.getDate("period_startdate").toLocalDate(),
                        resultSet.getDate("period_enddate").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("emp_name"),
                        resultSet.getString("emp_position"),
                        resultSet.getDouble("hourlyRate"),
                        resultSet.getDouble("monthlyRate"),
                        resultSet.getInt("totalHours"),
                        resultSet.getInt("overtimeHours"),
                        resultSet.getDouble("gross_income"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_benefits"),
                        resultSet.getDouble("sss_contrib"),
                        resultSet.getDouble("philhealth_contrib"),
                        resultSet.getDouble("pagibig_contrib"),
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
    
    public Payslip getPayslipByEmployeeIdAndMonthYear(String employeeId, String monthYear) {
        Payslip payslip = null;
        String sql = "SELECT * FROM payroll_system.payslip WHERE emp_id = ? AND " +
                     "YEAR(period_startdate) = ? AND MONTH(period_startdate) = ?";

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
                        resultSet.getDate("period_startdate").toLocalDate(),
                        resultSet.getDate("period_enddate").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("emp_name"),
                        resultSet.getString("emp_position"),
                        resultSet.getDouble("hourlyRate"),
                        resultSet.getDouble("monthlyRate"),
                        resultSet.getInt("totalHours"),
                        resultSet.getInt("overtimeHours"),
                        resultSet.getDouble("gross_income"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_benefits"),
                        resultSet.getDouble("sss_contrib"),
                        resultSet.getDouble("philhealth_contrib"),
                        resultSet.getDouble("pagibig_contrib"),
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

    
    public Payslip getPayslipByEmployeeId(String employeeId) {
        Payslip payslip = null;
        String sql = "SELECT * FROM payroll_system.payslip WHERE emp_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                payslip = new Payslip(
                    resultSet.getDate("period_startdate").toLocalDate(),
                    resultSet.getDate("period_enddate").toLocalDate(),
                    resultSet.getInt("emp_id"),
                    resultSet.getString("emp_name"),
                    resultSet.getString("emp_position"),
                    resultSet.getDouble("hourlyRate"),
                    resultSet.getDouble("monthlyRate"),
                    resultSet.getInt("totalHours"),
                    resultSet.getInt("overtimeHours"),
                    resultSet.getDouble("gross_income"),
                    resultSet.getDouble("rice_subsidy"),
                    resultSet.getDouble("phone_allowance"),
                    resultSet.getDouble("clothing_allowance"),
                    resultSet.getDouble("total_benefits"),
                    resultSet.getDouble("sss_contrib"),
                    resultSet.getDouble("philhealth_contrib"),
                    resultSet.getDouble("pagibig_contrib"),
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


    // Method to retrieve all employees payslip records for a specific month and year
    public List<Payslip> getPayslipsByMonthYear(String monthYear) {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payroll_system.payslip WHERE period_startdate >= ? AND period_enddate <= ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            LocalDate startDate = LocalDate.parse(monthYear + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payslip payslip = new Payslip(
                		resultSet.getDate("period_startdate").toLocalDate(),
                        resultSet.getDate("period_enddate").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("emp_name"),
                        resultSet.getString("emp_position"),
                        resultSet.getDouble("hourlyRate"),
                        resultSet.getDouble("monthlyRate"),
                        resultSet.getInt("totalHours"),
                        resultSet.getInt("overtimeHours"),
                        resultSet.getDouble("gross_income"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_benefits"),
                        resultSet.getDouble("sss_contrib"),
                        resultSet.getDouble("philhealth_contrib"),
                        resultSet.getDouble("pagibig_contrib"),
                        resultSet.getDouble("withholding_tax"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("net_pay")
                );
                payslips.add(payslip);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslips by month and year: " + e.getMessage());
        }
        return payslips;
    }
    
	// Method to retrieve all payslip records from the database
    public List<Payslip> getAllPayslips() {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payroll_system.payslip";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payslip payslip = new Payslip(
                    resultSet.getDate("period_startdate").toLocalDate(),
                    resultSet.getDate("period_enddate").toLocalDate(),
                    resultSet.getInt("emp_id"),
                    resultSet.getString("emp_name"),
                    resultSet.getString("emp_position"),
                    resultSet.getDouble("hourlyRate"),
                    resultSet.getDouble("monthlyRate"),
                    resultSet.getInt("totalHours"),
                    resultSet.getInt("overtimeHours"),
                    resultSet.getDouble("gross_income"),
                    resultSet.getDouble("rice_subsidy"),
                    resultSet.getDouble("phone_allowance"),
                    resultSet.getDouble("clothing_allowance"),
                    resultSet.getDouble("total_benefits"),
                    resultSet.getDouble("sss_contrib"),
                    resultSet.getDouble("philhealth_contrib"),
                    resultSet.getDouble("pagibig_contrib"),
                    resultSet.getDouble("withholding_tax"),
                    resultSet.getDouble("total_deductions"),
                    resultSet.getDouble("net_pay")
                );
                payslips.add(payslip);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all payslips: " + e.getMessage());
        }
        return payslips;
    }
    
    public List<Integer> getPayslipNumbersByEmployeeIdAndMonthYear(String employeeId, String monthYear) {
        List<Integer> payslipNumbers = new ArrayList<>();
        String sql = "SELECT payslip_no FROM payroll_system.payslip WHERE emp_id = ? AND " +
                     "period_startdate >= ? AND period_enddate <= ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            LocalDate startDate = LocalDate.parse(monthYear + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            statement.setString(1, employeeId);
            statement.setDate(2, java.sql.Date.valueOf(startDate));
            statement.setDate(3, java.sql.Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int payslipNumber = resultSet.getInt("payslip_no");
                payslipNumbers.add(payslipNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslip numbers by employee ID and month-year: " + e.getMessage());
        }
        return payslipNumbers;
    }
    
    private Payslip getPayslipDetailsByNumber(int payslipNumber) {
        Payslip payslip = null;
        String sql = "SELECT * FROM payroll_system.payslip WHERE payslip_no = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, payslipNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve payslip information from the result set and create a Payslip object
                payslip = new Payslip(
                        resultSet.getDate("period_startdate").toLocalDate(),
                        resultSet.getDate("period_enddate").toLocalDate(),
                        resultSet.getInt("emp_id"),
                        resultSet.getString("emp_name"),
                        resultSet.getString("emp_position"),
                        resultSet.getDouble("hourlyRate"),
                        resultSet.getDouble("monthlyRate"),
                        resultSet.getInt("totalHours"),
                        resultSet.getInt("overtimeHours"),
                        resultSet.getDouble("gross_income"),
                        resultSet.getDouble("rice_subsidy"),
                        resultSet.getDouble("phone_allowance"),
                        resultSet.getDouble("clothing_allowance"),
                        resultSet.getDouble("total_benefits"),
                        resultSet.getDouble("sss_contrib"),
                        resultSet.getDouble("philhealth_contrib"),
                        resultSet.getDouble("pagibig_contrib"),
                        resultSet.getDouble("withholding_tax"),
                        resultSet.getDouble("total_deductions"),
                        resultSet.getDouble("net_pay")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payslip details by payslip number: " + e.getMessage());
        }
        return payslip;
    }

}
