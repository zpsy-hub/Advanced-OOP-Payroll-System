package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import customUI.ImagePanel;
import model.Payslip;
import service.PayrollSalaryCalculationService;
import DAO.PayslipDAO;

public class PayslipDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textfieldEmployeeID;
    private JTextField textfieldEmployeeName;
    private JTextField textfieldPositionDept;
    private JTextField textfieldStartDate;
    private JTextField textfieldEndDate;
    private JTextField txtfieldMonthlyRate;
    private JTextField txtfieldHourlyRate;
    private JTextField txtfieldHoursWorked;
    private JTextField txtfieldGrossIncome;
    private JTextField txtfieldRiceSubsidy;
    private JTextField txtfieldPhoneAllowance;
    private JTextField txtfieldClothingAllowance;
    private JTextField txtfieldTotalBenefits;
    private JTextField txtfieldSSS;
    private JTextField txtfieldPhilhealth;
    private JTextField txtfieldPagIbig;
    private JTextField txtfieldTotalDeductions;
    private JTextField txtfieldSummaryGrossIncome;
    private JTextField txtfieldSummaryBenefits;
    private JTextField txtfieldSummaryDeductions;
    private JTextField txtfieldTakeHomePay;
    private JTextField textFieldwithholdingtax;
    private PayrollSalaryCalculationService service;
    private JTextField PayslipNumbertextField;
    private JTextField OvertimeHourstextField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            PayslipDialog dialog = new PayslipDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(null); // this method displays the screen at the center of the screen.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public PayslipDialog(JFrame parent) {
        super(parent, "Payslip", true);
        setBounds(0, 0, 1000, 666);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("/img/new payslip.png");
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBounds(0, 0, 1000, 666);
        contentPanel.add(mainPanel);
        mainPanel.setLayout(null);

        textfieldEmployeeID = new JTextField();
        textfieldEmployeeID.setBounds(254, 183, 250, 25);
        textfieldEmployeeID.setEditable(false);
        mainPanel.add(textfieldEmployeeID);

        textfieldEmployeeName = new JTextField();
        textfieldEmployeeName.setBounds(254, 210, 250, 25);
        textfieldEmployeeName.setEditable(false);
        mainPanel.add(textfieldEmployeeName);

        textfieldPositionDept = new JTextField();
        textfieldPositionDept.setBounds(693, 210, 250, 25);
        textfieldPositionDept.setEditable(false);
        mainPanel.add(textfieldPositionDept);

        textfieldStartDate = new JTextField();
        textfieldStartDate.setBounds(693, 156, 250, 25);
        textfieldStartDate.setEditable(false);
        mainPanel.add(textfieldStartDate);

        textfieldEndDate = new JTextField();
        textfieldEndDate.setBounds(693, 183, 250, 25);
        textfieldEndDate.setEditable(false);
        mainPanel.add(textfieldEndDate);

        txtfieldMonthlyRate = new JTextField();
        txtfieldMonthlyRate.setBounds(254, 288, 250, 25);
        txtfieldMonthlyRate.setEditable(false);
        mainPanel.add(txtfieldMonthlyRate);

        txtfieldHourlyRate = new JTextField();
        txtfieldHourlyRate.setBounds(254, 313, 250, 25);
        txtfieldHourlyRate.setEditable(false);
        mainPanel.add(txtfieldHourlyRate);

        txtfieldHoursWorked = new JTextField();
        txtfieldHoursWorked.setBounds(254, 337, 250, 25);
        txtfieldHoursWorked.setEditable(false);
        mainPanel.add(txtfieldHoursWorked);

        txtfieldGrossIncome = new JTextField();
        txtfieldGrossIncome.setBounds(254, 397, 250, 25);
        txtfieldGrossIncome.setEditable(false);
        mainPanel.add(txtfieldGrossIncome);

        txtfieldRiceSubsidy = new JTextField();
        txtfieldRiceSubsidy.setBounds(254, 482, 250, 25);
        txtfieldRiceSubsidy.setEditable(false);
        mainPanel.add(txtfieldRiceSubsidy);

        txtfieldPhoneAllowance = new JTextField();
        txtfieldPhoneAllowance.setBounds(254, 517, 250, 25);
        txtfieldPhoneAllowance.setEditable(false);
        mainPanel.add(txtfieldPhoneAllowance);

        txtfieldClothingAllowance = new JTextField();
        txtfieldClothingAllowance.setBounds(254, 552, 250, 25);
        txtfieldClothingAllowance.setEditable(false);
        mainPanel.add(txtfieldClothingAllowance);

        txtfieldTotalBenefits = new JTextField();
        txtfieldTotalBenefits.setBounds(254, 587, 250, 25);
        txtfieldTotalBenefits.setEditable(false);
        mainPanel.add(txtfieldTotalBenefits);

        txtfieldSSS = new JTextField();
        txtfieldSSS.setBounds(693, 288, 250, 25);
        txtfieldSSS.setEditable(false);
        mainPanel.add(txtfieldSSS);

        txtfieldPhilhealth = new JTextField();
        txtfieldPhilhealth.setBounds(693, 313, 250, 25);
        txtfieldPhilhealth.setEditable(false);
        mainPanel.add(txtfieldPhilhealth);

        txtfieldPagIbig = new JTextField();
        txtfieldPagIbig.setBounds(693, 337, 250, 25);
        txtfieldPagIbig.setEditable(false);
        mainPanel.add(txtfieldPagIbig);

        txtfieldTotalDeductions = new JTextField();
        txtfieldTotalDeductions.setBounds(693, 397, 250, 25);
        txtfieldTotalDeductions.setEditable(false);
        mainPanel.add(txtfieldTotalDeductions);

        txtfieldSummaryGrossIncome = new JTextField();
        txtfieldSummaryGrossIncome.setBounds(693, 482, 250, 25);
        txtfieldSummaryGrossIncome.setEditable(false);
        mainPanel.add(txtfieldSummaryGrossIncome);

        txtfieldSummaryBenefits = new JTextField();
        txtfieldSummaryBenefits.setBounds(693, 517, 250, 25);
        txtfieldSummaryBenefits.setEditable(false);
        mainPanel.add(txtfieldSummaryBenefits);

        txtfieldSummaryDeductions = new JTextField();
        txtfieldSummaryDeductions.setBounds(693, 552, 250, 25);
        txtfieldSummaryDeductions.setEditable(false);
        mainPanel.add(txtfieldSummaryDeductions);

        textFieldwithholdingtax = new JTextField();
        textFieldwithholdingtax.setBounds(693, 362, 250, 25);
        textFieldwithholdingtax.setEditable(false);
        mainPanel.add(textFieldwithholdingtax);

        txtfieldTakeHomePay = new JTextField();
        txtfieldTakeHomePay.setBounds(693, 587, 250, 25);
        txtfieldTakeHomePay.setEditable(false);
        mainPanel.add(txtfieldTakeHomePay);

        JButton exportButton = new JButton("Export");
        exportButton.setFont(new Font("Poppins Medium", Font.PLAIN, 16));
        exportButton.setBackground(Color.WHITE);
        exportButton.setBounds(793, 45, 150, 33);
        mainPanel.add(exportButton);
        
        PayslipNumbertextField = new JTextField();
        PayslipNumbertextField.setBounds(254, 156, 250, 25);
        mainPanel.add(PayslipNumbertextField);
        PayslipNumbertextField.setColumns(10);
        
        OvertimeHourstextField = new JTextField();
        OvertimeHourstextField.setColumns(10);
        OvertimeHourstextField.setBounds(254, 362, 250, 25);
        mainPanel.add(OvertimeHourstextField);

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = textfieldEmployeeID.getText();
                Payslip payslip = PayslipDAO.getInstance().getPayslipByEmployeeId(employeeId);
                if (payslip != null) {
                    service.writePayslipDetailsToFile(payslip);
                } else {
                    JOptionPane.showMessageDialog(null, "Payslip details not found for selected employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void populateTextFieldsWithPayslip(Payslip payslip) {
        textfieldEmployeeID.setText(String.valueOf(payslip.getEmployeeId()));
        textfieldEmployeeName.setText(payslip.getEmployeeName());
        textfieldPositionDept.setText(payslip.getEmployeePosition());
        textfieldStartDate.setText(String.valueOf(payslip.getPeriodStartDate()));
        textfieldEndDate.setText(String.valueOf(payslip.getPeriodEndDate()));
        txtfieldMonthlyRate.setText(formatDecimal(payslip.getBasicSalary()));
        txtfieldHourlyRate.setText(formatDecimal(payslip.getHourlyRate()));
        txtfieldHoursWorked.setText(String.valueOf(payslip.getTotalHours()));
        txtfieldGrossIncome.setText(formatDecimal(payslip.getGrossIncome()));
        txtfieldRiceSubsidy.setText(formatDecimal(payslip.getRiceSubsidy()));
        txtfieldPhoneAllowance.setText(formatDecimal(payslip.getPhoneAllowance()));
        txtfieldClothingAllowance.setText(formatDecimal(payslip.getClothingAllowance()));
        txtfieldTotalBenefits.setText(formatDecimal(payslip.getTotalAllowances()));
        txtfieldSSS.setText(formatDecimal(payslip.getSssContribution()));
        txtfieldPhilhealth.setText(formatDecimal(payslip.getPhilhealthContribution()));
        txtfieldPagIbig.setText(formatDecimal(payslip.getPagibigContribution()));
        txtfieldTotalDeductions.setText(formatDecimal(payslip.getTotalDeductions()));
        txtfieldSummaryGrossIncome.setText(formatDecimal(payslip.getGrossIncome()));
        txtfieldSummaryBenefits.setText(formatDecimal(payslip.getTotalAllowances()));
        txtfieldSummaryDeductions.setText(formatDecimal(payslip.getTotalDeductions()));
        textFieldwithholdingtax.setText(formatDecimal(payslip.getWithholdingTax()));
        txtfieldTakeHomePay.setText(formatDecimal(payslip.getNetPay()));
        PayslipNumbertextField.setText(String.valueOf(payslip.getPayslipId()));
        OvertimeHourstextField.setText(String.valueOf(payslip.getOvertimeHours()));
    }


    private String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }
}
