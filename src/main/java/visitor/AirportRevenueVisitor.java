package visitor;

import model.*;

public class AirportRevenueVisitor implements Visitor<Double> {

    @Override
    public Double visit(Airport airport) {
        double total = 0;
        for (Terminal t : airport.getTerminals()) {
            total += t.accept(this);
        }
        return total;
    }

    @Override
    public Double visit(Terminal terminal) {
        double total = 0;
        for (Element child : terminal.getChildren()) {
            total += child.accept(this);
        }
        return total;
    }

    @Override
    public Double visit(Gate gate) { return gate.getFee(); }

    @Override
    public Double visit(LoungeZone lounge) { return lounge.getDailyRevenue(); }

    @Override
    public Double visit(DutyFreeShop shop) { return shop.getRentFee(); }

    @Override
    public Double visit(BaggageCarousel carousel) { return 0.0; }

    @Override
    public Double visit(ParkingLot lot) { return 0.0; }
}