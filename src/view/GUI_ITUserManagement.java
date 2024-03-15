package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GUI_ITUserManagement {

	private JFrame usermngmntFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_ITUserManagement window = new GUI_ITUserManagement();
					window.usermngmntFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_ITUserManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		usermngmntFrame = new JFrame();
		usermngmntFrame.setBounds(100, 100, 1315, 770);
		usermngmntFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
