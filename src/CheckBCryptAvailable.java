/**
 * Checks if BCrypt library is available at runtime.
 */
public class CheckBCryptAvailable {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("BCrypt Library Check");
        System.out.println("============================================================\n");
        
        // Check if BCrypt class is available
        try {
            Class<?> bcryptClass = Class.forName("org.mindrot.jbcrypt.BCrypt");
            System.out.println("✓ BCrypt class found!");
            System.out.println("  Class: " + bcryptClass.getName());
            System.out.println("  Location: " + bcryptClass.getProtectionDomain().getCodeSource().getLocation());
            System.out.println();
            
            // Try to use BCrypt
            System.out.println("Testing BCrypt functionality...");
            String testPassword = "test123";
            String hash = org.mindrot.jbcrypt.BCrypt.hashpw(testPassword, org.mindrot.jbcrypt.BCrypt.gensalt(12));
            System.out.println("  Generated hash: " + hash.substring(0, 30) + "...");
            
            boolean matches = org.mindrot.jbcrypt.BCrypt.checkpw(testPassword, hash);
            System.out.println("  Verification: " + (matches ? "✓ PASS" : "✗ FAIL"));
            System.out.println();
            
            System.out.println("============================================================");
            System.out.println("✓ BCrypt library is working correctly!");
            System.out.println("============================================================");
            
        } catch (ClassNotFoundException e) {
            System.out.println("✗ ERROR: BCrypt class not found!");
            System.out.println("\nPossible causes:");
            System.out.println("  1. jbcrypt-0.4.jar is not in the classpath");
            System.out.println("  2. jbcrypt-0.4.jar file is missing or corrupted");
            System.out.println("  3. Wrong classpath configuration");
            System.out.println();
            System.out.println("Current classpath:");
            String classpath = System.getProperty("java.class.path");
            for (String path : classpath.split(";")) {
                System.out.println("  " + path);
            }
            System.out.println();
            System.out.println("Solution:");
            System.out.println("  1. Verify lib/jbcrypt-0.4.jar exists");
            System.out.println("  2. Add to classpath: -cp \"build/classes;lib/*\"");
            System.out.println("  3. In NetBeans: Check project.properties has jbcrypt in classpath");
            
        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
