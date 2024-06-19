package view;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import util.SignOutButton;
import DAO.TimesheetDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;

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
        timeinoutScreen.setBounds(100, 100, 1280, 800);
        timeinoutScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timeinoutScreen.getContentPane().setLayout(null);
              

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/time in.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        timeinoutScreen.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(394, 448, 814, 282);
        mainPanel.add(scrollPane);
        
        table = new JTable();
        table.setBorder(null);
        table.setRowMargin(12);
        table.setRowHeight(28);
        table.setEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setFont(new Font("Poppins", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        

        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(timeinoutScreen));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);

        JPanel timeinoutPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f)); 
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        timeinoutPanel.setOpaque(false); // Set the panel to be non-opaque
        timeinoutPanel.setBackground(new Color(255, 255, 255, 0)); // Fully transparent background
        timeinoutPanel.setBorder(null); // Remove border
        timeinoutPanel.setBounds(372, 127, 854, 164);
        mainPanel.add(timeinoutPanel);
        timeinoutPanel.setLayout(null);

        JButton timeInButton = new JButton("TIME IN");
        timeInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        timeInButton.setBackground(new Color(255, 255, 255));
        timeInButton.setFont(new Font("Montserrat ExtraBold", Font.PLAIN, 30));
        timeInButton.setBounds(29, 29, 239, 102);
        timeinoutPanel.add(timeInButton);      
        
        JButton timeOutButton = new JButton("TIME OUT");
        timeOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        timeOutButton.setFont(new Font("Montserrat ExtraBold", Font.PLAIN, 30));
        timeOutButton.setBackground(Color.WHITE);
        timeOutButton.setBounds(576, 29, 239, 102);
        timeinoutPanel.add(timeOutButton);
 
        JLabel empStatus = new JLabel("OFF"); // Default status is OFF
        empStatus.setForeground(new Color(255, 0, 0)); // Default color is red
        empStatus.setHorizontalAlignment(SwingConstants.CENTER);
        empStatus.setFont(new Font("Poppins ExtraBold", Font.PLAIN, 30));
        empStatus.setBounds(260, 81, 339, 39);
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
        currentstatusLabel.setFont(new Font("Poppins", Font.PLAIN, 21));
        currentstatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentstatusLabel.setBounds(260, 32, 339, 39);
        timeinoutPanel.add(currentstatusLabel);

        JLabel employeeNameLabel = new JLabel("");
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        employeeNameLabel.setBounds(677, 35, 400, 33);
        mainPanel.add(employeeNameLabel);
        
        JPanel panel = new JPanel();
        panel.setBorder(null);
        panel.setBounds(394, 448, 814, 282);
        mainPanel.add(panel);
        panel.setLayout(null);
      
        
        //Combobox to filter
        JComboBox comboBoxbyMonthYear = new JComboBox();
        comboBoxbyMonthYear.setFont(new Font("Poppins", Font.PLAIN, 15));
        comboBoxbyMonthYear.setBounds(534, 416, 162, 21);
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
        lblFilterRecordsBy.setFont(new Font("Poppins", Font.PLAIN, 15));
        lblFilterRecordsBy.setBounds(394, 406, 339, 39);
        mainPanel.add(lblFilterRecordsBy);
        
        JLabel lblAttendanceRecords = new JLabel("Attendance Records");
        lblAttendanceRecords.setHorizontalAlignment(SwingConstants.LEFT);
        lblAttendanceRecords.setFont(new Font("Montserrat", Font.PLAIN, 22));
        lblAttendanceRecords.setBounds(394, 357, 339, 39);
        mainPanel.add(lblAttendanceRecords);        

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