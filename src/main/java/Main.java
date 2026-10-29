import observer.*;
import model.*;
import visitor.*;
import integration.*;
import listeners.*;
import concurrency.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================================");
        System.out.println("   AIRPORT OPERATIONS SYSTEM (SDP ASSIGNMENT 6)   ");
        System.out.println("==================================================\n");

        // Построение дерева композита
        Airport airport = new Airport("Astana International Airport (NQZ)");

        Terminal t1 = new Terminal("Terminal 1");
        t1.addChild(new Gate("Gate A1", false, 500.0));
        t1.addChild(new Gate("Gate A2", true, 500.0)); // Требует ремонта
        t1.addChild(new LoungeZone("VIP Astana Lounge", 40, 1500.0));
        t1.addChild(new DutyFreeShop("Samruk DutyFree", 3000.0));
        t1.addChild(new BaggageCarousel("Carousel #1", true)); // Требует ремонта

        Terminal t2 = new Terminal("Terminal 2");
        t2.addChild(new Gate("Gate B1", false, 400.0));
        t2.addChild(new DutyFreeShop("Souvenir Shop", 1200.0));

        airport.addTerminal(t1);
        airport.addTerminal(t2);

        // Демонстрация работы Visitor 1
        System.out.println("--- 1. VISITOR PATTERN DEMO ---");

        AirportRevenueVisitor revenueVisitor = new AirportRevenueVisitor();
        System.out.println("Total Airport Revenue: $" + airport.accept(revenueVisitor));

        PassengerCapacityVisitor capacityVisitor = new PassengerCapacityVisitor();
        System.out.println("Total Passenger Capacity: " + airport.accept(capacityVisitor) + " passengers");

        MaintenanceFilterVisitor maintenanceVisitor = new MaintenanceFilterVisitor();
        System.out.println("All Broken Elements: " + airport.accept(maintenanceVisitor));

        AirportJsonExportVisitor jsonVisitor = new AirportJsonExportVisitor();
        System.out.println("\nJSON Export:\n" + airport.accept(jsonVisitor));

        // Создание Observer инфраструктуры
        System.out.println("\n--- 2. OBSERVER PATTERN & INTEGRATION DEMO ---");
        EventManager<FlightEvent> eventManager = new EventManager<>();

        // Создание 5 слушателей
        AirportAlertService alertService = new AirportAlertService(airport);
        DepartureBoardListener boardListener = new DepartureBoardListener();
        PassengerSmsNotifier smsNotifier = new PassengerSmsNotifier();
        GateManagerListener gateListener = new GateManagerListener();
        AnalyticsTracker analyticsTracker = new AnalyticsTracker();

        // Подписки на типы событий
        eventManager.subscribe(EventType.DELAYED, alertService);
        eventManager.subscribe(EventType.DELAYED, boardListener);
        eventManager.subscribe(EventType.DELAYED, smsNotifier);
        eventManager.subscribe(EventType.BOARDING, gateListener);
        eventManager.subscribe(EventType.DELAYED, analyticsTracker);
        eventManager.subscribe(EventType.SCHEDULED, boardListener);
        eventManager.subscribe(EventType.CANCELLED, smsNotifier);
        eventManager.subscribe(EventType.BAGGAGE_ALERT, alertService);

        eventManager.notifyObservers(new FlightEvent("KC-999", EventType.SCHEDULED, "Terminal 1", "Gate A1", 0));
        eventManager.notifyObservers(new FlightEvent("DV-303", EventType.CANCELLED, "Terminal 2", "Gate B1", 0));
        eventManager.notifyObservers(new FlightEvent("IQ-505", EventType.BAGGAGE_ALERT, "Terminal 1", "Carousel #1", 0));

        // Многопоточный запуск 3 продюсеров через ExecutorService
        System.out.println("\n--- 3. MULTITHREADING PRODUCERS RUNNING ---");
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new FlightEventProducer(eventManager, "KC"));
        executor.submit(new FlightEventProducer(eventManager, "DV"));
        executor.submit(new FlightEventProducer(eventManager, "IQ"));

        // Graceful shutdown
        executor.shutdown();
        executor.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println("\n==================================================");
        System.out.println("   EXECUTION COMPLETED SUCCESSFULLY               ");
        System.out.println("==================================================");
    }
}