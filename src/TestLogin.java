import DAO.LoginDAO;
import java.util.Scanner;
import model.User;

/**
 * Simple command-line login test to verify BCrypt password migration.
 * 
 * Usage: java -cp "build/classes;lib/*" TestLogin
 * 
 * After migration, all users can login with password: ChangeMe2025!
 */
public class TestLogin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginDAO loginDAO = new LoginDAO();
        
        System.out.println("============================================================");
        System.out.println("Login Test - BCrypt Password Verification");
        System.out.println("============================================================");
        System.out.println("After migration, use password: ChangeMe2025!\n");
        
        while (true) {
            System.out.print("Username (or 'quit' to exit): ");
            String username = scanner.nextLine().trim();
            
            if (username.equalsIgnoreCase("quit") || username.equalsIgnoreCase("exit")) {
                System.out.println("\nGoodbye!");
                break;
            }
            
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.\n");
                continue;
            }
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            try {
                User user = loginDAO.authenticateUser(username, password);
                
                if (user != null) {
                    System.out.println("\n✓ LOGIN SUCCESSFUL!");
                    System.out.println("  User ID:    " + user.getId());
                    System.out.println("  Username:   " + user.getUsername());
                    System.out.println("  Name:       " + user.getFirstName() + " " + user.getLastName());
                    System.out.println();
                } else {
                    System.out.println("\n✗ LOGIN FAILED");
                    System.out.println("  Invalid username or password.\n");
                }
            } catch (Exception e) {
                System.out.println("\n✗ ERROR: " + e.getMessage());
                System.out.println();
            }
        }
        
        scanner.close();
    }
}
