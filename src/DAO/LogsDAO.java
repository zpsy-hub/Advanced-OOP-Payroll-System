package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LoginAttempt;
import service.SQL_client;

public class LogsDAO {
    private static LogsDAO instance = null;

    // Singleton pattern: private constructor
    private LogsDAO() {}

    // Singleton pattern: getInstance method
    public static LogsDAO getInstance() {
        if (instance == null) {
            instance = new LogsDAO();
        }
        return instance;
    }

    // Method to fetch login attempts from the database
    public List<LoginAttempt> getLoginAttempts() {
        List<LoginAttempt> loginAttempts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = SQL_client.getInstance().getConnection();
            String query = "SELECT emp_id, username, timestamp, CASE WHEN success = 1 THEN 'Success' ELSE 'Fail' END AS login_status FROM payrollsystem_db.login_attempts";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int empId = rs.getInt("emp_id");
                String username = rs.getString("username");
                String timestamp = rs.getString("timestamp");
                String loginStatus = rs.getString("login_status");

                LoginAttempt attempt = new LoginAttempt(empId, username, timestamp, loginStatus);
                loginAttempts.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }

        return loginAttempts;
    }

    // Method to close database resources
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
