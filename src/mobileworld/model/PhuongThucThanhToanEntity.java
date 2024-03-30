package mobileworld.model;

import java.util.Date;

public class PhuongThucThanhToanEntity {

    private String tenKieuThanhToan;
    private float deleted;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updateBy;
    private String id;

    public PhuongThucThanhToanEntity() {
    }

    public PhuongThucThanhToanEntity(String tenKieuThanhToan, float deleted, Date createdAt, String createdBy, Date updatedAt, String updateBy, String id) {
        this.tenKieuThanhToan = tenKieuThanhToan;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public String getTenKieuThanhToan() {
        return tenKieuThanhToan;
    }

    public void setTenKieuThanhToan(String tenKieuThanhToan) {
        this.tenKieuThanhToan = tenKieuThanhToan;
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
