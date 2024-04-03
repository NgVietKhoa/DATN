package mobileworld.viewModel.BanHangViewModel;

import java.util.Date;

public class HoaDonViewModel {

    private String idHD;
    private Date createAt;
    private String createBy;
    private String tenKH;
    private int tongSP;
    private int trangthai;
    private Float deleted;
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

    public Float getDeleted() {
        return deleted;
    }

    public void setDeleted(Float deleted) {
        this.deleted = deleted;
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

}
