/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.KhachHangService;

import java.util.List;
import mobileworld.model.KhachHang;
import mobileworld.repository.KhachHangRepo.KhachHangRepository;

/**
 *
 * @author thinh
 */
public class KhachHangService {

    private KhachHangRepository repo = new KhachHangRepository();

    public List<KhachHang> getAll() {
        return repo.getAll();
    }

    public boolean add(KhachHang kh) {
        return repo.add(kh);
    }

    public boolean update(KhachHang kh, String ma) {
        return repo.update(kh, ma);
    }

    public boolean delete(String ma) {
        return repo.delete(ma);
    }

    public List<KhachHang> search(String search) {
        return repo.search(search);
    }

}
