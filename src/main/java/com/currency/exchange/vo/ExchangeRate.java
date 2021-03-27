package com.currency.exchange.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ExchangeRate implements Serializable {

    private final CurrencyType currencyType;
    private final BigDecimal rate;
    private final LocalDateTime localDateTime;

    public ExchangeRate(CurrencyType currencyType, BigDecimal rate, LocalDateTime localDateTime) {
        this.currencyType = currencyType;
        this.rate = rate;
        this.localDateTime = localDateTime;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return currencyType == that.currencyType &&
                rate.equals(that.rate) &&
                localDateTime.equals(that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDateTime);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "currencyType=" + currencyType +
                ", rate=" + rate +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
