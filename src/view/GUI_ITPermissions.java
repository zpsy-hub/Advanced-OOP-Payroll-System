package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.User;
import util.SessionManager;

public class GUI_ITPermissions {
	JFrame permissionsFrame;
	private static User loggedInEmployee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User loggedInEmployee = SessionManager.getLoggedInUser();
					GUI_ITPermissions window = new GUI_ITPermissions(loggedInEmployee);
					window.permissionsFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_ITPermissions(User loggedInEmployee) {
		GUI_ITPermissions.loggedInEmployee = loggedInEmployee;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		permissionsFrame = new JFrame();
		permissionsFrame.setBounds(100, 100, 450, 300);
		permissionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
