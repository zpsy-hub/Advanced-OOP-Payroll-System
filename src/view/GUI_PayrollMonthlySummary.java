package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import model.MonthlySummaryReport;
import model.Permission;
import model.User;
import util.SessionManager;
import util.SignOutButton;
import DAO.MonthlySummaryReportDAO;
import customUI.ImagePanel;
import customUI.Sidebar;
import customUI.SidebarButton;
import service.PayrollSalaryCalculationService;
import service.PermissionService;
import service.SQL_client;

import com.opencsv.CSVWriter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUI_PayrollMonthlySummary {

    public JFrame payrollmontlysummary;
    private static User loggedInEmployee;
    private PayrollSalaryCalculationService service;
    private JTable MonhlySummarytable;
    private MonthlySummaryReportDAO summaryReportDAO;
    private String selectedMonthYear; // Add this variable to store the selected month-year

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUI_PayrollMonthlySummary window = new GUI_PayrollMonthlySummary(loggedInEmployee);
                    window.payrollmontlysummary.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * @param loggedInEmployee2 
     */
    public GUI_PayrollMonthlySummary(User loggedInEmployee) {
        GUI_PayrollMonthlySummary.loggedInEmployee = loggedInEmployee;
        this.service = new PayrollSalaryCalculationService();
        this.summaryReportDAO = new DAO.MonthlySummaryReportDAO();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        payrollmontlysummary = new JFrame();
        payrollmontlysummary.setTitle("MotorPH Payroll System");
        payrollmontlysummary.setBounds(100, 100, 1280, 800);
        payrollmontlysummary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        payrollmontlysummary.getContentPane().setLayout(null);
        
        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/monthly report.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1280, 800);
        payrollmontlysummary.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
     // Use the Sidebar class
        Sidebar sidebar = new Sidebar(loggedInEmployee);
        sidebar.setBounds(0, 92, 321, 680);
        mainPanel.add(sidebar);

        // Sign Out button initialization
        SignOutButton signOutButton = new SignOutButton(SignOutButton.getSignOutActionListener(payrollmontlysummary));
        signOutButton.setBounds(1125, 24, 111, 40);
        mainPanel.add(signOutButton);
        
        JLabel lblPayPeriod = new JLabel("Pay Period:");
        lblPayPeriod.setFont(new Font("Poppins", Font.PLAIN, 16));
        lblPayPeriod.setBounds(388, 109, 215, 33);
        mainPanel.add(lblPayPeriod);        
        
        JButton exportButton = new JButton("Export");
        exportButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        exportButton.setBackground(Color.WHITE);
        exportButton.setBounds(1063, 109, 154, 33);
        mainPanel.add(exportButton);
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToCSV();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(388, 318, 829, 395);
        mainPanel.add(scrollPane);
        
        MonhlySummarytable = new JTable();
        scrollPane.setViewportView(MonhlySummarytable);
        
        JComboBox<String> monthComboBox = new JComboBox<String>();
        monthComboBox.setMaximumRowCount(13);
        monthComboBox.setFont(new Font("Poppins", Font.PLAIN, 16));
        monthComboBox.setBounds(487, 110, 194, 30);
        mainPanel.add(monthComboBox);
        monthComboBox.addItem("Select Month-Year");
        
        // Populate monthComboBox with month-year combinations
        service.populateMonthComboBox(monthComboBox);
        
        // Action listener for monthComboBox
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMonthYear = (String) monthComboBox.getSelectedItem(); // Store the selected month-year
                if (!selectedMonthYear.equals("Select Month-Year")) {
                    displayMonthlySummary(selectedMonthYear);
                    // Enable export button
                    exportButton.setEnabled(true);
                } else {
                    // Disable export button
                    exportButton.setEnabled(false);
                }
            }
        });
    }

    private void displayMonthlySummary(String selectedMonth) {
        List<MonthlySummaryReport> monthlySummaryReports = MonthlySummaryReportDAO.generateMonthlySummaryReport(selectedMonth);

        if (monthlySummaryReports.isEmpty()) {
            JOptionPane.showMessageDialog(MonhlySummarytable, "No records available for the selected month-year.", "No Records", JOptionPane.INFORMATION_MESSAGE);
            // Clear the table
            DefaultTableModel model = (DefaultTableModel) MonhlySummarytable.getModel();
            model.setRowCount(0); // Clear the table
            return;
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Employee ID");
        model.addColumn("Employee Name");
        model.addColumn("Position");
        model.addColumn("Gross Income (PHP)");
        model.addColumn("SSS Number");
        model.addColumn("SSS Contribution (PHP)");
        model.addColumn("PhilHealth Number");
        model.addColumn("PhilHealth Contribution (PHP)");
        model.addColumn("Pagibig Number");
        model.addColumn("Pagibig Contribution (PHP)");
        model.addColumn("TIN Number");
        model.addColumn("Withholding Tax (PHP)");
        model.addColumn("Net Pay (PHP)");

        double totalGrossIncome = 0;
        double totalSssContribution = 0;
        double totalPhilhealthContribution = 0;
        double totalPagibigContribution = 0;
        double totalWithholdingTax = 0;
        double totalNetPay = 0;

        for (MonthlySummaryReport report : monthlySummaryReports) {
            totalGrossIncome += report.getGrossIncome();
            totalSssContribution += report.getSssContribution();
            totalPhilhealthContribution += report.getPhilhealthContribution();
            totalPagibigContribution += report.getPagibigContribution();
            totalWithholdingTax += report.getWithholdingTax();
            totalNetPay += report.getNetPay();

            model.addRow(new Object[]{
                    report.getEmployeeId(),
                    report.getEmployeeName(),
                    report.getPosition(),
                    formatCurrency(report.getGrossIncome()),
                    report.getSssNumber(),
                    formatCurrency(report.getSssContribution()),
                    report.getPhilhealthNumber(),
                    formatCurrency(report.getPhilhealthContribution()),
                    report.getPagibigNumber(),
                    formatCurrency(report.getPagibigContribution()),
                    report.getTinNumber(),
                    formatCurrency(report.getWithholdingTax()),
                    formatCurrency(report.getNetPay())
            });
        }

        // Add a row for the totals
        model.addRow(new Object[]{
                "Total",
                "",
                "",
                formatCurrency(totalGrossIncome),
                "",
                formatCurrency(totalSssContribution),
                "",
                formatCurrency(totalPhilhealthContribution),
                "",
                formatCurrency(totalPagibigContribution),
                "",
                formatCurrency(totalWithholdingTax),
                formatCurrency(totalNetPay)
        });

        MonhlySummarytable.setModel(model);
    }

    private String formatCurrency(double amount) {
        return "PHP " + String.format("%.2f", amount);
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        int userSelection = fileChooser.showSaveDialog(payrollmontlysummary);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getPath();
                if (!filePath.toLowerCase().endsWith(".csv")) {
                    filePath += ".csv"; // Ensure the file has the .csv extension
                }

                FileWriter fileWriter = new FileWriter(filePath);
                CSVWriter writer = new CSVWriter(fileWriter);

                // Write header information
                writer.writeNext(new String[]{"MotorPH"});
                writer.writeNext(new String[]{"7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City"});
                writer.writeNext(new String[]{"Phone: (028) 911-5071 / (028) 911-5072 / (028) 911-5073"});
                writer.writeNext(new String[]{"Email: corporate@motorph.com"});
                writer.writeNext(new String[]{"Pay period: " + selectedMonthYear}); // Use the selected month-year
                
                // Write an empty line as a separator
                writer.writeNext(new String[]{});
                
                writer.writeNext(new String[]{"MONTHLY PAYROLL SUMMARY REPORT"});

                // Write an empty line as a separator
                writer.writeNext(new String[]{});

                // Write column headers
                String[] headers = {"Employee ID", "Employee Name", "Position", "Gross Income (PHP)",
                                    "SSS Number", "SSS Contribution (PHP)", "PhilHealth Number",
                                    "PhilHealth Contribution (PHP)", "Pagibig Number", "Pagibig Contribution (PHP)",
                                    "TIN Number", "Withholding Tax (PHP)", "Net Pay (PHP)"};
                writer.writeNext(headers);

                // Write table data
                DefaultTableModel model = (DefaultTableModel) MonhlySummarytable.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String[] rowData = new String[model.getColumnCount()];
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        rowData[j] = model.getValueAt(i, j).toString();
                    }
                    writer.writeNext(rowData);
                }

                writer.close();
                JOptionPane.showMessageDialog(payrollmontlysummary, "CSV file has been created successfully at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openWindow() {
        payrollmontlysummary.setVisible(true);
    }
}
