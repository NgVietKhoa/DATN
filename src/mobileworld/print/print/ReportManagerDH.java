package mobileworld.print.print;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import mobileworld.print.print.model.ParameterReportPayment;


public class ReportManagerDH {

    private static ReportManagerDH instance;

    private JasperReport reportPay;

    public static ReportManagerDH getInstance() {
        if (instance == null) {
            instance = new ReportManagerDH();
        }
        return instance;
    }

    private ReportManagerDH() {
    }

    public void compileReport() throws JRException {
//        reportPay = JasperCompileManager.compileReport(getClass().getResourceAsStream("C:\\Users\\ADMIN\\Documents\\mobileWorld3 - Sao chép (2)\\src\\mobileworld\\inHoaDon\\HoaDon.jrxml"));
        reportPay = JasperCompileManager.compileReport(getClass().getResourceAsStream("/mobileworld/print/print/PrintHoaDonDH.jrxml"));

    }

    public void printReportPayment(ParameterReportPayment data) {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("maHoaDon", data.getMaHoaDon());
            para.put("tenKhachHang", data.getTenKhachHang());
            para.put("soDienThoai", data.getSoDienThoai());
            para.put("diaChi", data.getDiaChi());
            para.put("thanhToan", data.getThanhToan());
            para.put("maNV", data.getMaNV());
            para.put("thoiGian", data.getThoiGian());
            // Convert tongTien to a numeric type if necessary
            Object tongTienObject = data.getTongTien();
            double tongTien;
            if (tongTienObject instanceof Number) {
                tongTien = ((Number) tongTienObject).doubleValue();
            } else {
                try {
                    tongTien = Double.parseDouble(tongTienObject.toString());
                } catch (NumberFormatException e) {
                    // Handle the case where tongTien is not a valid number
                    tongTien = 0.0; // Or handle differently based on your requirements
                }
            }

            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String formattedTongTien = decimalFormat.format(tongTien);
            para.put("tongTien", formattedTongTien + " VND");
            para.put("ngayMuonNhan", data.getNgayMuonNhan());
            

            InputStream QR = data.getQR();
            if (QR != null) {
                BufferedImage qrCodeImage = ImageIO.read(QR);
                para.put("QR", qrCodeImage);
            } else {
                para.put("QR", null); // Provide null if QR code InputStream is null
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
            JasperPrint print = JasperFillManager.fillReport(reportPay, para, dataSource);

            view(print);
        } catch (JRException | IOException e) {
            e.printStackTrace(); // Handle or log the exception according to your application's requirements
        }
    }

    private void view(JasperPrint print) throws JRException {
        JasperViewer.viewReport(print, false);
    }

    public void checkJRXMLPath() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/mobileworld/print/print/PrintHoaDonDH.jrxml");
            if (inputStream != null) {
                System.out.println("JRXML path is valid.");
            } else {
                System.out.println("JRXML path is invalid. Check the path.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCompilation() {
        try {
            compileReport();
            if (reportPay != null) {
                System.out.println("JRXML compilation successful.");
            } else {
                System.out.println("JRXML compilation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkReportParameters(ParameterReportPayment data) {
        try {
            System.out.println("Mã hóa đơn: " + data.getMaHoaDon());
            System.out.println("Khách hàng: " + data.getTenKhachHang());
            System.out.println("Số điện thoại: " + data.getSoDienThoai());
            System.out.println("Địa chỉ: " + data.getDiaChi());
            System.out.println("Thanh toán: " + data.getThanhToan());
            System.out.println("NVBH:" + data.getMaNV());
            System.out.println("Tong Tien: " + data.getTongTien());
            System.out.println("QR Code: " + data.getQR());
            System.out.println("Ngày Nhân: " + data.getNgayMuonNhan());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
