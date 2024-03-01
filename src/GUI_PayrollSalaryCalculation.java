import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;

import view.GUI_PayrollSalaryCalculation;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.border.LineBorder;


public class GUI_PayrollSalaryCalculation {

	private JFrame payrollsalarycalc;
	private JTable salarycalculationTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation();
					window.payrollsalarycalc.setVisible(true);
					window.payrollsalarycalc.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_PayrollSalaryCalculation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		payrollsalarycalc = new JFrame();
		payrollsalarycalc.setTitle("MotorPH Payroll System");
		payrollsalarycalc.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\GitHub\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		payrollsalarycalc.setBounds(100, 100, 1315, 770);
		payrollsalarycalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payrollsalarycalc.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1301, 733);
		payrollsalarycalc.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(new Color(255, 255, 255));
		sidePanel.setBounds(0, 0, 299, 733);
		mainPanel.add(sidePanel);
		sidePanel.setLayout(null);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidePanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidePanel.add(payslipButton);
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidePanel.add(leaverequestButton);
		
		JLabel payrollaccessLabel = new JLabel("Payroll Access");
		payrollaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payrollaccessLabel.setBounds(139, 354, 138, 33);
		sidePanel.add(payrollaccessLabel);
		
		JPanel separator = new JPanel();
		separator.setBackground(new Color(30, 55, 101));
		separator.setBounds(37, 372, 88, 3);
		sidePanel.add(separator);
		
		JButton salarycalculationButton = new JButton("Salary Calculation");
		salarycalculationButton.setEnabled(false);
		salarycalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		salarycalculationButton.setBackground(Color.WHITE);
		salarycalculationButton.setBounds(37, 411, 227, 31);
		sidePanel.add(salarycalculationButton);
		
		JButton monthlyreportButton = new JButton("Monthly Summary Report");
		monthlyreportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthlyreportButton.setBackground(Color.WHITE);
		monthlyreportButton.setBounds(37, 473, 227, 31);
		sidePanel.add(monthlyreportButton);
		
		JLabel salarycalculationLabel = new JLabel("Salary Calculation");
		salarycalculationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		salarycalculationLabel.setBounds(340, 36, 267, 33);
		mainPanel.add(salarycalculationLabel);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				payrollsalarycalc.dispose();
			}
		});
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		JLabel lblPayPeriodMonth = new JLabel("Pay Period for the Month:");
		lblPayPeriodMonth.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lblPayPeriodMonth.setBounds(340, 79, 215, 33);
		mainPanel.add(lblPayPeriodMonth);
		
		JComboBox monthComboBox = new JComboBox();
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
		monthComboBox.setMaximumRowCount(13);
		monthComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		monthComboBox.setBounds(554, 79, 139, 30);
		mainPanel.add(monthComboBox);
		
		JLabel lbl2024 = new JLabel("2024");
		lbl2024.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		lbl2024.setBounds(703, 79, 57, 33);
		mainPanel.add(lbl2024);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(340, 122, 648, 531);
		mainPanel.add(tablePanel);
		tablePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		salarycalculationTable = new JTable();
		salarycalculationTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablePanel.add(salarycalculationTable);
		
		JButton viewButton = new JButton("View");
		viewButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		viewButton.setBackground(Color.WHITE);
		viewButton.setBounds(340, 678, 154, 33);
		mainPanel.add(viewButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		editButton.setBackground(Color.WHITE);
		editButton.setBounds(526, 678, 154, 33);
		mainPanel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		deleteButton.setBackground(Color.WHITE);
		deleteButton.setBounds(709, 678, 154, 33);
		mainPanel.add(deleteButton);
		
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		calculateButton.setBackground(Color.WHITE);
		calculateButton.setBounds(857, 79, 131, 33);
		mainPanel.add(calculateButton);
	}

	public void openWindow() {
		payrollsalarycalc.setVisible(true);
		
	}
}
