package mobileworld.repository.ChiTietSanPhamRepo;

import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.ChiTietSP;
import mobileworld.viewModel.ChiTietSanPhamViewModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class ChiTietSPRepository {

    public List<ChiTietSanPhamViewModel> getAll() {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
                     SELECT
                             CTS.IDImel,
                             CTS.IDNSX,
                             CTS.IDDongSP,
                             CTS.IDMauSac,
                             CTS.IDPin,
                             CTS.IDManHinh,
                             CTS.IDRam,
                             CTS.IDBoNho,
                             CTS.IDCPU,
                             CTS.IDCamSau,
                             CTS.IDCamTruoc,
                             Imel.Imel,
                             DS.TenDsp,
                             NSX.TenNsx,
                             Pin.DungLuongPin,
                             ManHinh.LoaiManHinh,
                             CPU.CPU,
                             Ram.DungLuongRam,
                             BoNho.DungLuongBoNho,
                             MauSac.TenMau,
                             CTS.GiaBan,
                             CTS.GhiChu,
                             COUNT(CTS.ID) AS SoLuong,
                             CameraSau.SoMP,
                             CameraTruoc.SoMP,
                             CTS.ID
                         FROM
                             dbo.ChiTietSP AS CTS
                             INNER JOIN dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID
                             INNER JOIN dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                             INNER JOIN dbo.Pin ON CTS.IDPin = Pin.ID
                             INNER JOIN dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                             INNER JOIN dbo.CPU ON CTS.IDCPU = CPU.ID
                             INNER JOIN dbo.Ram ON CTS.IDRam = Ram.ID
                             INNER JOIN dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                             INNER JOIN dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                             INNER JOIN dbo.CameraSau ON CTS.IDCamSau = CameraSau.ID
                             INNER JOIN dbo.CameraTruoc ON CTS.IDCamTruoc = CameraTruoc.ID
                             INNER JOIN dbo.Imel ON CTS.IDImel = Imel.ID
                         WHERE
                             CTS.Deleted = 1
                         GROUP BY
                             CTS.IDImel,
                             CTS.IDNSX,
                             CTS.IDDongSP,
                             CTS.IDMauSac,
                             CTS.IDPin,
                             CTS.IDManHinh,
                             CTS.IDRam,
                             CTS.IDBoNho,
                             CTS.IDCPU,
                             CTS.IDCamSau,
                             CTS.IDCamTruoc,
                             Imel.Imel,
                             DS.TenDsp,
                             NSX.TenNsx,
                             Pin.DungLuongPin,
                             ManHinh.LoaiManHinh,
                             CPU.CPU,
                             Ram.DungLuongRam,
                             BoNho.DungLuongBoNho,
                             MauSac.TenMau,
                             CTS.GiaBan,
                             CTS.GhiChu,
                             CameraSau.SoMP,
                             CameraTruoc.SoMP,
                             CTS.ID
                         ORDER BY
                             CTS.ID DESC;
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setIdimel(rs.getString(1));
                spvm.setIdNsx(rs.getString(2));
                spvm.setIdDsp(rs.getString(3));
                spvm.setIdMauSac(rs.getString(4));
                spvm.setIdPin(rs.getString(5));
                spvm.setIdManHinh(rs.getString(6));
                spvm.setIdRam(rs.getString(7));
                spvm.setIdboNho(rs.getString(8));
                spvm.setIdCpu(rs.getString(9));
                spvm.setIdCameraSau(rs.getString(10));
                spvm.setIdCameraTruoc(rs.getString(11));
                spvm.setImel(rs.getString(12));
                spvm.setTenDsp(rs.getString(13));
                spvm.setTenNsx(rs.getString(14));
                spvm.setDungLuongPin(rs.getString(15));
                spvm.setLoaiManHinh(rs.getString(16));
                spvm.setCpu(rs.getString(17));
                spvm.setDungLuongRam(rs.getString(18));
                spvm.setDungLuongBoNho(rs.getString(19));
                spvm.setTenMau(rs.getString(20));
                spvm.setGiaBan(rs.getBigDecimal(21));
                spvm.setGhiChu(rs.getString(22));
                spvm.setSoLuong(rs.getInt(23));
                spvm.setCameraSau(rs.getString(24));
                spvm.setCameraTruoc(rs.getString(25));
                spvm.setId(rs.getString(26));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> getSP(String idDsp) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
        WITH DSP_Count AS (
                        SELECT
                            IDDongSP,
                            COUNT(*) AS SoLuongDSP
                        FROM
                            dbo.ChiTietSP
                        WHERE 
                            Deleted = 1
                        GROUP BY
                            IDDongSP
                    ),
                    CTE_RN AS (
                        SELECT
                            CTS.IDImel,
                            CTS.IDNSX,
                            CTS.IDDongSP,
                            CTS.IDMauSac,
                            CTS.IDPin,
                            CTS.IDManHinh,
                            CTS.IDRam,
                            CTS.IDBoNho,
                            CTS.IDCPU,
                            CTS.IDCamSau,
                            CTS.IDCamTruoc,
                            Imel.Imel,
                            DS.TenDsp,
                            NSX.TenNsx,
                            Pin.DungLuongPin,
                            ManHinh.LoaiManHinh,
                            CPU.CPU,
                            Ram.DungLuongRam,
                            BoNho.DungLuongBoNho,
                            MauSac.TenMau,
                            CTS.GiaBan,
                            CTS.GhiChu,
                            DC.SoLuongDSP,
                            CameraSau.SoMP AS CameraSau,
                            CameraTruoc.SoMP AS CameraTruoc,
                            CTS.ID,
                            ROW_NUMBER() OVER(PARTITION BY CTS.IDDongSP ORDER BY CTS.ID DESC) AS RN
                        FROM
                            dbo.ChiTietSP AS CTS
                        INNER JOIN 
                            DSP_Count AS DC ON CTS.IDDongSP = DC.IDDongSP
                        INNER JOIN 
                            dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID
                        INNER JOIN 
                            dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                        INNER JOIN 
                            dbo.Pin ON CTS.IDPin = Pin.ID
                        INNER JOIN 
                            dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                        INNER JOIN 
                            dbo.CPU ON CTS.IDCPU = CPU.ID
                        INNER JOIN 
                            dbo.Ram ON CTS.IDRam = Ram.ID
                        INNER JOIN 
                            dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                        INNER JOIN 
                            dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                        INNER JOIN 
                            dbo.CameraSau ON CTS.IDCamSau = CameraSau.ID
                        INNER JOIN 
                            dbo.CameraTruoc ON CTS.IDCamTruoc = CameraTruoc.ID
                        INNER JOIN 
                            dbo.Imel ON CTS.IDImel = Imel.ID
                        WHERE 
                            CTS.Deleted = 1 AND CTS.IDDongSP = ?
                    )
                    SELECT 
                        Imel,
                        IDNSX,
                        IDDongSP,
                        IDMauSac,
                        IDPin,
                        IDManHinh,
                        IDRam,
                        IDBoNho,
                        IDCPU,
                        IDCamSau,
                        IDCamTruoc,
                        TenDsp,
                        TenNsx,
                        DungLuongPin,
                        LoaiManHinh,
                        CPU,
                        DungLuongRam,
                        DungLuongBoNho,
                        TenMau,
                        GiaBan,
                        GhiChu,
                        SoLuongDSP,
                        CameraSau,
                        CameraTruoc,
                        ID    
                    FROM 
                        CTE_RN
                    WHERE 
                        RN = 1
                    ORDER BY 
                        ID DESC;
    """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setImel(rs.getString(1));
                spvm.setIdNsx(rs.getString(2));
                spvm.setIdDsp(rs.getString(3));
                spvm.setIdMauSac(rs.getString(4));
                spvm.setIdPin(rs.getString(5));
                spvm.setIdManHinh(rs.getString(6));
                spvm.setIdRam(rs.getString(7));
                spvm.setIdboNho(rs.getString(8));
                spvm.setIdCpu(rs.getString(9));
                spvm.setIdCameraSau(rs.getString(10));
                spvm.setIdCameraTruoc(rs.getString(11));
                spvm.setTenDsp(rs.getString(12));
                spvm.setTenNsx(rs.getString(13));
                spvm.setDungLuongPin(rs.getString(14));
                spvm.setLoaiManHinh(rs.getString(15));
                spvm.setCpu(rs.getString(16));
                spvm.setDungLuongRam(rs.getString(17));
                spvm.setDungLuongBoNho(rs.getString(18));
                spvm.setTenMau(rs.getString(19));
                spvm.setGiaBan(rs.getBigDecimal(20));
                spvm.setGhiChu(rs.getString(21));
                spvm.setSoLuong(rs.getInt(22));
                spvm.setCameraSau(rs.getString(23));
                spvm.setCameraTruoc(rs.getString(24));
                spvm.setId(rs.getString(25));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<ChiTietSanPhamViewModel> getSPImel(String idDsp) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
                     SELECT
                         CTS.IDImel,
                         CTS.IDNSX,
                         CTS.IDDongSP,
                         CTS.IDMauSac,
                         CTS.IDPin,
                         CTS.IDManHinh,
                         CTS.IDRam,
                         CTS.IDBoNho,
                         CTS.IDCPU,
                         CTS.IDCamSau,
                         CTS.IDCamTruoc,
                         Imel.Imel,
                         DS.TenDsp,
                         NSX.TenNsx,
                         Pin.DungLuongPin,
                         ManHinh.LoaiManHinh,
                         CPU.CPU,
                         Ram.DungLuongRam,
                         BoNho.DungLuongBoNho,
                         MauSac.TenMau,
                         CTS.GiaBan,
                         CTS.GhiChu,
                         COUNT(CTS.ID) AS SoLuong,
                         CameraSau.SoMP,
                         CameraTruoc.SoMP,
                         CTS.ID
                     FROM
                         dbo.ChiTietSP AS CTS
                         INNER JOIN dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID
                         INNER JOIN dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                         INNER JOIN dbo.Pin ON CTS.IDPin = Pin.ID
                         INNER JOIN dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                         INNER JOIN dbo.CPU ON CTS.IDCPU = CPU.ID
                         INNER JOIN dbo.Ram ON CTS.IDRam = Ram.ID
                         INNER JOIN dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                         INNER JOIN dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                         INNER JOIN dbo.CameraSau ON CTS.IDCamSau = CameraSau.ID
                         INNER JOIN dbo.CameraTruoc ON CTS.IDCamTruoc = CameraTruoc.ID
                         INNER JOIN dbo.Imel ON CTS.IDImel = Imel.ID
                     WHERE
                         CTS.Deleted = 1
                         AND CTS.IDDongSP = ?
                     GROUP BY
                         CTS.IDImel,
                         CTS.IDNSX,
                         CTS.IDDongSP,
                         CTS.IDMauSac,
                         CTS.IDPin,
                         CTS.IDManHinh,
                         CTS.IDRam,
                         CTS.IDBoNho,
                         CTS.IDCPU,
                         CTS.IDCamSau,
                         CTS.IDCamTruoc,
                         Imel.Imel,
                         DS.TenDsp,
                         NSX.TenNsx,
                         Pin.DungLuongPin,
                         ManHinh.LoaiManHinh,
                         CPU.CPU,
                         Ram.DungLuongRam,
                         BoNho.DungLuongBoNho,
                         MauSac.TenMau,
                         CTS.GiaBan,
                         CTS.GhiChu,
                         CameraSau.SoMP,
                         CameraTruoc.SoMP,
                         CTS.ID
                     ORDER BY
                         CTS.ID DESC
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idDsp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setIdimel(rs.getString(1));
                spvm.setIdNsx(rs.getString(2));
                spvm.setIdDsp(rs.getString(3));
                spvm.setIdMauSac(rs.getString(4));
                spvm.setIdPin(rs.getString(5));
                spvm.setIdManHinh(rs.getString(6));
                spvm.setIdRam(rs.getString(7));
                spvm.setIdboNho(rs.getString(8));
                spvm.setIdCpu(rs.getString(9));
                spvm.setIdCameraSau(rs.getString(10));
                spvm.setIdCameraTruoc(rs.getString(11));
                spvm.setImel(rs.getString(12));
                spvm.setTenDsp(rs.getString(13));
                spvm.setTenNsx(rs.getString(14));
                spvm.setDungLuongPin(rs.getString(15));
                spvm.setLoaiManHinh(rs.getString(16));
                spvm.setCpu(rs.getString(17));
                spvm.setDungLuongRam(rs.getString(18));
                spvm.setDungLuongBoNho(rs.getString(19));
                spvm.setTenMau(rs.getString(20));
                spvm.setGiaBan(rs.getBigDecimal(21));
                spvm.setGhiChu(rs.getString(22));
                spvm.setSoLuong(rs.getInt(23));
                spvm.setCameraSau(rs.getString(24));
                spvm.setCameraTruoc(rs.getString(25));
                spvm.setId(rs.getString(26));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public boolean add(ChiTietSP ctsp) {
        String sql = """
                     -- Thêm imel mới vào bảng Imel
                     INSERT INTO dbo.Imel (
                         [Imel],
                         [Deleted],
                         [CreatedAt],
                         [CreatedBy]
                     )
                     VALUES (?, ?, ?, ?);
                     
                     -- Thêm thông tin vào bảng ChiTietSP và cập nhật IDImel
                     INSERT INTO dbo.ChiTietSP (
                         IDDongSP, IDMauSac, IDPin, IDManHinh, IDRam, IDBoNho, IDCPU, IDCamSau, IDCamTruoc, IDNSX,
                         GiaBan, IDImel, GhiChu, [Deleted], [CreatedAt], [CreatedBy]
                     ) 
                     VALUES (
                         (SELECT ID FROM dbo.DongSP WHERE TenDsp = ?),
                         (SELECT TOP 1 ID FROM dbo.MauSac WHERE TenMau = ?),
                         (SELECT TOP 1 ID FROM dbo.Pin WHERE DungLuongPin = ?),
                         (SELECT TOP 1 ID FROM dbo.ManHinh WHERE LoaiManHinh = ?),
                         (SELECT TOP 1 ID FROM dbo.Ram WHERE DungLuongRam = ?),
                         (SELECT TOP 1 ID FROM dbo.BoNho WHERE DungLuongBoNho = ?),
                         (SELECT TOP 1 ID FROM dbo.CPU WHERE CPU = ?),
                         (SELECT TOP 1 ID FROM dbo.CameraSau WHERE SoMP = ?),
                         (SELECT TOP 1 ID FROM dbo.CameraTruoc WHERE SoMP = ?),
                         (SELECT TOP 1 ID FROM dbo.NhaSanXuat WHERE TenNsx = ?),
                         ?, (SELECT MAX(ID) FROM dbo.Imel), ?, ?, ?, ?
                     );
                     """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ctsp.getImel());
            ps.setObject(2, ctsp.getDeleted());
            ps.setObject(3, ctsp.getCreatedAt());
            ps.setObject(4, ctsp.getCreatedBy());
            ps.setObject(5, ctsp.getIdDsp());
            ps.setObject(6, ctsp.getIdMauSac());
            ps.setObject(7, ctsp.getIdPin());
            ps.setObject(8, ctsp.getIdManHinh());
            ps.setObject(9, ctsp.getIdRam());
            ps.setObject(10, ctsp.getIdboNho());
            ps.setObject(11, ctsp.getIdCpu());
            ps.setObject(12, ctsp.getIdCamSau());
            ps.setObject(13, ctsp.getIdCamTruoc());
            ps.setObject(14, ctsp.getIdNsx());
            ps.setObject(15, ctsp.getGiaBan());
            ps.setObject(16, ctsp.getGhiChu());
            ps.setObject(17, ctsp.getDeleted());
            ps.setObject(18, ctsp.getCreatedAt());
            ps.setObject(19, ctsp.getCreatedBy());
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return check > 0;
    }

    public boolean remove(String id) {
        int check = 0;
        String sql = """
            UPDATE [dbo].[ChiTietSP]
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

    public boolean update(ChiTietSP ctsp, String id) {
        String sql = """
                 UPDATE dbo.ChiTietSP
                                  SET IDDongSP = (SELECT ID FROM dbo.DongSP WHERE TenDsp = ?),
                                      IDMauSac = (SELECT TOP 1 ID FROM dbo.MauSac WHERE TenMau = ?),
                                      IDPin = (SELECT TOP 1 ID FROM dbo.Pin WHERE DungLuongPin = ?),
                                      IDManHinh = (SELECT TOP 1 ID FROM dbo.ManHinh WHERE LoaiManHinh = ?),
                                      IDRam = (SELECT TOP 1 ID FROM dbo.Ram WHERE DungLuongRam = ?),
                                      IDBoNho = (SELECT TOP 1 ID FROM dbo.BoNho WHERE DungLuongBoNho = ?),
                                      IDCPU = (SELECT TOP 1 ID FROM dbo.CPU WHERE CPU = ?),
                                      IDCamSau = (SELECT TOP 1 ID FROM dbo.CameraSau WHERE SoMP = ?),
                                      IDCamTruoc = (SELECT TOP 1 ID FROM dbo.CameraTruoc WHERE SoMP = ?),
                                      IDNSX = (SELECT TOP 1 ID FROM dbo.NhaSanXuat WHERE [TenNsx] = ?),
                                      GiaBan = ?,
                                      GhiChu = ?,
                                      [Deleted] = ?,
                                      [UpdatedAt] = ?,
                                      [UpdatedBy] = ?
                                  WHERE ID = ?
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ctsp.getIdDsp());
            ps.setObject(2, ctsp.getIdMauSac());
            ps.setObject(3, ctsp.getIdPin());
            ps.setObject(4, ctsp.getIdManHinh());
            ps.setObject(5, ctsp.getIdRam());
            ps.setObject(6, ctsp.getIdboNho());
            ps.setObject(7, ctsp.getIdCpu());
            ps.setObject(8, ctsp.getIdCamSau());
            ps.setObject(9, ctsp.getIdCamTruoc());
            ps.setObject(10, ctsp.getIdNsx());
            ps.setObject(11, ctsp.getGiaBan());
            ps.setObject(12, ctsp.getGhiChu());
            ps.setObject(13, ctsp.getDeleted());
            ps.setObject(14, ctsp.getCreatedAt());
            ps.setObject(15, ctsp.getCreatedBy());
            ps.setObject(16, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<ChiTietSanPhamViewModel> search(String search) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();
        List<ChiTietSanPhamViewModel> allSP = getAll();

        // Filtering based on search criteria
        for (ChiTietSanPhamViewModel sp : allSP) {
            if (matchesSearchCriteria(sp, search)) {
                listSP.add(sp);
            }
        }

        return listSP;
    }

    private boolean matchesSearchCriteria(ChiTietSanPhamViewModel sp, String search) {
        String searchTerm = search.toLowerCase(); // Convert search term to lowercase for case-insensitive comparison
        // Check if any field of the product contains the search term
        return sp.getImel().toLowerCase().contains(searchTerm)
                || sp.getTenDsp().toLowerCase().contains(searchTerm)
                || sp.getTenNsx().toLowerCase().contains(searchTerm)
                || sp.getDungLuongPin().toLowerCase().contains(searchTerm)
                || sp.getLoaiManHinh().toLowerCase().contains(searchTerm)
                || sp.getCpu().toLowerCase().contains(searchTerm)
                || sp.getDungLuongRam().toLowerCase().contains(searchTerm)
                || sp.getDungLuongBoNho().toLowerCase().contains(searchTerm)
                || sp.getGiaBan().toString().toLowerCase().contains(searchTerm)
                || sp.getCameraSau().toLowerCase().contains(searchTerm)
                || sp.getCameraTruoc().toLowerCase().contains(searchTerm);
    }

    public List<ChiTietSanPhamViewModel> searchBoLoc(String search) {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();
        List<ChiTietSanPhamViewModel> allSP = getAll();

        // Filtering based on search criteria
        for (ChiTietSanPhamViewModel sp : allSP) {
            if (matchesSearchCriteria(sp, search)) {
                listSP.add(sp);
            }
        }

        return listSP;
    }

    public List<ChiTietSanPhamViewModel> getAllCTSP() {
        List<ChiTietSanPhamViewModel> listSP = new ArrayList<>();

        String sql = """
        WITH DSP_Count AS (
                        SELECT
                            IDDongSP,
                            COUNT(*) AS SoLuongDSP
                        FROM
                            dbo.ChiTietSP
                        WHERE 
                            Deleted = 1
                        GROUP BY
                            IDDongSP
                    ),
                    CTE_RN AS (
                        SELECT
                            CTS.IDImel,
                            CTS.IDNSX,
                            CTS.IDDongSP,
                            CTS.IDMauSac,
                            CTS.IDPin,
                            CTS.IDManHinh,
                            CTS.IDRam,
                            CTS.IDBoNho,
                            CTS.IDCPU,
                            CTS.IDCamSau,
                            CTS.IDCamTruoc,
                            Imel.Imel,
                            DS.TenDSP,
                            NSX.TenNsx,
                            Pin.DungLuongPin,
                            ManHinh.LoaiManHinh,
                            CPU.CPU,
                            Ram.DungLuongRam,
                            BoNho.DungLuongBoNho,
                            MauSac.TenMau,
                            CTS.GiaBan,
                            CTS.GhiChu,
                            DC.SoLuongDSP,
                            CameraSau.SoMP AS CameraSau,
                            CameraTruoc.SoMP AS CameraTruoc,
                            CTS.ID,
                            ROW_NUMBER() OVER(PARTITION BY CTS.IDDongSP ORDER BY CTS.ID DESC) AS RN
                        FROM
                            dbo.ChiTietSP AS CTS
                        INNER JOIN 
                            DSP_Count AS DC ON CTS.IDDongSP = DC.IDDongSP
                        INNER JOIN 
                            dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID
                        INNER JOIN 
                            dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                        INNER JOIN 
                            dbo.Pin ON CTS.IDPin = Pin.ID
                        INNER JOIN 
                            dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                        INNER JOIN 
                            dbo.CPU ON CTS.IDCPU = CPU.ID
                        INNER JOIN 
                            dbo.Ram ON CTS.IDRam = Ram.ID
                        INNER JOIN 
                            dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                        INNER JOIN 
                            dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                        INNER JOIN 
                            dbo.CameraSau ON CTS.IDCamSau = CameraSau.ID
                        INNER JOIN 
                            dbo.CameraTruoc ON CTS.IDCamTruoc = CameraTruoc.ID
                        INNER JOIN 
                            dbo.Imel ON CTS.IDImel = Imel.ID
                        WHERE 
                            CTS.Deleted = 1
                    )
                    SELECT 
                        Imel,
                        IDNSX,
                        IDDongSP,
                        IDMauSac,
                        IDPin,
                        IDManHinh,
                        IDRam,
                        IDBoNho,
                        IDCPU,
                        IDCamSau,
                        IDCamTruoc,
                        TenDsp,
                        TenNsx,
                        DungLuongPin,
                        LoaiManHinh,
                        CPU,
                        DungLuongRam,
                        DungLuongBoNho,
                        TenMau,
                        GiaBan,
                        GhiChu,
                        SoLuongDSP,
                        CameraSau,
                        CameraTruoc,
                        ID    
                    FROM 
                        CTE_RN
                    WHERE 
                        RN = 1
                    ORDER BY 
                        ID DESC;
    """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPhamViewModel spvm = new ChiTietSanPhamViewModel();
                spvm.setImel(rs.getString(1));
                spvm.setIdNsx(rs.getString(2));
                spvm.setIdDsp(rs.getString(3));
                spvm.setIdMauSac(rs.getString(4));
                spvm.setIdPin(rs.getString(5));
                spvm.setIdManHinh(rs.getString(6));
                spvm.setIdRam(rs.getString(7));
                spvm.setIdboNho(rs.getString(8));
                spvm.setIdCpu(rs.getString(9));
                spvm.setIdCameraSau(rs.getString(10));
                spvm.setIdCameraTruoc(rs.getString(11));
                spvm.setTenDsp(rs.getString(12));
                spvm.setTenNsx(rs.getString(13));
                spvm.setDungLuongPin(rs.getString(14));
                spvm.setLoaiManHinh(rs.getString(15));
                spvm.setCpu(rs.getString(16));
                spvm.setDungLuongRam(rs.getString(17));
                spvm.setDungLuongBoNho(rs.getString(18));
                spvm.setTenMau(rs.getString(19));
                spvm.setGiaBan(rs.getBigDecimal(20));
                spvm.setGhiChu(rs.getString(21));
                spvm.setSoLuong(rs.getInt(22));
                spvm.setCameraSau(rs.getString(23));
                spvm.setCameraTruoc(rs.getString(24));
                spvm.setId(rs.getString(25));
                listSP.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public boolean xuatSanPham() {
        try ( Connection connection = DBConnect.getConnection()) {
            String query = """
            SELECT
                                CTS.IDImel,
                                CTS.IDNSX,
                                CTS.IDDongSP,
                                CTS.IDMauSac,
                                CTS.IDPin,
                                CTS.IDManHinh,
                                CTS.IDRam,
                                CTS.IDBoNho,
                                CTS.IDCPU,
                		Imel.Imel,
                                CTS.IDCamSau,
                		CTS.IDCamTruoc,
                                DS.TenDSP,
                                NSX.TenNsx,
                                Pin.DungLuongPin,
                                ManHinh.LoaiManHinh,
                                CPU.CPU,
                                Ram.DungLuongRam,
                                BoNho.DungLuongBoNho,
                                MauSac.TenMau,
                                CTS.GiaBan,
                                CTS.GhiChu,
                                CameraSau.SoMP,
                		CameraTruoc.SoMP,
                                CTS.ID
                            FROM
                                dbo.ChiTietSP AS CTS
                                INNER JOIN dbo.NhaSanXuat AS NSX ON CTS.IDNSX = NSX.ID
                                INNER JOIN dbo.DongSP AS DS ON CTS.IDDongSP = DS.ID
                                INNER JOIN dbo.Pin ON CTS.IDPin = Pin.ID
                                INNER JOIN dbo.ManHinh ON CTS.IDManHinh = ManHinh.ID
                                INNER JOIN dbo.CPU ON CTS.IDCPU = CPU.ID
                                INNER JOIN dbo.Ram ON CTS.IDRam = Ram.ID
                                INNER JOIN dbo.BoNho ON CTS.IDBoNho = BoNho.ID
                                INNER JOIN dbo.MauSac ON CTS.IDMauSac = MauSac.ID
                		INNER JOIN dbo.CameraSau ON CTS.IDCamSau = CameraSau.ID
                		INNER JOIN dbo.CameraTruoc ON CTS.IDCamTruoc = CameraTruoc.ID
                		INNER JOIN dbo.Imel ON CTS.IDImel = Imel.ID
                            WHERE
                                CTS.Deleted = 1
                            GROUP BY
                                CTS.IDImel,
                                CTS.IDNSX,
                                CTS.IDDongSP,
                                CTS.IDMauSac,
                                CTS.IDPin,
                                CTS.IDManHinh,
                                CTS.IDRam,
                                CTS.IDBoNho,
                                CTS.IDCPU,
                		Imel.Imel,
                                CTS.IDCamSau,
                		CTS.IDCamTruoc,
                                DS.TenDSP,
                                NSX.TenNsx,
                                Pin.DungLuongPin,
                                ManHinh.LoaiManHinh,
                                CPU.CPU,
                                Ram.DungLuongRam,
                                BoNho.DungLuongBoNho,
                                MauSac.TenMau,
                                CTS.GiaBan,
                                CTS.GhiChu,
                                CameraSau.SoMP,
                		CameraTruoc.SoMP,
                                CTS.ID
                            ORDER BY
                                CTS.ID DESC
            """;

            try ( PreparedStatement statement = connection.prepareStatement(query);  ResultSet resultSet = statement.executeQuery()) {

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách sản phẩm");

                // Tạo phông in đậm
                Font font = workbook.createFont();
                font.setBold(true);
                CellStyle style = workbook.createCellStyle();
                style.setFont(font);

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                Row headerRow = sheet.createRow(0);

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(columnName);
                    cell.setCellStyle(style);
                }

                int rowIndex = 1;
                while (resultSet.next()) {
                    Row row = sheet.createRow(rowIndex++);

                    row.createCell(0).setCellValue(resultSet.getString("IDImel"));
                    row.createCell(1).setCellValue(resultSet.getString("IDNSX"));
                    row.createCell(2).setCellValue(resultSet.getString("IDDongSP"));
                    row.createCell(3).setCellValue(resultSet.getString("IDMauSac"));
                    row.createCell(4).setCellValue(resultSet.getString("IDPin"));
                    row.createCell(5).setCellValue(resultSet.getString("IDManHinh"));
                    row.createCell(6).setCellValue(resultSet.getString("IDRam"));
                    row.createCell(7).setCellValue(resultSet.getString("IDBoNho"));
                    row.createCell(8).setCellValue(resultSet.getString("IDCPU"));
                    row.createCell(9).setCellValue(resultSet.getString("Imel"));
                    row.createCell(10).setCellValue(resultSet.getString("IDCamSau"));
                    row.createCell(11).setCellValue(resultSet.getString("IDCamTruoc"));
                    row.createCell(12).setCellValue(resultSet.getString("TenDsp"));
                    row.createCell(13).setCellValue(resultSet.getString("TenNSX"));
                    row.createCell(14).setCellValue(resultSet.getString("DungLuongPin"));
                    row.createCell(15).setCellValue(resultSet.getString("LoaiManHinh"));
                    row.createCell(16).setCellValue(resultSet.getString("CPU"));
                    row.createCell(17).setCellValue(resultSet.getString("DungLuongRam"));
                    row.createCell(18).setCellValue(resultSet.getString("DungLuongBoNho"));
                    row.createCell(19).setCellValue(resultSet.getString("TenMau"));
                    row.createCell(20).setCellValue(resultSet.getDouble("GiaBan"));
                    row.createCell(21).setCellValue(resultSet.getString("GhiChu"));
                    row.createCell(22).setCellValue(resultSet.getString("SoMP"));
                    row.createCell(23).setCellValue(resultSet.getString("SoMP"));
                    row.createCell(24).setCellValue(resultSet.getString("ID"));
                }

                String fileName = "DanhSachSanPham_" + System.currentTimeMillis() + ".xlsx";
                try ( FileOutputStream fileOut = new FileOutputStream(fileName)) {
                    workbook.write(fileOut);
                }
                System.out.println("Đã xuất file Excel: " + fileName);
                return true;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}