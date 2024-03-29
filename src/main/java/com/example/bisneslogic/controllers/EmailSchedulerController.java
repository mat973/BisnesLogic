package com.example.bisneslogic.controllers;

import com.example.bisneslogic.dto.EmailRequest;
import com.example.bisneslogic.dto.EmailResponse;
import com.example.bisneslogic.quartz.job.EmailJob;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.UUID;
@Slf4j
@RestController
public class EmailSchedulerController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/schedule/email")
    public ResponseEntity<EmailResponse> scheduleEmail(@Valid @RequestBody EmailRequest emailRequest){
        try{
                ZonedDateTime dateTime = ZonedDateTime.of(emailRequest.getLocalDateTime(), emailRequest.getTimezone());
                if (dateTime.isBefore(ZonedDateTime.now())) {
                    EmailResponse emailResponse= new EmailResponse(false,"dateTime must be after current time");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(emailResponse);
                }

                JobDetail jobDetail = buildJobDetail(emailRequest);
                Trigger trigger = buildTrigger(jobDetail, dateTime);
                scheduler.scheduleJob(jobDetail, trigger);

                EmailResponse emailResponse = new EmailResponse(true, jobDetail.getKey().getName(),
                        jobDetail.getKey().getGroup(), "Email Schedule Successfuly");
                return ResponseEntity.ok(emailResponse);
        }catch (SchedulerException se){
            log.error("Error while scheduling email", se);
            EmailResponse emailResponse =new EmailResponse(false,"Error while scheduling email, please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(emailResponse);
        }
    }




    private JobDetail buildJobDetail(EmailRequest scheduleEmailRequest){
        JobDataMap jobDataMap =new JobDataMap();
        jobDataMap.put("email", scheduleEmailRequest.getEmail());
        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
        jobDataMap.put("body", scheduleEmailRequest.getBody());
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(),"email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(),"email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
