package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import DAO.TimesheetDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import model.User;
import util.SessionManager;
import util.SignOutButton;

public class GUI_HRAttendanceManagement {
    private static User loggedInEmployee;
    public JFrame hrattendancemngmnt;
    private JTable attendancemanagementTable;
    private JTextField textFieldSearch;
    private JComboBox<String> comboBoxPayrollPeriod;

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
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(employeeNameLabel);

        // ComboBox for Payroll Periods
        comboBoxPayrollPeriod = new JComboBox<>();
        comboBoxPayrollPeriod.setBounds(538, 143, 182, 30);
        comboBoxPayrollPeriod.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(comboBoxPayrollPeriod);

        // Populate comboBox with payroll periods
        populatePayrollPeriodComboBox();

        comboBoxPayrollPeriod.addActionListener(e -> {
            String selectedPayrollPeriod = (String) comboBoxPayrollPeriod.getSelectedItem();
            filterRecordsByPayrollPeriod(selectedPayrollPeriod);
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
        attendancemanagementTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
        searchButton.addActionListener(e -> {
            String query = textFieldSearch.getText().trim();
            if (!query.isEmpty()) {
                searchEmployees(query);
            } else {
                populateTable(); // If the search field is empty, show all records
            }
        });

        // Populate the table with all records initially
        populateTable();
    }

    // Method to populate the payroll period combo box
    private void populatePayrollPeriodComboBox() {
        TimesheetDAO timesheetDAO = TimesheetDAO.getInstance();
        comboBoxPayrollPeriod.removeAllItems(); // Clear existing items
        comboBoxPayrollPeriod.addItem("All Payroll Periods"); // Add default item
        List<String> payPeriods = timesheetDAO.getDistinctPayPeriods();
        for (String payPeriod : payPeriods) {
            comboBoxPayrollPeriod.addItem(payPeriod);
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
        model.addColumn("Employee Name");
        model.addColumn("Date");
        model.addColumn("Time In");
        model.addColumn("Time Out");

        for (String[] record : allRecords) {
            model.addRow(record);
        }

        attendancemanagementTable.setModel(model);
    }

    // Method to filter records based on the selected payroll period
    private void filterRecordsByPayrollPeriod(String payrollPeriod) {
        TimesheetDAO timesheetDAO = TimesheetDAO.getInstance();
        List<String[]> records = timesheetDAO.getTimesheetRecordsByPayrollPeriod(payrollPeriod);
        DefaultTableModel model = (DefaultTableModel) attendancemanagementTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (String[] record : records) {
            model.addRow(record);
        }
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
            if (employee[1].equalsIgnoreCase(query) || employee[2].toLowerCase().contains(query.toLowerCase())) {
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
        model.addColumn("Employee Name");
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
