package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.ManHinh;

public class ManHinhRepository {

    public List<ManHinh> getAll() {
        List<ManHinh> listMH = new ArrayList<>();

        String sql = """
            select ID,LoaiManHinh from ManHinh WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ManHinh mh = new ManHinh();
                mh.setId(rs.getString(1));
                mh.setLoaiManHinh(rs.getString(2));
                listMH.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMH;
    }
    
    public boolean add(ManHinh mh) {
        String sql = """
                            INSERT INTO [dbo].[ManHinh]
                            ([LoaiManHinh]
                            ,[Deleted]
                      ,[CreatedAt]
                                             ,[CreatedBy])
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
}
