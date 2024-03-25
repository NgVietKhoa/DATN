/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.HoaDonRepo;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.buildin.WebcamDefaultDriver;
import com.google.zxing.BarcodeFormat;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;

import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.List;
import mobileworld.viewModel.HoaDonModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
//import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Paragraph;
//import static com.microsoft.sqlserver.jdbc.StringUtils.isNumeric;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.imageio.ImageIO;
//import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;
import com.google.zxing.WriterException;

import mobileworld.service.InvoiceGenerator;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import javax.swing.table.TableColumn;
import mobileworld.entity.HoaDon;
//import jdk.jfr.Timestamp;
//import mobileworld.viewModel.ChiTietSanPhamViewModel;
import mobileworld.viewModel.HoaDonChiTietModel;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
//import org.apache.poi.common.usermodel.HyperlinkType;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.apache.poi.ss.usermodel.Hyperlink;
//import org.apache.poi.ss.usermodel.RichTextString;
//import qrcode.qrcode;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mobileworld.config.DBConnect;
import org.apache.poi.ss.usermodel.Table;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author ADMIN
 */
public class HoaDonRepository {

    public List<HoaDonModel> getAllHD() {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTien, 
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     INNER JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     INNER JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                     ORDER BY HoaDon.NgayThanhToan ASC
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel HDM = new HoaDonModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSDTKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                HDM.setTenKieuThanhToan(rs.getString(7));
                HDM.setTongTien(rs.getBigDecimal(8));
                HDM.setTrangThai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public List<HoaDonModel> getAll() {
//        List<HoaDonModel> list = new ArrayList<>();
//        String sql = """
//                   SELECT 
//                       dbo.HoaDon.ID, 
//                       dbo.KHACHHANG.Ten, 
//                       dbo.KHACHHANG.SDT, 
//                       dbo.KHACHHANG.DiaChi, 
//                       dbo.HoaDon.IDNhanVien, 
//                       dbo.HoaDon.NgayTao, 
//                       dbo.HoaDon.NgayThanhToan, 
//                       dbo.HinhThucThanhToan.IDPhuongThucThanhToan, 
//                       dbo.PhuongThucThanhToan.TenKieuThanhToan, 
//                       dbo.HoaDon.TongTien, 
//                       dbo.HoaDon.TrangThai
//                   FROM   
//                       dbo.HoaDon 
//                   INNER JOIN
//                       dbo.NHANVIEN ON dbo.HoaDon.IDNhanVien = dbo.NHANVIEN.ID 
//                   INNER JOIN
//                       dbo.KHACHHANG ON dbo.HoaDon.IDKhachHang = dbo.KHACHHANG.ID 
//                   INNER JOIN
//                       dbo.HinhThucThanhToan ON dbo.HoaDon.ID = dbo.HinhThucThanhToan.IDHoaDon 
//                   INNER JOIN
//                       dbo.PhuongThucThanhToan ON dbo.HinhThucThanhToan.IDPhuongThucThanhToan = dbo.PhuongThucThanhToan.ID
//                   ORDER BY  
//                       HoaDon.NgayTao, 
//                       HoaDon.NgayThanhToan ASC;
//                     """;
//
//        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                HoaDonModel hoaDon = new HoaDonModel();
//                hoaDon.setIdHoaDon(rs.getString(1));
//                hoaDon.setTenKH(rs.getString(2));
//                hoaDon.setSdt(rs.getString(3));
//                hoaDon.setDiaChi(rs.getString(4));
//                hoaDon.setIdNhV(rs.getString(5));
//                hoaDon.setNgayTao(rs.getTimestamp(6).toLocalDateTime());
//                hoaDon.setNgayThanhToan(rs.getTimestamp(7).toLocalDateTime());
//                hoaDon.setIdPhuongThucThanhToan(rs.getString(8));
//                hoaDon.setKieuThanhToan(rs.getString(9));
//                hoaDon.setTongTien(rs.getBigDecimal(10));
//                hoaDon.setTrangThai(rs.getInt(11));
//                list.add(hoaDon);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    public List<HoaDonModel> getAllQR(String result) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTien, 
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     INNER JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     INNER JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                     WHERE HoaDon.ID = ?
                     ORDER BY HoaDon.NgayThanhToan ASC
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, result);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel HDM = new HoaDonModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSDTKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                HDM.setTenKieuThanhToan(rs.getString(7));
                HDM.setTongTien(rs.getBigDecimal(8));
                HDM.setTrangThai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> filterHoaDon(String ht, int trangThai) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                   SELECT 
                                                HoaDon.ID, 
                                                HoaDon.IDNhanVien, 
                                                HoaDon.TenKhachHang, 
                                                HoaDon.SoDienThoaiKhachHang, 
                                                HoaDon.DiaChiKhachHang, 
                                                HoaDon.NgayThanhToan, 
                                                PhuongThucThanhToan.TenKieuThanhToan, 
                                                HoaDon.TongTien, 
                                                HoaDon.TrangThai
                                            FROM   
                                                dbo.HoaDon 
                                            INNER JOIN
                                                dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                            INNER JOIN
                                                dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                                                WHERE dbo.PhuongThucThanhToan.TenKieuThanhToan = ? OR ? IS NULL
                                                      AND dbo.HoaDon.TrangThai = ? OR ? IS NULL
                                             ORDER BY HoaDon.NgayThanhToan ASC

                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, ht);
            ps.setObject(2, ht);
            ps.setObject(3, trangThai);
            ps.setObject(4, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel HDM = new HoaDonModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSDTKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                HDM.setTenKieuThanhToan(rs.getString(7));
                HDM.setTongTien(rs.getBigDecimal(8));
                HDM.setTrangThai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> hinhThucHoaDon(String hthd) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                   SELECT 
                                                HoaDon.ID, 
                                                HoaDon.IDNhanVien, 
                                                HoaDon.TenKhachHang, 
                                                HoaDon.SoDienThoaiKhachHang, 
                                                HoaDon.DiaChiKhachHang, 
                                                HoaDon.NgayThanhToan, 
                                                PhuongThucThanhToan.TenKieuThanhToan, 
                                                HoaDon.TongTien, 
                                                HoaDon.TrangThai
                                            FROM   
                                                dbo.HoaDon 
                                            INNER JOIN
                                                dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                            INNER JOIN
                                                dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                                             WHERE dbo.PhuongThucThanhToan.TenKieuThanhToan = ?
                                                   OR dbo.HoaDon.TrangThai = ?
                                             ORDER BY HoaDon.NgayThanhToan ASC

                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, hthd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel HDM = new HoaDonModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSDTKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                HDM.setTenKieuThanhToan(rs.getString(7));
                HDM.setTongTien(rs.getBigDecimal(8));
                HDM.setTrangThai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> trangThaiHoaDon(int trangThai) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                   SELECT 
                                            HoaDon.ID, 
                                            HoaDon.IDNhanVien, 
                                            HoaDon.TenKhachHang, 
                                            HoaDon.SoDienThoaiKhachHang, 
                                            HoaDon.DiaChiKhachHang, 
                                            HoaDon.NgayThanhToan, 
                                            PhuongThucThanhToan.TenKieuThanhToan, 
                                            HoaDon.TongTien, 
                                            HoaDon.TrangThai
                                        FROM   
                                            dbo.HoaDon 
                                        INNER JOIN
                                            dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                        INNER JOIN
                                            dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                                        WHERE dbo.HoaDon.TrangThai = ?
                                        ORDER BY HoaDon.NgayThanhToan ASC
                 
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel HDM = new HoaDonModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSDTKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                HDM.setTenKieuThanhToan(rs.getString(7));
                HDM.setTongTien(rs.getBigDecimal(8));
                HDM.setTrangThai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> searchGia(BigDecimal giaTu, BigDecimal giaDen) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTien, 
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     INNER JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     INNER JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                      WHERE
                           HoaDon.TongTien BETWEEN ? AND ?
                     ORDER BY HoaDon.NgayThanhToan ASC
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setBigDecimal(1, giaTu);
            ps.setBigDecimal(2, giaDen);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonModel HDM = new HoaDonModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSDTKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                HDM.setTenKieuThanhToan(rs.getString(7));
                HDM.setTongTien(rs.getBigDecimal(8));
                HDM.setTrangThai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> search(String ten) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                      SELECT 
                                              HoaDon.ID, 
                                              HoaDon.IDNhanVien, 
                                              HoaDon.TenKhachHang, 
                                              HoaDon.SoDienThoaiKhachHang, 
                                              HoaDon.DiaChiKhachHang, 
                                              HoaDon.NgayThanhToan, 
                                              PhuongThucThanhToan.TenKieuThanhToan, 
                                              HoaDon.TongTien, 
                                              HoaDon.TrangThai
                                          FROM   
                                              dbo.HoaDon 
                                          INNER JOIN
                                              dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                          INNER JOIN
                                              dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                 WHERE 
                         HoaDon.ID LIKE ? ESCAPE '!'
                      OR HoaDon.TenKhachHang LIKE ? ESCAPE '!'
                      OR HoaDon.SoDienThoaiKhachHang LIKE ? ESCAPE '!'
                      OR HoaDon.DiaChiKhachHang LIKE ? ESCAPE '!'
                      OR HoaDon.IDNhanVien LIKE ? ESCAPE '!'
                      OR HoaDon.IDNhanVien LIKE ? ESCAPE '!'
                      OR CONVERT(VARCHAR, HoaDon.NgayThanhToan, 111) LIKE ? ESCAPE '!'
                      OR PhuongThucThanhToan.TenKieuThanhToan LIKE ? ESCAPE '!'
                      OR HoaDon.TongTien LIKE ? ESCAPE '!'
                      OR HoaDon.TrangThai LIKE ? ESCAPE '!'
                     ORDER BY HoaDon.NgayThanhToan ASC
                      
                 """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            for (int i = 0; i < ten.length(); i++) {
                ps.setString(1, "%" + ten + "%");
                ps.setString(2, "%" + ten + "%");
                ps.setString(3, "%" + ten + "%");
                ps.setString(4, "%" + ten + "%");
                ps.setString(5, "%" + ten + "%");
                ps.setString(6, "%" + ten + "%");
                ps.setString(7, "%" + ten + "%");
                ps.setString(8, "%" + ten + "%");
                ps.setString(9, "%" + ten + "%");
                ps.setString(10, "%" + ten + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        HoaDonModel HDM = new HoaDonModel();
                        HDM.setIdHD(rs.getString(1));
                        HDM.setIdNV(rs.getString(2));
                        HDM.setTenKH(rs.getString(3));
                        HDM.setSDTKH(rs.getString(4));
                        HDM.setDiaChiKH(rs.getString(5));
                        HDM.setNgayThanhToan(rs.getTimestamp(6).toLocalDateTime());
                        HDM.setTenKieuThanhToan(rs.getString(7));
                        HDM.setTongTien(rs.getBigDecimal(8));
                        HDM.setTrangThai(rs.getInt(9));
                        list.add(HDM);
                    }
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean xuatHoaDon() {
        try (Connection connection = DBConnect.getConnection()) {
            String query = """
                    SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTien, 
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     INNER JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     INNER JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                            ORDER BY HoaDon.NgayThanhToan ASC
                    """;

            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách hóa đơn");

                // Tạo phông in đậm cho header
                Font fontHeader = workbook.createFont();
                fontHeader.setBold(true);
                CellStyle styleHeader = workbook.createCellStyle();
                styleHeader.setFont(fontHeader);

                // Tạo phông in đậm cho dữ liệu
                Font fontData = workbook.createFont();
                CellStyle styleData = workbook.createCellStyle();
                styleData.setFont(fontData);

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                Row headerRow = sheet.createRow(0);
                // Tạo cell style cho border
                CellStyle styleBorder = workbook.createCellStyle();
                styleBorder.setBorderTop(BorderStyle.THIN);
                styleBorder.setBorderBottom(BorderStyle.THIN);
                styleBorder.setBorderLeft(BorderStyle.THIN);
                styleBorder.setBorderRight(BorderStyle.THIN);
                // Tạo header cho danh sách hóa đơn
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(columnName);
                    cell.setCellStyle(styleHeader);
                }

                int rowIndex = 1;
                while (resultSet.next()) {
                    Row row = sheet.createRow(rowIndex++);

                    // Tạo hyperlink cho ID
                    CreationHelper createHelper = workbook.getCreationHelper();
                    Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
                    hyperlink.setAddress("'Chi tiết hóa đơn - " + resultSet.getString("ID") + "'!A1");
                    Cell idCell = row.createCell(0);
                    idCell.setCellValue(resultSet.getString("ID"));
                    idCell.setHyperlink(hyperlink);
                    idCell.setCellStyle(styleData); // Sử dụng phông in đậm cho dữ liệu

                    // Điền dữ liệu vào các cột còn lại
                    for (int i = 2; i <= columnCount; i++) {
                        Cell dataCell = row.createCell(i - 1);
                        dataCell.setCellValue(resultSet.getString(i));
                        dataCell.setCellStyle(styleData); // Sử dụng phông in đậm cho dữ liệu
                    }

                    // Tiếp tục xử lý các chi tiết hóa đơn...
                    row.createCell(1).setCellValue(resultSet.getString("IDNhanVien"));
                    row.createCell(2).setCellValue(resultSet.getString("TenKhachHang"));
                    row.createCell(3).setCellValue(resultSet.getString("SoDienThoaiKhachHang"));
                    row.createCell(4).setCellValue(resultSet.getString("DiaChiKhachHang"));
                    row.createCell(5).setCellValue(resultSet.getTimestamp("NgayThanhToan").toLocalDateTime());
                    row.createCell(6).setCellValue(resultSet.getString("TenKieuThanhToan"));
                    row.createCell(7).setCellValue(resultSet.getString("TongTien"));
                    row.createCell(8).setCellValue(resultSet.getString("TrangThai"));

                    // Tạo header cho danh sách hóa đơn với border
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Cell cell = headerRow.createCell(i - 1);
                        cell.setCellValue(columnName);
                        cell.setCellStyle(styleHeader);
                        cell.setCellStyle(styleBorder); // Thêm border
                    }
                    // Điền dữ liệu vào các cột còn lại với border
                    for (int i = 2; i <= columnCount; i++) {
                        Cell dataCell = row.createCell(i - 1);
                        dataCell.setCellValue(resultSet.getString(i));
                        dataCell.setCellStyle(styleData); // Sử dụng phông in đậm cho dữ liệu
                        dataCell.setCellStyle(styleBorder); // Thêm border
                    }

                    // Lấy ID hóa đơn để lấy thông tin chi tiết hóa đơn
                    String idHoaDon = resultSet.getString("ID");
                    HoaDonCTRepository repo = new HoaDonCTRepository();
                    List<HoaDonChiTietModel> hoaDonChiTietList = repo.getAll(idHoaDon);

                    // Tạo sheet mới cho chi tiết hóa đơn
                    Sheet chiTietSheet = workbook.createSheet("Chi tiết hóa đơn - " + idHoaDon);
                    Row headerChiTietRow = chiTietSheet.createRow(0);
                    String[] chiTietHeaders = {"ID hóa đơn", "Tên sản phẩm", "Tên NSX", "Tên Màu", "Dung Luọng Pin", "Imel", "Giá bán", "Tổng tiền"};
                    for (int i = 0; i < chiTietHeaders.length; i++) {
                        Cell chiTietCell = headerChiTietRow.createCell(i);
                        chiTietCell.setCellValue(chiTietHeaders[i]);
                        chiTietCell.setCellStyle(styleData);

                    }

                    // Đổ dữ liệu chi tiết hóa đơn vào sheet mới
                    int chiTietRowIndex = 1;
                    for (HoaDonChiTietModel hoaDonChiTiet : hoaDonChiTietList) {
                        Row chiTietRow = chiTietSheet.createRow(chiTietRowIndex++);
                        chiTietRow.createCell(0).setCellValue(hoaDonChiTiet.getIdHD());
                        chiTietRow.createCell(1).setCellValue(hoaDonChiTiet.getTenDSP());
                        chiTietRow.createCell(2).setCellValue(hoaDonChiTiet.getTenNSX());
                        chiTietRow.createCell(3).setCellValue(hoaDonChiTiet.getTenMau());
                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getDungLuongPin());
                        chiTietRow.createCell(5).setCellValue(hoaDonChiTiet.getImel());
                        chiTietRow.createCell(6).setCellValue(hoaDonChiTiet.getGiaBan().doubleValue());
                        chiTietRow.createCell(7).setCellValue(hoaDonChiTiet.getTongTien().doubleValue());
                    }

                }

                // Tạo JComboBox để chọn kiểu file
                String[] fileTypes = {".xlsx", ".xls"}; // Các kiểu file Excel bạn muốn cho phép
                JComboBox<String> fileTypeComboBox = new JComboBox<>(fileTypes);
                JPanel fileTypePanel = new JPanel();
                fileTypePanel.add(new JLabel("Chọn kiểu file:"));
                fileTypePanel.add(fileTypeComboBox);

                int option = JOptionPane.showOptionDialog(null, fileTypePanel, "Chọn kiểu file",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                if (option == JOptionPane.OK_OPTION) {
                    // Lấy kiểu file được chọn
//                    String selectedFileType = (String) fileTypeComboBox.getSelectedItem();

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();

                        // Lấy kiểu file được chọn
                        String selectedFileType = (String) fileTypeComboBox.getSelectedItem();

                        // Kiểm tra và điều chỉnh tên file nếu cần thiết
                        String filePath = fileToSave.getAbsolutePath();
                        if (!filePath.toLowerCase().endsWith(selectedFileType)) {
                            filePath += selectedFileType;
                        }
                        File file = new File(filePath);

                        // Kiểm tra xem tên file đã tồn tại chưa
                        while (file.exists()) {
                            JOptionPane.showMessageDialog(null, "Tên file đã tồn tại. Vui lòng chọn tên file khác.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            userSelection = fileChooser.showSaveDialog(null);
                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                fileToSave = fileChooser.getSelectedFile();
                                filePath = fileToSave.getAbsolutePath();
                                if (!filePath.toLowerCase().endsWith(selectedFileType)) {
                                    filePath += selectedFileType;
                                }
                                file = new File(filePath);
                            } else {
                                System.out.println("Không có nơi lưu được chọn.");
                                return false;
                            }
                        }

                        // Tiến hành lưu file
                        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                            workbook.write(fileOut);
                        }
                        System.out.println("Đã xuất file Excel: " + filePath);
                        return true;
                    } else {
                        System.out.println("Không có nơi lưu được chọn.");
                        return false;
                    }

//                    // Yêu cầu người dùng chọn nơi lưu trữ và nhập tên file
//                    JFileChooser fileChooser = new JFileChooser();
//                    fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
//                    int userSelection = fileChooser.showSaveDialog(null);
//                    if (userSelection == JFileChooser.APPROVE_OPTION) {
//                        File fileToSave = fileChooser.getSelectedFile();
//
//                        // Đảm bảo có đuôi của kiểu file được chọn
//                        String filePath = fileToSave.getAbsolutePath();
//                        if (!filePath.toLowerCase().endsWith(selectedFileType)) {
//                            filePath += selectedFileType;
//                        }
//
//                        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//                            workbook.write(fileOut);
//                        }
//                        System.out.println("Đã xuất file Excel: " + filePath);
//                        return true;
//                    } else {
//                        System.out.println("Không có nơi lưu được chọn.");
//                        return false;
//                    }
                }
            }

//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
//                int userSelection = fileChooser.showSaveDialog(null);
//                if (userSelection == JFileChooser.APPROVE_OPTION) {
//                    File fileToSave = fileChooser.getSelectedFile();
//
//                    // Đảm bảo có đuôi ".xlsx"
//                    String filePath = fileToSave.getAbsolutePath();
//                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
//                        filePath += ".xlsx";
//                    }
//
//                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//                        workbook.write(fileOut);
//                    }
//                    System.out.println("Đã xuất file Excel: " + filePath);
//                    return true;
//                } else {
//                    System.out.println("Không có nơi lưu được chọn.");
//                    return false;
//                }
//            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Phần code khác, như DBConnect, HoaDonCTRepository và HoaDonChiTietModel, bạn cần
    //    public boolean xuatHoaDon() {
    //        try (Connection connection = DBConnect.getConnection()) {
    //            String query = """
    //                    SELECT 
    //                         HoaDon.ID, 
    //                         HoaDon.IDNhanVien, 
    //                         HoaDon.TenKhachHang, 
    //                         HoaDon.SoDienThoaiKhachHang, 
    //                         HoaDon.DiaChiKhachHang, 
    //                         HoaDon.NgayThanhToan, 
    //                         PhuongThucThanhToan.TenKieuThanhToan, 
    //                         HoaDon.TongTien, 
    //                         HoaDon.TrangThai
    //                     FROM   
    //                         dbo.HoaDon 
    //                     INNER JOIN
    //                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
    //                     INNER JOIN
    //                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
    //                    """;
    //
    //            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
    //                
    //                Workbook workbook = new XSSFWorkbook();
    //                Sheet sheet = workbook.createSheet("Danh sách hóa đơn");
    //                
    //                // Tạo phông in đậm cho header
    //                Font fontHeader = workbook.createFont();
    //                fontHeader.setBold(true);
    //                CellStyle styleHeader = workbook.createCellStyle();
    //                styleHeader.setFont(fontHeader);
    //
    //                // Tạo phông in đậm cho dữ liệu
    //                Font fontData = workbook.createFont();
    //                CellStyle styleData = workbook.createCellStyle();
    //                styleData.setFont(fontData);
    //
    //                ResultSetMetaData metaData = resultSet.getMetaData();
    //                int columnCount = metaData.getColumnCount();
    //                Row headerRow = sheet.createRow(0);
    //
    //                // Tạo header cho danh sách hóa đơn
    //                for (int i = 1; i <= columnCount; i++) {
    //                    String columnName = metaData.getColumnName(i);
    //                    Cell cell = headerRow.createCell(i - 1);
    //                    cell.setCellValue(columnName);
    //                    cell.setCellStyle(styleHeader);
    //                }
    //                int rowIndex = 1;
    //                while (resultSet.next()) {
    //                    Row row = sheet.createRow(rowIndex++);
    //
    //                    // Tạo hyperlink cho ID
    //                    CreationHelper createHelper = workbook.getCreationHelper();
    //                    Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
    //                    hyperlink.setAddress("'Chi tiết hóa đơn - " + resultSet.getString("ID") + "'!A1");
    //                    Cell idCell = row.createCell(0);
    //                    idCell.setCellValue(resultSet.getString("ID"));
    //                    idCell.setHyperlink(hyperlink);
    //                    idCell.setCellStyle(styleData); // Sử dụng phông in đậm cho dữ liệu
    //
    //                    // Điền dữ liệu vào các cột còn lại
    //                    for (int i = 2; i <= columnCount; i++) {
    //                        Cell dataCell = row.createCell(i - 1);
    //                        dataCell.setCellValue(resultSet.getString(i));
    //                        dataCell.setCellStyle(styleData); // Sử dụng phông in đậm cho dữ liệu
    //                    }
    //
    //                    // Tiếp tục xử lý các chi tiết hóa đơn...
    //                    row.createCell(1).setCellValue(resultSet.getString("IDNhanVien"));
    //                    row.createCell(2).setCellValue(resultSet.getString("TenKhachHang"));
    //                    row.createCell(3).setCellValue(resultSet.getString("SoDienThoaiKhachHang"));
    //                    row.createCell(4).setCellValue(resultSet.getString("DiaChiKhachHang"));
    //                    row.createCell(5).setCellValue(resultSet.getTimestamp("NgayThanhToan").toLocalDateTime());
    //                    row.createCell(6).setCellValue(resultSet.getString("TenKieuThanhToan"));
    //                    row.createCell(7).setCellValue(resultSet.getString("TongTien"));
    //
    //                    // Lấy ID hóa đơn để lấy thông tin chi tiết hóa đơn
    //                    String idHoaDon = resultSet.getString("ID");
    //                    HoaDonCTRepository repo = new HoaDonCTRepository();
    //                    List<HoaDonChiTietModel> hoaDonChiTietList = repo.getAll(idHoaDon);
    //
    //                    // Tạo sheet mới cho chi tiết hóa đơn
    //                    Sheet chiTietSheet = workbook.createSheet("Chi tiết hóa đơn - " + idHoaDon);
    //                    Row headerChiTietRow = chiTietSheet.createRow(0);
    //                    String[] chiTietHeaders = {"ID hóa đơn", "Tên sản phẩm", "Số lượng", "Giá bán", "Giảm giá", "Tổng tiền"};
    //                    for (int i = 0; i < chiTietHeaders.length; i++) {
    //                        Cell chiTietCell = headerChiTietRow.createCell(i);
    //                        chiTietCell.setCellValue(chiTietHeaders[i]);
    //                        chiTietCell.setCellStyle(styleData);
    //                    }
    //
    //                    // Đổ dữ liệu chi tiết hóa đơn vào sheet mới
    //                    int chiTietRowIndex = 1;
    //                    for (HoaDonChiTietModel hoaDonChiTiet : hoaDonChiTietList) {
    //                        Row chiTietRow = chiTietSheet.createRow(chiTietRowIndex++);
    //                        chiTietRow.createCell(0).setCellValue(hoaDonChiTiet.getIdHD());
    //                        chiTietRow.createCell(1).setCellValue(hoaDonChiTiet.getTenDSP());
    //                        chiTietRow.createCell(2).setCellValue(hoaDonChiTiet.getSoLuong());
    //                        chiTietRow.createCell(3).setCellValue(hoaDonChiTiet.getGiaBan().doubleValue());
    ////                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getGiamGia());
    //                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getTongTien().doubleValue());
    //                    }
    //                }
    //                
    //                
    //                String fileName = "DSP_" + System.currentTimeMillis() + ".xlsx";
    //                try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
    //                    workbook.write(fileOut);
    //                }
    //                System.out.println("Đã xuất file Excel: " + fileName);
    //                return true;
    //            }
    //            
    //        } catch (SQLException | IOException e) {
    //            e.printStackTrace();
    //        }
    //        return false;
    //    }
    //    public boolean xuatHoaDon() {
    //        try (Connection connection = DBConnect.getConnection()) {
    //            String query = """
    //                        SELECT 
    //                             HoaDon.ID, 
    //                             HoaDon.IDNhanVien, 
    //                             HoaDon.TenKhachHang, 
    //                             HoaDon.SoDienThoaiKhachHang, 
    //                             HoaDon.DiaChiKhachHang, 
    //                             HoaDon.NgayThanhToan, 
    //                             PhuongThucThanhToan.TenKieuThanhToan, 
    //                             HoaDon.TongTien, 
    //                             HoaDon.TrangThai
    //                         FROM   
    //                             dbo.HoaDon 
    //                         INNER JOIN
    //                             dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
    //                         INNER JOIN
    //                             dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
    //                        """;
    //
    //            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
    //                Workbook workbook = new XSSFWorkbook();
    //                Sheet sheet = workbook.createSheet("Danh sách hóa đơn");
    //
    //                // Tạo phông in đậm
    //                Font font = workbook.createFont();
    //                font.setBold(true);
    //                CellStyle style = workbook.createCellStyle();
    //                style.setFont(font);
    //
    //                ResultSetMetaData metaData = resultSet.getMetaData();
    //                int columnCount = metaData.getColumnCount();
    //                Row headerRow = sheet.createRow(0);
    //
    //                for (int i = 1; i <= columnCount; i++) {
    //                    String columnName = metaData.getColumnName(i);
    //                    Cell cell = headerRow.createCell(i - 1);
    //                    cell.setCellValue(columnName);
    //                    cell.setCellStyle(style);
    //                }
    //
    //                int rowIndex = 1;
    //                while (resultSet.next()) {
    //                    Row row = sheet.createRow(rowIndex++);
    //
    //                    // Tạo hyperlink cho ID
    //                    CreationHelper createHelper = workbook.getCreationHelper();
    //                    Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
    //                    hyperlink.setAddress("'Chi tiết hóa đơn - " + resultSet.getString("ID") + "'!A1");
    //                    Cell idCell = row.createCell(0);
    //                    idCell.setCellValue(resultSet.getString("ID"));
    //                    idCell.setHyperlink(hyperlink);
    //
    //                    row.createCell(1).setCellValue(resultSet.getString("IDNhanVien"));
    //                    row.createCell(2).setCellValue(resultSet.getString("TenKhachHang"));
    //                    row.createCell(3).setCellValue(resultSet.getString("SoDienThoaiKhachHang"));
    //                    row.createCell(4).setCellValue(resultSet.getString("DiaChiKhachHang"));
    //                    row.createCell(5).setCellValue(resultSet.getTimestamp("NgayThanhToan").toLocalDateTime());
    //                    row.createCell(6).setCellValue(resultSet.getString("TenKieuThanhToan"));
    //                    row.createCell(7).setCellValue(resultSet.getString("TongTien"));
    //
    //                    // Lấy ID hóa đơn để lấy thông tin chi tiết hóa đơn
    //                    String idHoaDon = resultSet.getString("ID");
    //                    HoaDonCTRepository repo = new HoaDonCTRepository();
    //                    List<HoaDonChiTietModel> hoaDonChiTietList = repo.getAll(idHoaDon);
    //
    //                    // Tạo sheet mới cho chi tiết hóa đơn
    //                    Sheet chiTietSheet = workbook.createSheet("Chi tiết hóa đơn - " + idHoaDon);
    //                    Row headerChiTietRow = chiTietSheet.createRow(0);
    //                    String[] chiTietHeaders = {"ID hóa đơn", "Tên sản phẩm", "Số lượng", "Giá bán", "Giảm giá", "Tổng tiền"};
    //                    for (int i = 0; i < chiTietHeaders.length; i++) {
    //                        Cell chiTietCell = headerChiTietRow.createCell(i);
    //                        chiTietCell.setCellValue(chiTietHeaders[i]);
    //                        chiTietCell.setCellStyle(style);
    //                    }
    //
    //                    // Đổ dữ liệu chi tiết hóa đơn vào sheet mới
    //                    int chiTietRowIndex = 1;
    //                    for (HoaDonChiTietModel hoaDonChiTiet : hoaDonChiTietList) {
    //                        Row chiTietRow = chiTietSheet.createRow(chiTietRowIndex++);
    //                        chiTietRow.createCell(0).setCellValue(hoaDonChiTiet.getIdHD());
    //                        chiTietRow.createCell(1).setCellValue(hoaDonChiTiet.getTenDSP());
    //                        chiTietRow.createCell(2).setCellValue(hoaDonChiTiet.getSoLuong());
    //                        chiTietRow.createCell(3).setCellValue(hoaDonChiTiet.getGiaBan().doubleValue());
    ////                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getGiamGia());
    //                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getTongTien().doubleValue());
    //                    }
    //                }
    //
    //                String fileName = "DSP_" + System.currentTimeMillis() + ".xlsx";
    //                try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
    //                    workbook.write(fileOut);
    //                }
    //                System.out.println("Đã xuất file Excel: " + fileName);
    //                return true;
    //            }
    //        } catch (SQLException | IOException e) {
    //            e.printStackTrace();
    //        }
    //        return false;
    //    }
    //    public boolean xuatHoaDon() {
    //        try (Connection connection = DBConnect.getConnection()) {
    //            String query = """
    //                        SELECT 
    //                             HoaDon.ID, 
    //                             HoaDon.IDNhanVien, 
    //                             HoaDon.TenKhachHang, 
    //                             HoaDon.SoDienThoaiKhachHang, 
    //                             HoaDon.DiaChiKhachHang, 
    //                             HoaDon.NgayThanhToan, 
    //                             PhuongThucThanhToan.TenKieuThanhToan, 
    //                             HoaDon.TongTien, 
    //                             HoaDon.TrangThai
    //                         FROM   
    //                             dbo.HoaDon 
    //                         INNER JOIN
    //                             dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
    //                         INNER JOIN
    //                             dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
    //                        """;
    //
    //            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
    //                Workbook workbook = new XSSFWorkbook();
    //                Sheet sheet = workbook.createSheet("Danh sách hóa đơn");
    //
    //                // Tạo phông in đậm
    //                Font font = workbook.createFont();
    //                font.setBold(true);
    //                CellStyle style = workbook.createCellStyle();
    //                style.setFont(font);
    //
    //                ResultSetMetaData metaData = resultSet.getMetaData();
    //                int columnCount = metaData.getColumnCount();
    //                Row headerRow = sheet.createRow(0);
    //
    //                for (int i = 1; i <= columnCount; i++) {
    //                    String columnName = metaData.getColumnName(i);
    //                    Cell cell = headerRow.createCell(i - 1);
    //                    cell.setCellValue(columnName);
    //                    cell.setCellStyle(style);
    //                }
    //
    //                int rowIndex = 1;
    //                while (resultSet.next()) {
    //                    Row row = sheet.createRow(rowIndex++);
    //                    row.createCell(0).setCellValue(resultSet.getString("ID"));
    //                    row.createCell(1).setCellValue(resultSet.getString("IDNhanVien"));
    //                    row.createCell(2).setCellValue(resultSet.getString("TenKhachHang"));
    //                    row.createCell(3).setCellValue(resultSet.getString("SoDienThoaiKhachHang"));
    //                    row.createCell(4).setCellValue(resultSet.getString("DiaChiKhachHang"));
    //                    row.createCell(5).setCellValue(resultSet.getTimestamp("NgayThanhToan").toLocalDateTime());
    //                    row.createCell(6).setCellValue(resultSet.getString("TenKieuThanhToan"));
    //                    row.createCell(7).setCellValue(resultSet.getString("TongTien"));
    //
    //                    // Lấy ID hóa đơn để lấy thông tin chi tiết hóa đơn
    //                    String idHoaDon = resultSet.getString("ID");
    //                    HoaDonCTRepository repo = new HoaDonCTRepository();
    //                    List<HoaDonChiTietModel> hoaDonChiTietList = repo.getAll(idHoaDon);
    //
    //                    // Tạo sheet mới cho chi tiết hóa đơn
    //                    Sheet chiTietSheet = workbook.createSheet("Chi tiết hóa đơn - " + idHoaDon);
    //                    Row headerChiTietRow = chiTietSheet.createRow(0);
    //                    String[] chiTietHeaders = {"ID hóa đơn", "Tên sản phẩm", "Số lượng", "Giá bán", "Giảm giá", "Tổng tiền"};
    //                    for (int i = 0; i < chiTietHeaders.length; i++) {
    //                        Cell chiTietCell = headerChiTietRow.createCell(i);
    //                        chiTietCell.setCellValue(chiTietHeaders[i]);
    //                        chiTietCell.setCellStyle(style);
    //                    }
    //
    //                    // Đổ dữ liệu chi tiết hóa đơn vào sheet mới
    //                    int chiTietRowIndex = 1;
    //                    for (HoaDonChiTietModel hoaDonChiTiet : hoaDonChiTietList) {
    //                        Row chiTietRow = chiTietSheet.createRow(chiTietRowIndex++);
    //                        chiTietRow.createCell(0).setCellValue(hoaDonChiTiet.getIdHD());
    //                        chiTietRow.createCell(1).setCellValue(hoaDonChiTiet.getTenDSP());
    //                        chiTietRow.createCell(2).setCellValue(hoaDonChiTiet.getSoLuong());
    //                        chiTietRow.createCell(3).setCellValue(hoaDonChiTiet.getGiaBan().doubleValue());
    ////                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getGiamGia());
    //                        chiTietRow.createCell(4).setCellValue(hoaDonChiTiet.getTongTien().doubleValue());
    //                    }
    //                }
    //
    //                String fileName = "DSP_" + System.currentTimeMillis() + ".xlsx";
    //                try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
    //                    workbook.write(fileOut);
    //                }
    //                System.out.println("Đã xuất file Excel: " + fileName);
    //                return true;
    //            }
    //        } catch (SQLException | IOException e) {
    //            e.printStackTrace();
    //        }
    //        return false;
    //    }

    public boolean inHD(String invoiceId) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Sử dụng class DBConnect để kết nối đến cơ sở dữ liệu SQL Server
            connection = DBConnect.getConnection();
            String sql = "SELECT "
                    + "HoaDon.ID, "
                    + "HoaDon.IDNhanVien, "
                    + "HoaDon.TenKhachHang, "
                    + "HoaDon.SoDienThoaiKhachHang, "
                    + "HoaDon.DiaChiKhachHang, "
                    + "HoaDon.NgayThanhToan, "
                    + "PhuongThucThanhToan.TenKieuThanhToan, "
                    + "HoaDon.TongTien "
                    + "FROM "
                    + "dbo.HoaDon "
                    + "INNER JOIN dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon "
                    + "INNER JOIN dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID "
                    + "WHERE HoaDon.ID = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, invoiceId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String invoiceID = resultSet.getString("ID");
                String employeeId = resultSet.getString("IDNhanVien");
                String customerName = resultSet.getString("TenKhachHang");
                String phoneNumber = resultSet.getString("SoDienThoaiKhachHang");
                String address = resultSet.getString("DiaChiKhachHang");
                Date paymentDate = resultSet.getDate("NgayThanhToan");
                String paymentMethodName = resultSet.getString("TenKieuThanhToan");
                double totalAmount = resultSet.getDouble("TongTien");

                // Tạo mã QR code
                String qrCodeContent = invoiceID;

                String qrCodeImagePath = "C:\\Users\\ADMIN\\Documents\\mobileWorld3\\QR/" + invoiceID + ".png";
                generateQRCodeImage(qrCodeContent, qrCodeImagePath);
                System.err.println("In Mã QR thành công" + qrCodeImagePath);
                // Tạo hóa đơn PDF
                String pdfFilePath = "C:\\Users\\ADMIN\\Documents\\mobileWorld3\\PDF/" + invoiceID + ".pdf";
                generateInvoicePDF(invoiceID, customerName, phoneNumber, address,
                        employeeId, paymentDate,
                        paymentMethodName, totalAmount,
                        qrCodeImagePath, pdfFilePath);
                System.err.println("In Hóa đơn thành công" + pdfFilePath);

                return true; // Trả về true nếu in thành công
            }

        } catch (SQLException | IOException | WriterException | DocumentException e) {
            e.printStackTrace();
            Logger.getLogger(InvoiceGenerator.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            // Đóng các kết nối và tài nguyên
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    private static void generateQRCodeImage(String text, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        // Tạo BufferedImage để viết mã QR
        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                int pixelColor = bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF;
                bufferedImage.setRGB(i, j, pixelColor);
            }
        }

        // Lưu hình ảnh mã QR bằng ImageIO
        ImageIO.write(bufferedImage, "png", new File(filePath));
    }

    private static void generateInvoicePDF(String invoiceId, String customerName, String phoneNumber,
            String address, String employeeId, Date paymentDate,
            String paymentMethodName,
            double totalAmount, String qrCodeImagePath, String pdfFilePath)
            throws DocumentException, IOException {
        Document document = new Document();

        // Chỉ định đường dẫn chính xác để lưu các tập tin PDF
        PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));

        document.open();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00"); // Định dạng số tiền

        document.add(new Paragraph("Invoice ID: " + invoiceId));
        document.add(new Paragraph("Customer: " + customerName));
        document.add(new Paragraph("Phone Number: " + phoneNumber));
        document.add(new Paragraph("Address: " + address));
        document.add(new Paragraph("Employee ID: " + employeeId));
        document.add(new Paragraph("Payment Date: " + dateFormat.format(paymentDate)));
        document.add(new Paragraph("Payment Method: " + paymentMethodName));
        document.add(new Paragraph("Total Amount: " + decimalFormat.format(totalAmount))); // Định dạng số tiền

        // Thêm hình ảnh mã QR vào PDF sử dụng đường dẫn chính xác
        com.itextpdf.text.Image qrCodeImage = com.itextpdf.text.Image.getInstance(qrCodeImagePath);
        document.add(qrCodeImage);
        document.close();
    }

    public static void main(String[] args) {
        List<HoaDonModel> list1 = new HoaDonRepository().getAllQR("HÐ00002");
        for (HoaDonModel hoaDonModel : list1) {
            System.out.println(hoaDonModel.toString());
        }
    }

}
