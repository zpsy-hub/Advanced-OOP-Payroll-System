package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Attendance;

public class AttendanceData {
    private List<Attendance> attendanceRecords;

    public AttendanceData() {
        attendanceRecords = new ArrayList<>();
    }

    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void loadFromCSV(String filePath) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String lastName = parts[1];
                String firstName = parts[2];
                LocalDate date = LocalDate.parse(parts[3], dateFormatter);
                LocalTime timeIn = LocalTime.parse(parts[4], timeFormatter);
                LocalTime timeOut = parts[5].isEmpty() ? null : LocalTime.parse(parts[5], timeFormatter);
                Attendance record = new Attendance(id, lastName, firstName, date, timeIn, timeOut);
                attendanceRecords.add(record);
            }
        }
    }

    public void saveToCSV(String filePath) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Employee #,Last Name,First Name,Date,Time-In,Time-Out\n");
            for (Attendance record : attendanceRecords) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                        record.getid(), record.getLastName(), record.getFirstName(),
                        record.getDate().format(dateFormatter), record.getTimeIn().format(timeFormatter),
                        record.getTimeOut() == null ? "" : record.getTimeOut().format(timeFormatter)));
            }
        }
    }

    public void logTimeIn(String id, String lastName, String firstName, LocalDate date, LocalTime timeIn) {
        Attendance record = new Attendance(id, lastName, firstName, date, timeIn, null);
        attendanceRecords.add(record);
        try {
            saveToCSV("src/data/Attendance Timesheet.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logTimeOut(String id, LocalDate date, LocalTime timeOut) {
        for (Attendance record : attendanceRecords) {
            if (record.getid().equals(id) && record.getDate().equals(date) && record.getTimeOut() == null) {
                record.setTimeOut(timeOut);
                try {
                    saveToCSV("src/data/Attendance Timesheet.csv");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
    // Method to check if an employee has already logged in for the day
    public boolean hasLoggedTimeInToday(String id) {
        LocalDate currentDate = LocalDate.now();
        for (Attendance record : attendanceRecords) {
            if (record.getid().equals(id) && record.getDate().equals(currentDate) && record.getTimeIn() != null) {
                return true;
            }
        }
        return false;
    }
    
    // Method to check if an employee has already logged out for the day
    public boolean hasLoggedTimeOutToday(String id) {
        LocalDate today = LocalDate.now();
        for (Attendance record : attendanceRecords) {
            if (record.getid().equals(id) && record.getDate().equals(today) && record.getTimeOut() != null) {
                return true;
            }
        }
        return false;
        }
    }
