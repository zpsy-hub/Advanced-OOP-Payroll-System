import util.PasswordUtil;

/**
 * Demonstrates that BCrypt automatically generates unique salts.
 * Even hashing the same password multiple times produces different hashes!
 */
public class DemonstrateBCryptSalting {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("BCrypt Automatic Salting Demonstration");
        System.out.println("=============================================================\n");
        
        String password = "MySecurePassword123!";
        
        System.out.println("Password: " + password);
        System.out.println("\nHashing the SAME password 5 times:\n");
        
        String[] hashes = new String[5];
        for (int i = 0; i < 5; i++) {
            hashes[i] = PasswordUtil.hashPassword(password);
            System.out.println((i+1) + ". " + hashes[i]);
        }
        
        System.out.println("\n=============================================================");
        System.out.println("Notice: ALL 5 hashes are DIFFERENT!");
        System.out.println("=============================================================\n");
        
        // Verify all hashes are unique
        boolean allUnique = true;
        for (int i = 0; i < hashes.length; i++) {
            for (int j = i + 1; j < hashes.length; j++) {
                if (hashes[i].equals(hashes[j])) {
                    allUnique = false;
                }
            }
        }
        System.out.println("All hashes unique: " + allUnique);
        
        // But all still verify correctly
        System.out.println("\nVerifying all 5 hashes against the original password:\n");
        for (int i = 0; i < 5; i++) {
            boolean valid = PasswordUtil.verifyPassword(password, hashes[i]);
            System.out.println((i+1) + ". Verification: " + (valid ? "✓ PASS" : "✗ FAIL"));
        }
        
        System.out.println("\n=============================================================");
        System.out.println("How BCrypt Works:");
        System.out.println("=============================================================");
        System.out.println("Hash format: $2a$[cost]$[22-char salt][31-char hash]");
        System.out.println();
        System.out.println("Example breakdown:");
        String example = hashes[0];
        System.out.println("  Full hash: " + example);
        System.out.println("  Version:   " + example.substring(0, 4) + " (BCrypt algorithm version)");
        System.out.println("  Cost:      " + example.substring(4, 6) + " (2^12 = 4,096 iterations)");
        System.out.println("  Salt:      " + example.substring(7, 29) + " (unique random 22 chars)");
        System.out.println("  Hash:      " + example.substring(29) + " (31 chars)");
        
        System.out.println("\n=============================================================");
        System.out.println("Security Benefits:");
        System.out.println("=============================================================");
        System.out.println("✓ Each password gets a UNIQUE random salt");
        System.out.println("✓ Rainbow table attacks are IMPOSSIBLE");
        System.out.println("✓ Same password hashed twice = different hashes");
        System.out.println("✓ Salt is stored WITH the hash (no separate storage needed)");
        System.out.println("✓ Cost factor can be increased as computers get faster");
        System.out.println("=============================================================");
    }
}
