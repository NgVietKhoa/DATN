package mobileworld.service.ChiTietSanPhamService;

import java.util.List;
import mobileworld.model.Imel;
import mobileworld.repository.ChiTietSanPhamRepo.ImelRepository;

public class ImelService {

    ImelRepository repo = new ImelRepository();

    public List<Imel> getImel() {
        return repo.getImel();
    }

    public List<Imel> getAllImel(String getImel) {
        return repo.getAllImel(getImel);
    }
}
