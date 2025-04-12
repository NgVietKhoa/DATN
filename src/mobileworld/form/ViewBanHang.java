package mobileworld.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mobileworld.dialog.DeselectProductSP;
import mobileworld.dialog.ReadQRCodeAddSP;
import mobileworld.dialog.SelectProductSP;
import mobileworld.dialog.ThongTinKhachHangDialog;
import mobileworld.dialog.ThongTinNhanVienDialog;
import mobileworld.main.SessionStorage;
import mobileworld.model.CPU;
import mobileworld.model.HinhThucThanhToanEntity;
import mobileworld.model.HoaDon;
import mobileworld.model.HoaDonChiTietEntity;
import mobileworld.model.ManHinh;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.PhieuGiamGia;
import mobileworld.model.PhuongThucThanhToan;
import mobileworld.model.Pin;
import mobileworld.service.BanHangService.BanHangService;
import mobileworld.service.BanHangService.KHInBanHangService;
import mobileworld.service.ChiTietSanPhamService.CpuService;
import mobileworld.service.ChiTietSanPhamService.ManHinhService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
import mobileworld.service.PhieuGiamGiaService.PhieuGiamGiaService;
import mobileworld.thread.ThreadTinhTrang;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import mobileworld.viewModel.ChiTietSanPhamViewModel;
import mobileworld.viewModel.LichSuHDModel;

public class ViewBanHang extends javax.swing.JPanel {

    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    BanHangService bhService = new BanHangService();
    DefaultTableModel tblModelHD = new DefaultTableModel();
    DefaultTableModel tblModelSP = new DefaultTableModel();
    DefaultTableModel tblModelGH = new DefaultTableModel();
    DefaultComboBoxModel cbbPin = new DefaultComboBoxModel();
    PinService pinService = new PinService();
    DefaultComboBoxModel cbbManHinh = new DefaultComboBoxModel();
    ManHinhService mhService = new ManHinhService();
    DefaultComboBoxModel cbbCpu = new DefaultComboBoxModel();
    CpuService cpuService = new CpuService();
    DefaultComboBoxModel cbbNsx = new DefaultComboBoxModel();
    NhaSanXuatService NsxService = new NhaSanXuatService();
    List<ChiTietSanPhamViewModel> BoLocCtsp = new ArrayList<>();
    PhieuGiamGiaService pggService = new PhieuGiamGiaService();
    DefaultComboBoxModel cbbPgg = new DefaultComboBoxModel();
    DefaultComboBoxModel cbbPggĐH = new DefaultComboBoxModel();
    List<ChiTietSanPhamViewModel> gioHangList = new ArrayList<>();
    List<HoaDonViewModel> listHDM = new ArrayList<>();
    KHInBanHangService khService = new KHInBanHangService();

    List<PhieuGiamGia> dsPGG = new ArrayList<>();
    protected ThreadTinhTrang threadTinhTrang;
    JLabel getMaNV = new JLabel();
    private List<ChiTietSanPhamViewModel> selectedImels = new ArrayList<>();
    private List<ChiTietSanPhamViewModel> selectedCTSPs = new ArrayList<>();
    private List<ChiTietSanPhamViewModel> selectedImelsDelete = new ArrayList<>();

    public ViewBanHang() {
        initComponents();
        setOpaque(false);
        listHDM = bhService.getHD();
        tblModelHD = (DefaultTableModel) tblHoaDon.getModel();
        tblModelSP = (DefaultTableModel) tblSP.getModel();
        tblModelGH = (DefaultTableModel) tblGioHang.getModel();
        tblSP.setDefaultEditor(Object.class, null);
        tblGioHang.setDefaultEditor(Object.class, null);
        tblHoaDon.setDefaultEditor(Object.class, null);
        //cbo
        cbbPin = (DefaultComboBoxModel) cboPin.getModel();
        cbbManHinh = (DefaultComboBoxModel) cboManHinh.getModel();
        cbbCpu = (DefaultComboBoxModel) cboCPU.getModel();
        cbbNsx = (DefaultComboBoxModel) cboNSX.getModel();
        cbbPgg = (DefaultComboBoxModel) cboPgg.getModel();
        cbbPggĐH = (DefaultComboBoxModel) cboPggDH.getModel();
        setDataCboPin(pinService.getAll());
        setDataCboManHinh(mhService.getAll());
        setDataCboCpu(cpuService.getAll());
        setDataCboNsx(NsxService.getAll());
        setDataCboHTTT();

        tblSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblSP.getSelectedRow();
                int indexHD = tblHoaDon.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    String idDsp = (String) tblSP.getValueAt(index, 1);
                    // Loại bỏ dấu phẩy trước khi chuyển đổi thành BigDecimal
                    String giaBanStr = tblSP.getValueAt(index, 7).toString().replace(",", "");
                    BigDecimal giaBan = new BigDecimal(giaBanStr);

                    if (indexHD < 0) {
                        JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Hóa Đơn Để Thêm Sản Phẩm");
                        return;
                    }

                    SelectProductSP productImel = new SelectProductSP(idDsp, giaBan, ViewBanHang.this);
                    productImel.setVisible(true);
                }
            }
        });

        tblGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblGioHang.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    Object idCtspObject = tblGioHang.getValueAt(index, 1);
                    String giaBanStr = tblGioHang.getValueAt(index, 9).toString().replace(",", "");
                    BigDecimal giaBan = new BigDecimal(giaBanStr);
                    if (idCtspObject != null) {
                        String idCtsp = idCtspObject.toString();

                        DeselectProductSP deselectProductSP = new DeselectProductSP(idCtsp, giaBan, ViewBanHang.this);
                        deselectProductSP.setVisible(true);
                    }
                }
            }
        });

        showDataTableHoaDon(bhService.getHD());
        showDataTableSP(bhService.getSP());
        System.out.println();

        threadTinhTrang = new ThreadTinhTrang(dsPGG, pggService);
        threadTinhTrang.start();
        try {
            threadTinhTrang.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDataCboPhieuGG(pggService.getAll());

        txtGetTenKH.setText(khService.getTen());
        txtMaKH.setText(khService.getID());
    }

    private void setDataCboPhieuGG(List<PhieuGiamGia> setPgg) {
        cbbPgg.removeAllElements();
        cbbPggĐH.removeAllElements();
        for (PhieuGiamGia pgg : setPgg) {
            if (pgg.getTrangThai() == 1 && pgg.getSoLuongDung() > 0) {
                cbbPggĐH.addElement(pgg.getTenGiamGia());
                cbbPgg.addElement(pgg.getTenGiamGia());
            }
        }
        cbbPggĐH.setSelectedItem(null);
        cbbPgg.setSelectedItem(null);
    }

    private void setDataCboHTTT() {
        String[] paymentMethods = {"", "Tiền mặt", "Chuyển khoản", "Cả 2 hình thức"};
        for (String method : paymentMethods) {
            cboHTT.addItem(method);
            cboHTTTĐH.addItem(method);
        }
        cboHTT.setSelectedIndex(0);
        cboHTTTĐH.setSelectedIndex(0);
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

    private int calculateTotalQuantityFromGioHang() {
        int totalQuantity = 0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            Object quantityObject = tblGioHang.getValueAt(i, 8);
            if (quantityObject != null) {
                String quantityString = quantityObject.toString();
                if (!quantityString.isEmpty()) {
                    totalQuantity += Integer.parseInt(quantityString);
                }
            }
        }
        return totalQuantity;
    }

    public void getImelDelete(List<String> imels) {
        for (String imel : imels) {
            // Tạo một đối tượng ChiTietSanPhamViewModel từ ID IMEI và thêm vào gioHangList
            ChiTietSanPhamViewModel sp = new ChiTietSanPhamViewModel();
            sp.setImel(imel); // Thiết lập ID IMEI cho sản phẩm
            selectedImelsDelete.add(sp); // Thêm sản phẩm vào gioHangList
        }
        System.out.println("imel:" + selectedImelsDelete.size());
    }

    public void getThongTinKH(String ten, String ma) {
        txtGetTenKH.setText(ten);
        txtMaKH.setText(ma);
        txtSetTenKH.setText(ten);
    }

    public void getThongTinKHĐH(String ten, String sdt, String diaChiKH) {
        txtGetTenKHDH.setText(ten);
        txtSDTKH.setText(sdt);
        txtDiaChiKH.setText(diaChiKH);
    }

    public void getThongTinNv(String tenNv, String sdtNv, String maNV) {
        txtTenNhanVien.setText(tenNv);
        txtSDTNV.setText(sdtNv);
        getMaNV.setText(maNV);
        System.out.println(maNV);
    }

    public void getAddThongTinKH(String ten, String ma) {
        txtGetTenKH.setText(ten);
        txtMaKH.setText(ma);
        txtSetTenKH.setText(ten);
    }

    public void getAddThongTinKHĐH(String ten, String sdt, String diaChi) {
        txtGetTenKHDH.setText(ten);
        txtSDTKH.setText(sdt);
        txtDiaChiKH.setText(diaChi);
    }

    private void showDataTableHoaDon(List<HoaDonViewModel> listHD) {
        if (listHD == null || listHD.isEmpty()) {
            return;
        }

        tblModelHD.setRowCount(0);

        int stt = 0;
        for (HoaDonViewModel hdvm : listHD) {
            stt++;
            String trangThai;
            trangThai = switch (hdvm.getTrangthai()) {
                case 0 ->
                    "Chờ Thanh Toán";
                case 1 ->
                    "Đã thanh toán";
                case 2 ->
                    "Đang Giao";
                case 3 ->
                    "Chờ Giao";
                default ->
                    "Hủy Giao";
            };
            tblModelHD.addRow(new Object[]{
                stt, hdvm.getIdHD(), hdvm.getCreateBy(), hdvm.getTenKH(), hdvm.getSdtKH(), hdvm.getTongSP(), trangThai
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

    public void getImelAndCTSPQR(String imel) {
        List<String> idImels = bhService.getIdImelFromImelsAdd(imel);
        for (String idImel : idImels) {
            // Tạo một đối tượng ChiTietSanPhamViewModel từ ID IMEI và thêm vào selectedImels
            ChiTietSanPhamViewModel sp = new ChiTietSanPhamViewModel();
            sp.setIdimel(idImel); // Thiết lập ID IMEI cho sản phẩm
            selectedImels.add(sp); // Thêm sản phẩm vào selectedImels
        }

        List<String> idCTSPs = bhService.getCTSPFromImelsAdd(imel);
        for (String idCTSP : idCTSPs) {
            // Tạo một đối tượng ChiTietSanPhamViewModel từ ID IMEI và thêm vào selectedImels
            ChiTietSanPhamViewModel sp = new ChiTietSanPhamViewModel();
            sp.setId(idCTSP); // Thiết lập ID IMEI cho sản phẩm
            selectedCTSPs.add(sp); // Thêm sản phẩm vào selectedImels
        }

        System.out.println("getImelAndCTSPQR:" + selectedImels.size());
        System.out.println("getImelAndCTSPQR:" + selectedCTSPs.size());
    }

    public void updateGioHangWithImel(String imel, int totalQuantity) {

        List<ChiTietSanPhamViewModel> gioHang = bhService.getGioHang(imel);
        showDataTableGioHang(gioHang, totalQuantity);
        showDataTableSP(bhService.getSP());

        // Cập nhật tổng số lượng sản phẩm
        int totalQuantityFromGioHang = calculateTotalQuantityFromGioHang();

        // Cập nhật lại tổng tiền
        BigDecimal totalAmount = calculateTotalAmountFromGioHang();
        txtTongTien.setText(decimalFormat.format(totalAmount));
        txtTongTienDH.setText(decimalFormat.format(totalAmount));

        // Cập nhật cột Tổng sản phẩm trong bảng tblHoaDon
        updateTotalQuantityInHoaDon(totalQuantityFromGioHang);
    }

    public void deleteGioHangWithImel(List<String> imels, int totalQuantity) {
        // Xóa sản phẩm khỏi giỏ hàng
        List<ChiTietSanPhamViewModel> gioHang = bhService.deleteGioHang(imels);

        // Lấy danh sách ID Imel từ danh sách Imels
        List<String> idImels = bhService.getIdImelFromImels(imels);
        // Xóa các ID Imel đã được loại bỏ khỏi danh sách selectedImels
        for (String idImel : idImels) {
            for (ChiTietSanPhamViewModel sp : selectedImels) {
                if (sp.getIdimel().equals(idImel)) {
                    selectedImels.remove(sp);
                    break;
                }
            }
        }

        // Lấy danh sách ID CTSP từ danh sách Imels
        List<String> idCTSPs = bhService.getCTSPFromImels(imels);
        // Xóa các ID CTSP đã được loại bỏ khỏi danh sách selectedCTSPs
        for (String idCTSP : idCTSPs) {
            for (ChiTietSanPhamViewModel sp : selectedCTSPs) {
                if (sp.getId().equals(idCTSP)) {
                    selectedCTSPs.remove(sp);
                    break;
                }
            }
        }

        showDataDeleteTableGioHang(gioHang, totalQuantity);
        showDataTableSP(bhService.getSP());

        // Cập nhật tổng số lượng sản phẩm
        int totalQuantityFromGioHang = calculateTotalQuantityFromGioHang();
        // Cập nhật lại tổng tiền
        BigDecimal totalAmount = calculateTotalAmountFromGioHang();
        txtTongTien.setText(decimalFormat.format(totalAmount));
        txtTongTienDH.setText(decimalFormat.format(totalAmount));
        // Cập nhật cột Tổng sản phẩm trong bảng tblHoaDon
        updateTotalQuantityInHoaDon(totalQuantityFromGioHang);
    }

    private void updateTotalQuantityInHoaDon(int totalQuantity) {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            String idHD = tblModelHD.getValueAt(selectedRow, 1).toString(); // Lấy ID hóa đơn từ bảng
            int tongSP = Integer.parseInt(tblModelHD.getValueAt(selectedRow, 5).toString()); // Lấy tổng số lượng sản phẩm từ bảng
            if (idHD != null && !idHD.isEmpty() && tongSP != totalQuantity) {
                tblModelHD.setValueAt(totalQuantity, selectedRow, 5); // Cập nhật giá trị tại chỉ mục của hàng đó
            }
        }
    }

    private void showDataTableGioHang(List<ChiTietSanPhamViewModel> listSP, int totalQuantity) {
        for (ChiTietSanPhamViewModel sp : listSP) {
            boolean existed = false;
            for (ChiTietSanPhamViewModel gioHangSP : gioHangList) {
                // Sửa điều kiện so sánh trong if statement, thêm dấu đóng ngoặc vào phần so sánh giữa các biểu thức
                if (gioHangSP.getTenDsp().equals(sp.getTenDsp()) && gioHangSP.getGiaBan().equals(sp.getGiaBan())) {
                    // Sản phẩm đã tồn tại trong giỏ hàng, chỉ cập nhật số lượng
                    gioHangSP.setSoLuong(gioHangSP.getSoLuong() + 1); // Cập nhật số lượng
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
        }

        // Cập nhật dữ liệu trên bảng
        updateDataTableGioHang();
    }

    private void deleteTableGioHang(List<ChiTietSanPhamViewModel> listSP) {
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

    public HoaDon getFormData() {
        String idKH = txtMaKH.getText();
        String tongTienText = txtTongTien.getText();
        String tongTienSauKhiGiamText = jLabel14.getText();
        String idHD = txtMaHD.getText();
        String idNv = txtMaNV.getText();

        if (tongTienText.isEmpty() || tongTienSauKhiGiamText.isEmpty()) {
            return null;
        }

        // Loại bỏ dấu phẩy khỏi chuỗi tổng tiền
        String tongTienWithoutComma = tongTienText.replaceAll(",", "");
        String tongTienSauKhiGiamWithoutComma = tongTienSauKhiGiamText.replaceAll(",", "");

        // Chuyển đổi chuỗi thành đối tượng BigDecimal
        BigDecimal tongTienBD = new BigDecimal(tongTienWithoutComma);
        BigDecimal tongTienSauKhiGiamBD = new BigDecimal(tongTienSauKhiGiamWithoutComma);

        // Tiếp tục với việc tạo đối tượng HoaDon
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();

        HoaDon hd = new HoaDon(idHD, idKH, idNv, ngayThucTe, ngayThucTe, tongTienSauKhiGiamBD, tongTienBD, idKH, idKH, idKH, ngayThucTe, nhanVien, 1);
        return hd;
    }

    public void getImel(List<String> idImels) {
        for (String idImel : idImels) {
            // Tạo một đối tượng ChiTietSanPhamViewModel từ ID IMEI và thêm vào selectedImels
            ChiTietSanPhamViewModel sp = new ChiTietSanPhamViewModel();
            sp.setIdimel(idImel); // Thiết lập ID IMEI cho sản phẩm
            selectedImels.add(sp); // Thêm sản phẩm vào selectedImels
        }
        System.out.println("id imel" + selectedImels.size());
    }

// Phương thức getCTSP được gọi khi nhận được danh sách ID CTSP
    public void getCTSP(List<String> idCTSPs) {
        for (String idCTSP : idCTSPs) {
            // Tạo một đối tượng ChiTietSanPhamViewModel từ ID CTSP và thêm vào selectedCTSPs
            ChiTietSanPhamViewModel sp = new ChiTietSanPhamViewModel();
            sp.setId(idCTSP); // Thiết lập ID CTSP cho sản phẩm
            selectedCTSPs.add(sp); // Thêm sản phẩm vào selectedCTSPs
        }
        System.out.println("id dsp" + selectedCTSPs.size());
    }

    public List<HoaDonChiTietEntity> getFormDataHDCT() {
        int index = tblGioHang.getRowCount(); // Assuming tblGioHang is accessible
        String idHD = txtMaHD.getText(); // Assuming txtMaHD is accessible
        String nhanVien = SessionStorage.getInstance().getUsername(); // Assuming SessionStorage is accessible
        LocalDateTime ngayThucTe = LocalDateTime.now();
        String giaBanStr = (String) tblGioHang.getValueAt(index - 1, 9);

        // Remove commas from giaBanStr
        String giaBanWithoutCommas = giaBanStr.replaceAll(",", "");

        // Parse giaBanWithoutCommas to a BigDecimal
        BigDecimal giaBan = new BigDecimal(giaBanWithoutCommas);

        List<HoaDonChiTietEntity> hdctList = new ArrayList<>();

        // Lặp qua danh sách ID IMEI và ID CTSP để tạo các đối tượng HoaDonChiTietEntity
        int maxSize = Math.max(selectedImels.size(), selectedCTSPs.size());
        for (int i = 0; i < maxSize; i++) {
            String idImel = (i < selectedImels.size()) ? selectedImels.get(i).getIdimel() : null;
            String idCTSP = (i < selectedCTSPs.size()) ? selectedCTSPs.get(i).getId() : null;
            HoaDonChiTietEntity hdct = new HoaDonChiTietEntity(
                    idImel, // Lấy ID IMEI từ danh sách selectedImels
                    idCTSP, // Lấy ID CTSP từ danh sách selectedCTSPs
                    idHD, giaBan, 1, ngayThucTe, nhanVien, ngayThucTe, nhanVien
            );
            hdctList.add(hdct);
        }

        return hdctList;
    }

    public PhuongThucThanhToan getFormDataPTTT() {
        String tenKieuTT = (String) cboHTT.getSelectedItem();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();

        PhuongThucThanhToan pttt = new PhuongThucThanhToan(tenKieuTT, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return pttt;
    }

    public HinhThucThanhToanEntity getFormDataHTTT() {
        String idHD = txtMaHD.getText();

        // Tiếp tục xử lý khi có idPTTT hợp lệ
        String tienMatText = txtTienKhachDua.getText();
        String tienCKText = txtTienKhachCK.getText();

        // Remove commas and other non-numeric characters
        String TienMatWithoutCommas = tienMatText.replaceAll("[^\\d.]", "");
        String TienCKWithoutCommas = tienCKText.replaceAll("[^\\d.]", "");

        // Initialize BigDecimal variables
        BigDecimal tienMat = null;
        BigDecimal tienCK = null;

        // Parse strings to BigDecimal if they are not null or empty
        if (!TienMatWithoutCommas.isEmpty()) {
            tienMat = new BigDecimal(TienMatWithoutCommas);
        }
        if (!TienCKWithoutCommas.isEmpty()) {
            tienCK = new BigDecimal(TienCKWithoutCommas);
        }

        // Get username and current date
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();

        // Create HinhThucThanhToanEntity object
        HinhThucThanhToanEntity httte = new HinhThucThanhToanEntity(idHD, tienCK, tienMat, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return httte;
    }

    public HoaDon getFormDataGiaoHang() {
        String idKH = txtMaKH.getText();
        String tongTienText = txtTongTienDH.getText();
        String tongTienSauKhiGiamText = jLabel27.getText();
        String idHD = txtMaHĐH.getText();
        String idNv = getMaNV.getText();
        String diaChiKH = txtDiaChiKH.getText();
        String phiShip = txtPhiShip.getText();
        String sdtKH = txtSDTKH.getText();
        String tenKh = txtGetTenKHDH.getText();

        if (tongTienText.isEmpty() || tongTienSauKhiGiamText.isEmpty()) {
            return null;
        }

        // Loại bỏ dấu phẩy khỏi chuỗi tổng tiền
        String tongTienWithoutComma = tongTienText.replaceAll(",", "");
        String phiShipWithoutComma = phiShip.replaceAll(",", "");
        String tongTienSauKhiGiamWithoutComma = tongTienSauKhiGiamText.replaceAll(",", "");

        // Chuyển đổi chuỗi thành đối tượng BigDecimal
        BigDecimal tongTienBD = new BigDecimal(tongTienWithoutComma);
        BigDecimal tongTienSauKhiGiamBD = new BigDecimal(tongTienSauKhiGiamWithoutComma);

        // Convert JDateChooser date to LocalDateTime
        Date date = jdcNgayNhan.getDate();
        Instant instant = date.toInstant();
        LocalDateTime ngayNhan = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Tiếp tục với việc tạo đối tượng HoaDon
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();

        float phiShipFloat = Float.parseFloat(phiShipWithoutComma);

        // Assuming the appropriate constructor parameters
        HoaDon hd = new HoaDon(idHD, idKH, idNv, ngayThucTe, ngayThucTe, ngayNhan, phiShipFloat, tongTienSauKhiGiamBD, tongTienBD, tenKh, sdtKH, diaChiKH, ngayThucTe, nhanVien, 1);
        return hd;
    }

    public PhuongThucThanhToan getFormDataPTTTĐH() {
        String tenKieuTT = (String) cboHTTTĐH.getSelectedItem();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();

        PhuongThucThanhToan pttt = new PhuongThucThanhToan(tenKieuTT, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return pttt;
    }

    public HinhThucThanhToanEntity getFormDataHTTTĐH() {
        String idHD = txtMaHĐH.getText();

        // Tiếp tục xử lý khi có idPTTT hợp lệ
        String tienMatText = txtTienKhachDuaDH.getText();
        String tienCKText = txtTienCKDH.getText();

        // Remove commas and other non-numeric characters
        String TienMatWithoutCommas = tienMatText.replaceAll("[^\\d.]", "");
        String TienCKWithoutCommas = tienCKText.replaceAll("[^\\d.]", "");

        // Initialize BigDecimal variables
        BigDecimal tienMat = null;
        BigDecimal tienCK = null;

        // Parse strings to BigDecimal if they are not null or empty
        if (!TienMatWithoutCommas.isEmpty()) {
            tienMat = new BigDecimal(TienMatWithoutCommas);
        }
        if (!TienCKWithoutCommas.isEmpty()) {
            tienCK = new BigDecimal(TienCKWithoutCommas);
        }

        // Get username and current date
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();

        // Create HinhThucThanhToanEntity object
        HinhThucThanhToanEntity httte = new HinhThucThanhToanEntity(idHD, tienCK, tienMat, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return httte;
    }

    public LichSuHDModel getFormDataLSHD() {
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();
        String hanhDong = "Tạo hóa đơn";

        LichSuHDModel lshdm = new LichSuHDModel(nhanVien, hanhDong, 1, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return lshdm;
    }

    public LichSuHDModel getFormDataUpdateLSHD() {
        String idHD = txtMaHD.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();
        String hanhDong = "Thanh Toán Hóa Đơn";

        LichSuHDModel lshdm = new LichSuHDModel(idHD, nhanVien, hanhDong, 1, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return lshdm;
    }

    public LichSuHDModel getFormDataUpdateLSHDGiaoHang() {
        String idHD = txtMaHD.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();
        String hanhDong = "Đã Thanh Toán Hóa Đơn, Đang Giao Hàng";

        LichSuHDModel lshdm = new LichSuHDModel(idHD, nhanVien, hanhDong, 1, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
        return lshdm;
    }

    private void cleardata() {
        // Xóa dữ liệu trong các thành phần giao diện
        txtMaHD.setText("");
        txtNgayTao.setText("");
        txtSetTenKH.setText("");
        txtNgayThanhToan.setText("");
        txtMaNV.setText("");
        txtTongTien.setText("0");
        cboPgg.setSelectedItem(null);
        cboHTT.setSelectedIndex(0);
        showDataTableSP(bhService.getSP());
        showDataTableHoaDon(bhService.getHD());
        gioHangList.clear();
        selectedCTSPs.clear();
        selectedImels.clear();
        selectedImelsDelete.clear();
        updateDataTableGioHang();
        int index = tblHoaDon.getSelectedRow();
        updateDataTableGioHang();
        if (index == 0) {
            tblModelHD.setRowCount(0);
        }
        System.out.println(gioHangList.size());
    }

    private void cleardataĐH() {
        txtSDTKH.setText("");
        txtGetTenKHDH.setText("");
        txtMaHĐH.setText("");
        txtDiaChiKH.setText("");
        txtPhiShip.setText("");
        jdcNgayNhan.setDate(null);
        txtSetTenKH.setText("");
        txtTenNhanVien.setText("");
        txtSDTNV.setText("");
        txtTongTienDH.setText("0");
        cboPggDH.setSelectedItem(null);
        cboHTTTĐH.setSelectedIndex(0);
        showDataTableSP(bhService.getSP());
        showDataTableHoaDon(bhService.getHD());
        gioHangList.clear();
        selectedCTSPs.clear();
        selectedImels.clear();
        selectedImelsDelete.clear();
        int index = tblHoaDon.getSelectedRow();
        updateDataTableGioHang();
        if (index == 0) {
            tblModelHD.setRowCount(0);
        }
        System.out.println(gioHangList.size());
    }

    private boolean checkTaiQuay() {
        if (tblHoaDon.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn hóa đơn để thanh toán");
            return false;
        }

        if (txtMaKH.getText().trim().isEmpty() || txtGetTenKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy chọn Khách Hàng");
            return false;
        }

        if (jLabel14.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 sản phẩm để thanh toán");
            return false;
        }

        if (cboPgg.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn phiếu giảm giá");
            return false;
        }

        if (cboHTT.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn hình thức thanh toán");
            return false;
        }

        if (cboHTT.getSelectedIndex() == 1) {
            if (txtTienKhachDua.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "hãy nhập tiền khách đưa");
                return false;
            }
        }

        if (cboHTT.getSelectedIndex() == 1) {
            try {
                double tien = Double.parseDouble(txtTienKhachDua.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số.");
                return false;
            }
        }

        if (cboHTT.getSelectedIndex() == 2) {
            if (txtTienKhachCK.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "hãy nhập tiền khách chuyển khoản");
                return false;
            }
        }

        if (cboHTT.getSelectedIndex() == 2) {
            try {
                double tien = Double.parseDouble(txtTienKhachCK.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải là số.");
                return false;
            }
        }

        if (cboHTT.getSelectedIndex() == 3) {
            if (txtTienKhachCK.getText().trim().equals("") && txtTienKhachDua.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "hãy nhập tiền khách đưa và chuyển khoản");
                return false;
            }
        }

        if (cboHTT.getSelectedIndex() == 3) {
            try {
                double tien = Double.parseDouble(txtTienKhachDua.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số.");
                return false;
            }

            try {
                double tien = Double.parseDouble(txtTienKhachCK.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải là số.");
                return false;
            }
        }

        String tienThua = txtTienThua.getText().trim().replace(",", "");
        if (!tienThua.isEmpty()) {
            try {
                double tienThuaValue = Double.parseDouble(tienThua);
                if (tienThuaValue < 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng trả thêm số tiền còn thiếu" + tienThua);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho số tiền thừa.");
                return false;
            }
        }

        return true;
    }

    private boolean checkDatHang() {
        if (tblHoaDon.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn hóa đơn để thanh toán");
            return false;
        }

        if (txtSDTKH.getText().trim().isEmpty() || txtGetTenKHDH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy chọn Khách Hàng");
            return false;
        }

        if (txtSDTNV.getText().trim().isEmpty() || txtTenNhanVien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy chọn nhân viên giao hàng");
            return false;
        }

        if (jLabel27.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 sản phẩm để thanh toán");
            return false;
        }

        if (txtDiaChiKH.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy nhập địa chỉ khách hàng");
            return false;
        }

        if (txtPhiShip.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy nhập phí ship");
            return false;
        }

        String phiShipText = txtPhiShip.getText().trim().replace(",", "");
        if (!phiShipText.isEmpty()) {
            try {
                double phiShip = Double.parseDouble(phiShipText);
                if (phiShip <= 0) {
                    JOptionPane.showMessageDialog(this, "Phí ship phải > 0");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho phí ship.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập phí ship.");
            return false;
        }

        if (cboPggDH.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn phiếu giảm giá");
            return false;
        }

        if (cboHTTTĐH.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn hình thức thanh toán");
            return false;
        }

        if (cboHTTTĐH.getSelectedIndex() == 1) {
            if (txtTienKhachDuaDH.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "hãy nhập tiền khách đưa");
                return false;
            }
        }

        if (cboHTTTĐH.getSelectedIndex() == 1) {
            try {
                double tien = Double.parseDouble(txtTienKhachDuaDH.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số.");
                return false;
            }
        }

        if (cboHTTTĐH.getSelectedIndex() == 2) {
            if (txtTienCKDH.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "hãy nhập tiền khách chuyển khoản");
                return false;
            }
        }

        if (cboHTTTĐH.getSelectedIndex() == 2) {
            try {
                double tien = Double.parseDouble(txtTienCKDH.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải là số.");
                return false;
            }
        }

        if (cboHTTTĐH.getSelectedIndex() == 3) {
            if (txtTienCKDH.getText().trim().equals("") && txtTienKhachDuaDH.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "hãy nhập tiền khách đưa và chuyển khoản");
                return false;
            }
        }

        if (cboHTTTĐH.getSelectedIndex() == 3) {
            try {
                double tien = Double.parseDouble(txtTienKhachDuaDH.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số.");
                return false;
            }

            try {
                double tien = Double.parseDouble(txtTienCKDH.getText().replace(",", ""));
                if (tien <= 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải lớn hơn 0.");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tiền khách chuyển khoản phải là số.");
                return false;
            }
        }

        if (jdcNgayNhan.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Nhập ngày nhận");
            return false;
        } else {
            // Lấy ngày hiện tại
            Date currentDate = new Date();
            // Lấy ngày nhận từ JDateChooser
            Date selectedDate = jdcNgayNhan.getDate();

            // So sánh ngày nhận với ngày hiện tại
            if (selectedDate.before(currentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày nhận không được nhỏ hơn ngày hiện tại");
                return false;
            }
        }

        String tienThua = txtTienThuaDH.getText().trim().replace(",", "");
        if (!tienThua.isEmpty()) {
            try {
                double tienThuaValue = Double.parseDouble(tienThua);
                if (tienThuaValue < 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng trả thêm số tiền còn thiếu" + tienThua);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho số tiền thừa.");
                return false;
            }
        }

        return true;
    }

    //in hd
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonCustom5 = new mobileworld.swing.ButtonCustom();
        txtTimKiemHD = new mobileworld.swing.TextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new mobileworld.swing.Table();
        btnQuetQR = new mobileworld.swing.ButtonCustom();
        btnClear = new mobileworld.swing.ButtonCustom();
        jPanel2 = new javax.swing.JPanel();
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
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        buttonCustom2 = new mobileworld.swing.ButtonCustom();
        jLabel28 = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtGetTenKHDH = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDiaChiKH = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        txtSDTNV = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtTenNhanVien = new javax.swing.JTextField();
        buttonCustom6 = new mobileworld.swing.ButtonCustom();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        buttonCustom9 = new mobileworld.swing.ButtonCustom();
        jLabel26 = new javax.swing.JLabel();
        cboHTTTĐH = new javax.swing.JComboBox<>();
        cboPggDH = new javax.swing.JComboBox<>();
        txtTongTienDH = new javax.swing.JLabel();
        txtTienKhachDuaDH = new javax.swing.JTextField();
        txtTienCKDH = new javax.swing.JTextField();
        txtTienThuaDH = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jdcNgayNhan = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtMaHĐH = new javax.swing.JLabel();
        txtPhiShip = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel1.setOpaque(false);

        buttonCustom5.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-receipt-24.png"))); // NOI18N
        buttonCustom5.setText("Thêm Hóa Đơn");
        buttonCustom5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom5ActionPerformed(evt);
            }
        });

        txtTimKiemHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTimKiemHD.setLabelText("Tìm Kiếm");
        txtTimKiemHD.setLineColor(new java.awt.Color(102, 102, 102));
        txtTimKiemHD.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtTimKiemHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemHDKeyReleased(evt);
            }
        });

        jScrollPane3.setBorder(null);

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Mã Nhân Viên", "Tên KH", "SĐT KH", "Tổng SP", "Trạng Thái"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(20);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        btnQuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQuetQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-qr-code-30.png"))); // NOI18N
        btnQuetQR.setText("Quét QR");
        btnQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-refresh-24.png"))); // NOI18N
        btnClear.setText("Làm Mới");
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel2.setOpaque(false);

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
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
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
            .addComponent(jScrollPane4)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            tblSP.getColumnModel().getColumn(1).setMinWidth(200);
            tblSP.getColumnModel().getColumn(1).setMaxWidth(250);
            tblSP.getColumnModel().getColumn(4).setMinWidth(150);
            tblSP.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(cboNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(cboManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(cboCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(cboPin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addComponent(jScrollPane5)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboPin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboManHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboNSX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bán Hàng");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel7.setOpaque(false);

        txtMaKH.setDisabledTextColor(new java.awt.Color(19, 35, 86));
        txtMaKH.setEnabled(false);
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMaKH.setLabelText("Mã Khách Hàng");
        txtMaKH.setOpaque(true);

        txtGetTenKH.setDisabledTextColor(new java.awt.Color(19, 35, 86));
        txtGetTenKH.setEnabled(false);
        txtGetTenKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtGetTenKH.setLabelText("Tên Khách Hàng");
        txtGetTenKH.setOpaque(true);
        txtGetTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGetTenKHActionPerformed(evt);
            }
        });

        buttonCustom1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setText("Chọn");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

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
        buttonCustom3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom3ActionPerformed(evt);
            }
        });

        buttonCustom8.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom8.setText("Thanh Toán HĐ");
        buttonCustom8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom8ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 0, 0));
        jLabel13.setText("Tổng Tiền:");

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

        txtTongTien.setText("0");
        txtTongTien.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTongTienPropertyChange(evt);
            }
        });

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

        txtTienThua.setEnabled(false);
        txtTienThua.setOpaque(true);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 0, 0));
        jLabel14.setText("0");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(buttonCustom3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
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
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTienKhachCK)
                            .addComponent(txtTienKhachDua)
                            .addComponent(cboHTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboPgg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        materialTabbed2.addTab("Tại Quầy", jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel9.setOpaque(false);

        buttonCustom2.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonCustom2.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom2.setText("Chọn");
        buttonCustom2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setText("SĐT KH:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("Tên KH:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Địa Chỉ:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiaChiKH)
                            .addComponent(txtGetTenKHDH)
                            .addComponent(txtSDTKH))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtGetTenKHDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtDiaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhân Viên Vận Chuyển", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel5.setOpaque(false);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("SĐT NV:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("Tên NV:");

        buttonCustom6.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonCustom6.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom6.setText("Chọn");
        buttonCustom6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(txtSDTNV))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel10.setOpaque(false);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Mã HĐ:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Tổng Tiền:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Phiếu Giảm Giá:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("HT Thanh Toán:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("Tiền Khách Đưa:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("Tiền Khách CK:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Tiền Thừa:");

        buttonCustom9.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom9.setText("Giao Hàng");
        buttonCustom9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom9ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 0, 0));
        jLabel26.setText("Tổng Tiền:");

        cboHTTTĐH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHTTTĐHActionPerformed(evt);
            }
        });

        cboPggDH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboPggDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPggDHActionPerformed(evt);
            }
        });

        txtTongTienDH.setText("0");
        txtTongTienDH.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTongTienDHPropertyChange(evt);
            }
        });

        txtTienKhachDuaDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaDHKeyReleased(evt);
            }
        });

        txtTienCKDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienCKDHKeyReleased(evt);
            }
        });

        txtTienThuaDH.setEnabled(false);
        txtTienThuaDH.setOpaque(true);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(153, 0, 0));
        jLabel27.setText("0");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setText("Phí Ship:");

        jdcNgayNhan.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayNhan.setDateFormatString("yyyy-MM-dd");
        jdcNgayNhan.setOpaque(false);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Ngày Muốn Nhận:");

        txtPhiShip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPhiShipFocusLost(evt);
            }
        });
        txtPhiShip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhiShipKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonCustom9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboHTTTĐH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboPggDH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongTienDH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMaHĐH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(14, 14, 14))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel23)
                                        .addComponent(jLabel24))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(57, 57, 57)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTienThuaDH)
                            .addComponent(jdcNgayNhan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienCKDH)
                            .addComponent(txtTienKhachDuaDH))))
                .addGap(18, 18, 18))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMaHĐH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTongTienDH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cboPggDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cboHTTTĐH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jdcNgayNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtTienKhachDuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtTienCKDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtTienThuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addGap(0, 0, 0)
                .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        materialTabbed2.addTab("Đặt Hàng", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(materialTabbed2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(materialTabbed2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void cboPggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPggActionPerformed
        // TODO add your handling code here:
        String tenPGG = (String) cboPgg.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float soTienGiamToiDa = pggService.laySTGTD(tenPGG);
        float tongTien = 0;
        float giamTheoPhanTram = 0;
        float soTienGiam = 0;
        String txtTongTienValue = txtTongTien.getText().trim();
        if (txtTongTienValue.equals("")) {
            return;
        }
        try {
            tongTien = Float.parseFloat(txtTongTienValue.replace(",", ""));
        } catch (NumberFormatException e) {
            return;
        }
        if (phanTramGiam == 0) {
            jLabel14.setText(decimalFormat.format(tongTien));
        } else {
            giamTheoPhanTram = tongTien * ((phanTramGiam / 100));
            soTienGiam = Float.min(giamTheoPhanTram, soTienGiamToiDa);
            jLabel14.setText(decimalFormat.format(tongTien - soTienGiam));
        }
        txtTienThua.setText("");

    }//GEN-LAST:event_cboPggActionPerformed

    private void cboHTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHTTActionPerformed
        // TODO add your handling code here:    
        String ht = (String) cboHTT.getSelectedItem();
        if (ht.equals("Tiền mặt")) {
            txtTienKhachDua.setEnabled(true);
            txtTienKhachCK.setEnabled(false);
            txtTienKhachDua.setText("");
            txtTienKhachCK.setText("");
            txtTienThua.setText("");
        } // Kiểm tra nếu phương thức thanh toán là "Chuyển khoản"
        else if (ht.equals("Chuyển khoản")) {
            txtTienKhachCK.setEnabled(true);
            txtTienKhachDua.setEnabled(false);
            txtTienKhachCK.setText("");
            txtTienKhachDua.setText("");
            txtTienThua.setText("");
        } else {
            txtTienKhachDua.setEnabled(true);
            txtTienKhachCK.setEnabled(true);
            txtTienKhachDua.setText("");
            txtTienKhachCK.setText("");
            txtTienThua.setText("");
        }

    }//GEN-LAST:event_cboHTTActionPerformed


    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        String text = txtTienKhachDua.getText().trim();
        if (!text.isEmpty()) {
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
        float thanhTien = 0;
        float tienMat = 0;
        float tienCK = 0;
        if (calculateTotalAmountFromGioHang().compareTo(BigDecimal.ZERO) != 0) {
            thanhTien = Float.parseFloat(jLabel14.getText().trim().replace(",", ""));
        }
        if (!txtTienKhachDua.getText().isEmpty()) {
            tienMat = Float.parseFloat(txtTienKhachDua.getText().trim().replace(",", ""));
        }
        if (cboHTT.getSelectedIndex() == 1) {
            if (!txtTienKhachDua.getText().isEmpty()) {
                tienThua = tienMat - thanhTien;
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }
        if (cboHTT.getSelectedIndex() == 3) {
            if (!txtTienKhachDua.getText().isEmpty() && !txtTienKhachCK.getText().isEmpty()) {
                tienCK = Float.parseFloat(txtTienKhachCK.getText().trim().replace(",", ""));
                tienThua = (tienMat + tienCK) - thanhTien;
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachCKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachCKKeyReleased
        // TODO add your handling code here:
        String text = txtTienKhachCK.getText().trim();
        if (!text.isEmpty()) {
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
            tongTien = Float.parseFloat(jLabel14.getText().trim().replace(",", ""));
        }
        if (!txtTienKhachCK.getText().isEmpty()) {
            tienCK = Float.parseFloat(txtTienKhachCK.getText().trim().replace(",", ""));
        }
        if (cboHTT.getSelectedIndex() == 2) {
            if (!txtTienKhachCK.getText().isEmpty()) {
                tienThua = tienCK - tongTien;
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }
        if (cboHTT.getSelectedIndex() == 3) {

            if (!txtTienKhachDua.getText().isEmpty() && !txtTienKhachCK.getText().isEmpty()) {
                tienMat = Float.parseFloat(txtTienKhachDua.getText().trim().replace(",", ""));
                tienThua = (tienMat + tienCK) - tongTien;
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThua.setText(decimalFormat.format(tienThua));
            }
        }

    }//GEN-LAST:event_txtTienKhachCKKeyReleased

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        ThongTinKhachHangDialog thongTinKhachHangDialog = new ThongTinKhachHangDialog(this);
        thongTinKhachHangDialog.setVisible(true);
    }//GEN-LAST:event_buttonCustom1ActionPerformed

    public HoaDon getFromDataHoaDon() {
        int trangThai = 0;
        if (materialTabbed2.getSelectedIndex() == 0) {
            trangThai = 0;
        } else if (materialTabbed2.getSelectedIndex() == 1) {
            trangThai = 3;
        }
        String idNV = SessionStorage.getInstance().getUsername();
        HoaDon hd = new HoaDon(idNV, idNV, idNV, trangThai);
        return hd;
    }


    private void buttonCustom5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom5ActionPerformed
        HoaDon hoaDonData = getFromDataHoaDon();
        LichSuHDModel lshdm = getFormDataLSHD();

        if (lshdm == null) {
            // Xử lý trường hợp lshdm là null
            System.out.println("Lỗi khi lấy dữ liệu LichSuHDModel");
            return;
        }

        bhService.addNewBlankInvoice(hoaDonData, lshdm);
        listHDM = bhService.getHD();
        showDataTableHoaDon(listHDM);

        int rowCount = tblHoaDon.getRowCount();
        if (rowCount > 0) {
            tblHoaDon.setRowSelectionInterval(0, 0); // Chọn hàng cuối cùng
            mouseClickHD();
            mouseClickHoaDonDH(); // Kích hoạt sự kiện click cho hàng cuối cùng
        }
        System.out.println("Thêm hóa đơn trống thành công!");
    }//GEN-LAST:event_buttonCustom5ActionPerformed

    private void mouseClickHD() {
        int index = tblHoaDon.getSelectedRow();

        // Check if a row is selected
        if (index < 0) {
            return;
        }

        String trangThai = (String) tblHoaDon.getValueAt(index, 6);

        // Kiểm tra xem trạng thái có hợp lệ không
        if (trangThai.equals("Chờ Thanh Toán")) {
            materialTabbed2.setSelectedIndex(0);
        } else if (trangThai.equals("Chờ Giao")) {
            materialTabbed2.setSelectedIndex(1);
        }

        HoaDonViewModel hdvm;
        if (listHDM != null && !listHDM.isEmpty() && index < listHDM.size()) {
            hdvm = listHDM.get(index);
        } else {
            hdvm = bhService.getHD().get(index);
        }
        txtMaHD.setText(hdvm.getIdHD());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayTaoString = dateFormat.format(hdvm.getNgayTao());
        String ngayTTString = dateFormat.format(hdvm.getNgayThanhToan());
        txtNgayTao.setText(ngayTaoString);
        txtNgayThanhToan.setText(ngayTTString);
        txtMaNV.setText(hdvm.getIdNV());
        txtSetTenKH.setText(hdvm.getTenKH());

        BigDecimal tongTien = hdvm.getTongTien();
        // Ensure tongTien is not null
        if (tongTien != null) {
            // Format the BigDecimal using DecimalFormat
            String formattedTongTien = decimalFormat.format(tongTien);

            // Set the formatted value to txtTongTien
            txtTongTien.setText(formattedTongTien);

        } else {
            txtTongTien.setText("0");
            jLabel14.setText("0");
        }
    }

    private void mouseClickHoaDonDH() {
        int index = tblHoaDon.getSelectedRow();

        // Check if a row is selected
        if (index < 0) {
            return;
        }

        String trangThai = (String) tblHoaDon.getValueAt(index, 6);

        // Kiểm tra xem trạng thái có hợp lệ không
        if (trangThai.equals("Chờ Thanh Toán")) {
            materialTabbed2.setSelectedIndex(0);
        } else if (trangThai.equals("Chờ Giao")) {
            materialTabbed2.setSelectedIndex(1);
        }

        HoaDonViewModel hdvm;
        if (listHDM != null && !listHDM.isEmpty() && index < listHDM.size()) {
            hdvm = listHDM.get(index);
        } else {
            hdvm = bhService.getHD().get(index);
        }
        txtMaHĐH.setText(hdvm.getIdHD());
        txtGetTenKHDH.setText(hdvm.getTenKH());
        txtSDTKH.setText(hdvm.getSdtKH());
        jdcNgayNhan.setDate(hdvm.getNgayNhan());
        txtDiaChiKH.setText(hdvm.getDiaChiKH());

        BigDecimal tongTien = hdvm.getTongTien();
        // Ensure tongTien is not null
        if (tongTien != null) {
            // Format the BigDecimal using DecimalFormat
            String formattedTongTien = decimalFormat.format(tongTien);

            // Set the formatted value to txtTongTien
            txtTongTienDH.setText(formattedTongTien);

        } else {
            txtTongTienDH.setText("0");
            jLabel27.setText("0");
        }
    }


    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        mouseClickHD();
        mouseClickHoaDonDH();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void buttonCustom8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom8ActionPerformed
        if (checkTaiQuay()) {
            // Lấy dữ liệu từ form
            HoaDon hoaDon = getFormData();

            if (hoaDon == null) {
                JOptionPane.showMessageDialog(this, "Dữ liệu hóa đơn không hợp lệ.");
                return;
            }

            // Lấy danh sách HoaDonChiTietEntity từ bảng
            List<HoaDonChiTietEntity> hdctList = getFormDataHDCT();
            if (hdctList == null || hdctList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Dữ liệu hóa đơn chi tiết không hợp lệ.");
                return;
            }

            // Lấy thông tin phương thức thanh toán và hình thức thanh toán
            PhuongThucThanhToan pttt = getFormDataPTTT();
            HinhThucThanhToanEntity httte = getFormDataHTTT();
            LichSuHDModel lshdm = getFormDataUpdateLSHD();

            // Thực hiện thanh toán
            if (!bhService.ThanhToanHD(hoaDon, hdctList, hoaDon.getIdHD(), pttt, httte, lshdm)) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thanh toán. Vui lòng thử lại.");
                return;
            }
            listHDM = bhService.getHD();
            showDataTableHoaDon(listHDM);
            cleardata();
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                ViewHoaDon viewHoaDon = new ViewHoaDon();
                viewHoaDon.printHD(hoaDon.getIdHD());
            }
        }
    }//GEN-LAST:event_buttonCustom8ActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        if (tblHoaDon.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn hóa đơn để quét QR.");
            return;
        }

        // If a row is selected, proceed to scan QR code.
        ReadQRCodeAddSP qrCodeScanner = new ReadQRCodeAddSP(this);
        qrCodeScanner.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void txtGetTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGetTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGetTenKHActionPerformed

    private void txtTongTienPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTongTienPropertyChange
        String getTongTien = txtTongTien.getText().trim();
        String tenGG = "";
        float HoaDonMin = 0;
        float soTienGiamMax = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        if (!getTongTien.equals("0") && !getTongTien.equals("0")) {
            List<PhieuGiamGia> pggPhuHop = new ArrayList<>();
            float tongTien = 0;
            try {
                tongTien = Float.parseFloat(getTongTien.replace(",", ""));
            } catch (NumberFormatException e) {
                return;
            }
            pggPhuHop = pggService.getPGGPhuHop(tongTien);
            cbbPgg.removeAllElements();
            for (PhieuGiamGia pgg : pggPhuHop) {
                cbbPgg.addElement(pgg.getTenGiamGia());
            }
            List<PhieuGiamGia> goiY = new ArrayList<>();
            goiY = pggService.goiY(tongTien);
            for (PhieuGiamGia pgg : goiY) {
                tenGG = pgg.getTenGiamGia();
                HoaDonMin = pgg.getHoatDonToiThieu();
                soTienGiamMax = pgg.getSoTiemGiamToiDa();
            }
            if (HoaDonMin - tongTien < 0) {
                cboPgg.setToolTipText("");
            } else {
                cboPgg.setToolTipText("Mua thêm " + decimalFormat.format(HoaDonMin - tongTien) + " để được giảm tối đa " + decimalFormat.format(soTienGiamMax));
            }
        } else {
            List<PhieuGiamGia> dsPGGAll = pggService.getAll();
            setDataCboPhieuGG(dsPGGAll);
        }

    }//GEN-LAST:event_txtTongTienPropertyChange

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void buttonCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom3ActionPerformed
        txtGetTenKH.setText(khService.getTen());
        txtMaKH.setText(khService.getID());
        txtMaHD.setText("");
        txtNgayTao.setText("");
        txtSetTenKH.setText("");
        txtNgayThanhToan.setText("");
        txtMaNV.setText("");
        txtTongTien.setText("0");
        cboPgg.setSelectedItem(null);
        cboHTT.setSelectedIndex(0);

        int index = tblHoaDon.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để hủy.");
            return;
        }

        List<String> ImelList = new ArrayList<>();
        for (ChiTietSanPhamViewModel sp : selectedImelsDelete) {
            ImelList.add(sp.getImel());
        }

        // Kiểm tra xem có sản phẩm trong giỏ hàng không
        if (!ImelList.isEmpty()) {
            deleteTableGioHang(bhService.deleteGioHang(ImelList));
        }

        if (index == 0) {
            tblModelHD.setRowCount(0);
        }

        showDataTableSP(bhService.getSP());
        HoaDonViewModel hd = bhService.getHD().get(index);
        bhService.updateDeleteHD(hd.getIdHD());
        JOptionPane.showMessageDialog(this, "Hóa đơn đã được hủy!");
        listHDM = bhService.getHD();
        showDataTableHoaDon(listHDM);
    }//GEN-LAST:event_buttonCustom3ActionPerformed

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed
        ThongTinKhachHangDialog thongTinKhachHangDialog = new ThongTinKhachHangDialog(this);
        thongTinKhachHangDialog.setVisible(true);
    }//GEN-LAST:event_buttonCustom2ActionPerformed

    private void buttonCustom9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom9ActionPerformed
        if (checkDatHang()) {
            // Lấy dữ liệu từ form
            HoaDon hoaDon = getFormDataGiaoHang();
            if (hoaDon == null) {
                JOptionPane.showMessageDialog(this, "Dữ liệu hóa đơn không hợp lệ.");
                return;
            }

            // Lấy danh sách HoaDonChiTietEntity từ bảng
            List<HoaDonChiTietEntity> hdctList = getFormDataHDCT();
            if (hdctList == null || hdctList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Dữ liệu hóa đơn chi tiết không hợp lệ.");
                return;
            }

            PhuongThucThanhToan pttt = getFormDataPTTTĐH();
            HinhThucThanhToanEntity httte = getFormDataHTTTĐH();
            LichSuHDModel lshdm = getFormDataUpdateLSHDGiaoHang();

            // Thực hiện thanh toán
            if (!bhService.GiaoHang(hoaDon, hdctList, hoaDon.getIdHD(), pttt, httte, lshdm)) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi giao hàng. Vui lòng thử lại.");
                return;
            }

            // Cập nhật giao diện sau khi thanh toán
            listHDM = bhService.getHD();
            showDataTableHoaDon(listHDM);
            cleardataĐH();
            JOptionPane.showMessageDialog(this, "Giao Hàng thành công!");
            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                ViewHoaDon viewHoaDon = new ViewHoaDon();
                viewHoaDon.printHDDH(hoaDon.getIdHD());
            }
        }
    }//GEN-LAST:event_buttonCustom9ActionPerformed

    private void cboHTTTĐHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHTTTĐHActionPerformed
        // TODO add your handling code here:    
        String ht = (String) cboHTTTĐH.getSelectedItem();
        if (ht.equals("Tiền mặt")) {
            txtTienKhachDuaDH.setEnabled(true);
            txtTienCKDH.setEnabled(false);
            txtTienKhachDuaDH.setText("");
            txtTienCKDH.setText("");
            txtTienThuaDH.setText("");
        } // Kiểm tra nếu phương thức thanh toán là "Chuyển khoản"
        else if (ht.equals("Chuyển khoản")) {
            txtTienCKDH.setEnabled(true);
            txtTienKhachDuaDH.setEnabled(false);
            txtTienKhachDuaDH.setText("");
            txtTienCKDH.setText("");
            txtTienThuaDH.setText("");
        } else {
            txtTienKhachDuaDH.setEnabled(true);
            txtTienCKDH.setEnabled(true);
            txtTienKhachDuaDH.setText("");
            txtTienCKDH.setText("");
            txtTienThuaDH.setText("");
        }
    }//GEN-LAST:event_cboHTTTĐHActionPerformed

    private void cboPggDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPggDHActionPerformed
        // TODO add your handling code here:
        String tenPGG = (String) cboPggDH.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float soTienGiamToiDa = pggService.laySTGTD(tenPGG);
        float tongTien = 0;
        float giamTheoPhanTram = 0;
        float soTienGiam = 0;
        String txtTongTienValue = txtTongTienDH.getText().trim();

        if (txtTongTienValue.equals("")) {
            return;
        }
        try {
            tongTien = Float.parseFloat(txtTongTienValue.replace(",", ""));
        } catch (NumberFormatException e) {
            return;
        }
        if (phanTramGiam == 0) {
            jLabel27.setText(decimalFormat.format(tongTien));
        } else {
            giamTheoPhanTram = tongTien * ((phanTramGiam / 100));
            soTienGiam = Float.min(giamTheoPhanTram, soTienGiamToiDa);
            jLabel27.setText(decimalFormat.format((tongTien - soTienGiam)));
        }
        txtTienThuaDH.setText("");
    }//GEN-LAST:event_cboPggDHActionPerformed

    private void txtTongTienDHPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTongTienDHPropertyChange
        String getTongTien = txtTongTienDH.getText().trim();
        String tenGG = "";
        float HoaDonMin = 0;
        float soTienGiamMax = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        if (!getTongTien.equals("0đ") && !getTongTien.equals("0")) {
            List<PhieuGiamGia> pggPhuHop = new ArrayList<>();
            float tongTien = 0;
            try {
                tongTien = Float.parseFloat(getTongTien.replace(",", ""));
            } catch (NumberFormatException e) {
                return;
            }
            pggPhuHop = pggService.getPGGPhuHop(tongTien);
            cbbPggĐH.removeAllElements();
            for (PhieuGiamGia pgg : pggPhuHop) {
                cbbPggĐH.addElement(pgg.getTenGiamGia());
            }
            List<PhieuGiamGia> goiY = new ArrayList<>();
            goiY = pggService.goiY(tongTien);
            for (PhieuGiamGia pgg : goiY) {
                tenGG = pgg.getTenGiamGia();
                HoaDonMin = pgg.getHoatDonToiThieu();
                soTienGiamMax = pgg.getSoTiemGiamToiDa();
            }
            if (HoaDonMin - tongTien < 0) {
                cboPggDH.setToolTipText("");
            } else {
                cboPggDH.setToolTipText("Mua thêm " + decimalFormat.format(HoaDonMin - tongTien) + " để được giảm tối đa " + decimalFormat.format(soTienGiamMax));
            }
        } else {
            List<PhieuGiamGia> dsPGGAll = pggService.getAll();
            setDataCboPhieuGG(dsPGGAll);
        }
    }//GEN-LAST:event_txtTongTienDHPropertyChange

    private void txtTienKhachDuaDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaDHKeyReleased
        String text = txtTienKhachDuaDH.getText().trim();
        if (!text.isEmpty()) {
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    txtTienKhachDuaDH.setText(decimalFormat.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }
        }

        String tenPGG = (String) cboPggDH.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float tienThua = 0;
        float tongTien = 0;
        float tienMat = 0;
        float tienCK = 0;
        if (calculateTotalAmountFromGioHang().compareTo(BigDecimal.ZERO) != 0) {
            tongTien = Float.parseFloat(jLabel27.getText().trim().replace(",", ""));
        }
        if (!txtTienKhachDuaDH.getText().isEmpty()) {
            tienMat = Float.parseFloat(txtTienKhachDuaDH.getText().trim().replace(",", ""));
        }
        if (cboHTTTĐH.getSelectedIndex() == 1) {
            if (!txtTienKhachDuaDH.getText().isEmpty()) {
                tienThua = tienMat - tongTien;
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThuaDH.setText(decimalFormat.format(tienThua));
            }
        }
        if (cboHTTTĐH.getSelectedIndex() == 3) {
            if (!txtTienKhachDuaDH.getText().isEmpty() && !txtTienCKDH.getText().isEmpty()) {
                tienCK = Float.parseFloat(txtTienCKDH.getText().trim().replace(",", ""));
                tienThua = (tienMat + tienCK) - tongTien;
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThuaDH.setText(decimalFormat.format(tienThua));
            }
        }

    }//GEN-LAST:event_txtTienKhachDuaDHKeyReleased

    private void txtTienCKDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCKDHKeyReleased
        String text = txtTienCKDH.getText().trim();
        if (!text.isEmpty()) {
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    txtTienCKDH.setText(decimalFormat.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }
        }
        String tenPGG = (String) cboPggDH.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float tienThua = 0;
        float tongTien = 0;
        float tienMat = 0;
        float tienCK = 0;
        if (calculateTotalAmountFromGioHang().compareTo(BigDecimal.ZERO) != 0) {
            tongTien = Float.parseFloat(jLabel27.getText().trim().replace(",", ""));
        }
        if (!txtTienCKDH.getText().isEmpty()) {
            tienCK = Float.parseFloat(txtTienCKDH.getText().trim().replace(",", ""));
        }
        if (cboHTTTĐH.getSelectedIndex() == 2) {
            if (!txtTienCKDH.getText().isEmpty()) {
                tienThua = tienCK - tongTien;
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThuaDH.setText(decimalFormat.format(tienThua));
            }
        }
        if (cboHTTTĐH.getSelectedIndex() == 3) {

            if (!txtTienKhachDuaDH.getText().isEmpty() && !txtTienCKDH.getText().isEmpty()) {
                tienMat = Float.parseFloat(txtTienKhachDuaDH.getText().trim().replace(",", ""));
                tienThua = (tienMat + tienCK) - tongTien;
                // Format tiền thừa thành tiền tệ
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                txtTienThuaDH.setText(decimalFormat.format(tienThua));
            }
        }
    }//GEN-LAST:event_txtTienCKDHKeyReleased

    private void buttonCustom6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom6ActionPerformed
        ThongTinNhanVienDialog nhanVienDialog = new ThongTinNhanVienDialog(this);
        nhanVienDialog.setVisible(true);
    }//GEN-LAST:event_buttonCustom6ActionPerformed

    private void txtTimKiemHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemHDKeyReleased
        String searchText = txtTimKiemHD.getText().trim();
        if (searchText.isEmpty()) {
            showDataTableHoaDon(bhService.getHD());
        } else {
            listHDM = bhService.searchHD(searchText);
            showDataTableHoaDon(listHDM);
        }
    }//GEN-LAST:event_txtTimKiemHDKeyReleased

    private void txtPhiShipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhiShipKeyReleased
        String text = txtPhiShip.getText().trim();
        if (!text.isEmpty()) {
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormatPhiShip = new DecimalFormat("#,##0");
                    txtPhiShip.setText(decimalFormatPhiShip.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }
        }
    }//GEN-LAST:event_txtPhiShipKeyReleased

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtTimKiemHD.setText("");
        txtTimKiemSP.setText("");
        cboNSX.setSelectedItem(null);
        cboManHinh.setSelectedItem(null);
        cboCPU.setSelectedItem(null);
        cboPin.setSelectedItem(null);
        showDataTableHoaDon(bhService.getHD());
        showDataTableSP(bhService.getSP());
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtPhiShipFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhiShipFocusLost
        // TODO add your handling code here:

        try {
            if (!txtPhiShip.getText().isEmpty() && !jLabel27.getText().isEmpty()) {
                float phiShip = Float.parseFloat(txtPhiShip.getText().trim().replace(",", ""));
                float thanhTienShip = Float.parseFloat(jLabel27.getText().trim().replace(",", ""));
                float thanhTienTong = phiShip + thanhTienShip;
                if (phiShip == 0.0 || phiShip == 0 || txtPhiShip.getText().trim().isEmpty()) {
                    String tenPGG = (String) cboPggDH.getSelectedItem();
                    float phanTramGiam = pggService.layPGG(tenPGG);
                    float soTienGiamToiDa = pggService.laySTGTD(tenPGG);
                    float tongTien = 0;
                    float giamTheoPhanTram = 0;
                    float soTienGiam = 0;
                    String txtTongTienValue = txtTongTienDH.getText().trim();

                    if (txtTongTienValue.equals("")) {
                        return;
                    }
                    try {
                        tongTien = Float.parseFloat(txtTongTienValue.replace(",", ""));
                    } catch (NumberFormatException e) {
                        return;
                    }
                    if (phanTramGiam == 0) {
                        jLabel27.setText(decimalFormat.format(tongTien));
                    } else {
                        giamTheoPhanTram = tongTien * ((phanTramGiam / 100));
                        soTienGiam = Float.min(giamTheoPhanTram, soTienGiamToiDa);
                        jLabel27.setText(decimalFormat.format((tongTien - soTienGiam)));
                    }
                } else {
                    jLabel27.setText(decimalFormat.format(thanhTienTong));
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng phí Ship");
            return;
        }
    }//GEN-LAST:event_txtPhiShipFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnClear;
    private mobileworld.swing.ButtonCustom btnQuetQR;
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom2;
    private mobileworld.swing.ButtonCustom buttonCustom3;
    private mobileworld.swing.ButtonCustom buttonCustom5;
    private mobileworld.swing.ButtonCustom buttonCustom6;
    private mobileworld.swing.ButtonCustom buttonCustom8;
    private mobileworld.swing.ButtonCustom buttonCustom9;
    private mobileworld.swing.Combobox cboCPU;
    private javax.swing.JComboBox<String> cboHTT;
    private javax.swing.JComboBox<String> cboHTTTĐH;
    private mobileworld.swing.Combobox cboManHinh;
    private mobileworld.swing.Combobox cboNSX;
    private javax.swing.JComboBox<String> cboPgg;
    private javax.swing.JComboBox<String> cboPggDH;
    private mobileworld.swing.Combobox cboPin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.toedter.calendar.JDateChooser jdcNgayNhan;
    private mobileworld.swing.MaterialTabbed materialTabbed2;
    private mobileworld.swing.Table tblGioHang;
    private mobileworld.swing.Table tblHoaDon;
    private mobileworld.swing.Table tblSP;
    private javax.swing.JTextField txtDiaChiKH;
    private mobileworld.swing.TextField txtGetTenKH;
    private javax.swing.JTextField txtGetTenKHDH;
    private javax.swing.JLabel txtMaHD;
    private javax.swing.JLabel txtMaHĐH;
    private mobileworld.swing.TextField txtMaKH;
    private javax.swing.JLabel txtMaNV;
    private javax.swing.JLabel txtNgayTao;
    private javax.swing.JLabel txtNgayThanhToan;
    private javax.swing.JTextField txtPhiShip;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSDTNV;
    private javax.swing.JLabel txtSetTenKH;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTienCKDH;
    private javax.swing.JTextField txtTienKhachCK;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienKhachDuaDH;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTienThuaDH;
    private mobileworld.swing.TextField txtTimKiemHD;
    private mobileworld.swing.TextField txtTimKiemSP;
    private javax.swing.JLabel txtTongTien;
    private javax.swing.JLabel txtTongTienDH;
    // End of variables declaration//GEN-END:variables

}
