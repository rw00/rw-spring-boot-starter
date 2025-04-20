package com.rw.apps.starter.common.security.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

class UsernameOnlyAuthProvider extends DaoAuthenticationProvider {

    UsernameOnlyAuthProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(TruePasswordEncoder.INSTANCE);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails user = getUserDetailsService().loadUserByUsername(username);

        if (user == null) {
            throw new AuthenticationException("Account not found") {
            };
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private static final class TruePasswordEncoder implements PasswordEncoder {
        static final PasswordEncoder INSTANCE = new TruePasswordEncoder();

        private TruePasswordEncoder() {
        }

        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    }
}
