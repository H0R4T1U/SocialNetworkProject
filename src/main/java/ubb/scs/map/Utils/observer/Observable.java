package ubb.scs.map.Utils.observer;

import ubb.scs.map.Utils.events.Event;


public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E e);

}
