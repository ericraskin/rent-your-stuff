package com.paslists.rys.customer;

import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification<Customer> validationVerification;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
        customer.setLastName("Bar");
    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_oneViolationOccurs() {

        // given

        customer.setEmail("invalidEmailAddress");

        // when
        List<ValidationVerification.ValidationResult<Customer>> violations = validationVerification.validate(customer);

        // then

        assertThat(violations).hasSize(1);

    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalidBecauseOfEmail() {

        // given

        customer.setEmail("invalidEmailAddress");

        // when
        ValidationVerification.ValidationResult<Customer> streetViolation = validationVerification.validateFirst(customer);

        // then

        assertThat(streetViolation.getAttribute()).
                isEqualTo("email");

        assertThat(streetViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("Email"));
    }

}