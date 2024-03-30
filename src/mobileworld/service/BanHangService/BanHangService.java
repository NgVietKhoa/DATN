package mobileworld.service.BanHangService;

import java.util.List;
import mobileworld.repository.BanHangRepo.BanHangRepository;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import mobileworld.viewModel.ChiTietSanPhamViewModel;

public class BanHangService {

    BanHangRepository repo = new BanHangRepository();

    public List<HoaDonViewModel> getHD() {
        return repo.getHD();
    }

    public List<ChiTietSanPhamViewModel> getSP() {
        return repo.getSP();
    }
}
