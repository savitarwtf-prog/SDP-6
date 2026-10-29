package model;

import visitor.Element;
import visitor.Visitor;

public class BaggageCarousel implements Element {
    private final String carouselId;
    private final boolean needsMaintenance;

    public BaggageCarousel(String carouselId, boolean needsMaintenance) {
        this.carouselId = carouselId;
        this.needsMaintenance = needsMaintenance;
    }

    public String getCarouselId() { return carouselId; }
    public boolean isNeedsMaintenance() { return needsMaintenance; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}