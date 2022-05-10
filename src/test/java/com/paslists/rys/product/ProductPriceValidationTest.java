package com.paslists.rys.product;

import com.paslists.rys.entity.Currency;
import com.paslists.rys.entity.Money;
import com.paslists.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ProductPriceValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations<ProductPrice> validations;
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

        // then

        validations.assertNoViolations(productPrice);

    }

    @Test
    void given_ProductPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.getPrice().setAmount(BigDecimal.ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.setUnit(null);

        // when
        List<Validations.ValidationResult<ProductPrice>> violations = validations.validate(productPrice);

        // then

        validations.assertOneViolationWith(productPrice, "unit", "NotNull");
    }

    @Test
    void given_ProductwithoutPrice_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.setPrice(null);

        // when
        List<Validations.ValidationResult<ProductPrice>> violations = validations.validate(productPrice);

        // then

        validations.assertOneViolationWith(productPrice, "price", "NotNull");
    }

    @Test
    void given_ProductPriceWithoutAmount_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and

        productPrice.getPrice().setAmount(null);

        // when
        List<Validations.ValidationResult<ProductPrice>> violations = validations.validate(productPrice);

        // then

        validations.assertOneViolationWith(productPrice, "price.amount", "NotNull");
    }

    @Test
    void given_ProductPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.getPrice().setAmount(BigDecimal.ONE);

        // and

        productPrice.setProduct(null);

        // when
        List<Validations.ValidationResult<ProductPrice>> violations = validations.validate(productPrice);

        // then

        validations.assertOneViolationWith(productPrice, "product", "NotNull");
    }

}