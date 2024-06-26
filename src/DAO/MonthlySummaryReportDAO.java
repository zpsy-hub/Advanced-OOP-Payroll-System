package DAO;

import model.MonthlySummaryReport;
import model.MonthlySummaryReportWithTotal;
import service.SQL_client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonthlySummaryReportDAO {
    private static Connection connection;

    public MonthlySummaryReportDAO() {
        connection = SQL_client.getInstance().getConnection();
    }

    public List<MonthlySummaryReport> getAllMonthlySummaryReports() {
        List<MonthlySummaryReport> monthlySummaryReports = new ArrayList<>();
        String sql = "SELECT emp_id, employee_name, position, department, gross_income, sss_no, sss_contrib_amount, " +
                "philhealth_no, philhealth_contrib_amount, pagibig_no, pagibig_contrib_amount, bir_no, " +
                "withholding_tax, net_pay, payroll_period_id FROM payrollsystem_db.monthly_summary_report_breakdown";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                MonthlySummaryReport monthlySummaryReport = new MonthlySummaryReport();
                monthlySummaryReport.setEmployeeId(resultSet.getInt("emp_id"));
                monthlySummaryReport.setEmployeeName(resultSet.getString("employee_name"));
                monthlySummaryReport.setPosition(resultSet.getString("position"));
                monthlySummaryReport.setDepartment(resultSet.getString("department"));
                monthlySummaryReport.setGrossIncome(resultSet.getDouble("gross_income"));
                monthlySummaryReport.setSssNumber(resultSet.getString("sss_no"));
                monthlySummaryReport.setSssContribution(resultSet.getDouble("sss_contrib_amount"));
                monthlySummaryReport.setPhilhealthNumber(resultSet.getString("philhealth_no"));
                monthlySummaryReport.setPhilhealthContribution(resultSet.getDouble("philhealth_contrib_amount"));
                monthlySummaryReport.setPagibigNumber(resultSet.getString("pagibig_no"));
                monthlySummaryReport.setPagibigContribution(resultSet.getDouble("pagibig_contrib_amount"));
                monthlySummaryReport.setTinNumber(resultSet.getString("bir_no"));
                monthlySummaryReport.setWithholdingTax(resultSet.getDouble("withholding_tax"));
                monthlySummaryReport.setNetPay(resultSet.getDouble("net_pay"));
                monthlySummaryReports.add(monthlySummaryReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlySummaryReports;
    }

    public List<MonthlySummaryReport> getMonthlySummaryReportsByPeriod(int payrollPeriodId) {
        List<MonthlySummaryReport> monthlySummaryReports = new ArrayList<>();
        String sql = "SELECT emp_id, employee_name, position, department, gross_income, sss_no, sss_contrib_amount, " +
                "philhealth_no, philhealth_contrib_amount, pagibig_no, pagibig_contrib_amount, bir_no, " +
                "withholding_tax, net_pay, payroll_period_id FROM payrollsystem_db.monthly_summary_report_breakdown " +
                "WHERE payroll_period_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payrollPeriodId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    MonthlySummaryReport monthlySummaryReport = new MonthlySummaryReport();
                    monthlySummaryReport.setEmployeeId(resultSet.getInt("emp_id"));
                    monthlySummaryReport.setEmployeeName(resultSet.getString("employee_name"));
                    monthlySummaryReport.setPosition(resultSet.getString("position"));
                    monthlySummaryReport.setDepartment(resultSet.getString("department"));
                    monthlySummaryReport.setGrossIncome(resultSet.getDouble("gross_income"));
                    monthlySummaryReport.setSssNumber(resultSet.getString("sss_no"));
                    monthlySummaryReport.setSssContribution(resultSet.getDouble("sss_contrib_amount"));
                    monthlySummaryReport.setPhilhealthNumber(resultSet.getString("philhealth_no"));
                    monthlySummaryReport.setPhilhealthContribution(resultSet.getDouble("philhealth_contrib_amount"));
                    monthlySummaryReport.setPagibigNumber(resultSet.getString("pagibig_no"));
                    monthlySummaryReport.setPagibigContribution(resultSet.getDouble("pagibig_contrib_amount"));
                    monthlySummaryReport.setTinNumber(resultSet.getString("bir_no"));
                    monthlySummaryReport.setWithholdingTax(resultSet.getDouble("withholding_tax"));
                    monthlySummaryReport.setNetPay(resultSet.getDouble("net_pay"));
                    monthlySummaryReports.add(monthlySummaryReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlySummaryReports;
    }

    public MonthlySummaryReportWithTotal getMonthlySummaryReportsWithTotal(int payrollPeriodId) {
        List<MonthlySummaryReport> monthlySummaryReports = new ArrayList<>();
        double totalGrossIncome = 0;
        double totalSssContribution = 0;
        double totalPhilhealthContribution = 0;
        double totalPagibigContribution = 0;
        double totalWithholdingTax = 0;
        double totalNetPay = 0;

        String sql = "SELECT emp_id, employee_name, position, department, gross_income, sss_no, sss_contrib_amount, " +
                "philhealth_no, philhealth_contrib_amount, pagibig_no, pagibig_contrib_amount, bir_no, " +
                "withholding_tax, net_pay, payroll_period_id FROM payrollsystem_db.monthly_summary_report_breakdown " +
                "WHERE payroll_period_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payrollPeriodId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    MonthlySummaryReport monthlySummaryReport = new MonthlySummaryReport();
                    monthlySummaryReport.setEmployeeId(resultSet.getInt("emp_id"));
                    monthlySummaryReport.setEmployeeName(resultSet.getString("employee_name"));
                    monthlySummaryReport.setPosition(resultSet.getString("position"));
                    monthlySummaryReport.setDepartment(resultSet.getString("department"));
                    monthlySummaryReport.setGrossIncome(resultSet.getDouble("gross_income"));
                    monthlySummaryReport.setSssNumber(resultSet.getString("sss_no"));
                    monthlySummaryReport.setSssContribution(resultSet.getDouble("sss_contrib_amount"));
                    monthlySummaryReport.setPhilhealthNumber(resultSet.getString("philhealth_no"));
                    monthlySummaryReport.setPhilhealthContribution(resultSet.getDouble("philhealth_contrib_amount"));
                    monthlySummaryReport.setPagibigNumber(resultSet.getString("pagibig_no"));
                    monthlySummaryReport.setPagibigContribution(resultSet.getDouble("pagibig_contrib_amount"));
                    monthlySummaryReport.setTinNumber(resultSet.getString("bir_no"));
                    monthlySummaryReport.setWithholdingTax(resultSet.getDouble("withholding_tax"));
                    monthlySummaryReport.setNetPay(resultSet.getDouble("net_pay"));

                    totalGrossIncome += monthlySummaryReport.getGrossIncome();
                    totalSssContribution += monthlySummaryReport.getSssContribution();
                    totalPhilhealthContribution += monthlySummaryReport.getPhilhealthContribution();
                    totalPagibigContribution += monthlySummaryReport.getPagibigContribution();
                    totalWithholdingTax += monthlySummaryReport.getWithholdingTax();
                    totalNetPay += monthlySummaryReport.getNetPay();

                    monthlySummaryReports.add(monthlySummaryReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MonthlySummaryReportWithTotal summaryWithTotal = new MonthlySummaryReportWithTotal();
        summaryWithTotal.setMonthlySummaryReports(monthlySummaryReports);
        summaryWithTotal.setTotalGrossIncome(totalGrossIncome);
        summaryWithTotal.setTotalSssContribution(totalSssContribution);
        summaryWithTotal.setTotalPhilhealthContribution(totalPhilhealthContribution);
        summaryWithTotal.setTotalPagibigContribution(totalPagibigContribution);
        summaryWithTotal.setTotalWithholdingTax(totalWithholdingTax);
        summaryWithTotal.setTotalNetPay(totalNetPay);

        return summaryWithTotal;
    }
}
