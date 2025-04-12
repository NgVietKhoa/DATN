package mobileworld.viewModel;

import java.time.LocalDateTime;

public class LichSuHDModel {

    private String idHD;
    private String idNV;
    private String hanhDong;
    private float deleted;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updateBy;

    public LichSuHDModel() {
    }

    public LichSuHDModel(String idHD, String idNV, String hanhDong, float deleted, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updateBy) {
        this.idHD = idHD;
        this.idNV = idNV;
        this.hanhDong = hanhDong;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
    }

    public LichSuHDModel(String idNV, String hanhDong, float deleted, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updateBy) {
        this.idNV = idNV;
        this.hanhDong = hanhDong;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
    }

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public String getIdNV() {
        return idNV;
    }

    public void setIdNV(String idNV) {
        this.idNV = idNV;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
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

}
