package customUI;

import model.User;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sidebar extends JPanel {
    private List<SidebarButton> buttons;
    private SidebarButton signOutButton;
    private User loggedInEmployee;

    public Sidebar(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 255, 0));
        setOpaque(false);
        setBounds(30, 93, 321, 680);  // Set the bounds as specified
        buttons = new ArrayList<>();
        initializeButtons();
        layoutButtons();
    }

    private void initializeButtons() {
        addSidebarButton("Dashboard", "/img/001-dashboard.png", e -> {
            GUIDashboard window = new GUIDashboard(loggedInEmployee);
            window.dashboardScreen.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Time In/Out", "/img/008-clock.png", e -> {
            GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
            timeInOut.openWindow();
            closeCurrentFrame();
        });

        addSidebarButton("Payslip", "/img/042-document.png", e -> {
            GUIPayslip payslip = new GUIPayslip(loggedInEmployee);
            payslip.openWindow();
            closeCurrentFrame();
        });

        addSidebarButton("Leave Request", "/img/022-leave.png", e -> {
            try {
                GUILeaveRequest window = new GUILeaveRequest(loggedInEmployee);
                window.leaverequestScreen.setVisible(true);
                closeCurrentFrame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        addSidebarButton("Overtime Request", "/img/014-overtime-1.png", e -> {
            // Overtime request logic
        });

        addSidebarButton("Employee Management", "/img/016-attendance.png", e -> {
            try {
                GUI_HREmployeeManagement employeeManagement = new GUI_HREmployeeManagement(loggedInEmployee);
                employeeManagement.setVisible(true);
                closeCurrentFrame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        addSidebarButton("Attendance Management", "/img/009-calendar.png", e -> {
            GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement(loggedInEmployee);
            window.hrattendancemngmnt.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Leave Management", "/img/023-leave-1.png", e -> {
            GUI_HRLeaveManagement window = new GUI_HRLeaveManagement(loggedInEmployee);
            window.hrleavemngmnt.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Salary Calculation", "/img/026-calculator.png", e -> {
            GUI_PayrollSalaryCalculation window = new GUI_PayrollSalaryCalculation(loggedInEmployee);
            window.payrollsalarycalc.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Monthly Summary Reports", "/img/027-report.png", e -> {
            GUI_PayrollMonthlySummary window = new GUI_PayrollMonthlySummary(loggedInEmployee);
            window.payrollmontlysummary.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Permissions Management", "/img/030-secure-shield.png", e -> {
            GUI_ITPermissions window = new GUI_ITPermissions(loggedInEmployee);
            window.permissionsFrame.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Credentials Management", "/img/032-permission.png", e -> {
            GUI_ITCredentialsManagement window = new GUI_ITCredentialsManagement(loggedInEmployee);
            window.usermngmntFrame.setVisible(true);
            closeCurrentFrame();
        });

        addSidebarButton("Authentication Logs", "/img/033-permission-1.png", e -> {
            GUI_ITLogs window = new GUI_ITLogs(loggedInEmployee);
            window.authenticationlogs.setVisible(true);
            closeCurrentFrame();
        });

        // Initialize the Sign Out button
        signOutButton = new SidebarButton("Sign Out", null, e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign out?", "Sign Out Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                GUIlogin login = new GUIlogin();
                login.loginScreen1.setVisible(true);
                closeCurrentFrame();
            }
        });

        // Set custom UI for the sign-out button
        signOutButton.setBackground(new Color(0, 74, 173)); // Set background color to #004AAD
        signOutButton.setForeground(Color.WHITE); // Set text color to white
        signOutButton.setUI(new HoverButtonUI(new Color(0, 74, 173), new Color(0, 74, 173).brighter(), new Color(0, 74, 173).darker(), 20)); // Apply rounded button UI
        signOutButton.setPreferredSize(new Dimension(150, 40)); // Set preferred size
    }

    private void addSidebarButton(String text, String iconPath, ActionListener actionListener) {
        SidebarButton button = new SidebarButton(text, iconPath, actionListener);
        buttons.add(button);
    }

    private void layoutButtons() {
        for (SidebarButton button : buttons) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT); // Align buttons to the left
            add(button);
            add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical spacing between buttons
        }
    }

    private void closeCurrentFrame() {
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public void setButtonVisibility(List<String> visibleButtons) {
        for (SidebarButton button : buttons) {
            button.setVisible(visibleButtons.contains(button.getText()));
        }
        revalidate();
        repaint();
    }

    public void addSignOutButtonToContainer(Container container) {
        container.add(signOutButton);
        signOutButton.setBounds(1107, 35, signOutButton.getPreferredSize().width, signOutButton.getPreferredSize().height);
    }
}
