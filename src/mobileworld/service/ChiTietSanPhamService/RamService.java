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
}
