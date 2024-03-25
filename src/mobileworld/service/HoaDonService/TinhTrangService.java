package mobileworld.service;

import java.util.List;
import mobileworld.entity.TinhTrangEntity;
import mobileworld.repository.TinhTrangRepository;

public class TinhTrangService {

    TinhTrangRepository repo = new TinhTrangRepository();

    public List<TinhTrangEntity> getAll() {
        return repo.getAll();
    }
}
