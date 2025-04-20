package com.rw.apps.starter.common.security.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rw.apps.starter.common.security.model.json.AuthoritiesJsonDeserializer;
import com.rw.apps.starter.common.security.model.json.AuthoritiesJsonSerializer;
import com.rw.apps.starter.common.security.util.OAuth2Util;
import java.io.Serial;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CurrentUser implements Principal, UserDetails, OAuth2User { // not a record, deliberately
    @Serial
    private static final long serialVersionUID = 1L;
    private final String userId;
    private final String username;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;
    @JsonIgnore
    private final String password;
    @JsonIgnore
    private final Map<String, Object> attributes; // NOSONAR

    // For OAuth2 login
    public CurrentUser(String userId, OAuth2User user) {
        this.userId = userId;
        this.username = OAuth2Util.getEmail(user);
        this.authorities = user.getAuthorities();
        this.attributes = user.getAttributes();
        this.enabled = true;
        this.password = null;
    }

    // For pass login
    public CurrentUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.attributes = null;
    }

    // For JWT decoding
    @JsonCreator
    public CurrentUser(@JsonProperty("userId") String userId, @JsonProperty("username") String username, @JsonProperty("enabled") boolean enabled, @JsonProperty("authorities") @JsonDeserialize(using = AuthoritiesJsonDeserializer.class) @JsonSerialize(using = AuthoritiesJsonSerializer.class) Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.enabled = enabled;
        this.authorities = authorities;
        this.password = null;
        this.attributes = null;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getName() {
        if (attributes != null) {
            return getAttribute("name");
        }
        return username;
    }

    @JsonIgnore
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
