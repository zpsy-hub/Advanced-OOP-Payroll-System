import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import service.SQL_client;
import util.PasswordUtil;

/**
 * Migrates existing SHA-256 passwords to BCrypt.
 * Run this once to migrate all user passwords in the database.
 */
public class MigratePasswordsToBcrypt {
    
    // Temporary password for all users after migration
    private static final String TEMP_PASSWORD = "ChangeMe2025!";
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Password Migration: SHA-256 → BCrypt");
        System.out.println("=".repeat(60));
        
        Connection conn = SQL_client.getConnection();
        if (conn == null) {
            System.err.println("ERROR: Cannot connect to database!");
            System.err.println("Make sure MySQL is running and config.properties is correct.");
            return;
        }
        
        try {
            // Step 1: Backup user table
            System.out.println("\n[1/4] Creating backup table...");
            createBackup(conn);
            System.out.println("✓ Backup created: user_backup_sha256");
            
            // Step 2: Count users
            System.out.println("\n[2/4] Counting users...");
            int userCount = countUsers(conn);
            System.out.println("✓ Found " + userCount + " users to migrate");
            
            // Step 3: Generate BCrypt hash for temporary password
            System.out.println("\n[3/4] Generating BCrypt hash for temporary password...");
            String bcryptHash = PasswordUtil.hashPassword(TEMP_PASSWORD);
            System.out.println("✓ Temporary password: " + TEMP_PASSWORD);
            System.out.println("✓ BCrypt hash: " + bcryptHash.substring(0, 20) + "...");
            
            // Step 4: Update all passwords
            System.out.println("\n[4/4] Updating passwords to BCrypt...");
            int updated = updateAllPasswords(conn, bcryptHash);
            System.out.println("✓ Updated " + updated + " passwords");
            
            // Step 5: Verify migration
            System.out.println("\n[5/5] Verifying migration...");
            verifyMigration(conn);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("✓ MIGRATION COMPLETE!");
            System.out.println("=".repeat(60));
            System.out.println("\nAll users can now login with:");
            System.out.println("  Password: " + TEMP_PASSWORD);
            System.out.println("\nNext steps:");
            System.out.println("  1. Test login with any username and password: " + TEMP_PASSWORD);
            System.out.println("  2. Implement forced password change on first login");
            System.out.println("  3. Backup table 'user_backup_sha256' contains old passwords");
            
        } catch (SQLException e) {
            System.err.println("\nERROR during migration:");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void createBackup(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        
        // Drop existing backup if present
        stmt.execute("DROP TABLE IF EXISTS payrollsystem_db.user_backup_sha256");
        
        // Create backup
        stmt.execute("CREATE TABLE payrollsystem_db.user_backup_sha256 AS SELECT * FROM payrollsystem_db.user");
        
        stmt.close();
    }
    
    private static int countUsers(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM payrollsystem_db.user");
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();
        return count;
    }
    
    private static int updateAllPasswords(Connection conn, String bcryptHash) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE payrollsystem_db.user SET password = ?"
        );
        ps.setString(1, bcryptHash);
        int updated = ps.executeUpdate();
        ps.close();
        return updated;
    }
    
    private static void verifyMigration(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT " +
            "  COUNT(*) AS total_users, " +
            "  SUM(CASE WHEN password LIKE '$2a$%' OR password LIKE '$2b$%' OR password LIKE '$2y$%' " +
            "      THEN 1 ELSE 0 END) AS bcrypt_hashes, " +
            "  SUM(CASE WHEN LENGTH(password) = 64 AND password NOT LIKE '$%' " +
            "      THEN 1 ELSE 0 END) AS sha256_hashes " +
            "FROM payrollsystem_db.user"
        );
        
        if (rs.next()) {
            int total = rs.getInt("total_users");
            int bcrypt = rs.getInt("bcrypt_hashes");
            int sha256 = rs.getInt("sha256_hashes");
            
            System.out.println("  Total users:     " + total);
            System.out.println("  BCrypt hashes:   " + bcrypt);
            System.out.println("  SHA-256 hashes:  " + sha256);
            
            if (bcrypt == total) {
                System.out.println("  ✓ All passwords successfully migrated to BCrypt!");
            } else {
                System.out.println("  ⚠ WARNING: Some passwords may not have migrated correctly!");
            }
        }
        
        rs.close();
        stmt.close();
    }
}
