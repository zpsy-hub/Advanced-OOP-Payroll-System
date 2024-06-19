package util;

import view.GUIlogin;

import javax.swing.*;

import customUI.HoverButtonUI;

import java.awt.*;
import java.awt.event.ActionListener;

public class SignOutButton extends JButton {

    public SignOutButton(ActionListener actionListener) {
        super("Sign Out");
        setBackground(new Color(0, 74, 173)); // Set background color to #004AAD
        setForeground(Color.WHITE); // Set text color to white
        setUI(new HoverButtonUI(new Color(0, 74, 173), new Color(0, 74, 173).brighter(), new Color(0, 74, 173).darker(), 20)); // Apply rounded button UI
        setPreferredSize(new Dimension(111, 40)); // Set preferred size

        // Set custom font
        setFont(new Font("Poppins", Font.PLAIN, 14)); // Adjust the size (14) as per your preference

        addActionListener(actionListener);
    }

    public static ActionListener getSignOutActionListener(Component parent) {
        return e -> {
            int choice = JOptionPane.showConfirmDialog(parent, "Are you sure you want to sign out?", "Sign Out Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                GUIlogin login = new GUIlogin();
                login.loginScreen1.setVisible(true);
                Window window = SwingUtilities.getWindowAncestor(parent);
                if (window != null) {
                    window.dispose();
                }
            }
        };
    }
}
