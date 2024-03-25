package mobileworld.model;

import java.time.LocalDate;

public class MauSac {

    private int stt;
    private String tenMau;
    private float deleted;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updateBy;
    private String id;

    public MauSac() {
    }

    public MauSac(String tenMau, float deleted, LocalDate createdAt, String createdBy, LocalDate updatedAt, String updateBy, String id) {
        this.tenMau = tenMau;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public MauSac(String tenMau, float deleted, LocalDate createdAt, String createdBy) {
        this.tenMau = tenMau;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
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
