package mobileworld.repository.NhanVienRepo;

import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.NhanVien;
import mobileworld.viewModel.NhanVienViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVienRepository {

    public List<NhanVienViewModel> getAll() {
        List<NhanVienViewModel> listnhanvien = new ArrayList<>();
        String sql = """
                     SELECT dbo.NhanVien.ID, dbo.NhanVien.TenNV, dbo.NhanVien.NgaySinh, dbo.NhanVien.DiaChi, dbo.NhanVien.SDT, dbo.NhanVien.Email, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu
                                                            FROM     dbo.ChucVu INNER JOIN
                                                                              dbo.NhanVien ON dbo.ChucVu.ID = dbo.NhanVien.IDChucVu WHERE NhanVien.Deleted = 1
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienViewModel nv = new NhanVienViewModel();
                nv.setId(rs.getString(1));
                nv.setTenNhanVien(rs.getString(2));
                nv.setNgaySinh(rs.getDate(3).toLocalDate());
                nv.setDiaChi(rs.getString(4));
                nv.setSdt(rs.getString(5));
                nv.setEmail(rs.getString(6));
                nv.setCccd(rs.getString(7));
                nv.setChucVu(rs.getString(8));
                listnhanvien.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listnhanvien;
    }

    public List<NhanVienViewModel> getAllNvDaNghi() {
        List<NhanVienViewModel> listnhanvien = new ArrayList<>();
        String sql = """
                     SELECT dbo.NhanVien.ID, dbo.NhanVien.TenNV, dbo.NhanVien.NgaySinh, dbo.NhanVien.DiaChi, dbo.NhanVien.SDT, dbo.NhanVien.Email, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu
                                                            FROM     dbo.ChucVu INNER JOIN
                                                                              dbo.NhanVien ON dbo.ChucVu.ID = dbo.NhanVien.IDChucVu WHERE NhanVien.Deleted = 0
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienViewModel nv = new NhanVienViewModel();
                nv.setId(rs.getString(1));
                nv.setTenNhanVien(rs.getString(2));
                nv.setNgaySinh(rs.getDate(3).toLocalDate());
                nv.setDiaChi(rs.getString(4));
                nv.setSdt(rs.getString(5));
                nv.setEmail(rs.getString(6));
                nv.setCccd(rs.getString(7));
                nv.setChucVu(rs.getString(8));
                listnhanvien.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listnhanvien;
    }

    public boolean checkLogin(String maNhanVien, char[] password) {
        String sql = "SELECT COUNT(*) FROM dbo.NHANVIEN WHERE ID = ? AND Password = ?";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ps.setString(2, String.valueOf(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getChucVuFromDatabase(String maNhanVien) {
        String tenChucVu = null;
        String sql = "SELECT dbo.ChucVu.TenChucVu FROM dbo.NHANVIEN INNER JOIN dbo.ChucVu ON dbo.NHANVIEN.IDChucVu = dbo.ChucVu.ID WHERE dbo.NHANVIEN.ID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenChucVu = rs.getString("TenChucVu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenChucVu;
    }

    public boolean delete(String id) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[NhanVien]
                                    SET 
                                       Deleted = 0
                                  WHERE ID = ?
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean update(NhanVien nv, String oldID) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[NhanVien]
                        SET [TenNV] = ?
                           ,[NgaySinh] = ?
                           ,[DiaChi] = ?
                           ,[SDT] = ?
                           ,[Email] = ?
                           ,[IDChucVu] = (SELECT TOP 1 ID FROM ChucVu WHERE TenChucVu = ?)
                           ,[CCCD] = ?
                           ,[UpdatedAt] = ?
                           ,[UpdatedBy] = ?
                      WHERE ID = ?
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nv.getTenNhanVien());
            ps.setObject(2, nv.getNgaySinh());
            ps.setObject(3, nv.getDiaChi());
            ps.setObject(4, nv.getSdt());
            ps.setObject(5, nv.getEmail());
            ps.setObject(6, nv.getIdChucVu());
            ps.setObject(7, nv.getCccd());
            ps.setObject(8, nv.getCreatedAt());
            ps.setObject(9, nv.getCreatedBy());
            ps.setObject(10, oldID);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean add(NhanVien nv) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[NhanVien]
                                     ([TenNV]
                                     ,[NgaySinh]
                                     ,[DiaChi]
                                     ,[SDT]
                                     ,[Email]
                                     ,[IDChucVu]
                                     ,[Deleted]
                                     ,[CreatedAt]
                                     ,[CreatedBy]
                                     ,[UpdatedAt]
                                     ,[UpdatedBy] 
                                     ,[CCCD])
                               VALUES
                                     (?,?,?,?,?,(SELECT TOP 1 ID FROM ChucVu WHERE TenChucVu = ?), ?,?,?,?,?,?)
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nv.getTenNhanVien());
            ps.setObject(2, nv.getNgaySinh());
            ps.setObject(3, nv.getDiaChi());
            ps.setObject(4, nv.getSdt());
            ps.setObject(5, nv.getEmail());
            ps.setObject(6, nv.getIdChucVu());
            ps.setObject(7, nv.getDeleted());
            ps.setObject(8, nv.getCreatedAt());
            ps.setObject(9, nv.getCreatedBy());
            ps.setObject(10, nv.getUpdatedAt());
            ps.setObject(11, nv.getUpdateBy());
            ps.setObject(12, nv.getCccd());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<NhanVienViewModel> search(String search) {
        List<NhanVienViewModel> searchResults = new ArrayList<>();
        String sql = """
                 SELECT dbo.NhanVien.ID, dbo.NhanVien.TenNV, dbo.NhanVien.NgaySinh, dbo.NhanVien.DiaChi, dbo.NhanVien.SDT, dbo.NhanVien.Email, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu
                 FROM dbo.ChucVu 
                 INNER JOIN dbo.NhanVien ON dbo.ChucVu.ID = dbo.NhanVien.IDChucVu 
                 WHERE NhanVien.Deleted = 1
                 AND (dbo.NhanVien.TenNV LIKE ? OR dbo.NhanVien.DiaChi LIKE ? OR dbo.NhanVien.SDT LIKE ? OR dbo.NhanVien.Email LIKE ? OR dbo.NhanVien.CCCD LIKE ? OR dbo.ChucVu.TenChucVu LIKE ?)
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            // Set parameters
            String keywordPattern = "%" + search + "%";
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, keywordPattern);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienViewModel nv = new NhanVienViewModel();
                nv.setId(rs.getString(1));
                nv.setTenNhanVien(rs.getString(2));
                nv.setNgaySinh(rs.getDate(3).toLocalDate());
                nv.setDiaChi(rs.getString(4));
                nv.setSdt(rs.getString(5));
                nv.setEmail(rs.getString(6));
                nv.setCccd(rs.getString(7));
                nv.setChucVu(rs.getString(8));
                searchResults.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public boolean xuatDanhSachNhanVien() {
        try ( Connection connection = DBConnect.getConnection()) {
            String query = """
                SELECT dbo.NhanVien.ID, dbo.NhanVien.TenNV, dbo.NhanVien.NgaySinh, dbo.NhanVien.DiaChi, dbo.NhanVien.SDT, dbo.NhanVien.Email, dbo.NhanVien.CCCD, dbo.ChucVu.TenChucVu
                FROM dbo.ChucVu INNER JOIN dbo.NhanVien ON dbo.ChucVu.ID = dbo.NhanVien.IDChucVu WHERE NhanVien.Deleted = 1
                """;

            try ( PreparedStatement statement = connection.prepareStatement(query);  ResultSet resultSet = statement.executeQuery()) {

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách nhân viên");

                // Tạo phông in đậm cho header
                Font fontHeader = (Font) workbook.createFont();
                fontHeader.setBold(true);
                CellStyle styleHeader = workbook.createCellStyle();
                styleHeader.setFont((org.apache.poi.ss.usermodel.Font) fontHeader);

                // Tạo phông in đậm cho dữ liệu
                Font fontData = (Font) workbook.createFont();
                CellStyle styleData = workbook.createCellStyle();
                styleData.setFont((org.apache.poi.ss.usermodel.Font) fontData);

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                Row headerRow = sheet.createRow(0);

                // Tạo header cho danh sách nhân viên
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(columnName);
                    cell.setCellStyle(styleHeader);
                }

                int rowIndex = 1;
                while (resultSet.next()) {
                    Row row = sheet.createRow(rowIndex++);

                    // Điền dữ liệu vào các cột
                    for (int i = 1; i <= columnCount; i++) {
                        Cell dataCell = row.createCell(i - 1);
                        dataCell.setCellValue(resultSet.getString(i));
                        dataCell.setCellStyle(styleData); // Sử dụng phông in đậm cho dữ liệu
                    }
                }

                // Yêu cầu người dùng chọn nơi lưu trữ và nhập tên file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    // Đảm bảo có đuôi ".xlsx"
                    String filePath = fileToSave.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }

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

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
