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

    public CredentialsManagementDAO() {
    }

    public boolean deleteUser(int employeeId) {
        try {
            Connection conn = SQL_client.getInstance().getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return false;
            }

            PreparedStatement ps = conn.prepareStatement("DELETE FROM payroll_system.login_credentials WHERE emp_id = ?");
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
            Connection conn = SQL_client.getInstance().getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return false;
            }

            String hashedPassword = hashPassword(newPassword);

            PreparedStatement ps = conn.prepareStatement("UPDATE payroll_system.login_credentials SET password = ? WHERE emp_id = ?");
            ps.setString(1, hashedPassword);
            ps.setInt(2, employeeId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String password) {
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
    
    public List<String> getAllEmployeeNames() {
        List<String> employeeNames = new ArrayList<>();
        try {
            Connection conn = SQL_client.getInstance().getConnection();
            if (conn == null) {
                System.err.println("Database connection is null.");
                return employeeNames;
            }

            PreparedStatement ps = conn.prepareStatement("SELECT emp_id, employee_lastname, employee_firstname FROM payroll_system.employees");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int empId = rs.getInt("emp_id");
                String lastName = rs.getString("employee_lastname");
                String firstName = rs.getString("employee_firstname");
                String fullName = empId + " - " + lastName + ", " + firstName;
                employeeNames.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeNames;
    }


}
