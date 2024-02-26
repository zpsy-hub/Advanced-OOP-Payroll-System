import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
<<<<<<< Updated upstream
import javax.swing.JPasswordField;

public class GUIlogin {

	private JFrame loginScreen;
	private JTextField usernameTextField;
	private JTextField passwordtextField;
	private JPasswordField passwordField;
=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserRole;
import service.LoginService;
import util.SessionManager;
import util.UserRepository;
import view.Dashboard;

public class GUIlogin {

	 private JFrame loginScreen;
	    private JTextField usernameTextField;
	    private JTextField passwordTextField;
	    private LoginService loginService;
	    private SessionManager sessionManager;
	    private UserRepository userRepository;
>>>>>>> Stashed changes

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIlogin window = new GUIlogin();
					window.loginScreen.setVisible(true);
					window.loginScreen.setLocationRelativeTo(null); // this method displays the login screen at the center of the screen. slay!
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
        // Initialize the LoginService, UserRepository, and SessionManager
        userRepository = new UserRepository();
        loginService = new LoginService(userRepository);
        sessionManager = new SessionManager(userRepository);
    }

	/**
	 * Initialize the contents of the frame. GUI starts here.
	 */
	private void initialize() {
		loginScreen = new JFrame();
		loginScreen.setBackground(new Color(255, 255, 255));
		loginScreen.setTitle("MotorPH Payroll System");
		loginScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		loginScreen.getContentPane().setBackground(new Color(255, 255, 255));
		loginScreen.setBounds(100, 100, 1315, 770);
		loginScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginScreen.getContentPane().setLayout(null);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 60));
		motorphLabel.setBounds(524, 138, 250, 54);
		loginScreen.getContentPane().add(motorphLabel);
		
		JLabel welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		welcomeLabel.setBounds(534, 202, 240, 33);
		loginScreen.getContentPane().add(welcomeLabel);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		usernameLabel.setBounds(452, 281, 107, 33);
		loginScreen.getContentPane().add(usernameLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setForeground(new Color(30, 55, 101));
		usernameTextField.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		usernameTextField.setBounds(452, 313, 397, 42);
		loginScreen.getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		passwordLabel.setBounds(452, 385, 107, 33);
		loginScreen.getContentPane().add(passwordLabel);
		
		JButton loginButton = new JButton("Log In");
		loginButton.setForeground(new Color(30, 55, 101));
		loginButton.setBackground(new Color(255, 255, 255));
		loginButton.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		loginButton.setBounds(452, 562, 397, 51);
		loginScreen.getContentPane().add(loginButton);
		
		JLabel blobLeft = new JLabel("");
		blobLeft.setIcon(new ImageIcon("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\Login_blobLeft.png"));
		blobLeft.setBounds(-231, -17, 602, 446);
		loginScreen.getContentPane().add(blobLeft);
		
		JLabel blobRight = new JLabel("New label");
		blobRight.setIcon(new ImageIcon("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\Login_blobRight.png"));
		blobRight.setBounds(1018, 289, 397, 516);
		loginScreen.getContentPane().add(blobRight);
		
		JLabel motorphIcon = new JLabel("");
		motorphIcon.setHorizontalAlignment(SwingConstants.CENTER);
		motorphIcon.setIcon(new ImageIcon("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Logo.png"));
		motorphIcon.setBounds(526, 42, 232, 95);
		loginScreen.getContentPane().add(motorphIcon);
		
<<<<<<< Updated upstream
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		passwordField.setBounds(452, 416, 397, 42);
		loginScreen.getContentPane().add(passwordField);
=======
		// ActionListener for Log In button
		loginButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String username = usernameTextField.getText();
		        String password = passwordTextField.getText();

		        // Check login credentials
		        boolean loginSuccess = sessionManager.login(username, password);
		        if (loginSuccess) {
		            // Log successful login attempt
		            sessionManager.logLoginAttempt(username, true);
		            // Open the Dashboard
		            openDashboard();
		        } else {
		            // Log unsuccessful login attempt
		            sessionManager.logLoginAttempt(username, false);
		            // Show error message
		            JOptionPane.showMessageDialog(loginScreen,
		                    "Invalid username or password. Please try again.");
		        }
		    }
		});
>>>>>>> Stashed changes
	}
	
	private void openDashboard() {
        // Create and display the Dashboard window
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);

        // Close the Login window
        loginScreen.dispose();
    }
}