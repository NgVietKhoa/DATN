/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.PhieuGiamGiaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mobileworld.entity.PhieuGiamGia;
import mobileworld.repository.PhieuGiamGiaRepo.PhieuGiamGiaRepository;

/**
 *
 * @author Admin
 */
public class PhieuGiamGiaService {

    private PhieuGiamGiaRepository repo = new PhieuGiamGiaRepository();

    public List<PhieuGiamGia> getAll() {
        return repo.getAll();
    }

    public boolean addData(PhieuGiamGia gg) {
        return repo.addData(gg);
    }

    public boolean update(PhieuGiamGia gg, String ID) {
        return repo.update(gg, ID);
    }

  public boolean deleteData(String id, int tt,String maNV, LocalDateTime ngayCN) {
       return repo.deleteData(id, tt, maNV, ngayCN);
   }

    public List<PhieuGiamGia> timTrangThai(int trangThai) {
        return repo.timTrangThai(trangThai);
    }

    public List<PhieuGiamGia> timKiemAll(String All) {
        return repo.timKiemAll(All);
    }

    public List<PhieuGiamGia> timKiemID(String tkID) {
        return repo.timKiemID(tkID);
    }

    public List<PhieuGiamGia> timKiemHoaDonTT(Float tkHD) {
        return repo.timKiemHoaDonTT(tkHD);
    }

    public List<PhieuGiamGia> timKiemTenGG(String tkTen) {
        return repo.timKiemTenGG(tkTen);
    }

    public List<PhieuGiamGia> timKiemTienGiam(Float tkTM) {
        return repo.timKiemTienGiam(tkTM);
    }

    public List<PhieuGiamGia> timKiemNgay(LocalDate ngayBD, LocalDate ngayKT) {
        return repo.timKiemNgay(ngayBD, ngayKT);
    }

    public List<PhieuGiamGia> timKiemSoLan(int soLan) {
        return repo.timKiemSoLan(soLan);
    }

    public List<PhieuGiamGia> timKiemPhanTram(float phanTram) {
        return repo.timKiemPhanTram(phanTram);
    }

  public boolean updateTTThread(String ID, int trangThai,float Deleted) {
      return repo.updateTTThread(ID, trangThai, Deleted);
    }

}
