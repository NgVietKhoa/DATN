package mobileworld.form;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import mobileworld.service.HoaDonService.HoaDonCTService;
import mobileworld.service.HoaDonService.HoaDonService;
import mobileworld.viewModel.HoaDonChiTietModel;
import mobileworld.viewModel.HoaDonModel;

import mobileworld.component.Menu;
import mobileworld.model.HoaDon;
import mobileworld.model.PhuongThucThanhToan;
import mobileworld.main.Main;
import mobileworld.qrcode.qrcode;
import mobileworld.qrcode.qrcode.QRCodeListener;

import mobileworld.service.HoaDonService.LichSuHDService;
import mobileworld.service.HoaDonService.PhuongThucThanhToanService;
//import mobileworld.service.QRCodeScannerApp;

import mobileworld.viewModel.LichSuHDModel;

//import qrcode.qrcode.QRCodeListener;
public class ViewHoaDon extends javax.swing.JPanel implements QRCodeListener {

    private Main main;
    private Menu menu;
    List<HoaDon> listHD = new ArrayList<>();
    List<HoaDonModel> list = new ArrayList<>();
    List<HoaDonModel> listHDM = new ArrayList<>();

    List<HoaDonChiTietModel> list22 = new ArrayList<>();
    List<LichSuHDModel> listLS2 = new ArrayList<>();
    List<HoaDonChiTietModel> list2 = new ArrayList<>();

    List<LichSuHDModel> listLS = new ArrayList<>();
    List<PhuongThucThanhToan> listPTTT = new ArrayList<>();

    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultTableModel tableModel2 = new DefaultTableModel();
    DefaultTableModel tableModel3 = new DefaultTableModel();

    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModel1 = new DefaultComboBoxModel();

    List<HoaDonModel> search = new ArrayList<>();

    PhuongThucThanhToanService srPTTT = new PhuongThucThanhToanService();
    HoaDonService sr = new HoaDonService();
    HoaDonCTService srCT = new HoaDonCTService();
    LichSuHDService srLSHD = new LichSuHDService();

    public ViewHoaDon(Main main, Menu menu) {
        initComponents();
        list = sr.getAllHD();
        listPTTT = srPTTT.getAll();

//        listHD = sr.getAllHD();
        tableModel = (DefaultTableModel) tblHienThi1.getModel();
        comboBoxModel = (DefaultComboBoxModel) cboHTTT.getModel();
        comboBoxModel1 = (DefaultComboBoxModel) cboTrangThaiHD.getModel();
        showDataTable(list);
//        showCBOTT(listHD);
//        showCBO(listPTTT);
        setOpaque(false);
        this.main = main;
        this.menu = menu;
    }

    @Override
    public void onQRCodeScanned(String result) {

        List<HoaDonModel> list = sr.getAllQR(result);
        showDataTable(list);
        if (!list.isEmpty()) {
            tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
            List<HoaDonChiTietModel> list22 = srCT.getAll(result);
            showDataTable2(list22);

            tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
            List<LichSuHDModel> listLS2 = srLSHD.getAll(result);
            showDataTable3(listLS2);

            System.out.println("Nhận giá trị" + result);

        } else {
            // Nếu danh sách rỗng, hiển thị thông báo yêu cầu người dùng thử lại hoặc chọn QR khác
            JOptionPane.showMessageDialog(null, "Quét không thành công. Vui lòng thử lại hoặc chọn QR khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

//    @Override
//    public void onQRCodeScanned(String result) {
//        list = sr.getAllQR(result);
//        showDataTable(list);
//        
//        
//        tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
//        list2 = srCT.getAll(result);
//        showDataTable2(list2);
//
//        tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
//        listLS = srLSHD.getAll(result);
//        showDataTable3(listLS);
//        
//        System.out.println(result);
//    }
    public void showDataTable(List<HoaDonModel> list1) {
        tableModel.setRowCount(0);
        int tt = 0;
        String trangThai = "";
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        for (HoaDonModel hoaDonModel : list1) {

            tt++;

            if (hoaDonModel.getTrangThai() == 0) {
                trangThai = "Chờ thanh toán";
            } else {
                trangThai = "Đã thanh toán";
            }
            String formattedTongTien = currencyFormat.format(hoaDonModel.getTongTien());
            String formattedNgayThanhToan = hoaDonModel.getNgayThanhToan().format(dateFormatter);

            tableModel.addRow(new Object[]{
                tt,
                hoaDonModel.getIdHD(),
                hoaDonModel.getIdNV(),
                hoaDonModel.getTenKH(),
                hoaDonModel.getSDTKH(),
                hoaDonModel.getDiaChiKH(),
                formattedNgayThanhToan,
                hoaDonModel.getTenKieuThanhToan(),
                formattedTongTien,
                trangThai
            });
        }
    }

//    public void showDataTable2(List<HoaDonChiTietModel> listHDCT) {
//        tableModel2.setRowCount(0);
//        int stt = 0;
//        // Lấy định dạng tiền tệ của Việt Nam
//        DecimalFormat currencyFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        for (HoaDonChiTietModel hoaDonChiTietModel : listHDCT) {
//            stt++;
//            // Định dạng tổng tiền và giá bán về kí hiệu tiền Việt Nam
//            String formattedTongTien = currencyFormat.format(hoaDonChiTietModel.getTongTien()) + " VND";
//            String formattedGiaBan = currencyFormat.format(hoaDonChiTietModel.getGiaBan()) + " VND";
//            tableModel2.addRow(new Object[]{
//                stt,
//                hoaDonChiTietModel.getIdCTSP(),
//                hoaDonChiTietModel.getTenDSP(),
//                hoaDonChiTietModel.getTenNSX(),
//                hoaDonChiTietModel.getTenMau(),
//                hoaDonChiTietModel.getDungLuongPin(),
//                hoaDonChiTietModel.getImel(),
//                formattedGiaBan,
//                formattedTongTien // Sử dụng tổng tiền đã định dạng
//            });
//        }
//    }
    public void showDataTable2(List<HoaDonChiTietModel> listHDCT) {
        tableModel2.setRowCount(0);
        int stt = 0;
        // Lấy định dạng tiền tệ của Việt Nam
        DecimalFormat currencyFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("vi", "VN"));
        for (HoaDonChiTietModel hoaDonChiTietModel : listHDCT) {
            stt++;
            // Định dạng tổng tiền về kí hiệu tiền Việt Nam
            String formattedTongTien = currencyFormat.format(hoaDonChiTietModel.getTongTien());
            String formattedGiaBan = currencyFormat.format(hoaDonChiTietModel.getGiaBan());
            tableModel2.addRow(new Object[]{
                stt,
                hoaDonChiTietModel.getIdCTSP(),
                hoaDonChiTietModel.getTenDSP(),
                hoaDonChiTietModel.getTenNSX(),
                hoaDonChiTietModel.getTenMau(),
                hoaDonChiTietModel.getDungLuongPin(),
                hoaDonChiTietModel.getImel(),
                formattedGiaBan,
                formattedTongTien// Sử dụng tổng tiền đã định dạng
            });
        }
    }

    public void showDataTable3(List<LichSuHDModel> listLSHD) {
        tableModel3.setRowCount(0);
        int stt = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        for (LichSuHDModel lichSuHDModel : listLSHD) {
            stt++;
            String formattedNgayGio = lichSuHDModel.getNgayGio().format(dateFormatter);

            tableModel3.addRow(new Object[]{
                stt,
                lichSuHDModel.getIdNV(),
                formattedNgayGio,
                lichSuHDModel.getHanhDong()
            });
        }
    }
//=============================================================================================================
//    public void showCBO(List<PhuongThucThanhToan> listPTTT1) {
//        comboBoxModel.removeAllElements();
//        for (PhuongThucThanhToan phuongThucThanhToan : listPTTT1) {
//            comboBoxModel.addElement(phuongThucThanhToan.getTeKieuThanhToan());
//        }
//    }

//    public void showCBOTT(List<HoaDon> listHD) {
//        comboBoxModel1.removeAllElements();
//        String trangThaiHD = "";
//        for (HoaDon hoaDon : listHD) {
//            if (hoaDon.getTrangthai() == 0) {
//                trangThaiHD = "Chờ thanh toán";
//            } else {
//                trangThaiHD = "Đã thanh toán";
//            }
//            comboBoxModel1.addElement(hoaDon.getTrangthai());
//        }
//    }
//==============================================================================================================
    public void show() {

        int index = tblHienThi1.getSelectedRow();
        HoaDonModel hdm = list.get(index);

        tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
        list2 = srCT.getAll(hdm.getIdHD());
        showDataTable2(list2);

        tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
        listLS = srLSHD.getAll(hdm.getIdHD());
        showDataTable3(listLS);
    }

    private void inHoaDon(String invoiceId) {
        if (sr.inHD(invoiceId)) {
            JOptionPane.showMessageDialog(this, "In hóa đơn thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi in hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public void searchGia() {
//        String giaKhoangText = txtGiaKhoang.getText().trim();
//        String denGiaText = txtDenGia.getText().trim();
//
//        if (giaKhoangText.isEmpty() || denGiaText.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá khoảng và đến giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        BigDecimal giaKhoang = new BigDecimal(giaKhoangText);
//        BigDecimal denGia = new BigDecimal(denGiaText);
//
//        showDataTable(sr.searchGia(giaKhoang, denGia));
//        if (tblHienThi1.getRowCount() == 1) {
//            JOptionPane.showMessageDialog(this, "Lọc thành công");
//        }
//
//    }
    private void filterData() {
        String ht = (String) cboHTTT.getSelectedItem();
        String tt = (String) cboTrangThaiHD.getSelectedItem();

        int trangThai;
        if (tt.equals("Chờ thanh toán")) {
            trangThai = 0;
        } else {
            trangThai = 1;
        }
        list = sr.filterHoaDon(ht, trangThai);
        if (list.isEmpty()) {
            showDataTable(list);
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            showDataTable(list);
            JOptionPane.showMessageDialog(this, "Lọc thành công");
            return;

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtSearch = new mobileworld.swing.TextField();
        cboTrangThaiHD = new mobileworld.swing.Combobox();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();
        btnTaoHoaDon = new mobileworld.swing.ButtonCustom();
        cboHTTT = new mobileworld.swing.Combobox();
        jLabel2 = new javax.swing.JLabel();
        txtGiaKhoang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHienThi1 = new mobileworld.swing.Table();
        reset = new mobileworld.swing.ButtonCustom();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHienThi2 = new mobileworld.swing.Table();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHienThi3 = new mobileworld.swing.Table();
        btnXuatHoaDon = new mobileworld.swing.ButtonCustom();
        buttonCustom4 = new mobileworld.swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel1.setOpaque(false);

        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSearch.setLabelText("Tìm Kiếm");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        cboTrangThaiHD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chờ thanh toán", "Đã thanh toán" }));
        cboTrangThaiHD.setSelectedIndex(-1
        );
        cboTrangThaiHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboTrangThaiHD.setLabeText("Trạng Thái Hóa Đơn");
        cboTrangThaiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiHDActionPerformed(evt);
            }
        });

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-qr-code-30.png"))); // NOI18N
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-plus-24.png"))); // NOI18N
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoHoaDon.setPreferredSize(new java.awt.Dimension(86, 26));
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiền mặt", "Chuyển khoản", "Cả 2 hình thức" }));
        cboHTTT.setSelectedIndex(-1);
        cboHTTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboHTTT.setLabeText("Hình Thức Thanh Toán");
        cboHTTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHTTTActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Giá Khoảng Từ");

        txtGiaKhoang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaKhoangActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);

        tblHienThi1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Mã NV ", "Tên KH", "SĐT KH", "Địa chỉ", "Ngày Thanh Toán", "Loại thanh toán", "Tổng tiền ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHienThi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHienThi1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHienThi1);
        if (tblHienThi1.getColumnModel().getColumnCount() > 0) {
            tblHienThi1.getColumnModel().getColumn(0).setMinWidth(10);
            tblHienThi1.getColumnModel().getColumn(0).setMaxWidth(40);
            tblHienThi1.getColumnModel().getColumn(1).setMinWidth(10);
            tblHienThi1.getColumnModel().getColumn(1).setMaxWidth(60);
            tblHienThi1.getColumnModel().getColumn(2).setMinWidth(10);
            tblHienThi1.getColumnModel().getColumn(2).setMaxWidth(55);
        }

        reset.setForeground(new java.awt.Color(255, 255, 255));
        reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-refresh-24.png"))); // NOI18N
        reset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGiaKhoang, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGiaKhoang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(reset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel2.setOpaque(false);

        jScrollPane3.setBorder(null);

        tblHienThi2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "NSX", "Màu", "Pin", "Imel", "Giá bán", "Tổng tiền"
            }
        ));
        jScrollPane3.setViewportView(tblHienThi2);
        if (tblHienThi2.getColumnModel().getColumnCount() > 0) {
            tblHienThi2.getColumnModel().getColumn(0).setMinWidth(10);
            tblHienThi2.getColumnModel().getColumn(0).setMaxWidth(40);
            tblHienThi2.getColumnModel().getColumn(3).setMinWidth(1);
            tblHienThi2.getColumnModel().getColumn(3).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hóa Đơn");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lịch Sử Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel4.setOpaque(false);

        jScrollPane4.setBorder(null);

        tblHienThi3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Người tác động", "Ngày Giờ cập nhật", "Hành động"
            }
        ));
        jScrollPane4.setViewportView(tblHienThi3);
        if (tblHienThi3.getColumnModel().getColumnCount() > 0) {
            tblHienThi3.getColumnModel().getColumn(0).setMinWidth(10);
            tblHienThi3.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btnXuatHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-excel-30.png"))); // NOI18N
        btnXuatHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHoaDonActionPerformed(evt);
            }
        });

        buttonCustom4.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-pdf-40.png"))); // NOI18N
        buttonCustom4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom4ActionPerformed(evt);
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bán có muốn thêm Hóa Đơn không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            if (menu != null) {
                main.showForm(new ViewBanHang());
                menu.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Đã chuyển hướng MENU.");
                return;
            }
        }
        if (check == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn NO.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn CANCEL.");
            return;
        }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHoaDonActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất danh sách Hóa Đơn không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            sr.xuatHoaDon();
            JOptionPane.showMessageDialog(this, "Xuất hóa đơn thành công.");
            return;
        }
        if (check == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn NO.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn CANCEL.");
            return;
        }
    }//GEN-LAST:event_btnXuatHoaDonActionPerformed

    private void buttonCustom4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom4ActionPerformed

        int index = tblHienThi1.getSelectedRow();
        if (index != -1) { // Đảm bảo rằng đã chọn một dòng trong bảng
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn in Hoa Đơn đã chọn không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // Nếu người dùng chọn Yes, thực hiện in Hoa Đơn
                HoaDonModel hdm = list.get(index);
                inHoaDon(hdm.getIdHD());
            }
        } else {
            // Nếu không có hàng nào được chọn, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần in!");
        }

    }//GEN-LAST:event_buttonCustom4ActionPerformed

    private void tblHienThi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHienThi1MouseClicked
        // TODO add your handling code here:
        int index = tblHienThi1.getSelectedRow();
        HoaDonModel hdm = list.get(index);

        tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
        list2 = srCT.getAll(hdm.getIdHD());
        showDataTable2(list2);

        tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
        listLS = srLSHD.getAll(hdm.getIdHD());
        showDataTable3(listLS);
    }//GEN-LAST:event_tblHienThi1MouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        if (txtSearch.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin cần tìm kiếm.", "ERROR", JOptionPane.QUESTION_MESSAGE);
            txtSearch.setText("");
            return;
        }
        list = sr.search(txtSearch.getText()); // Cập nhật danh sách sau khi tìm kiếm
        showDataTable(list);
        int index = tblHienThi1.getRowCount();
        if (index >= 1) {
            JOptionPane.showMessageDialog(this, "Search thành công.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu.");
            return;
        }
    }//GEN-LAST:event_txtSearchActionPerformed

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn quét QR không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if (check == JOptionPane.YES_OPTION) {
            qrcode qr = new qrcode();
            qr.setQRCodeListener(this);
            qr.setVisible(true);
            return;
        }
        if (check == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn NO.");
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Banj đã chọn CANCEL.");
            return;
        }

    }//GEN-LAST:event_buttonCustom1ActionPerformed
    private BigDecimal convertStringToBigDecimal(String input) {
        // Loại bỏ tất cả các ký tự không phải là số từ chuỗi
        String cleanInput = input.replaceAll("[^0-9]", "");
        // Chuyển đổi chuỗi đã làm sạch thành BigDecimal
        return new BigDecimal(cleanInput);
    }
    private void cboHTTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHTTTActionPerformed
        // TODO add your handling code here:
        list = sr.hinhThucHoaDon((String) cboHTTT.getSelectedItem());
        showDataTable(list);
//        filterData();
    }//GEN-LAST:event_cboHTTTActionPerformed

    private void cboTrangThaiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiHDActionPerformed
        // TODO add your handling code here:
        String tt = (String) cboTrangThaiHD.getSelectedItem();
        int trangThai;
        if (tt.equals("Chờ thanh toán")) {
            trangThai = 0;
        } else {
            trangThai = 1;
        }
        list = sr.trangThaiHoaDon(trangThai);
        showDataTable(list);
//        filterData();

    }//GEN-LAST:event_cboTrangThaiHDActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        list = sr.getAllHD();
        showDataTable(list);

    }//GEN-LAST:event_resetActionPerformed

    private void txtGiaKhoangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaKhoangActionPerformed
        // TODO add your handling code here:
////        // Lấy giá trị từ trường văn bản
        String giaKhoang = txtGiaKhoang.getText();
        // Tách giá trị từ và đến
        String[] giaKhoangArr = giaKhoang.split("-");
        if (giaKhoangArr.length == 2) {
            // Lấy giá trị từ và đến
            BigDecimal giaTu = new BigDecimal(giaKhoangArr[0].trim());
            BigDecimal giaDen = new BigDecimal(giaKhoangArr[1].trim());

            // Thực hiện tìm kiếm từ khoảng đến và hiển thị dữ liệu
            showDataTable(sr.searchGia(giaTu, giaDen));
        } else {
            // Hiển thị thông báo lỗi nếu định dạng không chính xác
            JOptionPane.showMessageDialog(this, "Định dạng không hợp lệ. Vui lòng nhập lại theo định dạng 'GiaTu - GiaDen'.");
        }

    }//GEN-LAST:event_txtGiaKhoangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnTaoHoaDon;
    private mobileworld.swing.ButtonCustom btnXuatHoaDon;
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom4;
    private mobileworld.swing.Combobox cboHTTT;
    private mobileworld.swing.Combobox cboTrangThaiHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private mobileworld.swing.ButtonCustom reset;
    private mobileworld.swing.Table tblHienThi1;
    private mobileworld.swing.Table tblHienThi2;
    private mobileworld.swing.Table tblHienThi3;
    private javax.swing.JTextField txtGiaKhoang;
    private mobileworld.swing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
