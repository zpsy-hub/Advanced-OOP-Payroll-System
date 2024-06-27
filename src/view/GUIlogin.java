package view;

import customUI.RoundedButtonUI;
import customUI.RoundedTextFieldUI;
import customUI.ImagePanel;
import customUI.RoundedBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.User;
import DAO.LoginDAO;
import util.SessionManager;
import service.SQL_client;
import javax.swing.JPanel;

public class GUIlogin {

    public JFrame loginScreen1;
    private JTextField usernameTextField1;
    private SessionManager sessionManager;
    private customUI.PasswordTextField PasswordTextField;

    /**
     * Launch the application.garman
     */
    public static void main(String[] args) {
        SQL_client sql = SQL_client.getInstance();
        sql.getConnection();              
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set FlatLaf IntelliJ theme look and feel
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());

                    GUIlogin window = new GUIlogin();
                    window.loginScreen1.setVisible(true);
                    window.loginScreen1.setLocationRelativeTo(null); 
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUIlogin() {
        initialize();
        LoginDAO userRepository = new LoginDAO();
        sessionManager = new SessionManager(userRepository);
    }

    // Initialize the contents of the frame. GUI starts here.
    private void initialize() {
    	FlatIntelliJLaf.setup();
    	
        loginScreen1 = new JFrame();
        loginScreen1.setBackground(new Color(255, 255, 255));
        loginScreen1.setTitle("MotorPH Payroll System");
        loginScreen1.setIconImage(Toolkit.getDefaultToolkit().getImage(GUIlogin.class.getResource("/img/logo.png")));
        loginScreen1.getContentPane().setBackground(new Color(255, 255, 255));
        loginScreen1.setBounds(100, 100, 1280, 800);
        loginScreen1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginScreen1.getContentPane().setLayout(null);
         
        // Create ImagePanel with the path to your background image
        ImagePanel loginbgpanel = new ImagePanel("/img/login.png");  
        loginbgpanel.setBounds(0, 0, 1280, 800);
        loginScreen1.getContentPane().add(loginbgpanel);
        loginbgpanel.setLayout(null);
        
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setBounds(796, 185, 240, 33);
        loginbgpanel.add(welcomeLabel);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Montserrat Medium", Font.PLAIN, 28));
        
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(768, 516, 301, 51);
        loginbgpanel.add(loginButton);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(30, 55, 101));
        loginButton.setFont(new Font("Montserrat SemiBold", Font.BOLD, 25));
        loginButton.setUI(new RoundedButtonUI());
        
        usernameTextField1 = new JTextField();
        usernameTextField1.setBounds(734, 300, 360, 54);
        loginbgpanel.add(usernameTextField1);
        usernameTextField1.setForeground(new Color(30, 55, 101));
        usernameTextField1.setFont(new Font("Montserrat Medium", Font.BOLD, 20));
        usernameTextField1.setUI(new RoundedTextFieldUI());
        usernameTextField1.setBorder(new RoundedBorder(15));
        usernameTextField1.setColumns(10);
        
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(734, 268, 151, 33);
        loginbgpanel.add(usernameLabel);
        usernameLabel.setFont(new Font("Montserrat", Font.PLAIN, 18));
        
        PasswordTextField = new customUI.PasswordTextField();
        PasswordTextField.setBounds(734, 407, 360, 54);
        PasswordTextField.setFont(new Font("Montserrat Medium", Font.BOLD, 20));
        PasswordTextField.setForeground(new Color(30, 55, 101));
        PasswordTextField.setBorder(new RoundedBorder(15));
        loginbgpanel.add(PasswordTextField);


        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(734, 376, 151, 33);
        loginbgpanel.add(passwordLabel);
        passwordLabel.setFont(new Font("Montserrat", Font.PLAIN, 18));

        // ActionListener for Log Igarn button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField1.getText();
                String password = new String(PasswordTextField.getPassword());

                // Check if username or password is empty
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginScreen1,
                            "Please enter both username and password.");
                    return;
                }

                // Get the SQL connection
                Connection conn = SQL_client.getInstance().getConnection();

                // Call the login method with the Connection parameter
                boolean loginSuccess = sessionManager.login(username, password, conn);
                
                if (loginSuccess) {
                    // Retrieve the logged-in user
                    User loggedInUser = SessionManager.getLoggedInUser();

                    // Open the Dashboard with the logged-in user
                    openDashboard(loggedInUser);
                } else {
                    // Show error message for incorrect username or password
                    JOptionPane.showMessageDialog(loginScreen1,
                            "Incorrect username or password. Please try again.");
                }
            }
        });
        
        // Set the default button for the JFrame's root pane
        loginScreen1.getRootPane().setDefaultButton(loginButton);
    }
    
    


    private void openDashboard(User loggedInEmployee) {
        GUIDashboard dashboard = new GUIDashboard(loggedInEmployee);
        JFrame dashboardScreen = dashboard.getDashboardScreen();
        dashboardScreen.setLocationRelativeTo(null); // Center the frame
        dashboardScreen.setVisible(true); // Make the frame visible
        loginScreen1.dispose(); // Close the login screen
    }
}
