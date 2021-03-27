package com.currency.exchange.service;

import com.currency.exchange.entity.ExchangeRateEntity;
import com.currency.exchange.repository.ExchangeRateRepository;
import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public void save(ExchangeRate exchangeRate) {
        exchangeRateRepository.save(mapTo(exchangeRate));
    }

    public ExchangeRate getByBase(CurrencyType currencyType) {
        ExchangeRateEntity entity = exchangeRateRepository.findByBaseCurrency(currencyType);
        return mapTo(entity);
    }

    private ExchangeRateEntity mapTo(ExchangeRate exchangeRate) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        exchangeRateEntity.setBaseCurrency(CurrencyType.BTC);
        exchangeRateEntity.setDestinationCurrency(exchangeRate.getCurrencyType());
        exchangeRateEntity.setExchangeDateTime(exchangeRate.getLocalDateTime());
        exchangeRateEntity.setExchangeValue(exchangeRate.getRate());

        return exchangeRateEntity;
    }

    private ExchangeRate mapTo(ExchangeRateEntity entity) {
        return new ExchangeRate(
                entity.getDestinationCurrency(),
                entity.getExchangeValue(),
                entity.getExchangeDateTime()
        );
    }

}
