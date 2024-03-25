/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.PhieuGiamGiaRepo;

import java.util.ArrayList;
import java.util.List;
import mobileworld.entity.PhieuGiamGia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import mobileworld.config.DBConnect;

/**
 *
 * @author Admin
 */
public class PhieuGiamGiaRepository {

    public List<PhieuGiamGia> getAll() {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """
    SELECT [TenGiamGia]
                                              ,[Số lượng dùng]
                                              ,[PhanTramGiam]
                                              ,[SoTienGiamToiDa]
                                              ,[HoaDonToiThieu]
                                              ,[NgayBatDau]
                                              ,[NgayKetThuc]
                                              ,[TrangThai]
                                              ,[MoTa]
                                              ,[Deleted]
                                               ,[CreatedAt]
                                                    ,[CreatedBy]
                                                    ,[UpdatedAt]
                                                    ,[UpdatedBy]
                                                    ,[ID]
                                          FROM [dbo].[PhieuGiamGia] order by [CreatedAt] DESC
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean addData(PhieuGiamGia gg) {
        int check = 0;
        String sql = """
                   INSERT INTO [dbo].[PhieuGiamGia]
                              ([TenGiamGia]
                              ,[Số lượng dùng]
                              ,[PhanTramGiam]
                              ,[SoTienGiamToiDa]
                              ,[HoaDonToiThieu]
                              ,[NgayBatDau]
                              ,[NgayKetThuc]
                              ,[TrangThai]
                              ,[MoTa]
                              ,[Deleted]
                              ,[CreatedAt]
                                    ,[CreatedBy]
                                    ,[UpdatedAt]
                                    ,[UpdatedBy]
                              )
                        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)                            
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, gg.getTenGiamGia());
            ps.setObject(2, gg.getSoLuongDung());
            ps.setObject(3, gg.getPhanTramGiam());
            ps.setObject(4, gg.getSoTiemGiamToiDa());
            ps.setObject(5, gg.getHoatDonToiThieu());
            ps.setObject(6, gg.getNgayBatDau());
            ps.setObject(7, gg.getNgayKetThuc());
            ps.setObject(8, gg.getTrangThai());
            ps.setObject(9, gg.getMoTa());
            ps.setObject(10, gg.getDeleted());
            ps.setObject(11, gg.getCreatedAt());
            ps.setObject(12, gg.getCreatedBy());
            ps.setObject(13, gg.getUpdatedAt());
            ps.setObject(14, gg.getUpdatedBy());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean update(PhieuGiamGia gg, String ID) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[PhieuGiamGia]
                                      SET [TenGiamGia] = ?
                                         ,[Số lượng dùng] = ?
                                         ,[PhanTramGiam] = ?
                                         ,[SoTienGiamToiDa] = ?
                                         ,[HoaDonToiThieu] = ?
                                         ,[NgayBatDau] = ?
                                         ,[NgayKetThuc] = ?
                                         ,[TrangThai] = ?
                                         ,[MoTa] = ?
                                         ,[Deleted] = ?
                                         ,[UpdatedAt] = ?
                                         ,[UpdatedBy] = ?
                                    WHERE ID=?
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, gg.getTenGiamGia());
            ps.setObject(2, gg.getSoLuongDung());
            ps.setObject(3, gg.getPhanTramGiam());
            ps.setObject(4, gg.getSoTiemGiamToiDa());
            ps.setObject(5, gg.getHoatDonToiThieu());
            ps.setObject(6, gg.getNgayBatDau());
            ps.setObject(7, gg.getNgayKetThuc());
            ps.setObject(8, gg.getTrangThai());
            ps.setObject(9, gg.getMoTa());
            ps.setObject(10, gg.getDeleted());
            ps.setObject(11, gg.getUpdatedAt());
            ps.setObject(12, gg.getUpdatedBy());
            ps.setObject(13, ID);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteData(String id, int tt, String maNV, LocalDateTime ngayCN) {
        int check = 0;
        String sql = """
        UPDATE dbo.PhieuGiamGia
                SET Deleted = 
                    CASE 
                        WHEN Deleted = 1 THEN 0
                        WHEN Deleted = 0 THEN 1
                    END
        			WHERE ID = ?;
                     
        UPDATE dbo.PhieuGiamGia
                SET
                    TrangThai = 
                    CASE 
                        WHEN Deleted = 0 THEN 0
                        WHEN Deleted = 1 THEN ?                        
                    END
                WHERE ID = ?;
        UPDATE dbo.PhieuGiamGia set 
                     [UpdatedAt] = ?
                     ,[UpdatedBy] = ?
                     where ID=?
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.setObject(2, tt);
            ps.setObject(3, id);
            ps.setObject(4, ngayCN);
            ps.setObject(5, maNV);
            ps.setObject(6, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<PhieuGiamGia> timTrangThai(int trangThai) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """
                        SELECT [TenGiamGia]
                         ,[Số lượng dùng]
                         ,[PhanTramGiam]
                         ,[SoTienGiamToiDa]
                         ,[HoaDonToiThieu]
                         ,[NgayBatDau]
                         ,[NgayKetThuc]
                         ,[TrangThai]
                         ,[MoTa]
                         ,[Deleted]
                         ,[CreatedAt]
                         ,[CreatedBy]
                         ,[UpdatedAt]
                         ,[UpdatedBy]
                         ,[ID]
                     FROM [dbo].[PhieuGiamGia]where TrangThai = ?  
                                          order by [CreatedAt] DESC			
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemAll(String All) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                     ,[CreatedAt]
                                                         ,[CreatedBy]
                                                         ,[UpdatedAt]
                                                         ,[UpdatedBy]
                     ,[ID]
                     FROM [dbo].[PhieuGiamGia]  
                     where TenGiamGia like '%'+?+'%' Or [Số lượng dùng] like '%'+?+'%'  OR PhanTramGiam like ?
                     or SoTienGiamToiDa like '%'+?+'%' or HoaDonToiThieu like '%'+?+'%' or NgayBatDau like '%' + ?+ '%'
                     or NgayKetThuc like '%' + ?+ '%' or Id like '%'+?+'%'
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, All);
            ps.setObject(2, All);
            ps.setObject(3, All);
            ps.setObject(4, All);
            ps.setObject(5, All);
            ps.setObject(6, All);
            ps.setObject(7, All);
            ps.setObject(8, All);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemID(String tkID) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                       ,[CreatedAt]
                                                                              ,[CreatedBy]
                                                                              ,[UpdatedAt]
                                                                              ,[UpdatedBy]
                     ,[ID]
                     FROM [dbo].[PhieuGiamGia]  
                     where ID like '%'+?+'%' 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tkID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemHoaDonTT(Float tkHD) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                     ,[CreatedAt]
                     ,[CreatedBy]
                     ,[UpdatedAt]
                     ,[UpdatedBy]
                     ,[ID]
                     FROM [dbo].[PhieuGiamGia]  
                     where ?<=HoaDonToiThieu
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tkHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemTenGG(String tkTen) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                    ,[CreatedAt]
                                        ,[CreatedBy]
                                        ,[UpdatedAt]
                                        ,[UpdatedBy]                   
                     ,[ID]
                     FROM [dbo].[PhieuGiamGia]  
                     where TenGiamGia like '%'+?+'%' 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tkTen);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemTienGiam(Float tkTM) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                      ,[CreatedAt]
                                                               ,[CreatedBy]
                                                               ,[UpdatedAt]
                                                               ,[UpdatedBy]
                     ,[ID]
                     FROM [dbo].[PhieuGiamGia]  
                     where SoTienGiamToiDa = ? 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tkTM);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemNgay(LocalDate ngayBD, LocalDate ngayKT) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                     ,[CreatedAt]
                                          ,[CreatedBy]
                                          ,[UpdatedAt]
                                          ,[UpdatedBy]
                     ,[ID]
                      FROM [dbo].[PhieuGiamGia]  
                      where NgayBatDau between ? And ?
                      and NgayKetThuc between  ? And ?                                          					 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ngayBD);
            ps.setObject(2, ngayKT);
            ps.setObject(3, ngayBD);
            ps.setObject(4, ngayKT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemSoLan(int soLan) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                      ,[CreatedAt]
                                          ,[CreatedBy]
                                          ,[UpdatedAt]
                                          ,[UpdatedBy]
                     ,[ID]
                      FROM [dbo].[PhieuGiamGia]  
                      where [Số lượng dùng] = ?                                    					 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, soLan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<PhieuGiamGia> timKiemPhanTram(float phanTram) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,[Số lượng dùng]
                     ,[PhanTramGiam]
                     ,[SoTienGiamToiDa]
                     ,[HoaDonToiThieu]
                     ,[NgayBatDau]
                     ,[NgayKetThuc]
                     ,[TrangThai]
                     ,[MoTa]
                     ,[Deleted]
                      ,[CreatedAt]
                                          ,[CreatedBy]
                                          ,[UpdatedAt]
                                          ,[UpdatedBy]
                     ,[ID]
                      FROM [dbo].[PhieuGiamGia]  
                      where PhanTramGiam = ?                                    					 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, phanTram);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia gg = new PhieuGiamGia();
                gg.setTenGiamGia(rs.getString(1));
                gg.setSoLuongDung(rs.getInt(2));
                gg.setPhanTramGiam(rs.getFloat(3));
                gg.setSoTiemGiamToiDa(rs.getFloat(4));
                gg.setHoatDonToiThieu(rs.getFloat(5));
                gg.setNgayBatDau(rs.getTimestamp(6).toLocalDateTime());
                gg.setNgayKetThuc(rs.getTimestamp(7).toLocalDateTime());
                gg.setTrangThai(rs.getInt(8));
                gg.setMoTa(rs.getString(9));
                gg.setDeleted(rs.getInt(10));
                gg.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                gg.setCreatedBy(rs.getString(12));
                gg.setUpdatedAt(rs.getTimestamp(13).toLocalDateTime());
                gg.setUpdatedBy(rs.getString(14));
                gg.setID(rs.getNString(15));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean updateTTThread(String ID, int trangThai, float Deleted) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[PhieuGiamGia]
                                      SET [TrangThai] = ?
                                      ,[Deleted] =?                 
                                    WHERE ID=?
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, trangThai);
            ps.setObject(2, Deleted);
            ps.setObject(3, ID);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

}
