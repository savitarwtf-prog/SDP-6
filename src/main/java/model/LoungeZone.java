package model;

import visitor.Element;
import visitor.Visitor;

public class LoungeZone implements Element {
    private final String name;
    private final int capacity;
    private final double dailyRevenue;

    public LoungeZone(String name, int capacity, double dailyRevenue) {
        this.name = name;
        this.capacity = capacity;
        this.dailyRevenue = dailyRevenue;
    }

    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public double getDailyRevenue() { return dailyRevenue; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}