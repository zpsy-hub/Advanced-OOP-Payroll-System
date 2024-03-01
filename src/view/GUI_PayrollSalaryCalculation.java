package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


public class GUI_PayrollSalaryCalculation {

	private JFrame payrollsalarycalc;
	private JTable salarycalculationTable;
	private JTextField textfieldPayslipNo;
	private JTextField textfieldEmployeeID;
	private JTextField textfieldEmployeeName;
	private JTextField textfieldStartDate;
	private JTextField textfieldEndDate;
	private JTextField textfieldPositionDept;
	private JTextField txtfieldMonthlyRate;
	private JTextField txtfieldDailyRate;
	private JTextField txtfieldDaysWorked;
	private JTextField txtfieldGrossIncome;
	private JTextField txtfieldRiceSubsidy;
	private JTextField txtfieldPhoneAllowance;
	private JTextField txtfieldClothingAllowance;
	private JTextField txtfieldTotalBenefits;
	private JTextField txtfieldSSS;
	private JTextField txtfieldPhilhealth;
	private JTextField txtfieldPagIbig;
	private JTextField txtfieldWithholdingTax;
	private JTextField txtfieldTotalDeductions;
	private JTextField txtfieldSummaryGrossIncome;
	private JTextField txtfieldSummaryBenefits;
	private JTextField txtfieldSummaryDeductions;
	private JTextField txtfieldTakeHomePay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation();
					window.payrollsalarycalc.setVisible(true);
					window.payrollsalarycalc.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_PayrollSalaryCalculation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		payrollsalarycalc = new JFrame();
		payrollsalarycalc.setTitle("MotorPH Payroll System");
		payrollsalarycalc.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\GitHub\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		payrollsalarycalc.setBounds(100, 100, 1315, 770);
		payrollsalarycalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payrollsalarycalc.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1312, 733);
		payrollsalarycalc.getContentPane().add(mainPanel);
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
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
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
		
		JLabel payrollaccessLabel = new JLabel("Payroll Access");
		payrollaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payrollaccessLabel.setBounds(139, 354, 138, 33);
		sidePanel.add(payrollaccessLabel);
		
		JPanel separator = new JPanel();
		separator.setBackground(new Color(30, 55, 101));
		separator.setBounds(37, 372, 88, 3);
		sidePanel.add(separator);
		
		JButton salarycalculationButton = new JButton("Salary Calculation");
		salarycalculationButton.setEnabled(false);
		salarycalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		salarycalculationButton.setBackground(Color.WHITE);
		salarycalculationButton.setBounds(37, 411, 227, 31);
		sidePanel.add(salarycalculationButton);
		
		JButton monthlyreportButton = new JButton("Monthly Summary Report");
		monthlyreportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthlyreportButton.setBackground(Color.WHITE);
		monthlyreportButton.setBounds(37, 473, 227, 31);
		sidePanel.add(monthlyreportButton);
		
		JLabel salarycalculationLabel = new JLabel("Salary Calculation");
		salarycalculationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		salarycalculationLabel.setBounds(324, 36, 267, 33);
		mainPanel.add(salarycalculationLabel);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				payrollsalarycalc.dispose();
			}
		});
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(841, 38, 131, 31);
		mainPanel.add(signoutButton);
		
		JLabel lblPayPeriodMonth = new JLabel("Pay Period for the Month:");
		lblPayPeriodMonth.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblPayPeriodMonth.setBounds(324, 79, 215, 33);
		mainPanel.add(lblPayPeriodMonth);
		
		JComboBox monthComboBox = new JComboBox();
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
		monthComboBox.setMaximumRowCount(13);
		monthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		monthComboBox.setBounds(538, 79, 139, 30);
		mainPanel.add(monthComboBox);
		
		JLabel lbl2024 = new JLabel("2024");
		lbl2024.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lbl2024.setBounds(687, 79, 57, 33);
		mainPanel.add(lbl2024);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(324, 122, 648, 531);
		mainPanel.add(tablePanel);
		tablePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		salarycalculationTable = new JTable();
		salarycalculationTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablePanel.add(salarycalculationTable);
		
		JButton viewButton = new JButton("View");
		viewButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		viewButton.setBackground(Color.WHITE);
		viewButton.setBounds(324, 678, 154, 33);
		mainPanel.add(viewButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		editButton.setBackground(Color.WHITE);
		editButton.setBounds(510, 678, 154, 33);
		mainPanel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		deleteButton.setBackground(Color.WHITE);
		deleteButton.setBounds(693, 678, 154, 33);
		mainPanel.add(deleteButton);
		
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		calculateButton.setBackground(Color.WHITE);
		calculateButton.setBounds(841, 79, 131, 33);
		mainPanel.add(calculateButton);
		
		JLabel lblEmployeePayslip = new JLabel("Employee Payslip");
		lblEmployeePayslip.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployeePayslip.setFont(new Font("Tw Cen MT", Font.PLAIN, 25));
		lblEmployeePayslip.setBounds(998, 64, 293, 24);
		mainPanel.add(lblEmployeePayslip);
		
		JLabel lblPayslip = new JLabel("Payslip No.");
		lblPayslip.setForeground(new Color(30, 55, 101));
		lblPayslip.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPayslip.setBounds(982, 118, 67, 13);
		mainPanel.add(lblPayslip);
		
		JLabel lblStartDate = new JLabel("Period Start Date");
		lblStartDate.setForeground(new Color(30, 55, 101));
		lblStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblStartDate.setBounds(1136, 116, 85, 13);
		mainPanel.add(lblStartDate);
		
		JLabel lblEmployeeId = new JLabel("Employee ID");
		lblEmployeeId.setForeground(new Color(30, 55, 101));
		lblEmployeeId.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEmployeeId.setBounds(982, 147, 67, 13);
		mainPanel.add(lblEmployeeId);
		
		JLabel lblEnddate = new JLabel("Period EndDate");
		lblEnddate.setForeground(new Color(30, 55, 101));
		lblEnddate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEnddate.setBounds(1136, 145, 85, 13);
		mainPanel.add(lblEnddate);
		
		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setForeground(new Color(30, 55, 101));
		lblEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEmployeeName.setBounds(982, 173, 85, 13);
		mainPanel.add(lblEmployeeName);
		
		textfieldPayslipNo = new JTextField();
		textfieldPayslipNo.setEditable(false);
		textfieldPayslipNo.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPayslipNo.setBounds(1048, 112, 76, 19);
		mainPanel.add(textfieldPayslipNo);
		textfieldPayslipNo.setColumns(10);
		
		textfieldEmployeeID = new JTextField();
		textfieldEmployeeID.setEditable(false);
		textfieldEmployeeID.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeID.setColumns(10);
		textfieldEmployeeID.setBounds(1048, 141, 76, 19);
		mainPanel.add(textfieldEmployeeID);
		
		textfieldEmployeeName = new JTextField();
		textfieldEmployeeName.setEditable(false);
		textfieldEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeName.setColumns(10);
		textfieldEmployeeName.setBounds(1065, 170, 226, 19);
		mainPanel.add(textfieldEmployeeName);
		
		textfieldStartDate = new JTextField();
		textfieldStartDate.setEditable(false);
		textfieldStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldStartDate.setColumns(10);
		textfieldStartDate.setBounds(1224, 111, 67, 19);
		mainPanel.add(textfieldStartDate);
		
		textfieldEndDate = new JTextField();
		textfieldEndDate.setEditable(false);
		textfieldEndDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEndDate.setColumns(10);
		textfieldEndDate.setBounds(1224, 141, 67, 19);
		mainPanel.add(textfieldEndDate);
		
		JLabel lblPosition = new JLabel("Position/Dept.");
		lblPosition.setForeground(new Color(30, 55, 101));
		lblPosition.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPosition.setBounds(982, 207, 85, 13);
		mainPanel.add(lblPosition);
		
		textfieldPositionDept = new JTextField();
		textfieldPositionDept.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPositionDept.setEditable(false);
		textfieldPositionDept.setColumns(10);
		textfieldPositionDept.setBounds(1065, 204, 226, 19);
		mainPanel.add(textfieldPositionDept);
		
		JLabel lblEarnings = new JLabel("Earnings");
		lblEarnings.setForeground(new Color(30, 55, 101));
		lblEarnings.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblEarnings.setBounds(982, 230, 85, 24);
		mainPanel.add(lblEarnings);
		
		JLabel lblMonthlyRate = new JLabel("Monthly Rate");
		lblMonthlyRate.setForeground(new Color(30, 55, 101));
		lblMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblMonthlyRate.setBounds(982, 255, 85, 13);
		mainPanel.add(lblMonthlyRate);
		
		JLabel lblDailyRate = new JLabel("Daily Rate");
		lblDailyRate.setForeground(new Color(30, 55, 101));
		lblDailyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblDailyRate.setBounds(982, 274, 85, 13);
		mainPanel.add(lblDailyRate);
		
		JLabel lblDaysWorked = new JLabel("Days Worked");
		lblDaysWorked.setForeground(new Color(30, 55, 101));
		lblDaysWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblDaysWorked.setBounds(982, 292, 85, 13);
		mainPanel.add(lblDaysWorked);
		
		JLabel lblGrossIncome = new JLabel("Gross Income");
		lblGrossIncome.setForeground(new Color(30, 55, 101));
		lblGrossIncome.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblGrossIncome.setBounds(982, 315, 103, 19);
		mainPanel.add(lblGrossIncome);
		
		JPanel separator_1 = new JPanel();
		separator_1.setBackground(new Color(30, 55, 101));
		separator_1.setBounds(1048, 241, 243, 4);
		mainPanel.add(separator_1);
		
		JLabel lblBenefits = new JLabel("Benefits");
		lblBenefits.setForeground(new Color(30, 55, 101));
		lblBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblBenefits.setBounds(982, 339, 85, 24);
		mainPanel.add(lblBenefits);
		
		JPanel separator_1_1 = new JPanel();
		separator_1_1.setBackground(new Color(30, 55, 101));
		separator_1_1.setBounds(1048, 350, 243, 4);
		mainPanel.add(separator_1_1);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setForeground(new Color(30, 55, 101));
		lblTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTotal.setBounds(982, 424, 51, 17);
		mainPanel.add(lblTotal);
		
		JLabel lblClothingAllowance = new JLabel("Clothing Allowance");
		lblClothingAllowance.setForeground(new Color(30, 55, 101));
		lblClothingAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblClothingAllowance.setBounds(982, 401, 92, 13);
		mainPanel.add(lblClothingAllowance);
		
		JLabel lblPhoneAllowance = new JLabel("Phone Allowance");
		lblPhoneAllowance.setForeground(new Color(30, 55, 101));
		lblPhoneAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPhoneAllowance.setBounds(982, 383, 85, 13);
		mainPanel.add(lblPhoneAllowance);
		
		JLabel lblRiceSubsidy = new JLabel("Rice Subsidy");
		lblRiceSubsidy.setForeground(new Color(30, 55, 101));
		lblRiceSubsidy.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblRiceSubsidy.setBounds(982, 364, 85, 13);
		mainPanel.add(lblRiceSubsidy);
		
		JLabel lblDeductions = new JLabel("Deductions");
		lblDeductions.setForeground(new Color(30, 55, 101));
		lblDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblDeductions.setBounds(982, 447, 85, 24);
		mainPanel.add(lblDeductions);
		
		JPanel separator_1_1_1 = new JPanel();
		separator_1_1_1.setBackground(new Color(30, 55, 101));
		separator_1_1_1.setBounds(1072, 458, 219, 4);
		mainPanel.add(separator_1_1_1);
		
		JLabel lblSSS = new JLabel("Social Security System");
		lblSSS.setForeground(new Color(30, 55, 101));
		lblSSS.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblSSS.setBounds(982, 472, 119, 13);
		mainPanel.add(lblSSS);
		
		JLabel lblPhilhealth = new JLabel("Philhealth");
		lblPhilhealth.setForeground(new Color(30, 55, 101));
		lblPhilhealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPhilhealth.setBounds(982, 491, 85, 13);
		mainPanel.add(lblPhilhealth);
		
		JLabel lblPagibig = new JLabel("Pag-ibig");
		lblPagibig.setForeground(new Color(30, 55, 101));
		lblPagibig.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPagibig.setBounds(982, 509, 92, 13);
		mainPanel.add(lblPagibig);
		
		JLabel lblTotalDeductions = new JLabel("Total Deductions");
		lblTotalDeductions.setForeground(new Color(30, 55, 101));
		lblTotalDeductions.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTotalDeductions.setBounds(982, 551, 111, 20);
		mainPanel.add(lblTotalDeductions);
		
		JLabel lblWithholdingTax = new JLabel("Withholding Tax");
		lblWithholdingTax.setForeground(new Color(30, 55, 101));
		lblWithholdingTax.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblWithholdingTax.setBounds(983, 528, 92, 13);
		mainPanel.add(lblWithholdingTax);
		
		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setForeground(new Color(30, 55, 101));
		lblSummary.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblSummary.setBounds(982, 570, 85, 24);
		mainPanel.add(lblSummary);
		
		JPanel separator_1_1_2 = new JPanel();
		separator_1_1_2.setBackground(new Color(30, 55, 101));
		separator_1_1_2.setBounds(1057, 581, 234, 4);
		mainPanel.add(separator_1_1_2);
		
		JLabel lblGrossIncome_1 = new JLabel("Gross Income");
		lblGrossIncome_1.setForeground(new Color(30, 55, 101));
		lblGrossIncome_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblGrossIncome_1.setBounds(982, 595, 85, 13);
		mainPanel.add(lblGrossIncome_1);
		
		JLabel lblBenefits_1 = new JLabel("Benefits");
		lblBenefits_1.setForeground(new Color(30, 55, 101));
		lblBenefits_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblBenefits_1.setBounds(982, 614, 85, 13);
		mainPanel.add(lblBenefits_1);
		
		JLabel lblTotalDeductions_1 = new JLabel("Total Deductions");
		lblTotalDeductions_1.setForeground(new Color(30, 55, 101));
		lblTotalDeductions_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblTotalDeductions_1.setBounds(982, 632, 92, 13);
		mainPanel.add(lblTotalDeductions_1);
		
		JLabel lblTakeHomePay = new JLabel("TAKE HOME PAY");
		lblTakeHomePay.setForeground(new Color(30, 55, 101));
		lblTakeHomePay.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTakeHomePay.setBounds(982, 675, 111, 19);
		mainPanel.add(lblTakeHomePay);
		
		txtfieldMonthlyRate = new JTextField();
		txtfieldMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldMonthlyRate.setEditable(false);
		txtfieldMonthlyRate.setColumns(10);
		txtfieldMonthlyRate.setBounds(1099, 252, 192, 19);
		mainPanel.add(txtfieldMonthlyRate);
		
		txtfieldDailyRate = new JTextField();
		txtfieldDailyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldDailyRate.setEditable(false);
		txtfieldDailyRate.setColumns(10);
		txtfieldDailyRate.setBounds(1099, 271, 192, 19);
		mainPanel.add(txtfieldDailyRate);
		
		txtfieldDaysWorked = new JTextField();
		txtfieldDaysWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldDaysWorked.setEditable(false);
		txtfieldDaysWorked.setColumns(10);
		txtfieldDaysWorked.setBounds(1099, 289, 192, 19);
		mainPanel.add(txtfieldDaysWorked);
		
		txtfieldGrossIncome = new JTextField();
		txtfieldGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldGrossIncome.setEditable(false);
		txtfieldGrossIncome.setColumns(10);
		txtfieldGrossIncome.setBounds(1099, 315, 192, 19);
		mainPanel.add(txtfieldGrossIncome);
		
		txtfieldRiceSubsidy = new JTextField();
		txtfieldRiceSubsidy.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldRiceSubsidy.setEditable(false);
		txtfieldRiceSubsidy.setColumns(10);
		txtfieldRiceSubsidy.setBounds(1099, 361, 192, 19);
		mainPanel.add(txtfieldRiceSubsidy);
		
		txtfieldPhoneAllowance = new JTextField();
		txtfieldPhoneAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPhoneAllowance.setEditable(false);
		txtfieldPhoneAllowance.setColumns(10);
		txtfieldPhoneAllowance.setBounds(1099, 380, 192, 19);
		mainPanel.add(txtfieldPhoneAllowance);
		
		txtfieldClothingAllowance = new JTextField();
		txtfieldClothingAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldClothingAllowance.setEditable(false);
		txtfieldClothingAllowance.setColumns(10);
		txtfieldClothingAllowance.setBounds(1099, 398, 192, 19);
		mainPanel.add(txtfieldClothingAllowance);
		
		txtfieldTotalBenefits = new JTextField();
		txtfieldTotalBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalBenefits.setEditable(false);
		txtfieldTotalBenefits.setColumns(10);
		txtfieldTotalBenefits.setBounds(1099, 422, 192, 19);
		mainPanel.add(txtfieldTotalBenefits);
		
		txtfieldSSS = new JTextField();
		txtfieldSSS.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSSS.setEditable(false);
		txtfieldSSS.setColumns(10);
		txtfieldSSS.setBounds(1099, 469, 192, 19);
		mainPanel.add(txtfieldSSS);
		
		txtfieldPhilhealth = new JTextField();
		txtfieldPhilhealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPhilhealth.setEditable(false);
		txtfieldPhilhealth.setColumns(10);
		txtfieldPhilhealth.setBounds(1099, 488, 192, 19);
		mainPanel.add(txtfieldPhilhealth);
		
		txtfieldPagIbig = new JTextField();
		txtfieldPagIbig.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPagIbig.setEditable(false);
		txtfieldPagIbig.setColumns(10);
		txtfieldPagIbig.setBounds(1099, 506, 192, 19);
		mainPanel.add(txtfieldPagIbig);
		
		txtfieldWithholdingTax = new JTextField();
		txtfieldWithholdingTax.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldWithholdingTax.setEditable(false);
		txtfieldWithholdingTax.setColumns(10);
		txtfieldWithholdingTax.setBounds(1099, 525, 192, 19);
		mainPanel.add(txtfieldWithholdingTax);
		
		txtfieldTotalDeductions = new JTextField();
		txtfieldTotalDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalDeductions.setEditable(false);
		txtfieldTotalDeductions.setColumns(10);
		txtfieldTotalDeductions.setBounds(1099, 552, 192, 19);
		mainPanel.add(txtfieldTotalDeductions);
		
		txtfieldSummaryGrossIncome = new JTextField();
		txtfieldSummaryGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryGrossIncome.setEditable(false);
		txtfieldSummaryGrossIncome.setColumns(10);
		txtfieldSummaryGrossIncome.setBounds(1099, 592, 192, 19);
		mainPanel.add(txtfieldSummaryGrossIncome);
		
		txtfieldSummaryBenefits = new JTextField();
		txtfieldSummaryBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryBenefits.setEditable(false);
		txtfieldSummaryBenefits.setColumns(10);
		txtfieldSummaryBenefits.setBounds(1099, 611, 192, 19);
		mainPanel.add(txtfieldSummaryBenefits);
		
		txtfieldSummaryDeductions = new JTextField();
		txtfieldSummaryDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryDeductions.setEditable(false);
		txtfieldSummaryDeductions.setColumns(10);
		txtfieldSummaryDeductions.setBounds(1099, 629, 192, 19);
		mainPanel.add(txtfieldSummaryDeductions);
		
		txtfieldTakeHomePay = new JTextField();
		txtfieldTakeHomePay.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTakeHomePay.setEditable(false);
		txtfieldTakeHomePay.setColumns(10);
		txtfieldTakeHomePay.setBounds(1099, 675, 192, 19);
		mainPanel.add(txtfieldTakeHomePay);
	}

	public void openWindow() {
		payrollsalarycalc.setVisible(true);
		
	}
}
