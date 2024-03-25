package mobileworld.event;

import mobileworld.model.Pin;

public interface EventPinDialog {

    public boolean add(Pin pin);

    public boolean remove(String id);

    public boolean update(Pin pin, String id);

}
