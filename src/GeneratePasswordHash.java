import util.PasswordUtil;

/**
 * Utility to generate BCrypt password hashes for database migration.
 * 
 * Usage:
 * 1. Run this class from your IDE or command line
 * 2. Copy the generated hash
 * 3. Use in SQL UPDATE statements to set user passwords
 * 
 * Example:
 *   UPDATE payrollsystem_db.user 
 *   SET password = '$2a$12$...' 
 *   WHERE username = 'admin';
 */
public class GeneratePasswordHash {
    
    public static void main(String[] args) {
        // Change these to generate hashes for your users
        String[] testPasswords = {
            "ChangeMe2025!",    // Temporary password for migration
            "admin123",         // Example admin password
            "test123",          // Example test user password
            "employee2025"      // Example employee password
        };
        
        System.out.println("BCrypt Password Hash Generator");
        System.out.println("================================\n");
        
        for (String password : testPasswords) {
            String hash = PasswordUtil.hashPassword(password);
            System.out.println("Password: " + password);
            System.out.println("BCrypt Hash: " + hash);
            System.out.println();
        }
        
        System.out.println("================================");
        System.out.println("Copy the hash and use in SQL:");
        System.out.println("UPDATE payrollsystem_db.user SET password = 'HASH_HERE' WHERE username = 'USERNAME';");
    }
}
