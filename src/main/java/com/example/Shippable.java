package com.example;

import java.math.BigDecimal;

public interface Shippable {
    //varor som skickas ut
    //BigDecimal weight();
    Double weight();
    BigDecimal calculateShippingCost();
}
