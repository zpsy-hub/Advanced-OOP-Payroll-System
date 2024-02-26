package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import util.SessionManager;
import model.User;
import util.UserRepository;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private SessionManager sessionManager;
    private JLabel lbl_EmpFirstName;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard frame = new Dashboard();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Dashboard() {
        // Initialize UserRepository
        UserRepository userRepository = new UserRepository();

        // Initialize SessionManager with UserRepository
        sessionManager = new SessionManager(userRepository);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1315, 770);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblWelcome = new JLabel("Welcome,");
        lblWelcome.setFont(new Font("Montserrat Black", Font.PLAIN, 28));
        lblWelcome.setBounds(196, 27, 166, 35);
        contentPane.add(lblWelcome);
        
        lbl_EmpFirstName = new JLabel("<EmpFirstName>");
        lbl_EmpFirstName.setFont(new Font("Montserrat Black", Font.PLAIN, 28));
        lbl_EmpFirstName.setBounds(361, 27, 354, 35);
        contentPane.add(lbl_EmpFirstName);
        
        updateLoggedInUser(); // Update <EmpFirstName> label with logged-in user's first name
    }
    
 // Method to update <EmpFirstName> label with logged-in user's first name
    private void updateLoggedInUser() {
        if (sessionManager.isLoggedIn()) {
            User loggedInUser = sessionManager.getLoggedInUser();
            if (loggedInUser != null) {
                String firstName = loggedInUser.getFirstName();
                lbl_EmpFirstName.setText(firstName);
            } else {
                lbl_EmpFirstName.setText("Unknown"); // Set default text if user information is not available
            }
        } else {
            lbl_EmpFirstName.setText("Not Logged In"); // Set default text if no user is logged in
        }
    }

}
