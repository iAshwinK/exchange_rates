package com.currency.exchange.service;

import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class ExchangeService {

    @Autowired
    private DBService dbService;

    public ConcurrentSkipListMap<LocalDateTime, ExchangeRate> dateTimeExchangeMap = new ConcurrentSkipListMap<>(
            Comparator.comparingLong(v -> v.toEpochSecond(ZoneOffset.UTC)));


    public ExchangeRate getLatest(CurrencyType base, CurrencyType destination){
        return dateTimeExchangeMap.lastEntry().getValue();
    }

    public List<ExchangeRate> getHistoric(String startDate, String endDate){
        LocalDateTime startDateTime = LocalDate
                .parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .atStartOfDay()
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime();

        LocalDateTime endDateTime = LocalDate
                .parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .atStartOfDay()
                .plusHours(23)
                .plusMinutes(59)
                .plusSeconds(59)
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime();

        return dbService.getHistoric(startDateTime,endDateTime);
    }




}
