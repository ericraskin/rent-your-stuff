package com.paslists.rys.customer.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.customer.Customer;

@UiController("rys_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}