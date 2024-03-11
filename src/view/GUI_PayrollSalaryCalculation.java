package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Employee;
import model.Payslip;
import model.User;
import service.EmployeeDAO;
import service.PayslipService;
import service.TimesheetDAO;
import service.PayslipDAO;
import util.SessionManager;

import javax.swing.JScrollPane;


public class GUI_PayrollSalaryCalculation {

	JFrame payrollsalarycalc;
	private JTable employeeattendanceTable;
	private JTable salarycalculationTable;
	private JTextField textfieldPayslipNo;
	private JTextField textfieldEmployeeID;
	private JTextField textfieldEmployeeName;
	private JTextField textfieldStartDate;
	private JTextField textfieldEndDate;
	private JTextField textfieldPositionDept;
	private JTextField txtfieldMonthlyRate;
	private JTextField txtfieldDailyRate;
	private JTextField txtfieldHoursWorked;
	private JTextField txtfieldGrossIncome;
	private JTextField txtfieldRiceSubsidy;
	private JTextField txtfieldPhoneAllowance;
	private JTextField txtfieldClothingAllowance;
	private JTextField txtfieldTotalBenefits;
	private JTextField txtfieldSSS;
	private JTextField txtfieldPhilhealth;
	private JTextField txtfieldPagIbig;
	private JTextField txtfieldTotalDeductions;
	private JTextField txtfieldSummaryGrossIncome;
	private JTextField txtfieldSummaryBenefits;
	private JTextField txtfieldSummaryDeductions;
	private JTextField txtfieldTakeHomePay;
    private JComboBox<String> monthComboBox;
    private static User loggedInEmployee;
    private JButton btnCalculateHoursWorked;
    private JTextField textFieldwithholdingtax;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation(loggedInEmployee);
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
	public GUI_PayrollSalaryCalculation(User loggedInEmployee) {
		GUI_PayrollSalaryCalculation.loggedInEmployee = loggedInEmployee; 
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
		
		JButton btnCalculateHoursWorked = new JButton("Calculate Monthly Hours Worked");
		
		// JComboBox for selecting month
        monthComboBox = new JComboBox<>();
        monthComboBox.setMaximumRowCount(13);
        monthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        monthComboBox.setBounds(745, 42, 200, 30);
        payrollsalarycalc.getContentPane().add(monthComboBox);
        monthComboBox.addItem("All Records");
        
     // Action listener for monthComboBox
        monthComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMonthYear = (String) monthComboBox.getSelectedItem();
                if (!selectedMonthYear.equals("All Records")) {
                    filterRecordsByMonthYear(selectedMonthYear);
                    refreshSalaryCalculationTable(selectedMonthYear); // Update salary calculation table based on selected month-year
                    // Enable the calculate button when a specific month is selected
                    btnCalculateHoursWorked.setEnabled(true);
                } else {
                    populateTable();
                    populateSalaryCalculationTable(); // Populate salary calculation table with all records
                    // Disable the calculate button when "All Records" is selected
                    btnCalculateHoursWorked.setEnabled(false);
                }
            }
        });


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
		
		//sidebar
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
                payrollsalarycalc.dispose();
		        }
		});
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();
		        payrollsalarycalc.dispose();
		        }
		});
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidePanel.add(payslipButton);
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		    	payslip.openWindow();
		    	payrollsalarycalc.dispose();		    		        	    
		    }
		});
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidePanel.add(leaverequestButton);
		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip window = new GUIPayslip(loggedInEmployee);
				window.payslipScreen.setVisible(true);
				payrollsalarycalc.dispose();
		    }
		});		
		
		JButton monthlyreportButton = new JButton("Monthly Summary Report");
		monthlyreportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthlyreportButton.setBackground(Color.WHITE);
		monthlyreportButton.setBounds(37, 473, 227, 31);
		sidePanel.add(monthlyreportButton);
		monthlyreportButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_PayrollMonthlySummary window = new GUI_PayrollMonthlySummary(loggedInEmployee);
				window.payrollmontlysummary.setVisible(true);
				payrollsalarycalc.dispose();
		    }
		});	
		
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
		signoutButton.setBounds(1160, 10, 131, 31);
		mainPanel.add(signoutButton);		
		
		//main panel
		JLabel lblPayPeriodMonth = new JLabel("Timesheet Records");
		lblPayPeriodMonth.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblPayPeriodMonth.setBounds(324, 73, 215, 33);
		mainPanel.add(lblPayPeriodMonth);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(324, 104, 623, 188);
		mainPanel.add(scrollPane);
		
		employeeattendanceTable = new JTable();
		scrollPane.setViewportView(employeeattendanceTable);
		employeeattendanceTable.setBorder(new LineBorder(new Color(0, 0, 0)));

        // Populate monthComboBox with month-year combinations
        populateMonthComboBox();
        
        // Call populateTable initially to populate the attendance table with all records
        populateTable();
        
        JButton generatepayslipButton = new JButton("Generate Payslip");
        generatepayslipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if a row is selected in the salary calculation table
                int selectedRow = salarycalculationTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the employee ID from the selected row in the salary calculation table
                    String employeeId = salarycalculationTable.getValueAt(selectedRow, 0).toString();
                    // Get the selected month-year from the monthComboBox
                    String selectedMonthYear = (String) monthComboBox.getSelectedItem();
                    
                    // Retrieve the payslip numbers for the selected employee ID and month-year
                    List<Integer> payslipNumbers = PayslipDAO.getInstance().getPayslipNumbersByEmployeeIdAndMonthYear(employeeId, selectedMonthYear);
                    
                    // Display the retrieved payslip numbers in the text field
                    if (!payslipNumbers.isEmpty()) {
                        // If there's only one payslip number, directly set it to the text field
                        if (payslipNumbers.size() == 1) {
                            textfieldPayslipNo.setText(String.valueOf(payslipNumbers.get(0)));
                        } else {
                            // If there are multiple payslip numbers, display them in the text field
                            StringBuilder payslipNumbersText = new StringBuilder();
                            for (Integer payslipNumber : payslipNumbers) {
                                payslipNumbersText.append(payslipNumber).append(", ");
                            }
                            // Remove the trailing comma and space
                            if (payslipNumbersText.length() > 0) {
                                payslipNumbersText.setLength(payslipNumbersText.length() - 2);
                            }
                            textfieldPayslipNo.setText(payslipNumbersText.toString());
                        }
                        // Retrieve and populate payslip details
                        generatePayslip();
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "No payslip records found for the selected employee and month-year.", "No Payslip Records", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // If no row is selected, display a message
                    JOptionPane.showMessageDialog(mainPanel, "Please select a row in the salary calculation table.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


		generatepayslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		generatepayslipButton.setBackground(Color.WHITE);
		generatepayslipButton.setBounds(324, 691, 226, 33);
		mainPanel.add(generatepayslipButton);
		
		JButton deleteButton = new JButton("Save");
		deleteButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		deleteButton.setBackground(Color.WHITE);
		deleteButton.setBounds(582, 691, 154, 33);
		mainPanel.add(deleteButton);
		
		JLabel lblEmployeeMonthlyAttendance = new JLabel("Employee Monthly Hours Worked");
		lblEmployeeMonthlyAttendance.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblEmployeeMonthlyAttendance.setBounds(334, 306, 340, 33);
		mainPanel.add(lblEmployeeMonthlyAttendance);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(324, 350, 623, 331);
		mainPanel.add(scrollPane_1);
		
		salarycalculationTable = new JTable();
		scrollPane_1.setViewportView(salarycalculationTable);
		salarycalculationTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		// Call to initially populate the salary calculation table with all records
        populateSalaryCalculationTable();			
		
		btnCalculateHoursWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 14));
		btnCalculateHoursWorked.setBackground(Color.WHITE);
		btnCalculateHoursWorked.setBounds(721, 306, 226, 33);
		mainPanel.add(btnCalculateHoursWorked);
		btnCalculateHoursWorked.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Retrieve selected month-year from the combo box
		        String selectedMonthYear = (String) monthComboBox.getSelectedItem();

		        // Retrieve list of employees from your data source
		        List<Employee> employees = EmployeeDAO.getInstance().getAllEmployees();

		        // Calculate payslip information for each employee for the selected month-year
		        PayslipService payslipService = new PayslipService();
		        int successfulInsertions = 0;
		        List<Integer> failedEmployeeIds = new ArrayList<>();

		        for (Employee employee : employees) {
		            // Generate payslip for the employee for the selected month-year
		            Payslip payslip = payslipService.generatePayslip(employee.getEmpId(), selectedMonthYear);

		            // Insert the payslip into the database
		            boolean inserted = PayslipDAO.getInstance().insertPayslip(payslip);
		            if (inserted) {
		                successfulInsertions++;
		            } else {
		                failedEmployeeIds.add(employee.getEmpId());
		            }
		        }

		        // Display the result of the insertion process
		        if (successfulInsertions == employees.size()) {
		            JOptionPane.showMessageDialog(null, "Payslips added successfully for all employees.", "Success", JOptionPane.INFORMATION_MESSAGE);
		        } else if (successfulInsertions == 0) {
		            JOptionPane.showMessageDialog(null, "Failed to add payslips for all employees.", "Error", JOptionPane.ERROR_MESSAGE);
		        } else {
		            JOptionPane.showMessageDialog(null, "Payslips added successfully for " + successfulInsertions + " employees.\nFailed to add payslips for the following employee(s): " + failedEmployeeIds.toString(), "Partial Success", JOptionPane.WARNING_MESSAGE);
		        }

		        // Refresh the salary calculation table to reflect the updated data from the database
		        refreshSalaryCalculationTable(selectedMonthYear);
		    }
		});
		
		JLabel lblPayslip = new JLabel("Payslip No.");
		lblPayslip.setForeground(new Color(30, 55, 101));
		lblPayslip.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPayslip.setBounds(982, 96, 67, 13);
		mainPanel.add(lblPayslip);
		
		JLabel lblStartDate = new JLabel("Period Start Date");
		lblStartDate.setForeground(new Color(30, 55, 101));
		lblStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblStartDate.setBounds(1136, 94, 85, 13);
		mainPanel.add(lblStartDate);
		
		JLabel lblEmployeeId = new JLabel("Employee ID");
		lblEmployeeId.setForeground(new Color(30, 55, 101));
		lblEmployeeId.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEmployeeId.setBounds(982, 125, 67, 13);
		mainPanel.add(lblEmployeeId);
		
		JLabel lblEnddate = new JLabel("Period EndDate");
		lblEnddate.setForeground(new Color(30, 55, 101));
		lblEnddate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEnddate.setBounds(1136, 123, 85, 13);
		mainPanel.add(lblEnddate);
		
		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setForeground(new Color(30, 55, 101));
		lblEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblEmployeeName.setBounds(982, 153, 85, 13);
		mainPanel.add(lblEmployeeName);
		
		JLabel lblPosition = new JLabel("Position/Dept.");
		lblPosition.setForeground(new Color(30, 55, 101));
		lblPosition.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblPosition.setBounds(982, 184, 85, 13);
		mainPanel.add(lblPosition);		
		

		JLabel lblEarnings = new JLabel("Earnings");
		lblEarnings.setForeground(new Color(30, 55, 101));
		lblEarnings.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblEarnings.setBounds(982, 207, 85, 24);
		mainPanel.add(lblEarnings);
		
		JLabel lblMonthlyRate = new JLabel("Monthly Rate");
		lblMonthlyRate.setForeground(new Color(30, 55, 101));
		lblMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblMonthlyRate.setBounds(982, 237, 85, 13);
		mainPanel.add(lblMonthlyRate);
		
		JLabel lblDailyRate = new JLabel("Hourly Rate");
		lblDailyRate.setForeground(new Color(30, 55, 101));
		lblDailyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblDailyRate.setBounds(982, 260, 85, 13);
		mainPanel.add(lblDailyRate);
		
		JLabel lblDaysWorked = new JLabel("Hours Worked");
		lblDaysWorked.setForeground(new Color(30, 55, 101));
		lblDaysWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblDaysWorked.setBounds(982, 284, 85, 13);
		mainPanel.add(lblDaysWorked);
		
		JPanel separator_1 = new JPanel();
		separator_1.setBackground(new Color(30, 55, 101));
		separator_1.setBounds(1048, 218, 243, 4);
		mainPanel.add(separator_1);
		
		JLabel lblBenefits = new JLabel("Benefits");
		lblBenefits.setForeground(new Color(30, 55, 101));
		lblBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblBenefits.setBounds(982, 328, 85, 24);
		mainPanel.add(lblBenefits);
		
		JPanel separator_1_1 = new JPanel();
		separator_1_1.setBackground(new Color(30, 55, 101));
		separator_1_1.setBounds(1048, 339, 243, 4);
		mainPanel.add(separator_1_1);
		
		JLabel lblTotal = new JLabel("Total Benefits");
		lblTotal.setForeground(new Color(30, 55, 101));
		lblTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTotal.setBounds(982, 424, 110, 17);
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
		
		JLabel lblSSS = new JLabel("SSS");
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
		lblTotalDeductions.setBounds(982, 532, 111, 20);
		mainPanel.add(lblTotalDeductions);
		
		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setForeground(new Color(30, 55, 101));
		lblSummary.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblSummary.setBounds(982, 558, 85, 24);
		mainPanel.add(lblSummary);
		
		JPanel separator_1_1_2 = new JPanel();
		separator_1_1_2.setBackground(new Color(30, 55, 101));
		separator_1_1_2.setBounds(1057, 569, 234, 4);
		mainPanel.add(separator_1_1_2);
		
		JLabel lblGrossIncome_1 = new JLabel("Gross Income");
		lblGrossIncome_1.setForeground(new Color(30, 55, 101));
		lblGrossIncome_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblGrossIncome_1.setBounds(982, 592, 85, 13);
		mainPanel.add(lblGrossIncome_1);
		
		JLabel lblBenefits_1 = new JLabel("Total Benefits");
		lblBenefits_1.setForeground(new Color(30, 55, 101));
		lblBenefits_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblBenefits_1.setBounds(982, 611, 85, 13);
		mainPanel.add(lblBenefits_1);
		
		JLabel lblTotalDeductions_1 = new JLabel("Total Deductions");
		lblTotalDeductions_1.setForeground(new Color(30, 55, 101));
		lblTotalDeductions_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		lblTotalDeductions_1.setBounds(982, 629, 92, 13);
		mainPanel.add(lblTotalDeductions_1);
		
		JLabel lblTakeHomePay = new JLabel("TAKE HOME PAY");
		lblTakeHomePay.setForeground(new Color(30, 55, 101));
		lblTakeHomePay.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTakeHomePay.setBounds(982, 669, 111, 19);
		mainPanel.add(lblTakeHomePay);
		
		JLabel lblPayPeriod = new JLabel("Pay Period:");
		lblPayPeriod.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblPayPeriod.setBounds(646, 39, 215, 33);
		mainPanel.add(lblPayPeriod);
				
		textfieldPayslipNo = new JTextField();
		textfieldPayslipNo.setEditable(false);
		textfieldPayslipNo.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPayslipNo.setBounds(1048, 90, 76, 19);
		mainPanel.add(textfieldPayslipNo);
		textfieldPayslipNo.setColumns(10);
		
		textfieldEmployeeID = new JTextField();
		textfieldEmployeeID.setEditable(false);
		textfieldEmployeeID.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeID.setColumns(10);
		textfieldEmployeeID.setBounds(1048, 119, 76, 19);
		mainPanel.add(textfieldEmployeeID);
		
		textfieldEmployeeName = new JTextField();
		textfieldEmployeeName.setEditable(false);
		textfieldEmployeeName.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEmployeeName.setColumns(10);
		textfieldEmployeeName.setBounds(1065, 150, 226, 19);
		mainPanel.add(textfieldEmployeeName);
		
		textfieldStartDate = new JTextField();
		textfieldStartDate.setEditable(false);
		textfieldStartDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldStartDate.setColumns(10);
		textfieldStartDate.setBounds(1224, 89, 67, 19);
		mainPanel.add(textfieldStartDate);
		
		textfieldEndDate = new JTextField();
		textfieldEndDate.setEditable(false);
		textfieldEndDate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldEndDate.setColumns(10);
		textfieldEndDate.setBounds(1224, 119, 67, 19);
		mainPanel.add(textfieldEndDate);
		
		textfieldPositionDept = new JTextField();
		textfieldPositionDept.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		textfieldPositionDept.setEditable(false);
		textfieldPositionDept.setColumns(10);
		textfieldPositionDept.setBounds(1065, 181, 226, 19);
		mainPanel.add(textfieldPositionDept);		
		
		txtfieldMonthlyRate = new JTextField();
		txtfieldMonthlyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldMonthlyRate.setEditable(false);
		txtfieldMonthlyRate.setColumns(10);
		txtfieldMonthlyRate.setBounds(1099, 234, 192, 19);
		mainPanel.add(txtfieldMonthlyRate);
		
		txtfieldDailyRate = new JTextField();
		txtfieldDailyRate.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldDailyRate.setEditable(false);
		txtfieldDailyRate.setColumns(10);
		txtfieldDailyRate.setBounds(1099, 257, 192, 19);
		mainPanel.add(txtfieldDailyRate);
		
		txtfieldHoursWorked = new JTextField();
		txtfieldHoursWorked.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldHoursWorked.setEditable(false);
		txtfieldHoursWorked.setColumns(10);
		txtfieldHoursWorked.setBounds(1099, 281, 192, 19);
		mainPanel.add(txtfieldHoursWorked);
		
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
		
		txtfieldTotalDeductions = new JTextField();
		txtfieldTotalDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTotalDeductions.setEditable(false);
		txtfieldTotalDeductions.setColumns(10);
		txtfieldTotalDeductions.setBounds(1099, 533, 192, 19);
		mainPanel.add(txtfieldTotalDeductions);
		
		txtfieldSummaryGrossIncome = new JTextField();
		txtfieldSummaryGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryGrossIncome.setEditable(false);
		txtfieldSummaryGrossIncome.setColumns(10);
		txtfieldSummaryGrossIncome.setBounds(1099, 589, 192, 19);
		mainPanel.add(txtfieldSummaryGrossIncome);
		
		txtfieldSummaryBenefits = new JTextField();
		txtfieldSummaryBenefits.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryBenefits.setEditable(false);
		txtfieldSummaryBenefits.setColumns(10);
		txtfieldSummaryBenefits.setBounds(1099, 608, 192, 19);
		mainPanel.add(txtfieldSummaryBenefits);
		
		txtfieldSummaryDeductions = new JTextField();
		txtfieldSummaryDeductions.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldSummaryDeductions.setEditable(false);
		txtfieldSummaryDeductions.setColumns(10);
		txtfieldSummaryDeductions.setBounds(1099, 626, 192, 19);
		mainPanel.add(txtfieldSummaryDeductions);
		
		txtfieldTakeHomePay = new JTextField();
		txtfieldTakeHomePay.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldTakeHomePay.setEditable(false);
		txtfieldTakeHomePay.setColumns(10);
		txtfieldTakeHomePay.setBounds(1099, 669, 192, 19);
		mainPanel.add(txtfieldTakeHomePay);
		
		
		
		JPanel payslippanel = new JPanel();
		payslippanel.setBounds(966, 44, 336, 650);
		mainPanel.add(payslippanel);
		payslippanel.setLayout(null);
		
		JLabel lblEmployeePayslip = new JLabel("Employee Payslip");
		lblEmployeePayslip.setBounds(79, 5, 178, 28);
		payslippanel.add(lblEmployeePayslip);
		lblEmployeePayslip.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployeePayslip.setFont(new Font("Tw Cen MT", Font.PLAIN, 25));
		
		JLabel lblGrossIncome = new JLabel("Gross Income");
		lblGrossIncome.setBounds(21, 261, 103, 19);
		payslippanel.add(lblGrossIncome);
		lblGrossIncome.setForeground(new Color(30, 55, 101));
		lblGrossIncome.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		
		txtfieldGrossIncome = new JTextField();
		txtfieldGrossIncome.setBounds(134, 261, 192, 19);
		payslippanel.add(txtfieldGrossIncome);
		txtfieldGrossIncome.setFont(new Font("Tw Cen MT", Font.PLAIN, 12));
		txtfieldGrossIncome.setEditable(false);
		txtfieldGrossIncome.setColumns(10);
		
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
		
	}

	
	

	public void openWindow() {
		payrollsalarycalc.setVisible(true);
		
	}
	
	// Method to populate the monthComboBox with month-year combinations
    private void populateMonthComboBox() {
        TimesheetDAO dao = TimesheetDAO.getInstance();
        List<String> monthYearCombinations = dao.getUniqueMonthYearCombinations();
        for (String monthYear : monthYearCombinations) {
            monthComboBox.addItem(monthYear);
        }
    }

    // Method to populate the attendance table with all records
    private void populateTable() {
        TimesheetDAO dao = TimesheetDAO.getInstance();
        List<String[]> allRecords = dao.getAllTimesheetRecords();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Record ID");
        model.addColumn("Employee ID");
        model.addColumn("Last Name");
        model.addColumn("First Name");
        model.addColumn("Date");
        model.addColumn("Time In");
        model.addColumn("Time Out");

        for (String[] record : allRecords) {
            model.addRow(record);
        }

        employeeattendanceTable.setModel(model);
    }

    // Method to filter attendance records by selected month
    private void filterRecordsByMonthYear(String selectedMonthYear) {
        TimesheetDAO dao = TimesheetDAO.getInstance();
        List<String[]> filteredRecords = dao.getFilteredTimesheetRecords(selectedMonthYear);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Record ID");
        model.addColumn("Employee ID");
        model.addColumn("Last Name");
        model.addColumn("First Name");
        model.addColumn("Date");
        model.addColumn("Time In");
        model.addColumn("Time Out");

        for (String[] record : filteredRecords) {
            model.addRow(record);
        }

        employeeattendanceTable.setModel(model);
    }
    
 // Modify the method to populate the salary calculation table with all records by default
    private void populateSalaryCalculationTable() {
        // Retrieve all payslip records from the database
        List<Payslip> payslips = PayslipDAO.getInstance().getAllPayslips();

        DefaultTableModel salaryModel = new DefaultTableModel();
        salaryModel.addColumn("Employee ID");
        salaryModel.addColumn("Employee Name");
        salaryModel.addColumn("Employee Position");
        salaryModel.addColumn("Hourly Rate");
        salaryModel.addColumn("Total Hours Worked");
        salaryModel.addColumn("Overtime Hours");
        salaryModel.addColumn("Gross Income");

        if (payslips.isEmpty()) {
            salaryModel.addRow(new Object[]{"No records", "", "", "", "", "", ""});
        } else {
            for (Payslip payslip : payslips) {
                salaryModel.addRow(new Object[]{
                    payslip.getEmployeeId(),
                    payslip.getEmployeeName(),
                    payslip.getEmployeePosition(),
                    payslip.getHourlyRate(),
                    payslip.getTotalHours(),
                    payslip.getOvertimeHours(),
                    payslip.getGrossIncome()
                });
            }
        }

        salarycalculationTable.setModel(salaryModel);
    }

    // Modify the method to populate the salary calculation table based on selected month-year
    private void refreshSalaryCalculationTable(String selectedMonthYear) {
        if (selectedMonthYear.equals("All Records")) {
            populateSalaryCalculationTable();
            return;
        }

        // Retrieve payslip records for the selected month-year from the database
        List<Payslip> payslips = PayslipDAO.getInstance().getPayslipsByMonthYear(selectedMonthYear);

        DefaultTableModel salaryModel = new DefaultTableModel();
        salaryModel.addColumn("Employee ID");
        salaryModel.addColumn("Employee Name");
        salaryModel.addColumn("Employee Position");
        salaryModel.addColumn("Hourly Rate");
        salaryModel.addColumn("Total Hours Worked");
        salaryModel.addColumn("Overtime Hours");
        salaryModel.addColumn("Gross Income");

        if (payslips.isEmpty()) {
            salaryModel.addRow(new Object[]{"No records", "", "", "", "", "", ""});
        } else {
            for (Payslip payslip : payslips) {
                salaryModel.addRow(new Object[]{
                    payslip.getEmployeeId(),
                    payslip.getEmployeeName(),
                    payslip.getEmployeePosition(),
                    payslip.getHourlyRate(),
                    payslip.getTotalHours(),
                    payslip.getOvertimeHours(),
                    payslip.getGrossIncome()
                });
            }
        }

        salarycalculationTable.setModel(salaryModel);
    }
    
    private void generatePayslip() {
        int selectedRowIndex = salarycalculationTable.getSelectedRow();

        if (selectedRowIndex != -1) {
            Object employeeIdObject = salarycalculationTable.getValueAt(selectedRowIndex, 0);
            if (employeeIdObject != null) {
                String employeeId = employeeIdObject.toString();
                // Proceed with the rest of the method
                Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeId(employeeId);

                if (payslip != null) {
                    populateTextFieldsWithPayslip(payslip);
                } else {
                    JOptionPane.showMessageDialog(null, "Payslip details not found for selected employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Employee ID is null for selected row.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row in the salary calculation table.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void populateTextFieldsWithPayslip(Payslip payslip) {
        // Employee Details
        textfieldEmployeeID.setText(String.valueOf(payslip.getEmployeeId()));
        textfieldEmployeeName.setText(payslip.getEmployeeName());
        textfieldPositionDept.setText(payslip.getEmployeePosition());
        textfieldStartDate.setText(String.valueOf(payslip.getPeriodStartDate()));
        textfieldEndDate.setText(String.valueOf(payslip.getPeriodEndDate()));

        // Earnings
        txtfieldMonthlyRate.setText(String.valueOf(payslip.getMonthlyRate()));
        txtfieldDailyRate.setText(String.valueOf(payslip.getHourlyRate()));
        txtfieldHoursWorked.setText(String.valueOf(payslip.getTotalHours()));
        txtfieldGrossIncome.setText(String.valueOf(payslip.getGrossIncome()));

        // Benefits
        txtfieldRiceSubsidy.setText(String.valueOf(payslip.getRiceSubsidy()));
        txtfieldPhoneAllowance.setText(String.valueOf(payslip.getPhoneAllowance()));
        txtfieldClothingAllowance.setText(String.valueOf(payslip.getClothingAllowance()));
        double totalBenefits = payslip.getRiceSubsidy() + payslip.getPhoneAllowance() + payslip.getClothingAllowance();
        txtfieldTotalBenefits.setText(String.valueOf(totalBenefits));

        // Deductions
        txtfieldSSS.setText(String.valueOf(payslip.getSssContribution()));
        txtfieldPhilhealth.setText(String.valueOf(payslip.getPhilhealthContribution()));
        txtfieldPagIbig.setText(String.valueOf(payslip.getPagibigContribution()));
        double totalDeductions = payslip.getSssContribution() + payslip.getPhilhealthContribution() + payslip.getPagibigContribution();
        txtfieldTotalDeductions.setText(String.valueOf(totalDeductions));

        // Summary
        txtfieldSummaryGrossIncome.setText(String.valueOf(payslip.getGrossIncome()));
        txtfieldSummaryBenefits.setText(String.valueOf(totalBenefits));
        txtfieldSummaryDeductions.setText(String.valueOf(totalDeductions));
        double withholdingTax = payslip.getWithholdingTax();
        textFieldwithholdingtax.setText(String.valueOf(withholdingTax));

        // Net Pay
        double netPay = payslip.getGrossIncome() - totalDeductions + totalBenefits - withholdingTax;
        txtfieldTakeHomePay.setText(String.valueOf(netPay));
    }

}
