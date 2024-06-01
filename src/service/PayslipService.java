package service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import DAO.EmployeeDAO;
import DAO.TimesheetDAO;
import model.Employee;
import model.Payslip;
import util.DeductionCalculator;

public class PayslipService {
    private TimesheetDAO timesheetDAO;
    private EmployeeDAO employeeDAO;

    public PayslipService() {
        timesheetDAO = TimesheetDAO.getInstance();
        employeeDAO = EmployeeDAO.getInstance();
    }

    // Method to calculate the gross salary for a specific employee for a given month and year
    public double calculateGrossSalary(int empId, String monthYear) {
        // Get the employee's hourly rate
        double hourlyRate = employeeDAO.getHourlyRateById(empId);

        // Get the timesheet records for the specified month and year
        List<String[]> timesheetRecords = timesheetDAO.getFilteredTimesheetRecords(empId, monthYear);

        // Calculate total hours worked
        int totalHoursWorked = calculateTotalHoursWorked(timesheetRecords);

        // Calculate gross salary
        double grossSalary = hourlyRate * totalHoursWorked;

        return grossSalary;
    }

    // Method to calculate the total hours worked based on timesheet records
    private int calculateTotalHoursWorked(List<String[]> timesheetRecords) {
        int totalHours = 0;
        int absences = 0;
        int lates = 0;
        int overtimeHours = 0;
        
        for (String[] record : timesheetRecords) {
            String timeIn = record[1];
            String timeOut = record[2];

            if (timeIn == null || timeOut == null) {
                // If either time in or time out is null, count as absence
                absences++;
                continue; // Skip to the next record
            }

            // Calculate hours worked for each day and add to total hours
            int hoursWorked = calculateHoursWorked(timeIn, timeOut);
            totalHours += hoursWorked;
            
            // Check for late arrivals
            if (LocalTime.parse(timeIn).isAfter(LocalTime.of(8, 11))) {
                lates++;
            }
            
            // Check for overtime
            if (hoursWorked > 8) {
                overtimeHours += hoursWorked - 8;
            }
        }
        return totalHours;
    }

    // Method to calculate hours worked between time in and time out
    private int calculateHoursWorked(String timeIn, String timeOut) {
        LocalTime startTime = LocalTime.parse(timeIn);
        LocalTime endTime = LocalTime.parse(timeOut);

        // Define regular working hours
        LocalTime regularStartTime = LocalTime.of(8, 0); // 8:00 AM
        LocalTime regularEndTime = LocalTime.of(17, 0); // 5:00 PM

        // Define break time
        LocalTime breakStartTime = LocalTime.of(12, 0); // 12:00 PM
        LocalTime breakEndTime = LocalTime.of(13, 0); // 1:00 PM

        // Calculate regular hours, late hours, and overtime
        int regularHours = 0;
        int lateHours = 0;
        int overtimeHours = 0;

        // Check if the employee started work on time
        if (startTime.isBefore(regularStartTime)) {
            // Calculate late hours
            Duration lateDuration = Duration.between(startTime, regularStartTime);
            lateHours += (int) lateDuration.toMinutes() / 60;
            startTime = regularStartTime; // Adjust start time to regular start time
        }

        // Check if the employee worked beyond regular working hours
        if (endTime.isAfter(regularEndTime)) {
            // Calculate overtime
            Duration overtimeDuration = Duration.between(regularEndTime, endTime);
            overtimeHours += (int) overtimeDuration.toMinutes() / 60;
            endTime = regularEndTime; // Adjust end time to regular end time
        }

        // Calculate regular hours
        Duration regularDuration = Duration.between(startTime, endTime);
        regularHours += (int) regularDuration.toMinutes() / 60;

        // Subtract break time from regular hours
        if (startTime.isBefore(breakEndTime) && endTime.isAfter(breakStartTime)) {
            Duration breakDuration = Duration.between(breakStartTime, breakEndTime);
            regularHours -= (int) breakDuration.toMinutes() / 60; // Subtract break time
        }

        // Ensure regular hours do not exceed 8 hours
        regularHours = Math.min(regularHours, 8);

        // Return total hours worked
        return regularHours + lateHours + overtimeHours;
    }
    
    // Method to count absences in a month
    public int countAbsences(int empId, String monthYear) {
        List<String[]> timesheetRecords = timesheetDAO.getFilteredTimesheetRecords(empId, monthYear);
        int absences = 0;
        
        for (String[] record : timesheetRecords) {
            String timeIn = record[1];
            String timeOut = record[2];

            if (timeIn == null || timeOut == null) {
                absences++;
            }
        }
        
        return absences;
    }
    
    // Method to count lates in a month
    public int countLates(int empId, String monthYear) {
        List<String[]> timesheetRecords = timesheetDAO.getFilteredTimesheetRecords(empId, monthYear);
        int lates = 0;
        
        for (String[] record : timesheetRecords) {
            String timeIn = record[1];
            String timeOut = record[2];

            if (timeIn != null && timeOut != null && LocalTime.parse(timeIn).isAfter(LocalTime.of(8, 11))) {
                lates++;
            }
        }
        
        return lates;
    }
    
    // Method to count overtime hours in a month
    public int countOvertimeHours(int empId, String monthYear) {
        List<String[]> timesheetRecords = timesheetDAO.getFilteredTimesheetRecords(empId, monthYear);
        int overtimeHours = 0;
        
        for (String[] record : timesheetRecords) {
            String timeIn = record[1];
            String timeOut = record[2];

            if (timeIn != null && timeOut != null) {
                int hoursWorked = calculateHoursWorked(timeIn, timeOut);
                if (hoursWorked > 8) {
                    overtimeHours += hoursWorked - 8;
                }
            }
        }
        
        return overtimeHours;
    }
    
    // Method to calculate SSS contribution for a specific employee for a given month and year
    public double calculateSSSContribution(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        return DeductionCalculator.calculateSSSContribution(grossSalary);
    }

    // Method to calculate Philhealth contribution for a specific employee for a given month and year
    public double calculatePhilhealthContribution(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        return DeductionCalculator.calculatePhilhealthContribution(grossSalary);
    }

    // Method to calculate Pag-ibig contribution for a specific employee for a given month and year
    public double calculatePagibigContribution(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        return DeductionCalculator.calculatePagibigContribution(grossSalary);
    }

    // Method to calculate total deductions for a specific employee for a given month and year
    public double calculateTotalDeductions(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        return DeductionCalculator.calculateTotalDeductions(grossSalary);
    }

    // Method to calculate the taxable income for a specific employee for a given month and year
    public double calculateTaxableIncome(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        double totalDeductions = calculateTotalDeductions(empId, monthYear);
        return grossSalary - totalDeductions;
    }

    // Method to calculate net pay for a specific employee for a given month and year
    public double calculateNetPay(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        double totalBenefits = calculateTotalBenefits(empId);
        double totalDeductions = calculateTotalDeductions(empId, monthYear);
        double withholdingTax = calculateWithholdingTax(empId, monthYear);

        double netPay = grossSalary - totalDeductions - withholdingTax + totalBenefits;
        return netPay;
    }


    
    private double calculateWithholdingTax(int empId, String monthYear) {
        double grossSalary = calculateGrossSalary(empId, monthYear);
        double taxableIncome = DeductionCalculator.calculateTaxableIncome(grossSalary);
        return DeductionCalculator.calculateWithholdingTax(taxableIncome);
    }

    // Method to retrieve employee's allowances and calculate total benefits
    public double calculateTotalBenefits(int empId) {
        double totalBenefits = 0;
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee != null) {
            // Assuming allowances (rice, phone, clothing) are properties of Employee
            double riceAllowance = employee.getRiceSubsidy();
            double phoneAllowance = employee.getPhoneAllowance();
            double clothingAllowance = employee.getClothingAllowance();
            totalBenefits = riceAllowance + phoneAllowance + clothingAllowance;
        }
        return totalBenefits;
    }
    
    // Method to generate a payslip for a specific employee for a given month and year
    public Payslip generatePayslip(int empId, String monthYear) {
        // Retrieve employee details
        Employee employee = employeeDAO.getEmployeeById(empId);
        String empName = employee.getFirstName() + " " + employee.getLastName();
        String empPosition = employee.getPosition();
        double monthlyRate = employee.getBasicSalary();
        double riceSubsidy = employee.getRiceSubsidy();
        double phoneAllowance = employee.getPhoneAllowance();
        double clothingAllowance = employee.getClothingAllowance();

        // Calculate gross salary
        double grossSalary = calculateGrossSalary(empId, monthYear);

        // Calculate total benefits
        double totalBenefits = calculateTotalBenefits(empId);

        // Calculate total deductions
        double totalDeductions = calculateTotalDeductions(empId, monthYear);

        // Calculate net pay
        double netPay = calculateNetPay(empId, monthYear);

        // Calculate SSS contribution
        double sssContribution = calculateSSSContribution(empId, monthYear);

        // Calculate Philhealth contribution
        double philhealthContribution = calculatePhilhealthContribution(empId, monthYear);

        // Calculate Pag-ibig contribution
        double pagibigContribution = calculatePagibigContribution(empId, monthYear);

        // Calculate withholding tax
        double withholdingTax = calculateWithholdingTax(empId, monthYear);

        // Retrieve pay period start and end dates from TimesheetDAO
        LocalDate payPeriodStartDate = timesheetDAO.calculatePayPeriodStartDate(monthYear);
        LocalDate payPeriodEndDate = timesheetDAO.calculatePayPeriodEndDate(monthYear);
        
     // Retrieve hourly rate from the EmployeeDAO or calculate it based on your logic
        double hourlyRate = employeeDAO.getHourlyRateById(empId);

        // Create and return the payslip object
        return new Payslip(
        	    payPeriodStartDate,
        	    payPeriodEndDate,
        	    empId,
        	    empName,
        	    empPosition,
        	    hourlyRate, 
        	    monthlyRate,
        	    calculateTotalHoursWorked(timesheetDAO.getFilteredTimesheetRecords(empId, monthYear)),
        	    countOvertimeHours(empId, monthYear),
        	    grossSalary, 
        	    riceSubsidy,
        	    phoneAllowance,
        	    clothingAllowance,
        	    totalBenefits,
        	    sssContribution,
        	    philhealthContribution,
        	    pagibigContribution,
        	    withholdingTax,
        	    totalDeductions,
        	    netPay
        	);
    }
    
       
    // Method to display total hours worked for a specific employee in a given month
    public void displayTotalHours(int empId, String monthYear) {
        List<String[]> timesheetRecords = timesheetDAO.getFilteredTimesheetRecords(empId, monthYear);
        int totalHoursWorked = calculateTotalHoursWorked(timesheetRecords);
        
        System.out.println("Total hours worked for Employee ID " + empId + " in " + monthYear + ": " + totalHoursWorked + " hours");
    }
    
    // Main method to test the PayslipService
    public static void main(String[] args) {
        // Create an instance of PayslipService
        PayslipService calculator = new PayslipService();

        // Assuming employee ID and month-year for testing
        int employeeId = 1;
        String monthYear = "2023-02"; // Example month-year

        // Calculate gross salary for the specified employee and month-year
        double grossSalary = calculator.calculateGrossSalary(employeeId, monthYear);
        
        // Count absences for the specified employee and month-year
        int absences = calculator.countAbsences(employeeId, monthYear);

        // Count lates for the specified employee and month-year
        int lates = calculator.countLates(employeeId, monthYear);

        // Count overtime hours for the specified employee and month-year
        int overtimeHours = calculator.countOvertimeHours(employeeId, monthYear);
        
        // Display total hours worked for the specified employee and month-year
        calculator.displayTotalHours(employeeId, monthYear);
        
        // Generate the payslip for the specified employee and month-year
        Payslip payslip = calculator.generatePayslip(employeeId, monthYear);


        // Output the results
        System.out.println("Gross Salary for Employee ID " + employeeId + " in " + monthYear + ": $" + grossSalary);
        System.out.println("Absences: " + absences);
        System.out.println("Lates: " + lates);
        System.out.println("Overtime Hours: " + overtimeHours);
        System.out.println("Payslip for Employee ID " + employeeId + " in " + monthYear + ":");
        System.out.println("Period Start Date: " + payslip.getPeriodStartDate());
        System.out.println("Period End Date: " + payslip.getPeriodEndDate());
        System.out.println("Employee Name: " + payslip.getEmployeeName());
        System.out.println("Employee Position: " + payslip.getEmployeePosition());
        System.out.println("Monthly Rate: $" + payslip.getMonthlyRate());
        System.out.println("Total Hours: " + payslip.getTotalHours());
        System.out.println("Overtime Hours: " + payslip.getOvertimeHours());
        System.out.println("Gross Income: $" + payslip.getGrossIncome());
        System.out.println("Rice Subsidy: $" + payslip.getRiceSubsidy());
        System.out.println("Phone Allowance: $" + payslip.getPhoneAllowance());
        System.out.println("Clothing Allowance: $" + payslip.getClothingAllowance());
        System.out.println("SSS Contribution: $" + payslip.getSssContribution());
        System.out.println("Philhealth Contribution: $" + payslip.getPhilhealthContribution());
        System.out.println("Pagibig Contribution: $" + payslip.getPagibigContribution());
        System.out.println("Withholding Tax: $" + payslip.getWithholdingTax());
        System.out.println("Net Pay: " + payslip.getNetPay());
    }
}
