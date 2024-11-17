package ubb.scs.map.Utils.observer;


import ubb.scs.map.Utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}