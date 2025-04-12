package mobileworld.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HinhThucThanhToanEntity {

    private String idHoaDon;
    private String idPhuongThucThanhToan;
    private BigDecimal tienChuyenKhoan;
    private BigDecimal tienMat;
    private float deleted;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updateBy;
    private String id;

    public HinhThucThanhToanEntity() {
    }

    public HinhThucThanhToanEntity(String idHoaDon, BigDecimal tienChuyenKhoan, BigDecimal tienMat, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updateBy) {
        this.idHoaDon = idHoaDon;
        this.idPhuongThucThanhToan = idPhuongThucThanhToan;
        this.tienChuyenKhoan = tienChuyenKhoan;
        this.tienMat = tienMat;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdPhuongThucThanhToan() {
        return idPhuongThucThanhToan;
    }

    public void setIdPhuongThucThanhToan(String idPhuongThucThanhToan) {
        this.idPhuongThucThanhToan = idPhuongThucThanhToan;
    }

    public BigDecimal getTienChuyenKhoan() {
        return tienChuyenKhoan;
    }

    public void setTienChuyenKhoan(BigDecimal tienChuyenKhoan) {
        this.tienChuyenKhoan = tienChuyenKhoan;
    }

    public BigDecimal getTienMat() {
        return tienMat;
    }

    public void setTienMat(BigDecimal tienMat) {
        this.tienMat = tienMat;
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
