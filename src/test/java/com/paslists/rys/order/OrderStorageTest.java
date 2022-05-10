package com.paslists.rys.order;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.entity.Address;
import com.paslists.rys.product.Product;
import com.paslists.rys.product.StockItem;
import com.paslists.rys.test_support.TenantUserEnvironment;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class OrderStorageTest {

    @Autowired
    DataManager dataManager;

    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = TODAY.minusDays(1);
    private final LocalDate TOMORROW = TODAY.plusDays(1);
    private final LocalDate IN_TWO_DAYS = TODAY.plusDays(2);

    private final LocalDateTime NOW = LocalDateTime.now().plusMinutes(1);
    private final LocalDateTime YESTERDAY_LDT = NOW.minusDays(1);
    private final LocalDateTime TOMORROW_LDT = NOW.plusDays(1);
    private final LocalDateTime IN_TWO_DAYS_LDT = NOW.plusDays(2);



    @Test
    void given_validOrder_when_save_then_orderIsSaved() {

        // given
        Order order = dataManager.create(Order.class);

        order.setCustomer(createCustomer());
        order.setOrderDate(TODAY);
        order.setOrderLines(null);

        // when

        dataManager.save(order);
        Optional<Order> savedOrder = loadOrderWithOrderLines(order);
        // then

        assertThat(savedOrder)
                .isPresent();
    }


    @Test
    void given_validOrderWithOrderLines_save_then_orderAndLinesAreSaved() {

        // given
        Order order = dataManager.create(Order.class);

        order.setCustomer(createCustomer());
        order.setOrderDate(TODAY);

        // and

        StockItem stockItem = createStockItem();
        OrderLine orderLine1 = createOrderLine(order, NOW, TOMORROW_LDT, stockItem);
        OrderLine orderLine2 = createOrderLine(order, TOMORROW_LDT, IN_TWO_DAYS_LDT, stockItem);

        order.setOrderLines(List.of(orderLine1, orderLine2));

        // when

        dataManager.save(order, orderLine1, orderLine2, stockItem);
        Optional<Order> savedOrder = loadOrderWithOrderLines(order);

        // then

        assertThat(savedOrder)
                .isPresent();

    }

    @NotNull
    private Optional<Order> loadOrderWithOrderLines(Order order) {
        return dataManager.load(Id.of(order))
                .fetchPlan(orderFp -> {
                    orderFp.addFetchPlan(FetchPlan.BASE);
                    orderFp.add("orderLines", orderLineFp -> orderLineFp.addFetchPlan(FetchPlan.BASE));
                })
                .optional();
    }

    @NotNull
    private OrderLine createOrderLine(Order order, LocalDateTime startsAt, LocalDateTime endsAt, StockItem stockItem) {
        OrderLine orderLine = dataManager.create(OrderLine.class);
        orderLine.setOrder(order);
        orderLine.setStartsAt(startsAt);
        orderLine.setEndsAt(endsAt);
        orderLine.setStockItem(stockItem);
        return orderLine;
    }

    private Customer createCustomer() {

        Customer customer = dataManager.create(Customer.class);
        customer.setLastName("test");

        Address address = dataManager.create(Address.class);
        address.setStreet("test street");
        customer.setAddress(address);

        return dataManager.save(customer);
    }

    private StockItem createStockItem() {

        Product product = dataManager.create(Product.class);
        product.setName("Foo Product");

        StockItem stockItem = dataManager.create(StockItem.class);
        stockItem.setProduct(dataManager.save(product));
        stockItem.setIdentifier("Foo Stock Item");

        return dataManager.save(stockItem);
    }
}