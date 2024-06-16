package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Employee;
import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;
import DAO.EmployeeDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import util.SessionManager;

public class GUI_HREmployeeManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User loggedInEmployee;
    private JLabel employeeNameLabel;
    private JTable table;
    private JButton btnSaveChanges;
    private boolean isAddingEmployee = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUI_HREmployeeManagement employeeManagement = new GUI_HREmployeeManagement(loggedInEmployee);
                    employeeManagement.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     *
     * @throws IOException
     */
    public GUI_HREmployeeManagement(User loggedInEmployee) throws IOException {
        this.loggedInEmployee = loggedInEmployee;      
        table = new JTable();
        btnSaveChanges = new JButton("Save Changes");
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/emp mngmnt.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        contentPane.add(mainPanel);  // Add mainPanel to the content pane
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
                dispose(); // Close the dashboard frame
            }
        });
        signOutButton.setBounds(1114, 26, 117, 40);
        mainPanel.add(signOutButton);
       
        employeeNameLabel = new JLabel(); 
        employeeNameLabel.setBounds(707, 30, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        mainPanel.add(employeeNameLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(390, 148, 818, 488);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane);

        table = new JTable();
        table.setRowMargin(12);
        table.setRowHeight(28);
        table.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(table);
        // Call the populateEmployeeTable method to create and populate the table
        populateEmployeeTable();

        // CRUD

        // Add
        JButton addemployeeButton = new JButton("Add Employee");
        addemployeeButton.setBounds(390, 663, 154, 51);
        addemployeeButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        addemployeeButton.setBackground(Color.WHITE);
        mainPanel.add(addemployeeButton);
        // Add action listener for the "Add Employee" button
        addemployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int rowCount = model.getRowCount();
                int maxEmployeeId = 0;
                if (rowCount > 0) {
                    maxEmployeeId = (int) model.getValueAt(rowCount - 1, 0);
                }
                int nextEmployeeId = maxEmployeeId + 1;
                model.addRow(new Object[]{nextEmployeeId, "", "", "", "", "", "", "", "", "", "", "", "", 0.0f, 0.0f, 0.0f, 0.0f, 0.0, 0.0f});
                btnSaveChanges.setEnabled(true);
                isAddingEmployee = true; // Set flag to indicate adding new employee
            }
        });

        // Update
        JButton updatedataButton = new JButton("Update Data");
        updatedataButton.setBounds(578, 663, 154, 51);
        updatedataButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        updatedataButton.setBackground(Color.WHITE);
        updatedataButton.setEnabled(false); // Initially disabled
        mainPanel.add(updatedataButton);

        // Add action listener for the "Update Data" button
        updatedataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // Enable the "Save Changes" button
                    btnSaveChanges.setEnabled(true);
                    // Enable editing in the selected row
                    table.editCellAt(selectedRow, 1);
                    updatedataButton.setEnabled(false); // Disable the "Update Data" button after it's clicked
                } else {
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete
        JButton deletedataButton = new JButton("Delete Data");
        deletedataButton.setBounds(953, 663, 154, 51);
        deletedataButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        deletedataButton.setBackground(Color.WHITE);
        deletedataButton.setEnabled(false); // Initially disabled
        mainPanel.add(deletedataButton);

     // Add ListSelectionListener to the table to enable/disable delete button based on row selection
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    if (table.getSelectedRow() != -1) {
                        updatedataButton.setEnabled(true); // Enable the "Update Data" button
                        deletedataButton.setEnabled(true); // Enable the "Delete Data" button
                    } else {
                        updatedataButton.setEnabled(false); // Disable the "Update Data" button
                        deletedataButton.setEnabled(false); // Disable the "Delete Data" button
                    }
                }
            }
        });

        // Add action listener for the "Delete Data" button
        deletedataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(GUI_HREmployeeManagement.this, "Are you sure you want to delete this employee? Deletion is permanent.", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        int idToDelete = (int) model.getValueAt(selectedRow, 0);
                        boolean success = EmployeeDAO.deleteEmployee(idToDelete);
                        if (success) {
                            model.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Employee deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Error deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Save
        btnSaveChanges = new JButton("Save Changes");
        btnSaveChanges.setBounds(768, 663, 154, 51);
        btnSaveChanges.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        btnSaveChanges.setBackground(Color.WHITE);
        btnSaveChanges.setEnabled(false); // Initially disabled
        mainPanel.add(btnSaveChanges);
        
     // Add action listener for the "Save Changes" button
        btnSaveChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (validateInput(model)) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        if (isAddingEmployee) {
                            addNewEmployee(model, selectedRow); // Pass the selected row to addNewEmployee method
                        } else {
                            updateEmployee(model, selectedRow); // Pass the selected row to updateEmployee method
                        }
                    } else {
                        JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please enter valid data in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }
    }

    private void populateEmployeeTable() {
        // Get all employees from the database
        List<Employee> employees = EmployeeDAO.getAllEmployees();

        // Create a table model with column headers and editable cells
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone Number",
                        "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position",
                        "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance",
                        "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        // Add each employee to the table model
        for (Employee employee : employees) {
            model.addRow(new Object[]{
                    employee.getEmpId(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getBirthday(),
                    employee.getAddress(),
                    employee.getPhoneNumber(),
                    employee.getSssNumber(),
                    employee.getPhilhealthNumber(),
                    employee.getTinNumber(),
                    employee.getPagibigNumber(),
                    employee.getStatus(),
                    employee.getPosition(),
                    employee.getImmediateSupervisor(),
                    employee.getBasicSalary(),
                    employee.getRiceSubsidy(),
                    employee.getPhoneAllowance(),
                    employee.getClothingAllowance(),
                    employee.getGrossSemimonthlyRate(),
                    employee.getHourlyRate()
            });
        }

        // Set the table model
        table.setModel(model);
    }
    
    // Method to validate input for each column
    private String validateColumn(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0: // Employee #
                // Validate if it's a valid integer
                try {
                    Integer.parseInt(value.toString());
                    return null; // No error
                } catch (NumberFormatException e) {
                    return "Invalid Employee ID"; // Error message
                }
            case 3: // Birthday
                // Validate if it's a valid date in the format "yyyy-MM-dd"
                String birthday = value.toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                try {
                    sdf.parse(birthday);
                    return null; // No error
                } catch (ParseException e) {
                    return "Invalid Date Format (Use yyyy-MM-dd)"; // Error message
                }
            case 13: // Basic Salary
            case 14: // Rice Subsidy
            case 15: // Phone Allowance
            case 16: // Clothing Allowance
            case 17: // Gross Semi-monthly Rate
            case 18: // Hourly Rate
                // Validate if it's a valid float
                try {
                    Float.parseFloat(value.toString());
                    return null; // No error
                } catch (NumberFormatException e) {
                    return "Invalid Float Value"; // Error message
                }
            default:
                return null; // No specific validation needed for other columns
        }
    }

    // Method to validate input for each row
    private boolean validateInput(DefaultTableModel model) {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                Object value = model.getValueAt(i, j);
                if (value == null || value.toString().isEmpty()) {
                    return false; // Return false if any field is empty
                }
                String columnError = validateColumn(j, value);
                if (columnError != null) {
                    return false; // Return false if any field fails validation
                }
            }
        }
        return true; // Return true if all fields pass validation
    }
    
    private void addNewEmployee(DefaultTableModel model, int selectedRow) {
        if (validateInput(model)) {
            int lastIndex = model.getRowCount() - 1;
            if (lastIndex >= 0) {
                // Extract data from the last row
                Integer id = (Integer) model.getValueAt(lastIndex, 0);
                String lastName = (String) model.getValueAt(lastIndex, 1);
                String firstName = (String) model.getValueAt(lastIndex, 2);
                String birthday = (String) model.getValueAt(lastIndex, 3);
                String address = (String) model.getValueAt(lastIndex, 4);
                String phoneNumber = (String) model.getValueAt(lastIndex, 5);
                String sssNumber = (String) model.getValueAt(lastIndex, 6);
                String philhealthNumber = (String) model.getValueAt(lastIndex, 7);
                String tinNumber = (String) model.getValueAt(lastIndex, 8);
                String pagibigNumber = (String) model.getValueAt(lastIndex, 9);
                String status = (String) model.getValueAt(lastIndex, 10);
                String position = (String) model.getValueAt(lastIndex, 11);
                String immediateSupervisor = (String) model.getValueAt(lastIndex, 12);
                float basicSalary = Float.parseFloat(model.getValueAt(lastIndex, 13).toString());
                float riceSubsidy = Float.parseFloat(model.getValueAt(lastIndex, 14).toString());
                float phoneAllowance = Float.parseFloat(model.getValueAt(lastIndex, 15).toString());
                float clothingAllowance = Float.parseFloat(model.getValueAt(lastIndex, 16).toString());
                float grossSemimonthlyRate = Float.parseFloat(model.getValueAt(lastIndex, 17).toString());
                double hourlyRate = Double.parseDouble(model.getValueAt(lastIndex, 18).toString());

                // Create an Employee object with the data
                Employee employee = new Employee(id, lastName, firstName, birthday, address, phoneNumber,
                        sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, position, immediateSupervisor,
                        basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossSemimonthlyRate, hourlyRate);

                // Add the employee to the database
                boolean success = EmployeeDAO.createEmployee(employee);
                if (success) {
                    // Display success message
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Employee data saved successfully.");
                    btnSaveChanges.setEnabled(false); // Disable the "Save Changes" button after saving
                } else {
                    // Display error message if saving fails
                    JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Error saving employee data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            // Display error message if input validation fails
            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please enter valid data in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateEmployee(DefaultTableModel model, int selectedRow) {
        if (selectedRow != -1) {
            // Extract data from the selected row
            Integer id = (Integer) model.getValueAt(selectedRow, 0);
            String lastName = (String) model.getValueAt(selectedRow, 1);
            String firstName = (String) model.getValueAt(selectedRow, 2);
            String birthday = (String) model.getValueAt(selectedRow, 3);
            String address = (String) model.getValueAt(selectedRow, 4);
            String phoneNumber = (String) model.getValueAt(selectedRow, 5);
            String sssNumber = (String) model.getValueAt(selectedRow, 6);
            String philhealthNumber = (String) model.getValueAt(selectedRow, 7);
            String tinNumber = (String) model.getValueAt(selectedRow, 8);
            String pagibigNumber = (String) model.getValueAt(selectedRow, 9);
            String status = (String) model.getValueAt(selectedRow, 10);
            String position = (String) model.getValueAt(selectedRow, 11);
            String immediateSupervisor = (String) model.getValueAt(selectedRow, 12);
            float basicSalary = Float.parseFloat(model.getValueAt(selectedRow, 13).toString());
            float riceSubsidy = Float.parseFloat(model.getValueAt(selectedRow, 14).toString());
            float phoneAllowance = Float.parseFloat(model.getValueAt(selectedRow, 15).toString());
            float clothingAllowance = Float.parseFloat(model.getValueAt(selectedRow, 16).toString());
            float grossSemimonthlyRate = Float.parseFloat(model.getValueAt(selectedRow, 17).toString());
            double hourlyRate = Double.parseDouble(model.getValueAt(selectedRow, 18).toString());

            // Create an Employee object with the updated data
            Employee employee = new Employee(id, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, position, immediateSupervisor,
                    basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossSemimonthlyRate, hourlyRate);

            // Update the employee's information in the database
            boolean success = EmployeeDAO.updateEmployee(employee);
            if (success) {
                // Display success message
                JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Employee data updated successfully.");
                btnSaveChanges.setEnabled(false); // Disable the "Save Changes" button after updating
            } else {
                // Display error message if update fails
                JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Error updating employee data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Display error message if no row is selected
            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
