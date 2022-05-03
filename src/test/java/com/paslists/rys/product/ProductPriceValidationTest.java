package com.paslists.rys.product;

import com.paslists.rys.entity.Currency;
import com.paslists.rys.entity.Money;
import com.paslists.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
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
        Money price = dataManager.create(Money.class);
        price.setCurrency(Currency.USD);
        productPrice.setPrice(price);
    }

    @Test
    void given_validProductPrice_when_validate_then_noViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);

        productPrice.getPrice().setAmount(BigDecimal.ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).isEmpty();

    }

    @Test
    void given_ProductPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.getPrice().setAmount(BigDecimal.ONE);
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
    void given_ProductwithoutPrice_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.setPrice(null);

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductPrice> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("price");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));

    }

    @Test
    void given_ProductPriceWithoutAmount_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.getPrice().setAmount(null);

        // when
        List<ValidationVerification.ValidationResult<ProductPrice>> violations = validationVerification.validate(productPrice);

        // then

        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResult<ProductPrice> unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute()).
                isEqualTo("price.amount");

        assertThat(unitViolation.getErrorType()).
                isEqualTo(validationVerification.validationMessage("NotNull"));

    }

    @Test
    void given_ProductPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.getPrice().setAmount(BigDecimal.ONE);

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

}