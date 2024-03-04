package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Payslip;

public class PayslipDAO {
    private Connection connection;
    private static PayslipDAO instance;

    private PayslipDAO() {
        // Initialize database connection
        connection = DatabaseConnection.getConnection();
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
            statement.setString(3, payslip.getEmployeeId());
            statement.setString(4, payslip.getEmployeeName());
            statement.setString(5, payslip.getEmployeePosition());
            statement.setDouble(6, payslip.getHourlyRate());
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

    // Method to retrieve payslip records from the database
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
                        resultSet.getString("emp_id"),
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
}

