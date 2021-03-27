package com.currency.exchange.service;

import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class ExchangeService {

    public ConcurrentSkipListMap<LocalDateTime, ExchangeRate> dateTimeExchangeMap = new ConcurrentSkipListMap<>(
            Comparator.comparingLong(v -> v.toEpochSecond(ZoneOffset.UTC)));


    public ExchangeRate getLatest(CurrencyType base, CurrencyType destination){
        return dateTimeExchangeMap.lastEntry().getValue();
    }



}
