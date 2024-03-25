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

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(ManHinh mh, String id) {
        return repo.update(mh, id);
    }
}
