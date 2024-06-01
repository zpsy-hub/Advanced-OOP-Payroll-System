package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import model.Attendance;
import model.Employee;
import model.Permission;
import model.User;
import service.EmployeeService;
import DAO.LoginDAO;
import service.PermissionService;
import service.SQL_client;
import service.TimeSheetService;
import DAO.TimesheetDAO;

import javax.swing.JComboBox;


public class GUITimeInOut {

    private JFrame timeinoutScreen;
    private JTable timeTable;
    private User loggedInEmployee;
    private JTable table;
    private boolean timeOutRecorded = false;

    // Constructor 
    public GUITimeInOut(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        initialize();
        populateTable();
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUITimeInOut window = new GUITimeInOut();
                    window.timeinoutScreen.setVisible(true);
                    window.timeinoutScreen.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUITimeInOut() {
    	this.loggedInEmployee = loggedInEmployee;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        timeinoutScreen = new JFrame();
        timeinoutScreen.setBackground(new Color(255, 255, 255));
        timeinoutScreen.setTitle("MotorPH Payroll System");
        timeinoutScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
        timeinoutScreen.setBounds(100, 100, 1315, 770);
        timeinoutScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timeinoutScreen.getContentPane().setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1301, 733);
        timeinoutScreen.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);

        JPanel sidePanel = new JPanel();
        sidePanel.setBounds(0, 0, 299, 733);
        mainPanel.add(sidePanel);
        sidePanel.setLayout(null);

        JLabel motorphLabel = new JLabel("MotorPH");
        motorphLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
        motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
        motorphLabel.setForeground(new Color(30, 55, 101));
        motorphLabel.setBounds(10, 30, 279, 45);
        sidePanel.add(motorphLabel);

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dashboardButton.setBackground(new Color(255, 255, 255));
        dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        dashboardButton.setBounds(37, 95, 227, 31);
        sidePanel.add(dashboardButton);
        dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
                timeinoutScreen.dispose();
		        }
		});

        JButton timeInOutButton = new JButton("Time In/Out");
        timeInOutButton.setEnabled(false);
        timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        timeInOutButton.setBackground(Color.WHITE);
        timeInOutButton.setBounds(37, 155, 227, 31);
        sidePanel.add(timeInOutButton);

        JButton payslipButton = new JButton("Payslip");
        payslipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        payslipButton.setBackground(Color.WHITE);
        payslipButton.setBounds(37, 216, 227, 31);
        sidePanel.add(payslipButton);
    	payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		    	payslip.openWindow();
		    	timeinoutScreen.dispose();		    		        	    
		    }
		});

        JButton leaverequestButton = new JButton("Leave Request");
        leaverequestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        leaverequestButton.setBackground(Color.WHITE);
        leaverequestButton.setBounds(37, 277, 227, 31);
        sidePanel.add(leaverequestButton);
        leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip window = new GUIPayslip(loggedInEmployee);
				window.payslipScreen.setVisible(true);
				timeinoutScreen.dispose();	
		    }
		});	

        JButton helpButton = new JButton("Help & Support");
        helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        helpButton.setBackground(Color.WHITE);
        helpButton.setBounds(37, 669, 227, 31);
        sidePanel.add(helpButton);
        
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
					timeinoutScreen.dispose();
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
				timeinoutScreen.dispose(); 
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
		    	timeinoutScreen.dispose(); 
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
		    	timeinoutScreen.dispose(); 
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
		    	timeinoutScreen.dispose(); 
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
				timeinoutScreen.dispose(); 
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
				timeinoutScreen.dispose(); 
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
		    	timeinoutScreen.dispose(); 
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


        JLabel timeinoutLabel = new JLabel("Time In/Out");
        timeinoutLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        timeinoutLabel.setBounds(340, 36, 205, 33);
        mainPanel.add(timeinoutLabel);

        JPanel timeinoutPanel = new JPanel();
        timeinoutPanel.setBackground(new Color(255, 255, 255));
        timeinoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        timeinoutPanel.setBounds(340, 79, 923, 220);
        mainPanel.add(timeinoutPanel);
        timeinoutPanel.setLayout(null);

        JButton timeInButton = new JButton("TIME IN");
        timeInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        timeInButton.setBackground(new Color(255, 255, 255));
        timeInButton.setFont(new Font("Tahoma", Font.BOLD, 35));
        timeInButton.setBounds(42, 55, 239, 102);
        timeinoutPanel.add(timeInButton);      
        
        JButton timeOutButton = new JButton("TIME OUT");
        timeOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        timeOutButton.setFont(new Font("Tahoma", Font.BOLD, 35));
        timeOutButton.setBackground(Color.WHITE);
        timeOutButton.setBounds(640, 55, 239, 102);
        timeinoutPanel.add(timeOutButton);
 
        JLabel empStatus = new JLabel("OFF"); // Default status is OFF
        empStatus.setForeground(new Color(255, 0, 0)); // Default color is red
        empStatus.setHorizontalAlignment(SwingConstants.CENTER);
        empStatus.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
        empStatus.setBounds(291, 102, 339, 39);
        timeinoutPanel.add(empStatus);

        timeInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if there is already a time in record for the current date
                if (!TimesheetDAO.getInstance().hasTimeInRecordForToday(loggedInEmployee.getId())) {
                    // If no time in record exists, record time in for the logged-in employee
                    recordTimeIn();
                    empStatus.setText("IN");
                    empStatus.setForeground(new Color(0, 255, 0)); // Change color to green
                    timeInButton.setEnabled(false); // Disable the Time In button
                    timeOutButton.setEnabled(true); // Enable the Time Out button
                    // Repopulate the table after recording time in
                    populateTable();
                } else {
                    // Notify the user that a time in record already exists for today
                    JOptionPane.showMessageDialog(null, "Time in has already been recorded for today", "Time In Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        timeOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if there is already a time out record for the current date
                if (!TimesheetDAO.getInstance().hasTimeOutRecordForToday(loggedInEmployee.getId())) {
                    // If no time out record exists, record time out for the logged-in employee
                    recordTimeOut();
                    empStatus.setText("OUT");
                    empStatus.setForeground(new Color(255, 0, 0)); // Change color back to red
                    timeOutButton.setEnabled(false); // Disable the Time Out button
                    timeInButton.setEnabled(false); // Disable the Time In button
                    // Repopulate the table after recording time out
                    populateTable();
                } else {
                    // Notify the user that a time out record already exists for today
                    JOptionPane.showMessageDialog(null, "Time out has already been recorded for today", "Time Out Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



             
        JLabel currentstatusLabel = new JLabel("Current Status:");
        currentstatusLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
        currentstatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentstatusLabel.setBounds(291, 69, 339, 39);
        timeinoutPanel.add(currentstatusLabel);

        JButton signoutButton = new JButton("Sign Out");
        signoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		GUIlogin login = new GUIlogin();
        		login.loginScreen1.setVisible(true);
        		timeinoutScreen.dispose();
        	}
        });
        signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
        signoutButton.setBackground(Color.WHITE);
        signoutButton.setBounds(1160, 36, 103, 31);
        mainPanel.add(signoutButton);

        JLabel employeeNameLabel = new JLabel("");
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        employeeNameLabel.setBounds(750, 36, 400, 33);
        mainPanel.add(employeeNameLabel);
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Attendance Records", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(340, 355, 923, 350);
        mainPanel.add(panel);
        panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 15, 923, 346);
        panel.add(scrollPane);

        table = new JTable();
        table.setRowMargin(12);
        table.setRowHeight(28);
        table.setEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        scrollPane.setViewportView(table);
      
        
        //Combobox to filter
        JComboBox comboBoxbyMonthYear = new JComboBox();
        comboBoxbyMonthYear.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
        comboBoxbyMonthYear.setBounds(480, 319, 162, 21);
        mainPanel.add(comboBoxbyMonthYear);
        // Implement an ActionListener for the JComboBox to filter records based on the selected month-year
        comboBoxbyMonthYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMonthYear = (String) comboBoxbyMonthYear.getSelectedItem();
                // Check if the selected option is not the default option
                if (!selectedMonthYear.equals("All Records")) {
                    // Filter records based on the selected month-year and update the table
                    filterRecordsByMonthYear(selectedMonthYear);
                } else {
                    // Display all records
                    populateTable();
                }
            }
        });

        
        //Combobox for month year
        TimesheetDAO dao = TimesheetDAO.getInstance();
        List<String> monthYearCombinations = dao.getUniqueMonthYearCombinations();
        
        // Add a default option to the combo box
        comboBoxbyMonthYear.addItem("All Records");

        // Populate the JComboBox with the retrieved month-year combinations
        for (String monthYear : monthYearCombinations) {
            comboBoxbyMonthYear.addItem(monthYear);
        }
        
        JLabel lblFilterRecordsBy = new JLabel("Filter records by:");
        lblFilterRecordsBy.setHorizontalAlignment(SwingConstants.LEFT);
        lblFilterRecordsBy.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
        lblFilterRecordsBy.setBounds(340, 309, 339, 39);
        mainPanel.add(lblFilterRecordsBy);

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
    }

    private void populateTable() {
        // Check if a user is logged in
        if (loggedInEmployee != null) {
            // Instantiate TimesheetDAO
            TimesheetDAO dao = TimesheetDAO.getInstance();

            // Retrieve timesheet records for the logged-in employee
            List<String[]> timesheetRecords = dao.getLoggedInEmployeeTimesheetRecords(loggedInEmployee.getId());
            
            // Check if timesheetRecords is not null and not empty
            if (timesheetRecords != null && !timesheetRecords.isEmpty()) {
                // Populate the table with timesheet records
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Date");
                model.addColumn("Time In");
                model.addColumn("Time Out");

                // Loop through each timesheet record
                for (String[] record : timesheetRecords) {
                    String date = record[0];
                    String timeIn = record[1];
                    String timeOut = record[2];

                    // Add the timesheet record to the table model
                    model.addRow(new Object[]{date, timeIn, timeOut});
                }

                // Set the table model
                table.setModel(model);
            } else {
                // Display a message indicating that no timesheet records were found
                JOptionPane.showMessageDialog(null, "No timesheet records found for the logged-in employee", "No Records", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Display a message indicating that no user is logged in
            JOptionPane.showMessageDialog(null, "No user is logged in", "Not Logged In", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Define the method to filter records by month-year
    private void filterRecordsByMonthYear(String selectedMonthYear) {
        // Check if a user is logged in
        if (loggedInEmployee != null) {
            // Instantiate TimesheetDAO
            TimesheetDAO dao = TimesheetDAO.getInstance();

            // Retrieve filtered timesheet records for the logged-in employee based on selectedMonthYear
            List<String[]> filteredRecords;

            // Check if the selected month-year is "All Records"
            if (selectedMonthYear.equals("All Records")) {
                // If "All Records" selected, retrieve all timesheet records for the logged-in employee
                filteredRecords = dao.getLoggedInEmployeeTimesheetRecords(loggedInEmployee.getId());
            } else {
                // Otherwise, retrieve filtered timesheet records for the selected month-year
                filteredRecords = dao.getFilteredTimesheetRecords(loggedInEmployee.getId(), selectedMonthYear);
            }

            // Check if filteredRecords is not null and not empty
            if (filteredRecords != null && !filteredRecords.isEmpty()) {
                // Populate the table with filtered timesheet records
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Date");
                model.addColumn("Time In");
                model.addColumn("Time Out");

                // Loop through each filtered timesheet record
                for (String[] record : filteredRecords) {
                    String date = record[0];
                    String timeIn = record[1];
                    String timeOut = record[2];

                    // Add the timesheet record to the table model
                    model.addRow(new Object[]{date, timeIn, timeOut});
                }

                // Set the table model
                table.setModel(model);
            } else {
                // Display a message indicating that no timesheet records were found for the selected month-year
                JOptionPane.showMessageDialog(null, "No timesheet records found for the selected month-year", "No Records", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Display a message indicating that no user is logged in
            JOptionPane.showMessageDialog(null, "No user is logged in", "Not Logged In", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 // Method to record time in for the logged-in employee
    private void recordTimeIn() {
        // Get the logged-in employee details
        int empId = loggedInEmployee.getId();
        String empLastName = loggedInEmployee.getLastName();
        String empFirstName = loggedInEmployee.getFirstName();

        // Record time in using TimesheetDAO
        TimesheetDAO.getInstance().recordTimeIn(empId, empLastName, empFirstName);
    }

    // Method to record time out for the logged-in employee
    private void recordTimeOut() {
        // Get the logged-in employee ID
        int empId = loggedInEmployee.getId();

        // Record time out using TimesheetDAO
        TimesheetDAO.getInstance().recordTimeOut(empId);
    }


    public void openWindow() {
        timeinoutScreen.setVisible(true);
    }
}