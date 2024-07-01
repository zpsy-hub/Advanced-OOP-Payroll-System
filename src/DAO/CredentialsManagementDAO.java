package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean deleteUser(int employeeId) {
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

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(int employeeId, String newPassword) {
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

    public static List<String> getAllEmployeeNames() {
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
}
