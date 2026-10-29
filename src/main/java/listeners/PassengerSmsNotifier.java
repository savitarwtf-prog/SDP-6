package listeners;

import observer.FlightEvent;
import observer.Observer;

public class PassengerSmsNotifier implements Observer<FlightEvent> {
    @Override
    public void onEvent(FlightEvent event) {
        System.out.println(" [SMS] Notification sent: Flight " + event.flightNumber() + " is now " + event.type());
    }
}