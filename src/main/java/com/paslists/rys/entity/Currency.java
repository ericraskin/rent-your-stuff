package com.paslists.rys.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Currency implements EnumClass<String> {

    USD("USD"),
    EUR("EUR");

    private String id;

    Currency(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Currency fromId(String id) {
        for (Currency at : Currency.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}