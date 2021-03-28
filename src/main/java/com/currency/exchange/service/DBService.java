package com.currency.exchange.service;

import com.currency.exchange.entity.ExchangeRateEntity;
import com.currency.exchange.repository.ExchangeRateRepository;
import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ExchangeRate> getHistoric(LocalDateTime startTime, LocalDateTime endTime){
        List<ExchangeRateEntity> result = exchangeRateRepository.findAllByExchangeDateTimeGreaterThanEqualAndExchangeDateTimeLessThanEqual(startTime, endTime);

        return result
                .stream()
                .map(this::mapTo)
                .sorted(Comparator.comparing(ExchangeRate::getLocalDateTime))
                .collect(Collectors.toList());
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
