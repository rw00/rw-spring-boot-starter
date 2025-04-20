package com.rw.apps.starter.contact.model;

import com.rw.apps.starter.common.api.ApiConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactUsRequest(
        @NotBlank String name,
        @NotNull @Email(message = ApiConstants.INVALID_EMAIL_MESSAGE) String email,
        @NotNull ContactUsSubject subject,
        @NotBlank @Size(min = 20, max = 1000) String text
) {
}
