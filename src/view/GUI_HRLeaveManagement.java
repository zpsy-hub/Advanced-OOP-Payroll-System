package view;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.LeaveDetails;
import model.User;
import model.LeaveBalance;
import DAO.LeaveDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import util.SessionManager;
import util.SignOutButton;
import java.util.List;


public class GUI_HRLeaveManagement {

    public JFrame hrleavemngmnt;
    private JTable table_LeaveLog;
    private JTable table_EmpLeaveBalance;
    private static User loggedInEmployee;

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
	
	
    private void initialize() {
        hrleavemngmnt = new JFrame("HR Leave Management System");
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
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(employeeNameLabel);

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(386, 171, 834, 200);
        mainPanel.add(scrollPane);

        table_LeaveLog = new JTable();
        table_LeaveLog.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Date Submitted", "Employee ID", "Last Name", "First Name", "Leave Type",
                "Start Date", "End Date", "Days Taken", "Status", "Date Approved", "Days Remaining"
            }
        ));
        scrollPane.setViewportView(table_LeaveLog);

        JButton approveButton = new JButton("Approve");
        approveButton.addActionListener(this::approveAction);
        approveButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		approveButton.setBackground(Color.WHITE);
		approveButton.setBounds(396, 381, 154, 35);
		mainPanel.add(approveButton);

        JButton rejectButton = new JButton("Reject");
        rejectButton.addActionListener(this::rejectAction);
        mainPanel.add(rejectButton);
        rejectButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		rejectButton.setBackground(Color.WHITE);
		rejectButton.setBounds(605, 381, 154, 35);
		mainPanel.add(rejectButton);

		JLabel lblEmployeeLeaveBalance = new JLabel("Employee Leave Balance");
		lblEmployeeLeaveBalance.setFont(new Font("Poppins", Font.PLAIN, 20));
		lblEmployeeLeaveBalance.setBounds(387, 462, 323, 33);
		mainPanel.add(lblEmployeeLeaveBalance);

        JScrollPane scrollPaneBalance = new JScrollPane();
        scrollPaneBalance.setBounds(386, 505, 834, 228);
        mainPanel.add(scrollPaneBalance);

        table_EmpLeaveBalance = new JTable();
        table_EmpLeaveBalance.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Employee ID", "Last Name", "First Name", "Sick Leave", "Emergency Leave", "Vacation Leave"
            }
        ));
        scrollPaneBalance.setViewportView(table_EmpLeaveBalance);
        
        JLabel lblEmployeeLeaveRequests = new JLabel("Employee Leave Requests");
        lblEmployeeLeaveRequests.setFont(new Font("Poppins", Font.PLAIN, 20));
        lblEmployeeLeaveRequests.setBounds(386, 129, 323, 33);
        mainPanel.add(lblEmployeeLeaveRequests);

        populateAllLeaveHistoryTable();
        populateAllEmployeeLeaveBalanceTable();
    }

    private void approveAction(ActionEvent e) {
        int selectedRow = table_LeaveLog.getSelectedRow();
        if (selectedRow != -1) {
            String status = table_LeaveLog.getValueAt(selectedRow, 8).toString();
            if (!"Approved".equals(status)) {
                int empId = Integer.parseInt(table_LeaveLog.getValueAt(selectedRow, 1).toString());
                String leaveTypeName = table_LeaveLog.getValueAt(selectedRow, 4).toString();
                int leaveTypeId = LeaveDAO.getInstance().getLeaveTypeIdFromName(leaveTypeName);
                int year = LocalDate.now().getYear();
                java.sql.Date dateApproved = new java.sql.Date(System.currentTimeMillis());

                LeaveDAO.getInstance().updateLeaveStatusAndDate(empId, leaveTypeId, year, "Approved", dateApproved);

                JOptionPane.showMessageDialog(hrleavemngmnt, "Leave request approved successfully.");
                populateAllLeaveHistoryTable(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(hrleavemngmnt, "This leave request is already approved.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(hrleavemngmnt, "Please select a leave request to approve.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void rejectAction(ActionEvent e) {
        int selectedRow = table_LeaveLog.getSelectedRow();
        if (selectedRow != -1) {
            String status = table_LeaveLog.getValueAt(selectedRow, 8).toString();
            if (!"Rejected".equals(status)) {
                int empId = Integer.parseInt(table_LeaveLog.getValueAt(selectedRow, 1).toString());
                String leaveTypeName = table_LeaveLog.getValueAt(selectedRow, 4).toString();
                int leaveTypeId = LeaveDAO.getInstance().getLeaveTypeIdFromName(leaveTypeName); // Convert leave type name to ID
                int year = LocalDate.now().getYear();
                java.sql.Date dateApproved = new java.sql.Date(System.currentTimeMillis());

                LeaveDAO.getInstance().updateLeaveStatusAndDate(empId, leaveTypeId, year, "Rejected", dateApproved);

                JOptionPane.showMessageDialog(hrleavemngmnt, "Leave request rejected successfully.");
                populateAllLeaveHistoryTable(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(hrleavemngmnt, "This leave request is already rejected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(hrleavemngmnt, "Please select a leave request to reject.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void populateAllLeaveHistoryTable() {
        List<LeaveDetails> leaveHistory = LeaveDAO.getInstance().getLeaveHistory();
        DefaultTableModel model = (DefaultTableModel) table_LeaveLog.getModel();
        model.setRowCount(0); // Clear existing data
        for (LeaveDetails details : leaveHistory) {
            model.addRow(new Object[]{
                details.getLeave().getDateSubmitted(),
                details.getLeave().getEmpId(),
                details.getLastName(),
                details.getFirstName(),
                details.getLeaveTypeName(),
                details.getLeave().getStartDate(),
                details.getLeave().getEndDate(),
                details.getLeave().getDaysTaken(),
                details.getLeave().getStatus(),
                details.getLeave().getDateApproved(),
                details.getLeave().getLeaveDaysRemaining()
            });
        }
    }

    private void populateAllEmployeeLeaveBalanceTable() {
        List<LeaveBalance> balances = LeaveDAO.getInstance().getAllEmployeeLeaveBalances();
        DefaultTableModel model = (DefaultTableModel) table_EmpLeaveBalance.getModel();
        model.setRowCount(0); // Clear existing data
        for (LeaveBalance balance : balances) {
            model.addRow(new Object[]{
                balance.getEmpId(),
                balance.getEmployeeLastName(),
                balance.getEmployeeFirstName(),
                balance.getSickLeave(),
                balance.getEmergencyLeave(),
                balance.getVacationLeave()
            });
        }
    }
}
