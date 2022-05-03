package com.paslists.rys.product.screen.productcategory;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.ProductCategory;

@UiController("rys_ProductCategory.edit")
@UiDescriptor("product-category-edit.xml")
@EditedEntityContainer("productCategoryDc")
public class ProductCategoryEdit extends StandardEditor<ProductCategory> {
}