import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;

import model.User;

import java.awt.GridLayout;
import javax.swing.JTable;
import java.awt.Cursor;

public class GUIPayslip {

	private JFrame payslipScreen;
	private JTable earningsTable;
	private JTable deductionsTable;
	private JTable netpayTable;
	private static User loggedInEmployee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIPayslip window = new GUIPayslip(loggedInEmployee);
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
	public GUIPayslip(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
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
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dashboardButton.setBackground(new Color(255, 255, 255));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		
		dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDashboard(loggedInEmployee);
                payslipScreen.dispose(); // Optionally dispose the current window
            }

            // Define the openDashboard method here within the ActionListener class
            private void openDashboard(User loggedInEmployee) {
                // Create an instance of GUIDashboard with the logged-in employee
                GUIDashboard dashboard = new GUIDashboard(loggedInEmployee);

                // Make the dashboard window visible
                dashboard.getDashboardScreen().setVisible(true);
            }
        });
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		
		// Define action listener for the timeInOutButton
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Open GUITimeInOut with the logged-in employee
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();

		        // Close the current dashboard window after
		        if (payslipScreen != null) {
		        	payslipScreen.dispose();
		        }
		    }
		});
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setEnabled(false);
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidePanel.add(payslipButton);
		
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        openPayslip(loggedInEmployee);
		        payslipScreen.dispose(); // Optionally dispose the current window
		    }

		    // Define the openPayslip method here within the ActionListener class
		    private void openPayslip(User loggedInEmployee) {
		        // Create an instance of GUIPayslip with the loggedInEmployee
		        GUIPayslip payslip = new GUIPayslip(loggedInEmployee);

		        // Make the payslip window visible
		        payslip.openWindow();
		    }
		});
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidePanel.add(leaverequestButton);
		
		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
					openLeaveRequest(loggedInEmployee);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        payslipScreen.dispose(); // Optionally dispose the current window
		    }

		    // Define the openLeaveRequest method here within the ActionListener class
		    private void openLeaveRequest(User loggedInEmployee) throws IOException {
		        // Create an instance of GUILeaveRequest with the loggedInEmployee
		        GUILeaveRequest leaveRequest = new GUILeaveRequest(loggedInEmployee);

		        // Make the leave request window visible
		        leaveRequest.openWindow();
		    }
		});
		
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
		
		JLabel employeeNameLabel = new JLabel("");
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		employeeNameLabel.setBounds(750, 36, 400, 33);
		mainPanel.add(employeeNameLabel);
		
		// Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
	}

	public void openWindow() {
	    payslipScreen.setVisible(true);
	}

}
