import java.sql.*;
import service.SQL_client;
import util.PasswordUtil;

/**
 * Simple check to verify the user table and passwords.
 */
public class QuickDBCheck {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("Database User Table Check");
        System.out.println("============================================================\n");
        
        try (Connection conn = SQL_client.getConnection()) {
            
            // Check which database we're connected to
            try (Statement stmt1 = conn.createStatement();
                 ResultSet rs1 = stmt1.executeQuery("SELECT DATABASE()")) {
                if (rs1.next()) {
                    System.out.println("Connected to database: " + rs1.getString(1));
                }
            }
            System.out.println();
            
            // Check payrollsystem_db.user table
            System.out.println("Checking payrollsystem_db.user:");
            try (Statement stmt2 = conn.createStatement();
                 ResultSet rs2 = stmt2.executeQuery(
                     "SELECT username, password FROM payrollsystem_db.user LIMIT 5")) {
                
                int count = 0;
                while (rs2.next()) {
                    count++;
                    String username = rs2.getString("username");
                    String password = rs2.getString("password");
                    boolean isBcrypt = password.matches("^\\$2[aby]\\$\\d{2}\\$.+");
                    boolean matches = isBcrypt && PasswordUtil.verifyPassword("ChangeMe2025!", password);
                    
                    System.out.println(count + ". " + username);
                    System.out.println("   BCrypt: " + isBcrypt + ", Matches 'ChangeMe2025!': " + matches);
                }
                
                if (count == 0) {
                    System.out.println("ERROR: No users found in payrollsystem_db.user!");
                }
            }
            
            System.out.println("\n" + "=".repeat(60));
            
            // Check if there's a different payroll_system database
            System.out.println("\nChecking for payroll_system database...");
            try (Statement stmt3 = conn.createStatement();
                 ResultSet rs3 = stmt3.executeQuery(
                     "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME LIKE '%payroll%'")) {
                
                while (rs3.next()) {
                    String dbName = rs3.getString(1);
                    System.out.println("  Found: " + dbName);
                    
                    // Check if it has a user table
                    try (Statement stmt4 = conn.createStatement();
                         ResultSet rs4 = stmt4.executeQuery(
                             "SELECT COUNT(*) FROM `" + dbName + "`.user")) {
                        if (rs4.next()) {
                            int userCount = rs4.getInt(1);
                            System.out.println("    Has " + userCount + " users in '" + dbName + ".user'");
                            
                            // Sample a password
                            try (Statement stmt5 = conn.createStatement();
                                 ResultSet rs5 = stmt5.executeQuery(
                                     "SELECT username, password FROM `" + dbName + "`.user LIMIT 1")) {
                                if (rs5.next()) {
                                    String password = rs5.getString("password");
                                    boolean isBcrypt = password.matches("^\\$2[aby]\\$\\d{2}\\$.+");
                                    System.out.println("    Sample password is BCrypt: " + isBcrypt);
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("    No user table in " + dbName);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("If BCrypt = true and Matches = true, migration worked!");
        System.out.println("Password should be: ChangeMe2025!");
        System.out.println("=".repeat(60));
    }
}
