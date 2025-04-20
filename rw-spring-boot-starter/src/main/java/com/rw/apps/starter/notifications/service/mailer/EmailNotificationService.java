package com.rw.apps.starter.notifications.service.mailer;

import jakarta.mail.internet.MimeMessage;

public interface EmailNotificationService {
    // TODO should have own model for message
    void sendEmail(MimeMessage mimeMessage);
}
