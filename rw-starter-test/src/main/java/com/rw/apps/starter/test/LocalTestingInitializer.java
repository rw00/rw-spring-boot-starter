package com.rw.apps.starter.test;

import com.rw.apps.starter.common.config.AppConfigProperties.AdminsAccountsConfigProperties;
import com.rw.apps.starter.common.config.BaseAccountConfigProps;
import com.rw.apps.starter.common.env.cfg.AppConfig;
import com.rw.apps.starter.test.users.repository.AccountsRepository;
import com.rw.apps.starter.test.users.repository.entity.AccountEntity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile(AppConfig.LOCAL_PROFILE)
@EnableConfigurationProperties(AdminsAccountsConfigProperties.class)
public class LocalTestingInitializer implements ApplicationRunner {
    private final AccountsRepository accountsRepository;
    private final AdminsAccountsConfigProperties adminsAccountsConfigProperties;
    private final PasswordEncoder passwordEncoder;

    public LocalTestingInitializer(AccountsRepository accountsRepository, AdminsAccountsConfigProperties adminsAccountsConfigProps, PasswordEncoder passwordEncoder) {
        this.accountsRepository = accountsRepository;
        this.adminsAccountsConfigProperties = adminsAccountsConfigProps;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (BaseAccountConfigProps account : adminsAccountsConfigProperties.accounts()) {
            if (accountsRepository.findByEmail(account.email()).isEmpty()) {
                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setEmail(account.email());
                accountEntity.setPassword(passwordEncoder.encode(account.pass())); // passwords don't matter locally
                accountsRepository.save(accountEntity);
            }
        }
    }
}
