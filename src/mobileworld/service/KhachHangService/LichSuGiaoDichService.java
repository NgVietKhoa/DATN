/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.KhachHangService;

import java.util.List;
import mobileworld.repository.KhachHangRepo.LichSuGiaoDichRepo;
import mobileworld.viewModel.LichSuGiaoDichViewModel;

/**
 *
 * @author Admin
 */
public class LichSuGiaoDichService {

    private LichSuGiaoDichRepo repo = new LichSuGiaoDichRepo();

    public List<LichSuGiaoDichViewModel> searchLSGG(String search) {
        return repo.searchLSGG(search);
    }

}
