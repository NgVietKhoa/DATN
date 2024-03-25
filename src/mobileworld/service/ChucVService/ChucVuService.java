package mobileworld.service.ChucVService;

import java.util.List;
import mobileworld.model.ChucVu;
import mobileworld.repository.ChucVuRepo.ChucVuRepository;

public class ChucVuService {

    ChucVuRepository repo = new ChucVuRepository();

    public List<ChucVu> getAll() {
        return repo.getAll();
    }
}
