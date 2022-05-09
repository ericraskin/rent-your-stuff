package com.paslists.rys.product.screen;

import com.paslists.rys.entity.Currency;
import com.paslists.rys.product.*;
import com.paslists.rys.product.screen.productprice.ProductPriceEdit;
import com.paslists.rys.product.screen.stockitem.StockItemEdit;
import com.paslists.rys.test_support.ui.FormInteractions;
import com.paslists.rys.test_support.ui.ScreenInteractions;
import com.paslists.rys.test_support.ui.TableInteractions;
import com.paslists.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class ProductEditTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    FormInteractions formInteractions;

    private ScreenInteractions screenInteractions;

    private ProductEdit productEdit;

    @Nested
    class withOpenedProductEditForm extends WebIntegrationTest
    {
        @BeforeEach
        void setUp(Screens screens) {
            initProductEditForm(screens);
        }

        @Test
        void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved() {

            // given

            String name = "Foo Product" + UUID.randomUUID();

            formInteractions.setTextFieldValue("nameField",name);

            // when
            OperationResult operationResult = formInteractions.saveForm();

            assertThat(operationResult).isEqualTo(OperationResult.success());

            // then
            Optional<Product> savedProduct = findProductByAttribute("name", name);

            assertThat(savedProduct)
                    .isPresent();
        }

        @Test
        void given_validProductWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved() {

            // given
            String name = "Foo Product" + UUID.randomUUID();
            formInteractions.setTextFieldValue("nameField",name);

            // and
            TableInteractions<ProductPrice> pricesTable = TableInteractions.of(productEdit, ProductPrice.class, "pricesTable");
            pricesTable.create();

            ProductPriceEdit productPriceEdit = screenInteractions.findOpenScreen(ProductPriceEdit.class);
            FormInteractions priceForm = FormInteractions.of(productPriceEdit);

            BigDecimal expectedAmount = BigDecimal.TEN;
            Currency expectedCurrency = Currency.USD;
            PriceUnit expectedUnit = PriceUnit.DAY;

            //priceForm.setCurrencyFieldValue("priceAmountField", expectedAmount, expectedCurrency);
            priceForm.setBigDecimalFieldValue("priceAmountField", expectedAmount);
            priceForm.setEnumFieldValue("priceCurrencyField", expectedCurrency);
            priceForm.setEnumFieldValue("unitField", expectedUnit);

            //when

            OperationResult priceFormResult = priceForm.saveForm();

            // then
            assertThat(priceFormResult).isEqualTo(OperationResult.success());

            // when
            OperationResult productFormResult = formInteractions.saveForm();
            assertThat(productFormResult).isEqualTo(OperationResult.success());

            // then
            Optional<Product> savedProduct = findProductByAttribute("name", name);

            assertThat(savedProduct)
                    .isPresent();

            // and

            List<ProductPrice> prices = savedProduct.get().getPrices();

            assertThat(prices)
                    .hasSize(1);

            // and

            ProductPrice price = prices.get(0);

            assertThat(price.getPrice().getAmount())
                    .isEqualByComparingTo(expectedAmount);

            assertThat(price.getPrice().getCurrency())
                    .isEqualTo(expectedCurrency);

            assertThat(price.getUnit())
                    .isEqualTo(expectedUnit);
        }
        @Test
        void given_productWithoutName_when_saveProductThroughTheForm_then_productIsNotSaved() {

            // given

            formInteractions.setTextFieldValue("nameField", null);

            // when
            OperationResult operationResult = formInteractions.saveForm();

            // then
            assertThat(operationResult).isEqualTo(OperationResult.fail());

            // and

            assertThat(dataManager.load(Product.class).all().list()).isEmpty();
        }

        @Test
        void given_validProductWithStockItem_when_saveProductThroughTheForm_then_productAndStockItemAreSaved() {

            // given
            String name = "Foo Product" + UUID.randomUUID();
            formInteractions.setTextFieldValue("nameField",name);

            // and
            TableInteractions<StockItem> stockItemsTable = TableInteractions.of(productEdit, StockItem.class, "stockItemsTable");
            stockItemsTable.create();

            StockItemEdit stockItemEdit = screenInteractions.findOpenScreen(StockItemEdit.class);
            FormInteractions stockItemForm = FormInteractions.of(stockItemEdit);

            String expectedIdentifier = "Foo ID" + UUID.randomUUID();

            //priceForm.setCurrencyFieldValue("priceAmountField", expectedAmount, expectedCurrency);
            stockItemForm.setTextFieldValue("identifierField", expectedIdentifier);

            //when

            OperationResult stockItemFormResult = stockItemForm.saveForm();

            // then
            assertThat(stockItemFormResult).isEqualTo(OperationResult.success());

            // when
            OperationResult productFormResult = formInteractions.saveForm();
            assertThat(productFormResult).isEqualTo(OperationResult.success());

            // then
            Optional<Product> savedProduct = findProductByAttribute("name", name);

            assertThat(savedProduct)
                    .isPresent();

            // and

            List<StockItem> stockItems = savedProduct.get().getStockItems();

            assertThat(stockItems)
                    .hasSize(1);

            // and

            StockItem stockItem = stockItems.get(0);

            assertThat(stockItem.getIdentifier())
                    .isEqualTo(expectedIdentifier);

        }
    }

    @Nested
    class ProductCategoryTests extends WebIntegrationTest {

        private ProductCategory productCategory1;
        private ProductCategory productCategory2;

        @BeforeEach
        void setUp(Screens screens) {
            productCategory1 = saveProductCategory("Product Category 1");
            productCategory2 = saveProductCategory("Product Category 2");
            initProductEditForm(screens);
        }
        @Test
        void given_twoProductCategoriesArePresent_when_openingTheProductEditor_then_categoriesAreDisplayedInTheComboBox() {

            // expect

            List<ProductCategory> availableProductCategories = formInteractions.getEntityComboBoxValues("categoryField", ProductCategory.class);

            assertThat(availableProductCategories)
                    .contains(productCategory1, productCategory2);
        }

        @Test
        void given_validProductWithCategory_when_saveProductThroughTheForm_then_productAndCategoryAssociationAreSaved() {

            // given

            String name = "Foo Product" + UUID.randomUUID();
            formInteractions.setTextFieldValue("nameField",name);

            // and

            formInteractions.setEntityComboBoxFieldValue("categoryField", productCategory1, ProductCategory.class);

            // when
            OperationResult productFormResult = formInteractions.saveForm();
            assertThat(productFormResult).isEqualTo(OperationResult.success());

            // then
            Optional<Product> savedProduct = findProductByAttribute("name", name);

            assertThat(savedProduct)
                    .isPresent()
                    .get()
                    .extracting("category")
                    .isEqualTo(productCategory1);
        }
    }

    private void initProductEditForm(Screens screens) {
        screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);
    }

    @NotNull
    private Optional<Product> findProductByAttribute(String attribute, String firstName) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal(attribute, firstName))
                .optional();
    }

    @NotNull
    private ProductCategory saveProductCategory(String name) {
        ProductCategory productCategory = dataManager.create(ProductCategory.class);
        productCategory.setName(name);
        return dataManager.save(productCategory);
    }
}