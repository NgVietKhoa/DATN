
package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.NhaSanXuat;


public class NhaSanXuatRepository {
    
    public List<NhaSanXuat> getAll() {
        List<NhaSanXuat> listNsx = new ArrayList<>();

        String sql = """
              select TenNsx from NhaSanXuat
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaSanXuat nsx = new NhaSanXuat();
                nsx.setTenNsx(rs.getString(1));
                listNsx.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNsx;
    }
}
