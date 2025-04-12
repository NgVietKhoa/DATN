/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.print.print.model;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author VHC
 */
public class ParameterReportPayment {

    private String maHoaDon;
    private String thoiGian;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChi;
    private String thanhToan;
    private String maNV;
    private String tongTien;
    private InputStream QR;
    private String ngayMuonNhan;
    private List<FieldReportPayment> fields;

    public ParameterReportPayment() {
    }

    public ParameterReportPayment(String maHoaDon, String thoiGian, String tenKhachHang, String soDienThoai, String diaChi, String thanhToan, String maNV, String tongTien, InputStream QR, String ngayMuonNhan,List<FieldReportPayment> fields) {
        this.maHoaDon = maHoaDon;
        this.thoiGian = thoiGian;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.thanhToan = thanhToan;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.QR = QR;
        this.ngayMuonNhan = ngayMuonNhan;
        this.fields = fields;
    }

    public ParameterReportPayment(String maHoaDon, String thoiGian, String tenKhachHang, String soDienThoai, String diaChi, String thanhToan, String maNV, String tongTien, InputStream QR, List<FieldReportPayment> fields) {
        this.maHoaDon = maHoaDon;
        this.thoiGian = thoiGian;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.thanhToan = thanhToan;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.QR = QR;
        this.fields = fields;
    }
    
    

    public String getNgayMuonNhan() {
        return ngayMuonNhan;
    }

    public void setNgayMuonNhan(String ngayMuonNhan) {
        this.ngayMuonNhan = ngayMuonNhan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public InputStream getQR() {
        return QR;
    }

    public void setQR(InputStream QR) {
        this.QR = QR;
    }

    public List<FieldReportPayment> getFields() {
        return fields;
    }

    public void setFields(List<FieldReportPayment> fields) {
        this.fields = fields;
    }

}
