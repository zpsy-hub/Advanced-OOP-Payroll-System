import java.awt.EventQueue;

import javax.swing.JFrame;

import view.GUI_PayrollSalaryCalculation;


public class GUI_PayrollSalaryCalculation {

	private JFrame payrollsalarycalc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation();
					window.payrollsalarycalc.setVisible(true);
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
		payrollsalarycalc.setBounds(100, 100, 1315, 770);
		payrollsalarycalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void openWindow() {
		payrollsalarycalc.setVisible(true);
		
	}

}
