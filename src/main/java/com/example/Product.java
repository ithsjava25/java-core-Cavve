package com.example;

import java.math.BigDecimal;
import java.util.UUID;

abstract class Product {
       /*
       Beskriver en enskild produkt
     */

    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    protected Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public Category getCategory() {return category;}
    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}


    public abstract String productDetails();

    //returnerar price
    public BigDecimal price() {return price;}
    //returnerar name
    public String name() {return name;}
    //returnerar category
    public Category category() {return category;}
    //returnerar UUID
    public UUID uuid() {return id;}
}

/*
- Product (abstract base class)
    - Keep UUID id, String name, Category category, BigDecimal price.
    - Provide getters named uuid(), name(), category(), price() and a setter price(BigDecimal).
    - Provide an abstract String productDetails() for polymorphism.
 */