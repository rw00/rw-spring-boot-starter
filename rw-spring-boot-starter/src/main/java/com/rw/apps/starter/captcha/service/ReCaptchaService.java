package com.rw.apps.starter.captcha.service;

import com.rw.apps.starter.captcha.InvalidCaptchaException;
import com.rw.apps.starter.captcha.config.CaptchaSettings;
import com.rw.apps.starter.captcha.model.ReCaptchaResponse;
import com.rw.apps.starter.common.api.util.HttpRequestUtil;
import com.rw.apps.starter.common.env.cfg.AppConfig;
import com.rw.apps.starter.common.exceptions.InvalidRequestException;
import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

@Service
@Profile(AppConfig.LIVE_PROFILE)
public class ReCaptchaService implements CaptchaService {
    public static final String RECAPTCHA_TOKEN_NAME = "recaptcha-token";
    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
    private final CaptchaSettings captchaSettings;
    private final CaptchaAttemptsService attemptsService;
    private final RestOperations restTemplate;

    public ReCaptchaService(CaptchaSettings captchaSettings, CaptchaAttemptsService attemptsService, RestOperations restTemplate) {
        this.captchaSettings = captchaSettings;
        this.attemptsService = attemptsService;
        this.restTemplate = restTemplate;
    }

    @Override
    public void validateCaptcha(String captchaResponse, Actions action) {
        String ipAddress = HttpRequestUtil.resolveClientIpAddress();
        if (attemptsService.isBlocked(ipAddress)) {
            throw new InvalidRequestException("Client exceeded maximum number of failed attempts");
        }

        if (!responseSanityCheck(captchaResponse)) {
            throw new InvalidCaptchaException("ReCaptcha could not be verified");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                captchaSettings.key().secret(), captchaResponse, ipAddress));

        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(verifyUri, ReCaptchaResponse.class);

        if (reCaptchaResponse != null
            && (!reCaptchaResponse.isSuccess()
                || !action.name().equals(reCaptchaResponse.getAction())
                || reCaptchaResponse.getScore() < captchaSettings.threshold())) {

            if (reCaptchaResponse.hasClientError()) {
                attemptsService.captchaFailed(ipAddress);
            }
            throw new InvalidCaptchaException("ReCaptcha was not successfully validated");
        }
        attemptsService.captchaSucceeded(ipAddress);
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
}
