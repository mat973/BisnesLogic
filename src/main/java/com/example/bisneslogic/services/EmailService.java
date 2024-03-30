package com.example.bisneslogic.services;

import com.example.bisneslogic.models.UserInfo;
import com.example.bisneslogic.quartz.job.MyJob;
import com.example.bisneslogic.repositories.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor
@AllArgsConstructor
public class EmailService {

    static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail2(String email) {
        log.info(email);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("To address must not be blank");
        }
        try {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserInfo userInfo = userRepository.findByUsername(authentication.getName());
            helper.setTo(email);
            helper.setSubject("Order");
            helper.setText("Thanks fo order");
            javaMailSender.send(message);
            System.out.println("Письмо отправлено успешно.");


        } catch (MessagingException e) {
            System.err.println("Ошибка при отправке письма: " + e.getMessage());
        }
    }


    public void sendEmail(String email, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserInfo userInfo = userRepository.findByUsername(authentication.getName());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body);
            javaMailSender.send(message);
            System.out.println("Письмо отправлено успешно. 7");


        } catch (MessagingException e) {
            System.err.println("Ошибка при отправке письма: " + e.getMessage());
        }
    }


}