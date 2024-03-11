package service;

import java.util.ArrayList;

import model.Employee;

public class DeductionCalculator {
    
    public static double calculateSSSContribution(double grossSalary) {
        double contribSSS;
        
        if (grossSalary <= 3250) {
            contribSSS = 135;
        } else if (grossSalary >= 24750) {
            contribSSS = 1125;
        } else {
            contribSSS = Math.ceil((grossSalary - 3250) / 500) * 22.5 + 135;
        }
        
        return contribSSS;
    }

    public static double calculatePhilhealthContribution(double grossSalary) {
        double contribPhilhealth;
        
        if (grossSalary <= 1000) {
            contribPhilhealth = 150;
        } else if (grossSalary < 60000) {
            contribPhilhealth = grossSalary * 0.015;
        } else {
            contribPhilhealth = 900;
        }
        
        return contribPhilhealth;
    }

    public static double calculatePagibigContribution(double grossSalary) {
        double contribPagibig;
        
        if (grossSalary <= 1500) {
            contribPagibig = grossSalary * 0.01;
        } else if (grossSalary <= 5000) {
            contribPagibig = grossSalary * 0.02;
        } else {
            contribPagibig = 100;
        }
        
        return contribPagibig;
    }

    public static double calculateTotalDeductions(double grossSalary) {
        double sssContribution = calculateSSSContribution(grossSalary);
        double philhealthContribution = calculatePhilhealthContribution(grossSalary);
        double pagibigContribution = calculatePagibigContribution(grossSalary);

        double totalDeductions = sssContribution + philhealthContribution + pagibigContribution;

        return totalDeductions;
    }
    
    public static double calculateTaxableIncome(double grossSalary) {
        double totalDeductions = DeductionCalculator.calculateTotalDeductions(grossSalary);
        double taxableIncome = grossSalary - totalDeductions;
        return taxableIncome;
    }

    public static double calculateWithholdingTax(double taxableIncome) {
        double withholdingTax = 0.0;
        if (taxableIncome <= 20832) {
            withholdingTax = 0.0;
        } else if (taxableIncome <= 33333) {
            withholdingTax = (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 66667) {
            withholdingTax = 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166667) {
            withholdingTax = 10833 + (taxableIncome - 66667) * 0.30;
        } else if (taxableIncome <= 666667) {
            withholdingTax = 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            withholdingTax = 200833.33 + (taxableIncome - 666667) * 0.35;
        }
        
        return withholdingTax;
    }


}

