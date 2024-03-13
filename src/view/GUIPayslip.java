package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Payslip;
import model.User;
import service.PayrollSalaryCalculationService;
import service.PayslipDAO;
import util.SessionManager;
import javax.swing.JTextField;

public class GUIPayslip {

	JFrame payslipScreen;
	private static User loggedInEmployee;
	private JTextField textfieldPositionDept;
	private JTextField textfieldEmployeeName;
	private JTextField textfieldEmployeeID;
	private JTextField textfieldPayslipNo;
	private JTextField textfieldStartDate;
	private JTextField textfieldEndDate;
	private JTextField txtfieldMonthlyRate;
	private JTextField txtfieldHourlyRate;
	private JTextField txtfieldHoursWorked;
	private JTextField txtfieldRiceSubsidy;
	private JTextField txtfieldPhoneAllowance;
	private JTextField txtfieldClothingAllowance;
	private JTextField txtfieldTotalBenefits;
	private JTextField txtfieldPagIbig;
	private JTextField txtfieldTotalDeductions;
	private JTextField txtfieldSSS;
	private JTextField txtfieldPhilhealth;
	private JTextField txtfieldSummaryGrossIncome;
	private JTextField txtfieldSummaryBenefits;
	private JTextField txtfieldSummaryDeductions;
	private JTextField txtfieldTakeHomePay;
	private JTextField txtfieldGrossIncome;
	private JTextField textFieldwithholdingtax;
    private JComboBox<String> monthComboBox;
    private PayrollSalaryCalculationService service;

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
        this.service = new PayrollSalaryCalculationService();
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
                payslipScreen.dispose(); 
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
		payslipLabel.setBounds(322, 22, 205, 33);
		mainPanel.add(payslipLabel);
		
		JLabel payperiodLabel = new JLabel("Pay Period");
		payperiodLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payperiodLabel.setBounds(680, 70, 98, 33);
		mainPanel.add(payperiodLabel);
		
		monthComboBox = new JComboBox<>();
		monthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthComboBox.setBounds(789, 71, 250, 30);		
		monthComboBox.addItem("Select Month-Year");
		// Populate monthComboBox with month-year combinations
        service.populateMonthComboBox(monthComboBox);
		
		mainPanel.add(monthComboBox);
		monthComboBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        generatePayslip();
		    }
		});
	
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
		signoutButton.setBounds(1142, 23, 103, 31);
		mainPanel.add(signoutButton);
		
		JLabel employeeNameLabel = new JLabel("");
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		employeeNameLabel.setBounds(732, 22, 400, 33);
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
		
		textfieldPositionDept = new JTextField();
		textfieldPositionDept.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPositionDept.setEditable(false);
		textfieldPositionDept.setColumns(10);
		textfieldPositionDept.setBounds(421, 202, 226, 19);
		mainPanel.add(textfieldPositionDept);
		
		textfieldEmployeeName = new JTextField();
		textfieldEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeName.setEditable(false);
		textfieldEmployeeName.setColumns(10);
		textfieldEmployeeName.setBounds(421, 171, 226, 19);
		mainPanel.add(textfieldEmployeeName);
		
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
		
		textfieldEmployeeID = new JTextField();
		textfieldEmployeeID.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeID.setEditable(false);
		textfieldEmployeeID.setColumns(10);
		textfieldEmployeeID.setBounds(404, 140, 76, 19);
		mainPanel.add(textfieldEmployeeID);
		
		JLabel lblPosition = new JLabel("Position/Dept.");
		lblPosition.setForeground(new Color(30, 55, 101));
		lblPosition.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPosition.setBounds(338, 205, 85, 13);
		mainPanel.add(lblPosition);
		
		textfieldPayslipNo = new JTextField();
		textfieldPayslipNo.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPayslipNo.setEditable(false);
		textfieldPayslipNo.setColumns(10);
		textfieldPayslipNo.setBounds(404, 111, 76, 19);
		mainPanel.add(textfieldPayslipNo);
		
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
		
		textfieldStartDate = new JTextField();
		textfieldStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldStartDate.setEditable(false);
		textfieldStartDate.setColumns(10);
		textfieldStartDate.setBounds(580, 110, 67, 19);
		mainPanel.add(textfieldStartDate);
		
		textfieldEndDate = new JTextField();
		textfieldEndDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEndDate.setEditable(false);
		textfieldEndDate.setColumns(10);
		textfieldEndDate.setBounds(580, 140, 67, 19);
		mainPanel.add(textfieldEndDate);
		
		JLabel lblEnddate = new JLabel("Period EndDate");
		lblEnddate.setForeground(new Color(30, 55, 101));
		lblEnddate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEnddate.setBounds(492, 144, 85, 13);
		mainPanel.add(lblEnddate);
		
		txtfieldMonthlyRate = new JTextField();
		txtfieldMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldMonthlyRate.setEditable(false);
		txtfieldMonthlyRate.setColumns(10);
		txtfieldMonthlyRate.setBounds(455, 255, 192, 19);
		mainPanel.add(txtfieldMonthlyRate);
		
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
		
		txtfieldHourlyRate = new JTextField();
		txtfieldHourlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldHourlyRate.setEditable(false);
		txtfieldHourlyRate.setColumns(10);
		txtfieldHourlyRate.setBounds(455, 278, 192, 19);
		mainPanel.add(txtfieldHourlyRate);
		
		txtfieldHoursWorked = new JTextField();
		txtfieldHoursWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldHoursWorked.setEditable(false);
		txtfieldHoursWorked.setColumns(10);
		txtfieldHoursWorked.setBounds(455, 302, 192, 19);
		mainPanel.add(txtfieldHoursWorked);
		
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
		
		txtfieldRiceSubsidy = new JTextField();
		txtfieldRiceSubsidy.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldRiceSubsidy.setEditable(false);
		txtfieldRiceSubsidy.setColumns(10);
		txtfieldRiceSubsidy.setBounds(455, 382, 192, 19);
		mainPanel.add(txtfieldRiceSubsidy);
		
		txtfieldPhoneAllowance = new JTextField();
		txtfieldPhoneAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPhoneAllowance.setEditable(false);
		txtfieldPhoneAllowance.setColumns(10);
		txtfieldPhoneAllowance.setBounds(455, 401, 192, 19);
		mainPanel.add(txtfieldPhoneAllowance);
		
		txtfieldClothingAllowance = new JTextField();
		txtfieldClothingAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldClothingAllowance.setEditable(false);
		txtfieldClothingAllowance.setColumns(10);
		txtfieldClothingAllowance.setBounds(455, 419, 192, 19);
		mainPanel.add(txtfieldClothingAllowance);
		
		txtfieldTotalBenefits = new JTextField();
		txtfieldTotalBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalBenefits.setEditable(false);
		txtfieldTotalBenefits.setColumns(10);
		txtfieldTotalBenefits.setBounds(455, 443, 192, 19);
		mainPanel.add(txtfieldTotalBenefits);
		
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
		
		txtfieldPagIbig = new JTextField();
		txtfieldPagIbig.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPagIbig.setEditable(false);
		txtfieldPagIbig.setColumns(10);
		txtfieldPagIbig.setBounds(455, 527, 192, 19);
		mainPanel.add(txtfieldPagIbig);
		
		txtfieldTotalDeductions = new JTextField();
		txtfieldTotalDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalDeductions.setEditable(false);
		txtfieldTotalDeductions.setColumns(10);
		txtfieldTotalDeductions.setBounds(455, 554, 192, 19);
		mainPanel.add(txtfieldTotalDeductions);
		
		txtfieldSSS = new JTextField();
		txtfieldSSS.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSSS.setEditable(false);
		txtfieldSSS.setColumns(10);
		txtfieldSSS.setBounds(455, 490, 192, 19);
		mainPanel.add(txtfieldSSS);
		
		txtfieldPhilhealth = new JTextField();
		txtfieldPhilhealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPhilhealth.setEditable(false);
		txtfieldPhilhealth.setColumns(10);
		txtfieldPhilhealth.setBounds(455, 509, 192, 19);
		mainPanel.add(txtfieldPhilhealth);
		
		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setForeground(new Color(30, 55, 101));
		lblSummary.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblSummary.setBounds(338, 579, 85, 24);
		mainPanel.add(lblSummary);
		
		JPanel separator_1_1_2 = new JPanel();
		separator_1_1_2.setBackground(new Color(30, 55, 101));
		separator_1_1_2.setBounds(413, 590, 234, 4);
		mainPanel.add(separator_1_1_2);
		
		txtfieldSummaryGrossIncome = new JTextField();
		txtfieldSummaryGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryGrossIncome.setEditable(false);
		txtfieldSummaryGrossIncome.setColumns(10);
		txtfieldSummaryGrossIncome.setBounds(455, 610, 192, 19);
		mainPanel.add(txtfieldSummaryGrossIncome);
		
		txtfieldSummaryBenefits = new JTextField();
		txtfieldSummaryBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryBenefits.setEditable(false);
		txtfieldSummaryBenefits.setColumns(10);
		txtfieldSummaryBenefits.setBounds(455, 629, 192, 19);
		mainPanel.add(txtfieldSummaryBenefits);
		
		txtfieldSummaryDeductions = new JTextField();
		txtfieldSummaryDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryDeductions.setEditable(false);
		txtfieldSummaryDeductions.setColumns(10);
		txtfieldSummaryDeductions.setBounds(455, 647, 192, 19);
		mainPanel.add(txtfieldSummaryDeductions);
		
		txtfieldTakeHomePay = new JTextField();
		txtfieldTakeHomePay.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTakeHomePay.setEditable(false);
		txtfieldTakeHomePay.setColumns(10);
		txtfieldTakeHomePay.setBounds(455, 690, 192, 19);
		mainPanel.add(txtfieldTakeHomePay);
		
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
		
		txtfieldGrossIncome = new JTextField();
		txtfieldGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldGrossIncome.setEditable(false);
		txtfieldGrossIncome.setColumns(10);
		txtfieldGrossIncome.setBounds(134, 261, 192, 19);
		payslippanel.add(txtfieldGrossIncome);
		
		JLabel lblWithholdingTax = new JLabel("Withholding Tax");
		lblWithholdingTax.setForeground(new Color(30, 55, 101));
		lblWithholdingTax.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblWithholdingTax.setBounds(18, 606, 92, 13);
		payslippanel.add(lblWithholdingTax);
		
		textFieldwithholdingtax = new JTextField();
		textFieldwithholdingtax.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textFieldwithholdingtax.setEditable(false);
		textFieldwithholdingtax.setColumns(10);
		textFieldwithholdingtax.setBounds(134, 603, 192, 19);
		payslippanel.add(textFieldwithholdingtax);
		
		JButton exportButton = new JButton("Export");
		exportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		exportButton.setBackground(Color.WHITE);
		exportButton.setBounds(885, 133, 154, 33);
		mainPanel.add(exportButton);
		exportButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Get the logged-in employee's ID from the logged-in user object
		        int loggedInEmployeeId = loggedInEmployee.getId();

		        // Get the selected month and year from the payslipScreen
		        String selectedMonthYear = (String) monthComboBox.getSelectedItem();

		        // If a month-year is selected
		        if (!selectedMonthYear.equals("Date")) {
		            // Retrieve the payslip for the logged-in employee and selected month-year
		            Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeIdAndMonthYear(String.valueOf(loggedInEmployeeId), selectedMonthYear);

		            // If a payslip is found for the logged-in employee and selected month-year
		            if (payslip != null) {
		                // Proceed with exporting the payslip details to a file
		                service.writePayslipDetailsToFile(payslip);
		            } else {
		                // If no payslip is found, display an error message
		                JOptionPane.showMessageDialog(null, "Payslip details not found for the selected month and year.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            // If no month-year is selected, display an error message
		            JOptionPane.showMessageDialog(null, "Please select a valid month and year.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		
		// Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
	}

	public void openWindow() {
	    payslipScreen.setVisible(true);
	}
	
	   // Method to format decimal values with two decimal places
    private String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.##"); 
        return df.format(value);
    }
	
    private void populateTextFieldsWithPayslip(Payslip payslip, int payslipNumber) {
        // Employee Details
    	 textfieldPayslipNo.setText(String.valueOf(payslipNumber));
        textfieldEmployeeID.setText(String.valueOf(payslip.getEmployeeId()));
        textfieldEmployeeName.setText(payslip.getEmployeeName());
        textfieldPositionDept.setText(payslip.getEmployeePosition());
        textfieldStartDate.setText(String.valueOf(payslip.getPeriodStartDate()));
        textfieldEndDate.setText(String.valueOf(payslip.getPeriodEndDate()));

        // Earnings
        txtfieldMonthlyRate.setText(formatDecimal(payslip.getMonthlyRate()));
        txtfieldHourlyRate.setText(formatDecimal(payslip.getHourlyRate()));
        txtfieldHoursWorked.setText(String.valueOf(payslip.getTotalHours()));
        txtfieldGrossIncome.setText(formatDecimal(payslip.getGrossIncome()));

        // Benefits
        txtfieldRiceSubsidy.setText(formatDecimal(payslip.getRiceSubsidy()));
        txtfieldPhoneAllowance.setText(formatDecimal(payslip.getPhoneAllowance()));
        txtfieldClothingAllowance.setText(formatDecimal(payslip.getClothingAllowance()));
        double totalBenefits = payslip.getRiceSubsidy() + payslip.getPhoneAllowance() + payslip.getClothingAllowance();
        txtfieldTotalBenefits.setText(formatDecimal(totalBenefits));

        // Deductions
        txtfieldSSS.setText(formatDecimal(payslip.getSssContribution()));
        txtfieldPhilhealth.setText(formatDecimal(payslip.getPhilhealthContribution()));
        txtfieldPagIbig.setText(formatDecimal(payslip.getPagibigContribution()));
        double totalDeductions = payslip.getSssContribution() + payslip.getPhilhealthContribution() + payslip.getPagibigContribution();
        txtfieldTotalDeductions.setText(formatDecimal(totalDeductions));

        // Summary
        txtfieldSummaryGrossIncome.setText(formatDecimal(payslip.getGrossIncome()));
        txtfieldSummaryBenefits.setText(formatDecimal(totalBenefits));
        txtfieldSummaryDeductions.setText(formatDecimal(totalDeductions));
        double withholdingTax = payslip.getWithholdingTax();
        textFieldwithholdingtax.setText(formatDecimal(withholdingTax));

        // Net Pay
        double netPay = payslip.getGrossIncome() - totalDeductions + totalBenefits - withholdingTax;
        txtfieldTakeHomePay.setText(formatDecimal(netPay));
    }
    
    private void generatePayslip() {
        // Get the logged-in employee's ID from the logged-in user object
        int loggedInEmployeeId = loggedInEmployee.getId();

        // Get the selected month and year from the monthComboBox
        String selectedMonthYear = (String) monthComboBox.getSelectedItem();

        // If a month-year is selected
        if (!selectedMonthYear.equals("Date")) {
            // Split the selected month-year to extract the month and year
            String[] parts = selectedMonthYear.split("-");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            // Retrieve the payslip for the selected month and year
            Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeIdAndMonthYear(String.valueOf(loggedInEmployeeId), selectedMonthYear);

            // Retrieve the payslip numbers for the selected employee ID and month-year
            List<Integer> payslipNumbers = PayslipDAO.getInstance().getPayslipNumbersByEmployeeIdAndMonthYear(String.valueOf(loggedInEmployeeId), selectedMonthYear);

            // If a payslip is found for the selected month and year
            if (payslip != null) {
                // Get the first payslip number (assuming there's only one payslip per month)
                int payslipNumber = payslipNumbers.isEmpty() ? 0 : payslipNumbers.get(0);

                // Populate the text fields with the payslip details and payslip number
                populateTextFieldsWithPayslip(payslip, payslipNumber);
            } else {
                // If no payslip is found, display an error message
                JOptionPane.showMessageDialog(payslipScreen, "Payslip not found for selected month and year.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // If no month-year is selected, display an error message
            JOptionPane.showMessageDialog(payslipScreen, "Please select a valid month and year.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
