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
                hdvm.setTongSP(rs.getInt(4));
                hdvm.setTrangthai(rs.getInt(5));
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
                         CTS.GiaBan,
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
                     GiaBan,
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

    public List<ChiTietSanPhamViewModel> search(String keyword) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();
        List<ChiTietSanPhamViewModel> allSP = getSP(); // Lấy danh sách tất cả sản phẩm

        // Lọc dựa trên tiêu chí tìm kiếm
        for (ChiTietSanPhamViewModel sp : allSP) {
            if (matchesSearchCriteria(sp, keyword)) {
                listSP.add(sp);
            }
        }

        return listSP;
    }

// Phương thức trợ giúp kiểm tra xem một sản phẩm có khớp với tiêu chí tìm kiếm hay không
    private boolean matchesSearchCriteria(ChiTietSanPhamViewModel sp, String search) {
        String searchTerm = search.toLowerCase(); // Chuyển đổi từ tìm kiếm thành chữ thường để so sánh không phân biệt chữ hoa chữ thường
        // Kiểm tra xem bất kỳ trường nào của sản phẩm có chứa từ khóa tìm kiếm hay không
        return sp.getId().toLowerCase().contains(searchTerm)
                || sp.getTenDsp().toLowerCase().contains(searchTerm)
                || sp.getTenNsx().toLowerCase().contains(searchTerm)
                || sp.getLoaiManHinh().toLowerCase().contains(searchTerm)
                || sp.getCpu().toLowerCase().contains(searchTerm)
                || sp.getDungLuongPin().toLowerCase().contains(searchTerm)
                || String.valueOf(sp.getSoLuong()).toLowerCase().contains(searchTerm);
    }

    public List<ChiTietSanPhamViewModel> LocSP(String Nsx, String Pin, String ManHinh, String Cpu, boolean sapXepGiaTangDan) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = "WITH DSP_Count AS ("
                + "SELECT "
                + "    IDDongSP, "
                + "    COUNT(*) AS SoLuongDSP "
                + "FROM "
                + "    dbo.ChiTietSP "
                + "WHERE "
                + "    Deleted = 1 "
                + "GROUP BY "
                + "    IDDongSP "
                + "), "
                + "CTE_RN AS ("
                + "SELECT "
                + "    CTS.IDDongSP, "
                + "    CTS.ID, "
                + "    DS.TenDSP, "
                + "    NSX.TenNsx, "
                + "    ManHinh.LoaiManHinh, "
                + "    CPU.CPU, "
                + "    Pin.DungLuongPin, "
                + "    SUM(CTS.GiaBan) OVER(PARTITION BY CTS.IDDongSP) AS TongGiaBan, "
                + "    DC.SoLuongDSP, "
                + "    ROW_NUMBER() OVER(PARTITION BY CTS.IDDongSP ORDER BY CTS.GiaBan " + (sapXepGiaTangDan ? "ASC" : "DESC") + ") AS RN "
                + "FROM "
                + "    dbo.ChiTietSP AS CTS "
                + "INNER JOIN "
                + "    DSP_Count AS DC ON CTS.IDDongSP = DC.IDDongSP "
                + "INNER JOIN "
                + "    dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID "
                + "INNER JOIN "
                + "    dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID "
                + "INNER JOIN "
                + "    dbo.Pin ON CTS.IDPin = Pin.ID "
                + "INNER JOIN "
                + "    dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID "
                + "INNER JOIN "
                + "    dbo.CPU ON CTS.IDCPU = CPU.ID "
                + "WHERE "
                + "CTS.Deleted = 1 "
                + "AND ( "
                + "NSX.TenNsx LIKE ? OR "
                + "Pin.DungLuongPin LIKE ? OR "
                + "ManHinh.LoaiManHinh LIKE ? OR "
                + "CPU.CPU LIKE ? "
                + ") "
                + "), "
                + "CTE_Final AS ("
                + "SELECT "
                + "    IDDongSP, "
                + "    ID, "
                + "    TenDSP, "
                + "    TenNsx, "
                + "    LoaiManHinh, "
                + "    CPU, "
                + "    DungLuongPin, "
                + "    (SELECT SUM(GiaBan) FROM dbo.ChiTietSP WHERE IDDongSP = CTE_RN.IDDongSP) AS TongGiaBan, "
                + "    SoLuongDSP, "
                + "    ROW_NUMBER() OVER(PARTITION BY IDDongSP ORDER BY ID DESC) AS RN "
                + "FROM "
                + "    CTE_RN "
                + "WHERE "
                + "    RN = 1 "
                + ") "
                + "SELECT "
                + "    * "
                + "FROM "
                + "    CTE_Final ";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Nsx);
            ps.setObject(2, Pin);
            ps.setObject(3, ManHinh);
            ps.setObject(4, Cpu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setIdDsp(rs.getString("IDDongSP"));
                spvm.setId(rs.getString("ID"));
                spvm.setTenDsp(rs.getString("TenDSP"));
                spvm.setTenNsx(rs.getString("TenNsx"));
                spvm.setLoaiManHinh(rs.getString("LoaiManHinh"));
                spvm.setCpu(rs.getString("CPU"));
                spvm.setDungLuongPin(rs.getString("DungLuongPin"));
                spvm.setGiaBan(rs.getBigDecimal("TongGiaBan"));
                spvm.setSoLuong(rs.getInt("SoLuongDSP"));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> getGioHang(String idDsp) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

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
                                     Imel.Imel,
                                     DS.TenDSP,
                                     Pin.DungLuongPin,
                                     ManHinh.LoaiManHinh,
                                     CPU.CPU,
                                     Ram.DungLuongRam,
                                     BoNho.DungLuongBoNho,
                                     MauSac.TenMau,
                                     CTS.GiaBan,
                                     CTS.ID,
                                     ROW_NUMBER() OVER (PARTITION BY DS.TenDSP ORDER BY CTS.ID DESC) AS RN
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
                                 INNER JOIN 
                                     dbo.Ram ON CTS.IDRam = Ram.ID
                                 INNER JOIN 
                                     dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                                 INNER JOIN 
                                     dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                                 INNER JOIN 
                                     dbo.Imel ON CTS.IDImel = Imel.ID
                                 WHERE 
                                     CTS.Deleted = 1 AND DS.ID = (SELECT ID FROM DongSP WHERE TenDsp = ?)
                             )
                             SELECT
                                 Imel,
                                 TenDsp,
                                 DungLuongPin,
                                 LoaiManHinh,
                                 CPU,
                                 DungLuongRam,
                                 DungLuongBoNho,
                                 TenMau,
                                 GiaBan,
                                 ID    
                             FROM 
                                 CTE_RN
                             WHERE 
                                 RN = 1
                             ORDER BY 
                                 ID DESC;
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idDsp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setImel(rs.getString(1));
                spvm.setTenDsp(rs.getString(2));
                spvm.setDungLuongPin(rs.getString(3));
                spvm.setLoaiManHinh(rs.getString(4));
                spvm.setCpu(rs.getString(5));
                spvm.setDungLuongRam(rs.getString(6));
                spvm.setDungLuongBoNho(rs.getString(7));
                spvm.setTenMau(rs.getString(8));
                spvm.setGiaBan(rs.getBigDecimal(9));
                spvm.setId(rs.getString(10));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> selectIdDSP(String idDsp) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
                 SELECT
                             CTS.IDDongSP,
                             DS.TenDsp,
                             Imel.Imel
                         FROM
                             dbo.ChiTietSP AS CTS
                             INNER JOIN dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                             INNER JOIN dbo.Imel ON CTS.IDImel = Imel.ID
                         WHERE
                             CTS.Deleted = 1 AND DS.ID = (SELECT ID FROM DongSP WHERE TenDsp = ?)
                         ORDER BY
                             CTS.ID DESC;
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setTenDsp(rs.getString(1));
                spvm.setImel(rs.getString(2));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

}
