package mobileworld.form;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import mobileworld.service.ThongKeService.QuanLythongKeController;
import mobileworld.service.ThongKeService.ThongKeService;
import mobileworld.service.ThongKeService.ThongKeServiceImpl;
import mobileworld.viewModel.ThongKe.HoaDonTK;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ViewThongKe extends javax.swing.JPanel {

    private ThongKeService sr = new ThongKeServiceImpl();

    DefaultTableModel dtm = new DefaultTableModel();
    private List<HoaDonTK> ds = new ArrayList<>();
    private List<HoaDonTK> dsSearch = new ArrayList<>();

    SimpleDateFormat spxHien = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public ViewThongKe() {
        initComponents();
        setOpaque(false);
        QuanLythongKeController controller = new QuanLythongKeController();
        controller.setDateToChart(panelTK);
        hienDoanhThu();
        soTienDaThu();
        hienHoaDonChuaTT();
        soHD();
        ds = sr.hienBang();
        dtm = (DefaultTableModel) tblBang.getModel();
        filltable(ds);
    }

    private void filltable(List<HoaDonTK> TKList) {
        dtm.setRowCount(0);
        int i = 0;

        for (HoaDonTK tk : TKList) {
            i++;
            LocalDate localDate = tk.getNgayTao();
            LocalDateTime localDateTime = localDate.atStartOfDay();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String ngay = spxHien.format(date);
            String tongTien = decimalFormat.format(tk.getThanhTien());
            dtm.addRow(new Object[]{i, tk.getMaHD(), ngay, tongTien});
        }
    }

    private void hienDoanhThu() {
        float dt = sr.getDoanhThu();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String tt = decimalFormat.format(dt);
        textDoanhThu.setText(tt);
    }

    private void soTienDaThu() {
        float dt = sr.soTienDaThuDuoc();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String tt = decimalFormat.format(dt);
        textSoHoaDonDTT.setText(tt);
    }

    private void hienHoaDonChuaTT() {
        float dt = sr.hoaDonChuaThanhToan();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String tt = decimalFormat.format(dt);
        textHoaDonCTT.setText(tt);
    }

    private void soHD() {
        int soHD = sr.soHD();
        String hienHDCTT = "" + soHD;
        textsoHD.setText(hienHDCTT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        textDoanhThu = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        textSoHoaDonDTT = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        textHoaDonCTT = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        textsoHD = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        cboYear = new mobileworld.swing.Combobox();
        jPanel7 = new javax.swing.JPanel();
        cboNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboNgayKT = new com.toedter.calendar.JDateChooser();
        btnRefesh2 = new mobileworld.swing.ButtonCustom();
        materialTabbed1 = new mobileworld.swing.MaterialTabbed();
        jPanel8 = new javax.swing.JPanel();
        panelTK = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new mobileworld.swing.Table();
        btnTKToday = new mobileworld.swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 15, 15));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Doanh thu ước tính");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        textDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        textDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textDoanhThu.setText("Text");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel6)
                .addGap(31, 31, 31)
                .addComponent(textDoanhThu)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 204, 51));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tổng tiền các hóa đơn");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        textSoHoaDonDTT.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textSoHoaDonDTT.setForeground(new java.awt.Color(255, 255, 255));
        textSoHoaDonDTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textSoHoaDonDTT.setText("Text");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("đã thanh toán");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textSoHoaDonDTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(textSoHoaDonDTT)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(51, 153, 0));

        textHoaDonCTT.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textHoaDonCTT.setForeground(new java.awt.Color(255, 255, 255));
        textHoaDonCTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textHoaDonCTT.setText("Text");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tổng tiền các hóa đơn");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("chưa thanh toán");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textHoaDonCTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(14, 14, 14)
                .addComponent(textHoaDonCTT)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(251, 168, 52));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tổng số hóa đơn");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        textsoHD.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textsoHD.setForeground(new java.awt.Color(255, 255, 255));
        textsoHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textsoHD.setText("Text");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(textsoHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel5)
                .addGap(35, 35, 35)
                .addComponent(textsoHD)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THỐNG KÊ");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Lọc Theo Năm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel6.setOpaque(false);

        cboYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "2024", "2023", "2022", "2021", "2020" }));
        cboYear.setLabeText("");
        cboYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboYear, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Lọc Theo Khoảng Thời Gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel7.setOpaque(false);

        cboNgayBatDau.setDateFormatString("dd-MM-yyyy");

        jLabel2.setBackground(new java.awt.Color(12, 45, 87));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Từ");

        jLabel3.setBackground(new java.awt.Color(12, 45, 87));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Đến");

        cboNgayKT.setDateFormatString("dd-MM-yyyy");
        cboNgayKT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboNgayKTPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboNgayKT, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(cboNgayBatDau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnRefesh2.setForeground(new java.awt.Color(255, 255, 255));
        btnRefesh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-24.png"))); // NOI18N
        btnRefesh2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefesh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefesh2ActionPerformed(evt);
            }
        });

        jPanel8.setOpaque(false);

        javax.swing.GroupLayout panelTKLayout = new javax.swing.GroupLayout(panelTK);
        panelTK.setLayout(panelTKLayout);
        panelTKLayout.setHorizontalGroup(
            panelTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1178, Short.MAX_VALUE)
        );
        panelTKLayout.setVerticalGroup(
            panelTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(panelTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        materialTabbed1.addTab("Biểu đồ", jPanel8);

        jPanel9.setOpaque(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày thanh toán", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBang.setOpaque(false);
        jScrollPane1.setViewportView(tblBang);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1169, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Doanh thu theo thời gian", jPanel9);

        btnTKToday.setForeground(new java.awt.Color(255, 255, 255));
        btnTKToday.setText("Thống kê hôm nay");
        btnTKToday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTKToday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKTodayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTKToday, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRefesh2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(materialTabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnRefesh2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKToday, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        materialTabbed1.getAccessibleContext().setAccessibleName("Biểu đồ");
    }// </editor-fold>//GEN-END:initComponents

    private void cboYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearActionPerformed
        // TODO add your handling code here:
        String Year = (String) cboYear.getSelectedItem();
        if (Year.equals("Tất cả")) {
            QuanLythongKeController controller = new QuanLythongKeController();
            controller.setDateToChart(panelTK);
            ds = sr.hienBang();
            filltable(ds);
        } else {
            sr.timTheoNam(Year);
            QuanLythongKeController controller = new QuanLythongKeController();
            controller.setDateToChartPerYear(panelTK, Year);
            dsSearch = sr.timTheoNamTable(Year);
            filltable(dsSearch);
        }

    }//GEN-LAST:event_cboYearActionPerformed

    private void cboNgayKTPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboNgayKTPropertyChange
        // TODO add your handling code here:
        if (cboNgayBatDau != null && cboNgayKT != null && cboNgayBatDau.getDate() != null && cboNgayKT.getDate() != null) {

            Date getNgayBD = cboNgayBatDau.getDate();
            LocalDateTime ngayBD = getNgayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDate ngayBDLocalDate = ngayBD.toLocalDate();
            Date getNgayKT = cboNgayKT.getDate();
            LocalDateTime ngayKT = getNgayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDate ngayKTLocalDate = ngayKT.toLocalDate();

            sr.timTheoThoiGianTable(ngayBDLocalDate, ngayKTLocalDate);
            QuanLythongKeController controller = new QuanLythongKeController();
            controller.setDateToChartPerTime(panelTK, ngayBDLocalDate, ngayKTLocalDate);
            dsSearch = sr.timTheoThoiGianTable(ngayBDLocalDate, ngayKTLocalDate);
            filltable(dsSearch);
        }
    }//GEN-LAST:event_cboNgayKTPropertyChange

    private void btnRefesh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefesh2ActionPerformed
        // TODO add your handling code here:       
        cboNgayBatDau.setDate(null);
        cboNgayKT.setDate(null);
        cboYear.setSelectedIndex(0);
        QuanLythongKeController controller = new QuanLythongKeController();
        controller.setDateToChart(panelTK);
        hienDoanhThu();
        soTienDaThu();
        hienHoaDonChuaTT();
        soHD();
        dsSearch.clear();
        ds = sr.hienBang();
        filltable(ds);
        materialTabbed1.setSelectedComponent(jPanel8);
    }//GEN-LAST:event_btnRefesh2ActionPerformed

    private void btnTKTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKTodayActionPerformed
        // TODO add your handling code here:
        LocalDate ngayHT = LocalDate.now();
        QuanLythongKeController controller = new QuanLythongKeController();
        controller.setDateToChartPerDay(panelTK, ngayHT);
        dsSearch = sr.thongKeTheoNgayTable(ngayHT);
        filltable(dsSearch);
    }//GEN-LAST:event_btnTKTodayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnRefesh2;
    private mobileworld.swing.ButtonCustom btnTKToday;
    private com.toedter.calendar.JDateChooser cboNgayBatDau;
    private com.toedter.calendar.JDateChooser cboNgayKT;
    private mobileworld.swing.Combobox cboYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private mobileworld.swing.MaterialTabbed materialTabbed1;
    private javax.swing.JPanel panelTK;
    private mobileworld.swing.Table tblBang;
    private javax.swing.JLabel textDoanhThu;
    private javax.swing.JLabel textHoaDonCTT;
    private javax.swing.JLabel textSoHoaDonDTT;
    private javax.swing.JLabel textsoHD;
    // End of variables declaration//GEN-END:variables
}
