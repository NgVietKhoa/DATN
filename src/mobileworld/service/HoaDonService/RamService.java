package mobileworld.service;

import java.util.List;
import mobileworld.entity.RamEntity;
import mobileworld.repository.RamRepository;

public class RamService {

    RamRepository repo = new RamRepository();

    public List<RamEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(RamEntity ram) {
        return repo.add(ram);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(RamEntity ram, String id) {
        return repo.update(ram, id);
    }
}
