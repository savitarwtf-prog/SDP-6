package visitor;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class AirportJsonExportVisitor implements Visitor<String> {

    @Override
    public String visit(Airport airport) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"airport\": \"").append(airport.getName()).append("\",\n");
        sb.append("  \"terminals\": [\n");

        List<String> terminalJsons = new ArrayList<>();
        for (Terminal t : airport.getTerminals()) {
            terminalJsons.add(t.accept(this));
        }
        sb.append(String.join(",\n", terminalJsons));
        sb.append("\n  ]\n");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String visit(Terminal terminal) {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append("      \"terminalId\": \"").append(terminal.getId()).append("\",\n");
        sb.append("      \"elementsCount\": ").append(terminal.getChildren().size()).append("\n");
        sb.append("    }");
        return sb.toString();
    }

    @Override
    public String visit(Gate gate) { return ""; }
    @Override
    public String visit(LoungeZone lounge) { return ""; }
    @Override
    public String visit(DutyFreeShop shop) { return ""; }
    @Override
    public String visit(BaggageCarousel carousel) { return ""; }
    @Override
    public String visit(ParkingLot lot) { return ""; }
}