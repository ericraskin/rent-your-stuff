package com.paslists.rys.order;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.test_support.Validations;
import com.paslists.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderValidationTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    MetadataTools metadataTools;

    @Autowired
    Validations<Order> validations;
    private Order order;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = TODAY.minusDays(1);

    @BeforeEach
    void setUp() {
        order = dataManager.create(Order.class);
    }

    @Test
    void given_validOrder_when_validate_then_noViolationOccurs() {

        // given

        order.setOrderDate(TODAY);
        order.setCustomer(dataManager.create(Customer.class));

        // then

        validations.assertNoViolations(order);

    }

    @Test
    void given_orderWithoutOrderDate_when_validate_then_oneViolationOccurs() {

        // given

        order.setOrderDate(null);
        order.setCustomer(dataManager.create(Customer.class));

        // then

        validations.assertOneViolationWith(order, "orderDate", "NotNull");
    }

    @Test
    void given_orderWithOrderDateInThePast_when_validate_then_oneViolationOccurs() {

        // given

        order.setOrderDate(YESTERDAY);
        order.setCustomer(dataManager.create(Customer.class));

        // then

        validations.assertOneViolationWith(order, "orderDate", "FutureOrPresent");
    }

    @Test
    void given_orderWithoutCustomer_when_validate_then_oneViolationOccurs() {

        // given

        order.setOrderDate(TODAY);
        order.setCustomer(null);

        // then

        validations.assertOneViolationWith(order, "customer", "NotNull");
    }

    @Test
    void given_orderContainsCustomerAndOrderDate_expect_instanceNameContainsFormattedValues() {

        // given

        Customer customer = dataManager.create(Customer.class);
        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        order.setCustomer(customer);

        // and

        order.setOrderDate(LocalDate.parse("2022-05-10"));

        // then

        assertThat(metadataTools.getInstanceName(order))
                .isEqualTo("Foo Bar - 10/05/2022");
    }
}