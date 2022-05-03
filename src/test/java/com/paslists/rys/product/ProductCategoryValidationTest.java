package com.paslists.rys.product;

import com.paslists.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductCategoryValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification<ProductCategory> validationVerification;
    private ProductCategory ProductCategory;

    @BeforeEach
    void setUp() {
        ProductCategory = dataManager.create(ProductCategory.class);
    }

    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

        // given

        ProductCategory.setName("Foo Category");

        // when
        List<ValidationVerification.ValidationResult<ProductCategory>> violations = validationVerification.validate(ProductCategory);

        // then

        assertThat(violations).isEmpty();

    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void given_ProductCategoryWithoutName_when_validate_then_oneViolationOccurs(String name) {

        // given

        ProductCategory.setName(name);

        // when
        List<ValidationVerification.ValidationResult<ProductCategory>> violations = validationVerification.validate(ProductCategory);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductCategory> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("name");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotBlank"));

    }
}