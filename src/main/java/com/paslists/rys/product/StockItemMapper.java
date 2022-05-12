package com.paslists.rys.product;

import com.paslists.rys.entity.JmixEntityFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface StockItemMapper {

    StockItem toEntity(StockItemData stockItemtData);

}
