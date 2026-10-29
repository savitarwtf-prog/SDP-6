package integration;

import observer.FlightEvent;
import observer.Observer;
import model.Airport;
import model.Terminal;
import visitor.MaintenanceFilterVisitor;

import java.util.List;

public class AirportAlertService implements Observer<FlightEvent> {
    private final Airport airport;

    public AirportAlertService(Airport airport) {
        this.airport = airport;
    }

    @Override
    public void onEvent(FlightEvent event) {
        if (event.delayMinutes() > 60) {
            System.out.println("\n [ALERT SERVICE] Flight " + event.flightNumber() + " delayed by " + event.delayMinutes() + " mins!");
            System.out.println("   Triggering Inspection Visitor on subtree: " + event.terminalId());

            for (Terminal t : airport.getTerminals()) {
                if (t.getId().equalsIgnoreCase(event.terminalId())) {
                    MaintenanceFilterVisitor visitor = new MaintenanceFilterVisitor();
                    List<String> maintenanceList = t.accept(visitor);
                    System.out.println("    Inspection Report for " + t.getId() + ": " + maintenanceList);
                }
            }
        }
    }
}