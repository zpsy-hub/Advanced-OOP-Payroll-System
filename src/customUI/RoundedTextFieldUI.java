package customUI;

import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class RoundedTextFieldUI extends BasicTextFieldUI {
    private int borderRadius;

    public RoundedTextFieldUI(int borderRadius) {
        this.borderRadius = borderRadius;
    }
    
    public RoundedTextFieldUI() {
        super();
    }
    
    @Override
    protected void paintSafely(Graphics g) {
        JTextComponent component = getComponent();
        if (component.isEnabled()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(component.getBackground());
            g2.fillRoundRect(0, 0, component.getWidth() - 1, component.getHeight() - 1, borderRadius, borderRadius);
            g2.setColor(component.getForeground());
            super.paintSafely(g2);
            g2.dispose();
        } else {
            super.paintSafely(g);
        }
    }
}
