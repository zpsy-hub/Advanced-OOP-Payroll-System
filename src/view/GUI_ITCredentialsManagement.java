package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingConstants;

import model.Employee;
import model.User;
import DAO.CredentialsManagementDAO;
import util.SessionManager;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Cursor;

public class GUI_ITCredentialsManagement {

	private static User loggedInEmployee;
	public JFrame usermngmntFrame;
	private JTextField textField_NewPassword;
	private JComboBox<String> comboBoxSelectUser;
    private JComboBox<String> comboBoxSelectUser_1;
    private DAO.CredentialsManagementDAO credentialsManagementDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_ITCredentialsManagement window = new GUI_ITCredentialsManagement(loggedInEmployee);
					window.usermngmntFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_ITCredentialsManagement(User loggedInEmployee) {
		credentialsManagementDAO = new DAO.CredentialsManagementDAO();
       	initialize();
       	 populateUserComboBoxes();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		usermngmntFrame = new JFrame();
		usermngmntFrame.setBounds(100, 100, 1315, 770);
		usermngmntFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		usermngmntFrame.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 1301, 733);
		usermngmntFrame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setBounds(0, 0, 299, 733);
		sidebarPanel.setLayout(null);
		sidebarPanel.setBackground(Color.WHITE);
		mainPanel.add(sidebarPanel);
		
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
                usermngmntFrame.dispose();
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
		        usermngmntFrame.dispose();
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
		    	usermngmntFrame.dispose();		    		        	    
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
				usermngmntFrame.dispose();	
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
				usermngmntFrame.dispose();	
		    }
		});
		
		JButton IT_CredentialsManagement = new JButton("Credentials Management");
		IT_CredentialsManagement.setEnabled(false);
		IT_CredentialsManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_CredentialsManagement.setBackground(Color.WHITE);
		IT_CredentialsManagement.setBounds(37, 438, 227, 31);
		sidebarPanel.add(IT_CredentialsManagement);
				
		JButton IT_LogsButton = new JButton("Authentication Logs");
		IT_LogsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		IT_LogsButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		IT_LogsButton.setBackground(Color.WHITE);
		IT_LogsButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(IT_LogsButton);
		IT_LogsButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_ITLogs window = new GUI_ITLogs(loggedInEmployee);
		    	window.authenticationlogs.setVisible(true);
		    	usermngmntFrame.dispose();	
		    }
		});
		
		JLabel lblUserManagement = new JLabel("Credentials Management");
		lblUserManagement.setBounds(340, 36, 379, 33);
		lblUserManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		mainPanel.add(lblUserManagement);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signoutButton.setBounds(1160, 36, 103, 31);
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		mainPanel.add(signoutButton);
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				usermngmntFrame.dispose();
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(340, 98, 630, 379);
		mainPanel.add(tabbedPane);
		
		JPanel ChangePWpanel = new JPanel();
		tabbedPane.addTab("Change Password", null, ChangePWpanel, null);
		ChangePWpanel.setLayout(null);
		
		comboBoxSelectUser = new JComboBox<>();
		comboBoxSelectUser.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		comboBoxSelectUser.setBounds(187, 46, 277, 28);
		ChangePWpanel.add(comboBoxSelectUser);
		
		JLabel lblSelectUser = new JLabel("Select User :");
		lblSelectUser.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		lblSelectUser.setBounds(40, 51, 137, 21);
		ChangePWpanel.add(lblSelectUser);
		
		JLabel lblNewPassword = new JLabel("New Password :");
		lblNewPassword.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		lblNewPassword.setBounds(40, 104, 149, 21);
		ChangePWpanel.add(lblNewPassword);
		
		textField_NewPassword = new JTextField();
		textField_NewPassword.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		textField_NewPassword.setColumns(10);
		textField_NewPassword.setBounds(187, 100, 277, 28);
		ChangePWpanel.add(textField_NewPassword);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		btnSaveChanges.setBounds(316, 160, 148, 28);
		ChangePWpanel.add(btnSaveChanges);
		btnSaveChanges.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedUser = (String) comboBoxSelectUser.getSelectedItem();
		        String newPassword = textField_NewPassword.getText();
		        int employeeId = extractEmployeeId(selectedUser);
		        boolean passwordUpdated = credentialsManagementDAO.updatePassword(employeeId, newPassword);
		        if (passwordUpdated) {
		            JOptionPane.showMessageDialog(null, "Password updated successfully for employee: " + selectedUser);
		        } else {
		            JOptionPane.showMessageDialog(null, "Failed to update password for employee: " + selectedUser);
		        }
		    }
		    
		    // Helper method 
		    private int extractEmployeeId(String selectedUser) {
		    	// user string is in the format "empId - LastName, FirstName"
		        String[] parts = selectedUser.split(" - ");
		        return Integer.parseInt(parts[0]);
		    }
		    
		});

		
		
		JPanel DeleteUserpanel = new JPanel();
		tabbedPane.addTab("Delete User", null, DeleteUserpanel, null);
		DeleteUserpanel.setLayout(null);
		
		JButton btnDeleteUser = new JButton("Delete from System");
		btnDeleteUser.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		btnDeleteUser.setBounds(294, 110, 170, 28);
		DeleteUserpanel.add(btnDeleteUser);
		btnDeleteUser.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedUserToDelete = (String) comboBoxSelectUser_1.getSelectedItem();
		        int employeeIdToDelete = extractEmployeeId(selectedUserToDelete);
		        boolean userDeleted = credentialsManagementDAO.deleteUser(employeeIdToDelete);		        
		        if (userDeleted) {
		            JOptionPane.showMessageDialog(null, "User deleted successfully: " + selectedUserToDelete);
		        } else {
		            JOptionPane.showMessageDialog(null, "Failed to delete user: " + selectedUserToDelete);
		        }
		    }
		    
		    // Helper method 
		    private int extractEmployeeId(String selectedUser) {
		        // user string is in the format "empId - LastName, FirstName"
		        String[] parts = selectedUser.split(" - ");
		        return Integer.parseInt(parts[0]);
		    }
		});


		
		JLabel lblSelectUser_1 = new JLabel("Select User :");
		lblSelectUser_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		lblSelectUser_1.setBounds(40, 54, 137, 21);
		DeleteUserpanel.add(lblSelectUser_1);
		
		comboBoxSelectUser_1 = new JComboBox<>();
		comboBoxSelectUser_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		comboBoxSelectUser_1.setBounds(187, 49, 277, 28);
		DeleteUserpanel.add(comboBoxSelectUser_1);
	}
	
	private void populateUserComboBoxes() {
	    if (comboBoxSelectUser != null && comboBoxSelectUser_1 != null) {
	        // Clear existing items in the combo boxes
	        comboBoxSelectUser.removeAllItems();
	        comboBoxSelectUser_1.removeAllItems();
	        
	        // Add blank as the first item
	        comboBoxSelectUser.addItem("");
	        comboBoxSelectUser_1.addItem("");
	        
	        // Get employee names and add them to combo boxes
	        List<String> employeeInfos = credentialsManagementDAO.getAllEmployeeNames();
	        for (String employeeInfo : employeeInfos) {
	            comboBoxSelectUser.addItem(employeeInfo);
	            comboBoxSelectUser_1.addItem(employeeInfo);
	        }
	    }
	}
}
