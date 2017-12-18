package com.feo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * Created by huang on 2017/10/27.
 */
@Component
public class MailSendUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 发送简单邮件
     */
    public void sendSimpleMail(String receiive, String subject, String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(receiive);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * 发送html邮件
     */
    public void sendHtmlMail(String receiive, String subject, String text) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiive);
            helper.setSubject(subject);

            StringBuffer sb = new StringBuffer();
            sb.append(text);
            helper.setText(sb.toString(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

}
