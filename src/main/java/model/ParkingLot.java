package model;

import visitor.Element;
import visitor.Visitor;

public class ParkingLot implements Element {
    private final String lotId;

    public ParkingLot(String lotId) {
        this.lotId = lotId;
    }

    public String getLotId() { return lotId; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}