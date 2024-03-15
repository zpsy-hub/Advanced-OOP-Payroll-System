package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.Employee;
import model.User;
import service.EmployeeDAO;
import service.PermissionDAO;
import service.SQL_client;
import service.PermissionWithStatus;
import util.SessionManager;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Permission;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class GUI_ITPermissions {
    JFrame permissionsFrame;
    private static User loggedInEmployee;
    private final EmployeeDAO employeeDAO;
    private JTable permissionsTable;
    private JTable userstable;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUI_ITPermissions window = new GUI_ITPermissions(loggedInEmployee);
                    window.permissionsFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUI_ITPermissions(User loggedInEmployee) {
        GUI_ITPermissions.loggedInEmployee = loggedInEmployee;
        employeeDAO = EmployeeDAO.getInstance(); // Instantiate EmployeeDAO
        initialize();
        populateUserTable();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        permissionsFrame = new JFrame();
        permissionsFrame.setBounds(100, 100, 1315, 770);
        permissionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        permissionsFrame.getContentPane().setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, 1301, 733);
        permissionsFrame.getContentPane().add(mainPanel);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setBounds(0, 0, 299, 733);
        mainPanel.add(sidebarPanel);

        JLabel motorphLabel = new JLabel("MotorPH");
        motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
        motorphLabel.setForeground(new Color(30, 55, 101));
        motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 28));
        motorphLabel.setBounds(10, 30, 279, 45);
        sidebarPanel.add(motorphLabel);

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        dashboardButton.setBackground(Color.WHITE);
        dashboardButton.setBounds(37, 95, 227, 31);
        sidebarPanel.add(dashboardButton);

        JButton timeInOutButton = new JButton("Time In/Out");
        timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        timeInOutButton.setBackground(Color.WHITE);
        timeInOutButton.setBounds(37, 154, 227, 31);
        sidebarPanel.add(timeInOutButton);

        JButton payslipButton = new JButton("Payslip");
        payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        payslipButton.setBackground(Color.WHITE);
        payslipButton.setBounds(37, 216, 227, 31);
        sidebarPanel.add(payslipButton);

        JButton leaverequestButton = new JButton("Leave Request");
        leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        leaverequestButton.setBackground(Color.WHITE);
        leaverequestButton.setBounds(37, 277, 227, 31);
        sidebarPanel.add(leaverequestButton);

        JButton helpButton = new JButton("Help & Support");
        helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        helpButton.setBackground(Color.WHITE);
        helpButton.setBounds(37, 669, 227, 31);
        sidebarPanel.add(helpButton);

        JButton IT_PermissionsManagement = new JButton("Permissions Management");
        IT_PermissionsManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
        IT_PermissionsManagement.setBackground(Color.WHITE);
        IT_PermissionsManagement.setBounds(37, 383, 227, 31);
        sidebarPanel.add(IT_PermissionsManagement);

        JButton IT_UserManagement = new JButton("User Management");
        IT_UserManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
        IT_UserManagement.setBackground(Color.WHITE);
        IT_UserManagement.setBounds(37, 438, 227, 31);
        sidebarPanel.add(IT_UserManagement);

        JLabel dashboardLabel = new JLabel("Permissions Management");
        dashboardLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        dashboardLabel.setBounds(340, 36, 379, 33);
        mainPanel.add(dashboardLabel);

        JButton signoutButton = new JButton("Sign Out");
        signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
        signoutButton.setBackground(Color.WHITE);
        signoutButton.setBounds(1160, 36, 103, 31);
        mainPanel.add(signoutButton);

        JPanel panel = new JPanel();
        panel.setBounds(826, 121, 450, 370);
        mainPanel.add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 450, 370);
        panel.add(scrollPane);

        permissionsTable = new JTable();
        scrollPane.setViewportView(permissionsTable);
        
        JLabel lblPermissions = new JLabel("Users");
        lblPermissions.setFont(new Font("Tw Cen MT", Font.PLAIN, 24));
        lblPermissions.setBounds(340, 84, 379, 33);
        mainPanel.add(lblPermissions);     
        
        JLabel lblPermissions_1 = new JLabel("Permissions");
        lblPermissions_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 24));
        lblPermissions_1.setBounds(826, 84, 379, 33);
        mainPanel.add(lblPermissions_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(340, 121, 450, 370);
        mainPanel.add(scrollPane_1);

        userstable = new JTable();
        scrollPane_1.setViewportView(userstable);

        userstable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userstable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Emp ID", "Last Name", "First Name", "Position" }));
        userstable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = userstable.getSelectedRow();
                    if (selectedRow != -1) {
                        int selectedEmpId = (int) userstable.getValueAt(selectedRow, 0);
                        Employee selectedEmployee = employeeDAO.getEmployeeById(selectedEmpId);
                        updatePermissionsTable(selectedEmployee);
                    }
                }
            }
        });

        scrollPane_1.setViewportView(userstable);
        
        JButton btnNewButton = new JButton("Grant Access");
        btnNewButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        btnNewButton.setBounds(882, 516, 150, 35);
        mainPanel.add(btnNewButton);
        
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userstable.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedEmpId = (int) userstable.getValueAt(selectedRow, 0);
                    Employee selectedEmployee = employeeDAO.getEmployeeById(selectedEmpId);
                    int selectedPermissionRow = permissionsTable.getSelectedRow();
                    if (selectedPermissionRow != -1) {
                        DefaultTableModel permissionsTableModel = (DefaultTableModel) permissionsTable.getModel();
                        String permissionName = (String) permissionsTableModel.getValueAt(selectedPermissionRow, 0);
                        PermissionDAO permissionDAO = PermissionDAO.getInstance();
                        model.Permission selectedPermission = permissionDAO.getAllPermissions().stream()
                                .filter(permission -> permission.getPermissionName().equals(permissionName))
                                .findFirst().orElse(null);
                        if (selectedPermission != null) {
                            permissionDAO.grantAccess(selectedEmployee.getEmpId(), selectedPermission.getPermissionId());
                            updatePermissionsTable(selectedEmployee);
                        } else {
                            JOptionPane.showMessageDialog(null, "Permission not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a permission to grant access.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton btnRevokeAccess = new JButton("Revoke Access");
        btnRevokeAccess.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        btnRevokeAccess.setBounds(1059, 516, 150, 35);
        mainPanel.add(btnRevokeAccess);
        
        btnRevokeAccess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userstable.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedEmpId = (int) userstable.getValueAt(selectedRow, 0);
                    Employee selectedEmployee = employeeDAO.getEmployeeById(selectedEmpId);
                    int selectedPermissionRow = permissionsTable.getSelectedRow();
                    if (selectedPermissionRow != -1) {
                        DefaultTableModel permissionsTableModel = (DefaultTableModel) permissionsTable.getModel();
                        String permissionName = (String) permissionsTableModel.getValueAt(selectedPermissionRow, 0);
                        PermissionDAO permissionDAO = PermissionDAO.getInstance();
                        model.Permission selectedPermission = permissionDAO.getAllPermissions().stream()
                                .filter(permission -> permission.getPermissionName().equals(permissionName))
                                .findFirst().orElse(null);
                        if (selectedPermission != null) {
                            permissionDAO.revokeAccess(selectedEmployee.getEmpId(), selectedPermission.getPermissionId());
                            updatePermissionsTable(selectedEmployee);
                        } else {
                            JOptionPane.showMessageDialog(null, "Permission not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a permission to revoke access.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
    }

    // Update the permissions table
    private void updatePermissionsTable(Employee selectedEmployee) {
        DefaultTableModel permissionsTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Permission Name", "Access" });
        if (selectedEmployee != null) {
            try (Connection connection = SQL_client.getInstance().getConnection()) {
                PermissionDAO permissionDAO = PermissionDAO.getInstance();
                List<model.Permission> allPermissions = permissionDAO.getAllPermissions(); // Get all permissions
                List<model.Permission> userPermissions = permissionDAO.getPermissionsByEmployeeId(selectedEmployee.getEmpId(), connection); // Get permissions for the selected user
                for (model.Permission permission : allPermissions) {
                    boolean hasPermission = userPermissions.stream()
                            .anyMatch(userPermission -> userPermission.getPermissionId() == permission.getPermissionId());
                    permissionsTableModel.addRow(new Object[] { permission.getPermissionName(), hasPermission ? "Granted" : "Not Granted" });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        permissionsTable.setModel(permissionsTableModel);
    }




    // Method to populate the user table
    private void populateUserTable() {
        DefaultTableModel userTableModel = (DefaultTableModel) userstable.getModel();
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee employee : employees) {
            userTableModel.addRow(new Object[] { employee.getEmpId(), employee.getLastName(), employee.getFirstName(), employee.getPosition() });
        }
    }
}
