/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel.BanHangViewModel;

/**
 *
 * @author ADMIN
 */
public class KhachHangViewModel {

    private String idKH;
    private String ten;
    private String soDienThoai;
    private String diaChi;
    private float gioiTinh;

    public KhachHangViewModel() {
    }

    public KhachHangViewModel(String idKH, String ten, String soDienThoai, String diaChi, float gioiTinh) {
        this.idKH = idKH;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public float getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(float gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "KhachHangViewModel{" + "idKH=" + idKH + ", ten=" + ten + ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + '}';
    }

}
