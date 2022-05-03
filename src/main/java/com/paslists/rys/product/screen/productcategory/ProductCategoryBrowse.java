package com.paslists.rys.product.screen.productcategory;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.ProductCategory;

@UiController("rys_ProductCategory.browse")
@UiDescriptor("product-category-browse.xml")
@LookupComponent("productCategoriesTable")
public class ProductCategoryBrowse extends StandardLookup<ProductCategory> {
}