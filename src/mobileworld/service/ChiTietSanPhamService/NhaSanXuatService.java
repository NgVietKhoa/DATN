package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.NhaSanXuat;
import mobileworld.repository.ChiTietSanPhamRepo.NhaSanXuatRepository;

public class NhaSanXuatService {

    NhaSanXuatRepository repo = new NhaSanXuatRepository();

    public List<NhaSanXuat> getAll() {
        return repo.getAll();
    }
}
