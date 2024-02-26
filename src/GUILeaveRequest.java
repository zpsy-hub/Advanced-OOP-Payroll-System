import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Leave;
import model.User;
import util.LeaveData;

import javax.swing.JScrollPane;
import java.awt.Cursor;
import javax.swing.JTextField;

public class GUILeaveRequest {

	private JFrame leaverequestScreen;
	private JTable leavehistoryTable;
    private User loggedInEmployee;
    private JLabel leaveTotal;
    private JLabel vacationTotal;
    private JLabel sickTotal;
    private JLabel emergencyTotal;
    private JTextField textField_ComputedDays;

	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Pass the loggedInEmployee when creating GUILeaveRequest instance
                    User loggedInEmployee = new User(); // Replace with actual loggedInEmployee
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
	 */
    public GUILeaveRequest(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        initialize();
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
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dashboardButton.setBackground(new Color(255, 255, 255));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		
		dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDashboard(loggedInEmployee);
                leaverequestScreen.dispose(); // Optionally dispose the current window
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
		
		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        openLeaveRequest(loggedInEmployee);
		        leaverequestScreen.dispose(); // Optionally dispose the current window
		    }

		    // Define the openLeaveRequest method here within the ActionListener class
		    private void openLeaveRequest(User loggedInEmployee) {
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

     // Update leave totals
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
		
		JComboBox leaveTypeComboBox = new JComboBox();
		leaveTypeComboBox.setBackground(new Color(255, 255, 255));
		leaveTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Vacation Leave", "Sick Leave", "Emergency Leave"}));
		leaveTypeComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		leaveTypeComboBox.setBounds(510, 331, 200, 32);
		mainPanel.add(leaveTypeComboBox);
		
		JComboBox startmonthComboBox = new JComboBox();
		startmonthComboBox.setBackground(new Color(255, 255, 255));
		startmonthComboBox.setMaximumRowCount(13);
		startmonthComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
		startmonthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		startmonthComboBox.setBounds(510, 393, 200, 32);
		mainPanel.add(startmonthComboBox);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblMonth.setBounds(439, 399, 53, 21);
		mainPanel.add(lblMonth);
		
		JLabel lblDay = new JLabel("Day:");
		lblDay.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblDay.setBounds(439, 441, 53, 21);
		mainPanel.add(lblDay);
		
		JComboBox startdayComboBox = new JComboBox();
		startdayComboBox.setBackground(new Color(255, 255, 255));
		startdayComboBox.setMaximumRowCount(32);
		startdayComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		startdayComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		startdayComboBox.setBounds(510, 435, 53, 32);
		mainPanel.add(startdayComboBox);
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblYear.setBounds(576, 441, 44, 21);
		mainPanel.add(lblYear);
		
		JComboBox startyearComboBox = new JComboBox();
		startyearComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "2022", "2023", "2024"}));
		startyearComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		startyearComboBox.setBackground(Color.WHITE);
		startyearComboBox.setBounds(628, 435, 82, 32);
		mainPanel.add(startyearComboBox);
		
		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		lblEndDate.setBounds(340, 505, 100, 21);
		mainPanel.add(lblEndDate);
		
		JLabel lblMonth_1 = new JLabel("Month:");
		lblMonth_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblMonth_1.setBounds(439, 505, 53, 21);
		mainPanel.add(lblMonth_1);
		
		JComboBox endmonthComboBox = new JComboBox();
		endmonthComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
		endmonthComboBox.setMaximumRowCount(13);
		endmonthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		endmonthComboBox.setBackground(Color.WHITE);
		endmonthComboBox.setBounds(510, 499, 200, 32);
		mainPanel.add(endmonthComboBox);
		
		JComboBox enddayComboBox = new JComboBox();
		enddayComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		enddayComboBox.setMaximumRowCount(32);
		enddayComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		enddayComboBox.setBackground(Color.WHITE);
		enddayComboBox.setBounds(510, 541, 53, 32);
		mainPanel.add(enddayComboBox);
		
		JLabel lblYear_1 = new JLabel("Year:");
		lblYear_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblYear_1.setBounds(576, 547, 44, 21);
		mainPanel.add(lblYear_1);
		
		JComboBox endyearComboBox = new JComboBox();
		endyearComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "2022", "2023", "2024"}));
		endyearComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		endyearComboBox.setBackground(Color.WHITE);
		endyearComboBox.setBounds(628, 541, 82, 32);
		mainPanel.add(endyearComboBox);
		
		JLabel lblDay_1 = new JLabel("Day:");
		lblDay_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblDay_1.setBounds(439, 547, 53, 21);
		mainPanel.add(lblDay_1);
		
		JButton sendleaveButton = new JButton("Send Leave Request");
		sendleaveButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		sendleaveButton.setBackground(Color.WHITE);
		sendleaveButton.setBounds(340, 652, 370, 60);
		mainPanel.add(sendleaveButton);
		
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
		leavehistoryPanel.add(scrollPane);
		
		leavehistoryTable = new JTable();
		scrollPane.setViewportView(leavehistoryTable);
		leavehistoryTable.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		leavehistoryTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"No.", "Type", "From", "To", "Total Days", "Status"
			}
		));
		leavehistoryTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton signoutButton = new JButton("Sign Out");
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
		textField_ComputedDays.setBounds(510, 594, 200, 32);
		mainPanel.add(textField_ComputedDays);
		textField_ComputedDays.setColumns(10);
		
		// Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
	}
	
	// Method to fetch and update leave data for the logged-in employee
    private void updateLeaveData() {
        try {
            // Fetch leave data for the logged-in employee
            Leave leave = LeaveData.getLeaveDataByEmployeeId(loggedInEmployee.getid());

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
}
