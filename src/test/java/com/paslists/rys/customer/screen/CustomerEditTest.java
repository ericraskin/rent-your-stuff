package com.paslists.rys.customer.screen;

import com.paslists.rys.RentYourStuffApplication;
import com.paslists.rys.app.test_support.DatabaseCleanup;
import com.paslists.rys.customer.Customer;
import com.paslists.rys.test_support.ui.FormInteractions;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "com.paslists.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerEditTest {

    @Autowired
    DataManager dataManager;

    FormInteractions formInteractions;

    @Autowired
    DatabaseCleanup<Customer> databaseCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerIsSaved(Screens screens) {

        // given
        CustomerEdit customerEdit = openCustomerEdit(screens);
        formInteractions = FormInteractions.of(customerEdit);

        // and
        String firstName = "Foo" + UUID.randomUUID();
        String expectedLastName = "Bar";

        formInteractions.setFieldValue("firstNameField",firstName);
        formInteractions.setFieldValue("lastNameField",expectedLastName);
        formInteractions.setFieldValue("addressStreetField","Foo Street 123");

        // when
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult).isEqualTo(OperationResult.success());

        // then
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", firstName);

        assertThat(savedCustomer)
                .isPresent()
                .get()
                .extracting("lastName")
                .isEqualTo(expectedLastName);
    }

    @Test
    void given_customerWithoutStreet_when_saveCustomerThroughTheForm_then_customerIsNotSaved(Screens screens) {

        // given
        CustomerEdit customerEdit = openCustomerEdit(screens);
        formInteractions = FormInteractions.of(customerEdit);

        // and
        String firstName = "Foo" + UUID.randomUUID();
        String expectedLastName = "Bar";
        String invalidStreetName = "";

        formInteractions.setFieldValue("firstNameField",firstName);
        formInteractions.setFieldValue("lastNameField",expectedLastName);
        formInteractions.setFieldValue("addressStreetField",invalidStreetName);

        // when
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult).isEqualTo(OperationResult.fail());

        // then
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", firstName);

        assertThat(savedCustomer)
                .isNotPresent();
    }
    @NotNull
    private Optional<Customer> findCustomerByAttribute(String attribute, String firstName) {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.equal(attribute, firstName))
                .optional();
    }

    @NotNull
    private CustomerEdit openCustomerEdit(Screens screens) {
        CustomerEdit screen = screens.create(CustomerEdit.class);

        Customer customer = dataManager.create(Customer.class);
        screen.setEntityToEdit(customer);
        screen.show();
        return screen;
    }

}