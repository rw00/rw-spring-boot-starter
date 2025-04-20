package com.rw.apps.starter.notifications.service.mailer.msg;

import com.rw.apps.starter.notifications.Constants;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@ConditionalOnClass(name = Constants.MAIL_CLASS_NAME)
public class MailMessageCreator {
    private static final Logger log = LoggerFactory.getLogger(MailMessageCreator.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final String appEmailAddress;

    public MailMessageCreator(JavaMailSender mailSender,
                              TemplateEngine templateEngine,
                              @Value("${spring.mail.username}") String appEmailAddress) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.appEmailAddress = appEmailAddress;
    }

    public MimeMessage createMailMessage(String subject,
                                         String templateName,
                                         Map<String, String> templateParams,
                                         String... toEmails) {
        try {
            Context ctx = new Context(LocaleContextHolder.getLocale());
            templateParams.forEach(ctx::setVariable);
            String htmlContent = templateEngine.process(templateName, ctx);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(appEmailAddress);
            messageHelper.setSubject(subject);
            messageHelper.setTo(toEmails);
            messageHelper.setText(htmlContent, true);

            return mimeMessage;
        } catch (Exception e) {
            log.warn("Could not create '{}' message to user '{}'", templateName, toEmails, e);
            throw new IllegalStateException("Could not create message", e);
        }
    }
}
