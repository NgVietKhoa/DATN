package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.Pin;
import mobileworld.repository.ChiTietSanPhamRepo.PinRepository;

public class PinService {

    PinRepository repo = new PinRepository();

    public List<Pin> getAll() {
        return repo.getAll();
    }

    public boolean add(Pin pin) {
        return repo.add(pin);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(Pin pin, String id) {
        return repo.update(pin, id);
    }
}
