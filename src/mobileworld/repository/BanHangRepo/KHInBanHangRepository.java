/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.BanHangRepo;

import java.util.ArrayList;
import java.util.List;
import mobileworld.viewModel.BanHangViewModel.KhachHangViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import mobileworld.config.DBConnect;
import mobileworld.model.KhachHang;

/**
 *
 * @author ADMIN
 */
public class KHInBanHangRepository {

    public List<KhachHangViewModel> getAll() {
        List<KhachHangViewModel> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         [ID],
                         [Ten],
                         [SDT],
                         [DiaChi],
                         [GioiTinh]
                     FROM 
                         [dbo].[KhachHang]
                     """;
        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHangViewModel kh = new KhachHangViewModel();
                kh.setIdKH(rs.getString(1));
                kh.setTen(rs.getString(2));
                kh.setSoDienThoai(rs.getString(3));
                kh.setDiaChi(rs.getString(4));
                kh.setGioiTinh(rs.getFloat(5));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addKH(KhachHang kh,String idNV) {
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
                                ,[Email])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?)
                     """;
        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, kh.getTen());
            ps.setObject(2, kh.getSdt());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getNgaySinh());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, 1);
            ps.setObject(7, new Timestamp(new Date().getTime()));
            ps.setObject(8, idNV);
            ps.setObject(9, new Timestamp(new Date().getTime()));
            ps.setObject(10, idNV);
            ps.setObject(11, kh.getEmail());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check > 0;
    }
}
