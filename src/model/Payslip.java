package model;
import java.time.LocalDate;

public class Payslip {
    private String payslipNumber;
    private LocalDate periodStartDate;
    private LocalDate periodEndDate;
    private int employeeId;
    private String employeeName;
    private String employeePosition;
    private String employeeDepartment;
    private double monthlyRate;
    private int totalHours;
    private int overtimeHours;
    private double grossIncome;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double totalBenefits;
    private double sssContribution;
    private double philhealthContribution;
    private double pagibigContribution;
    private double withholdingTax;
    private double totalDeductions;
    private double netPay;

    // Constructor
    public Payslip(String payslipNumber, LocalDate periodStartDate, LocalDate periodEndDate,
                   int employeeId, String employeeName, String employeePosition,
                   String employeeDepartment, double monthlyRate, int totalHours, int overtimeHours,
                   double riceSubsidy, double phoneAllowance, double clothingAllowance,
                   double sssContribution, double philhealthContribution,
                   double pagibigContribution, double withholdingTax) {
        this.payslipNumber = payslipNumber;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.employeeDepartment = employeeDepartment;
        this.monthlyRate = monthlyRate;
        this.totalHours = totalHours;
        this.overtimeHours = overtimeHours;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.sssContribution = sssContribution;
        this.philhealthContribution = philhealthContribution;
        this.pagibigContribution = pagibigContribution;
        this.withholdingTax = withholdingTax;

        // Calculate other fields
        calculateGrossIncome();
        calculateTotalBenefits();
        calculateTotalDeductions();
        calculateNetPay();
    }

    // Method to calculate gross income
    private void calculateGrossIncome() {
        grossIncome = (monthlyRate / 30) * totalHours + (overtimeHours * monthlyRate / 30 * 1.5);
    }

    // Method to calculate total benefits
    private void calculateTotalBenefits() {
        totalBenefits = riceSubsidy + phoneAllowance + clothingAllowance;
    }

    // Method to calculate total deductions
    private void calculateTotalDeductions() {
        totalDeductions = sssContribution + philhealthContribution + pagibigContribution + withholdingTax;
    }

    // Method to calculate net pay
    private void calculateNetPay() {
        netPay = grossIncome - totalDeductions;
    }

    // Getters and Setters
    public String getPayslipNumber() {
        return payslipNumber;
    }

    public void setPayslipNumber(String payslipNumber) {
        this.payslipNumber = payslipNumber;
    }

    public LocalDate getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(LocalDate periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public void setRiceSubsidy(double riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public double getTotalBenefits() {
        return totalBenefits;
    }

    public void setTotalBenefits(double totalBenefits) {
        this.totalBenefits = totalBenefits;
    }

    public double getSssContribution() {
        return sssContribution;
    }

    public void setSssContribution(double sssContribution) {
        this.sssContribution = sssContribution;
    }

    public double getPhilhealthContribution() {
        return philhealthContribution;
    }

    public void setPhilhealthContribution(double philhealthContribution) {
        this.philhealthContribution = philhealthContribution;
    }

    public double getPagibigContribution() {
        return pagibigContribution;
    }

    public void setPagibigContribution(double pagibigContribution) {
        this.pagibigContribution = pagibigContribution;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }
}

