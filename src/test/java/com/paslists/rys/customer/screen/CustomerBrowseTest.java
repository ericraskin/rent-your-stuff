package com.paslists.rys.customer.screen;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.entity.Address;
import com.paslists.rys.test_support.ui.ScreenInteractions;
import com.paslists.rys.test_support.ui.TableInteractions;
import com.paslists.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Customer customer;


    @BeforeEach
    void setUp() {

        customer = dataManager.create(Customer.class);
        customer.setFirstName("Foo");
        customer.setLastName("Bar");

        Address address = dataManager.create(Address.class);
        address.setStreet("Foo Street 1");
        customer.setAddress(address);

        customer = dataManager.save(customer);
    }

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);

        TableInteractions<Customer> customerTable = customerTable(customerBrowse);

        // expect:
        assertThat(customerTable.firstItem())
                .isEqualTo(customer);
    }

    @Test
    void given_oneCustomerExists_when_editCustomer_then_editCustomerEditorIsShown(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);

        TableInteractions<Customer> customerTable = customerTable(customerBrowse);

        // and
        Customer firstCustomer = customerTable.firstItem();

        // when
        customerTable.edit(firstCustomer);

        // then
        CustomerEdit customerEdit = screenInteractions.findOpenScreen(CustomerEdit.class);

        assertThat(customerEdit.getEditedEntity())
                .isEqualTo(firstCustomer);

    }

    @NotNull
    private TableInteractions<Customer> customerTable(CustomerBrowse customerBrowse) {
        return TableInteractions.of(customerBrowse, Customer.class, "customersTable");
    }

}