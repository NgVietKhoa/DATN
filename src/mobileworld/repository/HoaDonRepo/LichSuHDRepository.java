/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package mobileworld.repository.HoaDonRepo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.viewModel.LichSuHDModel;


/**
 *
 * @author ADMIN
 */
public class LichSuHDRepository {
    public List<LichSuHDModel> getAll(String idLSHD) {
        List<LichSuHDModel> list = new ArrayList<>();
        String sql = """
                     SELECT
                         [IDHoaDon],
                         [CreatedBy],
                         [CreatedAt],
                         [HanhDong]
                     FROM 
                         [NHOM3_MOBILEWORLD_SD1702SP24_BL2].[dbo].[LichSuHoaDon]
                     WHERE 
                         [IDHoaDon] = ?
                     """;

        try (Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ps.setObject(1, idLSHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichSuHDModel lshdm = new LichSuHDModel();
                lshdm.setIdHD(rs.getString(1));
                lshdm.setIdNV(rs.getString(2));
                lshdm.setNgayGio(rs.getTimestamp(3).toLocalDateTime());
                lshdm.setHanhDong(rs.getString(4));
                list.add(lshdm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
