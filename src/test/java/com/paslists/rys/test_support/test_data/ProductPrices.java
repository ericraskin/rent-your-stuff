package com.paslists.rys.test_support.test_data;

import com.paslists.rys.entity.Currency;
import com.paslists.rys.entity.Money;
import com.paslists.rys.entity.MoneyData;
import com.paslists.rys.entity.MoneyMapper;
import com.paslists.rys.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("rys_ProductPrices")
public class ProductPrices implements TestDataProvisioning<ProductPriceData, ProductPriceData.ProductPriceDataBuilder, ProductPrice> {

    @Autowired
    private Products products;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    ProductPriceMapper productPriceMapper;

    @Autowired
    MoneyMapper moneyMapper;

    public static final PriceUnit DEFAULT_PRICE_UNIT = PriceUnit.DAY;
    public static final Currency DEFAULT_CURRENCY = Currency.USD;
    public static final BigDecimal DEFAULT_AMOUNT = BigDecimal.TEN;

    @Override
    public ProductPriceData.ProductPriceDataBuilder defaultData() {
        return ProductPriceData.builder()
                .product(products.createDefault())
                .unit(DEFAULT_PRICE_UNIT.getId())
                .price(defaultPrice());
    }

    public Money defaultPrice() {
        return money(DEFAULT_AMOUNT, DEFAULT_CURRENCY);
    }

    @Override
    public ProductPrice save(ProductPriceData productPriceData) { return productPriceRepository.save(productPriceData); }

    @Override
    public ProductPrice create(ProductPriceData productPriceData) { return productPriceMapper.toEntity(productPriceData); }

    @Override
    public ProductPrice saveDefault() { return save(defaultData().build()); }

    @Override
    public ProductPrice createDefault() { return create(defaultData().build()); }

    public Money money(BigDecimal amount, Currency currency) {
        return moneyMapper.toEntity(MoneyData.builder()
                .amount(amount)
                .currency(currency.getId())
                .build());
    }
}
