package com.paslists.rys.product;

import com.paslists.rys.entity.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductCategoryRepository")
public class ProductCategoryRepository implements EntityRepository<ProductCategoryData, ProductCategory> {
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Autowired
    DataManager dataManager;

    public ProductCategory save(ProductCategoryData productCategoryData) { return dataManager.save(productCategoryMapper.toEntity(productCategoryData)); }
}
