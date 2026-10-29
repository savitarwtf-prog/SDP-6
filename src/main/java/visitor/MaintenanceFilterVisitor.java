package visitor;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceFilterVisitor implements Visitor<List<String>> {

    @Override
    public List<String> visit(Airport airport) {
        List<String> result = new ArrayList<>();
        for (Terminal t : airport.getTerminals()) {
            result.addAll(t.accept(this));
        }
        return result;
    }

    @Override
    public List<String> visit(Terminal terminal) {
        List<String> result = new ArrayList<>();
        for (Element child : terminal.getChildren()) {
            result.addAll(child.accept(this));
        }
        return result;
    }

    @Override
    public List<String> visit(Gate gate) {
        List<String> result = new ArrayList<>();
        if (gate.isNeedsMaintenance()) {
            result.add("Gate requiring maintenance: " + gate.getGateNumber());
        }
        return result;
    }

    @Override
    public List<String> visit(BaggageCarousel carousel) {
        List<String> result = new ArrayList<>();
        if (carousel.isNeedsMaintenance()) {
            result.add("Baggage Carousel requiring maintenance: " + carousel.getCarouselId());
        }
        return result;
    }

    @Override
    public List<String> visit(LoungeZone lounge) { return List.of(); }

    @Override
    public List<String> visit(DutyFreeShop shop) { return List.of(); }

    @Override
    public List<String> visit(ParkingLot lot) { return List.of(); }

}