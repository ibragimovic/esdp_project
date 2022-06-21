package com.esdp.demo_esdp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
//    @Value("${spring.mail.protocol}")
//    private String protocol;
//    @Value("${mail.debug}")
//    private String debug;
//    @Value("mail.smtp.ssl.enable")
//    private String enable;
//    @Value("${spring.mail.properties.mail.smtp.auth}")
//    private String auth;
//    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private String starttls;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");;
        properties.put("mail.debug", true);
        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("spring.mail.properties.mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtps.ssl.checkserveridentity", "true");
        properties.put("mail.smtps.ssl.trust", "*");
        properties.put("mail.smtp.ssl.enable", "true");
        return mailSender;
    }
}
