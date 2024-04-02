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

    public HoaDonViewModel getNewestHoaDon() {
        // Lấy danh sách hóa đơn từ repository
        List<HoaDonViewModel> listHD = repo.getHD();

        // Nếu danh sách không rỗng, trả về hóa đơn đầu tiên (được sắp xếp theo ngày tạo giảm dần)
        if (!listHD.isEmpty()) {
            return listHD.get(0);
        }

        // Trả về null nếu không có hóa đơn nào
        return null;
    }

    public List<ChiTietSanPhamViewModel> LocSP(String Nsx, String Pin, String ManHinh, String Cpu, boolean sapXepGiaTangDan) {
        return repo.LocSP(Nsx, Pin, ManHinh, Cpu, sapXepGiaTangDan);
    }

    public List<ChiTietSanPhamViewModel> search(String keyword) {
        return repo.search(keyword);
    }

    public List<ChiTietSanPhamViewModel> getGioHang(String idDsp) {
        return repo.getGioHang(idDsp);
    }

    public List<ChiTietSanPhamViewModel> selectIdDSP(String idDsp) {
        return repo.selectIdDSP(idDsp);
    }
}
