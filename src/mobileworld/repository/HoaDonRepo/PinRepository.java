package mobileworld.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.entity.PinEntity;

public class PinRepository {

    public List<PinEntity> getAll() {
        List<PinEntity> listPin = new ArrayList<>();

        String sql = """
            select ID,DungLuongPin from Pin WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PinEntity pin = new PinEntity();
                pin.setId(rs.getString(1));
                pin.setDungLuongPin(rs.getString(2));
                listPin.add(pin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPin;
    }
    
    public boolean add(PinEntity pin) {
        String sql = """
                            INSERT INTO [dbo].[Pin]
                            ([DungLuongPin]
                            ,[Deleted]
                            ,[Created at]
                            ,[Created by])
                      VALUES
                            (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, pin.getDungLuongPin());
            ps.setObject(2, pin.getDeleted());
            ps.setObject(3, pin.getCreatedAt());
            ps.setObject(4, pin.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean remove(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[Pin]
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

    public boolean update(PinEntity pin, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[Pin]
                    SET [DungLuongPin] = ?
                       ,[Deleted] = ?
                       ,[Updated at] = ?
                       ,[Updated by] = ?
                  WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, pin.getDungLuongPin());
            ps.setObject(2, pin.getDeleted());
            ps.setObject(3, pin.getCreatedAt());
            ps.setObject(4, pin.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
