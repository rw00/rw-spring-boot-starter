package com.rw.apps.starter.contact.api;

import com.rw.apps.starter.captcha.service.CaptchaService;
import com.rw.apps.starter.captcha.service.CaptchaService.Actions;
import com.rw.apps.starter.captcha.service.ReCaptchaService;
import com.rw.apps.starter.contact.model.ContactUsRequest;
import com.rw.apps.starter.contact.service.ContactUsService;
import com.rw.apps.starter.notifications.Constants;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/contact")
@ConditionalOnClass(name = Constants.MAIL_CLASS_NAME)
class ContactApiV1 {
    private final CaptchaService captchaService;
    private final ContactUsService contactUsService;

    ContactApiV1(CaptchaService captchaService, ContactUsService contactUsService) {
        this.captchaService = captchaService;
        this.contactUsService = contactUsService;
    }

    @PostMapping("us")
    void contactUs(@RequestBody @Valid ContactUsRequest contactUsRequest,
                   @RequestHeader(value = ReCaptchaService.RECAPTCHA_TOKEN_NAME, required = false) String captchaToken) {
        captchaService.validateCaptcha(captchaToken, Actions.CONTACT_US);
        contactUsService.contactUs(contactUsRequest);
    }
}
