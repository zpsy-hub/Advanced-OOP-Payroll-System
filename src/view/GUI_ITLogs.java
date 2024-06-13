package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SortOrder;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.SwingConstants;
import model.LoginAttempt;
import model.User;
import DAO.LogsDAO;
import util.SessionManager;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Cursor;

public class GUI_ITLogs {

	public JFrame authenticationlogs;
	private JTable tableLogs;
	private static User loggedInEmployee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_ITLogs window = new GUI_ITLogs(loggedInEmployee);
					window.authenticationlogs.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_ITLogs(User loggedInEmployee) {
		initialize();
		populateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		authenticationlogs = new JFrame();
		authenticationlogs.setBounds(100, 100, 1315, 770);;
		authenticationlogs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		authenticationlogs.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1301, 1);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);
		authenticationlogs.getContentPane().add(mainPanel);
	
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				 authenticationlogs.dispose();
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(340, 98, 630, 379);
		mainPanel.add(tabbedPane);
		
		JPanel mainPanel_1 = new JPanel();
		mainPanel_1.setLayout(null);
		mainPanel_1.setBackground(Color.WHITE);
		mainPanel_1.setBounds(0, 0, 1301, 733);
		authenticationlogs.getContentPane().add(mainPanel_1);
		
		JLabel lblAuthenitcationLogs = new JLabel("Authentication Logs");
		lblAuthenitcationLogs.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		lblAuthenitcationLogs.setBounds(340, 36, 379, 33);
		mainPanel_1.add(lblAuthenitcationLogs);		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 97, 923, 500);
		mainPanel_1.add(scrollPane);
		
		tableLogs = new JTable();
		scrollPane.setViewportView(tableLogs);
		
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setLayout(null);
		sidebarPanel.setBackground(Color.WHITE);
		sidebarPanel.setBounds(0, 0, 299, 733);
		mainPanel_1.add(sidebarPanel);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 28));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidebarPanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
		dashboardButton.setBounds(37, 95, 227, 31);
		sidebarPanel.add(dashboardButton);
		dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
                authenticationlogs.dispose();
		        }
		});
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 154, 227, 31);
		sidebarPanel.add(timeInOutButton);
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();
		        authenticationlogs.dispose();
		        }
		});
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidebarPanel.add(payslipButton);
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		    	payslip.openWindow();
		    	authenticationlogs.dispose();	    		        	    
		    }
		});
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidebarPanel.add(leaverequestButton);
		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip window = new GUIPayslip(loggedInEmployee);
				window.payslipScreen.setVisible(true);
				authenticationlogs.dispose();
		    }
		});	
		
		JButton helpButton = new JButton("Help & Support");
		helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		helpButton.setBackground(Color.WHITE);
		helpButton.setBounds(37, 669, 227, 31);
		sidebarPanel.add(helpButton);
		
		JButton IT_PermissionsManagement = new JButton("Permissions Management");
		IT_PermissionsManagement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		IT_PermissionsManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_PermissionsManagement.setBackground(Color.WHITE);
		IT_PermissionsManagement.setBounds(37, 383, 227, 31);
		sidebarPanel.add(IT_PermissionsManagement);
		IT_PermissionsManagement.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_ITPermissions window = new GUI_ITPermissions(loggedInEmployee);
				window.permissionsFrame.setVisible(true);
				authenticationlogs.dispose();
		    }
		});
		
		JButton IT_CredentialsManagement = new JButton("Credentials Management");
		IT_CredentialsManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_CredentialsManagement.setEnabled(false);
		IT_CredentialsManagement.setBackground(Color.WHITE);
		IT_CredentialsManagement.setBounds(37, 438, 227, 31);
		sidebarPanel.add(IT_CredentialsManagement);
		IT_CredentialsManagement.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_ITCredentialsManagement window = new GUI_ITCredentialsManagement(loggedInEmployee);
				window.usermngmntFrame.setVisible(true);
				authenticationlogs.dispose();
		    }
		});
		
		JButton IT_LogsButton = new JButton("Authentication Logs");
		IT_LogsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		IT_LogsButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_LogsButton.setBackground(Color.WHITE);
		IT_LogsButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(IT_LogsButton);
	}
	
	private void populateTable() {
	    // Get the login attempts data from the database
	    DAO.LogsDAO logsDAO = DAO.LogsDAO.getInstance();
	    java.util.List<LoginAttempt> loginAttempts = logsDAO.getLoginAttempts();

	    // Create a table model with the appropriate columns
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Employee ID");
	    model.addColumn("Username");
	    model.addColumn("Timestamp");
	    model.addColumn("Login Status");

	    // Add data from the login attempts list to the table model
	    for (LoginAttempt attempt : loginAttempts) {
	        model.addRow(new Object[] { attempt.getEmpId(), attempt.getUsername(), attempt.getTimestamp(), attempt.getLoginStatus() });
	    }

	    // Set the table model to the JTable
	    tableLogs.setModel(model);

	    // Enable sorting for all columns
	    TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
	    tableLogs.setRowSorter(sorter);
	    
	    // Add sort keys for each column
	    for (int i = 0; i < model.getColumnCount(); i++) {
	        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
	        sortKeys.add(new RowSorter.SortKey(i, SortOrder.ASCENDING));
	        sorter.setSortKeys(sortKeys);
	    }
	}


}
