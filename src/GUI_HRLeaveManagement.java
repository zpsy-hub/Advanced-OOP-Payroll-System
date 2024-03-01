import java.awt.EventQueue;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.LeaveLog;
import util.LeaveLogData;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_HRLeaveManagement {

	private JFrame hrleavemngmnt;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_HRLeaveManagement window = new GUI_HRLeaveManagement();
					window.hrleavemngmnt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_HRLeaveManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		hrleavemngmnt = new JFrame();
		hrleavemngmnt.setBounds(100, 100, 1315, 770);
		hrleavemngmnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hrleavemngmnt.getContentPane().setLayout(null);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 1301, 733);
		hrleavemngmnt.getContentPane().add(contentPane);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 1301, 743);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setBounds(0, 0, 299, 733);
		sidebarPanel.setLayout(null);
		sidebarPanel.setBackground(Color.WHITE);
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
		HR_LeaveMngmntButton.setEnabled(false);
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
		
		JPanel separator = new JPanel();
		separator.setBackground(new Color(30, 55, 101));
		separator.setBounds(37, 350, 130, 3);
		sidebarPanel.add(separator);
		
		JLabel HRaccessLabel = new JLabel("HR Access");
		HRaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		HRaccessLabel.setBounds(177, 332, 100, 33);
		sidebarPanel.add(HRaccessLabel);
		
		JLabel lblLeaveManagement = new JLabel("Leave Management");
		lblLeaveManagement.setBounds(340, 36, 323, 33);
		lblLeaveManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		mainPanel.add(lblLeaveManagement);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				hrleavemngmnt.dispose();
			}
		});
		signoutButton.setBounds(1160, 36, 103, 31);
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		mainPanel.add(signoutButton);
		
		JLabel employeeNameLabel = new JLabel();
		employeeNameLabel.setBounds(750, 36, 400, 33);
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		mainPanel.add(employeeNameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 79, 935, 547);
		mainPanel.add(scrollPane);
		
		table = new JTable();
		table.setRowMargin(12);
		table.setRowHeight(28);
		table.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		
		JButton approveButton = new JButton("Approve");
		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		approveButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		approveButton.setBackground(Color.WHITE);
		approveButton.setBounds(340, 650, 154, 51);
		mainPanel.add(approveButton);
		
		JButton rejectButton = new JButton("Reject");
		rejectButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		rejectButton.setBackground(Color.WHITE);
		rejectButton.setBounds(544, 650, 154, 51);
		mainPanel.add(rejectButton);
		
		try {
	        // Populate the leave history table
	        populateLeaveHistoryTable();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void openWindow() {
		hrleavemngmnt.setVisible(true);
	}
	
	// Method to populate the leave history table with leave request history for all employees
	private void populateLeaveHistoryTable() throws IOException {
	    // Create a DefaultTableModel to hold the data
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Date");
	    model.addColumn("Employee #");
	    model.addColumn("Last Name");
	    model.addColumn("First Name");
	    model.addColumn("Leave Type");
	    model.addColumn("Start Date");
	    model.addColumn("End Date");
	    model.addColumn("Total Days");
	    model.addColumn("Remaining Balance");
	    model.addColumn("Status");

	    // Fetch leave request history for all employees
	    List<LeaveLog> leaveLogs = LeaveLogData.getAllLeaveLogs();

	    // Sort leave logs by date in descending order
	    Collections.sort(leaveLogs, new Comparator<LeaveLog>() {
	        @Override
	        public int compare(LeaveLog log1, LeaveLog log2) {
	            return log2.getDate().compareTo(log1.getDate());
	        }
	    });

	    // Populate the model with leave request history data
	    for (LeaveLog leaveLog : leaveLogs) {
	        model.addRow(new Object[]{
	            leaveLog.getDate(),
	            leaveLog.getId(),
	            leaveLog.getLastName(),
	            leaveLog.getFirstName(),
	            leaveLog.getLeaveType(),
	            leaveLog.getStartDate(),
	            leaveLog.getEndDate(),
	            leaveLog.getTotalDays(),
	            leaveLog.getRemainingBalance(),
	            leaveLog.getStatus()
	        });
	    }

	    // Set the model to the leavehistoryTable
	    table.setModel(model);
	    
	}
}
