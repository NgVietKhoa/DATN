package mobileworld.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDonChiTietEntity {

    private String idImel;
    private String idCtsp;
    private String idHoaDon;
    private BigDecimal donGia;
    private float deleted;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updateBy;
    private String id;

    public HoaDonChiTietEntity() {
    }

    public HoaDonChiTietEntity(String idImel, String idCtsp, String idHoaDon, BigDecimal donGia, float deleted, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updateBy) {
        this.idImel = idImel;
        this.idCtsp = idCtsp;
        this.idHoaDon = idHoaDon;
        this.donGia = donGia;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
    }

    public String getIdImel() {
        return idImel;
    }

    public void setIdImel(String idImel) {
        this.idImel = idImel;
    }

    public String getIdCtsp() {
        return idCtsp;
    }

    public void setIdCtsp(String idCtsp) {
        this.idCtsp = idCtsp;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public float getDeleted() {
        return deleted;
    }

    public void setDeleted(float deleted) {
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
