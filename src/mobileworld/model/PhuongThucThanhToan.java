/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.model;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class PhuongThucThanhToan {
    private String idPTTT;
    private String teKieuThanhToan;
    private Float Delete;
    private LocalDateTime CcreateAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(String idPTTT, String teKieuThanhToan, Float Delete, LocalDateTime CcreateAt, String createBy, LocalDateTime updateAt, String updateBy) {
        this.idPTTT = idPTTT;
        this.teKieuThanhToan = teKieuThanhToan;
        this.Delete = Delete;
        this.CcreateAt = CcreateAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
    }

    public String getIdPTTT() {
        return idPTTT;
    }

    public void setIdPTTT(String idPTTT) {
        this.idPTTT = idPTTT;
    }

    public String getTeKieuThanhToan() {
        return teKieuThanhToan;
    }

    public void setTeKieuThanhToan(String teKieuThanhToan) {
        this.teKieuThanhToan = teKieuThanhToan;
    }

    public Float getDelete() {
        return Delete;
    }

    public void setDelete(Float Delete) {
        this.Delete = Delete;
    }

    public LocalDateTime getCcreateAt() {
        return CcreateAt;
    }

    public void setCcreateAt(LocalDateTime CcreateAt) {
        this.CcreateAt = CcreateAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "PhuongThucThanhToan{" + "idPTTT=" + idPTTT + ", teKieuThanhToan=" + teKieuThanhToan + ", Delete=" + Delete + ", CcreateAt=" + CcreateAt + ", createBy=" + createBy + ", updateAt=" + updateAt + ", updateBy=" + updateBy + '}';
    }
    
    
}
