/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.KhachHangRepo;

import java.util.ArrayList;
import java.util.List;
import mobileworld.model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import mobileworld.config.DBConnect;

/**
 *
 * @author thinh
 */
public class KhachHangRepository {

    public List<KhachHang> getAll() {
        List<KhachHang> khachHang = new ArrayList<>();
        String sql = """
                     SELECT [Ten]
                               ,[SDT]
                               ,[GioiTinh]
                               ,[NgaySinh]
                               ,[DiaChi]
                               ,[Deleted]                               
                               ,[ID]
                               ,[Email]
                           FROM [dbo].[KhachHang]
                           where deleted =1
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setTen(rs.getString(1));
                kh.setSdt(rs.getString(2));
                kh.setGioiTinh(rs.getFloat(3));
                kh.setNgaySinh(rs.getDate(4));
                kh.setDiaChi(rs.getString(5));
                kh.setDeleted(rs.getInt(6));               
                kh.setId(rs.getString(7));
                kh.setEmail(rs.getString(8));
                khachHang.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachHang;
    }

    public List<KhachHang> search(String search) {
        List<KhachHang> khachHang = new ArrayList<>();
        String sql = """
                     SELECT [Ten]
                               ,[SDT]
                               ,[GioiTinh]
                               ,[NgaySinh]
                               ,[DiaChi]
                               ,[Deleted]                               
                               ,[ID]
                               ,[Email]
                           FROM [dbo].[KhachHang]
                     WHERE Deleted = 1
                             AND (ID LIKE ? ESCAPE '!'
                             OR Ten LIKE ? ESCAPE '!'
                             OR Email LIKE ? ESCAPE '!')
                     
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareCall(sql)) {
            for (int i = 0; i < search.length(); i++) {
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
                ps.setString(3, "%" + search + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    KhachHang kh = new KhachHang();
                    kh.setTen(rs.getString(1));
                    kh.setSdt(rs.getString(2));
                    kh.setGioiTinh(rs.getFloat(3));
                    kh.setNgaySinh(rs.getDate(4));
                    kh.setDiaChi(rs.getString(5));
                    kh.setDeleted(rs.getInt(6));                   
                    kh.setId(rs.getString(7));
                    kh.setEmail(rs.getString(8));
                    khachHang.add(kh);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachHang;
    }

    public boolean add(KhachHang kh) {
        int check = 0;
        String sql = """
                      INSERT INTO [dbo].[KhachHang]
                                            ([Ten]
                                            ,[SDT]
                                            ,[GioiTinh]
                                            ,[NgaySinh]
                                            ,[DiaChi]
                                            ,[Deleted]
                                            ,[CreatedAt]
                                            ,[CreatedBy]
                                            ,[UpdatedAt]
                                            ,[UpdatedBy]
                                            ,[Email]
                                            )
                                      VALUES
                                            (?,?,?,?,?,?,?,?,?,?,?)
                      """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, kh.getTen());
            ps.setObject(2, kh.getSdt());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getNgaySinh());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, kh.getDeleted());
            ps.setObject(7, kh.getCreatedat());
            ps.setObject(8, kh.getCreatedby());
            ps.setObject(9, kh.getUpdatedat());
            ps.setObject(10, kh.getUpdatedby());
            ps.setObject(11, kh.getEmail());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean update(KhachHang kh, String id) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[KhachHang]
                                      SET [Ten] = ?
                                         ,[SDT] = ?
                                         ,[GioiTinh] = ?
                                         ,[NgaySinh] = ?
                                         ,[DiaChi] = ?
                                         ,[Deleted] = ?                                        
                                         ,[UpdatedAt] = ?
                                         ,[UpdatedBy] = ?
                                         ,[Email]=?
                                    WHERE ID = ?
                      """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, kh.getTen());
            ps.setObject(2, kh.getSdt());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getNgaySinh());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, kh.getDeleted());
            ps.setObject(7, kh.getUpdatedat());
            ps.setObject(8, kh.getUpdatedby());
            ps.setObject(9, kh.getEmail());
            ps.setObject(10, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean delete(String id,LocalDateTime ngayTT,String maNV) {
        int check = 0;
        String sql = """
                   Update [dbo].[KhachHang] SET Deleted=0
                                      WHERE ID = ?
                       UPDATE [dbo].[KhachHang] SET [UpdatedAt] = ?
                                                    ,[UpdatedBy] = ?
                      WHERE ID = ?
                    """;
        try ( Connection cnt = DBConnect.getConnection();  PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.setObject(2, ngayTT);
            ps.setObject(3, maNV);
            ps.setObject(4, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

}
