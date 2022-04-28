package com.paslists.rys.product.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.Product;

@UiController("rys_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {
}