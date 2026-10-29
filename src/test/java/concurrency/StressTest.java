package concurrency;

import listeners.AnalyticsTracker;
import observer.EventManager;
import observer.EventType;
import observer.FlightEvent;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StressTest {

    @Test
    void testConcurrentEventsHandling() throws InterruptedException {
        EventManager<FlightEvent> eventManager = new EventManager<>();
        AnalyticsTracker tracker = new AnalyticsTracker();

        eventManager.subscribe(EventType.DELAYED, tracker);

        int threadsCount = 4;
        int eventsPerThread = 1000;
        int expectedTotalEvents = threadsCount * eventsPerThread; // 4,000 событий

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < eventsPerThread; j++) {
                    FlightEvent event = new FlightEvent(
                            "FL-" + threadId + "-" + j,
                            EventType.DELAYED,
                            "Terminal 1",
                            "Gate 1",
                            10
                    );
                    eventManager.notifyObservers(event);
                }
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(finished, "Threads should finish within 10 seconds");
        assertEquals(expectedTotalEvents, tracker.getTotalEventsProcessed());
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}