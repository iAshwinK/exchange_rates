package com.currency.exchange.entity;

import com.currency.exchange.vo.CurrencyType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "ExchangeRateEntity")
@Table(name = "exchange_rates")
public class ExchangeRateEntity implements Serializable {

    @Id
    @Column(name="exchange_date_time",unique = true)
    private LocalDateTime exchangeDateTime;

    @Column(name = "base_currency",nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType baseCurrency;

    @Column(name = "destination_currency",nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType destinationCurrency;

    @Column(name = "exchange_value",nullable = false)
    private BigDecimal exchangeValue;

    public ExchangeRateEntity() {
    }

    public LocalDateTime getExchangeDateTime() {
        return exchangeDateTime;
    }

    public void setExchangeDateTime(LocalDateTime exchangeDateTime) {
        this.exchangeDateTime = exchangeDateTime;
    }

    public CurrencyType getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyType baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyType getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(CurrencyType destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public BigDecimal getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(BigDecimal exchangeValue) {
        this.exchangeValue = exchangeValue;
    }
}
