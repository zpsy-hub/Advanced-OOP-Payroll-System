package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import service.TimesheetDAO;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI_HRAttendanceManagement {
	JFrame hrattendancemngmnt;
	private JTable attendancemanagementTable;
	private JTextField textFieldSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement();
					window.hrattendancemngmnt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_HRAttendanceManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		hrattendancemngmnt = new JFrame();
		hrattendancemngmnt.setBackground(new Color(255, 255, 255));
		hrattendancemngmnt.setTitle("MotorPH Payroll System");
		hrattendancemngmnt.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\GitHub\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		hrattendancemngmnt.setBounds(100, 100, 1315, 770);
		hrattendancemngmnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hrattendancemngmnt.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1301, 733);
		hrattendancemngmnt.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		JComboBox<String> comboBoxbyMonthYear = new JComboBox<>();
		comboBoxbyMonthYear.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		comboBoxbyMonthYear.setBounds(464, 55, 162, 21);
		mainPanel.add(comboBoxbyMonthYear);
		comboBoxbyMonthYear.addItem("All Records");
		
		        // ActionListener for the JComboBox
		        comboBoxbyMonthYear.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                String selectedMonthYear = (String) comboBoxbyMonthYear.getSelectedItem();
		                if (!selectedMonthYear.equals("All Records")) {
		                    filterRecordsByMonthYear(selectedMonthYear);
		                } else {
		                    populateTable();
		                }
		            }
		        });
		
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setBackground(new Color(255, 255, 255));
		sidebarPanel.setBounds(0, 0, 299, 733);
		mainPanel.add(sidebarPanel);
		sidebarPanel.setLayout(null);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 28));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidebarPanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dashboardButton.setBackground(new Color(255, 255, 255));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBounds(37, 95, 227, 31);
		sidebarPanel.add(dashboardButton);
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 154, 227, 31);
		sidebarPanel.add(timeInOutButton);
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidebarPanel.add(payslipButton);
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidebarPanel.add(leaverequestButton);
		
		JButton HR_EmpMngmntButton = new JButton("Employee management");
		HR_EmpMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_EmpMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_EmpMngmntButton.setBackground(Color.WHITE);
		HR_EmpMngmntButton.setBounds(37, 383, 227, 31);
		sidebarPanel.add(HR_EmpMngmntButton);
		
		JButton HR_AttendanceMngmntButton = new JButton("Attendance management");
		HR_AttendanceMngmntButton.setEnabled(false);
		HR_AttendanceMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_AttendanceMngmntButton.setBackground(Color.WHITE);
		HR_AttendanceMngmntButton.setBounds(37, 438, 227, 31);
		sidebarPanel.add(HR_AttendanceMngmntButton);
		
		JButton HR_LeaveMngmntButton = new JButton("LeaveBalance management");
		HR_LeaveMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_LeaveMngmntButton.setBackground(Color.WHITE);
		HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(HR_LeaveMngmntButton);
		
		JPanel separator = new JPanel();
		separator.setBackground(new Color(30, 55, 101));
		separator.setBounds(37, 350, 130, 3);
		sidebarPanel.add(separator);
		
		JLabel HRaccessLabel = new JLabel("HR Access");
		HRaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		HRaccessLabel.setBounds(177, 332, 100, 33);
		sidebarPanel.add(HRaccessLabel);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		tablePanel.setBackground(new Color(255, 255, 255));
		tablePanel.setBounds(333, 93, 937, 550);
		mainPanel.add(tablePanel);
		tablePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		attendancemanagementTable = new JTable();
		attendancemanagementTable.setRowMargin(12);
		attendancemanagementTable.setRowHeight(28);
		attendancemanagementTable.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		scrollPane.setViewportView(attendancemanagementTable);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				hrattendancemngmnt.dispose();
			}
		});
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JLabel lblFilterRecordsBy = new JLabel("Filter records by:");
		lblFilterRecordsBy.setHorizontalAlignment(SwingConstants.LEFT);
		lblFilterRecordsBy.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblFilterRecordsBy.setBounds(333, 46, 339, 39);
		mainPanel.add(lblFilterRecordsBy);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		textFieldSearch.setBounds(843, 55, 162, 21);
		mainPanel.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		textFieldSearch.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            String query = textFieldSearch.getText().trim();
		            if (!query.isEmpty()) {
		                searchEmployees(query);
		            } else {
		                populateTable(); // If the search field is empty, show all records
		            }
		        }
		    }
		});

		
		JLabel lblSearchForAn = new JLabel("Search for an Employee:");
		lblSearchForAn.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearchForAn.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		lblSearchForAn.setBounds(661, 46, 339, 39);
		mainPanel.add(lblSearchForAn);
		
		JButton searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		searchButton.setBounds(1015, 55, 85, 21);
		mainPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = textFieldSearch.getText().trim();
                if (!query.isEmpty()) {
                    searchEmployees(query);
                } else {
                    populateTable(); // If the search field is empty, show all records
                }
            }
        });


		
		//Combobox for month year
        TimesheetDAO dao = TimesheetDAO.getInstance();
        List<String> monthYearCombinations = dao.getUniqueMonthYearCombinations();
        for (String monthYear : monthYearCombinations) {
            comboBoxbyMonthYear.addItem(monthYear);
        }
    }

	private void populateTable() {
	    // Retrieve all timesheet records
	    TimesheetDAO dao = TimesheetDAO.getInstance();
	    List<String[]> allRecords = dao.getAllTimesheetRecords();

	    // Populate the table with all records
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Record ID");
	    model.addColumn("Employee ID");
	    model.addColumn("Last Name");
	    model.addColumn("First Name");
	    model.addColumn("Date");
	    model.addColumn("Time In");
	    model.addColumn("Time Out");

	    for (String[] record : allRecords) {
	        model.addRow(record);
	    }

	    attendancemanagementTable.setModel(model); // Corrected line
	}

	private void filterRecordsByMonthYear(String selectedMonthYear) {
	    // Retrieve filtered timesheet records
	    TimesheetDAO dao = TimesheetDAO.getInstance();
	    List<String[]> filteredRecords = dao.getFilteredTimesheetRecords(selectedMonthYear);

	    // Populate the table with filtered records
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Record ID");
	    model.addColumn("Employee ID");
	    model.addColumn("Last Name");
	    model.addColumn("First Name");
	    model.addColumn("Date");
	    model.addColumn("Time In");
	    model.addColumn("Time Out");

	    for (String[] record : filteredRecords) {
	        model.addRow(record);
	    }

	    attendancemanagementTable.setModel(model); // Corrected line
	}
	
	private void searchEmployees(String query) {
	    // Retrieve the list of all employees from the DAO
	    TimesheetDAO dao = TimesheetDAO.getInstance();
	    List<String[]> allEmployees = dao.getAllTimesheetRecords(); // Assuming this returns all employees

	    // Create a filtered list to store matching employees
	    List<String[]> filteredEmployees = new ArrayList<>();

	    // Iterate through all employees and check if the query matches employee ID or name
	    for (String[] employee : allEmployees) {
	        // Check if the employee ID or name contains the query (case-insensitive)
	        if (employee[1].equalsIgnoreCase(query) || 
	            employee[2].toLowerCase().contains(query.toLowerCase()) ||  // Assuming employee name is at index 2
	            employee[3].toLowerCase().contains(query.toLowerCase())) {  // Assuming employee name is at index 3
	            filteredEmployees.add(employee); // If match found, add to filtered list
	        }
	    }

	    // Populate the table with filtered results
	    populateTableWithFilteredResults(filteredEmployees);
	}
	
	private void populateTableWithFilteredResults(List<String[]> filteredEmployees) {
	    // Populate the table with filtered results
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Record ID");
	    model.addColumn("Employee ID");
	    model.addColumn("Last Name");
	    model.addColumn("First Name");
	    model.addColumn("Date");
	    model.addColumn("Time In");
	    model.addColumn("Time Out");

	    for (String[] employee : filteredEmployees) {
	        model.addRow(employee);
	    }

	    attendancemanagementTable.setModel(model); // Set the model to the table
	}




    public void openWindow() {
        hrattendancemngmnt.setVisible(true);
    }
}