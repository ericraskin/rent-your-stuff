package com.paslists.rys.customer;

import com.paslists.rys.entity.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerData {
    String firstName;
    String lastName;
    String email;
    Address address;
}
