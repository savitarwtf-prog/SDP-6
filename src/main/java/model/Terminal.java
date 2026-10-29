package model;

import visitor.Element;
import visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class Terminal implements Element {
    private final String id;
    private final List<Element> children = new ArrayList<>();

    public Terminal(String id) {
        this.id = id;
    }

    public void addChild(Element element) {
        children.add(element);
    }

    public List<Element> getChildren() { return children; }
    public String getId() { return id; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}