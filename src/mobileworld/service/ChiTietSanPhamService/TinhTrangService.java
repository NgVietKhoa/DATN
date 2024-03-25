package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.entity.TinhTrangEntity;
import mobileworld.repository.ChiTietSanPhamRepo.TinhTrangRepository;

public class TinhTrangService {

    TinhTrangRepository repo = new TinhTrangRepository();

    public List<TinhTrangEntity> getAll() {
        return repo.getAll();
    }
}
