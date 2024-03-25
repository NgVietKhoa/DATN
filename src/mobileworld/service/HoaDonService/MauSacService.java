package mobileworld.service;

import java.util.List;
import mobileworld.entity.MauSacEntity;
import mobileworld.repository.MauSacRepository;

public class MauSacService {

    MauSacRepository repo = new MauSacRepository();

    public List<MauSacEntity> getAll() {
        return repo.getAll();
    }

    public boolean add(MauSacEntity ms) {
        return repo.add(ms);
    }

    public boolean remove(String id) {
        return repo.remove(id);
    }

    public boolean update(MauSacEntity ms, String id) {
        return repo.update(ms, id);
    }
}
