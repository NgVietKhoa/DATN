package mobileworld.event;

import mobileworld.model.BoNho;

public interface EventRomDialog {

    public boolean add(BoNho bn);

    public boolean remove(String id);

    public boolean update(BoNho bn, String id);
}
