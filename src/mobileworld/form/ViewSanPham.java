package mobileworld.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView;
import mobileworld.dialog.ReadQRCode;
import mobileworld.event.DataChangeListener;
import mobileworld.event.EventChiTietSP;
import mobileworld.model.BoNho;
import mobileworld.model.CPU;
import mobileworld.model.DongSP;
import mobileworld.model.ManHinh;
import mobileworld.model.MauSac;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.Pin;
import mobileworld.model.Ram;
import mobileworld.model.CameraSau;
import mobileworld.service.ChiTietSanPhamService.CpuService;
import mobileworld.service.ChiTietSanPhamService.DongSPService;
import mobileworld.service.ChiTietSanPhamService.ManHinhService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
import mobileworld.service.ChiTietSanPhamService.ChiTietSPService;
import mobileworld.viewModel.ChiTietSanPhamViewModel;
import mobileworld.main.SessionStorage;
import mobileworld.model.CameraTruoc;
import mobileworld.model.ChiTietSP;
import mobileworld.model.Imel;
import mobileworld.service.ChiTietSanPhamService.ImelService;
import mobileworld.service.ChiTietSanPhamService.ThuocTinhSPService;
import mobileworld.tablecutoms.TableActionCellEditor;
import mobileworld.tablecutoms.TableActionCellRender;
import mobileworld.tablecutoms.TableActionEvent;
import mobileworld.viewModel.DongSPViewModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.exception.QRGenerationException;
import net.glxn.qrgen.image.ImageType;

public class ViewSanPham extends JPanel implements DataChangeListener, EventChiTietSP {

    //chi tiet san pham
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private DefaultTableModel tblModel = new DefaultTableModel();
    private DefaultTableModel tblModelSP = new DefaultTableModel();
    private DefaultTableModel tblModelTTSP = new DefaultTableModel();
    private final ThuocTinhSPService ttspService = new ThuocTinhSPService();
    private final ChiTietSPService ctspService = new ChiTietSPService();
    private DefaultComboBoxModel cbbPin = new DefaultComboBoxModel();
    private final PinService pinService = new PinService();
    private DefaultComboBoxModel cbbManHinh = new DefaultComboBoxModel();
    private final ManHinhService mhService = new ManHinhService();
    private DefaultComboBoxModel cbbCpu = new DefaultComboBoxModel();
    private final CpuService cpuService = new CpuService();
    private final DongSPService dspService = new DongSPService();
    private DefaultComboBoxModel cbbNsx = new DefaultComboBoxModel();
    private final NhaSanXuatService NsxService = new NhaSanXuatService();
    private final ImelService imelService = new ImelService();
    private List<ChiTietSanPhamViewModel> searchResults = new ArrayList<>();
    private List<ChiTietSanPhamViewModel> BoLocCtsp = new ArrayList<>();
    private List<ChiTietSanPhamViewModel> listDsp = new ArrayList<>();
    private List<DongSPViewModel> listConHang = new ArrayList<>();
    private List<DongSPViewModel> listHetHang = new ArrayList<>();
    private final ThongTinChiTietSP thongTinChiTietSP;
    private String idChiTietSP, CameraSau, CameraTruoc, Cpu, Imel, ManHinh, MauSac, NSX, Pin, Ram, Rom, TenDsp, MoTa;
    private BigDecimal gia;

    public ViewSanPham() {
        initComponents();
        setOpaque(false);
        thongTinChiTietSP = new ThongTinChiTietSP(idChiTietSP, CameraSau, CameraTruoc, Cpu, Imel, ManHinh, MauSac, NSX, Pin, Ram, Rom, TenDsp, gia, MoTa);
        thongTinChiTietSP.changeListener.addDataChangeListener(this);
        thongTinChiTietSP.changeListener.setEventDataChangeListener(this);

        //setcombobox
        cbbNsx = (DefaultComboBoxModel) cboNsx.getModel();
        cbbPin = (DefaultComboBoxModel) cboPin.getModel();
        cbbManHinh = (DefaultComboBoxModel) cboManHinh.getModel();
        cbbCpu = (DefaultComboBoxModel) cboCPU.getModel();
        setDataCboCpu(cpuService.getAll());
        setDataCboManHinh(mhService.getAll());
        setDataCboNsx(NsxService.getAll());
        setDataCboPin(pinService.getAll());
        setDataCboGia();

        tblSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblSP.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    getSelectedProductListImel();
                }
            }
        });

        tblCTSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = tblCTSP.getSelectedRow();
                if (index >= 0 && evt.getClickCount() == 2) {
                    ArrayList<ChiTietSanPhamViewModel> productListImel = (ArrayList<ChiTietSanPhamViewModel>) getSelectedProductListImel();
                    if (productListImel.isEmpty()) {
                        if (searchResults != null && !searchResults.isEmpty()) {
                            displayProductDetails(searchResults.get(index));
                        } else if (BoLocCtsp != null && !BoLocCtsp.isEmpty()) {
                            displayProductDetails(BoLocCtsp.get(index));
                        } else {
                            displayProductDetails(ctspService.getAll().get(index));
                        }

                    } else {
                        if (searchResults != null && !searchResults.isEmpty()) {
                            ChiTietSanPhamViewModel product = searchResults.get(index);
                            displayProductDetails(product);
                        } else if (BoLocCtsp != null && !BoLocCtsp.isEmpty()) {
                            ChiTietSanPhamViewModel BoLocproduct = BoLocCtsp.get(index);
                            displayProductDetails(BoLocproduct);
                        } else {
                            int productListImelIndex = productListImel.indexOf(productListImel.get(index));
                            displayProductDetails(productListImel.get(productListImelIndex));
                        }
                    }
                }
            }

            private void displayProductDetails(ChiTietSanPhamViewModel product) {
                String idChiTietSP = product.getId();
                String Pin = product.getDungLuongPin();
                String Ram = product.getDungLuongRam();
                String ManHinh = product.getLoaiManHinh();
                String NSX = product.getTenNsx();
                String TenDsp = product.getTenDsp();
                String MauSac = product.getTenMau();
                String Cpu = product.getCpu();
                String Rom = product.getDungLuongBoNho();
                String CameraTruoc = product.getCameraTruoc();
                String CameraSau = product.getCameraSau();
                BigDecimal gia = product.getGiaBan();
                String Imel = product.getImel();
                String MoTa = product.getGhiChu();

                ThongTinChiTietSP chiTietSP = new ThongTinChiTietSP(idChiTietSP, CameraSau, CameraTruoc, Cpu, Imel, ManHinh, MauSac, NSX, Pin, Ram, Rom, TenDsp, gia, MoTa);
                chiTietSP.setVisible(true);
            }
        });
        //thuoc tinh san pham
        tblModelTTSP = (DefaultTableModel) tblThuocTinhSP.getModel();
        tblSP.setDefaultEditor(Object.class, null);

        //button Table
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onDelete(int row) {
                if (tblSP.isEditing()) {
                    tblSP.getCellEditor().stopCellEditing();
                }
                int selectedRow = tblSP.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(tblSP, "Vui lòng chọn sản phẩm để xóa!");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(new JFrame(), "Bạn có muốn xóa sản phẩm đã chọn không?", "Thông Báo", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int modelRowIndex = tblSP.convertRowIndexToModel(selectedRow);
                    DongSPViewModel sp = dspService.getAll().get(modelRowIndex);
                    dspService.remove(sp.getId());
                    if (rbnConHang.isSelected()) {
                        showDataTableSP(dspService.getAllConHang());
                    } else if (rbnHetHang.isSelected()) {
                        showDataTableSP(dspService.getAllHetHang());
                    } else if (rbnTatCa.isSelected()) {
                        showDataTableSP(dspService.getAll());
                    }
                    txtTenSP.setText("");
                    JOptionPane.showMessageDialog(new JFrame(), "Xóa Thành Công!");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Xóa Thất Bại hoặc đã huỷ bỏ!");
                }
            }
        };

        // Thiết lập renderer cho cột chứa nút xóa
        tblSP.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        tblSP.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));
        tblSP.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = (String) value;
                if (status.equals("Còn Hàng")) {
                    cellComponent.setForeground(Color.GREEN.darker());
                    cellComponent.setFont(cellComponent.getFont().deriveFont(Font.BOLD));
                    cellComponent.setBackground(Color.WHITE);
                } else if (status.equals("Hết Hàng")) {
                    cellComponent.setForeground(Color.RED.darker());
                    cellComponent.setFont(cellComponent.getFont().deriveFont(Font.BOLD));
                    cellComponent.setBackground(Color.WHITE);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(null);

                return cellComponent;
            }
        });
        rbnTatCa.setSelected(true);
        rbnPin.setSelected(true);
        showDataTablePin(pinService.getAll());
        // chi tiet san pham
        tblModel = (DefaultTableModel) tblCTSP.getModel();
        tblCTSP.setDefaultEditor(Object.class, null);
        showDataTableCTSP(ctspService.getAll());
        //san pham chi tiet
        tblModelSP = (DefaultTableModel) tblSP.getModel();
        tblSP.setDefaultEditor(Object.class, null);
        showDataTableSP(dspService.getAll());
    }

    public JPanel getPanelSPCT() {
        return panelSanPhamCT;
    }

    public JPanel getPanelSP() {
        return panelSanPham;
    }

    public boolean getSelectedProductList() {
        int index = tblSP.getSelectedRow();
        if (index >= 0) {
            String idDSP = (String) tblSP.getValueAt(index, 1);

            materialTabbed1.setSelectedIndex(1);

            // Gọi hàm getSP với iddsp đã lấy
            listDsp = ctspService.getSP(idDSP);

            // Hiển thị danh sách sản phẩm
            showDataTableCTSP(listDsp);

            return true;
        }
        return false;
    }

    public List<ChiTietSanPhamViewModel> getSelectedProductListImel() {
        List<ChiTietSanPhamViewModel> productList = new ArrayList<>();
        int index = tblSP.getSelectedRow();
        if (index >= 0) {
            String idDSP = (String) tblSP.getValueAt(index, 1);

            materialTabbed1.setSelectedIndex(1);

            // Gọi hàm getSP với iddsp đã lấy
            Object result = ctspService.getSPImel(idDSP);

            // Check if the result is of the correct type before casting
            if (result instanceof List) {
                for (Object obj : (List<?>) result) {
                    if (obj instanceof ChiTietSanPhamViewModel) {
                        productList.add((ChiTietSanPhamViewModel) obj);
                    }
                }
            }

            // Hiển thị danh sách sản phẩm
            showDataTableCTSPImel(productList);
        }
        return productList;
    }

    //chi tiet san pham
    private void showDataTableCTSPImel(List<ChiTietSanPhamViewModel> showSP) {
        tblModel.setRowCount(0);
        int stt = 0;
        boolean check = false;
        for (ChiTietSanPhamViewModel spvm : showSP) {
            String giaBan = decimalFormat.format(spvm.getGiaBan());
            stt++;
            tblModel.addRow(new Object[]{
                stt, spvm.getTenDsp(), spvm.getImel(), spvm.getTenNsx(), spvm.getDungLuongPin(), spvm.getLoaiManHinh(), spvm.getCpu(), spvm.getDungLuongRam(), spvm.getDungLuongBoNho(), spvm.getTenMau(), giaBan, spvm.getCameraSau(), spvm.getCameraTruoc()
            });
        }
    }

    private void showDataTableCTSP(List<ChiTietSanPhamViewModel> showSP) {
        tblModel.setRowCount(0);
        int stt = 0;
        for (ChiTietSanPhamViewModel spvm : showSP) {
            String giaBan = decimalFormat.format(spvm.getGiaBan());
            stt++;
            tblModel.addRow(new Object[]{
                stt, spvm.getTenDsp(), spvm.getImel(), spvm.getTenNsx(), spvm.getDungLuongPin(), spvm.getLoaiManHinh(), spvm.getCpu(), spvm.getDungLuongRam(), spvm.getDungLuongBoNho(), spvm.getTenMau(), giaBan, spvm.getCameraSau(), spvm.getCameraTruoc()

            });
        }

    }

    private void showDataTableSP(List<DongSPViewModel> dongSP) {
        tblModelSP.setRowCount(0);
        int stt = 0;
        String trangThai = "";
        for (DongSPViewModel spvm : dongSP) {
            stt++;
            if (spvm.getSoLuong() == 0) {
                trangThai = "Hết Hàng";
            } else {
                trangThai = "Còn Hàng";
            }
            tblModelSP.addRow(new Object[]{
                stt, spvm.getId(), spvm.getTenDsp(), spvm.getSoLuong(), trangThai
            });
        }
    }

    private void clearData() {
        txtTimKiem.setText("");
        if (cboCPU.getSelectedItem() != null) {
            cboCPU.setSelectedItem(null);
        }
        if (cboGia.getSelectedItem() != null) {
            cboGia.setSelectedItem(null);
        }
        if (cboManHinh.getSelectedItem() != null) {
            cboManHinh.setSelectedItem(null);
        }
        if (cboPin.getSelectedItem() != null) {
            cboPin.setSelectedItem(null);
        }
        if (cboNsx.getSelectedItem() != null) {
            cboNsx.setSelectedItem(null);
        }
        jCheckBox1.setSelected(false);
        showDataTableCTSP(ctspService.getAll());
    }

    //san pham
    public DongSP getFormSanPham() {
        String tenSP = txtTenSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        LocalDate dateTime = LocalDate.now();

        DongSP dsp = new DongSP(tenSP, 1, dateTime, nhanVien);
        return dsp;
    }

    public boolean checkSP() {
        if (txtTenSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên Sản Phẩm Không Được Để Trống!");
            return false;
        }

        List<DongSPViewModel> tenDsp = dspService.getTenDsp();

        // Chuẩn hóa tên sản phẩm mới để so sánh
        String tenSanPhamMoi = txtTenSP.getText().trim().toLowerCase();

        for (DongSPViewModel dsp : tenDsp) {
            String tenSanPhamTrongDanhSach = dsp.getTenDsp().toLowerCase();
            if (tenSanPhamTrongDanhSach.equals(tenSanPhamMoi)) {
                JOptionPane.showMessageDialog(this, "Sản Phẩm đã tồn tại trong cơ sở dữ liệu!");
                return false;
            }
        }
        return true;
    }

    //thuoc tinh san pham
    private void showDataTablePin(List<Pin> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (Pin pe : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, pe.getId(), pe.getDungLuongPin()
            });
        }
    }

    private void showDataTableCameraTruoc(List<CameraTruoc> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (CameraTruoc cam : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, cam.getId(), cam.getSoMP()
            });
        }
    }

    private void showDataTableCameraSau(List<CameraSau> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (CameraSau cam : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, cam.getId(), cam.getSoMP()
            });
        }
    }

    private void showDataTableCPU(List<CPU> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (CPU cpu : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, cpu.getId(), cpu.getCpu()
            });
        }
    }

    // Hàm hiển thị dữ liệu ManHinhEntity lên bảng
    private void showDataTableManHinh(List<ManHinh> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (ManHinh mh : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, mh.getId(), mh.getLoaiManHinh()
            });
        }
    }

    // Hàm hiển thị dữ liệu MauSacEntity lên bảng
    private void showDataTableMauSac(List<MauSac> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (MauSac ms : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, ms.getId(), ms.getTenMau()
            });
        }
    }

    // Hàm hiển thị dữ liệu RamEntity lên bảng
    private void showDataTableRam(List<Ram> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (Ram ram : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, ram.getId(), ram.getDungLuongRam()
            });
        }
    }

    // Hàm hiển thị dữ liệu BoNhoEntity lên bảng
    private void showDataTableBoNho(List<BoNho> lists) {
        tblModelTTSP.setRowCount(0);
        int stt = 0;
        for (BoNho bn : lists) {
            stt++;
            tblModelTTSP.addRow(new Object[]{
                stt, bn.getId(), bn.getDungLuongBoNho()
            });
        }
    }

    /////load combobox
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
        cboManHinh.setSelectedItem(null);
    }

    private void setDataCboNsx(List<NhaSanXuat> setNsx) {
        cbbNsx.removeAllElements();

        for (NhaSanXuat nsx : setNsx) {
            cbbNsx.addElement(nsx.getTenNsx());
        }
        cboNsx.setSelectedItem(null);
    }

    private void setDataCboCpu(List<CPU> setCpu) {
        cbbCpu.removeAllElements();

        for (CPU cpu : setCpu) {
            cbbCpu.addElement(cpu.getCpu());
        }
        cboCPU.setSelectedItem(null);
    }

    private void setDataCboGia() {
        cboGia.addItem("Giá Tăng Dần");
        cboGia.addItem("Giá Giảm Dần");
        cboGia.setSelectedItem(null);
    }

    //getformcombobox
    private Pin getFormDataPin() {
        String tenPin = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new Pin(tenPin, 1, LocalDate.now(), nhanVien);
    }

    private CameraTruoc getFormDataCameraTruoc() {
        String camTruoc = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new CameraTruoc(camTruoc, 1, LocalDate.now(), nhanVien);
    }

    private CameraSau getFormDataCameraSau() {
        String camSau = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new CameraSau(camSau, 1, LocalDate.now(), nhanVien);
    }

    private CPU getFormDataCPU() {
        String cpuName = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new CPU(cpuName, 1, LocalDate.now(), nhanVien);
    }

// Hàm get form dữ liệu ManHinhEntity
    private ManHinh getFormDataManHinh() {
        String loaiManHinh = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new ManHinh(loaiManHinh, 1, LocalDate.now(), nhanVien);
    }

// Hàm get form dữ liệu MauSacEntity
    private MauSac getFormDataMauSac() {
        String tenMau = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new MauSac(tenMau, 1, LocalDate.now(), nhanVien);
    }

// Hàm get form dữ liệu RamEntity
    private Ram getFormDataRam() {
        String dungLuongRam = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new Ram(dungLuongRam, 1, LocalDate.now(), nhanVien);
    }

// Hàm get form dữ liệu BoNhoEntity
    private BoNho getFormDataBoNho() {
        String dungLuongBoNho = txtTenThuocTinhSP.getText();
        String nhanVien = SessionStorage.getInstance().getUsername();
        return new BoNho(dungLuongBoNho, 1, LocalDate.now(), nhanVien);
    }

    public boolean checkThuocTinhSP() {
        if (txtTenThuocTinhSP.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy Nhập Tên Thuộc Tính");
            return false;
        }

        List<BoNho> tenboNho = ttspService.getTenBoNho();

        String tenBoNho = txtTenThuocTinhSP.getText().trim();
        for (BoNho rom : tenboNho) {
            if (rom.getDungLuongBoNho().equals(tenBoNho)) {
                JOptionPane.showMessageDialog(this, "Bộ nhớ đã tồn tại trong cơ sở dữ liệu!");
                return false;
            }
        }

        List<Pin> tenPin = ttspService.getTenPin();

        String tenpin = txtTenThuocTinhSP.getText().trim();
        for (Pin pin : tenPin) {
            if (pin.getDungLuongPin().equals(tenpin)) {
                JOptionPane.showMessageDialog(this, "Pin đã tồn tại trong cơ sở dữ liệu!");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDataChange() {
        showDataTableCTSP(ctspService.getAll());
    }

    @Override
    public boolean update(ChiTietSP ctsp, String id) {
        onDataChange();
        return true;
    }

    private void filterCTSP() {
        String nsx = (String) cboNsx.getSelectedItem();
        String pin = (String) cboPin.getSelectedItem();
        String manHinh = (String) cboManHinh.getSelectedItem();
        String cpu = (String) cboCPU.getSelectedItem();

        // Kiểm tra xem giá trị được chọn từ combobox có null không
        boolean sapXepGiaTangDan = "Giá Tăng Dần".equals(cboGia.getSelectedItem());

        BoLocCtsp = ctspService.LocCTSP(nsx, pin, manHinh, cpu, sapXepGiaTangDan);
        showDataTableCTSP(BoLocCtsp);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        materialTabbed1 = new mobileworld.swing.MaterialTabbed();
        panelSanPham = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSP = new mobileworld.swing.Table();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiemSP = new mobileworld.swing.TextField();
        jPanel5 = new javax.swing.JPanel();
        txtTenSP = new mobileworld.swing.TextField();
        btnAddSp = new mobileworld.swing.ButtonCustom();
        btnUpdateSp = new mobileworld.swing.ButtonCustom();
        btnClearSp = new mobileworld.swing.ButtonCustom();
        jLabel4 = new javax.swing.JLabel();
        rbnTatCa = new javax.swing.JRadioButton();
        rbnHetHang = new javax.swing.JRadioButton();
        rbnConHang = new javax.swing.JRadioButton();
        panelSanPhamCT = new javax.swing.JPanel();
        buttonCustom10 = new mobileworld.swing.ButtonCustom();
        btnClear = new mobileworld.swing.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCTSP = new mobileworld.swing.Table();
        txtTimKiem = new mobileworld.swing.TextField();
        buttonCustom16 = new mobileworld.swing.ButtonCustom();
        btnTaiQR = new mobileworld.swing.ButtonCustom();
        btnQuetQR = new mobileworld.swing.ButtonCustom();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cboNsx = new mobileworld.swing.Combobox();
        cboPin = new mobileworld.swing.Combobox();
        cboManHinh = new mobileworld.swing.Combobox();
        cboCPU = new mobileworld.swing.Combobox();
        cboGia = new mobileworld.swing.Combobox();
        jCheckBox1 = new javax.swing.JCheckBox();
        panelThuocTinhSP = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinhSP = new mobileworld.swing.Table();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtMaSP = new mobileworld.swing.TextField();
        jPanel10 = new javax.swing.JPanel();
        rbnPin = new javax.swing.JRadioButton();
        rbnMauSac = new javax.swing.JRadioButton();
        rbnManHinh = new javax.swing.JRadioButton();
        rbnCpu = new javax.swing.JRadioButton();
        rbnRam = new javax.swing.JRadioButton();
        rbnRom = new javax.swing.JRadioButton();
        rbnCamTruoc = new javax.swing.JRadioButton();
        rbnCamSau = new javax.swing.JRadioButton();
        txtTenThuocTinhSP = new mobileworld.swing.TextField();
        jPanel11 = new javax.swing.JPanel();
        btnAddThuocTinh = new mobileworld.swing.ButtonCustom();
        btnUpdateThuocTinh = new mobileworld.swing.ButtonCustom();
        btnRemoveThuocTinh = new mobileworld.swing.ButtonCustom();
        btnClearThuocTinh = new mobileworld.swing.ButtonCustom();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        panelSanPham.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(null);

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên Sản Phẩm", "Số Lượng", "Trạng Thái", "Hành Động"
            }
        ));
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSP);
        if (tblSP.getColumnModel().getColumnCount() > 0) {
            tblSP.getColumnModel().getColumn(0).setMinWidth(30);
            tblSP.getColumnModel().getColumn(0).setMaxWidth(60);
            tblSP.getColumnModel().getColumn(5).setMinWidth(100);
            tblSP.getColumnModel().getColumn(5).setMaxWidth(200);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Danh Sách Sản Phẩm");

        txtTimKiemSP.setLabelText("Tìm Kiếm");
        txtTimKiemSP.setName(""); // NOI18N
        txtTimKiemSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSPKeyReleased(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTenSP.setLabelText("Tên Sản Phẩm");

        btnAddSp.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnAddSp.setText("Thêm");
        btnAddSp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSpActionPerformed(evt);
            }
        });

        btnUpdateSp.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateSp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-refresh-24.png"))); // NOI18N
        btnUpdateSp.setText("Sửa");
        btnUpdateSp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSpActionPerformed(evt);
            }
        });

        btnClearSp.setForeground(new java.awt.Color(255, 255, 255));
        btnClearSp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-24 (1).png"))); // NOI18N
        btnClearSp.setText("Làm Mới");
        btnClearSp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClearSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 709, Short.MAX_VALUE)
                .addComponent(btnAddSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearSp, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSp, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateSp, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearSp, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(12, 45, 87));
        jLabel4.setText("Trạng Thái");

        buttonGroup2.add(rbnTatCa);
        rbnTatCa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbnTatCa.setForeground(new java.awt.Color(12, 45, 87));
        rbnTatCa.setText("Tất Cả");
        rbnTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnTatCaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbnHetHang);
        rbnHetHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbnHetHang.setForeground(new java.awt.Color(12, 45, 87));
        rbnHetHang.setText("Hết Hàng");
        rbnHetHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnHetHangActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbnConHang);
        rbnConHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbnConHang.setForeground(new java.awt.Color(12, 45, 87));
        rbnConHang.setText("Còn Hàng");
        rbnConHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnConHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSanPhamLayout = new javax.swing.GroupLayout(panelSanPham);
        panelSanPham.setLayout(panelSanPhamLayout);
        panelSanPhamLayout.setHorizontalGroup(
            panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSanPhamLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbnTatCa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbnHetHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbnConHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelSanPhamLayout.setVerticalGroup(
            panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(rbnTatCa)
                        .addComponent(rbnHetHang)
                        .addComponent(rbnConHang)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        materialTabbed1.addTab("Sản Phẩm", panelSanPham);

        panelSanPhamCT.setOpaque(false);

        buttonCustom10.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        buttonCustom10.setText("Thêm");
        buttonCustom10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom10ActionPerformed(evt);
            }
        });

        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-24 (1).png"))); // NOI18N
        btnClear.setText("Làm Mới");
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);

        tblCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên SP", "Imel", "NSX", "Pin", "Màn Hình", "CPU", "RAM", "ROM", "Màu", "Giá", "Camera Sau", "Camera Trước", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCTSP);
        if (tblCTSP.getColumnModel().getColumnCount() > 0) {
            tblCTSP.getColumnModel().getColumn(0).setMinWidth(20);
            tblCTSP.getColumnModel().getColumn(0).setMaxWidth(40);
            tblCTSP.getColumnModel().getColumn(1).setMinWidth(150);
            tblCTSP.getColumnModel().getColumn(1).setMaxWidth(200);
            tblCTSP.getColumnModel().getColumn(2).setMinWidth(150);
            tblCTSP.getColumnModel().getColumn(2).setMaxWidth(200);
            tblCTSP.getColumnModel().getColumn(3).setMinWidth(30);
            tblCTSP.getColumnModel().getColumn(3).setMaxWidth(60);
            tblCTSP.getColumnModel().getColumn(4).setMinWidth(50);
            tblCTSP.getColumnModel().getColumn(4).setMaxWidth(80);
            tblCTSP.getColumnModel().getColumn(6).setMinWidth(120);
            tblCTSP.getColumnModel().getColumn(6).setMaxWidth(150);
            tblCTSP.getColumnModel().getColumn(7).setMinWidth(30);
            tblCTSP.getColumnModel().getColumn(7).setMaxWidth(50);
            tblCTSP.getColumnModel().getColumn(8).setMinWidth(30);
            tblCTSP.getColumnModel().getColumn(8).setMaxWidth(50);
            tblCTSP.getColumnModel().getColumn(13).setMinWidth(20);
            tblCTSP.getColumnModel().getColumn(13).setMaxWidth(40);
        }

        txtTimKiem.setLabelText("Tìm Kiếm");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        buttonCustom16.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-export-excel-24.png"))); // NOI18N
        buttonCustom16.setText("Xuất File");
        buttonCustom16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom16ActionPerformed(evt);
            }
        });

        btnTaiQR.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-qr-code-30.png"))); // NOI18N
        btnTaiQR.setText("Tải QR");
        btnTaiQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaiQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiQRActionPerformed(evt);
            }
        });

        btnQuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQuetQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-qr-code-30.png"))); // NOI18N
        btnQuetQR.setText("Quét QR");
        btnQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Thông Tin sản Phẩm Chi Tiết");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(12, 45, 87))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 20, 20));

        cboNsx.setLabeText("Nhà Sản Xuất");
        cboNsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNsxActionPerformed(evt);
            }
        });
        jPanel2.add(cboNsx);

        cboPin.setLabeText("Pin");
        cboPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPinActionPerformed(evt);
            }
        });
        jPanel2.add(cboPin);

        cboManHinh.setLabeText("Màn Hình");
        cboManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboManHinhActionPerformed(evt);
            }
        });
        jPanel2.add(cboManHinh);

        cboCPU.setLabeText("CPU");
        cboCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCPUActionPerformed(evt);
            }
        });
        jPanel2.add(cboCPU);

        cboGia.setLabeText("Giá");
        cboGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiaActionPerformed(evt);
            }
        });
        jPanel2.add(cboGia);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(220, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(12, 45, 87));
        jCheckBox1.setText("All");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSanPhamCTLayout = new javax.swing.GroupLayout(panelSanPhamCT);
        panelSanPhamCT.setLayout(panelSanPhamCTLayout);
        panelSanPhamCTLayout.setHorizontalGroup(
            panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelSanPhamCTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSanPhamCTLayout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnTaiQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(buttonCustom16, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelSanPhamCTLayout.setVerticalGroup(
            panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSanPhamCTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSanPhamCTLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(buttonCustom16, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnQuetQR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSanPhamCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTaiQR, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        materialTabbed1.addTab("Sản Phẩm Chi Tiết", panelSanPhamCT);

        panelThuocTinhSP.setBackground(new java.awt.Color(255, 255, 255));

        tblThuocTinhSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Thuộc Tính", "Tên Thuộc Tính"
            }
        ));
        tblThuocTinhSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblThuocTinhSP);
        if (tblThuocTinhSP.getColumnModel().getColumnCount() > 0) {
            tblThuocTinhSP.getColumnModel().getColumn(0).setMinWidth(30);
            tblThuocTinhSP.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(12, 45, 87));
        jLabel2.setText("Danh Sách Thuộc Tính");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Thuộc Tính Sản Phẩm");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtMaSP.setEnabled(false);
        txtMaSP.setLabelText("Mã Thuộc Tính");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel10.setLayout(new java.awt.GridLayout(2, 4, 15, 15));

        buttonGroup1.add(rbnPin);
        rbnPin.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnPin.setForeground(new java.awt.Color(12, 45, 87));
        rbnPin.setText("Pin");
        rbnPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnPinActionPerformed(evt);
            }
        });
        jPanel10.add(rbnPin);

        buttonGroup1.add(rbnMauSac);
        rbnMauSac.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnMauSac.setForeground(new java.awt.Color(12, 45, 87));
        rbnMauSac.setText("Màu Sắc");
        rbnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMauSacActionPerformed(evt);
            }
        });
        jPanel10.add(rbnMauSac);

        buttonGroup1.add(rbnManHinh);
        rbnManHinh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnManHinh.setForeground(new java.awt.Color(12, 45, 87));
        rbnManHinh.setText("Màn Hình");
        rbnManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnManHinhActionPerformed(evt);
            }
        });
        jPanel10.add(rbnManHinh);

        buttonGroup1.add(rbnCpu);
        rbnCpu.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnCpu.setForeground(new java.awt.Color(12, 45, 87));
        rbnCpu.setText("CPU");
        rbnCpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnCpuActionPerformed(evt);
            }
        });
        jPanel10.add(rbnCpu);

        buttonGroup1.add(rbnRam);
        rbnRam.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnRam.setForeground(new java.awt.Color(12, 45, 87));
        rbnRam.setText("Ram");
        rbnRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnRamActionPerformed(evt);
            }
        });
        jPanel10.add(rbnRam);

        buttonGroup1.add(rbnRom);
        rbnRom.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnRom.setForeground(new java.awt.Color(12, 45, 87));
        rbnRom.setText("Rom");
        rbnRom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnRomActionPerformed(evt);
            }
        });
        jPanel10.add(rbnRom);

        buttonGroup1.add(rbnCamTruoc);
        rbnCamTruoc.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnCamTruoc.setForeground(new java.awt.Color(12, 45, 87));
        rbnCamTruoc.setText("Camera Trước");
        rbnCamTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnCamTruocActionPerformed(evt);
            }
        });
        jPanel10.add(rbnCamTruoc);

        buttonGroup1.add(rbnCamSau);
        rbnCamSau.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rbnCamSau.setForeground(new java.awt.Color(12, 45, 87));
        rbnCamSau.setText("Camera Sau");
        rbnCamSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnCamSauActionPerformed(evt);
            }
        });
        jPanel10.add(rbnCamSau);

        txtTenThuocTinhSP.setLabelText("Tên Thuộc Tính");

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel11.setLayout(new java.awt.GridLayout(2, 0, 15, 15));

        btnAddThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnAddThuocTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnAddThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddThuocTinh.setLabel("Insert");
        btnAddThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThuocTinhActionPerformed(evt);
            }
        });
        jPanel11.add(btnAddThuocTinh);

        btnUpdateThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateThuocTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-refresh-24.png"))); // NOI18N
        btnUpdateThuocTinh.setText("Update");
        btnUpdateThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateThuocTinhActionPerformed(evt);
            }
        });
        jPanel11.add(btnUpdateThuocTinh);

        btnRemoveThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveThuocTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-delete-24.png"))); // NOI18N
        btnRemoveThuocTinh.setText("Remove");
        btnRemoveThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemoveThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveThuocTinhActionPerformed(evt);
            }
        });
        jPanel11.add(btnRemoveThuocTinh);

        btnClearThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnClearThuocTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-24.png"))); // NOI18N
        btnClearThuocTinh.setText("Clear");
        btnClearThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClearThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearThuocTinhActionPerformed(evt);
            }
        });
        jPanel11.add(btnClearThuocTinh);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(txtTenThuocTinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenThuocTinhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelThuocTinhSPLayout = new javax.swing.GroupLayout(panelThuocTinhSP);
        panelThuocTinhSP.setLayout(panelThuocTinhSPLayout);
        panelThuocTinhSPLayout.setHorizontalGroup(
            panelThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelThuocTinhSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelThuocTinhSPLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        panelThuocTinhSPLayout.setVerticalGroup(
            panelThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThuocTinhSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Thuộc Tính Sản Phẩm", panelThuocTinhSP);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Sản Phẩm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(0, 0, 0)
                .addComponent(materialTabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearData();
    }//GEN-LAST:event_btnClearActionPerformed


    private void buttonCustom10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom10ActionPerformed
        ThemChiTietSP themChiTietSP = new ThemChiTietSP();

        // Lấy tham chiếu đến viewsanpham và xóa tất cả các thành phần hiện tại
        JPanel viewsanpham = panelSanPhamCT;
        viewsanpham.removeAll();

        // Thêm ThemChiTietSP vào viewsanpham
        viewsanpham.add(themChiTietSP);

        viewsanpham.setLayout(new BorderLayout());

        // Thêm ThemChiTietSP vào viewsanpham với ràng buộc layout phù hợp
        viewsanpham.add(themChiTietSP, BorderLayout.CENTER);

        // Cập nhật giao diện
        viewsanpham.revalidate();
        viewsanpham.repaint();
    }//GEN-LAST:event_buttonCustom10ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        searchResults = ctspService.search(txtTimKiem.getText());
        showDataTableCTSP(searchResults);
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        if (txtTimKiem.getText().trim().equals("")) {
            showDataTableCTSP(ctspService.getAll());
            searchResults = null;
        } else {
            searchResults = ctspService.search(txtTimKiem.getText());
            showDataTableCTSP(searchResults);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void buttonCustom16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom16ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất danh sách sản phẩm không", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Create a list to store selected IMEI values
                List<String> selectedImels = new ArrayList<>();

                // Iterate through the table rows
                DefaultTableModel model = (DefaultTableModel) tblCTSP.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    // Get the value of the checkbox (column 13)
                    Boolean column13Value = (Boolean) model.getValueAt(i, 13);

                    // Check if the checkbox is ticked
                    if (column13Value != null && column13Value) {
                        // Get the IMEI from column 2 (assuming column index is 1)
                        String imel = (String) model.getValueAt(i, 2);
                        selectedImels.add(imel);
                    }
                }

                // Check if any products were selected
                if (selectedImels.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không có sản phẩm nào được chọn.");
                    return;
                }

                // Call the service method to export all selected products
                boolean result = ctspService.xuatSanPham(selectedImels);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Xuất Thành Công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Xuất Thất Bại!");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xuất Thất Bại!");
            }
        }
    }//GEN-LAST:event_buttonCustom16ActionPerformed

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
        int index = tblSP.getSelectedRow();

        DongSPViewModel sp;
        if (listHetHang != null && !listHetHang.isEmpty() && index < listHetHang.size()) {
            sp = listHetHang.get(index);
        } else if (listConHang != null && !listConHang.isEmpty() && index < listConHang.size()) {
            sp = listConHang.get(index);
        } else {
            sp = dspService.getAll().get(index);
        }

        txtTenSP.setText(sp.getTenDsp());
    }//GEN-LAST:event_tblSPMouseClicked

    private void btnTaiQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiQRActionPerformed
        try {
            // Lặp qua các hàng của table view
            for (int i = 0; i < tblCTSP.getRowCount(); i++) {
                // Lấy giá trị của cột thứ 13 của hàng hiện tại
                Boolean column13Value = (Boolean) tblCTSP.getValueAt(i, 13); // Giả sử cột 13 có index là 12

                // Kiểm tra nếu cả cột thứ 2 và cột thứ 13 không null và cột thứ 13 là true
                if (tblCTSP.getValueAt(i, 2) != null && column13Value != null && column13Value) {
                    // Lấy giá trị từ cột thứ 2 của hàng hiện tại (assumed column index: 1)
                    String imel = (String) tblCTSP.getValueAt(i, 2);

                    // Tạo và lưu mã QR cho Imel
                    ByteArrayOutputStream out = QRCode.from(imel)
                            .to(ImageType.PNG).stream();

                    // Thay thế các ký tự không hợp lệ trong tên tệp
                    String f_name = imel.replaceAll("[^a-zA-Z0-9.-]", "_");
                    String Path_name = "D:\\mobileWorldCopy\\QR\\";

                    FileOutputStream fout = new FileOutputStream(new File(Path_name + (f_name + ".PNG")));
                    fout.write(out.toByteArray());
                    fout.flush();

                    // Thông báo cho người dùng rằng mã QR đã được tải xuống thành công
                    System.out.println("QR code for Imel " + imel + " has been downloaded successfully.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }//GEN-LAST:event_btnTaiQRActionPerformed


    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        ReadQRCode rqr = new ReadQRCode();
        rqr.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void txtTimKiemSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSPKeyReleased
        if (txtTimKiemSP.getText().trim().equals("")) {
            showDataTableSP(dspService.getAll());
        }
        showDataTableSP(dspService.search(txtTimKiemSP.getText()));
    }//GEN-LAST:event_txtTimKiemSPKeyReleased

    private void btnAddSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSpActionPerformed
        if (checkSP()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn Thêm không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dspService.add(getFormSanPham());
                showDataTableSP(dspService.getAll());
                txtTenSP.setText("");
                JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Thêm Thất Bại!");
        }
    }//GEN-LAST:event_btnAddSpActionPerformed

    private void btnUpdateSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSpActionPerformed
        int i = tblSP.getSelectedRow();
        int selectedRow = tblSP.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm để cập nhật!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DongSPViewModel sp = dspService.getAll().get(i);
            dspService.update(getFormSanPham(), sp.getId());
            showDataTableSP(dspService.getAll());
            txtTenSP.setText("");
            JOptionPane.showMessageDialog(this, "Cập Nhật Thành Công!");
        } else {
            JOptionPane.showMessageDialog(this, "Cập Nhật Thất Bại!");
        }
    }//GEN-LAST:event_btnUpdateSpActionPerformed

    private void clearThuocTinhSP() {
        txtTenThuocTinhSP.setText("");
        txtMaSP.setText("");
    }
    private void btnClearSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSpActionPerformed
        txtTenSP.setText("");
        showDataTableSP(dspService.getAll());
    }//GEN-LAST:event_btnClearSpActionPerformed

    private void rbnPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnPinActionPerformed
        showDataTablePin(ttspService.getAllPin());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnPinActionPerformed

    private void rbnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnMauSacActionPerformed
        showDataTableMauSac(ttspService.getAllMauSac());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnMauSacActionPerformed

    private void rbnManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnManHinhActionPerformed
        showDataTableManHinh(ttspService.getAllManHinh());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnManHinhActionPerformed

    private void rbnCpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnCpuActionPerformed
        showDataTableCPU(ttspService.getAllCPU());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnCpuActionPerformed

    private void rbnRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnRamActionPerformed
        showDataTableRam(ttspService.getAllRam());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnRamActionPerformed

    private void rbnRomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnRomActionPerformed
        showDataTableBoNho(ttspService.getAllBoNho());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnRomActionPerformed

    private void tblThuocTinhSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhSPMouseClicked
        int i = tblThuocTinhSP.getSelectedRow();

        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng trong bảng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (rbnPin.isSelected()) {
            Pin pin = ttspService.getAllPin().get(i);
            txtTenThuocTinhSP.setText(pin.getDungLuongPin());
            txtMaSP.setText(pin.getId());
        } else if (rbnCpu.isSelected()) {
            CPU cpu = ttspService.getAllCPU().get(i);
            txtTenThuocTinhSP.setText(cpu.getCpu());
            txtMaSP.setText(cpu.getId());
        } else if (rbnManHinh.isSelected()) {
            ManHinh manHinh = ttspService.getAllManHinh().get(i);
            txtTenThuocTinhSP.setText(manHinh.getLoaiManHinh());
            txtMaSP.setText(manHinh.getId());
        } else if (rbnMauSac.isSelected()) {
            MauSac mauSac = ttspService.getAllMauSac().get(i);
            txtTenThuocTinhSP.setText(mauSac.getTenMau());
            txtMaSP.setText(mauSac.getId());
        } else if (rbnRam.isSelected()) {
            Ram ram = ttspService.getAllRam().get(i);
            txtTenThuocTinhSP.setText(ram.getDungLuongRam());
            txtMaSP.setText(ram.getId());
        } else if (rbnRom.isSelected()) {
            BoNho boNho = ttspService.getAllBoNho().get(i);
            txtTenThuocTinhSP.setText(boNho.getDungLuongBoNho());
            txtMaSP.setText(boNho.getId());
        } else if (rbnCamTruoc.isSelected()) {
            CameraTruoc cam = ttspService.getAllCameraTruoc().get(i);
            txtTenThuocTinhSP.setText(cam.getSoMP());
            txtMaSP.setText(cam.getId());
        } else if (rbnCamSau.isSelected()) {
            CameraSau cam = ttspService.getAllCameraSau().get(i);
            txtTenThuocTinhSP.setText(cam.getSoMP());
            txtMaSP.setText(cam.getId());
        }
    }//GEN-LAST:event_tblThuocTinhSPMouseClicked

    private void btnAddThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThuocTinhActionPerformed
        if (checkThuocTinhSP()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn Thêm không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean addSuccess = false;
                if (rbnPin.isSelected()) {
                    addSuccess = ttspService.addPin(getFormDataPin());
                    showDataTablePin(ttspService.getAllPin());
                    setDataCboPin(pinService.getAll());
                    clearThuocTinhSP();
                    showDataTableCTSP(ctspService.getAllCTSP());
                } else if (rbnCpu.isSelected()) {
                    addSuccess = ttspService.addCPU(getFormDataCPU());
                    showDataTableCPU(ttspService.getAllCPU());
                    setDataCboCpu(cpuService.getAll());
                    clearThuocTinhSP();
                    showDataTableCTSP(ctspService.getAllCTSP());
                } else if (rbnManHinh.isSelected()) {
                    addSuccess = ttspService.addManHinh(getFormDataManHinh());
                    showDataTableManHinh(ttspService.getAllManHinh());
                    setDataCboManHinh(mhService.getAll());
                    clearThuocTinhSP();
                    showDataTableCTSP(ctspService.getAllCTSP());
                } else if (rbnMauSac.isSelected()) {
                    addSuccess = ttspService.addMauSac(getFormDataMauSac());
                    showDataTableMauSac(ttspService.getAllMauSac());
                    clearThuocTinhSP();
                } else if (rbnRam.isSelected()) {
                    addSuccess = ttspService.addRam(getFormDataRam());
                    showDataTableRam(ttspService.getAllRam());
                    clearThuocTinhSP();
                } else if (rbnRom.isSelected()) {
                    addSuccess = ttspService.addBoNho(getFormDataBoNho());
                    showDataTableBoNho(ttspService.getAllBoNho());
                    clearThuocTinhSP();
                } else if (rbnCamTruoc.isSelected()) {
                    addSuccess = ttspService.addCameraTruoc(getFormDataCameraTruoc());
                    showDataTableCameraTruoc(ttspService.getAllCameraTruoc());
                    clearThuocTinhSP();
                } else if (rbnCamSau.isSelected()) {
                    addSuccess = ttspService.addCameraSau(getFormDataCameraSau());
                    showDataTableCameraSau(ttspService.getAllCameraSau());
                    clearThuocTinhSP();
                }

                if (addSuccess) {
                    JOptionPane.showMessageDialog(this, "Thêm Thất bại!");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
                }
            }
        }
    }//GEN-LAST:event_btnAddThuocTinhActionPerformed

    private void btnUpdateThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateThuocTinhActionPerformed
        int selectedRow = tblThuocTinhSP.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm để cập nhật!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật không", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean updateSuccess = false;
            if (rbnPin.isSelected()) {
                Pin selectedPin = ttspService.getAllPin().get(selectedRow);
                updateSuccess = ttspService.updatePin(getFormDataPin(), selectedPin.getId());
                showDataTablePin(ttspService.getAllPin());
                setDataCboPin(pinService.getAll());
                clearThuocTinhSP();
                showDataTableCTSP(ctspService.getAllCTSP());
            } else if (rbnCpu.isSelected()) {
                CPU selectedCPU = ttspService.getAllCPU().get(selectedRow);
                updateSuccess = ttspService.updateCPU(getFormDataCPU(), selectedCPU.getId());
                showDataTableCPU(ttspService.getAllCPU());
                setDataCboCpu(cpuService.getAll());
                clearThuocTinhSP();
                showDataTableCTSP(ctspService.getAllCTSP());
            } else if (rbnManHinh.isSelected()) {
                ManHinh selectedManHinh = ttspService.getAllManHinh().get(selectedRow);
                updateSuccess = ttspService.updateManHinh(getFormDataManHinh(), selectedManHinh.getId());
                showDataTableManHinh(ttspService.getAllManHinh());
                setDataCboManHinh(mhService.getAll());
                clearThuocTinhSP();
                showDataTableCTSP(ctspService.getAllCTSP());
            } else if (rbnMauSac.isSelected()) {
                MauSac selectedMauSac = ttspService.getAllMauSac().get(selectedRow);
                updateSuccess = ttspService.updateMauSac(getFormDataMauSac(), selectedMauSac.getId());
                showDataTableMauSac(ttspService.getAllMauSac());
                clearThuocTinhSP();
            } else if (rbnRam.isSelected()) {
                Ram selectedRam = ttspService.getAllRam().get(selectedRow);
                updateSuccess = ttspService.updateRam(getFormDataRam(), selectedRam.getId());
                showDataTableRam(ttspService.getAllRam());
                clearThuocTinhSP();
            } else if (rbnRom.isSelected()) {
                BoNho selectedBoNho = ttspService.getAllBoNho().get(selectedRow);
                updateSuccess = ttspService.updateBoNho(getFormDataBoNho(), selectedBoNho.getId());
                showDataTableBoNho(ttspService.getAllBoNho());
                clearThuocTinhSP();
            } else if (rbnCamTruoc.isSelected()) {
                CameraTruoc selectedCamTruoc = ttspService.getAllCameraTruoc().get(selectedRow);
                updateSuccess = ttspService.updateCameraTruoc(getFormDataCameraTruoc(), selectedCamTruoc.getId());
                showDataTableCameraTruoc(ttspService.getAllCameraTruoc());
                clearThuocTinhSP();
            } else if (rbnCamSau.isSelected()) {
                CameraSau selectedCamSau = ttspService.getAllCameraSau().get(selectedRow);
                updateSuccess = ttspService.updateCameraSau(getFormDataCameraSau(), selectedCamSau.getId());
                showDataTableCameraSau(ttspService.getAllCameraSau());
                clearThuocTinhSP();
            }

            if (updateSuccess) {
                JOptionPane.showMessageDialog(this, "Cập Nhật Thất Bại!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập Nhật Thành Công!");
            }
        }
    }//GEN-LAST:event_btnUpdateThuocTinhActionPerformed

    private void btnRemoveThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveThuocTinhActionPerformed
        int selectedRow = tblThuocTinhSP.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm đã chọn không?", "Thông Báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean removeSuccess = false;
            if (rbnPin.isSelected()) {
                Pin removePin = ttspService.getAllPin().get(selectedRow);
                removeSuccess = ttspService.removePin(removePin.getId());
                showDataTablePin(ttspService.getAllPin());
                setDataCboPin(pinService.getAll());
                clearThuocTinhSP();
                showDataTableCTSP(ctspService.getAllCTSP());
            } else if (rbnCpu.isSelected()) {
                CPU removeCPU = ttspService.getAllCPU().get(selectedRow);
                removeSuccess = ttspService.removeCPU(removeCPU.getId());
                showDataTableCPU(ttspService.getAllCPU());
                setDataCboCpu(cpuService.getAll());
                clearThuocTinhSP();
                showDataTableCTSP(ctspService.getAllCTSP());
            } else if (rbnManHinh.isSelected()) {
                ManHinh removeManHinh = ttspService.getAllManHinh().get(selectedRow);
                removeSuccess = ttspService.removeManHinh(removeManHinh.getId());
                showDataTableManHinh(ttspService.getAllManHinh());
                setDataCboManHinh(mhService.getAll());
                clearThuocTinhSP();
                showDataTableCTSP(ctspService.getAllCTSP());
            } else if (rbnMauSac.isSelected()) {
                MauSac removeMauSac = ttspService.getAllMauSac().get(selectedRow);
                removeSuccess = ttspService.removeMauSac(removeMauSac.getId());
                showDataTableMauSac(ttspService.getAllMauSac());
                clearThuocTinhSP();
            } else if (rbnRam.isSelected()) {
                Ram removeRam = ttspService.getAllRam().get(selectedRow);
                removeSuccess = ttspService.removeRam(removeRam.getId());
                showDataTableRam(ttspService.getAllRam());
                clearThuocTinhSP();
            } else if (rbnRom.isSelected()) {
                BoNho removeBoNho = ttspService.getAllBoNho().get(selectedRow);
                removeSuccess = ttspService.removeBoNho(removeBoNho.getId());
                showDataTableBoNho(ttspService.getAllBoNho());
                clearThuocTinhSP();
            } else if (rbnCamTruoc.isSelected()) {
                CameraTruoc selectedCamTruoc = ttspService.getAllCameraTruoc().get(selectedRow);
                removeSuccess = ttspService.removeCameraTruoc(selectedCamTruoc.getId());
                showDataTableCameraTruoc(ttspService.getAllCameraTruoc());
                clearThuocTinhSP();
            } else if (rbnCamSau.isSelected()) {
                CameraSau selectedCamSau = ttspService.getAllCameraSau().get(selectedRow);
                removeSuccess = ttspService.removeCameraSau(selectedCamSau.getId());
                showDataTableCameraSau(ttspService.getAllCameraSau());
                clearThuocTinhSP();
            }

            if (removeSuccess) {
                JOptionPane.showMessageDialog(this, "Xóa Thất Bại!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa Thành Công!");
            }
        }
    }//GEN-LAST:event_btnRemoveThuocTinhActionPerformed

    private void btnClearThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearThuocTinhActionPerformed
        clearThuocTinhSP();
    }//GEN-LAST:event_btnClearThuocTinhActionPerformed

    private void rbnHetHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnHetHangActionPerformed
        listHetHang = dspService.getAllHetHang();
        showDataTableSP(listHetHang);
    }//GEN-LAST:event_rbnHetHangActionPerformed

    private void rbnConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnConHangActionPerformed
        listConHang = dspService.getAllConHang();
        showDataTableSP(listConHang);
    }//GEN-LAST:event_rbnConHangActionPerformed

    private void rbnTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnTatCaActionPerformed
        showDataTableSP(dspService.getAll());
    }//GEN-LAST:event_rbnTatCaActionPerformed

    private void cboNsxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNsxActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboNsxActionPerformed

    private void cboPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPinActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboPinActionPerformed

    private void cboManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboManHinhActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboManHinhActionPerformed

    private void cboCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCPUActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboCPUActionPerformed

    private void rbnCamTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnCamTruocActionPerformed
        showDataTableCameraTruoc(ttspService.getAllCameraTruoc());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnCamTruocActionPerformed

    private void rbnCamSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnCamSauActionPerformed
        showDataTableCameraSau(ttspService.getAllCameraSau());
        clearThuocTinhSP();
    }//GEN-LAST:event_rbnCamSauActionPerformed


    private void cboGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiaActionPerformed
        filterCTSP();
    }//GEN-LAST:event_cboGiaActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        boolean isChecked = jCheckBox1.isSelected();
        if (isChecked) {
            int rowCount = tblModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                tblModel.setValueAt(true, i, 13);
            }
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnAddSp;
    private mobileworld.swing.ButtonCustom btnAddThuocTinh;
    private mobileworld.swing.ButtonCustom btnClear;
    private mobileworld.swing.ButtonCustom btnClearSp;
    private mobileworld.swing.ButtonCustom btnClearThuocTinh;
    private mobileworld.swing.ButtonCustom btnQuetQR;
    private mobileworld.swing.ButtonCustom btnRemoveThuocTinh;
    private mobileworld.swing.ButtonCustom btnTaiQR;
    private mobileworld.swing.ButtonCustom btnUpdateSp;
    private mobileworld.swing.ButtonCustom btnUpdateThuocTinh;
    private mobileworld.swing.ButtonCustom buttonCustom10;
    private mobileworld.swing.ButtonCustom buttonCustom16;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private mobileworld.swing.Combobox cboCPU;
    private mobileworld.swing.Combobox cboGia;
    private mobileworld.swing.Combobox cboManHinh;
    private mobileworld.swing.Combobox cboNsx;
    private mobileworld.swing.Combobox cboPin;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private mobileworld.swing.MaterialTabbed materialTabbed1;
    private javax.swing.JPanel panelSanPham;
    private javax.swing.JPanel panelSanPhamCT;
    private javax.swing.JPanel panelThuocTinhSP;
    private javax.swing.JRadioButton rbnCamSau;
    private javax.swing.JRadioButton rbnCamTruoc;
    private javax.swing.JRadioButton rbnConHang;
    private javax.swing.JRadioButton rbnCpu;
    private javax.swing.JRadioButton rbnHetHang;
    private javax.swing.JRadioButton rbnManHinh;
    private javax.swing.JRadioButton rbnMauSac;
    private javax.swing.JRadioButton rbnPin;
    private javax.swing.JRadioButton rbnRam;
    private javax.swing.JRadioButton rbnRom;
    private javax.swing.JRadioButton rbnTatCa;
    private mobileworld.swing.Table tblCTSP;
    private mobileworld.swing.Table tblSP;
    private mobileworld.swing.Table tblThuocTinhSP;
    private mobileworld.swing.TextField txtMaSP;
    private mobileworld.swing.TextField txtTenSP;
    private mobileworld.swing.TextField txtTenThuocTinhSP;
    private mobileworld.swing.TextField txtTimKiem;
    private mobileworld.swing.TextField txtTimKiemSP;
    // End of variables declaration//GEN-END:variables

}
