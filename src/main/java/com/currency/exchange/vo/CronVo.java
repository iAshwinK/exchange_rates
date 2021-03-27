package com.currency.exchange.vo;

import java.io.Serializable;

public class CronVo implements Serializable {
    private String cronExpression;

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Override
    public String toString() {
        return "CronVo{" +
                "cronExpression='" + cronExpression + '\'' +
                '}';
    }
}
