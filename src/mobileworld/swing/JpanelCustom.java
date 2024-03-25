package mobileworld.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class JpanelCustom extends javax.swing.JPanel {

    public JpanelCustom() {
        initComponents();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Define gradient colors
        Color color1 = Color.decode("#fc5c7d");
        Color color2 = Color.decode("#6a82fb");

        // Create a gradient paint
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, panelHeight, color2);

        // Set the paint to the Graphics2D object
        g2d.setPaint(gradientPaint);

        // Fill the panel with the gradient color
        g2d.fillRect(0, 0, panelWidth, panelHeight);

        // Dispose of the Graphics2D object
        g2d.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
