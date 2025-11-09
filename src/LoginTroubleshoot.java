import DAO.LoginDAO;
import java.util.Scanner;
import model.User;

/**
 * Interactive login troubleshooter - helps debug login issues.
 */
public class LoginTroubleshoot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginDAO loginDAO = new LoginDAO();
        
        System.out.println("============================================================");
        System.out.println("Login Troubleshooter");
        System.out.println("============================================================");
        System.out.println();
        System.out.println("After BCrypt migration, the password for ALL users is:");
        System.out.println("  ChangeMe2025!");
        System.out.println();
        System.out.println("Note: Password is case-sensitive and includes the '!' at the end");
        System.out.println("============================================================");
        System.out.println();
        
        System.out.print("Enter username to test: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.println("\nAttempting login...");
        System.out.println("  Username entered: '" + username + "'");
        System.out.println("  Password entered: '" + password + "'");
        System.out.println("  Expected password: 'ChangeMe2025!'");
        System.out.println("  Password match: " + password.equals("ChangeMe2025!"));
        System.out.println();
        
        try {
            User user = loginDAO.authenticateUser(username, password);
            
            if (user != null) {
                System.out.println("✓ LOGIN SUCCESSFUL!");
                System.out.println("  User ID:  " + user.getId());
                System.out.println("  Username: " + user.getUsername());
                System.out.println("  Name:     " + user.getFirstName() + " " + user.getLastName());
            } else {
                System.out.println("✗ LOGIN FAILED");
                System.out.println("\nPossible reasons:");
                System.out.println("  1. Username does not exist");
                System.out.println("  2. Password is incorrect (remember: ChangeMe2025! with exclamation)");
                System.out.println("  3. Database connection issue");
                System.out.println("  4. Migration did not complete");
            }
        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
        
        System.out.println("\n============================================================");
        System.out.println("Valid usernames: agudel, alvrod, atiros, baumar, casjoh, etc.");
        System.out.println("Password for all: ChangeMe2025!");
        System.out.println("============================================================");
    }
}
