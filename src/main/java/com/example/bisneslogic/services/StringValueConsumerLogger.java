package com.example.bisneslogic.services;


import java.util.List;

import com.example.bisneslogic.models.StringValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;




public class StringValueConsumerLogger implements StringValueConsumer {
//    private static final Logger logi = LoggerFactory.getLogger(StringValueConsumerLogger.class);
 static final Logger logi = LoggerFactory.getLogger(StringValueConsumerLogger.class);

 public int digit =1;


    private EmailService emailService;

    public StringValueConsumerLogger(EmailService emailService) {
        this.emailService = emailService;
    }
    public StringValueConsumerLogger() {

    }



    @Override
    public void accept(StringValue values) {

        logi.info("log:{}" + values);
        logi.info(""+values.value());
        logi.info("email was send");
        if (emailService != null  ) {
//            if(values.id()== digit) {
//                    digit+=1;
                   emailService.sendEmail2(values.value());
//            }
        } else {
            logi.error("EmailService is null. Unable to send email.");
        }



    }
}