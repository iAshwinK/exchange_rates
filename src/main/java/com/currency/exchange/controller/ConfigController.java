package com.currency.exchange.controller;

import com.currency.exchange.config.RateFetchScheduler;
import com.currency.exchange.vo.CronVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private RateFetchScheduler rateFetchScheduler;

    @PostMapping("/{cronExpression}")
    @ApiOperation(value = "Config Period",tags = {"Config"})
    public ResponseEntity<Void> configPeriod(@RequestBody CronVo cronVo){
        rateFetchScheduler.updateRateSchedule(cronVo.getCronExpression());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
