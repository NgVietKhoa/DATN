package mobileworld.event;

import mobileworld.model.DongSP;

public interface EventSPDialog {

    public boolean add(DongSP dsp);

    public boolean remove(String id);

    public boolean update(DongSP sp, String id);
    
}
