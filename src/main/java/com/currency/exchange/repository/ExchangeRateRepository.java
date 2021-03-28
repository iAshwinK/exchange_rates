package com.currency.exchange.repository;

import com.currency.exchange.entity.ExchangeRateEntity;
import com.currency.exchange.vo.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, LocalDateTime> {

    ExchangeRateEntity findByBaseCurrency(CurrencyType currencyType);

    List<ExchangeRateEntity> findAllByExchangeDateTimeGreaterThanEqualAndExchangeDateTimeLessThanEqual(LocalDateTime startDate,LocalDateTime endDate);

}
