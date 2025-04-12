package mobileWorld.com.raven.component;

import mobileWorld.com.raven.event.EventItemSelected;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Item extends BreadcrumbItem {

    public EventItemSelected getEvent() {
        return event;
    }

    public void setEvent(EventItemSelected event) {
        this.event = event;
    }

    private EventItemSelected event;
    private boolean mouseOver;

    public Item(String name, Color color, int index) {
        initComponents();
        setIndex(index);
        setOpaque(false);
        lbName1.setText(name);
        lbName1.setForeground(color);
        lbName1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbName1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (mouseOver) {
                    event.selected(Item.this);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbIcon1 = new javax.swing.JLabel();
        lbName1 = new javax.swing.JLabel();

        lbIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/com/raven/icon/split.png"))); // NOI18N

        lbName1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lbName1.setForeground(new java.awt.Color(255, 255, 255));
        lbName1.setText("Name");
        lbName1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 104, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 27, Short.MAX_VALUE)
                    .addComponent(lbName1)
                    .addGap(0, 27, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbIcon1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lbName1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbIcon1;
    private javax.swing.JLabel lbName1;
    // End of variables declaration//GEN-END:variables
}
