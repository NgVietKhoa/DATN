package mobileworld.service;

import java.util.List;
import mobileworld.entity.ManHinhEntity;
import mobileworld.repository.ManHinhRepository;

public class ManHinhService {

    ManHinhRepository repo = new ManHinhRepository();

    public List<ManHinhEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(ManHinhEntity mh) {
        return repo.add(mh);

    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(ManHinhEntity mh, String id) {
        return repo.update(mh, id);
    }
}
