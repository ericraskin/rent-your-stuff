package com.paslists.rys.product;

import com.paslists.rys.entity.Currency;
import com.paslists.rys.test_support.TenantUserEnvironment;
import com.paslists.rys.test_support.test_data.ProductCategories;
import com.paslists.rys.test_support.test_data.ProductPrices;
import com.paslists.rys.test_support.test_data.Products;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class ProductStorageTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Products products;

    @Autowired
    ProductPrices productPrices;

    @Autowired
    ProductCategories productCategories;

    @Test
    void given_validProduct_when_save_then_productIsSaved() {

        // given
        Product product = products.save(
                products.defaultData()
                        .name("Foo Product")
                        .description("Foo Description")
                        .build()
        );

        // then

        assertThat(product.getId())
                .isNotNull();

    }


    @Test
    void given_validProductWithProductPrices_save_then_productAndPricesAreSaved() {

        // given
        Product product = products.saveDefault();

        // and

        ProductPrice pricePerDay = productPrices.save(
                productPrices.defaultData()
                        .product(product)
                        .price(productPrices.money(BigDecimal.ONE, Currency.USD))
                        .unit(PriceUnit.DAY.getId())
                        .build()
        );

        ProductPrice pricePerWeek = productPrices.save(
                productPrices.defaultData()
                        .product(product)
                        .price(productPrices.money(BigDecimal.TEN, Currency.USD))
                        .unit(PriceUnit.WEEK.getId())
                        .build()
        );

        // when

        Optional<Product> savedProduct = dataManager.load(Id.of(product)).optional();

        // then

        assertThat(savedProduct)
                .isPresent();

        assertThat(savedProduct.get().getPrices())
                .containsExactlyInAnyOrder(pricePerDay, pricePerWeek);

    }

    @Test
    void given_validProductWithProductCategory_save_then_productAndCategoryIsSaved() {

        // given

        ProductCategory productCategory = productCategories.saveDefault();

        // and

        Product product = products.save(
                products.defaultData()
                        .category(productCategory)
                        .build()
        );


        // then

        assertThat(loadProductWithCategory(product))
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

}