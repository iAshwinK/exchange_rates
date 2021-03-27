package com.currency.exchange.service;

import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import com.currency.exchange.vo.LatestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GatewayService {
    @Value("${api.latest}")
    private String latestUrl;

    @Value("${api.historic}")
    private String historicDataUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ExchangeRate getLatestRate(CurrencyType baseType, CurrencyType destinationType){

        ResponseEntity<LatestResponse> response = restTemplate.getForEntity(latestUrl + "?base={base}&symbols={symbol}",
                LatestResponse.class,
                baseType,
                destinationType);
        
        if(response.getStatusCode().is2xxSuccessful()){
            LatestResponse latestResponse = response.getBody();
            //current date time, this following statement will be removed if we get date-time as well in response
            LocalDateTime currentDateTime = LocalDateTime.now();
            return mapToExchangeRate(latestResponse,destinationType,currentDateTime);
        }
        return null;
    }

/*    public List<ExchangeRate> getHistoricData(CurrencyType baseType, CurrencyType destinationType,LocalDateTime startTime,LocalDateTime endTime){

        restTemplate.getForEntity(historicDataUrl+"?start_date={start_date}&end_date={end_date}&base={base}&symbols={symbol}",
                LatestResponse.class,
                startTime,
                endTime,
                baseType,
                destinationType);

    }*/


    private ExchangeRate mapToExchangeRate(LatestResponse latestResponse, CurrencyType destinatonType, LocalDateTime dateTime){
        BigDecimal value = latestResponse.getRates().get(destinatonType.toString());
        ExchangeRate exchangeRate = new ExchangeRate(destinatonType,value,dateTime);
        return exchangeRate;
    }

}
