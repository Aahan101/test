package com.increff.employee.scheduler;
import com.increff.employee.model.DayonDaySalesData;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DayonDaySalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@EnableScheduling
public class Scheduler {

    @Autowired
    private DayonDaySalesService dayonDaySalesService;

    @Async
    @Scheduled(cron = "0 58 15 * * ? ") // Runs every 5 seconds
    public void scheduledTask() throws ApiException {

        dayonDaySalesService.getRelevantAll();
    }
}