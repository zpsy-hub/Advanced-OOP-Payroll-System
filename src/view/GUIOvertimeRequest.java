package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DAO.OvertimeDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import model.Overtime;
import model.OvertimeType;
import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;
import util.LeaveRequestComboPopulator;
import util.SessionManager;
import util.SignOutButton;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import java.text.ParseException;

public class GUIOvertimeRequest extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User loggedInEmployee;
    private JFormattedTextField startTimetextField;
    private JFormattedTextField endTimetextField;
    private JTextField reasontextField;
    private JTable overtimehistorytable;
    private JComboBox<String> LeaveTypecomboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUIOvertimeRequest frame = new GUIOvertimeRequest(loggedInEmployee);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GUIOvertimeRequest(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;

        setTitle("MotorPH Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/ot request.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        contentPane.add(mainPanel);
        mainPanel.setLayout(null);

        // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(this));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);

        JLabel lblOvertimeApplication = new JLabel("Overtime Application");
        lblOvertimeApplication.setFont(new Font("Poppins", Font.PLAIN, 20));
        lblOvertimeApplication.setBounds(385, 131, 280, 33);
        mainPanel.add(lblOvertimeApplication);

        JLabel lblSelectLeaveType = new JLabel("Start Time: ");
        lblSelectLeaveType.setFont(new Font("Poppins", Font.PLAIN, 16));
        lblSelectLeaveType.setBounds(717, 240, 88, 21);
        mainPanel.add(lblSelectLeaveType);

        JLabel lblSelectLeaveType_1 = new JLabel("Date:");
        lblSelectLeaveType_1.setFont(new Font("Poppins", Font.PLAIN, 16));
        lblSelectLeaveType_1.setBounds(428, 189, 82, 21);
        mainPanel.add(lblSelectLeaveType_1);

        JLabel lblMonth = new JLabel("Month:");
        lblMonth.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblMonth.setBounds(516, 189, 53, 21);
        mainPanel.add(lblMonth);

        JComboBox<String> monthComboBox = new JComboBox<>();
        monthComboBox.setMaximumRowCount(13);
        monthComboBox.setFont(new Font("Poppins", Font.PLAIN, 16));
        monthComboBox.setBackground(Color.WHITE);
        monthComboBox.setBounds(579, 183, 200, 32);
        mainPanel.add(monthComboBox);

        JLabel lblDay = new JLabel("Day:");
        lblDay.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblDay.setBounds(803, 189, 53, 21);
        mainPanel.add(lblDay);

        JComboBox<String> dayComboBox = new JComboBox<>();
        dayComboBox.setMaximumRowCount(32);
        dayComboBox.setFont(new Font("Poppins", Font.PLAIN, 16));
        dayComboBox.setBackground(Color.WHITE);
        dayComboBox.setBounds(846, 183, 88, 32);
        mainPanel.add(dayComboBox);

        JLabel lblYear = new JLabel("Year:");
        lblYear.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblYear.setBounds(993, 189, 44, 21);
        mainPanel.add(lblYear);

        JComboBox<String> yearComboBox = new JComboBox<>();
        yearComboBox.setFont(new Font("Poppins", Font.PLAIN, 16));
        yearComboBox.setBackground(Color.WHITE);
        yearComboBox.setBounds(1035, 183, 119, 32);
        mainPanel.add(yearComboBox);

        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            timeFormatter.setPlaceholderCharacter('_');

            startTimetextField = new JFormattedTextField(timeFormatter);
            startTimetextField.setFont(new Font("Poppins", Font.PLAIN, 14));
            startTimetextField.setBounds(815, 235, 119, 32);
            mainPanel.add(startTimetextField);

            endTimetextField = new JFormattedTextField(timeFormatter);
            endTimetextField.setFont(new Font("Poppins", Font.PLAIN, 14));
            endTimetextField.setBounds(1035, 234, 119, 32);
            mainPanel.add(endTimetextField);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblEndTime = new JLabel("End Time: ");
        lblEndTime.setFont(new Font("Poppins", Font.PLAIN, 16));
        lblEndTime.setBounds(949, 240, 88, 21);
        mainPanel.add(lblEndTime);

        JLabel Reason = new JLabel("Reason:");
        Reason.setFont(new Font("Poppins", Font.PLAIN, 16));
        Reason.setBounds(428, 291, 88, 21);
        mainPanel.add(Reason);

        reasontextField = new JTextField();
        reasontextField.setFont(new Font("Poppins", Font.PLAIN, 14));
        reasontextField.setColumns(10);
        reasontextField.setBounds(526, 285, 628, 32);
        mainPanel.add(reasontextField);

        JButton btnSendRequest = new JButton("Send Request");
        btnSendRequest.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        btnSendRequest.setBackground(Color.WHITE);
        btnSendRequest.setBounds(954, 330, 200, 40);
        mainPanel.add(btnSendRequest);

        JLabel overtimehistoryLabel = new JLabel("Overtime Request History");
        overtimehistoryLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        overtimehistoryLabel.setBounds(385, 397, 335, 33);
        mainPanel.add(overtimehistoryLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(428, 444, 726, 287);
        mainPanel.add(scrollPane);

        overtimehistorytable = new JTable();
        scrollPane.setViewportView(overtimehistorytable);

        // Populate ComboBoxes
        populateComboBoxes(monthComboBox, dayComboBox, yearComboBox);

        JLabel lblSelectLeaveType_1_1 = new JLabel("Leave Type:");
        lblSelectLeaveType_1_1.setFont(new Font("Poppins", Font.PLAIN, 16));
        lblSelectLeaveType_1_1.setBounds(428, 240, 110, 21);
        mainPanel.add(lblSelectLeaveType_1_1);

        LeaveTypecomboBox = new JComboBox<>();
        LeaveTypecomboBox.setBounds(526, 235, 169, 32);
        mainPanel.add(LeaveTypecomboBox);

        // Populate Leave Type ComboBox
        LeaveTypecomboBox.addItem(""); // Add blank entry at the start
        List<OvertimeType> overtimeTypes = OvertimeDAO.getInstance().getOvertimeTypes();
        for (OvertimeType type : overtimeTypes) {
            LeaveTypecomboBox.addItem(type.getOvertimeTypeName());
        }

        // Populate overtime history table
        populateOvertimeHistoryTable();

        // Action Listener for Send Request button
        btnSendRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendOvertimeRequest(monthComboBox, dayComboBox, yearComboBox);
            }
        });
    }

    private void populateComboBoxes(JComboBox<String> monthComboBox, JComboBox<String> dayComboBox, JComboBox<String> yearComboBox) {
        LeaveRequestComboPopulator.populateMonths(monthComboBox);
        LeaveRequestComboPopulator.populateDays(dayComboBox, 1, 12);
        LeaveRequestComboPopulator.populateCurrentYear(yearComboBox);
    }

    private void sendOvertimeRequest(JComboBox<String> monthComboBox, JComboBox<String> dayComboBox, JComboBox<String> yearComboBox) {
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        String selectedDay = (String) dayComboBox.getSelectedItem();
        String selectedYear = (String) yearComboBox.getSelectedItem();

        if (selectedMonth == null || selectedDay == null || selectedYear == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dateStr = selectedYear + "-" + selectedMonth + "-" + selectedDay;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-d");

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate weekBefore = currentDate.minusWeeks(1);
        LocalDate weekAfter = currentDate.plusWeeks(1);

        if (date.isBefore(weekBefore) || date.isAfter(weekAfter) || !date.getMonth().equals(currentDate.getMonth())) {
            JOptionPane.showMessageDialog(this, "Date must be within the current month or 1 week before/after the current date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String startTimeStr = startTimetextField.getText();
        String endTimeStr = endTimetextField.getText();

        if (startTimeStr.trim().isEmpty() || endTimeStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Start time and end time cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidTimeFormat(startTimeStr) || !isValidTimeFormat(endTimeStr)) {
            JOptionPane.showMessageDialog(this, "Invalid time format. Please use HH:mm format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        if (startTime.isAfter(endTime)) {
            JOptionPane.showMessageDialog(this, "Start time should be earlier than end time.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String reason = reasontextField.getText().trim();
        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Reason cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String leaveTypeName = (String) LeaveTypecomboBox.getSelectedItem();
        if (leaveTypeName == null || leaveTypeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a leave type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int overtimeTypeId = -1;
        // Find overtime type ID based on selected name
        List<OvertimeType> overtimeTypes = OvertimeDAO.getInstance().getOvertimeTypes();
        for (OvertimeType type : overtimeTypes) {
            if (type.getOvertimeTypeName().equals(leaveTypeName)) {
                overtimeTypeId = type.getOvertimeTypeId();
                break;
            }
        }

        if (overtimeTypeId == -1) {
            JOptionPane.showMessageDialog(this, "Invalid overtime type selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Overtime overtime = new Overtime();
        overtime.setEmpId(loggedInEmployee.getId());
        overtime.setDate(date);
        overtime.setStartTime(startTime);
        overtime.setEndTime(endTime);
        overtime.setReason(reason);
        overtime.setStatus("Pending");
        overtime.setOvertimeTypeId(overtimeTypeId);

        double totalHours = calculateTotalHours(startTime, endTime);
        overtime.setTotalHours(totalHours);

        OvertimeDAO.getInstance().addOvertime(overtime);
        JOptionPane.showMessageDialog(this, "Overtime request sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        populateOvertimeHistoryTable();
    }

    private boolean isValidTimeFormat(String time) {
        return Pattern.matches("^([01]\\d|2[0-3]):([0-5]\\d)$", time);
    }

    private double calculateTotalHours(LocalTime startTime, LocalTime endTime) {
        return (double) (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 3600;
    }

    private void populateOvertimeHistoryTable() {
        List<Overtime> overtimes = OvertimeDAO.getInstance().getOvertimeByEmployeeId(loggedInEmployee.getId());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Date");
        model.addColumn("Start Time");
        model.addColumn("End Time");
        model.addColumn("Leave Type");
        model.addColumn("Reason");
        model.addColumn("Status");

        for (Overtime overtime : overtimes) {
            model.addRow(new Object[]{
                overtime.getDate().toString(),
                overtime.getStartTime().toString(),
                overtime.getEndTime().toString(),
                overtime.getOvertimeTypeName(),
                overtime.getReason(),
                overtime.getStatus()
            });
        }

        overtimehistorytable.setModel(model);
    }
}
