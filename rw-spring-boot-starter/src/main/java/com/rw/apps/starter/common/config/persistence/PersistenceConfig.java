package com.rw.apps.starter.common.config.persistence;

import com.rw.apps.starter.common.util.DateTimeUtil;
import java.util.Optional;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "defaultDateTimeProvider")
@EnableAutoConfiguration(exclude =
        JdbcClientAutoConfiguration.class /* not sure why it's being loaded in the first place */
)
@ConditionalOnClass(name = "javax.persistence.EntityManager")
public class PersistenceConfig {

    @Bean
    @ConditionalOnMissingBean
    DateTimeProvider defaultDateTimeProvider() {
        return () -> Optional.of(DateTimeUtil.now());
    }
}
