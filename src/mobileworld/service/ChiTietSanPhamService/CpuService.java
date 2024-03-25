package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.CPU;
import mobileworld.repository.ChiTietSanPhamRepo.CPURepository;

public class CpuService {

    CPURepository repo = new CPURepository();

    public List<CPU> getAll() {
        return repo.getAll();
    }

    public boolean add(CPU cpu) {
        return repo.add(cpu);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(CPU cpu, String id) {
        return repo.update(cpu, id);
    }
}
