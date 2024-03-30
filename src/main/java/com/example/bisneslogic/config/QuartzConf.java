//package com.example.bisneslogic.config;
//
//import com.example.bisneslogic.dto.EmailRequest;
//import com.example.bisneslogic.quartz.job.EmailJob;
//import com.example.bisneslogic.quartz.job.MyJob;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import java.sql.Date;
//import java.time.ZonedDateTime;
//import java.util.UUID;
//
//
//@Configuration
//public class QuartzConf {
//
//
//    @Bean
//    public JobDetail buildJobDetail(){
//        JobDataMap jobDataMap =new JobDataMap();
//        jobDataMap.put("email", scheduleEmailRequest.getEmail());
//        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
//        jobDataMap.put("body", scheduleEmailRequest.getBody());
//        return JobBuilder.newJob(EmailJob.class)
//                .withIdentity(UUID.randomUUID().toString(),"email-jobs")
//                .withDescription("Send Email Job")
//                .usingJobData(jobDataMap)
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
//                .withIdentity(jobDetail.getKey().getName(),"email-triggers")
//                .withDescription("Send Email Trigger")
//                .startAt(Date.from(startAt.toInstant()))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
//                .build();
//    }
////    @Autowired
////    private ApplicationContext applicationContext;
////    @Bean
////    public JobDetail myJobDetail() {
////        return JobBuilder.newJob(MyJob.class)
////                .withIdentity("cronJob", "groupCrone")
////                .build();
////    }
////
////    @Bean
////    public Trigger myJobTrigger() {
////        return TriggerBuilder.newTrigger()
////                .forJob(myJobDetail())
////                .withIdentity("cronTrigger", "groupCrone")
////                .withSchedule(CronScheduleBuilder.cronSchedule("0 5-59/5 * * * ?"))
////                .build();
////    }
////
////
////    @Bean
////    public SchedulerFactoryBean schedulerFactoryBean() {
////        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
////        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
////        schedulerFactoryBean.setOverwriteExistingJobs(true);
////        schedulerFactoryBean.setAutoStartup(true);
////        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
////
////        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory(applicationContext);
////        schedulerFactoryBean.setJobFactory(jobFactory);
////
////        return schedulerFactoryBean;
////    }
//}
//
