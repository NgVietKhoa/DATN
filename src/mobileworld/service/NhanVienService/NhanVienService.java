package mobileworld.service.NhanVienService;

import java.util.List;
import mobileworld.model.NhanVien;
import mobileworld.repository.NhanVienRepo.NhanVienRepository;

public class NhanVienService {

    NhanVienRepository repo = new NhanVienRepository();

    public List<NhanVien> getAll() {
        return repo.getAll();
    }

    public boolean checkLogin(String maNhanVien, char[] password) {
        return repo.checkLogin(maNhanVien, password);
    }

    public String getChucVuFromDatabase(String chucVuId) {
        return repo.getChucVuFromDatabase(chucVuId);
    }
}
