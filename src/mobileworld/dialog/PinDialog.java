package mobileworld.dialog;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import mobileworld.model.Pin;
import mobileworld.event.DataChangeListener;
import mobileworld.service.ChiTietSanPhamService.PinService;

public class PinDialog extends javax.swing.JFrame {

    public PinService service = new PinService();

    public PinDialog() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Pin");
        pack();
    }

    public DataChangeListener changeListener = () -> {

    };

    public void setDataChangeListener(DataChangeListener listener) {
        this.changeListener = listener;
    }

    private Pin getFormData() {
        String tenPin = txtPin.getText();
        LocalDate dateTime = LocalDate.now();

        Pin pin = new Pin(tenPin, 1, dateTime, "NV0003");
        return pin;
    }

    public boolean check() {
        if (txtPin.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Pin không được trống");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPin = new mobileworld.swing.TextField();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtPin.setLabelText("Dung Lượng Pin");

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        buttonCustom1.setText("Thêm");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        if (check()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn Thêm không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.add(getFormData());
                changeListener.notifyDataChangeListeners();
                JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Thêm Thất Bại!");
        }
    }//GEN-LAST:event_buttonCustom1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private javax.swing.JPanel jPanel1;
    private mobileworld.swing.TextField txtPin;
    // End of variables declaration//GEN-END:variables
}
