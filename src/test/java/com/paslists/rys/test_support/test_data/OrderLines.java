package com.paslists.rys.test_support.test_data;

import com.paslists.rys.order.OrderLine;
import com.paslists.rys.order.OrderLineData;
import com.paslists.rys.order.OrderLineMapper;
import com.paslists.rys.order.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("rys_OrderLine")
public class OrderLines implements TestDataProvisioning<OrderLineData, OrderLineData.OrderLineDataBuilder, OrderLine> {

    @Autowired
    private StockItems stockItems;

    @Autowired
    private Orders orders;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    OrderLineMapper orderLineMapper;

    public static final LocalDateTime DEFAULT_STARTSAT_DATE = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime DEFAULT_ENDSAT_DATE = LocalDateTime.now().plusDays(2);

    @Override
    public OrderLineData.OrderLineDataBuilder defaultData() {
        return OrderLineData.builder()
                .stockItem(stockItems.createDefault())
                .order(orders.createDefault())
                .startsAt(DEFAULT_STARTSAT_DATE)
                .endsAt(DEFAULT_ENDSAT_DATE);
    }

    @Override
    public OrderLine save(OrderLineData orderLineData) { return orderLineRepository.save(orderLineData); }

    @Override
    public OrderLine create(OrderLineData orderLineData) { return orderLineMapper.toEntity(orderLineData); }

    @Override
    public OrderLine saveDefault() { return save(defaultData().build()); }

    @Override
    public OrderLine createDefault() { return create(defaultData().build()); }
    
}
