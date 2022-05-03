package com.paslists.rys.product;

import com.paslists.rys.app.test_support.DatabaseCleanup;
import com.paslists.rys.entity.Currency;
import com.paslists.rys.entity.Money;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import io.jmix.core.security.SystemAuthenticator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        databaseCleanup.removeAllEntities(ProductCategory.class);
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
        Optional<Product> savedProduct = systemAuthenticator.withSystem( () -> {
            dataManager.save(product);
            return loadProductWithcategory(product);
        });

        // then

        assertThat(savedProduct)
                .isPresent()
                .get()
                .extracting("category")
                .isEqualTo(productCategory);

    }

    @NotNull
    private Optional<Product> loadProductWithcategory(Product product) {
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
        ProductCategory savedProductCategory = systemAuthenticator.withSystem( () -> dataManager.save(productCategory));
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