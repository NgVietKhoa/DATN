package mobileworld.event;

import mobileworld.model.CPU;

public interface EventCPUDialog {

    public boolean add(CPU cpu);

    public boolean remove(String id);

    public boolean update(CPU cpu, String id);
}
