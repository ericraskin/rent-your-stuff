package com.paslists.rys.product;

import com.paslists.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations<Product> validations;
    private Product product;

    @BeforeEach
    void setUp() {
        product = dataManager.create(Product.class);
    }

    @Test
    void given_validProduct_when_validate_then_noViolationOccurs() {

        // given

        product.setName("validName");

        // then

        validations.assertNoViolations(product);

    }

    @Test
    void given_productWithoutName_when_validate_then_oneViolationOccurs() {

        // given

        product.setName(null);

        // then

        validations.assertOneViolationWith(product, "name", "NotNull");
    }

}