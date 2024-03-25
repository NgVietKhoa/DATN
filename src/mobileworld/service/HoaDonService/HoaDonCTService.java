/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.HoaDonService;

import java.util.List;
import mobileworld.repository.HoaDonRepo.HoaDonCTRepository;
import mobileworld.viewModel.HoaDonChiTietModel;

/**
 *
 * @author ADMIN
 */
public class HoaDonCTService {
    HoaDonCTRepository repo = new HoaDonCTRepository();
        public List<HoaDonChiTietModel> getAll(String idHD) {
            return repo.getAll(idHD);
        }

}
