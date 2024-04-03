package mobileworld.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class KhachHang {

    private String ten;
    private String sdt;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String diaChi;
    private int deleted;
    private LocalDateTime createdat;
    private String createdby;
    private LocalDateTime updatedat;
    private String updatedby;
    private String id;
    private String email;

    public KhachHang() {
    }

    public KhachHang(String ten, String sdt, boolean gioiTinh, String diaChi, int deleted, LocalDateTime createdat, String createdby, LocalDateTime updatedat, String updatedby, String id, String email) {
        this.ten = ten;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.deleted = deleted;
        this.createdat = createdat;
        this.createdby = createdby;
        this.updatedat = updatedat;
        this.updatedby = updatedby;
        this.id = id;
        this.email = email;
    }

    public KhachHang(String ten, String sdt, boolean gioiTinh, Date ngaySinh, String diaChi, int deleted, LocalDateTime createdat, String createdby, LocalDateTime updatedat, String updatedby, String email) {
        this.ten = ten;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.deleted = deleted;
        this.createdat = createdat;
        this.createdby = createdby;
        this.updatedat = updatedat;
        this.updatedby = updatedby;
        this.email = email;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
