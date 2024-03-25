package mobileworld.service;

import java.util.List;
import mobileworld.entity.PinEntity;
import mobileworld.repository.PinRepository;

public class PinService {

    PinRepository repo = new PinRepository();

    public List<PinEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(PinEntity pin) {
        return repo.add(pin);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(PinEntity pin, String id) {
        return repo.update(pin, id);
    }
}
