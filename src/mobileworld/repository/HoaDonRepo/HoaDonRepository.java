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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;

import mobileworld.service.InvoiceGenerator;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import javax.swing.table.TableColumn;
import mobileworld.model.HoaDon;
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
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author ADMIN
 */
public class HoaDonRepository {

    public List<HoaDonViewModel> getAllHD() {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                                              HoaDon.ID, 
                                              HoaDon.IDNhanVien, 
                                              HoaDon.TenKhachHang, 
                                              HoaDon.SoDienThoaiKhachHang, 
                                              HoaDon.DiaChiKhachHang, 
                                              HoaDon.NgayThanhToan, 
                                              PhuongThucThanhToan.TenKieuThanhToan, 
                                              HoaDon.TongTienSauGiam, 
                                              HoaDon.TrangThai
                                          FROM   
                                              dbo.HoaDon 
                                          LEFT JOIN
                                              dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                          LEFT JOIN
                                              dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                                          WHERE HoaDon.Deleted = 1
                                          ORDER BY ID DESC
                     """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel HDM = new HoaDonViewModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSdtKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getDate(6));
                HDM.setGetTenKieuThanhToan(rs.getString(7));
                HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                HDM.setTrangthai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonViewModel> searchHDByMaxPrice(BigDecimal maxPrice) {
        List<HoaDonViewModel> resultList = new ArrayList<>();
        String sql = """
                 SELECT 
                                          HoaDon.ID, 
                                          HoaDon.IDNhanVien, 
                                          HoaDon.TenKhachHang, 
                                          HoaDon.SoDienThoaiKhachHang, 
                                          HoaDon.DiaChiKhachHang, 
                                          HoaDon.NgayThanhToan, 
                                          PhuongThucThanhToan.TenKieuThanhToan, 
                                          HoaDon.TongTienSauGiam, 
                                          HoaDon.TrangThai
                                      FROM   
                                          dbo.HoaDon 
                                      INNER JOIN
                                          dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                      INNER JOIN
                                          dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                                      WHERE HoaDon.Deleted = 1 AND HoaDon.TongTien <= ?
                                      ORDER BY ID DESC
                 """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setBigDecimal(1, maxPrice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel HDM = new HoaDonViewModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSdtKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getDate(6));
                HDM.setGetTenKieuThanhToan(rs.getString(7));
                HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                HDM.setTrangthai(rs.getInt(9));
                resultList.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<HoaDonViewModel> getTrangThaiHD(int trangThai) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTienSauGiam, 
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     LEFT JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     LEFT JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                     WHERE 
                         HoaDon.Deleted = 1 AND HoaDon.TrangThai = ?
                     ORDER BY 
                         HoaDon.ID DESC
                     """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel HDM = new HoaDonViewModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSdtKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getDate(6));
                HDM.setGetTenKieuThanhToan(rs.getString(7));
                HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                HDM.setTrangthai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonViewModel> getAllQR(String result) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTienSauGiam, 
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

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, result);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel HDM = new HoaDonViewModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSdtKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getDate(6));
                HDM.setGetTenKieuThanhToan(rs.getString(7));
                HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                HDM.setTrangthai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonViewModel> filterHoaDon(String ht, int trangThai) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                   SELECT 
                                                HoaDon.ID, 
                                                HoaDon.IDNhanVien, 
                                                HoaDon.TenKhachHang, 
                                                HoaDon.SoDienThoaiKhachHang, 
                                                HoaDon.DiaChiKhachHang, 
                                                HoaDon.NgayThanhToan, 
                                                PhuongThucThanhToan.TenKieuThanhToan, 
                                                HoaDon.TongTienSauGiam, 
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

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, ht);
            ps.setObject(2, ht);
            ps.setObject(3, trangThai);
            ps.setObject(4, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel HDM = new HoaDonViewModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSdtKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getDate(6));
                HDM.setGetTenKieuThanhToan(rs.getString(7));
                HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                HDM.setTrangthai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonViewModel> hinhThucHoaDon(String hthd) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                   SELECT 
                                                HoaDon.ID, 
                                                HoaDon.IDNhanVien, 
                                                HoaDon.TenKhachHang, 
                                                HoaDon.SoDienThoaiKhachHang, 
                                                HoaDon.DiaChiKhachHang, 
                                                HoaDon.NgayThanhToan, 
                                                PhuongThucThanhToan.TenKieuThanhToan, 
                                                HoaDon.TongTienSauGiam, 
                                                HoaDon.TrangThai
                                            FROM   
                                                dbo.HoaDon 
                                            INNER JOIN
                                                dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                                            INNER JOIN
                                                dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                                             WHERE dbo.PhuongThucThanhToan.TenKieuThanhToan = ?
                                             ORDER BY HoaDon.NgayThanhToan ASC

                     """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, hthd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel HDM = new HoaDonViewModel();
                HDM.setIdHD(rs.getString(1));
                HDM.setIdNV(rs.getString(2));
                HDM.setTenKH(rs.getString(3));
                HDM.setSdtKH(rs.getString(4));
                HDM.setDiaChiKH(rs.getString(5));
                HDM.setNgayThanhToan(rs.getDate(6));
                HDM.setGetTenKieuThanhToan(rs.getString(7));
                HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                HDM.setTrangthai(rs.getInt(9));
                list.add(HDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonViewModel> search(String txt) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                      SELECT 
                                              HoaDon.ID, 
                                              HoaDon.IDNhanVien, 
                                              HoaDon.TenKhachHang, 
                                              HoaDon.SoDienThoaiKhachHang, 
                                              HoaDon.DiaChiKhachHang, 
                                              HoaDon.NgayThanhToan, 
                                              PhuongThucThanhToan.TenKieuThanhToan, 
                                              HoaDon.TongTienSauGiam, 
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
                      OR CONVERT(VARCHAR, HoaDon.NgayThanhToan, 111) LIKE ? ESCAPE '!'
                      OR PhuongThucThanhToan.TenKieuThanhToan LIKE ? ESCAPE '!'
                      OR HoaDon.TongTien LIKE ? ESCAPE '!'
                      OR HoaDon.TrangThai LIKE ? ESCAPE '!'
                    
                     ORDER BY HoaDon.NgayThanhToan ASC
                 """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            for (int i = 0; i < txt.length(); i++) {
                ps.setString(1, "%" + txt + "%");
                ps.setString(2, "%" + txt + "%");
                ps.setString(3, "%" + txt + "%");
                ps.setString(4, "%" + txt + "%");
                ps.setString(5, "%" + txt + "%");
                ps.setString(6, "%" + txt + "%");
                ps.setString(7, "%" + txt + "%");
                ps.setString(8, "%" + txt + "%");
                ps.setString(9, "%" + txt + "%");
                try ( ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        HoaDonViewModel HDM = new HoaDonViewModel();
                        HDM.setIdHD(rs.getString(1));
                        HDM.setIdNV(rs.getString(2));
                        HDM.setTenKH(rs.getString(3));
                        HDM.setSdtKH(rs.getString(4));
                        HDM.setDiaChiKH(rs.getString(5));
                        HDM.setNgayThanhToan(rs.getDate(6));
                        HDM.setGetTenKieuThanhToan(rs.getString(7));
                        HDM.setTongTienSauGiam(rs.getBigDecimal(8));
                        HDM.setTrangthai(rs.getInt(9));
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
        try ( Connection connection = DBConnect.getConnection()) {
            String query = """
                    SELECT 
                         HoaDon.ID, 
                         HoaDon.IDNhanVien, 
                         HoaDon.TenKhachHang, 
                         HoaDon.SoDienThoaiKhachHang, 
                         HoaDon.DiaChiKhachHang, 
                         HoaDon.NgayThanhToan, 
                         PhuongThucThanhToan.TenKieuThanhToan, 
                         HoaDon.TongTienSauGiam, 
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     INNER JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     INNER JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                            ORDER BY HoaDon.NgayThanhToan ASC
                    """;

            try ( PreparedStatement statement = connection.prepareStatement(query);  ResultSet resultSet = statement.executeQuery()) {

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
                    row.createCell(7).setCellValue(resultSet.getString("TongTienSauGiam"));
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
                        try ( FileOutputStream fileOut = new FileOutputStream(filePath)) {
                            workbook.write(fileOut);
                        }
                        System.out.println("Đã xuất file Excel: " + filePath);
                        return true;
                    } else {
                        System.out.println("Không có nơi lưu được chọn.");
                        return false;
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inHD(String invoiceId) {
        try ( Connection connection = DBConnect.getConnection()) {
            String sql = """
                SELECT 
                    HoaDon.ID, 
                    HoaDon.IDNhanVien, 
                    HoaDon.TenKhachHang, 
                    HoaDon.SoDienThoaiKhachHang, 
                    HoaDon.DiaChiKhachHang, 
                    HoaDon.NgayThanhToan, 
                    PhuongThucThanhToan.TenKieuThanhToan, 
                    HoaDon.TongTienSauGiam, 
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

            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, invoiceId);

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String invoiceID = resultSet.getString("ID");
                        String employeeId = resultSet.getString("IDNhanVien");
                        String customerName = resultSet.getString("TenKhachHang");
                        String phoneNumber = resultSet.getString("SoDienThoaiKhachHang");
                        String address = resultSet.getString("DiaChiKhachHang");
                        Date paymentDate = resultSet.getDate("NgayThanhToan");
                        String paymentMethodName = resultSet.getString("TenKieuThanhToan");
                        double totalAmount = resultSet.getDouble("TongTienSauGiam");

                        // Tạo mã QR code
                        String qrCodeContent = invoiceID;
                        String qrCodeImagePath = "C:\\Users\\ADMIN\\Documents\\FINAL_DATN\\DATN\\QR/" + invoiceID + ".png";
                        try {
                            generateQRCodeImage(qrCodeContent, qrCodeImagePath);
                        } catch (WriterException ex) {
                            Logger.getLogger(HoaDonRepository.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.err.println("In Mã QR thành công" + qrCodeImagePath);

                        // Tạo hóa đơn PDF
                        String pdfFilePath = "C:\\Users\\ADMIN\\Documents\\FINAL_DATN\\DATN\\PDF/" + invoiceID + ".pdf";
                        generateInvoicePDF(invoiceID, customerName, phoneNumber, address,
                                employeeId, paymentDate,
                                paymentMethodName, totalAmount,
                                qrCodeImagePath, pdfFilePath);
                        System.err.println("In Hóa đơn thành công" + pdfFilePath);

                        return true;
                    }
                }
            }
        } catch (SQLException | IOException | DocumentException e) {
            e.printStackTrace();
            Logger.getLogger(InvoiceGenerator.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    private static void generateQRCodeImage(String qrCodeContent, String filePqrCodeImagePathath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, 200, 200);

        // Tạo BufferedImage để viết mã QR
        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                int pixelColor = bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF;
                bufferedImage.setRGB(i, j, pixelColor);
            }
        }

        // Lưu hình ảnh mã QR bằng ImageIO
        ImageIO.write(bufferedImage, "png", new File(filePqrCodeImagePathath));
    }

    private static void generateInvoicePDF(String invoiceId, String customerName, String phoneNumber,
            String address, String employeeId, Date paymentDate,
            String paymentMethodName,
            double totalAmount, String qrCodeImagePath, String pdfFilePath)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        document.open();

        // Thiết lập font cho tiêu đề
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
        com.itextpdf.text.Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        com.itextpdf.text.Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        // Thêm logo của công ty (nếu có)
        com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("C:\\Users\\ADMIN\\Documents\\FINAL_DATN\\DATN\\src\\mobileworld\\icon\\Logomb.png");
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        // Thêm tiêu đề
        Paragraph title = new Paragraph("Invoice", (com.itextpdf.text.Font) titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Thêm thông tin hóa đơn
        document.add(Chunk.NEWLINE); // Khoảng trống giữa tiêu đề và thông tin hóa đơn
        document.add(new Paragraph("Invoice ID: " + invoiceId, (com.itextpdf.text.Font) contentFont));
        document.add(new Paragraph("Customer: " + customerName, (com.itextpdf.text.Font) contentFont));
        document.add(new Paragraph("Phone Number: " + phoneNumber, (com.itextpdf.text.Font) contentFont));
        document.add(new Paragraph("Address: " + address, (com.itextpdf.text.Font) contentFont));
        document.add(new Paragraph("Employee ID: " + employeeId, (com.itextpdf.text.Font) contentFont));
        document.add(new Paragraph("Payment Date: " + new SimpleDateFormat("dd/MM/yyyy").format(paymentDate), (com.itextpdf.text.Font) contentFont));
        document.add(new Paragraph("Payment Method: " + paymentMethodName, (com.itextpdf.text.Font) contentFont));

        document.add(new Paragraph("Total Amount: " + DecimalFormat.getCurrencyInstance().format(totalAmount), (com.itextpdf.text.Font) subtitleFont));

        // Thêm hình ảnh mã QR vào PDF
        com.itextpdf.text.Image qrCodeImage = com.itextpdf.text.Image.getInstance(qrCodeImagePath);
        qrCodeImage.setAlignment(Element.ALIGN_CENTER);
        document.add(qrCodeImage);

        document.close();
    }
}
