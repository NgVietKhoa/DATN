package mobileworld.form;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import mobileworld.dialog.DeselectProductSP;
import mobileworld.dialog.ReadQRCode;
import mobileworld.dialog.SelectProductSP;
import mobileworld.dialog.ThongTinKhachHangDialog;
import mobileworld.main.SessionStorage;
import mobileworld.model.CPU;
import mobileworld.model.HoaDon;
import mobileworld.model.HoaDonChiTietEntity;
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
import mobileworld.thread.ThreadTinhTrang;
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
    private List<HoaDonViewModel> listHDM = new ArrayList<>();
    
    private List<PhieuGiamGia> dsPGG = new ArrayList<>();
    protected ThreadTinhTrang threadTinhTrang;
    JTextArea textAreaImel = new JTextArea();
    JTextArea textAreaCtsp = new JTextArea();
    JTextArea textAreaMergedCtsp = new JTextArea();
    JTextArea getImelDelete = new JTextArea();
    
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
        setDataCboHTTT();
        
        tblSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblSP.getSelectedRow();
                int indexHD = tblHoaDon.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    String idDsp = (String) tblSP.getValueAt(index, 2);
                    
                    if (indexHD < 0) {
                        JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Hóa Đơn Để Thêm Sản Phẩm");
                        return;
                    }
                    
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
                    String[] idCtsps = textAreaMergedCtsp.getText().split("\n");
                    List<String> idCtspList = new ArrayList<>();
                    
                    for (String idCtsp : idCtsps) {
                        idCtsp = idCtsp.trim();
                        if (!idCtsp.isEmpty()) {
                            idCtspList.add(idCtsp);
                        }
                    }
                    
                    if (!idCtspList.isEmpty()) {
                        DeselectProductSP deselectProductSP = new DeselectProductSP(idCtspList, ViewBanHang.this);
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
        
        textAreaImel.setRows(100);
        
        textAreaCtsp.setRows(100);
        
        textAreaMergedCtsp.setRows(100);
        
        getImelDelete.setRows(100);
    }
    
    public void getIdCTSP(List<String> ctsps) {
        for (String ctsp : ctsps) {
            textAreaMergedCtsp.append(ctsp + "\n");
        }
        System.out.println(ctsps);
    }
    
    public void getImelDelete(List<String> ctsps) {
        for (String ctsp : ctsps) {
            getImelDelete.append(ctsp + "\n");
        }
        System.out.println(ctsps);
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
    
    public void getThongTinKH(String ten, String ma) {
        txtGetTenKH.setText(ten);
        txtMaKH.setText(ma);
        txtSetTenKH.setText(ten);
    }
    
    public void getAddThongTinKH(String ten, String ma) {
        txtGetTenKH.setText(ten);
        txtMaKH.setText(ma);
        txtSetTenKH.setText(ten);
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
                stt, sp.getId(), sp.getTenDsp(), sp.getTenNsx(), sp.getLoaiManHinh(), sp.getCpu(), sp.getDungLuongPin(), sp.getSoLuong(), giaBan
            });
        }
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
        // Cập nhật cột Tổng sản phẩm trong bảng tblHoaDon
        updateTotalQuantityInHoaDon(totalQuantityFromGioHang);
    }
    
    public void deleteGioHangWithImel(List<String> imels, int totalQuantity) {
        List<ChiTietSanPhamViewModel> gioHang = bhService.deleteGioHang(imels);
        showDataDeleteTableGioHang(gioHang, totalQuantity);
        showDataTableSP(bhService.getSP());

        // Cập nhật tổng số lượng sản phẩm
        int totalQuantityFromGioHang = calculateTotalQuantityFromGioHang();
        // Cập nhật lại tổng tiền
        BigDecimal totalAmount = calculateTotalAmountFromGioHang();
        txtTongTien.setText(decimalFormat.format(totalAmount));
        // Cập nhật cột Tổng sản phẩm trong bảng tblHoaDon
        updateTotalQuantityInHoaDon(totalQuantityFromGioHang);
    }
    
    private void updateTotalQuantityInHoaDon(int totalQuantity) {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            tblModelHD.setValueAt(totalQuantity, selectedRow, 4); // Cập nhật giá trị tại cột Tổng sản phẩm
        }
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
    
    private void SelectDataTableGioHang(List<ChiTietSanPhamViewModel> listSP) {
        int stt = 0;
        tblModelGH.setRowCount(0);
        for (ChiTietSanPhamViewModel sp : listSP) {
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
    
    public void getImel(List<String> imels) {
        // Duyệt qua danh sách imel và thêm giá trị vào txtGetImel
        for (String imel : imels) {
            textAreaImel.append(imel + "\n");
        }
        System.out.println(imels);
    }
    
    public void getCTSP(List<String> ctsps) {
        // Duyệt qua danh sách ctsp và thêm giá trị vào txtgetIDCTSP
        for (String ctsp : ctsps) {
            textAreaCtsp.append(ctsp + "\n");
        }
        System.out.println(ctsps);
    }
    
    public List<HoaDonChiTietEntity> getFormDataHDCT() {
        int index = tblGioHang.getRowCount();
        String idHD = txtMaHD.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDateTime ngayThucTe = LocalDateTime.now();
        String giaBanStr = (String) tblGioHang.getValueAt(index - 1, 10);

        // Remove commas from giaBanStr
        String giaBanWithoutCommas = giaBanStr.replaceAll(",", "");

        // Parse giaBanWithoutCommas to a BigDecimal
        BigDecimal giaBan = new BigDecimal(giaBanWithoutCommas);
        
        List<HoaDonChiTietEntity> hdctList = new ArrayList<>();
        
        String[] idCtsp = textAreaCtsp.getText().split("\n");
        String[] idImels = textAreaImel.getText().split("\n");

        // Lấy số lượng phần tử tối đa giữa idCtsp và idImels
        int maxSize = Math.max(idCtsp.length, idImels.length);

        // Duyệt qua tất cả các phần tử trong cả hai mảng idCtsp và idImels
        for (int i = 0; i < maxSize; i++) {
            String idImel = (i < idImels.length) ? idImels[i].trim() : ""; // Lấy idImel tại vị trí i hoặc rỗng nếu vượt quá chỉ số mảng
            String idCtsps = (i < idCtsp.length) ? idCtsp[i].trim() : ""; // Lấy idCtsps tại vị trí i hoặc rỗng nếu vượt quá chỉ số mảng
            HoaDonChiTietEntity hdct = new HoaDonChiTietEntity(idImel, idCtsps, idHD, giaBan, 1, ngayThucTe, nhanVien, ngayThucTe, nhanVien);
            hdctList.add(hdct);
        }
        
        return hdctList;
    }
    
    private void cleardata() {
        txtMaKH.setText("");
        txtGetTenKH.setText("Khách Bán Lẻ");
        txtMaHD.setText("");
        txtNgayTao.setText("");
        txtNgayThanhToan.setText("");
        txtMaNV.setText("");
        txtSetTenKH.setText("");
        txtTongTien.setText("0");
        cboPgg.setSelectedItem(null);
        cboHTT.setSelectedIndex(0);
        
        String[] imel = getImelDelete.getText().split("\n");
        List<String> idImelList = new ArrayList<>();
        
        for (String imels : imel) {
            imels = imels.trim();
            if (!imels.isEmpty()) {
                idImelList.add(imels);
            }
        }
        deleteTableGioHang(bhService.deleteGioHang(idImelList));
        showDataTableSP(bhService.getSP());
        int index = tblHoaDon.getSelectedRow();
        HoaDonViewModel hd = bhService.getHD().get(index);
        bhService.updateDeleteHD(hd.getIdHD());
        showDataTableHoaDon(bhService.getHD());
    }
    
    private boolean checkSP() {
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonCustom5 = new mobileworld.swing.ButtonCustom();
        txtTimKiemHD = new mobileworld.swing.TextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new mobileworld.swing.Table();
        btnQuetQR = new mobileworld.swing.ButtonCustom();
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

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel1.setOpaque(false);

        buttonCustom5.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-receipt-24.png"))); // NOI18N
        buttonCustom5.setText("Thêm Hóa Đơn");
        buttonCustom5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom5ActionPerformed(evt);
            }
        });

        txtTimKiemHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTimKiemHD.setLabelText("Tìm Kiếm");
        txtTimKiemHD.setLineColor(new java.awt.Color(102, 102, 102));
        txtTimKiemHD.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtTimKiemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHDActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(null);

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Ngày Tạo", "Mã Nhân Viên", "Tổng SP", "Trạng Thái"
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
        btnQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
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
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên Sản Phẩm", "NSX", "Màn Hình", "CPU", "Pin", "Số Lượng", "Giá"
            }
        ));
        jScrollPane5.setViewportView(tblSP);
        if (tblSP.getColumnModel().getColumnCount() > 0) {
            tblSP.getColumnModel().getColumn(0).setMinWidth(20);
            tblSP.getColumnModel().getColumn(0).setMaxWidth(40);
            tblSP.getColumnModel().getColumn(2).setMinWidth(150);
            tblSP.getColumnModel().getColumn(2).setMaxWidth(200);
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
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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
        buttonCustom3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom3ActionPerformed(evt);
            }
        });

        buttonCustom8.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom8.setText("Thanh Toán HĐ");
        buttonCustom8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
                        .addGap(14, 14, 14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboPgg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(buttonCustom3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void cboPggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPggActionPerformed
        // TODO add your handling code here:
        String tenPGG = (String) cboPgg.getSelectedItem();
        float phanTramGiam = pggService.layPGG(tenPGG);
        float tongTien = 0;
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
            jLabel14.setText(decimalFormat.format(tongTien * ((100 - phanTramGiam)) / 100));
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
        // TODO add your handling code here:

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

    }//GEN-LAST:event_txtTienKhachCKKeyReleased

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        ThongTinKhachHangDialog thongTinKhachHangDialog = new ThongTinKhachHangDialog(this);
        thongTinKhachHangDialog.setVisible(true);
    }//GEN-LAST:event_buttonCustom1ActionPerformed

    private void buttonCustom5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom5ActionPerformed
        int checkAddHD = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm Hóa đơn không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
        if (checkAddHD == JOptionPane.YES_OPTION) {
            HoaDon hd = new HoaDon();
            String idNV = SessionStorage.getInstance().getUsername();
            boolean result = bhService.addNewBlankInvoice(hd, idNV);
            if (result) {
                listHDM = bhService.getHD();
                showDataTableHoaDon(listHDM);
                
                if (tblHoaDon.getRowCount() > 0) {
                    tblHoaDon.setRowSelectionInterval(0, 0); // Select the first row
                    mouseClickHD(); // Trigger click event for the first row
                }
                
                System.out.println("Thêm hóa đơn trống thành công!");
            } else {
                // Xử lý thất bại
                System.out.println("Thêm hóa đơn trống thất bại!");
            }
        } else if (checkAddHD == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn NO");
        }
    }//GEN-LAST:event_buttonCustom5ActionPerformed
    
    private void mouseClickHD() {
        int index = tblHoaDon.getSelectedRow();

        // Check if a row is selected
        if (index < 0) {
            return;
        }
        
        HoaDonViewModel hdvm = bhService.getHD().get(index);
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
        SelectDataTableGioHang(bhService.getSPTuHoaDon(txtMaHD.getText()));
    }
    

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        mouseClickHD();
    }//GEN-LAST:event_tblHoaDonMouseClicked
    

    private void buttonCustom8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom8ActionPerformed
        String tongTienText = txtTongTien.getText();
        String tongTienSauKhiGiamText = jLabel14.getText();
        
        if (tongTienText.isEmpty() || tongTienSauKhiGiamText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin trước khi thanh toán.");
            return;
        }

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

        // Thực hiện thanh toán
        if (!bhService.ThanhToanHD(hoaDon, hdctList, hoaDon.getIdHD())) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thanh toán. Vui lòng thử lại.");
            return;
        }

        // Cập nhật giao diện sau khi thanh toán
        showDataTableHoaDon(bhService.getHD());
        JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
    }//GEN-LAST:event_buttonCustom8ActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        ReadQRCode rqr = new ReadQRCode();
        rqr.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void txtTimKiemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHDActionPerformed
        // TODO add your handling code here:
        listHDM = bhService.searchHD(txtTimKiemHD.getText());
        showDataTableHoaDon(listHDM);
    }//GEN-LAST:event_txtTimKiemHDActionPerformed

    private void txtGetTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGetTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGetTenKHActionPerformed

    private void txtTongTienPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTongTienPropertyChange
        String getTongTien = txtTongTien.getText().trim();
        if (!getTongTien.equals("0đ") && !getTongTien.equals("0")) {
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
        } else {
            List<PhieuGiamGia> dsPGGAll = pggService.getAll();
            setDataCboPhieuGG(dsPGGAll);
        }

    }//GEN-LAST:event_txtTongTienPropertyChange

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void buttonCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom3ActionPerformed
        cleardata();
    }//GEN-LAST:event_buttonCustom3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnQuetQR;
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom3;
    private mobileworld.swing.ButtonCustom buttonCustom5;
    private mobileworld.swing.ButtonCustom buttonCustom8;
    private mobileworld.swing.Combobox cboCPU;
    private javax.swing.JComboBox<String> cboHTT;
    private mobileworld.swing.Combobox cboManHinh;
    private mobileworld.swing.Combobox cboNSX;
    private javax.swing.JComboBox<String> cboPgg;
    private mobileworld.swing.Combobox cboPin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private mobileworld.swing.TextField txtTimKiemHD;
    private mobileworld.swing.TextField txtTimKiemSP;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables

}
