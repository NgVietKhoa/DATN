package mobileworld.service;

import java.util.List;
import mobileworld.entity.DongSPEntity;
import mobileworld.repository.DongSPRepository;

public class DongSPService {

    DongSPRepository repo = new DongSPRepository();

    public List<DongSPEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(DongSPEntity dsp) {
        return repo.add(dsp);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(DongSPEntity sp, String id) {
        return repo.update(sp, id);
    }
}
