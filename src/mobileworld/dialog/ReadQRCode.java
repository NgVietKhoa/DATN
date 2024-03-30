package mobileworld.dialog;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.buildin.WebcamDefaultDriver;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import mobileworld.form.ThongTinChiTietSP;
import mobileworld.model.Imel;
import mobileworld.service.ChiTietSanPhamService.ChiTietSPService;
import mobileworld.service.ChiTietSanPhamService.ImelService;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class ReadQRCode extends javax.swing.JFrame {

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private ThongTinChiTietSP thongTinSP;
    private final ChiTietSPService ctspService = new ChiTietSPService();

    public ReadQRCode() {
        initComponents();
        initialize();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (webcam != null) {
                    webcam.close();
                }
            }
        });
    }

    private void initialize() {
        Webcam.setDriver(new WebcamDefaultDriver());

        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        webcamPanel = new WebcamPanel(webcam, true);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setFPSDisplayed(true);

        jPanelQR.add(webcamPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 533, 297));

        startQRCodeScanner();
    }

    private void startQRCodeScanner() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (webcam.isOpen()) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        Result result = decodeQRCode(image);
                        if (result != null) {
                            // Lấy dữ liệu từ mã QR
                            String qrCodeData = result.getText();
                            List<ChiTietSanPhamViewModel> products = ctspService.getAllQR(qrCodeData);
                            if (products != null && !products.isEmpty()) {
                                // Display all products retrieved based on IMEI
                                for (ChiTietSanPhamViewModel product : products) {
                                    displayProductDetails(product);
                                    return;
                                }
                            } else {
                                // Handle case where no product found
                                System.out.println("No product found for the QR code data: " + qrCodeData);
                            }
                        }
                    }
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    private void displayProductDetails(ChiTietSanPhamViewModel product) {
        SwingUtilities.invokeLater(() -> {
            if (product != null) {
                String idChiTietSP = product.getId();
                String pin = product.getDungLuongPin();
                String ram = product.getDungLuongRam();
                String manHinh = product.getLoaiManHinh();
                String nsx = product.getTenNsx();
                String tenDsp = product.getTenDsp();
                String mauSac = product.getTenMau();
                String cpu = product.getCpu();
                String rom = product.getDungLuongBoNho();
                String cameraTruoc = product.getCameraTruoc();
                String cameraSau = product.getCameraSau();
                BigDecimal gia = product.getGiaBan();
                String imel = product.getImel();
                String moTa = product.getGhiChu();

                ThongTinChiTietSP chiTietSP = new ThongTinChiTietSP(idChiTietSP, cameraSau, cameraTruoc, cpu, imel, manHinh, mauSac, nsx, pin, ram, rom, tenDsp, gia, moTa);
                chiTietSP.setVisible(true);
            } else {
                // Handle case where product is null
                System.out.println("Product details not available.");
            }
        });
    }

    public Result decodeQRCode(BufferedImage image) {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(image)));
        try {
            return new MultiFormatReader().decode(binaryBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelQR = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtImel = new javax.swing.JTextField();
        buttonCustom1 = new mobileworld.swing.ButtonCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelQR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("IMEL");

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setText("Reset");
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImel, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelQR, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtImel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        txtImel.setText("");
    }//GEN-LAST:event_buttonCustom1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mobileworld.swing.ButtonCustom buttonCustom1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelQR;
    private javax.swing.JTextField txtImel;
    // End of variables declaration//GEN-END:variables
}
