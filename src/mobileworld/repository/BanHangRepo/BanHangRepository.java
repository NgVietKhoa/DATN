package mobileworld.repository.BanHangRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class BanHangRepository {

    public List<HoaDonViewModel> getHD() {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                    SELECT 
                         HoaDon.ID, 
                         HoaDon.CreatedAt,
                         HoaDon.CreatedBy, 
                         HoaDon.TenKhachHang,
                         COUNT(HoaDonChiTiet.ID) AS TongSoSanPham,
                         HoaDon.TrangThai
                     FROM   
                         dbo.HoaDon 
                     INNER JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
                     INNER JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                     INNER JOIN
                         dbo.HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.IDHoaDon
                     GROUP BY
                         HoaDon.ID, 
                         HoaDon.CreatedAt,
                         HoaDon.CreatedBy, 
                         HoaDon.TenKhachHang, 
                         HoaDon.TrangThai
                     ORDER BY 
                         MAX(HoaDon.NgayThanhToan) ASC
                     """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hdvm = new HoaDonViewModel();
                hdvm.setIdHD(rs.getString(1));
                hdvm.setCreateAt(rs.getDate(2));
                hdvm.setCreateBy(rs.getString(3));
                hdvm.setTenKH(rs.getString(4));
                hdvm.setTongSP(rs.getInt(5));
                hdvm.setTrangthai(rs.getInt(6));
                list.add(hdvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietSanPhamViewModel> getSP() {
        List<ChiTietSanPhamViewModel> listSp = new ArrayList<>();

        String sql = """
                 WITH DSP_Count AS (
                                         SELECT
                                             IDDongSP,
                                             COUNT(*) AS SoLuongDSP
                                         FROM
                                             dbo.ChiTietSP
                                         WHERE 
                                             Deleted = 1
                                         GROUP BY
                                             IDDongSP
                                     ),
                                     CTE_RN AS (
                                         SELECT
                                             CTS.ID,
                                             DS.TenDsp,
                                             NSX.TenNsx,
                                             ManHinh.LoaiManHinh,
                                             CPU.CPU,
                                             Pin.DungLuongPin,
                                             DC.SoLuongDSP,
                                             SUM(CTS.GiaBan) OVER(PARTITION BY CTS.IDDongSP) AS TongGiaBan,
                                             ROW_NUMBER() OVER(PARTITION BY CTS.IDDongSP ORDER BY CTS.ID DESC) AS RN
                                         FROM
                                             dbo.ChiTietSP AS CTS
                                         INNER JOIN 
                                             DSP_Count AS DC ON CTS.IDDongSP = DC.IDDongSP
                                         INNER JOIN 
                                             dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID
                                         INNER JOIN 
                                             dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                                         INNER JOIN 
                                             dbo.Pin ON CTS.IDPin = Pin.ID
                                         INNER JOIN 
                                             dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                                         INNER JOIN 
                                             dbo.CPU ON CTS.IDCPU = CPU.ID
                                         WHERE 
                                             CTS.Deleted = 1
                                     )
                                     SELECT 
                 			 ID,
                                         TenDsp,
                                         TenNsx,
                                         LoaiManHinh,
                                         CPU,
                                         DungLuongPin,
                                         TongGiaBan,
                                         SoLuongDSP
                                     FROM 
                                         CTE_RN
                                     WHERE 
                                         RN = 1
                                     ORDER BY 
                                         ID DESC;
                 """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setId(rs.getString(1));
                spvm.setTenDsp(rs.getString(2));
                spvm.setTenNsx(rs.getString(3));
                spvm.setLoaiManHinh(rs.getString(4));
                spvm.setCpu(rs.getString(5));
                spvm.setDungLuongPin(rs.getString(6));
                spvm.setGiaBan(rs.getBigDecimal(7));
                spvm.setSoLuong(rs.getInt(8));
                listSp.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSp;
    }
}
