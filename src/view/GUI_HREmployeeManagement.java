package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.Employee;
import model.User;
import service.EmployeeManager;
import util.EmployeeData;

public class GUI_HREmployeeManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User loggedInEmployee;
    private EmployeeData employeeData;
    private EmployeeManager employeeManager;
    private JLabel employeeNameLabel;
    private JTable table;
    private JButton editButton;
    private JButton deleteButton;
    private JButton btnSaveChanges;
    private JButton signoutButton;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = null; 
                    GUI_HREmployeeManagement window = new GUI_HREmployeeManagement(loggedInEmployee);
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the frame.
     * @throws IOException 
     */
    public GUI_HREmployeeManagement(User loggedInEmployee) throws IOException {
        this.loggedInEmployee = loggedInEmployee;
        this.employeeData = new EmployeeData();

        // Initialize the JTable, editButton, and btnSaveChanges objects
        table = new JTable();
        editButton = new JButton("Edit");
        btnSaveChanges = new JButton("Save Changes");

        // Now create an instance of EmployeeManager with the initialized objects
        this.employeeManager = new EmployeeManager(table, editButton, btnSaveChanges);

        loadData();
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

        //SIDEBAR BUTTONS
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
        dashboardButton.setBackground(Color.WHITE);
        dashboardButton.setBounds(37, 95, 227, 31);
        sidebarPanel.add(dashboardButton);

        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement your action listener logic for dashboard button
            }
        });

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
		
		JButton HR_LeaveMngmntButton = new JButton("LeaveBalance management");
		HR_LeaveMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_LeaveMngmntButton.setBackground(Color.WHITE);
		HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(HR_LeaveMngmntButton);
		
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

		DefaultTableModel model = new DefaultTableModel(new Object[]{"Employee #", "Last Name", "First Name", "Birthday", "Address", "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"}, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column > 0;
		    }
		};


	    // Set the table model
	    table.setModel(model);

		// Allow row selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane.setViewportView(table);

		//CRUD 
	        
	    //Add 
		JButton addemployeeButton = new JButton("Add Employee");
		addemployeeButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		addemployeeButton.setBackground(Color.WHITE);
		addemployeeButton.setBounds(340, 654, 154, 51);
		mainPanel.add(addemployeeButton);
		
		// Add action listener for the "Add Employee" button
		addemployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                String lastEmployeeNumber = (String) model.getValueAt(model.getRowCount() - 1, 0);
                int newEmployeeNumber = Integer.parseInt(lastEmployeeNumber) + 1;
                Object[] newRow = new Object[]{
                    String.valueOf(newEmployeeNumber),
                    "", "", "", "", "", "", "", "", "", "", "", "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
                };
                model.addRow(newRow);
                btnSaveChanges.setEnabled(true); // Enable save changes button after adding a new row
            }
        });
		
		// Update
		JButton updatedataButton = new JButton("Update Data");
		updatedataButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();

		        if (selectedRow != -1) {
		            // Get the updated data from the selected row
		            Object[] updatedEmployeeData = new Object[model.getColumnCount()];
		            for (int col = 0; col < model.getColumnCount(); col++) {
		                updatedEmployeeData[col] = table.getValueAt(selectedRow, col);
		            }

		            // Update the employee using EmployeeManager
		            try {
		                employeeManager.updateEmployee(model, selectedRow, updatedEmployeeData);
		                btnSaveChanges.setEnabled(true); // Enable save changes button after updating the data
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        } else {
		            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this,
		                "Please select an employee record to update.", "No Record Selected",
		                JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		});

		updatedataButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		updatedataButton.setBackground(Color.WHITE);
		updatedataButton.setBounds(528, 654, 154, 51);
		updatedataButton.setEnabled(false); // Initially disabled
		mainPanel.add(updatedataButton);

		
		//Delete
		JButton deletedataButton = new JButton("Delete Data");
		deletedataButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            // Delete the employee using EmployeeManager
		            try {
		                employeeManager.deleteEmployee((DefaultTableModel) table.getModel(), selectedRow);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        } else {
		            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this,
		                    "Please select an employee record to delete.", "No Record Selected",
		                    JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		});
        deletedataButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        deletedataButton.setBackground(Color.WHITE);
        deletedataButton.setBounds(903, 654, 154, 51);
        deletedataButton.setEnabled(false); // Initially disabled
        mainPanel.add(deletedataButton);
		
		//Save
        btnSaveChanges = new JButton("Save Changes");
        btnSaveChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveChanges();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnSaveChanges.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
        btnSaveChanges.setBackground(Color.WHITE);
        btnSaveChanges.setBounds(718, 654, 154, 51);
        btnSaveChanges.setEnabled(false); // Initially disabled
        mainPanel.add(btnSaveChanges);

		
        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }

        // Populate the table with loaded data
        populateTableWithAllEmployees();
        
        // Add selection listener to enable/disable buttons based on row selection
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    if (table.getSelectedRow() != -1) {
                        // Row is selected, enable update and delete buttons
                        updatedataButton.setEnabled(true);
                        deletedataButton.setEnabled(true);
                    } else {
                        // No row selected, disable update and delete buttons
                        updatedataButton.setEnabled(false);
                        deletedataButton.setEnabled(false);
                    }
                }
            }
        });
    }
    
    // Helper method to create Employee object from selected row data
    private Employee createEmployeeFromRow(int row) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Extract data from the selected row and create an Employee object
        Employee employee = new Employee();
        employee.setId((String) model.getValueAt(row, 0));
        employee.setLastName((String) model.getValueAt(row, 1));
        // Populate other fields similarly
        return employee;
    }
    
    private void populateTableWithAllEmployees() {
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Employee #");
        model.addColumn("Last Name");
        model.addColumn("First Name");
        model.addColumn("Birthday");
        model.addColumn("Address");
        model.addColumn("Phone Number");
        model.addColumn("SSS #");
        model.addColumn("Philhealth #");
        model.addColumn("TIN #");
        model.addColumn("Pag-ibig #");
        model.addColumn("Status");
        model.addColumn("Position");
        model.addColumn("Immediate Supervisor");
        model.addColumn("Basic Salary");
        model.addColumn("Rice Subsidy");
        model.addColumn("Phone Allowance");
        model.addColumn("Clothing Allowance");
        model.addColumn("Gross Semi-monthly Rate");
        model.addColumn("Hourly Rate");

        // Assuming employeeData.getEmployees() returns a list of Employee objects
        List<Employee> employees = employeeData.getEmployees();

     // Sort employees by ascending employee number
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1, Employee emp2) {
                // Parse employee IDs to integers for comparison
                int id1 = Integer.parseInt(emp1.getId());
                int id2 = Integer.parseInt(emp2.getId());
                // Compare employee numbers in ascending order
                return id1 - id2;
            }
        });

        for (Employee employee : employees) {
            model.addRow(new Object[]{
                    employee.getId(),
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
                    employee.getGrossSemiMonthlyRate(),
                    employee.getHourlyRate()
            });
        }

        table.setModel(model);
    }

    private void loadData() throws IOException {
        employeeData.loadFromCSV("src/data/Employee Database.csv");
        populateTableWithAllEmployees();
    }
    
    private Employee getUpdatedEmployeeFromModel(DefaultTableModel model, int row) {
        String id = (String) model.getValueAt(row, 0);
        String lastName = (String) model.getValueAt(row, 1);
        String firstName = (String) model.getValueAt(row, 2);
        String birthday = (String) model.getValueAt(row, 3);
        String address = (String) model.getValueAt(row, 4);
        String phoneNumber = (String) model.getValueAt(row, 5);
        String sssNumber = (String) model.getValueAt(row, 6);
        String philhealthNumber = (String) model.getValueAt(row, 7);
        String tinNumber = (String) model.getValueAt(row, 8);
        String pagibigNumber = (String) model.getValueAt(row, 9);
        String status = (String) model.getValueAt(row, 10);
        String position = (String) model.getValueAt(row, 11);
        String supervisor = (String) model.getValueAt(row, 12);
        double basicSalary = parseDouble(model.getValueAt(row, 13));
        double riceSubsidy = parseDouble(model.getValueAt(row, 14));
        double phoneAllowance = parseDouble(model.getValueAt(row, 15));
        double clothingAllowance = parseDouble(model.getValueAt(row, 16));
        double semiMonthlyRate = parseDouble(model.getValueAt(row, 17));
        double hourlyRate = parseDouble(model.getValueAt(row, 18));

        return new Employee(id, lastName, firstName, birthday, address, phoneNumber, sssNumber, philhealthNumber,
                tinNumber, pagibigNumber, status, position, supervisor, basicSalary, riceSubsidy, phoneAllowance,
                clothingAllowance, semiMonthlyRate, hourlyRate);
    }

    private double parseDouble(Object value) {
        if (value instanceof Double) {
            return (double) value;
        } else if (value instanceof String) {
            return Double.parseDouble((String) value);
        } else {
            throw new IllegalArgumentException("Cannot parse value to double: " + value);
        }
    }


    
    private Employee getEmployeeFromModel(DefaultTableModel model, int row) {
        String id = (String) model.getValueAt(row, 0);
        String lastName = (String) model.getValueAt(row, 1);
        String firstName = (String) model.getValueAt(row, 2);
        String birthday = (String) model.getValueAt(row, 3);
        String address = (String) model.getValueAt(row, 4);
        String phoneNumber = (String) model.getValueAt(row, 5);
        String sssNumber = (String) model.getValueAt(row, 6);
        String philhealthNumber = (String) model.getValueAt(row, 7);
        String tinNumber = (String) model.getValueAt(row, 8);
        String pagibigNumber = (String) model.getValueAt(row, 9);
        String status = (String) model.getValueAt(row, 10);
        String position = (String) model.getValueAt(row, 11);
        String supervisor = (String) model.getValueAt(row, 12);
        double basicSalary = (double) model.getValueAt(row, 13);
        double riceSubsidy = (double) model.getValueAt(row, 14);
        double phoneAllowance = (double) model.getValueAt(row, 15);
        double clothingAllowance = (double) model.getValueAt(row, 16);
        double semiMonthlyRate = (double) model.getValueAt(row, 17);
        double hourlyRate = (double) model.getValueAt(row, 18);

        // Create and return the Employee object
        return new Employee(id, lastName, firstName, birthday, address, phoneNumber, sssNumber, philhealthNumber,
                tinNumber, pagibigNumber, status, position, supervisor, basicSalary, riceSubsidy, phoneAllowance,
                clothingAllowance, semiMonthlyRate, hourlyRate);
    }

    private void saveChanges() throws IOException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        // Get the index of the selected row
        int selectedRow = table.getSelectedRow();
        
        // Check if a row is selected
        if (selectedRow != -1) {
            String employeeId = (String) model.getValueAt(selectedRow, 0);
            Employee originalEmployee = employeeData.getEmployee(employeeId); 
            Employee updatedEmployee = getUpdatedEmployeeFromModel(model, selectedRow);

            // Print original and updated employee data for logging purposes
            System.out.println("Original Employee Data: " + originalEmployee.toString());
            System.out.println("Updated Employee Data: " + updatedEmployee.toString());

            if (!originalEmployee.equals(updatedEmployee)) {
                // Update the employee in the employeeData object
                employeeData.updateEmployee(employeeId, updatedEmployee, loggedInEmployee.getUsername());

                // Save the updated employee data to the CSV file
				employeeData.saveToCSV("src/data/Employee Database.csv");
				btnSaveChanges.setEnabled(false); // Disable save changes button after saving
				// Disable cell editing after saving changes
				enableCellEditing(false);
				// Refresh the table after saving changes
				populateTableWithAllEmployees();

				// Print a message indicating that changes have been saved
				System.out.println("Changes saved successfully.");
            } else {
                // Print a message indicating that no changes were made
                System.out.println("No changes made.");
            }
        } else {
            // Print a message indicating that no row is selected
            System.out.println("Please select a row to update.");
        }
    }



    private void deleteEmployeeData(String employeeId) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(GUI_HREmployeeManagement.this,
                    "Are you sure you want to delete this employee record?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Enable logging before deleting an employee
                employeeData.setLogging(true);
                // Call the removeEmployee method from EmployeeData class
                employeeData.removeEmployee(employeeId, loggedInEmployee.getUsername());
                // Refresh the table after deletion
                populateTableWithAllEmployees();
                // Disable logging after deleting an employee
                employeeData.setLogging(false);
            }
        } else {
            JOptionPane.showMessageDialog(GUI_HREmployeeManagement.this,
                    "Please select an employee record to delete.", "No Record Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    private void enableCellEditing(boolean editable) {
        // Get the table's DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Enable/disable cell editing for each column
        for (int i = 0; i < model.getColumnCount(); i++) {
            // Get the TableColumn corresponding to the current column index
            TableColumn column = table.getColumnModel().getColumn(i);

            // Set the cell editor based on the 'editable' parameter
            if (editable) {
                // If 'editable' is true, set a DefaultCellEditor with a JTextField
                column.setCellEditor(new DefaultCellEditor(new JTextField()));
            } else {
                // If 'editable' is false, set the cell editor to null to disable editing
                column.setCellEditor(null);
            }
        }
    }



}
