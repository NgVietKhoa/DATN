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
}
