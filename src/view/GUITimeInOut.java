package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import DAO.TimesheetDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import model.User;
import util.SignOutButton;

public class GUITimeInOut {

    private JFrame timeinoutScreen;
    private JTable table;
    private static User loggedInEmployee;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUITimeInOut window = new GUITimeInOut(loggedInEmployee);                   
                    window.timeinoutScreen.setLocationRelativeTo(null);
                    window.timeinoutScreen.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GUITimeInOut(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        initialize();
        populateTable();
    }

    private void initialize() {
        timeinoutScreen = new JFrame("MotorPH Payroll System");
        timeinoutScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
        timeinoutScreen.setBounds(100, 100, 1280, 800);
        timeinoutScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timeinoutScreen.getContentPane().setLayout(null);

        ImagePanel mainPanel = new ImagePanel("/img/time in.png");
        mainPanel.setBounds(0, 0, 1280, 800);
        timeinoutScreen.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(394, 360, 814, 370);
        mainPanel.add(scrollPane);

        table = new JTable();
        table.setFont(new Font("Poppins", Font.PLAIN, 14));
        scrollPane.setViewportView(table);

        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(timeinoutScreen));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);

        JPanel timeinoutPanel = new JPanel();
        timeinoutPanel.setOpaque(false);
        timeinoutPanel.setBounds(372, 127, 854, 164);
        mainPanel.add(timeinoutPanel);
        timeinoutPanel.setLayout(null);

        JButton timeInButton = new JButton("TIME IN");
        timeInButton.setFont(new Font("Montserrat ExtraBold", Font.PLAIN, 30));
        timeInButton.setBounds(29, 29, 239, 102);
        timeinoutPanel.add(timeInButton);

        JButton timeOutButton = new JButton("TIME OUT");
        timeOutButton.setFont(new Font("Montserrat ExtraBold", Font.PLAIN, 30));
        timeOutButton.setBounds(576, 29, 239, 102);
        timeinoutPanel.add(timeOutButton);

        JLabel empStatus = new JLabel("OFF");
        empStatus.setFont(new Font("Poppins ExtraBold", Font.PLAIN, 30));
        empStatus.setHorizontalAlignment(SwingConstants.CENTER);
        empStatus.setBounds(260, 81, 339, 39);
        timeinoutPanel.add(empStatus);

        timeInButton.addActionListener(e -> handleTimeIn(timeInButton, timeOutButton, empStatus));
        timeOutButton.addActionListener(e -> handleTimeOut(timeOutButton, empStatus));

        JLabel currentstatusLabel = new JLabel("Current Status:");
        currentstatusLabel.setFont(new Font("Poppins", Font.PLAIN, 21));
        currentstatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentstatusLabel.setBounds(260, 32, 339, 39);
        timeinoutPanel.add(currentstatusLabel);

        JLabel employeeNameLabel = new JLabel();
        employeeNameLabel.setBounds(706, 28, 400, 33);
        employeeNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        employeeNameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(employeeNameLabel);

        // Set employee name dynamically
        if (loggedInEmployee != null) {
            employeeNameLabel.setText(loggedInEmployee.getFirstName() + " " + loggedInEmployee.getLastName());
        }

    }

    private void populateTable() {
        List<String[]> records = TimesheetDAO.getInstance().getLoggedInEmployeeTimesheetRecords(loggedInEmployee.getId());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Date", "Time In", "Time Out", "Total Hours"}, 0);
        for (String[] record : records) {
            model.insertRow(0, new Object[]{record[0], record[1], record[2], record[3]}); // Ensures latest record at top
        }
        table.setModel(model);
    }

    private void handleTimeIn(JButton timeInButton, JButton timeOutButton, JLabel empStatus) {
        int empId = loggedInEmployee.getId();
        System.out.println("Time In - Emp ID: " + empId);
        
        if (!TimesheetDAO.getInstance().hasTimeInRecordForToday(empId)) {
            TimesheetDAO.getInstance().recordTimeIn(empId, LocalDate.now(), new Time(System.currentTimeMillis()));
            empStatus.setText("IN");
            empStatus.setForeground(Color.GREEN);
            timeInButton.setEnabled(false);
            timeOutButton.setEnabled(true);
            populateTable();
        } else {
            JOptionPane.showMessageDialog(timeinoutScreen, "Time in has already been recorded for today.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleTimeOut(JButton timeOutButton, JLabel empStatus) {
        int empId = loggedInEmployee.getId();
        System.out.println("Time Out - Emp ID: " + empId);

        if (!TimesheetDAO.getInstance().hasTimeOutRecordForToday(empId)) {
            TimesheetDAO.getInstance().recordTimeOut(empId);
            empStatus.setText("OUT");
            empStatus.setForeground(Color.RED);
            timeOutButton.setEnabled(false);
            populateTable();
        } else {
            JOptionPane.showMessageDialog(timeinoutScreen, "Time out has already been recorded for today.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openWindow() {
        timeinoutScreen.setVisible(true);
    }
    
    public JFrame getFrame() {
        return timeinoutScreen;
    }

    
}
