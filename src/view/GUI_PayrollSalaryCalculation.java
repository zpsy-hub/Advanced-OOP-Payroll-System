package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatIntelliJLaf;

import model.Employee;
import model.EmployeeHoursByPayPeriod;
import model.Payslip;
import model.User;
import DAO.EmployeeDAO;
import service.PayrollSalaryCalculationService;
import service.PayslipService;
import DAO.TimesheetDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import DAO.PayslipDAO;
import util.PermissionChecker;
import util.SessionManager;
import util.SignOutButton;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class GUI_PayrollSalaryCalculation {

	public JFrame payrollsalarycalc;
	private JTable employeeattendanceTable;
	private JTable payslipTable;
	private JTextField textfieldPayslipNo;
	private JTextField textfieldEmployeeID;
	private JTextField textfieldEmployeeName;
	private JTextField textfieldStartDate;
	private JTextField textfieldEndDate;
	private JTextField textfieldPositionDept;
	private JTextField txtfieldMonthlyRate;
	private JTextField txtfieldHourlyRate;
	private JTextField txtfieldHoursWorked;
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
    private PayrollSalaryCalculationService service;
    private JButton btnBatchProcessPayroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatIntelliJLaf.setup();
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
		this.service = new PayrollSalaryCalculationService();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FlatIntelliJLaf.setup();
		payrollsalarycalc = new JFrame();
		payrollsalarycalc.setTitle("MotorPH Payroll System");
		payrollsalarycalc.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_PayrollSalaryCalculation.class.getResource("/img/logo.png")));
		payrollsalarycalc.setBounds(100, 100, 1280, 800);
		payrollsalarycalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payrollsalarycalc.getContentPane().setLayout(null);
		
		
        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/Salary Calc.png"); 
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        payrollsalarycalc.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
		
		// Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(payrollsalarycalc));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);
        
        JLabel employeeNameLabel = new JLabel();
        employeeNameLabel.setBounds(706, 28, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(employeeNameLabel);

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
		
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(384, 168, 826, 203);
        mainPanel.add(scrollPane);

        employeeattendanceTable = new JTable(new DefaultTableModel(new Object[]{"Employee ID", "Employee Name", "Total Hours", "Overtime Hours"}, 0));
        employeeattendanceTable.setRowMargin(12);
        employeeattendanceTable.setRowHeight(28);
        scrollPane.setViewportView(employeeattendanceTable);
        employeeattendanceTable.setBorder(new LineBorder(new Color(0, 0, 0)));

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_1.setBounds(384, 495, 826, 203);
        mainPanel.add(scrollPane_1);

        payslipTable = new JTable(new DefaultTableModel(new Object[]{"Employee ID", "Employee Name", "Position", "Monthly Rate", "Total Hours", "Overtime Hours", "Gross Income", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Total Benefits", "SSS", "Philhealth", "Pag-Ibig", "Withholding Tax", "Total Deductions", "Net Pay", "Start Date", "End Date", "Payroll Period ID", "Date Generated"}, 0));
        payslipTable.setRowMargin(12);
        payslipTable.setRowHeight(28);
        scrollPane_1.setViewportView(payslipTable);
        payslipTable.setBorder(new LineBorder(new Color(0, 0, 0)));

        
        JButton generatepayslipButton = new JButton("Generate Payslip");
        generatepayslipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if a row is selected in the salary calculation table
                int selectedRow = payslipTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the employee ID from the selected row in the salary calculation table
                    String employeeId = payslipTable.getValueAt(selectedRow, 0).toString();
                    // Get the selected month-year from the monthComboBox
                    String selectedMonthYear = (String) monthComboBox.getSelectedItem();
                    
                    if (!selectedMonthYear.equals("Select Pay Period")) {
                        int payPeriodId = PayslipDAO.getPayPeriodId(selectedMonthYear);

                        // Retrieve the payslip for the selected employee ID and pay period
                        Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeIdAndPayPeriod(employeeId, payPeriodId);

                        if (payslip != null) {
                        	PayslipDialog payslipDialog = new PayslipDialog(payrollsalarycalc);
                            payslipDialog.populateTextFieldsWithPayslip(payslip);
                            payslipDialog.setLocationRelativeTo(null); 
                            payslipDialog.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "No payslip records found for the selected employee and month-year.", "No Payslip Records", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Please select a valid pay period.", "Invalid Pay Period", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // If no row is selected, display a message
                    JOptionPane.showMessageDialog(mainPanel, "Please select a row in the salary calculation table.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        generatepayslipButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        generatepayslipButton.setBackground(Color.WHITE);
        generatepayslipButton.setBounds(384, 711, 226, 33);
        mainPanel.add(generatepayslipButton);
		
		JButton exportButton = new JButton("Export");
		exportButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		exportButton.setBackground(Color.WHITE);
		exportButton.setBounds(629, 711, 154, 33);
		mainPanel.add(exportButton);
		exportButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int selectedRowIndex = payslipTable.getSelectedRow();

		        if (selectedRowIndex != -1) {
		            Object employeeIdObject = payslipTable.getValueAt(selectedRowIndex, 0);
		            if (employeeIdObject != null) {
		                String employeeId = employeeIdObject.toString();
		                // Proceed with the rest of the method
		                Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeId(employeeId);

		                if (payslip != null) {
		                    service.writePayslipDetailsToFile(payslip);
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
		});

		
		JLabel lblEmployeeMonthlyAttendance = new JLabel("Payslip Breakdown");
		lblEmployeeMonthlyAttendance.setFont(new Font("Poppins", Font.PLAIN, 18));
		lblEmployeeMonthlyAttendance.setBounds(384, 458, 340, 33);
		mainPanel.add(lblEmployeeMonthlyAttendance);
				
		JButton btnBatchProcessPayroll = new JButton("Batch Process Payroll");
		btnBatchProcessPayroll.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		btnBatchProcessPayroll.setBackground(Color.WHITE);
		btnBatchProcessPayroll.setBounds(384, 381, 226, 33);
		mainPanel.add(btnBatchProcessPayroll);

		btnBatchProcessPayroll.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Retrieve selected pay period from the combo box
	            String selectedPayPeriod = (String) monthComboBox.getSelectedItem();
	            if (!selectedPayPeriod.equals("Select Pay Period")) {
	                // Get the pay period ID from the selected pay period
	                int payPeriodId = PayslipDAO.getPayPeriodId(selectedPayPeriod);

	                // Execute the stored procedure to calculate and insert net pay
	                boolean success = PayslipDAO.executeCalculateAndInsertNetPay(payPeriodId);

	                // Show a message based on the success of the operation
	                if (success) {
	                    JOptionPane.showMessageDialog(null, "Payslips calculated and inserted successfully for " + selectedPayPeriod + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
	                    
	                    // Retrieve the updated payslips for the selected pay period
	                    List<Payslip> payslips = PayslipDAO.getInstance().getPayslipsByPayPeriod(selectedPayPeriod);
	                    System.out.println("Payslips Retrieved: " + payslips.size()); // Debug message

	                    // Populate payslipTable with the retrieved payslips
	                    populatePayslipTable(payslips);
	                } else {
	                    JOptionPane.showMessageDialog(null, "Failed to calculate and insert payslips for " + selectedPayPeriod + ".", "Error", JOptionPane.ERROR_MESSAGE);
	                }

	                // Refresh the salary calculation table to reflect the updated data from the database
	                service.refreshSalaryCalculationTable(selectedPayPeriod, payslipTable);
	            } else {
	                JOptionPane.showMessageDialog(null, "Please select a valid pay period.", "Warning", JOptionPane.WARNING_MESSAGE);
	            }
	        }
	    });
		
		// Get the list of visible buttons based on permissions
        PermissionChecker permissionChecker = new PermissionChecker(loggedInEmployee);
        List<String> visibleButtons = permissionChecker.getVisibleButtons();

        // Set the visibility of the btnBatchProcessPayroll based on permissions
        if (!visibleButtons.contains("Batch Process Payroll")) {
            btnBatchProcessPayroll.setVisible(false);
        }
		
		JLabel lblEmployeeTotalHours = new JLabel("Employee Total Hours");
		lblEmployeeTotalHours.setFont(new Font("Poppins", Font.PLAIN, 18));
		lblEmployeeTotalHours.setBounds(384, 127, 340, 33);
		mainPanel.add(lblEmployeeTotalHours);
		
		// JComboBox for selecting pay periods (pay_period_start - pay_period_end)
	    monthComboBox = new JComboBox<>();
	    monthComboBox.setBounds(766, 128, 200, 30);
	    mainPanel.add(monthComboBox);
	    monthComboBox.setFont(new Font("Poppins", Font.PLAIN, 16));

	    // Populate monthComboBox with pay periods
	    List<String> payPeriods = PayslipDAO.getDistinctPayPeriods();
	    monthComboBox.addItem("Select Pay Period");
	    for (String period : payPeriods) {
	        monthComboBox.addItem(period);
	    }

	    monthComboBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String selectedPayPeriod = (String) monthComboBox.getSelectedItem();
	            System.out.println("Selected Pay Period: " + selectedPayPeriod); // Debug message
	            if (!selectedPayPeriod.equals("Select Pay Period")) {
	                int payPeriodId = PayslipDAO.getPayPeriodId(selectedPayPeriod);
	                System.out.println("Pay Period ID: " + payPeriodId); // Debug message

	                // Retrieve payslips for the selected pay period
	                List<Payslip> payslips = PayslipDAO.getInstance().getPayslipsByPayPeriod(selectedPayPeriod);
	                System.out.println("Payslips Retrieved: " + payslips.size()); // Debug message

	                // Retrieve employee hours for the selected pay period
	                List<EmployeeHoursByPayPeriod> employeeHours = PayslipDAO.getInstance().getEmployeeHoursByPayPeriod(payPeriodId);
	                System.out.println("Employee Hours Retrieved: " + employeeHours.size()); // Debug message

	                // Populate payslipTable with the retrieved payslips
	                populatePayslipTable(payslips);

	                // Populate employeeattendanceTable with the retrieved employee hours
	                populateEmployeeHoursTable(employeeHours);

	                // Enable batch process payroll button if needed
	                btnBatchProcessPayroll.setEnabled(true);
	            } else {
	                // Clear tables
	                clearAttendanceTable();
	                clearSalaryCalculationTable();
	                // Display message to select a month-year
	                JOptionPane.showMessageDialog(null, "Please select a month-year.", "No Month-Year Selected", JOptionPane.WARNING_MESSAGE);
	                btnBatchProcessPayroll.setEnabled(false);
	            }
	        }
	    });   
		
	}

	public void openWindow() {
		payrollsalarycalc.setVisible(true);		
	}
	
	private void populateEmployeeAttendanceTable(List<EmployeeHoursByPayPeriod> employeeHoursList) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Employee ID", "Total Hours", "Overtime Hours"}, 0);
        for (EmployeeHoursByPayPeriod employeeHours : employeeHoursList) {
            model.addRow(new Object[]{employeeHours.getEmpId(), employeeHours.getTotalHours(), employeeHours.getOvertimeTotalHours()});
        }
        employeeattendanceTable.setModel(model);
    }
	
	   
	private void generatePayslip() {
	    int selectedRowIndex = payslipTable.getSelectedRow();

	    if (selectedRowIndex != -1) {
	        Object employeeIdObject = payslipTable.getValueAt(selectedRowIndex, 0);
	        if (employeeIdObject != null) {
	            String employeeId = employeeIdObject.toString();
	            // Get the selected pay period from the combo box
	            String selectedPayPeriod = (String) monthComboBox.getSelectedItem();
	            int payPeriodId = PayslipDAO.getPayPeriodId(selectedPayPeriod);

	            // Retrieve the payslip using the employee ID and pay period ID
	            Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeIdAndPayPeriod(employeeId, payPeriodId);

	            if (payslip != null) {
	                // Create and show the payslip dialog
	                PayslipDialog dialog = new PayslipDialog(payrollsalarycalc);
	                dialog.populateTextFieldsWithPayslip(payslip);
	                dialog.setVisible(true);
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



    
    // Method to format decimal values with two decimal places
    private String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.##"); 
        return df.format(value);
    }


    private void populateTextFieldsWithPayslip(Payslip payslip) {
        // Employee Details
        textfieldEmployeeID.setText(String.valueOf(payslip.getEmployeeId()));
        textfieldEmployeeName.setText(payslip.getEmployeeName());
        textfieldPositionDept.setText(payslip.getEmployeePosition());
        textfieldStartDate.setText(String.valueOf(payslip.getPeriodStartDate()));
        textfieldEndDate.setText(String.valueOf(payslip.getPeriodEndDate()));

        // Earnings
        txtfieldMonthlyRate.setText(formatDecimal(payslip.getBasicSalary()));
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

    private void populatePayslipTable(List<Payslip> payslips) {
        DefaultTableModel model = (DefaultTableModel) payslipTable.getModel();
        model.setRowCount(0); // Clear existing rows

        // Format for displaying LocalDate in the table
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (Payslip payslip : payslips) {
            Object[] row = {
                payslip.getEmployeeId(),
                payslip.getEmployeeName(),
                payslip.getEmployeePosition(),
                formatCurrency(payslip.getBasicSalary()),
                payslip.getTotalHours(),
                payslip.getOvertimeHours(),
                formatCurrency(payslip.getGrossIncome()),
                formatCurrency(payslip.getRiceSubsidy()),
                formatCurrency(payslip.getPhoneAllowance()),
                formatCurrency(payslip.getClothingAllowance()),
                formatCurrency(payslip.getTotalAllowances()),
                formatCurrency(payslip.getSssContribution()),
                formatCurrency(payslip.getPhilhealthContribution()),
                formatCurrency(payslip.getPagibigContribution()),
                formatCurrency(payslip.getWithholdingTax()),
                formatCurrency(payslip.getTotalDeductions()),
                formatCurrency(payslip.getNetPay()),
                payslip.getPeriodStartDate().format(dateFormatter),
                payslip.getPeriodEndDate().format(dateFormatter),
                payslip.getPayrollPeriodId(),
                payslip.getDateGenerated().format(dateFormatter)
            };
            model.addRow(row);
        }
    }

    private void populateEmployeeHoursTable(List<EmployeeHoursByPayPeriod> employeeHours) {
        DefaultTableModel model = (DefaultTableModel) employeeattendanceTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (EmployeeHoursByPayPeriod hours : employeeHours) {
            Object[] row = {
                hours.getEmpId(),
                hours.getEmployeeName(), 
                hours.getTotalHours(),
                hours.getOvertimeTotalHours()
            };
            model.addRow(row);
        }
    }


    private void clearAttendanceTable() {
        DefaultTableModel model = (DefaultTableModel) employeeattendanceTable.getModel();
        model.setRowCount(0); // Clear existing rows
    }

    private void clearSalaryCalculationTable() {
        DefaultTableModel model = (DefaultTableModel) payslipTable.getModel();
        model.setRowCount(0); // Clear existing rows
    }

    // Method to format currency values
    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(amount);
    }
}
