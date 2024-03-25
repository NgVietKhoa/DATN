
package mobileworld.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.entity.NhaSanXuatEntity;


public class NhaSanXuatRepository {
    
    public List<NhaSanXuatEntity> getAll() {
        List<NhaSanXuatEntity> listNsx = new ArrayList<>();

        String sql = """
              select [Tên NSX] from NhaSanXuat
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaSanXuatEntity nsx = new NhaSanXuatEntity();
                nsx.setTenNsx(rs.getString(1));
                listNsx.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNsx;
    }
}
