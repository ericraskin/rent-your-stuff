package com.paslists.rys.product.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.StockItem;

@UiController("rys_StockItem.browse")
@UiDescriptor("stock-item-browse.xml")
@LookupComponent("stockItemsTable")
public class StockItemBrowse extends StandardLookup<StockItem> {
}