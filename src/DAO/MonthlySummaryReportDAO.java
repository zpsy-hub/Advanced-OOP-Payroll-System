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

    public static List<MonthlySummaryReport> generateMonthlySummaryReport(String monthYear) {
        List<MonthlySummaryReport> monthlySummaryReports = new ArrayList<>();
        String sql = "SELECT e.emp_id, CONCAT(e.employee_firstname, ' ', e.employee_lastname) AS employee_name, e.position, " +
                "p.gross_income, e.sss_number, p.sss_contrib, e.philhealth_number, p.philhealth_contrib, " +
                "e.pagibig_number, p.pagibig_contrib, e.tin_number, p.withholding_tax, p.net_pay " +
                "FROM payroll_system.employees e " +
                "INNER JOIN payroll_system.payslip p ON e.emp_id = p.emp_id " +
                "WHERE YEAR(p.period_startdate) = ? AND MONTH(p.period_startdate) = ?";



        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String[] parts = monthYear.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            statement.setInt(1, year);
            statement.setInt(2, month);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MonthlySummaryReport monthlySummaryReport = new MonthlySummaryReport();
                monthlySummaryReport.setEmployeeId(resultSet.getInt("emp_id"));
                monthlySummaryReport.setEmployeeName(resultSet.getString("employee_name"));
                monthlySummaryReport.setPosition(resultSet.getString("position"));
                monthlySummaryReport.setGrossIncome(resultSet.getDouble("gross_income"));
                monthlySummaryReport.setSssNumber(resultSet.getString("sss_number"));
                monthlySummaryReport.setSssContribution(resultSet.getDouble("sss_contrib"));
                monthlySummaryReport.setPhilhealthNumber(resultSet.getString("philhealth_number"));
                monthlySummaryReport.setPhilhealthContribution(resultSet.getDouble("philhealth_contrib"));
                monthlySummaryReport.setPagibigNumber(resultSet.getString("pagibig_number"));
                monthlySummaryReport.setPagibigContribution(resultSet.getDouble("pagibig_contrib"));
                monthlySummaryReport.setTinNumber(resultSet.getString("tin_number"));
                monthlySummaryReport.setWithholdingTax(resultSet.getDouble("withholding_tax"));
                monthlySummaryReport.setNetPay(resultSet.getDouble("net_pay"));
                monthlySummaryReports.add(monthlySummaryReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlySummaryReports;
    }
    
    public MonthlySummaryReportWithTotal generateMonthlySummaryReportWithTotal(String monthYear) {
        List<MonthlySummaryReport> monthlySummaryReports = new ArrayList<>();
        double totalGrossIncome = 0;
        double totalSssContribution = 0;
        double totalPhilhealthContribution = 0;
        double totalPagibigContribution = 0;
        double totalWithholdingTax = 0;
        double totalNetPay = 0;

        String sql = "SELECT e.emp_id, CONCAT(e.employee_firstname, ' ', e.employee_lastname) AS employee_name, e.position, " +
                "p.gross_income, e.sss_number, p.sss_contrib, e.philhealth_number, p.philhealth_contrib, " +
                "e.pagibig_number, p.pagibig_contrib, e.tin_number, p.withholding_tax, p.net_pay " +
                "FROM payroll_system.employees e " +
                "INNER JOIN payroll_system.payslip p ON e.emp_id = p.emp_id " +
                "WHERE YEAR(p.period_startdate) = ? AND MONTH(p.period_startdate) = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String[] parts = monthYear.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            statement.setInt(1, year);
            statement.setInt(2, month);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MonthlySummaryReport monthlySummaryReport = new MonthlySummaryReport();
                monthlySummaryReport.setEmployeeId(resultSet.getInt("emp_id"));
                monthlySummaryReport.setEmployeeName(resultSet.getString("employee_name"));
                monthlySummaryReport.setPosition(resultSet.getString("position"));
                monthlySummaryReport.setGrossIncome(resultSet.getDouble("gross_income"));
                monthlySummaryReport.setSssNumber(resultSet.getString("sss_number"));
                monthlySummaryReport.setSssContribution(resultSet.getDouble("sss_contrib"));
                monthlySummaryReport.setPhilhealthNumber(resultSet.getString("philhealth_number"));
                monthlySummaryReport.setPhilhealthContribution(resultSet.getDouble("philhealth_contrib"));
                monthlySummaryReport.setPagibigNumber(resultSet.getString("pagibig_number"));
                monthlySummaryReport.setPagibigContribution(resultSet.getDouble("pagibig_contrib"));
                monthlySummaryReport.setTinNumber(resultSet.getString("tin_number"));
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
