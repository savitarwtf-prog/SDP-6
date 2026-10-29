# Airport Operations Management System (SDP Assignment 6)

## Project Overview
This project is an Airport Operations Management System implemented in Java 17. It combines two behavioral design patterns:
1. Observer Pattern: Handles live data feeds (flight status updates, delay alerts, baggage tracking) and notifies multiple subscribers asynchronously and thread-safely.
2. Visitor Pattern: Operates on a stable hierarchical tree of the airport infrastructure (Terminals, Gates, Lounges, Duty-Free Shops, Baggage Carousels, Parking Lots) to calculate metrics, aggregate capacities, filter maintenance issues, and export data to JSON.

## Architecture and Key Design Decisions

### 1. Observer Pattern (Live Events)
- Push vs. Pull Model Choice: We selected the Push Model. Event notifications pass fully immutable FlightEvent record objects containing all relevant contextual data (flightNumber, eventType, terminalId, gateId, delayMinutes). Observers do not need to call back into the Subject to fetch details, minimizing coupling and thread contention.
- Thread Safety: The subscription registry in EventManager uses ConcurrentHashMap and CopyOnWriteArrayList. This allows listeners to join, leave, or unsubscribe dynamically at runtime without raising ConcurrentModificationException or blocking event publication.
- Chain of Observers: AirportAlertService acts as both an Observer (subscribes to delay events) and a trigger for system inspections (invokes Visitor operations on specific subtrees).

### 2. Visitor Pattern (Infrastructure Operations)
- Double Dispatch: Every tree node implements Element.accept(Visitor<R>), delegating traversal to the corresponding visitor.visit(node) method.
- Tree Traversal Responsibility: The Visitor classes control the traversal logic over the Airport and Terminal composite containers. This keeps the element classes strictly data-only (POJOs) and gives visitors the flexibility to selectively visit subtrees.

## Airport Infrastructure Tree Structure

Airport (Root Container)
 - Terminal (Container)
    - Gate (Leaf)
    - LoungeZone (Leaf)
    - DutyFreeShop (Leaf)
    - BaggageCarousel (Leaf)
    - ParkingLot (Leaf)

## Trade-off Proof (Git Commit History)

To demonstrate the architectural trade-offs of the Visitor Pattern, two specific commits were made in the repository:

1. Commit 1 (New Visitor Operation): Added src/main/java/visitor/AirportSecurityVisitor.java.
   - Trade-off: Adding a new operation (AirportSecurityVisitor) touched 0 element classes. The infrastructure tree remained completely unaffected.

2. Commit 2 (New Element Type): Added model/ParkingLot.java and updated visitor/Visitor.java alongside all concrete visitor implementations.
   - Trade-off: Adding a single new element (ParkingLot) forced an update to the Visitor interface and required modifying every existing Visitor implementation.

## Testing and Verification

The project includes JUnit 5 unit tests covering:
1. Visitor Calculations: All visitors tested against hand-calculated values (AirportRevenueVisitor, PassengerCapacityVisitor, MaintenanceFilterVisitor, AirportJsonExportVisitor).
2. Observer Mechanics: Subscriptions, unsubscriptions, and type-based event filtering.
3. Stress Test (StressTest.java): 4 producer threads publishing 1,000 events each (4,000 total events) on an ExecutorService, verifying exact event counts without loss or deadlocks.

## Conclusion

In this assignment, a flexible, scalable, and thread-safe Airport Operations Management System was designed and implemented using Java 17. By combining the Observer and Visitor design patterns, the system cleanly decouples real-time event notifications from complex operations over hierarchical domain structures. The implementation adheres to Clean Code principles, utilizes Constructor Injection, and avoids global locks in concurrent event processing. The two dedicated Git commits demonstrate the fundamental trade-off of the Visitor pattern: adding new operations is highly extensible without modifying existing domain elements, whereas adding new domain elements requires cascading changes across all visitor implementations.
