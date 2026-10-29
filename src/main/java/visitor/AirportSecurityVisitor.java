package visitor;

import model.*;

public class AirportSecurityVisitor implements Visitor<String> {
    @Override
    public String visit(Airport airport) {
        return "Security scan passed for " + airport.getName();
    }

    @Override
    public String visit(Terminal terminal) {
        return "Security scan passed for " + terminal.getId();
    }

    @Override
    public String visit(Gate gate) { return "Gate OK"; }

    @Override
    public String visit(LoungeZone lounge) { return "Lounge OK"; }

    @Override
    public String visit(DutyFreeShop shop) { return "Shop OK"; }

    @Override
    public String visit(BaggageCarousel carousel) { return "Carousel OK"; }
}