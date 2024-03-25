/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.thread;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import mobileworld.entity.PhieuGiamGia;
import mobileworld.form.ViewGiamGia1;
import mobileworld.service.PhieuGiamGiaService.PhieuGiamGiaService;
import mobileworld.service.PhieuGiamGiaService.PhieuGiamGiaService;

/**
 *
 * @author Admin
 */
public class ThreadTinhTrang extends Thread {

    List<PhieuGiamGia> ds = new ArrayList<>();
    PhieuGiamGia pgg = new PhieuGiamGia();
    private PhieuGiamGiaService sr;

    public ThreadTinhTrang(List<PhieuGiamGia> ds, PhieuGiamGiaService sr) {
        this.ds = ds;
        this.sr = sr;
    }

    @Override
    public void run() {
        int trangThaiKetThuc = 3;
        int trangThaiBatDau = 3;
        float deleted = 3;
        for (PhieuGiamGia pgg : ds) {
            LocalDate ngayBDLocalDate = pgg.getNgayBatDau().toLocalDate();
            LocalDate ngayKTLocalDate = pgg.getNgayKetThuc().toLocalDate();

            if (ngayBDLocalDate.isEqual(LocalDate.now()) && pgg.getDeleted() == 1) {
                trangThaiBatDau = 1;
                deleted = (float) 1.0;
                sr.updateTTThread(pgg.getID(), trangThaiBatDau, deleted);
            }
            
            if (ngayBDLocalDate.isBefore(LocalDate.now()) && ngayKTLocalDate.isBefore(LocalDate.now())) {
                trangThaiKetThuc = 0;
                deleted = (float) 0.0;
                sr.updateTTThread(pgg.getID(), trangThaiKetThuc, deleted);
            }
        }
    }

}
