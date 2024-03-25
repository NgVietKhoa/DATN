/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class HoaDonChiTietModel {

    private String idHD;
    private String idHDinHDCT;
    private String idCTSP;
    private String tenDSP;
    private String tenNSX;
    private String tenMau;
    private String dungLuongPin;
    private String imel;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal tongTien;

    public HoaDonChiTietModel() {
    }

    public HoaDonChiTietModel(String idHD, String idHDinHDCT, String idCTSP, String tenDSP, String tenNSX, String tenMau, String dungLuongPin, String imel, int soLuong, BigDecimal giaBan, BigDecimal tongTien) {
        this.idHD = idHD;
        this.idHDinHDCT = idHDinHDCT;
        this.idCTSP = idCTSP;
        this.tenDSP = tenDSP;
        this.tenNSX = tenNSX;
        this.tenMau = tenMau;
        this.dungLuongPin = dungLuongPin;
        this.imel = imel;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.tongTien = tongTien;
    }

    public String getIdCTSP() {
        return idCTSP;
    }

    public void setIdCTSP(String idCTSP) {
        this.idCTSP = idCTSP;
    }

    public String getDungLuongPin() {
        return dungLuongPin;
    }

    public void setDungLuongPin(String dungLuongPin) {
        this.dungLuongPin = dungLuongPin;
    }

    public String getIdHD() {
        return idHD;
    }

    public void setIdHD(String idHD) {
        this.idHD = idHD;
    }

    public String getIdHDinHDCT() {
        return idHDinHDCT;
    }

    public void setIdHDinHDCT(String idHDinHDCT) {
        this.idHDinHDCT = idHDinHDCT;
    }

    public String getImel() {
        return imel;
    }

    public void setImel(String imel) {
        this.imel = imel;
    }

    public String getTenDSP() {
        return tenDSP;
    }

    public void setTenDSP(String tenDSP) {
        this.tenDSP = tenDSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    @Override
    public String toString() {
        return "HoaDonChiTietModel{" + "idHD=" + idHD + ", idHDinHDCT=" + idHDinHDCT + ", idCTSP=" + idCTSP + ", tenDSP=" + tenDSP + ", tenNSX=" + tenNSX + ", tenMau=" + tenMau + ", dungLuongPin=" + dungLuongPin + ", imel=" + imel + ", soLuong=" + soLuong + ", giaBan=" + giaBan + ", tongTien=" + tongTien + '}';
    }

}
