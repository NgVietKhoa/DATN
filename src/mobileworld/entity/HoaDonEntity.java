package mobileworld.entity;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDonEntity {

    private String idKhachHang;
    private String idNhanVien;
    private Date ngayTao;
    private Date ngayThanhToan;
    private String idChiTietGiamGia;
    private BigDecimal tongTien;
    private String idHinhThucThanhToan;
    private String tenKhachHang;
    private String sdtKhachHang;
    private String diaChiKhachHang;
    private float deleted;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updateBy;
    private String id;

    public HoaDonEntity() {
    }

    public HoaDonEntity(String idKhachHang, String idNhanVien, Date ngayTao, Date ngayThanhToan, String idChiTietGiamGia, BigDecimal tongTien, String idHinhThucThanhToan, String tenKhachHang, String sdtKhachHang, String diaChiKhachHang, float deleted, Date createdAt, String createdBy, Date updatedAt, String updateBy, String id) {
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.idChiTietGiamGia = idChiTietGiamGia;
        this.tongTien = tongTien;
        this.idHinhThucThanhToan = idHinhThucThanhToan;
        this.tenKhachHang = tenKhachHang;
        this.sdtKhachHang = sdtKhachHang;
        this.diaChiKhachHang = diaChiKhachHang;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getIdChiTietGiamGia() {
        return idChiTietGiamGia;
    }

    public void setIdChiTietGiamGia(String idChiTietGiamGia) {
        this.idChiTietGiamGia = idChiTietGiamGia;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getIdHinhThucThanhToan() {
        return idHinhThucThanhToan;
    }

    public void setIdHinhThucThanhToan(String idHinhThucThanhToan) {
        this.idHinhThucThanhToan = idHinhThucThanhToan;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdtKhachHang() {
        return sdtKhachHang;
    }

    public void setSdtKhachHang(String sdtKhachHang) {
        this.sdtKhachHang = sdtKhachHang;
    }

    public String getDiaChiKhachHang() {
        return diaChiKhachHang;
    }

    public void setDiaChiKhachHang(String diaChiKhachHang) {
        this.diaChiKhachHang = diaChiKhachHang;
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
