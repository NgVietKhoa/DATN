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
import javax.swing.table.DefaultTableModel;
import mobileworld.dialog.SelectProductImel;
import mobileworld.model.CPU;
import mobileworld.model.ManHinh;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.Pin;
import mobileworld.service.BanHangService.BanHangService;
import mobileworld.service.ChiTietSanPhamService.CpuService;
import mobileworld.service.ChiTietSanPhamService.ManHinhService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
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
        txtTenKhachHang.setText("Khách Bán Lẻ");
        cbbPin = (DefaultComboBoxModel) cboPin.getModel();
        cbbManHinh = (DefaultComboBoxModel) cboManHinh.getModel();
        cbbCpu = (DefaultComboBoxModel) cboCPU.getModel();
        cbbNsx = (DefaultComboBoxModel) cboNSX.getModel();
        setDataCboPin(pinService.getAll());
        setDataCboManHinh(mhService.getAll());
        setDataCboCpu(cpuService.getAll());
        setDataCboNsx(NsxService.getAll());
        setDataCboGia();

        HoaDonViewModel newestHoaDon = bhService.getNewestHoaDon();
        if (newestHoaDon != null) {
            setTxtFieldsFromSelectedHoaDon(newestHoaDon);
        }

        tblSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblSP.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    SelectProductImel imel = new SelectProductImel();
                    imel.setVisible(true);
                }
            }
        });

        showDataTableHoaDon(bhService.getHD());
        showDataTableSP(bhService.getSP());
    }

    private void setTxtFieldsFromSelectedHoaDon(HoaDonViewModel selectedHoaDon) {
        Date createAt = selectedHoaDon.getCreateAt();
        String createBy = selectedHoaDon.getCreateBy();
        String tenKH = selectedHoaDon.getTenKH();
        String maHD = selectedHoaDon.getIdHD();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(createAt);

        // Đặt giá trị cho các trường txt
        txtNgayTao.setText(dateString);
        txtNgayThanhToan.setText(dateString);
        txtNhanVien.setText(createBy);
        txtMaHD.setText(maHD);
        txtTenKH.setText(tenKH);
    }

    public List<ChiTietSanPhamViewModel> getSelectedProductListImel() {
        List<ChiTietSanPhamViewModel> result = new ArrayList<>(); // Khai báo biến result

        int index = tblSP.getSelectedRow();
        if (index >= 0) {
            String idSP = (String) tblSP.getValueAt(index, 1);

            // Gọi hàm getSP với idSP đã lấy
            Object objResult = bhService.getGioHang(idSP);

            // Kiểm tra nếu kết quả là đúng kiểu dữ liệu trước khi thực hiện
            if (objResult instanceof List) {
                for (Object obj : (List<?>) objResult) {
                    if (obj instanceof ChiTietSanPhamViewModel) {
                        result.add((ChiTietSanPhamViewModel) obj);
                    }
                }
            }

            // Hiển thị danh sách sản phẩm trong bảng tblGioHang
            showDataTableGioHang(result);
        }
        return result;
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

    private void showDataTableGioHang(List<ChiTietSanPhamViewModel> listSP) {
        tblModelGH.setRowCount(0);
        int stt = 0;
        for (ChiTietSanPhamViewModel sp : listSP) {
            stt++;
            BigDecimal giaBan = sp.getGiaBan(); // Giá bán kiểu BigDecimal
            String formattedGiaBan = decimalFormat.format(giaBan); // Định dạng giá bán
            int soLuong = sp.getSoLuong(); // Số lượng kiểu int
            BigDecimal thanhTien = giaBan.multiply(BigDecimal.valueOf(soLuong));
            tblModelGH.addRow(new Object[]{
                stt, sp.getTenDsp(), sp.getLoaiManHinh(), sp.getCpu(), sp.getDungLuongRam(), sp.getDungLuongBoNho(),
                sp.getDungLuongPin(), sp.getTenMau(), sp.getCameraTruoc(), sp.getCameraSau(), soLuong, formattedGiaBan, thanhTien
            });
        }
    }

    private void setDataCboGia() {
        cboGia.addItem("Giá Tăng Dần");
        cboGia.addItem("Giá Giảm Dần");
        cboGia.setSelectedItem(null);
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

        // Kiểm tra xem giá trị được chọn từ combobox có null không
        boolean sapXepGiaTangDan = "Giá Tăng Dần".equals(cboGia.getSelectedItem());

        BoLocCtsp = bhService.LocSP(nsx, pin, manHinh, cpu, sapXepGiaTangDan);
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
        buttonCustom6 = new mobileworld.swing.ButtonCustom();
        buttonCustom7 = new mobileworld.swing.ButtonCustom();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGioHang = new mobileworld.swing.Table();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiemSP = new mobileworld.swing.TextField();
        cboManHinh = new mobileworld.swing.Combobox();
        cboCPU = new mobileworld.swing.Combobox();
        cboPin = new mobileworld.swing.Combobox();
        cboNSX = new mobileworld.swing.Combobox();
        cboGia = new mobileworld.swing.Combobox();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSP = new mobileworld.swing.Table();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtMaKH = new mobileworld.swing.TextField();
        txtTenKhachHang = new mobileworld.swing.TextField();
        buttonCustom8 = new mobileworld.swing.ButtonCustom();
        buttonCustom9 = new mobileworld.swing.ButtonCustom();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNgayThanhToan = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNhanVien = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        buttonCustom10 = new mobileworld.swing.ButtonCustom();
        buttonCustom11 = new mobileworld.swing.ButtonCustom();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel1.setOpaque(false);

        buttonCustom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-delete-document-24 (1).png"))); // NOI18N

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
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

        buttonCustom6.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom6.setText("Chọn Tất Cả");
        buttonCustom6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonCustom7.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom7.setText("Xóa");
        buttonCustom7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPane4.setBorder(null);

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên SP", "Màn Hình", "CPU", "Ram", "Rom", "Pin", "Màu Sắc", "CamTruoc", "CamSau", "SL", "Giá", "Tổng Tiền"
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(636, Short.MAX_VALUE)
                .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane4)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        cboGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboGia.setLabeText("Giá");
        cboGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiaActionPerformed(evt);
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
                .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(cboNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(cboCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(cboPin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboGia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addComponent(jScrollPane5)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboPin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboManHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboNSX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel4.setOpaque(false);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel6.setOpaque(false);

        txtMaKH.setEnabled(false);
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMaKH.setLabelText("Mã Khách Hàng");
        txtMaKH.setLineColor(new java.awt.Color(102, 102, 102));
        txtMaKH.setSelectionColor(new java.awt.Color(102, 102, 102));

        txtTenKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTenKhachHang.setLabelText("Tên Khách Hàng");
        txtTenKhachHang.setLineColor(new java.awt.Color(102, 102, 102));
        txtTenKhachHang.setSelectionColor(new java.awt.Color(102, 102, 102));

        buttonCustom8.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom8.setText("Chọn Tất Cả");
        buttonCustom8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonCustom9.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom9.setText("Chọn");
        buttonCustom9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(58, Short.MAX_VALUE)))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel7.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Mã Hóa Đơn:");

        txtMaHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMaHD.setText("HD1");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Ngày Tạo:");

        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNgayTao.setText("1/17/2024");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Ngày Thanh Toán:");

        txtNgayThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNgayThanhToan.setText("1/17/2024");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Mã Nhân Viên:");

        txtNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNhanVien.setText("NV01");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Tên Khách Hàng:");

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTenKH.setText("Khách Lẻ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("Tổng Tiền:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 0, 0));
        jLabel15.setText("0đ");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setText("Phiếu Giảm Giá:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("HT Thanh Toán:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(153, 153, 153));
        jLabel18.setText("Tiền Khách Đưa:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Tiền Khách CK:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Tiền Thừa:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 0, 0));
        jLabel21.setText("0đ");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 0, 0));
        jLabel22.setText("Tổng: 0đ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonCustom10.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom10.setText("Hủy HĐ");
        buttonCustom10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonCustom11.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom11.setText("Thanh Toán");
        buttonCustom11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(47, 47, 47)
                                .addComponent(txtMaHD))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNhanVien)
                                    .addComponent(txtNgayThanhToan)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(63, 63, 63)
                                .addComponent(txtNgayTao))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel21))
                            .addComponent(jLabel22))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCustom11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNgayTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonCustom10, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(buttonCustom11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
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

    private void cboGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiaActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboGiaActionPerformed

    private void txtTimKiemSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSPKeyReleased
        if (txtTimKiemSP.getText().trim().equals("")) {
            showDataTableSP(bhService.getSP());
        }
        showDataTableSP(bhService.search(txtTimKiemSP.getText()));
    }//GEN-LAST:event_txtTimKiemSPKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom buttonCustom10;
    private mobileworld.swing.ButtonCustom buttonCustom11;
    private mobileworld.swing.ButtonCustom buttonCustom2;
    private mobileworld.swing.ButtonCustom buttonCustom4;
    private mobileworld.swing.ButtonCustom buttonCustom5;
    private mobileworld.swing.ButtonCustom buttonCustom6;
    private mobileworld.swing.ButtonCustom buttonCustom7;
    private mobileworld.swing.ButtonCustom buttonCustom8;
    private mobileworld.swing.ButtonCustom buttonCustom9;
    private mobileworld.swing.Combobox cboCPU;
    private mobileworld.swing.Combobox cboGia;
    private mobileworld.swing.Combobox cboManHinh;
    private mobileworld.swing.Combobox cboNSX;
    private mobileworld.swing.Combobox cboPin;
    private mobileworld.swing.Combobox combobox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private mobileworld.swing.Table tblGioHang;
    private mobileworld.swing.Table tblHoaDon;
    private mobileworld.swing.Table tblSP;
    private mobileworld.swing.TextField textField1;
    private javax.swing.JLabel txtMaHD;
    private mobileworld.swing.TextField txtMaKH;
    private javax.swing.JLabel txtNgayTao;
    private javax.swing.JLabel txtNgayThanhToan;
    private javax.swing.JLabel txtNhanVien;
    private javax.swing.JLabel txtTenKH;
    private mobileworld.swing.TextField txtTenKhachHang;
    private mobileworld.swing.TextField txtTimKiemSP;
    // End of variables declaration//GEN-END:variables
}
