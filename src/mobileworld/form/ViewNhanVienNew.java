package mobileworld.form;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mobileworld.main.SessionStorage;
import mobileworld.model.ChucVu;
import mobileworld.model.NhanVien;
import mobileworld.service.ChucVService.ChucVuService;
import mobileworld.service.NhanVienService.NhanVienService;
import mobileworld.viewModel.NhanVienViewModel;

public class ViewNhanVienNew extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private final NhanVienService sv = new NhanVienService();
    private final ChucVuService chucVuService = new ChucVuService();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DefaultComboBoxModel cbb = new DefaultComboBoxModel();
    private List<NhanVienViewModel> nvDangLam = new ArrayList<>();
    private List<NhanVienViewModel> nvDaNghi = new ArrayList<>();

    public ViewNhanVienNew() {
        initComponents();
        setOpaque(false);
        dtm = (DefaultTableModel) tblbang11.getModel();
        cbb = (DefaultComboBoxModel) cbbchucvu.getModel();
        ShowDataTable(sv.getAll());
        showCbb(chucVuService.getAll());
        rbnDangLam.setSelected(true);
    }

    private void ShowDataTable(List<NhanVienViewModel> lists) {
        dtm.setRowCount(0);
        int stt = 0;
        for (NhanVienViewModel list : lists) {
            stt++;
            dtm.addRow(new Object[]{
                stt,
                list.getId(),
                list.getTenNhanVien(),
                list.getNgaySinh(),
                list.getDiaChi(),
                list.getSdt(),
                list.getEmail(),
                list.getChucVu()
            });
        }
    }

    private void showCbb(List<ChucVu> chucVus) {
        cbb.removeAllElements();
        for (ChucVu cv : chucVus) {
            cbb.addElement(cv.getTenChucVu());
        }
        cbbchucvu.setSelectedItem(null);
    }

    public NhanVien getFormData() {
        String tennv = txthovaten.getText();
        java.util.Date date = dcngaysinh.getDate();
        LocalDate ngaySinh = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String diachi = txtdiachi.getText();
        String sdt = txtsdt.getText();
        String email = txtemail.getText();
        String cccd = txtcccd.getText();
        String chucvu = (String) cbbchucvu.getSelectedItem();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDate dateTime = LocalDate.now();

        NhanVien nv = new NhanVien(tennv, ngaySinh, diachi, sdt, email, chucvu, 1, dateTime, nhanVien, dateTime, nhanVien, cccd);
        return nv;
    }

    public boolean check() {
        if (txthovaten.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống tên nhân viên");
            return false;
        }
        if (txtsdt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống sđt nhân viên");
            return false;
        }
        if (txtdiachi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống địa chỉ nhân viên");
            return false;
        }
        if (txtemail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống email nhân viên");
            return false;
        }
        if (txtcccd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống cccd nhân viên");
            return false;
        }

        Date selectedDate = dcngaysinh.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh khách hàng");
            return false;
        }

// Check if selected date is before current date
        Date currentDate = new Date();
        if (selectedDate.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hiện tại");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtsdt = new mobileworld.swing.TextField();
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
        jPanel2 = new javax.swing.JPanel();
        txttimkiem = new mobileworld.swing.TextField();
        jPanel3 = new javax.swing.JPanel();
        rbnDangLam = new javax.swing.JRadioButton();
        rbnDaNghi = new javax.swing.JRadioButton();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbang11 = new mobileworld.swing.Table();
        buttonCustom2 = new mobileworld.swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Nhân Viên");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết Lập Thông Tin Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel1.setOpaque(false);

        txtsdt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtsdt.setLabelText("Số Điện Thoại");

        txtma.setEnabled(false);
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
        btnadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-30.png"))); // NOI18N
        btnadd.setText("Thêm");
        btnadd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btnsua.setForeground(new java.awt.Color(255, 255, 255));
        btnsua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-update-30 (1).png"))); // NOI18N
        btnsua.setText("Sửa");
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-delete-30.png"))); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbbchucvu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcccd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtemail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdiachi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsdt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txthovaten, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtcccd, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel2.setOpaque(false);

        txttimkiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txttimkiem.setLabelText("Tìm Kiếm");
        txttimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimkiemKeyReleased(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Trạng Thái", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel3.setOpaque(false);

        buttonGroup2.add(rbnDangLam);
        rbnDangLam.setText("Đang Làm");
        rbnDangLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnDangLamActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbnDaNghi);
        rbnDaNghi.setText("Đã Nghỉ");
        rbnDaNghi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnDaNghiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbnDangLam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(rbnDaNghi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbnDangLam)
                    .addComponent(rbnDaNghi)))
        );

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-excel-30.png"))); // NOI18N
        buttonCustom1.setText("Xuất Execl");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

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
                "STT", "Mã Nhân Viên", "Tên NV", "Ngày Sinh", "Địa Chỉ", "SĐT", "Email", "Chức Vụ"
            }
        ));
        tblbang11.setOpaque(false);
        tblbang11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbang11MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblbang11);
        if (tblbang11.getColumnModel().getColumnCount() > 0) {
            tblbang11.getColumnModel().getColumn(0).setMinWidth(30);
            tblbang11.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        buttonCustom2.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-30.png"))); // NOI18N
        buttonCustom2.setText("Làm Mới");
        buttonCustom2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCustom2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1160, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        NhanVienViewModel nv = sv.getAll().get(row);
        txthovaten.setText(nv.getTenNhanVien());
        txtma.setText(nv.getId());
        txtsdt.setText(nv.getSdt());
        txtdiachi.setText(nv.getDiaChi());
        txtemail.setText(nv.getEmail());
        cbbchucvu.setSelectedItem(nv.getChucVu());
        txtcccd.setText(nv.getCccd());
        LocalDate ngaySinh = nv.getNgaySinh();
        java.util.Date date = java.sql.Date.valueOf(ngaySinh);
        dcngaysinh.setDate(date);
    }//GEN-LAST:event_tblbang11MouseClicked

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        if (check()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn Thêm không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                sv.add(getFormData());
                ShowDataTable(sv.getAll());
                JOptionPane.showMessageDialog(this, "Thêm Nhân Viên Thành Công!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Thêm Thất Bại!");
        }
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        int row = tblbang11.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn 1 dòng để cập xóa", "Thông Báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn Xóa không", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            NhanVienViewModel nv = sv.getAll().get(row);
            sv.delete(nv.getId());
            ShowDataTable(sv.getAll());
            JOptionPane.showMessageDialog(this, "Xóa Nhân Viên Thành Công!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Xóa Thất Bại!");
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
       int row = tblbang11.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn 1 dòng để cập nhật", "Thông Báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            NhanVienViewModel nv = sv.getAll().get(row);
            sv.update(getFormData(), nv.getId());
            ShowDataTable(sv.getAll());
            JOptionPane.showMessageDialog(this, "cập nhật Nhân Viên Thành Công!");
            return;
        }
        JOptionPane.showMessageDialog(this, "cập nhật Thất Bại!");
    }//GEN-LAST:event_btnsuaActionPerformed

    private void txttimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimkiemKeyReleased
        ShowDataTable(sv.search(txttimkiem.getText()));
    }//GEN-LAST:event_txttimkiemKeyReleased

    private void rbnDangLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnDangLamActionPerformed
        nvDangLam = sv.getAll();
        ShowDataTable(nvDangLam);
    }//GEN-LAST:event_rbnDangLamActionPerformed

    private void rbnDaNghiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnDaNghiActionPerformed
        nvDaNghi = sv.getAllNvDaNghi();
        ShowDataTable(nvDaNghi);
    }//GEN-LAST:event_rbnDaNghiActionPerformed

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed
        txthovaten.setText("");
        txtma.setText("");
        txtsdt.setText("");
        txtdiachi.setText("");
        txtcccd.setText("");
        txtemail.setText("");
        cbbchucvu.setSelectedItem(null);
        dcngaysinh.setDate(null);
        ShowDataTable(sv.getAll());
    }//GEN-LAST:event_buttonCustom2ActionPerformed

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất danh sách không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            sv.xuatDanhSachNhanVien();
            JOptionPane.showMessageDialog(this, "Xuất thành công!");
            return;
        }
        if (check == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn NO.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn Cancel.");
            return;
        }
    }//GEN-LAST:event_buttonCustom1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnadd;
    private mobileworld.swing.ButtonCustom btnsua;
    private mobileworld.swing.ButtonCustom btnxoa;
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom2;
    private javax.swing.ButtonGroup buttonGroup2;
    private mobileworld.swing.Combobox cbbchucvu;
    private com.toedter.calendar.JDateChooser dcngaysinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbnDaNghi;
    private javax.swing.JRadioButton rbnDangLam;
    private mobileworld.swing.Table tblbang11;
    private mobileworld.swing.TextField txtcccd;
    private mobileworld.swing.TextField txtdiachi;
    private mobileworld.swing.TextField txtemail;
    private mobileworld.swing.TextField txthovaten;
    private mobileworld.swing.TextField txtma;
    private mobileworld.swing.TextField txtsdt;
    private mobileworld.swing.TextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
