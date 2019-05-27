package co.qyef.starter.firebase.service;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service("mailService")
@Component("mailService")
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private MessageSource messageSource;

    private String from;

    @PostConstruct
    public void init() {
        this.from = env.getProperty("spring.mail.from");
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'!", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(final String email, String content, Locale locale) {
        log.debug("Sending activation e-mail to '{}'", email);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(email, subject, content, false, true);
    }
}
