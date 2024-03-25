package mobileworld.event;

import mobileworld.model.CameraTruoc;

public interface EventCameraTruocDialog {

    public boolean add(CameraTruoc cam);

    public boolean remove(String id);

    public boolean update(CameraTruoc cam, String id);
}
