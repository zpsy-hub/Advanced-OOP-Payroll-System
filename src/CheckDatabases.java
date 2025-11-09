import java.sql.*;
import service.SQL_client;

/**
 * Checks all databases and user tables to find where passwords are stored.
 */
public class CheckDatabases {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("Database and Table Check");
        System.out.println("============================================================\n");
        
        try (Connection conn = SQL_client.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Check current database
            ResultSet rs1 = stmt.executeQuery("SELECT DATABASE()");
            if (rs1.next()) {
                System.out.println("Current database: " + rs1.getString(1));
            }
            System.out.println();
            
            // Check all databases
            System.out.println("All databases:");
            ResultSet rs2 = stmt.executeQuery("SHOW DATABASES");
            while (rs2.next()) {
                String dbName = rs2.getString(1);
                System.out.println("  - " + dbName);
                
                // Check if it has a 'user' table
                try {
                    ResultSet rs3 = stmt.executeQuery(
                        "SELECT COUNT(*) FROM information_schema.tables " +
                        "WHERE table_schema = '" + dbName + "' AND table_name = 'user'");
                    if (rs3.next() && rs3.getInt(1) > 0) {
                        System.out.println("    ✓ Has 'user' table!");
                        
                        // Count users in this table
                        ResultSet rs4 = stmt.executeQuery("SELECT COUNT(*) FROM `" + dbName + "`.user");
                        if (rs4.next()) {
                            System.out.println("    User count: " + rs4.getInt(1));
                        }
                    }
                } catch (SQLException e) {
                    // Ignore databases we can't access
                }
            }
            
            System.out.println("\n============================================================");
            System.out.println("Checking payrollsystem_db.user table:");
            System.out.println("============================================================\n");
            
            // Sample from payrollsystem_db.user
            ResultSet rs5 = stmt.executeQuery(
                "SELECT username, password, emp_id FROM payrollsystem_db.user LIMIT 3");
            
            while (rs5.next()) {
                String username = rs5.getString("username");
                String password = rs5.getString("password");
                int empId = rs5.getInt("emp_id");
                boolean isBcrypt = password.matches("^\\$2[aby]\\$\\d{2}\\$.+");
                
                System.out.println("Username: " + username);
                System.out.println("  emp_id: " + empId);
                System.out.println("  Hash:   " + password.substring(0, Math.min(50, password.length())) + "...");
                System.out.println("  BCrypt: " + isBcrypt);
                System.out.println();
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
