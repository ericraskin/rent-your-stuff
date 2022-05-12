package com.paslists.rys.order;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.product.Product;
import com.paslists.rys.product.StockItem;
import com.paslists.rys.test_support.TenantUserEnvironment;
import com.paslists.rys.test_support.test_data.*;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.paslists.rys.order.Assertions.assertThat;


@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class OrderStorageTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Orders orders;

    @Autowired
    Customers customers;

    @Autowired
    Products products;

    @Autowired
    StockItems stockItems;

    @Autowired
    OrderLines orderLines;

    private final LocalDate TODAY = LocalDate.now();

    private final LocalDateTime NOW = LocalDateTime.now().plusMinutes(1);
    private final LocalDateTime IN_TWO_DAYS_LDT = NOW.plusDays(2);
    private final LocalDateTime IN_THREE_DAYS_LDT = NOW.plusDays(3);
    @Test
    void given_validOrderWithOrderLines_when_save_then_allEntitiesAreSaved() {

        // given
        Customer customer = customers.saveDefault();

        // and
        Product product = products.saveDefault();

        // and
        StockItem stockItem1 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("SKU1")
                        .build()
        );
        StockItem stockItem2 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("SKU2")
                        .build()
        );

        // when

        Order order = orders.save(
                orders.defaultData()
                       .customer(customer)
                       .orderDate(TODAY)
                       .build()
        );

        // and

        OrderLine orderLine1 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem1)
                        .startsAt(NOW)
                        .endsAt(IN_TWO_DAYS_LDT)
                        .build()
        );
        OrderLine orderLine2 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem2)
                        .startsAt(NOW)
                        .endsAt(IN_THREE_DAYS_LDT)
                        .build()
        );

        // then

        Order savedOrder = dataManager.load(Id.of(order)).one();

        // then


        assertThat(savedOrder)
                .hasCustomer(customer)
                .hasOrderDate(TODAY)
                .hasOnlyOrderLines(orderLine1, orderLine2);

    }
}