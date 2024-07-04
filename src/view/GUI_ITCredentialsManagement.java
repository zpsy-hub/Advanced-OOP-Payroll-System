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

import com.formdev.flatlaf.FlatIntelliJLaf;

import model.Employee;
import model.User;
import service.SQL_client;
import DAO.CredentialsManagementDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import util.SessionManager;
import util.SignOutButton;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.Toolkit;

public class GUI_ITCredentialsManagement {

    private static User loggedInEmployee;
    public JFrame usermngmntFrame;
    private JTextField textField_NewPassword;
    private JComboBox<String> comboBoxSelectUser;
    private JComboBox<String> comboBoxSelectUser_1;
    private DAO.CredentialsManagementDAO credentialsManagementDAO;
    private JTextField textField_AddUsername;
    private JTextField textField_AddPassword;
    private JTextArea textAreaLogs;
    private JComboBox<String> comboBoxNewEmployees;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
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
        populateNewEmployeesDropdown();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        FlatIntelliJLaf.setup();
        usermngmntFrame = new JFrame();
        usermngmntFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_ITCredentialsManagement.class.getResource("/img/logo.png")));
        usermngmntFrame.setTitle("MotorPH Payroll System");
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
                boolean passwordUpdated = credentialsManagementDAO.updatePassword(employeeId, newPassword, loggedInEmployee.getId());
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
                boolean userDeleted = credentialsManagementDAO.deleteUser(employeeIdToDelete, employeeIdToDelete);
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

        // Add New User Panel
        JPanel addUserPanel = new JPanel();
        tabbedPane.addTab("Add New User", null, addUserPanel, null);
        addUserPanel.setLayout(null);

        JLabel lblNewEmployee = new JLabel("New Employee:");
        lblNewEmployee.setFont(new Font("Poppins", Font.PLAIN, 20));
        lblNewEmployee.setBounds(39, 45, 150, 30);
        addUserPanel.add(lblNewEmployee);

        comboBoxNewEmployees = new JComboBox<>();
        comboBoxNewEmployees.setFont(new Font("Poppins", Font.PLAIN, 20));
        comboBoxNewEmployees.setBounds(199, 45, 250, 30);
        addUserPanel.add(comboBoxNewEmployees);

        JLabel lblAddUsername = new JLabel("Username:");
        lblAddUsername.setFont(new Font("Poppins", Font.PLAIN, 20));
        lblAddUsername.setBounds(39, 95, 150, 30);
        addUserPanel.add(lblAddUsername);

        textField_AddUsername = new JTextField();
        textField_AddUsername.setFont(new Font("Poppins", Font.PLAIN, 20));
        textField_AddUsername.setBounds(199, 95, 250, 30);
        addUserPanel.add(textField_AddUsername);

        JLabel lblAddPassword = new JLabel("Password:");
        lblAddPassword.setFont(new Font("Poppins", Font.PLAIN, 20));
        lblAddPassword.setBounds(39, 145, 150, 30);
        addUserPanel.add(lblAddPassword);

        textField_AddPassword = new JTextField();
        textField_AddPassword.setFont(new Font("Poppins", Font.PLAIN, 20));
        textField_AddPassword.setBounds(199, 145, 250, 30);
        addUserPanel.add(textField_AddPassword);

        JButton btnAddUser = new JButton("Add User");
        btnAddUser.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        btnAddUser.setBounds(299, 209, 150, 30);
        addUserPanel.add(btnAddUser);

        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEmployee = (String) comboBoxNewEmployees.getSelectedItem();
                if (selectedEmployee == null) {
                    JOptionPane.showMessageDialog(null, "Please select an employee.");
                    return;
                }

                int empId = Integer.parseInt(selectedEmployee.split(" - ")[0]);
                String username = textField_AddUsername.getText();
                String password = textField_AddPassword.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both username and password.");
                    return;
                }

                Employee employee = credentialsManagementDAO.getEmployeeById(empId);
                boolean isAdded = credentialsManagementDAO.addUser(employee, username, password, loggedInEmployee.getId());

                if (isAdded) {
                    JOptionPane.showMessageDialog(null, "User added successfully.");
                    populateNewEmployeesDropdown(); // Refresh the dropdown after adding an account
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add user. Please try again.");
                }
            }
        });

        // Credentials Changes Logs Panel
        JPanel logsPanel = new JPanel();
        tabbedPane.addTab("Credentials Changes Logs", null, logsPanel, null);
        logsPanel.setLayout(null);

        textAreaLogs = new JTextArea();
        textAreaLogs.setFont(new Font("Poppins", Font.PLAIN, 16));
        textAreaLogs.setEditable(false);

        JScrollPane scrollPaneLogs = new JScrollPane(textAreaLogs);
        scrollPaneLogs.setBounds(20, 20, 740, 350);
        logsPanel.add(scrollPaneLogs);

        JButton btnRefreshLogs = new JButton("Refresh Logs");
        btnRefreshLogs.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        btnRefreshLogs.setBounds(600, 380, 160, 30);
        logsPanel.add(btnRefreshLogs);

        btnRefreshLogs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> logs = credentialsManagementDAO.getCredentialChangesLogs();
                textAreaLogs.setText("");
                for (String log : logs) {
                    textAreaLogs.append(log + "\n");
                }
            }
        });
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

    private void populateNewEmployeesDropdown() {
        List<Employee> newEmployees = credentialsManagementDAO.getEmployeesWithoutAccounts();
        comboBoxNewEmployees.removeAllItems();
        for (Employee employee : newEmployees) {
            comboBoxNewEmployees.addItem(employee.getEmpId() + " - " + employee.getFirstName() + " " + employee.getLastName());
        }
    }
}
