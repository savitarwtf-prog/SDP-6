package concurrency;

import observer.EventManager;
import observer.EventType;
import observer.FlightEvent;

public class FlightEventProducer implements Runnable {
    private final EventManager<FlightEvent> eventManager;
    private final String flightPrefix;

    public FlightEventProducer(EventManager<FlightEvent> eventManager, String flightPrefix) {
        this.eventManager = eventManager;
        this.flightPrefix = flightPrefix;
    }

    @Override
    public void run() {
        eventManager.notifyObservers(new FlightEvent(flightPrefix + "-101", EventType.SCHEDULED, "Terminal 1", "Gate A1", 0));
        eventManager.notifyObservers(new FlightEvent(flightPrefix + "-102", EventType.BOARDING, "Terminal 1", "Gate A2", 0));
        eventManager.notifyObservers(new FlightEvent(flightPrefix + "-103", EventType.DELAYED, "Terminal 1", "Gate A3", 75));
        eventManager.notifyObservers(new FlightEvent(flightPrefix + "-104", EventType.CANCELLED, "Terminal 2", "Gate B1", 0));
        eventManager.notifyObservers(new FlightEvent(flightPrefix + "-105", EventType.BAGGAGE_ALERT, "Terminal 1", "Gate A1", 0));
    }
}