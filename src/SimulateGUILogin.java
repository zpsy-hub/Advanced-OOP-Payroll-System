import DAO.LoginDAO;
import java.sql.Connection;
import model.User;
import service.SQL_client;
import util.SessionManager;

/**
 * Simulates exactly what the GUI login does to debug the issue.
 */
public class SimulateGUILogin {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("Simulating GUI Login Process");
        System.out.println("============================================================\n");
        
        // Test credentials
        String username = "agudel";
        String password = "ChangeMe2025!";
        
        System.out.println("Step 1: Create LoginDAO and SessionManager (like GUI does)");
        LoginDAO userRepository = new LoginDAO();
        SessionManager sessionManager = new SessionManager(userRepository);
        System.out.println("  ✓ Created\n");
        
        System.out.println("Step 2: Get SQL connection");
        Connection conn = SQL_client.getInstance().getConnection();
        System.out.println("  ✓ Connection: " + (conn != null ? "OK" : "FAIL") + "\n");
        
        System.out.println("Step 3: Call sessionManager.login() with:");
        System.out.println("  Username: " + username);
        System.out.println("  Password: " + password);
        System.out.println();
        
        // This is exactly what the GUI does
        boolean loginSuccess = sessionManager.login(username, password, conn);
        
        System.out.println("Step 4: Check result");
        System.out.println("  Login success: " + loginSuccess);
        System.out.println();
        
        if (loginSuccess) {
            User loggedInUser = SessionManager.getLoggedInUser();
            System.out.println("✓ LOGIN SUCCESSFUL!");
            System.out.println("  User ID:  " + loggedInUser.getId());
            System.out.println("  Username: " + loggedInUser.getUsername());
            System.out.println("  Name:     " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        } else {
            System.out.println("✗ LOGIN FAILED");
            System.out.println("\nDirect DAO test:");
            User user = userRepository.authenticateUser(username, password);
            if (user != null) {
                System.out.println("  BUT direct DAO call worked! SessionManager has a bug.");
            } else {
                System.out.println("  Direct DAO call also failed. Password issue?");
            }
        }
        
        System.out.println("\n============================================================");
        System.out.println("Try logging into the GUI with:");
        System.out.println("  Username: agudel (or any username from ListUsers)");
        System.out.println("  Password: ChangeMe2025!");
        System.out.println("============================================================");
    }
}
