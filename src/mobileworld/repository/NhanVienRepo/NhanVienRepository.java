package mobileworld.repository.NhanVienRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.NhanVien;

public class NhanVienRepository {

     public List<NhanVien> getAll() {
        List<NhanVien> listnhanvien = new ArrayList<>();
        String sql = """
                     SELECT [TenNV]
                             ,[NgaySinh]
                             ,[DiaChi]
                             ,[SDT]
                             ,[Email]
                             ,[IDChucVu]
                             ,[Password]
                             ,[Deleted]
                             ,[CreatedAt]
                             ,[CreatedBy]
                             ,[UpdatedAt]
                             ,[UpdatedBy]
                             ,[ID]
                         FROM [dbo].[NhanVien]
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setTenNhanVien(rs.getString(1));
                nv.setNgaySinh(rs.getDate(2));
                nv.setDiaChi(rs.getString(3));
                nv.setSdt(rs.getString(4));
                nv.setEmail(rs.getString(5));
                nv.setIdChucVu(rs.getString(6));
                nv.setPassword(rs.getString(7));
                nv.setDeleted(rs.getInt(8));
                nv.setCreatedAt(rs.getDate(9));
                nv.setCreatedBy(rs.getString(10));
                nv.setUpdatedAt(rs.getDate(11));
                nv.setUpdateBy(rs.getString(12));
                nv.setId(rs.getString(13));
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
                     DELETE FROM [dbo].[NhanVien]
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
                           ,[IDChucVu] = ?
                           ,[Password] = ?
                           ,[Deleted] = ?
                           ,[CreatedAt] = ?
                           ,[CreatedBy] = ?
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
            ps.setObject(7, nv.getPassword());
            ps.setObject(8, nv.getDeleted());
            ps.setObject(9, nv.getCreatedAt());
            ps.setObject(10, nv.getCreatedBy());
            ps.setObject(11, nv.getUpdatedAt());
            ps.setObject(12, nv.getUpdateBy());
            ps.setObject(13, oldID);
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
                                ,[Password]
                                ,[Deleted]
                                      ,[CreatedAt]
                                      ,[CreatedBy]
                                      ,[UpdatedAt]
                                      ,[UpdatedBy]
                                )
                          VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nv.getTenNhanVien());
            ps.setObject(2, nv.getNgaySinh());
            ps.setObject(3, nv.getDiaChi());
            ps.setObject(4, nv.getSdt());
            ps.setObject(5, nv.getEmail());
            ps.setObject(6, nv.getIdChucVu());
            ps.setObject(7, nv.getPassword());
            ps.setObject(8, nv.getDeleted());
            ps.setObject(9, nv.getCreatedAt());
            ps.setObject(10, nv.getCreatedBy());
            ps.setObject(11, nv.getUpdatedAt());
            ps.setObject(12, nv.getUpdateBy());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
     public List<NhanVien> search(String search) {
        List<NhanVien> listnhanvien = new ArrayList<>();
        String sql = """
                     SELECT [TenNV]
                             ,[NgaySinh]
                             ,[DiaChi]
                             ,[SDT]
                             ,[Email]
                             ,[IDChucVu]
                             ,[Password]
                             ,[Deleted]
                             ,[CreatedAt]
                             ,[CreatedBy]
                             ,[UpdatedAt]
                             ,[UpdatedBy]
                             ,[ID]
                         FROM [dbo].[NhanVien]
                      Where ID like ? ESCAPE '!'
                       or [TenNV] like ? ESCAPE '!';
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
             for (int i = 0; i < search.length(); i++) {
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setTenNhanVien(rs.getString(1));
                nv.setNgaySinh(rs.getDate(2));
                nv.setDiaChi(rs.getString(3));
                nv.setSdt(rs.getString(4));
                nv.setEmail(rs.getString(5));
                nv.setIdChucVu(rs.getString(6));
                nv.setPassword(rs.getString(7));
                nv.setDeleted(rs.getInt(8));
                nv.setCreatedAt(rs.getDate(9));
                nv.setCreatedBy(rs.getString(10));
                nv.setUpdatedAt(rs.getDate(11));
                nv.setUpdateBy(rs.getString(12));
                nv.setId(rs.getString(13));
                listnhanvien.add(nv);
            }
            break;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listnhanvien;
    }
    
}
