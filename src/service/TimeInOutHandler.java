package service;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JOptionPane;

import util.AttendanceData;

public class TimeInOutHandler {
    private AttendanceData attendanceData;

    public TimeInOutHandler(AttendanceData attendanceData) {
        this.attendanceData = attendanceData;
    }

    public void logTimeIn(String id, String lastName, String firstName) {
        // Get current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        try {
            if (attendanceData.hasLoggedTimeInToday(id)) {
                throw new Exception("You have already logged in for the day.");
            }
            // Call the method in AttendanceData to log time in
            attendanceData.logTimeIn(id, lastName, firstName, currentDate, currentTime);
            JOptionPane.showMessageDialog(null, "You have successfully logged in.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logTimeOut(String id) {
        // Get current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        try {
            if (!attendanceData.hasLoggedTimeInToday(id)) {
                throw new Exception("You cannot log out without logging in first.");
            }
            if (attendanceData.hasLoggedTimeOutToday(id)) {
                throw new Exception("You have already logged out for the day.");
            }
            // Call the method in AttendanceData to log time out
            attendanceData.logTimeOut(id, currentDate, currentTime);
            JOptionPane.showMessageDialog(null, "You have successfully logged out.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
