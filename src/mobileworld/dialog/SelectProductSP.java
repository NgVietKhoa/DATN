package mobileworld.dialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import mobileworld.form.ViewBanHang;
import mobileworld.service.BanHangService.BanHangService;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class SelectProductSP extends javax.swing.JFrame {

    private final BanHangService bhService = new BanHangService();
    private DefaultTableModel tblModel = new DefaultTableModel();
    private final ViewBanHang viewBanHang; // Add this field
    private List<ChiTietSanPhamViewModel> listSearch = new ArrayList<>();
    private List<ChiTietSanPhamViewModel> listSpSelect = new ArrayList<>();
    JLabel getIdDsp = new JLabel();
    JLabel getGiaBan = new JLabel();

    public SelectProductSP(String idDsp, BigDecimal giaBan, ViewBanHang viewBanHang) { // Modify constructor parameter
        this.viewBanHang = viewBanHang; // Initialize viewBanHang field
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        tblSelectDsp.setDefaultEditor(Object.class, null);
        setLocationRelativeTo(null);
        setTitle("Chọn Sản Phẩm");
        tblModel = (DefaultTableModel) tblSelectDsp.getModel();
        listSpSelect = bhService.selectIdDSP(idDsp, giaBan);
        showDataTableSP(listSpSelect);
        getIdDsp.setText(idDsp);
        getGiaBan.setText(String.valueOf(giaBan));

        tblSelectDsp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblSelectDsp.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    boolean currentValue = (boolean) tblModel.getValueAt(index, 0);
                    tblModel.setValueAt(!currentValue, index, 0); // Toggle the value
                }
            }
        });
    }

    private void showDataTableSP(List<ChiTietSanPhamViewModel> listSP) {
        tblModel.setRowCount(0);
        int stt = 0;
        for (ChiTietSanPhamViewModel sp : listSP) {
            stt++;
            tblModel.addRow(new Object[]{
                false, stt, sp.getId(), sp.getTenDsp(), sp.getImel(), sp.getIdimel()
            });
        }
    }

    public void updateIMEI(String imei) {
        // Cập nhật logic xử lý khi nhận thông tin IMEI từ QR
        // Ví dụ: Tích chọn sản phẩm tương ứng với IMEI này
        for (int i = 0; i < tblModel.getRowCount(); i++) {
            String rowIMEI = (String) tblModel.getValueAt(i, 4); // Lấy IMEI từ bảng
            if (rowIMEI.equals(imei)) {
                tblModel.setValueAt(true, i, 0); // Tích chọn sản phẩm có IMEI tương ứng
                break; // Sau khi tìm thấy và tích chọn, thoát khỏi vòng lặp
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnXacNhan = new mobileworld.swing.ButtonCustom();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSelectDsp = new mobileworld.swing.Table();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtTimKiem = new mobileworld.swing.TextField();
        btnQuetQR = new mobileworld.swing.ButtonCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác Nhận");
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        tblSelectDsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "STT", "Mã CTSP", "Tên Sản Phẩm", "Imel Sản Phẩm", "Mã Imel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblSelectDsp);
        if (tblSelectDsp.getColumnModel().getColumnCount() > 0) {
            tblSelectDsp.getColumnModel().getColumn(0).setMinWidth(20);
            tblSelectDsp.getColumnModel().getColumn(0).setMaxWidth(40);
            tblSelectDsp.getColumnModel().getColumn(1).setMinWidth(30);
            tblSelectDsp.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(12, 45, 87));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chọn Sản Phẩm");

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(19, 35, 86));
        jCheckBox1.setText("All");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        txtTimKiem.setLabelText("Tìm Kiếm");
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnQuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQuetQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-qr-code-30.png"))); // NOI18N
        btnQuetQR.setText("Quét QR");
        btnQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuetQR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        Map<String, Integer> selectedQuantities = new HashMap<>();
        List<String> selectedImels = new ArrayList<>();
        List<String> selectedCTSPs = new ArrayList<>();
        List<String> selectedImelsDelete = new ArrayList<>();
        for (int i = 0; i < tblSelectDsp.getRowCount(); i++) {
            boolean isSelected = (boolean) tblSelectDsp.getValueAt(i, 0);
            if (isSelected) {
                String imel = (String) tblSelectDsp.getValueAt(i, 4); // Lấy ID IMEI từ cột 5
                String idimel = (String) tblSelectDsp.getValueAt(i, 5); // Lấy ID IMEI từ cột 6 (nếu đúng)
                String idCTSP = (String) tblSelectDsp.getValueAt(i, 2); // Lấy ID CTSP từ cột tương ứng
                selectedImels.add(idimel); // Thêm ID IMEI vào danh sách
                selectedCTSPs.add(idCTSP);
                selectedImelsDelete.add(imel);
                int quantity = selectedQuantities.getOrDefault(imel, 0);
                selectedQuantities.put(imel, quantity + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : selectedQuantities.entrySet()) {
            String imel = entry.getKey();
            int quantity = entry.getValue();
            viewBanHang.updateGioHangWithImel(imel, quantity);
        }
        viewBanHang.getImel(selectedImels);
        viewBanHang.getCTSP(selectedCTSPs);
        viewBanHang.getImelDelete(selectedImelsDelete);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        boolean isChecked = jCheckBox1.isSelected();
        int rowCount = tblModel.getRowCount();
        boolean allSelected = true; // Flag to track if all checkboxes are selected
        for (int i = 0; i < rowCount; i++) {
            tblModel.setValueAt(isChecked, i, 0); // Set all checkboxes to the state of the "All" checkbox
            if (!(boolean) tblModel.getValueAt(i, 0)) { // If any checkbox is not selected
                allSelected = false; // Update the flag
            }
        }
        jCheckBox1.setSelected(allSelected); //
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        ReadQRCodeSelectSP rqr = new ReadQRCodeSelectSP(this);
        rqr.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        String searchText = txtTimKiem.getText();

        if (searchText.isEmpty()) {
            showDataTableSP(listSpSelect);
        } else {
            listSearch = bhService.searchBySelectDsp(searchText, listSpSelect);
            showDataTableSP(listSearch);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnQuetQR;
    private mobileworld.swing.ButtonCustom btnXacNhan;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private mobileworld.swing.Table tblSelectDsp;
    private mobileworld.swing.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
