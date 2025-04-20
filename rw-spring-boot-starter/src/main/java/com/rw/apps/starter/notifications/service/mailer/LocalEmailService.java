package com.rw.apps.starter.notifications.service.mailer;

import com.rw.apps.starter.common.env.cfg.AppConfig;
import com.rw.apps.starter.notifications.Constants;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(AppConfig.LOCAL_PROFILE)
@ConditionalOnClass(name = Constants.MAIL_CLASS_NAME)
public class LocalEmailService implements EmailNotificationService {
    private static final Logger log = LoggerFactory.getLogger(LocalEmailService.class);

    @Override
    public void sendEmail(MimeMessage mimeMessage) {
        try {
            log.info("Email: {}", mimeMessage.getContent());
        } catch (Exception e) {
            log.debug("Is the message null?", e);
        }
    }
}
