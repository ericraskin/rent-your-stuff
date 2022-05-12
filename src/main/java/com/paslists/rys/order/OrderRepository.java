package com.paslists.rys.order;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_OrderRepository")
public class OrderRepository implements EntityRepository<OrderData, Order> {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    DataManager dataManager;

    public Order save(OrderData orderData) { return dataManager.save(orderMapper.toEntity(orderData)); }
}
