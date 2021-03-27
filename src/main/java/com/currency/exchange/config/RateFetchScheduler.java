package com.currency.exchange.config;


import com.currency.exchange.service.ExchangeService;
import com.currency.exchange.service.GatewayService;
import com.currency.exchange.vo.CurrencyType;
import com.currency.exchange.vo.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class RateFetchScheduler implements SchedulingConfigurer, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateFetchScheduler.class);
    private static final Integer CANCEL_SCHEDULED_TASK_DELAY_THRESHOLD_IN_SECONDS = 5;
    private final String jobName = "rateFetch";

    private Date nextExecutionTime = null;
    private ScheduledTaskRegistrar scheduledTaskRegistrar;
    private ScheduledFuture<?> scheduledFuture;

    private String cronExpression = "0/30 * * * * ?";

    @Autowired
    private TaskScheduler currencyTaskScheduler;
    @Autowired
    private GatewayService gatewayService;
    @Autowired
    private ExchangeService exchangeService;



    @Override
    public void run() {
        LOGGER.info("Run started");
        ExchangeRate exchangeRate = gatewayService.getLatestRate(CurrencyType.BTC, CurrencyType.USD);
        exchangeService.dateTimeExchangeMap.put(exchangeRate.getLocalDateTime(),exchangeRate);
        LOGGER.info("Size of events:"+exchangeService.dateTimeExchangeMap.size());
        LOGGER.info("Run completed in.");
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (this.scheduledTaskRegistrar == null) {
            this.scheduledTaskRegistrar = taskRegistrar;
        }
        this.scheduledTaskRegistrar.setScheduler(currencyTaskScheduler);

        Trigger trigger = triggerContext -> {
            taskRegistrar.setTriggerTasksList(new ArrayList<>());
            taskRegistrar.destroy(); // destroys previously scheduled tasks.
            taskRegistrar.setScheduler(currencyTaskScheduler);
            taskRegistrar.afterPropertiesSet();
            CronTrigger cronTrigger = new CronTrigger(cronExpression);

            Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
            if (lastActualExecutionTime == null) {
                LOGGER.info("Sync running for the first time for job {}", jobName);
                lastActualExecutionTime = new Date();
            }
            LOGGER.info(
                    "{} sync : lastActualExecutionTime {}. Next schedule : {}. If the schedule has already passed",
                    this.jobName,
                    lastActualExecutionTime,
                    nextExecutionTime);

            return cronTrigger.nextExecutionTime(triggerContext);
        };

        scheduledFuture = this.scheduledTaskRegistrar
                .getScheduler()
                .schedule(this, trigger);

    }

    public synchronized void updateRateSchedule(String updatedCronExpression) {
        LOGGER.info("Sync schedule is updated for {} from {} to {} seconds", jobName, cronExpression, updatedCronExpression);
        this.cronExpression = updatedCronExpression;

        long delayInSeconds = this.scheduledFuture.getDelay(TimeUnit.SECONDS);
        LOGGER.info("Current scheduledTask delay {}", delayInSeconds);

        if (delayInSeconds >= 0) {
            if (delayInSeconds >= CANCEL_SCHEDULED_TASK_DELAY_THRESHOLD_IN_SECONDS) {
                LOGGER.info("Next sync is more than {} seconds away." +
                                "scheduledFuture.delay() is {}." +
                                "Hence cancelling the schedule and rescheduling.",
                        CANCEL_SCHEDULED_TASK_DELAY_THRESHOLD_IN_SECONDS,
                        delayInSeconds);

                boolean cancel = this.scheduledFuture.cancel(false); //do not interrupt the current run if it kicked in.
                LOGGER.info(
                        "future.cancel() returned {}. isCancelled() : {} isDone : {}",
                        cancel,
                        scheduledFuture.isCancelled(),
                        scheduledFuture.isDone());
                LOGGER.info("Reconfiguring sync for {} with new schedule {}", jobName, updatedCronExpression);
                configureTasks(this.scheduledTaskRegistrar);
            }
        }
    }

    public Date getNextSyncExecutionTime() {
        return nextExecutionTime;
    }

    public void trigger() {
        run();
    }
}