package com.paslists.rys.entity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface MoneyMapper {

    Money toEntity(MoneyData moneyData);

}
