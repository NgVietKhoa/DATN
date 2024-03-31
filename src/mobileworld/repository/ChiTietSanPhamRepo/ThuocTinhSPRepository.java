package mobileworld.repository.ChiTietSanPhamRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mobileworld.config.DBConnect;
import mobileworld.model.BoNho;
import mobileworld.model.CPU;
import mobileworld.model.CameraSau;
import mobileworld.model.CameraTruoc;
import mobileworld.model.DongSP;
import mobileworld.model.ManHinh;
import mobileworld.model.MauSac;
import mobileworld.model.Pin;
import mobileworld.model.Ram;

public class ThuocTinhSPRepository {

    public List<BoNho> getAllBoNho() {
        List<BoNho> listBN = new ArrayList<>();

        String sql = """
            select ID,DungLuongBoNho from BoNho WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BoNho bn = new BoNho();
                bn.setId(rs.getString(1));
                bn.setDungLuongBoNho(rs.getString(2));
                listBN.add(bn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBN;
    }

    public List<BoNho> getTenBoNho() {
        List<BoNho> listBN = new ArrayList<>();

        String sql = """
            select DungLuongBoNho from BoNho WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BoNho bn = new BoNho();
                bn.setDungLuongBoNho(rs.getString(1));
                listBN.add(bn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBN;
    }

    public boolean addBoNho(BoNho bn) {
        String sql = """
                            INSERT INTO [dbo].[BoNho]
                            ([DungLuongBoNho]
                            ,[Deleted]
                            ,[CreatedAt]
                            ,[CreatedBy])
                      VALUES
                            (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, bn.getDungLuongBoNho());
            ps.setObject(2, bn.getDeleted());
            ps.setObject(3, bn.getCreatedAt());
            ps.setObject(4, bn.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removeBoNho(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[BoNho]
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

    public boolean updateBoNho(BoNho bn, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[BoNho]
                    SET [DungLuongBoNho] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
                    WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, bn.getDungLuongBoNho());
            ps.setObject(2, bn.getDeleted());
            ps.setObject(3, bn.getCreatedAt());
            ps.setObject(4, bn.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<CPU> getAllCPU() {
        List<CPU> listCpu = new ArrayList<>();

        String sql = """
            select ID,CPU from CPU WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CPU cpu = new CPU();
                cpu.setId(rs.getString(1));
                cpu.setCpu(rs.getString(2));
                listCpu.add(cpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCpu;
    }

    public List<CPU> getTenCPU() {
        List<CPU> listCpu = new ArrayList<>();

        String sql = """
            select CPU from CPU WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CPU cpu = new CPU();
                cpu.setCpu(rs.getString(1));
                listCpu.add(cpu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCpu;
    }

    public boolean addCPU(CPU cpu) {
        String sql = """
                     INSERT INTO [dbo].[CPU]
                     ([CPU]
                    ,[Deleted]
                    ,[CreatedAt]
                    ,[CreatedBy])
                    VALUES
                    (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cpu.getCpu());
            ps.setObject(2, cpu.getDeleted());
            ps.setObject(3, cpu.getCreatedAt());
            ps.setObject(4, cpu.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removeCPU(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[CPU]
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

    public boolean updateCPU(CPU cpu, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[CPU]
                    SET [CPU] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
                WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cpu.getCpu());
            ps.setObject(2, cpu.getDeleted());
            ps.setObject(3, cpu.getCreatedAt());
            ps.setObject(4, cpu.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<ManHinh> getAllManHinh() {
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
    
    public List<ManHinh> getLoaiManHinh() {
        List<ManHinh> listMH = new ArrayList<>();

        String sql = """
            select LoaiManHinh from ManHinh WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ManHinh mh = new ManHinh();
                mh.setLoaiManHinh(rs.getString(1));
                listMH.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMH;
    }

    public boolean addManHinh(ManHinh mh) {
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

    public boolean removeManHinh(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[ManHinh]
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

    public boolean updateManHinh(ManHinh mh, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[ManHinh]
                    SET [LoaiManHinh] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
                WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, mh.getLoaiManHinh());
            ps.setObject(2, mh.getDeleted());
            ps.setObject(3, mh.getCreatedAt());
            ps.setObject(4, mh.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<MauSac> getAllMauSac() {
        List<MauSac> listMS = new ArrayList<>();

        String sql = """
            select ID,TenMau from MauSac WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MauSac ms = new MauSac();
                ms.setId(rs.getString(1));
                ms.setTenMau(rs.getString(2));
                listMS.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMS;
    }
    
    public List<MauSac> getTenMauSac() {
        List<MauSac> listMS = new ArrayList<>();

        String sql = """
            select TenMau from MauSac WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MauSac ms = new MauSac();
                ms.setTenMau(rs.getString(1));
                listMS.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMS;
    }

    public boolean addMauSac(MauSac ms) {
        String sql = """
                     INSERT INTO [dbo].[MauSac]
                                ([TenMau]
                                ,[Deleted]
                                ,[CreatedAt]
                                ,[CreatedBy])
                          VALUES
                                (?,?,?,?)
                     """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ms.getTenMau());
            ps.setObject(2, ms.getDeleted());
            ps.setObject(3, ms.getCreatedAt());
            ps.setObject(4, ms.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removeMauSac(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[MauSac]
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

    public boolean updateMauSac(MauSac ms, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[MauSac]
                     SET [TenMau] = ?
                        ,[Deleted] = ?
                        ,[UpdatedAt] = ?
                        ,[UpdatedBy] = ?
                   WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ms.getTenMau());
            ps.setObject(2, ms.getDeleted());
            ps.setObject(3, ms.getCreatedAt());
            ps.setObject(4, ms.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<Pin> getAllPin() {
        List<Pin> listPin = new ArrayList<>();

        String sql = """
            select ID,DungLuongPin from Pin WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pin pin = new Pin();
                pin.setId(rs.getString(1));
                pin.setDungLuongPin(rs.getString(2));
                listPin.add(pin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPin;
    }

    public List<Pin> getTenPin() {
        List<Pin> listPin = new ArrayList<>();

        String sql = """
            select DungLuongPin from Pin WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pin pin = new Pin();
                pin.setDungLuongPin(rs.getString(1));
                listPin.add(pin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPin;
    }

    public boolean addPin(Pin pin) {
        String sql = """
                            INSERT INTO [dbo].[Pin]
                            ([DungLuongPin]
                            ,[Deleted]
                            ,[CreatedAt]
                            ,[CreatedBy])
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

    public boolean removePin(String id) {
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

    public boolean updatePin(Pin pin, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[Pin]
                    SET [DungLuongPin] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
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

    public List<Ram> getAllRam() {
        List<Ram> listRam = new ArrayList<>();

        String sql = """
            select ID,DungLuongRam from Ram WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ram ram = new Ram();
                ram.setId(rs.getString(1));
                ram.setDungLuongRam(rs.getString(2));
                listRam.add(ram);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRam;
    }
    
    public List<Ram> getDungLuongRam() {
        List<Ram> listRam = new ArrayList<>();

        String sql = """
            select DungLuongRam from Ram WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ram ram = new Ram();
                ram.setDungLuongRam(rs.getString(1));
                listRam.add(ram);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRam;
    }

    public boolean addRam(Ram ram) {
        String sql = """
                            INSERT INTO [dbo].[Ram]
                            ([DungLuongRam]
                            ,[Deleted]
                            ,[CreatedAt]
                            ,[CreatedBy])
                      VALUES
                            (?,?,?,?)
                 """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ram.getDungLuongRam());
            ps.setObject(2, ram.getDeleted());
            ps.setObject(3, ram.getCreatedAt());
            ps.setObject(4, ram.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removeRam(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[Ram]
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

    public boolean updateRam(Ram ram, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[Ram]
                    SET [DungLuongRam] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
                WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ram.getDungLuongRam());
            ps.setObject(2, ram.getDeleted());
            ps.setObject(3, ram.getCreatedAt());
            ps.setObject(4, ram.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<CameraSau> getAllCameraSau() {
        List<CameraSau> listCam = new ArrayList<>();

        String sql = """
              select ID,SoMP from CameraSau WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CameraSau cam = new CameraSau();
                cam.setId(rs.getString(1));
                cam.setSoMP(rs.getString(2));
                listCam.add(cam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCam;
    }
    
    public List<CameraSau> getCameraSau() {
        List<CameraSau> listCam = new ArrayList<>();

        String sql = """
              select SoMP from CameraSau WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CameraSau cam = new CameraSau();
                cam.setSoMP(rs.getString(1));
                listCam.add(cam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCam;
    }

    public boolean addCameraSau(CameraSau cam) {
        String sql = """
                     INSERT INTO [dbo].[CameraSau]
                     ([SoMP]
                     ,[Deleted]
                     ,[CreatedAt]
                     ,[CreatedBy])
                     VALUES
                     (?,?,?,?)
                     """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cam.getSoMP());
            ps.setObject(2, cam.getDeleted());
            ps.setObject(3, cam.getCreatedAt());
            ps.setObject(4, cam.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removeCameraSau(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[CameraSau]
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

    public boolean updateCameraSau(CameraSau cam, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[CameraSau]
                    SET [SoMP] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
                    WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cam.getSoMP());
            ps.setObject(2, cam.getDeleted());
            ps.setObject(3, cam.getCreatedAt());
            ps.setObject(4, cam.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<CameraTruoc> getAllCameraTruoc() {
        List<CameraTruoc> listCam = new ArrayList<>();

        String sql = """
                select ID,SoMP from CameraTruoc WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CameraTruoc cam = new CameraTruoc();
                cam.setId(rs.getString(1));
                cam.setSoMP(rs.getString(2));
                listCam.add(cam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCam;
    }
    
    public List<CameraTruoc> getCameraTruoc() {
        List<CameraTruoc> listCam = new ArrayList<>();

        String sql = """
                select SoMP from CameraTruoc WHERE Deleted = 1 ORDER BY ID DESC
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CameraTruoc cam = new CameraTruoc();
                cam.setSoMP(rs.getString(1));
                listCam.add(cam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCam;
    }

    public boolean addCameraTruoc(CameraTruoc cam) {
        String sql = """
                     INSERT INTO [dbo].[CameraTruoc]
                     ([SoMP]
                     ,[Deleted]
                     ,[CreatedAt]
                     ,[CreatedBy])
                     VALUES
                     (?,?,?,?)
                     """;
        int check = 0;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cam.getSoMP());
            ps.setObject(2, cam.getDeleted());
            ps.setObject(3, cam.getCreatedAt());
            ps.setObject(4, cam.getCreatedBy());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean removeCameraTruoc(String id) {
        int check = 0;
        String sql = """
                 UPDATE [dbo].[CameraTruoc]
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

    public boolean updateCameraTruoc(CameraTruoc cam, String id) {
        int check = 0;
        String sql = """
                UPDATE [dbo].[CameraTruoc]
                    SET [SoMP] = ?
                       ,[Deleted] = ?
                       ,[UpdatedAt] = ?
                       ,[UpdatedBy] = ?
                    WHERE ID = ?
                 """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, cam.getSoMP());
            ps.setObject(2, cam.getDeleted());
            ps.setObject(3, cam.getCreatedAt());
            ps.setObject(4, cam.getCreatedBy());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public List<DongSP> getTenDsp() {
        List<DongSP> listDsp = new ArrayList<>();

        String sql = """
                SELECT TenDsp FROM DongSP WHERE Deleted = 1
        """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DongSP dsp = new DongSP();
                dsp.setTenDsp(rs.getString(1));
                listDsp.add(dsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDsp;
    }
}
