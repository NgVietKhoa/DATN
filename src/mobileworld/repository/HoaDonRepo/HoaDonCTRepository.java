package mobileworld.repository.HoaDonRepo;

import java.util.ArrayList;
import java.util.List;
import mobileworld.viewModel.HoaDonChiTietModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mobileworld.config.DBConnect;

public class HoaDonCTRepository {

    public List<HoaDonChiTietModel> getAll(String idHD) {
        List<HoaDonChiTietModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                                               dbo.HoaDon.ID,
                                               dbo.HoaDonChiTiet.ID, 
                                               dbo.ChiTietSP.ID, 
                                               dbo.DongSP.TenDsp, 
                                               dbo.NhaSanXuat.TenNsx, 
                                               dbo.MauSac.TenMau, 
                                               dbo.Pin.DungLuongPin, 
                                               dbo.Imel.Imel, 
                                               dbo.ChiTietSP.GiaBan, 
                                               dbo.ChiTietSP.GiaBan
                                           FROM   
                                               dbo.HoaDon
                                           INNER JOIN
                                               dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
                                           INNER JOIN
                                               dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID
                                           INNER JOIN
                                               dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID
                                           INNER JOIN
                                               dbo.NhaSanXuat ON dbo.ChiTietSP.IDNSX = dbo.NhaSanXuat.ID
                                           INNER JOIN
                                               dbo.MauSac ON dbo.ChiTietSP.IDMauSac = dbo.MauSac.ID
                                           INNER JOIN
                                               dbo.Pin ON dbo.ChiTietSP.IDPin = dbo.Pin.ID
                                           INNER JOIN
                                               dbo.Imel ON dbo.HoaDonChiTiet.IDImel = dbo.Imel.ID
                                           WHERE dbo.HoaDonChiTiet.IDHoaDon= ? 
                     """;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTietModel hdctm = new HoaDonChiTietModel();
                hdctm.setIdHD(rs.getString(1));
                hdctm.setIdHDinHDCT(rs.getString(2));
                hdctm.setIdCTSP(rs.getString(3));
                hdctm.setTenDSP(rs.getString(4));
                hdctm.setTenNSX(rs.getString(5));
                hdctm.setTenMau(rs.getString(6));
                hdctm.setDungLuongPin(rs.getString(7));
                hdctm.setImel(rs.getString(8));
                hdctm.setGiaBan(rs.getBigDecimal(9));
                hdctm.setTongTien(rs.getBigDecimal(10));
                list.add(hdctm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonChiTietModel> getAll(String idHD, String tenNSX, String tenMau, String dungLuongPin) {
        List<HoaDonChiTietModel> list = new ArrayList<>();

        // Câu truy vấn SQL mặc định
        String sql = """
              SELECT 
                  dbo.HoaDon.ID,
                                                                 dbo.HoaDonChiTiet.ID, 
                                                                 dbo.ChiTietSP.ID, 
                                                                 dbo.DongSP.TenDsp, 
                                                                 dbo.NhaSanXuat.TenNsx, 
                                                                 dbo.MauSac.TenMau, 
                                                                 dbo.Pin.DungLuongPin, 
                                                                 dbo.Imel.Imel, 
                                                                 dbo.ChiTietSP.GiaBan, 
                                                                 dbo.ChiTietSP.GiaBan
              FROM   
                  dbo.HoaDon
              INNER JOIN
                  dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
              INNER JOIN
                  dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID
              INNER JOIN
                  dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID
              INNER JOIN
                  dbo.NhaSanXuat ON dbo.ChiTietSP.IDNSX = dbo.NhaSanXuat.ID
              INNER JOIN
                  dbo.MauSac ON dbo.ChiTietSP.IDMauSac = dbo.MauSac.ID
              INNER JOIN
                  dbo.Pin ON dbo.ChiTietSP.IDPin = dbo.Pin.ID
              INNER JOIN
                  dbo.Imel ON dbo.HoaDonChiTiet.IDImel = dbo.Imel.ID
              WHERE 
                  dbo.HoaDonChiTiet.IDHoaDon = ? 
             """;

        // Xây dựng điều kiện cho câu truy vấn SQL dựa trên dữ liệu nhập vào
        StringBuilder condition = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(idHD);

        // Thêm điều kiện nếu có dữ liệu nhập vào từ các trường tenNSX, tenMau, dungLuongPin
        if (tenNSX != null && !tenNSX.isEmpty()) {
            condition.append("AND dbo.NhaSanXuat.TenNsx = ? ");
            params.add(tenNSX);
        }
        if (tenMau != null && !tenMau.isEmpty()) {
            condition.append("AND dbo.MauSac.TenMau = ? ");
            params.add(tenMau);
        }
        if (dungLuongPin != null && !dungLuongPin.isEmpty()) {
            condition.append("AND dbo.Pin.DungLuongPin = ? ");
            params.add(dungLuongPin);
        }

        // Ghép điều kiện vào câu truy vấn SQL nếu có điều kiện được thêm vào
        if (condition.length() > 0) {
            sql += condition.toString();
        }

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            // Gán các tham số vào câu truy vấn SQL
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTietModel hdctm = new HoaDonChiTietModel();
                hdctm.setIdHD(rs.getString(1));
                hdctm.setIdHDinHDCT(rs.getString(2));
                hdctm.setIdCTSP(rs.getString(3));
                hdctm.setTenDSP(rs.getString(4));
                hdctm.setTenNSX(rs.getString(5));
                hdctm.setTenMau(rs.getString(6));
                hdctm.setDungLuongPin(rs.getString(7));
                hdctm.setImel(rs.getString(8));
                hdctm.setGiaBan(rs.getBigDecimal(9));
                hdctm.setTongTien(rs.getBigDecimal(10));
                list.add(hdctm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonChiTietModel> searchAllFields(String idHD, String searchText) {
        List<HoaDonChiTietModel> list = new ArrayList<>();

        // Câu truy vấn SQL mặc định
        String sql = """
          SELECT 
              dbo.HoaDon.ID,
                                                             dbo.HoaDonChiTiet.ID, 
                                                             dbo.ChiTietSP.ID, 
                                                             dbo.DongSP.TenDsp, 
                                                             dbo.NhaSanXuat.TenNsx, 
                                                             dbo.MauSac.TenMau, 
                                                             dbo.Pin.DungLuongPin, 
                                                             dbo.Imel.Imel, 
                                                             dbo.ChiTietSP.GiaBan, 
                                                             dbo.ChiTietSP.GiaBan
          FROM   
              dbo.HoaDon
          INNER JOIN
              dbo.HoaDonChiTiet ON dbo.HoaDon.ID = dbo.HoaDonChiTiet.IDHoaDon
          INNER JOIN
              dbo.ChiTietSP ON dbo.HoaDonChiTiet.IDCTSP = dbo.ChiTietSP.ID
          INNER JOIN
              dbo.DongSP ON dbo.ChiTietSP.IDDongSP = dbo.DongSP.ID
          INNER JOIN
              dbo.NhaSanXuat ON dbo.ChiTietSP.IDNSX = dbo.NhaSanXuat.ID
          INNER JOIN
              dbo.MauSac ON dbo.ChiTietSP.IDMauSac = dbo.MauSac.ID
          INNER JOIN
              dbo.Pin ON dbo.ChiTietSP.IDPin = dbo.Pin.ID
          INNER JOIN
              dbo.Imel ON dbo.HoaDonChiTiet.IDImel = dbo.Imel.ID
          WHERE 
              dbo.HoaDonChiTiet.IDHoaDon LIKE ? AND
              (dbo.DongSP.TenDsp LIKE ? OR
              dbo.NhaSanXuat.TenNsx LIKE ? OR
              dbo.MauSac.TenMau LIKE ? OR
              dbo.Pin.DungLuongPin LIKE ? OR
              dbo.Imel.Imel LIKE ? OR
              dbo.ChiTietSP.ID LIKE ?);
         """;

        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idHD);
            String searchParam = "%" + searchText + "%";
            for (int i = 2; i <= 7; i++) {
                ps.setString(i, searchParam);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTietModel hdctm = new HoaDonChiTietModel();
                hdctm.setIdHD(rs.getString(1));
                hdctm.setIdHDinHDCT(rs.getString(2));
                hdctm.setIdCTSP(rs.getString(3));
                hdctm.setTenDSP(rs.getString(4));
                hdctm.setTenNSX(rs.getString(5));
                hdctm.setTenMau(rs.getString(6));
                hdctm.setDungLuongPin(rs.getString(7));
                hdctm.setImel(rs.getString(8));
                hdctm.setGiaBan(rs.getBigDecimal(9));
                hdctm.setTongTien(rs.getBigDecimal(10));
                list.add(hdctm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
