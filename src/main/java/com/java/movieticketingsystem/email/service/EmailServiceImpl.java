package com.java.movieticketingsystem.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.java.movieticketingsystem.utils.constants.EmailConstants.*;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${cors.originUrl}")
    private String appUrl;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendResetPasswordEmail(String toEmail, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(RESET_YOUR_PASSWORD_SUBJECT);
            helper.setText(String.format(RESET_LINK_MESSAGE, appUrl, resetToken));
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(FAILED_TO_SEND_PASSWORD_RESET_EMAIL, e);
            throw new RuntimeException(FAILED_TO_SEND_PASSWORD_RESET_EMAIL);
        }
    }

}
