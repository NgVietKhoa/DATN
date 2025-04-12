package mobileworld.viewModel.BanHangViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class HoaDonViewModel {

    private String idHD;
    private Date createAt;
    private String createBy;
    private String tenKH;
    private int tongSP;
    private int trangthai;
    private Float deleted;
    private String idKH;
    private String idNV;
    private Date NgayTao;
    private Date ngayThanhToan;
    private BigDecimal tongTienSauGiam;
    private String idCTDG;
    private BigDecimal tongTien;
    private String sdtKH;
    private String diaChiKH;
    private LocalDateTime updateAt;
    private String updateBy;
    private String tenNV;
    private String sdtNV;
    private BigDecimal tienCK;
    private BigDecimal tienMat;
    private Float phiShip;
    private Date ngayNhan;
    private String getTenKieuThanhToan;

    public HoaDonViewModel() {

    }

    public HoaDonViewModel(String idHD, Date createAt, String createBy, String tenKH, int tongSP, int trangthai) {
        this.idHD = idHD;
        this.createAt = createAt;
        this.createBy = createBy;
        this.tenKH = tenKH;
        this.tongSP = tongSP;
        this.trangthai = trangthai;
    }

    public String getGetTenKieuThanhToan() {
        return getTenKieuThanhToan;
    }

    public void setGetTenKieuThanhToan(String getTenKieuThanhToan) {
        this.getTenKieuThanhToan = getTenKieuThanhToan;
    }
    
    

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getTongSP() {
        return tongSP;
    }

    public void setTongSP(int tongSP) {
        this.tongSP = tongSP;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public Float getDeleted() {
        return deleted;
    }

    public void setDeleted(Float deleted) {
        this.deleted = deleted;
    }

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getIdNV() {
        return idNV;
    }

    public void setIdNV(String idNV) {
        this.idNV = idNV;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public BigDecimal getTongTienSauGiam() {
        return tongTienSauGiam;
    }

    public void setTongTienSauGiam(BigDecimal tongTienSauGiam) {
        this.tongTienSauGiam = tongTienSauGiam;
    }

    public String getIdCTDG() {
        return idCTDG;
    }

    public void setIdCTDG(String idCTDG) {
        this.idCTDG = idCTDG;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
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

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdtNV() {
        return sdtNV;
    }

    public void setSdtNV(String sdtNV) {
        this.sdtNV = sdtNV;
    }

    public BigDecimal getTienCK() {
        return tienCK;
    }

    public void setTienCK(BigDecimal tienCK) {
        this.tienCK = tienCK;
    }

    public BigDecimal getTienMat() {
        return tienMat;
    }

    public void setTienMat(BigDecimal tienMat) {
        this.tienMat = tienMat;
    }

    public Float getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(Float phiShip) {
        this.phiShip = phiShip;
    }

    public Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

}
