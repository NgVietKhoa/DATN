package mobileworld.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.entity.ManHinhEntity;

public class ManHinhRepository {

    public List<ManHinhEntity> getAll() {
        List<ManHinhEntity> listMH = new ArrayList<>();

        String sql = """
            select ID,LoaiManHinh from ManHinh WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ManHinhEntity mh = new ManHinhEntity();
                mh.setId(rs.getString(1));
                mh.setLoaiManHinh(rs.getString(2));
                listMH.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMH;
    }
    
    public boolean add(ManHinhEntity mh) {
        String sql = """
                            INSERT INTO [dbo].[ManHinh]
                            ([LoaiManHinh]
                            ,[Deleted]
                            ,[Created at]
                            ,[Created by])
                      VALUES
                            (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, mh.getLoaiManHinh());
            ps.setObject(2, mh.getDeleted());
            ps.setObject(3, mh.getCreatedAt());
            ps.setObject(4, mh.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean remove(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[ManHinh]
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

    public boolean update(ManHinhEntity mh, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[ManHinh]
                    SET [LoaiManHinh] = ?
                       ,[Deleted] = ?
                       ,[Updated at] = ?
                       ,[Updated by] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, mh.getLoaiManHinh());
            ps.setObject(2, mh.getDeleted());
            ps.setObject(3, mh.getCreatedAt());
            ps.setObject(4, mh.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
