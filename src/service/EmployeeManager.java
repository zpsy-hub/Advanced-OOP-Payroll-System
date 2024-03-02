package service;

import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.Employee;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import util.EmployeeData;
import view.GUI_HREmployeeManagement;

public class EmployeeManager {
    private EmployeeData employeeData;
    private JTable table;
    private JButton updateButton;
    private JButton deleteButton;

    public EmployeeManager(JTable table, JButton updateButton, JButton deleteButton) throws IOException {
        this.employeeData = new EmployeeData();
        this.table = table;
        this.updateButton = updateButton;
        this.deleteButton = deleteButton;

        // Add a listener to enable/disable buttons based on row selection
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean rowSelected = table.getSelectedRow() != -1;
                    updateButton.setEnabled(rowSelected);
                    deleteButton.setEnabled(rowSelected);
                }
            }
        });

        loadData();
    }

    private void loadData() throws IOException {
        employeeData.loadFromCSV("src/data/Employee Database.csv");
    }

    public void addEmployee(DefaultTableModel model, Object[] newRow) {
        // Add the new row to the table model
        model.addRow(newRow);

        // Enable cell editing for the new row
        enableCellEditing(true);
    }

    public void updateEmployee(DefaultTableModel model, int row, Object[] updatedEmployeeData) throws IOException {
        // Log the update operation
        System.out.println("Updating employee...");
        
        // Get the employee ID from the selected row
        String employeeId = (String) model.getValueAt(row, 0);
        
        // Update the employee in the model
        for (int col = 0; col < model.getColumnCount(); col++) {
            Object value = updatedEmployeeData[col];
            // Handle Double values properly
            if (value instanceof Double) {
                // Convert Double to String before setting the value
                model.setValueAt(String.valueOf(value), row, col);
            } else {
                // For other types, just set the value
                model.setValueAt(value, row, col);
            }
        }

        // Convert the updated data into an Employee object
        Employee updatedEmployee = new Employee(
            (String) updatedEmployeeData[0], // Id
            (String) updatedEmployeeData[1], // Last Name
            (String) updatedEmployeeData[2], // First Name
            (String) updatedEmployeeData[3], // Birthday
            (String) updatedEmployeeData[4], // Address
            (String) updatedEmployeeData[5], // Phone Number
            (String) updatedEmployeeData[6], // SSS Number
            (String) updatedEmployeeData[7], // Philhealth Number
            (String) updatedEmployeeData[8], // TIN Number
            (String) updatedEmployeeData[9], // Pagibig Number
            (String) updatedEmployeeData[10], // Status
            (String) updatedEmployeeData[11], // Position
            (String) updatedEmployeeData[12], // Immediate Supervisor
            Double.parseDouble(String.valueOf(updatedEmployeeData[13])), // Basic Salary
            Double.parseDouble(String.valueOf(updatedEmployeeData[14])), // Rice Subsidy
            Double.parseDouble(String.valueOf(updatedEmployeeData[15])), // Phone Allowance
            Double.parseDouble(String.valueOf(updatedEmployeeData[16])), // Clothing Allowance
            Double.parseDouble(String.valueOf(updatedEmployeeData[17])), // Gross Semi-Monthly Rate
            Double.parseDouble(String.valueOf(updatedEmployeeData[18])) // Hourly Rate
        );
        
        // Update the employee in the database
        employeeData.updateEmployee(employeeId, updatedEmployee, "User"); // Assuming "User" is the current user
        
        // Log that the update operation is complete
        System.out.println("Employee updated successfully.");
    }

    public void deleteEmployee(DefaultTableModel model, int row) throws IOException {
        model.removeRow(row);

        // Save changes to CSV file
        saveChanges(model);
    }

    public void saveChanges(DefaultTableModel model) throws IOException {
        // Save the changes to the CSV file
        employeeData.saveToCSV("src/data/Employee Database.csv");

        // Disable cell editing after saving changes
        enableCellEditing(false);
    }

    private void enableCellEditing(boolean editable) {
        if (table != null) {
            // Enable/disable cell editing for each column
            for (int i = 0; i < table.getColumnCount(); i++) {
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
}
