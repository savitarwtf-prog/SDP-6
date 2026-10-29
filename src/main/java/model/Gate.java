package model;

import visitor.Element;
import visitor.Visitor;

public class Gate implements Element {
    private final String gateNumber;
    private final boolean needsMaintenance;
    private final double fee;

    public Gate(String gateNumber, boolean needsMaintenance, double fee) {
        this.gateNumber = gateNumber;
        this.needsMaintenance = needsMaintenance;
        this.fee = fee;
    }

    public String getGateNumber() { return gateNumber; }
    public boolean isNeedsMaintenance() { return needsMaintenance; }
    public double getFee() { return fee; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}