package mobileworld.service.HoaDonService;

import java.math.BigDecimal;
import java.util.List;
import mobileworld.repository.HoaDonRepo.HoaDonRepository;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;

public class HoaDonService {

    HoaDonRepository repo = new HoaDonRepository();

    public List<HoaDonViewModel> getAllHD() {
        return repo.getAllHD();
    }

    public List<HoaDonViewModel> search(String ten) {
        return repo.search(ten);
    }

    public boolean xuatHoaDon() {
        return HoaDonRepository.xuatHoaDon();
    }

    public List<HoaDonViewModel> hinhThucHoaDon(String hthd) {
        return repo.hinhThucHoaDon(hthd);
    }

    public List<HoaDonViewModel> getAllQR(String result) {
        return repo.getAllQR(result);
    }

    public List<HoaDonViewModel> filterHoaDon(String ht, int trangThai) {
        return repo.filterHoaDon(ht, trangThai);
    }

    public List<HoaDonViewModel> getTrangThaiHD(int trangThai) {
        return repo.getTrangThaiHD(trangThai);
    }

    public List<HoaDonViewModel> searchHDByMaxPrice(BigDecimal maxPrice) {
        return repo.searchHDByMaxPrice(maxPrice);
    }
}
