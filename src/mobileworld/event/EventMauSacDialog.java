package mobileworld.event;

import mobileworld.model.MauSac;

public interface EventMauSacDialog {

    public boolean add(MauSac ms);

    public boolean remove(String id);

    public boolean update(MauSac ms, String id);
}
