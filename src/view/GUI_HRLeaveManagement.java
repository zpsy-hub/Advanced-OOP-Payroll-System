package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.LeaveBalance;
import model.LeaveRequestLog;
import model.Permission;
import model.User;
import DAO.LeaveBalanceDAO;
import DAO.LeaveRequestLogDAO;
import service.PermissionService;
import service.SQL_client;
import util.SessionManager;
import java.awt.Cursor;

public class GUI_HRLeaveManagement {

	public JFrame hrleavemngmnt;
	private JTable table_LeaveLog;
	private JTable table_EmpLeaveBalance;
	private static User loggedInEmployee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_HRLeaveManagement window = new GUI_HRLeaveManagement(loggedInEmployee);
					window.hrleavemngmnt.setVisible(true);
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
	public GUI_HRLeaveManagement(User loggedInEmployee) {
		GUI_HRLeaveManagement.loggedInEmployee = loggedInEmployee;    
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
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
		dashboardButton.setBounds(37, 95, 227, 31);
		sidebarPanel.add(dashboardButton);
		dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
                hrleavemngmnt.dispose();
		        }
		});
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 154, 227, 31);
		sidebarPanel.add(timeInOutButton);
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();
		        hrleavemngmnt.dispose();
		        }
		});
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidebarPanel.add(payslipButton);
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		    	payslip.openWindow();
		    	hrleavemngmnt.dispose();	    		        	    
		    }
		});
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidebarPanel.add(leaverequestButton);
		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUILeaveRequest window = null;
				try {
					window = new GUILeaveRequest(loggedInEmployee);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		         window.leaverequestScreen.setVisible(true);
		         hrleavemngmnt.dispose(); 
		    }
		});
		
		JButton helpButton = new JButton("Help & Support");
		helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		helpButton.setBackground(Color.WHITE);
		helpButton.setBounds(37, 669, 227, 31);
		sidebarPanel.add(helpButton);
		
		JButton HR_EmpMngmntButton = new JButton("Employee Management");
		HR_EmpMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_EmpMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_EmpMngmntButton.setBackground(Color.WHITE);
		HR_EmpMngmntButton.setBounds(37, 383, 227, 31);
		sidebarPanel.add(HR_EmpMngmntButton);
		HR_EmpMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
					openEmployeeManagement();
					hrleavemngmnt.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }

		    // Define the openEmployeeManagement method
		    private void openEmployeeManagement() throws IOException {
		        // Create an instance of GUI_HREmployeeManagement
		        GUI_HREmployeeManagement employeeManagement = new GUI_HREmployeeManagement(loggedInEmployee);

		        // Make the employee management window visible
		        employeeManagement.setVisible(true);
		    }
		});
		
		JButton HR_AttendanceMngmntButton = new JButton("Attendance Management");
		HR_AttendanceMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_AttendanceMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_AttendanceMngmntButton.setBackground(Color.WHITE);
		HR_AttendanceMngmntButton.setBounds(37, 438, 227, 31);
		sidebarPanel.add(HR_AttendanceMngmntButton);
		HR_AttendanceMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement(loggedInEmployee);
				window.hrattendancemngmnt.setVisible(true);
				hrleavemngmnt.dispose();
		    }
		});
		
		JButton HR_LeaveMngmntButton = new JButton("Leave Management");
		HR_LeaveMngmntButton.setEnabled(false);
		HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_LeaveMngmntButton.setBackground(Color.WHITE);
		HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(HR_LeaveMngmntButton);
		
		Connection connection = SQL_client.getInstance().getConnection();
		PermissionService permissionsService = PermissionService.getInstance();
		List<Permission> userPermissions = permissionsService.getPermissionsForEmployee(loggedInEmployee.getId(), connection);

		// Map permissions to button visibility
		HR_EmpMngmntButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 1)); // Employee Management
		HR_AttendanceMngmntButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 2)); // Attendance Management
		HR_LeaveMngmntButton.setVisible(userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 3));
		
		JLabel lblLeaveManagement = new JLabel("Leave Management");
		lblLeaveManagement.setBounds(340, 36, 323, 33);
		lblLeaveManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		mainPanel.add(lblLeaveManagement);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				hrleavemngmnt.dispose();
			}
		});
		signoutButton.setBounds(1172, 41, 103, 31);
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		mainPanel.add(signoutButton);
		
		JLabel employeeNameLabel = new JLabel();
		employeeNameLabel.setBounds(750, 36, 400, 33);
		employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		mainPanel.add(employeeNameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 79, 935, 250);
		mainPanel.add(scrollPane);
		
		table_LeaveLog = new JTable();
		table_LeaveLog.setRowMargin(12);
		table_LeaveLog.setRowHeight(28);
		table_LeaveLog.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		scrollPane.setViewportView(table_LeaveLog);
		
		JButton approveButton = new JButton("Approve");
		approveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		approveButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table_LeaveLog.getSelectedRow();
		        if (selectedRow != -1) { // Ensure a row is selected
		            try {
		                // Get the timestamp of the selected leave request log
		                java.sql.Timestamp timestamp = (java.sql.Timestamp) table_LeaveLog.getValueAt(selectedRow, 0);

		                // Check if the status of the selected leave request log is already "Approved"
		                String currentStatus = (String) table_LeaveLog.getValueAt(selectedRow, 9);
		                if ("Approved".equals(currentStatus)) {
		                    JOptionPane.showMessageDialog(null, "Leave request is already approved.", "Error", JOptionPane.ERROR_MESSAGE);
		                    return; // Exit the method if already approved
		                }

		                // Update the status of the leave request log to "Approved"
		                LeaveRequestLogDAO.getInstance().updateLeaveStatus(timestamp, "Approved");

		                // Get the employee ID, leave type, and number of days from the selected row
		                int empId = (int) table_LeaveLog.getValueAt(selectedRow, 1);
		                String leaveType = (String) table_LeaveLog.getValueAt(selectedRow, 4);
		                int days = (int) table_LeaveLog.getValueAt(selectedRow, 7);

		                // Update the leave balance in the database
		                LeaveBalanceDAO.getInstance().updateNewLeaveBalance(empId, leaveType, days);

		                // Refresh the leave history table
		                populateAllLeaveHistoryTable(table_LeaveLog);

		                // Refresh the employee leave balance table
		                populateAllEmployeeLeaveBalanceTable(table_EmpLeaveBalance);

		                JOptionPane.showMessageDialog(null, "Leave request approved successfully.");
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a leave request to approve.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});


		approveButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		approveButton.setBackground(Color.WHITE);
		approveButton.setBounds(340, 350, 154, 35);
		mainPanel.add(approveButton);
		
		JButton rejectButton = new JButton("Reject");
		rejectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rejectButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table_LeaveLog.getSelectedRow();
		        if (selectedRow != -1) { // Ensure a row is selected
		            try {
		                // Get the timestamp of the selected leave request log
		                java.sql.Timestamp timestamp = (java.sql.Timestamp) table_LeaveLog.getValueAt(selectedRow, 0);
		                
		                // Check if the status of the selected leave request log is already "Rejected"
		                String currentStatus = (String) table_LeaveLog.getValueAt(selectedRow, 9);
		                if ("Rejected".equals(currentStatus)) {
		                    JOptionPane.showMessageDialog(null, "Leave request is already rejected.", "Error", JOptionPane.ERROR_MESSAGE);
		                    return; // Exit the method if already rejected
		                }

		                // Update the status of the selected leave request log to "Rejected"
		                LeaveRequestLogDAO.getInstance().updateLeaveStatus(timestamp, "Rejected");
		                
		                // Refresh only the selected row in the leave history table
		                DefaultTableModel model = (DefaultTableModel) table_LeaveLog.getModel();
		                model.setValueAt("Rejected", selectedRow, 9); // Update the status column of the selected row
		                
		                JOptionPane.showMessageDialog(null, "Leave request rejected successfully.");
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a leave request to reject.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		rejectButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		rejectButton.setBackground(Color.WHITE);
		rejectButton.setBounds(544, 350, 154, 35);
		mainPanel.add(rejectButton);
		
		// Populate the leave history table
		populateAllLeaveHistoryTable(table_LeaveLog);
		
		JLabel lblEmployeeLeaveBalance = new JLabel("Employee Leave Balance");
		lblEmployeeLeaveBalance.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		lblEmployeeLeaveBalance.setBounds(340, 417, 323, 33);
		mainPanel.add(lblEmployeeLeaveBalance);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(340, 460, 935, 250);
		mainPanel.add(scrollPane_1);
		
		table_EmpLeaveBalance = new JTable();
		table_EmpLeaveBalance.setRowMargin(12);
		table_EmpLeaveBalance.setRowHeight(28);
		table_EmpLeaveBalance.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		scrollPane_1.setViewportView(table_EmpLeaveBalance);
		
		// Populate the employee leave balance table
		populateAllEmployeeLeaveBalanceTable(table_EmpLeaveBalance);

	}

	public void openWindow() {
		hrleavemngmnt.setVisible(true);
	}
	
	private void populateAllLeaveHistoryTable(JTable table) {
	    try {
	        // Retrieve all leave request logs
	        List<LeaveRequestLog> leaveLogs = LeaveRequestLogDAO.getInstance().getAllLeaveLogs();
	        
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
	        table.setModel(model);
	    } catch (Exception e) {
	        // Handle exceptions
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void populateAllEmployeeLeaveBalanceTable(JTable table) {
	    try {
	        // Retrieve all employees' leave balance records
	        List<LeaveBalance> leaveBalances = LeaveBalanceDAO.getInstance().getAllEmployeesLeaveRecords();
	        
	        // Create a DefaultTableModel
	        DefaultTableModel model = new DefaultTableModel();
	        model.addColumn("Employee ID");
	        model.addColumn("Last Name");
	        model.addColumn("First Name");
	        model.addColumn("Sick Leave");
	        model.addColumn("Emergency Leave");
	        model.addColumn("Vacation Leave");
	        
	        // Populate the model with leave balance records
	        for (LeaveBalance leaveBalance : leaveBalances) {
	            model.addRow(new Object[]{
	                leaveBalance.getEmpId(),
	                leaveBalance.getEmployeeLastName(),
	                leaveBalance.getEmployeeFirstName(),
	                leaveBalance.getSickLeave(),
	                leaveBalance.getEmergencyLeave(),
	                leaveBalance.getVacationLeave()
	            });
	        }
	        
	        // Set the model for the table
	        table.setModel(model);
	    } catch (Exception e) {
	        // Handle exceptions
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

}
