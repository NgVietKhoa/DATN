package mobileworld.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.entity.DongSPEntity;

public class DongSPRepository {

    public List<DongSPEntity> getAll() {
        List<DongSPEntity> listDsp = new ArrayList<>();

        String sql = """
            select ID,TenDSP from DongSP WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DongSPEntity dsp = new DongSPEntity();
                dsp.setId(rs.getString(1));
                dsp.setTenDsp(rs.getString(2));
                listDsp.add(dsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDsp;
    }

    public boolean add(DongSPEntity dsp) {
        String sql = """
                 INSERT INTO [dbo].[DongSP]
                            ([TenDSP]
                            ,[Deleted]
                            ,[Created at]
                            ,[Created by]
                            )
                      VALUES
                            (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, dsp.getTenDsp());
            ps.setObject(2, dsp.getDeleted());
            ps.setObject(3, dsp.getCreatedAt());
            ps.setObject(4, dsp.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean remove(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[DongSP]
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

    public boolean update(DongSPEntity sp, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[DongSP]
                    SET [TenDSP] = ?
                       ,[Deleted] = ?
                       ,[Updated at] = ?
                       ,[Updated by] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, sp.getTenDsp());
            ps.setObject(2, sp.getDeleted());
            ps.setObject(3, sp.getCreatedAt());
            ps.setObject(4, sp.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
