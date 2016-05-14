package com.noctest.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by admin on 5/05/2016.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {


    @Autowired
    private GetBusLocationTask busLocation;

    @Autowired
    private LoginTask loginTask;


    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addFixedRateTask(new Runnable() {
            public void run() {
                busLocation.work();
            }
        }, 10000);


        taskRegistrar.addFixedRateTask(new Runnable() {
            public void run() {
                loginTask.work();
            }
        }, 1200000);
    }
}

