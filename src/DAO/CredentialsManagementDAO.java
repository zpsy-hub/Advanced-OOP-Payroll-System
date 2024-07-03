package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import service.SQL_client;

public class CredentialsManagementDAO {
    private static final String HASH_ALGORITHM = "SHA-256";
    private SQL_client sqlClient;

    public CredentialsManagementDAO(SQL_client sqlClient) {
        this.sqlClient = sqlClient;
    }

    public CredentialsManagementDAO() {
    }

    public boolean deleteUser(int employeeId, int changedBy) {
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return false;
            }

            PreparedStatement ps = conn.prepareStatement("DELETE FROM payrollsystem_db.user WHERE emp_id = ?");
            ps.setInt(1, employeeId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logChange(employeeId, "delete", changedBy);
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(int employeeId, String newPassword, int changedBy) {
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return false;
            }

            String hashedPassword = hashPassword(newPassword);

            PreparedStatement ps = conn.prepareStatement("UPDATE payrollsystem_db.user SET password = ? WHERE emp_id = ?");
            ps.setString(1, hashedPassword);
            ps.setInt(2, employeeId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logChange(employeeId, "update_password", changedBy);
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addUser(Employee employee, String username, String password, int changedBy) {
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return false;
            }

            String hashedPassword = hashPassword(password);

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO payrollsystem_db.user (emp_id, username, password) VALUES (?, ?, ?)");
            ps.setInt(1, employee.getEmpId());
            ps.setString(2, username);
            ps.setString(3, hashedPassword);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logChange(employee.getEmpId(), "add_user", changedBy);
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] digest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getEmployeeById(int empId) {
        Employee employee = null;
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return employee;
            }

            // SQL query with joins
            String sql = "SELECT e.emp_id, e.first_name, e.last_name, p.position_name, d.dept_name " +
                         "FROM payrollsystem_db.employee e " +
                         "JOIN payrollsystem_db.position p ON e.position_id = p.position_id " +
                         "JOIN payrollsystem_db.department d ON e.dept_id = d.dept_id " +
                         "WHERE e.emp_id = ?";
                         
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("emp_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String position = rs.getString("position_name");
                String department = rs.getString("dept_name");

                employee = new Employee(id, firstName, lastName, position, department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<String> getAllEmployeeNames() {
        List<String> employeeNames = new ArrayList<>();
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return employeeNames;
            }

            PreparedStatement ps = conn.prepareStatement("SELECT emp_id, last_name, first_name FROM payrollsystem_db.employee");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int empId = rs.getInt("emp_id");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                String fullName = empId + " - " + lastName + ", " + firstName;
                employeeNames.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeNames;
    }

    public List<String> getCredentialChangesLogs() {
        List<String> logs = new ArrayList<>();
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return logs;
            }

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM payrollsystem_db.credential_changes_log");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int empId = rs.getInt("emp_id");
                String action = rs.getString("action");
                int changedBy = rs.getInt("changed_by");
                Timestamp timestamp = rs.getTimestamp("change_timestamp");

                logs.add(String.format("Emp ID: %d, Action: %s, Changed By: %d, Timestamp: %s", empId, action, changedBy, timestamp.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    private void logChange(int empId, String action, int changedBy) {
        try {
            SQL_client.getInstance();
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return;
            }

            PreparedStatement ps = conn.prepareStatement("INSERT INTO payrollsystem_db.credential_changes_log (emp_id, action, changed_by) VALUES (?, ?, ?)");
            ps.setInt(1, empId);
            ps.setString(2, action);
            ps.setInt(3, changedBy);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
