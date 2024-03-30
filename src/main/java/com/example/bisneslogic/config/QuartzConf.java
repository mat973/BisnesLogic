package com.example.bisneslogic.config;

import com.example.bisneslogic.dto.EmailRequest;
import com.example.bisneslogic.quartz.job.EmailJob;
import com.example.bisneslogic.quartz.job.MyJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.UUID;


@Configuration
public class QuartzConf {
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startQuartzJobs() {
        try {
            // Создаем работу, которая будет выполняться каждые 5 минут
            scheduleContinuousJob("continuousEmailJob", MyJob.class);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void scheduleContinuousJob(String jobId, Class<? extends Job> jobClass) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobId)
                .storeDurably()
                .build();

        // Создаем триггер с интервалом выполнения 5 минут
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobId + "Trigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();

        // Расписание для SimpleTrigger, чтобы работа запускалась каждые 5 минут
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
//@Configuration
//public class QuartzConf {
//    @Autowired
//    private Scheduler scheduler;
//
//    @PostConstruct
//    public void startQuartzJobs() {
//        try {
//            // Создаем постоянно крутящуюся работу при старте приложения
//            scheduleContinuousJob("continuousEmailJob", MyJob.class, "* */5 * ? * *"); // каждые 5 минут
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void scheduleContinuousJob(String jobId, Class<? extends Job> jobClass, String cronExpression) throws SchedulerException {
//        JobDetail jobDetail = JobBuilder.newJob(jobClass)
//                .withIdentity(jobId)
//                .storeDurably()
//                .build();
//
//        // Проверяем, существует ли уже триггер для данной работы
//        if (scheduler.checkExists(new TriggerKey(jobId + "Trigger"))) {
//            // Если существует, обновляем его
//            Trigger oldTrigger = scheduler.getTrigger(new TriggerKey(jobId + "Trigger"));
//            Trigger newTrigger = TriggerBuilder.newTrigger()
//                    .forJob(jobDetail)
//                    .withIdentity(oldTrigger.getKey())
//                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
//                    .build();
//            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
//        } else {
//            // Если триггер не существует, создаем новый
//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .forJob(jobDetail)
//                    .withIdentity(jobId + "Trigger")
//                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
//                    .build();
//            scheduler.scheduleJob(jobDetail, trigger);
//        }
//    }
//}

