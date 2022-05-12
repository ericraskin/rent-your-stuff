package com.paslists.rys.customer;

import com.paslists.rys.entity.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface CustomerMapper {

    Customer toEntity(CustomerData customerData);

}
