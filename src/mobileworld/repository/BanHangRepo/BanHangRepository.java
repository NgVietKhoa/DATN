package mobileworld.repository.BanHangRepo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import mobileworld.config.DBConnect;
import mobileworld.model.HinhThucThanhToanEntity;
import mobileworld.model.HoaDon;
import mobileworld.model.HoaDonChiTietEntity;
import mobileworld.model.PhuongThucThanhToan;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import mobileworld.viewModel.ChiTietSanPhamViewModel;
import mobileworld.viewModel.LichSuHDModel;

public class BanHangRepository {

    public List<HoaDonViewModel> getHD() {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                     SELECT
                         HoaDon.IDKhachHang,
                         HoaDon.IDNhanVien,
                         HoaDon.NgayTao,
                         HoaDon.NgayThanhToan,
                         HoaDon.TongTien,
                         HoaDon.TongTienSauGiam,
                         HoaDon.TenKhachHang,
                         HoaDon.SoDienThoaiKhachHang,
                         HoaDon.DiaChiKhachHang,
                         HoaDon.Deleted,
                         HoaDon.ID,
                         HoaDon.CreatedAt,
                         HoaDon.CreatedBy,
                         COUNT(HoaDonChiTiet.ID) AS TongSoSanPham,
                         HoaDon.TrangThai,
                         HinhThucThanhToan.TienCK,
                         HinhThucThanhToan.TienMat,
                         NhanVien.TenNV,
                         NhanVien.SDT,
                         HoaDon.PhiShip,
                         HoaDon.NgayNhan,
                         PhuongThucThanhToan.TenKieuThanhToan
                     FROM
                         dbo.HoaDon
                     LEFT JOIN
                         dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon
                     LEFT JOIN
                         dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                     LEFT JOIN
                         dbo.HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.IDHoaDon
                     LEFT JOIN
                         NhanVien ON HoaDon.IDNhanVien = NhanVien.ID
                     LEFT JOIN
                         dbo.HinhThucThanhToan AS HTTT ON HoaDon.ID = HTTT.IDHoaDon
                     LEFT JOIN
                         dbo.PhuongThucThanhToan AS PTTT ON HTTT.IDPhuongThucThanhToan = PTTT.ID
                     WHERE
                         HoaDon.Deleted = 1 AND TrangThai IN (0,3)
                     GROUP BY 
                         HoaDon.IDKhachHang,
                         HoaDon.IDNhanVien,
                         HoaDon.NgayTao,
                         HoaDon.NgayThanhToan,
                         HoaDon.TongTien,
                         HoaDon.TongTienSauGiam,
                         HoaDon.TenKhachHang,
                         HoaDon.SoDienThoaiKhachHang,
                         HoaDon.DiaChiKhachHang,
                         HoaDon.Deleted,
                         HoaDon.ID,
                         HoaDon.CreatedAt,
                         HoaDon.CreatedBy,
                         HoaDon.TrangThai,
                         HinhThucThanhToan.TienCK,
                         HinhThucThanhToan.TienMat,
                         NhanVien.TenNV,
                         NhanVien.SDT,
                         HoaDon.PhiShip,
                         HoaDon.NgayNhan,
                         PhuongThucThanhToan.TenKieuThanhToan
                     ORDER BY
                         MAX(HoaDon.NgayThanhToan) DESC
                     """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hdvm = new HoaDonViewModel();
                hdvm.setIdKH(rs.getString(1));
                hdvm.setIdNV(rs.getString(2));
                hdvm.setNgayTao(rs.getDate(3));
                hdvm.setNgayThanhToan(rs.getDate(4));
                hdvm.setTongTien(rs.getBigDecimal(5));
                hdvm.setTongTienSauGiam(rs.getBigDecimal(6));
                hdvm.setTenKH(rs.getString(7));
                hdvm.setSdtKH(rs.getString(8));
                hdvm.setDiaChiKH(rs.getString(9));
                hdvm.setDeleted(rs.getFloat(10));
                hdvm.setIdHD(rs.getString(11));
                hdvm.setCreateAt(rs.getDate(12));
                hdvm.setCreateBy(rs.getString(13));
                hdvm.setTongSP(rs.getInt(14));
                hdvm.setTrangthai(rs.getInt(15));
                hdvm.setTienCK(rs.getBigDecimal(16));
                hdvm.setTienMat(rs.getBigDecimal(17));
                hdvm.setTenNV(rs.getString(18));
                hdvm.setSdtNV(rs.getString(19));
                hdvm.setPhiShip(rs.getFloat(20));
                hdvm.setNgayNhan(rs.getDate(21));
                list.add(hdvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getCTSPFromImels(List<String> idImels) {
        List<String> idCTSPs = new ArrayList<>();

        String sql = """
                     SELECT id FROM ChiTietSP WHERE IDImel IN (SELECT id FROM Imel WHERE Imel = ?)
                     """;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            for (String idImel : idImels) {
                ps.setString(1, idImel);
                try ( ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String idCTSP = rs.getString("id");
                        idCTSPs.add(idCTSP);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idCTSPs; // Trả về danh sách ID CTSP
    }

    public List<String> getIdImelFromImels(List<String> Imels) {
        List<String> idImels = new ArrayList<>();

        String sql = "select id from Imel where Imel = ?";
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            for (String Imel : Imels) {
                ps.setString(1, Imel);
                try ( ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String imel = rs.getString("id");
                        idImels.add(imel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idImels;
    }

    public List<String> getCTSPFromImelsAdd(String idImel) {
        List<String> idCTSPs = new ArrayList<>();

        String sql = """
                     SELECT id FROM ChiTietSP WHERE IDImel IN (SELECT id FROM Imel WHERE Imel = ?)
                     """;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setString(1, idImel);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idCTSP = rs.getString("id");
                    idCTSPs.add(idCTSP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idCTSPs; // Trả về danh sách ID CTSP
    }

    public List<String> getIdImelFromImelsAdd(String Imel) {
        List<String> idImels = new ArrayList<>();

        String sql = "select id from Imel where Imel = ?";
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {

            ps.setString(1, Imel);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String imel = rs.getString("id");
                    idImels.add(imel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idImels;
    }

    public List<HoaDonViewModel> searchHD(String text) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = """
                        SELECT
                        HoaDon.IDKhachHang,
                        HoaDon.IDNhanVien,
                        HoaDon.NgayTao,
                        HoaDon.NgayThanhToan,
                        HoaDon.TongTien,
                        HoaDon.TongTienSauGiam,
                        HoaDon.TenKhachHang,
                        HoaDon.SoDienThoaiKhachHang,
                        HoaDon.DiaChiKhachHang,
                        HoaDon.Deleted,
                        HoaDon.ID,
                        HoaDon.CreatedAt,
                        HoaDon.CreatedBy,
                        COUNT(HoaDonChiTiet.ID) AS TongSoSanPham,
                        HoaDon.TrangThai,
                        HinhThucThanhToan.TienCK,
                        HinhThucThanhToan.TienMat,
                        NhanVien.TenNV,
                        NhanVien.SDT,
                        HoaDon.PhiShip,
                        HoaDon.NgayNhan,
                        PhuongThucThanhToan.TenKieuThanhToan
                    FROM
                        dbo.HoaDon
                    LEFT JOIN
                        dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon
                    LEFT JOIN
                        dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
                    LEFT JOIN
                        dbo.HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.IDHoaDon
                    LEFT JOIN
                        NhanVien ON HoaDon.IDNhanVien = NhanVien.ID
                    LEFT JOIN
                        dbo.HinhThucThanhToan AS HTTT ON HoaDon.ID = HTTT.IDHoaDon
                    LEFT JOIN
                        dbo.PhuongThucThanhToan AS PTTT ON HTTT.IDPhuongThucThanhToan = PTTT.ID
                    WHERE
                        HoaDon.Deleted = 1 AND HoaDon.TrangThai IN (0, 3)
                        AND (
                            HoaDon.ID LIKE ? ESCAPE '!'
                            OR HoaDon.TenKhachHang LIKE ? ESCAPE '!'
                            OR HoaDon.SoDienThoaiKhachHang LIKE ? ESCAPE '!'
                        )
                    GROUP BY
                        HoaDon.IDKhachHang,
                        HoaDon.IDNhanVien,
                        HoaDon.NgayTao,
                        HoaDon.NgayThanhToan,
                        HoaDon.TongTien,
                        HoaDon.TongTienSauGiam,
                        HoaDon.TenKhachHang,
                        HoaDon.SoDienThoaiKhachHang,
                        HoaDon.DiaChiKhachHang,
                        HoaDon.Deleted,
                        HoaDon.ID,
                        HoaDon.CreatedAt,
                        HoaDon.CreatedBy,
                        HoaDon.TrangThai,
                        HinhThucThanhToan.TienCK,
                        HinhThucThanhToan.TienMat,
                        NhanVien.TenNV,
                        NhanVien.SDT,
                        HoaDon.PhiShip,
                        HoaDon.NgayNhan,
                        PhuongThucThanhToan.TenKieuThanhToan
                    ORDER BY
                        MAX(HoaDon.NgayThanhToan) DESC
                 """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
            ps.setString(3, "%" + text + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonViewModel hdvm = new HoaDonViewModel();
                    hdvm.setIdKH(rs.getString(1));
                    hdvm.setIdNV(rs.getString(2));
                    hdvm.setNgayTao(rs.getDate(3));
                    hdvm.setNgayThanhToan(rs.getDate(4));
                    hdvm.setTongTien(rs.getBigDecimal(5));
                    hdvm.setTongTienSauGiam(rs.getBigDecimal(6));
                    hdvm.setTenKH(rs.getString(7));
                    hdvm.setSdtKH(rs.getString(8));
                    hdvm.setDiaChiKH(rs.getString(9));
                    hdvm.setDeleted(rs.getFloat(10));
                    hdvm.setIdHD(rs.getString(11));
                    hdvm.setCreateAt(rs.getDate(12));
                    hdvm.setCreateBy(rs.getString(13));
                    hdvm.setTongSP(rs.getInt(14));
                    hdvm.setTrangthai(rs.getInt(15));
                    hdvm.setTienCK(rs.getBigDecimal(16));
                    hdvm.setTienMat(rs.getBigDecimal(17));
                    hdvm.setTenNV(rs.getString(18));
                    hdvm.setSdtNV(rs.getString(19));
                    hdvm.setPhiShip(rs.getFloat(20));
                    hdvm.setNgayNhan(rs.getDate(21));
                    list.add(hdvm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addNewBlankInvoice(HoaDon hd, LichSuHDModel lshdm) {
        int check = 0;
        // Kiểm tra số lượng hóa đơn có trạng thái là 0
        int countChuaThanhToan = countChuaThanhToan();

        // Nếu số lượng đã đạt tới 5, thông báo lỗi
        if (countChuaThanhToan >= 5) {
            JOptionPane.showMessageDialog(null, "Không thể tạo quá 5 hóa đơn chờ, tránh spam hệ thống!");
            return false;
        }

        int countChoGiaoHang = countChoGiaoHang();

        if (countChoGiaoHang >= 3) {
            JOptionPane.showMessageDialog(null, "Không thể tạo quá 3 hóa đơn chờ, tránh spam hệ thống!");
            return false;
        }

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
                   OUTPUT INSERTED.ID
             VALUES
                   ((SELECT ID FROM KhachHang WHERE Ten = ?),?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                 """;

        String sqlInsertLichSuHD = """
                                   INSERT INTO [dbo].[LichSuHoaDon]
                                              ([IDHoaDon]
                                              ,[IDNhanVien]
                                              ,[HanhDong]
                                              ,[Deleted]
                                              ,[CreatedAt]
                                              ,[CreatedBy]
                                              ,[UpdatedAt]
                                              ,[UpdatedBy])
                                        VALUES
                                              (?,?,?,?,?,?,?,?)
                                   """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql);  PreparedStatement psInsertLSHD = cnt.prepareStatement(sqlInsertLichSuHD);) {
            // Thiết lập các tham số cho câu lệnh SQL
            ps.setObject(1, hd.getIdKH());
            ps.setObject(2, hd.getIdNV());
            ps.setObject(3, new Timestamp(new Date().getTime()));
            ps.setObject(4, new Timestamp(new Date().getTime()));
            ps.setObject(5, hd.getTongTien());
            ps.setObject(6, hd.getTongTienSauGiam());
            ps.setObject(7, hd.getTenKH());
            ps.setObject(8, hd.getSdtKH());
            ps.setObject(9, hd.getDiaChiKH());
            ps.setObject(10, 1);
            ps.setObject(11, new Timestamp(new Date().getTime()));
            ps.setObject(12, hd.getCreateBy());
            ps.setObject(13, new Timestamp(new Date().getTime()));
            ps.setObject(14, hd.getUpdateBy());
            ps.setObject(15, hd.getTrangthai());
            // Thực thi câu lệnh SQL
            ResultSet generatedKeys = ps.executeQuery(); // Retrieve the generated keys
            String idHD = null;
            if (generatedKeys.next()) {
                idHD = generatedKeys.getString(1); // Get the generated ID
            }

            // Thêm vào bảng LichSuHoaDon
            psInsertLSHD.setObject(1, idHD);
            psInsertLSHD.setObject(2, lshdm.getIdNV());
            psInsertLSHD.setObject(3, lshdm.getHanhDong());
            psInsertLSHD.setObject(4, lshdm.getDeleted());
            psInsertLSHD.setObject(5, lshdm.getCreatedAt());
            psInsertLSHD.setObject(6, lshdm.getCreatedBy());
            psInsertLSHD.setObject(7, lshdm.getUpdatedAt());
            psInsertLSHD.setObject(8, lshdm.getUpdateBy());
            psInsertLSHD.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

// Phương thức để đếm số lượng hóa đơn có trạng thái là 0
    public int countChuaThanhToan() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE Deleted = 1 AND TrangThai = 0";

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countDaThanhToan() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE Deleted = 1 AND TrangThai = 1";

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countChoGiaoHang() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE Deleted = 1 AND TrangThai = 3";

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countDangGiaoHang() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE Deleted = 1 AND TrangThai = 2";

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countAll() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon";

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public boolean deleteHD(String idHD) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[HoaDon]
                        SET [Deleted] = 0
                        WHERE ID = ?
                     """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean ThanhToanHD(HoaDon hd, List<HoaDonChiTietEntity> hdctList, String idHD, PhuongThucThanhToan pttt, HinhThucThanhToanEntity httte, LichSuHDModel lshdm) {
        int check = 0;

        String sqlUpdateHoaDon = """
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

        String sqlInsertHoaDonChiTiet = """
    INSERT INTO [dbo].[HoaDonChiTiet]
                (
                    [IDImel],
                    [IDCTSP],
                    [IDHoaDon],
                    [DonGia],
                    [Deleted],
                    [CreatedAt],
                    [CreatedBy],
                    [UpdatedAt],
                    [UpdatedBy]
                )
                VALUES
                (?,?,?,?,?,?,?,?,?);
    """;

        String sqlInsertPhuongThucThanhToan = """
    INSERT INTO [dbo].[PhuongThucThanhToan]
            ([TenKieuThanhToan]
            ,[Deleted]
            ,[CreatedAt]
            ,[CreatedBy]
            ,[UpdatedAt]
            ,[UpdatedBy])
    OUTPUT INSERTED.ID
    VALUES
            (?,?,?,?,?,?)
    """;

        String sqlInsertHinhThucThanhToan = """
    INSERT INTO [dbo].[HinhThucThanhToan]
            ([IDHoaDon]
            ,[IDPhuongThucThanhToan]
            ,[TienCK]
            ,[TienMat]
            ,[Deleted]
            ,[CreatedAt]
            ,[CreatedBy]
            ,[UpdatedAt]
            ,[UpdatedBy])
    VALUES
            (?,?,?,?,?,?,?,?,?)
    """;

        String sqlInsertLichSuHD = """
                               INSERT INTO [dbo].[LichSuHoaDon]
                                          ([IDHoaDon]
                                          ,[IDNhanVien]
                                          ,[HanhDong]
                                          ,[Deleted]
                                          ,[CreatedAt]
                                          ,[CreatedBy]
                                          ,[UpdatedAt]
                                          ,[UpdatedBy])
                                    VALUES
                                          (?,?,?,?,?,?,?,?)
                               """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement psUpdate = cnt.prepareStatement(sqlUpdateHoaDon);  PreparedStatement psInsert = cnt.prepareStatement(sqlInsertHoaDonChiTiet);  PreparedStatement psInsertPTTT = cnt.prepareStatement(sqlInsertPhuongThucThanhToan);  PreparedStatement psInsertHTTT = cnt.prepareStatement(sqlInsertHinhThucThanhToan);  PreparedStatement psInsertLSHD = cnt.prepareStatement(sqlInsertLichSuHD);) {

            cnt.setAutoCommit(false); // Start a transaction

            // Update the information of the invoice
            psUpdate.setObject(1, hd.getIdKH());
            psUpdate.setObject(2, hd.getIdNV());
            psUpdate.setObject(3, hd.getNgayTao());
            psUpdate.setObject(4, hd.getNgayThanhToan());
            psUpdate.setObject(5, hd.getTongTien());
            psUpdate.setObject(6, hd.getTongTienSauGiam());
            psUpdate.setObject(7, hd.getTenKH());
            psUpdate.setObject(8, hd.getSdtKH());
            psUpdate.setObject(9, hd.getDiaChiKH());
            psUpdate.setObject(10, 1);
            psUpdate.setObject(11, hd.getUpdateAt());
            psUpdate.setObject(12, hd.getUpdateBy());
            psUpdate.setObject(13, 1);
            psUpdate.setObject(14, idHD);
            psUpdate.addBatch(); // Add the update statement to the batch

            // Insert the details of the invoice
            for (HoaDonChiTietEntity hdct : hdctList) {
                // Add each item from gioHangList to the database
                psInsert.setObject(1, hdct.getIdImel());
                psInsert.setObject(2, hdct.getIdCtsp());
                psInsert.setObject(3, hdct.getIdHoaDon());
                psInsert.setObject(4, hdct.getDonGia());
                psInsert.setObject(5, 1);
                psInsert.setObject(6, hdct.getCreatedAt());
                psInsert.setObject(7, hdct.getCreatedBy());
                psInsert.setObject(8, hdct.getUpdatedAt());
                psInsert.setObject(9, hdct.getUpdateBy());
                psInsert.addBatch(); // Add the insert statement to the batch
            }

            psInsertPTTT.setObject(1, pttt.getTeKieuThanhToan());
            psInsertPTTT.setObject(2, 1);
            psInsertPTTT.setObject(3, pttt.getCcreateAt());
            psInsertPTTT.setObject(4, pttt.getCreateBy());
            psInsertPTTT.setObject(5, pttt.getUpdateAt());
            psInsertPTTT.setObject(6, pttt.getUpdateBy());
            ResultSet generatedKeys = psInsertPTTT.executeQuery(); // Retrieve the generated keys
            String idPTTT = null;
            if (generatedKeys.next()) {
                idPTTT = generatedKeys.getString(1); // Get the generated ID
            }

            psInsertHTTT.setObject(1, httte.getIdHoaDon());
            psInsertHTTT.setObject(2, idPTTT);
            psInsertHTTT.setObject(3, httte.getTienChuyenKhoan());
            psInsertHTTT.setObject(4, httte.getTienMat());
            psInsertHTTT.setObject(5, 1);
            psInsertHTTT.setObject(6, httte.getCreatedAt());
            psInsertHTTT.setObject(7, httte.getCreatedBy());
            psInsertHTTT.setObject(8, httte.getUpdatedAt());
            psInsertHTTT.setObject(9, httte.getUpdateBy());
            psInsertHTTT.addBatch();

            psInsertLSHD.setObject(1, lshdm.getIdHD());
            psInsertLSHD.setObject(2, lshdm.getIdNV());
            psInsertLSHD.setObject(3, lshdm.getHanhDong());
            psInsertLSHD.setObject(4, lshdm.getDeleted());
            psInsertLSHD.setObject(5, lshdm.getCreatedAt());
            psInsertLSHD.setObject(6, lshdm.getCreatedBy());
            psInsertLSHD.setObject(7, lshdm.getUpdatedAt());
            psInsertLSHD.setObject(8, lshdm.getUpdateBy());
            psInsertLSHD.addBatch();

            // Execute the batch for both update and insert
            int[] updateCounts = psUpdate.executeBatch();
            int[] insertCounts = psInsert.executeBatch();
            int[] insertPTTTCounts = psInsertPTTT.executeBatch();
            int[] insertHTTTCounts = psInsertHTTT.executeBatch();
            int[] inserLSHDCounts = psInsertLSHD.executeBatch();

            // Check the results of both batches
            for (int updateCount : updateCounts) {
                if (updateCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Update hoa don failed");
                }
                check += updateCount;
            }
            for (int insertCount : insertCounts) {
                if (insertCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert hoa don chi tiet failed");
                }
                check += insertCount;
            }

            for (int insertPTTTCount : insertPTTTCounts) {
                if (insertPTTTCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert phuong thuc thanh toan failed");
                }
                check += insertPTTTCount;
            }

            for (int insertHTTTCount : insertHTTTCounts) {
                if (insertHTTTCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert phuong thuc thanh toan failed");
                }
                check += insertHTTTCount;
            }

            for (int insertLSHDCount : inserLSHDCounts) {
                if (insertLSHDCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert Lich Su Hoa Don failed");
                }
                check += insertLSHDCount;
            }

            cnt.commit();
            updateSelectSPSauKhiTT(hdctList);
        } catch (Exception e) {
            e.printStackTrace();
            check = 0;
        }

        return check > 0;
    }

    public boolean GiaoHang(HoaDon hd, List<HoaDonChiTietEntity> hdctList, String idHD, PhuongThucThanhToan pttt, HinhThucThanhToanEntity httte, LichSuHDModel lshdm) {
        int check = 0;

        String sqlUpdateHoaDon = """
         UPDATE [dbo].[HoaDon]
                SET [IDKhachHang] = ?,
                    [IDNhanVien] = ?,
                    [NgayTao] = ?,
                    [NgayThanhToan] = ?,
                    [TongTien] = ?,
                    [TongTienSauGiam] = ?,
                    [TenKhachHang] = ?,
                    [SoDienThoaiKhachHang] = ?,
                    [DiaChiKhachHang] = ?,
                    [Deleted] = ?,
                    [UpdatedAt] = ?,
                    [UpdatedBy] = ?,
                    [TrangThai] = ?,
                    [NgayNhan] = ?,
                    [PhiShip] = ?
                WHERE ID = ?;
    """;

        String sqlInsertHoaDonChiTiet = """
        INSERT INTO [dbo].[HoaDonChiTiet]
                    (
                        [IDImel],
                        [IDCTSP],
                        [IDHoaDon],
                        [DonGia],
                        [Deleted],
                        [CreatedAt],
                        [CreatedBy],
                        [UpdatedAt],
                        [UpdatedBy]
                    )
                    VALUES
                    (?,?,?,?,?,?,?,?,?);
    """;

        String sqlInsertPhuongThucThanhToan = """
        INSERT INTO [dbo].[PhuongThucThanhToan]
                ([TenKieuThanhToan]
                ,[Deleted]
                ,[CreatedAt]
                ,[CreatedBy]
                ,[UpdatedAt]
                ,[UpdatedBy])
        OUTPUT INSERTED.ID
        VALUES
                (?,?,?,?,?,?)
""";

        String sqlInsertHinhThucThanhToan = """
        INSERT INTO [dbo].[HinhThucThanhToan]
                ([IDHoaDon]
                ,[IDPhuongThucThanhToan]
                ,[TienCK]
                ,[TienMat]
                ,[Deleted]
                ,[CreatedAt]
                ,[CreatedBy]
                ,[UpdatedAt]
                ,[UpdatedBy])
        VALUES
                (?,?,?,?,?,?,?,?,?)
    """;

        String sqlInsertLichSuHD = """
                                   INSERT INTO [dbo].[LichSuHoaDon]
                                              ([IDHoaDon]
                                              ,[IDNhanVien]
                                              ,[HanhDong]
                                              ,[Deleted]
                                              ,[CreatedAt]
                                              ,[CreatedBy]
                                              ,[UpdatedAt]
                                              ,[UpdatedBy])
                                        VALUES
                                              (?,?,?,?,?,?,?,?)
                                   """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement psUpdate = cnt.prepareStatement(sqlUpdateHoaDon);  PreparedStatement psInsert = cnt.prepareStatement(sqlInsertHoaDonChiTiet);  PreparedStatement psInsertPTTT = cnt.prepareStatement(sqlInsertPhuongThucThanhToan);  PreparedStatement psInsertHTTT = cnt.prepareStatement(sqlInsertHinhThucThanhToan);  PreparedStatement psInsertLSHD = cnt.prepareStatement(sqlInsertLichSuHD);) {

            cnt.setAutoCommit(false); // Start a transaction

            // Update the information of the invoice
            psUpdate.setObject(1, hd.getIdKH());
            psUpdate.setObject(2, hd.getIdNV());
            psUpdate.setObject(3, hd.getNgayTao());
            psUpdate.setObject(4, hd.getNgayThanhToan());
            psUpdate.setObject(5, hd.getTongTien());
            psUpdate.setObject(6, hd.getTongTienSauGiam());
            psUpdate.setObject(7, hd.getTenKH());
            psUpdate.setObject(8, hd.getSdtKH());
            psUpdate.setObject(9, hd.getDiaChiKH());
            psUpdate.setObject(10, 1);
            psUpdate.setObject(11, hd.getUpdateAt());
            psUpdate.setObject(12, hd.getUpdateBy());
            psUpdate.setObject(13, 2);
            psUpdate.setObject(14, hd.getNgayNhan());
            psUpdate.setObject(15, hd.getPhiShip());
            psUpdate.setObject(16, idHD);
            psUpdate.addBatch(); // Add the update statement to the batch

            // Insert the details of the invoice
            for (HoaDonChiTietEntity hdct : hdctList) {
                // Split idimel and idCTSP values
                psInsert.setObject(1, hdct.getIdImel());
                psInsert.setObject(2, hdct.getIdCtsp());
                psInsert.setObject(3, hdct.getIdHoaDon());
                psInsert.setObject(4, hdct.getDonGia());
                psInsert.setObject(5, 1);
                psInsert.setObject(6, hdct.getCreatedAt());
                psInsert.setObject(7, hdct.getCreatedBy());
                psInsert.setObject(8, hdct.getUpdatedAt());
                psInsert.setObject(9, hdct.getUpdateBy());
                psInsert.addBatch(); // Add the insert statement to the batch

            }

            psInsertPTTT.setObject(1, pttt.getTeKieuThanhToan());
            psInsertPTTT.setObject(2, 1);
            psInsertPTTT.setObject(3, pttt.getCcreateAt());
            psInsertPTTT.setObject(4, pttt.getCreateBy());
            psInsertPTTT.setObject(5, pttt.getUpdateAt());
            psInsertPTTT.setObject(6, pttt.getUpdateBy());
            ResultSet generatedKeys = psInsertPTTT.executeQuery(); // Retrieve the generated keys
            String idPTTT = null;
            if (generatedKeys.next()) {
                idPTTT = generatedKeys.getString(1); // Get the generated ID
            }

            psInsertHTTT.setObject(1, httte.getIdHoaDon());
            psInsertHTTT.setObject(2, idPTTT);
            psInsertHTTT.setObject(3, httte.getTienChuyenKhoan());
            psInsertHTTT.setObject(4, httte.getTienMat());
            psInsertHTTT.setObject(5, 1);
            psInsertHTTT.setObject(6, httte.getCreatedAt());
            psInsertHTTT.setObject(7, httte.getCreatedBy());
            psInsertHTTT.setObject(8, httte.getUpdatedAt());
            psInsertHTTT.setObject(9, httte.getUpdateBy());
            psInsertHTTT.addBatch();

            psInsertLSHD.setObject(1, lshdm.getIdHD());
            psInsertLSHD.setObject(2, lshdm.getIdNV());
            psInsertLSHD.setObject(3, lshdm.getHanhDong());
            psInsertLSHD.setObject(4, lshdm.getDeleted());
            psInsertLSHD.setObject(5, lshdm.getCreatedAt());
            psInsertLSHD.setObject(6, lshdm.getCreatedBy());
            psInsertLSHD.setObject(7, lshdm.getUpdatedAt());
            psInsertLSHD.setObject(8, lshdm.getUpdateBy());
            psInsertLSHD.addBatch();

            // Execute the batch for both update and insert
            int[] updateCounts = psUpdate.executeBatch();
            int[] insertCounts = psInsert.executeBatch();
            int[] insertPTTTCounts = psInsertPTTT.executeBatch();
            int[] insertHTTTCounts = psInsertHTTT.executeBatch();
            int[] inserLSHDCounts = psInsertLSHD.executeBatch();

            // Check the results of both batches
            for (int updateCount : updateCounts) {
                if (updateCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Update hoa don failed");
                }
                check += updateCount;
            }
            for (int insertCount : insertCounts) {
                if (insertCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert hoa don chi tiet failed");
                }
                check += insertCount;
            }

            for (int insertPTTTCount : insertPTTTCounts) {
                if (insertPTTTCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert phuong thuc thanh toan failed");
                }
                check += insertPTTTCount;
            }

            for (int insertHTTTCount : insertHTTTCounts) {
                if (insertHTTTCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert phuong thuc thanh toan failed");
                }
                check += insertHTTTCount;
            }

            for (int insertLSHDCount : inserLSHDCounts) {
                if (insertLSHDCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Insert Lich Su Hoa Don failed");
                }
                check += insertLSHDCount;
            }

            cnt.commit();
            updateSelectSPSauKhiTT(hdctList);
        } catch (Exception e) {
            e.printStackTrace();
            check = 0;
        }

        return check > 0;
    }

    public void updateSelectSPSauKhiTT(List<HoaDonChiTietEntity> hdctList) {
        String updateSql = "UPDATE dbo.ChiTietSP SET Deleted = 0 WHERE IDImel = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(updateSql)) {
            for (HoaDonChiTietEntity hdct : hdctList) {
                String idImel = hdct.getIdImel();
                ps.setString(1, idImel);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean HuyGiaoHang(String idHD) {
        String sql = """
                   -- Truy vấn UPDATE cho bảng HoaDon
                   UPDATE [dbo].[HoaDon]
                   SET [TrangThai] = 3
                   WHERE ID = ?
                   
                   -- Truy vấn UPDATE cho bảng ChiTietSP
                   UPDATE [dbo].[ChiTietSP]
                   SET [Deleted] = 1
                   WHERE ID IN (SELECT IDCTSP FROM HoaDonChiTiet WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE ID = ?))
                 """;
        int check = 0;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
            ps.setObject(2, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean GiaoLaiHang(String idHD) {
        String sql = """
                 UPDATE [dbo].[HoaDon]
                    SET [TrangThai] = 2 
                  WHERE ID = ?
                     
                -- Truy vấn UPDATE cho bảng ChiTietSP
                UPDATE [dbo].[ChiTietSP]
                SET [Deleted] = 0
                WHERE ID IN (SELECT IDCTSP FROM HoaDonChiTiet WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE ID = ?))
                 """;
        int check = 0;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
            ps.setObject(2, idHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean ThanhToanGiaoHangHD(String idHD) {
        String sql = """
                 UPDATE [dbo].[HoaDon]
                    SET [TrangThai] = 1 
                  WHERE ID = ?
                 """;
        int check = 0;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
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

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
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
                WITH Price_Count AS (
                    SELECT
                        IDDongSP,
                        GiaBan,
                        COUNT(*) AS SoLuongGia
                    FROM
                        dbo.ChiTietSP
                    WHERE
                        Deleted = 1
                    GROUP BY
                        IDDongSP,
                        GiaBan
                )
                SELECT
                    DS.ID,
                    DS.TenDsp,
                    NSX.TenNsx,
                    ManHinh.LoaiManHinh,
                    CPU.CPU,
                    Pin.DungLuongPin,
                    PC.GiaBan,
                    COUNT(PC.SoLuongGia) AS SoLuongGia
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
                    Price_Count AS PC ON CTS.IDDongSP = PC.IDDongSP AND CTS.GiaBan = PC.GiaBan
                WHERE 
                    CTS.Deleted = 1
                GROUP BY
                    DS.ID,
                    DS.TenDsp,
                    NSX.TenNsx,
                    ManHinh.LoaiManHinh,
                    CPU.CPU,
                    Pin.DungLuongPin,
                    PC.GiaBan
                ORDER BY 
                    DS.ID DESC;
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

    public List<ChiTietSanPhamViewModel> LocSP(String Nsx, String Pin, String ManHinh, String Cpu) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = "WITH Price_Count AS ("
                + "SELECT "
                + "IDDongSP, "
                + "GiaBan, "
                + "COUNT(*) AS SoLuongGia "
                + "FROM "
                + "dbo.ChiTietSP "
                + "WHERE "
                + "Deleted = 1 "
                + "GROUP BY "
                + "IDDongSP, "
                + "GiaBan"
                + ")"
                + "SELECT "
                + "DS.ID, "
                + "DS.TenDsp, "
                + "NSX.TenNsx, "
                + "ManHinh.LoaiManHinh, "
                + "CPU.CPU, "
                + "Pin.DungLuongPin, "
                + "PC.GiaBan, "
                + "COUNT(PC.SoLuongGia) AS SoLuongGia "
                + "FROM "
                + "dbo.ChiTietSP AS CTS "
                + "INNER JOIN "
                + "dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID "
                + "INNER JOIN "
                + "dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID "
                + "INNER JOIN "
                + "dbo.Pin ON CTS.IDPin = Pin.ID "
                + "INNER JOIN "
                + "dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID "
                + "INNER JOIN "
                + "dbo.CPU ON CTS.IDCPU = CPU.ID "
                + "INNER JOIN "
                + "Price_Count AS PC ON CTS.IDDongSP = PC.IDDongSP AND CTS.GiaBan = PC.GiaBan "
                + "WHERE "
                + "CTS.Deleted = 1 ";

        // Thêm điều kiện lọc vào câu SQL
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (Nsx != null && !Nsx.isEmpty()) {
            conditions.add("NSX.TenNsx LIKE ?");
            params.add("%" + Nsx + "%");
        }
        if (Pin != null && !Pin.isEmpty()) {
            conditions.add("Pin.DungLuongPin LIKE ?");
            params.add("%" + Pin + "%");
        }
        if (ManHinh != null && !ManHinh.isEmpty()) {
            conditions.add("ManHinh.LoaiManHinh LIKE ?");
            params.add("%" + ManHinh + "%");
        }
        if (Cpu != null && !Cpu.isEmpty()) {
            conditions.add("CPU.CPU LIKE ?");
            params.add("%" + Cpu + "%");
        }

        if (!conditions.isEmpty()) {
            sql += "AND (" + String.join(" AND ", conditions) + ") ";
        }

        sql += "GROUP BY "
                + "DS.ID, "
                + "DS.TenDsp, "
                + "NSX.TenNsx, "
                + "ManHinh.LoaiManHinh, "
                + "CPU.CPU, "
                + "Pin.DungLuongPin, "
                + "PC.GiaBan "
                + "ORDER BY "
                + "DS.ID DESC;";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

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

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
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

    public List<ChiTietSanPhamViewModel> UpdateLaiSPSauKhiTT(String imel) {
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
                         CTS.Deleted = 2 AND Imel.Imel = ?
                     ORDER BY 
                         ID DESC;
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
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

    public List<ChiTietSanPhamViewModel> getSPTuHoaDon(String idHD) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
                     WITH Price_Count_CTSP AS (
                                                                      SELECT
                                                                          IDDongSP,
                                                                          GiaBan,
                                                                          COUNT(*) AS TongSoLuong
                                                                      FROM
                                                                          dbo.ChiTietSP
                                                                      WHERE
                                                                          Deleted = 1
                                                                      GROUP BY
                                                                          IDDongSP,
                                                                          GiaBan
                                                                  )
                                                                  SELECT
                                                                      DS.TenDSP,
                                                                      Pin.DungLuongPin,
                                                                      ManHinh.LoaiManHinh,
                                                                      CPU.CPU,
                                                                      Ram.DungLuongRam,
                                                                      BoNho.DungLuongBoNho,
                                                                      MauSac.TenMau,
                                                                      PC.GiaBan,
                                                                      DS.ID,
                                                                      PC.IDDongSP,
                                                                      COUNT(PC.TongSoLuong) AS SoLuong  -- Sử dụng hàm tổng hợp SUM để tính tổng số lượng
                                                                  FROM
                                                                      dbo.ChiTietSP CTS
                                                                  JOIN
                                                                      dbo.NhaSanXuat NSX ON CTS.IDNSX = NSX.ID
                                                                  JOIN
                                                                      dbo.DongSP DS ON CTS.IDDongSP = DS.ID
                                                                  JOIN
                                                                      dbo.Pin ON CTS.IDPin = Pin.ID
                                                                  JOIN
                                                                      dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                                                                  JOIN
                                                                      dbo.CPU ON CTS.IDCPU = CPU.ID
                                                                  JOIN
                                                                      dbo.Ram ON CTS.IDRam = Ram.ID
                                                                  JOIN
                                                                      dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                                                                  JOIN
                                                                      dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                                                                  INNER JOIN 
                                                                      Price_Count_CTSP AS PC ON CTS.IDDongSP = PC.IDDongSP AND CTS.GiaBan = PC.GiaBan
                                                                  WHERE
                                                                      CTS.ID IN (SELECT IDCTSP FROM HoaDonChiTiet WHERE IDHoaDon = ?)
                                                                  GROUP BY 
                                                                      DS.TenDSP,
                                                                      Pin.DungLuongPin,
                                                                      ManHinh.LoaiManHinh,
                                                                      CPU.CPU,
                                                                      Ram.DungLuongRam,
                                                                      BoNho.DungLuongBoNho,
                                                                      MauSac.TenMau,
                                                                      PC.GiaBan,
                                                                      DS.ID,
                                                                      PC.IDDongSP
                                                                  ORDER BY
                                                                      DS.ID DESC
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idHD);
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
                spvm.setSoLuong(rs.getInt(12));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> deleteGioHang(List<String> imels) {
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
                     CTS.Deleted = 2 AND Imel.Imel = ?
                 ORDER BY 
                     ID DESC;
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            for (String imel : imels) {
                ps.setString(1, imel);
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
            }
            // Gọi phương thức updateDeleteSP sau khi truy vấn cho tất cả các Imel
            updateDeleteSP(imels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> selectIdDSP(String idDsp, BigDecimal giaBan) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
                     SELECT
                             CTS.ID,
                             CTS.IDDongSP,
                             DS.TenDsp,
                             Imel.Imel,
                             CTS.IDImel
                         FROM
                             dbo.ChiTietSP AS CTS
                             INNER JOIN dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                             INNER JOIN dbo.Imel ON CTS.IDImel = Imel.ID
                         WHERE
                             CTS.Deleted = 1
                             AND DS.ID = (SELECT TOP 1 ID FROM DongSP WHERE TenDsp = ?)
                             AND GiaBan = ?
                         ORDER BY
                             CTS.ID DESC;
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ps.setBigDecimal(2, giaBan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setId(rs.getString(1));
                spvm.setIdDsp(rs.getString(2));
                spvm.setTenDsp(rs.getString(3));
                spvm.setImel(rs.getString(4));
                spvm.setIdimel(rs.getString(5));
                listSP.add(spvm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> searchBySelectDsp(String keyword, List<ChiTietSanPhamViewModel> listSpSelect) {
        List<ChiTietSanPhamViewModel> resultList = new ArrayList<>();

        // Iterate over the listSpSelect and filter based on the keyword
        for (ChiTietSanPhamViewModel sp : listSpSelect) {
            if (sp.getId().contains(keyword)
                    || sp.getTenDsp().contains(keyword)
                    || sp.getImel().contains(keyword)
                    || sp.getIdimel().contains(keyword)) {
                resultList.add(sp);
            }
        }

        return resultList;
    }

    public List<String> getIdCtspQR(String Imel) {
        List<String> idList = new ArrayList<>();

        String sql = """
                 SELECT ID FROM ChiTietSP WHERE IDImel = (SELECT ID FROM Imel WHERE Imel = ?)
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Imel);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Thêm ID vào danh sách chuỗi
                idList.add(rs.getString("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }

    public List<ChiTietSanPhamViewModel> deleteIdDSP(String idDsp, BigDecimal giaBan) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
                     SELECT
                         CTS.ID,
                         CTS.IDDongSP,
                         DS.TenDsp,
                         Imel.Imel,
                         CTS.IDImel
                     FROM
                         dbo.ChiTietSP AS CTS
                         INNER JOIN dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                         INNER JOIN dbo.Imel ON CTS.IDImel = Imel.ID
                     WHERE
                         CTS.Deleted = 2 
                         AND DS.ID = (SELECT TOP 1 ID FROM DongSP WHERE TenDsp = ?)
                         AND GiaBan = ?
                     ORDER BY
                         CTS.ID DESC;
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ps.setBigDecimal(2, giaBan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setId(rs.getString(1));
                spvm.setIdDsp(rs.getString(2));
                spvm.setTenDsp(rs.getString(3));
                spvm.setImel(rs.getString(4));
                spvm.setIdimel(rs.getString(5));
                listSP.add(spvm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public void updateSelectSP(String imel) {
        String updateSql = "UPDATE dbo.ChiTietSP SET Deleted = 2 WHERE IDImel = (SELECT ID FROM dbo.Imel WHERE Imel = ?)";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setString(1, imel);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDeleteSP(List<String> imels) {
        // Xây dựng chuỗi 'IN' parameter từ danh sách imels
        String inParams = String.join(",", Collections.nCopies(imels.size(), "?"));

        // Tạo câu lệnh SQL với IN và PreparedStatement
        String updateSql = "UPDATE dbo.ChiTietSP SET Deleted = 1 WHERE IDImel IN (SELECT ID FROM dbo.Imel WHERE Imel IN (" + inParams + "))";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(updateSql)) {
            // Gán giá trị từ mảng imels vào câu lệnh SQL
            for (int i = 0; i < imels.size(); i++) {
                ps.setString(i + 1, imels.get(i));
            }
            // Thực thi câu lệnh SQL
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateDeleteHD(String idHD) {
        String updateSql = """
                           UPDATE [dbo].[HoaDon]
                              SET [Deleted] = 0
                            WHERE ID = ?
                           """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setObject(1, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

}
