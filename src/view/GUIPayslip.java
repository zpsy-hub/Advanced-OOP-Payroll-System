package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import model.User;
import util.SessionManager;
import javax.swing.JTextField;

public class GUIPayslip {

	JFrame payslipScreen;
	private static User loggedInEmployee;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
    private JComboBox<String> monthComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
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
        GUIPayslip.loggedInEmployee = loggedInEmployee;
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
		payslipLabel.setBounds(340, 22, 205, 33);
		mainPanel.add(payslipLabel);
		
		JLabel payperiodLabel = new JLabel("Pay Period");
		payperiodLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payperiodLabel.setBounds(508, 22, 98, 33);
		mainPanel.add(payperiodLabel);
		
		monthComboBox = new JComboBox<>();
		monthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"Date"}));
		monthComboBox.setBounds(617, 22, 250, 30);
		mainPanel.add(monthComboBox);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				payslipScreen.dispose();
			}
		});
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JLabel employeeNameLabel = new JLabel("");
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		employeeNameLabel.setBounds(750, 36, 400, 33);
		mainPanel.add(employeeNameLabel);
		
		JPanel separator_1 = new JPanel();
		separator_1.setBackground(new Color(30, 55, 101));
		separator_1.setBounds(404, 239, 243, 4);
		mainPanel.add(separator_1);
		
		JLabel lblEarnings = new JLabel("Earnings");
		lblEarnings.setForeground(new Color(30, 55, 101));
		lblEarnings.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblEarnings.setBounds(338, 228, 85, 24);
		mainPanel.add(lblEarnings);
		
		textField = new JTextField();
		textField.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(421, 202, 226, 19);
		mainPanel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(421, 171, 226, 19);
		mainPanel.add(textField_1);
		
		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setForeground(new Color(30, 55, 101));
		lblEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEmployeeName.setBounds(338, 174, 85, 13);
		mainPanel.add(lblEmployeeName);
		
		JLabel lblEmployeeId = new JLabel("Employee ID");
		lblEmployeeId.setForeground(new Color(30, 55, 101));
		lblEmployeeId.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEmployeeId.setBounds(338, 146, 67, 13);
		mainPanel.add(lblEmployeeId);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(404, 140, 76, 19);
		mainPanel.add(textField_2);
		
		JLabel lblPosition = new JLabel("Position/Dept.");
		lblPosition.setForeground(new Color(30, 55, 101));
		lblPosition.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPosition.setBounds(338, 205, 85, 13);
		mainPanel.add(lblPosition);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(404, 111, 76, 19);
		mainPanel.add(textField_3);
		
		JLabel lblPayslip = new JLabel("Payslip No.");
		lblPayslip.setForeground(new Color(30, 55, 101));
		lblPayslip.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPayslip.setBounds(338, 117, 67, 13);
		mainPanel.add(lblPayslip);
		
		JLabel lblStartDate = new JLabel("Period Start Date");
		lblStartDate.setForeground(new Color(30, 55, 101));
		lblStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblStartDate.setBounds(492, 115, 85, 13);
		mainPanel.add(lblStartDate);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(580, 110, 67, 19);
		mainPanel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(580, 140, 67, 19);
		mainPanel.add(textField_5);
		
		JLabel lblEnddate = new JLabel("Period EndDate");
		lblEnddate.setForeground(new Color(30, 55, 101));
		lblEnddate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEnddate.setBounds(492, 144, 85, 13);
		mainPanel.add(lblEnddate);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBounds(455, 255, 192, 19);
		mainPanel.add(textField_6);
		
		JLabel lblMonthlyRate = new JLabel("Monthly Rate");
		lblMonthlyRate.setForeground(new Color(30, 55, 101));
		lblMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblMonthlyRate.setBounds(338, 258, 85, 13);
		mainPanel.add(lblMonthlyRate);
		
		JLabel lblDailyRate = new JLabel("Hourly Rate");
		lblDailyRate.setForeground(new Color(30, 55, 101));
		lblDailyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblDailyRate.setBounds(338, 281, 85, 13);
		mainPanel.add(lblDailyRate);
		
		JLabel lblDaysWorked = new JLabel("Hours Worked");
		lblDaysWorked.setForeground(new Color(30, 55, 101));
		lblDaysWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblDaysWorked.setBounds(338, 305, 85, 13);
		mainPanel.add(lblDaysWorked);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		textField_7.setBounds(455, 278, 192, 19);
		mainPanel.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_8.setEditable(false);
		textField_8.setColumns(10);
		textField_8.setBounds(455, 302, 192, 19);
		mainPanel.add(textField_8);
		
		JPanel separator_1_1 = new JPanel();
		separator_1_1.setBackground(new Color(30, 55, 101));
		separator_1_1.setBounds(404, 360, 243, 4);
		mainPanel.add(separator_1_1);
		
		JLabel lblBenefits = new JLabel("Benefits");
		lblBenefits.setForeground(new Color(30, 55, 101));
		lblBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblBenefits.setBounds(338, 349, 85, 24);
		mainPanel.add(lblBenefits);
		
		JLabel lblRiceSubsidy = new JLabel("Rice Subsidy");
		lblRiceSubsidy.setForeground(new Color(30, 55, 101));
		lblRiceSubsidy.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblRiceSubsidy.setBounds(338, 385, 85, 13);
		mainPanel.add(lblRiceSubsidy);
		
		JLabel lblPhoneAllowance = new JLabel("Phone Allowance");
		lblPhoneAllowance.setForeground(new Color(30, 55, 101));
		lblPhoneAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPhoneAllowance.setBounds(338, 404, 85, 13);
		mainPanel.add(lblPhoneAllowance);
		
		JLabel lblClothingAllowance = new JLabel("Clothing Allowance");
		lblClothingAllowance.setForeground(new Color(30, 55, 101));
		lblClothingAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblClothingAllowance.setBounds(338, 422, 92, 13);
		mainPanel.add(lblClothingAllowance);
		
		JLabel lblTotal = new JLabel("Total Benefits");
		lblTotal.setForeground(new Color(30, 55, 101));
		lblTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTotal.setBounds(338, 445, 110, 17);
		mainPanel.add(lblTotal);
		
		textField_9 = new JTextField();
		textField_9.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_9.setEditable(false);
		textField_9.setColumns(10);
		textField_9.setBounds(455, 382, 192, 19);
		mainPanel.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_10.setEditable(false);
		textField_10.setColumns(10);
		textField_10.setBounds(455, 401, 192, 19);
		mainPanel.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_11.setEditable(false);
		textField_11.setColumns(10);
		textField_11.setBounds(455, 419, 192, 19);
		mainPanel.add(textField_11);
		
		textField_12 = new JTextField();
		textField_12.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_12.setEditable(false);
		textField_12.setColumns(10);
		textField_12.setBounds(455, 443, 192, 19);
		mainPanel.add(textField_12);
		
		JPanel separator_1_1_1 = new JPanel();
		separator_1_1_1.setBackground(new Color(30, 55, 101));
		separator_1_1_1.setBounds(428, 479, 219, 4);
		mainPanel.add(separator_1_1_1);
		
		JLabel lblDeductions = new JLabel("Deductions");
		lblDeductions.setForeground(new Color(30, 55, 101));
		lblDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblDeductions.setBounds(338, 468, 85, 24);
		mainPanel.add(lblDeductions);
		
		JLabel lblSSS = new JLabel("SSS");
		lblSSS.setForeground(new Color(30, 55, 101));
		lblSSS.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblSSS.setBounds(338, 493, 119, 13);
		mainPanel.add(lblSSS);
		
		JLabel lblPhilhealth = new JLabel("Philhealth");
		lblPhilhealth.setForeground(new Color(30, 55, 101));
		lblPhilhealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPhilhealth.setBounds(338, 512, 85, 13);
		mainPanel.add(lblPhilhealth);
		
		JLabel lblPagibig = new JLabel("Pag-ibig");
		lblPagibig.setForeground(new Color(30, 55, 101));
		lblPagibig.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPagibig.setBounds(338, 530, 92, 13);
		mainPanel.add(lblPagibig);
		
		JLabel lblTotalDeductions = new JLabel("Total Deductions");
		lblTotalDeductions.setForeground(new Color(30, 55, 101));
		lblTotalDeductions.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTotalDeductions.setBounds(338, 553, 111, 20);
		mainPanel.add(lblTotalDeductions);
		
		textField_13 = new JTextField();
		textField_13.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_13.setEditable(false);
		textField_13.setColumns(10);
		textField_13.setBounds(455, 527, 192, 19);
		mainPanel.add(textField_13);
		
		textField_14 = new JTextField();
		textField_14.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_14.setEditable(false);
		textField_14.setColumns(10);
		textField_14.setBounds(455, 554, 192, 19);
		mainPanel.add(textField_14);
		
		textField_15 = new JTextField();
		textField_15.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_15.setEditable(false);
		textField_15.setColumns(10);
		textField_15.setBounds(455, 490, 192, 19);
		mainPanel.add(textField_15);
		
		textField_16 = new JTextField();
		textField_16.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_16.setEditable(false);
		textField_16.setColumns(10);
		textField_16.setBounds(455, 509, 192, 19);
		mainPanel.add(textField_16);
		
		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setForeground(new Color(30, 55, 101));
		lblSummary.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblSummary.setBounds(338, 579, 85, 24);
		mainPanel.add(lblSummary);
		
		JPanel separator_1_1_2 = new JPanel();
		separator_1_1_2.setBackground(new Color(30, 55, 101));
		separator_1_1_2.setBounds(413, 590, 234, 4);
		mainPanel.add(separator_1_1_2);
		
		textField_17 = new JTextField();
		textField_17.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_17.setEditable(false);
		textField_17.setColumns(10);
		textField_17.setBounds(455, 610, 192, 19);
		mainPanel.add(textField_17);
		
		textField_18 = new JTextField();
		textField_18.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_18.setEditable(false);
		textField_18.setColumns(10);
		textField_18.setBounds(455, 629, 192, 19);
		mainPanel.add(textField_18);
		
		textField_19 = new JTextField();
		textField_19.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_19.setEditable(false);
		textField_19.setColumns(10);
		textField_19.setBounds(455, 647, 192, 19);
		mainPanel.add(textField_19);
		
		textField_20 = new JTextField();
		textField_20.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_20.setEditable(false);
		textField_20.setColumns(10);
		textField_20.setBounds(455, 690, 192, 19);
		mainPanel.add(textField_20);
		
		JLabel lblTakeHomePay = new JLabel("TAKE HOME PAY");
		lblTakeHomePay.setForeground(new Color(30, 55, 101));
		lblTakeHomePay.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTakeHomePay.setBounds(338, 690, 111, 19);
		mainPanel.add(lblTakeHomePay);
		
		JLabel lblTotalDeductions_1 = new JLabel("Total Deductions");
		lblTotalDeductions_1.setForeground(new Color(30, 55, 101));
		lblTotalDeductions_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblTotalDeductions_1.setBounds(338, 650, 92, 13);
		mainPanel.add(lblTotalDeductions_1);
		
		JLabel lblBenefits_1 = new JLabel("Total Benefits");
		lblBenefits_1.setForeground(new Color(30, 55, 101));
		lblBenefits_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblBenefits_1.setBounds(338, 632, 85, 13);
		mainPanel.add(lblBenefits_1);
		
		JLabel lblGrossIncome_1 = new JLabel("Gross Income");
		lblGrossIncome_1.setForeground(new Color(30, 55, 101));
		lblGrossIncome_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblGrossIncome_1.setBounds(338, 613, 85, 13);
		mainPanel.add(lblGrossIncome_1);
		
		JPanel payslippanel = new JPanel();
		payslippanel.setLayout(null);
		payslippanel.setBounds(322, 65, 336, 650);
		mainPanel.add(payslippanel);
		
		JLabel lblEmployeePayslip = new JLabel("Employee Payslip");
		lblEmployeePayslip.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployeePayslip.setFont(new Font("Tw Cen MT", Font.PLAIN, 25));
		lblEmployeePayslip.setBounds(79, 5, 178, 28);
		payslippanel.add(lblEmployeePayslip);
		
		JLabel lblGrossIncome = new JLabel("Gross Income");
		lblGrossIncome.setForeground(new Color(30, 55, 101));
		lblGrossIncome.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblGrossIncome.setBounds(21, 261, 103, 19);
		payslippanel.add(lblGrossIncome);
		
		textField_21 = new JTextField();
		textField_21.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_21.setEditable(false);
		textField_21.setColumns(10);
		textField_21.setBounds(134, 261, 192, 19);
		payslippanel.add(textField_21);
		
		JLabel lblWithholdingTax = new JLabel("Withholding Tax");
		lblWithholdingTax.setForeground(new Color(30, 55, 101));
		lblWithholdingTax.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblWithholdingTax.setBounds(18, 606, 92, 13);
		payslippanel.add(lblWithholdingTax);
		
		textField_22 = new JTextField();
		textField_22.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textField_22.setEditable(false);
		textField_22.setColumns(10);
		textField_22.setBounds(134, 603, 192, 19);
		payslippanel.add(textField_22);
		
		// Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
	}

	public void openWindow() {
	    payslipScreen.setVisible(true);
	}
}
