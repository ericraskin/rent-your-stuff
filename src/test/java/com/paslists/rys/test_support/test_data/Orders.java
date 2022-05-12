package com.paslists.rys.test_support.test_data;

import com.paslists.rys.order.Order;
import com.paslists.rys.order.OrderData;
import com.paslists.rys.order.OrderMapper;
import com.paslists.rys.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("rys_Orders")
public class Orders implements TestDataProvisioning<OrderData, OrderData.OrderDataBuilder, Order> {

    @Autowired
    private Customers customers;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    public static final LocalDate DEFAULT_ORDER_DATE = LocalDate.now().plusDays(1);

    @Override
    public OrderData.OrderDataBuilder defaultData() {
        return OrderData.builder()
                .orderDate(DEFAULT_ORDER_DATE)
                .customer(customers.createDefault())
                .orderLines(List.of());
    }

    @Override
    public Order save(OrderData orderData) { return orderRepository.save(orderData); }

    @Override
    public Order create(OrderData orderData) { return orderMapper.toEntity(orderData); }

    @Override
    public Order saveDefault() { return save(defaultData().build()); }

    @Override
    public Order createDefault() { return create(defaultData().build()); }

}
