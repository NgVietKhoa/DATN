package mobileworld.service.BanHangService;

import java.math.BigDecimal;
import java.util.List;
import mobileworld.model.HinhThucThanhToanEntity;
import mobileworld.model.HoaDon;
import mobileworld.model.HoaDonChiTietEntity;
import mobileworld.model.PhuongThucThanhToan;
import mobileworld.repository.BanHangRepo.BanHangRepository;
import mobileworld.viewModel.BanHangViewModel.HoaDonViewModel;
import mobileworld.viewModel.ChiTietSanPhamViewModel;
import mobileworld.viewModel.LichSuHDModel;

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

    public boolean addNewBlankInvoice(HoaDon hd, LichSuHDModel lshdm) {
        return repo.addNewBlankInvoice(hd, lshdm);
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

    public List<ChiTietSanPhamViewModel> deleteGioHang(List<String> imels) {
        return repo.deleteGioHang(imels);
    }

    public List<ChiTietSanPhamViewModel> selectIdDSP(String idDsp, BigDecimal giaBan) {
        return repo.selectIdDSP(idDsp, giaBan);
    }

    public List<ChiTietSanPhamViewModel> deleteIdDSP(String idDsp, BigDecimal giaBan) {
        return repo.deleteIdDSP(idDsp, giaBan);
    }

    public boolean ThanhToanHD(HoaDon hd, List<HoaDonChiTietEntity> hdctList, String idHD, PhuongThucThanhToan pttt, HinhThucThanhToanEntity httte, LichSuHDModel lshdm) {
        return repo.ThanhToanHD(hd, hdctList, idHD, pttt, httte, lshdm);
    }

    public List<ChiTietSanPhamViewModel> getSPTuHoaDon(String idHD) {
        return repo.getSPTuHoaDon(idHD);
    }

    public boolean updateDeleteHD(String idHD) {
        return repo.updateDeleteHD(idHD);
    }

    public boolean GiaoHang(HoaDon hd, List<HoaDonChiTietEntity> hdctList, String idHD, PhuongThucThanhToan pttt, HinhThucThanhToanEntity httte, LichSuHDModel lshdm) {
        return repo.GiaoHang(hd, hdctList, idHD, pttt, httte, lshdm);
    }

    public boolean HuyGiaoHang(String idHD) {
        return repo.HuyGiaoHang(idHD);
    }

    public boolean GiaoLaiHang(String idHD) {
        return repo.GiaoLaiHang(idHD);
    }

    public List<ChiTietSanPhamViewModel> searchBySelectDsp(String keyword, List<ChiTietSanPhamViewModel> listSpSelect) {
        return repo.searchBySelectDsp(keyword, listSpSelect);
    }

    public List<String> getIdCtspQR(String Imel) {
        return repo.getIdCtspQR(Imel);
    }

    public int countChuaThanhToan() {
        return repo.countChuaThanhToan();
    }

    public int countChoGiaoHang() {
        return repo.countChoGiaoHang();
    }

    public int countDangGiaoHang() {
        return repo.countDangGiaoHang();
    }

    public int countDaThanhToan() {
        return repo.countDaThanhToan();
    }

    public int countAll() {
        return repo.countAll();
    }

    public boolean ThanhToanGiaoHangHD(String idHD) {
        return repo.ThanhToanGiaoHangHD(idHD);
    }

    public List<String> getCTSPFromImels(List<String> idImels) {
        return repo.getCTSPFromImels(idImels);
    }

    public List<String> getIdImelFromImels(List<String> Imels) {
        return repo.getIdImelFromImels(Imels);
    }

    public List<String> getCTSPFromImelsAdd(String idImel) {
        return repo.getCTSPFromImelsAdd(idImel);
    }

    public List<String> getIdImelFromImelsAdd(String Imel) {
        return repo.getIdImelFromImelsAdd(Imel);
    }
}
