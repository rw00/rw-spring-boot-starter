package com.rw.apps.starter.common.security.config;

import com.rw.apps.starter.accounts.model.Authorities;
import com.rw.apps.starter.common.env.cfg.AppConfig;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
class StarterSecurityConfig {
    private final ApplicationContext context;

    StarterSecurityConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile(AppConfig.LOCAL_PROFILE)
    AuthenticationManager localAuthenticationManager(UserDetailsService userDetailsService) {
        return new ProviderManager(List.of(new UsernameOnlyAuthProvider(userDetailsService)));
    }

    @Bean
    SecurityFilterChain adminSecurityFilterChain(HttpSecurity httpSecurity,
                                                 AuthenticationManager authenticationManager) throws Exception {
        // TODO: create another securityFilterChain for login
        httpSecurity
                // See com.rw.apps.starter.common.security.filter.AdminConstants
                .securityMatcher("/login", "/default-ui.css", "/admin/**", "/management/**", "/actuator/**")
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/default-ui.css").permitAll()
                        .anyRequest().hasAuthority(Authorities.ROLE_ADMIN.name()))
                .authenticationManager(authenticationManager)
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                .sessionManagement(session -> session.maximumSessions(1))
                .formLogin(login -> login
                        .defaultSuccessUrl("/admin/api-docs")
                        .permitAll())
                .logout(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    RequestMatcher[] anonymousRequestsMatchers(AnonymousRequestsContainer anonymousRequestsContainer) {
        HandlerMappingIntrospector introspector = context.getBean("mvcHandlerMappingIntrospector",
                                                                  HandlerMappingIntrospector.class);

        List<String> urlPatterns = anonymousRequestsContainer.getUrlPatterns();
        int n = urlPatterns.size();
        RequestMatcher[] anonymousRequestsMatchers = new RequestMatcher[n];
        for (int i = 0; i < n; i++) {
            String openUrlPattern = urlPatterns.get(i);
            anonymousRequestsMatchers[i] = new MvcRequestMatcher(introspector, openUrlPattern);
        }
        return anonymousRequestsMatchers;
    }

    @Bean
    @ConditionalOnMissingBean
    AnonymousRequestsContainer anonymousRequestsContainer() {
        return List::of;
    }
}
