package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.entity.TinhTrangEntity;

public class TinhTrangRepository {

    public List<TinhTrangEntity> getAll() {
        List<TinhTrangEntity> listTT = new ArrayList<>();

        String sql = """
            select TinhTrang from TinhTrang
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TinhTrangEntity tt = new TinhTrangEntity();
                tt.setTinhTrang(rs.getString(1));
                listTT.add(tt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTT;
    }
}
