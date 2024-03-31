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
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import mobileworld.form.ThongTinChiTietSP;
import mobileworld.service.ChiTietSanPhamService.ChiTietSPService;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class ReadQRCode extends javax.swing.JFrame {

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private ThongTinChiTietSP thongTinSP;
    private final ChiTietSPService ctspService = new ChiTietSPService();

    public ReadQRCode() {
        initComponents();
        initialize();
        setTitle("Quét QR");
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

    private volatile boolean productDisplayed = false; // Biến để theo dõi trạng thái hiển thị sản phẩm

    private void startQRCodeScanner() {
        Thread thread;
        thread = new Thread(() -> {
            while (true) {
                if (webcam.isOpen() && !productDisplayed) {
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
                                }
                                // Đặt cờ hiển thị sản phẩm thành true và đóng JFrame
                                productDisplayed = true;
                                closeWindowAndWebcam();
                                return;
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

    public void closeWindowAndWebcam() {
        webcam.close();
        setVisible(false);
        dispose();
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
        txtImel = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanelQR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtImel.setBackground(new java.awt.Color(12, 45, 87));
        txtImel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtImel.setForeground(new java.awt.Color(255, 255, 255));
        txtImel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImel.setText("QUÉT QR");
        txtImel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(242, 242, 242)));
        txtImel.setDisabledTextColor(new java.awt.Color(12, 45, 87));
        txtImel.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtImel, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelQR, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelQR;
    private javax.swing.JTextField txtImel;
    // End of variables declaration//GEN-END:variables
}
