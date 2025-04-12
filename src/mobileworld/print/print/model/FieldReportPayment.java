/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.print.print.model;

/**
 *
 * @author VHC
 */
public class FieldReportPayment {

    private int stt;
    private String tenSanPham;
    private String mauSac;
    private String pin;
    private String giaBan;
    private String thanhTien;
    
    public FieldReportPayment() {
    }

    public FieldReportPayment(int stt, String tenSanPham, String mauSac, String pin, String giaBan, String thanhTien) {
        this.stt = stt;
        this.tenSanPham = tenSanPham;
        this.mauSac = mauSac;
        this.pin = pin;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    


    
    
}
