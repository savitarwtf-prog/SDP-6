package visitor;

import model.*;

public class PassengerCapacityVisitor implements Visitor<Integer> {

    @Override
    public Integer visit(Airport airport) {
        int total = 0;
        for (Terminal t : airport.getTerminals()) {
            total += t.accept(this);
        }
        return total;
    }

    @Override
    public Integer visit(Terminal terminal) {
        int total = 0;
        for (Element child : terminal.getChildren()) {
            total += child.accept(this);
        }
        return total;
    }

    @Override
    public Integer visit(Gate gate) { return 150; } // Стандартный гейт вмещает ~150 пассажиров

    @Override
    public Integer visit(LoungeZone lounge) { return lounge.getCapacity(); }

    @Override
    public Integer visit(DutyFreeShop shop) { return 0; }

    @Override
    public Integer visit(BaggageCarousel carousel) { return 0; }

    @Override
    public Integer visit(ParkingLot lot) {
        return 0; // Парковка не влияет на вместимость пассажиров в терминале
    }
}