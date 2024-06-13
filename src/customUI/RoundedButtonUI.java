package customUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundedButtonUI extends BasicButtonUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        int width = b.getWidth();
        int height = b.getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (model.isArmed()) {
            g2.setColor(b.getBackground().darker());
        } else {
            g2.setColor(b.getBackground());
        }

        int borderRadius = Math.min(width, height); // Make the border radius equal to the smaller dimension
        g2.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);
        super.paint(g2, c);
        g2.dispose();
    }


    @Override
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        FontMetrics fm = g.getFontMetrics();

        // Get the current font and color
        Font font = c.getFont();
        Color color = c.getForeground();

        // If the button is pressed, adjust the text position
        if (model.isArmed()) {
            g.setColor(color.darker());
        } else {
            g.setColor(color);
        }

        // Draw the text centered within the text rectangle
        int textX = (textRect.width - fm.stringWidth(text)) / 2;
        int textY = (textRect.height - fm.getHeight()) / 2 + fm.getAscent();
        g.setFont(font);
        g.drawString(text, textRect.x + textX, textRect.y + textY);
    }
}
