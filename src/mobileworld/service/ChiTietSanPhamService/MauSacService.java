package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.MauSac;
import mobileworld.repository.ChiTietSanPhamRepo.MauSacRepository;

public class MauSacService {

    MauSacRepository repo = new MauSacRepository();

    public List<MauSac> getAll() {
        return repo.getAll();
    }

    public boolean add(MauSac ms) {
        return repo.add(ms);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(MauSac ms, String id) {
        return repo.update(ms, id);
    }
}
