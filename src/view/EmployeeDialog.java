package view;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import com.formdev.flatlaf.FlatLightLaf;
import DAO.EmployeeDAO;
import model.Employee;

public class EmployeeDialog extends JDialog {

    private JFormattedTextField[] formattedTextFields;
    private JComboBox<String> statusComboBox;
    private JComboBox<String> positionComboBox;
    private JComboBox<String> supervisorComboBox;
    private JComboBox<String> departmentComboBox;

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
        setBounds(100, 100, 450, 700); 
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(40, 40, 40, 40)); // 40-pixel border
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout()); // Use GridBagLayout for flexible layout

        String[] labels = {
            "Employee #", "Last Name", "First Name", "Birthday", "Address", 
            "Phone Number", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", 
            "Basic Salary"
        };

        formattedTextFields = new JFormattedTextField[labels.length];
        MaskFormatter[] formatters = new MaskFormatter[labels.length];

        try {
            // Define formatters
            formatters[3] = new MaskFormatter("####-##-##"); // Birthday
            formatters[5] = new MaskFormatter("###-###-###"); // Phone Number
            formatters[6] = new MaskFormatter("##-#######-#"); // SSS #
            formatters[7] = new MaskFormatter("############"); // Philhealth #
            formatters[8] = new MaskFormatter("###-###-###-###"); // TIN #
            formatters[9] = new MaskFormatter("############"); // Pag-ibig #
            formatters[10] = null; // Basic Salary: No specific format, but should have 2 decimal places

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Padding around each component
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.gridwidth = 1;

            for (int i = 0; i < labels.length; i++) {
                gbc.gridx = 0;
                gbc.gridy = i;
                contentPane.add(new JLabel(labels[i]), gbc);

                if (formatters[i] != null) {
                    formatters[i].setPlaceholderCharacter('_');
                    formattedTextFields[i] = new JFormattedTextField(formatters[i]);
                } else {
                    formattedTextFields[i] = new JFormattedTextField();
                }
                formattedTextFields[i].setToolTipText(getToolTipText(labels[i]));

                gbc.gridx = 1;
                gbc.gridy = i;
                contentPane.add(formattedTextFields[i], gbc);
            }

            // Adding dropdowns
            statusComboBox = new JComboBox<>();
            positionComboBox = new JComboBox<>();
            supervisorComboBox = new JComboBox<>();
            departmentComboBox = new JComboBox<>();

            gbc.gridx = 0;
            gbc.gridy = labels.length;
            contentPane.add(new JLabel("Status"), gbc);

            gbc.gridx = 1;
            contentPane.add(statusComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = labels.length + 1;
            contentPane.add(new JLabel("Position"), gbc);

            gbc.gridx = 1;
            contentPane.add(positionComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = labels.length + 2;
            contentPane.add(new JLabel("Supervisor"), gbc);

            gbc.gridx = 1;
            contentPane.add(supervisorComboBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = labels.length + 3;
            contentPane.add(new JLabel("Department"), gbc);

            gbc.gridx = 1;
            contentPane.add(departmentComboBox, gbc);

            // Spacer for the 40-pixel gap
            gbc.gridx = 0;
            gbc.gridy = labels.length + 4;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(40, 5, 5, 5); // 40-pixel gap
            contentPane.add(new JLabel(""), gbc);

            // Button
            btnSave = new JButton(isAddingEmployee ? "Add" : "Update");
            btnSave.setFont(new Font("Poppins", Font.PLAIN, 16)); // Set font to Poppins
            btnSave.setPreferredSize(new Dimension(200, 50)); // Make the button bigger
            btnSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onSave();
                }
            });

            gbc.gridx = 0;
            gbc.gridy = labels.length + 5;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(5, 5, 5, 5); // Reset padding
            contentPane.add(btnSave, gbc);

            populateDropdowns(); // Populate dropdowns from database

            if (isAddingEmployee) {
                int nextId = EmployeeDAO.getInstance().getNextAutoIncrementValue();
                if (nextId != -1) {
                    formattedTextFields[0].setText(String.valueOf(nextId));
                    formattedTextFields[0].setEditable(false); // Make it non-editable
                }
            }

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getToolTipText(String fieldName) {
        switch (fieldName) {
            case "Birthday":
                return "Enter in YYYY-MM-DD format";
            case "Phone Number":
                return "Enter in XXX-XXX-XXX format";
            case "SSS #":
                return "Enter in XX-XXXXXXX-X format";
            case "Philhealth #":
                return "Enter 12-digit number";
            case "TIN #":
                return "Enter in XXX-XXX-XXX-XXX format";
            case "Pag-ibig #":
                return "Enter 12-digit number";
            case "Basic Salary":
                return "Enter amount with 2 decimal points";
            default:
                return "";
        }
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

        // Populate department dropdown
        Map<Integer, String> departments = employeeDAO.getAllDepartments();
        for (String department : departments.values()) {
            departmentComboBox.addItem(department);
        }
    }

    private void populateFields() {
        if (employee != null) {
            formattedTextFields[0].setText(String.valueOf(employee.getEmpId()));
            formattedTextFields[1].setText(employee.getLastName());
            formattedTextFields[2].setText(employee.getFirstName());
            formattedTextFields[3].setText(employee.getBirthday());
            formattedTextFields[4].setText(employee.getAddress());
            formattedTextFields[5].setText(employee.getPhoneNumber());
            formattedTextFields[6].setText(employee.getSssNumber());
            formattedTextFields[7].setText(employee.getPhilhealthNumber());
            formattedTextFields[8].setText(employee.getTinNumber());
            formattedTextFields[9].setText(employee.getPagibigNumber());
            formattedTextFields[10].setText(String.valueOf(employee.getBasicSalary()));
            statusComboBox.setSelectedItem(employee.getStatus());
            positionComboBox.setSelectedItem(employee.getPosition());
            supervisorComboBox.setSelectedItem(employee.getImmediateSupervisor());
            departmentComboBox.setSelectedItem(employee.getDepartment());
        }
    }

    private void onSave() {
        try {
            Integer id = isAddingEmployee ? 0 : Integer.parseInt(formattedTextFields[0].getText());
            String lastName = formattedTextFields[1].getText();
            String firstName = formattedTextFields[2].getText();
            String birthday = formattedTextFields[3].getText();
            String address = formattedTextFields[4].getText();
            String phoneNumber = formattedTextFields[5].getText();
            String sssNumber = formattedTextFields[6].getText();
            String philhealthNumber = formattedTextFields[7].getText();
            String tinNumber = formattedTextFields[8].getText();
            String pagibigNumber = formattedTextFields[9].getText();
            String department = (String) departmentComboBox.getSelectedItem();
            double basicSalary = Double.parseDouble(formattedTextFields[10].getText());
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
            String errorMessage = getSpecificErrorMessage();
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getSpecificErrorMessage() {
        if (!isValidDate(formattedTextFields[3].getText())) {
            return "Please enter a valid date in YYYY-MM-DD format.";
        }
        if (!isValidPhoneNumber(formattedTextFields[5].getText())) {
            return "Please enter a valid phone number in XXX-XXX-XXX format.";
        }
        if (!isValidSSSNumber(formattedTextFields[6].getText())) {
            return "Please enter a valid SSS number in XX-XXXXXXX-X format.";
        }
        if (!isValidPhilhealthNumber(formattedTextFields[7].getText())) {
            return "Please enter a valid Philhealth number in XXXXXXXXXXXX format.";
        }
        if (!isValidTINNumber(formattedTextFields[8].getText())) {
            return "Please enter a valid TIN number in XXX-XXX-XXX-XXX format.";
        }
        if (!isValidPagibigNumber(formattedTextFields[9].getText())) {
            return "Please enter a valid Pag-ibig number in XXXXXXXXXXXX format.";
        }
        if (!isValidBasicSalary(formattedTextFields[10].getText())) {
            return "Please enter a valid basic salary amount with 2 decimal points.";
        }
        return "Please enter valid data in all fields.";
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{3}");
    }

    private boolean isValidSSSNumber(String sssNumber) {
        return sssNumber.matches("\\d{2}-\\d{7}-\\d");
    }

    private boolean isValidPhilhealthNumber(String philhealthNumber) {
        return philhealthNumber.matches("\\d{12}");
    }

    private boolean isValidTINNumber(String tinNumber) {
        return tinNumber.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}");
    }

    private boolean isValidPagibigNumber(String pagibigNumber) {
        return pagibigNumber.matches("\\d{12}");
    }

    private boolean isValidBasicSalary(String basicSalary) {
        return basicSalary.matches("\\d+\\.\\d{2}");
    }
}
