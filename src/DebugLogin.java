import DAO.LoginDAO;
import java.sql.*;
import model.User;
import service.SQL_client;
import util.PasswordUtil;

/**
 * Detailed debugging of the login process step by step.
 */
public class DebugLogin {
    public static void main(String[] args) {
        String username = args.length > 0 ? args[0] : "agudel";
        String password = args.length > 1 ? args[1] : "ChangeMe2025!";
        
        System.out.println("============================================================");
        System.out.println("Login Debug Tool");
        System.out.println("============================================================");
        System.out.println("Testing with:");
        System.out.println("  Username: " + username);
        System.out.println("  Password: " + password);
        System.out.println("============================================================\n");
        
        try (Connection conn = SQL_client.getConnection()) {
            
            // Step 1: Check user exists
            System.out.println("Step 1: Check if user exists in database");
            PreparedStatement ps1 = conn.prepareStatement(
                "SELECT u.user_id, u.username, u.password, e.emp_id, e.last_name, e.first_name " +
                "FROM payrollsystem_db.user u " +
                "JOIN payrollsystem_db.employee e ON u.emp_id = e.emp_id " +
                "WHERE u.username = ?");
            ps1.setString(1, username);
            ResultSet rs1 = ps1.executeQuery();
            
            if (!rs1.next()) {
                System.out.println("  ✗ FAIL: Username '" + username + "' not found!");
                System.out.println("\n  Valid usernames:");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT username FROM payrollsystem_db.user LIMIT 10");
                while (rs.next()) {
                    System.out.println("    - " + rs.getString(1));
                }
                return;
            }
            
            System.out.println("  ✓ User found!");
            int userId = rs1.getInt("user_id");
            int empId = rs1.getInt("emp_id");
            String firstName = rs1.getString("first_name");
            String lastName = rs1.getString("last_name");
            String storedHash = rs1.getString("password");
            
            System.out.println("    User ID: " + userId);
            System.out.println("    Emp ID:  " + empId);
            System.out.println("    Name:    " + firstName + " " + lastName);
            System.out.println("    Hash:    " + storedHash.substring(0, Math.min(50, storedHash.length())) + "...");
            System.out.println();
            
            // Step 2: Check hash format
            System.out.println("Step 2: Verify password hash format");
            boolean isBcrypt = storedHash.matches("^\\$2[aby]\\$\\d{2}\\$.+");
            System.out.println("  Is BCrypt: " + isBcrypt);
            
            if (!isBcrypt) {
                System.out.println("  ✗ FAIL: Hash is not BCrypt format!");
                System.out.println("  Migration may have failed. Run MigratePasswordsToBcrypt again.");
                return;
            }
            System.out.println("  ✓ Hash format correct!");
            System.out.println();
            
            // Step 3: Verify password
            System.out.println("Step 3: Verify password matches");
            boolean passwordMatches = PasswordUtil.verifyPassword(password, storedHash);
            System.out.println("  Password matches: " + passwordMatches);
            
            if (!passwordMatches) {
                System.out.println("  ✗ FAIL: Password does not match!");
                System.out.println("  Expected: 'ChangeMe2025!' (with exclamation mark)");
                System.out.println("  You entered: '" + password + "'");
                System.out.println("  Match check: " + password.equals("ChangeMe2025!"));
                return;
            }
            System.out.println("  ✓ Password verified!");
            System.out.println();
            
            // Step 4: Test LoginDAO
            System.out.println("Step 4: Test LoginDAO.authenticateUser()");
            LoginDAO loginDAO = new LoginDAO();
            User user = loginDAO.authenticateUser(username, password);
            
            if (user == null) {
                System.out.println("  ✗ FAIL: LoginDAO returned null!");
                System.out.println("  Check LoginDAO implementation for bugs.");
                return;
            }
            
            System.out.println("  ✓ LoginDAO authentication successful!");
            System.out.println("    Username: " + user.getUsername());
            System.out.println("    Name:     " + user.getFirstName() + " " + user.getLastName());
            System.out.println();
            
            // Final result
            System.out.println("============================================================");
            System.out.println("✓✓✓ ALL CHECKS PASSED ✓✓✓");
            System.out.println("============================================================");
            System.out.println("Login should work with these credentials:");
            System.out.println("  Username: " + username);
            System.out.println("  Password: " + password);
            System.out.println("\nIf GUI still doesn't work, the problem is in the GUI code,");
            System.out.println("not in the authentication system.");
            System.out.println("============================================================");
            
        } catch (Exception e) {
            System.err.println("\n✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
