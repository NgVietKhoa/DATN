/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.BanHangService;

import java.util.List;
import mobileworld.model.KhachHang;
import mobileworld.repository.BanHangRepo.KHInBanHangRepository;
import mobileworld.viewModel.BanHangViewModel.KhachHangViewModel;

/**
 *
 * @author ADMIN
 */
public class KHInBanHangService {

    KHInBanHangRepository repo = new KHInBanHangRepository();

    public List<KhachHangViewModel> getAll() {
        return repo.getAll();
    }

    public boolean addKH(KhachHang kh) {
        return repo.addKH(kh);
    }

}
