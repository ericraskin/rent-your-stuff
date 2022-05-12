package com.paslists.rys.order;

import com.paslists.rys.product.StockItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderLineData {
    StockItem stockItem;
    Order order;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
}
