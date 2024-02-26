import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class GUIDashboard {

	private JFrame dashboardScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDashboard window = new GUIDashboard();
					window.dashboardScreen.setVisible(true);
					window.dashboardScreen.setLocationRelativeTo(null); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIDashboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dashboardScreen = new JFrame();
		dashboardScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		dashboardScreen.setBackground(new Color(255, 255, 255));
		dashboardScreen.setTitle("MotorPH Payroll System");
		dashboardScreen.setBounds(100, 100, 1315, 770);
		dashboardScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dashboardScreen.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1301, 733);
		dashboardScreen.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(new Color(255, 255, 255));
		sidePanel.setBounds(0, 0, 299, 733);
		mainPanel.add(sidePanel);
		sidePanel.setLayout(null);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 28));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidePanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
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
		
		JLabel dashboardLabel = new JLabel("Dashboard");
		dashboardLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		dashboardLabel.setBounds(340, 36, 205, 33);
		mainPanel.add(dashboardLabel);
		
		JLabel welcomeLabel = new JLabel("Welcome, ");
		welcomeLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		welcomeLabel.setBounds(340, 103, 177, 33);
		mainPanel.add(welcomeLabel);
		
		JPanel employeeinfoPanel = new JPanel();
		employeeinfoPanel.setBackground(new Color(255, 255, 255));
		employeeinfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		employeeinfoPanel.setBounds(340, 146, 923, 242);
		mainPanel.add(employeeinfoPanel);
		employeeinfoPanel.setLayout(null);
		
		JLabel employeeIcon = new JLabel("");
		employeeIcon.setIcon(new ImageIcon("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\Employee Icon.png"));
		employeeIcon.setHorizontalAlignment(SwingConstants.CENTER);
		employeeIcon.setBounds(-21, 10, 204, 222);
		employeeinfoPanel.add(employeeIcon);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		nameLabel.setBounds(158, 22, 45, 13);
		employeeinfoPanel.add(nameLabel);
		
		JLabel employeeName = new JLabel("Sample Name here period");
		employeeName.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		employeeName.setBounds(158, 34, 228, 23);
		employeeinfoPanel.add(employeeName);
		
		JLabel empID = new JLabel("ID here");
		empID.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empID.setBounds(158, 89, 119, 23);
		employeeinfoPanel.add(empID);
		
		JLabel idLabel = new JLabel("Employee ID");
		idLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		idLabel.setBounds(158, 77, 98, 13);
		employeeinfoPanel.add(idLabel);
		
		JLabel empPosition = new JLabel("Sample Position here");
		empPosition.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empPosition.setBounds(158, 134, 228, 23);
		employeeinfoPanel.add(empPosition);
		
		JLabel positionLabel = new JLabel("Position");
		positionLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		positionLabel.setBounds(158, 122, 69, 13);
		employeeinfoPanel.add(positionLabel);
		
		JLabel immediateSupervisor = new JLabel("Sample Name here period");
		immediateSupervisor.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		immediateSupervisor.setBounds(158, 192, 228, 23);
		employeeinfoPanel.add(immediateSupervisor);
		
		JLabel supervisorLabel = new JLabel("Immediate Supervisor");
		supervisorLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		supervisorLabel.setBounds(158, 180, 171, 13);
		employeeinfoPanel.add(supervisorLabel);
		
		JLabel empStatus = new JLabel("Status here");
		empStatus.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empStatus.setBounds(407, 34, 113, 23);
		employeeinfoPanel.add(empStatus);
		
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		statusLabel.setBounds(407, 22, 98, 13);
		employeeinfoPanel.add(statusLabel);
		
		JLabel empBday = new JLabel("February 32, 1876");
		empBday.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empBday.setBounds(407, 89, 160, 23);
		employeeinfoPanel.add(empBday);
		
		JLabel bdayLabel = new JLabel("Birthday");
		bdayLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		bdayLabel.setBounds(407, 77, 98, 13);
		employeeinfoPanel.add(bdayLabel);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		addressLabel.setBounds(407, 180, 98, 13);
		employeeinfoPanel.add(addressLabel);
		
		JLabel empAddress = new JLabel("7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City");
		empAddress.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empAddress.setBounds(407, 192, 506, 23);
		employeeinfoPanel.add(empAddress);
		
		JLabel empTIN = new JLabel("123-002-087-3244");
		empTIN.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empTIN.setBounds(407, 134, 171, 23);
		employeeinfoPanel.add(empTIN);
		
		JLabel tinLabel = new JLabel("TIN");
		tinLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		tinLabel.setBounds(407, 122, 98, 13);
		employeeinfoPanel.add(tinLabel);
		
		JLabel empSSS = new JLabel("12-32352341");
		empSSS.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empSSS.setBounds(615, 34, 171, 23);
		employeeinfoPanel.add(empSSS);
		
		JLabel sssLabel = new JLabel("SSS");
		sssLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		sssLabel.setBounds(615, 22, 98, 13);
		employeeinfoPanel.add(sssLabel);
		
		JLabel empPhilhealth = new JLabel("14-53533543");
		empPhilhealth.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empPhilhealth.setBounds(615, 89, 171, 23);
		employeeinfoPanel.add(empPhilhealth);
		
		JLabel philhealthLabel = new JLabel("Philhealth");
		philhealthLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		philhealthLabel.setBounds(615, 77, 98, 13);
		employeeinfoPanel.add(philhealthLabel);
		
		JLabel empPagibig = new JLabel("14-53533543");
		empPagibig.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empPagibig.setBounds(615, 134, 171, 23);
		employeeinfoPanel.add(empPagibig);
		
		JLabel pagibigLabel = new JLabel("Pag-ibig");
		pagibigLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		pagibigLabel.setBounds(615, 122, 98, 13);
		employeeinfoPanel.add(pagibigLabel);
		
		JPanel announcementsPanel = new JPanel();
		announcementsPanel.setBackground(new Color(30, 55, 101));
		announcementsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		announcementsPanel.setBounds(340, 419, 923, 281);
		mainPanel.add(announcementsPanel);
		announcementsPanel.setLayout(null);
		
		JLabel announcementsLabel = new JLabel("Company Announcements");
		announcementsLabel.setForeground(new Color(255, 255, 255));
		announcementsLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 45));
		announcementsLabel.setBounds(25, 26, 531, 40);
		announcementsPanel.add(announcementsLabel);
		
		JPanel mismongAnnouncementPanel = new JPanel();
		mismongAnnouncementPanel.setBounds(25, 86, 874, 169);
		announcementsPanel.add(mismongAnnouncementPanel);
		mismongAnnouncementPanel.setLayout(null);
		
		JLabel announcementText = new JLabel("Company-wide meeting tomorrow at 10 AM in the conference room. See you there! ");
		announcementText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		announcementText.setHorizontalAlignment(SwingConstants.CENTER);
		announcementText.setBounds(21, 71, 843, 47);
		mismongAnnouncementPanel.add(announcementText);
		
		JLabel reminderText = new JLabel("Reminder:");
		reminderText.setHorizontalAlignment(SwingConstants.CENTER);
		reminderText.setFont(new Font("Tahoma", Font.BOLD, 25));
		reminderText.setBounds(10, 40, 854, 47);
		mismongAnnouncementPanel.add(reminderText);
		
		JLabel teamheadText = new JLabel("- HR Team Head");
		teamheadText.setFont(new Font("Tahoma", Font.BOLD, 15));
		teamheadText.setBounds(692, 128, 125, 13);
		mismongAnnouncementPanel.add(teamheadText);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JLabel employeeFirstName = new JLabel("FirstName here");
		employeeFirstName.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		employeeFirstName.setBounds(512, 103, 323, 33);
		mainPanel.add(employeeFirstName);
		
		
		
		
	}
}
