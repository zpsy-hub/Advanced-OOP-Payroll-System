import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Employee;
import model.User;
import util.EmployeeData;

public class GUI_HREmployeeManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User loggedInEmployee;
    private EmployeeData employeeData;
    private JLabel employeeNameLabel;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = null; // Set the logged-in user here
                    GUI_HREmployeeManagement window = new GUI_HREmployeeManagement(loggedInEmployee);
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GUI_HREmployeeManagement(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        this.employeeData = new EmployeeData();
        loadData();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1315, 770);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 10, 1301, 733);
        contentPane.add(mainPanel);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setBounds(0, 0, 299, 733);
        mainPanel.add(sidebarPanel);

        JLabel motorphLabel = new JLabel("MotorPH");
        motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
        motorphLabel.setForeground(new Color(30, 55, 101));
        motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 28));
        motorphLabel.setBounds(10, 30, 279, 45);
        sidebarPanel.add(motorphLabel);

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        dashboardButton.setBackground(Color.WHITE);
        dashboardButton.setBounds(37, 95, 227, 31);
        sidebarPanel.add(dashboardButton);

        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement your action listener logic for dashboard button
            }
        });

        JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 154, 227, 31);
		sidebarPanel.add(timeInOutButton);
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidebarPanel.add(payslipButton);
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidebarPanel.add(leaverequestButton);
		
		JButton helpButton = new JButton("Help & Support");
		helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		helpButton.setBackground(Color.WHITE);
		helpButton.setBounds(37, 669, 227, 31);
		sidebarPanel.add(helpButton);
		
		JButton HR_EmpMngmntButton = new JButton("Employee management");
		HR_EmpMngmntButton.setEnabled(false);
		HR_EmpMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_EmpMngmntButton.setBackground(Color.WHITE);
		HR_EmpMngmntButton.setBounds(37, 383, 227, 31);
		sidebarPanel.add(HR_EmpMngmntButton);
		
		JButton HR_AttendanceMngmntButton = new JButton("Attendance management");
		HR_AttendanceMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_AttendanceMngmntButton.setBackground(Color.WHITE);
		HR_AttendanceMngmntButton.setBounds(37, 438, 227, 31);
		sidebarPanel.add(HR_AttendanceMngmntButton);
		
		JButton HR_LeaveMngmntButton = new JButton("Leave management");
		HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_LeaveMngmntButton.setBackground(Color.WHITE);
		HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(HR_LeaveMngmntButton);
		
		JButton Payroll_SalaryCalculationButton = new JButton("Salary Calculation");
		Payroll_SalaryCalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		Payroll_SalaryCalculationButton.setBackground(Color.WHITE);
		Payroll_SalaryCalculationButton.setBounds(37, 383, 227, 31);
		sidebarPanel.add(Payroll_SalaryCalculationButton);
		
		JButton Payroll_MonthlyReportsButton = new JButton("Monthly Summary Reports");
		Payroll_MonthlyReportsButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		Payroll_MonthlyReportsButton.setBackground(Color.WHITE);
		Payroll_MonthlyReportsButton.setBounds(37, 438, 227, 31);
		sidebarPanel.add(Payroll_MonthlyReportsButton);
		
		JLabel lblEmployeeManagement = new JLabel("Employee Management");
		lblEmployeeManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		lblEmployeeManagement.setBounds(340, 36, 323, 33);
		mainPanel.add(lblEmployeeManagement);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);

		employeeNameLabel = new JLabel(); // Initialize employeeNameLabel
	    employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
	    employeeNameLabel.setBounds(750, 36, 400, 33);
	    mainPanel.add(employeeNameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 90, 935, 300);
		mainPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);		
		
        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }

        // Populate the table with loaded data
        populateTableWithAllEmployees();
    }
	private void populateTableWithAllEmployees() {
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Employee #");
	    model.addColumn("Last Name");
	    model.addColumn("First Name");
	    model.addColumn("Birthday");
	    model.addColumn("Address");
	    model.addColumn("Phone Number");
	    model.addColumn("SSS #");
	    model.addColumn("Philhealth #");
	    model.addColumn("TIN #");
	    model.addColumn("Pag-ibig #");
	    model.addColumn("Status");
	    model.addColumn("Position");
	    model.addColumn("Immediate Supervisor");
	    model.addColumn("Basic Salary");
	    model.addColumn("Rice Subsidy");
	    model.addColumn("Phone Allowance");
	    model.addColumn("Clothing Allowance");
	    model.addColumn("Gross Semi-monthly Rate");
	    model.addColumn("Hourly Rate");

	    // Assuming employeeData.getEmployees() returns a list of Employee objects
	    for (Employee employee : employeeData.getEmployees()) {
	        model.addRow(new Object[]{
	                employee.getId(),
	                employee.getLastName(),
	                employee.getFirstName(),
	                employee.getBirthday(),
	                employee.getAddress(),
	                employee.getPhoneNumber(),
	                employee.getSssNumber(),
	                employee.getPhilhealthNumber(),
	                employee.getTinNumber(),
	                employee.getPagibigNumber(),
	                employee.getStatus(),
	                employee.getPosition(),
	                employee.getImmediateSupervisor(),
	                employee.getBasicSalary(),
	                employee.getRiceSubsidy(),
	                employee.getPhoneAllowance(),
	                employee.getClothingAllowance(),
	                employee.getGrossSemiMonthlyRate(),
	                employee.getHourlyRate()
	        });
	    }

	    table.setModel(model);
	}

    private void loadData() {
        try {
            // Load employee data from CSV file
            employeeData.loadFromCSV("src/data/Employee Database.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

