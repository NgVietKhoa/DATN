/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.viewModel.ThongKe;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class HoaDonTK {
    private float thanhTien;
    private LocalDate ngayTao;
    private String  maHD;

    public HoaDonTK() {
    }

    public HoaDonTK(float thanhTien, LocalDate ngayTao) {
        this.thanhTien = thanhTien;
        this.ngayTao = ngayTao;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

  
    
    

    
    

   
    
    
}
