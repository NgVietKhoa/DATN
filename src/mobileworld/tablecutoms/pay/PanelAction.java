package mobileworld.tablecutoms.pay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAction extends javax.swing.JPanel {

    public PanelAction() {
        initComponents();
    }

    public void initEvent(TableActionEventPay event, int row) {

        cmdPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.onPay(row);
            }
        });
        
        cmdCancelDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.onCancelDelivery(row);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdPay = new mobileworld.tablecutoms.pay.ButtonCustom();
        cmdCancelDelivery = new mobileworld.tablecutoms.pay.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(1, 0));

        cmdPay.setForeground(new java.awt.Color(102, 102, 102));
        cmdPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/tablecutoms/pay/icons8-paid-parking-20.png"))); // NOI18N
        cmdPay.setText("Thanh Toán");
        cmdPay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add(cmdPay);

        cmdCancelDelivery.setForeground(new java.awt.Color(102, 102, 102));
        cmdCancelDelivery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/tablecutoms/pay/icons8-cancel-20.png"))); // NOI18N
        cmdCancelDelivery.setText("Hủy Giao");
        cmdCancelDelivery.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add(cmdCancelDelivery);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.tablecutoms.pay.ButtonCustom cmdCancelDelivery;
    private mobileworld.tablecutoms.pay.ButtonCustom cmdPay;
    // End of variables declaration//GEN-END:variables
}
