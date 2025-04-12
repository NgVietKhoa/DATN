package mobileworld.tablecutoms.pay;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ButtonCustom extends JButton {

    boolean mouseOver = false;

    public ButtonCustom() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderSize = 5;

        if (isSelected()) {
            g2.setColor(new Color(255, 255, 255)); // Change color for selected state
        } else if (mouseOver) {
            g2.setColor(new Color(255, 255, 255));
        } else {
            g2.setColor(new Color(255, 255, 255));
        }

        g2.fillRoundRect(borderSize, borderSize, getWidth() - 2 * borderSize, getHeight() - 2 * borderSize, 10, 10);
        super.paintComponent(g);
    }
}
