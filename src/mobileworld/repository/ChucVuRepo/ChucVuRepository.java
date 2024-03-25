package mobileworld.repository.ChucVuRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.ChucVu;

public class ChucVuRepository {

    public List<ChucVu> getAll() {
        List<ChucVu> listCV = new ArrayList<>();

        String sql = """
                        SELECT [TenChucVu]
                          ,[Deleted]
                          ,[Created at]
                          ,[Created by]
                          ,[Updated at]
                          ,[Updated by]
                          ,[ID]
                      FROM [NHOM3_MOBILEWORLD_SOF102_SD1702_DEMO3].[dbo].[ChucVu]
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChucVu cv = new ChucVu();
                cv.setTenChucVu(rs.getString(1));
                cv.setDeleted(rs.getFloat(2));
                cv.setCreatedAt(rs.getDate(3));
                cv.setCreatedBy(rs.getString(4));
                cv.setUpdatedAt(rs.getDate(5));
                cv.setUpdateBy(rs.getString(6));
                cv.setId(rs.getString(7));
                listCV.add(cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCV;
    }
}
