package com.paslists.rys.product;

import com.paslists.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductCategoryValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations<ProductCategory> validations;
    private ProductCategory productCategory;

    @BeforeEach
    void setUp() {
        productCategory = dataManager.create(ProductCategory.class);
    }

    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

        // given

        productCategory.setName("Foo Category");

        // then

        validations.assertNoViolations(productCategory);

    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void given_ProductCategoryWithoutName_when_validate_then_oneViolationOccurs(String name) {

        // given

        productCategory.setName(name);

        // then

        validations.assertOneViolationWith(productCategory, "name", "NotBlank");
    }
}