package com.rw.apps.starter.accounts.repository.entity;

import com.rw.apps.starter.accounts.model.AccountProvider;
import com.rw.apps.starter.accounts.model.Authorities;
import com.rw.apps.starter.accounts.repository.entity.converter.StringToAccountProvidersSetConverter;
import com.rw.apps.starter.accounts.repository.entity.converter.StringToRolesSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Hibernate;

@MappedSuperclass
public abstract class BaseAccountEntity {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String id;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    @Convert(converter = StringToRolesSetConverter.class)
    private Set<Authorities> authorities;
    @Column(nullable = false)
    @Convert(converter = StringToAccountProvidersSetConverter.class)
    private Set<AccountProvider> accountProviders;
    @Column
    private String confirmationToken;
    @Column
    private String password;
    @Column
    private String passwordResetToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public Set<AccountProvider> getAccountProviders() {
        return accountProviders;
    }

    public void setAccountProviders(Set<AccountProvider> accountProviders) {
        this.accountProviders = accountProviders;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        BaseAccountEntity that = (BaseAccountEntity) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return getClass().hashCode();
        }
        return Objects.hash(id);
    }
}
