package model;

public class salaryCalculator {

public double sssContribution;
public double philhealthContribution;
public double pagibigContribution;
public double totalDeductions;
public double taxableIncome;
public double withholdingTax;
 
 
// Constructors   
public salaryCalculator(double sssContribution, double philhealthContribution, double pagibigContribution, double totalDeductions, double taxableIncome, double withholdingTax) {
    this.sssContribution = sssContribution;
    this.philhealthContribution = philhealthContribution;
    this.pagibigContribution = pagibigContribution;
    this.totalDeductions = totalDeductions;
    this.taxableIncome = taxableIncome;
    this.withholdingTax = withholdingTax;
}

 // Getters and setters
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

 public double getTotalDeductions() {
      return totalDeductions;
 }

 public void setTotalDeductions(double totalDeductions) {
      this.totalDeductions = totalDeductions;
 }

 public double getTaxableIncome() {
      return taxableIncome;
 }

 public void setTaxableIncome(double taxableIncome) {
      this.taxableIncome = taxableIncome;
 }

 public double getWithholdingTax() {
      return withholdingTax;
 }

 public void setWithholdingTax(double withholdingTax) {
      this.withholdingTax = withholdingTax;
 }
 
}
