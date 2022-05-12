package com.paslists.rys.product;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductPriceRepository")
public class ProductPriceRepository implements EntityRepository<ProductPriceData, ProductPrice> {
    @Autowired
    ProductPriceMapper productPriceMapper;

    @Autowired
    DataManager dataManager;

    public ProductPrice save(ProductPriceData productPriceData) { return dataManager.save(productPriceMapper.toEntity(productPriceData)); }
}
