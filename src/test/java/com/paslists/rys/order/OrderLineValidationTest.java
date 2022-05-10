package com.paslists.rys.order;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.product.StockItem;
import com.paslists.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class OrderLineValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations<OrderLine> validations;
    private OrderLine orderLine;

    private final LocalDate TODAY = LocalDate.now();
    private final LocalDateTime NOW = LocalDateTime.now().plusMinutes(1);
    private final LocalDateTime YESTERDAY = NOW.minusDays(1);
    private final LocalDateTime TOMORROW = NOW.plusDays(1);
    private final LocalDateTime IN_TWO_DAYS = NOW.plusDays(2);

    @BeforeEach
    void setUp() {
        Order order = dataManager.create(Order.class);
        order.setOrderDate(TODAY);
        order.setCustomer(dataManager.create(Customer.class));
        orderLine = dataManager.create(OrderLine.class);
        orderLine.setOrder(order);
    }

    @Test
    void given_validOrderLine_when_validate_then_noViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(TOMORROW);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // then

        validations.assertNoViolations(orderLine);


    }

    @Test
    void given_orderLineWithoutOrder_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(TOMORROW);
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setOrder(null);

        // then

        validations.assertExactlyOneViolationWith(orderLine, "order", "NotNull");
    }

    @Test
    void given_orderLineWithoutStockItem_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(TOMORROW);
        orderLine.setStockItem(null);

        // then

        validations.assertExactlyOneViolationWith(orderLine, "stockItem", "NotNull");
    }

    @Test
    void given_orderLineWithStartsAtInThePast_when_validate_then_violationOccurs() {

        // given

        orderLine.setStartsAt(YESTERDAY);
        orderLine.setEndsAt(NOW);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // then

        validations.assertOneViolationWith(orderLine, "startsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithEndsAtInThePast_when_validate_then_violationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(YESTERDAY);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // then

        validations.assertOneViolationWith(orderLine, "endsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithStartsNotPresent_when_validate_then_violationOccurs() {

        // given

        orderLine.setStartsAt(null);
        orderLine.setEndsAt(NOW);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // then

        validations.assertOneViolationWith(orderLine, "startsAt", "NotNull");
    }

    @Test
    void given_orderLineWithEndsAtNotNull_when_validate_then_violationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(null);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // then

        validations.assertOneViolationWith(orderLine, "endsAt", "NotNull");
    }

    @Test
    void given_orderLineWithEndsAtBeforeStartsAt_when_validate_then_violationOccurs() {

        // given

        orderLine.setStartsAt(IN_TWO_DAYS);
        orderLine.setEndsAt(TOMORROW);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // when
        List<Validations.ValidationResult<OrderLine>> violations = validations.validate(orderLine);

        // then

        validations.assertOneViolationWith(orderLine, "ValidRentalPeriod");
    }

}