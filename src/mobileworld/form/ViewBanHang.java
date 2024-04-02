package mobileworld.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import mobileworld.dialog.DeselectProductSP;
import mobileworld.dialog.SelectProductSP;
import mobileworld.model.CPU;
import mobileworld.model.ManHinh;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.PhieuGiamGia;
import mobileworld.model.Pin;
import mobileworld.service.BanHangService.BanHangService;
import mobileworld.service.ChiTietSanPhamService.CpuService;
import mobileworld.service.ChiTietSanPhamService.ManHinhService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
import mobileworld.service.PhieuGiamGiaService.PhieuGiamGiaService;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class ViewBanHang extends javax.swing.JPanel {

    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private final BanHangService bhService = new BanHangService();
    private DefaultTableModel tblModelHD = new DefaultTableModel();
    private DefaultTableModel tblModelSP = new DefaultTableModel();
    private DefaultTableModel tblModelGH = new DefaultTableModel();
    private DefaultComboBoxModel cbbPin = new DefaultComboBoxModel();
    private final PinService pinService = new PinService();
    private DefaultComboBoxModel cbbManHinh = new DefaultComboBoxModel();
    private final ManHinhService mhService = new ManHinhService();
    private DefaultComboBoxModel cbbCpu = new DefaultComboBoxModel();
    private final CpuService cpuService = new CpuService();
    private DefaultComboBoxModel cbbNsx = new DefaultComboBoxModel();
    private final NhaSanXuatService NsxService = new NhaSanXuatService();
    private List<ChiTietSanPhamViewModel> BoLocCtsp = new ArrayList<>();
    private final PhieuGiamGiaService pggService = new PhieuGiamGiaService();
    private DefaultComboBoxModel cbbPgg = new DefaultComboBoxModel();
    private List<ChiTietSanPhamViewModel> gioHangList = new ArrayList<>();

    public ViewBanHang() {
        initComponents();
        setOpaque(false);
        tblModelHD = (DefaultTableModel) tblHoaDon.getModel();
        tblModelSP = (DefaultTableModel) tblSP.getModel();
        tblModelGH = (DefaultTableModel) tblGioHang.getModel();
        tblSP.setDefaultEditor(Object.class, null);
        tblGioHang.setDefaultEditor(Object.class, null);
        tblHoaDon.setDefaultEditor(Object.class, null);
        //cbo
        txtGetTenKH.setText("Khách Bán Lẻ");
        cbbPin = (DefaultComboBoxModel) cboPin.getModel();
        cbbManHinh = (DefaultComboBoxModel) cboManHinh.getModel();
        cbbCpu = (DefaultComboBoxModel) cboCPU.getModel();
        cbbNsx = (DefaultComboBoxModel) cboNSX.getModel();
        cbbPgg = (DefaultComboBoxModel) cboPgg.getModel();
        setDataCboPin(pinService.getAll());
        setDataCboManHinh(mhService.getAll());
        setDataCboCpu(cpuService.getAll());
        setDataCboNsx(NsxService.getAll());
        setDataCboPhieuGG(pggService.getAll());
        setDataCboHTTT();

        tblSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblSP.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    String idDsp = (String) tblSP.getValueAt(index, 1);

                    SelectProductSP productImel = new SelectProductSP(idDsp, ViewBanHang.this);
                    productImel.setVisible(true);
                }
            }
        });

        tblGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblGioHang.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    String idDsp = (String) tblGioHang.getValueAt(index, 1);
                    DeselectProductSP deselectProductSP = new DeselectProductSP(idDsp, ViewBanHang.this);
                    deselectProductSP.setVisible(true);
                }
            }
        });

        HoaDonViewModel newestHoaDon = bhService.getNewestHoaDon();
        if (newestHoaDon != null) {
            setTxtFieldsFromSelectedHoaDon(newestHoaDon);
        }

        txtGetTenKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFields();
            }

            private void updateFields() {
                String tenKH = txtGetTenKH.getText();
                if (newestHoaDon != null) {
                    newestHoaDon.setTenKH(tenKH);
                    txtSetTenKH.setText(newestHoaDon.getTenKH());
                    setTxtFieldsFromSelectedHoaDon(newestHoaDon);
                }
            }
        });

        showDataTableHoaDon(bhService.getHD());
        showDataTableSP(bhService.getSP());
        System.out.println();
    }

    private void setDataCboPhieuGG(List<PhieuGiamGia> setPgg) {
        cbbPgg.removeAllElements();
        for (PhieuGiamGia pgg : setPgg) {
            if (pgg.getDeleted() == 1) {
                cbbPgg.addElement(pgg.getTenGiamGia());
            }
        }
        cbbPgg.setSelectedItem(null);
    }

    private void setDataCboHTTT() {
        String[] paymentMethods = {"", "Tiền mặt", "Chuyển khoản", "Cả 2 hình thức"};
        for (String method : paymentMethods) {
            cboHTT.addItem(method);
        }
        cboHTT.setSelectedIndex(0);
    }

    private BigDecimal calculateTotalAmountFromGioHang() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            Object amountObject = tblGioHang.getValueAt(i, 10);
            if (amountObject != null) {
                String amountString = amountObject.toString();
                if (!amountString.isEmpty()) {
                    BigDecimal amount = new BigDecimal(amountString.replace(",", ""));
                    totalAmount = totalAmount.add(amount);
                }
            }
        }
        return totalAmount;
    }

    private void setTxtFieldsFromSelectedHoaDon(HoaDonViewModel selectedHoaDon) {
        Date createAt = selectedHoaDon.getCreateAt();
        String createBy = selectedHoaDon.getCreateBy();
        String maHD = selectedHoaDon.getIdHD();
        String tenKH = txtGetTenKH.getText();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(createAt);

        txtNgayTao.setText(dateString);
        txtNgayThanhToan.setText(dateString);
        txtMaNV.setText(createBy);
        txtMaHD.setText(maHD);
        txtSetTenKH.setText(tenKH);
        // Tính tổng số tiền từ bảng tblGioHang
        BigDecimal totalAmount = calculateTotalAmountFromGioHang();
        // Hiển thị tổng số tiền tính được vào txtTongTien
        txtTongTien.setText(decimalFormat.format(totalAmount));
    }

    private void showDataTableHoaDon(List<HoaDonViewModel> listHD) {
        tblModelHD.setRowCount(0);
        int stt = 0;
        String trangThai = "";
        for (HoaDonViewModel hdvm : listHD) {
            stt++;
            if (hdvm.getTrangthai() == 0) {
                trangThai = "Chờ thanh toán";
            } else {
                trangThai = "Đã thanh toán";
            }
            tblModelHD.addRow(new Object[]{
                stt, hdvm.getIdHD(), hdvm.getCreateAt(), hdvm.getCreateBy(), hdvm.getTongSP(), trangThai
            });
        }

        // Sau khi cập nhật dữ liệu cho bảng tblHoaDon, cập nhật lại tổng tiền
        if (listHD.isEmpty()) {
            txtTongTien.setText(decimalFormat.format(BigDecimal.ZERO));
        } else {
            setTxtFieldsFromSelectedHoaDon(listHD.get(0)); // Chọn mặc định hóa đơn đầu tiên
        }
    }

    private void showDataTableSP(List<ChiTietSanPhamViewModel> listSP) {
        tblModelSP.setRowCount(0);
        int stt = 0;
        for (ChiTietSanPhamViewModel sp : listSP) {
            String giaBan = decimalFormat.format(sp.getGiaBan());
            stt++;
            tblModelSP.addRow(new Object[]{
                stt, sp.getTenDsp(), sp.getTenNsx(), sp.getLoaiManHinh(), sp.getCpu(), sp.getDungLuongPin(), sp.getSoLuong(), giaBan
            });
        }
    }

    public void updateGioHangWithImel(String imel, int totalQuantity) {
        List<ChiTietSanPhamViewModel> gioHang = bhService.getGioHang(imel);
        showDataTableGioHang(gioHang, totalQuantity);
        showDataTableSP(bhService.getSP());

        // Sau khi cập nhật dữ liệu cho bảng tblGioHang, cập nhật lại tổng tiền
        BigDecimal totalAmount = calculateTotalAmountFromGioHang();
        txtTongTien.setText(decimalFormat.format(totalAmount));
    }

    public void deleteGioHangWithImel(String imel, int totalQuantity) {
        List<ChiTietSanPhamViewModel> gioHang = bhService.deleteGioHang(imel);
        showDataDeleteTableGioHang(gioHang, totalQuantity);
        showDataTableSP(bhService.getSP());

        // Sau khi cập nhật dữ liệu cho bảng tblGioHang, cập nhật lại tổng tiền
        BigDecimal totalAmount = calculateTotalAmountFromGioHang();
        txtTongTien.setText(decimalFormat.format(totalAmount));
    }

    private void showDataTableGioHang(List<ChiTietSanPhamViewModel> listSP, int totalQuantity) {
        for (ChiTietSanPhamViewModel sp : listSP) {
            boolean existed = false;
            for (ChiTietSanPhamViewModel gioHangSP : gioHangList) {
                if (gioHangSP.getTenDsp().equals(sp.getTenDsp())) {
                    // Sản phẩm đã tồn tại trong giỏ hàng, chỉ cập nhật số lượng
                    gioHangSP.setSoLuong(gioHangSP.getSoLuong() + 1); // Chỉ cộng thêm 1 vào số lượng
                    existed = true;
                    break;
                }
            }
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới vào giỏ hàng với số lượng là 1
            if (!existed) {
                sp.setSoLuong(1);
                gioHangList.add(sp);
            }
        }
        // Cập nhật dữ liệu trên bảng
        updateDataTableGioHang();
    }

    private void showDataDeleteTableGioHang(List<ChiTietSanPhamViewModel> listSP, int totalQuantity) {
        for (ChiTietSanPhamViewModel sp : listSP) {
            boolean existed = false;
            for (ChiTietSanPhamViewModel gioHangSP : gioHangList) {
                if (gioHangSP.getTenDsp().equals(sp.getTenDsp())) {
                    // Sản phẩm đã tồn tại trong giỏ hàng, giảm số lượng đi 1
                    gioHangSP.setSoLuong(gioHangSP.getSoLuong() - 1); // Giảm đi 1 số lượng
                    if (gioHangSP.getSoLuong() == 0) { // Nếu số lượng bằng 0, xóa sản phẩm khỏi giỏ hàng
                        gioHangList.remove(gioHangSP);
                    }
                    existed = true;
                    break;
                }
            }
            // Nếu sản phẩm không tồn tại trong giỏ hàng, không cần thực hiện gì cả
        }
        // Cập nhật dữ liệu trên bảng
        updateDataTableGioHang();
    }

    private void updateDataTableGioHang() {
        int stt = 0;
        tblModelGH.setRowCount(0);
        for (ChiTietSanPhamViewModel sp : gioHangList) {
            stt++;
            String giaBan = decimalFormat.format(sp.getGiaBan());
            BigDecimal thanhTienBigDecimal = BigDecimal.valueOf(sp.getSoLuong()).multiply(sp.getGiaBan());
            String thanhTienFormatted = decimalFormat.format(thanhTienBigDecimal);

            tblModelGH.addRow(new Object[]{
                stt, sp.getTenDsp(), sp.getLoaiManHinh(), sp.getCpu(), sp.getDungLuongRam(), sp.getDungLuongBoNho(),
                sp.getDungLuongPin(), sp.getTenMau(), sp.getSoLuong(), giaBan, thanhTienFormatted
            });
        }
    }

    private void setDataCboPin(List<Pin> setPin) {
        cbbPin.removeAllElements();

        for (Pin pinE : setPin) {
            cbbPin.addElement(pinE.getDungLuongPin());
        }
        cboPin.setSelectedItem(null);
    }

    private void setDataCboManHinh(List<ManHinh> setMh) {
        cbbManHinh.removeAllElements();

        for (ManHinh mh : setMh) {
            cbbManHinh.addElement(mh.getLoaiManHinh());
        }
        cbbManHinh.setSelectedItem(null);
    }

    private void setDataCboCpu(List<CPU> setCpu) {
        cbbCpu.removeAllElements();

        for (CPU cpu : setCpu) {
            cbbCpu.addElement(cpu.getCpu());
        }
        cbbCpu.setSelectedItem(null);
    }

    private void setDataCboNsx(List<NhaSanXuat> setNsx) {
        cbbNsx.removeAllElements();

        for (NhaSanXuat nsx : setNsx) {
            cbbNsx.addElement(nsx.getTenNsx());
        }
        cbbNsx.setSelectedItem(null);
    }

    private void filterCTSP() {
        String nsx = (String) cboNSX.getSelectedItem();
        String pin = (String) cboPin.getSelectedItem();
        String manHinh = (String) cboManHinh.getSelectedItem();
        String cpu = (String) cboCPU.getSelectedItem();

        BoLocCtsp = bhService.LocSP(nsx, pin, manHinh, cpu);
        showDataTableSP(BoLocCtsp);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonCustom2 = new mobileworld.swing.ButtonCustom();
        buttonCustom4 = new mobileworld.swing.ButtonCustom();
        buttonCustom5 = new mobileworld.swing.ButtonCustom();
        combobox1 = new mobileworld.swing.Combobox();
        textField1 = new mobileworld.swing.TextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new mobileworld.swing.Table();
        jPanel2 = new javax.swing.JPanel();
        buttonCustom7 = new mobileworld.swing.ButtonCustom();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGioHang = new mobileworld.swing.Table();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiemSP = new mobileworld.swing.TextField();
        cboManHinh = new mobileworld.swing.Combobox();
        cboCPU = new mobileworld.swing.Combobox();
        cboPin = new mobileworld.swing.Combobox();
        cboNSX = new mobileworld.swing.Combobox();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSP = new mobileworld.swing.Table();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        materialTabbed2 = new mobileworld.swing.MaterialTabbed();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtMaKH = new mobileworld.swing.TextField();
        txtGetTenKH = new mobileworld.swing.TextField();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        buttonCustom3 = new mobileworld.swing.ButtonCustom();
        buttonCustom8 = new mobileworld.swing.ButtonCustom();
        jLabel13 = new javax.swing.JLabel();
        cboHTT = new javax.swing.JComboBox<>();
        cboPgg = new javax.swing.JComboBox<>();
        txtTongTien = new javax.swing.JLabel();
        txtSetTenKH = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JLabel();
        txtNgayThanhToan = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTienKhachCK = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel1.setOpaque(false);

        buttonCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-delete-document-24 (1).png"))); // NOI18N
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        buttonCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-24 (1).png"))); // NOI18N

        buttonCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-receipt-24.png"))); // NOI18N

        combobox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        combobox1.setLabeText("Trạng Thái");

        textField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textField1.setLabelText("Tìm Kiếm");
        textField1.setLineColor(new java.awt.Color(102, 102, 102));
        textField1.setSelectionColor(new java.awt.Color(102, 102, 102));

        jScrollPane3.setBorder(null);

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Ngày Tạo", "Người Tạo", "Tổng SP", "Trạng Thái"
            }
        ));
        jScrollPane3.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(20);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonCustom5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonCustom2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonCustom4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combobox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel2.setOpaque(false);

        buttonCustom7.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom7.setText("Xóa");
        buttonCustom7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonCustom7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom7ActionPerformed(evt);
            }
        });

        jScrollPane4.setBorder(null);

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên SP", "Màn Hình", "CPU", "Ram", "Rom", "Pin", "Màu Sắc", "SL", "Giá", "Tổng Tiền"
            }
        ));
        jScrollPane4.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setMinWidth(20);
            tblGioHang.getColumnModel().getColumn(0).setMaxWidth(40);
            tblGioHang.getColumnModel().getColumn(1).setMinWidth(150);
            tblGioHang.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel3.setOpaque(false);

        txtTimKiemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTimKiemSP.setLabelText("Tìm Kiếm");
        txtTimKiemSP.setLineColor(new java.awt.Color(102, 102, 102));
        txtTimKiemSP.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtTimKiemSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSPKeyReleased(evt);
            }
        });

        cboManHinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboManHinh.setLabeText("Màn Hình");
        cboManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboManHinhActionPerformed(evt);
            }
        });

        cboCPU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboCPU.setLabeText("CPU");
        cboCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCPUActionPerformed(evt);
            }
        });

        cboPin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboPin.setLabeText("Pin");
        cboPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPinActionPerformed(evt);
            }
        });

        cboNSX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboNSX.setLabeText("Nsx");
        cboNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNSXActionPerformed(evt);
            }
        });

        jScrollPane5.setBorder(null);

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên Sản Phẩm", "NSX", "Màn Hình", "CPU", "Pin", "Số Lượng", "Giá"
            }
        ));
        jScrollPane5.setViewportView(tblSP);
        if (tblSP.getColumnModel().getColumnCount() > 0) {
            tblSP.getColumnModel().getColumn(0).setMinWidth(20);
            tblSP.getColumnModel().getColumn(0).setMaxWidth(40);
            tblSP.getColumnModel().getColumn(1).setMinWidth(150);
            tblSP.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(cboNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboPin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addComponent(jScrollPane5)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboPin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboManHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboNSX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quét QR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel5.setOpaque(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bán Hàng");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel7.setOpaque(false);

        txtMaKH.setEnabled(false);
        txtMaKH.setLabelText("Mã Khách Hàng");

        txtGetTenKH.setLabelText("Tên Khách Hàng");
        txtGetTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGetTenKHActionPerformed(evt);
            }
        });

        buttonCustom1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setText("Chọn");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGetTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGetTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel8.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Mã Hóa Đơn:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Ngày Tạo:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Ngày TT:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Mã Nhân Viên:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Tên KH:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Tổng Tiền:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Phiếu Giảm Giá:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("HT Thanh Toán:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Tiền Khách Đưa:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Tiền Khách CK:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Tiền Thừa:");

        buttonCustom3.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom3.setText("Hủy HĐ");
        buttonCustom3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonCustom8.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom8.setText("Thanh Toán HĐ");
        buttonCustom8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 0, 0));
        jLabel13.setText("Tổng Tiền: 0đ");

        cboHTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHTTActionPerformed(evt);
            }
        });

        cboPgg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboPgg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPggActionPerformed(evt);
            }
        });

        txtTongTien.setText("jLabel17");

        txtSetTenKH.setText("jLabel18");

        txtMaNV.setText("jLabel19");

        txtNgayThanhToan.setText("jLabel20");

        txtNgayTao.setText("jLabel21");

        txtMaHD.setText("jLabel22");

        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        txtTienKhachCK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachCKKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(buttonCustom3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSetTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(44, 44, 44)
                        .addComponent(txtTienThua))
                    .addComponent(jLabel13)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTienKhachCK)
                            .addComponent(txtTienKhachDua)
                            .addComponent(cboHTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(cboPgg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNgayTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNgayThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMaNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSetTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cboPgg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cboHTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        materialTabbed2.addTab("Tại Quầy", jPanel4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 674, Short.MAX_VALUE)
        );

        materialTabbed2.addTab("Đặt Hàng", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(materialTabbed2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(materialTabbed2, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNSXActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboNSXActionPerformed

    private void cboManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboManHinhActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboManHinhActionPerformed

    private void cboCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCPUActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboCPUActionPerformed

    private void cboPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPinActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboPinActionPerformed

    private void txtTimKiemSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSPKeyReleased
        if (txtTimKiemSP.getText().trim().equals("")) {
            showDataTableSP(bhService.getSP());
        }
        showDataTableSP(bhService.search(txtTimKiemSP.getText()));
    }//GEN-LAST:event_txtTimKiemSPKeyReleased

    private void txtGetTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGetTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGetTenKHActionPerformed

    private void showDataDeleteTableGioHang(String TenDsp) {
        List<ChiTietSanPhamViewModel> tempList = new ArrayList<>();

        for (ChiTietSanPhamViewModel gioHangSP : gioHangList) {
            if (!gioHangSP.getTenDsp().equals(TenDsp)) {
                tempList.add(gioHangSP);
            }
        }

        gioHangList = tempList;
        updateDataTableGioHang();
    }
    private void buttonCustom7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom7ActionPerformed
        int index = tblGioHang.getSelectedRow();
        if (index != -1) {
            String TenDsp = (String) tblGioHang.getValueAt(index, 1);
            bhService.removeGioHang(TenDsp);
            showDataDeleteTableGioHang(TenDsp);
            showDataTableSP(bhService.getSP());
            txtTongTien.setText("0");
        }
    }//GEN-LAST:event_buttonCustom7ActionPerformed

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonCustom2ActionPerformed

    private void cboPggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPggActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboPggActionPerformed

    private void cboHTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHTTActionPerformed
        // TODO add your handling code here:     
        if (cboHTT.getSelectedIndex() == 1) {
            txtTienKhachCK.setText("0");
            txtTienKhachDua.setText("");
        }
        if (cboHTT.getSelectedIndex() == 2) {
            txtTienKhachDua.setText("0");
            txtTienKhachCK.setText("");
        } else {
            txtTienKhachCK.setText("");
            txtTienKhachDua.setText("");
        }

    }//GEN-LAST:event_cboHTTActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // TODO add your handling code here:

        if (!txtTienKhachDua.getText().trim().isEmpty()) {
            String text = txtTienKhachDua.getText();
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    txtTienKhachDua.setText(decimalFormat.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }
        }

        String tenPGG = (String) cboPgg.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float tienThua = 0;
        float tongTien = 0;
        float tienMat = 0;
        float tienCK = 0;
        if (calculateTotalAmountFromGioHang().compareTo(BigDecimal.ZERO) != 0) {
            tongTien = Float.parseFloat(txtTongTien.getText().trim().replace(",", ""));
        }
        if (!txtTienKhachDua.getText().isEmpty()) {
            tienMat = Float.parseFloat(txtTienKhachDua.getText().trim().replace(",", ""));
        }
        if (cboHTT.getSelectedIndex() == 1) {
            if (!txtTienKhachDua.getText().isEmpty()) {
                if (phanTramGiam == 0) {
                    tienThua = tienMat - tongTien;
                } else {
                    tienThua = tienMat - (tongTien * ((100 - phanTramGiam) / 100));
                }

                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }
        if (cboHTT.getSelectedIndex() == 3) {
            if (!txtTienKhachDua.getText().isEmpty() && !txtTienKhachCK.getText().isEmpty()) {
                tienCK = Float.parseFloat(txtTienKhachCK.getText().trim().replace(",", ""));
                if (phanTramGiam == 0) {
                    tienThua = (tienMat + tienCK) - tongTien;
                } else {
                    tienThua = tienMat - (tongTien * ((100 - phanTramGiam) / 100));
                }
                tienThua = (tienMat + tienCK) - (tongTien * ((100 - phanTramGiam) / 100));
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }
        if (phanTramGiam == 0) {
            jLabel13.setText("Tổng tiền: " + decimalFormat.format(tongTien));
        } else {
            jLabel13.setText("Tổng tiền: " + decimalFormat.format(tongTien * ((100 - phanTramGiam)) / 100));
        }
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachCKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachCKKeyReleased
        // TODO add your handling code here:
        if (!txtTienKhachCK.getText().trim().isEmpty()) {
            String text = txtTienKhachCK.getText();
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    txtTienKhachCK.setText(decimalFormat.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }
        }
        String tenPGG = (String) cboPgg.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float tienThua = 0;
        float tongTien = 0;
        float tienMat = 0;
        float tienCK = 0;
        if (calculateTotalAmountFromGioHang().compareTo(BigDecimal.ZERO) != 0) {
            tongTien = Float.parseFloat(txtTongTien.getText().trim().replace(",", ""));
        }
        if (!txtTienKhachDua.getText().isEmpty()) {
            tienCK = Float.parseFloat(txtTienKhachCK.getText().trim().replace(",", ""));
        }
        if (cboHTT.getSelectedIndex() == 2) {
            if (!txtTienKhachCK.getText().isEmpty()) {
                if (phanTramGiam == 0) {
                    tienThua = tienCK - tongTien;
                } else {
                    tienThua = tienCK - (tongTien * ((100 - phanTramGiam) / 100));
                }
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }
        if (cboHTT.getSelectedIndex() == 3) {

            if (!txtTienKhachDua.getText().isEmpty() && !txtTienKhachCK.getText().isEmpty()) {
                tienMat = Float.parseFloat(txtTienKhachDua.getText().trim().replace(",", ""));
                if (phanTramGiam == 0) {
                    tienThua = (tienMat + tienCK) - tongTien;
                } else {
                    tienThua = (tienMat + tienCK) - (tongTien * ((100 - phanTramGiam) / 100));
                }
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }
        if (phanTramGiam == 0) {
            jLabel13.setText("Tổng tiền: " + decimalFormat.format(tongTien));
        } else {
            jLabel13.setText("Tổng tiền: " + decimalFormat.format(tongTien * ((100 - phanTramGiam)) / 100));
        }
    }//GEN-LAST:event_txtTienKhachCKKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom2;
    private mobileworld.swing.ButtonCustom buttonCustom3;
    private mobileworld.swing.ButtonCustom buttonCustom4;
    private mobileworld.swing.ButtonCustom buttonCustom5;
    private mobileworld.swing.ButtonCustom buttonCustom7;
    private mobileworld.swing.ButtonCustom buttonCustom8;
    private mobileworld.swing.Combobox cboCPU;
    private javax.swing.JComboBox<String> cboHTT;
    private mobileworld.swing.Combobox cboManHinh;
    private mobileworld.swing.Combobox cboNSX;
    private javax.swing.JComboBox<String> cboPgg;
    private mobileworld.swing.Combobox cboPin;
    private mobileworld.swing.Combobox combobox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private mobileworld.swing.MaterialTabbed materialTabbed2;
    private mobileworld.swing.Table tblGioHang;
    private mobileworld.swing.Table tblHoaDon;
    private mobileworld.swing.Table tblSP;
    private mobileworld.swing.TextField textField1;
    private mobileworld.swing.TextField txtGetTenKH;
    private javax.swing.JLabel txtMaHD;
    private mobileworld.swing.TextField txtMaKH;
    private javax.swing.JLabel txtMaNV;
    private javax.swing.JLabel txtNgayTao;
    private javax.swing.JLabel txtNgayThanhToan;
    private javax.swing.JLabel txtSetTenKH;
    private javax.swing.JTextField txtTienKhachCK;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private mobileworld.swing.TextField txtTimKiemSP;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables

}
