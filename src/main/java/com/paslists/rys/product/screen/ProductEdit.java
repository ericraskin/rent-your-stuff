package com.paslists.rys.product.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.Product;

@UiController("rys_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
}