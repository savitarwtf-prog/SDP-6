package observer;

import integration.AirportAlertService;
import listeners.AnalyticsTracker;
import model.Airport;
import model.Gate;
import model.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    private EventManager<FlightEvent> eventManager;

    @BeforeEach
    void setUp() {
        eventManager = new EventManager<>();
    }

    @Test
    void testSubscribeAndNotify() {
        AnalyticsTracker tracker = new AnalyticsTracker();
        eventManager.subscribe(EventType.DELAYED, tracker);

        FlightEvent event = new FlightEvent("KC-101", EventType.DELAYED, "Terminal 1", "Gate A1", 30);
        eventManager.notifyObservers(event);

        assertEquals(1, tracker.getTotalEventsProcessed());
    }

    @Test
    void testUnsubscribe() {
        AnalyticsTracker tracker = new AnalyticsTracker();
        eventManager.subscribe(EventType.DELAYED, tracker);
        eventManager.unsubscribe(EventType.DELAYED, tracker);

        FlightEvent event = new FlightEvent("KC-101", EventType.DELAYED, "Terminal 1", "Gate A1", 30);
        eventManager.notifyObservers(event);

        assertEquals(0, tracker.getTotalEventsProcessed());
    }

    @Test
    void testIgnoreUnsubscribedEventType() {
        AnalyticsTracker tracker = new AnalyticsTracker();
        eventManager.subscribe(EventType.DELAYED, tracker);

        FlightEvent event = new FlightEvent("KC-102", EventType.BOARDING, "Terminal 1", "Gate A1", 0);
        eventManager.notifyObservers(event);

        assertEquals(0, tracker.getTotalEventsProcessed());
    }

    @Test
    void testIntegrationAlertServiceTrigger() {
        Airport airport = new Airport("Test Airport");
        Terminal t1 = new Terminal("Terminal 1");
        t1.addChild(new Gate("Gate A1", true, 100.0));
        airport.addTerminal(t1);

        AirportAlertService alertService = new AirportAlertService(airport);
        eventManager.subscribe(EventType.DELAYED, alertService);

        // Отправляем задержку > 60 мин
        FlightEvent event = new FlightEvent("KC-103", EventType.DELAYED, "Terminal 1", "Gate A1", 90);

        assertDoesNotThrow(() -> eventManager.notifyObservers(event));
    }

    @Test
    void testMultipleSubscribersSameEvent() {
        AnalyticsTracker tracker1 = new AnalyticsTracker();
        AnalyticsTracker tracker2 = new AnalyticsTracker();

        eventManager.subscribe(EventType.DELAYED, tracker1);
        eventManager.subscribe(EventType.DELAYED, tracker2);

        FlightEvent event = new FlightEvent("KC-104", EventType.DELAYED, "Terminal 1", "Gate A1", 45);
        eventManager.notifyObservers(event);

        assertEquals(1, tracker1.getTotalEventsProcessed());
        assertEquals(1, tracker2.getTotalEventsProcessed());
    }
}