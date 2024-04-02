/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel.ThongKe;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import mobileworld.config.DBConnect;

/**
 *
 * @author Admin
 */
public class ThongKeDAOImplement implements ThongKeDAO {

    @Override
    public List<HoaDonTK> getListByHoaDon() {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
                     SELECT CONCAT(YEAR(HoaDon.NgayTao), '-', MONTH(HoaDon.NgayTao)) AS ThangNam, SUM(HoaDon.TongTienSauGiam) AS TongTien
                          FROM HoaDon
                          GROUP BY CONCAT(YEAR(HoaDon.NgayTao), '-', MONTH(HoaDon.NgayTao))
                          ORDER BY ThangNam ASC;   
                       """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setThongKeThang(rs.getString(1));
                tk.setThanhTien(rs.getFloat(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public float getDoanhThu() {
        float tt = 0;
        String sql = """
			Select SUM(HoaDon.TongTienSauGiam) from HoaDon
                                            """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tt = rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tt;

    }

    @Override
    public int soHD() {
        int soLieu = 0;
        String sql = """
			Select COUNT(HoaDon.ID) from HoaDon
                                            """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                soLieu = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLieu;
    }

    @Override
    public float hoaDonChuaThanhToan() {
        float soLieu = 0;
        String sql = """
			Select SUM(HoaDon.TongTienSauGiam) from HoaDon where TrangThai = 0
                                            """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                soLieu = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLieu;
    }

    @Override
    public float soTienDaThuDuoc() {
        float tt = 0;
        String sql = """
			Select SUM(HoaDon.TongTienSauGiam) from HoaDon where TrangThai = 1
                        """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tt = rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tt;
    }

    @Override
    public List<HoaDonTK> timTheoNam(String Year) {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
            	SELECT CONCAT(YEAR(HoaDon.NgayTao), '-', MONTH(HoaDon.NgayTao)) AS ThangNam, SUM(HoaDon.TongTienSauGiam) AS TongTien
            FROM HoaDon
            WHERE YEAR(HoaDon.NgayTao) = ?
            GROUP BY CONCAT(YEAR(HoaDon.NgayTao), '-', MONTH(HoaDon.NgayTao))
            ORDER BY ThangNam ASC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setThongKeThang(rs.getString(1));
                tk.setThanhTien(rs.getFloat(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<HoaDonTK> timTheoNamTable(String Year) {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
            	  SELECT HoaDon.ID, CONVERT(DATE, HoaDon.NgayTao), HoaDon.TongTienSauGiam FROM HoaDon 
                  WHERE YEAR(HoaDon.NgayTao) = ?
                  ORDER BY CONVERT(DATE, HoaDon.NgayTao) DESC;
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setMaHD(rs.getString(1));
                tk.setNgayTao(rs.getDate(2).toLocalDate());
                tk.setThanhTien(rs.getFloat(3));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<HoaDonTK> timTheoThoiGianTable(LocalDate ngayBD, LocalDate ngayKT) {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
            SELECT HoaDon.ID, CONVERT(DATE, HoaDon.NgayTao), HoaDon.TongTienSauGiam
            FROM HoaDon 
            WHERE NgayTao >= ? AND NgayTao <= ?
            ORDER BY CONVERT(DATE, HoaDon.NgayTao) DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayBD);
            ps.setObject(2, ngayKT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setMaHD(rs.getString(1));
                tk.setNgayTao(rs.getDate(2).toLocalDate());
                tk.setThanhTien(rs.getFloat(3));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<HoaDonTK> hienBang() {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
           SELECT HoaDon.ID, CONVERT(DATE, HoaDon.NgayTao), HoaDon.TongTienSauGiam FROM HoaDon 
           ORDER BY HoaDon.NgayTao DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setMaHD(rs.getString(1));
                tk.setNgayTao(rs.getDate(2).toLocalDate());
                tk.setThanhTien(rs.getFloat(3));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<HoaDonTK> thongKeTheoNgayChart(LocalDate ngayHT) {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
            SELECT CONVERT(DATE, HoaDon.NgayTao), SUM(HoaDon.TongTienSauGiam) AS TongTien
            FROM HoaDon 
            WHERE NgayTao = ?	
            GROUP BY CONVERT(DATE, HoaDon.NgayTao)
            ORDER BY CONVERT(DATE, HoaDon.NgayTao) ASC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayHT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setNgayTao(rs.getDate(1).toLocalDate());
                tk.setThanhTien(rs.getFloat(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;

    }

    @Override
    public List<HoaDonTK> thongKeTheoNgayTable(LocalDate ngayHT) {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
          SELECT HoaDon.ID,CONVERT(DATE, HoaDon.NgayTao), HoaDon.TongTienSauGiam AS TongTien
                       FROM HoaDon 
                       WHERE NgayTao = ?	
                       ORDER BY CONVERT(DATE, HoaDon.NgayTao) DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayHT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setMaHD(rs.getString(1));
                tk.setNgayTao(rs.getDate(2).toLocalDate());
                tk.setThanhTien(rs.getFloat(3));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public List<HoaDonTK> timTheoThoiGianChart(LocalDate ngayBD, LocalDate ngayKT) {
        List<HoaDonTK> ds = new ArrayList<>();

        String sql = """
            	SELECT CONVERT(DATE, HoaDon.NgayTao), SUM(HoaDon.TongTienSauGiam)
                        FROM HoaDon 				
                        WHERE NgayTao >= ?  AND NgayTao <= ?
                        GROUP BY CONVERT(DATE, HoaDon.NgayTao)
            			ORDER BY CONVERT(DATE, HoaDon.NgayTao) ASC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayBD);
            ps.setObject(2, ngayKT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setNgayTao(rs.getDate(1).toLocalDate());
                tk.setThanhTien(rs.getFloat(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;

    }

    @Override
    public List<HoaDonTK> sanPhamBanChayTable() {

        List<HoaDonTK> ds = new ArrayList<>();
        String sql = """
            	SELECT top 10 dbo.DongSP.TenDsp, COUNT(dbo.DongSP.TenDsp) AS Expr1
                FROM            dbo.HoaDonChiTiet INNER JOIN
                                         dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID INNER JOIN
                                         dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID INNER JOIN
                                         dbo.HoaDon ON dbo.HoaDonChiTiet.IDHoaDon = dbo.HoaDon.ID                
                GROUP BY dbo.DongSP.TenDsp
                ORDER BY COUNT(dbo.DongSP.TenDsp) DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setDongSP(rs.getString(1));
                tk.setSoLuong(rs.getInt(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;

    }

    @Override
    public List<HoaDonTK> sanPhamBanChayPerYear(String Year) {
        List<HoaDonTK> ds = new ArrayList<>();
        String sql = """
            	SELECT TOP 10 dbo.DongSP.TenDsp, COUNT(dbo.DongSP.TenDsp) AS Expr1
                                FROM            dbo.HoaDonChiTiet INNER JOIN
                                                         dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID INNER JOIN
                                                         dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID INNER JOIN
                                                         dbo.HoaDon ON dbo.HoaDonChiTiet.IDHoaDon = dbo.HoaDon.ID
                                                         where YEAR(HoaDon.NgayTao) = ?
                                GROUP BY dbo.DongSP.TenDsp
                                ORDER BY COUNT(dbo.DongSP.TenDsp)  DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setDongSP(rs.getString(1));
                tk.setSoLuong(rs.getInt(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;

    }

    @Override
    public List<HoaDonTK> sanPhamBanChayPerTime(LocalDate ngayBD, LocalDate ngayKT) {
        List<HoaDonTK> ds = new ArrayList<>();
        String sql = """
                SELECT TOP 10 dbo.DongSP.TenDsp, COUNT(dbo.DongSP.TenDsp) AS Expr1
                FROM            dbo.HoaDonChiTiet INNER JOIN
                                         dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID INNER JOIN
                                         dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID INNER JOIN
                                         dbo.HoaDon ON dbo.HoaDonChiTiet.IDHoaDon = dbo.HoaDon.ID
                                         where HoaDon.NgayTao >= ?  AND HoaDon.NgayTao <= ?
                GROUP BY dbo.DongSP.TenDsp
                ORDER BY COUNT(dbo.DongSP.TenDsp)  DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayBD);
            ps.setObject(2, ngayKT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setDongSP(rs.getString(1));
                tk.setSoLuong(rs.getInt(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;

    }

    @Override
    public List<HoaDonTK> sanPhamBanChayToday(LocalDate ngayHT) {
        List<HoaDonTK> ds = new ArrayList<>();
        String sql = """
              SELECT TOP 10 dbo.DongSP.TenDsp, COUNT(dbo.DongSP.TenDsp) AS Expr1
                FROM            dbo.HoaDonChiTiet INNER JOIN
                                         dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID INNER JOIN
                                         dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID INNER JOIN
                                         dbo.HoaDon ON dbo.HoaDonChiTiet.IDHoaDon = dbo.HoaDon.ID
                						 where HoaDon.NgayTao = ?
                GROUP BY dbo.DongSP.TenDsp
                ORDER BY COUNT(dbo.DongSP.TenDsp)  DESC
                          """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayHT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTK tk = new HoaDonTK();
                tk.setDongSP(rs.getString(1));
                tk.setSoLuong(rs.getInt(2));
                ds.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

}
