/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class LichSuHDModel {

    private String idHD;
    private String idNV;
    private LocalDateTime ngayGio;
    private String hanhDong;

    public LichSuHDModel() {
    }

    public LichSuHDModel(String idHD, String idNV, LocalDateTime ngayGio, String hanhDong) {
        this.idHD = idHD;
        this.idNV = idNV;
        this.ngayGio = ngayGio;
        this.hanhDong = hanhDong;
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

    public LocalDateTime getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(LocalDateTime ngayGio) {
        this.ngayGio = ngayGio;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    @Override
    public String toString() {
        return "LichSuHDModel{" + "idHD=" + idHD + ", idNV=" + idNV + ", ngayGio=" + ngayGio + ", hanhDong=" + hanhDong + '}';
    }

}
