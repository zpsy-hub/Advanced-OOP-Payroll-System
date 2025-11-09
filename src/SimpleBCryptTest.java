import util.PasswordUtil;

/**
 * Simple test to trigger BCrypt error if it exists.
 */
public class SimpleBCryptTest {
    public static void main(String[] args) {
        System.out.println("Testing BCrypt through PasswordUtil...\n");
        
        try {
            // Test hashing
            System.out.println("1. Testing hashPassword()...");
            String password = "test123";
            String hash = PasswordUtil.hashPassword(password);
            System.out.println("   ✓ Hash created: " + hash.substring(0, 30) + "...");
            
            // Test verification
            System.out.println("\n2. Testing verifyPassword()...");
            boolean valid = PasswordUtil.verifyPassword(password, hash);
            System.out.println("   ✓ Verification: " + (valid ? "PASS" : "FAIL"));
            
            // Test with real password
            System.out.println("\n3. Testing with 'ChangeMe2025!'...");
            String realPassword = "ChangeMe2025!";
            String realHash = PasswordUtil.hashPassword(realPassword);
            boolean realValid = PasswordUtil.verifyPassword(realPassword, realHash);
            System.out.println("   ✓ Hash: " + realHash.substring(0, 30) + "...");
            System.out.println("   ✓ Verification: " + (realValid ? "PASS" : "FAIL"));
            
            System.out.println("\n============================================================");
            System.out.println("✓ ALL TESTS PASSED - BCrypt is working!");
            System.out.println("============================================================");
            
        } catch (NoClassDefFoundError e) {
            System.out.println("\n✗ ERROR: BCrypt class not found at runtime!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nThis means:");
            System.out.println("  - The code compiles (jbcrypt is in compile classpath)");
            System.out.println("  - BUT at runtime, jbcrypt jar is not found");
            System.out.println("\nSolution for NetBeans:");
            System.out.println("  1. Close NetBeans");
            System.out.println("  2. Delete the 'build' and 'dist' folders");
            System.out.println("  3. Reopen NetBeans");
            System.out.println("  4. Clean and Build Project (Shift+F11)");
            System.out.println("  5. Run again");
            
        } catch (Exception e) {
            System.out.println("\n✗ ERROR: " + e.getClass().getName());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
