package visitor;

import model.*;

public interface Visitor<R> {
    R visit(Airport airport);
    R visit(Terminal terminal);
    R visit(Gate gate);
    R visit(LoungeZone lounge);
    R visit(DutyFreeShop shop);
    R visit(BaggageCarousel carousel);
    R visit(ParkingLot lot);
}