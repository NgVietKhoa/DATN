package mobileworld.service.NhanVienService;

import java.util.List;
import mobileworld.model.NhanVien;
import mobileworld.repository.NhanVienRepo.NhanVienRepository;
import mobileworld.viewModel.NhanVienViewModel;

public class NhanVienService {

    NhanVienRepository repo = new NhanVienRepository();

    public List<NhanVienViewModel> getAll() {
        return repo.getAll();
    }

    public List<NhanVienViewModel> getAllNvDaNghi() {
        return repo.getAllNvDaNghi();
    }

    public boolean checkLogin(String maNhanVien, char[] password) {
        return repo.checkLogin(maNhanVien, password);
    }

    public String getChucVuFromDatabase(String chucVuId) {
        return repo.getChucVuFromDatabase(chucVuId);
    }

    public boolean delete(String id) {
        return repo.delete(id);
    }

    public boolean update(NhanVien nv, String oldID) {
        return repo.update(nv, oldID);
    }

    public boolean add(NhanVien nv) {
        return repo.add(nv);
    }

    public List<NhanVienViewModel> search(String search) {
        return repo.search(search);
    }

    public boolean xuatDanhSachNhanVien() {
        return repo.xuatDanhSachNhanVien();
    }
}
