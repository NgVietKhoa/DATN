package mobileworld.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ButtonCustom1 extends JButton {

    Color color1 = Color.decode("#11998e");
    Color color2 = Color.decode("#93f9b9");

    int weight1 = 2;
    int weight2 = 1;

    int combinedRed = (color1.getRed() * weight1 + color2.getRed() * weight2) / (weight1 + weight2);
    int combinedGreen = (color1.getGreen() * weight1 + color2.getGreen() * weight2) / (weight1 + weight2);
    int combinedBlue = (color1.getBlue() * weight1 + color2.getBlue() * weight2) / (weight1 + weight2);

    Color combinedColor = new Color(combinedRed, combinedGreen, combinedBlue);

    boolean mouseOver = false;

    public ButtonCustom1() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                updateColor();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                updateColor();
                repaint();
            }
        });
    }
    
    private void updateColor() {
        if (mouseOver) {
            setForeground(Color.WHITE); // Màu chữ khi di chuột vào là màu trắng
        } else {
            setForeground(Color.WHITE); // Màu chữ khi không di chuột vào
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderSize = 5;

        if (mouseOver) {
            g2.setColor(color1);
        } else {
            g2.setColor(combinedColor);
        }

        g2.fillRoundRect(borderSize, borderSize, getWidth() - 2 * borderSize, getHeight() - 2 * borderSize, 10, 10);
        super.paintComponent(g);
    }
}
