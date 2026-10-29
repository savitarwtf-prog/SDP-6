package observer;

public record FlightEvent(
        String flightNumber,
        EventType type,
        String terminalId,
        String gateId,
        int delayMinutes
) {}