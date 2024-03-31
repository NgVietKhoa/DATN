/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.ThongKeService;

import java.time.LocalDate;
import java.util.List;
import mobileworld.viewModel.ThongKe.HoaDonTK;
import mobileworld.viewModel.ThongKe.ThongKeDAO;
import mobileworld.viewModel.ThongKe.ThongKeDAOImplement;

/**
 *
 * @author Admin
 */
public class ThongKeServiceImpl implements ThongKeService {

    private ThongKeDAO tk = null;

    public ThongKeServiceImpl() {
        tk = new ThongKeDAOImplement();
    }

    @Override
    public List<HoaDonTK> getListByHoaDon() {
        return tk.getListByHoaDon();
    }

    @Override
    public float getDoanhThu() {
        return tk.getDoanhThu();
    }

    @Override
    public float soTienDaThuDuoc() {
        return tk.soTienDaThuDuoc();
    }

    @Override
    public int soHD() {
        return tk.soHD();
    }

    @Override
    public float hoaDonChuaThanhToan() {
        return tk.hoaDonChuaThanhToan();
    }

    @Override
    public List<HoaDonTK> timTheoNam(String Year) {
        return tk.timTheoNam(Year);
    }

    @Override
    public List<HoaDonTK> timTheoThoiGian(LocalDate ngayBD, LocalDate ngayKT) {
        return tk.timTheoThoiGian(ngayBD, ngayKT);
    }

    @Override
    public List<HoaDonTK> timTheoNamTable(String Year) {
        return tk.timTheoNamTable(Year);
    }

    @Override
    public List<HoaDonTK> timTheoThoiGianTable(LocalDate ngayBD, LocalDate ngayKT) {
        return tk.timTheoThoiGianTable(ngayBD, ngayKT);
    }

    @Override
    public List<HoaDonTK> hienBang() {
        return tk.hienBang();
    }
    
    
    

}
