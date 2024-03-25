/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.entity;

import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class PhieuGiamGia {

    private String ID;
    private String tenGiamGia;
    private int soLuongDung;
    private float phanTramGiam;
    private float soTiemGiamToiDa;
    private float HoatDonToiThieu;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private int trangThai;
    private String moTa;
    private int deleted;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public PhieuGiamGia() {
    }

    public PhieuGiamGia(String tenGiamGia, int soLuongDung, float phanTramGiam, float soTiemGiamToiDa, float HoatDonToiThieu, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, int trangThai, String moTa, int deleted, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        this.tenGiamGia = tenGiamGia;
        this.soLuongDung = soLuongDung;
        this.phanTramGiam = phanTramGiam;
        this.soTiemGiamToiDa = soTiemGiamToiDa;
        this.HoatDonToiThieu = HoatDonToiThieu;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenGiamGia() {
        return tenGiamGia;
    }

    public void setTenGiamGia(String tenGiamGia) {
        this.tenGiamGia = tenGiamGia;
    }

    public int getSoLuongDung() {
        return soLuongDung;
    }

    public void setSoLuongDung(int soLuongDung) {
        this.soLuongDung = soLuongDung;
    }

    public float getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(float phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public float getSoTiemGiamToiDa() {
        return soTiemGiamToiDa;
    }

    public void setSoTiemGiamToiDa(float soTiemGiamToiDa) {
        this.soTiemGiamToiDa = soTiemGiamToiDa;
    }

    public float getHoatDonToiThieu() {
        return HoatDonToiThieu;
    }

    public void setHoatDonToiThieu(float HoatDonToiThieu) {
        this.HoatDonToiThieu = HoatDonToiThieu;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    
    
}
