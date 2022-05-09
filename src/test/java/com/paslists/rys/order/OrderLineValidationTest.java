package com.paslists.rys.order;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.product.StockItem;
import com.paslists.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderLineValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification<OrderLine> validationVerification;
    private OrderLine orderLine;

    private final LocalDate TODAY = LocalDate.now();
    private final LocalDateTime NOW = LocalDateTime.now().plusMinutes(1);
    private final LocalDateTime YESTERDAY = NOW.minusDays(1);
    private final LocalDateTime TOMORROW = NOW.plusDays(1);

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

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).isEmpty();

    }

    @Test
    void given_orderLineWithoutStockItem_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(TOMORROW);
        orderLine.setStockItem(null);

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<OrderLine> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("stockItem");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithStartsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(YESTERDAY);
        orderLine.setEndsAt(NOW);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<OrderLine> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("startsAt");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("FutureOrPresent"));
    }

    @Test
    void given_orderLineWithEndsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(YESTERDAY);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<OrderLine> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("endsAt");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("FutureOrPresent"));
    }

    @Test
    void given_orderLineWithStartsNotPresent_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(null);
        orderLine.setEndsAt(NOW);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<OrderLine> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("startsAt");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithEndsAtNotNull_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(null);
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<OrderLine> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("endsAt");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithoutOrder_when_validate_then_oneViolationOccurs() {

        // given

        orderLine.setStartsAt(NOW);
        orderLine.setEndsAt(TOMORROW);
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setOrder(null);

        // when
        List<ValidationVerification.ValidationResult<OrderLine>> violations = validationVerification.validate(orderLine);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<OrderLine> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("order");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));
    }
}