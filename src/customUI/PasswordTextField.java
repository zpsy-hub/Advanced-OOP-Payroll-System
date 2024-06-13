package customUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PasswordTextField extends JPasswordField implements FocusListener {
    private static final char PASSWORD_CHAR = '\u2022';
    private boolean showingPassword;

    public PasswordTextField() {
        super();
        setEchoChar(PASSWORD_CHAR);
        showingPassword = false;
        addFocusListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    togglePasswordVisibility();
                }
            }
        });
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (showingPassword) {
            setEchoChar((char) 0);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (showingPassword) {
            setEchoChar(PASSWORD_CHAR);
        }
    }

    private void togglePasswordVisibility() {
        showingPassword = !showingPassword;
        if (showingPassword) {
            setEchoChar((char) 0);
        } else {
            setEchoChar(PASSWORD_CHAR);
        }
    }
}
