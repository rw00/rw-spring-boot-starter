package com.rw.apps.starter.contact.service;

import com.rw.apps.starter.common.config.AppConfigProperties.AdminsAccountsConfigProperties;
import com.rw.apps.starter.common.config.BaseAccountConfigProps;
import com.rw.apps.starter.common.exceptions.InvalidRequestException;
import com.rw.apps.starter.common.util.StringsNormalizer;
import com.rw.apps.starter.contact.model.ContactUsRequest;
import com.rw.apps.starter.notifications.Constants;
import com.rw.apps.starter.notifications.service.mailer.EmailNotificationService;
import com.rw.apps.starter.notifications.service.mailer.msg.MailMessageCreator;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnClass(name = Constants.MAIL_CLASS_NAME)
public class ContactUsService {
    private final String appName;
    private final EmailNotificationService emailService;
    private final MailMessageCreator mailMessageCreator;
    private final String[] adminsEmails;

    public ContactUsService(@Value("${spring.application.name}") String appName,
                            EmailNotificationService emailService,
                            MailMessageCreator mailMessageCreator,
                            AdminsAccountsConfigProperties adminsAccountsConfigProperties) {
        this.appName = appName;
        this.emailService = emailService;
        this.mailMessageCreator = mailMessageCreator;
        this.adminsEmails = adminsAccountsConfigProperties.accounts().stream()
                                                          .map(BaseAccountConfigProps::email)
                                                          .toArray(String[]::new);
    }

    public void contactUs(ContactUsRequest contactUsRequest) {
        String emailContent = StringsNormalizer.normalize(contactUsRequest.text());
        if (emailContent.isEmpty()) {
            throw new InvalidRequestException("Invalid contact-us message");
        }

        notifyAdmins(contactUsRequest, emailContent);

        notifyUser(contactUsRequest, emailContent);
    }

    private void notifyAdmins(ContactUsRequest contactUsRequest, String emailContent) {
        String subject = String.format("%s - Contact Us - %s", appName, contactUsRequest.subject());
        Map<String, String> templateParams = Map.of("name",
                                                    contactUsRequest.name(),
                                                    "email",
                                                    contactUsRequest.email(),
                                                    "text",
                                                    emailContent);
        MimeMessage adminsMessage = mailMessageCreator.createMailMessage(subject,
                                                                         "contact_us_admins",
                                                                         templateParams,
                                                                         adminsEmails);
        emailService.sendEmail(adminsMessage);
    }

    private void notifyUser(ContactUsRequest contactUsRequest, String emailContent) {
        Map<String, String> templateParams = Map.of("name", StringsNormalizer.normalize(contactUsRequest.name()),
                                                    "text", emailContent);
        MimeMessage mailMessage = mailMessageCreator.createMailMessage(String.format("%s - contact", appName),
                                                                       "contact_us_confirmation",
                                                                       templateParams,
                                                                       contactUsRequest.email()
        );
        emailService.sendEmail(mailMessage);
    }
}
