package com.paslists.rys.product;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_StockItemRepository")
public class StockItemRepository implements EntityRepository<StockItemData, StockItem> {
    @Autowired
    StockItemMapper stockItemMapper;

    @Autowired
    DataManager dataManager;

    public StockItem save(StockItemData stockItemData) { return dataManager.save(stockItemMapper.toEntity(stockItemData)); }
}
