package com.paslists.rys.order;

import com.paslists.rys.entity.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderLineMapper {

    OrderLine toEntity(OrderLineData orderLineData);

}
