package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.DongSP;
import mobileworld.viewModel.DongSPViewModel;

public class DongSPRepository {

    public List<DongSPViewModel> getAll() {
        List<DongSPViewModel> listDsp = new ArrayList<>();

        String sql = """
            SELECT DongSP.ID, DongSP.TenDsp, COUNT(ChiTietSP.IDDongSP) AS soluong
                                    FROM DongSP 
                                    LEFT JOIN ChiTietSP ON DongSP.ID = ChiTietSP.IDDongSP AND ChiTietSP.Deleted = 1
                                    WHERE DongSP.Deleted = 1 
                                    GROUP BY DongSP.ID, DongSP.TenDsp
                                    ORDER BY DongSP.ID DESC;
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DongSPViewModel dsp = new DongSPViewModel();
                dsp.setId(rs.getString(1));
                dsp.setTenDsp(rs.getString(2));
                dsp.setSoLuong(rs.getInt(3));
                listDsp.add(dsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDsp;
    }
    
    public List<DongSPViewModel> getAllConHang() {
        List<DongSPViewModel> listDsp = new ArrayList<>();

        String sql = """
            SELECT 
                                DongSP.ID, 
                                DongSP.TenDsp, 
                                COUNT(ChiTietSP.IDDongSP) AS soluong 
                            FROM 
                                DongSP 
                            LEFT JOIN 
                                ChiTietSP ON DongSP.ID = ChiTietSP.IDDongSP AND ChiTietSP.Deleted = 1
                            WHERE 
                                DongSP.Deleted = 1 
                            GROUP BY 
                                DongSP.ID, 
                                DongSP.TenDsp
                            HAVING 
                                COUNT(ChiTietSP.IDDongSP) > 0
                            ORDER BY 
                                DongSP.ID DESC;
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DongSPViewModel dsp = new DongSPViewModel();
                dsp.setId(rs.getString(1));
                dsp.setTenDsp(rs.getString(2));
                dsp.setSoLuong(rs.getInt(3));
                listDsp.add(dsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDsp;
    }
    
    public List<DongSPViewModel> getAllHetHang() {
        List<DongSPViewModel> listDsp = new ArrayList<>();

        String sql = """
            SELECT 
                                DongSP.ID, 
                                DongSP.TenDsp, 
                                COUNT(ChiTietSP.IDDongSP) AS soluong 
                            FROM 
                                DongSP 
                            LEFT JOIN 
                                ChiTietSP ON DongSP.ID = ChiTietSP.IDDongSP AND ChiTietSP.Deleted = 1
                            WHERE 
                                DongSP.Deleted = 1 
                            GROUP BY 
                                DongSP.ID, 
                                DongSP.TenDsp
                            HAVING 
                                COUNT(ChiTietSP.IDDongSP) = 0
                            ORDER BY 
                                DongSP.ID DESC;
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DongSPViewModel dsp = new DongSPViewModel();
                dsp.setId(rs.getString(1));
                dsp.setTenDsp(rs.getString(2));
                dsp.setSoLuong(rs.getInt(3));
                listDsp.add(dsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDsp;
    }

    public boolean add(DongSP dsp) {
        String sql = """
                 INSERT INTO [dbo].[DongSP]
                            ([TenDsp]
                            ,[Deleted]
                        ,[CreatedAt]
                        ,[CreatedBy])
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

    public boolean update(DongSP sp, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[DongSP]
                    SET [TenDsp] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
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

    public List<DongSPViewModel> getTenDsp() {
        List<DongSPViewModel> listDsp = new ArrayList<>();

        String sql = """
                SELECT TenDsp FROM DongSP WHERE Deleted = 1
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DongSPViewModel dsp = new DongSPViewModel();
                dsp.setTenDsp(rs.getString(1));
                listDsp.add(dsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDsp;
    }

    public List<DongSPViewModel> search(String search) {
        List<DongSPViewModel> listSP = new ArrayList<>();
        List<DongSPViewModel> allSP = getAll(); // Lấy danh sách tất cả sản phẩm

        // Lọc dựa trên tiêu chí tìm kiếm
        for (DongSPViewModel sp : allSP) {
            if (matchesSearchCriteria(sp, search)) {
                listSP.add(sp);
            }
        }

        return listSP;
    }

    // Phương thức trợ giúp kiểm tra xem một sản phẩm có khớp với tiêu chí tìm kiếm hay không
    private boolean matchesSearchCriteria(DongSPViewModel sp, String search) {
        String searchTerm = search.toLowerCase(); // Chuyển đổi từ tìm kiếm thành chữ thường để so sánh không phân biệt chữ hoa chữ thường
        // Kiểm tra xem bất kỳ trường nào của sản phẩm có chứa từ khóa tìm kiếm hay không
        return sp.getId().toLowerCase().contains(searchTerm)
                || sp.getTenDsp().toLowerCase().contains(searchTerm)
                || String.valueOf(sp.getSoLuong()).toLowerCase().contains(searchTerm);
    }
}
