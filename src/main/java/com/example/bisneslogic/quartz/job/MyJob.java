package com.example.bisneslogic.quartz.job;

import com.example.bisneslogic.services.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class MyJob extends QuartzJobBean {

    @Autowired
    private EmailService emailService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        emailService.sendEmail("yekekab648@shaflyn.com");
    }
}
