package listeners;

import observer.FlightEvent;
import observer.Observer;

public class GateManagerListener implements Observer<FlightEvent> {
    @Override
    public void onEvent(FlightEvent event) {
        System.out.println(" [GATE MGR] Preparing " + event.gateId() + " for flight " + event.flightNumber());
    }
}