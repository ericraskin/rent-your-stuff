package com.paslists.rys.customer.screen;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.test_support.ui.FormInteractions;
import com.paslists.rys.test_support.ui.ScreenInteractions;
import com.paslists.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerEditTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    FormInteractions formInteractions;


    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerIsSaved(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        CustomerEdit customerEdit = screenInteractions.openEditorForCreation(CustomerEdit.class, Customer.class);

        formInteractions = FormInteractions.of(customerEdit);

        // and
        String firstName = "Foo" + UUID.randomUUID();
        String expectedLastName = "Bar";

        formInteractions.setTextFieldValue("firstNameField",firstName);
        formInteractions.setTextFieldValue("lastNameField",expectedLastName);
        formInteractions.setTextFieldValue("addressStreetField","Foo Street 123");

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
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        CustomerEdit customerEdit = screenInteractions.openEditorForCreation(CustomerEdit.class, Customer.class);
        formInteractions = FormInteractions.of(customerEdit);

        // and
        String firstName = "Foo" + UUID.randomUUID();
        String expectedLastName = "Bar";
        String invalidStreetName = "";

        formInteractions.setTextFieldValue("firstNameField",firstName);
        formInteractions.setTextFieldValue("lastNameField",expectedLastName);
        formInteractions.setTextFieldValue("addressStreetField",invalidStreetName);

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

}