package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable{

    private final int warrantyMonths;
    private final BigDecimal weight;

//   - Fields: int warrantyMonths, BigDecimal weight (kg).

    public ElectronicsProduct(UUID id, String name, Category category,
                                 BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);

        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }

        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative."); //OR zero
        }

        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }

    @Override
    public String productDetails() {
        //- productDetails() should look like: "Electronics: Laptop, Warranty: 24 months".
        return "Electronics: " + name() + ", Warranty: " + warrantyMonths + " months";
    }

    @Override
    public Double weight() {
        return weight.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost() {
        //- Shipping rule: base 79, add 49 if weight > 5.0 kg.
        BigDecimal shippingCost = BigDecimal.valueOf(79);
        if (weight.compareTo(BigDecimal.valueOf(5.0)) > 0) {
            shippingCost = shippingCost.add(BigDecimal.valueOf(49));
        }
        return shippingCost;
    }
}

