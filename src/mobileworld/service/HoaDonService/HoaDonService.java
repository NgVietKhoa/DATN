/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.HoaDonService;

import java.math.BigDecimal;
import java.util.List;
import mobileworld.entity.HoaDon;
import mobileworld.repository.HoaDonRepo.HoaDonRepository;
import mobileworld.viewModel.HoaDonModel;

/**
 *
 * @author ADMIN
 */
public class HoaDonService {

    HoaDonRepository repo = new HoaDonRepository();

//    public List<HoaDonModel> getAll() {
//        return repo.getAll();
//    }

    public List<HoaDonModel> getAllHD() {
        return repo.getAllHD();
    }

    public List<HoaDonModel> search(String ten) {
        return repo.search(ten);
    }

    public boolean xuatHoaDon() {
        return repo.xuatHoaDon();
    }

    public boolean inHD(String invoiceId) {
        return repo.inHD(invoiceId);
    }

    public List<HoaDonModel> searchGia(BigDecimal giaTu, BigDecimal giaDen) {
        return repo.searchGia(giaTu, giaDen);
    }

    public List<HoaDonModel> hinhThucHoaDon(String hthd) {
        return repo.hinhThucHoaDon(hthd);
    }

    public List<HoaDonModel> trangThaiHoaDon(int trangThai) {
        return repo.trangThaiHoaDon(trangThai);
    }

    public List<HoaDonModel> getAllQR(String result) {
        return repo.getAllQR(result);
    }
        public List<HoaDonModel> filterHoaDon(String ht, int trangThai) {
            return repo.filterHoaDon(ht, trangThai);
        }


}
