/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel.ThongKe;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ThongKeDAO {

    public List<HoaDonTK> getListByHoaDon();

    public float getDoanhThu();

    public float soTienDaThuDuoc();

    public int soHD();

    public float hoaDonChuaThanhToan();

    public List<HoaDonTK> timTheoNam(String Year);

    public List<HoaDonTK> timTheoThoiGian(LocalDate ngayBD, LocalDate ngayKT);
    
    public List<HoaDonTK> hienBang();

    public List<HoaDonTK> timTheoNamTable(String Year);

    public List<HoaDonTK> timTheoThoiGianTable(LocalDate ngayBD, LocalDate ngayKT);

}
