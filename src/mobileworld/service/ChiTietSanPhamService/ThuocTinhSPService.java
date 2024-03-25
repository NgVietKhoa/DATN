package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.BoNho;
import mobileworld.model.CPU;
import mobileworld.model.CameraSau;
import mobileworld.model.CameraTruoc;
import mobileworld.model.ManHinh;
import mobileworld.model.MauSac;
import mobileworld.model.Pin;
import mobileworld.model.Ram;
import mobileworld.repository.ChiTietSanPhamRepo.ThuocTinhSPRepository;

public class ThuocTinhSPService {

    ThuocTinhSPRepository repo = new ThuocTinhSPRepository();

    public List<BoNho> getAllBoNho() {
        return repo.getAllBoNho();
    }

    public boolean addBoNho(BoNho bn) {
        return repo.addBoNho(bn);
    }

    public boolean removeBoNho(String id) {
        return repo.removeBoNho(id);
    }

    public boolean updateBoNho(BoNho bn, String id) {
        return repo.updateBoNho(bn, id);
    }

    public List<CPU> getAllCPU() {
        return repo.getAllCPU();
    }

    public boolean addCPU(CPU cpu) {
        return repo.addCPU(cpu);
    }

    public boolean removeCPU(String id) {
        return repo.removeCPU(id);
    }

    public boolean updateCPU(CPU cpu, String id) {
        return repo.updateCPU(cpu, id);
    }

    public List<ManHinh> getAllManHinh() {
        return repo.getAllManHinh();
    }

    public boolean addManHinh(ManHinh mh) {
        return repo.addManHinh(mh);
    }

    public boolean removeManHinh(String id) {
        return repo.removeManHinh(id);
    }

    public boolean updateManHinh(ManHinh mh, String id) {
        return repo.updateManHinh(mh, id);
    }

    public List<MauSac> getAllMauSac() {
        return repo.getAllMauSac();
    }

    public boolean addMauSac(MauSac ms) {
        return repo.addMauSac(ms);
    }

    public boolean removeMauSac(String id) {
        return repo.removeMauSac(id);
    }

    public boolean updateMauSac(MauSac ms, String id) {
        return repo.updateMauSac(ms, id);
    }

    public List<Pin> getAllPin() {
        return repo.getAllPin();
    }

    public boolean addPin(Pin pin) {
        return repo.addPin(pin);
    }

    public boolean removePin(String id) {
        return repo.removePin(id);
    }

    public boolean updatePin(Pin pin, String id) {
        return repo.updatePin(pin, id);
    }

    public List<Ram> getAllRam() {
        return repo.getAllRam();
    }

    public boolean addRam(Ram ram) {
        return repo.addRam(ram);
    }

    public boolean removeRam(String id) {
        return repo.removeRam(id);
    }

    public boolean updateRam(Ram ram, String id) {
        return repo.updateRam(ram, id);
    }

    public List<BoNho> getTenBoNho() {
        return repo.getTenBoNho();
    }

    public List<Pin> getTenPin() {
        return repo.getTenPin();
    }

    public List<CameraSau> getAllCameraSau() {
        return repo.getAllCameraSau();
    }

    public boolean addCameraSau(CameraSau cam) {
        return repo.addCameraSau(cam);
    }

    public boolean removeCameraSau(String id) {
        return repo.removeCameraSau(id);
    }

    public boolean updateCameraSau(CameraSau cam, String id) {
        return repo.updateCameraSau(cam, id);
    }

    public List<CameraTruoc> getAllCameraTruoc() {
        return repo.getAllCameraTruoc();
    }

    public boolean addCameraTruoc(CameraTruoc cam) {
        return repo.addCameraTruoc(cam);
    }

    public boolean removeCameraTruoc(String id) {
        return repo.removeCameraTruoc(id);
    }

    public boolean updateCameraTruoc(CameraTruoc cam, String id) {
        return repo.updateCameraTruoc(cam, id);
    }
}
