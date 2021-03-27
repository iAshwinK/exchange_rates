package com.currency.exchange.controller;

import com.currency.exchange.service.DBService;
import com.currency.exchange.service.ExchangeService;
import com.currency.exchange.service.GatewayService;
import com.currency.exchange.vo.CronVo;
import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private DBService dbService;

    @GetMapping("/{base}/{currency}")
    @ApiOperation(value = "Retrieve Exchange Rates",tags = {"Exchange Rates"})
    public ResponseEntity<ExchangeRate> getCustomer(@PathVariable(value = "base") CurrencyType base, @PathVariable(value = "currency") CurrencyType currency){
        ExchangeRate response = exchangeService.getLatest(base, currency);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/saveMock")
    @ApiOperation(value = "Retrieve Exchange Rates",tags = {"Exchange Rates"})
    public ResponseEntity<Void> mockSave(){

        ExchangeRate exchangeRate = new ExchangeRate(CurrencyType.USD,new BigDecimal(12345.323), LocalDateTime.now());
        dbService.save(exchangeRate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mockRetrieve")
    @ApiOperation(value = "Retrieve Mock ",tags = {"Exchange Rates"})
    public ResponseEntity<ExchangeRate> mockRetrieve(){
        ExchangeRate response = dbService.getByBase(CurrencyType.BTC);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
