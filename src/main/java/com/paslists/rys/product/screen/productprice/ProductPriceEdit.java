package com.paslists.rys.product.screen.productprice;

import com.paslists.rys.RentYourStuffProperties;
import com.paslists.rys.entity.Currency;
import com.paslists.rys.entity.Money;
import com.paslists.rys.product.ProductPrice;
import io.jmix.core.Messages;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("rys_ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {

    @Autowired
    private ComboBox<Currency> priceCurrencyField;

    @Autowired
    private DataContext dataContext;

    @Autowired
    private RentYourStuffProperties rentYourStuffProperties;

    @Autowired
    private Messages messages;

    @Subscribe
    public void onInitEntity(InitEntityEvent<ProductPrice> event) {
        Money price = dataContext.create(Money.class);
        price.setCurrency(rentYourStuffProperties.getCurrency());
        event.getEntity().setPrice(price);
    }

    @Subscribe
    public void onAfterShow(BeforeShowEvent event) {
        if (getEditedEntity().getPrice() != null && getEditedEntity().getPrice().getCurrency() != null) {
            priceCurrencyField.setValue(Currency.USD);
        }
    }



}