import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.SQL_client;

public class PayrollPeriodDAO {
    private static PayrollPeriodDAO instance = null;
    private Connection connection;

    private PayrollPeriodDAO() {
        connection = SQL_client.getInstance().getConnection();
    }

    public static PayrollPeriodDAO getInstance() {
        if (instance == null) {
            instance = new PayrollPeriodDAO();
        }
        return instance;
    }

    public List<String> getPayrollPeriodsDropdown() {
        List<String> dropdownItems = new ArrayList<>();
        String sql = "SELECT payroll_period_id, pay_period_start, pay_period_end FROM payroll_periods";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int payrollPeriodId = resultSet.getInt("payroll_period_id");
                String startDate = resultSet.getString("pay_period_start");
                String endDate = resultSet.getString("pay_period_end");
                String dropdownItem = String.format("%d %s %s-%s",
                        payrollPeriodId,
                        getMonthYear(startDate),
                        getDay(startDate),
                        getDay(endDate));
                dropdownItems.add(dropdownItem);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving payroll periods: " + e.getMessage());
        }
        return dropdownItems;
    }

    private String getMonthYear(String date) {
        // Assuming date format is 'YYYY-MM-DD'
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        String monthName = switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "";
        };
        return year + " " + monthName;
    }

    private String getDay(String date) {
        // Assuming date format is 'YYYY-MM-DD'
        String[] parts = date.split("-");
        return parts[2];
    }
}
