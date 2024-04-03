package mobileworld.service.BanHangService;

import java.util.List;
import mobileworld.model.HoaDon;
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
    
    public boolean addNewBlankInvoice(HoaDon hd, String idNV) {
        return repo.addNewBlankInvoice(hd, idNV);
    }
    
    public List<HoaDonViewModel> searchHD(String text) {
        return repo.searchHD(text);
    }
    
    public boolean deleteHD(String idHD) {
        return repo.deleteHD(idHD);
    }
    
    public List<ChiTietSanPhamViewModel> LocSP(String Nsx, String Pin, String ManHinh, String Cpu) {
        return repo.LocSP(Nsx, Pin, ManHinh, Cpu);
    }
    
    public List<ChiTietSanPhamViewModel> search(String keyword) {
        return repo.search(keyword);
    }
    
    public List<ChiTietSanPhamViewModel> getGioHang(String imel) {
        return repo.getGioHang(imel);
    }
    
    public List<ChiTietSanPhamViewModel> deleteGioHang(String imel) {
        return repo.deleteGioHang(imel);
    }
    
    public List<ChiTietSanPhamViewModel> selectIdDSP(String idDsp) {
        return repo.selectIdDSP(idDsp);
    }
    
    public List<ChiTietSanPhamViewModel> deleteIdDSP(String idDsp) {
        return repo.deleteIdDSP(idDsp);
    }
    
    public boolean ThanhToanHD(HoaDon hd, String idHD) {
        return repo.ThanhToanHD(hd, idHD);
    }
}
