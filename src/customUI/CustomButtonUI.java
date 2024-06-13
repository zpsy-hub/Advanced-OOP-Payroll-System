package customUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import java.awt.*;

public class CustomButtonUI extends BasicButtonUI {

    // Override the paint method to customize the button's appearance
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = c.getWidth();
        int height = c.getHeight();
        if (model.isArmed()) {
            g2.setColor(Color.white);
            g2.fillRoundRect(0, 0, width, height, 10, 10);
            g2.setColor(Color.gray);
            g2.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);
        } else {
            g2.setColor(Color.white);
            g2.fillRoundRect(0, 0, width, height, 10, 10);
            g2.setColor(Color.gray);
            g2.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);
        }
        super.paint(g, c);
    }

    // Override the paintText method to customize the button's text appearance
    @Override
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        FontMetrics fm = g.getFontMetrics();
        int mnemonicIndex = b.getDisplayedMnemonicIndex();
        if (model.isEnabled()) {
            g.setColor(b.getForeground());
        } else {
            g.setColor(Color.gray);
        }
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
    }
}
