package mobileworld.model;

import java.time.LocalDate;

public class ManHinh {

    private int stt;
    private String loaiManHinh;
    private float deleted;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updateBy;
    private String id;

    public ManHinh() {
    }

    public ManHinh(String loaiManHinh, float deleted, LocalDate createdAt, String createdBy, LocalDate updatedAt, String updateBy, String id) {
        this.loaiManHinh = loaiManHinh;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public ManHinh(String loaiManHinh, float deleted, LocalDate createdAt, String createdBy) {
        this.loaiManHinh = loaiManHinh;
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

    public String getLoaiManHinh() {
        return loaiManHinh;
    }

    public void setLoaiManHinh(String loaiManHinh) {
        this.loaiManHinh = loaiManHinh;
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
