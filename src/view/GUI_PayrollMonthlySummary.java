package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import model.User;
import util.SessionManager;

import javax.swing.JButton;

public class GUI_PayrollMonthlySummary {

	JFrame payrollmontlysummary;
	 private static User loggedInEmployee;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		payrollmontlysummary = new JFrame();
		payrollmontlysummary.setBounds(100, 100, 1315, 770);
		payrollmontlysummary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		payrollmontlysummary.getContentPane().setLayout(null);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(null);
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setBounds(0, 0, 299, 733);
		payrollmontlysummary.getContentPane().add(sidePanel);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidePanel.add(motorphLabel);
		
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBackground(Color.WHITE);
		dashboardButton.setBounds(37, 95, 227, 31);
		sidePanel.add(dashboardButton);
		dashboardButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIDashboard window = new GUIDashboard(loggedInEmployee);
                window.dashboardScreen.setVisible(true);
                payrollmontlysummary.dispose();
		        }
		});
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 155, 227, 31);
		sidePanel.add(timeInOutButton);
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();
		        payrollmontlysummary.dispose();
		        }
		});
		
		JButton payslipButton = new JButton("Payslip");
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidePanel.add(payslipButton);
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
		    	payslip.openWindow();
		    	payrollmontlysummary.dispose();    		        	    
		    }
		});		
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidePanel.add(leaverequestButton);
		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUIPayslip window = new GUIPayslip(loggedInEmployee);
				window.payslipScreen.setVisible(true);
				payrollmontlysummary.dispose(); 
		    }
		});	
		
		JButton salarycalculationButton = new JButton("Salary Calculation");
		salarycalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		salarycalculationButton.setBackground(Color.WHITE);
		salarycalculationButton.setBounds(37, 411, 227, 31);
		sidePanel.add(salarycalculationButton);
		salarycalculationButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation(loggedInEmployee);
		    	window.payrollsalarycalc.setVisible(true);
		    	payrollmontlysummary.dispose(); 
		    }
		});
		
		JButton monthlyreportButton = new JButton("Monthly Summary Report");
		monthlyreportButton.setEnabled(false);
		monthlyreportButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		monthlyreportButton.setBackground(Color.WHITE);
		monthlyreportButton.setBounds(37, 473, 227, 31);
		sidePanel.add(monthlyreportButton);
		
		JLabel payrollaccessLabel = new JLabel("Payroll Access");
		payrollaccessLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		payrollaccessLabel.setBounds(139, 354, 138, 33);
		sidePanel.add(payrollaccessLabel);
		
		JPanel separator = new JPanel();
		separator.setBackground(new Color(30, 55, 101));
		separator.setBounds(37, 372, 88, 3);
		sidePanel.add(separator);
		
		
	}

	public void openWindow() {
		payrollmontlysummary.setVisible(true);
	}
}

