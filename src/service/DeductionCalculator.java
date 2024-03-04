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
        double[] taxRates = {0.20, 0.25, 0.30, 0.32, 0.35};
        double[] incomeBrackets = {20833, 33333, 66667, 166667, 666667};
        double[] addOns = {0.0, 2500.0, 10833.0, 40833.33, 200833.33};

        for (int i = 0; i < incomeBrackets.length; i++) {
            if (taxableIncome > incomeBrackets[i]) {
                withholdingTax += (incomeBrackets[i] - ((i == 0) ? 0 : incomeBrackets[i - 1])) * taxRates[i] + addOns[i];
            } else {
                withholdingTax += (taxableIncome - ((i == 0) ? 0 : incomeBrackets[i - 1])) * taxRates[i];
                break;
            }
        }

        return withholdingTax;
    }


}

