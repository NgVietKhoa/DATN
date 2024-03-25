package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.DongSP;
import mobileworld.repository.ChiTietSanPhamRepo.DongSPRepository;
import mobileworld.viewModel.DongSPViewModel;

public class DongSPService {

    DongSPRepository repo = new DongSPRepository();

    public List<DongSPViewModel> getAll() {
        return repo.getAll();
    }

    public boolean add(DongSP dsp) {
        return repo.add(dsp);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(DongSP sp, String id) {
        return repo.update(sp, id);
    }

    public List<DongSPViewModel> getTenDsp() {
        return repo.getTenDsp();
    }

    public List<DongSPViewModel> search(String search) {
        return repo.search(search);
    }

    public List<DongSPViewModel> getAllConHang() {
        return repo.getAllConHang();
    }

    public List<DongSPViewModel> getAllHetHang() {
        return repo.getAllHetHang();
    }
}
