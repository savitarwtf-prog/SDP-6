package model;

import visitor.Element;
import visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class Airport implements Element {
    private final String name;
    private final List<Terminal> terminals = new ArrayList<>();

    public Airport(String name) {
        this.name = name;
    }

    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
    }

    public List<Terminal> getTerminals() { return terminals; }
    public String getName() { return name; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}