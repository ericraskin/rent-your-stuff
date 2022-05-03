package com.paslists.rys.product;

import com.paslists.rys.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@JmixEntity
@Table(name = "RYS_PRODUCT_CATEGORY")
@Entity(name = "rys_ProductCategory")
public class ProductCategory extends StandardEntity {
    @NotBlank
    @InstanceName
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

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