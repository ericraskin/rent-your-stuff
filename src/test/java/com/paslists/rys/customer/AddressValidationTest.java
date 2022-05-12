package com.paslists.rys.customer;

import com.paslists.rys.entity.Address;
import com.paslists.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations<Address> validations;
    private Address address;

    @BeforeEach
    void setUp() {
        address = dataManager.create(Address.class);
    }

    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_oneViolationOccurs() {

        // given

        address.setStreet(null);

        // then

        validations.assertOneViolationWith(address, "street", "NotBlank");
    }

    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_addressIsInvalidBecauseOfStreet() {

        // given

        address.setStreet(null);

        // then

        validations.assertOneViolationWith(address, "street", "NotBlank");
    }

}