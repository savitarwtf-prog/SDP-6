package listeners;

import observer.FlightEvent;
import observer.Observer;
import java.util.concurrent.atomic.AtomicInteger;

public class AnalyticsTracker implements Observer<FlightEvent> {
    private final AtomicInteger totalEventsProcessed = new AtomicInteger(0);

    @Override
    public void onEvent(FlightEvent event) {
        int count = totalEventsProcessed.incrementAndGet();
        System.out.println(" [ANALYTICS] Processed event #" + count + " for " + event.flightNumber());
    }

    public int getTotalEventsProcessed() { return totalEventsProcessed.get(); }
}