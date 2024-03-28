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
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mobileworld.config.DBConnect;
import mobileworld.model.KhachHang;

/**
 *
 * @author Admin
 */
public class PhieuGiamGiaRepository {

      public List<PhieuGiamGia> getAll() {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """
    SELECT [TenGiamGia]
                                              ,SoLuongDung
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
                                               ,[KieuPGG]
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
                gg.setID(rs.getString(15));
                gg.setKieuPGG(rs.getBoolean(16));
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
                              ,SoLuongDung
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
                              ,[KieuPGG]
                              )
                        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)                            
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
            ps.setObject(15, gg.isKieuPGG());
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
                                         ,SoLuongDung = ?
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
                                          ,[KieuPGG] = ?
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
            ps.setObject(13, gg.isKieuPGG());
            ps.setObject(14, ID);
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
                         , SoLuongDung
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
                     ,SoLuongDung
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
                     where TenGiamGia like '%'+?+'%' Or SoLuongDung like '%'+?+'%'  OR PhanTramGiam like ?
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
                     ,SoLuongDung
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
                     ,SoLuongDung
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
                     ,SoLuongDung
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
                     ,SoLuongDung
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
                     ,SoLuongDung
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
                     ,SoLuongDung
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
                      where SoLuongDung = ?                                    					 
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
                     ,SoLuongDung
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

    public boolean updateTTThread(String ID, int trangThai, float Deleted, LocalDate ngayHT) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[PhieuGiamGia]
                                      SET [TrangThai] = ?
                                      ,[Deleted] =?          
                                      ,[UpdatedAt] = ?
                                    WHERE ID=?
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, trangThai);
            ps.setObject(2, Deleted);
            ps.setObject(3, ngayHT);
            ps.setObject(4, ID);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<PhieuGiamGia> timKiemKieu(int Kieu) {
        List<PhieuGiamGia> ds = new ArrayList<>();
        String sql = """                                                            
                     SELECT [TenGiamGia]
                     ,SoLuongDung
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
                      ,[KieuPGG]
                      FROM [dbo].[PhieuGiamGia]  
                      where KieuPGG = ?                                    					 
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Kieu);
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
                gg.setKieuPGG(rs.getBoolean(16));
                ds.add(gg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<KhachHang> getAllKH() {
        List<KhachHang> ds = new ArrayList<>();
        String sql = """
                             SELECT [Ten]
                                                              ,[SDT]                                
                                                              ,[ID]
                                                              ,[Email]
                                                          FROM [dbo].[KhachHang] WHERE [ID] <> 'KH00001';
                   """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setTen(rs.getString(1));
                kh.setSdt(rs.getString(2));
                kh.setId(rs.getString(3));
                kh.setEmail(rs.getString(4));
                ds.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void sendEmail(List<String> EmailKH, List<String> tenNN, String noiDung, String ngayBD, String ngayKT) {
        final String from = "ducminh2212345@gmail.com";
        final String password = "lmuh xcoe ogcu slsb";

        // Properties: khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP host
        props.put("mail.smtp.port", "587"); //TLS 587
        props.put("mail.smtp.auth", "true"); //Loggin mail
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        try {
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            };

            // Phiên làm việc
            Session session = Session.getInstance(props, auth);

            for (int i = 0; i < EmailKH.size(); i++) {
                String to = EmailKH.get(i);
                String tenNguoiNhan = tenNN.get(i);

                // Gui email
                MimeMessage msg = new MimeMessage(session);
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8"); //kiểu nội dung
                msg.setFrom(from); // người gửi
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false)); // người nhận
                msg.setSubject("Phần mềm bán điện thoại thông minh Mobile World" + " " + System.currentTimeMillis()); // tiêu đề Email
                msg.setSentDate(new java.util.Date()); // quy định ngày gửi
                msg.setReplyTo(null);

                //Nội dung
                String emailContent = "<html>"
                        + "<tr></tr>"
                        + "<tr><td>Xin chào: " + tenNguoiNhan + "</td></tr>"
                        + "<tr><td>Bạn đã nhận được quyền sử dụng phiếu giảm giá: " + noiDung + "</td></tr>"
                        + "<tr><td>Phiếu giảm giá này có thời hạn từ " + ngayBD + " đến ngày " + ngayKT + "</td></tr>"
                        + "<tr><td>Hãy nhanh tay sử dụng phiếu giảm giá" + "</td></tr>"
                        + "</html>";
                msg.setContent(emailContent, "text/html; charset=UTF-8");
                // gui email
                Transport.send(msg);
                System.out.println("Đã gửi đến: " + to);
            }

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            e.printStackTrace();
            System.out.println("Lỗi rồi");
        }
    }

}
