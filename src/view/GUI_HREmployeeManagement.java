package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import model.User;
import DAO.EmployeeDAO;
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
        setBounds(100, 100, 1315, 770);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 10, 1301, 733);
        contentPane.add(mainPanel);

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

        // SIDEBAR BUTTONS
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        dashboardButton.setBackground(Color.WHITE);
        dashboardButton.setBounds(37, 95, 227, 31);
        sidebarPanel.add(dashboardButton);
        dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
		        dispose();
		        }
		});

        JButton timeInOutButton = new JButton("Time In/Out");
        timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        timeInOutButton.setBackground(Color.WHITE);
        timeInOutButton.setBounds(37, 154, 227, 31);
        sidebarPanel.add(timeInOutButton);
        timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();
		        dispose();
		        }
		});

        JButton payslipButton = new JButton("Payslip");
        payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        payslipButton.setBackground(Color.WHITE);
        payslipButton.setBounds(37, 216, 227, 31);
        sidebarPanel.add(payslipButton);
        payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		    	payslip.openWindow();
		    	dispose();		    		        	    
		    }
		});

        JButton leaverequestButton = new JButton("Leave Request");
        leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        leaverequestButton.setBackground(Color.WHITE);
        leaverequestButton.setBounds(37, 277, 227, 31);
        sidebarPanel.add(leaverequestButton);
        leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip window = new GUIPayslip(loggedInEmployee);
				window.payslipScreen.setVisible(true);
				dispose();
		    }
		});

        JButton helpButton = new JButton("Help & Support");
        helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        helpButton.setBackground(Color.WHITE);
        helpButton.setBounds(37, 669, 227, 31);
        sidebarPanel.add(helpButton);

        JButton HR_EmpMngmntButton = new JButton("Employee management");
        HR_EmpMngmntButton.setEnabled(false);
        HR_EmpMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
        HR_EmpMngmntButton.setBackground(Color.WHITE);
        HR_EmpMngmntButton.setBounds(37, 383, 227, 31);
        sidebarPanel.add(HR_EmpMngmntButton);

        JButton HR_AttendanceMngmntButton = new JButton("Attendance management");
        HR_AttendanceMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HR_AttendanceMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
        HR_AttendanceMngmntButton.setBackground(Color.WHITE);
        HR_AttendanceMngmntButton.setBounds(37, 438, 227, 31);
        sidebarPanel.add(HR_AttendanceMngmntButton);
        HR_AttendanceMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement(loggedInEmployee);
				window.hrattendancemngmnt.setVisible(true);
				dispose(); 
		    }
		});

        JButton HR_LeaveMngmntButton = new JButton("Leave management");
        HR_LeaveMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
        HR_LeaveMngmntButton.setBackground(Color.WHITE);
        HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
        sidebarPanel.add(HR_LeaveMngmntButton);
        HR_LeaveMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_HRLeaveManagement window = new GUI_HRLeaveManagement(loggedInEmployee);
		    	window.hrleavemngmnt.setVisible(true);
		    	dispose();
		    }
		});
       

        JPanel separator = new JPanel();
        separator.setBackground(new Color(30, 55, 101));
        separator.setBounds(37, 350, 130, 3);
        sidebarPanel.add(separator);

        JLabel HRaccessLabel = new JLabel("HR Access");
        HRaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
        HRaccessLabel.setBounds(177, 332, 100, 33);
        sidebarPanel.add(HRaccessLabel);

        JLabel lblEmployeeManagement = new JLabel("Employee Management");
        lblEmployeeManagement.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        lblEmployeeManagement.setBounds(340, 36, 323, 33);
        mainPanel.add(lblEmployeeManagement);

        JButton signoutButton = new JButton("Sign Out");
        signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
        signoutButton.setBackground(Color.WHITE);
        signoutButton.setBounds(1160, 36, 103, 31);
        mainPanel.add(signoutButton);

        employeeNameLabel = new JLabel(); // Initialize employeeNameLabel
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
        employeeNameLabel.setBounds(750, 36, 400, 33);
        mainPanel.add(employeeNameLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(340, 95, 935, 541);
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
        addemployeeButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        addemployeeButton.setBackground(Color.WHITE);
        addemployeeButton.setBounds(340, 654, 154, 51);
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
        updatedataButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        updatedataButton.setBackground(Color.WHITE);
        updatedataButton.setBounds(528, 654, 154, 51);
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
        deletedataButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        deletedataButton.setBackground(Color.WHITE);
        deletedataButton.setBounds(903, 654, 154, 51);
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
        btnSaveChanges.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        btnSaveChanges.setBackground(Color.WHITE);
        btnSaveChanges.setBounds(718, 654, 154, 51);
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
