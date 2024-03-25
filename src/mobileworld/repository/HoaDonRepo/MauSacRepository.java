package mobileworld.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.entity.MauSacEntity;

public class MauSacRepository {

    public List<MauSacEntity> getAll() {
        List<MauSacEntity> listMS = new ArrayList<>();

        String sql = """
            select ID,TenMau from MauSac WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MauSacEntity ms = new MauSacEntity();
                ms.setId(rs.getString(1));
                ms.setTenMau(rs.getString(2));
                listMS.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMS;
    }
    
    public boolean add(MauSacEntity ms) {
        String sql = """
                            INSERT INTO [dbo].[MauSac]
                            ([TenMau]
                            ,[Deleted]
                            ,[Created at]
                            ,[Created by])
                      VALUES
                            (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ms.getTenMau());
            ps.setObject(2, ms.getDeleted());
            ps.setObject(3, ms.getCreatedAt());
            ps.setObject(4, ms.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean remove(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[MauSac]
                               SET 
                                  [Deleted] = 0
                             WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean update(MauSacEntity ms, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[MauSac]
                    SET [TenMau] = ?
                       ,[Deleted] = ?
                       ,[Updated at] = ?
                       ,[Updated by] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ms.getTenMau());
            ps.setObject(2, ms.getDeleted());
            ps.setObject(3, ms.getCreatedAt());
            ps.setObject(4, ms.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
