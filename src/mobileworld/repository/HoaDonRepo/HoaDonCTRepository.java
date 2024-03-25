/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.HoaDonRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mobileworld.viewModel.HoaDonChiTietModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mobileworld.config.DBConnect;

/**
 *
 * @author ADMIN
 */
public class HoaDonCTRepository {
    
    public List<HoaDonChiTietModel> getAll(String idHD) {
        List<HoaDonChiTietModel> list = new ArrayList<>();
        String sql = """
                      SELECT 
                          dbo.HoaDon.ID AS [ID Hóa Đơn], 
                          dbo.HoaDonChiTiet.ID AS [ID Hóa Đơn Chi Tiết], 
                          dbo.ChiTietSP.ID AS [ID Chi Tiết SP], 
                          dbo.DongSP.TenDsp AS [Tên Dòng SP], 
                          dbo.NhaSanXuat.[TenNsx] AS [Tên Nhà Sản Xuất], 
                          dbo.MauSac.TenMau AS [Tên Màu Sắc], 
                          dbo.Pin.DungLuongPin AS [Dung Lượng Pin], 
                          dbo.Imel.Imel AS [Imel], 
                          dbo.ChiTietSP.GiaBan AS [Giá Bán], 
                          dbo.HoaDonChiTiet.DonGia AS [Đơn Giá]
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
        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
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

//    public List<HoaDonChiTietModel> getAll(String idHD) {
//        List<HoaDonChiTietModel> list = new ArrayList<>();
//        String sql = """
//                       SELECT 
//                         dbo.HoaDon.ID,
//                         dbo.HoaDonChiTiet.IDHoaDon,
//                         dbo.ChiTietSP.ID, 
//                         dbo.DongSP.[Tên DSP], 
//                         dbo.NhaSanXuat.[Tên NSX], 
//                         dbo.MauSac.TenMau, 
//                         dbo.Pin.DungLuongPin, 
//                         dbo.ChiTietSP.Imel, 
//                         COUNT(dbo.ChiTietSP.ID) AS 'Số lượng', 
//                         dbo.ChiTietSP.[Giá bán], 
//                         dbo.ChiTietSP.[Giá bán] * COUNT(dbo.ChiTietSP.ID) AS 'Tổng tiền'
//                     FROM   
//                         dbo.DongSP 
//                     INNER JOIN
//                         dbo.ChiTietSP ON dbo.DongSP.ID = dbo.ChiTietSP.IDDSP 
//                     INNER JOIN
//                         dbo.HoaDonChiTiet ON dbo.ChiTietSP.ID = dbo.HoaDonChiTiet.IDCTSP 
//                     INNER JOIN
//                         dbo.HoaDon ON dbo.HoaDonChiTiet.IDHoaDon = dbo.HoaDon.ID 
//                     INNER JOIN
//                         dbo.MauSac ON dbo.ChiTietSP.IDMauSac = dbo.MauSac.ID 
//                     INNER JOIN
//                         dbo.NhaSanXuat ON dbo.ChiTietSP.IDNSX = dbo.NhaSanXuat.ID 
//                     INNER JOIN
//                         dbo.Pin ON dbo.ChiTietSP.IDPin = dbo.Pin.ID 
//                     WHERE dbo.HoaDonChiTiet.IDHoaDon= ? 
//                     GROUP BY 
//                         dbo.DongSP.[Tên DSP], 
//                         dbo.ChiTietSP.[Giá bán], 
//                         dbo.MauSac.TenMau, 
//                         dbo.NhaSanXuat.[Tên NSX], 
//                         dbo.ChiTietSP.Imel, 
//                         dbo.HoaDon.ID, 
//                         dbo.ChiTietSP.ID, 
//                         dbo.HoaDonChiTiet.IDHoaDon, 
//                         dbo.Pin.DungLuongPin  
//                     
//                     """;
//        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
//            ps.setObject(1, idHD);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                HoaDonChiTietModel hdctm = new HoaDonChiTietModel();
//                hdctm.setIdHD(rs.getString(1));
//                hdctm.setIdHDinHDCT(rs.getString(2));
//                hdctm.setIdCTSP(rs.getString(3));
//                hdctm.setTenDSP(rs.getString(4));
//                hdctm.setTenNSX(rs.getString(5));
//                hdctm.setTenMau(rs.getString(6));
//                hdctm.setDungLuongPin(rs.getString(7));
//                hdctm.setImel(rs.getString(8));
//                hdctm.setSoLuong(rs.getInt(9));
//                hdctm.setGiaBan(rs.getBigDecimal(10));
//                hdctm.setTongTien(rs.getBigDecimal(11));
//                list.add(hdctm);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//    public static void main(String[] args) {
//        List<HoaDonChiTietModel> list1 = new HoaDonCTRepository().getAll();
//        for (HoaDonChiTietModel hoaDonChiTietModel : list1) {
//            System.out.println(hoaDonChiTietModel.toString());
//        }
//    }
}
