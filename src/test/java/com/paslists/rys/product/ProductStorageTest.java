package com.paslists.rys.product;

import com.paslists.rys.app.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductStorageTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Product.class);
        databaseCleanup.removeAllEntities(ProductPrice.class);
    }

    @Test
    void given_validProduct_when_save_then_productIsSaved() {

        // given
        Product product = dataManager.create(Product.class);

        product.setName("Foo Product");
        product.setDescription("Foo Description");
        product.setPrices(null);

        // when

        Product savedProduct = systemAuthenticator.withSystem( () -> dataManager.save(product));

        // then

        assertThat(savedProduct.getId())
                .isNotNull();

    }


    @Test
    void given_validProductWithProductPrices_save_then_productAndPricesAreSaved() {

        // given
        Product product = dataManager.create(Product.class);

        product.setName("Foo Product");
        product.setDescription("Foo Description");

        // and

        ProductPrice pricePerDay = createProductPrice(product, BigDecimal.ONE, PriceUnit.DAY);
        ProductPrice pricePerWeek = createProductPrice(product, BigDecimal.TEN, PriceUnit.WEEK);
        product.setPrices(List.of(pricePerDay, pricePerWeek));

        // when

        Product savedProduct = systemAuthenticator.withSystem( () -> dataManager.save(product));

        // then

        assertThat(savedProduct.getId())
                .isNotNull();

    }
    @NotNull
    private ProductPrice createProductPrice(Product product, BigDecimal amount, PriceUnit priceUnit) {
        ProductPrice productPrice = dataManager.create(ProductPrice.class);
        productPrice.setProduct(product);
        productPrice.setAmount(amount);
        productPrice.setUnit(priceUnit);
        return productPrice;
    }
}