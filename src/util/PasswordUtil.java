package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for secure password hashing using BCrypt.
 * BCrypt automatically handles salt generation and includes it in the hash.
 * 
 * This replaces the insecure SHA-256 (no salt) implementation.
 */
public class PasswordUtil {
    
    // Cost factor for BCrypt (10-12 recommended for good security/performance balance)
    private static final int BCRYPT_ROUNDS = 12;
    
    /**
     * Hash a plaintext password using BCrypt.
     * @param plainPassword the plaintext password
     * @return the bcrypt hash (includes salt)
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
    
    /**
     * Verify a plaintext password against a stored BCrypt hash.
     * @param plainPassword the plaintext password to verify
     * @param hashedPassword the stored BCrypt hash
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Invalid hash format (e.g., old SHA-256 hashes)
            System.err.println("Invalid hash format for verification: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if a hash is in BCrypt format (starts with $2a$, $2b$, or $2y$).
     * Useful for detecting legacy SHA-256 hashes during migration.
     * @param hash the hash to check
     * @return true if BCrypt format, false otherwise
     */
    public static boolean isBcryptHash(String hash) {
        if (hash == null) return false;
        return hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$");
    }
}
