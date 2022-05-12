package com.paslists.rys.order.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.order.OrderLine;

@UiController("rys_OrderLine.edit")
@UiDescriptor("order-line-edit.xml")
@EditedEntityContainer("orderLineDc")
public class OrderLineEdit extends StandardEditor<OrderLine> {
}