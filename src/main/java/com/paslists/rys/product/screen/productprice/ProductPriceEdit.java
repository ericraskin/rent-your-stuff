package com.paslists.rys.product.screen.productprice;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.ProductPrice;

@UiController("rys_ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {
}