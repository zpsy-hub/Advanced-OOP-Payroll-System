package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import model.MonthlySummaryReport;
import model.User;
import service.MonthlySummaryReportDAO;
import service.PayrollSalaryCalculationService;
import util.SessionManager;
import com.opencsv.CSVWriter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class GUI_PayrollMonthlySummary {

	JFrame payrollmontlysummary;
	 private static User loggedInEmployee;
	 private PayrollSalaryCalculationService service;
	 private JTable MonhlySummarytable;
	 private MonthlySummaryReportDAO summaryReportDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_PayrollMonthlySummary window = new GUI_PayrollMonthlySummary(loggedInEmployee);
					window.payrollmontlysummary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param loggedInEmployee2 
	 */
	public GUI_PayrollMonthlySummary(User loggedInEmployee) {
		GUI_PayrollMonthlySummary.loggedInEmployee = loggedInEmployee;
		 this.service = new PayrollSalaryCalculationService();
		 this.summaryReportDAO = new MonthlySummaryReportDAO();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		payrollmontlysummary = new JFrame();
		payrollmontlysummary.setTitle("MotorPH Payroll System");
		payrollmontlysummary.setBounds(100, 100, 1315, 770);
		payrollmontlysummary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payrollmontlysummary.getContentPane().setLayout(null);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(null);
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setBounds(0, 0, 299, 733);
		payrollmontlysummary.getContentPane().add(sidePanel);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidePanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
                payrollmontlysummary.dispose();
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
		        payrollmontlysummary.dispose();
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
		    	payrollmontlysummary.dispose();    		        	    
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
				payrollmontlysummary.dispose(); 
		    }
		});	
		
		JButton salarycalculationButton = new JButton("Salary Calculation");
		salarycalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		salarycalculationButton.setBackground(Color.WHITE);
		salarycalculationButton.setBounds(37, 411, 227, 31);
		sidePanel.add(salarycalculationButton);
		salarycalculationButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation(loggedInEmployee);
		    	window.payrollsalarycalc.setVisible(true);
		    	payrollmontlysummary.dispose(); 
		    }
		});
		
		JButton monthlyreportButton = new JButton("Monthly Summary Report");
		monthlyreportButton.setEnabled(false);
		monthlyreportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthlyreportButton.setBackground(Color.WHITE);
		monthlyreportButton.setBounds(37, 473, 227, 31);
		sidePanel.add(monthlyreportButton);
		
		JLabel payrollaccessLabel = new JLabel("Payroll Access");
		payrollaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payrollaccessLabel.setBounds(139, 354, 138, 33);
		sidePanel.add(payrollaccessLabel);
		
		JPanel separator = new JPanel();
		separator.setBackground(new Color(30, 55, 101));
		separator.setBounds(37, 372, 88, 3);
		sidePanel.add(separator);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(300, 0, 1000, 733);
		payrollmontlysummary.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblMonthlySalaryReport = new JLabel("MonthlyPayroll Summary Report");
		lblMonthlySalaryReport.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		lblMonthlySalaryReport.setBounds(20, 34, 508, 33);
		panel.add(lblMonthlySalaryReport);
		
		JLabel lblPayPeriod = new JLabel("Pay Period:");
		lblPayPeriod.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblPayPeriod.setBounds(20, 92, 215, 33);
		panel.add(lblPayPeriod);		
		
		JButton exportButton = new JButton("Export");
		exportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		exportButton.setBackground(Color.WHITE);
		exportButton.setBounds(356, 92, 154, 33);
		panel.add(exportButton);
		exportButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        exportToCSV();
		    }
		});

		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(867, 36, 103, 31);
		panel.add(signoutButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 145, 950, 500);
		panel.add(scrollPane);
		
		MonhlySummarytable = new JTable();
		scrollPane.setViewportView(MonhlySummarytable);
		
		JComboBox<String> monthComboBox = new JComboBox<String>();
		monthComboBox.setMaximumRowCount(13);
		monthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		monthComboBox.setBounds(125, 93, 200, 30);
		panel.add(monthComboBox);
		monthComboBox.addItem("Select Month-Year");
		// Populate monthComboBox with month-year combinations
        service.populateMonthComboBox(monthComboBox);
        // Action listener for monthComboBox
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMonth = (String) monthComboBox.getSelectedItem();
                if (!selectedMonth.equals("Select Month-Year")) {
                    displayMonthlySummary(selectedMonth);
                    // Enable export button
                    exportButton.setEnabled(true);
                } else {
                    // Disable export button
                    exportButton.setEnabled(false);
                }
            }
        });
	}

	
	private void displayMonthlySummary(String selectedMonth) {
	    List<MonthlySummaryReport> monthlySummaryReports = summaryReportDAO.generateMonthlySummaryReport(selectedMonth);

	    if (monthlySummaryReports.isEmpty()) {
	        JOptionPane.showMessageDialog( MonhlySummarytable, "No records available for the selected month-year.", "No Records", JOptionPane.INFORMATION_MESSAGE);
	        // Clear the table
	        DefaultTableModel model = (DefaultTableModel) MonhlySummarytable.getModel();
	        model.setRowCount(0); // Clear the table
	        return;
	    }

	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Employee ID");
	    model.addColumn("Employee Name");
	    model.addColumn("Position");
	    model.addColumn("Gross Income (PHP)");
	    model.addColumn("SSS Number");
	    model.addColumn("SSS Contribution (PHP)");
	    model.addColumn("PhilHealth Number");
	    model.addColumn("PhilHealth Contribution (PHP)");
	    model.addColumn("Pagibig Number");
	    model.addColumn("Pagibig Contribution (PHP)");
	    model.addColumn("TIN Number");
	    model.addColumn("Withholding Tax (PHP)");
	    model.addColumn("Net Pay (PHP)");

	    double totalGrossIncome = 0;
	    double totalSssContribution = 0;
	    double totalPhilhealthContribution = 0;
	    double totalPagibigContribution = 0;
	    double totalWithholdingTax = 0;
	    double totalNetPay = 0;

	    for (MonthlySummaryReport report : monthlySummaryReports) {
	        totalGrossIncome += report.getGrossIncome();
	        totalSssContribution += report.getSssContribution();
	        totalPhilhealthContribution += report.getPhilhealthContribution();
	        totalPagibigContribution += report.getPagibigContribution();
	        totalWithholdingTax += report.getWithholdingTax();
	        totalNetPay += report.getNetPay();

	        model.addRow(new Object[]{
	                report.getEmployeeId(),
	                report.getEmployeeName(),
	                report.getPosition(),
	                formatCurrency(report.getGrossIncome()),
	                report.getSssNumber(),
	                formatCurrency(report.getSssContribution()),
	                report.getPhilhealthNumber(),
	                formatCurrency(report.getPhilhealthContribution()),
	                report.getPagibigNumber(),
	                formatCurrency(report.getPagibigContribution()),
	                report.getTinNumber(),
	                formatCurrency(report.getWithholdingTax()),
	                formatCurrency(report.getNetPay())
	        });
	    }

	    // Add a row for the totals
	    model.addRow(new Object[]{
	            "Total",
	            "",
	            "",
	            formatCurrency(totalGrossIncome),
	            "",
	            formatCurrency(totalSssContribution),
	            "",
	            formatCurrency(totalPhilhealthContribution),
	            "",
	            formatCurrency(totalPagibigContribution),
	            "",
	            formatCurrency(totalWithholdingTax),
	            formatCurrency(totalNetPay)
	    });

	    MonhlySummarytable.setModel(model);
	}


	private String formatCurrency(double amount) {
	    return "PHP " + String.format("%.2f", amount);
	}
	
	private void exportToCSV() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Save CSV File");
	    fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
	    int userSelection = fileChooser.showSaveDialog(payrollmontlysummary);
	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        try {
	            String filePath = fileChooser.getSelectedFile().getPath();
	            if (!filePath.toLowerCase().endsWith(".csv")) {
	                filePath += ".csv"; // Ensure the file has the .csv extension
	            }

	            FileWriter fileWriter = new FileWriter(filePath);
	            CSVWriter writer = new CSVWriter(fileWriter);

	            // Write header information
	            writer.writeNext(new String[]{"MotorPH"});
	            writer.writeNext(new String[]{"7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City"});
	            writer.writeNext(new String[]{"Phone: (028) 911-5071 / (028) 911-5072 / (028) 911-5073"});
	            writer.writeNext(new String[]{"Email: corporate@motorph.com"});
	            writer.writeNext(new String[]{"Pay period: (Month-Year)"});
	            
	            // Write an empty line as a separator
	            writer.writeNext(new String[]{});
	            
	            writer.writeNext(new String[]{"MONTHLY PAYROLL SUMMARY REPORT"});

	            // Write an empty line as a separator
	            writer.writeNext(new String[]{});

	            // Write column headers
	            String[] headers = {"Employee ID", "Employee Name", "Position", "Gross Income (PHP)",
	                                "SSS Number", "SSS Contribution (PHP)", "PhilHealth Number",
	                                "PhilHealth Contribution (PHP)", "Pagibig Number", "Pagibig Contribution (PHP)",
	                                "TIN Number", "Withholding Tax (PHP)", "Net Pay (PHP)"};
	            writer.writeNext(headers);

	            // Write table data
	            DefaultTableModel model = (DefaultTableModel) MonhlySummarytable.getModel();
	            for (int i = 0; i < model.getRowCount(); i++) {
	                String[] rowData = new String[model.getColumnCount()];
	                for (int j = 0; j < model.getColumnCount(); j++) {
	                    rowData[j] = model.getValueAt(i, j).toString();
	                }
	                writer.writeNext(rowData);
	            }

	            writer.close();
	            JOptionPane.showMessageDialog(payrollmontlysummary, "CSV file has been created successfully at: " + filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public void openWindow() {
		payrollmontlysummary.setVisible(true);
	}
}

