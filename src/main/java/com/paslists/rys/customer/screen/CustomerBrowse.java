package com.paslists.rys.customer.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.customer.Customer;

@UiController("rys_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {


}