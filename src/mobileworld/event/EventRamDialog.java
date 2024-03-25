package mobileworld.event;

import mobileworld.model.Ram;

public interface EventRamDialog {

    public boolean add(Ram ram);

    public boolean remove(String id);

    public boolean update(Ram ram, String id);
}
