package view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
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
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import service.PermissionService;
import service.SQL_client;
import util.SessionManager;
import util.SignOutButton;

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
		hrleavemngmnt.setBounds(100, 100, 1280, 800);
		hrleavemngmnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hrleavemngmnt.getContentPane().setLayout(null);
				
		// Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/leave  mngmnt.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        hrleavemngmnt.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
		
        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(hrleavemngmnt));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);

        JLabel employeeNameLabel = new JLabel();
        employeeNameLabel.setBounds(706, 28, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Poppins Thin", Font.PLAIN, 22));
        mainPanel.add(employeeNameLabel);

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(387, 143, 834, 218);
		mainPanel.add(scrollPane);
		
		table_LeaveLog = new JTable();
		table_LeaveLog.setRowMargin(12);
		table_LeaveLog.setRowHeight(28);
		table_LeaveLog.setFont(new Font("Tahoma", Font.PLAIN, 10));
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


		approveButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		approveButton.setBackground(Color.WHITE);
		approveButton.setBounds(387, 371, 154, 35);
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

		rejectButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		rejectButton.setBackground(Color.WHITE);
		rejectButton.setBounds(581, 371, 154, 35);
		mainPanel.add(rejectButton);
		
		// Populate the leave history table
		populateAllLeaveHistoryTable(table_LeaveLog);
		
		JLabel lblEmployeeLeaveBalance = new JLabel("Employee Leave Balance");
		lblEmployeeLeaveBalance.setFont(new Font("Poppins", Font.PLAIN, 22));
		lblEmployeeLeaveBalance.setBounds(387, 462, 323, 33);
		mainPanel.add(lblEmployeeLeaveBalance);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(387, 505, 834, 238);
		mainPanel.add(scrollPane_1);
		
		table_EmpLeaveBalance = new JTable();
		table_EmpLeaveBalance.setRowMargin(12);
		table_EmpLeaveBalance.setRowHeight(28);
		table_EmpLeaveBalance.setFont(new Font("Tahoma", Font.PLAIN, 10));
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
