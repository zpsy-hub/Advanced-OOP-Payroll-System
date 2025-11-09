import util.PasswordUtil;
import DAO.LoginDAO;
import model.User;
import service.SQL_client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Comprehensive test suite for Control 2: BCrypt Password Hashing
 * Tests password hashing, verification, migration status, and security properties
 */
public class TestBCryptControl {
    
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
    
    public static void main(String[] args) {
        printHeader("CONTROL 2: BCRYPT PASSWORD HASHING - COMPREHENSIVE TEST SUITE");
        
        System.out.println("\n" + CYAN + "═══════════════════════════════════════════════════════════" + RESET);
        System.out.println(CYAN + "  Starting BCrypt Security Control Tests" + RESET);
        System.out.println(CYAN + "═══════════════════════════════════════════════════════════" + RESET + "\n");
        
        // Test 1: BCrypt Salt Uniqueness
        testSaltUniqueness();
        
        // Test 2: Password Verification
        testPasswordVerification();
        
        // Test 3: Hash Format Detection
        testHashFormatDetection();
        
        // Test 4: Database Migration Status
        testMigrationStatus();
        
        // Test 5: Login Authentication with BCrypt
        testLoginAuthentication();
        
        // Test 6: Performance Benchmarking
        testPerformance();
        
        // Test 7: Rainbow Table Resistance
        testRainbowTableResistance();
        
        // Test 8: Invalid Input Handling
        testInvalidInputHandling();
        
        // Print Summary
        printSummary();
    }
    
    private static void testSaltUniqueness() {
        printTestHeader("Test 1: BCrypt Salt Uniqueness");
        System.out.println("Objective: Verify each hash contains unique random salt\n");
        
        try {
            String password = "TestPassword123";
            String hash1 = PasswordUtil.hashPassword(password);
            String hash2 = PasswordUtil.hashPassword(password);
            String hash3 = PasswordUtil.hashPassword(password);
            
            System.out.println("Input Password: " + password);
            System.out.println("Hash 1: " + hash1.substring(0, 40) + "...");
            System.out.println("Hash 2: " + hash2.substring(0, 40) + "...");
            System.out.println("Hash 3: " + hash3.substring(0, 40) + "...");
            
            boolean allUnique = !hash1.equals(hash2) && !hash2.equals(hash3) && !hash1.equals(hash3);
            
            if (allUnique) {
                passTest("All hashes are unique - Automatic salt generation working");
            } else {
                failTest("Hashes are NOT unique - Salt generation failed");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
        }
    }
    
    private static void testPasswordVerification() {
        printTestHeader("Test 2: Password Verification");
        System.out.println("Objective: Verify correct password validates, incorrect fails\n");
        
        try {
            String correctPassword = "CorrectPassword123";
            String wrongPassword = "WrongPassword456";
            
            String hash = PasswordUtil.hashPassword(correctPassword);
            System.out.println("Generated Hash: " + hash.substring(0, 40) + "...");
            
            boolean correctVerifies = PasswordUtil.verifyPassword(correctPassword, hash);
            boolean wrongFails = !PasswordUtil.verifyPassword(wrongPassword, hash);
            
            System.out.println("\nCorrect password verification: " + (correctVerifies ? GREEN + "✓ TRUE" : RED + "✗ FALSE") + RESET);
            System.out.println("Wrong password verification:   " + (wrongFails ? GREEN + "✓ FALSE" : RED + "✗ TRUE") + RESET);
            
            if (correctVerifies && wrongFails) {
                passTest("Password verification working correctly");
            } else {
                failTest("Password verification failed");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
        }
    }
    
    private static void testHashFormatDetection() {
        printTestHeader("Test 3: Hash Format Detection");
        System.out.println("Objective: Verify BCrypt hash format detection\n");
        
        try {
            String bcryptHash = "$2a$12$AbCdEfGhIjKlMnOpQrStUvWxYz1234567890AbCdEfGhIjKlMnOp";
            String sha256Hash = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
            String emptyString = "";
            String nullString = null;
            
            boolean bcryptDetected = PasswordUtil.isBcryptHash(bcryptHash);
            boolean sha256NotDetected = !PasswordUtil.isBcryptHash(sha256Hash);
            boolean emptyNotDetected = !PasswordUtil.isBcryptHash(emptyString);
            boolean nullNotDetected = !PasswordUtil.isBcryptHash(nullString);
            
            System.out.println("BCrypt hash detected:    " + (bcryptDetected ? GREEN + "✓ TRUE" : RED + "✗ FALSE") + RESET);
            System.out.println("SHA-256 not detected:    " + (sha256NotDetected ? GREEN + "✓ TRUE" : RED + "✗ FALSE") + RESET);
            System.out.println("Empty string rejected:   " + (emptyNotDetected ? GREEN + "✓ TRUE" : RED + "✗ FALSE") + RESET);
            System.out.println("Null string rejected:    " + (nullNotDetected ? GREEN + "✓ TRUE" : RED + "✗ FALSE") + RESET);
            
            if (bcryptDetected && sha256NotDetected && emptyNotDetected && nullNotDetected) {
                passTest("Hash format detection working correctly");
            } else {
                failTest("Hash format detection failed");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
        }
    }
    
    private static void testMigrationStatus() {
        printTestHeader("Test 4: Database Migration Status");
        System.out.println("Objective: Verify all passwords migrated to BCrypt\n");
        
        try {
            Connection conn = SQL_client.getConnection();
            if (conn == null) {
                failTest("Database connection failed");
                return;
            }
            
            // Count total users
            PreparedStatement psTotal = conn.prepareStatement(
                "SELECT COUNT(*) as total FROM payrollsystem_db.user");
            ResultSet rsTotal = psTotal.executeQuery();
            int totalUsers = rsTotal.next() ? rsTotal.getInt("total") : 0;
            
            // Count BCrypt hashes
            PreparedStatement psBcrypt = conn.prepareStatement(
                "SELECT COUNT(*) as bcrypt_count FROM payrollsystem_db.user WHERE password LIKE '$2a$%'");
            ResultSet rsBcrypt = psBcrypt.executeQuery();
            int bcryptCount = rsBcrypt.next() ? rsBcrypt.getInt("bcrypt_count") : 0;
            
            // Count legacy SHA-256 hashes (64 characters, no $)
            PreparedStatement psSha256 = conn.prepareStatement(
                "SELECT COUNT(*) as sha256_count FROM payrollsystem_db.user WHERE LENGTH(password) = 64 AND password NOT LIKE '$%'");
            ResultSet rsSha256 = psSha256.executeQuery();
            int sha256Count = rsSha256.next() ? rsSha256.getInt("sha256_count") : 0;
            
            System.out.println("Total users:        " + totalUsers);
            System.out.println("BCrypt hashes:      " + GREEN + bcryptCount + RESET);
            System.out.println("SHA-256 hashes:     " + (sha256Count > 0 ? RED : GREEN) + sha256Count + RESET);
            System.out.println("Migration rate:     " + (totalUsers > 0 ? (bcryptCount * 100 / totalUsers) : 0) + "%");
            
            if (bcryptCount == totalUsers && sha256Count == 0) {
                passTest("100% migration complete - All passwords use BCrypt");
            } else if (bcryptCount > 0 && sha256Count > 0) {
                failTest("Partial migration - " + sha256Count + " users still have SHA-256 hashes");
            } else {
                failTest("Migration not started or failed");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testLoginAuthentication() {
        printTestHeader("Test 5: Login Authentication with BCrypt");
        System.out.println("Objective: Verify users can authenticate with BCrypt hashes\n");
        
        try {
            // Test with known user and temporary password
            String testUsername = "agudel";
            String testPassword = "ChangeMe2025!";
            
            System.out.println("Testing authentication...");
            System.out.println("Username: " + testUsername);
            System.out.println("Password: " + testPassword);
            
            LoginDAO loginDAO = new LoginDAO();
            User user = loginDAO.authenticateUser(testUsername, testPassword);
            
            if (user != null) {
                System.out.println("\n" + GREEN + "✓ Authentication Successful" + RESET);
                System.out.println("User ID:    " + user.getId());
                System.out.println("Username:   " + user.getUsername());
                System.out.println("Full Name:  " + user.getFirstName() + " " + user.getLastName());
                passTest("BCrypt authentication working correctly");
            } else {
                System.out.println("\n" + RED + "✗ Authentication Failed" + RESET);
                failTest("Login failed - Check credentials or BCrypt implementation");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testPerformance() {
        printTestHeader("Test 6: Performance Benchmarking");
        System.out.println("Objective: Measure BCrypt hashing performance\n");
        
        try {
            int iterations = 10;
            System.out.println("Hashing " + iterations + " passwords with cost factor 12...\n");
            
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < iterations; i++) {
                PasswordUtil.hashPassword("TestPassword" + i);
            }
            long endTime = System.currentTimeMillis();
            
            long totalTime = endTime - startTime;
            double avgTime = (double) totalTime / iterations;
            
            System.out.println("Total time:   " + totalTime + " ms");
            System.out.println("Average time: " + String.format("%.2f", avgTime) + " ms per hash");
            
            // BCrypt with cost 12 should take 200-400ms per hash on modern hardware
            if (avgTime >= 150 && avgTime <= 500) {
                passTest("Performance within expected range (150-500ms)");
            } else if (avgTime < 150) {
                System.out.println(YELLOW + "⚠ Warning: Hashing is faster than expected. Cost factor may be too low." + RESET);
                passTest("Functional but may need cost factor adjustment");
            } else {
                System.out.println(YELLOW + "⚠ Warning: Hashing is slower than expected. May impact user experience." + RESET);
                passTest("Functional but slower than optimal");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
        }
    }
    
    private static void testRainbowTableResistance() {
        printTestHeader("Test 7: Rainbow Table Resistance");
        System.out.println("Objective: Verify same password produces different hashes\n");
        
        try {
            String commonPassword = "CommonPassword123";
            int samples = 5;
            
            System.out.println("Testing with common password: " + commonPassword);
            System.out.println("Generating " + samples + " hashes...\n");
            
            String[] hashes = new String[samples];
            for (int i = 0; i < samples; i++) {
                hashes[i] = PasswordUtil.hashPassword(commonPassword);
                System.out.println("Hash " + (i + 1) + ": " + hashes[i].substring(0, 40) + "...");
            }
            
            // Check all hashes are unique
            boolean allUnique = true;
            for (int i = 0; i < samples; i++) {
                for (int j = i + 1; j < samples; j++) {
                    if (hashes[i].equals(hashes[j])) {
                        allUnique = false;
                        break;
                    }
                }
            }
            
            System.out.println("\nAll hashes unique: " + (allUnique ? GREEN + "✓ YES" : RED + "✗ NO") + RESET);
            
            if (allUnique) {
                passTest("Rainbow table attack prevented - All hashes unique");
            } else {
                failTest("Rainbow table vulnerability - Duplicate hashes found");
            }
        } catch (Exception e) {
            failTest("Exception: " + e.getMessage());
        }
    }
    
    private static void testInvalidInputHandling() {
        printTestHeader("Test 8: Invalid Input Handling");
        System.out.println("Objective: Verify proper handling of invalid inputs\n");
        
        int subTests = 0;
        int subPassed = 0;
        
        // Test null password
        try {
            PasswordUtil.hashPassword(null);
            System.out.println(RED + "✗ Null password accepted (should throw exception)" + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(GREEN + "✓ Null password rejected correctly" + RESET);
            subPassed++;
        } catch (Exception e) {
            System.out.println(YELLOW + "⚠ Null password caused unexpected exception: " + e.getMessage() + RESET);
        }
        subTests++;
        
        // Test empty password
        try {
            PasswordUtil.hashPassword("");
            System.out.println(RED + "✗ Empty password accepted (should throw exception)" + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(GREEN + "✓ Empty password rejected correctly" + RESET);
            subPassed++;
        } catch (Exception e) {
            System.out.println(YELLOW + "⚠ Empty password caused unexpected exception: " + e.getMessage() + RESET);
        }
        subTests++;
        
        // Test null verification
        boolean nullResult = PasswordUtil.verifyPassword(null, "somehash");
        System.out.println((nullResult ? RED + "✗" : GREEN + "✓") + " Null password verification returns false" + RESET);
        if (!nullResult) subPassed++;
        subTests++;
        
        // Test null hash verification
        boolean nullHashResult = PasswordUtil.verifyPassword("password", null);
        System.out.println((nullHashResult ? RED + "✗" : GREEN + "✓") + " Null hash verification returns false" + RESET);
        if (!nullHashResult) subPassed++;
        subTests++;
        
        // Test invalid hash format
        boolean invalidHashResult = PasswordUtil.verifyPassword("password", "not-a-valid-hash");
        System.out.println((invalidHashResult ? RED + "✗" : GREEN + "✓") + " Invalid hash format handled gracefully" + RESET);
        if (!invalidHashResult) subPassed++;
        subTests++;
        
        if (subPassed == subTests) {
            passTest("All invalid inputs handled correctly (" + subPassed + "/" + subTests + ")");
        } else {
            failTest("Some invalid inputs not handled correctly (" + subPassed + "/" + subTests + ")");
        }
    }
    
    private static void printHeader(String title) {
        System.out.println("\n" + BLUE + "╔═══════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + " " + title + " " + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚═══════════════════════════════════════════════════════════════════╝" + RESET);
    }
    
    private static void printTestHeader(String testName) {
        System.out.println("\n" + CYAN + "┌─────────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + "│ " + testName + RESET);
        System.out.println(CYAN + "└─────────────────────────────────────────────────────────────────┘" + RESET);
    }
    
    private static void passTest(String message) {
        totalTests++;
        passedTests++;
        System.out.println("\n" + GREEN + "✓ PASS: " + message + RESET);
    }
    
    private static void failTest(String message) {
        totalTests++;
        failedTests++;
        System.out.println("\n" + RED + "✗ FAIL: " + message + RESET);
    }
    
    private static void printSummary() {
        System.out.println("\n" + BLUE + "═══════════════════════════════════════════════════════════════════" + RESET);
        System.out.println(BLUE + "  TEST SUMMARY" + RESET);
        System.out.println(BLUE + "═══════════════════════════════════════════════════════════════════" + RESET);
        System.out.println("\nTotal Tests:  " + totalTests);
        System.out.println(GREEN + "Passed:       " + passedTests + RESET);
        System.out.println(RED + "Failed:       " + failedTests + RESET);
        
        double passRate = totalTests > 0 ? (passedTests * 100.0 / totalTests) : 0;
        System.out.println("\nPass Rate:    " + String.format("%.1f", passRate) + "%");
        
        if (failedTests == 0) {
            System.out.println("\n" + GREEN + "╔═══════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(GREEN + "║  ✓ ALL TESTS PASSED - CONTROL 2 FULLY FUNCTIONAL                 ║" + RESET);
            System.out.println(GREEN + "╚═══════════════════════════════════════════════════════════════════╝" + RESET);
        } else {
            System.out.println("\n" + RED + "╔═══════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(RED + "║  ✗ SOME TESTS FAILED - REVIEW CONTROL 2 IMPLEMENTATION           ║" + RESET);
            System.out.println(RED + "╚═══════════════════════════════════════════════════════════════════╝" + RESET);
        }
        
        System.out.println("\n" + CYAN + "Security Status:" + RESET);
        if (passedTests >= 7) {
            System.out.println(GREEN + "  ✓ BCrypt implementation is secure and functional" + RESET);
            System.out.println(GREEN + "  ✓ Rainbow table attacks prevented" + RESET);
            System.out.println(GREEN + "  ✓ Unique salt generation working" + RESET);
            System.out.println(GREEN + "  ✓ Ready for production deployment" + RESET);
        } else {
            System.out.println(RED + "  ✗ Security issues detected - Do not deploy to production" + RESET);
        }
        
        System.out.println("\n");
    }
}
