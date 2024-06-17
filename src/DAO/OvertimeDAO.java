package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Overtime;
import service.SQL_client;

public class OvertimeDAO {
    private Connection connection;
    private static OvertimeDAO instance;

    public OvertimeDAO() {
        this.connection = SQL_client.getInstance().getConnection();
    }

    public static OvertimeDAO getInstance() {
        if (instance == null) {
            instance = new OvertimeDAO();
        }
        return instance;
    }

    public List<Overtime> getOvertimeByEmployeeId(int empid) {
        List<Overtime> overtimes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.overtime WHERE empid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, empid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime(
                    resultSet.getInt("overtimeid"),
                    resultSet.getInt("empid"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("status"),
                    resultSet.getTime("starttime").toLocalTime(),
                    resultSet.getTime("endtime").toLocalTime(),
                    resultSet.getDouble("totalhours"),
                    resultSet.getString("reason"),
                    resultSet.getDate("dateapproved") != null ? resultSet.getDate("dateapproved").toLocalDate() : null
                );
                overtimes.add(overtime);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overtimes;
    }

    public void addOvertime(Overtime overtime) {
        try {
            String sql = "INSERT INTO payroll_system.overtime (empid, date, status, starttime, endtime, totalhours, reason, dateapproved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtime.getEmpId());
            statement.setDate(2, Date.valueOf(overtime.getDate()));
            statement.setString(3, overtime.getStatus());
            statement.setTime(4, Time.valueOf(overtime.getStart()));
            statement.setTime(5, Time.valueOf(overtime.getEnd()));
            statement.setDouble(6, overtime.getTotalHours());
            statement.setString(7, overtime.getReason());
            statement.setDate(8, overtime.getDateApproved() != null ? Date.valueOf(overtime.getDateApproved()) : null);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOvertime(Overtime overtime) {
        try {
            String sql = "UPDATE payroll_system.overtime SET empid = ?, date = ?, status = ?, starttime = ?, endtime = ?, totalhours = ?, reason = ?, dateapproved = ? WHERE overtimeid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtime.getEmpId());
            statement.setDate(2, Date.valueOf(overtime.getDate()));
            statement.setString(3, overtime.getStatus());
            statement.setTime(4, Time.valueOf(overtime.getStart()));
            statement.setTime(5, Time.valueOf(overtime.getEnd()));
            statement.setDouble(6, overtime.getTotalHours());
            statement.setString(7, overtime.getReason());
            statement.setDate(8, overtime.getDateApproved() != null ? Date.valueOf(overtime.getDateApproved()) : null);
            statement.setInt(9, overtime.getOvertimeId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOvertime(int overtimeid) {
        try {
            String sql = "DELETE FROM payroll_system.overtime WHERE overtimeid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, overtimeid);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Overtime> getAllOvertimes() {
        List<Overtime> overtimes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM payroll_system.overtime";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Overtime overtime = new Overtime(
                    resultSet.getInt("overtimeid"),
                    resultSet.getInt("empid"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("status"),
                    resultSet.getTime("starttime").toLocalTime(),
                    resultSet.getTime("endtime").toLocalTime(),
                    resultSet.getDouble("totalhours"),
                    resultSet.getString("reason"),
                    resultSet.getDate("dateapproved") != null ? resultSet.getDate("dateapproved").toLocalDate() : null
                );
                overtimes.add(overtime);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overtimes;
    }

    public void approveOvertime(int overtimeid, LocalDate dateApproved) {
        try {
            String sql = "UPDATE payroll_system.overtime SET status = 'Approved', dateapproved = ? WHERE overtimeid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(dateApproved));
            statement.setInt(2, overtimeid);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Main method for testing
    public static void main(String[] args) {
        OvertimeDAO dao = OvertimeDAO.getInstance();

        // Create a new overtime entry
        Overtime overtime = new Overtime();
        overtime.setEmpId(1); // Example employee ID
        overtime.setDate(LocalDate.now());
        overtime.setStart(LocalTime.of(18, 0)); // 6:00 PM
        overtime.setEnd(LocalTime.of(20, 0)); // 8:00 PM
        overtime.setTotalHours(2.0);
        overtime.setReason("Project deadline");
        overtime.setStatus("Pending");

        // Add the overtime entry
        dao.addOvertime(overtime);

        // Retrieve and print all overtime entries
        List<Overtime> overtimes = dao.getAllOvertimes();
        for (Overtime ot : overtimes) {
            System.out.println(ot);
        }
    }*/
}
