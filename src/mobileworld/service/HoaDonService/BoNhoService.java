package mobileworld.service;

import java.util.List;
import mobileworld.entity.BoNhoEntity;
import mobileworld.repository.BoNhoRepository;

public class BoNhoService {

    BoNhoRepository repo = new BoNhoRepository();

    public List<BoNhoEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(BoNhoEntity bn) {
        return repo.add(bn);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(BoNhoEntity bn, String id) {
        return repo.update(bn, id);
    }
}
