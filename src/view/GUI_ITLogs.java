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

import com.formdev.flatlaf.FlatIntelliJLaf;

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
import util.SignOutButton;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Cursor;
import java.awt.Toolkit;

public class GUI_ITLogs {

    public JFrame authenticationlogs;
    private JTable tableLogs;
    private static User loggedInEmployee;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	FlatIntelliJLaf.setup();
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
    	FlatIntelliJLaf.setup();
        authenticationlogs = new JFrame();
        authenticationlogs.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_ITLogs.class.getResource("/img/logo.png")));
        authenticationlogs.setTitle("MotorPH Payroll System");
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

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(authenticationlogs));
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
        tabbedPane.setBounds(387, 137, 835, 591);
        mainPanel.add(tabbedPane);
        
        JScrollPane scrollPane = new JScrollPane();
        tabbedPane.addTab("Authentication Logs", null, scrollPane, null);
        
        tableLogs = new JTable();
        tableLogs.setRowMargin(12);
        tableLogs.setRowHeight(28);
        scrollPane.setViewportView(tableLogs);
    }
    
    private void populateTable() {
        // Get the login attempts data from the database
        LogsDAO logsDAO = LogsDAO.getInstance();
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

        // Set the default sort order for the timestamp column to descending
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int timestampColumnIndex = 2; // Index of the "Timestamp" column
        sortKeys.add(new RowSorter.SortKey(timestampColumnIndex, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

}
