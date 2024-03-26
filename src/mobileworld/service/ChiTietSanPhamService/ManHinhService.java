package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.ManHinh;
import mobileworld.repository.ChiTietSanPhamRepo.ManHinhRepository;

public class ManHinhService {

    ManHinhRepository repo = new ManHinhRepository();

    public List<ManHinh> getAll() {
        return repo.getAll();
    }

    public boolean add(ManHinh mh) {
        return repo.add(mh);

    }
}
