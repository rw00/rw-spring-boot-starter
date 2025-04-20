package com.rw.apps.starter.common.security.filter;

import com.rw.apps.starter.common.security.model.CurrentUser;
import com.rw.apps.starter.common.security.util.AdminUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

@Component
public class AdminAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!loadAdminUser(request, response)) {
            return;
        }

        // not an admin request, next filter...
        filterChain.doFilter(request, response);
    }

    public boolean loadAdminUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        CurrentUser currentUser = Optional.ofNullable(request.getSession(false))
                                          .map(session -> session.getAttribute(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME))
                                          .filter(SecurityContext.class::isInstance)
                                          .map(SecurityContext.class::cast)
                                          .map(SecurityContext::getAuthentication)
                                          .filter(Objects::nonNull)
                                          .map(Authentication::getPrincipal)
                                          .filter(CurrentUser.class::isInstance)
                                          .map(CurrentUser.class::cast)
                                          .filter(user -> !CollectionUtils.isEmpty(user.getAuthorities()))
                                          .filter(user -> AdminUtils.isAdminAuthority(user.getAuthorities()))
                                          .orElse(null);

        if (currentUser == null && (AdminConstants.ADMIN_URLS.stream().anyMatch(requestURI::startsWith))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // not pretty error response
            return false;
        }

        if (currentUser != null) {
            var authToken = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        return true;
    }
}
