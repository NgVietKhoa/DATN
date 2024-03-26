package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.CameraTruoc;
import mobileworld.repository.ChiTietSanPhamRepo.CameraTruocRepository;

public class CameraTruocService {

    CameraTruocRepository repo = new CameraTruocRepository();

    public List<CameraTruoc> getAll() {
        return repo.getAll();
    }

    public boolean add(CameraTruoc cam) {
        return repo.add(cam);
    }
}
