/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.KhachHangRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.KhachHang;
import mobileworld.viewModel.LichSuGiaoDichViewModel;

/**
 *
 * @author Admin
 */
public class LichSuGiaoDichRepo {
    public List<LichSuGiaoDichViewModel> searchLSGG(String search) {
        List<LichSuGiaoDichViewModel> ds = new ArrayList<>();
        String sql = """
                    SELECT        dbo.HoaDon.IDNhanVien, dbo.HoaDon.IDKhachHang, dbo.KhachHang.Ten, dbo.KhachHang.Email, dbo.HoaDon.DiaChiKhachHang, dbo.HoaDon.SoDienThoaiKhachHang, dbo.HoaDon.TrangThai, 
                                             dbo.HoaDon.TongTienSauGiam
                    FROM            dbo.KhachHang INNER JOIN
                                             dbo.HoaDon ON dbo.KhachHang.ID = dbo.HoaDon.IDKhachHang
                     Where HoaDon.IDKhachHang like ? ESCAPE '!'
                     or KhachHang.Ten like ? ESCAPE '!'
                     or KhachHang.Email like ? ESCAPE '!';
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareCall(sql)) {
            for (int i = 0; i < search.length(); i++) {
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
                ps.setString(3, "%" + search + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                  LichSuGiaoDichViewModel lsgg=new LichSuGiaoDichViewModel();
                  lsgg.setIDNV(rs.getString(1));
                  lsgg.setIDKH(rs.getString(2));
                  lsgg.setTenKH(rs.getString(3));
                  lsgg.setMail(rs.getString(4));
                  lsgg.setDiaChi(rs.getString(5));
                  lsgg.setSdt(rs.getString(6));
                  lsgg.setTrangThai(rs.getInt(7));
                  lsgg.setTongTien(rs.getFloat(8));
                  ds.add(lsgg);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
