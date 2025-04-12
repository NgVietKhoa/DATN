package mobileworld.tablecutoms.delete;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAction extends javax.swing.JPanel {

    public PanelAction() {
        initComponents();
    }

    public void initEvent(TableActionEvent event, int row) {
        cmdDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.onDelete(row);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdDelete = new mobileworld.tablecutoms.delete.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(1, 0));

        cmdDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/tablecutoms/delete/delete.png"))); // NOI18N
        add(cmdDelete);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.tablecutoms.delete.ButtonCustom cmdDelete;
    // End of variables declaration//GEN-END:variables
}
