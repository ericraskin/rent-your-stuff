package com.paslists.rys.product.screen;

import com.paslists.rys.app.test_support.DatabaseCleanup;
import com.paslists.rys.product.PriceUnit;
import com.paslists.rys.product.Product;
import com.paslists.rys.product.ProductPrice;
import com.paslists.rys.product.screen.productprice.ProductPriceEdit;
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

    @Autowired
    DatabaseCleanup<Product> databaseCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Product.class);
    }

    @Test
    void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);

        formInteractions = FormInteractions.of(productEdit);

        // and
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
    void given_productWithoutStreet_when_saveProductThroughTheForm_then_productIsNotSaved(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

        // and

        formInteractions.setTextFieldValue("nameField", null);

        // when
        OperationResult operationResult = formInteractions.saveForm();

        // then
        assertThat(operationResult).isEqualTo(OperationResult.fail());

        // and

        assertThat(dataManager.load(Product.class).all().list()).isEmpty();
    }

    @Test
    void given_validProductWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);

        formInteractions = FormInteractions.of(productEdit);

        // and
        String name = "Foo Product" + UUID.randomUUID();
        formInteractions.setTextFieldValue("nameField",name);

        // and
        TableInteractions<ProductPrice> pricesTable = TableInteractions.of(productEdit, ProductPrice.class, "pricesTable");
        pricesTable.create();

        ProductPriceEdit productPriceEdit = screenInteractions.findOpenScreen(ProductPriceEdit.class);
        FormInteractions priceForm = FormInteractions.of(productPriceEdit);

        BigDecimal expectedAmount = BigDecimal.TEN;
        PriceUnit expectedUnit = PriceUnit.DAY;

        priceForm.setNumberFieldValue("amountField", expectedAmount);
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

        assertThat(price.getAmount())
                .isEqualByComparingTo(expectedAmount);

        assertThat(price.getUnit())
                .isEqualTo(expectedUnit);
    }

    @NotNull
    private Optional<Product> findProductByAttribute(String attribute, String firstName) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal(attribute, firstName))
                .optional();
    }

}