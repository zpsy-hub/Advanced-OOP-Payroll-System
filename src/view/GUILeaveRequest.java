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
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import model.LeaveBalance;
import model.LeaveRequestLog;
import model.Permission;
import model.User;
import DAO.LeaveBalanceDAO;
import DAO.LeaveRequestLogDAO;
import service.LeaveRequestService;
import service.PermissionService;
import service.SQL_client;
import util.LeaveRequestComboPopulator;
import util.SessionManager;

public class GUILeaveRequest {

	public JFrame leaverequestScreen;
	private JTable leavehistoryTable_1;
    private User loggedInEmployee;
    private JLabel leaveTotal;
    private JLabel vacationTotal;
    private JLabel sickTotal;
    private JLabel emergencyTotal;
    private JTextField textField_ComputedDays;
    private JComboBox<String> startmonthComboBox;
    private JComboBox<String> startdayComboBox;
    private JComboBox<String> startyearComboBox;
    private JComboBox<String> endmonthComboBox;
    private JComboBox<String> enddayComboBox;
    private JComboBox<String> endyearComboBox;
    JComboBox<String> leaveTypeComboBox;
    private JLabel textField;
    private DAO.LeaveBalanceDAO leaveBalanceDAO;
    private DAO.LeaveRequestLogDAO leaveLogDAO;


	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUILeaveRequest window = new GUILeaveRequest(loggedInEmployee);
                    window.leaverequestScreen.setVisible(true);
                    window.leaverequestScreen.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	/**
	 * Create the application.
	 * @throws IOException 
	 */
    public GUILeaveRequest(User loggedInEmployee) throws IOException {
        this.loggedInEmployee = loggedInEmployee;
        initialize();
        populateComboBoxes();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Initialize DAO instances
        leaveBalanceDAO = new DAO.LeaveBalanceDAO();
        leaveLogDAO = new DAO.LeaveRequestLogDAO();
        
		leaverequestScreen = new JFrame();
		leaverequestScreen.setTitle("MotorPH Payroll System");
		leaverequestScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\GitHub\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		leaverequestScreen.setBackground(new Color(255, 255, 255));
		leaverequestScreen.setBounds(100, 100, 1315, 770);
		leaverequestScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		leaverequestScreen.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(10, 0, 1301, 733);
		leaverequestScreen.getContentPane().add(mainPanel);
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

		
		//Sidebar buttons
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dashboardButton.setBackground(new Color(255, 255, 255));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		
		dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDashboard(loggedInEmployee);
                leaverequestScreen.dispose(); 
            }
            private void openDashboard(User loggedInEmployee) {
                GUIDashboard dashboard = new GUIDashboard(loggedInEmployee);
                dashboard.getDashboardScreen().setVisible(true);
            }
        });
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        if (leaverequestScreen != null) {
		        	leaverequestScreen.dispose();
		        }
		    }
		});				
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidePanel.add(payslipButton);		
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        openPayslip(loggedInEmployee);
		        leaverequestScreen.dispose(); 
		    }
		    private void openPayslip(User loggedInEmployee) {
		        GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		        payslip.openWindow();
		    }
		});	
		
		JButton HR_EmpMngmntButton = new JButton("Employee management");
		HR_EmpMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_EmpMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_EmpMngmntButton.setBackground(Color.WHITE);
		HR_EmpMngmntButton.setBounds(37, 383, 227, 31);
		sidePanel.add(HR_EmpMngmntButton);
		HR_EmpMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
					openEmployeeManagement();
					leaverequestScreen.dispose(); 
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }

		    private void openEmployeeManagement() throws IOException {
		        GUI_HREmployeeManagement employeeManagement = new GUI_HREmployeeManagement(loggedInEmployee);
		        employeeManagement.setVisible(true);
		    }
		});
		
		JButton HR_AttendanceMngmntButton = new JButton("Attendance management");
		HR_AttendanceMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_AttendanceMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_AttendanceMngmntButton.setBackground(Color.WHITE);
		HR_AttendanceMngmntButton.setBounds(37, 438, 227, 31);
		sidePanel.add(HR_AttendanceMngmntButton);
		HR_AttendanceMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement(loggedInEmployee);
				window.hrattendancemngmnt.setVisible(true);
				leaverequestScreen.dispose(); 
		    }
		});
						
		JButton HR_LeaveMngmntButton = new JButton("Leave management");
		HR_LeaveMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_LeaveMngmntButton.setBackground(Color.WHITE);
		HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
		sidePanel.add(HR_LeaveMngmntButton);
		HR_LeaveMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_HRLeaveManagement window = new GUI_HRLeaveManagement(loggedInEmployee);
		    	window.hrleavemngmnt.setVisible(true);
		    	leaverequestScreen.dispose(); 
		    }
		});
		
		JButton Payroll_SalaryCalculationButton = new JButton("Salary Calculation");
		Payroll_SalaryCalculationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Payroll_SalaryCalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		Payroll_SalaryCalculationButton.setBackground(Color.WHITE);
		Payroll_SalaryCalculationButton.setBounds(37, 383, 227, 31);
		sidePanel.add(Payroll_SalaryCalculationButton);
		Payroll_SalaryCalculationButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation(loggedInEmployee);
		    	window.payrollsalarycalc.setVisible(true);
		    	leaverequestScreen.dispose(); 
		    }
		});
		
		JButton Payroll_MonthlyReportsButton = new JButton("Monthly Summary Reports");
		Payroll_MonthlyReportsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Payroll_MonthlyReportsButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		Payroll_MonthlyReportsButton.setBackground(Color.WHITE);
		Payroll_MonthlyReportsButton.setBounds(37, 438, 227, 31);
		sidePanel.add(Payroll_MonthlyReportsButton);
		Payroll_MonthlyReportsButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_PayrollMonthlySummary window = new GUI_PayrollMonthlySummary(loggedInEmployee);
		    	window.payrollmontlysummary.setVisible(true);
		    	leaverequestScreen.dispose(); 
		    }
		});
		
		JButton IT_PermissionsManagement = new JButton("Permissions Management");
		IT_PermissionsManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		IT_PermissionsManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_PermissionsManagement.setBackground(Color.WHITE);
		IT_PermissionsManagement.setBounds(37, 383, 227, 31);
		sidePanel.add(IT_PermissionsManagement);
		IT_PermissionsManagement.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_ITPermissions window = new GUI_ITPermissions(loggedInEmployee);
				window.permissionsFrame.setVisible(true);
				leaverequestScreen.dispose(); 
		    }
		});
				
		JButton IT_CredentialsManagement = new JButton("Credentials Management");
		IT_CredentialsManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		IT_CredentialsManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_CredentialsManagement.setBackground(Color.WHITE);
		IT_CredentialsManagement.setBounds(37, 438, 227, 31);
		sidePanel.add(IT_CredentialsManagement);
		IT_CredentialsManagement.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_ITCredentialsManagement window = new GUI_ITCredentialsManagement(loggedInEmployee);
				window.usermngmntFrame.setVisible(true);
				leaverequestScreen.dispose(); 
		    }
		});
				
		JButton IT_LogsButton = new JButton("Authentication Logs");
		IT_LogsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		IT_LogsButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_LogsButton.setBackground(Color.WHITE);
		IT_LogsButton.setBounds(37, 491, 227, 31);
		sidePanel.add(IT_LogsButton);
		IT_LogsButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_ITLogs window = new GUI_ITLogs(loggedInEmployee);
		    	window.authenticationlogs.setVisible(true);
		    	leaverequestScreen.dispose(); 
		    }
		});
				
		Connection connection = SQL_client.getInstance().getConnection();
		PermissionService permissionsService = PermissionService.getInstance();
		List<Permission> userPermissions = permissionsService.getPermissionsForEmployee(loggedInEmployee.getId(), connection);

		// Map permissions to button visibility
		HR_EmpMngmntButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 1)); // Employee Management
		HR_AttendanceMngmntButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 2)); // Attendance Management
		HR_LeaveMngmntButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 3)); // Leave Management
		Payroll_SalaryCalculationButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 4)); // Salary Calculation
		Payroll_MonthlyReportsButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 5)); // Monthly Summary Report
		IT_PermissionsManagement.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 7)); // Permission Management
		IT_CredentialsManagement.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 8 )); // User Credentials Management
		IT_LogsButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 6)); // View Login Logs
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setEnabled(false);
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidePanel.add(leaverequestButton);
			
		JButton helpButton = new JButton("Help & Support");
		helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		helpButton.setBackground(Color.WHITE);
		helpButton.setBounds(37, 669, 227, 31);
		sidePanel.add(helpButton);
		
		JLabel leaverequestLabel = new JLabel("Leave Request");
		leaverequestLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		leaverequestLabel.setBounds(340, 36, 205, 33);
		mainPanel.add(leaverequestLabel);
		
		JPanel leavesPanel = new JPanel();
		leavesPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		leavesPanel.setBackground(new Color(255, 255, 255));
		leavesPanel.setBounds(340, 96, 216, 144);
		mainPanel.add(leavesPanel);
		leavesPanel.setLayout(null);
		
		JLabel totalleavesLabel = new JLabel("Total Available Leaves:");
		totalleavesLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		totalleavesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalleavesLabel.setBounds(10, 23, 196, 13);
		leavesPanel.add(totalleavesLabel);
		
		leaveTotal = new JLabel();
        leaveTotal.setForeground(new Color(12, 143, 15));
        leaveTotal.setHorizontalAlignment(SwingConstants.CENTER);
        leaveTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
        leaveTotal.setBounds(10, 63, 196, 43);
        leavesPanel.add(leaveTotal);
		
		JPanel vacationPanel = new JPanel();
		vacationPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		vacationPanel.setBackground(Color.WHITE);
		vacationPanel.setBounds(576, 96, 216, 144);
		mainPanel.add(vacationPanel);
		vacationPanel.setLayout(null);
		
		JLabel vacationLabel = new JLabel("Vacation Leaves Left:");
		vacationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		vacationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		vacationLabel.setBounds(10, 23, 196, 13);
		vacationPanel.add(vacationLabel);
		
        vacationTotal = new JLabel();
        vacationTotal.setHorizontalAlignment(SwingConstants.CENTER);
        vacationTotal.setForeground(new Color(0, 0, 0));
        vacationTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
        vacationTotal.setBounds(10, 61, 196, 43);
        vacationPanel.add(vacationTotal);
		
		JPanel sickPanel = new JPanel();
		sickPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		sickPanel.setBackground(Color.WHITE);
		sickPanel.setBounds(813, 96, 216, 144);
		mainPanel.add(sickPanel);
		sickPanel.setLayout(null);
		
		JLabel sickleavesLabel = new JLabel("Sick Leaves Left:");
		sickleavesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sickleavesLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		sickleavesLabel.setBounds(10, 24, 196, 13);
		sickPanel.add(sickleavesLabel);
		
        sickTotal = new JLabel();
        sickTotal.setHorizontalAlignment(SwingConstants.CENTER);
        sickTotal.setForeground(Color.BLACK);
        sickTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
        sickTotal.setBounds(10, 59, 196, 43);
        sickPanel.add(sickTotal);
		
		JPanel emergencyPanel = new JPanel();
		emergencyPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		emergencyPanel.setBackground(Color.WHITE);
		emergencyPanel.setBounds(1049, 96, 216, 144);
		mainPanel.add(emergencyPanel);
		emergencyPanel.setLayout(null);
		
		JLabel emergencyleavesLabel = new JLabel("Emergency Leaves Left:");
		emergencyleavesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emergencyleavesLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		emergencyleavesLabel.setBounds(10, 22, 196, 13);
		emergencyPanel.add(emergencyleavesLabel);
		
        emergencyTotal = new JLabel();
        emergencyTotal.setHorizontalAlignment(SwingConstants.CENTER);
        emergencyTotal.setForeground(Color.BLACK);
        emergencyTotal.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
        emergencyTotal.setBounds(10, 56, 196, 43);
        emergencyPanel.add(emergencyTotal);         
    		
		JLabel leaveapplicationLabel = new JLabel("Leave Application");
		leaveapplicationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		leaveapplicationLabel.setBounds(340, 273, 280, 33);
		mainPanel.add(leaveapplicationLabel);
		
		JPanel LineSeparator = new JPanel();
		LineSeparator.setBackground(new Color(30, 55, 101));
		LineSeparator.setBorder(new LineBorder(new Color(30, 55, 101), 0));
		LineSeparator.setBounds(340, 316, 370, 1);
		mainPanel.add(LineSeparator);
		
		JLabel lblSelectLeaveType = new JLabel("Select Leave Type:");
		lblSelectLeaveType.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblSelectLeaveType.setBounds(340, 337, 160, 21);
		mainPanel.add(lblSelectLeaveType);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblStartDate.setBounds(340, 399, 100, 21);
		mainPanel.add(lblStartDate);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblMonth.setBounds(439, 399, 53, 21);
		mainPanel.add(lblMonth);

		JLabel lblDay = new JLabel("Day:");
		lblDay.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblDay.setBounds(439, 441, 53, 21);
		mainPanel.add(lblDay);		
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblYear.setBounds(576, 441, 44, 21);
		mainPanel.add(lblYear);
		
		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblEndDate.setBounds(340, 505, 100, 21);
		mainPanel.add(lblEndDate);
		
		JLabel lblMonth_1 = new JLabel("Month:");
		lblMonth_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblMonth_1.setBounds(439, 505, 53, 21);
		mainPanel.add(lblMonth_1);		

		JLabel lblYear_1 = new JLabel("Year:");
		lblYear_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblYear_1.setBounds(576, 547, 44, 21);
		mainPanel.add(lblYear_1);	
		
		JLabel lblDay_1 = new JLabel("Day:");
		lblDay_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblDay_1.setBounds(439, 547, 53, 21);
		mainPanel.add(lblDay_1);
				
		//Combo boxes
		leaveTypeComboBox = new JComboBox<>();
		leaveTypeComboBox.setBackground(new Color(255, 255, 255));
		leaveTypeComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		leaveTypeComboBox.setBounds(510, 331, 200, 32);
		mainPanel.add(leaveTypeComboBox);
		
		startmonthComboBox = new JComboBox<String>();
		startmonthComboBox.setBackground(new Color(255, 255, 255));
		startmonthComboBox.setMaximumRowCount(13);
		startmonthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		startmonthComboBox.setBounds(510, 393, 200, 32);
		mainPanel.add(startmonthComboBox);

		startdayComboBox = new JComboBox<String>();
		startdayComboBox.setBackground(new Color(255, 255, 255));
		startdayComboBox.setMaximumRowCount(32);
		startdayComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		startdayComboBox.setBounds(510, 435, 53, 32);
		mainPanel.add(startdayComboBox);
		
		startyearComboBox = new JComboBox<String>();
		startyearComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		startyearComboBox.setBackground(Color.WHITE);
		startyearComboBox.setBounds(628, 435, 82, 32);
		mainPanel.add(startyearComboBox);		

		endmonthComboBox = new JComboBox<String>();
		endmonthComboBox.setMaximumRowCount(13);
		endmonthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		endmonthComboBox.setBackground(Color.WHITE);
		endmonthComboBox.setBounds(510, 499, 200, 32);
		mainPanel.add(endmonthComboBox);
		
		enddayComboBox = new JComboBox<String>();
		enddayComboBox.setMaximumRowCount(32);
		enddayComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		enddayComboBox.setBackground(Color.WHITE);
		enddayComboBox.setBounds(510, 541, 53, 32);
		mainPanel.add(enddayComboBox);

		endyearComboBox = new JComboBox<String>();
		endyearComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		endyearComboBox.setBackground(Color.WHITE);
		endyearComboBox.setBounds(628, 541, 82, 32);
		mainPanel.add(endyearComboBox);
		
		
		JPanel leavehistoryPanel = new JPanel();
		leavehistoryPanel.setBounds(736, 331, 523, 381);
		leavehistoryPanel.setBackground(new Color(255, 255, 255));
		mainPanel.add(leavehistoryPanel);
		leavehistoryPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		leavehistoryPanel.add(scrollPane);
		
		leavehistoryTable_1 = new JTable();
		scrollPane.setViewportView(leavehistoryTable_1);
		leavehistoryTable_1.setRowHeight(28);
		leavehistoryTable_1.setBounds(736, 331, 523, 381);
		leavehistoryTable_1.setRowSelectionAllowed(false);
		leavehistoryTable_1.setEnabled(false);
		leavehistoryTable_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		leavehistoryTable_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		// Set the table model using populateLeaveHistoryTable method
		populateLeaveHistoryTable(leavehistoryTable_1);

		JButton sendleaveButton = new JButton("Send Leave Request");
		sendleaveButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		sendleaveButton.setBackground(Color.WHITE);
		sendleaveButton.setBounds(510, 652, 200, 60);
		mainPanel.add(sendleaveButton);
		
		sendleaveButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String leaveType = (String) leaveTypeComboBox.getSelectedItem();
		        String startDate = (String) startmonthComboBox.getSelectedItem() + "-" +
		                            (String) startdayComboBox.getSelectedItem() + "-" +
		                            (String) startyearComboBox.getSelectedItem();
		        String endDate = (String) endmonthComboBox.getSelectedItem() + "-" +
		                         (String) enddayComboBox.getSelectedItem() + "-" +
		                         (String) endyearComboBox.getSelectedItem();

		        LeaveRequestService leaveRequestService = new LeaveRequestService();
		        boolean leaveSubmitted = leaveRequestService.submitLeaveRequest(loggedInEmployee, leaveType, startDate, endDate);
		        if (leaveSubmitted) {
		            // Refresh the leave history table
		        	populateLeaveHistoryTable(leavehistoryTable_1);
		            // Update leave balance data
		            updateLeaveData();
		        }
		    }
		});
		
		JPanel LineSeparator_1 = new JPanel();
		LineSeparator_1.setBorder(new LineBorder(new Color(30, 55, 101), 0));
		LineSeparator_1.setBackground(new Color(30, 55, 101));
		LineSeparator_1.setBounds(736, 316, 529, 1);
		mainPanel.add(LineSeparator_1);
		
		JLabel leavehistoryLabel = new JLabel("Leave Request History");
		leavehistoryLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		leavehistoryLabel.setBounds(736, 273, 335, 33);
		mainPanel.add(leavehistoryLabel);

			
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				leaverequestScreen.dispose();
			}
		});
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JLabel employeeNameLabel = new JLabel("");
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		employeeNameLabel.setBounds(746, 36, 400, 33);
		mainPanel.add(employeeNameLabel);
		
		JLabel lblTotalDays = new JLabel("Total Days: ");
		lblTotalDays.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalDays.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblTotalDays.setBounds(340, 600, 120, 21);
		mainPanel.add(lblTotalDays);
		
		textField_ComputedDays = new JTextField();
		textField_ComputedDays.setEditable(false);
		textField_ComputedDays.setHorizontalAlignment(SwingConstants.CENTER);
		textField_ComputedDays.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		textField_ComputedDays.setBounds(510, 594, 200, 32);
		mainPanel.add(textField_ComputedDays);
		textField_ComputedDays.setColumns(10);
		
		textField = new JLabel();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		textField.setBounds(300, 594, 200, 32);
		mainPanel.add(textField);
		
		JButton ClearFormButton = new JButton("Clear Form");
	    ClearFormButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
	    ClearFormButton.setBackground(Color.WHITE);
	    ClearFormButton.setBounds(330, 652, 170, 60);
	    mainPanel.add(ClearFormButton);
	    updateLeaveData();    
	    // Add ActionListener to ClearFormButton
	    ClearFormButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Reset combo boxes to their default values
	            leaveTypeComboBox.setSelectedIndex(0);
	            startyearComboBox.setSelectedIndex(0);
	            startmonthComboBox.setSelectedIndex(0);
	            startdayComboBox.setSelectedIndex(0);
	            endyearComboBox.setSelectedIndex(0);
	            endmonthComboBox.setSelectedIndex(0);
	            enddayComboBox.setSelectedIndex(0);
	            // Clear computed days text field
	            textField_ComputedDays.setText("");
	        }
	    });
	    
        try {
            populateComboBoxes(); 
            calculateTotalDays();
        } catch (IOException e) {
            e.printStackTrace();
        }
               
     // Add action listeners to combo boxes to trigger calculation of total days upon user input
        startmonthComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

        startdayComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

        startyearComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

        endmonthComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

        enddayComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

        endyearComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

        leaveTypeComboBox.addActionListener(e -> {
            if (!isAnyComboBoxAtDefaultSelection()) {
                calculateTotalDays();
            }
        });

		
		// Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
	}
	
	// Method to fetch and update leave data for the logged-in employee
    private void updateLeaveData() {
        try {
            // Fetch leave data for the logged-in employee using DAO
            LeaveBalance leave = leaveBalanceDAO.getLeaveBalanceByEmployeeId(loggedInEmployee.getId());

            // Update labels with leave data
            if (leave != null) {
                int totalLeaves = leave.getVacationLeave() + leave.getSickLeave() + leave.getEmergencyLeave();
                leaveTotal.setText(String.valueOf(totalLeaves));
                vacationTotal.setText(String.valueOf(leave.getVacationLeave()));
                sickTotal.setText(String.valueOf(leave.getSickLeave()));
                emergencyTotal.setText(String.valueOf(leave.getEmergencyLeave()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public void openWindow() {
        leaverequestScreen.setVisible(true);
    }
     
    // Method to populate the combo boxes
    private void populateComboBoxes() throws IOException {
        LeaveRequestComboPopulator.populateMonths(startmonthComboBox);
        LeaveRequestComboPopulator.populateDays(startdayComboBox, 1, 12);
        LeaveRequestComboPopulator.populateCurrentYear(startyearComboBox);
        LeaveRequestComboPopulator.populateMonths(endmonthComboBox);
        LeaveRequestComboPopulator.populateDays(enddayComboBox, 1, 12);
        LeaveRequestComboPopulator.populateCurrentYear(endyearComboBox);
        LeaveRequestComboPopulator.populateLeaveTypes(leaveTypeComboBox);
    }
    
    private boolean isAnyComboBoxAtDefaultSelection() {
        return startyearComboBox.getSelectedIndex() == 0 ||
               startmonthComboBox.getSelectedIndex() == 0 ||
               startdayComboBox.getSelectedIndex() == 0 ||
               endyearComboBox.getSelectedIndex() == 0 ||
               endmonthComboBox.getSelectedIndex() == 0 ||
               enddayComboBox.getSelectedIndex() == 0 ||
               leaveTypeComboBox.getSelectedIndex() == 0;
    }
   
    private void calculateTotalDays() {
        // Clear the total days text field
        textField_ComputedDays.setText("");

        // Check if any combo box is at default selection
        if (isAnyComboBoxAtDefaultSelection()) {
            // If any combo box is at default selection, exit the method
            return;
        }

        // Get selected values from combo boxes
        String startYear = (String) startyearComboBox.getSelectedItem();
        String startMonth = (String) startmonthComboBox.getSelectedItem();
        String startDay = (String) startdayComboBox.getSelectedItem();
        String endYear = (String) endyearComboBox.getSelectedItem();
        String endMonth = (String) endmonthComboBox.getSelectedItem();
        String endDay = (String) enddayComboBox.getSelectedItem();

        // Calculate total days using LeaveRequestService method
        int totalDays = LeaveRequestService.calculateTotalDays(startYear, startMonth, startDay, endYear, endMonth, endDay);

        // Check if total days is less than 0 or equal to 0
        if (totalDays < 0) {
            // Handle error condition
            JOptionPane.showMessageDialog(leaverequestScreen, "Error calculating total days.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (totalDays == 0) {
            // Handle case where end date is earlier than start date
            JOptionPane.showMessageDialog(leaverequestScreen, "End date cannot be earlier than the start date.", "Error", JOptionPane.ERROR_MESSAGE);
            textField_ComputedDays.setText("Error");
            return;
        }

        // Retrieve the leave balance for the employee and the selected leave type
        DAO.LeaveBalanceDAO leaveBalanceDAO = DAO.LeaveBalanceDAO.getInstance();
        LeaveBalance leaveBalance = leaveBalanceDAO.getLeaveBalanceByEmployeeId(loggedInEmployee.getId());
        int leaveTallyBalance = 0;

        // Check if the leave balance is not null and get the balance based on the selected leave type
        if (leaveBalance != null) {
            String selectedLeaveType = (String) leaveTypeComboBox.getSelectedItem();
            switch (selectedLeaveType) {
                case "Sick Leave":
                    leaveTallyBalance = leaveBalance.getSickLeave();
                    break;
                case "Vacation Leave":
                    leaveTallyBalance = leaveBalance.getVacationLeave();
                    break;
                case "Emergency Leave":
                    leaveTallyBalance = leaveBalance.getEmergencyLeave();
                    break;
                default:
                    // Handle invalid leave type
                    JOptionPane.showMessageDialog(leaverequestScreen, "Invalid leave type.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Check if the total days exceed the leave tally balance
            if (totalDays > leaveTallyBalance) {
                JOptionPane.showMessageDialog(leaverequestScreen, "Insufficient leave balance. Maximum allowed days: " + leaveTallyBalance, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // Handle case where leave balance is not found for the employee
            JOptionPane.showMessageDialog(leaverequestScreen, "Leave balance not found for employee.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Set the calculated total days in the text field
        textField_ComputedDays.setText(String.valueOf(totalDays));
    }

    private void populateLeaveHistoryTable(JTable table) {
        try {
            // Retrieve leave request logs for the logged-in employee
            List<LeaveRequestLog> leaveLogs = DAO.LeaveRequestLogDAO.getInstance().getLeaveLogsByEmployeeId(loggedInEmployee.getId());

            // Create a DefaultTableModel
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Timestamp");
            model.addColumn("ID");
            model.addColumn("Last Name");
            model.addColumn("First Name");
            model.addColumn("Leave Type");
            model.addColumn("Start Date");
            model.addColumn("End Date");
            model.addColumn("Total Days");
            model.addColumn("Leave Balance");
            model.addColumn("Status");

            // Populate the model with leave request logs
            for (LeaveRequestLog leaveLog : leaveLogs) {
                model.addRow(new Object[]{
                    leaveLog.getTimestamp(),
                    leaveLog.getId(),
                    leaveLog.getEmployeeLastName(),
                    leaveLog.getEmployeeFirstName(),
                    leaveLog.getLeaveType(),
                    leaveLog.getDateStart(),
                    leaveLog.getDateEnd(),
                    leaveLog.getDaysTotal(),
                    leaveLog.getLeaveBalance(),
                    leaveLog.getStatus()
                });
            }

            // Set the model for the table
            leavehistoryTable_1.setModel(model);

            
            // Enable sorting
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
}


