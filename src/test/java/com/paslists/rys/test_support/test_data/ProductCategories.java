package com.paslists.rys.test_support.test_data;

import com.paslists.rys.product.ProductCategory;
import com.paslists.rys.product.ProductCategoryData;
import com.paslists.rys.product.ProductCategoryMapper;
import com.paslists.rys.product.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductCategories")
public class ProductCategories implements TestDataProvisioning<ProductCategoryData, ProductCategoryData.ProductCategoryDataBuilder, ProductCategory> {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    public static final String DEFAULT_NAME = "default_category";
    public static final String DEFAULT_DESCRIPTION = "default category description";

    @Override
    public ProductCategoryData.ProductCategoryDataBuilder defaultData() {
        return ProductCategoryData.builder()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
    }

    @Override
    public ProductCategory save(ProductCategoryData productCategoryData) { return productCategoryRepository.save(productCategoryData); }

    @Override
    public ProductCategory create(ProductCategoryData productCategoryData) { return productCategoryMapper.toEntity(productCategoryData); }

    @Override
    public ProductCategory saveDefault() { return save(defaultData().build()); }

    @Override
    public ProductCategory createDefault() { return create(defaultData().build()); }

}
