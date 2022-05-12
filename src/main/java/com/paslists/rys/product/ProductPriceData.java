package com.paslists.rys.product;

import com.paslists.rys.entity.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductPriceData {
    Product product;
    Money price;
    String unit;
}
