package com.paslists.rys.test_support.test_data;

import com.paslists.rys.product.Product;
import com.paslists.rys.product.ProductData;
import com.paslists.rys.product.ProductMapper;
import com.paslists.rys.product.ProductRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_Products")
public class Products implements TestDataProvisioning<ProductData, ProductData.ProductDataBuilder, Product> {

    @Autowired
    private DataManager dataManager;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    public static String DEFAULT_NAME = "product_name";

    @Override
    public ProductData.ProductDataBuilder defaultData() {
        return ProductData.builder()
                .name(DEFAULT_NAME)
                .category(null);
    }

    @Override
    public Product save(ProductData productData) { return productRepository.save(productData); }

    @Override
    public Product create(ProductData productData) { return productMapper.toEntity(productData); }

    @Override
    public Product saveDefault() { return save(defaultData().build()); }

    @Override
    public Product createDefault() { return create(defaultData().build()); }
}
