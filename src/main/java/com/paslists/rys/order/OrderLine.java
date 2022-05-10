package com.paslists.rys.order;

import com.paslists.rys.entity.StandardEntity;
import com.paslists.rys.product.StockItem;
import com.paslists.rys.validation.ValidRentalPeriod;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.validation.group.UiCrossFieldChecks;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDateTime;

@JmixEntity
@Table(name = "RYS_ORDER_LINE", indexes = {
        @Index(name = "IDX_ORDERLINE_STOCK_ITEM_ID", columnList = "STOCK_ITEM_ID"),
        @Index(name = "IDX_ORDERLINE_ORDER_ID", columnList = "ORDER_ID")
})
@Entity(name = "rys_OrderLine")
@ValidRentalPeriod(groups = {Default.class, UiCrossFieldChecks.class})
public class OrderLine extends StandardEntity {
    @InstanceName
    @JoinColumn(name = "STOCK_ITEM_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private StockItem stockItem;

    @OnDelete(DeletePolicy.CASCADE)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @FutureOrPresent
    @Column(name = "STARTS_AT", nullable = false)
    @NotNull
    private LocalDateTime startsAt;

    @FutureOrPresent
    @Column(name = "ENDS_AT", nullable = false)
    @NotNull
    private LocalDateTime endsAt;

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
}