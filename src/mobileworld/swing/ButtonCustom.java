package mobileworld.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ButtonCustom extends JButton {

    Color color1 = Color.decode("#0C2D57");
    Color color2 = Color.decode("#1B1A55");

    int weight1 = 2;
    int weight2 = 1;

    int combinedRed = (color1.getRed() * weight1 + color2.getRed() * weight2) / (weight1 + weight2);
    int combinedGreen = (color1.getGreen() * weight1 + color2.getGreen() * weight2) / (weight1 + weight2);
    int combinedBlue = (color1.getBlue() * weight1 + color2.getBlue() * weight2) / (weight1 + weight2);

    Color combinedColor = new Color(combinedRed, combinedGreen, combinedBlue);
    
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
        
        if (mouseOver) {
            g2.setColor(combinedColor);
        } else {
            g2.setColor(color1);
        }
        
        g2.fillRoundRect(borderSize, borderSize, getWidth() - 2 * borderSize, getHeight() - 2 * borderSize, 10, 10);
        super.paintComponent(g);
    }
}
