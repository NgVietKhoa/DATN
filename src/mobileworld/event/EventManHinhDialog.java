package mobileworld.event;

import mobileworld.model.ManHinh;

public interface EventManHinhDialog {

    public boolean add(ManHinh mh);

    public boolean remove(String id);

    public boolean update(ManHinh mh, String id);
}
