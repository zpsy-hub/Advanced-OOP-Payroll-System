package model;

import java.util.List;

public class MonthlySummaryReportWithTotal extends MonthlySummaryReport {
    private List<MonthlySummaryReport> monthlySummaryReports;
    private double totalGrossIncome;
    private double totalSssContribution;
    private double totalPhilhealthContribution;
    private double totalPagibigContribution;
    private double totalWithholdingTax;
    private double totalNetPay;

    public MonthlySummaryReportWithTotal() {
        super(); 
    }

    // Getters and setters for the new totals
    public double getTotalGrossIncome() {
        return totalGrossIncome;
    }

    public void setTotalGrossIncome(double totalGrossIncome) {
        this.totalGrossIncome = totalGrossIncome;
    }

    public double getTotalSssContribution() {
        return totalSssContribution;
    }

    public void setTotalSssContribution(double totalSssContribution) {
        this.totalSssContribution = totalSssContribution;
    }

    public double getTotalPhilhealthContribution() {
        return totalPhilhealthContribution;
    }

    public void setTotalPhilhealthContribution(double totalPhilhealthContribution) {
        this.totalPhilhealthContribution = totalPhilhealthContribution;
    }

    public double getTotalPagibigContribution() {
        return totalPagibigContribution;
    }

    public void setTotalPagibigContribution(double totalPagibigContribution) {
        this.totalPagibigContribution = totalPagibigContribution;
    }

    public double getTotalWithholdingTax() {
        return totalWithholdingTax;
    }

    public void setTotalWithholdingTax(double totalWithholdingTax) {
        this.totalWithholdingTax = totalWithholdingTax;
    }

    public double getTotalNetPay() {
        return totalNetPay;
    }

    public void setTotalNetPay(double totalNetPay) {
        this.totalNetPay = totalNetPay;
    }

    // Method to calculate the totals
    public void calculateTotals(List<MonthlySummaryReport> reports) {
        totalGrossIncome = reports.stream().mapToDouble(MonthlySummaryReport::getGrossIncome).sum();
        totalSssContribution = reports.stream().mapToDouble(MonthlySummaryReport::getSssContribution).sum();
        totalPhilhealthContribution = reports.stream().mapToDouble(MonthlySummaryReport::getPhilhealthContribution).sum();
        totalPagibigContribution = reports.stream().mapToDouble(MonthlySummaryReport::getPagibigContribution).sum();
        totalWithholdingTax = reports.stream().mapToDouble(MonthlySummaryReport::getWithholdingTax).sum();
        totalNetPay = reports.stream().mapToDouble(MonthlySummaryReport::getNetPay).sum();
    }

    // Setter for monthlySummaryReports
    public void setMonthlySummaryReports(List<MonthlySummaryReport> monthlySummaryReports) {
        this.monthlySummaryReports = monthlySummaryReports;
    }

    // Getter for monthlySummaryReports
    public List<MonthlySummaryReport> getMonthlySummaryReports() {
        return monthlySummaryReports;
    }
}
