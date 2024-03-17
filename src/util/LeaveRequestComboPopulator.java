package util;

import javax.swing.JComboBox;
import java.time.YearMonth;

public class LeaveRequestComboPopulator {

    // Method to populate months in a JComboBox with a blank first option
    public static void populateMonths(JComboBox<String> comboBox) {
        comboBox.removeAllItems(); 
        comboBox.addItem(""); 
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            comboBox.addItem(month);
        }
    }


    // Method to populate days in a JComboBox based on selected month and year
    public static void populateDays(JComboBox<String> comboBox, int selectedMonth, int selectedYear) {
        comboBox.removeAllItems(); 
        comboBox.addItem(" ");
        int maxDays = getMaxDays(selectedMonth, selectedYear);
        for (int day = 1; day <= maxDays; day++) {
            String dayStr = String.valueOf(day);
            comboBox.addItem(dayStr);
        }
    }

    // Method to get the maximum number of days in a month for a given year
    private static int getMaxDays(int month, int year) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    // Method to populate years in a JComboBox with only the current year
    public static void populateCurrentYear(JComboBox<String> comboBox) {
        int currentYear = java.time.Year.now().getValue();
        comboBox.removeAllItems(); 
        comboBox.addItem(""); 
        comboBox.addItem(String.valueOf(currentYear)); 
    }

    
    // Method to populate leave types in a JComboBox from a different source
    public static void populateLeaveTypes(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem(""); 
        String[] leaveTypes = {"Sick Leave", "Vacation Leave", "Emergency Leave"};
        for (String leaveType : leaveTypes) {
            if (!comboBoxContainsItem(comboBox, leaveType)) {
                comboBox.addItem(leaveType);
            }
        }
    }
    
    // Helper method to check if the combo box contains the specified item
    private static boolean comboBoxContainsItem(JComboBox<String> comboBox, String item) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(item)) {
                return true;
            }
        }
        return false;
    }
}
