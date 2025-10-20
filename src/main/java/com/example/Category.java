package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Category {
    private static final Map<String, Category> CACHE = new ConcurrentHashMap<>();
    private final String name;


    //private Constructor
    private Category(String name){
        this.name = name;
    }

    //public static factory category.Of(String name)
    public static Category of(String name) {
        //validate input - cannot be null
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        //normalize name with inital capital letter fruit -> Fruit
        String normalize = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        return CACHE.computeIfAbsent(normalize, Category::new);
    }

    public String name(){return name;}

    public String getName(){return name;}

    @Override
    public String toString(){return name;}

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {return name.hashCode();}


}
/*
- Category (value object)
    - Use a private constructor and a public static factory Category.of(String name).
    - Validate input: null => "Category name can't be null"; empty/blank => "Category name can't be blank".
    - Normalize name with initial capital letter (e.g., "fruit" -> "Fruit").
    - Cache/flyweight: return the same instance for the same normalized name.
 */