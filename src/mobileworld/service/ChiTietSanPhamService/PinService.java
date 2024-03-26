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
}
