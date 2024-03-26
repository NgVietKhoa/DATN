package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.CameraTruoc;

public class CameraTruocRepository {

    public List<CameraTruoc> getAll() {
        List<CameraTruoc> listCam = new ArrayList<>();

        String sql = """
                select ID,SoMP from CameraTruoc WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CameraTruoc cam = new CameraTruoc();
                cam.setId(rs.getString(1));
                cam.setSoMP(rs.getString(2));
                listCam.add(cam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCam;
    }

    public boolean add(CameraTruoc cam) {
        String sql = """
                     INSERT INTO [dbo].[CameraTruoc]
                     ([SoMP]
                     ,[Deleted]
                      ,[CreatedAt]
                      ,[CreatedBy])
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
}
