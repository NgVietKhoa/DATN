package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.BoNho;
import mobileworld.repository.ChiTietSanPhamRepo.BoNhoRepository;

public class BoNhoService {

    BoNhoRepository repo = new BoNhoRepository();

    public List<BoNho> getAll() {
        return repo.getAll();
    }

    public boolean add(BoNho bn) {
        return repo.add(bn);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(BoNho bn, String id) {
        return repo.update(bn, id);
    }
}
