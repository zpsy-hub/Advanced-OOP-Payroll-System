import java.awt.EventQueue;

import javax.swing.JFrame;

public class GUI_HRAttendanceManagement {

	private JFrame hrattendancemngmnt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement();
					window.hrattendancemngmnt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_HRAttendanceManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		hrattendancemngmnt = new JFrame();
		hrattendancemngmnt.setBounds(100, 100, 450, 300);
		hrattendancemngmnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
