/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package mobileworld.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.View;
import mobileworld.entity.PhieuGiamGia;
import mobileworld.service.PhieuGiamGiaService.PhieuGiamGiaService;
import mobileworld.thread.ThreadTinhTrang;
import mobileworld.tablecutoms.TableActionCellRender;
import mobileworld.tablecutoms.TableActionCellEditor;
import mobileworld.tablecutoms.TableActionCellRender;
import mobileworld.tablecutoms.TableActionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import mobileworld.main.SessionStorage;

/**
 *
 * @author Admin
 */
public class ViewGiamGia1 extends javax.swing.JPanel {

    private ViewInputPhieuGiamGia jpanelHien;

    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat spxHien = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat spx = new SimpleDateFormat("yyyy-MM-dd");
    private DefaultTableModel dtm = new DefaultTableModel();
    private List<PhieuGiamGia> ds = new ArrayList<>();
    private List<PhieuGiamGia> dsTK = new ArrayList<>();
    public PhieuGiamGiaService sr = new PhieuGiamGiaService();
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
    protected ThreadTinhTrang threadTinhTrang;

    /**
     * Creates new form GiamGia1
     */
    public ViewGiamGia1() {
        initComponents();
        ds = sr.getAll();
        jpanelHien = new ViewInputPhieuGiamGia();
        dtm = (DefaultTableModel) tblBang.getModel();

        threadTinhTrang = new ThreadTinhTrang(ds, sr);
        threadTinhTrang.start();
        try {
            threadTinhTrang.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ds = sr.getAll();
        tblBang.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        fillTable(ds);

        //Code chuyen trang 
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onDelete(int row) {
                if (tblBang.isEditing()) {
                    tblBang.getCellEditor().stopCellEditing();
                }

                int lc = JOptionPane.showConfirmDialog(new JFrame(), "Bạn có muốn Kích hoạt/Ngưng kích hoạt phiếu giảm giá không?", "Notification", JOptionPane.YES_NO_OPTION);
                if (lc == JOptionPane.YES_OPTION) {
                    try {
                        int index = tblBang.getSelectedRow();
                        if (index == -1) {
                            JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn lại dòng trong bảng");
                            return;
                        }

                        Date getNgayBD = cboNgayBatDau.getDate();
                        Date getNgayKT = cboNgayKT.getDate();

                        if (getNgayBD == null) {
                            JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn ngày bắt đầu");
                            return;
                        }

                        if (getNgayKT == null) {
                            JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn lại ngày kết thúc");
                            return;
                        }
                        LocalDateTime ngayBD = getNgayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        LocalDate ngayBDLocalDate = ngayBD.toLocalDate();
                        LocalDateTime ngayKT = getNgayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        LocalDate ngayKTLocalDate = ngayKT.toLocalDate();
                        LocalDateTime ngayThucTe = LocalDateTime.now();
                        LocalDate ngayTTLC = ngayThucTe.toLocalDate();
                        String maNV = SessionStorage.getInstance().getUsername();
                        int tt = 3;

                        if (ngayBDLocalDate.isEqual(ngayTTLC) && ngayKTLocalDate.isAfter(ngayTTLC)) {
                            tt = 1;
                        } else if (ngayBDLocalDate.isAfter(ngayTTLC) && ngayKTLocalDate.isAfter(ngayTTLC)) {
                            tt = 2;
                        } else if (ngayBDLocalDate.isBefore(ngayTTLC) && ngayKTLocalDate.isBefore(ngayTTLC)) {
                            tt = 0;
                        } else if (ngayBDLocalDate.isBefore(ngayTTLC) && ngayKTLocalDate.isAfter(ngayTTLC)) {
                            tt = 1;
                        } else if (ngayBDLocalDate.isEqual(ngayTTLC) && ngayKTLocalDate.isEqual(ngayTTLC)) {
                            tt = 1;
                        } else if (ngayBDLocalDate.isBefore(ngayTTLC) && ngayKTLocalDate.isEqual(ngayTTLC)) {
                            tt = 1;
                        }

                        if (dsTK.isEmpty()) {
                            PhieuGiamGia gg = ds.get(index);
                            sr.deleteData(gg.getID(), tt, maNV, ngayThucTe);
                            ds = sr.getAll();
                            fillTable(ds);
                        } else {
                            PhieuGiamGia gg = dsTK.get(index);
                            sr.deleteData(gg.getID(), tt, maNV, ngayThucTe);
                            timTrangThai();
                            fillTable(dsTK);
                        }

                        JOptionPane.showMessageDialog(new JFrame(), "Thay đổi trạng thái thành công");
                    } catch (Exception e) {
                        System.out.println("" + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Bạn đã chọn NO");
                }

            }
        };

        tblBang.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        tblBang.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event));
        tblBang.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = (String) value;
                if (status.equals("Đang diễn ra")) {
                    cellComponent.setForeground(new Color(0, 100, 0));
                    cellComponent.setBackground(Color.WHITE);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    cellComponent.setFont(font);
                } else if (status.equals("Đã kết thúc")) {
                    cellComponent.setForeground(new Color(100, 0, 0));
                    cellComponent.setBackground(Color.WHITE);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    cellComponent.setFont(font);
                } else if (status.equals("Sắp diễn ra")) {
                    cellComponent.setForeground(new Color(0, 0, 100));
                    cellComponent.setBackground(Color.WHITE);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    cellComponent.setFont(font);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(null);

                return cellComponent;
            }
        });

    }

    public void fillTable(List<PhieuGiamGia> GGList) {
        dtm.setRowCount(0);
        tblBang.setDefaultEditor(Object.class, null);
        int i = 0;
        float phanTram = 0;
        for (PhieuGiamGia gg : GGList) {
            i++;
            Date getNgayBD = Date.from(gg.getNgayBatDau().atZone(ZoneId.systemDefault()).toInstant());
            String ngayBD = spxHien.format(getNgayBD);
            Date getNgayKT = Date.from(gg.getNgayKetThuc().atZone(ZoneId.systemDefault()).toInstant());
            String ngayKT = spxHien.format(getNgayKT);
            String tt = "";
            if (gg.getTrangThai() == 1 && gg.getDeleted() == 1) {
                tt = "Đang diễn ra";
            }
            if (gg.getTrangThai() == 0) {
                tt = "Đã kết thúc";
            }
            if (gg.getDeleted() == 0) {
                tt = "Đã kết thúc";
            }
            if (gg.getTrangThai() == 2 && gg.getDeleted() == 1) {
                tt = "Sắp diễn ra";
            }

            phanTram = gg.getPhanTramGiam();
            String soTienGiamMax = decimalFormat.format(gg.getSoTiemGiamToiDa());
            String hoaDonMin = decimalFormat.format(gg.getHoatDonToiThieu());

            dtm.addRow(new Object[]{i, gg.getID(), gg.getTenGiamGia(), ngayBD, ngayKT, gg.getSoLuongDung(), hoaDonMin, phanTram, soTienGiamMax, tt});
        }

        tblBang.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.CENTER);
                Component cellComponent = super.getTableCellRendererComponent(jtable, value, bln1, bln1, i, i1);

                String status = (String) value; //Lấy giá trị Combo Box
                if (status.equals("Đang diễn ra")) {
                    cellComponent.setForeground(new Color(0, 100, 0));
                    cellComponent.setBackground(Color.WHITE);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    cellComponent.setFont(font);
                } else if (status.equals("Đã kết thúc")) {
                    cellComponent.setForeground(new Color(100, 0, 0));
                    cellComponent.setBackground(Color.WHITE);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    cellComponent.setFont(font);
                } else if (status.equals("Sắp diễn ra")) {
                    cellComponent.setForeground(new Color(0, 0, 100));
                    cellComponent.setBackground(Color.WHITE);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    cellComponent.setFont(font);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(null);

                return cellComponent;
            }
        });

    }

    public PhieuGiamGia getFormData() {
        String ten = txtTen.getText().trim();
      int soLan = Integer.valueOf(Integer.parseInt(txtSoLan.getText().trim().replace(",", "")));
        float phanTram = Float.valueOf(Float.parseFloat(txtPhanTramGiam.getText().trim().replace("%", "")));
        String hoaDonFormat = txtHoaDonToiThieu.getText().replace(",", "");
        String soTienFormat = txtSoTienGiamToiDa.getText().replace(",", "");
        float hoaDonMin = Float.valueOf(Float.parseFloat(hoaDonFormat));
        float soTiemGiamMax = Float.valueOf(Float.parseFloat(soTienFormat));
        String maNV = SessionStorage.getInstance().getUsername();
        int tt = 1;

        //Bien thoi gian
        Date getNgayBD = cboNgayBatDau.getDate();
        LocalDateTime ngayBD = getNgayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate ngayBDLocalDate = ngayBD.toLocalDate();
        Date getNgayKT = cboNgayKT.getDate();
        LocalDateTime ngayKT = getNgayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate ngayKTLocalDate = ngayKT.toLocalDate();
        LocalDateTime ngayThucTe = LocalDateTime.now();
        LocalDate ngayTTLC = ngayThucTe.toLocalDate();

        if (ngayBDLocalDate.equals(ngayTTLC) && ngayKTLocalDate.isAfter(ngayTTLC)) {
            tt = 1;
        } else if (ngayBDLocalDate.isAfter(ngayTTLC) && ngayKTLocalDate.isAfter(ngayTTLC)) {
            tt = 2;
        }

        PhieuGiamGia pgg = new PhieuGiamGia(ten, soLan, phanTram, soTiemGiamMax, hoaDonMin, ngayBD, ngayKT, tt, ten, 1, ngayThucTe, "", ngayThucTe, maNV);
        return pgg;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtID = new mobileworld.swing.TextField();
        txtTen = new mobileworld.swing.TextField();
        txtHoaDonToiThieu = new mobileworld.swing.TextField();
        txtSoTienGiamToiDa = new mobileworld.swing.TextField();
        jLabel4 = new javax.swing.JLabel();
        cboNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        cboNgayKT = new com.toedter.calendar.JDateChooser();
        txtSoLan = new mobileworld.swing.TextField();
        txtPhanTramGiam = new mobileworld.swing.TextField();
        btnUpdate = new mobileworld.swing.ButtonCustom();
        btnCallAdd = new mobileworld.swing.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new mobileworld.swing.Table();
        txtSearchAll = new mobileworld.swing.TextField();
        cboTrangThai = new mobileworld.swing.Combobox();
        btnRefesh = new mobileworld.swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Phiếu Giảm Giá");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtID.setLabelText("Mã Khuyến Mãi");
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDKeyReleased(evt);
            }
        });

        txtTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTen.setLabelText("Tên Khuyến Mãi");
        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKeyReleased(evt);
            }
        });

        txtHoaDonToiThieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtHoaDonToiThieu.setLabelText("Hóa Đơn Tối Thiểu");
        txtHoaDonToiThieu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHoaDonToiThieuKeyReleased(evt);
            }
        });

        txtSoTienGiamToiDa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoTienGiamToiDa.setLabelText("Số Tiền Giảm Tối Đa");
        txtSoTienGiamToiDa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoTienGiamToiDaActionPerformed(evt);
            }
        });
        txtSoTienGiamToiDa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoTienGiamToiDaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Ngày Bắt Đầu");

        cboNgayBatDau.setDateFormatString("dd-MM-yyyy");
        cboNgayBatDau.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Ngày Sử Dụng Cuối");

        cboNgayKT.setDateFormatString("dd-MM-yyyy");
        cboNgayKT.setOpaque(false);
        cboNgayKT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboNgayKTPropertyChange(evt);
            }
        });

        txtSoLan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoLan.setLabelText("Số Lượng Được Phép Sử Dụng");
        txtSoLan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLanKeyReleased(evt);
            }
        });

        txtPhanTramGiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPhanTramGiam.setLabelText("Phần Trăm Giảm");
        txtPhanTramGiam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhanTramGiamKeyReleased(evt);
            }
        });

        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-update-30.png"))); // NOI18N
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSoTienGiamToiDa, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                        .addComponent(txtHoaDonToiThieu, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                        .addComponent(cboNgayKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLan, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoaDonToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTienGiamToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnCallAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnCallAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-add-24.png"))); // NOI18N
        btnCallAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCallAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCallAddActionPerformed(evt);
            }
        });

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên", "Ngày BD", "Ngày SD cuối", "Số lượng", "HĐTT", "Số % giảm", "Giảm tối đa", "Trạng thái", "Hành động"
            }
        ));
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        txtSearchAll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSearchAll.setLabelText("Tìm kiếm");
        txtSearchAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAllKeyReleased(evt);
            }
        });

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "Đang diễn ra", "Sắp diễn ra", "Đã kết thúc" }));
        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboTrangThai.setLabeText("");
        cboTrangThai.setName(""); // NOI18N
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        btnRefesh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefesh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-clear-24.png"))); // NOI18N
        btnRefesh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtSearchAll, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
                                .addComponent(btnCallAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(txtSearchAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCallAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refeshText() {
        txtHoaDonToiThieu.setText("");
        txtID.setText("");
        txtSoTienGiamToiDa.setText("");
        txtTen.setText("");
        cboNgayBatDau.setDate(null);
        cboNgayKT.setDate(null);
        txtSoLan.setText("");
        txtPhanTramGiam.setText("");
        txtSearchAll.setText("");
    }


    private void btnRefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshActionPerformed
        // TODO add your handling code here:
        refeshText();
        cboTrangThai.setSelectedIndex(0);
        dsTK.clear();
        ds = sr.getAll();
        fillTable(ds);
    }//GEN-LAST:event_btnRefeshActionPerformed


    private void btnCallAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCallAddActionPerformed
        // TODO add your handling code here:
        jPanel2.setVisible(false);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) this.getLayout();
        layout.replace(jPanel2, jpanelHien);
        jpanelHien.setBounds(jPanel2.getBounds());
    }//GEN-LAST:event_btnCallAddActionPerformed

    public void timTrangThai() {
        int tt = 0;
        if (cboTrangThai.getSelectedItem().equals("Đã kết thúc")) {
            tt = 0;
            dsTK = sr.timTrangThai(tt);
            fillTable(dsTK);
        } else if (cboTrangThai.getSelectedItem().equals("Đang diễn ra")) {
            tt = 1;
            dsTK = sr.timTrangThai(tt);
            fillTable(dsTK);

        } else if (cboTrangThai.getSelectedItem().equals("Sắp diễn ra")) {
            tt = 2;
            dsTK = sr.timTrangThai(tt);
            fillTable(dsTK);
        } else if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
            dsTK.clear();
            ds = sr.getAll();
            fillTable(ds);
        }
    }

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
        timTrangThai();
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void cboNgayKTPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboNgayKTPropertyChange
        // TODO add your handling code here:
        int index = tblBang.getSelectedRow();
        if (index == -1) {
            if (cboNgayBatDau != null && cboNgayKT != null && cboNgayBatDau.getDate() != null && cboNgayKT.getDate() != null) {
                Date getNgayBD = cboNgayBatDau.getDate();
                LocalDateTime ngayBD = getNgayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDate ngayBDLocalDate = ngayBD.toLocalDate();
                Date getNgayKT = cboNgayKT.getDate();
                LocalDateTime ngayKT = getNgayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDate ngayKTLocalDate = ngayKT.toLocalDate();
                dsTK = sr.timKiemNgay(ngayBDLocalDate, ngayKTLocalDate);
                fillTable(dsTK);
            }
        }
    }//GEN-LAST:event_cboNgayKTPropertyChange

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        // TODO add your handling code here:
        if (dsTK.isEmpty()) {
            try {
                int idnex = tblBang.getSelectedRow();
                PhieuGiamGia gg = ds.get(idnex);
                txtID.setText(gg.getID());
                String phanTram = decimalFormat.format(gg.getPhanTramGiam());
                txtPhanTramGiam.setText(phanTram + "%");
                String soLuongDung = decimalFormat.format(gg.getSoLuongDung());
                txtSoLan.setText(soLuongDung);
                txtTen.setText(gg.getTenGiamGia());
                String hoaDonMin = decimalFormat.format(gg.getHoatDonToiThieu());
                txtHoaDonToiThieu.setText(hoaDonMin);
                String soTienGiamMax = decimalFormat.format(gg.getSoTiemGiamToiDa());
                txtSoTienGiamToiDa.setText(soTienGiamMax);
                Date ngayBD = Date.from(gg.getNgayBatDau().atZone(ZoneId.systemDefault()).toInstant());
                cboNgayBatDau.setDate(ngayBD);
                Date ngayKT = Date.from(gg.getNgayKetThuc().atZone(ZoneId.systemDefault()).toInstant());
                cboNgayKT.setDate(ngayKT);
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        } else {
            try {
                int idnex = tblBang.getSelectedRow();
                PhieuGiamGia gg = dsTK.get(idnex);
                txtID.setText(gg.getID());
                String phanTram = decimalFormat.format(gg.getPhanTramGiam());
                txtPhanTramGiam.setText(phanTram + "%");
                String soLuongDung = decimalFormat.format(gg.getSoLuongDung());
                txtSoLan.setText(soLuongDung);
                txtTen.setText(gg.getTenGiamGia());
                String hoaDonMin = decimalFormat.format(gg.getHoatDonToiThieu());
                txtHoaDonToiThieu.setText(hoaDonMin);
                String soTienGiamMax = decimalFormat.format(gg.getSoTiemGiamToiDa());
                txtSoTienGiamToiDa.setText(soTienGiamMax);
                Date ngayBD = Date.from(gg.getNgayBatDau().atZone(ZoneId.systemDefault()).toInstant());
                cboNgayBatDau.setDate(ngayBD);
                Date ngayKT = Date.from(gg.getNgayKetThuc().atZone(ZoneId.systemDefault()).toInstant());
                cboNgayKT.setDate(ngayKT);
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        }
    }//GEN-LAST:event_tblBangMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int index = tblBang.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lại dữ liệu trên bảng");
            return;
        }
        String soLanFormat = txtSoLan.getText().replace(",", "");

        if (txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khuyến mãi");
            return;
        }

        if (txtSoLan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lần được phép sử dụng");
            return;
        }
        if (!soLanFormat.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lần được phép sử dụng");
            return;
        }

        String hoaDonFormat = txtHoaDonToiThieu.getText().replace(",", "");
        String soTienFormat = txtSoTienGiamToiDa.getText().replace(",", "");

        if (txtHoaDonToiThieu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập hóa đơn tối thiếu");
            return;
        }
        if (!hoaDonFormat.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại hóa đơn tối thiếu");
            return;
        }
        String soPhanTramFormat = txtPhanTramGiam.getText().trim().replace("%", "");
        if (txtPhanTramGiam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập phần trăm giảm");
            return;
        }
        if (!soPhanTramFormat.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại phần trăm giảm");
            return;
        }
        if (txtSoTienGiamToiDa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền giảm tối đa");
            return;
        }
        if (!soTienFormat.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số tiền giảm tối đa");
            return;
        }

        Date getNgayBD = cboNgayBatDau.getDate();
        Date getNgayKT = cboNgayKT.getDate();

        if (getNgayBD == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu");
            return;
        }

        if (getNgayKT == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày kết thúc");
            return;
        }

        LocalDateTime ngayBD = getNgayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate ngayBDLocalDate = ngayBD.toLocalDate();
        LocalDateTime ngayKT = getNgayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate ngayKTLocalDate = ngayKT.toLocalDate();
        LocalDateTime ngayThucTe = LocalDateTime.now();
        LocalDate ngayTTLC = ngayThucTe.toLocalDate();

        if (ngayBDLocalDate.isBefore(ngayTTLC) && ngayKTLocalDate.isBefore(ngayTTLC)) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lại ngày bắt đầu");
            return;
        } else if (ngayKTLocalDate.isBefore(ngayBDLocalDate)) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lại ngày kết thúc");
            return;
        }

        int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn Update dữ liệu không?", "Notification", JOptionPane.YES_NO_OPTION);
        if (lc == JOptionPane.YES_OPTION) {
            try {
                if (dsTK.isEmpty()) {
                    PhieuGiamGia gg = ds.get(index);
                    sr.update(getFormData(), gg.getID());
                    ds = sr.getAll();
                    fillTable(ds);
                } else {
                    PhieuGiamGia gg = dsTK.get(index);
                    sr.update(getFormData(), gg.getID());
                    int tt = 0;
                    if (cboTrangThai.getSelectedItem().equals("Đã kết thúc")) {
                        tt = 0;
                        dsTK = sr.timTrangThai(tt);
                        fillTable(dsTK);
                    } else if (cboTrangThai.getSelectedItem().equals("Đang diễn ra")) {
                        tt = 1;
                        dsTK = sr.timTrangThai(tt);
                        fillTable(dsTK);

                    } else if (cboTrangThai.getSelectedItem().equals("Sắp diễn ra")) {
                        tt = 2;
                        dsTK = sr.timTrangThai(tt);
                        fillTable(dsTK);
                    } else if (cboTrangThai.getSelectedItem().equals("Tất cả")) {
                        ds = sr.getAll();
                        fillTable(ds);
                    }
                    fillTable(dsTK);
                }
                refeshText();
                JOptionPane.showMessageDialog(this, "Đã Update dữ liệu");

            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn NO");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtSoLanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLanKeyReleased
        // TODO add your handling code here:
        if (!txtSoLan.getText().trim().isBlank()) {
            Document document = txtSoLan.getDocument();
            try {
                String text = document.getText(0, document.getLength());
                text = text.replaceAll("[^\\d]", "");
                long number = Long.parseLong(text);

                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                String formattedNumber = decimalFormat.format(number);
                txtSoLan.setText(formattedNumber);

            } catch (BadLocationException | NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        int index = tblBang.getSelectedRow();
        if (index == -1) {
            if (!txtSoLan.getText().isEmpty()) {
                try {
                    int soLan = Integer.valueOf(Integer.parseInt(txtSoLan.getText().trim().replace(",", "")));
                    dsTK = sr.timKiemSoLan(soLan);
                    fillTable(dsTK);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }

            }
        }
    }//GEN-LAST:event_txtSoLanKeyReleased

    private void txtSoTienGiamToiDaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoTienGiamToiDaKeyReleased
        // TODO add your handling code here:
        if (!txtSoTienGiamToiDa.getText().trim().isBlank()) {
            Document document = txtSoTienGiamToiDa.getDocument();
            try {
                String text = document.getText(0, document.getLength());
                text = text.replaceAll("[^\\d]", "");
                long number = Long.parseLong(text);

                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                String formattedNumber = decimalFormat.format(number);
                txtSoTienGiamToiDa.setText(formattedNumber);

            } catch (BadLocationException | NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        int index = tblBang.getSelectedRow();
        if (index == -1) {
            if (!txtSoTienGiamToiDa.getText().trim().isEmpty()) {
                try {
                    String soTienFormat = txtSoTienGiamToiDa.getText().replace(",", "");
                    Float st = Float.valueOf(Float.parseFloat(soTienFormat));
                    dsTK = sr.timKiemTienGiam(st);
                    fillTable(dsTK);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }
        }
    }//GEN-LAST:event_txtSoTienGiamToiDaKeyReleased

    private void txtHoaDonToiThieuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoaDonToiThieuKeyReleased
        // TODO add your handling code here:
        if (!txtHoaDonToiThieu.getText().trim().isEmpty()) {
            String text = txtHoaDonToiThieu.getText();
            text = text.replaceAll("[^\\d]", "");

            if (!text.isEmpty()) {
                try {
                    long number = Long.parseLong(text);

                    // Định dạng số và hiển thị trong JTextField
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    txtHoaDonToiThieu.setText(decimalFormat.format(number));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                    return;
                }
            }

            int index = tblBang.getSelectedRow();
            if (index == -1) {
                if (!txtHoaDonToiThieu.getText().trim().isEmpty()) {
                    try {
                        String hoaDonFormat = txtHoaDonToiThieu.getText().trim().replace(",", "");
                        Float hd = Float.valueOf(Float.parseFloat(hoaDonFormat));
                        dsTK = sr.timKiemHoaDonTT(hd);
                        fillTable(dsTK);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
                        return;
                    }
                }
            }
        }
    }//GEN-LAST:event_txtHoaDonToiThieuKeyReleased

    private void txtTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyReleased
        // TODO add your handling code here:
        int index = tblBang.getSelectedRow();
        if (index == -1) {
            if (!txtTen.getText().trim().isEmpty()) {
                String ten = txtTen.getText().trim();
                dsTK = sr.timKiemTenGG(ten);
                fillTable(dsTK);
            }
        }
    }//GEN-LAST:event_txtTenKeyReleased

    private void txtIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyReleased
        // TODO add your handling code here:
        if (!txtID.getText().trim().isEmpty()) {
            dsTK = sr.timKiemID(txtID.getText().trim());
            fillTable(dsTK);
        }
    }//GEN-LAST:event_txtIDKeyReleased

    private void txtSearchAllKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAllKeyReleased
        // TODO add your handling code here:
        if (!txtSearchAll.getText().isBlank()) {
            String all = txtSearchAll.getText().trim();
            dsTK = sr.timKiemAll(all);
            fillTable(dsTK);
        }
    }//GEN-LAST:event_txtSearchAllKeyReleased

    private void txtPhanTramGiamKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhanTramGiamKeyReleased
        // TODO add your handling code here:
        if (!txtPhanTramGiam.getText().trim().isEmpty()) {
            String text = txtPhanTramGiam.getText().trim().replaceAll("[^\\d]", "");
            if (!text.isEmpty()) {
                long number = Long.parseLong(text);
                DecimalFormat decimalFormat = new DecimalFormat("###'%'");
                String formattedNumber = decimalFormat.format(number);
                txtPhanTramGiam.setText(formattedNumber);
            }
        }

        try {
            int index = tblBang.getSelectedRow();
            if (index == -1) {
                if (!txtPhanTramGiam.getText().trim().isEmpty()) {
                    float phanTram = Float.parseFloat(txtPhanTramGiam.getText().trim().replace("%", ""));
                    dsTK = sr.timKiemPhanTram(phanTram);
                    fillTable(dsTK);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtPhanTramGiamKeyReleased

    private void txtSoTienGiamToiDaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoTienGiamToiDaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoTienGiamToiDaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom btnCallAdd;
    private mobileworld.swing.ButtonCustom btnRefesh;
    private mobileworld.swing.ButtonCustom btnUpdate;
    private com.toedter.calendar.JDateChooser cboNgayBatDau;
    private com.toedter.calendar.JDateChooser cboNgayKT;
    private mobileworld.swing.Combobox cboTrangThai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private mobileworld.swing.Table tblBang;
    private mobileworld.swing.TextField txtHoaDonToiThieu;
    private mobileworld.swing.TextField txtID;
    private mobileworld.swing.TextField txtPhanTramGiam;
    private mobileworld.swing.TextField txtSearchAll;
    private mobileworld.swing.TextField txtSoLan;
    private mobileworld.swing.TextField txtSoTienGiamToiDa;
    private mobileworld.swing.TextField txtTen;
    // End of variables declaration//GEN-END:variables

}
