package com.paslists.rys.order.screen;

import io.jmix.ui.screen.*;
import com.paslists.rys.order.Order;

@UiController("rys_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}