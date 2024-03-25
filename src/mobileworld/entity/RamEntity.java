package mobileworld.entity;

import java.time.LocalDate;
import java.util.Date;

public class RamEntity {

    private String dungLuongRam;
    private float deleted;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updateBy;
    private String id;

    public RamEntity() {
    }

    public RamEntity(String dungLuongRam, float deleted, LocalDate createdAt, String createdBy, LocalDate updatedAt, String updateBy, String id) {
        this.dungLuongRam = dungLuongRam;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
    }

    public RamEntity(String dungLuongRam, float deleted, LocalDate createdAt, String createdBy) {
        this.dungLuongRam = dungLuongRam;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public String getDungLuongRam() {
        return dungLuongRam;
    }

    public void setDungLuongRam(String dungLuongRam) {
        this.dungLuongRam = dungLuongRam;
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
