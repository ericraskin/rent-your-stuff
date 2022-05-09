package com.paslists.rys;


import com.paslists.rys.entity.Currency;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
//@ConstructorBinding
public class RentYourStuffProperties {

    @NotNull
    Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


}
