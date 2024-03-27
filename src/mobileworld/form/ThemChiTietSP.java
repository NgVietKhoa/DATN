package mobileworld.form;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import mobileworld.dialog.CPUDialog;
import mobileworld.dialog.CameraSauDialog;
import mobileworld.dialog.CameraTruocDialog;
import mobileworld.dialog.ManHinhDialog;
import mobileworld.dialog.MauSacDialog;
import mobileworld.dialog.PinDialog;
import mobileworld.dialog.RamDialog;
import mobileworld.dialog.RomDialog;
import mobileworld.dialog.SPDialog;
import mobileworld.model.BoNho;
import mobileworld.model.CPU;
import mobileworld.model.DongSP;
import mobileworld.model.ManHinh;
import mobileworld.model.MauSac;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.Pin;
import mobileworld.model.Ram;
import mobileworld.model.CameraSau;
import mobileworld.event.DataChangeListener;
import mobileworld.event.EventCPUDialog;
import mobileworld.event.EventCameraSauDialog;
import mobileworld.event.EventCameraTruocDialog;
import mobileworld.event.EventManHinhDialog;
import mobileworld.event.EventMauSacDialog;
import mobileworld.event.EventPinDialog;
import mobileworld.event.EventRamDialog;
import mobileworld.event.EventRomDialog;
import mobileworld.event.EventSPDialog;
import mobileworld.main.SessionStorage;
import mobileworld.model.CameraTruoc;
import mobileworld.model.ChiTietSP;
import mobileworld.model.Imel;
import mobileworld.service.ChiTietSanPhamService.BoNhoService;
import mobileworld.service.ChiTietSanPhamService.CpuService;
import mobileworld.service.ChiTietSanPhamService.DongSPService;
import mobileworld.service.ChiTietSanPhamService.ManHinhService;
import mobileworld.service.ChiTietSanPhamService.MauSacService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
import mobileworld.service.ChiTietSanPhamService.RamService;
import mobileworld.service.ChiTietSanPhamService.CameraSauService;
import mobileworld.service.ChiTietSanPhamService.CameraTruocService;
import mobileworld.service.ChiTietSanPhamService.ChiTietSPService;
import mobileworld.service.ChiTietSanPhamService.ImelService;
import mobileworld.service.ChiTietSanPhamService.ThuocTinhSPService;
import mobileworld.viewModel.DongSPViewModel;

public class ThemChiTietSP extends javax.swing.JPanel implements DataChangeListener,
        EventCPUDialog,
        EventManHinhDialog,
        EventMauSacDialog,
        EventPinDialog,
        EventRamDialog,
        EventRomDialog,
        EventSPDialog,
        EventCameraSauDialog,
        EventCameraTruocDialog {

    private final SPDialog spDialog;
    private final PinDialog pinDialog;
    private final ManHinhDialog manHinhDialog;
    private final MauSacDialog mauSacDialog;
    private final CPUDialog cPUDialog;
    private final RamDialog ramDialog;
    private final RomDialog romDialog;
    private final CameraSauDialog cameraSauDialog;
    private final CameraTruocDialog cameraTruocDialog;
    private DefaultComboBoxModel cbbPin = new DefaultComboBoxModel();
    private final PinService pinService = new PinService();
    private DefaultComboBoxModel cbbManHinh = new DefaultComboBoxModel();
    private final ManHinhService mhService = new ManHinhService();
    private DefaultComboBoxModel cbbMauSac = new DefaultComboBoxModel();
    private final MauSacService msService = new MauSacService();
    private DefaultComboBoxModel cbbCpu = new DefaultComboBoxModel();
    private final CpuService cpuService = new CpuService();
    private DefaultComboBoxModel cbbRam = new DefaultComboBoxModel();
    private final RamService ramService = new RamService();
    private DefaultComboBoxModel cbbRom = new DefaultComboBoxModel();
    private final BoNhoService romService = new BoNhoService();
    private DefaultComboBoxModel cbbCameraSau = new DefaultComboBoxModel();
    private final CameraSauService cameraSauService = new CameraSauService();
    private DefaultComboBoxModel cbbCameraTruoc = new DefaultComboBoxModel();
    private final CameraTruocService cameraTruocService = new CameraTruocService();
    private DefaultComboBoxModel cbbDongSP = new DefaultComboBoxModel();
    private final DongSPService dspService = new DongSPService();
    private DefaultComboBoxModel cbbNsx = new DefaultComboBoxModel();
    private final NhaSanXuatService NsxService = new NhaSanXuatService();
    private final ImelService imelService = new ImelService();
    private final ChiTietSPService chiTietSPService = new ChiTietSPService();


    public ThemChiTietSP() {
        initComponents();
        //spdialog
        spDialog = new SPDialog();
        spDialog.changeListener.addDataChangeListener(this);
        spDialog.changeListener.setEventDataChangeListener(this);
        //pindialog
        pinDialog = new PinDialog();
        pinDialog.changeListener.addDataChangeListener(this);
        pinDialog.changeListener.setEventDataChangeListener(this);
        //manhinhdialog
        manHinhDialog = new ManHinhDialog();
        manHinhDialog.changeListener.addDataChangeListener(this);
        manHinhDialog.changeListener.setEventDataChangeListener(this);
        //mausacdialog
        mauSacDialog = new MauSacDialog();
        mauSacDialog.changeListener.addDataChangeListener(this);
        mauSacDialog.changeListener.setEventDataChangeListener(this);
        //cpudialog
        cPUDialog = new CPUDialog();
        cPUDialog.changeListener.addDataChangeListener(this);
        cPUDialog.changeListener.setEventDataChangeListener(this);
        //ramdialog
        ramDialog = new RamDialog();
        ramDialog.changeListener.addDataChangeListener(this);
        ramDialog.changeListener.setEventDataChangeListener(this);
        //romdialog
        romDialog = new RomDialog();
        romDialog.changeListener.addDataChangeListener(this);
        romDialog.changeListener.setEventDataChangeListener(this);
        //cameraSaudialog
        cameraSauDialog = new CameraSauDialog();
        cameraSauDialog.changeListener.addDataChangeListener(this);
        cameraSauDialog.changeListener.setEventDataChangeListener(this);
        //cameraTruocdialog
        cameraTruocDialog = new CameraTruocDialog();
        cameraTruocDialog.changeListener.addDataChangeListener(this);
        cameraTruocDialog.changeListener.setEventDataChangeListener(this);
        //cbo
        cbbPin = (DefaultComboBoxModel) cboPin.getModel();
        cbbManHinh = (DefaultComboBoxModel) cboManHinh.getModel();
        cbbMauSac = (DefaultComboBoxModel) cboMauSac.getModel();
        cbbCpu = (DefaultComboBoxModel) cboCPU.getModel();
        cbbRam = (DefaultComboBoxModel) cboRam.getModel();
        cbbRom = (DefaultComboBoxModel) cboRom.getModel();
        cbbCameraSau = (DefaultComboBoxModel) cboCameraSau.getModel();
        cbbCameraTruoc = (DefaultComboBoxModel) cboCameraTruoc.getModel();
        cbbNsx = (DefaultComboBoxModel) cboNsx.getModel();
        cbbDongSP = (DefaultComboBoxModel) cboTenDsp.getModel();
        cbbCameraSau = (DefaultComboBoxModel) cboCameraSau.getModel();
        cbbCameraTruoc = (DefaultComboBoxModel) cboCameraTruoc.getModel();
        setDataCboPin(pinService.getAll());
        setDataCboManHinh(mhService.getAll());
        setDataCboMauSac(msService.getAll());
        setDataCboCpu(cpuService.getAll());
        setDataCboRam(ramService.getAll());
        setDataCboRom(romService.getAll());
        setDataCboNsx(NsxService.getAll());
        setDataCboDsp(dspService.getAll());
        setDataCboCameraSau(cameraSauService.getAll());
        setDataCboCameraTruoc(cameraTruocService.getAll());
        setTextArea();
    }

    private void setTextArea() {
        txtAreaImel.setLineWrap(true);
        txtAreaImel.setWrapStyleWord(true);
        jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        txtAreaMoTa.setLineWrap(true);
        txtAreaMoTa.setWrapStyleWord(true);
        jScrollPane5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    //set date combobox
    private void setDataCboPin(List<Pin> setPin) {
        cbbPin.removeAllElements();

        for (Pin pinE : setPin) {
            cbbPin.addElement(pinE.getDungLuongPin());
        }
    }

    private void setDataCboManHinh(List<ManHinh> setMh) {
        cbbManHinh.removeAllElements();

        for (ManHinh mh : setMh) {
            cbbManHinh.addElement(mh.getLoaiManHinh());
        }
    }

    private void setDataCboMauSac(List<MauSac> setMs) {
        cbbMauSac.removeAllElements();

        for (MauSac ms : setMs) {
            cbbMauSac.addElement(ms.getTenMau());
        }
    }

    private void setDataCboCpu(List<CPU> setCpu) {
        cbbCpu.removeAllElements();

        for (CPU cpu : setCpu) {
            cbbCpu.addElement(cpu.getCpu());
        }
    }

    private void setDataCboRam(List<Ram> setRam) {
        cbbRam.removeAllElements();

        for (Ram ram : setRam) {
            cbbRam.addElement(ram.getDungLuongRam());
        }
    }

    private void setDataCboRom(List<BoNho> setRom) {
        cbbRom.removeAllElements();

        for (BoNho rom : setRom) {
            cbbRom.addElement(rom.getDungLuongBoNho());
        }
    }

    private void setDataCboCameraSau(List<CameraSau> setCamSau) {
        cbbCameraSau.removeAllElements();

        for (CameraSau cam : setCamSau) {
            cbbCameraSau.addElement(cam.getSoMP());
        }
    }

    private void setDataCboCameraTruoc(List<CameraTruoc> setCamTruoc) {
        cbbCameraTruoc.removeAllElements();

        for (CameraTruoc cam : setCamTruoc) {
            cbbCameraTruoc.addElement(cam.getSoMP());
        }
    }

    private void setDataCboNsx(List<NhaSanXuat> setNsx) {
        cbbNsx.removeAllElements();

        for (NhaSanXuat nsx : setNsx) {
            cbbNsx.addElement(nsx.getTenNsx());
        }
    }

    private void setDataCboDsp(List<DongSPViewModel> setDsp) {
        cbbDongSP.removeAllElements();

        for (DongSPViewModel dsp : setDsp) {
            cbbDongSP.addElement(dsp.getTenDsp());
        }
    }

    @Override
    public void onDataChange() {
        if (spDialog.isVisible()) {
            setDataCboDsp(dspService.getAll());
        }

        if (pinDialog.isVisible()) {
            setDataCboPin(pinService.getAll());
        }

        if (manHinhDialog.isVisible()) {
            setDataCboManHinh(mhService.getAll());
        }

        if (mauSacDialog.isVisible()) {
            setDataCboMauSac(msService.getAll());
        }

        if (ramDialog.isVisible()) {
            setDataCboRam(ramService.getAll());
        }

        if (romDialog.isVisible()) {
            setDataCboRom(romService.getAll());
        }

        if (cPUDialog.isVisible()) {
            setDataCboCpu(cpuService.getAll());
        }

        if (cameraSauDialog.isVisible()) {
            setDataCboCameraSau(cameraSauService.getAll());
        }

        if (cameraTruocDialog.isVisible()) {
            setDataCboCameraTruoc(cameraTruocService.getAll());
        }
    }

    @Override
    public boolean add(DongSP dsp) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(DongSP sp, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(Pin pin) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(Pin pin, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(ManHinh mh) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(ManHinh mh, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(MauSac ms) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(MauSac ms, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(CPU cpu) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(CPU cpu, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(Ram ram) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(Ram ram, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(BoNho rom) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(BoNho rom, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(CameraSau cam) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(CameraSau cam, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean add(CameraTruoc cam) {
        onDataChange();
        return true;
    }

    @Override
    public boolean update(CameraTruoc cam, String id) {
        onDataChange();
        return true;
    }

    @Override
    public boolean remove(String id) {
        onDataChange();
        return true;
    }

    public boolean check() {
        if (cbbDongSP.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Dòng Sản Phẩm!");
            return false;
        }

        if (cbbNsx.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Nhà Sản Xuất!");
            return false;
        }

        if (cbbMauSac.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Màu Sắc!");
            return false;
        }

        if (cbbManHinh.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Loại Màn Hình!");
            return false;
        }

        if (cbbCpu.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn CPU!");
            return false;
        }

        if (cbbRam.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Ram!");
            return false;
        }

        String giaText = txtGia.getText().trim().replace(",", "");
        if (giaText.equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy Nhập Giá Bán!");
            return false;
        }

        if (cbbRom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Rom!");
            return false;
        }

        if (cbbPin.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Dung Lượng Pin!");
            return false;
        }

        if (cbbCameraTruoc.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Camera Trước!");
            return false;
        }

        if (cbbCameraSau.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Hãy Chọn Camera Sau!");
            return false;
        }

        String imelText = txtAreaImel.getText().trim();
        if (imelText.equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy Nhập Imel!");
            return false;
        }

        try {
            long imel = Long.parseLong(imelText);
            if (imel < 0) {
                JOptionPane.showMessageDialog(this, "Imel phải là số không âm!");
                return false;
            }

            List<Imel> existingImels = imelService.getImel();

            // Kiểm tra xem IMEI đã tồn tại trong danh sách hay không
            for (Imel imelModel : existingImels) {
                if (imelModel.getImel().equals(imelText)) {
                    JOptionPane.showMessageDialog(this, "Imel đã tồn tại trong cơ sở dữ liệu!");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Imel phải là số nguyên!");
            return false;
        }

        if (txtAreaMoTa.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Hãy Nhập Mô Tả Sản Phẩm!");
            return false;
        }

        try {
            BigDecimal gia = new BigDecimal(giaText);
            // Kiểm tra giá là số nguyên dương
            if (gia.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Giá phải là số nguyên dương!");
                return false;
            }
        } catch (NumberFormatException | ArithmeticException e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số nguyên!");
            return false;
        }

        return true;
    }

    private ChiTietSP getFormData() {
        String tenDsp = (String) cboTenDsp.getSelectedItem();
        String moTa = txtAreaMoTa.getText();
        BigDecimal giaBan = null;
        try {
            giaBan = new BigDecimal(txtGia.getText().trim().replace(",", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá Bán Phải Là Số", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String imel = txtAreaImel.getText();
        System.out.println("" + imel);
        LocalDate dateTime = LocalDate.now();
        String Cpu = (String) cboCPU.getSelectedItem();
        String Nsx = (String) cboNsx.getSelectedItem();
        String ManHinh = (String) cboManHinh.getSelectedItem();
        String MauSac = (String) cboMauSac.getSelectedItem();
        String Pin = (String) cboPin.getSelectedItem();
        String Ram = (String) cboRam.getSelectedItem();
        String BoNho = (String) cboRom.getSelectedItem();
        String CameraTruoc = (String) cboCameraTruoc.getSelectedItem();
        String CameraSau = (String) cboCameraSau.getSelectedItem();
        String nhanVien = SessionStorage.getInstance().getUsername();

        ChiTietSP ctsp = new ChiTietSP(imel, Nsx, tenDsp, MauSac, Pin, ManHinh, Ram, BoNho, Cpu, CameraTruoc, CameraSau, moTa, giaBan, 1, dateTime, nhanVien);

        return ctsp;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAddThuocTinhSP = new javax.swing.JPanel();
        btnQuayLai = new mobileworld.swing.ButtonCustom();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cboTenDsp = new mobileworld.swing.Combobox();
        btnTenDsp = new mobileworld.swing.ButtonCustom();
        jPanel3 = new javax.swing.JPanel();
        cboNsx = new mobileworld.swing.Combobox();
        btnNsx = new mobileworld.swing.ButtonCustom();
        jPanel4 = new javax.swing.JPanel();
        cboMauSac = new mobileworld.swing.Combobox();
        btnMauSac = new mobileworld.swing.ButtonCustom();
        jPanel5 = new javax.swing.JPanel();
        cboManHinh = new mobileworld.swing.Combobox();
        btnManHinh = new mobileworld.swing.ButtonCustom();
        jPanel6 = new javax.swing.JPanel();
        cboCPU = new mobileworld.swing.Combobox();
        btnCpu = new mobileworld.swing.ButtonCustom();
        jPanel7 = new javax.swing.JPanel();
        cboRam = new mobileworld.swing.Combobox();
        btnRam = new mobileworld.swing.ButtonCustom();
        jPanel8 = new javax.swing.JPanel();
        cboRom = new mobileworld.swing.Combobox();
        btnRom = new mobileworld.swing.ButtonCustom();
        jPanel9 = new javax.swing.JPanel();
        cboPin = new mobileworld.swing.Combobox();
        btnPin = new mobileworld.swing.ButtonCustom();
        jPanel10 = new javax.swing.JPanel();
        cboCameraTruoc = new mobileworld.swing.Combobox();
        btnCameraTruoc = new mobileworld.swing.ButtonCustom();
        jPanel11 = new javax.swing.JPanel();
        cboCameraSau = new mobileworld.swing.Combobox();
        btnCameraSau = new mobileworld.swing.ButtonCustom();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaImel = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaMoTa = new javax.swing.JTextArea();
        txtGia = new mobileworld.swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        btnThem = new mobileworld.swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1377, 695));

        panelAddThuocTinhSP.setBackground(new java.awt.Color(255, 255, 255));

        btnQuayLai.setForeground(new java.awt.Color(255, 255, 255));
        btnQuayLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-back-24.png"))); // NOI18N
        btnQuayLai.setText("Quay Lại");
        btnQuayLai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel2.setLayout(new java.awt.GridLayout(7, 0, 15, 15));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        cboTenDsp.setLabeText("Tên Sản Phẩm");

        btnTenDsp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnTenDsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTenDspActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cboTenDsp, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTenDsp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboTenDsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTenDsp, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        cboNsx.setLabeText("Nhà Sản Xuất");

        btnNsx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnNsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNsxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cboNsx, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNsx, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboNsx, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNsx, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        cboMauSac.setLabeText("Màu Sắc");

        btnMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cboMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        cboManHinh.setLabeText("Màn Hình");

        btnManHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManHinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(cboManHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboManHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        cboCPU.setLabeText("CPU");

        btnCpu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnCpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCpuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(cboCPU, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        cboRam.setLabeText("RAM");

        btnRam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(cboRam, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRam, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboRam, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRam, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        cboRom.setLabeText("ROM");

        btnRom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnRom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(cboRom, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRom, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboRom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRom, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        cboPin.setLabeText("Pin");

        btnPin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnPin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(cboPin, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboPin, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        cboCameraTruoc.setLabeText("Camera Trước");

        btnCameraTruoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnCameraTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCameraTruocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(cboCameraTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCameraTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboCameraTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCameraTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        cboCameraSau.setLabeText("Camera Sau");

        btnCameraSau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnCameraSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCameraSauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(cboCameraSau, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCameraSau, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboCameraSau, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCameraSau, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel11);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(153, 153, 153))); // NOI18N

        txtAreaImel.setColumns(20);
        txtAreaImel.setRows(5);
        txtAreaImel.setBorder(null);
        txtAreaImel.setPreferredSize(new java.awt.Dimension(220, 40));
        jScrollPane4.setViewportView(txtAreaImel);

        jPanel2.add(jScrollPane4);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mô Tả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(153, 153, 153))); // NOI18N

        txtAreaMoTa.setColumns(20);
        txtAreaMoTa.setRows(5);
        txtAreaMoTa.setBorder(null);
        txtAreaMoTa.setPreferredSize(new java.awt.Dimension(220, 40));
        jScrollPane5.setViewportView(txtAreaMoTa);

        jPanel2.add(jScrollPane5);

        txtGia.setForeground(new java.awt.Color(102, 102, 102));
        txtGia.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtGia.setLabelText("Giá");
        txtGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaKeyReleased(evt);
            }
        });
        jPanel2.add(txtGia);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(17, 153, 142));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thêm Sản Phẩm Chi Tiết");

        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAddThuocTinhSPLayout = new javax.swing.GroupLayout(panelAddThuocTinhSP);
        panelAddThuocTinhSP.setLayout(panelAddThuocTinhSPLayout);
        panelAddThuocTinhSPLayout.setHorizontalGroup(
            panelAddThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelAddThuocTinhSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAddThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelAddThuocTinhSPLayout.createSequentialGroup()
                        .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelAddThuocTinhSPLayout.setVerticalGroup(
            panelAddThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddThuocTinhSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAddThuocTinhSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAddThuocTinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAddThuocTinhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        ViewSanPham viewSanPham = new ViewSanPham();
        JPanel panelCTSP = viewSanPham.getPanelSPCT();
        JPanel viewThemThuocTinhSP = panelAddThuocTinhSP;
        viewThemThuocTinhSP.removeAll();
        viewThemThuocTinhSP.add(panelCTSP);
        viewThemThuocTinhSP.setLayout(new BorderLayout());
        viewThemThuocTinhSP.add(panelCTSP, BorderLayout.CENTER);
        viewThemThuocTinhSP.revalidate();
        viewThemThuocTinhSP.repaint();
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void btnTenDspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTenDspActionPerformed
        spDialog.setVisible(true);
    }//GEN-LAST:event_btnTenDspActionPerformed

    private void btnNsxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNsxActionPerformed

    }//GEN-LAST:event_btnNsxActionPerformed

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed
        mauSacDialog.setVisible(true);
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void btnManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManHinhActionPerformed
        manHinhDialog.setVisible(true);
    }//GEN-LAST:event_btnManHinhActionPerformed

    private void btnCpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCpuActionPerformed
        cPUDialog.setVisible(true);
    }//GEN-LAST:event_btnCpuActionPerformed

    private void btnRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRamActionPerformed
        ramDialog.setVisible(true);
    }//GEN-LAST:event_btnRamActionPerformed

    private void btnRomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRomActionPerformed
        romDialog.setVisible(true);
    }//GEN-LAST:event_btnRomActionPerformed

    private void btnPinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPinActionPerformed
        pinDialog.setVisible(true);
    }//GEN-LAST:event_btnPinActionPerformed

    private void btnCameraTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCameraTruocActionPerformed
        cameraTruocDialog.setVisible(true);
    }//GEN-LAST:event_btnCameraTruocActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (check()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn Thêm không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                chiTietSPService.add(getFormData());
                JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Thêm Thất Bại!");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCameraSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCameraSauActionPerformed
        cameraSauDialog.setVisible(true);
    }//GEN-LAST:event_btnCameraSauActionPerformed

    private void txtGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaKeyReleased
        if (!txtGia.getText().trim().isEmpty()) {
            String text = txtGia.getText();
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    txtGia.setText(decimalFormat.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập giá đúng định dạng số");
                    return;
                }
            }
        }
    }//GEN-LAST:event_txtGiaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnCameraSau;
    private mobileworld.swing.ButtonCustom btnCameraTruoc;
    private mobileworld.swing.ButtonCustom btnCpu;
    private mobileworld.swing.ButtonCustom btnManHinh;
    private mobileworld.swing.ButtonCustom btnMauSac;
    private mobileworld.swing.ButtonCustom btnNsx;
    private mobileworld.swing.ButtonCustom btnPin;
    private mobileworld.swing.ButtonCustom btnQuayLai;
    private mobileworld.swing.ButtonCustom btnRam;
    private mobileworld.swing.ButtonCustom btnRom;
    private mobileworld.swing.ButtonCustom btnTenDsp;
    private mobileworld.swing.ButtonCustom btnThem;
    private mobileworld.swing.Combobox cboCPU;
    private mobileworld.swing.Combobox cboCameraSau;
    private mobileworld.swing.Combobox cboCameraTruoc;
    private mobileworld.swing.Combobox cboManHinh;
    private mobileworld.swing.Combobox cboMauSac;
    private mobileworld.swing.Combobox cboNsx;
    private mobileworld.swing.Combobox cboPin;
    private mobileworld.swing.Combobox cboRam;
    private mobileworld.swing.Combobox cboRom;
    private mobileworld.swing.Combobox cboTenDsp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel panelAddThuocTinhSP;
    private javax.swing.JTextArea txtAreaImel;
    private javax.swing.JTextArea txtAreaMoTa;
    private mobileworld.swing.TextField txtGia;
    // End of variables declaration//GEN-END:variables
}
