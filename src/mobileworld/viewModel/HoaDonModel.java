/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel;

import java.math.BigDecimal;

import java.time.LocalDateTime;

//SELECT 
//    HoaDon.ID, 
//    HoaDon.IDNhanVien, 
//    HoaDon.TenKhachHang, 
//    HoaDon.SoDienThoaiKhachHang, 
//    HoaDon.DiaChiKhachHang, 
//    HoaDon.NgayThanhToan, 
//    PhuongThucThanhToan.TenKieuThanhToan, 
//    HoaDon.TongTien, 
//    HoaDon.TrangThai
//FROM   
//    dbo.HoaDon 
//INNER JOIN
//    dbo.HinhThucThanhToan ON HoaDon.ID = HinhThucThanhToan.IDHoaDon 
//INNER JOIN
//    dbo.PhuongThucThanhToan ON HinhThucThanhToan.IDPhuongThucThanhToan = PhuongThucThanhToan.ID
/**
 *
 * @author ADMIN
 */
public class HoaDonModel {

    private String idHD;
    private String idNV;
    private String tenKH;
    private String SDTKH;
    private String diaChiKH;
    private LocalDateTime ngayThanhToan;
    private String tenKieuThanhToan;
    private BigDecimal tongTien;
    private int trangThai;

    public HoaDonModel() {
    }

    public HoaDonModel(String idHD, String idNV, String tenKH, String SDTKH, String diaChiKH, LocalDateTime ngayThanhToan, String tenKieuThanhToan, BigDecimal tongTien, int trangThai) {
        this.idHD = idHD;
        this.idNV = idNV;
        this.tenKH = tenKH;
        this.SDTKH = SDTKH;
        this.diaChiKH = diaChiKH;
        this.ngayThanhToan = ngayThanhToan;
        this.tenKieuThanhToan = tenKieuThanhToan;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public String getIdNV() {
        return idNV;
    }

    public void setIdNV(String idNV) {
        this.idNV = idNV;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSDTKH() {
        return SDTKH;
    }

    public void setSDTKH(String SDTKH) {
        this.SDTKH = SDTKH;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTenKieuThanhToan() {
        return tenKieuThanhToan;
    }

    public void setTenKieuThanhToan(String tenKieuThanhToan) {
        this.tenKieuThanhToan = tenKieuThanhToan;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDonModel{" + "idHD=" + idHD + ", idNV=" + idNV + ", tenKH=" + tenKH + ", SDTKH=" + SDTKH + ", diaChiKH=" + diaChiKH + ", ngayThanhToan=" + ngayThanhToan + ", tenKieuThanhToan=" + tenKieuThanhToan + ", tongTien=" + tongTien + ", trangThai=" + trangThai + '}';
    }
    
    

}

//    private String idHoaDon;
//    private String tenKH;
//    private String sdt;
//    private String diaChi;
//    private String idNhV;
//    private String tenNV;
//    private LocalDateTime ngayTao;
//    private LocalDateTime ngayThanhToan;
//    private String idPhuongThucThanhToan;
//    private String kieuThanhToan;
//    private BigDecimal tongTien;
//    private int trangThai;
//    private String ghiChu;
//    private int stt;
//
//    public HoaDonModel() {
//    }
//
//    public HoaDonModel(String idHoaDon, String tenKH, String sdt, String diaChi, String idNhV, String tenNV, LocalDateTime ngayTao, LocalDateTime ngayThanhToan, String idPhuongThucThanhToan, String kieuThanhToan, BigDecimal tongTien, int trangThai, String ghiChu, int stt) {
//        this.idHoaDon = idHoaDon;
//        this.tenKH = tenKH;
//        this.sdt = sdt;
//        this.diaChi = diaChi;
//        this.idNhV = idNhV;
//        this.tenNV = tenNV;
//        this.ngayTao = ngayTao;
//        this.ngayThanhToan = ngayThanhToan;
//        this.idPhuongThucThanhToan = idPhuongThucThanhToan;
//        this.kieuThanhToan = kieuThanhToan;
//        this.tongTien = tongTien;
//        this.trangThai = trangThai;
//        this.ghiChu = ghiChu;
//        this.stt = stt;
//    }
//
//    public String getIdHoaDon() {
//        return idHoaDon;
//    }
//
//    public void setIdHoaDon(String idHoaDon) {
//        this.idHoaDon = idHoaDon;
//    }
//
//    public String getTenKH() {
//        return tenKH;
//    }
//
//    public void setTenKH(String tenKH) {
//        this.tenKH = tenKH;
//    }
//
//    public String getSdt() {
//        return sdt;
//    }
//
//    public void setSdt(String sdt) {
//        this.sdt = sdt;
//    }
//
//    public String getDiaChi() {
//        return diaChi;
//    }
//
//    public void setDiaChi(String diaChi) {
//        this.diaChi = diaChi;
//    }
//
//    public String getIdNhV() {
//        return idNhV;
//    }
//
//    public void setIdNhV(String idNhV) {
//        this.idNhV = idNhV;
//    }
//
//    public String getTenNV() {
//        return tenNV;
//    }
//
//    public void setTenNV(String tenNV) {
//        this.tenNV = tenNV;
//    }
//
//    public LocalDateTime getNgayTao() {
//        return ngayTao;
//    }
//
//    public void setNgayTao(LocalDateTime ngayTao) {
//        this.ngayTao = ngayTao;
//    }
//
//    public LocalDateTime getNgayThanhToan() {
//        return ngayThanhToan;
//    }
//
//    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
//        this.ngayThanhToan = ngayThanhToan;
//    }
//
//    public String getIdPhuongThucThanhToan() {
//        return idPhuongThucThanhToan;
//    }
//
//    public void setIdPhuongThucThanhToan(String idPhuongThucThanhToan) {
//        this.idPhuongThucThanhToan = idPhuongThucThanhToan;
//    }
//
//    public String getKieuThanhToan() {
//        return kieuThanhToan;
//    }
//
//    public void setKieuThanhToan(String kieuThanhToan) {
//        this.kieuThanhToan = kieuThanhToan;
//    }
//
//    public BigDecimal getTongTien() {
//        return tongTien;
//    }
//
//    public void setTongTien(BigDecimal tongTien) {
//        this.tongTien = tongTien;
//    }
//
//    public int getTrangThai() {
//        return trangThai;
//    }
//
//    public void setTrangThai(int trangThai) {
//        this.trangThai = trangThai;
//    }
//
//    public String getGhiChu() {
//        return ghiChu;
//    }
//
//    public void setGhiChu(String ghiChu) {
//        this.ghiChu = ghiChu;
//    }
//
//    public int getStt() {
//        return stt;
//    }
//
//    public void setStt(int stt) {
//        this.stt = stt;
//    }
//
//    @Override
//    public String toString() {
//        return "HoaDonModel{" + "idHoaDon=" + idHoaDon + ", tenKH=" + tenKH + ", sdt=" + sdt + ", diaChi=" + diaChi + ", idNhV=" + idNhV + ", tenNV=" + tenNV + ", ngayTao=" + ngayTao + ", ngayThanhToan=" + ngayThanhToan + ", idPhuongThucThanhToan=" + idPhuongThucThanhToan + ", kieuThanhToan=" + kieuThanhToan + ", tongTien=" + tongTien + ", trangThai=" + trangThai + ", ghiChu=" + ghiChu + ", stt=" + stt + '}';
//    }

