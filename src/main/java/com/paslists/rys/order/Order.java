package com.paslists.rys.order;

import com.paslists.rys.customer.Customer;
import com.paslists.rys.entity.StandardEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@JmixEntity
@Table(name = "RYS_ORDER", indexes = {
        @Index(name = "IDX_ORDER_CUSTOMER_ID", columnList = "CUSTOMER_ID")
})
@Entity(name = "rys_Order")
public class Order extends StandardEntity {
    @FutureOrPresent
    @Column(name = "ORDER_DATE", nullable = false)
    @NotNull
    private LocalDate orderDate;

    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @InstanceName
    @DependsOnProperties({"customer" ,"orderDate" })
    public String getInstanceName(MetadataTools metadataTools, DatatypeFormatter dataTypeFormatter) {
        return String.format("%s - %s", metadataTools.getInstanceName(customer), dataTypeFormatter.formatLocalDate(orderDate));
    }
}