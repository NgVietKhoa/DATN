package mobileworld.model;

import java.math.BigDecimal;
import java.util.Date;

public class ChiTietGiamGia {

    private String idPhieuGiamGia;
    private String idHoaDon;
    private float phanTramGiamGia;
    private BigDecimal giamGiaTienMat;
    private float deleted;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updateBy;
    private String id;

    public ChiTietGiamGia() {
    }

    public ChiTietGiamGia(String idPhieuGiamGia, String idHoaDon, float phanTramGiamGia, BigDecimal giamGiaTienMat, float deleted, Date createdAt, String createdBy, Date updatedAt, String updateBy, String id) {
        this.idPhieuGiamGia = idPhieuGiamGia;
        this.idHoaDon = idHoaDon;
        this.phanTramGiamGia = phanTramGiamGia;
        this.giamGiaTienMat = giamGiaTienMat;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public String getIdPhieuGiamGia() {
        return idPhieuGiamGia;
    }

    public void setIdPhieuGiamGia(String idPhieuGiamGia) {
        this.idPhieuGiamGia = idPhieuGiamGia;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public float getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public void setPhanTramGiamGia(float phanTramGiamGia) {
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public BigDecimal getGiamGiaTienMat() {
        return giamGiaTienMat;
    }

    public void setGiamGiaTienMat(BigDecimal giamGiaTienMat) {
        this.giamGiaTienMat = giamGiaTienMat;
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
