import java.sql.*;
import service.SQL_client;
import util.PasswordUtil;

/**
 * Checks what passwords are stored in the database and tests BCrypt verification.
 */
public class CheckPassword {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("Database Password Check");
        System.out.println("============================================================\n");
        
        String testPassword = "ChangeMe2025!";
        
        try (Connection conn = SQL_client.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT username, password FROM user ORDER BY username LIMIT 5")) {
            
            System.out.println("Sample passwords from database:\n");
            
            while (rs.next()) {
                String username = rs.getString("username");
                String storedHash = rs.getString("password");
                
                // Check if it's a BCrypt hash (starts with $2a$, $2b$, or $2y$)
                boolean isBcrypt = storedHash != null && storedHash.matches("^\\$2[aby]\\$\\d{2}\\$.+");
                
                System.out.println("Username: " + username);
                System.out.println("  Hash:      " + storedHash.substring(0, Math.min(60, storedHash.length())) + "...");
                System.out.println("  Is BCrypt: " + isBcrypt);
                
                if (isBcrypt) {
                    // Test password verification
                    boolean matches = PasswordUtil.verifyPassword(testPassword, storedHash);
                    System.out.println("  Password 'ChangeMe2025!' matches: " + matches);
                } else {
                    System.out.println("  WARNING: This is NOT a BCrypt hash!");
                }
                System.out.println();
            }
            
            System.out.println("============================================================");
            System.out.println("If 'Is BCrypt' shows false, the migration didn't work!");
            System.out.println("All users should have BCrypt hashes and match 'ChangeMe2025!'");
            System.out.println("============================================================");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
