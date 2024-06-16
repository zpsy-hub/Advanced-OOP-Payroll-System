package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Payslip;
import model.Permission;
import model.User;
import service.PayrollSalaryCalculationService;
import DAO.PayslipDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import service.PermissionService;
import service.SQL_client;
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
		payslipScreen.setBounds(100, 100, 1280, 800);
		payslipScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payslipScreen.getContentPane().setLayout(null);
		
		// Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/payslip.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        payslipScreen.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Set button visibility based on permissions
        List<String> visibleButtons = new ArrayList<>();
        visibleButtons.add("Dashboard");
        visibleButtons.add("Time In/Out");
        visibleButtons.add("Payslip");
        visibleButtons.add("Leave Request");
        visibleButtons.add("Overtime Request");

        Connection connection = SQL_client.getInstance().getConnection();
        PermissionService permissionsService = PermissionService.getInstance();
        List<Permission> userPermissions = permissionsService.getPermissionsForEmployee(loggedInEmployee.getId(), connection);

        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 1)) {
            visibleButtons.add("Employee Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 2)) {
            visibleButtons.add("Attendance Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 3)) {
            visibleButtons.add("Leave Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 4)) {
            visibleButtons.add("Salary Calculation");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 5)) {
            visibleButtons.add("Monthly Summary Reports");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 7)) {
            visibleButtons.add("Permissions Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 8)) {
            visibleButtons.add("Credentials Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 6)) {
            visibleButtons.add("Authentication Logs");
        }

        sidebar.setButtonVisibility(visibleButtons);
        
        // Add the sign-out button
        SidebarButton signOutButton = new SidebarButton("Sign Out", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIlogin login = new GUIlogin();
                login.loginScreen1.setVisible(true);
                payslipScreen.dispose(); 
            }
        });
        signOutButton.setBounds(1094, 35, 114, 40);
        mainPanel.add(signOutButton);
				
		JLabel payperiodLabel = new JLabel("Pay Period");
		payperiodLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
		payperiodLabel.setBounds(380, 110, 98, 33);
		mainPanel.add(payperiodLabel);
		
		monthComboBox = new JComboBox<>();
		monthComboBox.setFont(new Font("Poppins", Font.PLAIN, 16));
		monthComboBox.setBounds(489, 111, 250, 30);		
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
		
		JLabel employeeNameLabel = new JLabel("");
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		employeeNameLabel.setBounds(702, 31, 400, 33);
		mainPanel.add(employeeNameLabel);
		
		textfieldPositionDept = new JTextField();
		textfieldPositionDept.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPositionDept.setEditable(false);
		textfieldPositionDept.setColumns(10);
		textfieldPositionDept.setBounds(942, 361, 226, 19);
		mainPanel.add(textfieldPositionDept);
		
		textfieldEmployeeName = new JTextField();
		textfieldEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeName.setEditable(false);
		textfieldEmployeeName.setColumns(10);
		textfieldEmployeeName.setBounds(527, 361, 226, 19);
		mainPanel.add(textfieldEmployeeName);
		
		textfieldEmployeeID = new JTextField();
		textfieldEmployeeID.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeID.setEditable(false);
		textfieldEmployeeID.setColumns(10);
		textfieldEmployeeID.setBounds(527, 332, 76, 19);
		mainPanel.add(textfieldEmployeeID);
		
		textfieldPayslipNo = new JTextField();
		textfieldPayslipNo.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPayslipNo.setEditable(false);
		textfieldPayslipNo.setColumns(10);
		textfieldPayslipNo.setBounds(527, 303, 76, 19);
		mainPanel.add(textfieldPayslipNo);
		
		textfieldStartDate = new JTextField();
		textfieldStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldStartDate.setEditable(false);
		textfieldStartDate.setColumns(10);
		textfieldStartDate.setBounds(942, 305, 67, 19);
		mainPanel.add(textfieldStartDate);
		
		textfieldEndDate = new JTextField();
		textfieldEndDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEndDate.setEditable(false);
		textfieldEndDate.setColumns(10);
		textfieldEndDate.setBounds(942, 332, 67, 19);
		mainPanel.add(textfieldEndDate);
		
		txtfieldMonthlyRate = new JTextField();
		txtfieldMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldMonthlyRate.setEditable(false);
		txtfieldMonthlyRate.setColumns(10);
		txtfieldMonthlyRate.setBounds(550, 435, 192, 19);
		mainPanel.add(txtfieldMonthlyRate);
		
		txtfieldHourlyRate = new JTextField();
		txtfieldHourlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldHourlyRate.setEditable(false);
		txtfieldHourlyRate.setColumns(10);
		txtfieldHourlyRate.setBounds(550, 464, 192, 19);
		mainPanel.add(txtfieldHourlyRate);
		
		txtfieldHoursWorked = new JTextField();
		txtfieldHoursWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldHoursWorked.setEditable(false);
		txtfieldHoursWorked.setColumns(10);
		txtfieldHoursWorked.setBounds(550, 493, 192, 19);
		mainPanel.add(txtfieldHoursWorked);
		
		txtfieldRiceSubsidy = new JTextField();
		txtfieldRiceSubsidy.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldRiceSubsidy.setEditable(false);
		txtfieldRiceSubsidy.setColumns(10);
		txtfieldRiceSubsidy.setBounds(963, 444, 192, 19);
		mainPanel.add(txtfieldRiceSubsidy);
		
		txtfieldPhoneAllowance = new JTextField();
		txtfieldPhoneAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPhoneAllowance.setEditable(false);
		txtfieldPhoneAllowance.setColumns(10);
		txtfieldPhoneAllowance.setBounds(963, 464, 192, 19);
		mainPanel.add(txtfieldPhoneAllowance);
		
		txtfieldClothingAllowance = new JTextField();
		txtfieldClothingAllowance.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldClothingAllowance.setEditable(false);
		txtfieldClothingAllowance.setColumns(10);
		txtfieldClothingAllowance.setBounds(963, 493, 192, 19);
		mainPanel.add(txtfieldClothingAllowance);
		
		txtfieldTotalBenefits = new JTextField();
		txtfieldTotalBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalBenefits.setEditable(false);
		txtfieldTotalBenefits.setColumns(10);
		txtfieldTotalBenefits.setBounds(963, 522, 192, 19);
		mainPanel.add(txtfieldTotalBenefits);
		
		txtfieldPagIbig = new JTextField();
		txtfieldPagIbig.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPagIbig.setEditable(false);
		txtfieldPagIbig.setColumns(10);
		txtfieldPagIbig.setBounds(550, 658, 192, 19);
		mainPanel.add(txtfieldPagIbig);
		
		txtfieldTotalDeductions = new JTextField();
		txtfieldTotalDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalDeductions.setEditable(false);
		txtfieldTotalDeductions.setColumns(10);
		txtfieldTotalDeductions.setBounds(550, 705, 192, 19);
		mainPanel.add(txtfieldTotalDeductions);
		
		txtfieldSSS = new JTextField();
		txtfieldSSS.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSSS.setEditable(false);
		txtfieldSSS.setColumns(10);
		txtfieldSSS.setBounds(550, 600, 192, 19);
		mainPanel.add(txtfieldSSS);
		
		txtfieldPhilhealth = new JTextField();
		txtfieldPhilhealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldPhilhealth.setEditable(false);
		txtfieldPhilhealth.setColumns(10);
		txtfieldPhilhealth.setBounds(550, 629, 192, 19);
		mainPanel.add(txtfieldPhilhealth);
		
		JPanel separator_1_1_2 = new JPanel();
		separator_1_1_2.setBackground(new Color(30, 55, 101));
		separator_1_1_2.setBounds(413, 590, 234, 4);
		mainPanel.add(separator_1_1_2);
		
		txtfieldSummaryGrossIncome = new JTextField();
		txtfieldSummaryGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryGrossIncome.setEditable(false);
		txtfieldSummaryGrossIncome.setColumns(10);
		txtfieldSummaryGrossIncome.setBounds(963, 609, 192, 19);
		mainPanel.add(txtfieldSummaryGrossIncome);
		
		txtfieldSummaryBenefits = new JTextField();
		txtfieldSummaryBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryBenefits.setEditable(false);
		txtfieldSummaryBenefits.setColumns(10);
		txtfieldSummaryBenefits.setBounds(963, 647, 192, 19);
		mainPanel.add(txtfieldSummaryBenefits);
		
		txtfieldSummaryDeductions = new JTextField();
		txtfieldSummaryDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryDeductions.setEditable(false);
		txtfieldSummaryDeductions.setColumns(10);
		txtfieldSummaryDeductions.setBounds(963, 676, 192, 19);
		mainPanel.add(txtfieldSummaryDeductions);
		
		txtfieldTakeHomePay = new JTextField();
		txtfieldTakeHomePay.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTakeHomePay.setEditable(false);
		txtfieldTakeHomePay.setColumns(10);
		txtfieldTakeHomePay.setBounds(963, 705, 192, 19);
		mainPanel.add(txtfieldTakeHomePay);
		
		JButton exportButton = new JButton("Export");
		exportButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		exportButton.setBackground(Color.WHITE);
		exportButton.setBounds(1040, 110, 154, 33);
		mainPanel.add(exportButton);
		
		textFieldwithholdingtax = new JTextField();
		textFieldwithholdingtax.setBounds(550, 676, 192, 19);
		mainPanel.add(textFieldwithholdingtax);
		textFieldwithholdingtax.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textFieldwithholdingtax.setEditable(false);
		textFieldwithholdingtax.setColumns(10);
		
		txtfieldGrossIncome = new JTextField();
		txtfieldGrossIncome.setBounds(550, 530, 192, 19);
		mainPanel.add(txtfieldGrossIncome);
		txtfieldGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldGrossIncome.setEditable(false);
		txtfieldGrossIncome.setColumns(10);
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


	public void openWindow() {
		payslipScreen.setVisible(true);
	    }
	}
