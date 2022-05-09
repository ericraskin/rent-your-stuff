package com.paslists.rys.customer;

import com.paslists.rys.entity.Address;
import com.paslists.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerIntegrationTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Customer customer;

    @BeforeEach
    void setUp() {
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

        Customer savedCustomer = dataManager.save(customer);

        // then

        assertThat(savedCustomer.getId())
                .isNotNull();

    }
}