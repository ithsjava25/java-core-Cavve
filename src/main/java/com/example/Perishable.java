package com.example;
import java.time.LocalDate;

public interface Perishable{
    //varor som kan utgå
    LocalDate expirationDate();
}
