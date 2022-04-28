package com.paslists.rys.customer;

import com.paslists.rys.app.test_support.DatabaseCleanup;
import com.paslists.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseCleanup<Customer> databaseCleanup;

    private Customer customer;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Customer.class);
        customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

        // given


        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        customer.setEmail("foo@bar.com");

        Address address = dataManager.create(Address.class);
        address.setStreet("Foo Street 1");
        address.setPostCode("38919");
        address.setCity("Bar City");

        customer.setAddress(address);

        // when

        Customer savedCustomer = systemAuthenticator.withSystem( () -> dataManager.save(customer));

        // then

        assertThat(savedCustomer.getId())
                .isNotNull();

    }
}