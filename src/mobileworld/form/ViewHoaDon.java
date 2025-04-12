package mobileworld.form;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import mobileworld.dialog.ThongTinDonHangDiaLog;
import mobileworld.event.DataChangeListener;
import mobileworld.event.EventThongTinDonHang;
import mobileworld.service.HoaDonService.HoaDonCTService;
import mobileworld.service.HoaDonService.HoaDonService;
import mobileworld.viewModel.HoaDonChiTietModel;
import mobileworld.viewModel.HoaDonModel;
import mobileworld.model.HoaDon;
import mobileworld.model.MauSac;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.PhuongThucThanhToan;
import mobileworld.model.Pin;
import mobileworld.print.print.ReportManager;
import mobileworld.print.print.ReportManagerDH;
import mobileworld.print.print.model.FieldReportPayment;
import mobileworld.print.print.model.ParameterReportPayment;
import mobileworld.qrcode.qrcode;
import mobileworld.qrcode.qrcode.QRCodeListener;
import mobileworld.service.BanHangService.BanHangService;
import mobileworld.service.ChiTietSanPhamService.MauSacService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
import mobileworld.service.HoaDonService.LichSuHDService;
import mobileworld.service.HoaDonService.PhuongThucThanhToanService;
import mobileworld.tablecutoms.pay.PanelAction;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
//import mobileworld.service.QRCodeScannerApp;
import mobileworld.viewModel.LichSuHDModel;
import mobileworld.tablecutoms.pay.TableActionCellEditor;
import mobileworld.tablecutoms.pay.TableActionCellEditorDelete;
import mobileworld.tablecutoms.pay.TableActionCellRender;
import mobileworld.tablecutoms.pay.TableActionCellRenderDelete;
import mobileworld.tablecutoms.pay.TableActionEventPay;

//import qrcode.qrcode.QRCodeListener;
public final class ViewHoaDon extends javax.swing.JPanel implements QRCodeListener, DataChangeListener, EventThongTinDonHang {

    private String selectedInvoiceId;
    private final ThongTinDonHangDiaLog donHangDiaLog;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    List<HoaDon> listHD = new ArrayList<>();
    List<HoaDonViewModel> list = new ArrayList<>();
    List<HoaDonModel> listHDM = new ArrayList<>();

    List<HoaDonChiTietModel> list22 = new ArrayList<>();
    List<LichSuHDModel> listLS2 = new ArrayList<>();
    List<HoaDonChiTietModel> list2 = new ArrayList<>();

    List<LichSuHDModel> listLS = new ArrayList<>();
    List<PhuongThucThanhToan> listPTTT = new ArrayList<>();

    NhaSanXuatService NsxService = new NhaSanXuatService();
    PinService pinService = new PinService();
    MauSacService mauSacService = new MauSacService();

    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultTableModel tableModelDangGiao = new DefaultTableModel();
    DefaultTableModel tableModelHuyGiao = new DefaultTableModel();
    DefaultTableModel tableModel2 = new DefaultTableModel();
    DefaultTableModel tableModel3 = new DefaultTableModel();

    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModel1 = new DefaultComboBoxModel();
    DefaultComboBoxModel cbbNsx = new DefaultComboBoxModel();
    DefaultComboBoxModel cbbPin = new DefaultComboBoxModel();
    DefaultComboBoxModel cbbMauSac = new DefaultComboBoxModel();

    List<HoaDonModel> search = new ArrayList<>();

    PhuongThucThanhToanService srPTTT = new PhuongThucThanhToanService();
    HoaDonService sr = new HoaDonService();
    HoaDonCTService srCT = new HoaDonCTService();
    LichSuHDService srLSHD = new LichSuHDService();
    BanHangService banHangService = new BanHangService();
    List<HoaDonChiTietModel> boLocHDCT = new ArrayList<>();
    List<HoaDonViewModel> choThanhToan = new ArrayList<>();
    List<HoaDonViewModel> daThanhToan = new ArrayList<>();
    List<HoaDonViewModel> choGiao = new ArrayList<>();
    List<HoaDonViewModel> dangGiao = new ArrayList<>();
    List<HoaDonViewModel> HuyGiao = new ArrayList<>();
    List<HoaDonViewModel> listAll = new ArrayList<>();
    List<HoaDonViewModel> seacrchHD = new ArrayList<>();
    List<HoaDonViewModel> seacrchGia = new ArrayList<>();

    public ViewHoaDon() {
        initComponents();
        donHangDiaLog = new ThongTinDonHangDiaLog(selectedInvoiceId);
        donHangDiaLog.changeListener.addDataChangeListener(this);
        donHangDiaLog.changeListener.setEventDataChangeListener(this);
        list = sr.getAllHD();
        listPTTT = srPTTT.getAll();

        tableModel = (DefaultTableModel) tblHienThi1.getModel();
        tableModelDangGiao = (DefaultTableModel) tblHienThi1.getModel();
        tableModelHuyGiao = (DefaultTableModel) tblHienThi1.getModel();
        showDataTable(list);
        tblHienThi1.setDefaultEditor(Object.class, null);
        tblHienThi2.setDefaultEditor(Object.class, null);
        tblHienThi3.setDefaultEditor(Object.class, null);

        cbbPin = (DefaultComboBoxModel) cboPin.getModel();
        cbbNsx = (DefaultComboBoxModel) cboNsx.getModel();
        cbbMauSac = (DefaultComboBoxModel) cboMauSac.getModel();

        setOpaque(false);
        badge();
        setDataCboMauSac(mauSacService.getAll());
        setDataCboPin(pinService.getAll());
        setDataCboNsx(NsxService.getAll());

//        tblHienThi1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                int index = tblHienThi1.getSelectedRow();
//                if (index >= 0 && evt.getClickCount() == 2) {
//                    String idHD = (String) tblHienThi1.getValueAt(index, 1);
//                    ThongTinDonHangDiaLog donHangDiaLog = new ThongTinDonHangDiaLog(idHD);
//                    donHangDiaLog.setVisible(true);
//                }
//            }
//        });
    }

    public DataChangeListener changeListener = () -> {
    };

    public void setDataChangeListener(DataChangeListener listener) {
        this.changeListener = listener;
    }

    @Override
    public void onDataChange() {
        badge();
        showDataTable(sr.getTrangThaiHD(1));
    }

    @Override
    public boolean ThanhToanGiaoHangHD(String idHD) {
        onDataChange();
        return true;
    }

    private void badge() {
        String countCG = banHangService.countChoGiaoHang() + "";
        String countDG = banHangService.countDangGiaoHang() + "";
        String countCTT = banHangService.countChuaThanhToan() + "";
        String countDTT = banHangService.countDaThanhToan() + "";
        String countAll = banHangService.countAll() + "";
        badgeButton4.setText(countCTT);
        badgeButton8.setText(countDTT);
        badgeButton9.setText(countCG);
        badgeButton10.setText(countDG);
        badgeButton11.setText(countAll);
    }

    private void setDataCboNsx(List<NhaSanXuat> setNsx) {
        cbbNsx.removeAllElements();

        for (NhaSanXuat nsx : setNsx) {
            cbbNsx.addElement(nsx.getTenNsx());
        }
        cbbNsx.setSelectedItem(null);
    }

    private void setDataCboPin(List<Pin> setPin) {
        cbbPin.removeAllElements();

        for (Pin pinE : setPin) {
            cbbPin.addElement(pinE.getDungLuongPin());
        }
        cboPin.setSelectedItem(null);
    }

    private void setDataCboMauSac(List<MauSac> setMS) {
        cbbMauSac.removeAllElements();

        for (MauSac mauSac : setMS) {
            cbbMauSac.addElement(mauSac.getTenMau());
        }
        cboMauSac.setSelectedItem(null);
    }

    @Override
    public void onQRCodeScanned(String result) {

        List<HoaDonViewModel> listQR = sr.getAllQR(result);
        showDataTable(listQR);
        if (!listQR.isEmpty()) {
            tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
            List<HoaDonChiTietModel> listShowDatatbl2 = srCT.getAll(result);
            showDataTable2(listShowDatatbl2);

            tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
            List<LichSuHDModel> listLichSuHD = srLSHD.getAll(result);
            showDataTable3(listLichSuHD);

            System.out.println("Nhận giá trị" + result);

        } else {
            // Nếu danh sách rỗng, hiển thị thông báo yêu cầu người dùng thử lại hoặc chọn QR khác
            JOptionPane.showMessageDialog(null, "Quét không thành công. Vui lòng thử lại hoặc chọn QR khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    public void showDataTable(List<HoaDonViewModel> list1) {
        tableModel.setRowCount(0);
        int tt = 0;

        for (HoaDonViewModel hoaDonModel : list1) {
            tt++;
            String trangThai;
            trangThai = switch (hoaDonModel.getTrangthai()) {
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

            String tongTien = "";
            if (hoaDonModel.getTongTienSauGiam() != null) {
                tongTien = decimalFormat.format(hoaDonModel.getTongTienSauGiam());
            }

            // Thêm vào bảng
            tableModel.addRow(new Object[]{
                tt,
                hoaDonModel.getIdHD(),
                hoaDonModel.getIdNV(),
                hoaDonModel.getTenKH(),
                hoaDonModel.getSdtKH(),
                hoaDonModel.getDiaChiKH(),
                hoaDonModel.getNgayThanhToan(),
                hoaDonModel.getGetTenKieuThanhToan(),
                tongTien,
                trangThai
            });
        }
    }

    public void showDataTableGiaoHang(List<HoaDonViewModel> list1) {
        tableModelDangGiao.setRowCount(0);
        int tt = 0;

        for (HoaDonViewModel hoaDonModel : list1) {
            tt++;
            String trangThai;
            switch (hoaDonModel.getTrangthai()) {
                case 0:
                    trangThai = "Chờ Thanh Toán";
                    break;
                case 1:
                    trangThai = "Đã thanh toán";
                    break;
                case 2:
                    trangThai = "Đang Giao";
                    break;
                case 3:
                    trangThai = "Chờ Giao";
                    break;
                default:
                    trangThai = "Hủy Giao";
                    break;
            }

            String tongTien = "";
            if (hoaDonModel.getTongTienSauGiam() != null) {
                tongTien = decimalFormat.format(hoaDonModel.getTongTienSauGiam());
            }

            // Thêm vào bảng
            tableModelDangGiao.addRow(new Object[]{
                tt,
                hoaDonModel.getIdHD(),
                hoaDonModel.getIdNV(),
                hoaDonModel.getTenKH(),
                hoaDonModel.getSdtKH(),
                hoaDonModel.getDiaChiKH(),
                hoaDonModel.getNgayThanhToan(),
                hoaDonModel.getGetTenKieuThanhToan(),
                tongTien,
                trangThai
            });
        }
    }

    public void showDataTableHuyGiaoHang(List<HoaDonViewModel> list1) {
        tableModelHuyGiao.setRowCount(0);
        int tt = 0;

        for (HoaDonViewModel hoaDonModel : list1) {
            tt++;
            String trangThai;
            switch (hoaDonModel.getTrangthai()) {
                case 0:
                    trangThai = "Chờ Thanh Toán";
                    break;
                case 1:
                    trangThai = "Đã thanh toán";
                    break;
                case 2:
                    trangThai = "Đang Giao";
                    break;
                case 3:
                    trangThai = "Chờ Giao";
                    break;
                default:
                    trangThai = "Hủy Giao";
                    break;
            }

            String tongTien = "";
            if (hoaDonModel.getTongTienSauGiam() != null) {
                tongTien = decimalFormat.format(hoaDonModel.getTongTienSauGiam());
            }

            // Thêm vào bảng
            tableModelHuyGiao.addRow(new Object[]{
                tt,
                hoaDonModel.getIdHD(),
                hoaDonModel.getIdNV(),
                hoaDonModel.getTenKH(),
                hoaDonModel.getSdtKH(),
                hoaDonModel.getDiaChiKH(),
                hoaDonModel.getNgayThanhToan(),
                hoaDonModel.getGetTenKieuThanhToan(),
                tongTien,
                trangThai
            });
        }

    }

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
            String formattedNgayGio = lichSuHDModel.getCreatedAt().format(dateFormatter);

            tableModel3.addRow(new Object[]{
                stt,
                lichSuHDModel.getIdNV(),
                formattedNgayGio,
                lichSuHDModel.getHanhDong()
            });
        }
    }

    @Override
    public void show() {

        int index = tblHienThi1.getSelectedRow();
        HoaDonViewModel hdm = list.get(index);

        tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
        list2 = srCT.getAll(hdm.getIdHD());
        showDataTable2(list2);

        tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
        listLS = srLSHD.getAll(hdm.getIdHD());
        showDataTable3(listLS);
    }

    public InputStream generateQrcodeWithLogo(String invoiceNumber) {
        try {
            // Kiểm tra xem invoiceNumber có giá trị không
            if (invoiceNumber == null || invoiceNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn không hợp lệ");
                return null;
            }
            int margin = 10; // Đặt margin là 10 (hoặc giá trị bạn muốn)
            int qrSize = 5000; // Kích thước mã QR

            // Sử dụng mã hóa đơn để tạo mã QR
            BitMatrix bitMatrix = new MultiFormatWriter().encode(invoiceNumber, BarcodeFormat.QR_CODE, qrSize, qrSize);

            // Thêm margin vào BitMatrix
            BitMatrix withMargin = addMargin(bitMatrix, margin);

            // Chuyển đổi bitMatrix thành hình ảnh
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(withMargin);

            // Đọc logo từ file (đây là ví dụ, bạn cần thay đổi đường dẫn đến logo của bạn)
            BufferedImage logoImage = ImageIO.read(new File("/icon/logoMobileWorld.png"));

            // Tạo một hình ảnh mới có kích thước bằng qrImage
            BufferedImage combined = new BufferedImage(qrSize, qrSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();

            // Vẽ qrImage lên combined
            g.drawImage(qrImage, 0, 0, null);

            // Tính toán vị trí cho logo
            int logoSize = qrSize / 4; // Kích thước logo là 1/5 của qrSize
            int x = (qrSize - logoSize) / 2;
            int y = (qrSize - logoSize) / 2;

            // Vẽ logo lên combined
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.drawImage(logoImage, x, y, logoSize, logoSize, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Kết thúc vẽ
            g.dispose();

            // Chuyển đổi hình ảnh combined thành mảng byte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(combined, "png", outputStream);

            // Trả về InputStream từ mảng byte
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi cho người dùng hoặc log lỗi
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo mã QR Code: " + e.getMessage());
            return null;
        }
    }

    private static BitMatrix addMargin(BitMatrix matrix, int margin) {
        int originalWidth = matrix.getWidth();
        int originalHeight = matrix.getHeight();
        int newWidth = originalWidth + (margin * 2);
        int newHeight = originalHeight + (margin * 2);

        BitMatrix withMargin = new BitMatrix(newWidth, newHeight);
        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                if (matrix.get(x, y)) {
                    withMargin.set(x + margin, y + margin);
                }
            }
        }
        return withMargin;
    }

    public void printHD(String selectedInvoiceId) {
        try {
            // Validate selectedInvoiceId
            if (selectedInvoiceId == null || selectedInvoiceId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn không hợp lệ");
                return;
            }

            // Use selectedInvoiceId to generate QR code
            InputStream qrCodeStream = generateQrcodeWithLogo(selectedInvoiceId);
            if (qrCodeStream == null) {
                JOptionPane.showMessageDialog(this, "Không thể tạo mã QR Code.");
                return;
            }

            // Find the index of the invoice with the selectedInvoiceId
            int rowIndex = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIdHD().equals(selectedInvoiceId)) {
                    rowIndex = i;
                    break;
                }
            }

            if (rowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã đã chọn.");
                return;
            }

            // Select the row in tblHienThi1 corresponding to the selected invoice
            tblHienThi1.setRowSelectionInterval(rowIndex, rowIndex);
            HoaDonViewModel hd = list.get(rowIndex);
            tableModel = (DefaultTableModel) tblHienThi2.getModel();
            list2 = srCT.getAll(hd.getIdHD());
            showDataTable2(list2);
            List<FieldReportPayment> fields = new ArrayList<>();

            // Check if tblHienThi2 contains any rows before accessing them
            if (!list2.isEmpty()) {
                // Lặp qua từng hàng trong bảng
                for (int i = 0; i < list2.size(); i++) {
                    // Lấy dữ liệu từ mỗi hàng
                    HoaDonChiTietModel hdct = list2.get(i);
                    String tenSP = hdct.getTenDSP();
                    String mauSac = hdct.getTenMau();
                    String pin = hdct.getDungLuongPin();
                    DecimalFormat decimalFormatTT = new DecimalFormat("###,###");
                    BigDecimal giaBan = hdct.getGiaBan();
                    String formattedGiaBan = decimalFormatTT.format(giaBan);
                    fields.add(new FieldReportPayment(i + 1, tenSP, mauSac, pin, formattedGiaBan + " VND", formattedGiaBan + " VND"));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Danh sách chi tiết hóa đơn trống.");
                return;
            }

            // Kiểm tra nếu có dữ liệu để tạo báo cáo
            if (!fields.isEmpty()) {
                // Tạo tham số để in báo cáo
                ParameterReportPayment dataPrint = new ParameterReportPayment(hd.getIdHD(),
                        String.valueOf(hd.getNgayThanhToan()),
                        hd.getTenKH(),
                        hd.getSdtKH(),
                        hd.getDiaChiKH(),
                        hd.getGetTenKieuThanhToan(),
                        hd.getIdNV(),
                        String.valueOf(hd.getTongTienSauGiam()),
                        qrCodeStream,
                        fields);

                ReportManager reportManager = ReportManager.getInstance();
                // Check parameters before printing the report
                reportManager.checkReportParameters(dataPrint);
                // Check JRXML path and compilation before printing the report
                reportManager.checkJRXMLPath();
                reportManager.checkCompilation();
                // Print the report after ensuring all preparations are done
                reportManager.printReportPayment(dataPrint);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để tạo báo cáo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printHDDH(String selectedInvoiceId) {
        try {
            // Validate selectedInvoiceId
            if (selectedInvoiceId == null || selectedInvoiceId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn không hợp lệ");
                return;
            }

            // Use selectedInvoiceId to generate QR code
            InputStream qrCodeStream = generateQrcodeWithLogo(selectedInvoiceId);
            if (qrCodeStream == null) {
                JOptionPane.showMessageDialog(this, "Không thể tạo mã QR Code.");
                return;
            }

            int rowIndex = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIdHD().equals(selectedInvoiceId)) {
                    rowIndex = i;
                    break;
                }
            }

            if (rowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã đã chọn.");
                return;
            }

            // Select the row in tblHienThi1 corresponding to the selected invoice
            tblHienThi1.setRowSelectionInterval(rowIndex, rowIndex);
            HoaDonViewModel hd = list.get(rowIndex);
            tableModel = (DefaultTableModel) tblHienThi2.getModel();
            list2 = srCT.getAll(hd.getIdHD());
            showDataTable2(list2);
            List<FieldReportPayment> fields = new ArrayList<>();

            // Check if tblHienThi2 contains any rows before accessing them
            if (tblHienThi2.getRowCount() > 0) {
                // Retrieve data based on mouse click (first invoice)
                for (int i = 0; i < tblHienThi2.getRowCount(); i++) {
                    // Đảm bảo index không vượt quá số lượng phần tử trong list2
                    if (i < list2.size()) {
                        // Lấy dữ liệu từ mỗi hàng
                        HoaDonChiTietModel hdct = list2.get(i);
                        String tenSP = hdct.getTenDSP();
                        String mauSac = hdct.getTenMau();
                        String pin = hdct.getDungLuongPin();
                        DecimalFormat decimalFormatTT = new DecimalFormat("###,###");
                        BigDecimal giaBan = hdct.getGiaBan();
                        String formattedGiaBan = decimalFormatTT.format(giaBan);
                        fields.add(new FieldReportPayment(i + 1, tenSP, mauSac, pin, formattedGiaBan + " VND", formattedGiaBan + " VND"));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Danh sách chi tiết hóa đơn trống.");
                return;
            }

            // Kiểm tra nếu có dữ liệu để tạo báo cáo
            if (!fields.isEmpty()) {
                // Tạo tham số để in báo cáo
                ParameterReportPayment dataPrint = new ParameterReportPayment(hd.getIdHD(),
                        String.valueOf(hd.getNgayThanhToan()),
                        hd.getTenKH(),
                        hd.getSdtKH(),
                        hd.getDiaChiKH(),
                        hd.getGetTenKieuThanhToan(),
                        hd.getIdNV(),
                        String.valueOf(hd.getTongTienSauGiam()),
                        qrCodeStream,
                        String.valueOf(hd.getNgayNhan()),
                        fields);

                ReportManagerDH reportManagerDH = ReportManagerDH.getInstance();
                // Check parameters before printing the report
                reportManagerDH.checkReportParameters(dataPrint);
                // Check JRXML path and compilation before printing the report
                reportManagerDH.checkJRXMLPath();
                reportManagerDH.checkCompilation();
                // Print the report after ensuring all preparations are done
                reportManagerDH.printReportPayment(dataPrint);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để tạo báo cáo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locSP() {
        int index = tblHienThi1.getSelectedRow();

        // Kiểm tra xem có hàng nào được chọn không
        if (index >= 0) {
            // Lấy đối tượng HoaDonViewModel từ danh sách hoá đơn dựa vào chỉ số hàng đã chọn
            HoaDonViewModel hdm = sr.getAllHD().get(index);

            // Lấy ID hoá đơn từ đối tượng hoá đơn
            String idHD = hdm.getIdHD();

            // Lấy các giá trị từ các combobox để lọc dữ liệu
            String nsx = (String) cboNsx.getSelectedItem();
            String mauSac = (String) cboMauSac.getSelectedItem();
            String pin = (String) cboPin.getSelectedItem();

            // Lấy danh sách dữ liệu lọc được từ Service và hiển thị lên bảng thứ hai (tblHienThi2)
            showDataTable2(srCT.getAll(idHD, nsx, mauSac, pin));
        }
    }

    private void clearDS() {
        choGiao.clear();
        dangGiao.clear();
        choThanhToan.clear();
        daThanhToan.clear();
        HuyGiao.clear();

        tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
        list2 = new ArrayList<>();
        showDataTable2(list2);

        tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
        listLS = new ArrayList<>();
        showDataTable3(listLS);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHienThi1 = new mobileworld.swing.Table();
        jPanel3 = new javax.swing.JPanel();
        txtSearch = new mobileworld.swing.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();
        reset = new mobileworld.swing.ButtonCustom();
        sliderGradient1 = new mobileworld.test.raven.slider.SliderGradient();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        badgeButton11 = new mobileworld.swing.BadgeButton();
        buttonCustom9 = new mobileworld.swing.ButtonCustom();
        jPanel6 = new javax.swing.JPanel();
        badgeButton4 = new mobileworld.swing.BadgeButton();
        buttonCustom2 = new mobileworld.swing.ButtonCustom();
        jPanel13 = new javax.swing.JPanel();
        badgeButton8 = new mobileworld.swing.BadgeButton();
        buttonCustom6 = new mobileworld.swing.ButtonCustom();
        jPanel14 = new javax.swing.JPanel();
        badgeButton9 = new mobileworld.swing.BadgeButton();
        buttonCustom7 = new mobileworld.swing.ButtonCustom();
        jPanel15 = new javax.swing.JPanel();
        badgeButton10 = new mobileworld.swing.BadgeButton();
        buttonCustom8 = new mobileworld.swing.ButtonCustom();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHienThi2 = new mobileworld.swing.Table();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHienThi3 = new mobileworld.swing.Table();
        jPanel9 = new javax.swing.JPanel();
        txtSearchHDCT = new mobileworld.swing.TextField();
        cboNsx = new mobileworld.swing.Combobox();
        cboMauSac = new mobileworld.swing.Combobox();
        cboPin = new mobileworld.swing.Combobox();
        buttonCustom4 = new mobileworld.swing.ButtonCustom();
        btnXuatHoaDon = new mobileworld.swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel1.setOpaque(false);

        jScrollPane1.setBorder(null);

        tblHienThi1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Mã NV ", "Tên KH", "SĐT KH", "Địa chỉ", "Ngày TT", "Loại TT", "Tổng tiền ", "Trạng thái"
            }
        ));
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(45, 47, 90))); // NOI18N
        jPanel3.setOpaque(false);

        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSearch.setLabelText("Tìm Kiếm");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Giá Tối Thiểu");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Giá Tối Đa");

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-qr-code-30.png"))); // NOI18N
        buttonCustom1.setText("Quét QR");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        reset.setForeground(new java.awt.Color(255, 255, 255));
        reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-restore-30.png"))); // NOI18N
        reset.setText("Làm Mới");
        reset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        sliderGradient1.setMaximum(200000000);
        sliderGradient1.setColor1(new java.awt.Color(0, 51, 102));
        sliderGradient1.setColor2(new java.awt.Color(0, 51, 102));
        sliderGradient1.setOpaque(false);
        sliderGradient1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGradient1StateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(12, 45, 87));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0 VNĐ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderGradient1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sliderGradient1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tình Trạng Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 10, 10));

        jPanel16.setOpaque(false);
        jPanel16.setLayout(null);

        badgeButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-new-product-30.png"))); // NOI18N
        jPanel16.add(badgeButton11);
        badgeButton11.setBounds(0, 0, 52, 35);

        buttonCustom9.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom9.setText("Tất Cả");
        buttonCustom9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom9ActionPerformed(evt);
            }
        });
        jPanel16.add(buttonCustom9);
        buttonCustom9.setBounds(2, 0, 200, 35);

        jPanel5.add(jPanel16);

        jPanel6.setOpaque(false);

        badgeButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-paid-30.png"))); // NOI18N

        buttonCustom2.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom2.setText("Chờ Thanh Toán");
        buttonCustom2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(badgeButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(badgeButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.add(jPanel6);

        jPanel13.setOpaque(false);
        jPanel13.setLayout(null);

        badgeButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-paid-30.png"))); // NOI18N
        jPanel13.add(badgeButton8);
        badgeButton8.setBounds(0, 0, 52, 35);

        buttonCustom6.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom6.setText("Đã Thanh Toán");
        buttonCustom6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom6ActionPerformed(evt);
            }
        });
        jPanel13.add(buttonCustom6);
        buttonCustom6.setBounds(2, 0, 200, 35);

        jPanel5.add(jPanel13);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(null);

        badgeButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-delivery-30 (1).png"))); // NOI18N
        jPanel14.add(badgeButton9);
        badgeButton9.setBounds(0, 0, 52, 35);

        buttonCustom7.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom7.setText("Chờ Giao");
        buttonCustom7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom7ActionPerformed(evt);
            }
        });
        jPanel14.add(buttonCustom7);
        buttonCustom7.setBounds(2, 0, 200, 35);

        jPanel5.add(jPanel14);

        jPanel15.setOpaque(false);
        jPanel15.setLayout(null);

        badgeButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-delivery-30 (1).png"))); // NOI18N
        jPanel15.add(badgeButton10);
        badgeButton10.setBounds(0, 0, 52, 35);

        buttonCustom8.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom8.setText("Đang Giao");
        buttonCustom8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom8ActionPerformed(evt);
            }
        });
        jPanel15.add(buttonCustom8);
        buttonCustom8.setBounds(2, 0, 200, 35);

        jPanel5.add(jPanel15);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
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
                "STT", "Mã CTSP", "Tên SP", "NSX", "Màu", "Pin", "Imel", "Giá bán", "Thành Tiền"
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc Hóa Đơn Chi Tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(12, 45, 87))); // NOI18N
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        txtSearchHDCT.setLabelText("Tìm Kiếm");
        txtSearchHDCT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchHDCTKeyReleased(evt);
            }
        });
        jPanel9.add(txtSearchHDCT);

        cboNsx.setLabeText("Nhà Sản Xuất");
        cboNsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNsxActionPerformed(evt);
            }
        });
        jPanel9.add(cboNsx);

        cboMauSac.setLabeText("Màu");
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });
        jPanel9.add(cboMauSac);

        cboPin.setLabeText("Pin");
        cboPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPinActionPerformed(evt);
            }
        });
        jPanel9.add(cboPin);

        buttonCustom4.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-pdf-30 (1).png"))); // NOI18N
        buttonCustom4.setText("In Hóa Đơn");
        buttonCustom4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        buttonCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom4ActionPerformed(evt);
            }
        });
        jPanel9.add(buttonCustom4);

        btnXuatHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-excel-30.png"))); // NOI18N
        btnXuatHoaDon.setText("Xuất Execl");
        btnXuatHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHoaDonActionPerformed(evt);
            }
        });
        jPanel9.add(btnXuatHoaDon);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHoaDonActionPerformed
        int selectIndex = tblHienThi1.getSelectedRow();
        if (selectIndex == -1) {
            JOptionPane.showMessageDialog(this, "Chọn ít nhất 1 hóa đơn để xuất!");
            return;
        }
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
        if (index != -1) { // Ensure that a row is selected in the table
            // Retrieve the selected invoice ID
            selectedInvoiceId = list.get(index).getIdHD();

            int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn in Hoa Đơn đã chọn không?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                // If the user chooses Yes, print the selected invoice
                printHD(selectedInvoiceId);
            }
        } else {
            // If no row is selected, display an error message
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần in!");
        }
    }//GEN-LAST:event_buttonCustom4ActionPerformed

    private void tblHienThi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHienThi1MouseClicked
        int index = tblHienThi1.getSelectedRow();
        HoaDonViewModel hdm = null; // Initialize hdm as null

        if (index >= 0) { // Ensure index is valid
            if (!txtSearch.getText().isEmpty() && index < seacrchHD.size()) {
                hdm = seacrchHD.get(index);
            } else if (!seacrchGia.isEmpty() && index < seacrchGia.size()) {
                hdm = seacrchGia.get(index);
            } else {
                if (choGiao.isEmpty() && dangGiao.isEmpty() && choThanhToan.isEmpty() && daThanhToan.isEmpty() && HuyGiao.isEmpty()) {
                    if (index < list.size()) {
                        hdm = list.get(index);
                    }
                } else if (!choGiao.isEmpty() && index < choGiao.size()) {
                    hdm = choGiao.get(index);
                } else if (!dangGiao.isEmpty() && index < dangGiao.size()) {
                    hdm = dangGiao.get(index);
                } else if (!choThanhToan.isEmpty() && index < choThanhToan.size()) {
                    hdm = choThanhToan.get(index);
                } else if (!daThanhToan.isEmpty() && index < daThanhToan.size()) {
                    hdm = daThanhToan.get(index);
                } else if (!HuyGiao.isEmpty() && index < HuyGiao.size()) {
                    hdm = HuyGiao.get(index);
                }
            }
        }

        if (hdm != null) { // Check if hdm is not null before proceeding
            tableModel2 = (DefaultTableModel) tblHienThi2.getModel();
            list2 = srCT.getAll(hdm.getIdHD());
            showDataTable2(list2);

            tableModel3 = (DefaultTableModel) tblHienThi3.getModel();
            listLS = srLSHD.getAll(hdm.getIdHD());
            showDataTable3(listLS);
        }
    }//GEN-LAST:event_tblHienThi1MouseClicked

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

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        clearDS();
        cboMauSac.setSelectedItem(null);
        cboNsx.setSelectedItem(null);
        cboPin.setSelectedItem(null);
        txtSearch.setText("");
        txtSearchHDCT.setText("");
        sliderGradient1.setValue(0);
        jLabel9.setText("0 VNĐ");
        list = sr.getAllHD();
        showDataTable(list);
        TableColumn column = tblHienThi1.getColumnModel().getColumn(10);
        column.setCellRenderer(new TableActionCellRenderDelete());
        column.setCellEditor(new TableActionCellEditorDelete());
    }//GEN-LAST:event_resetActionPerformed

    private void sliderGradient1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGradient1StateChanged
        DecimalFormat decimalFormatGia = new DecimalFormat("#,###");
        int sliderValue = sliderGradient1.getValue();
        String formattedValue = decimalFormatGia.format(sliderValue);
        jLabel9.setText(formattedValue + " " + "VNĐ");

        // Chuyển đổi chuỗi thành kiểu số BigDecimal
        BigDecimal maxPrice = new BigDecimal(sliderValue);

        seacrchGia = sr.searchHDByMaxPrice(maxPrice);
        showDataTable(seacrchGia);
    }//GEN-LAST:event_sliderGradient1StateChanged

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String searchText = txtSearch.getText().trim();
        if (searchText.isEmpty()) {
            showDataTable(sr.getAllHD());
        } else {
            seacrchHD = sr.search(searchText);
            showDataTable(seacrchHD);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void cboNsxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNsxActionPerformed
        locSP();
    }//GEN-LAST:event_cboNsxActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        locSP();
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void cboPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPinActionPerformed
        locSP();
    }//GEN-LAST:event_cboPinActionPerformed

    private void txtSearchHDCTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchHDCTKeyReleased
        int index = tblHienThi1.getSelectedRow();

        if (index >= 0) {
            HoaDonViewModel hdm = sr.getAllHD().get(index);
            if (txtSearchHDCT.getText().equals("")) {
                showDataTable2(srCT.getAll(hdm.getIdHD()));
            } else {
                showDataTable2(srCT.searchAllFields(hdm.getIdHD(), txtSearchHDCT.getText()));
            }
        }
    }//GEN-LAST:event_txtSearchHDCTKeyReleased

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed
        clearDS();
        choThanhToan = sr.getTrangThaiHD(0);
        showDataTable(choThanhToan);
    }//GEN-LAST:event_buttonCustom2ActionPerformed

    private void buttonCustom6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom6ActionPerformed
        clearDS();
        daThanhToan = sr.getTrangThaiHD(1);
        showDataTable(daThanhToan);
    }//GEN-LAST:event_buttonCustom6ActionPerformed

    private void buttonCustom7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom7ActionPerformed
        clearDS();
        choGiao = sr.getTrangThaiHD(3);
        showDataTable(choGiao);
    }//GEN-LAST:event_buttonCustom7ActionPerformed

    private void buttonCustom8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom8ActionPerformed
        clearDS();
        dangGiao = sr.getTrangThaiHD(2);
        showDataTableGiaoHang(dangGiao);
    }//GEN-LAST:event_buttonCustom8ActionPerformed

    private void buttonCustom9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom9ActionPerformed
        clearDS();
        listAll = sr.getAllHD();
        showDataTable(listAll);
    }//GEN-LAST:event_buttonCustom9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.BadgeButton badgeButton10;
    private mobileworld.swing.BadgeButton badgeButton11;
    private mobileworld.swing.BadgeButton badgeButton4;
    private mobileworld.swing.BadgeButton badgeButton8;
    private mobileworld.swing.BadgeButton badgeButton9;
    private mobileworld.swing.ButtonCustom btnXuatHoaDon;
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private mobileworld.swing.ButtonCustom buttonCustom2;
    private mobileworld.swing.ButtonCustom buttonCustom4;
    private mobileworld.swing.ButtonCustom buttonCustom6;
    private mobileworld.swing.ButtonCustom buttonCustom7;
    private mobileworld.swing.ButtonCustom buttonCustom8;
    private mobileworld.swing.ButtonCustom buttonCustom9;
    private mobileworld.swing.Combobox cboMauSac;
    private mobileworld.swing.Combobox cboNsx;
    private mobileworld.swing.Combobox cboPin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private mobileworld.swing.ButtonCustom reset;
    private mobileworld.test.raven.slider.SliderGradient sliderGradient1;
    private mobileworld.swing.Table tblHienThi1;
    private mobileworld.swing.Table tblHienThi2;
    private mobileworld.swing.Table tblHienThi3;
    private mobileworld.swing.TextField txtSearch;
    private mobileworld.swing.TextField txtSearchHDCT;
    // End of variables declaration//GEN-END:variables

}
