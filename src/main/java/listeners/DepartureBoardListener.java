package listeners;

import observer.FlightEvent;
import observer.Observer;

public class DepartureBoardListener implements Observer<FlightEvent> {
    @Override
    public void onEvent(FlightEvent event) {
        System.out.println(" [BOARD] Status updated for " + event.flightNumber() + ": " + event.type());
    }
}