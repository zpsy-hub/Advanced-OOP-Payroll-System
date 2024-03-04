package service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class SalaryCalculator {
    private TimesheetDAO timesheetDAO;
    private EmployeeDAO employeeDAO;

    public SalaryCalculator() {
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
        for (String[] record : timesheetRecords) {
            String timeIn = record[1];
            String timeOut = record[2];

            // Calculate hours worked for each day and add to total hours
            int hoursWorked = calculateHoursWorked(timeIn, timeOut);
            totalHours += hoursWorked;
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
}
