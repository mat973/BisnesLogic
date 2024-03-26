package com.example.bisneslogic.services;

import com.example.bisneslogic.models.UserInfo;
import com.example.bisneslogic.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = userRepository.findByUsername(authentication.getName());
            helper.setTo(userInfo.getEmail());
            helper.setSubject("Order");
            helper.setText("Thanks fo order");
            javaMailSender.send(message);
            System.out.println("Письмо отправлено успешно.");

        javaMailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Ошибка при отправке письма: " + e.getMessage());
        }
    }
}