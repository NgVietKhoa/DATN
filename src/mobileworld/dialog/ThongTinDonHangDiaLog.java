package mobileworld.dialog;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import mobileworld.event.DataChangeListener;
import mobileworld.service.BanHangService.BanHangService;
import mobileworld.service.HoaDonService.LichSuHDService;
import mobileworld.viewModel.LichSuHDModel;

public final class ThongTinDonHangDiaLog extends javax.swing.JFrame {

    LichSuHDService srLSHD = new LichSuHDService();
    BanHangService bhService = new BanHangService();
    JLabel getIdHD = new JLabel();

    public ThongTinDonHangDiaLog(String idHD) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Thông Tin Đơn Hàng");
        pack();
        lichSuHoa(srLSHD.getAll(idHD));
        getIdHD.setText(idHD);
    }

    public void lichSuHoa(List<LichSuHDModel> listLSHD) {
        for (LichSuHDModel lichSuHDModel : listLSHD) {
            breadcrumb1.addItem(lichSuHDModel.getHanhDong());
        }
    }
    
    public DataChangeListener changeListener = () -> {
    };

    public void setDataChangeListener(DataChangeListener listener) {
        this.changeListener = listener;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new mobileworld.swing.Table();
        jPanel2 = new javax.swing.JPanel();
        buttonCustom1 = new mobileworld.tablecutoms.pay.ButtonCustom();
        buttonCustom2 = new mobileworld.tablecutoms.pay.ButtonCustom();
        buttonCustom3 = new mobileworld.tablecutoms.pay.ButtonCustom();
        jScrollPane2 = new javax.swing.JScrollPane();
        breadcrumb1 = new mobileworld.com.raven.component.Breadcrumb();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(12, 45, 87));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông Tin Đơn Hàng");

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table1);

        buttonCustom1.setForeground(new java.awt.Color(102, 102, 102));
        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/com/raven/icon/icons8-cancel-20.png"))); // NOI18N
        buttonCustom1.setText("Hủy Đơn Hàng");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonCustom2.setForeground(new java.awt.Color(102, 102, 102));
        buttonCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/com/raven/icon/icons8-paid-parking-20.png"))); // NOI18N
        buttonCustom2.setText("Xác Nhận Thanh Toán");
        buttonCustom2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        buttonCustom3.setForeground(new java.awt.Color(102, 102, 102));
        buttonCustom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/com/raven/icon/icons8-delivery-20.png"))); // NOI18N
        buttonCustom3.setText("Giao Lại Đơn Hàng");
        buttonCustom3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(334, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        breadcrumb1.setForeground(new java.awt.Color(255, 255, 255));
        breadcrumb1.setColor1(new java.awt.Color(0, 0, 70));
        breadcrumb1.setColor2(new java.awt.Color(0, 51, 153));
        breadcrumb1.setColorSelected(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(breadcrumb1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
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

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed
        bhService.ThanhToanGiaoHangHD(getIdHD.getText());
        lichSuHoa(srLSHD.getAll(getIdHD.getText()));
        changeListener.notifyDataChangeListeners();
        JOptionPane.showMessageDialog(this, "Thanh Toán Hóa Đơn Thành Công");
    }//GEN-LAST:event_buttonCustom2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.com.raven.component.Breadcrumb breadcrumb1;
    private mobileworld.tablecutoms.pay.ButtonCustom buttonCustom1;
    private mobileworld.tablecutoms.pay.ButtonCustom buttonCustom2;
    private mobileworld.tablecutoms.pay.ButtonCustom buttonCustom3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private mobileworld.swing.Table table1;
    // End of variables declaration//GEN-END:variables
}
