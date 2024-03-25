package mobileworld.model;

import java.math.BigDecimal;
import java.util.Date;

public class HinhThucThanhToan {

    private String idHoaDon;
    private String idPhuongThucThanhToan;
    private BigDecimal tienChuyenKhoan;
    private BigDecimal tienMat;
    private float deleted;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updateBy;
    private String id;

    public HinhThucThanhToan() {
    }

    public HinhThucThanhToan(String idHoaDon, String idPhuongThucThanhToan, BigDecimal tienChuyenKhoan, BigDecimal tienMat, float deleted, Date createdAt, String createdBy, Date updatedAt, String updateBy, String id) {
        this.idHoaDon = idHoaDon;
        this.idPhuongThucThanhToan = idPhuongThucThanhToan;
        this.tienChuyenKhoan = tienChuyenKhoan;
        this.tienMat = tienMat;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updateBy = updateBy;
        this.id = id;
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
