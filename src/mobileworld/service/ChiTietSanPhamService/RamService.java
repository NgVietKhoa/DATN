package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.Ram;
import mobileworld.repository.ChiTietSanPhamRepo.RamRepository;

public class RamService {

    RamRepository repo = new RamRepository();

    public List<Ram> getAll() {
        return repo.getAll();
    }

    public boolean add(Ram ram) {
        return repo.add(ram);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(Ram ram, String id) {
        return repo.update(ram, id);
    }
}
