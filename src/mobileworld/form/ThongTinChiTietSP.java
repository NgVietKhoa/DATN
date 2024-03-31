package mobileworld.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import mobileworld.event.DataChangeListener;
import mobileworld.main.SessionStorage;
import mobileworld.model.BoNho;
import mobileworld.model.CPU;
import mobileworld.model.CameraSau;
import mobileworld.model.CameraTruoc;
import mobileworld.model.ChiTietSP;
import mobileworld.model.ManHinh;
import mobileworld.model.MauSac;
import mobileworld.model.NhaSanXuat;
import mobileworld.model.Pin;
import mobileworld.model.Ram;
import mobileworld.service.ChiTietSanPhamService.BoNhoService;
import mobileworld.service.ChiTietSanPhamService.CameraSauService;
import mobileworld.service.ChiTietSanPhamService.CameraTruocService;
import mobileworld.service.ChiTietSanPhamService.ChiTietSPService;
import mobileworld.service.ChiTietSanPhamService.CpuService;
import mobileworld.service.ChiTietSanPhamService.DongSPService;
import mobileworld.service.ChiTietSanPhamService.ImelService;
import mobileworld.service.ChiTietSanPhamService.ManHinhService;
import mobileworld.service.ChiTietSanPhamService.MauSacService;
import mobileworld.service.ChiTietSanPhamService.NhaSanXuatService;
import mobileworld.service.ChiTietSanPhamService.PinService;
import mobileworld.service.ChiTietSanPhamService.RamService;
import mobileworld.viewModel.DongSPViewModel;

public class ThongTinChiTietSP extends javax.swing.JFrame {

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
    private final ChiTietSPService ctspService = new ChiTietSPService();
    private final ImelService imelService = new ImelService();
    private final String idChiTietSP;

    public ThongTinChiTietSP(String idChiTietSP, String CameraSau, String CameraTruoc, String Cpu, String Imel, String ManHinh, String MauSac, String NSX, String Pin, String Ram, String Rom, String TenDsp, BigDecimal gia, String moTa) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Thông Tin Chi Tiết Sản Phẩm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        cboPin.setSelectedItem(Pin);
        cboManHinh.setSelectedItem(ManHinh);
        cboMauSac.setSelectedItem(MauSac);
        cboCPU.setSelectedItem(Cpu);
        cboRam.setSelectedItem(Ram);
        cboRom.setSelectedItem(Rom);
        cboCameraSau.setSelectedItem(CameraSau);
        cboCameraTruoc.setSelectedItem(CameraTruoc);
        cboNsx.setSelectedItem(NSX);
        cboTenDsp.setSelectedItem(TenDsp);
        txtAreaImel.setText(Imel);
        txtAreaMoTa.setText(moTa);
        txtGia.setText(String.valueOf(gia));
        this.idChiTietSP = idChiTietSP;
    }

    public DataChangeListener changeListener = () -> {
    };

    public void setDataChangeListener(DataChangeListener listener) {
        this.changeListener = listener;
    }

    private void setTextArea() {
        txtAreaMoTa.setLineWrap(true);
        txtAreaMoTa.setWrapStyleWord(true);
        jScrollPane5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        txtAreaImel.setLineWrap(true);
        txtAreaImel.setWrapStyleWord(true);
        jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void setDataCboPin(List<Pin> setPin) {
        cbbPin.removeAllElements();
        cbbPin.addElement(null);

        for (Pin pinE : setPin) {
            cbbPin.addElement(pinE.getDungLuongPin());
        }
        cboPin.setSelectedItem(null);
    }

    private void setDataCboManHinh(List<ManHinh> setMh) {
        cbbManHinh.removeAllElements();
        cbbManHinh.addElement(null);

        for (ManHinh mh : setMh) {
            cbbManHinh.addElement(mh.getLoaiManHinh());
        }
        cboManHinh.setSelectedItem(null);
    }

    private void setDataCboMauSac(List<MauSac> setMs) {
        cbbMauSac.removeAllElements();
        cbbMauSac.addElement(null);

        for (MauSac ms : setMs) {
            cbbMauSac.addElement(ms.getTenMau());
        }
        cboMauSac.setSelectedItem(null);
    }

    private void setDataCboCpu(List<CPU> setCpu) {
        cbbCpu.removeAllElements();
        cbbCpu.addElement(null);

        for (CPU cpu : setCpu) {
            cbbCpu.addElement(cpu.getCpu());
        }
        cboCPU.setSelectedItem(null);
    }

    private void setDataCboRam(List<Ram> setRam) {
        cbbRam.removeAllElements();
        cbbRam.addElement(null);

        for (Ram ram : setRam) {
            cbbRam.addElement(ram.getDungLuongRam());
        }
        cboRam.setSelectedItem(null);
    }

    private void setDataCboRom(List<BoNho> setRom) {
        cbbRom.removeAllElements();
        cbbRom.addElement(null);

        for (BoNho rom : setRom) {
            cbbRom.addElement(rom.getDungLuongBoNho());
        }
        cboRom.setSelectedItem(null);
    }

    private void setDataCboCameraSau(List<CameraSau> setCamSau) {
        cbbCameraSau.removeAllElements();
        cbbCameraSau.addElement(null);

        for (CameraSau cam : setCamSau) {
            cbbCameraSau.addElement(cam.getSoMP());
        }
        cboCameraTruoc.setSelectedItem(null);
    }

    private void setDataCboCameraTruoc(List<CameraTruoc> setCamTruoc) {
        cbbCameraTruoc.removeAllElements();
        cbbCameraTruoc.addElement(null);

        for (CameraTruoc cam : setCamTruoc) {
            cbbCameraTruoc.addElement(cam.getSoMP());
        }
        cboCameraTruoc.setSelectedItem(null);
    }

    private void setDataCboNsx(List<NhaSanXuat> setNsx) {
        cbbNsx.removeAllElements();
        cbbNsx.addElement(null);

        for (NhaSanXuat nsx : setNsx) {
            cbbNsx.addElement(nsx.getTenNsx());
        }
        cboNsx.setSelectedItem(null);
    }

    private void setDataCboDsp(List<DongSPViewModel> setDsp) {
        cbbDongSP.removeAllElements();
        cbbDongSP.addElement(null);

        for (DongSPViewModel dsp : setDsp) {
            cbbDongSP.addElement(dsp.getTenDsp());
        }
        cboTenDsp.setSelectedItem(null);
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

        String giaText = txtGia.getText().trim();
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

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Imel phải là số nguyên!");
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cboPin = new mobileworld.swing.Combobox();
        cboRam = new mobileworld.swing.Combobox();
        cboManHinh = new mobileworld.swing.Combobox();
        cboNsx = new mobileworld.swing.Combobox();
        cboTenDsp = new mobileworld.swing.Combobox();
        cboMauSac = new mobileworld.swing.Combobox();
        cboCPU = new mobileworld.swing.Combobox();
        cboRom = new mobileworld.swing.Combobox();
        cboCameraTruoc = new mobileworld.swing.Combobox();
        cboCameraSau = new mobileworld.swing.Combobox();
        txtGia = new mobileworld.swing.TextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaImel = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaMoTa = new javax.swing.JTextArea();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông Tin Chi Tiết Sản Phẩm");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(4, 0, 30, 30));

        cboPin.setLabeText("Pin");
        jPanel2.add(cboPin);

        cboRam.setLabeText("RAM");
        jPanel2.add(cboRam);

        cboManHinh.setLabeText("Màn Hình");
        jPanel2.add(cboManHinh);

        cboNsx.setLabeText("Nhà Sản Xuất");
        jPanel2.add(cboNsx);

        cboTenDsp.setLabeText("Tên Sản Phẩm");
        jPanel2.add(cboTenDsp);

        cboMauSac.setLabeText("Màu Sắc");
        jPanel2.add(cboMauSac);

        cboCPU.setLabeText("CPU");
        jPanel2.add(cboCPU);

        cboRom.setLabeText("ROM");
        jPanel2.add(cboRom);

        cboCameraTruoc.setLabeText("Camera Trước");
        jPanel2.add(cboCameraTruoc);

        cboCameraSau.setLabeText("Camera Sau");
        jPanel2.add(cboCameraSau);

        txtGia.setForeground(new java.awt.Color(102, 102, 102));
        txtGia.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtGia.setLabelText("Giá");
        jPanel2.add(txtGia);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(153, 153, 153))); // NOI18N

        txtAreaImel.setColumns(20);
        txtAreaImel.setRows(5);
        txtAreaImel.setBorder(null);
        txtAreaImel.setPreferredSize(new java.awt.Dimension(220, 40));
        txtAreaImel.setRequestFocusEnabled(false);
        jScrollPane4.setViewportView(txtAreaImel);

        jPanel2.add(jScrollPane4);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mô Tả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(153, 153, 153))); // NOI18N

        txtAreaMoTa.setColumns(20);
        txtAreaMoTa.setRows(5);
        txtAreaMoTa.setBorder(null);
        txtAreaMoTa.setPreferredSize(new java.awt.Dimension(220, 40));
        jScrollPane5.setViewportView(txtAreaMoTa);

        jPanel2.add(jScrollPane5);

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-refresh-24.png"))); // NOI18N
        buttonCustom1.setText("Cập Nhật");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        if (check()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không", "Thông Báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ctspService.update(getFormData(), idChiTietSP);
                changeListener.notifyDataChangeListeners();
                JOptionPane.showMessageDialog(this, "Sửa Thành Công!");
                setVisible(false);
                dispose();
                return;
            }
            JOptionPane.showMessageDialog(this, "Sửa Thất Bại!");
        }

    }//GEN-LAST:event_buttonCustom1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom buttonCustom1;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea txtAreaImel;
    private javax.swing.JTextArea txtAreaMoTa;
    private mobileworld.swing.TextField txtGia;
    // End of variables declaration//GEN-END:variables
}
