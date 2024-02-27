package util;

import javax.swing.JComboBox;
import java.io.IOException;
import java.util.List;
import model.Leave;

public class LeaveRequestComboPopulator {

	    // Method to populate months in a JComboBox
	    public static void populateMonths(JComboBox<String> comboBox) {
	        comboBox.addItem(" ");
	        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	        for (String month : months) {
	            if (!comboBoxContainsItem(comboBox, month)) {
	                comboBox.addItem(month);
	            }
	        }
	    }

	    // Method to populate days in a JComboBox
	    public static void populateDays(JComboBox<String> comboBox) {
	        comboBox.addItem(" ");
	        for (int day = 1; day <= 31; day++) {
	            String dayStr = String.valueOf(day);
	            if (!comboBoxContainsItem(comboBox, dayStr)) {
	                comboBox.addItem(dayStr);
	            }
	        }
	    }

	    // Method to populate years in a JComboBox
	    public static void populateYears(JComboBox<String> comboBox) {
	        comboBox.addItem(" ");
	        int currentYear = java.time.Year.now().getValue();
	        for (int year = currentYear; year >= currentYear - 1; year--) {
	            String yearStr = String.valueOf(year);
	            if (!comboBoxContainsItem(comboBox, yearStr)) {
	                comboBox.addItem(yearStr);
	            }
	        }
	    }
	    
	    // Method to populate leave types in a JComboBox from a different source
	    public static void populateLeaveTypes(JComboBox<String> comboBox) {
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
