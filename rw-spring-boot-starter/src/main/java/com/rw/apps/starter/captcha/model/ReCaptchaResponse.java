package com.rw.apps.starter.captcha.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReCaptchaResponse {
    @JsonProperty("success")
    private final boolean success;
    @JsonProperty("hostname")
    private final String hostname;
    @JsonProperty("error-codes")
    private final List<ErrorCode> errorCodes;
    @JsonProperty("action")
    private final String action;
    @JsonProperty("score")
    private final double score;

    @JsonCreator
    public ReCaptchaResponse(boolean success, String hostname, List<ErrorCode> errorCodes, String action, double score) {
        this.success = success;
        this.hostname = hostname;
        this.errorCodes = errorCodes;
        this.action = action;
        this.score = score;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAction() {
        return action;
    }

    public double getScore() {
        return score;
    }

    @JsonIgnore
    public boolean hasClientError() {
        if (errorCodes == null) {
            return false;
        }
        for (ErrorCode error : errorCodes) {
            switch (error) {
                case INVALID_RESPONSE, MISSING_RESPONSE:
                    return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean hasErrors() {
        return !CollectionUtils.isEmpty(errorCodes);
    }

    public enum ErrorCode {
        MISSING_SECRET, INVALID_SECRET,
        MISSING_RESPONSE, INVALID_RESPONSE;

        private static final Map<String, ErrorCode> ERROR_CODES = Map.of(
                "missing-input-secret", MISSING_SECRET,
                "invalid-input-secret", INVALID_SECRET,
                "missing-input-response", MISSING_RESPONSE,
                "invalid-input-response", INVALID_RESPONSE
        );

        @JsonCreator
        public static ErrorCode forCode(String code) {
            return ERROR_CODES.get(code.toLowerCase());
        }
    }
}
