package service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.PayslipDAO;
import DAO.TimesheetDAO;
import model.Employee;
import model.Payslip;
import service.PayslipService;

public class PayrollSalaryCalculationService {
	private TimesheetDAO timesheetDAO;
    private PayslipDAO payslipDAO;
    private DecimalFormat df = new DecimalFormat("#,##0.00");


    public PayrollSalaryCalculationService() {
        this.timesheetDAO = TimesheetDAO.getInstance();
        this.payslipDAO = PayslipDAO.getInstance();
    }

    public void populateMonthComboBox(JComboBox<String> monthComboBox) {
        List<String> monthYearCombinations = timesheetDAO.getUniqueMonthYearCombinations();      
        for (String monthYear : monthYearCombinations) {
            monthComboBox.addItem(monthYear);
        }
    }

    public void filterRecordsByMonthYear(String selectedMonthYear, JTable attendanceTable) {
        List<String[]> filteredRecords = timesheetDAO.getFilteredTimesheetRecords(selectedMonthYear);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Record ID", "Employee ID", "Last Name", "First Name", "Date", "Time In", "Time Out"});

        for (String[] record : filteredRecords) {
            model.addRow(record);
        }

        attendanceTable.setModel(model);
    }
    
    public void calculateMonthlyHoursWorked(String selectedMonthYear, List<Employee> employees, JTable salaryCalculationTable) {
        int successfulInsertions = 0;
        List<Integer> failedEmployeeIds = new ArrayList<>();

        PayslipService payslipService = new PayslipService(); // Instantiate PayslipService

        for (Employee employee : employees) {
            // Generate payslip for the employee for the selected month-year
            Payslip payslip = payslipService.generatePayslip(employee.getEmpId(), selectedMonthYear);

            // Insert the payslip into the database
            boolean inserted = payslipDAO.insertPayslip(payslip);
            if (inserted) {
                successfulInsertions++;
            } else {
                failedEmployeeIds.add(employee.getEmpId());
            }
        }

        // Display the result of the insertion process
        if (successfulInsertions == employees.size()) {
            JOptionPane.showMessageDialog(null, "Payslips added successfully for all employees.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (successfulInsertions == 0) {
            JOptionPane.showMessageDialog(null, "Failed to add payslips for all employees.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Payslips added successfully for " + successfulInsertions + " employees.\nFailed to add payslips for the following employee(s): " + failedEmployeeIds.toString(), "Partial Success", JOptionPane.WARNING_MESSAGE);
        }

        // Refresh the salary calculation table to reflect the updated data from the database
        refreshSalaryCalculationTable(selectedMonthYear, salaryCalculationTable); // Provide both arguments here
    }


    public void refreshSalaryCalculationTable(String selectedMonthYear, JTable salaryCalculationTable) {
        DefaultTableModel salaryModel = new DefaultTableModel();
        salaryModel.setColumnIdentifiers(new String[]{"Employee ID", "Employee Name", "Employee Position", "Hourly Rate", "Total Hours Worked", "Overtime Hours", "Gross Income"});

        if (selectedMonthYear.equals("Select Month-Year")) {
            clearTable(salaryCalculationTable, salaryModel);
            return;
        }

        List<Payslip> payslips = payslipDAO.getPayslipsByMonthYear(selectedMonthYear);

        if (payslips.isEmpty()) {
            salaryModel.addRow(new Object[]{"No records", "", "", "", "", "", ""});
        } else {
            for (Payslip payslip : payslips) {
                salaryModel.addRow(new Object[]{
                        payslip.getEmployeeId(),
                        payslip.getEmployeeName(),
                        payslip.getEmployeePosition(),
                        payslip.getHourlyRate(),
                        payslip.getTotalHours(),
                        payslip.getOvertimeHours(),
                        payslip.getGrossIncome()
                });
            }
        }

        salaryCalculationTable.setModel(salaryModel);
    }

    public void exportPayslipDetails(JTable salaryCalculationTable) {
        int selectedRowIndex = salaryCalculationTable.getSelectedRow();

        if (selectedRowIndex != -1) {
            Object employeeIdObject = salaryCalculationTable.getValueAt(selectedRowIndex, 0);
            if (employeeIdObject != null) {
                String employeeId = employeeIdObject.toString();
                Payslip payslip = payslipDAO.getPayslipByEmployeeId(employeeId);

                if (payslip != null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save Payslip Details");
                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        // Write payslip details to the selected file
                        writePayslipDetailsToFile(payslip);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Payslip details not found for selected employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Employee ID is null for selected row.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row in the salary calculation table.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Method to format decimal values with two decimal places
    private String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.##"); 
        return df.format(value);
    }

    
    public void writePayslipDetailsToFile(Payslip payslip) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Payslip Details");
        
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".txt");
                
                // Header
                writer.write("==============  MotorPH  ==============\n");
                writer.write("7 Jupiter Avenue cor. F. Sandoval Jr. \n");
                writer.write("Bagong Nayon, Quezon City\n");
                writer.write("Phone: (028) 911-5071 / (028) 911-5072\n");
                writer.write("Email: corporate@motorph.com\n\n");
                writer.write("=========== EMPLOYEE PAYSLIP ===========\n\n");

                // Write Employee Details
                writer.write("Employee Details -----------------------\n");
                writer.write("Employee ID: " + payslip.getEmployeeId() + "\n");
                writer.write("Employee Name: " + payslip.getEmployeeName() + "\n");
                writer.write("Position: " + payslip.getEmployeePosition() + "\n");
                writer.write("Period Start Date: " + payslip.getPeriodStartDate() + "\n");
                writer.write("Period End Date: " + payslip.getPeriodEndDate() + "\n\n");

                // Write Earnings
                writer.write("Earnings -------------------------------\n");
                writer.write("Monthly Rate: ₱" + df.format(payslip.getMonthlyRate()) + "\n");
                writer.write("Daily Rate: ₱" + df.format(payslip.getHourlyRate()) + "\n");
                writer.write("Hours Worked: " + payslip.getTotalHours() + "\n");
                writer.write("Gross Income: ₱" + df.format(payslip.getGrossIncome()) + "\n\n");

                // Write Benefits
                writer.write("Benefits -------------------------------\n");
                writer.write("Rice Subsidy: ₱" + df.format(payslip.getRiceSubsidy()) + "\n");
                writer.write("Phone Allowance: ₱" + df.format(payslip.getPhoneAllowance()) + "\n");
                writer.write("Clothing Allowance: ₱" + df.format(payslip.getClothingAllowance()) + "\n");
                double totalBenefits = payslip.getRiceSubsidy() + payslip.getPhoneAllowance() + payslip.getClothingAllowance();
                writer.write("Total Benefits: ₱" + df.format(totalBenefits) + "\n\n");

                // Write Deductions
                writer.write("Deductions -----------------------------\n");
                writer.write("SSS Contribution: ₱" + df.format(payslip.getSssContribution()) + "\n");
                writer.write("Philhealth Contribution: ₱" + df.format(payslip.getPhilhealthContribution()) + "\n");
                writer.write("Pag-ibig Contribution: ₱" + df.format(payslip.getPagibigContribution()) + "\n");
                double totalDeductions = payslip.getSssContribution() + payslip.getPhilhealthContribution() + payslip.getPagibigContribution();
                writer.write("Total Deductions: ₱" + df.format(totalDeductions) + "\n\n");

                // Write Summary
                writer.write("Summary --------------------------------\n");
                writer.write("Gross Income: ₱" + df.format(payslip.getGrossIncome()) + "\n");
                writer.write("Total Benefits: ₱" + df.format(totalBenefits) + "\n");
                writer.write("Total Deductions: ₱" + df.format(totalDeductions) + "\n");
                writer.write("Withholding Tax: ₱" + df.format(payslip.getWithholdingTax()) + "\n");
                double netPay = payslip.getGrossIncome() - totalDeductions + totalBenefits - payslip.getWithholdingTax();
                writer.write("Net Pay: ₱" + df.format(netPay) + "\n");

                writer.close();
                JOptionPane.showMessageDialog(null, "Payslip details exported successfully.", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error exporting payslip details: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
    }
}
    
    private void clearTable(JTable table, DefaultTableModel model) {
        table.setModel(model);
        JOptionPane.showMessageDialog(null, "Please select a month-year.", "No Month-Year Selected", JOptionPane.WARNING_MESSAGE);
    }

}
