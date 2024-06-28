package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatIntelliJLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import model.LeaveDetails;
import model.User;
import model.LeaveBalance;
import DAO.LeaveDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import util.SessionManager;
import util.SignOutButton;

public class GUI_HRLeaveManagement {

    public JFrame hrleavemngmnt;
    private JTable table_LeaveLog;
    private JTable table_EmpLeaveBalance;
    private User loggedInEmployee;

    public static void main(String[] args) {
    	 FlatIntelliJLaf.setup();
    	 
        EventQueue.invokeLater(() -> {
            try {
                User loggedInEmployee = SessionManager.getLoggedInUser();
                GUI_HRLeaveManagement window = new GUI_HRLeaveManagement(loggedInEmployee);
                window.hrleavemngmnt.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GUI_HRLeaveManagement(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        initialize();
    }

    private void initialize() {
    	 FlatIntelliJLaf.setup();
    	 
        hrleavemngmnt = new JFrame("HR Leave Management System");
        hrleavemngmnt.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_HRLeaveManagement.class.getResource("/img/logo.png")));
        hrleavemngmnt.setTitle("MotorPH Payroll System");
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
        table_LeaveLog.setRowMargin(12);
        table_LeaveLog.setRowHeight(28);
        table_LeaveLog.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
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
        table_EmpLeaveBalance.setRowMargin(12);
        table_EmpLeaveBalance.setRowHeight(28);
        table_EmpLeaveBalance.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
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
            if (status != null && !"Approved".equals(status)) {
                try {
                    java.sql.Date dateApproved = new java.sql.Date(System.currentTimeMillis());

                    // Identify employee ID, leave type, and days taken
                    int empId = Integer.parseInt(table_LeaveLog.getValueAt(selectedRow, 1).toString());
                    String leaveTypeName = (String) table_LeaveLog.getValueAt(selectedRow, 4);
                    int leaveTypeId = LeaveDAO.getInstance().getLeaveTypeIdFromName(leaveTypeName);
                    int daysTaken = Integer.parseInt(table_LeaveLog.getValueAt(selectedRow, 7).toString());

                    // Update leave request status and approval date in the database
                    LeaveDAO leaveDAO = LeaveDAO.getInstance();
                    boolean success = leaveDAO.updateLeaveStatusAndDateByEmpAndType(empId, leaveTypeId, dateApproved, "Approved");

                    if (success) {
                        // Calculate remaining leave days
                        int currentBalance = leaveDAO.getLeaveBalanceByEmployeeIdAndType(empId, leaveTypeId);
                        int newBalance = currentBalance - daysTaken;

                        // Update leave balance
                        boolean balanceUpdated = leaveDAO.updateEmployeeLeaveBalance(empId, leaveTypeId, LocalDate.now().getYear(), daysTaken, newBalance);

                        if (balanceUpdated) {
                            JOptionPane.showMessageDialog(hrleavemngmnt, "Leave request approved successfully.");
                            populateAllLeaveHistoryTable(); // Refresh the leave history table
                            populateAllEmployeeLeaveBalanceTable(); // Refresh the leave balance table
                        } else {
                            JOptionPane.showMessageDialog(hrleavemngmnt, "Failed to update leave balance.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(hrleavemngmnt, "Failed to approve leave request.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(hrleavemngmnt, "Invalid data format.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(hrleavemngmnt, "Some values are missing.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                try {
                    java.sql.Date dateRejected = new java.sql.Date(System.currentTimeMillis());

                    // Identify employee ID and leave type
                    int empId = Integer.parseInt(table_LeaveLog.getValueAt(selectedRow, 1).toString());
                    String leaveTypeName = (String) table_LeaveLog.getValueAt(selectedRow, 4);
                    int leaveTypeId = LeaveDAO.getInstance().getLeaveTypeIdFromName(leaveTypeName);

                    // Update leave request status to "Rejected" and set date rejected
                    LeaveDAO leaveDAO = LeaveDAO.getInstance();
                    boolean success = leaveDAO.updateLeaveStatusAndDateByEmpAndType(empId, leaveTypeId, dateRejected, "Rejected");

                    if (success) {
                        JOptionPane.showMessageDialog(hrleavemngmnt, "Leave request rejected successfully.");
                        populateAllLeaveHistoryTable(); // Refresh the leave history table
                    } else {
                        JOptionPane.showMessageDialog(hrleavemngmnt, "Failed to reject leave request.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(hrleavemngmnt, "Invalid data format.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(hrleavemngmnt, "Some values are missing.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        List<LeaveBalance> leaveBalances = LeaveDAO.getInstance().getAllEmployeeLeaveBalances();
        DefaultTableModel model = (DefaultTableModel) table_EmpLeaveBalance.getModel();
        model.setRowCount(0); // Clear existing data
        for (LeaveBalance balance : leaveBalances) {
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
