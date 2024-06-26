package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import model.Employee;
import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;
import DAO.CredentialsManagementDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import util.SessionManager;
import util.SignOutButton;

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
        GUI_ITCredentialsManagement.loggedInEmployee = loggedInEmployee;
        credentialsManagementDAO = new DAO.CredentialsManagementDAO();
        initialize();
        populateUserComboBoxes();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		usermngmntFrame = new JFrame();
		usermngmntFrame.setBounds(100, 100, 1280, 800);
		usermngmntFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		usermngmntFrame.getContentPane().setLayout(null);
		
		// Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/credentials  mngmnt.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1266, 774);
        usermngmntFrame.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
		
        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(usermngmntFrame));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);
        
        JLabel employeeNameLabel = new JLabel();
        employeeNameLabel.setBounds(706, 28, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(employeeNameLabel);

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
        
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(408, 147, 790, 412);
		mainPanel.add(tabbedPane);
		
		// Set custom font size for tab titles
	    setTabbedPaneFont(tabbedPane, new Font("Poppins", Font.PLAIN, 24));
		
		JPanel ChangePWpanel = new JPanel();
		tabbedPane.addTab("Change Password", null, ChangePWpanel, null);
		ChangePWpanel.setLayout(null);
		
		comboBoxSelectUser = new JComboBox<>();
		comboBoxSelectUser.setFont(new Font("Poppins", Font.PLAIN, 20));
		comboBoxSelectUser.setBounds(218, 43, 315, 37);
		ChangePWpanel.add(comboBoxSelectUser);
		
		JLabel lblSelectUser = new JLabel("Select User :");
		lblSelectUser.setFont(new Font("Poppins", Font.PLAIN, 20));
		lblSelectUser.setBounds(40, 51, 137, 21);
		ChangePWpanel.add(lblSelectUser);
		
		JLabel lblNewPassword = new JLabel("New Password :");
		lblNewPassword.setFont(new Font("Poppins", Font.PLAIN, 20));
		lblNewPassword.setBounds(40, 112, 167, 21);
		ChangePWpanel.add(lblNewPassword);
		
		textField_NewPassword = new JTextField();
		textField_NewPassword.setFont(new Font("Poppins", Font.PLAIN, 20));
		textField_NewPassword.setColumns(10);
		textField_NewPassword.setBounds(218, 104, 315, 37);
		ChangePWpanel.add(textField_NewPassword);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		btnSaveChanges.setBounds(354, 192, 179, 37);
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
		btnDeleteUser.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
		btnDeleteUser.setBounds(540, 50, 208, 37);
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
		lblSelectUser_1.setFont(new Font("Poppins", Font.PLAIN, 20));
		lblSelectUser_1.setBounds(40, 57, 137, 21);
		DeleteUserpanel.add(lblSelectUser_1);
		
		comboBoxSelectUser_1 = new JComboBox<>();
		comboBoxSelectUser_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		comboBoxSelectUser_1.setBounds(187, 49, 315, 37);
		DeleteUserpanel.add(comboBoxSelectUser_1);
	}
	
	private void setTabbedPaneFont(JTabbedPane tabbedPane, Font font) {
	    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
	        tabbedPane.setFont(font);
	    }
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

