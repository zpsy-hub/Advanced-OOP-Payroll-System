package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import model.Employee;
import model.Permission;
import model.User;
import util.UserRole;
import DAO.EmployeeDAO;
import service.EmployeeService;
import DAO.LoginDAO;
import customUI.ImagePanel;
import customUI.RoundedButtonUI;
import customUI.Sidebar;
import customUI.SidebarButton;
import service.PermissionService;
import service.SQL_client;
import util.SessionManager;

public class GUIDashboard {

	public JFrame dashboardScreen;
    private static User loggedInEmployee;
    private JLabel employeeNameLabel;
    private JLabel empIDLabel;
    private JLabel empPositionLabel;
    private JLabel immediateSupervisorLabel;
    private JLabel empStatusLabel;
    private JLabel empBdayLabel;
    private JLabel empAddressLabel;
    private JLabel empTINLabel;
    private JLabel empSSSLabel;
    private JLabel empPhilhealthLabel;
    private JLabel empPagibigLabel;
    private JLabel employeeFirstNameLabel;
    private JPanel sidebarPanel;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Retrieve the logged-in employee from the session manager
                    User loggedInEmployee = SessionManager.getLoggedInUser();

                    // Before initializing GUIDashboard, make sure loggedInEmployee is not null
                    if (loggedInEmployee != null) {
                        // Initialize GUIDashboard with the logged-in employee
                        GUIDashboard window = new GUIDashboard(loggedInEmployee);
                        window.dashboardScreen.setVisible(true);
                        window.dashboardScreen.setLocationRelativeTo(null);

                        // Call displayEmployeeInformation() to display the logged-in employee's information
                        window.displayEmployeeInformation();
                    } else {
                        System.out.println("No user logged in.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




	/**
	 * Create the application.
	 */
    // Constructor
    public GUIDashboard(User loggedInEmployee) {
        GUIDashboard.loggedInEmployee = loggedInEmployee;
        
        initialize();
    }

    public static User getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public JFrame getDashboardScreen() {
        return dashboardScreen;
    }


	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void initialize() {
		dashboardScreen = new JFrame();
		dashboardScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		dashboardScreen.setBackground(new Color(255, 255, 255));
		dashboardScreen.setTitle("MotorPH Payroll System");
		dashboardScreen.setBounds(100, 100, 1280, 800);
		dashboardScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dashboardScreen.getContentPane().setLayout(null);
		
		// Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/dashboard 3.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        dashboardScreen.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
		
        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Set button visibility based on permissions
        List<String> visibleButtons = new ArrayList<>();
        visibleButtons.add("Dashboard");
        visibleButtons.add("Time In/Out");
        visibleButtons.add("Payslip");
        visibleButtons.add("Leave Request");
        visibleButtons.add("Overtime Request");

        Connection connection = SQL_client.getInstance().getConnection();
        PermissionService permissionsService = PermissionService.getInstance();
        List<Permission> userPermissions = permissionsService.getPermissionsForEmployee(loggedInEmployee.getId(), connection);

        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 1)) {
            visibleButtons.add("Employee Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 2)) {
            visibleButtons.add("Attendance Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 3)) {
            visibleButtons.add("Leave Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 4)) {
            visibleButtons.add("Salary Calculation");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 5)) {
            visibleButtons.add("Monthly Summary Reports");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 7)) {
            visibleButtons.add("Permissions Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 8)) {
            visibleButtons.add("Credentials Management");
        }
        if (userPermissions.stream().anyMatch(permission -> permission.getPermissionId() == 6)) {
            visibleButtons.add("Authentication Logs");
        }

        sidebar.setButtonVisibility(visibleButtons);
        
        // Add the sign-out button
        SidebarButton signOutButton = new SidebarButton("Sign Out", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIlogin login = new GUIlogin();
                login.loginScreen1.setVisible(true);
                dashboardScreen.dispose(); // Close the dashboard frame
            }
        });
        signOutButton.setBounds(1095, 35, 113, 40);
        mainPanel.add(signOutButton);
        
        JPanel sidebarPanel_1 = new JPanel();
        sidebar.add(sidebarPanel_1);
        sidebarPanel_1.setBackground(new Color(255, 255, 255, 0));
        sidebarPanel_1.setOpaque(false);
        sidebarPanel_1.setLayout(null);
		
		// Initialize JLabels with empty strings
	    employeeFirstNameLabel = new JLabel("");
	    employeeFirstNameLabel.setBounds(515, 143, 381, 64);
	    employeeFirstNameLabel.setForeground(new Color(255, 255, 255));
	    employeeFirstNameLabel.setFont(new Font("Montserrat Black", Font.BOLD, 50));
	    mainPanel.add(employeeFirstNameLabel);
		
	    employeeNameLabel = new JLabel("");
	    employeeNameLabel.setBounds(770, 344, 228, 23);
	    mainPanel.add(employeeNameLabel);
	    employeeNameLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
			    
	    empIDLabel = new JLabel("");
	    empIDLabel.setBounds(574, 344, 119, 23);
	    mainPanel.add(empIDLabel);
	    empIDLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 15));
			    		
	    empPositionLabel = new JLabel("");
	    empPositionLabel.setBounds(574, 408, 191, 23);
	    mainPanel.add(empPositionLabel);
	    empPositionLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
			    				
	    immediateSupervisorLabel = new JLabel("");
	    immediateSupervisorLabel.setBounds(985, 408, 228, 23);
	    mainPanel.add(immediateSupervisorLabel);
	    immediateSupervisorLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
			    						
	    empStatusLabel = new JLabel("");
	    empStatusLabel.setBounds(775, 408, 113, 23);
	    mainPanel.add(empStatusLabel);
	    empStatusLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
			    								
	    empBdayLabel = new JLabel("");
	    empBdayLabel.setBounds(494, 492, 160, 23);
	    mainPanel.add(empBdayLabel);
	    empBdayLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
			    										
	    empSSSLabel = new JLabel("");
	    empSSSLabel.setBounds(515, 632, 171, 23);
	    mainPanel.add(empSSSLabel);
	    empSSSLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
			    												
	    empAddressLabel = new JLabel("");
	    empAddressLabel.setBounds(494, 537, 677, 50);
	    mainPanel.add(empAddressLabel);
	    empAddressLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));			    														
			    					
	    empTINLabel = new JLabel("");
	    empTINLabel.setBounds(828, 674, 171, 23);
	    mainPanel.add(empTINLabel);
	    empTINLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
	    
	    empPhilhealthLabel = new JLabel("");
	    empPhilhealthLabel.setBounds(561, 674, 171, 23);
	    mainPanel.add(empPhilhealthLabel);
	    empPhilhealthLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));   
	    
	    empPagibigLabel = new JLabel("");
	    empPagibigLabel.setBounds(898, 632, 171, 23);
	    mainPanel.add(empPagibigLabel);
	    empPagibigLabel.setFont(new Font("Poppins SemiBold", Font.PLAIN, 15));
	    
	    JLabel empIDLabel_1 = new JLabel("");
	    empIDLabel_1.setFont(new Font("Poppins Medium", Font.PLAIN, 15));
	    empIDLabel_1.setBounds(1107, 35, 119, 23);
	    mainPanel.add(empIDLabel_1);

		// Set employee info
		if (loggedInEmployee != null) {
		    // Assuming you have an instance of EmployeeService and EmployeeDAO
			LoginDAO userRepository = new LoginDAO();
			EmployeeService employeeService = new EmployeeService(userRepository, loggedInEmployee);

		    // Retrieve the employee by ID using the service
		    Employee employee = employeeService.getEmployeeById(loggedInEmployee.getId());

		    // Convert the int value to String before setting the text
		    empIDLabel.setText(String.valueOf(employee.getEmpId()));
		}
		
		// Call displayEmployeeInformation after initializing the labels
	    displayEmployeeInformation();


	}
	
	public void displayEmployeeInformation() {
	    // Check if a user is logged in
	    if (loggedInEmployee != null) {
	        // Create an instance of EmployeeService using EmployeeDAO
	        LoginDAO userRepository = new LoginDAO();
	        EmployeeService employeeService = new EmployeeService(userRepository, loggedInEmployee);

	        // Retrieve the employee's information from the database using their ID
	        Employee employee = employeeService.getLoggedInEmployee();

	        // Check if employee is found
	        if (employee != null) {
	            // Display employee information in the GUI
	            employeeFirstNameLabel.setText(employee.getFirstName());
	            employeeNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());
	            empIDLabel.setText(String.valueOf(employee.getEmpId()));
	            empPositionLabel.setText(employee.getPosition());
	            immediateSupervisorLabel.setText(employee.getImmediateSupervisor());
	            empStatusLabel.setText(employee.getStatus());
	            empBdayLabel.setText(employee.getBirthday());
	            empAddressLabel.setText(employee.getAddress());
	            empTINLabel.setText(employee.getTinNumber());
	            empSSSLabel.setText(employee.getSssNumber());
	            empPhilhealthLabel.setText(employee.getPhilhealthNumber());
	            empPagibigLabel.setText(employee.getPagibigNumber());

	            // No need for pop-up message if employee information is successfully displayed
	        } else {
	            // Handle case where employee is not found
	            JOptionPane.showMessageDialog(dashboardScreen, "Employee not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        // Handle case where no user is logged in
	        JOptionPane.showMessageDialog(dashboardScreen, "No user logged in.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	}