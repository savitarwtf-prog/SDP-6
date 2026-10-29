package model;

import visitor.Element;
import visitor.Visitor;

public class DutyFreeShop implements Element {
    private final String shopName;
    private final double rentFee;

    public DutyFreeShop(String shopName, double rentFee) {
        this.shopName = shopName;
        this.rentFee = rentFee;
    }

    public String getShopName() { return shopName; }
    public double getRentFee() { return rentFee; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}