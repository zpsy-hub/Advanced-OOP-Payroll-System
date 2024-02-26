import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import javax.swing.JTable;

public class GUIPayslip {

	private JFrame payslipScreen;
	private JTable earningsTable;
	private JTable deductionsTable;
	private JTable netpayTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIPayslip window = new GUIPayslip();
					window.payslipScreen.setVisible(true);
					window.payslipScreen.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIPayslip() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		payslipScreen = new JFrame();
		payslipScreen.setTitle("MotorPH Payroll System");
		payslipScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		payslipScreen.setBounds(100, 100, 1315, 770);
		payslipScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payslipScreen.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1301, 733);
		payslipScreen.getContentPane().add(mainPanel);
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
		
		JLabel payslipLabel = new JLabel("Payslip");
		payslipLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		payslipLabel.setBounds(340, 36, 205, 33);
		mainPanel.add(payslipLabel);
		
		JLabel salarydetailsLabel = new JLabel("Salary Details");
		salarydetailsLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		salarydetailsLabel.setBounds(340, 96, 205, 44);
		mainPanel.add(salarydetailsLabel);
		
		JLabel payperiodLabel = new JLabel("Pay Period");
		payperiodLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payperiodLabel.setBounds(340, 138, 98, 33);
		mainPanel.add(payperiodLabel);
		
		JComboBox dateComboBox = new JComboBox();
		dateComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		dateComboBox.setModel(new DefaultComboBoxModel(new String[] {"Date"}));
		dateComboBox.setBounds(449, 138, 250, 30);
		mainPanel.add(dateComboBox);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JPanel earningsPanel = new JPanel();
		earningsPanel.setBackground(new Color(255, 255, 255));
		earningsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		earningsPanel.setBounds(340, 194, 420, 417);
		mainPanel.add(earningsPanel);
		earningsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		earningsTable = new JTable();
		earningsTable.setBorder(null);
		earningsPanel.add(earningsTable);
		
		JPanel deductionsPanel = new JPanel();
		deductionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		deductionsPanel.setBackground(Color.WHITE);
		deductionsPanel.setBounds(843, 194, 420, 417);
		mainPanel.add(deductionsPanel);
		deductionsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		deductionsTable = new JTable();
		deductionsTable.setBorder(null);
		deductionsPanel.add(deductionsTable);
		
		JPanel netpayPanel = new JPanel();
		netpayPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		netpayPanel.setBackground(Color.WHITE);
		netpayPanel.setBounds(340, 637, 420, 75);
		mainPanel.add(netpayPanel);
		netpayPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		netpayTable = new JTable();
		netpayTable.setBorder(null);
		netpayPanel.add(netpayTable);
		
		JButton requestpayslipButton = new JButton("Request Payslip");
		requestpayslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		requestpayslipButton.setBackground(Color.WHITE);
		requestpayslipButton.setBounds(843, 637, 420, 75);
		mainPanel.add(requestpayslipButton);
	}
}
