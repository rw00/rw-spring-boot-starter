package com.rw.apps.starter.common.security.jwt;

import com.rw.apps.starter.common.api.helper.AuthErrorResponseHelper;
import com.rw.apps.starter.common.api.util.OriginUtil;
import com.rw.apps.starter.common.config.AppConfigProperties.ServerConfigProperties;
import com.rw.apps.starter.common.env.EnvHelper;
import com.rw.apps.starter.common.exceptions.auth.AuthException;
import com.rw.apps.starter.common.exceptions.auth.UnauthorizedException;
import com.rw.apps.starter.common.security.config.CustomUserDetailsService;
import com.rw.apps.starter.common.security.filter.AdminAuthenticationFilter;
import com.rw.apps.starter.common.security.model.CurrentUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtCurrentUserExtractor currentUserExtractor;
    private final CustomUserDetailsService userDetailsService;
    private final AuthErrorResponseHelper authErrorResponseHelper;
    private final EnvHelper envHelper;
    private final AdminAuthenticationFilter adminAuthFilter;
    private final ServerConfigProperties serverConfigProperties;

    public JwtAuthenticationFilter(JwtCurrentUserExtractor currentUserExtractor,
                                   CustomUserDetailsService userDetailsService,
                                   AuthErrorResponseHelper authErrorResponseHelper,
                                   EnvHelper envHelper,
                                   AdminAuthenticationFilter adminAuthFilter,
                                   ServerConfigProperties serverConfigProperties) {
        this.currentUserExtractor = currentUserExtractor;
        this.userDetailsService = userDetailsService;
        this.authErrorResponseHelper = authErrorResponseHelper;
        this.envHelper = envHelper;
        this.adminAuthFilter = adminAuthFilter;
        this.serverConfigProperties = serverConfigProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String origin = OriginUtil.getOrigin(request);
        if (origin != null && origin.startsWith(serverConfigProperties.baseUrl())) {
            log.info("Server-side page; skipping JWT auth filter...");
            adminAuthFilter.loadAdminUser(request, response);
            filterChain.doFilter(request, response);
            return;
        }

        CurrentUser user = resolveCurrentUser(request, response, filterChain);
        if (user == null) {
            return;
        }

        CurrentUser trustedUserDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (!user.getUserId().equals(trustedUserDetails.getUserId())) {
            UnauthorizedException ex = new UnauthorizedException("Unauthorized");
            authErrorResponseHelper.writeError(response, ex);
            return;
        }
        var authToken = new UsernamePasswordAuthenticationToken(trustedUserDetails,
                                                                null,
                                                                trustedUserDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private CurrentUser resolveCurrentUser(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain filterChain) throws IOException, ServletException {
        try {
            CurrentUser user = currentUserExtractor.extractUser(request);
            if (user != null) {
                return user;
            }
        } catch (AuthException e) {
            authErrorResponseHelper.writeError(response, e);
            return null;
        }

        if (envHelper.isLive()) {
            UnauthorizedException ex = new UnauthorizedException("Invalid login");
            authErrorResponseHelper.writeError(response, ex);
            return null;
        }
        log.warn("! Invalid JWT - WILL BE BLOCKED ON LIVE !");
        filterChain.doFilter(request, response);
        return null;
    }
}
