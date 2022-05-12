package com.paslists.rys.test_support.test_data;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.customer.CustomerData;
import com.paslists.rys.customer.CustomerMapper;
import com.paslists.rys.customer.CustomerRepository;
import com.paslists.rys.entity.Address;
import com.paslists.rys.entity.AddressData;
import com.paslists.rys.entity.AddressMapper;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_Customers")
public class Customers implements TestDataProvisioning<CustomerData, CustomerData.CustomerDataBuilder, Customer> {

    @Autowired
    private DataManager dataManager;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    AddressMapper addressMapper;

    public static String DEFAULT_FIRST_NAME = "default_first_name";
    public static String DEFAULT_LAST_NAME = "default_last_name";
    public static String DEFAULT_EMAIL = "default@noemail.com";
    public static String DEFAULT_STREET = "default_street";
    public static String DEFAULT_POST_CODE = "default_post_code";
    public static String DEFAULT_CITY = "default_city";

    @Override
    public CustomerData.CustomerDataBuilder defaultData() {
        return CustomerData.builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .address(defaultAddress());
    }

    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .build());
    }
    @Override
    public Customer save(CustomerData customerData) { return customerRepository.save(customerData); }

    @Override
    public Customer create(CustomerData customerData) { return customerMapper.toEntity(customerData); }

    @Override
    public Customer saveDefault() { return save(defaultData().build()); }

    @Override
    public Customer createDefault() { return create(defaultData().build()); }
}
