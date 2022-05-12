package com.paslists.rys.test_support.test_data;

import com.paslists.rys.product.StockItem;
import com.paslists.rys.product.StockItemData;
import com.paslists.rys.product.StockItemMapper;
import com.paslists.rys.product.StockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_StockItems")
public class StockItems implements TestDataProvisioning<StockItemData, StockItemData.StockItemDataBuilder, StockItem> {

    @Autowired
    StockItemRepository stockItemRepository;

    @Autowired
    Products products;
    @Autowired
    StockItemMapper stockItemMapper;

    public static final String DEFAULT_IDENTIFIER = "default_sku";

    @Override
    public StockItemData.StockItemDataBuilder defaultData() {
        return StockItemData.builder()
                .identifier(DEFAULT_IDENTIFIER)
                .product(products.createDefault());
    }

    @Override
    public StockItem save(StockItemData stockItemData) { return stockItemRepository.save(stockItemData); }

    @Override
    public StockItem create(StockItemData stockItemData) { return stockItemMapper.toEntity(stockItemData); }

    @Override
    public StockItem saveDefault() { return save(defaultData().build()); }

    @Override
    public StockItem createDefault() { return create(defaultData().build()); }

}
