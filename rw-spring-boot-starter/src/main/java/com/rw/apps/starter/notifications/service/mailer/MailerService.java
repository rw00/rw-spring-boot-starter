package com.rw.apps.starter.notifications.service.mailer;

import com.rw.apps.starter.common.env.cfg.AppConfig;
import com.rw.apps.starter.notifications.Constants;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile(AppConfig.LIVE_PROFILE)
@ConditionalOnClass(name = Constants.MAIL_CLASS_NAME)
public class MailerService implements EmailNotificationService {
    private static final Logger log = LoggerFactory.getLogger(MailerService.class);
    private final JavaMailSender mailSender;

    public MailerService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(MimeMessage mimeMessage) {
        CompletableFuture.runAsync(() -> {
            try {
                mailSender.send(mimeMessage);
            } catch (Exception e) {
                log.warn("E-mail could not be sent", e);
            }
        });
    }
}
