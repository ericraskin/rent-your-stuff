package com.paslists.rys.product.screen.stockitem;

import io.jmix.ui.screen.*;
import com.paslists.rys.product.StockItem;

@UiController("rys_StockItem.edit")
@UiDescriptor("stock-item-edit.xml")
@EditedEntityContainer("stockItemDc")
public class StockItemEdit extends StandardEditor<StockItem> {
}