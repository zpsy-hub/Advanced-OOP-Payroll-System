package view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;
import DAO.TimesheetDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import util.SessionManager;
import util.SignOutButton;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI_HRAttendanceManagement {
	private static User loggedInEmployee;
	public JFrame hrattendancemngmnt;
	private JTable attendancemanagementTable;
	private JTextField textFieldSearch;
     

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement(loggedInEmployee);
					window.hrattendancemngmnt.setVisible(true);
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
	public GUI_HRAttendanceManagement(User loggedInEmployee) {
		GUI_HRAttendanceManagement.loggedInEmployee = loggedInEmployee;
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
		hrattendancemngmnt.setBounds(100, 100, 1280, 800);
		hrattendancemngmnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hrattendancemngmnt.getContentPane().setLayout(null);
		
		// Main panel with background image
		ImagePanel mainPanel = new ImagePanel("/img/attendance mngmnt.png"); // Update with your image path
		mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
		hrattendancemngmnt.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		// Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(hrattendancemngmnt)); 
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);
		
		JLabel employeeNameLabel = new JLabel(); 
        employeeNameLabel.setBounds(721, 30, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        mainPanel.add(employeeNameLabel);
					
		JComboBox<String> comboBoxbyMonthYear = new JComboBox<>();
		comboBoxbyMonthYear.setBounds(538, 143, 182, 30);
		comboBoxbyMonthYear.setFont(new Font("Poppins", Font.PLAIN, 16));
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
		
		
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(400, 213, 805, 506);
		tablePanel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		tablePanel.setBackground(new Color(255, 255, 255));
		mainPanel.add(tablePanel);
		tablePanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 806, 506);
		tablePanel.add(scrollPane);
		
		attendancemanagementTable = new JTable();
		attendancemanagementTable.setRowMargin(12);
		attendancemanagementTable.setRowHeight(28);
		attendancemanagementTable.setFont(new Font("Tahoma", Font.PLAIN, 10));
		scrollPane.setViewportView(attendancemanagementTable);
		
		JLabel lblFilterRecordsBy = new JLabel("Filter records by:");
		lblFilterRecordsBy.setBounds(400, 139, 339, 39);
		lblFilterRecordsBy.setHorizontalAlignment(SwingConstants.LEFT);
		lblFilterRecordsBy.setFont(new Font("Poppins", Font.PLAIN, 16));
		mainPanel.add(lblFilterRecordsBy);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(948, 143, 162, 30);
		textFieldSearch.setFont(new Font("Poppins", Font.PLAIN, 16));
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
		lblSearchForAn.setBounds(749, 139, 339, 39);
		lblSearchForAn.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearchForAn.setFont(new Font("Poppins", Font.PLAIN, 16));
		mainPanel.add(lblSearchForAn);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(1120, 143, 85, 31);
		searchButton.setFont(new Font("Poppins Medium", Font.PLAIN, 14));
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

	    attendancemanagementTable.setModel(model); 
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

	    attendancemanagementTable.setModel(model); 
	}
	
	private void searchEmployees(String query) {
	    // Retrieve the list of all employees from the DAO
	    TimesheetDAO dao = TimesheetDAO.getInstance();
	    List<String[]> allEmployees = dao.getAllTimesheetRecords(); 

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

	    attendancemanagementTable.setModel(model); 
	}

    public void openWindow() {
        hrattendancemngmnt.setVisible(true);
    }
}