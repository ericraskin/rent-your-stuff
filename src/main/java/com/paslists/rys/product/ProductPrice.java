package com.paslists.rys.product;

import com.paslists.rys.entity.Money;
import com.paslists.rys.entity.StandardEntity;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "RYS_PRODUCT_PRICE", indexes = {
        @Index(name = "IDX_PRODUCTPRICE_PRODUCT_ID", columnList = "PRODUCT_ID")
})
@Entity(name = "rys_ProductPrice")
public class
ProductPrice extends StandardEntity {


    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private Product product;

    @Valid
    @NotNull
    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "PRICE_AMOUNT")),
            @AttributeOverride(name = "currency", column = @Column(name = "PRICE_CURRENCY"))
    })
    private Money price;

    @Column(name = "UNIT", nullable = false)
    @NotNull
    private String unit;

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PriceUnit getUnit() {
        return unit == null ? null : PriceUnit.fromId(unit);
    }

    public void setUnit(PriceUnit unit) {
        this.unit = unit == null ? null : unit.getId();
    }


    @InstanceName
    @DependsOnProperties({"price", "unit"})
    public String getInstanceName() {
        return String.format("%s %s", price, unit);
    }
}