import DAO.LoginDAO;
import model.User;

/**
 * Quick automated login test (non-interactive).
 */
public class QuickLoginTest {
    public static void main(String[] args) {
        LoginDAO loginDAO = new LoginDAO();
        
        System.out.println("============================================================");
        System.out.println("BCrypt Login Test");
        System.out.println("============================================================\n");
        
        // Test with first username from database
        String testUsername = "agudel";
        String testPassword = "ChangeMe2025!";
        
        System.out.println("Testing login...");
        System.out.println("  Username: " + testUsername);
        System.out.println("  Password: " + testPassword);
        System.out.println();
        
        try {
            User user = loginDAO.authenticateUser(testUsername, testPassword);
            
            if (user != null) {
                System.out.println("✓ LOGIN SUCCESSFUL!");
                System.out.println("  User ID:  " + user.getId());
                System.out.println("  Username: " + user.getUsername());
                System.out.println("  Name:     " + user.getFirstName() + " " + user.getLastName());
                System.out.println("\n✓ BCrypt password verification is working correctly!");
            } else {
                System.out.println("✗ LOGIN FAILED");
                System.out.println("  Authentication returned null");
            }
        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n============================================================");
    }
}
