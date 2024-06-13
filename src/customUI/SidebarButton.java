package customUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SidebarButton extends JButton {

    public SidebarButton(String text, String iconPath, ActionListener actionListener) {
        super(text);
        setFont(new Font("Poppins", Font.PLAIN, 17));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setForeground(Color.BLACK); // Set text color to black
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        setHorizontalTextPosition(SwingConstants.RIGHT); // Set text position relative to icon

        if (iconPath != null) {
            setIcon(new ImageIcon(getClass().getResource(iconPath)));
        }

        setUI(new HoverButtonUI(Color.WHITE, Color.decode("#004AAD"), Color.decode("#00308F"), 20)); // Custom UI with hover effects and border radius

        setMinimumSize(new Dimension(0, 40)); // Ensure minimum height, width will adjust automatically
        setPreferredSize(new Dimension(getPreferredSize().width, 40)); // Preferred height

        addActionListener(actionListener);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }
}
