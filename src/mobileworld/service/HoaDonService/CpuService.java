package mobileworld.service;

import java.util.List;
import mobileworld.entity.CPUEntity;
import mobileworld.repository.CPURepository;

public class CpuService {

    CPURepository repo = new CPURepository();

    public List<CPUEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(CPUEntity cpu) {
        return repo.add(cpu);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(CPUEntity cpu, String id) {
        return repo.update(cpu, id);
    }
}
