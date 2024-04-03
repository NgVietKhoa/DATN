package mobileworld.repository.BanHangRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.HoaDon;
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
                         HoaDon.TrangThai,
                         HoaDon.Deleted
                     FROM
                         dbo.HoaDon
                     LEFT JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon
                     LEFT JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                     LEFT JOIN
                         dbo.HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.IDHoaDon
                     WHERE
                         HoaDon.Deleted = 1
                     GROUP BY
                         HoaDon.ID,
                         HoaDon.CreatedAt,
                         HoaDon.CreatedBy,
                         HoaDon.TrangThai,
                         HoaDon.Deleted
                     ORDER BY
                         MAX(HoaDon.NgayThanhToan) DESC
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hdvm = new HoaDonViewModel();
                hdvm.setIdHD(rs.getString(1));
                hdvm.setCreateAt(rs.getDate(2));
                hdvm.setCreateBy(rs.getString(3));
                hdvm.setTongSP(rs.getInt(4));
                hdvm.setTrangthai(rs.getInt(5));
                hdvm.setDeleted(rs.getFloat(6));
                list.add(hdvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonViewModel> searchHD(String text) {
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
                         LEFT JOIN
                             dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon
                         LEFT JOIN
                             dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                         LEFT JOIN
                             dbo.HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.IDHoaDon
                         WHERE
                             HoaDon.ID LIKE ? ESCAPE '!'
                             OR CONVERT(VARCHAR, HoaDon.CreatedAt, 111) LIKE ? ESCAPE '!'
                             OR HoaDon.CreatedBy LIKE ? ESCAPE '!'
                         GROUP BY
                             HoaDon.ID,
                             HoaDon.CreatedAt,
                             HoaDon.CreatedBy,
                             HoaDon.TrangThai
                         ORDER BY
                             MAX(HoaDon.NgayThanhToan) DESC
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            for (int i = 0; i < text.length(); i++) {
                ps.setString(1, "%" + text + "%");
                ps.setString(2, "%" + text + "%");
                ps.setString(3, "%" + text + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        HoaDonViewModel hdvm = new HoaDonViewModel();
                        hdvm.setIdHD(rs.getString(1));
                        hdvm.setCreateAt(rs.getDate(2));
                        hdvm.setCreateBy(rs.getString(3));
                        hdvm.setTongSP(rs.getInt(4));
                        hdvm.setTrangthai(rs.getInt(5));
                        list.add(hdvm);
                    }
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addNewBlankInvoice(HoaDon hd, String idNV) {
        int check = 0;
        String sql = """
            INSERT INTO [dbo].[HoaDon]
                       ([IDKhachHang]
                       ,[IDNhanVien]
                       ,[NgayTao]
                       ,[NgayThanhToan]
                       ,[TongTien]
                       ,[TongTienSauGiam]
                       ,[TenKhachHang]
                       ,[SoDienThoaiKhachHang]
                       ,[DiaChiKhachHang]
                       ,[Deleted]
                       ,[CreatedAt]
                       ,[CreatedBy]
                       ,[UpdatedAt]
                       ,[UpdatedBy] 
                       ,[TrangThai])
                           
                 VALUES
                       ((SELECT ID FROM KhachHang WHERE Ten = ?),?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            // Thiết lập các tham số cho câu lệnh SQL
            ps.setObject(1, hd.getIdKH());
            ps.setObject(2, idNV);
            ps.setObject(3, new Timestamp(new Date().getTime()));
            ps.setObject(4, new Timestamp(new Date().getTime()));
            ps.setObject(5, hd.getTongTien());
            ps.setObject(6, hd.getTongTienSauGiam());
            ps.setObject(7, hd.getTenKH());
            ps.setObject(8, hd.getSdtKH());
            ps.setObject(9, hd.getDiaChiKH());
            ps.setObject(10, 1);
            ps.setObject(11, new Timestamp(new Date().getTime()));
            ps.setObject(12, idNV);
            ps.setObject(13, new Timestamp(new Date().getTime()));
            ps.setObject(14, idNV);
            ps.setObject(15, 0);
            // Thực thi câu lệnh SQL
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteHD(String idHD) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDon]
                        SET [Deleted] = 0
                        WHERE ID = ?
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean ThanhToanHD(HoaDon hd, String idHD) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDon]
                     SET [IDKhachHang] = ?,
                         [IDNhanVien] = ?,
                         [NgayTao] = ?,
                         [NgayThanhToan] = ?,
                         [TongTien] = ?,
                         [TongTienSauGiam] = ?,
                         [TenKhachHang] = (SELECT Ten FROM KhachHang WHERE ID = ?),
                         [SoDienThoaiKhachHang] = (SELECT SDT FROM KhachHang WHERE ID = ?),
                         [DiaChiKhachHang] = (SELECT DiaChi FROM KhachHang WHERE ID = ?),
                         [Deleted] = ?,
                         [UpdatedAt] = ?,
                         [UpdatedBy] = ?,
                         [TrangThai] = ?
                     WHERE ID = ?;
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {

            ps.setObject(1, hd.getIdKH());
            ps.setObject(2, hd.getIdNV());
            ps.setObject(3, hd.getNgayTao());
            ps.setObject(4, hd.getNgayThanhToan());
            ps.setObject(5, hd.getTongTien());
            ps.setObject(6, hd.getTongTienSauGiam());
            ps.setObject(7, hd.getTenKH());
            ps.setObject(8, hd.getSdtKH());
            ps.setObject(9, hd.getDiaChiKH());
            ps.setObject(10, 1);
            ps.setObject(11, hd.getUpdateAt());
            ps.setObject(12, hd.getUpdateBy());
            ps.setObject(13, 1);
            ps.setObject(14, idHD);
            // Thực thi câu lệnh SQL
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateHoaDon(HoaDon hd, String idNV, String idHD) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDon]
                        SET [IDKhachHang] = (SELECT ID FROM KhachHang WHERE Ten = ?)
                           ,[NgayThanhToan] = ?
                           ,[TongTien] = ?
                           ,[TongTienSauGiam] = ?
                           ,[TenKhachHang] = ?
                           ,[SoDienThoaiKhachHang] = (SELECT SDT FROM KhachHang WHERE ID = ?)
                           ,[DiaChiKhachHang] = (SELECT DiaChi FROM KhachHang WHERE ID = ?)
                           ,[UpdatedAt] = ?
                           ,[UpdatedBy] = ?
                           ,[TrangThai] = ?
                      WHERE [ID] = ?
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            // Thiết lập các tham số cho câu lệnh SQL
            ps.setObject(1, hd.getIdKH());
            ps.setObject(2, hd.getNgayTao());
            ps.setObject(3, hd.getTongTien());
            ps.setObject(4, hd.getTongTienSauGiam());
            ps.setObject(5, hd.getTenKH());
            ps.setObject(6, hd.getSdtKH());
            ps.setObject(7, hd.getDiaChiKH());
            ps.setObject(8, new Timestamp(new Date().getTime()));
            ps.setObject(9, idNV);
            ps.setObject(10, 1);
            ps.setObject(11, idHD);
            // Thực thi câu lệnh SQL
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
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
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
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

    public List<ChiTietSanPhamViewModel> LocSP(String Nsx, String Pin, String ManHinh, String Cpu) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = "SELECT "
                + "    CTS.IDDongSP, "
                + "    CTS.ID, "
                + "    DS.TenDSP, "
                + "    NSX.TenNsx, "
                + "    ManHinh.LoaiManHinh, "
                + "    CPU.CPU, "
                + "    Pin.DungLuongPin, "
                + "    CTS.GiaBan, "
                + "    (SELECT COUNT(*) FROM dbo.ChiTietSP WHERE IDDongSP = CTS.IDDongSP AND Deleted = 1) AS SoLuongDSP "
                + "FROM "
                + "    dbo.ChiTietSP AS CTS "
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
                + "    CTS.Deleted = 1 "
                + "    AND ( "
                + "        NSX.TenNsx LIKE ? OR "
                + "        Pin.DungLuongPin LIKE ? OR "
                + "        ManHinh.LoaiManHinh LIKE ? OR "
                + "        CPU.CPU LIKE ? "
                + "    )";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
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
                spvm.setGiaBan(rs.getBigDecimal("GiaBan"));
                spvm.setSoLuong(rs.getInt("SoLuongDSP"));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> getGioHang(String imel) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
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
                         CTS.IDDongSP
                     FROM
                         dbo.ChiTietSP AS CTS
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
                         CTS.Deleted = 1 AND Imel.Imel = ?
                     ORDER BY 
                         ID DESC;
                     """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, imel);
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
                spvm.setIdDsp(rs.getString(11));
                listSP.add(spvm);
            }
            updateSelectSP(imel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> deleteGioHang(String imel) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
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
                         CTS.IDDongSP
                     FROM
                         dbo.ChiTietSP AS CTS
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
                         CTS.Deleted = 0 AND Imel.Imel = ?
                     ORDER BY 
                         ID DESC;
                     """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, imel);
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
                spvm.setIdDsp(rs.getString(11));
                listSP.add(spvm);
            }
            updateDeleteSP(imel);
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

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setIdDsp(rs.getString(1));
                spvm.setTenDsp(rs.getString(2));
                spvm.setImel(rs.getString(3));
                listSP.add(spvm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> deleteIdDSP(String idDsp) {
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
                             CTS.Deleted = 0 AND DS.ID = (SELECT ID FROM DongSP WHERE TenDsp = ?)
                         ORDER BY
                             CTS.ID DESC;
                 """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setIdDsp(rs.getString(1));
                spvm.setTenDsp(rs.getString(2));
                spvm.setImel(rs.getString(3));
                listSP.add(spvm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public void updateSelectSP(String imel) {
        String updateSql = "UPDATE dbo.ChiTietSP SET Deleted = 0 WHERE IDImel = (SELECT ID FROM dbo.Imel WHERE Imel = ?)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setString(1, imel);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDeleteSP(String imel) {
        String updateSql = "UPDATE dbo.ChiTietSP SET Deleted = 1 WHERE IDImel = (SELECT ID FROM dbo.Imel WHERE Imel = ?)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setString(1, imel);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
