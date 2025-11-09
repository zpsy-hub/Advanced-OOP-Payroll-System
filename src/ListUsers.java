import java.sql.*;
import service.SQL_client;

/**
 * Lists all usernames from the database for testing login.
 */
public class ListUsers {
    public static void main(String[] args) {
        System.out.println("Available Usernames:");
        System.out.println("====================");
        
        try (Connection conn = SQL_client.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT username FROM user ORDER BY username LIMIT 10")) {
            
            while (rs.next()) {
                String username = rs.getString("username");
                System.out.printf("  %s%n", username);
            }
            
            System.out.println("\nAll users can login with password: ChangeMe2025!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
