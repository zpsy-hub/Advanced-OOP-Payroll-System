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
import javax.swing.SortOrder;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.SwingConstants;
import model.LoginAttempt;
import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;
import DAO.LogsDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
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
        this.loggedInEmployee = loggedInEmployee;
        initialize();
        populateTable();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        authenticationlogs = new JFrame();
        authenticationlogs.setBounds(100, 100, 1280, 800);
        authenticationlogs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authenticationlogs.getContentPane().setLayout(null);
        
        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/auth logs.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        authenticationlogs.getContentPane().add(mainPanel);
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
                authenticationlogs.dispose();
            }
        });
        signOutButton.setBounds(1094, 35, 114, 40);
        mainPanel.add(signOutButton);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(387, 137, 835, 591);
        mainPanel.add(tabbedPane);
        
        JScrollPane scrollPane = new JScrollPane();
        tabbedPane.addTab("Authentication Logs", null, scrollPane, null);
        
        tableLogs = new JTable();
        scrollPane.setViewportView(tableLogs);
    }
    
    private void populateTable() {
        // Get the login attempts data from the database
        DAO.LogsDAO logsDAO = DAO.LogsDAO.getInstance();
        List<LoginAttempt> loginAttempts = logsDAO.getLoginAttempts();

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
