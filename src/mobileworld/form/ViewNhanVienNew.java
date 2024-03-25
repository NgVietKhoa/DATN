package mobileworld.form;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mobileworld.entity.NhanVienEntity;
import mobileworld.model.NhanVien;
import mobileworld.service.NhanVienService.NhanVienService;

public class ViewNhanVienNew extends javax.swing.JPanel {

    private List<NhanVien> lists = new ArrayList<>();
    private DefaultTableModel dtm = new DefaultTableModel();
    private NhanVienService sv = new NhanVienService();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DefaultComboBoxModel cbb = new DefaultComboBoxModel();

    public ViewNhanVienNew() {
        initComponents();
        setOpaque(false);
        lists = sv.getAll();
        dtm = (DefaultTableModel) tblbang11.getModel();
         cbb = (DefaultComboBoxModel) cbbchucvu.getModel();
        ShowDataTable(lists);
        showCbb();
    }

    public void ShowDataTable(List<NhanVien> lists) {
        dtm.setRowCount(0);
        for (NhanVien list : lists) {
            dtm.addRow(new Object[]{
                list.getId(),
                list.getTenNhanVien(),
                list.getNgaySinh(),
                list.getDiaChi(),
                list.getSdt(),
                list.getEmail(),
                list.getIdChucVu(),
                list.getPassword()
            });
        }
    }
     public void showCbb(){
        cbb.removeAllElements();
        for (NhanVien nv : lists) {
            cbb.addElement(nv.getIdChucVu());
        }
    }

    public NhanVien getFormData() {
        String tennv = txthovaten.getText();
        String manv = txtma.getText();        
        Date hienTai = dcngaysinh.getDate();
        SimpleDateFormat spx = new SimpleDateFormat("yyyy-MM-dd");
        String diachi = txtdiachi.getText();
        String sdt = txtsdt.getText();
        String email = txtemail.getText();
        String cccd = txtcccd.getText();
        String idchucvu = (String) cbbchucvu.getSelectedItem();
        String pass = txtPass.getText();
        boolean gioitinh = rbtnam.isSelected();

        NhanVien nv = new NhanVien(tennv, hienTai, diachi, sdt, email, idchucvu, pass, 1, hienTai, "NV00001", hienTai, "09123123123");
        return nv;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtsdt = new mobileworld.swing.TextField();
        jPanel4 = new javax.swing.JPanel();
        rbtnam = new javax.swing.JRadioButton();
        rbtnu = new javax.swing.JRadioButton();
        txtma = new mobileworld.swing.TextField();
        cbbchucvu = new mobileworld.swing.Combobox();
        txthovaten = new mobileworld.swing.TextField();
        txtdiachi = new mobileworld.swing.TextField();
        txtemail = new mobileworld.swing.TextField();
        txtcccd = new mobileworld.swing.TextField();
        jPanel5 = new javax.swing.JPanel();
        dcngaysinh = new com.toedter.calendar.JDateChooser();
        btnadd = new mobileworld.swing.ButtonCustom();
        btnsua = new mobileworld.swing.ButtonCustom();
        btnxoa = new mobileworld.swing.ButtonCustom();
        buttonCustom5 = new mobileworld.swing.ButtonCustom();
        txtPass = new mobileworld.swing.TextField();
        jPanel2 = new javax.swing.JPanel();
        txttimkiem = new mobileworld.swing.TextField();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbang11 = new mobileworld.swing.Table();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Nhân Viên");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết Lập Thông Tin Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.setOpaque(false);

        txtsdt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtsdt.setLabelText("Số Điện Thoại");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Giới Tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N
        jPanel4.setOpaque(false);

        buttonGroup1.add(rbtnam);
        rbtnam.setText("Nam");

        buttonGroup1.add(rbtnu);
        rbtnu.setText("Nữ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(rbtnam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(rbtnu)
                .addGap(0, 56, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnam)
                    .addComponent(rbtnu)))
        );

        txtma.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtma.setLabelText("Mã");

        cbbchucvu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbbchucvu.setLabeText("Chức Vụ");

        txthovaten.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txthovaten.setLabelText("Họ Và Tên");

        txtdiachi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtdiachi.setLabelText("Địa Chỉ");

        txtemail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtemail.setLabelText("Email");

        txtcccd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtcccd.setLabelText("CCCD");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Ngày Sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N
        jPanel5.setOpaque(false);

        dcngaysinh.setDateFormatString("yyyy-MM-dd");
        dcngaysinh.setOpaque(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(dcngaysinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(dcngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnadd.setForeground(new java.awt.Color(255, 255, 255));
        btnadd.setText("Thêm");
        btnadd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btnsua.setForeground(new java.awt.Color(255, 255, 255));
        btnsua.setText("Sửa");
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        buttonCustom5.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-fingerprint-scan-36.png"))); // NOI18N
        buttonCustom5.setText("Quét QR CCCD");
        buttonCustom5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtPass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPass.setLabelText("Password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonCustom5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txthovaten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtsdt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtdiachi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtcccd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbchucvu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtcccd, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cbbchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel2.setOpaque(false);

        txttimkiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txttimkiem.setLabelText("Tìm Kiếm");
        txttimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimkiemActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Trạng Thái", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel3.setOpaque(false);

        jRadioButton1.setText("Tất Cả");

        jRadioButton2.setText("Đang Làm");

        jRadioButton3.setText("Đã Nghỉ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)))
        );

        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-excel-30.png"))); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        tblbang11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Tên NV", "Ngày Sinh", "Địa Chỉ", "SĐT", "Email", "ID Chức Vụ", "PassWord"
            }
        ));
        tblbang11.setOpaque(false);
        tblbang11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbang11MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblbang11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1130, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblbang11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbang11MouseClicked
        int row = tblbang11.getSelectedRow();
        NhanVien nv = lists.get(row);
        txthovaten.setText(nv.getTenNhanVien());
        txtma.setText(nv.getId());
        txtsdt.setText(nv.getSdt());
        txtdiachi.setText(nv.getDiaChi());
        txtemail.setText(nv.getEmail());
        txtcccd.setText(nv.getUpdateBy());
        cbbchucvu.setSelectedItem(nv.getIdChucVu());
        boolean gioitinh = false;
        if (gioitinh) {
            rbtnam.setSelected(true);
        } else {
            rbtnu.setSelected(true);
        }
        dcngaysinh.setDate(nv.getNgaySinh());
        txtPass.setText(nv.getPassword());
    }//GEN-LAST:event_tblbang11MouseClicked

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        if (txthovaten.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống tên nhân viên");
            return;
        }
        if (txtma.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống mã nhân viên");
            return;
        }
        if (txtsdt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống sđt nhân viên");
            return;
        }
        if (txtdiachi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống địa chỉ nhân viên");
            return;
        }
        if (txtemail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống email nhân viên");
            return;
        }
        if (txtcccd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống cccd nhân viên");
            return;
        }
        sv.add(getFormData());
        lists = sv.getAll();
        ShowDataTable(lists);
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        // TODO add your handling code here:
        int xoa = tblbang11.getSelectedRow();
        NhanVien nv = lists.get(xoa);
        sv.delete(nv.getId());
        lists = sv.getAll();
        ShowDataTable(lists);
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        // TODO add your handling code here:
        int sua = tblbang11.getSelectedRow();
        NhanVien nv = lists.get(sua);
        sv.update(getFormData(), nv.getId());
        lists = sv.getAll();
        ShowDataTable(lists);
    }//GEN-LAST:event_btnsuaActionPerformed

    private void txttimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimkiemActionPerformed
        // TODO add your handling code here:
        ShowDataTable(sv.search(txttimkiem.getText()));
    }//GEN-LAST:event_txttimkiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnadd;
    private mobileworld.swing.ButtonCustom btnsua;
    private mobileworld.swing.ButtonCustom btnxoa;
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom5;
    private javax.swing.ButtonGroup buttonGroup1;
    private mobileworld.swing.Combobox cbbchucvu;
    private com.toedter.calendar.JDateChooser dcngaysinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtnam;
    private javax.swing.JRadioButton rbtnu;
    private mobileworld.swing.Table tblbang11;
    private mobileworld.swing.TextField txtPass;
    private mobileworld.swing.TextField txtcccd;
    private mobileworld.swing.TextField txtdiachi;
    private mobileworld.swing.TextField txtemail;
    private mobileworld.swing.TextField txthovaten;
    private mobileworld.swing.TextField txtma;
    private mobileworld.swing.TextField txtsdt;
    private mobileworld.swing.TextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
