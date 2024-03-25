/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.repository.HoaDonRepo;


import java.util.ArrayList;
import java.util.List;
import mobileworld.entity.PhuongThucThanhToan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mobileworld.config.DBConnect;
/**
 *
 * @author ADMIN
 */
public class PhuongThucThanhToanRepository {
    public List<PhuongThucThanhToan> getAll(){
        List<PhuongThucThanhToan> list = new ArrayList<>();
        String sql = """
                     SELECT [TenKieuThanhToan]
                           ,[ID]
                       FROM [dbo].[PhuongThucThanhToan]
                     """;
        try(Connection cnt = DBConnect.getConnection(); PreparedStatement ps = cnt.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PhuongThucThanhToan pttt = new PhuongThucThanhToan();
                pttt.setTeKieuThanhToan(rs.getString(1));
                pttt.setIdPTTT(rs.getString(2));
                list.add(pttt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
}
