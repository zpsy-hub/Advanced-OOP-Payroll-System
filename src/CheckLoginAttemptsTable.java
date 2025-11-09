import java.sql.*;
import service.SQL_client;

/**
 * Checks if login_attempts table exists and creates it if needed.
 */
public class CheckLoginAttemptsTable {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("Checking login_attempts Table");
        System.out.println("============================================================\n");
        
        try (Connection conn = SQL_client.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Check if login_attempts table exists
            ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) FROM information_schema.tables " +
                "WHERE table_schema = 'payrollsystem_db' AND table_name = 'login_attempts'");
            
            rs.next();
            boolean exists = rs.getInt(1) > 0;
            
            System.out.println("login_attempts table exists: " + exists);
            
            if (!exists) {
                System.out.println("\n❌ Table does NOT exist! Creating it now...\n");
                
                // Create the table
                stmt.execute(
                    "CREATE TABLE payrollsystem_db.login_attempts (" +
                    "  attempt_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "  emp_id INT," +
                    "  username VARCHAR(50)," +
                    "  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "  success BOOLEAN" +
                    ")");
                
                System.out.println("✓ Table created successfully!");
            } else {
                System.out.println("\n✓ Table exists!");
                
                // Show structure
                System.out.println("\nTable structure:");
                ResultSet rs2 = stmt.executeQuery("DESCRIBE payrollsystem_db.login_attempts");
                while (rs2.next()) {
                    System.out.println("  " + rs2.getString("Field") + " - " + rs2.getString("Type"));
                }
                
                // Show recent attempts
                System.out.println("\nRecent login attempts:");
                ResultSet rs3 = stmt.executeQuery(
                    "SELECT * FROM payrollsystem_db.login_attempts ORDER BY timestamp DESC LIMIT 5");
                
                if (!rs3.isBeforeFirst()) {
                    System.out.println("  (no attempts logged yet)");
                } else {
                    while (rs3.next()) {
                        System.out.println("  " + rs3.getTimestamp("timestamp") + " - " +
                            rs3.getString("username") + " - " +
                            (rs3.getBoolean("success") ? "SUCCESS" : "FAILED"));
                    }
                }
            }
            
            System.out.println("\n============================================================");
            System.out.println("You can now try logging in!");
            System.out.println("============================================================");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
