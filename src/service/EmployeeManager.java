package service;

import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
        // Update the employee in the model
        for (int col = 0; col < model.getColumnCount(); col++) {
            model.setValueAt(updatedEmployeeData[col], row, col);
        }

        // Save changes to CSV file
        saveChanges(model);
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
