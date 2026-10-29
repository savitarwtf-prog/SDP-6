package observer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager<E> implements Subject<E> {
    private final Map<EventType, List<Observer<E>>> listeners = new ConcurrentHashMap<>();

    @Override
    public void subscribe(EventType type, Observer<E> observer) {
        listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(observer);
    }

    @Override
    public void unsubscribe(EventType type, Observer<E> observer) {
        List<Observer<E>> list = listeners.get(type);
        if (list != null) {
            list.remove(observer);
        }
    }

    @Override
    public void notifyObservers(E event) {
        if (event instanceof FlightEvent fe) {
            List<Observer<E>> list = listeners.get(fe.type());
            if (list != null) {
                for (Observer<E> observer : list) {
                    observer.onEvent(event);
                }
            }
        }
    }
}