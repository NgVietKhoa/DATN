package mobileworld.repository.NhanVienRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.NhanVien;
import mobileworld.viewModel.NhanVienViewModel;

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
                           ,[Password] = ?
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
            ps.setObject(8, nv.getCccd());
            ps.setObject(7, nv.getPassword());
            ps.setObject(9, nv.getUpdatedAt());
            ps.setObject(10, nv.getUpdateBy());
            ps.setObject(11, oldID);
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
                                     ,[CCCD])
                               VALUES
                                     (?,?,?,?,?,(SELECT TOP 1 ID FROM ChucVu WHERE TenChucVu = ?), ?,?,?,?)
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
            ps.setObject(10, nv.getCccd());
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
}
