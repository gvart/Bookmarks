package dev.gva.bookmarks.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by pika on 11/30/16.
 */
@Configuration
@PropertySource(value = {"classpath:config.properties"})
public class MailConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enabled", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.host", environment.getProperty("mail.host"));

        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));
        mailSender.setPort(465);
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

    @Bean
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngineFactory velocityEngine = new VelocityEngineFactory();
        velocityEngine.setResourceLoaderPath("mail/");
        return velocityEngine.createVelocityEngine();
    }
}
