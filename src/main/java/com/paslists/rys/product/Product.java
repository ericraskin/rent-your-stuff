package com.paslists.rys.product;

import com.paslists.rys.entity.StandardEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "RYS_PRODUCT", indexes = {
        @Index(name = "IDX_PRODUCT_CATEGORY_ID", columnList = "CATEGORY_ID")
})
@Entity(name = "rys_Product")
public class Product extends StandardEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductPrice> prices;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory category;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public List<ProductPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductPrice> prices) {
        this.prices = prices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}