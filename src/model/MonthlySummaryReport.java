package model;

public class MonthlySummaryReport {
    private int employeeId;
    private String employeeName;
    private String position;
    private String department;
    private double grossIncome;
    private String sssNumber;
    private double sssContribution;
    private String philhealthNumber;
    private double philhealthContribution;
    private String pagibigNumber;
    private double pagibigContribution;
    private String tinNumber;
    private double withholdingTax;
    private double netPay;

    // Constructors
    public MonthlySummaryReport() {
    }

    public MonthlySummaryReport(int employeeId, String employeeName, String position, String department, 
                                double grossIncome, String sssNumber, double sssContribution, 
                                String philhealthNumber, double philhealthContribution, 
                                String pagibigNumber, double pagibigContribution, 
                                String tinNumber, double withholdingTax, double netPay) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.position = position;
        this.department = department;
        this.grossIncome = grossIncome;
        this.sssNumber = sssNumber;
        this.sssContribution = sssContribution;
        this.philhealthNumber = philhealthNumber;
        this.philhealthContribution = philhealthContribution;
        this.pagibigNumber = pagibigNumber;
        this.pagibigContribution = pagibigContribution;
        this.tinNumber = tinNumber;
        this.withholdingTax = withholdingTax;
        this.netPay = netPay;
    }

    // Getters and Setters
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public String getSssNumber() {
        return sssNumber;
    }

    public void setSssNumber(String sssNumber) {
        this.sssNumber = sssNumber;
    }

    public double getSssContribution() {
        return sssContribution;
    }

    public void setSssContribution(double sssContribution) {
        this.sssContribution = sssContribution;
    }

    public String getPhilhealthNumber() {
        return philhealthNumber;
    }

    public void setPhilhealthNumber(String philhealthNumber) {
        this.philhealthNumber = philhealthNumber;
    }

    public double getPhilhealthContribution() {
        return philhealthContribution;
    }

    public void setPhilhealthContribution(double philhealthContribution) {
        this.philhealthContribution = philhealthContribution;
    }

    public String getPagibigNumber() {
        return pagibigNumber;
    }

    public void setPagibigNumber(String pagibigNumber) {
        this.pagibigNumber = pagibigNumber;
    }

    public double getPagibigContribution() {
        return pagibigContribution;
    }

    public void setPagibigContribution(double pagibigContribution) {
        this.pagibigContribution = pagibigContribution;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }
}
