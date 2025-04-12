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
import javax.swing.JFrame;

public class ReadQRCodeSelectSP extends javax.swing.JFrame {

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private final SelectProductSP selectProductSP;
    

    public ReadQRCodeSelectSP(SelectProductSP selectProductSP) {
        this.selectProductSP = selectProductSP;
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

    private void startQRCodeScanner() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (webcam.isOpen()) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        Result result = decodeQRCode(image);
                        if (result != null) {
                            String qrCodeData = result.getText();
                            selectProductSP.updateIMEI(qrCodeData);
                            
                            closeWindowAndWebcam();
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
