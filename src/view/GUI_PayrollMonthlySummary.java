package view;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class GUI_PayrollMonthlySummary {

	private JFrame payrollmontlysummary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_PayrollMonthlySummary window = new GUI_PayrollMonthlySummary();
					window.payrollmontlysummary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_PayrollMonthlySummary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		payrollmontlysummary = new JFrame();
		payrollmontlysummary.setBounds(100, 100, 1315, 770);
		payrollmontlysummary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void openWindow() {
		payrollmontlysummary.setVisible(true);
	}

}

