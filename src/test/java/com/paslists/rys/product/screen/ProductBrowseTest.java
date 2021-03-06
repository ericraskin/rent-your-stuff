package com.paslists.rys.product.screen;

import com.paslists.rys.product.Product;
import com.paslists.rys.test_support.ui.ScreenInteractions;
import com.paslists.rys.test_support.ui.TableInteractions;
import com.paslists.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class ProductBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Product product;

    @BeforeEach
    void setUp() {
        product = dataManager.create(Product.class);
        product.setName("Foo Product");

        product = dataManager.save(product);
    }

    @Test
    void given_oneProductExists_when_openProductBrowse_then_tableContainsTheProduct(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);

        TableInteractions<Product> productTable = productTable(productBrowse);

        // expect:
        assertThat(productTable.firstItem())
                .isEqualTo(product);
    }

    @Test
    void given_oneProductExists_when_editProduct_then_editProductEditorIsShown(Screens screens) {

        // given
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);

        TableInteractions<Product> productTable = productTable(productBrowse);

        // and
        Product firstProduct = productTable.firstItem();

        // when
        productTable.edit(firstProduct);

        // then
        ProductEdit productEdit = screenInteractions.findOpenScreen(ProductEdit.class);

        assertThat(productEdit.getEditedEntity())
                .isEqualTo(firstProduct);

    }

    @NotNull
    private TableInteractions<Product> productTable(ProductBrowse productBrowse) {
        return TableInteractions.of(productBrowse, Product.class, "productsTable");
    }

}
