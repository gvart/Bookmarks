package dev.gva.bookmarks.service;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by pika on 11/30/16.
 */
@Service
public class EmailService {

    public static final String FROM = "from";

    public static final String TO = "to";
    /*Email Subject*/
    public static final String SUBJECT = "subject";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;


    public boolean sendMail(final String templateName, final Map<String, Object> model) {
        boolean res = false;
        try {
            MimeMessagePreparator preparator = mimeMessage -> {
                String from = (String) model.get("from");
                String to[] = (String[]) model.get("to");
                String subject = (String) model.get("subject");

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setSentDate(new Date());
                model.put("noArgs", new Object());

                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, templateName, "UTF-8", model);

                message.setText(text, true);
            };

            mailSender.send(preparator);
            res = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return res;
    }
}
