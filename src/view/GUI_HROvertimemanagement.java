package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import model.Overtime;
import model.Permission;
import model.User;
import service.PermissionService;
import service.SQL_client;
import util.SessionManager;
import util.SignOutButton;
import DAO.OvertimeDAO;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.RowFilter;

public class GUI_HROvertimemanagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User loggedInEmployee;
    private JTextField searchemptextField;
    private JTable overtimelogstable;
    private JTable overtimehistorytable;
    private DefaultTableModel overtimelogstableModel;
    private DefaultTableModel overtimehistorytableModel;
    private OvertimeDAO overtimeDAO = OvertimeDAO.getInstance();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUI_HROvertimemanagement frame = new GUI_HROvertimemanagement(loggedInEmployee);
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
    public GUI_HROvertimemanagement(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/ot mngmnt.png");
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
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(384, 140, 834, 218);
        mainPanel.add(scrollPane);
        
        overtimelogstable = new JTable();
        overtimelogstableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Emp ID", "Name", "Date", "Start Time", "End Time", "Total Hours", "Reason", "Status"
            }
        );
        overtimelogstable.setModel(overtimelogstableModel);
        scrollPane.setViewportView(overtimelogstable);

        JButton approveButton = new JButton("Approve");
        approveButton.setBounds(384, 368, 154, 35);
        approveButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        approveButton.setBackground(Color.WHITE);
        mainPanel.add(approveButton);
        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateOvertimeStatus("Approved");
            }
        });

        JButton rejectButton = new JButton("Reject");
        rejectButton.setBounds(578, 368, 154, 35);
        rejectButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        rejectButton.setBackground(Color.WHITE);
        mainPanel.add(rejectButton);
        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateOvertimeStatus("Rejected");
            }
        });

        JLabel lblOvertimeRequestsHistory = new JLabel("Overtime Requests History");
        lblOvertimeRequestsHistory.setBounds(384, 459, 323, 33);
        lblOvertimeRequestsHistory.setFont(new Font("Poppins", Font.PLAIN, 22));
        mainPanel.add(lblOvertimeRequestsHistory);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(384, 502, 834, 238);
        mainPanel.add(scrollPane_1);
        
        overtimehistorytable = new JTable();
        overtimehistorytableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Emp ID", "Name", "Date", "Start Time", "End Time", "Total Hours", "Reason", "Status", "Date Approved"
            }
        );
        overtimehistorytable.setModel(overtimehistorytableModel);
        scrollPane_1.setViewportView(overtimehistorytable);

        searchemptextField = new JTextField();
        searchemptextField.setBounds(961, 463, 162, 30);
        searchemptextField.setFont(new Font("Poppins", Font.PLAIN, 16));
        searchemptextField.setColumns(10);
        mainPanel.add(searchemptextField);
        
        JLabel lblSearchForAn = new JLabel("Search for an Employee:");
        lblSearchForAn.setBounds(762, 459, 339, 39);
        lblSearchForAn.setHorizontalAlignment(SwingConstants.LEFT);
        lblSearchForAn.setFont(new Font("Poppins", Font.PLAIN, 16));
        mainPanel.add(lblSearchForAn);
        
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(1133, 463, 85, 31);
        searchButton.setFont(new Font("Poppins Medium", Font.PLAIN, 14));
        mainPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchemptextField.getText();
                searchOvertimeHistory(searchTerm);
            }
        });

        loadOvertimeLogs();
        loadOvertimeHistory();
    }

    private void loadOvertimeLogs() {
        List<Overtime> overtimes = overtimeDAO.getOvertimeByStatus("Pending");
        for (Overtime overtime : overtimes) {
            overtimelogstableModel.addRow(new Object[]{
                overtime.getEmpId(),
                overtime.getEmployeeName(),
                overtime.getDate(),
                overtime.getStartTime(),
                overtime.getEndTime(),
                overtime.getTotalHours(),
                overtime.getReason(),
                overtime.getStatus()
            });
        }
    }

    private void loadOvertimeHistory() {
        List<Overtime> overtimes = overtimeDAO.getAllOvertimes();
        for (Overtime overtime : overtimes) {
            if (!"Pending".equals(overtime.getStatus())) {
                overtimehistorytableModel.addRow(new Object[]{
                    overtime.getEmpId(),
                    overtime.getEmployeeName(),
                    overtime.getDate(),
                    overtime.getStartTime(),
                    overtime.getEndTime(),
                    overtime.getTotalHours(),
                    overtime.getReason(),
                    overtime.getStatus(),
                    overtime.getDateApproved()
                });
            }
        }
    }

    private void updateOvertimeStatus(String status) {
        int selectedRow = overtimelogstable.getSelectedRow();
        if (selectedRow >= 0) {
            int empId = (int) overtimelogstableModel.getValueAt(selectedRow, 0);
            Date date = (Date) overtimelogstableModel.getValueAt(selectedRow, 2);
            LocalDate dateApproved = LocalDate.now();

            Overtime overtime = overtimeDAO.getOvertimeByEmpIdAndDate(empId, date.toLocalDate());
            if (overtime != null) {
                overtime.setStatus(status);
                overtime.setDateApproved(dateApproved);
                overtimeDAO.updateOvertime(overtime);

                overtimelogstableModel.removeRow(selectedRow);
                loadOvertimeHistory();
            }
        }
    }

    private void searchOvertimeHistory(String searchTerm) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(overtimehistorytableModel);
        overtimehistorytable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
    }
}
