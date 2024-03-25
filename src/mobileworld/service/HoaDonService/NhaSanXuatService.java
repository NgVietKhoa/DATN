package mobileworld.service;

import java.util.List;
import mobileworld.entity.NhaSanXuatEntity;
import mobileworld.repository.NhaSanXuatRepository;

public class NhaSanXuatService {

    NhaSanXuatRepository repo = new NhaSanXuatRepository();

    public List<NhaSanXuatEntity> getAll() {
        return repo.getAll();
    }
}
