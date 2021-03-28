package com.currency.exchange.controller;

import com.currency.exchange.service.DBService;
import com.currency.exchange.service.ExchangeService;
import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/mock")
public class MockController {

    @Autowired
    private DBService dbService;
    @Autowired
    private ExchangeService exchangeService;

    @GetMapping("/save")
    @ApiOperation(value = "save", tags = {"Mock API"})
    public ResponseEntity<Void> mockSave() {

        ExchangeRate exchangeRate = new ExchangeRate(CurrencyType.USD, new BigDecimal(12345.323), LocalDateTime.now());
        dbService.save(exchangeRate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/retrieve")
    @ApiOperation(value = "Retrieve ", tags = {"Mock API"})
    public ResponseEntity<ExchangeRate> mockRetrieve() {
        ExchangeRate response = dbService.getByBase(CurrencyType.BTC);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/historic/{startDate}/{endDate}")
    @ApiOperation(value = "Retrieve Exchange Rates", tags = {"Mock API"})
    public ResponseEntity<List<ExchangeRate>> getHistoric(@PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate) {
        List<ExchangeRate> response = exchangeService.getHistoric(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
