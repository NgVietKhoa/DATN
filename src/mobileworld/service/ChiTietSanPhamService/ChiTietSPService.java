package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.ChiTietSP;
import mobileworld.repository.ChiTietSanPhamRepo.ChiTietSPRepository;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class ChiTietSPService {

    private final ChiTietSPRepository repo = new ChiTietSPRepository();

    public List<ChiTietSanPhamViewModel> getAll() {
        return repo.getAll();
    }

    public List<ChiTietSanPhamViewModel> getSP(String idDSP) {
        return repo.getSP(idDSP);
    }

    public List<ChiTietSanPhamViewModel> getSPImel(String idDsp) {
        return repo.getSPImel(idDsp);
    }

    public boolean add(ChiTietSP ctsp) {
        return repo.add(ctsp);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(ChiTietSP ctsp, String id) {
        return repo.update(ctsp, id);
    }

    public List<ChiTietSanPhamViewModel> getAllCTSP() {
        return repo.getAllCTSP();
    }

    public boolean xuatSanPham(List<String> imels) {
        return repo.xuatSanPham(imels);
    }

    public List<ChiTietSanPhamViewModel> search(String keyword) {
        return repo.search(keyword);
    }

    public List<ChiTietSanPhamViewModel> LocSP(String Nsx, String Pin, String ManHinh, String Cpu, boolean sapXepGiaTangDan) {
        return repo.LocSP(Nsx, Pin, ManHinh, Cpu, sapXepGiaTangDan);
    }

    public List<ChiTietSanPhamViewModel> LocCTSP(String Nsx, String Pin, String ManHinh, String Cpu, boolean sapXepGiaTangDan) {
        return repo.LocCTSP(Nsx, Pin, ManHinh, Cpu, sapXepGiaTangDan);
    }

    public List<ChiTietSanPhamViewModel> getAllQR(String imel) {
        return repo.getAllQR(imel);
    }

}
