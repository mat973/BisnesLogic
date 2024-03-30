package com.example.bisneslogic.quartz.job;

import com.example.bisneslogic.repositories.UserRepository;
import com.example.bisneslogic.services.DataSenderKafka;
import com.example.bisneslogic.services.EmailService;
import com.example.bisneslogic.util.ProductNotCreatedException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component

public class MyJob extends QuartzJobBean {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepository userRepository;
    static final Logger log = LoggerFactory.getLogger(MyJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String email = userRepository.findByMaxMoney().orElseThrow().getEmail();
        log.debug(email);
        emailService.sendEmail(email,"Акция", "по промокоду халява получите скидку 5% на следущй заказ");
    }
}
