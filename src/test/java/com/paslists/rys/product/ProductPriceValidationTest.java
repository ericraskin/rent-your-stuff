package com.paslists.rys.product;

import com.paslists.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductPriceValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification<ProductPrice> validationVerification;
    private ProductPrice productPrice;

    @BeforeEach
    void setUp() {
        productPrice = dataManager.create(ProductPrice.class);
    }

    @Test
    void given_validProductPrice_when_validate_then_noViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setAmount(BigDecimal.ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).isEmpty();

    }

    @Test
    void given_ProductPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setAmount(BigDecimal.ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.setUnit(null);

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductPrice> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("unit");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));

    }

    @Test
    void given_ProductPriceWithoutAmount_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setAmount(BigDecimal.ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.setAmount(null);

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductPrice> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("amount");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));

    }

    @Test
    void given_ProductPriceWithNegativeAmount_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.setAmount(negativeAmount());

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductPrice> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("amount");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("PositiveOrZero"));

    }

    @Test
    void given_ProductPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setAmount(BigDecimal.ONE);

        // and

        productPrice.setProduct(null);

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductPrice> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("product");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));

    }

    @NotNull
    private BigDecimal negativeAmount() {
        return BigDecimal.valueOf(-10d);
    }

}