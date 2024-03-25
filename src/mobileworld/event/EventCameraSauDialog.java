package mobileworld.event;

import mobileworld.model.CameraSau;

public interface EventCameraSauDialog {

    public boolean add(CameraSau cam);

    public boolean remove(String id);

    public boolean update(CameraSau cam, String id);
}
