package view;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;
import DAO.EmployeeDAO;
import model.Employee;

public class EmployeeDialog extends JDialog {

    private JTextField[] textFields;
    private JComboBox<String> statusComboBox;
    private JComboBox<String> positionComboBox;
    private JComboBox<String> supervisorComboBox;

    private JButton btnSave;
    private boolean isAddingEmployee;
    private Employee employee;

    static {
        // Set the FlatLaf look and feel
        FlatLightLaf.install();
    }

    public EmployeeDialog(Frame parent, boolean isAddingEmployee, Employee employee) {
        super(parent, "Employee Dialog", true);
        this.isAddingEmployee = isAddingEmployee;
        this.employee = employee;
        initialize();
        populateFields();
    }

    private void initialize() {
        setBounds(100, 100, 450, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(40, 40, 40, 40)); // 40-pixel left border
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(18, 2, 5, 5));

        String[] labels = {
            "Employee #", "Last Name", "First Name", "Birthday", "Address", 
            "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", 
            "Department", "Basic Salary"
        };

        textFields = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            contentPane.add(new JLabel(labels[i]));
            textFields[i] = new JTextField();
            contentPane.add(textFields[i]);
        }

        // Adding dropdowns
        statusComboBox = new JComboBox<>();
        positionComboBox = new JComboBox<>();
        supervisorComboBox = new JComboBox<>();

        contentPane.add(new JLabel("Status"));
        contentPane.add(statusComboBox);
        contentPane.add(new JLabel("Position"));
        contentPane.add(positionComboBox);
        contentPane.add(new JLabel("Supervisor"));
        contentPane.add(supervisorComboBox);

        btnSave = new JButton(isAddingEmployee ? "Add" : "Update");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });
        contentPane.add(new JLabel()); // empty label for alignment
        contentPane.add(btnSave);

        populateDropdowns(); // Populate dropdowns from database

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void populateDropdowns() {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

        // Populate status dropdown
        Map<Integer, String> statuses = employeeDAO.getAllStatuses();
        for (String status : statuses.values()) {
            statusComboBox.addItem(status);
        }

        // Populate position dropdown
        Map<Integer, String> positions = employeeDAO.getAllPositions();
        for (String position : positions.values()) {
            positionComboBox.addItem(position);
        }

        // Populate supervisor dropdown
        Map<Integer, String> supervisors = employeeDAO.getAllSupervisors();
        for (String supervisor : supervisors.values()) {
            supervisorComboBox.addItem(supervisor);
        }
    }

    private void populateFields() {
        if (employee != null) {
            textFields[0].setText(String.valueOf(employee.getEmpId()));
            textFields[1].setText(employee.getLastName());
            textFields[2].setText(employee.getFirstName());
            textFields[3].setText(employee.getBirthday());
            textFields[4].setText(employee.getAddress());
            textFields[5].setText(employee.getPhoneNumber());
            textFields[6].setText(employee.getSssNumber());
            textFields[7].setText(employee.getPhilhealthNumber());
            textFields[8].setText(employee.getTinNumber());
            textFields[9].setText(employee.getPagibigNumber());
            textFields[10].setText(employee.getDepartment());
            textFields[11].setText(String.valueOf(employee.getBasicSalary()));
            statusComboBox.setSelectedItem(employee.getStatus());
            positionComboBox.setSelectedItem(employee.getPosition());
            supervisorComboBox.setSelectedItem(employee.getImmediateSupervisor());
        }
    }

    private void onSave() {
        try {
            Integer id = isAddingEmployee ? 0 : Integer.parseInt(textFields[0].getText());
            String lastName = textFields[1].getText();
            String firstName = textFields[2].getText();
            String birthday = textFields[3].getText();
            String address = textFields[4].getText();
            String phoneNumber = textFields[5].getText();
            String sssNumber = textFields[6].getText();
            String philhealthNumber = textFields[7].getText();
            String tinNumber = textFields[8].getText();
            String pagibigNumber = textFields[9].getText();
            String department = textFields[10].getText();
            double basicSalary = Double.parseDouble(textFields[11].getText());
            String status = (String) statusComboBox.getSelectedItem();
            String position = (String) positionComboBox.getSelectedItem();
            String immediateSupervisor = (String) supervisorComboBox.getSelectedItem();

            Employee employee = new Employee(id, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, department, position, 
                    immediateSupervisor, basicSalary, 0.0, 0.0); // Default values for non-editable fields

            boolean success;
            if (isAddingEmployee) {
                success = EmployeeDAO.createEmployee(employee);
            } else {
                success = EmployeeDAO.updateEmployee(employee);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Employee data saved successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving employee data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid data in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
