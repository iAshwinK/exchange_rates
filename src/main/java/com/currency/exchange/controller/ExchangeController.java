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

import java.util.List;

@RestController
@RequestMapping(value = "/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping("/{base}/{currency}")
    @ApiOperation(value = "Retrieve Latest Rates",tags = {"Exchange Rates"})
    public ResponseEntity<ExchangeRate> getLatest(@PathVariable(value = "base") CurrencyType base, @PathVariable(value = "currency") CurrencyType currency) {
        ExchangeRate response = exchangeService.getLatest(base, currency);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/historic/{startDate}/{endDate}")
    @ApiOperation(value = "Retrieve Exchange Rates",tags = {"Exchange Rates"})
    public ResponseEntity<List<ExchangeRate>> getHistoric(@PathVariable(value = "startDate") String startDate, @PathVariable(value = "endDate") String endDate) {
        List<ExchangeRate> response = exchangeService.getHistoric(startDate,endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
