import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class GUITimeInOut {

	private JFrame timeinoutScreen;
	private JTable timeTable;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITimeInOut window = new GUITimeInOut();
					window.timeinoutScreen.setVisible(true);
					window.timeinoutScreen.setLocationRelativeTo(null); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUITimeInOut() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		timeinoutScreen = new JFrame();
		timeinoutScreen.setBackground(new Color(255, 255, 255));
		timeinoutScreen.setTitle("MotorPH Payroll System");
		timeinoutScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		timeinoutScreen.setBounds(100, 100, 1315, 770);
		timeinoutScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timeinoutScreen.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1301, 733);
		timeinoutScreen.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(new Color(255, 255, 255));
		sidePanel.setBounds(0, 0, 299, 733);
		mainPanel.add(sidePanel);
		sidePanel.setLayout(null);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidePanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dashboardButton.setBackground(new Color(255, 255, 255));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidePanel.add(payslipButton);
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidePanel.add(leaverequestButton);
		
		JButton helpButton = new JButton("Help & Support");
		helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		helpButton.setBackground(Color.WHITE);
		helpButton.setBounds(37, 669, 227, 31);
		sidePanel.add(helpButton);
		
		JLabel timeinoutLabel = new JLabel("Time In/Out");
		timeinoutLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		timeinoutLabel.setBounds(340, 36, 205, 33);
		mainPanel.add(timeinoutLabel);
		
		JPanel timeinoutPanel = new JPanel();
		timeinoutPanel.setBackground(new Color(255, 255, 255));
		timeinoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		timeinoutPanel.setBounds(340, 79, 923, 226);
		mainPanel.add(timeinoutPanel);
		timeinoutPanel.setLayout(null);
		
		JButton timeInButton = new JButton("TIME IN");
		timeInButton.setBackground(new Color(255, 255, 255));
		timeInButton.setFont(new Font("Tahoma", Font.BOLD, 35));
		timeInButton.setBounds(42, 55, 239, 102);
		timeinoutPanel.add(timeInButton);
		
		JButton timeOutButton = new JButton("TIME OUT");
		timeOutButton.setFont(new Font("Tahoma", Font.BOLD, 35));
		timeOutButton.setBackground(Color.WHITE);
		timeOutButton.setBounds(640, 55, 239, 102);
		timeinoutPanel.add(timeOutButton);
		
		JLabel currentstatusLabel = new JLabel("Current Status:");
		currentstatusLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		currentstatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentstatusLabel.setBounds(291, 69, 339, 39);
		timeinoutPanel.add(currentstatusLabel);
		
		JLabel empStatus = new JLabel("OUT"); // sample only
		empStatus.setForeground(new Color(255, 0, 0));
		empStatus.setHorizontalAlignment(SwingConstants.CENTER);
		empStatus.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
		empStatus.setBounds(291, 102, 339, 39);
		timeinoutPanel.add(empStatus);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JPanel timePanel = new JPanel();
		timePanel.setBounds(340, 338, 923, 371);
		mainPanel.add(timePanel);
		timePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		timeTable = new JTable();
		timeTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		timeTable.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		timeTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		timePanel.add(timeTable);
		
		
	}
}
