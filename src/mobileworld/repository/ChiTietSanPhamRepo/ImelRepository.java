package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.Imel;

public class ImelRepository {

    public List<Imel> getImel() {
        List<Imel> listImel = new ArrayList<>();

        String sql = """
            select ID,Imel from Imel
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Imel imel = new Imel();
                imel.setId(rs.getString(1));
                imel.setImel(rs.getString(2));
                listImel.add(imel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listImel;
    }

    public List<Imel> getAllImel(String getImel) {
        List<Imel> listImel = new ArrayList<>();

        String sql = "SELECT Imel FROM Imel WHERE Imel = ?";

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, getImel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Imel imel = new Imel();
                imel.setImel(rs.getString("Imel"));
                listImel.add(imel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listImel;
    }
}
