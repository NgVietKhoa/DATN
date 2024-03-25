package mobileworld.model;

import java.time.LocalDate;

public class ImelDaBan {

    private String idImel;
    private String idHoaDonCT;
    private float deleted;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updateBy;
    private String id;

    public ImelDaBan() {
    }

    public ImelDaBan(String idImel, String idHoaDonCT, float deleted, LocalDate createdAt, String createdBy, LocalDate updatedAt, String updateBy, String id) {
        this.idImel = idImel;
        this.idHoaDonCT = idHoaDonCT;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public String getIdImel() {
        return idImel;
    }

    public void setIdImel(String idImel) {
        this.idImel = idImel;
    }

    public String getIdHoaDonCT() {
        return idHoaDonCT;
    }

    public void setIdHoaDonCT(String idHoaDonCT) {
        this.idHoaDonCT = idHoaDonCT;
    }

    public float getDeleted() {
        return deleted;
    }

    public void setDeleted(float deleted) {
        this.deleted = deleted;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
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
