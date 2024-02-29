import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import model.Attendance;
import model.User;
import service.TimeInOutHandler;
import util.AttendanceData;
import util.EmployeeData;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class GUITimeInOut {

    private JFrame timeinoutScreen;
    private JTable timeTable;
    private User loggedInEmployee;
    private EmployeeData employeeData;
    private JTable table;
    private AttendanceData attendanceData;
    private TimeInOutHandler timeInOutHandler;
    private boolean timeOutRecorded = false;

    // Constructor 
    public GUITimeInOut(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        this.employeeData = new EmployeeData();
        this.attendanceData = new AttendanceData();
        loadData();
        this.timeInOutHandler = new TimeInOutHandler(attendanceData);
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
                openDashboard(loggedInEmployee);
                timeinoutScreen.dispose(); // Optionally dispose the current window
            }

            // Define the openDashboard method here within the ActionListener class
            private void openDashboard(User loggedInEmployee) {
                // Create an instance of GUIDashboard with the logged-in employee
                GUIDashboard dashboard = new GUIDashboard(loggedInEmployee);

                // Make the dashboard window visible
                dashboard.getDashboardScreen().setVisible(true);
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
		        openPayslip(loggedInEmployee);
		        timeinoutScreen.dispose(); // 
		    }

		    // Define the openPayslip method here within the ActionListener class
		    private void openPayslip(User loggedInEmployee) {
		        // Create an instance of GUIPayslip with the loggedInEmployee
		        GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		        // Make the payslip window visible
		        payslip.openWindow();
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
		        try {
					openLeaveRequest(loggedInEmployee);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        timeinoutScreen.dispose(); // Optionally dispose the current window
		    }

		    // Define the openLeaveRequest method here within the ActionListener class
		    private void openLeaveRequest(User loggedInEmployee) throws IOException {
		        // Create an instance of GUILeaveRequest with the loggedInEmployee
		        GUILeaveRequest leaveRequest = new GUILeaveRequest(loggedInEmployee);

		        // Make the leave request window visible
		        leaveRequest.openWindow();
		    }
		});

        JButton helpButton = new JButton("Help & Support");
        helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        helpButton.setBackground(Color.WHITE);
        helpButton.setBounds(37, 669, 227, 31);
        sidePanel.add(helpButton);

        JLabel timeinoutLabel = new JLabel("Time In/Out");
        timeinoutLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        timeinoutLabel.setBounds(340, 36, 205, 33);
        mainPanel.add(timeinoutLabel);

        JPanel timeinoutPanel = new JPanel();
        timeinoutPanel.setBackground(new Color(255, 255, 255));
        timeinoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        timeinoutPanel.setBounds(340, 79, 923, 242);
        mainPanel.add(timeinoutPanel);
        timeinoutPanel.setLayout(null);

        JButton timeInButton = new JButton("TIME IN");
        timeInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        timeInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Call the method in TimeInOutHandler to handle time in
                    timeInOutHandler.logTimeIn(loggedInEmployee.getid(), loggedInEmployee.getLastName(), loggedInEmployee.getFirstName());
                    // Refresh the table after logging time in
                    populateTable();
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                }
            }
        });
        
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
        
        
        timeOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Call the method in TimeInOutHandler to handle time out
                    timeInOutHandler.logTimeOut(loggedInEmployee.getid());
                    // Refresh the table after logging time out
                    populateTable();
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                }
            }
        });

        JLabel empStatus = new JLabel("OFF"); // Default status is OFF
        empStatus.setForeground(new Color(255, 0, 0)); // Default color is red
        empStatus.setHorizontalAlignment(SwingConstants.CENTER);
        empStatus.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
        empStatus.setBounds(291, 102, 339, 39);
        timeinoutPanel.add(empStatus);

        // BUG NEED TO FIX THE TIME IN/OUT STATUS
        timeInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empStatus.setText("IN");
                empStatus.setForeground(new Color(0, 255, 0)); // Change color to green
                timeInButton.setEnabled(false); // Disable the Time In button
                timeOutButton.setEnabled(true); // Enable the Time Out button
            }
        });

        timeOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empStatus.setText("OUT");
                empStatus.setForeground(new Color(255, 0, 0)); // Change color back to red
                timeOutButton.setEnabled(false); // Disable the Time Out button
                timeInButton.setEnabled(false); // Disable the Time In button
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
        panel.setBounds(340, 341, 923, 371);
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

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
    }

    //Populate the table with attendance records for the logged-in user.
    private void populateTable() {
        // Create a DefaultTableModel to hold the data
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Date");
        model.addColumn("Time In");
        model.addColumn("Time Out");

        // Fetch attendance records for the logged-in user
        List<Attendance> userRecords = new ArrayList<>();
        for (Attendance record : attendanceData.getAttendanceRecords()) {
            if (record.getid().equals(loggedInEmployee.getid())) {
                userRecords.add(record);
            }
        }

        // Sort the records by date in descending order
        userRecords.sort(Comparator.comparing(Attendance::getDate).reversed());

        // Add sorted records to the table model
        for (Attendance record : userRecords) {
            // Format time in HH:MM format
            String timeIn = record.getTimeIn().format(DateTimeFormatter.ofPattern("HH:mm"));
            // Format time out HH:MM format
            String timeOut = record.getTimeOut() != null ? record.getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm")) : "";
            model.addRow(new Object[]{record.getDate(), timeIn, timeOut});
        }

        // Set the table model
        table.setModel(model);
    }

    public void openWindow() {
        timeinoutScreen.setVisible(true);
    }

    private void loadData() {
        try {
            // Load employee data from CSV file
            employeeData.loadFromCSV("src/data/Employee Database.csv");
            // Load attendance data from CSV file
            attendanceData.loadFromCSV("src/data/Attendance Timesheet.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}