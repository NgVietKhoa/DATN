package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.CameraSau;

public class CameraSauRepository {

    public List<CameraSau> getAll() {
        List<CameraSau> listCam = new ArrayList<>();

        String sql = """
              select ID,SoMP from CameraSau WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CameraSau cam = new CameraSau();
                cam.setId(rs.getString(1));
                cam.setSoMP(rs.getString(2));
                listCam.add(cam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCam;
    }

    public boolean add(CameraSau cam) {
        String sql = """
                     INSERT INTO [dbo].[CameraSau]
                     ([SoMP]
                     ,[Deleted]
                     ,[Created at]
                     ,[Created by])
                     VALUES
                     (?,?,?,?)
                     """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cam.getSoMP());
            ps.setObject(2, cam.getDeleted());
            ps.setObject(3, cam.getCreatedAt());
            ps.setObject(4, cam.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean remove(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[CameraSau]
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

    public boolean update(CameraSau cam, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[CameraSau]
                    SET [SoMP] = ?
                       ,[Deleted] = ?
                       ,[Updated at] = ?
                       ,[Updated by] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cam.getSoMP());
            ps.setObject(2, cam.getDeleted());
            ps.setObject(3, cam.getCreatedAt());
            ps.setObject(4, cam.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
