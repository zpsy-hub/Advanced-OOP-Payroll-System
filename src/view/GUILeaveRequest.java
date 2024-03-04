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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Leave;
import model.LeaveLog;
import model.User;
import service.LeaveRequestService;
import util.LeaveLogData;
import util.LeaveRequestComboPopulator;
import util.LeaveRequestData;

public class GUILeaveRequest {

	private JFrame leaverequestScreen;
	private JTable leavehistoryTable;
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


	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Pass the loggedInEmployee when creating GUILeaveRequest instance
                    User loggedInEmployee = new User(null, null, 0, null, null, null, null);
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
            // Define the openDashboard method here within the ActionListener class
            private void openDashboard(User loggedInEmployee) {
                // Create an instance of GUIDashboard with the logged-in employee
                GUIDashboard dashboard = new GUIDashboard(loggedInEmployee);
                // Make the dashboard window visible
                dashboard.getDashboardScreen().setVisible(true);
            }
        });
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		
		// Define action listener for the timeInOutButton
				timeInOutButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        // Open GUITimeInOut with the logged-in employee
				        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
				        timeInOut.openWindow();
				        // Close the current dashboard window after
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
		        leaverequestScreen.dispose(); // Optionally dispose the current window
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

        updateLeaveData();      
    		
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

		JButton sendleaveButton = new JButton("Send Leave Request");
		sendleaveButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		sendleaveButton.setBackground(Color.WHITE);
		sendleaveButton.setBounds(510, 652, 200, 60);
		mainPanel.add(sendleaveButton);
		
		sendleaveButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String leaveType = (String) leaveTypeComboBox.getSelectedItem();
		        String startDate = (String) startmonthComboBox.getSelectedItem() + "/" +
		                           (String) startdayComboBox.getSelectedItem() + "/" +
		                           (String) startyearComboBox.getSelectedItem();
		        String endDate = (String) endmonthComboBox.getSelectedItem() + "/" +
		                         (String) enddayComboBox.getSelectedItem() + "/" +
		                         (String) endyearComboBox.getSelectedItem();
		        int totalDays = Integer.parseInt(textField_ComputedDays.getText());
		        int remainingBalance = 0; // Assuming you get this value from somewhere

		        // Assuming loggedInEmployee is accessible here
		        User loggedInUser = GUILeaveRequest.this.loggedInEmployee;

		        // Assuming LeaveLogData.addLeaveRequest() method accepts User
		        LeaveLogData.addLeaveRequest(loggedInUser, leaveType, startDate, endDate, totalDays, remainingBalance);
		        JOptionPane.showMessageDialog(leaverequestScreen, "Leave request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
		        
		        // Repopulate the table with the updated leave history
		        try {
		            populateLeaveHistoryTable();
		        } catch (IOException ex) {
		            ex.printStackTrace();
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
		
		JPanel leavehistoryPanel = new JPanel();
		leavehistoryPanel.setBounds(736, 331, 523, 381);
		leavehistoryPanel.setBackground(new Color(255, 255, 255));
		mainPanel.add(leavehistoryPanel);
		leavehistoryPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		leavehistoryPanel.add(scrollPane);
		
		leavehistoryTable = new JTable();
		leavehistoryTable.setRowHeight(28);
		leavehistoryTable.setRowSelectionAllowed(false);
		leavehistoryTable.setEnabled(false);
		scrollPane.setViewportView(leavehistoryTable);
		leavehistoryTable.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		leavehistoryTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		
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
            populateComboBoxes(); // Populate combo boxes before using them
            calculateTotalDays(); // Calculate total days after combo boxes are populated
        } catch (IOException e) {
            e.printStackTrace();
        }
        
     // Call the method to populate the leave history table
        try {
            populateLeaveHistoryTable();
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
            // Fetch leave data for the logged-in employee
            Leave leave = LeaveRequestData.getLeaveDataByEmployeeId(loggedInEmployee.getId());

            // Update labels with leave data
            if (leave != null) {
                int totalLeaves = leave.getVacationLeaveDays() + leave.getSickLeaveDays() + leave.getEmergencyLeaveDays();
                leaveTotal.setText(String.valueOf(totalLeaves));
                vacationTotal.setText(String.valueOf(leave.getVacationLeaveDays()));
                sickTotal.setText(String.valueOf(leave.getSickLeaveDays()));
                emergencyTotal.setText(String.valueOf(leave.getEmergencyLeaveDays()));
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
        LeaveRequestComboPopulator.populateDays(startdayComboBox);
        LeaveRequestComboPopulator.populateYears(startyearComboBox);
        LeaveRequestComboPopulator.populateMonths(endmonthComboBox);
        LeaveRequestComboPopulator.populateDays(enddayComboBox);
        LeaveRequestComboPopulator.populateYears(endyearComboBox);
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
        if (isAnyComboBoxAtDefaultSelection()) {
            // If any combo box is at default selection, exit the method
            return;
        }    

        String startYear = (String) startyearComboBox.getSelectedItem();
        String startMonth = (String) startmonthComboBox.getSelectedItem();
        String startDay = (String) startdayComboBox.getSelectedItem();
        String endYear = (String) endyearComboBox.getSelectedItem();
        String endMonth = (String) endmonthComboBox.getSelectedItem();
        String endDay = (String) enddayComboBox.getSelectedItem();

        // Calculate total days using LeaveRequestService method
        int totalDays = LeaveRequestService.calculateTotalDays(startYear, startMonth, startDay, endYear, endMonth, endDay);

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

        // Get the selected leave type
        String selectedLeaveType = (String) leaveTypeComboBox.getSelectedItem();

        // Check the leave tally balance for the employee and the selected leave type
        int leaveTallyBalance = LeaveRequestData.getLeaveTallyBalance(loggedInEmployee.getId(), selectedLeaveType);

        // Check if the total days exceed the leave tally balance
        if (totalDays > leaveTallyBalance) {
            JOptionPane.showMessageDialog(leaverequestScreen, "Insufficient leave balance. Maximum allowed days: " + leaveTallyBalance, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Set the calculated total days in the text field
        textField_ComputedDays.setText(String.valueOf(totalDays));
    }

    
    // Method to populate the leave history table with leave request history
    private void populateLeaveHistoryTable() throws IOException {
        // Create a DefaultTableModel to hold the data
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Date");
        model.addColumn("Leave Type");
        model.addColumn("Start Date");
        model.addColumn("End Date");
        model.addColumn("Total Days");
        model.addColumn("Remaining Balance");
        model.addColumn("Status");

        // Fetch leave request history for the logged-in employee
        List<LeaveLog> leaveLogs = LeaveLogData.getLeaveLogsForEmployee(loggedInEmployee.getId());

        // Populate the model with leave request history data
        for (LeaveLog leaveLog : leaveLogs) {
            model.addRow(new Object[]{
                    leaveLog.getDate(),
                    leaveLog.getLeaveType(),
                    leaveLog.getStartDate(),
                    leaveLog.getEndDate(),
                    leaveLog.getTotalDays(),
                    leaveLog.getRemainingBalance(),
                    leaveLog.getStatus()
            });
        }

        // Set the model to the leavehistoryTable
        leavehistoryTable.setModel(model);
    }
    
}


