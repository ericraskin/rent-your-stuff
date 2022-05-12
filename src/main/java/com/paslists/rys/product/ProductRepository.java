package com.paslists.rys.product;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductRepository")
public class ProductRepository implements EntityRepository<ProductData, Product> {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    DataManager dataManager;

    public Product save(ProductData productData) { return dataManager.save(productMapper.toEntity(productData)); }
}
