package com.paslists.rys.customer;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_CustomerRepository")
public class CustomerRepository implements EntityRepository<CustomerData, Customer> {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    DataManager dataManager;

    public Customer save(CustomerData customerData) { return dataManager.save(customerMapper.toEntity(customerData)); }
}
