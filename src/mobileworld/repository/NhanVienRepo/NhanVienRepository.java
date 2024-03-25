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
        List<NhanVien> listNV = new ArrayList<>();

        String sql = """
                SELECT 
                    dbo.NHANVIEN.ID, 
                    dbo.NHANVIEN.TenNV, 
                    dbo.NHANVIEN.SDT, 
                    dbo.NHANVIEN.Email, 
                    dbo.NHANVIEN.DiaChi, 
                    dbo.NHANVIEN.NgaySinh, 
                    dbo.NHANVIEN.Password, 
                    dbo.NHANVIEN.Deleted, 
                    dbo.NHANVIEN.[Created at], 
                    dbo.NHANVIEN.[Created by], 
                    dbo.NHANVIEN.[Updated at], 
                    dbo.NHANVIEN.[Updated by], 
                    dbo.ChucVu.TenChucVu,
                    dbo.NHANVIEN.[CCCD]
                FROM     
                    dbo.NHANVIEN 
                INNER JOIN
                    dbo.ChucVu ON dbo.NHANVIEN.IDChucVu = dbo.ChucVu.ID AND dbo.NHANVIEN.IDChucVu = dbo.ChucVu.ID
                WHERE 
                    dbo.NhanVien.Deleted = 1 
                ORDER BY 
                    ID
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getString(1));
                nv.setTenNhanVien(rs.getString(2));
                nv.setSdt(rs.getString(3));
                nv.setEmail(rs.getString(4));
                nv.setDiaChi(rs.getString(5));
                nv.setNgaySinh(rs.getDate(6));
                nv.setPassword(rs.getString(7));
                nv.setDeleted(rs.getFloat(8));
                nv.setCreatedAt(rs.getDate(9));
                nv.setCreatedBy(rs.getString(10));
                nv.setUpdatedAt(rs.getDate(11));
                nv.setUpdateBy(rs.getString(12));
                nv.setIdChucVu(rs.getString(13));
                nv.setCccd(rs.getString(14));
                listNV.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNV;
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
}
