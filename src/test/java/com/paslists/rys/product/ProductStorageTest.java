package com.paslists.rys.product;

import com.paslists.rys.app.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductStorageTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseCleanup<Product> databaseCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Product.class);
    }

    @Test
    void given_validProduct_when_save_then_productIsSaved() {

        // given
        Product product = dataManager.create(Product.class);

        product.setName("Foo Product");
        product.setDescription("Foo Description");

        // when

        Product savedProduct = systemAuthenticator.withSystem( () -> dataManager.save(product));

        // then

        assertThat(savedProduct.getId())
                .isNotNull();

    }
}