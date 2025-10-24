package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {

    /*
    - FoodProduct (extends Product)
    - Implements Perishable and Shippable.
    - Fields: LocalDate expirationDate, BigDecimal weight (kg).
    - Validations: negative price -> IllegalArgumentException("Price cannot be negative."); negative weight ->
      IllegalArgumentException("Weight cannot be negative.").
    - productDetails() should look like: "Food: Milk, Expires: 2025-12-24".
    - Shipping rule: cost = weight * 50.
     */
    private final LocalDate expirationDate;
    //private final Double weight;
    private final BigDecimal weight;

    public FoodProduct(UUID id, String name, Category category, BigDecimal price,
                       LocalDate expirationDate, BigDecimal weight) {
        super(id, name, category, price);

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        //weight villkor
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative."); //OR zero.
        }

        this.expirationDate = expirationDate;
        this.weight = weight;
    }


    //implementerar productDetails
    @Override
    public String productDetails() {
        return "Food: " + name() + ", Expires: " + expirationDate();
    };

    // ----- Perishable -----
    @Override
    public LocalDate expirationDate() {return expirationDate;}

    // ----- Shippable -----
    @Override
    public Double weight(){
        return weight.doubleValue();}

    @Override
    public BigDecimal calculateShippingCost(){
        //Shipping rule: cost = weight * 50.
        //return weight().multiply(BigDecimal.valueOf(50));
        return BigDecimal.valueOf(weight.doubleValue() * 50);
    }


}
