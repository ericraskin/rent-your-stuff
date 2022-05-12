package com.paslists.rys.customer;

import com.paslists.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations<Customer> validations;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
        customer.setLastName("Bar");
    }

    @Test
    void given_validCustomer_when_validateCustomer_then_noViolationsOccur() {
        customer.setEmail("test@noemail.com");

        validations.assertNoViolations(customer);
    }
    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_oneViolationOccurs() {

        // given

        customer.setEmail("invalidEmailAddress");

        // then

        validations.assertOneViolationWith(customer, "email", "Email");
    }

}