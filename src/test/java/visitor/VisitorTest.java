package visitor;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {

    private Airport airport;

    @BeforeEach
    void setUp() {
        airport = new Airport("Test Airport");
        Terminal t1 = new Terminal("Terminal 1");
        t1.addChild(new Gate("Gate A1", false, 500.0));
        t1.addChild(new Gate("Gate A2", true, 500.0));
        t1.addChild(new LoungeZone("VIP Lounge", 50, 1000.0));
        t1.addChild(new DutyFreeShop("Shop 1", 2000.0));
        t1.addChild(new BaggageCarousel("Carousel 1", true));

        airport.addTerminal(t1);
    }

    @Test
    void testRevenueVisitorCalculation() {
        AirportRevenueVisitor visitor = new AirportRevenueVisitor();
        double totalRevenue = airport.accept(visitor);
        // 500 + 500 + 1000 + 2000 = 4000.0
        assertEquals(4000.0, totalRevenue);
    }

    @Test
    void testPassengerCapacityVisitor() {
        PassengerCapacityVisitor visitor = new PassengerCapacityVisitor();
        int capacity = airport.accept(visitor);
        // 150 (Gate A1) + 150 (Gate A2) + 50 (VIP Lounge) = 350
        assertEquals(350, capacity);
    }

    @Test
    void testMaintenanceFilterVisitor() {
        MaintenanceFilterVisitor visitor = new MaintenanceFilterVisitor();
        List<String> brokenElements = airport.accept(visitor);

        assertEquals(2, brokenElements.size());
        assertTrue(brokenElements.get(0).contains("Gate A2"));
        assertTrue(brokenElements.get(1).contains("Carousel 1"));
    }

    @Test
    void testJsonExportVisitorNotEmpty() {
        AirportJsonExportVisitor visitor = new AirportJsonExportVisitor();
        String json = airport.accept(visitor);

        assertNotNull(json);
        assertTrue(json.contains("Test Airport"));
        assertTrue(json.contains("Terminal 1"));
    }

    @Test
    void testEmptyTerminalVisitor() {
        Terminal emptyTerminal = new Terminal("Empty Terminal");
        AirportRevenueVisitor visitor = new AirportRevenueVisitor();
        assertEquals(0.0, emptyTerminal.accept(visitor));
    }
}