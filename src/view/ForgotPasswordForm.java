package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import DAO.CredentialsManagementDAO;
import model.Employee;

public class ForgotPasswordForm extends JDialog {
    private JTextField employeeIdField;
    private JTextField nameField;
    private JTextField positionField;
    private JTextField departmentField;
    private JTextField newPasswordField;
    private CredentialsManagementDAO credentialsManagementDAO;

    public ForgotPasswordForm() {
        credentialsManagementDAO = new CredentialsManagementDAO();

        setTitle("Forgot Password");
        setBounds(100, 100, 450, 450);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblEmployeeId = new JLabel("Employee ID");
        lblEmployeeId.setBounds(33, 88, 120, 25);
        lblEmployeeId.setFont(new Font("Poppins", Font.PLAIN, 16));
        getContentPane().add(lblEmployeeId);

        employeeIdField = new JTextField();
        employeeIdField.setBounds(187, 88, 200, 25);
        employeeIdField.setFont(new Font("Poppins", Font.PLAIN, 15));
        employeeIdField.setEditable(true);
        employeeIdField.setFocusable(true);
        getContentPane().add(employeeIdField);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(33, 128, 120, 25);
        lblName.setFont(new Font("Poppins", Font.PLAIN, 16));
        getContentPane().add(lblName);

        nameField = new JTextField();
        nameField.setBounds(187, 128, 200, 25);
        nameField.setFont(new Font("Poppins", Font.PLAIN, 15));
        nameField.setEditable(true);
        nameField.setFocusable(true);
        getContentPane().add(nameField);

        JLabel lblPosition = new JLabel("Position");
        lblPosition.setBounds(33, 168, 120, 25);
        lblPosition.setFont(new Font("Poppins", Font.PLAIN, 16));
        getContentPane().add(lblPosition);

        positionField = new JTextField();
        positionField.setBounds(187, 168, 200, 25);
        positionField.setFont(new Font("Poppins", Font.PLAIN, 15));
        positionField.setEditable(true);
        positionField.setFocusable(true);
        getContentPane().add(positionField);

        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(33, 208, 120, 25);
        lblDepartment.setFont(new Font("Poppins", Font.PLAIN, 16));
        getContentPane().add(lblDepartment);

        departmentField = new JTextField();
        departmentField.setBounds(187, 208, 200, 25);
        departmentField.setFont(new Font("Poppins", Font.PLAIN, 15));
        departmentField.setEditable(true);
        departmentField.setFocusable(true);
        getContentPane().add(departmentField);

        JLabel lblNewPassword = new JLabel("New Password");
        lblNewPassword.setBounds(33, 248, 150, 25);
        lblNewPassword.setFont(new Font("Poppins", Font.PLAIN, 16));
        getContentPane().add(lblNewPassword);

        newPasswordField = new JTextField();
        newPasswordField.setBounds(187, 248, 200, 25);
        newPasswordField.setFont(new Font("Poppins", Font.PLAIN, 15));
        newPasswordField.setEditable(true);
        newPasswordField.setFocusable(true);
        getContentPane().add(newPasswordField);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(187, 305, 200, 30);
        btnSubmit.setFont(new Font("Montserrat SemiBold", Font.BOLD, 20));
        btnSubmit.setBackground(new Color(30, 55, 101));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getContentPane().add(btnSubmit);

        JLabel lblChangePasswordVerification = new JLabel("Change Password Verification");
        lblChangePasswordVerification.setHorizontalAlignment(SwingConstants.CENTER);
        lblChangePasswordVerification.setFont(new Font("Montserrat SemiBold", Font.PLAIN, 18));
        lblChangePasswordVerification.setBounds(33, 29, 354, 25);
        getContentPane().add(lblChangePasswordVerification);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeIdField.getText();
                String name = nameField.getText();
                String position = positionField.getText();
                String department = departmentField.getText();
                String newPassword = newPasswordField.getText();

                // Verify the entered information
                verifyInformation(employeeId, name, position, department, newPassword);
            }
        });
    }

    private void verifyInformation(String employeeId, String name, String position, String department, String newPassword) {
        // Attempt to parse the employee ID
        int empId;
        try {
            empId = Integer.parseInt(employeeId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID format. Please enter a valid number.");
            return;
        }

        // Retrieve the employee by ID
        Employee employee = credentialsManagementDAO.getEmployeeById(empId);

        // Check if the employee exists and details match
        if (employee != null &&
            employee.getEmpId() == empId &&
            (employee.getFirstName() + " " + employee.getLastName()).equalsIgnoreCase(name) &&
            employee.getPosition().equalsIgnoreCase(position) &&
            employee.getDepartment().equalsIgnoreCase(department)) {

            // Allow the employee to reset their password
            boolean isUpdated = credentialsManagementDAO.updatePassword(empId, newPassword, empId); // empId to indicate the employee themselves

            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "Password reset successful. IT will be notified.");
                // Notify IT (you can add your notification logic here)
            } else {
                JOptionPane.showMessageDialog(this, "Failed to reset password. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid details. Please try again.");
        }
    }
}