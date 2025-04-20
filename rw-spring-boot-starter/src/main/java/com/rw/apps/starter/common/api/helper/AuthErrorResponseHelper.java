package com.rw.apps.starter.common.api.helper;

import com.rw.apps.starter.common.exceptions.auth.AuthException;
import com.rw.apps.starter.common.exceptions.handlers.AuthExceptionsHandler;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
public class AuthErrorResponseHelper {
    private final AuthExceptionsHandler authExceptionsHandler;
    private final ProblemResponseHelper problemResponseHelper;

    public AuthErrorResponseHelper(AuthExceptionsHandler authExceptionsHandler,
                                   ProblemResponseHelper problemResponseHelper) {
        this.authExceptionsHandler = authExceptionsHandler;
        this.problemResponseHelper = problemResponseHelper;
    }

    public void writeError(HttpServletResponse response, AuthException ex) throws IOException {
        ProblemDetail problemDetail = authExceptionsHandler.handleAnyAuthException(ex);
        problemResponseHelper.writeErrorResponse(response, problemDetail);
    }
}
