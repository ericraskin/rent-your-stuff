package com.paslists.rys.product;

import com.paslists.rys.entity.Currency;
import com.paslists.rys.entity.Money;
import com.paslists.rys.test_support.TenantUserEnvironment;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class ProductStorageTest {

    @Autowired
    DataManager dataManager;

    @Test
    void given_validProduct_when_save_then_productIsSaved() {

        // given
        Product product = dataManager.create(Product.class);

        product.setName("Foo Product");
        product.setDescription("Foo Description");
        product.setPrices(null);

        // when

        Product savedProduct = dataManager.save(product);

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

        dataManager.save(product, pricePerDay, pricePerWeek);
        Optional<Product> savedProduct = dataManager.load(Id.of(product)).optional();

        // then

        assertThat(savedProduct)
                .isPresent();

    }

    @Test
    void given_validProductWithProductCategory_save_then_productAndCategoryIsSaved() {

        // given
        Product product = dataManager.create(Product.class);

        product.setName("Foo Product");
        product.setDescription("Foo Description");

        // and

        ProductCategory productCategory = saveProductCategory("Foo Category");

        // when

        product.setCategory(productCategory);
        dataManager.save(product);
        Optional<Product> savedProduct = loadProductWithCategory(product);

        // then

        assertThat(savedProduct)
                .isPresent()
                .get()
                .extracting("category")
                .isEqualTo(productCategory);

    }

    @NotNull
    private Optional<Product> loadProductWithCategory(Product product) {
        return dataManager.load(Id.of(product))
                .fetchPlan(productFp -> {
                    productFp.addFetchPlan(FetchPlan.BASE);
                    productFp.add("category", categoryFp -> categoryFp.addFetchPlan(FetchPlan.BASE));
                })
                .optional();
    }

    @NotNull
    private ProductCategory saveProductCategory(String name) {
        ProductCategory productCategory = dataManager.create(ProductCategory.class);
        productCategory.setName(name);
        ProductCategory savedProductCategory = dataManager.save(productCategory);
        return savedProductCategory;
    }

    @NotNull
    private ProductPrice createProductPrice(Product product, BigDecimal amount, PriceUnit priceUnit) {
        ProductPrice productPrice = dataManager.create(ProductPrice.class);
        productPrice.setProduct(product);
        Money money = dataManager.create(Money.class);
        money.setAmount(amount);
        money.setCurrency(Currency.USD);
        productPrice.setPrice(money);
        productPrice.setUnit(priceUnit);
        return productPrice;
    }
}