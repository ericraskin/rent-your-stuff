package com.paslists.rys.order;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_OrderLineRepository")
public class OrderLineRepository implements EntityRepository<OrderLineData, OrderLine> {
    @Autowired
    OrderLineMapper orderLineMapper;

    @Autowired
    DataManager dataManager;

    public OrderLine save(OrderLineData orderLineData) { return dataManager.save(orderLineMapper.toEntity(orderLineData)); }
}
