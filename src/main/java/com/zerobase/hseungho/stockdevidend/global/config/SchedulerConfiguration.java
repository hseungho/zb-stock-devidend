package com.zerobase.hseungho.stockdevidend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

        int n = Runtime.getRuntime().availableProcessors();
        threadPool.setPoolSize(n);
        threadPool.initialize();

        taskRegistrar.setTaskScheduler(threadPool);
    }

}
