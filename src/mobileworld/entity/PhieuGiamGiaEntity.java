package mobileworld.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PhieuGiamGiaEntity {

    private String tenGiamGia;
    private float soLuongDung;
    private float phanTramGiam;
    private BigDecimal soTienGiamToiDa;
    private BigDecimal hoaDonToiThieu;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private float trangThai;
    private String moTa;
    private float deleted;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updateBy;
    private String id;

    public PhieuGiamGiaEntity() {
    }

    public PhieuGiamGiaEntity(String tenGiamGia, float soLuongDung, float phanTramGiam, BigDecimal soTienGiamToiDa, BigDecimal hoaDonToiThieu, Date ngayBatDau, Date ngayKetThuc, float trangThai, String moTa, float deleted, Date createdAt, String createdBy, Date updatedAt, String updateBy, String id) {
        this.tenGiamGia = tenGiamGia;
        this.soLuongDung = soLuongDung;
        this.phanTramGiam = phanTramGiam;
        this.soTienGiamToiDa = soTienGiamToiDa;
        this.hoaDonToiThieu = hoaDonToiThieu;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public String getTenGiamGia() {
        return tenGiamGia;
    }

    public void setTenGiamGia(String tenGiamGia) {
        this.tenGiamGia = tenGiamGia;
    }

    public float getSoLuongDung() {
        return soLuongDung;
    }

    public void setSoLuongDung(float soLuongDung) {
        this.soLuongDung = soLuongDung;
    }

    public float getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(float phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public BigDecimal getSoTienGiamToiDa() {
        return soTienGiamToiDa;
    }

    public void setSoTienGiamToiDa(BigDecimal soTienGiamToiDa) {
        this.soTienGiamToiDa = soTienGiamToiDa;
    }

    public BigDecimal getHoaDonToiThieu() {
        return hoaDonToiThieu;
    }

    public void setHoaDonToiThieu(BigDecimal hoaDonToiThieu) {
        this.hoaDonToiThieu = hoaDonToiThieu;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public float getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(float trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getDeleted() {
        return deleted;
    }

    public void setDeleted(float deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
