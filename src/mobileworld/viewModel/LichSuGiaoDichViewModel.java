/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel;

/**
 *
 * @author Admin
 */
public class LichSuGiaoDichViewModel {
    private String IDNV;
    private String IDKH;
    private String tenKH;
    private String mail;
    private String diaChi;
    private String sdt;
    private int trangThai;
    private float tongTien;

    public LichSuGiaoDichViewModel() {
    }

    public LichSuGiaoDichViewModel(String IDNV, String IDKH, String tenKH, String mail, String diaChi, String sdt, int trangThai, float tongTien) {
        this.IDNV = IDNV;
        this.IDKH = IDKH;
        this.tenKH = tenKH;
        this.mail = mail;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public String getIDNV() {
        return IDNV;
    }

    public void setIDNV(String IDNV) {
        this.IDNV = IDNV;
    }

    public String getIDKH() {
        return IDKH;
    }

    public void setIDKH(String IDKH) {
        this.IDKH = IDKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
            
}
