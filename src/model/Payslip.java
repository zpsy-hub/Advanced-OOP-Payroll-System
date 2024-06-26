package model;

import java.time.LocalDate;

public class Payslip {
    private LocalDate periodStartDate;
    private LocalDate periodEndDate;
    private int employeeId;
    private String employeeName;
    private String employeePosition;
    private double hourlyRate;
    private double basicSalary;
    private int totalHours;
    private int overtimeHours;
    private double grossIncome;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double totalAllowances;
    private double sssContribution;
    private double philhealthContribution;
    private double pagibigContribution;
    private double withholdingTax;
    private double totalDeductions;
    private double netPay;
    private int payslipId;
    private int payrollPeriodId;
    private LocalDate dateGenerated;

    // Full constructor including all fields
    public Payslip(LocalDate periodStartDate, LocalDate periodEndDate, int employeeId, String employeeName,
                   String employeePosition, double hourlyRate, double basicSalary, int totalHours, int overtimeHours,
                   double grossIncome, double riceSubsidy, double phoneAllowance, double clothingAllowance,
                   double totalAllowances, double sssContribution, double philhealthContribution,
                   double pagibigContribution, double withholdingTax, double totalDeductions, double netPay) {
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hourlyRate = hourlyRate;
        this.basicSalary = basicSalary;
        this.totalHours = totalHours;
        this.overtimeHours = overtimeHours;
        this.grossIncome = grossIncome;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.totalAllowances = totalAllowances;
        this.sssContribution = sssContribution;
        this.philhealthContribution = philhealthContribution;
        this.pagibigContribution = pagibigContribution;
        this.withholdingTax = withholdingTax;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
    }

    // Additional constructor using employee ID and fetching hourly rate from DAO
    public Payslip(LocalDate periodStartDate, LocalDate periodEndDate, int employeeId, String employeeName,
                   String employeePosition, int totalHours, int overtimeHours, double grossIncome,
                   double riceSubsidy, double phoneAllowance, double clothingAllowance, double totalAllowances,
                   double sssContribution, double philhealthContribution, double pagibigContribution,
                   double withholdingTax, double totalDeductions, double netPay) {
        this(periodStartDate, periodEndDate, employeeId, employeeName, employeePosition,
                EmployeeDAO.getInstance().getHourlyRateById(employeeId), 0, totalHours, overtimeHours, grossIncome,
                riceSubsidy, phoneAllowance, clothingAllowance, totalAllowances, sssContribution, philhealthContribution,
                pagibigContribution, withholdingTax, totalDeductions, netPay);
    }

    // Constructor for retrieving from payslip_view
    public Payslip(LocalDate periodStartDate, LocalDate periodEndDate, int employeeId, String employeeName,
                   String employeePosition, double hourlyRate, double basicSalary, double totalHoursWorked,
                   double overtimeHours, double grossIncome, double riceSubsidy, double phoneAllowance,
                   double clothingAllowance, double totalAllowances, double sssContribution,
                   double philhealthContribution, double pagibigContribution, double withholdingTax,
                   double totalDeductions, double netPay) {
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hourlyRate = hourlyRate;
        this.basicSalary = basicSalary;
        this.totalHours = (int) totalHoursWorked;
        this.overtimeHours = (int) overtimeHours;
        this.grossIncome = grossIncome;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.totalAllowances = totalAllowances;
        this.sssContribution = sssContribution;
        this.philhealthContribution = philhealthContribution;
        this.pagibigContribution = pagibigContribution;
        this.withholdingTax = withholdingTax;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
    }

    // Constructor for retrieving from payslips (newly inserted payslips)
    public Payslip(int payslipId, int employeeId, LocalDate periodStartDate, LocalDate periodEndDate,
                   double grossIncome, double totalDeductions, double totalAllowances, double netPay) {
        this.payslipId = payslipId;
        this.employeeId = employeeId;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.grossIncome = grossIncome;
        this.totalDeductions = totalDeductions;
        this.totalAllowances = totalAllowances;
        this.netPay = netPay;
    }
    
    public Payslip(int employeeId, String employeeName, String employeePosition, double hourlyRate, int totalHours, int overtimeHours, double grossIncome, double riceSubsidy, double phoneAllowance, double clothingAllowance, double totalAllowances, double sssContribution, double philhealthContribution, double pagibigContribution, double withholdingTax, double totalDeductions, double netPay, LocalDate periodStartDate, LocalDate periodEndDate, int payrollPeriodId, LocalDate dateGenerated) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.hourlyRate = hourlyRate;
        this.totalHours = totalHours;
        this.overtimeHours = overtimeHours;
        this.grossIncome = grossIncome;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.totalAllowances = totalAllowances;
        this.sssContribution = sssContribution;
        this.philhealthContribution = philhealthContribution;
        this.pagibigContribution = pagibigContribution;
        this.withholdingTax = withholdingTax;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.payrollPeriodId = payrollPeriodId;
        this.dateGenerated = dateGenerated;
    }
    
    public Payslip(int payslipId, LocalDate periodStartDate, LocalDate periodEndDate, int employeeId, String employeeName,
            String employeePosition, double hourlyRate, double basicSalary, int totalHours, int overtimeHours,
            double grossIncome, double riceSubsidy, double phoneAllowance, double clothingAllowance,
            double totalAllowances, double sssContribution, double philhealthContribution,
            double pagibigContribution, double withholdingTax, double totalDeductions, double netPay) {
 this.payslipId = payslipId;
 this.periodStartDate = periodStartDate;
 this.periodEndDate = periodEndDate;
 this.employeeId = employeeId;
 this.employeeName = employeeName;
 this.employeePosition = employeePosition;
 this.hourlyRate = hourlyRate;
 this.basicSalary = basicSalary;
 this.totalHours = totalHours;
 this.overtimeHours = overtimeHours;
 this.grossIncome = grossIncome;
 this.riceSubsidy = riceSubsidy;
 this.phoneAllowance = phoneAllowance;
 this.clothingAllowance = clothingAllowance;
 this.totalAllowances = totalAllowances;
 this.sssContribution = sssContribution;
 this.philhealthContribution = philhealthContribution;
 this.pagibigContribution = pagibigContribution;
 this.withholdingTax = withholdingTax;
 this.totalDeductions = totalDeductions;
 this.netPay = netPay;
}

    // Getters for all fields
    public LocalDate getPeriodStartDate() {
        return periodStartDate;
    }

    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public double getTotalAllowances() {
        return totalAllowances;
    }

    public double getSssContribution() {
        return sssContribution;
    }

    public double getPhilhealthContribution() {
        return philhealthContribution;
    }

    public double getPagibigContribution() {
        return pagibigContribution;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public int getPayslipId() {
        return payslipId;
    }

    public int getPayrollPeriodId() {
        return payrollPeriodId;
    }

    public LocalDate getDateGenerated() {
        return dateGenerated;
    }

    // Override toString() method for debugging or logging purposes
    @Override
    public String toString() {
        return "Payslip{" +
                "periodStartDate=" + periodStartDate +
                ", periodEndDate=" + periodEndDate +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeePosition='" + employeePosition + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", basicSalary=" + basicSalary +
                ", totalHours=" + totalHours +
                ", overtimeHours=" + overtimeHours +
                ", grossIncome=" + grossIncome +
                ", riceSubsidy=" + riceSubsidy +
                ", phoneAllowance=" + phoneAllowance +
                ", clothingAllowance=" + clothingAllowance +
                ", totalAllowances=" + totalAllowances +
                ", sssContribution=" + sssContribution +
                ", philhealthContribution=" + philhealthContribution +
                ", pagibigContribution=" + pagibigContribution +
                ", withholdingTax=" + withholdingTax +
                ", totalDeductions=" + totalDeductions +
                ", netPay=" + netPay +
                '}';
    }
}
