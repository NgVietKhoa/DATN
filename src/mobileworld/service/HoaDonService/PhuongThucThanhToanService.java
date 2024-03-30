/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.HoaDonService;

import java.util.List;
import mobileworld.model.PhuongThucThanhToan;
import mobileworld.repository.HoaDonRepo.PhuongThucThanhToanRepository;

/**
 *
 * @author ADMIN
 */
public class PhuongThucThanhToanService {
    PhuongThucThanhToanRepository repo = new PhuongThucThanhToanRepository();
        public List<PhuongThucThanhToan> getAll(){
            return repo.getAll();
        }

}
